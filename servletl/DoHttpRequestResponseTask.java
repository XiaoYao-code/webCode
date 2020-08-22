package tomcat_implements;

import servlet_standard.HttpServlet;
import servlet_standard.HttpServletRequest;
import servlet_standard.HttpServletResponse;
import servlet_standard.ServletException;

import java.io.IOException;
import java.net.Socket;

public class DoHttpRequestResponseTask implements Runnable {
    private final Socket socket;

    public DoHttpRequestResponseTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // 责任列表：
        // 1. 从 socket 的输入端，按照 HTTP 协议请求的格式，读取完整的数据，并封装成一个 HttpServletRequest 对象
        // 2. 准备就好一个 空的 HttpServletResponse 对象
        // 3. 根据 URL，找到合适的 Servlet 对象
        // 4. 调用 Servlet 对象的 service(req, resp) 方法，并传入 请求对象 和 响应对象
        // 5. 把最终的响应对象，按照 HTTP 协议响应的格式，把数据进行组装，并发送到 socket 的输出端
        // 6. 为了简单，我们实现成 HTTP/1.0，由服务端主动关闭 socket（TCP 的主动关闭方会产生很多 TIME_WAIT 状态，所以服务端来主动关不好）

        // 我们直接去找到 Servlet 对象，Servlet 对象的创建和销毁工作不在我们这里负责。
        try {
            // 用到工厂模式
            HttpServletRequest req = HttpServletRequestBuilder.build(socket.getInputStream());
            System.out.println(req);
            /*
            HttpServletResponse resp = HttpServletResponseBuilder.build(socket.getOutputStream());

            // 如何确定使用哪个 Servlet 对象处理请求的逻辑：
            // 1. 优先在 web.xml 找动态资源处理的 Servlet 对象
            // 2. 如果没找到，看是不是一个静态资源（就看 URL 对应的静态资源文件是否存在在指定的位置）
            // 3. 如果也不是静态资源，则使用 404 处理
            HttpServlet httpServlet = ServletManager.getInstance().searchFromWebXML(req.getRequestURI());
            if (httpServlet == null) {
                StaticResourceHttpServlet staticResourceHttpServlet = ServletManager.getInstance().getStaticResourceServlet();
                if (staticResourceHttpServlet.exists(req.getRequestURI())) {
                    // 是静态资源，按静态资源处理
                    httpServlet = staticResourceHttpServlet;
                } else {
                    // 不是静态资源，就按 404 处理
                    httpServlet = ServletManager.getInstance().getNotFoundServlet();
                }
            }

            // 调用 service 方法：生命周期，service 会被调用很多次
            httpServlet.service(req, resp);

            // resp 中的内容已经业务端填充完毕，所以使用 send 方法，发送到 socket 的输出端中
            HttpServletResponseImpl respImpl = (HttpServletResponseImpl)resp;
            respImpl.send();
            */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
