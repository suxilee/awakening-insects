package com.lansu.awakening.auth;


import com.lansu.awakening.mapper.RolePermissionSecurityMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.Collection;
import java.util.Map;


/**
 * Ai安全配置
 *
 * @author sulan
 * @date 2023/08/03
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {


    /**
     * 角色权限映射器
     */
    private final RolePermissionSecurityMapper rolePermissionSecurityMapper;

    /**
     * 身份验证提供者
     */
    private final AuthenticationProvider authenticationProvider;

    /**
     * JWT验证过滤器
     */
    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * 注销处理程序
     */
    private final LogoutHandler logoutHandler;

    /**
     * 安全过滤链
     *
     * @param http http
     * @return {@link SecurityFilterChain}
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                //放行静态资源和登录相关接口
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(
                                        "/api/v1/auth/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html").permitAll()
                                //动态权限验证
                                .anyRequest().access((authenticationSupplier,requestAuthorizationContext)->{
                                    // 当前用户的权限信息 比如角色
                                    Collection<? extends GrantedAuthority> authorities
                                            = authenticationSupplier.get().getAuthorities();
                                    // 当前请求上下文
                                    // 我们可以获取携带的参数
                                    Map<String, String> variables = requestAuthorizationContext.getVariables();
                                    // 我们可以获取原始request对象
                                    HttpServletRequest request = requestAuthorizationContext.getRequest();
                                    //todo 根据这些信息 和业务写逻辑即可 最终决定是否授权 isGranted
                                    boolean isGranted = true;
                                    return new AuthorizationDecision(isGranted);
                                })
                                .anyRequest().authenticated()
                );
        //其它设置
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                //jwt过滤器
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                //自定义异常处理
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new AuthenticationExceptionHandler())
                        .accessDeniedHandler(new AccessDeniedExceptionHandler())
                )
                .logout(logout -> {
                    //退出登录处理
                    logout.addLogoutHandler(logoutHandler);
                    logout.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
                })
        ;
        return http.build();
    }

    /**
     * Web安全定制器
     *
     * @return {@link WebSecurityCustomizer}
     */
//    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        //放行所有请求，通过上方@bean控制，方便开发环境调试
        return webSecurity -> webSecurity.ignoring().anyRequest();
    }




}
