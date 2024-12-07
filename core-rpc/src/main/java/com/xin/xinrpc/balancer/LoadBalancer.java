package com.xin.xinrpc.balancer;

import com.xin.xinrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * ClassName: LoadBalancer
 * Package: com.xin.xinrpc.balancer
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/1 下午6:11
 * @Version 1.0
 */
public interface LoadBalancer {
    /**
     * @param requestParams: 请求参数
     * @param servicesList:  可用的服务列表
     * @return: com.xin.xinrpc.model.ServiceMetaInfo
     * @author: Guoxn
     * @description:
     */
    ServiceMetaInfo select(Map<String,Object> requestParams, List<ServiceMetaInfo> servicesList);
}
