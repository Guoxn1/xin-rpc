package com.xin.springbootconsumer;

import com.xin.common.model.User;
import com.xin.common.service.IUserService;
import com.xin.xinrpcspringbootstarter.annotation.RpcReference;
import org.springframework.stereotype.Service;

/**
 * ClassName: Main
 * Package: com.xin.springbootconsumer
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午9:49
 * @Version 1.0
 */
@Service
public class Main {
    @RpcReference
    private IUserService userService;

    public void main1() {
        User user = new User();
        user.setName("guoxin1");
        User user1 = userService.getUser(user);
        System.out.println(user1.getName());
    }

}
