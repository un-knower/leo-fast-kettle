package com.dr.leo.etl.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spark session 管理配置类
 *
 * @author leo.jie (weixiao.me@aliyun.com)
 * @version 1.0
 * @organization DataReal
 * @website https://www.jlpyyf.com
 * @date 2019-06-21 21:52
 * @since 1.0
 */
@Configuration
public class SparkContextBean {
    private static final String PROD_OS = "linux";

    @Bean
    @ConditionalOnMissingBean(SparkSession.class)
    public SparkSession sparkSession() throws Exception {
        final String os = System.getProperty("os.name");
        if (os.toLowerCase().contains(PROD_OS)) {
            return SparkSession.builder()
                    .appName("FastKettle")
                    .enableHiveSupport()
                    .getOrCreate();
        } else {
            return SparkSession.builder()
                    .appName("FastKettle")
                    .master("local[8]")
                    .enableHiveSupport()
                    .getOrCreate();
        }
    }
}
