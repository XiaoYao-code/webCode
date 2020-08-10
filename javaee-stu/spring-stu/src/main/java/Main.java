import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Main {
    public static void main(String[] args) {
        /**
         * spring 开启容器的方式：ApplicationContext 应用上下文（可以配置并管理Bean对象，以及其他的工作）
         *ClassPathXmlApplicationContext根据classpath路径，指定一个xml文件。
         *
         */
        ApplicationContext context = new ClassPathXmlApplicationContext("applications.xml");
        //通过bean的名称获取bean对象，bean的名称就是xml的bean id。
        String bit = (String) context.getBean("bit");
        System.out.println(bit);
        //通过类型获取bean对象，如果该类型有多个对象，就会报错，只支持一个该类的对象。
       // context.getBean(String.class);

    }
}
