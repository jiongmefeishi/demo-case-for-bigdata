package org.example.sql.udf.pool;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.example.bean.WaterSensor;
import org.example.bean.WaterSensorData;

public class TestDeleteWaterSensorDataUDF {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 加载数据
        DataStreamSource<WaterSensor> sensorDS = env.fromCollection(WaterSensorData.getDefaultWaterSensorData());
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // table转换
        Table sensorTable = tableEnv.fromDataStream(sensorDS);
        tableEnv.createTemporaryView("sensorSource", sensorTable);

        // 注册 UDF
        tableEnv.createTemporarySystemFunction("DELETE_WATER_SENSOR_DATA_UDF", DELETE_WATER_SENSOR_DATA_UDF.class);

        // 如果入参是 Map<String, Object> 类型，传参：MAP['sensorId', sensorId, 'sensorName', sensorName]
//        tableEnv.sqlQuery("select deleteData(MAP['sensorId', sensorId, 'sensorName', sensorName]) from sensor")
//                .execute()  // 调用了 sql的execute，就不需要 env.execute()
//                .print();

        tableEnv.sqlQuery("select DELETE_WATER_SENSOR_DATA_UDF(sensorId) as test from sensorSource")
                .execute()  // 调用了 sql的execute，就不需要 env.execute()
                .print();
    }
}
