package com.lansu.awakening.controller;

import com.lansu.awakening.annotation.Desensitize;
import com.lansu.awakening.annotation.Log4ai;
import com.lansu.awakening.controller.dto.AuthenticationRequestDTO;
import com.lansu.awakening.controller.dto.RegisterRequestDTO;
import com.lansu.awakening.controller.vo.AuthenticationResponseVO;
import com.lansu.awakening.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 身份验证控制器
 *
 * @author sulan
 * @date 2023/08/04
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    /**
     * 注册
     *
     * @param request 请求
     * @return {@link ResponseEntity}<{@link AuthenticationResponseVO}>
     */
    @PostMapping("/register")
    @Log4ai("注册用户")
    public String register(@RequestBody @Desensitize RegisterRequestDTO request) throws JOSEException {
        return service.register(request)?"注册成功":"注册失败";
    }

    /**
     * 进行身份验证
     *
     * @param request 请求
     * @return {@link ResponseEntity}<{@link AuthenticationResponseVO}>
     */
    @PostMapping("/login")
    @Log4ai
    public AuthenticationResponseVO authenticate(@RequestBody AuthenticationRequestDTO request) throws JOSEException {
        return service.authenticate(request);
    }

    /**
     * 刷新令牌
     *
     * @param request  请求
     * @param response 响应
     * @throws IOException ioexception
     */
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }


}
