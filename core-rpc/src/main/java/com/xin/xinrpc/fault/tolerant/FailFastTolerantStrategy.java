package com.xin.xinrpc.fault.tolerant;

import com.xin.xinrpc.model.RpcResponse;

import java.util.Map;

/**
 * ClassName: FailFastTolerantStrategy
 * Package: com.xin.xinrpc.fault.tolerant
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午2:10
 * @Version 1.0
 */
public class FailFastTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("FailFastTolerantStrategy doTolerant");
    }
}
