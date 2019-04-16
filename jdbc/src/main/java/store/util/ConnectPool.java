package src.main.java.store.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectPool {
    private static List<Connection> connectionPool = new ArrayList<Connection>();
    private static int connectionLeft;
    static {
        for (int i = 0; i < 2; i++) {
            connectionPool.add(JDBCUtil.getConnection());
        }
        connectionLeft = connectionPool.size();
    }
    public static Connection getConnection(){
        Connection conn = null;
        if(connectionLeft>0) {
            conn = connectionPool.remove(connectionLeft-1);
        }else {
            return JDBCUtil.getConnection();
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
            connectionPool.add(connection);
            connectionLeft++;
            connection = null;
        }
    }
}
