package com.xin.xinrpc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: ServiceRegisterInfo
 * Package: com.xin.xinrpc.model
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午5:16
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRegisterInfo<T> {

    // 服务名称
    private String serviceName;

    // 实现类
    private Class<? extends  T> implClass;
}
