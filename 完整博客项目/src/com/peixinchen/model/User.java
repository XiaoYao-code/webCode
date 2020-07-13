package com.peixinchen.model;

import com.peixinchen.util.DBUtil;

import java.sql.*;

public class User {
    public int id;
    public String username;

    public static User register(String username, String password) throws SQLException {
        try (Connection c = DBUtil.getConnection()) {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                s.setString(1, username);
                s.setString(2, password);

                s.executeUpdate();

                try (ResultSet r = s.getGeneratedKeys()) {
                    if (!r.next()) {
                        return null;
                    }

                    User user = new User();
                    user.id = r.getInt(1);
                    user.username = username;
                    return user;
                }
            }
        }
    }

    public static User login(String username, String password) throws SQLException {
        try (Connection c = DBUtil.getConnection()) {
            String sql = "SELECT id FROM users WHERE username = ? AND PASSWORD = ?";
            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setString(1, username);
                s.setString(2, password);

                try (ResultSet r = s.executeQuery()) {
                    if (!r.next()) {
                        return null;
                    }

                    User user = new User();
                    user.id = r.getInt("id");
                    user.username = username;
                    return user;
                }
            }
        }
    }
}
