package com.xin.xinrpc.fault.tolerant;

/**
 * ClassName: TolerantStrategyKeys
 * Package: com.xin.xinrpc.fault.tolerant
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午4:20
 * @Version 1.0
 */
public interface TolerantStrategyKeys {

    // 快速失败
    String FAIL_BACK = "failBack";

    // 不做处理
    String FAIL_SAFE = "failSafe";
}
