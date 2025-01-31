package com.xin.xinrpc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: RpcRequest
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
public class RpcRequest implements Serializable {

    // 服务名称
    private String serviceName;

    // 方法名称
    private String methodName;

    // 参数类型列表
    private Class<?> [] parameterTypes;

    // 参数列表
    private Object[] Args;
}
