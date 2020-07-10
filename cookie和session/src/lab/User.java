package lab;

import java.util.ArrayList;
import java.util.List;

public class User {
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // 提交准备一些系统中的用户
    private static final List<User> userList = new ArrayList<>();
    static {
        userList.add(new User("peixinchen", "123"));
        userList.add(new User("gaobo", "456"));
    }

    public static User login(String username, String password) {
        // 本质上就是一个查找
        for (User user : userList) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }

        return null;
    }
}
