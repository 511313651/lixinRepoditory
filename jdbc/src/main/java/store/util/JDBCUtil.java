package src.main.java.store.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {
    static String DRIVER ;
    static String URL ;
    static String USERNAME ;
    static String PASSWORD ;

    static{
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("./jdbc\\src\\main\\resource\\jdbc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DRIVER = properties.getProperty("DRIVER");
        URL = properties.getProperty("URL");
        USERNAME = properties.getProperty("USERNAME");
        PASSWORD = properties.getProperty("PASSWORD");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void release(ResultSet resultSet, Statement statement,Connection connection){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
            }
            resultSet = null;
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
            }
            statement = null;
        }
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
            }
            connection = null;
        }
    }
}
