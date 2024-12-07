package com.xin.xinrpc.server;

import io.vertx.core.Vertx;

/**
 * ClassName: VertxHttpServer
 * Package: com.xin.xinrpc.server
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 上午10:53
 * @Version 1.0
 */
public class VertxHttpServer implements HttpServer{
    @Override
    public void doStart(int port) {
        // 创建 Vertx 实例
        Vertx vertx = Vertx.vertx();

        // 创建http服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 监听端口并处理请求
        server.requestHandler(new HttpServerHandler());

        server.listen(port,result->{
            if(result.succeeded()){
                System.out.println("Server is now listening on port" + port);
            }else{
                System.out.println("Failed to start server "+ result.cause());
            }
        });



    }
}
