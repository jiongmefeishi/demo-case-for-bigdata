package org.example.sql.udf.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 定义连接池
 */
public class JdbcConnectionPool {

    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://xxxxx:3366/dbxxxx");
        config.setUsername("xxxx");
        config.setPassword("xxxx");
        config.setMaximumPoolSize(10); // 设置最大连接数
//        config.setIdleTimeout(30000); // 设置空闲连接超时时间
        config.setIdleTimeout(3000); // 设置空闲连接超时时间
        config.setConnectionTimeout(3000); // 设置连接超时时间
//        config.setConnectionTimeout(30000); // 设置连接超时时间

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        // 获取 HikariPoolMXBean
        HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();

        // 获取剩余连接数
        int remainingConnections = poolMXBean.getIdleConnections(); // 当前空闲连接数
        int maxPoolSize = dataSource.getMaximumPoolSize(); // 最大连接数
        int activeConnections = poolMXBean.getActiveConnections(); // 当前活跃连接数

        // 计算剩余可用连接数
        int availableConnections = maxPoolSize - activeConnections;

        System.out.println("=======  getConnection 获取jdbc 连接前检查 start ========");
        System.out.println("最大连接数: " + maxPoolSize);
        System.out.println("当前空闲连接数: " + remainingConnections);
        System.out.println("当前活跃连接数: " + activeConnections);
        System.out.println("剩余可用连接数: " + availableConnections);
        System.out.println("=======  getConnection 获取jdbc 连接前检查 end ========");

        return dataSource.getConnection();
    }

    public static void close(@Nullable Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}