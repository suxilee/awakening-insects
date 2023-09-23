package com.lansu.awakening.auth;

import com.lansu.awakening.entity.UserDetailsDO;
import com.lansu.awakening.mapper.UserDetailsMapper;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.lansu.awakening.entity.table.RoleTableDef.ROLE;
import static com.lansu.awakening.entity.table.UserDetailsDOTableDef.USER_DETAILS_D_O;
import static com.mybatisflex.core.query.QueryMethods.select;


/**
 * 应用程序配置
 *
 * @author sulan
 * @date 2023/08/04
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig {


    /**
     * 用户详细信息映射器
     */
    private final UserDetailsMapper userDetailsMapper;

    /**
     * 用户详情服务
     *
     * @return {@link UserDetailsService}
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            //获取数据用户信息
            QueryWrapper wrapper = QueryWrapper.create().from(USER_DETAILS_D_O)
                    .where(USER_DETAILS_D_O.USERNAME.eq(username));
            List<UserDetailsDO> userDetailsList = userDetailsMapper.selectListByQuery(wrapper
                    , fieldQueryBuilder -> fieldQueryBuilder.field(UserDetailsDO::getRoles)
                            .queryWrapper(userDetailsDO -> QueryWrapper.create()
                                    //关联查询角色
                                    .select().from(ROLE).where(ROLE.ROLE_ID.in(select(new QueryColumn("role_id"))
                                            .from("sys_user_role")
                                            .where("user_id = ?", userDetailsDO.getUserId()))
                                    )
                            )
            );
            // 使用流处理迭代查找userDetailsDOS 集合内username= username的元素
            return userDetailsList.stream().filter(userDetailsDO -> userDetailsDO.getUsername().equals(username))
                    .findFirst().orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        };
    }

    /**
     * 密码编码器
     *
     * @return {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份验证提供者
     *
     * @return {@link AuthenticationProvider}
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * 身份验证管理器
     *
     * @param config 配置
     * @return {@link AuthenticationManager}
     * @throws Exception 异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
