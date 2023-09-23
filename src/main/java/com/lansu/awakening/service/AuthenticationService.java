package com.lansu.awakening.service;

import com.lansu.awakening.controller.dto.AuthenticationRequestDTO;
import com.lansu.awakening.controller.dto.RegisterRequestDTO;
import com.lansu.awakening.controller.vo.AuthenticationResponseVO;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 身份验证服务
 *
 * @author sulan
 * @date 2023/08/04
 */
public interface AuthenticationService {
    /**
     * 注册
     *
     * @param request 请求
     * @return {@link AuthenticationResponseVO}
     */
    Boolean register(RegisterRequestDTO request) throws JOSEException;

    /**
     * 进行身份验证
     *
     * @param request 请求
     * @return {@link AuthenticationResponseVO}
     */
    AuthenticationResponseVO authenticate(AuthenticationRequestDTO request) throws JOSEException;

    /**
     * 刷新令牌
     *
     * @param request  请求
     * @param response 响应
     */
    void refreshToken(HttpServletRequest request,HttpServletResponse response);
}
