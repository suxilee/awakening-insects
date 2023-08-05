package com.lansu.awakening.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * 注销服务
 *
 * @author sulan
 * @date 2023/08/04
 */
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //todo 在此拉黑token
    }
}
