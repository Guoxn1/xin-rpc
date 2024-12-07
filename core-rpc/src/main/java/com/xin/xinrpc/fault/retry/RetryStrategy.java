package com.xin.xinrpc.fault.retry;

import com.xin.xinrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * ClassName: RetryStrategy
 * Package: com.xin.xinrpc.fault.retry
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 上午9:58
 * @Version 1.0
 */
public interface RetryStrategy {

    // 重试策略

    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
