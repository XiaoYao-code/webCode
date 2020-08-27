package com.peixinchen.dao;

import com.peixinchen.model.User;
import com.peixinchen.util.DB;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * Java 代码表示的 INSERT/UPDATE/DELETE/SELECT
 *
 * 关于用户的密码，不要保存明文密码（peixinchen/123）
 * 如果数据从你这里泄露了，则所有用户的密码也跟着全部泄露了
 * 一般都是保存做完 hash 的密码（这里选择 sha-256 这个hash算法）
 */
public class UserDao {
    public User insert(String username, String plainPassword) throws SQLException {
        String password = encrypt(plainPassword);

        String sql = "insert into users (username, password) values (?, ?)";

        try (Connection c = DB.getConnection()) {
            try (PreparedStatement s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                s.setString(1, username);
                s.setString(2, password);

                s.executeUpdate();

                try (ResultSet r = s.getGeneratedKeys()) {
                    if (!r.next()) {
                        return null;
                    }

                    return new User(r.getInt(1), username);
                }
            }
        }
    }

    public User select(String username, String plainPassword) throws SQLException {
        String password = encrypt(plainPassword);

        String sql = "select uid from users where username = ? and password = ?";

        try (Connection c = DB.getConnection()) {
            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setString(1, username);
                s.setString(2, password);

                try (ResultSet r = s.executeQuery()) {
                    if (!r.next()) {
                        return null;
                    }

                    return new User(r.getInt(1), username);
                }
            }
        }
    }

    // 这个做法实际上也不适合生产环境真正使用
    // 但至少比明文的情况要安全一点
    private String encrypt(String plain) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = plain.getBytes();
            byte[] digest = messageDigest.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String a = "123";
        UserDao userDao = new UserDao();
        String encrypt = userDao.encrypt(a);
        System.out.println(encrypt);
    }
}
