package com.lansu.awakening.auth;

import com.lansu.awakening.entity.Permission;
import com.lansu.awakening.entity.RolePermissions;
import com.lansu.awakening.mapper.RolePermissionsMapper;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lansu.awakening.entity.table.PermissionTableDef.PERMISSION;
import static com.lansu.awakening.entity.table.RoleTableDef.ROLE;
import static com.mybatisflex.core.query.QueryMethods.select;


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
    private final RolePermissionsMapper rolePermissionsMapper;

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
        Map<String, List<String>> rolePermission = getRolePermission();
        rolePermission.forEach((key, value) -> {
            try {
                if (!value.isEmpty()) {
                    log.info("初始化权限:角色({}),权限({})", key,value);
                    http.authorizeHttpRequests(auth ->
                            auth.requestMatchers(value.toArray(new String[0])).hasRole(key));
                }
            } catch (Exception e) {
                log.warn("角色权限配置异常:{}", e.getMessage());
            }
        });
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

    /**
     * 获得角色权限
     *
     * @return {@link Map}<{@link String},{@link List}<{@link String}>>
     */
    private Map<String, List<String>> getRolePermission() {
        Map<String, List<String>> roleMap = new HashMap<>();
        QueryWrapper wrapper = QueryWrapper.create().from(ROLE);
        List<RolePermissions> rolePermissionsList = rolePermissionsMapper.selectListByQuery(wrapper
                , fieldQueryBuilder -> fieldQueryBuilder
                        .field(RolePermissions::getPermissions)
                        .queryWrapper(rolePermissions -> QueryWrapper.create()
                                .select().from(PERMISSION)
                                .where(PERMISSION.ID.in(
                                        select(new QueryColumn("permission_id"))
                                                .from("sys_role_permission")
                                                .where("role_id = ?", rolePermissions.getId()))
                                )
                        )
        );
        //构建map映射
        rolePermissionsList.forEach(rolePermissions -> {
            roleMap.put(rolePermissions.getRoleCode()
                    , rolePermissions.getPermissions().stream()
                            .map(Permission::getApi).toList());
        });
        return roleMap;
    }


}
