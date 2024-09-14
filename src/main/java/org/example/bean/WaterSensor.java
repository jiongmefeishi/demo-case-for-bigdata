package org.example.bean;

import java.util.Objects;

/**
 * 水位传感器
 *
 * 这里需要注意，我们定义的WaterSensor，有这样几个特点：
 *
 * 类是公有（public）的
 *
 * 有一个==无参的构造方法==
 *
 * 所有属性都是公有（public）的
 *
 * 所有属性的类型都是可以序列化的
 */

public class WaterSensor {

    /**
     * 传感器ID
     */
    private String sensorId;

    /**
     * 传感器名称
     */
    private String sensorName;

    /**
     * 传感器型号
     */
    private Integer sensorModel;

    /**
     * 传感器记录时间戳
     */
    public Long ts;

    /**
     * 水位记录
     */
    public Integer vc;

    /**
     * 传感器当前数据
     */
    private Double sensorData;

    /**
     * 数据采集时间（当前小时）
     */
    private Integer dataCollectionHour;

    /**
     * 数据采集时间
     */
    private Long dataCollectionTime;

    /**
     * 数据采集地点
     */
    private String dataCollectionLocation;

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Integer getSensorModel() {
        return sensorModel;
    }

    public void setSensorModel(Integer sensorModel) {
        this.sensorModel = sensorModel;
    }

    public Double getSensorData() {
        return sensorData;
    }

    public void setSensorData(Double sensorData) {
        this.sensorData = sensorData;
    }

    public Integer getDataCollectionHour() {
        return dataCollectionHour;
    }

    public void setDataCollectionHour(Integer dataCollectionHour) {
        this.dataCollectionHour = dataCollectionHour;
    }

    public Long getDataCollectionTime() {
        return dataCollectionTime;
    }

    public void setDataCollectionTime(Long dataCollectionTime) {
        this.dataCollectionTime = dataCollectionTime;
    }

    public String getDataCollectionLocation() {
        return dataCollectionLocation;
    }

    public void setDataCollectionLocation(String dataCollectionLocation) {
        this.dataCollectionLocation = dataCollectionLocation;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public Integer getVc() {
        return vc;
    }

    public void setVc(Integer vc) {
        this.vc = vc;
    }

    public WaterSensor() {
    }

    public WaterSensor(String sensorId, String sensorName, Integer sensorModel, Long ts, Integer vc, Double sensorData, Integer dataCollectionHour, Long dataCollectionTime, String dataCollectionLocation) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.sensorModel = sensorModel;
        this.ts = ts;
        this.vc = vc;
        this.sensorData = sensorData;
        this.dataCollectionHour = dataCollectionHour;
        this.dataCollectionTime = dataCollectionTime;
        this.dataCollectionLocation = dataCollectionLocation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorId, sensorName, sensorModel, ts, vc, sensorData, dataCollectionHour, dataCollectionTime, dataCollectionLocation);
    }

    @Override
    public String toString() {
        return "WaterSensor{" +
                "sensorId='" + sensorId + '\'' +
                ", sensorName='" + sensorName + '\'' +
                ", sensorModel=" + sensorModel +
                ", ts=" + ts +
                ", vc=" + vc +
                ", sensorData=" + sensorData +
                ", dataCollectionHour=" + dataCollectionHour +
                ", dataCollectionTime=" + dataCollectionTime +
                ", dataCollectionLocation='" + dataCollectionLocation + '\'' +
                '}';
    }
}

