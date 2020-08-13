package servlet_standard;

import java.io.IOException;

public abstract class HttpServlet implements Servlet {
    @Override
    public void init() {
    }

    @Override
    public void destroy() {
    }

    // 模板设计模式
    @Override
    public void service(ServletRequest req, ServletResponse resp) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;

        String method = httpReq.getMethod();
        if (method.equals("GET")) {
            doGet(httpReq, httpResp);
        } else if (method.equals("POST")) {
            doPost(httpReq, httpResp);
        }
    }

    private void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setStatus(405);
        resp.setContentType("text/plain; charset=utf-8");
        resp.getWriter().println("不支持的 HTTP 方法");
    }

    /*
    1. 为什么要返回 405。 HTTP 协议决定，如果一种方法不支持，就应该返回 405
    2. 怎么做到返回 405 的。（多态的知识）
       因为 doGet 的默认实现就是返回 405，所以，只要子类没有覆写 doGet 方法。
       当有 GET 请求过来时，就会默认执行父类中的 doGet 方法
       使得可以返回 405

       因为子类没有覆写 doGet，意味着子类的 Servlet 不准备支持 GET 方法
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setStatus(405);
        resp.setContentType("text/plain; charset=utf-8");
        resp.getWriter().println("不支持的 HTTP 方法");
    }
}
