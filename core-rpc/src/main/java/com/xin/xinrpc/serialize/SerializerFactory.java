package com.xin.xinrpc.serialize;

import com.xin.xinrpc.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;

/**
 * ClassName: SerializerFactory
 * Package: com.xin.xinrpc.serialize
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/28 下午7:35
 * @Version 1.0
 */
public class SerializerFactory {

    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    public static Serializer getInstance(String key){
        return SpiLoader.getInstance(Serializer.class, key);
    }

}
