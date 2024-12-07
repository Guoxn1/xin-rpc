package com.xin.xinrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xin.xinrpc.RpcApplication;
import com.xin.xinrpc.balancer.LoadBalancer;
import com.xin.xinrpc.balancer.LoadBalancerFactory;
import com.xin.xinrpc.constant.ProtocolConstant;
import com.xin.xinrpc.constant.RpcConstant;
import com.xin.xinrpc.fault.retry.RetryStrategy;
import com.xin.xinrpc.fault.retry.RetryStrategyFactory;
import com.xin.xinrpc.fault.tolerant.TolerantStrategy;
import com.xin.xinrpc.fault.tolerant.TolerantStrategyFactory;
import com.xin.xinrpc.model.RpcRequest;
import com.xin.xinrpc.model.RpcResponse;
import com.xin.xinrpc.model.ServiceMetaInfo;
import com.xin.xinrpc.protocol.*;
import com.xin.xinrpc.registry.Registry;
import com.xin.xinrpc.rpc.RpcConfig;
import com.xin.xinrpc.serialize.JdkSerializer;
import com.xin.xinrpc.serialize.Serializer;
import com.xin.xinrpc.serialize.SerializerFactory;
import com.xin.xinrpc.server.tcp.VertxTcpClient;
import com.xin.xinrpc.spi.SpiLoader;
import io.netty.util.concurrent.CompleteFuture;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import org.checkerframework.checker.units.qual.C;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .Args(args)
                .build();

        // 从注册中心获取服务者的地址
        Registry registry = RpcApplication.getRegistry();
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(method.getDeclaringClass().getName());
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            throw new RuntimeException("没有服务地址 fuck");
        }
        // 获取实例
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(RpcApplication.getRpcConfig().getLoadBalancer());
        // 将调用的方法名字作为负载均衡参数
        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put("method", rpcRequest.getMethodName());
        ServiceMetaInfo selectServiceMetaInfo = loadBalancer.select(requestParams,serviceMetaInfoList);
        // 发送tcp请求 并使用重试机制
        RpcResponse rpcResponse =null;
        // 使用 try 完成容错机制
        try {
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(RpcApplication.getRpcConfig().getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(()->VertxTcpClient.doRequest(rpcRequest, selectServiceMetaInfo));
        }catch (Exception e){
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(RpcApplication.getRpcConfig().getTolerantStrategy());
            tolerantStrategy.doTolerant(null,e);
        }

        return rpcResponse.getData();

    }
}
