package com.xin.xinrpcspringbootstarter.bootstrap;

import com.xin.xinrpc.RpcApplication;
import com.xin.xinrpc.registry.Registry;
import com.xin.xinrpc.registry.RegistryFactory;
import com.xin.xinrpc.rpc.RpcConfig;
import com.xin.xinrpc.server.tcp.VertxTcpServer;
import com.xin.xinrpcspringbootstarter.annotation.EnableRpc;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ClassName: RpcInitBootstrap
 * Package: com.xin.xinrpcspringbootstarter.bootstrap
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午8:56
 * @Version 1.0
 */
public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {
    // spring 初始化的时候  初始化 rpc框架

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 获取 EnableRpc 的属性值
        boolean needServer = (boolean) importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName()).get("needServer");

        // Rpc init
        RpcApplication.init();
        // 全局配置
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 启动服务器
        if (needServer) {

            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.doStart(rpcConfig.getPort());
        }else{
            System.out.println("没启动服务器");
        }
    }

}
