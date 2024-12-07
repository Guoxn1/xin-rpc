package com.xin.xinrpc.balancer;

import com.xin.xinrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: RoundRobinLoadBalancer
 * Package: com.xin.xinrpc.balancer
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/1 下午7:13
 * @Version 1.0
 */
public class RoundRobinLoadBalancer implements LoadBalancer {

    private final AtomicInteger counter = new AtomicInteger(0);

    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> servicesList) {
        if(servicesList==null || servicesList.size()==0){
            return null;
        }

        // 如果只有一个服务 就不需要轮询
        int size = servicesList.size();
        if (size==1) {
            return servicesList.get(0);
        }

        // 取模轮询算法
        int index = counter.getAndIncrement() % size;
        return servicesList.get(index);
    }
}
