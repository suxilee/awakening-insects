package com.lansu.awakening.service.impl;

import com.lansu.awakening.controller.dto.AuthenticationRequest;
import com.lansu.awakening.controller.dto.RegisterRequest;
import com.lansu.awakening.controller.vo.AuthenticationResponse;
import com.lansu.awakening.entity.User;
import com.lansu.awakening.entity.UserRole;
import com.lansu.awakening.mapper.UserMapper;
import com.lansu.awakening.mapper.UserRoleMapper;
import com.lansu.awakening.service.AuthenticationService;
import com.lansu.awakening.util.JwtUtils;
import com.mybatisflex.core.keygen.impl.FlexIDKeyGenerator;
import com.mybatisflex.core.query.QueryWrapper;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lansu.awakening.entity.table.UserTableDef.USER;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 身份验证管理器
     */
    private final AuthenticationManager authenticationManager;

    /**
     * 用户角色映射器
     */
    private final UserRoleMapper userRoleMapper;

    /**
     * 用户映射器
     */
    private final UserMapper userMapper;

    /**
     * flex id
     */
    private final FlexIDKeyGenerator flexId;

    /**
     * 令牌过期时间
     * 默认7天
     * 单位毫秒
     */
    @Value("${jwt.token.expire-time}")
    private Long tokenExpireTime;

    /**
     * 刷新令牌过期时间
     * 默认15天
     * 单位毫秒
     */
    @Value("${jwt.refresh-token.expire-time}")
    private Long refreshTokenExpireTime;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthenticationResponse register(RegisterRequest request) throws JOSEException {
        //创建用户
        User user = User.create()
                .setUsername(request.getUsername())
                .setPassword(passwordEncoder.encode(request.getPassword())).setEnabled(1);
        //设置用户id
        Object id = flexId.generate(user, "id");
        user.setId((Long) id);
        userMapper.insert(user);
        //设置普通角色身份
        userRoleMapper.insert(UserRole.create()
                .setUserId(user.getId())
                .setRoleId(3L));
        //生成令牌 todo 需修改为注册结果
        return AuthenticationResponse
                .builder()
                .accessToken(JwtUtils.generateToken(user.getUsername(), tokenExpireTime))
                .refreshToken(JwtUtils.generateToken(user.getUsername(), refreshTokenExpireTime))
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws JOSEException {
        //验证用户
        Authentication token = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername()
                        , request.getPassword()));
        //生成令牌
        return AuthenticationResponse
                .builder()
                .accessToken(JwtUtils.generateToken(request.getUsername(), tokenExpireTime))
                .refreshToken(JwtUtils.generateToken(request.getUsername(), refreshTokenExpireTime))
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {

    }
}