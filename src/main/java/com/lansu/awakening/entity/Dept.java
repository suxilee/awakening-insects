package com.lansu.awakening.entity;


import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 部门
 *
 * @author sulan
 * @date 2023/09/23
 */
@Table("sys_dept")
@Accessors(chain = true)
@Data(staticConstructor = "create")
public class Dept {
    /**
     * 用户ID
     */
    @Id(keyType= KeyType.Generator, value= KeyGenerators.flexId)
    private Long deptId;

    private String deptName;

    private Long parentId;

    private String childrenId;


}
