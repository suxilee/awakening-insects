package com.lansu.awakening.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户详细信息
 *
 * @author sulan
 * @date 2023/08/03
 */
@Table("sys_user")
@Accessors(chain = true)
@Data(staticConstructor = "create")
public class UserDetailsDO implements UserDetails , Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    private Long id;

    /**
     * 用户名
     */
    @JSONField(name = "username")
    private String username;

    /**
     * 密码
     */
    @JSONField(name = "password")
    private String password;

    /**
     * 启用
     */
    private Boolean enabled;

    /**
     * 角色
     */
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleCode()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username ;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
