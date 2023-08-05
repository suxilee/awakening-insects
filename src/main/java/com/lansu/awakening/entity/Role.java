package com.lansu.awakening.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色
 *
 * @author sulan
 * @date 2023/08/03
 */
@Table("sys_role")
@Accessors(chain = true)
@Data(staticConstructor = "create")
public class Role {

    /**
     * 用户ID
     */
    @Id(keyType= KeyType.Generator, value= KeyGenerators.flexId)
    private Long id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色代码
     */
    private String roleCode;
}
