package com.xin.xinrpc.rpc;

import com.xin.xinrpc.balancer.LoadBalancerKeys;
import com.xin.xinrpc.fault.retry.RetryKeys;
import com.xin.xinrpc.fault.tolerant.TolerantStrategyKeys;
import com.xin.xinrpc.serialize.SerializerKeys;
import lombok.Data;

/**
 * ClassName: RpcConfig
 * Package: com.xin.xinrpc.rpc
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/27 上午10:23
 * @Version 1.0
 */
@Data
public class RpcConfig {

    // name
    private String name ="xin-rpc";

    // 版本号
    private String version = "1.0";

    // 服务器主机名
    private String host = "127.0.0.1";

    // 服务器端口号
    private int port = 1212;

    // 是否开启mock
    private boolean mock = false;

    // 序列化器的配置
    private String serializer = SerializerKeys.JDK;


    // 注册中心的配置
    private RegistryConfig registryConfig = new RegistryConfig();

    // 负载均衡器配置
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    // 重试策略
    private String retryStrategy = RetryKeys.NO;

    // 容错策略
    private String tolerantStrategy = TolerantStrategyKeys.FAIL_SAFE;
}
