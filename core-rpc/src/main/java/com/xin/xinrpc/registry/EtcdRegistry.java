package com.xin.xinrpc.registry;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.json.JSONUtil;
import com.xin.xinrpc.model.ServiceMetaInfo;
import com.xin.xinrpc.rpc.RegistryConfig;
import io.etcd.jetcd.*;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.watch.WatchEvent;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * ClassName: EtcdRegistry
 * Package: com.xin.xinrpc.registry
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/29 下午6:12
 * @Version 1.0
 */
public class EtcdRegistry implements Registry{


    private Client client;

    private KV kvClient;

    // 本机注册节点key的集合 用于维护续期
    private final Set<String> localRegisterNodeKeySet = new HashSet<>();

    // 注册中心服务缓存 支持多个服务缓存
    private final RegistryServiceCache registryServiceCache = new RegistryServiceCache();

    /**
     * 正在监听的 key 集合
     */
    private final Set<String> watchingKeySet = new ConcurrentHashSet<>();


    // 根节点
    private static final String ETCD_ROOT_PATH = "rpc";

    @Override
    public void init(RegistryConfig registryConfig) {
        client = Client.builder()
                .endpoints(registryConfig.getAddress())
                .connectTimeout(Duration.ofMillis(registryConfig.getTimeOut()))
                .build();
        kvClient = client.getKVClient();

    }


    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {

        // 创建一个Lease 客户端
        Lease leaseClient = client.getLeaseClient();

        // 创建一个30s的租约
        long leaseId = leaseClient.grant(30).get().getID();

        // 设置要存储的键值对
        String registryKey = ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
        ByteSequence key = ByteSequence.from(registryKey, StandardCharsets.UTF_8);
        ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceMetaInfo), StandardCharsets.UTF_8);

        // 将键值对和租约关系关联起来  并设置过期时间
        PutOption putOption = PutOption.builder().withLeaseId(leaseId).build();
        kvClient.put(key,value,putOption).get();

        // 放到本地 后面可以进行续期
        localRegisterNodeKeySet.add(registryKey);

    }

    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) throws Exception {
        String registryKey = ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
        kvClient.delete(ByteSequence.from(registryKey, StandardCharsets.UTF_8)).get();

        // 从本地移除要续期的key
        localRegisterNodeKeySet.remove(registryKey);
    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) throws Exception {
        String searchPrefix = ETCD_ROOT_PATH + serviceKey;

        // 优先从cache中获取
        List<ServiceMetaInfo> serviceMetaInfoList = registryServiceCache.readServiceMetaInfos(serviceKey);
        if(serviceMetaInfoList != null){
            return serviceMetaInfoList;
        }


        // 进行前缀查询
        GetOption getOption = GetOption.builder().isPrefix(true).build();
        List<KeyValue> kvs = kvClient.get(ByteSequence.from(searchPrefix, StandardCharsets.UTF_8), getOption).get().getKvs();

        // 解析服务信息
        serviceMetaInfoList = kvs.stream().map(keyValue -> {
            String key = keyValue.getKey().toString();
            watch(key,serviceKey);
            String value = keyValue.getValue().toString(StandardCharsets.UTF_8);

            return JSONUtil.toBean(value, ServiceMetaInfo.class);
        }).collect(Collectors.toList());

        registryServiceCache.writeCache(serviceKey,serviceMetaInfoList);
        return serviceMetaInfoList;

    }

    @Override
    public void destroy() {
        // 清除本地set中所有的值
        for (String localRegister : localRegisterNodeKeySet) {
            try {
                kvClient.delete(ByteSequence.from(localRegister, StandardCharsets.UTF_8)).get();
                localRegisterNodeKeySet.remove(localRegister);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("当前节点下线");
        if(kvClient != null) {
            kvClient.close();
        }
        if(client != null) {
            client.close();
        }
    }


    public void heatBeat() {
        // 为了保证只有一个心跳机制，可以往注册中心中设置key值

        // 10 秒续签一次
        CronUtil.schedule("*/10 * * * * *", new Task() {
            @Override
            public void execute() {
                // 遍历本节点所有的 key
                for (String key : localRegisterNodeKeySet) {
                    try {
                        List<KeyValue> keyValues = kvClient.get(ByteSequence.from(key, StandardCharsets.UTF_8))
                                .get()
                                .getKvs();
                        // 该节点已过期（需要重启节点才能重新注册）
                        if (CollUtil.isEmpty(keyValues)) {
                            localRegisterNodeKeySet.remove(key);
                            continue;
                        }
                        // 节点未过期，重新注册（相当于续签）
                        KeyValue keyValue = keyValues.get(0);
                        String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                        ServiceMetaInfo serviceMetaInfo = JSONUtil.toBean(value, ServiceMetaInfo.class);
                        register(serviceMetaInfo);
                    } catch (Exception e) {
                        throw new RuntimeException(key + "续签失败", e);
                    }
                }
            }
        });

        // 支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();

    }

    // 如果出现了delete key事件，就清理服务注册缓存
    public void watch(String serviceNodeKey,String serviceKey) {
        Watch watchClient = client.getWatchClient();
        // 之前没有监听 开启监听
        boolean newWatch = watchingKeySet.add(serviceNodeKey);

        if (newWatch) {
            watchClient.watch(ByteSequence.from(serviceNodeKey,StandardCharsets.UTF_8),response->{
                for (WatchEvent event : response.getEvents()) {
                    switch (event.getEventType()){
                        case DELETE:
                        case PUT:
                            registryServiceCache.clearCache(serviceKey);
                            break;
                        default:
                            break;
                    }
                }
            });
        }

    }

}
