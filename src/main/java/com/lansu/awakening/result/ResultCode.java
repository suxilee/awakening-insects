package com.lansu.awakening.result;

/**
 * 结果代码
 *
 * @author sulan
 * @date 2023/08/06
 */
public interface ResultCode {

    Integer SUCCESS_CODE = 200;
    String SUCCESS_MESSAGE = "success";

    Integer FAILED_CODE = 400;
    String FAILED_MESSAGE = "未知请求错误";

    Integer ERROR_CODE=500;
    String ERROR_MESSAGE="服务器未知错误";

    Integer getCode();

    String getMessage();

}
