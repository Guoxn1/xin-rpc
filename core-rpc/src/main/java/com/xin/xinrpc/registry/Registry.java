package com.xin.xinrpc.registry;

import com.xin.xinrpc.model.ServiceMetaInfo;
import com.xin.xinrpc.rpc.RegistryConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ClassName: Registry
 * Package: com.xin.xinrpc.registry
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/29 下午6:06
 * @Version 1.0
 */
public interface Registry {




    // 初始化
    void init(RegistryConfig registryConfig);


    /**
     * @param serviceMetaInfo:
     * @return: void
     * @author: Guoxn
     * @description: 注册服务
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * @param serviceMetaInfo:
     * @return: void
     * @author: Guoxn
     * @description: 注销服务
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * @param serviceKey:
     * @return: java.util.List<com.xin.xinrpc.model.ServiceMetaInfo>
     * @author: Guoxn
     * @description: 服务发现
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey) throws Exception;

    // 服务销毁 （提供者）
    void destroy();

    // 心跳检测机制 （提供者）
    void heatBeat();

    // 监听机制 （消费者）
    void watch(String serviceNodeKey,String serviceKey) throws Exception;

}
