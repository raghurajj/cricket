package com.tekion.cricket.dbconnector;

import com.tekion.cricket.PrivateData;
import com.tekion.cricket.constants.StringUtils;
import com.tekion.cricket.models.*;

import java.sql.*;
import java.util.List;

public class MySqlConnector {
    private static Connection connection = null;
    public static void initializeConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(StringUtils.MYSQL_URL,StringUtils.MYSQL_USER,PrivateData.mysqlPassword);

    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(connection == null || connection.isClosed()) {
            initializeConnection();
        }
        return connection;
    }

}
