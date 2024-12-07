package com.xin.xinrpc.balancer;

import com.xin.xinrpc.model.ServiceMetaInfo;

import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * ClassName: ConsistentHashLoadBalancer
 * Package: com.xin.xinrpc.balancer
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/1 下午8:05
 * @Version 1.0
 */
public class ConsistentHashLoadBalancer implements LoadBalancer {

    // 使用treemap实现一致性哈希环
    private final TreeMap<Integer,ServiceMetaInfo> virtualNodes = new TreeMap<>();

    // 虚拟节点个数
    private static final int VIRTUAL_NODE_SIZE = 1280;


    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> servicesList) {
        if(servicesList==null || servicesList.size()==0){
            return null;
        }

        // 构建虚拟节点
        for (ServiceMetaInfo serviceMetaInfo : servicesList) {
            for(int i=0; i<VIRTUAL_NODE_SIZE; i++){
                int hash = getHash(serviceMetaInfo.getServiceAddress()+"#"+i + "guoxin rpc ");
                virtualNodes.put(hash, serviceMetaInfo);
            }
        }

        int hash = getHash(requestParams);

        // 选择最接近且大于等于请求hash的虚拟节点
        Map.Entry<Integer, ServiceMetaInfo> entry = virtualNodes.ceilingEntry(hash);

        if(entry==null){
            // 如果没有 就返回首节点
            entry = virtualNodes.firstEntry();
        }
        return entry.getValue();
    }

    private int getHash(Object key) {
        return key.hashCode();
    }


}
