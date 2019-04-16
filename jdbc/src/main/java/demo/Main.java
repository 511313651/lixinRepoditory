package src.main.java.demo;

import src.main.java.store.util.ConnectPool;
import src.main.java.store.util.JDBCUtil;

import java.sql.*;
import java.util.Enumeration;

public class Main {

    public static void main(String[] args) throws SQLException {

        Connection connection = JDBCUtil.getConnection();
        Statement statement = connection.createStatement();
        //开启事务
        connection.setAutoCommit(false);
        try {
            statement.executeUpdate("UPDATE tt SET money = money+100 WHERE id = 1");
            int i = 1 / 0;
            statement.executeUpdate("UPDATE tt SET money = money-100 WHERE id = 2");
            connection.commit();
            System.out.println("转账成功!");
        } catch (SQLException e) {
            System.out.println("转账失败!");
            connection.rollback();
        }
    }

    public static void method1() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectPool.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM tt");
            while (resultSet.next()) {
                System.out.println(resultSet.getRow());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.release(resultSet, statement, connection);
        }
    }

    public static void method2() throws Exception {
        Statement statement = JDBCUtil.getConnection().createStatement();
        //String sql = "SELECT * FROM tt;";
        String sql = "UPDATE tt SET money = money+1";
        boolean execute = statement.execute(sql);
        if (execute) {
            //如果是查询
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } else {
            //如果是增删改
            int updateCount = statement.getUpdateCount();
            int resultSetType = statement.getResultSetType();
            System.out.println(updateCount);
        }
    }
}
