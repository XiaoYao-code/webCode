package com.peixinchen.model;

import com.peixinchen.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Article {
    public int authorId;
    public String authorUsername;
    public int id;
    public String title;
    public String publishedAt;
    public String content;

    /*
    这么用 DateFormat 是错误的，因为是
    1. DateFormat 不是线程安全的
    2. 我们的 publish 实际上是运行在多线程环境下的

    static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void publish(int userId, String title, String content) {
        Date date = new Date();
        String publishedAt = format.format(date); // 2020-07-12 11:42:38
    }
    */

    public static void publish(int userId, String title, String content) throws SQLException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String publishedAt = format.format(date);

        try (Connection c = DBUtil.getConnection()) {
            String sql = "INSERT INTO articles (user_id, title, content, published_at) VALUES (?, ?, ?, ?)";
            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setInt(1, userId);
                s.setString(2, title);
                s.setString(3, content);
                s.setString(4, publishedAt);

                s.executeUpdate();
            }
        }
    }

    public static List<Article> list() throws SQLException {
        List<Article> articleList = new ArrayList<>();

        try (Connection c = DBUtil.getConnection()) {
            String sql = "select\n" +
                    "  users.id author_id,\n" +
                    "  users.username author_username,\n" +
                    "  articles.id,\n" +
                    "  title,\n" +
                    "  published_at\n" +
                    "from articles join users on articles.user_id = users.id\n" +
                    "order by published_at desc";

            try (PreparedStatement s = c.prepareStatement(sql)) {
                try (ResultSet r = s.executeQuery()) {
                    while (r.next()) {
                        Article article = new Article();
                        article.id = r.getInt("id");
                        article.authorId = r.getInt("author_id");
                        article.authorUsername = r.getString("author_username");
                        article.title = r.getString("title");
                        article.publishedAt = r.getString("published_at");

                        articleList.add(article);
                    }
                }
            }

            return articleList;
        }
    }

    public static Article get(int id) throws SQLException {
        try (Connection c = DBUtil.getConnection()) {
            String sql = "select title, content from articles where id = ?";
            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setInt(1, id);
                try (ResultSet r = s.executeQuery()) {
                    if (!r.next()) {
                        return null;
                    }

                    Article article = new Article();
                    article.title = r.getString("title");
                    article.content = r.getString("content");

                    return article;
                }
            }
        }
    }
}
