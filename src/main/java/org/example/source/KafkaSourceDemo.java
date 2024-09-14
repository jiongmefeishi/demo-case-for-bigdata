package org.example.source;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;

/**
 * 从Kafka读取数据：
 *
 * Flink官方提供了连接工具flink-connector-kafka，直接帮我们实现了一个消费者FlinkKafkaConsumer，
 * 它就是用来读取Kafka数据的SourceFunction。
 *
 * 所以想要以Kafka作为数据源获取数据，我们只需要引入Kafka连接器的依赖。
 * Flink官方提供的是一个通用的Kafka连接器，它会自动跟踪最新版本的Kafka客户端。
 *
 * 目前最新版本只支持0.10.0版本以上的Kafka。这里我们需要导入的依赖如下。
 */
public class KafkaSourceDemo {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // TODO 从Kafka读： 新Source架构
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers("hadoop102:9092,hadoop103:9092,hadoop104:9092") // 指定kafka节点的地址和端口
                .setGroupId("atguigu")  // 指定消费者组的id
                .setTopics("topic_1")   // 指定消费的 Topic
                .setValueOnlyDeserializer(new SimpleStringSchema()) // 指定 反序列化器，这个是反序列化value
                .setStartingOffsets(OffsetsInitializer.latest())  // flink消费kafka的策略
                .build();
        env
//                .fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "kafkasource")
                .fromSource(kafkaSource, WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(3)), "kafkasource")
                .print();


        env.execute();
    }
}
/**
 *   kafka消费者的参数：
 *      auto.reset.offsets
 *          earliest: 如果有offset，从offset继续消费; 如果没有offset，从 最早 消费
 *          latest  : 如果有offset，从offset继续消费; 如果没有offset，从 最新 消费
 *
 *   flink的kafkasource，offset消费策略：OffsetsInitializer，默认是 earliest
 *          earliest: 一定从 最早 消费
 *          latest  : 一定从 最新 消费
 *
 *
 *
 */