package com.xin.xinrpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ClassName: ProtocolMessage
 * Package: com.xin.xinrpc.protocol
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/30 下午9:01
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolMessage<T> {

    // 消息头
    private Header header;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    public static class Header{
        // 魔术 保证安全性
        private byte magic;

        // 版本号
        private byte version;

        // 序列化器
        private byte serializer;

        // 消息类型 (请求 响应 心跳)
        private byte type;

        // 状态
        private byte status;

        // 请求id
        private long requestId;

        // 消息体长度
        private int bodyLength;
    }

    // 消息体
    private T body;


}
