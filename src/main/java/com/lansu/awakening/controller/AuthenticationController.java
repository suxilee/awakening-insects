package com.lansu.awakening.controller;

import com.lansu.awakening.annotation.Log4ai;
import com.lansu.awakening.controller.dto.AuthenticationRequest;
import com.lansu.awakening.controller.dto.RegisterRequest;
import com.lansu.awakening.controller.vo.AuthenticationResponse;
import com.lansu.awakening.result.R;
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
     * @return {@link ResponseEntity}<{@link AuthenticationResponse}>
     */
    @PostMapping("/register")
    @Log4ai("注册用户")
    public R register(@RequestBody RegisterRequest request) throws JOSEException {
        return R.ok(service.register(request));
    }

    /**
     * 进行身份验证
     *
     * @param request 请求
     * @return {@link ResponseEntity}<{@link AuthenticationResponse}>
     */
    @PostMapping("/login")
    public R authenticate(@RequestBody AuthenticationRequest request) throws JOSEException {
        return R.ok(service.authenticate(request)));
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
