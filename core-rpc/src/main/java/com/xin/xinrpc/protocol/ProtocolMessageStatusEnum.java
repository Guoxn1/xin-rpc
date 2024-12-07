package com.xin.xinrpc.protocol;

import lombok.Getter;

/**
 * ClassName: ProtocolMessageStatusEnum
 * Package: com.xin.xinrpc.protocol
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/30 下午9:09
 * @Version 1.0
 */
@Getter
public enum ProtocolMessageStatusEnum {
    OK("ok",20),
    BAD_REQUEST("badRequest",40),
    BAD_RESPONSE("badResponse",50);

    private final String text;

    private final int value;

    ProtocolMessageStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    // 根据value获取枚举
    public static ProtocolMessageStatusEnum getEnumByValue(int value) {
        for (ProtocolMessageStatusEnum anEnum : ProtocolMessageStatusEnum.values()) {
            if(anEnum.value==value){
                return anEnum;
            }
        }
        return null;
    }


}
