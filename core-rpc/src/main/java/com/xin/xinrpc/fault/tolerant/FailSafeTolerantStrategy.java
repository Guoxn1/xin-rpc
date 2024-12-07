package com.xin.xinrpc.fault.tolerant;

import com.xin.xinrpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * ClassName: FailSafeTolerantStrategy
 * Package: com.xin.xinrpc.fault.tolerant
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午4:18
 * @Version 1.0
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("不处理异常 你能怎么样");
        return new RpcResponse();
    }
}
