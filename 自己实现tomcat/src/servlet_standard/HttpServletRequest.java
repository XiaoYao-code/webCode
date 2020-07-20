package servlet_standard;

public interface HttpServletRequest extends ServletRequest {
    String getMethod();
    String getProtocol();
    String getParameter(String target);
    String getRequestURI();
}
