package com.xin.xinrpc.rpc;

import lombok.Data;

/**
 * ClassName: RegistryConfig
 * Package: com.xin.xinrpc.registry
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/29 下午4:11
 * @Version 1.0
 */
@Data
public class RegistryConfig {

    // 注册中心的类别
    private String registry = "etcd";

    // 注册中心的地址
    private String address="htttp://192.168.133.138:2380";

    // 用户名
    private String name;

    // 密码
    private String password;

    // 超时时间
    private Long timeOut = 10000L;

}
