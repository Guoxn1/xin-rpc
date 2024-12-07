package com.xin.xinrpc.fault.tolerant;

import com.xin.xinrpc.model.RpcResponse;

import java.util.Map;
import java.util.Objects;

/**
 * ClassName: TolerantStrategy
 * Package: com.xin.xinrpc.fault.tolerant
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 上午10:54
 * @Version 1.0
 */
public interface TolerantStrategy {


    /**
     * @param context: 上下文 用于传递数据
     * @param e:  异常
     * @return: com.xin.xinrpc.model.RpcResponse
     * @author: Guoxn
     * @description:
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
