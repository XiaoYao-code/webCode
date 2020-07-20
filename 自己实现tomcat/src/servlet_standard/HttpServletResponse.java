package servlet_standard;

import java.io.IOException;
import java.io.PrintWriter;

public interface HttpServletResponse extends ServletResponse {
    void setStatus(int status);

    void setContentType(String contentType);

    PrintWriter getWriter() throws IOException;
}
