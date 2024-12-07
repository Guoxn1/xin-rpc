package com.xin.xinrpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xin.xinrpc.model.RpcRequest;
import com.xin.xinrpc.model.RpcResponse;
import com.xin.xinrpc.serialize.JdkSerializer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * ClassName: ServiceProxy
 * Package: com.xin.xinrpc.proxy
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 下午8:38
 * @Version 1.0
 */
public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        JdkSerializer jdkSerializer = new JdkSerializer();

        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .Args(args)
                .build();

        // 序列化 传递
        byte[] serialize = jdkSerializer.serialize(rpcRequest);
        HttpResponse httpResponse = HttpRequest.post("http://127.0.0.1:1212")
                .body(serialize)
                .execute();

        byte[] result = httpResponse.bodyBytes();

        // 反序列化回去
        RpcResponse rpcResponse = jdkSerializer.deserialize(result, RpcResponse.class);
        return rpcResponse.getData();

    }
}
