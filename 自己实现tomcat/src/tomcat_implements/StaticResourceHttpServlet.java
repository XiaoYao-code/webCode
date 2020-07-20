package tomcat_implements;

import servlet_standard.HttpServlet;

public class StaticResourceHttpServlet extends HttpServlet {
    public boolean exists(String requestURI) {
        return false;
    }
}
