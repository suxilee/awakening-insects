/*
 初始化数据库，仅有RBAC权限管理相关表
 Source Server Type    : MySQL
 Source Server Version : 80023 (8.0.23)
 Source Schema         : awakening_insects

*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `permission_name` varchar(255) NOT NULL COMMENT '权限名称',
                                  `permission_code` varchar(255) NOT NULL COMMENT '权限码',
                                  `api` varchar(255) DEFAULT NULL COMMENT 'api地址',
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `unique_api` (`api`) USING BTREE COMMENT 'api唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `api`) VALUES (1, '测试接口', 'test', '/test');
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `api`) VALUES (2, '管理员测试接口', 'adminCode', '/api/v1/test/admin');
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `api`) VALUES (3, '普通用户接口', 'userCode', '/api/v1/test/user');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                            `id` bigint NOT NULL COMMENT '主键',
                            `role_name` varchar(255) NOT NULL COMMENT '角色名',
                            `role_code` varchar(255) NOT NULL COMMENT '角色码',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`) VALUES (1, '超级管理员', 'super_admin');
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`) VALUES (2, '管理员', 'admin');
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`) VALUES (3, '普通用户', 'normal');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                       `role_id` bigint NOT NULL COMMENT '角色id',
                                       `permission_id` bigint NOT NULL COMMENT '权限id',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限关系表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES (1, 1, 1);
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES (2, 1, 2);
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES (3, 1, 3);
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES (4, 3, 1);
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES (5, 3, 3);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL COMMENT '主键',
                            `username` varchar(64) NOT NULL COMMENT '用户名',
                            `password` varchar(255) NOT NULL COMMENT '密码',
                            `enabled` int DEFAULT '1' COMMENT '禁用',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `unique_username` (`username`) USING BTREE COMMENT '用户名唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `username`, `password`, `enabled`) VALUES (1, 'admin', '$2a$10$BVnCdnquIHdHs9a8jY3MPOxWj/rd2sxf7EiblZ3N9tTrVsJ/riAy2', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                 `user_id` bigint NOT NULL COMMENT '用户id',
                                 `role_id` bigint NOT NULL COMMENT '角色id',
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10895829436000117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES (10819356358001033, 1, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
