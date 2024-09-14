package org.example.source;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 从文件读取数据：
 * 真正的实际应用中，自然不会直接将数据写在代码中。
 * 通常情况下，我们会从存储介质中获取数据，一个比较常见的方式就是读取日志文件。这也是批处理中最常见的读取方式。
 */
public class FileSourceDemo {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);

        // TODO 从文件读： 新Source架构
        FileSource<String> fileSource = FileSource
                .forRecordStreamFormat(
                        new TextLineInputFormat(),
                        new Path("input/word.txt")
                )
                .build();

        env
                .fromSource(fileSource, WatermarkStrategy.noWatermarks(), "filesource")
                .print();


        env.execute();
    }
}
/**
 *
 * 新的Source写法：
 *   env.fromSource(Source的实现类，Watermark，名字)
 *
 */