package com.peixinchen.model;

import java.util.Objects;

/**
 * 一般来说，model 这类对象，都需要覆写
 * 1. toString
 * 2. equals
 * 3. hashCode
 */
public class User {
    public int uid;
    public String username;

    public User() {
    }

    public User(int uid, String username) {
        this.uid = uid;
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;     // 两个引用是不是指向同一个对象
        if (!(o instanceof User)) return false; // 两个对象的类型是否一致
        User user = (User) o;                   // 判断重要属性是否一样
        return uid == user.uid &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uid, username);
    }
}
