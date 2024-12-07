package com.xin.xinrpc.constant;

/**
 * ClassName: ProtocolConstant
 * Package: com.xin.xinrpc.constant
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/30 下午9:06
 * @Version 1.0
 */
public interface ProtocolConstant {
    // 消息头长度
    int MESSAGE_HEADER_SIZE = 17;

    // 协议的魔术
    byte PROTOCOL_MAGIC = 0x1;

    // 协议的版本号
    byte PROTOCOL_VERSION = 0x1;
}
