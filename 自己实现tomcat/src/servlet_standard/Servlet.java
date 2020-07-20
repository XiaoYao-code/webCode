package servlet_standard;

import java.io.IOException;

public interface Servlet {
    void init();

    void service(ServletRequest req, ServletResponse resp) throws IOException, ServletException;

    void destroy();
}
