package com.lansu.awakening.util;

import com.alibaba.fastjson2.JSON;
import com.lansu.awakening.result.R;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * http工具类
 *
 * @author sulan
 * @date 2023/08/05
 */
public class HttpUtils {

    /**
     * json响应
     *
     * @param response 响应
     */
    public static void responseJson(HttpServletResponse response, R result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
