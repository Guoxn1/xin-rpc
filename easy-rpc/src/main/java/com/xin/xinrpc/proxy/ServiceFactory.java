package com.xin.xinrpc.proxy;

import java.lang.reflect.Proxy;

/**
 * ClassName: ServiceFactory
 * Package: com.xin.xinrpc.proxy
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 下午8:44
 * @Version 1.0
 */
// 工厂对象  用来创建代理对象
public class ServiceFactory {


    public static <T> T getProxy(Class<T> Service) {
        return (T) Proxy.newProxyInstance(
                Service.getClassLoader(),
                new Class[]{Service},
                new ServiceProxy());
    }
}
