package tomcat_implements;

import servlet_standard.HttpServlet;
import webapp.WebXML;

import java.util.HashMap;
import java.util.Map;

public class ServletManager {
    // 同时，为了使得 ServletManager 管理对象正确，这个类也是一个单例类
    private static final ServletManager instance = new ServletManager();
    public static ServletManager getInstance() {
        return instance;
    }

    private ServletManager() {}

    private Map<String, HttpServlet> allServletInstances = new HashMap<>();
    // 专门处理静态资源的 Servlet 对象
    private StaticResourceHttpServlet staticResourceHttpServlet;
    // 专门返回 404 的 Servlet 对象
    private NotFoundServlet notFoundServlet;

    // 这个方法，在Tomcat的运行期间，只会被调用一次
    // 真实中的 Tomcat，Servlet 对象使用延迟加载技术，用到某个Serlvet对象时，才把它实例化处理
    public void init() {
        // 职责列表：
        // 1. 找到所有的 Servlet 类
        // 2. 把所有的 Servlet 类实例化成 Servlet 对象 —— 单例（每个Servlet类，只需要一个对象即可）
        // 3. 负责调用这些对象的 init() 方法
        // 目前只支持一个 webapp，所以只有一个 WebXML 类
        for (Map.Entry<String, String> entry : WebXML.servlet.entrySet()) {
            String servletName = entry.getKey();
            String servletClass = entry.getValue();

            // 使用类名称实例化对象
            // com.peixinchen.HelloServlet -> webapp.com.peixinchen.HelloServlet
            // 因为这里有约定，所以，如果开发 Servlet 应用的把类放错位置了，就会出现找不到类的情况
            // 类似我们必须把类放到 classes 目录下
            String trueServletClass = "webapp." + servletClass;

            // 如何根据 String 类型的类名称，实例化对象呢？ —— 使用反射
            try {
                Class<HttpServlet> cls = (Class<HttpServlet>) Class.forName(trueServletClass);
                HttpServlet httpServlet = cls.newInstance();

                // Servlet 对象生命周期，每个 init() 只会在初始化阶段被调用这么一次
                httpServlet.init();

                allServletInstances.put(servletName, httpServlet);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }

            staticResourceHttpServlet = new StaticResourceHttpServlet();
            staticResourceHttpServlet.init();

            notFoundServlet = new NotFoundServlet();
            notFoundServlet.init();
        }
    }

    // 这个方法，在Tomcat的运行期间，只会被调用一次
    public void destroy() {
        // 职责列表:
        // 1. 调用所有 Servlet 对象的 destroy() 方法
        // 2. 负责销毁对象

        notFoundServlet.destroy();
        staticResourceHttpServlet.destroy();

        for (HttpServlet httpServlet : allServletInstances.values()) {
            // Servlet 对象生命周期，每个 destroy() 只会在销毁阶段被调用这么一次
            httpServlet.destroy();
        }
    }

    public HttpServlet searchFromWebXML(String requestURI) {
        return null;
    }

    public StaticResourceHttpServlet getStaticResourceServlet() {
        return staticResourceHttpServlet;
    }

    public NotFoundServlet getNotFoundServlet() {
        return notFoundServlet;
    }
}
