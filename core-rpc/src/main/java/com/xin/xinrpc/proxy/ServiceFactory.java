package com.xin.xinrpc.proxy;

import com.xin.xinrpc.RpcApplication;

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

        if(RpcApplication.getRpcConfig().isMock()){
            return getMockProxy(Service);
        }

        return (T) Proxy.newProxyInstance(
                Service.getClassLoader(),
                new Class[]{Service},
                new ServiceProxy());
    }

    private static <T> T getMockProxy(Class<T> service) {
        return (T) Proxy.newProxyInstance(
                service.getClassLoader(),
                new Class[]{service},
                new MockServiceProxy());
    }
}
