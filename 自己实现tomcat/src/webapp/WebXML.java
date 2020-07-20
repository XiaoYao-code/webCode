package webapp;

import java.util.HashMap;
import java.util.Map;

public class WebXML {
    // key: servlet-name
    // value: servlet-class
    public static Map<String, String> servlet = new HashMap<>();

    // key: url-pattern
    // value: servlet-name
    public static Map<String, String> servletMapping = new HashMap<>();

    static {
        /*
        <servlet>
            <servlet-name>Hello</servlet-name>
            <servlet-class>com.peixinchen.HelloServlet</servlet-class>
        </servlet>
        <servlet-mapping>
            <servlet-name>Hello</servlet-name>
            <url-pattern>/hello</url-pattern>
        </servlet-mapping>
         */
        servlet.put("Hello", "com.peixinchen.HelloServlet");
        servletMapping.put("/hello", "Hello");
    }
}
