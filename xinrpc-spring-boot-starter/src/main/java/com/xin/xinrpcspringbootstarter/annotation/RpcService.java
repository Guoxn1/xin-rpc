package com.xin.xinrpcspringbootstarter.annotation;

import com.xin.xinrpc.constant.RpcConstant;
import org.springframework.stereotype.Component;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: RpcService
 * Package: com.xin.xinrpcspringbootstarter.annotation
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午8:29
 * @Version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {

    // 服务接口类
    Class<?> interfaceClass() default void.class;

    // 版本
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;
}
