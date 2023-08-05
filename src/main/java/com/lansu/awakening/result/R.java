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
public class R<T> implements Serializable, ResultCode {
    private Integer code;
    private String message;
    private T data;

    private R() {
    }

    public static <T> R<T> ok() {
        R<T> r = new R<>();
        r.code = SUCCESS_CODE;
        r.message = SUCCESS_MESSAGE;
        return r;
    }


    public static <T> R<T> error() {
        R<T> r = new R<>();
        r.code = ERROR_CODE;
        r.message = ERROR_MESSAGE;
        return r;
    }

    public static <T> R<T> failed() {
        R<T> r = new R<>();
        r.code = FAILED_CODE;
        r.message = FAILED_MESSAGE;
        return r;
    }

    public static <T> R<T> result(ResultCode code) {
        R<T> r = new R<>();
        r.code = code.getCode();
        r.message = code.getMessage();
        return r;
    }

    public static <T> R<T> result(Integer code, String message) {
        R<T> r = new R<>();
        r.code = code;
        r.message = message;
        return r;
    }

    public R<T> data(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}