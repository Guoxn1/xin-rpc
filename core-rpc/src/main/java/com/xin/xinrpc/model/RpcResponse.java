package com.xin.xinrpc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: RpcResponse
 * Package: com.xin.xinrpc.model
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 下午5:16
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse implements Serializable {
    // 响应数据
    private Object data;

    // 响应数据类型
    private Class<?> dataType;

    // 响应的额外信息
    private String message;

    // 异常信息
    private Exception exception;

}
