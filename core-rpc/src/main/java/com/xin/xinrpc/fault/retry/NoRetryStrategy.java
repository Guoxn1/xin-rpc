package com.xin.xinrpc.fault.retry;

import com.xin.xinrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * ClassName: NoRetryStrategy
 * Package: com.xin.xinrpc.fault.retry
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 上午10:01
 * @Version 1.0
 */
public class NoRetryStrategy implements RetryStrategy{
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
