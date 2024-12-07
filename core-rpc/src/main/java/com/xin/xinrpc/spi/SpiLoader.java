package com.xin.xinrpc.spi;

import cn.hutool.core.io.resource.ResourceUtil;
import com.xin.xinrpc.serialize.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: SpiLoader
 * Package: com.xin.xinrpc.spi
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/28 下午8:02
 * @Version 1.0
 */
@Slf4j
public class SpiLoader {

    // 存储已经加载的类
    private static Map<String, Map<String,Class<?>>> loaderMap;

    // 对象实例缓存
    private static Map<String,Object> instanceCache;

    // 用户自定义spi目录
    private static final String RPC_CUSTOM_SPI_DIR = "META-INF/rpc/custom/";

    // 系统spi目录
    private static final String RPC_SYSTEM_SPI_DIR = "META-INF/rpc/system/";

    // 扫描路径 就是上面的两个路径
    private static final String[] SCAN_DIRS = new String[] {RPC_SYSTEM_SPI_DIR, RPC_CUSTOM_SPI_DIR};

    // 动态加载的类列表
    private static final List<Class<?>> LOAD_CLASS_LIST = Arrays.asList(Serializer.class);

    // 加载所有的类型
    public static void loadAll(){
        System.out.println("加载所有的 spi");
        for (Class<?> aClass : LOAD_CLASS_LIST) {
            load(aClass);
        }
    }


    // 加载某个类型
    public static Map<String,Class<?>> load(Class<?> loadClass){
        log.info("加载的类型为 {} 的 SPI", loadClass.getName());
        // 扫描路径，用户自定义的spi优先级高于系统的spi
        Map<String,Class<?>> keyClassMap = new HashMap<>();
        for (String scanDir : SCAN_DIRS) {
            List<URL> resources = ResourceUtil.getResources(scanDir+loadClass.getName());

            // 读取每个资源文件
            for (URL resource : resources) {
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] split = line.split("=");
                        if (split.length>1) {
                            String key = split[0];
                            String className = split[1];
                            keyClassMap.put(key,Class.forName(className));
                        }
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }
        loaderMap.put(loadClass.getName(),keyClassMap);
        return keyClassMap;
    }


    // 获取某个接口的实例
    public static <T> T getInstance(Class<?> tClass,String key){
        // 实现懒加载spi
        initMap();
        // 实现懒加载loaderMap
        if (SpiLoader.loaderMap.isEmpty() || !loaderMap.containsKey(tClass.getName())) {
            synchronized (SpiLoader.class) {
                if (SpiLoader.loaderMap.isEmpty() || !loaderMap.containsKey(tClass.getName())) {
                    load((tClass));
                }
            }
        }

        String tClassName = tClass.getName();
        Map<String, Class<?>> keyClassMap = loaderMap.get(tClassName);
        if (keyClassMap==null) {
            throw new RuntimeException(String.format("SpiLoader 未加载 %s 类型",tClassName));
        }
        if(!keyClassMap.containsKey(key)){
            throw new RuntimeException(String.format("SpiLoader 的 %s 不存在 key = %s的类型",tClassName,key));
        }

        // 获取到要加载的实现类
        Class<?> implClass = keyClassMap.get(key);
        String implClassName = implClass.getName();
        if(!instanceCache.containsKey(implClassName)){
            try {
                instanceCache.put(implClassName,implClass.newInstance());
            } catch (Exception e) {

                throw new RuntimeException(e);
            }
        }
        return (T)instanceCache.get(implClassName);
    }

    private static void initMap() {
        if (loaderMap==null) {
            synchronized (SpiLoader.class){
                if (loaderMap==null) {
                    loaderMap = new ConcurrentHashMap<>();
                }
            }
        }

        if (instanceCache==null) {
            synchronized (SpiLoader.class){
                if (instanceCache==null) {
                    instanceCache = new ConcurrentHashMap<>();
                }
            }
        }
    }


}
