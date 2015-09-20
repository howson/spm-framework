package com.sprouts.spm_framework.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sprouts.spm_framework.utils.ConfigUtils;
import com.sprouts.spm_framework.utils.Logger;

public class SQLService {

    public Connection connection;
    public Statement statement;
    public PreparedStatement preparedStatement;
    public Logger logger = new Logger();

    /**
     * 关闭数据库
     */
    public void close() {
        try {
            statement.close();
            preparedStatement.close();
            connection.isClosed();
        } catch (SQLException e) {
            logger.error("close sql service failed:\n", e);
        }
    }

    /**
     * 连接数据库
     */
    public void connectDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection =
                    DriverManager.getConnection(ConfigUtils.getString("Database.host"),
                            ConfigUtils.getString("Database.username"),
                            ConfigUtils.getString("Database.password"));
            statement = connection.createStatement();

        } catch (ClassNotFoundException e) {
            logger.error("", e);

        } catch (SQLException e) {
            logger.error("", e);
        }
    }

    public void connectDatabase(String host, String user, String pwd) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(host, user, pwd);
            statement = connection.createStatement();

        } catch (ClassNotFoundException e) {
            logger.error("", e);

        } catch (SQLException e) {
            logger.error("", e);
        }
    }

    /**
     * 根据一个sql语句获取某个表的数据列表
     * 
     * @param objects
     * @return
     */
    public ResultSet attachList(String sql) {
        reInitService();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean executeSql(String sql) {
        reInitService();
        boolean success = false;
        try {
            success = statement.execute(sql);
        } catch (SQLException e) {
            logger.error("", e);
        }
        return success;
    }

    public void reInitService() {
        try {
            if (connection == null || connection.isClosed() || statement == null
                    || statement.isClosed() || preparedStatement == null
                    || preparedStatement.isClosed()) {
                connectDatabase();
            }
        } catch (SQLException e) {
            logger.error("reinit Service failed.", e);
        }
    }


    public long getTimestampSec() {
        return System.currentTimeMillis() / 1000;
    }

    public String getTimeStamp() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formater.format(new Date());
    }

}
