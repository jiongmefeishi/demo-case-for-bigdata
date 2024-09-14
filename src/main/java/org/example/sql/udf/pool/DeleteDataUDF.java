package org.example.sql.udf.pool;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class DeleteDataUDF extends RichMapFunction<Map<String, Object>, Map<String, Object>> {

    private Connection connection;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = JdbcConnectionPool.getConnection();
    }

    @Override
    public void close() throws Exception {
        super.close();
        JdbcConnectionPool.close(connection);
    }

    @Override
    public Map<String, Object> map(Map<String, Object> value) throws Exception {
        // 动态构建 WHERE 子句
        StringBuilder conditions = new StringBuilder();
        int index = 1;
        for (Map.Entry<String, Object> entry : value.entrySet()) {
            if (index > 1) {
                conditions.append(" AND ");
            }
            conditions.append(entry.getKey()).append(" = ?");
            index++;
        }

        String sql = "DELETE FROM your_table WHERE " + conditions.toString();

        // 构造并执行 SQL 语句
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            for (Object param : value.values()) {
                if (param instanceof Integer) {
                    statement.setInt(paramIndex, (Integer) param);
                } else if (param instanceof String) {
                    statement.setString(paramIndex, (String) param);
                } else if (param instanceof Double) {
                    statement.setDouble(paramIndex, (Double) param);
                } else {
                    throw new IllegalArgumentException("Unsupported type for value: " + param.getClass());
                }
                paramIndex++;
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 返回原始数据
        return value;
    }
}