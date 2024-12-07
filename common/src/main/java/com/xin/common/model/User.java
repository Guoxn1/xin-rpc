package com.xin.common.model;

import java.io.Serializable;

/**
 * ClassName: user
 * Package: com.xin.common.model
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 上午10:20
 * @Version 1.0
 */
public class User implements Serializable  {

    private String name;
    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }
}
