package com.xin.springbootprovider;

import com.xin.common.model.User;
import com.xin.common.service.IUserService;
import com.xin.xinrpcspringbootstarter.annotation.RpcService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserServiceImpl
 * Package: com.xin.springbootprovider
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/2 下午9:47
 * @Version 1.0
 */
@RpcService
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public User getUser(User user) {
        User user1 = new User();
        user1.setName(user.getName() + "provider spring");
        return user1;
    }
}
