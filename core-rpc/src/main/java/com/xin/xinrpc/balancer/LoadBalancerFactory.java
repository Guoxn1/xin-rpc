package com.xin.xinrpc.balancer;

import com.xin.xinrpc.spi.SpiLoader;

/**
 * ClassName: LoadBalancerFactory
 * Package: com.xin.xinrpc.balancer
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/1 下午8:34
 * @Version 1.0
 */
public class LoadBalancerFactory {

    static {
        SpiLoader.load(LoadBalancer.class);
    }

    // 默认的负载均衡器
    private static final LoadBalancer DEFAULT_LOADBALANCER = new RandomLoadBalancer();

    // 获取实例
    public static LoadBalancer getInstance(String key) {
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }
}
