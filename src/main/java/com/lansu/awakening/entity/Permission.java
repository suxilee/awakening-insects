package com.lansu.awakening.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 许可
 *
 * @author sulan
 * @date 2023/08/03
 */
@Table("sys_permission")
@Accessors(chain = true)
@Data(staticConstructor = "create")
public class Permission {

    /**
     * 用户ID
     */
    @Id(keyType= KeyType.Generator, value= KeyGenerators.flexId)
    private Long id;

    /**
     * 许可名字
     */
    private String permissionName;

    /**
     * 许可代码
     */
    private String permissionCode;


    /**
     * api
     */
    private String api;
}
