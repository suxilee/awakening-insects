package com.lansu.awakening.auth;

import com.alibaba.fastjson2.JSON;
import com.lansu.awakening.result.R;
import com.lansu.awakening.util.HttpUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import java.io.IOException;

/**
 * 身份验证异常处理程序
 *
 * @author sulan
 * @date 2023/08/05
 */
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //异常为AuthenticationServiceException类 或 AuthenticationServiceException类
        if (authException instanceof AuthenticationServiceException) {
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, R.result(401,"身份验证服务异常"));
            return;
        }
        //异常为 UsernameNotFoundException
        if (authException instanceof UsernameNotFoundException) {
            //用户名不存在  R.result(401,"用户名不存在")
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, R.result(401,"用户名不存在"));
            return;
        }
        //ProviderNotFoundException
        if (authException instanceof ProviderNotFoundException) {
            //找不到身份验证提供者
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, R.result(401,"找不到身份验证提供者"));
            return;
        }
        //PreAuthenticatedCredentialsNotFoundException
        if (authException instanceof PreAuthenticatedCredentialsNotFoundException) {
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, R.result(401,"找不到预先验证的凭据"));
            return;
        }
        //NonceExpiredException
        if (authException instanceof NonceExpiredException) {
            //Nonce已过期
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, R.result(401,"Nonce已过期"));
            return;
        }
        //BadCredentialsException
        if (authException instanceof BadCredentialsException) {
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, R.result(401,"账号或密码错误"));
            return;
        }
        //RememberMeAuthenticationException null
        if (authException instanceof RememberMeAuthenticationException) {
            //自动登录异常
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, R.result(401,"账号或密码错误"));
            return;
        }
        //InsufficientAuthenticationException null
        if (authException instanceof InsufficientAuthenticationException) {
            //身份验证不足
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, R.result(401,"身份验证不足"));
            return;
        }
        //AuthenticationCredentialsNotFoundException null
        if (authException instanceof AuthenticationCredentialsNotFoundException) {
            //未找到密码
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, R.result(401,"账号或密码错误"));
            return;
        }
        // AccountStatusException null
        if (authException instanceof AccountStatusException) {
            //账号状态异常
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, R.result(401,"账号已禁用"));
            return;
        }
        //SessionAuthenticationException null
        if (authException instanceof SessionAuthenticationException) {
            //会话身份验证异常
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, R.result(401,"会话身份验证异常"));
            return;
        }
        //身份验证异常
        //使用json序列化返回，状态码200
        HttpUtils.responseJson(response, R.result(401,"身份验证异常"));
    }
}
