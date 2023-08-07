package com.lansu.awakening.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户
 *
 * @author sulan
 * @date 2023/08/03
 */
@Table("sys_user")
@Accessors(chain = true)
@Data(staticConstructor = "create")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @Id(keyType= KeyType.None)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 启用
     */
    private Integer enabled;
}
