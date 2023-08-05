package com.lansu.awakening.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * log4ai
 * 请求日志注解
 *
 * @author sulan
 * @date 2023/08/05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log4ai {
    String value() default "";
}
