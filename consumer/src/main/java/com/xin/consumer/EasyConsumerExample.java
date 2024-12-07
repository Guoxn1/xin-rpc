package com.xin.consumer;

import com.xin.common.model.User;
import com.xin.common.service.IUserService;
import com.xin.xinrpc.proxy.ServiceFactory;
import com.xin.xinrpc.rpc.RpcConfig;
import com.xin.xinrpc.utils.ConfigUtils;

/**
 * ClassName: EasyConsumerExample
 * Package: com.xin.consumer
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 上午10:46
 * @Version 1.0
 */
public class EasyConsumerExample {
    public static void main(String[] args) {

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
