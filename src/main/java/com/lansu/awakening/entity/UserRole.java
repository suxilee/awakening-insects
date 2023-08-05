package com.lansu.awakening.entity;


import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户角色
 *
 * @author sulan
 * @date 2023/08/04
 */
@Table("sys_user_role")
@Accessors(chain = true)
@Data(staticConstructor = "create")
public class UserRole {

    /**
     * zhujian
     */
    @Id(keyType= KeyType.Generator, value= KeyGenerators.flexId)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
