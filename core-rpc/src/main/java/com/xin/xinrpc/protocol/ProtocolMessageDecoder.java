package com.xin.xinrpc.protocol;

import com.xin.xinrpc.constant.ProtocolConstant;
import com.xin.xinrpc.model.RpcRequest;
import com.xin.xinrpc.model.RpcResponse;
import com.xin.xinrpc.serialize.Serializer;
import com.xin.xinrpc.serialize.SerializerFactory;
import io.vertx.core.buffer.Buffer;

/**
 * ClassName: ProtocolMessageDecoder
 * Package: com.xin.xinrpc.protocol
 * Description:
 *
 * @Author guoxin
 * @Create 2024/12/1 上午11:11
 * @Version 1.0
 */
public class ProtocolMessageDecoder {

    // 解码
    public static ProtocolMessage<?> decode(Buffer buffer)throws Exception {
        // 分别从指定位置读出Buffer
        byte magic = buffer.getByte(0);
        if (magic!= ProtocolConstant.PROTOCOL_MAGIC) {
            throw new RuntimeException("magic not match");
        }
        ProtocolMessage.Header header = new ProtocolMessage.Header();
        header.setMagic(magic);
        header.setVersion(buffer.getByte(1));
        header.setSerializer(buffer.getByte(2));
        header.setType(buffer.getByte(3));
        header.setStatus(buffer.getByte(4));
        header.setRequestId(buffer.getByte(5));
        header.setBodyLength(buffer.getInt(13));

        // 解决粘包问题
        byte[] bodyBytes = buffer.getBytes(17, 17 + header.getBodyLength());

        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getByKey(header.getSerializer());
        if (serializerEnum == null) {
            throw new RuntimeException("unknown serializer");
        }
        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());
        ProtocolMessageTypeEnum typeEnum = ProtocolMessageTypeEnum.getEnumByKey(header.getType());
        if (typeEnum == null) {
            throw new RuntimeException("unknown type");
        }

        switch (typeEnum){
            case REQUEST:
                RpcRequest request = serializer.deserialize(bodyBytes, RpcRequest.class);
                return new ProtocolMessage<>(header,request);
            case RESPONSE:
                RpcResponse rpcResponse = serializer.deserialize(bodyBytes, RpcResponse.class);
                return new ProtocolMessage<>(header,rpcResponse);
            case HEART_BEAT:
            case OTHERS:
            default:
                throw new RuntimeException("unknown type");
        }
    }
}
