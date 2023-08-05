package com.lansu.awakening.exception;

import com.alibaba.fastjson2.JSONObject;
import com.lansu.awakening.result.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 网络异常
 *
 * @author sulan
 * @date 2023/08/06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WebException extends RuntimeException {
    private Integer code;

    public WebException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public WebException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
