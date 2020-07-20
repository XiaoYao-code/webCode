package tomcat_implements;

import com.sun.corba.se.impl.activation.ServerMain;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        // 主要的知识点是 如何在 Java 中，通过 Socket 编程，使用 TCP 协议
        // 顺便思考，每句代码背后，TCP 做了什么。

        ExecutorService threadPool = Executors.newFixedThreadPool(8);
        ServletManager.getInstance().init();

        // 我们是 Server，站在 TCP 的角度，我们是被动打开方
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            // serverSocket 对应的 TCP 内部有个连接——连接1，状态是 LISTEN

            while (true) {
                Socket socket = serverSocket.accept();
                // socket 对应的 TCP 内部有一个新的连接——连接2，状态是 ESTABLISHED（三次握手已经完成）
                // 连接2 从 连接1 中生了出来

                // 作为 HTTP 的服务端，应该被动地等客户端发请求过来
                // 直接这么写，会有问题
                // 这段代码中，有两个点会造成阻塞：accept() / read()
                // 导致，这段代码，无法支持并发的处理客户端的请求
                // 这里的解决办法有：1. 使用 NIO 的非阻塞模式         2. 使用多线程处理
            /*
            InputStream is = socket.getInputStream();
            is.read();
            */

                // 主线程只负责 accept，所有 HTTP 请求处理的事务，全部交给线程池中的线程去处理

                // 虽然 socket 理论上是共享的，但因为主线程一定不会再用 socket 了，所以没有线程安全问题
                Runnable doHttpRequestResponseTask = new DoHttpRequestResponseTask(socket);
                threadPool.execute(doHttpRequestResponseTask);
            }
        }

        // 上面是死循环，不会运行到这里，真实中的 Tomcat 一定可以处理到这个位置
        // ServletManager.getInstance().destroy();
    }
}
