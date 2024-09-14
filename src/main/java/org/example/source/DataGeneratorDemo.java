package org.example.source;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.datagen.source.GeneratorFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 从数据生成器读取数据：
 *
 * Flink从1.11开始提供了一个内置的DataGen 连接器，主要是用于生成一些随机数
 *
 * 用于在没有数据源的时候，进行流任务的测试以及性能测试等。
 * 1.17提供了新的Source写法
 */
public class DataGeneratorDemo {
    public static void main(String[] args) throws Exception {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Configuration conf = new Configuration();
        conf.setString(RestOptions.BIND_PORT,"8081");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
        env.setParallelism(6);

        // 如果有n个并行度， 最大值设为a
        // 将数值 均分成 n份，  a/n ,比如，最大100，并行度2，每个并行度生成50个
        // 其中一个是 0-49，另一个50-99
        env.setParallelism(2);

        /**
         * 数据生成器Source，四个参数：
         *     第一个： GeneratorFunction接口，需要实现， 重写map方法， 输入类型固定是Long
         *     第二个： long类型， 自动生成的数字序列（从0自增）的最大值(小于)，达到这个值就停止了
         *     第三个： 限速策略， 比如 每秒生成几条数据
         *     第四个： 返回的类型
         */
        DataGeneratorSource<String> dataGeneratorSource = new DataGeneratorSource<>(
                new GeneratorFunction<Long, String>() {
                    @Override
                    public String map(Long value) throws Exception {
                        return "Number:" + value;
                    }
                },
                100,
                RateLimiterStrategy.perSecond(1),
                Types.STRING
        );

        env
                .fromSource(dataGeneratorSource, WatermarkStrategy.noWatermarks(), "data-generator")
                .print();


        env.execute();
    }
}
