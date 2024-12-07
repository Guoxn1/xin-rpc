package com.xin.xinrpc.server;

/**
 * ClassName: HttpServer
 * Package: com.xin.xinrpc.server
 * Description:
 *
 * @Author guoxin
 * @Create 2024/11/26 上午10:51
 * @Version 1.0
 */
public interface HttpServer {

    /*
     * @param port:
     * @return: void
     * @author: Guoxn
     * @description:  http统一的服务启动点
     */
    void doStart(int port);
}
