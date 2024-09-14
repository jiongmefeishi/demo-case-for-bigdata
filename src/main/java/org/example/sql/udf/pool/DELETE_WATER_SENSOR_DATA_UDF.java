package org.example.sql.udf.pool;

import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 自定义UDF ：需求：实现复用 jdbc 连接池，根据 sensorId 从MySQL中删除数据记录
 */
public class DELETE_WATER_SENSOR_DATA_UDF extends ScalarFunction {

    private Connection connection;

    @Override
    public void open(FunctionContext context) throws Exception {
        super.open(context);
        // TODO open 方法用于获取jdbc连接
        System.out.println("子任务调用 open()");
        try {
            connection = JdbcConnectionPool.getConnection();
        } catch (Exception e) {
            System.out.println("jdbc 连接获取失败");
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        // TODO 用于关闭jdbc连接
        System.out.println("子任务调用 close()");
        JdbcConnectionPool.close(connection);
    }

    public boolean eval(String sensorId) throws SQLException {
        // TODO 实现 delete 删除逻辑
        System.out.println("子任务调用 eval(), sensorId: " + sensorId);

        String sql = "DELETE FROM tb_water_sensor WHERE sensorId='" + sensorId + "';";
        System.out.println("delete sql : " + sql);
        PreparedStatement statement = connection.prepareStatement(sql);
        int i = statement.executeUpdate();
        return i > 0;
    }
}
