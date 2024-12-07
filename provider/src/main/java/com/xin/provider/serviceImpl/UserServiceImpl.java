package com.xin.provider.serviceImpl;

import com.xin.common.model.User;
import com.xin.common.service.IUserService;

/**
 * ClassName: UserServiceImpl
 * Package: com.xin.provider.serviceImpl
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 上午10:27
 * @Version 1.0
 */
public class UserServiceImpl implements IUserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名是：" + user.getName());
        user.setName(user.getName()+ " 后缀");
        return user;
    }
}
