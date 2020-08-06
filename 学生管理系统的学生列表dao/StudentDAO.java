package yaoxinyuan.dao;

import com.sun.xml.internal.stream.events.NamedEvent;
import yaoxinyuan.model.Classes;
import yaoxinyuan.model.DictionaryTag;
import yaoxinyuan.model.Student;
import yaoxinyuan.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDAO {
    public static List<Student> query() {
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<Student> list=new ArrayList<>();
        try {
            //1.获取数据库连接
            c= DBUtil.getConnection();//工具类以及该封装好的方法
            //sql语句预编译
            String sql="select s.id," +
                    "       s.student_name," +
                    "       s.student_no," +
                    "       s.id_card," +
                    "       s.student_email," +
                    "       s.classes_id," +
                    "       s.create_time," +
                    "       c.id cid," +
                    "       c.classes_name," +
                    "       c.classes_graduate_year," +
                    "       c.classes_major," +
                    "       c.classes_desc" +
                    "  from student s" +
                    "         join classes c on s.classes_id = c.id";
            //在实际应用中，如果查询一张表的所有字段，sql语句也不要写成*，两张表如果有一样的字段，比如上面的id   一定要用别名区别
            //2.创建操作命令对象
            ps=c.prepareStatement(sql);

            //3.执行查询操作
            rs=ps.executeQuery();
            //4.处理查询结果集
            while (rs.next()){
              Student student=new Student();
              //设置属性，通过结果集获取来设置
                student.setId(rs.getInt("id"));
                student.setStudentName(rs.getString("student_name"));
                student.setStudentNo(rs.getString("student_no"));
                student.setIdCard(rs.getString("id_card"));
                student.setStudentEmail(rs.getString("student_email"));
                student.setClassesId(rs.getInt("classes_id"));
                student.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                Classes  classes = new Classes();
                student.setClasses(classes);
                classes.setId(rs.getInt("cid"));
                classes.setClassesName(rs.getString("classes_name"));
                classes.setClassesGraduateYear(rs.getString("classes_graduate_year"));
                classes.setClassesMajor(rs.getString("classes_major"));
                classes.setClassesDesc(rs.getString("classes_desc"));


                // classes.setCreateTime(new Date(rs.getTimestamp("cct").getTime()));
                list.add(student);
            }
            return list;
        } catch (Exception e) {
            throw  new RuntimeException("查询学生列表出错",e);//如果不把这个具体的异常信息传入参数，那么异常信息就会丢失，出现问题时不方便定位
        } finally {
            //5.释放资源
            DBUtil.close(c,ps,rs);//封装得释放资源的方法
        }
    }
}
