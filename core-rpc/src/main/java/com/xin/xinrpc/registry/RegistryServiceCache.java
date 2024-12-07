package com.xin.xinrpc.registry;

import com.xin.xinrpc.model.ServiceMetaInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: RegistryServiceCache
 * Package: com.xin.xinrpc.registry
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/30 下午5:30
 * @Version 1.0
 */
@Data
public class RegistryServiceCache {

    // 服务缓存
    Map<String, List<ServiceMetaInfo>> serviceCache = new ConcurrentHashMap<>();

    // 写缓存
    void writeCache(String serviceKey, List<ServiceMetaInfo> newServiceMetaInfos) {
        serviceCache.put(serviceKey, newServiceMetaInfos);
    }

    // 读缓存
    List<ServiceMetaInfo> readServiceMetaInfos(String serviceKey) {
        return serviceCache.get(serviceKey);
    }

    // 清空缓存
    void clearCache(String serviceKey) {
        serviceCache.remove(serviceKey);
    }
}
