package org.example.sql.udf;

import org.apache.flink.table.functions.ScalarFunction;

/**
 * 自定义函数UDF: 给传入的字符串拼接并增加后缀 -test
 */
public class UDF_STRING_APPEND_SUFFIX extends ScalarFunction {

    public String eval(String ...params) {

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            res.append("PARAMS_").append(i).append("_").append(params[i]);
            if (i != params.length - 1) {
                res.append("_");
            }
        }
        return res.append("-test").toString();
    }
}
