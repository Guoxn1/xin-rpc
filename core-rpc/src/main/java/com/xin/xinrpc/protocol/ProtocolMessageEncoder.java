package com.xin.xinrpc.protocol;

import com.xin.xinrpc.serialize.Serializer;
import com.xin.xinrpc.serialize.SerializerFactory;
import io.vertx.core.buffer.Buffer;

/**
 * ClassName: ProtocolMessageEncoder
 * Package: com.xin.xinrpc.protocol
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/1 上午11:01
 * @Version 1.0
 */
public class ProtocolMessageEncoder {
    // 编码

    public static Buffer encode(ProtocolMessage<?> protocolMessage) throws Exception{
        if(protocolMessage==null || protocolMessage.getHeader()==null){
            return Buffer.buffer();
        }
        ProtocolMessage.Header header = protocolMessage.getHeader();
        // 向缓冲区写入字节
        Buffer buffer = Buffer.buffer();
        buffer.appendByte(header.getMagic());
        buffer.appendByte(header.getVersion());
        buffer.appendByte(header.getSerializer());
        buffer.appendByte(header.getType());
        buffer.appendByte(header.getStatus());
        buffer.appendLong(header.getRequestId());

        // 获取序列化器
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getByKey(header.getSerializer());
        if(serializerEnum==null){
            throw new RuntimeException("没有这种序列化器");
        }

        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());

        byte[] bodyBytes = serializer.serialize(protocolMessage.getBody());

        // 写入body 和 数据
        buffer.appendInt(bodyBytes.length);
        buffer.appendBytes(bodyBytes);
        return buffer;
    }
}
