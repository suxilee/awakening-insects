package com.lansu.awakening.result;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一返回对象
 *
 * @author sulan
 * @date 2023/08/05
 */
@Getter
public class R implements Serializable, ResultCode {
    private Integer code;
    private String message;
    private Object data;

    private R() {
    }

    public static R ok() {
        R r = new R();
        r.code = SUCCESS_CODE;
        r.message = SUCCESS_MESSAGE;
        return r;
    }


    public static R error() {
        R r = new R();
        r.code = ERROR_CODE;
        r.message = ERROR_MESSAGE;
        return r;
    }

    public static R failed() {
        R r = new R();
        r.code = FAILED_CODE;
        r.message = FAILED_MESSAGE;
        return r;
    }

    public static R result(ResultCode code) {
        R r = new R();
        r.code = code.getCode();
        r.message = code.getMessage();
        return r;
    }

    public static <T> R result(Integer code, String message) {
        R r = new R();
        r.code = code;
        r.message = message;
        return r;
    }

    public R data(Object data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}