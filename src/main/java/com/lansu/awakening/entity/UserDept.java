package com.lansu.awakening.entity;


import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户部门
 *
 * @author sulan
 * @date 2023/09/23
 */
@Table("sys_user_dept")
@Accessors(chain = true)
@Data(staticConstructor = "create")
public class UserDept {

    /**
     * 用户ID
     */
    @Id(keyType= KeyType.Generator, value= KeyGenerators.flexId)
    private Long id;


    private Long userId;

    private Long DeptId;
}
