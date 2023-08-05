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
            //身份验证服务异常
            R result = R.create().setCode(401).setMessage("身份验证服务异常");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        //异常为 UsernameNotFoundException
        if (authException instanceof UsernameNotFoundException) {
            //用户名不存在
            R result = R.create().setCode(401).setMessage("用户名不存在");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        //ProviderNotFoundException
        if (authException instanceof ProviderNotFoundException) {
            //找不到身份验证提供者
            R result = R.create().setCode(401).setMessage("找不到身份验证提供者");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        //PreAuthenticatedCredentialsNotFoundException
        if (authException instanceof PreAuthenticatedCredentialsNotFoundException) {
            //找不到预先验证的凭据
            R result = R.create().setCode(401).setMessage("找不到预先验证的凭据");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        //NonceExpiredException
        if (authException instanceof NonceExpiredException) {
            //Nonce已过期
            R result = R.create().setCode(401).setMessage("Nonce已过期");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        //BadCredentialsException
        if (authException instanceof BadCredentialsException) {
            //账号或密码错误
            R result = R.create().setCode(401).setMessage("账号或密码错误");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        //RememberMeAuthenticationException null
        if (authException instanceof RememberMeAuthenticationException) {
            //自动登录异常
            R result = R.create().setCode(401).setMessage("自动登录异常");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        //InsufficientAuthenticationException null
        if (authException instanceof InsufficientAuthenticationException) {
            //身份验证不足
            R result = R.create().setCode(401).setMessage("身份验证不足");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        //AuthenticationCredentialsNotFoundException null
        if (authException instanceof AuthenticationCredentialsNotFoundException) {
            //未找到密码
            R result = R.create().setCode(401).setMessage("账号或密码错误");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        // AccountStatusException null
        if (authException instanceof AccountStatusException) {
            //账号状态异常
            R result = R.create().setCode(401).setMessage("账号已禁用");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        //SessionAuthenticationException null
        if (authException instanceof SessionAuthenticationException) {
            //会话身份验证异常
            R result = R.create().setCode(401).setMessage("会话身份验证异常");
            //使用json序列化返回，状态码200
            HttpUtils.responseJson(response, result);
            return;
        }
        //身份验证异常
        R result = R.create().setCode(401).setMessage("身份验证异常");
        //使用json序列化返回，状态码200
        HttpUtils.responseJson(response, result);
    }
}
