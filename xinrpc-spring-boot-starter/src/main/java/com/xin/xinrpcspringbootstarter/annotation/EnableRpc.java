package com.xin.xinrpcspringbootstarter.annotation;

import com.xin.xinrpcspringbootstarter.bootstrap.RpcConsumerBootstrap;
import com.xin.xinrpcspringbootstarter.bootstrap.RpcInitBootstrap;
import com.xin.xinrpcspringbootstarter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: EnableRpc
 * Package: com.xin.xinrpcspringbootstarter.annotation
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午8:29
 * @Version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {

    // 是否启动server
    boolean needServer() default true;
}
