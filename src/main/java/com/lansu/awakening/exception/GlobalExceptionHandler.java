package com.lansu.awakening.exception;

import com.lansu.awakening.result.R;
import com.lansu.awakening.util.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.security.web.server.csrf.CsrfException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 全局异常处理程序
 *
 * @author sulan
 * @date 2023/08/06
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 通用异常处理
     *
     * @param e e
     * @return {@link R}
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            log.error("响应异常: {},uri: {}, ex: {}", attributes.getSessionId()
                    , attributes.getRequest().getRequestURI(), ExceptionUtils.getMessage(e));
        }
        log.error("响应异常: ex: {}", ExceptionUtils.getMessage(e));
        return R.error();
    }


    /**
     * security 框架异常再次抛出
     *
     * @param e e
     * @throws Exception 异常
     */
    @ExceptionHandler({
            CsrfException.class
            , org.springframework.security.web.server.csrf.CsrfException.class
            , AuthorizationServiceException.class
    })
    public void security(AccessDeniedException e) throws Exception {
        throw e;
    }

    /**
     * security 框架异常再次抛出
     *
     * @param e e
     * @throws Exception 异常
     */
    @ExceptionHandler({
            AuthenticationServiceException.class
            , UsernameNotFoundException.class
            , ProviderNotFoundException.class
            , PreAuthenticatedCredentialsNotFoundException.class
            , NonceExpiredException.class
            , BadCredentialsException.class
            , RememberMeAuthenticationException.class
            , InsufficientAuthenticationException.class
            , AuthenticationCredentialsNotFoundException.class
            , AccountStatusException.class
            , SessionAuthenticationException.class
    })
    public void security(AuthenticationException e) throws Exception {
        throw e;
    }
}
