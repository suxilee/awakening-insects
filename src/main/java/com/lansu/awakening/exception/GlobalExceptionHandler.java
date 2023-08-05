package com.lansu.awakening.exception;

import com.lansu.awakening.result.R;
import com.lansu.awakening.util.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理程序
 *
 * @author sulan
 * @date 2023/08/06
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 通用异常处理
     *
     * @param e e
     * @return {@link R}
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        log.error(ExceptionUtils.getMessage(e));
        return R.error();
    }
}
