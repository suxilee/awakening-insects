package com.lansu.awakening;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author sulan
 * @date 2023/08/05
 */
@SpringBootApplication
@MapperScan("com.lansu.awakening.mapper")
public class AwakeningInsectsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwakeningInsectsApplication.class, args);
    }

}
