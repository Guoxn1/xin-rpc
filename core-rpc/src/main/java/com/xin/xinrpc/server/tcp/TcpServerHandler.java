package com.xin.xinrpc.server.tcp;

import cn.hutool.core.util.IdUtil;
import com.xin.xinrpc.RpcApplication;
import com.xin.xinrpc.constant.ProtocolConstant;
import com.xin.xinrpc.model.RpcRequest;
import com.xin.xinrpc.model.RpcResponse;
import com.xin.xinrpc.protocol.*;
import com.xin.xinrpc.registry.LocalRegistry;
import com.xin.xinrpc.serialize.Serializer;
import com.xin.xinrpc.serialize.SerializerFactory;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.net.NetSocket;

import java.lang.reflect.Method;

/**
 * ClassName: HttpServerHandler
 * Package: com.xin.xinrpc.server
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 下午5:20
 * @Version 1.0
 */
public class TcpServerHandler implements Handler<NetSocket> {
    @Override
    public void handle(NetSocket netSocket) {

        TcpBufferHandlerWrapper tcpBufferHandlerWrapper = new TcpBufferHandlerWrapper(buffer -> {
            ProtocolMessage<RpcRequest> protocolMessage = null;

            try {
                protocolMessage = (ProtocolMessage<RpcRequest>) ProtocolMessageDecoder.decode(buffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            RpcRequest rpcRequest = protocolMessage.getBody();
            // 构造响应结果
            RpcResponse rpcResponse = new RpcResponse();


            try {
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());

                // 封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setMessage("ok");
                rpcResponse.setDataType(method.getReturnType());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // 发送响应
            ProtocolMessage.Header header = protocolMessage.getHeader();
            header.setType((byte) ProtocolMessageTypeEnum.RESPONSE.getKey());
            ProtocolMessage<RpcResponse> rpcResponseProtocolMessage = new ProtocolMessage<>(header, rpcResponse);

            try {
                Buffer encode = ProtocolMessageEncoder.encode(rpcResponseProtocolMessage);
                netSocket.write(encode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });

        // 异步处理http请求
        netSocket.handler(tcpBufferHandlerWrapper);
    }

    private void doResponse(HttpServerRequest httpServerRequest, RpcResponse rpcResponse, Serializer serializer){
        HttpServerResponse httpServerResponse = httpServerRequest.response().putHeader("content-type", "application/json");

        try {
            byte[] serialize = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialize));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
