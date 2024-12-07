package com.xin.xinrpc.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

import java.nio.charset.StandardCharsets;

/**
 * ClassName: ConfigUtils
 * Package: com.xin.xinrpc.utils
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/27 上午10:58
 * @Version 1.0
 */
public class ConfigUtils {

    public static <T> T loadConfig(Class<T> tClass,String prefix){
        return loadConfig(tClass,prefix,"");
    }


    public static <T> T loadConfig(Class<T> tClass,String prefix,String environment){
        StringBuilder configStringBuilder = new StringBuilder("application");
        if(StrUtil.isNotBlank(environment)){
            configStringBuilder.append("-").append(environment);
        }
        configStringBuilder.append(".properties");
        Props props = new Props(configStringBuilder.toString());
        return props.toBean(tClass,prefix);
    }
}
