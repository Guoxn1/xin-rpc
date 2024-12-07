package com.xin.xinrpc.protocol;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: ProtocolMessageSerializerEnum
 * Package: com.xin.xinrpc.protocol
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/30 下午9:16
 * @Version 1.0
 */
@Getter
public enum ProtocolMessageSerializerEnum {
    JDK(0,"jdk"),
    JSON(1,"json"),
    KRYO(2,"kryo"),
    HESSIAN(3,"hessian");

    private final int key;
    private final String value;

    private ProtocolMessageSerializerEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    // 根据key来获取枚举
    public static ProtocolMessageSerializerEnum getByKey(int key) {
        for (ProtocolMessageSerializerEnum anEnum : ProtocolMessageSerializerEnum.values()) {
            if(anEnum.key == key) {
                return anEnum;
            }
        }
        return null;
    }

    // 根据value获取枚举
    public static ProtocolMessageSerializerEnum getByValue(String value) {
        if (StrUtil.isBlank(value)) {
            return null;
        }
        for (ProtocolMessageSerializerEnum anEnum : ProtocolMessageSerializerEnum.values()) {
            if(anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
