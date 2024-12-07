package com.xin.xinrpc.fault.retry;

import com.xin.xinrpc.spi.SpiLoader;

/**
 * ClassName: RetryStrategyFactory
 * Package: com.xin.xinrpc.fault.retry
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 上午10:17
 * @Version 1.0
 */
public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    // 默认重试器
    private final RetryStrategy retryStrategy = new NoRetryStrategy();

    public static RetryStrategy getInstance(String key) {
        return SpiLoader.getInstance(RetryStrategy.class,key);
    }
}
