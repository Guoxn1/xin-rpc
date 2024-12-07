package com.xin.xinrpc.bootstrap;

import com.xin.xinrpc.RpcApplication;
import com.xin.xinrpc.model.ServiceMetaInfo;
import com.xin.xinrpc.model.ServiceRegisterInfo;
import com.xin.xinrpc.registry.LocalRegistry;
import com.xin.xinrpc.registry.Registry;
import com.xin.xinrpc.registry.RegistryFactory;
import com.xin.xinrpc.rpc.RegistryConfig;
import com.xin.xinrpc.rpc.RpcConfig;
import com.xin.xinrpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * ClassName: ProviderBootstrap
 * Package: com.xin.xinrpc.bootstrap
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午5:16
 * @Version 1.0
 */
public class ProviderBootstrap {

    // 初始化
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getHost());
            serviceMetaInfo.setServicePort(rpcConfig.getPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        // 启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getPort());
    }

}
