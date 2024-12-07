package com.xin.xinrpc.balancer;

import com.xin.xinrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * ClassName: RandomLoadBalancer
 * Package: com.xin.xinrpc.balancer
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/1 下午8:03
 * @Version 1.0
 */
public class RandomLoadBalancer implements LoadBalancer {

    private final Random random = new Random();

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> servicesList) {
        int size = servicesList.size();
        if (size==1) {
            return servicesList.get(0);
        }else if(size==0){
            return null;
        }

        return servicesList.get(random.nextInt(size));
    }
}
