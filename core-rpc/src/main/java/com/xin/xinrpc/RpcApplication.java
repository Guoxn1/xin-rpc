package com.xin.xinrpc;

/**
 * ClassName: RpcApplication
 * Package: com.xin.xinrpc
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/27 上午11:12
 * @Version 1.0
 */

import com.xin.xinrpc.constant.RpcConstant;
import com.xin.xinrpc.registry.Registry;
import com.xin.xinrpc.registry.RegistryFactory;
import com.xin.xinrpc.rpc.RegistryConfig;
import com.xin.xinrpc.rpc.RpcConfig;
import com.xin.xinrpc.serialize.Serializer;
import com.xin.xinrpc.utils.ConfigUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @param null:
 * @return: null
 * @author: Guoxn
 * @description: 双锁单检查的单例模式
 */
@Slf4j
public class RpcApplication {

    // 保证只加入一个配置
    private static volatile RpcConfig rpcConfig;

    // 注册中心
    @Getter
    private static Registry registry;

    // 支持自定义配置
    public static void init(RpcConfig newrpcConfig) {
        RpcApplication.rpcConfig = newrpcConfig;
        System.out.println(rpcConfig.toString());

        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init , config = {}", registryConfig);

        // 注册shutdown hook 程序退出会执行注册中心的destroy方法
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    // 支持默认配置  支持手动init
    public static void init(){
        RpcConfig newrpcConfig;
        try {
            newrpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {

            newrpcConfig = new RpcConfig();
        }
        init(newrpcConfig);
    }
    // 支持手动getconfig
    public static RpcConfig getRpcConfig() {
        if(rpcConfig==null){
            synchronized (RpcApplication.class) {
                if(rpcConfig==null){
                    init();
                }
            }
        }
        return rpcConfig;
    }

}
