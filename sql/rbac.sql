/*
 简单的RBAC模型

 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Schema         : awakening_insects

 Date: 04/08/2023 21:13:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `permission_name` varchar(255) NOT NULL COMMENT '权限名称',
  `permission_code` varchar(255) NOT NULL COMMENT '权限码',
  `api` varchar(255) DEFAULT NULL COMMENT 'api地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `api`) VALUES (1, '首页', 'index', '/index');
INSERT INTO `sys_permission` (`id`, `permission_name`, `permission_code`, `api`) VALUES (2, '管理后台', 'admin_mg', '/admin');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int NOT NULL COMMENT '主键',
  `role_name` varchar(255) NOT NULL COMMENT '角色名',
  `role_code` varchar(255) NOT NULL COMMENT '角色码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`) VALUES (1, '普通用户', 'qrdinary');
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`) VALUES (2, '超级管理员', 'superAdmin');
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`) VALUES (3, '管理员', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int NOT NULL COMMENT '角色id',
  `permission_id` int NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限关系表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES (1, 1, 1);
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES (2, 2, 1);
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`) VALUES (3, 2, 2);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int NOT NULL COMMENT '主键',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `enabled` int DEFAULT '1' COMMENT '禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `username`, `password`, `enabled`) VALUES (1, 'admin', '$2a$10$TIC/DSXe/vSutfzWam/E3efIeJ8h1H1CGnfEIRhuuFPay1lXGXada', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES (1, 1, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
