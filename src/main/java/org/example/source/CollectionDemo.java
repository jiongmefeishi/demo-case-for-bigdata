package org.example.source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 从集合中读取数据:
 *
 * 最简单的读取数据的方式，就是在代码中直接创建一个Java集合
 * 然后调用执行环境的fromCollection方法进行读取。
 *
 * 这相当于将数据临时存储到内存中，形成特殊的数据结构后，作为数据源使用，一般用于测试。
 */
public class CollectionDemo {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // TODO 从集合读取数据
        DataStreamSource<Integer> source = env
                .fromElements(1,2,33); // 从元素读
//                .fromCollection(Arrays.asList(1, 22, 3));  // 从集合读


        source.print();

        env.execute();

    }
}
