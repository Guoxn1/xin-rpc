package com.xin.xinrpc.fault.tolerant;

import com.xin.xinrpc.spi.SpiLoader;

/**
 * ClassName: TolerantStrategyFactory
 * Package: com.xin.xinrpc.fault.tolerant
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午4:21
 * @Version 1.0
 */
public class TolerantStrategyFactory {
    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    private final TolerantStrategy DEFAULT_TOLERANT_STRATEGY = new FailSafeTolerantStrategy();

    public static TolerantStrategy getInstance(String key){
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
