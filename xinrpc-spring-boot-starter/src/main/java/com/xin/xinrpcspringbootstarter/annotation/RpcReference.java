package com.xin.xinrpcspringbootstarter.annotation;

import com.xin.xinrpc.balancer.LoadBalancerKeys;
import com.xin.xinrpc.constant.RpcConstant;
import com.xin.xinrpc.fault.retry.RetryKeys;
import com.xin.xinrpc.fault.tolerant.TolerantStrategyKeys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: RpcReference
 * Package: com.xin.xinrpcspringbootstarter.annotation
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午8:29
 * @Version 1.0
 */

// 服务消费者注解
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RpcReference {

    // 服务接口类
    Class<?> interfaceClass() default void.class;

    // 版本
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;

    // 负载均衡器
    String loadBalancer() default LoadBalancerKeys.ROUND_ROBIN;

    // 重试策略
    String retryStrategy() default RetryKeys.NO;

    // 容错策略
    String toleranceStrategy() default TolerantStrategyKeys.FAIL_BACK;

    // 模拟调用
    boolean mock() default false;
}
