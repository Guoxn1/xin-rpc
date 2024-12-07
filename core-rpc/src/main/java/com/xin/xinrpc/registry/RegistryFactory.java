package com.xin.xinrpc.registry;

import com.xin.xinrpc.serialize.Serializer;
import com.xin.xinrpc.spi.SpiLoader;

/**
 * ClassName: RegistryFactory
 * Package: com.xin.xinrpc.registry
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/29 下午7:28
 * @Version 1.0
 */
public class RegistryFactory {

    // 默认的注册中心
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    public static Registry getInstance(String key){
        return SpiLoader.getInstance(Registry.class, key);
    }

}
