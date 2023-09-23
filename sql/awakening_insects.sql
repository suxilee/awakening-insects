/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.77_3306
 Source Server Type    : MySQL
 Source Server Version : 80023 (8.0.23)
 Source Host           : 192.168.1.77:3306
 Source Schema         : awakening_insects

 Target Server Type    : MySQL
 Target Server Version : 80023 (8.0.23)
 File Encoding         : 65001

 Date: 23/09/2023 16:31:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL COMMENT '主键',
  `dept_name` varchar(255) DEFAULT NULL COMMENT '部门名称',
  `parent_id` bigint DEFAULT NULL COMMENT '父部门id',
  `children_id` varchar(2048) DEFAULT NULL COMMENT '子部门集合',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `permission_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `permission_name` varchar(255) NOT NULL COMMENT '权限名称',
  `permission_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限标识',
  `api` varchar(255) DEFAULT NULL COMMENT 'api地址',
  `parent_id` bigint DEFAULT '0' COMMENT '父id',
  `children_id` varchar(2048) DEFAULT '[]' COMMENT '子id集合',
  `permission_type` int NOT NULL COMMENT '权限类型：1、路由，2、接口',
  `component` varchar(255) DEFAULT NULL COMMENT '组件名称',
  `path` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `name` varchar(255) DEFAULT NULL COMMENT '路由名称',
  `title` varchar(255) DEFAULT NULL COMMENT '路由标题',
  `api_method` varchar(255) DEFAULT NULL COMMENT 'api请求方式',
  PRIMARY KEY (`permission_id`) USING BTREE,
  UNIQUE KEY `unique_api` (`api`) USING BTREE COMMENT 'api唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` (`permission_id`, `permission_name`, `permission_code`, `api`, `parent_id`, `children_id`, `permission_type`, `component`, `path`, `name`, `title`, `api_method`) VALUES (1, '测试接口', 'test', '/test', 0, '[]', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission` (`permission_id`, `permission_name`, `permission_code`, `api`, `parent_id`, `children_id`, `permission_type`, `component`, `path`, `name`, `title`, `api_method`) VALUES (2, '管理员测试接口', 'adminCode', '/api/v1/test/admin', 0, '[]', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_permission` (`permission_id`, `permission_name`, `permission_code`, `api`, `parent_id`, `children_id`, `permission_type`, `component`, `path`, `name`, `title`, `api_method`) VALUES (3, '普通用户接口', 'userCode', '/api/v1/test/user', 0, '[]', 0, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL COMMENT '主键',
  `role_name` varchar(255) NOT NULL COMMENT '角色名',
  `role_code` varchar(255) NOT NULL COMMENT '角色码',
  `data_scope` int DEFAULT NULL COMMENT '1、全局数据了；2、部门及下级部门；3、所在部门；、4、本人数据；5、指定部门数据。',
  `data_depts` varchar(2048) DEFAULT NULL COMMENT '指定数据可视范围',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`role_id`, `role_name`, `role_code`, `data_scope`, `data_depts`) VALUES (1, '超级管理员', 'super_admin', NULL, NULL);
INSERT INTO `sys_role` (`role_id`, `role_name`, `role_code`, `data_scope`, `data_depts`) VALUES (2, '管理员', 'admin', NULL, NULL);
INSERT INTO `sys_role` (`role_id`, `role_name`, `role_code`, `data_scope`, `data_depts`) VALUES (3, '普通用户', 'normal', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `permission_id` bigint NOT NULL COMMENT '权限id',
  `api` varchar(255) DEFAULT NULL COMMENT '权限api',
  `api_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '请求方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限关系表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `api`, `api_method`) VALUES (1, 1, 1, NULL, NULL);
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `api`, `api_method`) VALUES (2, 1, 2, NULL, NULL);
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `api`, `api_method`) VALUES (3, 1, 3, NULL, NULL);
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `api`, `api_method`) VALUES (4, 3, 1, NULL, NULL);
INSERT INTO `sys_role_permission` (`id`, `role_id`, `permission_id`, `api`, `api_method`) VALUES (5, 3, 3, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL COMMENT '主键',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `enabled` int DEFAULT '1' COMMENT '禁用',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `unique_username` (`username`) USING BTREE COMMENT '用户名唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`user_id`, `username`, `password`, `enabled`) VALUES (1, 'admin', '$2a$10$BVnCdnquIHdHs9a8jY3MPOxWj/rd2sxf7EiblZ3N9tTrVsJ/riAy2', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `dept_id` bigint NOT NULL COMMENT '部门id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
BEGIN;
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
