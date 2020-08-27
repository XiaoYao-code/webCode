package com.peixinchen.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DB {
    private static volatile DataSource dataSource = null;

    private static DataSource initDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setServerName("127.0.0.1");
        mysqlDataSource.setPort(3306);
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("");
        mysqlDataSource.setDatabaseName("hjb2_tingshu");
        mysqlDataSource.setCharacterEncoding("utf8");
        mysqlDataSource.setUseSSL(false);

        return mysqlDataSource;
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            synchronized (DB.class) {
                if (dataSource == null) {
                    dataSource = initDataSource();
                }
            }
        }

        return dataSource.getConnection();
    }
}
