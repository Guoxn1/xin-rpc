package com.xin.provider;

import com.xin.common.service.IUserService;
import com.xin.provider.serviceImpl.UserServiceImpl;
import com.xin.xinrpc.registry.LocalRegistry;
import com.xin.xinrpc.server.VertxHttpServer;

/**
 * ClassName: EasyProviderExample
 * Package: com.xin.provider
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 上午10:31
 * @Version 1.0
 */
public class EasyProviderExample {

    public static void main(String[] args) {
        // 注册到本地注册器中
        LocalRegistry.register(IUserService.class.getName(), UserServiceImpl.class);
        // 提供服务
        VertxHttpServer vertxHttpServer = new VertxHttpServer();
        vertxHttpServer.doStart(1212);
    }
}
