package com.xin.xinrpc.registry;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: LocalRegistry
 * Package: com.xin.xinrpc.registry
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 下午4:33
 * @Version 1.0
 */
public class LocalRegistry {

    private static final Map<String,Class<?>> map = new ConcurrentHashMap<>();


    // 注册服务
    public static void register(String className, Class<?> implClass){
        map.put(className, implClass);
    }

    // 获取服务
    public static Class<?> get(String className){
        return map.get(className);
    }

    // 删除服务
    public static void remove(String className){
        map.remove(className);
    }
}
