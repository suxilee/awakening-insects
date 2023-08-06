package com.lansu.awakening.config;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.keygen.impl.FlexIDKeyGenerator;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisFlex配置
 *
 * @author sulan
 * @date 2023/08/05
 */
@Configuration
@Slf4j
public class MyBatisFlexConfiguration implements MyBatisFlexCustomizer {


    public MyBatisFlexConfiguration() {
        //开启审计功能
        AuditManager.setAuditEnable(true);

        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage ->
                log.info("SQL: {}; , 耗时: {}ms", auditMessage.getFullSql()
                        , auditMessage.getElapsedTime())
        );
    }

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
