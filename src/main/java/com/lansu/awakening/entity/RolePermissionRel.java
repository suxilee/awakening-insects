package com.lansu.awakening.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色权限关联关系
 *
 * @author sulan
 * @date 2023/08/04
 */
@Table("sys_role_permission")
@Accessors(chain = true)
@Data(staticConstructor = "create")
public class RolePermissionRel {

    /**
     * zhujian
     */
    @Id(keyType= KeyType.Generator, value= KeyGenerators.flexId)
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 权限ID
     */
    private Long permissionId;
}
