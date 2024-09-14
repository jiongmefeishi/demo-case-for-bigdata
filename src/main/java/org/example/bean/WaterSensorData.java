package org.example.bean;

import java.util.Arrays;
import java.util.List;

/**
 * 模拟水位传感器数据
 */
public class WaterSensorData {
    public static List<WaterSensor> getDefaultWaterSensorData() {

        return Arrays.asList(
                new WaterSensor("sensor001", "水位传感器001", 2, System.currentTimeMillis() - 2000, 41, 2.44, 1, System.currentTimeMillis() - 1000, "河床1号水位传感信息采集点"),
                new WaterSensor("sensor002", "水位传感器002", 4, System.currentTimeMillis() - 3000, 55,12.11, 3, System.currentTimeMillis() - 2000, "河床2号水位传感信息采集点"),
                new WaterSensor("sensor003", "水位传感器003", 2, System.currentTimeMillis() - 4000, 4,23.64, 3, System.currentTimeMillis() - 3000, "河床3号水位传感信息采集点"),
                new WaterSensor("sensor004", "水位传感器004", 1, System.currentTimeMillis() - 5000, 11,2.88, 4, System.currentTimeMillis() - 4000, "河床4号水位传感信息采集点"),
                new WaterSensor("sensor005", "水位传感器005", 3, System.currentTimeMillis() - 6000, 4,27.22, 5, System.currentTimeMillis() - 6000, "河床5号水位传感信息采集点"),
                new WaterSensor("sensor001", "水位传感器001", 2, System.currentTimeMillis() - 7000, 33,33.33, 1, System.currentTimeMillis() - 1000, "河床1号水位传感信息采集点"),
                new WaterSensor("sensor001", "水位传感器001", 2, System.currentTimeMillis() - 8000, 4,6.09, 1, System.currentTimeMillis() - 1000, "河床1号水位传感信息采集点"),
                new WaterSensor("sensor001", "水位传感器001", 2, System.currentTimeMillis() - 11000, 45,6.09, 1, System.currentTimeMillis() - 1000, "河床1号水位传感信息采集点"),
                new WaterSensor("sensor002", "水位传感器002", 4, System.currentTimeMillis() - 12000, 48,12.31, 3, System.currentTimeMillis() - 2000, "河床2号水位传感信息采集点"),
                new WaterSensor("sensor006", "水位传感器006", 3, System.currentTimeMillis() - 23000, 84,12.31, 6, System.currentTimeMillis() - 2000, "河床6号水位传感信息采集点"),
                new WaterSensor("sensor006", "水位传感器006", 3, System.currentTimeMillis() - 2000, 88,11.33, 6, System.currentTimeMillis() - 2000, "河床6号水位传感信息采集点"),
                new WaterSensor("sensor006", "水位传感器006", 3, System.currentTimeMillis() - 22000, 22,1.41, 6, System.currentTimeMillis() - 2000, "河床6号水位传感信息采集点"));
    }
}
