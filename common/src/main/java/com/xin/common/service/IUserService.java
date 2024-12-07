package com.xin.common.service;

import com.xin.common.model.User;

/**
 * ClassName: IUserService
 * Package: com.xin.common.service
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 上午10:21
 * @Version 1.0
 */
public interface IUserService {

    User getUser(User user);

    default int getNumber(){
        return 1;
    }
}
