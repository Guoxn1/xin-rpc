package com.xin.xinrpc.balancer;

/**
 * ClassName: LoadBalancerKeys
 * Package: com.xin.xinrpc.balancer
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/1 下午8:33
 * @Version 1.0
 */
public interface LoadBalancerKeys {

    String ROUND_ROBIN = "roundRobin";
    String RANDOM = "random";
    String CONSISTENT_HASH = "consistentHash";
}
