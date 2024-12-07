package com.xin.xinrpcspringbootstarter.bootstrap;

import com.xin.xinrpc.proxy.ServiceFactory;
import com.xin.xinrpcspringbootstarter.annotation.RpcReference;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * ClassName: RpcConsumerBootstrap
 * Package: com.xin.xinrpcspringbootstarter.bootstrap
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午8:44
 * @Version 1.0
 */
public class RpcConsumerBootstrap implements BeanPostProcessor {

    // Bean 初始化后执行
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();

        // 遍历对象的所有属性
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                // 为属性生成代理对象
                Class<?> interfaceClass = rpcReference.interfaceClass();
                if (interfaceClass == void.class) {
                    interfaceClass = field.getType();
                }
                field.setAccessible(true);

                Object proxyObject = ServiceFactory.getProxy(interfaceClass);
                try {
                    field.set(bean,proxyObject);
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}
