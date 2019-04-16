package demo;

import com.alibaba.druid.mock.MockConnection;
import com.alibaba.druid.mock.MockDriver;
import com.alibaba.druid.mock.MockStatement;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.mchange.v2.c3p0.cfg.C3P0Config;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;

public class M {

    public static void main(String[] args) throws Exception {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        Connection connection = cpds.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM  tt");
        while (resultSet.next()){
            int anInt = resultSet.getInt(1);
            System.out.println(anInt);
        }
    }

    public static void druidDemo() throws Exception {
        Thread.currentThread().interrupt();
       Properties properties = new Properties();
        properties.load(new FileInputStream("jdbc/src/main/resource/jdbc.properties"));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM  tt");
        while (resultSet.next()){
            int anInt = resultSet.getInt(1);
            System.out.println(anInt);
        }
    }
}
