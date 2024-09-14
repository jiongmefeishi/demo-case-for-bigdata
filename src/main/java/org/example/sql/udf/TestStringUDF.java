package org.example.sql.udf;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.ApiExpression;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.expressions.Expression;
import org.example.bean.WaterSensor;
import org.example.bean.WaterSensorData;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.call;

/**
 * 测试 SQL 如何注册并调用 UDF 函数
 */
public class TestStringUDF {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<WaterSensor> sensorDS = env.fromCollection(WaterSensorData.getDefaultWaterSensorData());
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        Table sensorTable = tableEnv.fromDataStream(sensorDS);
        tableEnv.createTemporaryView("sensor", sensorTable);

        // TODO 2.注册函数
        tableEnv.createTemporaryFunction("UDF_STRING_APPEND_SUFFIX", UDF_STRING_APPEND_SUFFIX.class);

        // TODO 3.调用 自定义函数
        // 3.1 sql用法
//        tableEnv.sqlQuery("select sensorId, sensorName, UDF_STRING_APPEND_SUFFIX(sensorId, sensorName) as id_and_name, ts, vc, dataCollectionLocation from sensor")
//                .execute()  // 调用了 sql的execute，就不需要 env.execute()
//                .print();

        // 3.2 table api用法
        ApiExpression call = call("UDF_STRING_APPEND_SUFFIX", $("sensorId"), $("sensorName"));
        sensorTable
                .select($("sensorId"), $("sensorName"), call("UDF_STRING_APPEND_SUFFIX", $("sensorId"), $("sensorName")), $("dataCollectionLocation"))
                .execute()
                .print();

    }
}
