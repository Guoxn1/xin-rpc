package com.xin.provider;

import com.xin.common.service.IUserService;
import com.xin.provider.serviceImpl.UserServiceImpl;
import com.xin.xinrpc.RpcApplication;
import com.xin.xinrpc.bootstrap.ProviderBootstrap;
import com.xin.xinrpc.model.ServiceMetaInfo;
import com.xin.xinrpc.model.ServiceRegisterInfo;
import com.xin.xinrpc.registry.LocalRegistry;
import com.xin.xinrpc.registry.Registry;
import com.xin.xinrpc.registry.RegistryFactory;
import com.xin.xinrpc.rpc.RegistryConfig;
import com.xin.xinrpc.rpc.RpcConfig;
import com.xin.xinrpc.server.VertxHttpServer;
import com.xin.xinrpc.server.tcp.VertxTcpServer;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ProviderExample
 * Package: com.xin.provider
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/27 下午3:26
 * @Version 1.0
 */
public class ProviderExample {
    public static void main(String[] args) {
        // 要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfos = new ArrayList<ServiceRegisterInfo<?>>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(IUserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfos.add(serviceRegisterInfo);

        // 初始化
        ProviderBootstrap.init(serviceRegisterInfos);
    }
}
