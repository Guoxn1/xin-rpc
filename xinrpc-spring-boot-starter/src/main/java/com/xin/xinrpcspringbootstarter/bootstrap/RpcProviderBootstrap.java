package com.xin.xinrpcspringbootstarter.bootstrap;

import com.xin.xinrpc.RpcApplication;
import com.xin.xinrpc.model.ServiceMetaInfo;
import com.xin.xinrpc.registry.LocalRegistry;
import com.xin.xinrpc.registry.Registry;
import com.xin.xinrpc.registry.RegistryFactory;
import com.xin.xinrpc.rpc.RegistryConfig;
import com.xin.xinrpc.rpc.RpcConfig;
import com.xin.xinrpcspringbootstarter.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * ClassName: RpcProviderBootstrap
 * Package: com.xin.xinrpcspringbootstarter.bootstrap
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午8:44
 * @Version 1.0
 */
public class RpcProviderBootstrap implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        RpcService rpcService = beanClass.getAnnotation(RpcService.class);

        if(rpcService != null) {
            // 需要注册服务
            // 1 获取服务的基本信息
            Class<?> interfaceClass = rpcService.interfaceClass();

            // 默认值处理
            if (interfaceClass==void.class) {
                interfaceClass = beanClass.getInterfaces()[0];
            }

            String serviceName = interfaceClass.getName();
            String serviceVersion = rpcService.serviceVersion();
            //2 注册服务
            // 本地注册
            LocalRegistry.register(serviceName,beanClass);

            final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            registry.heatBeat();
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(serviceVersion);
            serviceMetaInfo.setServiceHost(rpcConfig.getHost());
            serviceMetaInfo.setServicePort(rpcConfig.getPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }


        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}
