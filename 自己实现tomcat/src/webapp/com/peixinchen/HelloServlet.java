package webapp.com.peixinchen;

import servlet_standard.HttpServlet;
import servlet_standard.HttpServletRequest;
import servlet_standard.HttpServletResponse;
import servlet_standard.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String target = req.getParameter("target");
        if (target == null) {
            target = "world";
        }

        resp.setContentType("text/plain; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.println("hello " + target);
    }
}
