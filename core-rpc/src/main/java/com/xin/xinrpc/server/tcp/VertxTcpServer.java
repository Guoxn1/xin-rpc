package com.xin.xinrpc.server.tcp;

import com.xin.xinrpc.server.HttpServer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.parsetools.RecordParser;

import java.util.Arrays;

/**
 * ClassName: VertxTcpServer
 * Package: com.xin.xinrpc.server.tcp
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/30 下午9:38
 * @Version 1.0
 */
public class VertxTcpServer implements HttpServer {
    @Override
    public void doStart(int port) {

        // 创建tcp服务器
        Vertx vertx = Vertx.vertx();
        NetServer netServer = vertx.createNetServer();

        // 处理请求
        netServer.connectHandler(new TcpServerHandler());

        // 启动tcp服务并监听指定端口
        netServer.listen(port,result->{
            if(result.succeeded()){
                System.out.println("Tcp server started on port "+port);
            }else {
                System.out.println("Tcp server failed to start on port "+port);
            }
        });
    }

    private byte[] handleRequest(byte[] requestData) {
        System.out.println("Received request "+ Arrays.toString(requestData));
        return "hello client".getBytes();

    }

    public static void main(String[] args) {
        new VertxTcpServer().doStart(8888);
    }
}
