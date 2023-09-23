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
    private Long permissionId;

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

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 孩子id
     */
    private String childrenId;

    /**
     * 权限类型
     */
    private Integer permissionType;

    /**
     * 组件
     */
    private String component;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 路由名称
     */
    private String name;

    /**
     * 路由标题
     */
    private String title;

    /**
     * 请求方式
     */
    private String apiMethod;
}
