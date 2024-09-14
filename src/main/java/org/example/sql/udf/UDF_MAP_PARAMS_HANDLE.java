package org.example.sql.udf;

import org.apache.flink.table.functions.ScalarFunction;

import java.util.Map;

/**
 * 自定义函数UDF: 接收Map类型的参数
 */
public class UDF_MAP_PARAMS_HANDLE extends ScalarFunction {

    public String eval(Map<String, String> params) {
        StringBuilder res = new StringBuilder();
        res.append("params_");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // 在这里处理键和值
            System.out.println("UDF_MAP_PARAMS_HANDLE 函数处理 key: " + key);
            System.out.println("UDF_MAP_PARAMS_HANDLE 函数处理 value: " + value);
            res.append(key).append("_").append(value);
        }

        return res.toString();
    }
}
