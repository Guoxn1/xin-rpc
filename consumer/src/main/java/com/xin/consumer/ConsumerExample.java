package com.xin.consumer;

import com.xin.common.model.User;
import com.xin.common.service.IUserService;
import com.xin.xinrpc.bootstrap.ConsumerBootstrap;
import com.xin.xinrpc.proxy.ServiceFactory;
import com.xin.xinrpc.rpc.RpcConfig;
import com.xin.xinrpc.utils.ConfigUtils;

/**
 * ClassName: ConsumerExample
 * Package: com.xin.consumer
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/27 下午3:25
 * @Version 1.0
 */
public class ConsumerExample {
    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootstrap.init();

        IUserService userService = ServiceFactory.getProxy(IUserService.class);
        User user = new User();
        user.setName("xin");
        User newUser = userService.getUser(user);
        if (newUser!=null) {
            System.out.println(newUser.getName());
        }else{
            System.out.println("User == null");
        }
    }

}
