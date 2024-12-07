package com.xin.xinrpc.protocol;

import lombok.Getter;

/**
 * ClassName: ProtocolMessageTypeEnum
 * Package: com.xin.xinrpc.protocol
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/30 下午9:14
 * @Version 1.0
 */
@Getter
public enum ProtocolMessageTypeEnum {
    REQUEST(0),
    RESPONSE(1),
    HEART_BEAT(2),
    OTHERS(3);

    private final int key;

    private ProtocolMessageTypeEnum(int key) {
        this.key = key;
    }

    public static ProtocolMessageTypeEnum getEnumByKey(int key) {
        for (ProtocolMessageTypeEnum e : ProtocolMessageTypeEnum.values()) {
            if (e.key == key) {
                return e;
            }
        }
        return null;
    }
}
