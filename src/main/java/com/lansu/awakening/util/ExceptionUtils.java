package com.lansu.awakening.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 *
 * @author sulan
 * @date 2023/08/06
 */
@Slf4j
public class ExceptionUtils {

    /**
     * 得到消息
     *
     * @param e e
     * @return {@link String}
     */
    public static String getMessage(Exception e){
        String swStr = null;
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            swStr = sw.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return swStr;
    }
}
