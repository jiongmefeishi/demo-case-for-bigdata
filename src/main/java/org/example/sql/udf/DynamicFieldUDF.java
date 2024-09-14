package org.example.sql.udf;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.types.Row;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Map;

public class DynamicFieldUDF extends RichMapFunction<Map<String, Object>, Map<String, Object>> {

    private Connection connection;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        // 初始化数据库连接
        connection = DriverManager.getConnection("jdbc:mysql://your-db-host:3306/your-db-name", "username", "password");
    }

    @Override
    public void close() throws Exception {
        super.close();
        if (connection != null) {
            connection.close();
        }
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 返回原始数据
        return value;
    }
}
