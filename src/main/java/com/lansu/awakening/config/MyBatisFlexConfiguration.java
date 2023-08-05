package com.lansu.awakening.config;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.keygen.impl.FlexIDKeyGenerator;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisFlex配置
 *
 * @author sulan
 * @date 2023/08/05
 */
@Configuration
public class MyBatisFlexConfiguration implements MyBatisFlexCustomizer {
    @Override
    public void customize(FlexGlobalConfig globalConfig) {

    }

    /**
     * Flex id密钥生成器
     *
     * @return {@link FlexIDKeyGenerator}
     */
    @Bean
    FlexIDKeyGenerator flexIdKeyGenerator() {
        return new FlexIDKeyGenerator();
    }
}
