package com.ruolan.living.sqlutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlManager {

    private static String driverUrl = "jdbc:mysql://localhost:3306/CN_LIVE?"
            + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";

    private static final String dbPro = "jdbc:mysql://";
    private static final String host = "192.168.1.89";// 远程ip地址
    private static final String port = "30351";// 远程端口号
    private static final String dbName = "dd8bca5e";// 远程数据库名字
    private static final String charset = "?useUnicode=true&charactsetEncoding=utf-8";// �ַ���

    private static final String url = dbPro + host + ":" + port + "/" + dbName
            + charset;

    private static final String user = "7f79db3a";
    private static final String password = "8477deb8";


    /**
     * 获取远程的数据库连接
     *
     * @return Connection
     * @throws SQLException SQLException
     */
    public static Connection getRemoteConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 获取到本地的数据库连接
     *
     * @return
     * @throws SQLException           SQLException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static Connection getLocalConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
        return DriverManager.getConnection(driverUrl);
    }

}
