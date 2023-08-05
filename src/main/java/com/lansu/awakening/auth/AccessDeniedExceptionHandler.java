package com.lansu.awakening.auth;

import com.lansu.awakening.result.R;
import com.lansu.awakening.util.HttpUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfException;

import java.io.IOException;

/**
 * 拒绝访问异常处理程序
 *
 * @author sulan
 * @date 2023/08/05
 */
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //CsrfException
        if (accessDeniedException instanceof CsrfException) {
            //Csrf异常
            R result = R.create().setCode(403).setMessage("跨站请求保护");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        //CsrfException
        if (accessDeniedException instanceof org.springframework.security.web.server.csrf.CsrfException) {
            //Csrf异常
            R result = R.create().setCode(403).setMessage("跨站请求保护");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        //AuthorizationServiceException null
        if (accessDeniedException instanceof org.springframework.security.access.AuthorizationServiceException) {
            //授权服务异常
            R result = R.create().setCode(403).setMessage("授权服务异常");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        //拒绝访问
        R result = R.create().setCode(403).setMessage("权限不足,拒绝访问");
        //使用json序列化返回，状态码200
        HttpUtils.responseJson(response, result);
    }
}
