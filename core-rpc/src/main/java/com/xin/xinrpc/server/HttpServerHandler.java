package com.xin.xinrpc.server;

import com.xin.xinrpc.RpcApplication;
import com.xin.xinrpc.model.RpcRequest;
import com.xin.xinrpc.model.RpcResponse;
import com.xin.xinrpc.registry.LocalRegistry;
import com.xin.xinrpc.serialize.JdkSerializer;
import com.xin.xinrpc.serialize.Serializer;
import com.xin.xinrpc.serialize.SerializerFactory;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

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
public class HttpServerHandler implements Handler<HttpServerRequest> {
    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 记录日志
        System.out.println("Received request: " + httpServerRequest.method()+" "+httpServerRequest.uri());

        // 异步处理http请求
        httpServerRequest.bodyHandler(body ->{
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;

            try{
                rpcRequest = serializer.deserialize(bytes,RpcRequest.class);
            }catch (Exception e){
                e.printStackTrace();
            }

            // 构造响应结果
            RpcResponse rpcResponse = new RpcResponse();
            if(rpcRequest == null){
                rpcResponse.setMessage("Rpc request is null, what fuck you doing");
                doResponse(httpServerRequest,rpcResponse,serializer);
                return;
            }

            Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
            try {
                Method method  = implClass.getMethod(rpcRequest.getMethodName(),rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());

                // 封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setMessage("ok");
                rpcResponse.setDataType(method.getReturnType());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            doResponse(httpServerRequest,rpcResponse,serializer);

        });
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
