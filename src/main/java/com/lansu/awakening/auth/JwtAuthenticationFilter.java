package com.lansu.awakening.auth;

import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.lansu.awakening.util.JwtUtils;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;

/**
 * JWT认证过滤器
 *
 * @author sulan
 * @date 2023/08/04
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 用户详情服务
     */
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //请求验证服务api直接放行
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        //获取基础信息
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String subject;
        //非Bearer 开头的Authorization头部信息直接放行
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //截取请求头的中jwt
        jwt = authHeader.substring(7);
        try {
            //检查token是否合法
            if (!JwtUtils.validateToken(jwt)) {
                log.warn("token不合法");
                //使用response返回错误信息
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token不合法");
                return;
            }
            //检查token是否过期
            if (JwtUtils.isTokenExpired(jwt)) {
                log.info("token已过期");
                //使用response返回错误信息
                response.sendError(HttpServletResponse.SC_OK, "token已过期");
                return;
            }
            //todo 校验token是否在黑名单中
            //解析jwt获取凭证
            subject = JwtUtils.getSubject(jwt);
            Authentication authentication = JSONObject.parseObject(subject, Authentication.class);
            //设置请求安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ParseException e) {
            log.warn("token解析失败:{}", e.getMessage());
            //使用response返回错误信息
            response.sendError(HttpServletResponse.SC_OK, e.getMessage());
            return;
        } catch (JOSEException e) {
            log.warn("token校验失败:{}", e.getMessage());
            response.sendError(HttpServletResponse.SC_OK, e.getMessage());
            return;
        } catch (JSONException e){
            log.warn("token解析失败:{}", e.getMessage());
            response.sendError(HttpServletResponse.SC_OK, e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
