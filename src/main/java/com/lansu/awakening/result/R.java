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
public class R<T> implements Serializable,ResultCode{
    private Integer code;
    private String message;
    private T data;

    private R() {
    }

    public static <T> R<T> ok() {
        R<T> r = new R<>();
        r.code = 200;
        r.message = "成功";
        return r;
    }

    //error方法
    public static <T> R<T> error() {
        R<T> r = new R<>();
        r.code = 500;
        r.message = "服务器未知错误";
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