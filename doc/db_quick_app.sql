/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : db_quick_app

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-08-28 21:05:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_contact`
-- ----------------------------
DROP TABLE IF EXISTS `sys_contact`;
CREATE TABLE `sys_contact` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  `contact_type` int(1) NOT NULL COMMENT '联系方式：1：邮箱2：电话3：qq4：微信',
  `contact_info` varchar(50) NOT NULL COMMENT '联系信息',
  `dis_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标记：0：有效1：无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户联系信息表';

-- ----------------------------
-- Records of sys_contact
-- ----------------------------
INSERT INTO `sys_contact` VALUES ('1', 'admin', '1', '33333322', '0');
INSERT INTO `sys_contact` VALUES ('2', 'admin', '2', '333333333', '0');
INSERT INTO `sys_contact` VALUES ('3', 'admin22', '2', '222', '0');

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(10) DEFAULT NULL COMMENT '排序',
  `pid` int(10) DEFAULT NULL COMMENT '父部门id',
  `pids` varchar(255) DEFAULT NULL COMMENT '父部门ids',
  `simple_name` varchar(45) NOT NULL COMMENT '部门简称',
  `full_name` varchar(255) NOT NULL COMMENT '部门全称',
  `tips` varchar(255) NOT NULL COMMENT '提示',
  `version` int(10) NOT NULL COMMENT '版本（乐观锁保留字段）',
  `dis_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标记：0：有效1：无效',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `update_date` datetime NOT NULL COMMENT '更新日期',
  `create_user` varchar(20) NOT NULL COMMENT '创建者',
  `update_user` varchar(20) NOT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(10) NOT NULL COMMENT '排序',
  `pid` int(10) NOT NULL COMMENT '父级字典',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `tips` varchar(50) DEFAULT NULL COMMENT '提示',
  `dis_flag` int(1) NOT NULL COMMENT '删除标记：0：有效1：无效',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `update_date` datetime NOT NULL COMMENT '更新日期',
  `create_user` varchar(20) NOT NULL COMMENT '创建者',
  `update_user` varchar(20) NOT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_login_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `log_name` varchar(80) NOT NULL COMMENT '日志名称',
  `create_user` varchar(20) NOT NULL COMMENT '管理员名',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `succeed` varchar(50) DEFAULT NULL COMMENT '是否执行成功',
  `message` varchar(255) DEFAULT NULL COMMENT '具体消息',
  `ip` char(15) NOT NULL COMMENT '登录ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8 COMMENT='登录LOG表';

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES ('8', '用户登录', 'admin', '2018-08-24 20:05:19', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('9', '用户登录', 'admin', '2018-08-24 20:08:11', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('10', '用户登录', 'admin', '2018-08-24 20:13:59', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('11', '用户登录', 'admin', '2018-08-24 20:14:08', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('12', '用户登录', 'admin', '2018-08-24 20:19:05', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('13', '用户登录', 'admin', '2018-08-24 20:20:04', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('14', '用户登录', 'admin', '2018-08-24 20:22:00', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('15', '用户登录', 'admin', '2018-08-24 20:23:40', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('16', '用户登录', 'null', '2018-08-24 20:28:14', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('17', '用户登录', 'null', '2018-08-24 20:28:19', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('18', '用户登录', 'null', '2018-08-24 20:29:16', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('19', '用户登录', 'null', '2018-08-24 20:47:09', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('20', '用户登录', 'null', '2018-08-24 20:47:25', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('21', '用户登录', 'null', '2018-08-24 20:48:03', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('22', '用户登录', 'null', '2018-08-24 20:49:54', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('23', '用户登录', 'null', '2018-08-24 20:50:06', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('24', '用户登录', 'null', '2018-08-24 20:50:19', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('25', '用户登录', '22222222', '2018-08-24 20:58:00', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('26', '用户登录', '22222222', '2018-08-24 21:04:28', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('27', '用户登录', 'admin', '2018-08-24 21:04:31', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('28', '用户登录', 'admin', '2018-08-24 21:09:22', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('29', '用户登录', 'admin', '2018-08-24 21:12:14', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('30', '用户登录', 'admin', '2018-08-24 21:12:19', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('31', '用户登录', 'admin', '2018-08-24 21:13:13', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('32', '用户登录', 'admin', '2018-08-24 21:13:48', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('33', '用户登录', 'admin', '2018-08-24 21:14:57', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('34', '用户登录', 'admin', '2018-08-24 21:17:11', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('35', '用户登录', 'admin', '2018-08-24 21:21:51', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('36', '用户登录', 'admin', '2018-08-24 21:23:00', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('37', '用户登录', 'admin', '2018-08-24 21:25:39', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('38', '用户登录', 'admin', '2018-08-25 10:31:05', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('39', '用户登录', 'admin', '2018-08-25 10:35:32', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('40', '用户登录', 'admin', '2018-08-25 10:45:25', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('41', '用户登录', 'admin', '2018-08-25 10:46:32', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('42', '用户登录', 'admin', '2018-08-25 10:57:42', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('43', '用户登录', 'admin', '2018-08-25 11:01:44', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('44', '用户登录', 'admin', '2018-08-25 11:01:56', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('45', '用户登录', 'admin', '2018-08-25 11:03:17', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('46', '用户登录', 'admin', '2018-08-25 11:05:18', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('47', '用户登录', 'admin', '2018-08-25 11:09:26', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('48', '用户登录', 'admin', '2018-08-25 11:11:18', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('49', '用户登录', 'admin', '2018-08-25 11:16:46', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('50', '用户登录', 'admin', '2018-08-25 11:21:21', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('51', '用户登录', 'admin', '2018-08-25 11:25:51', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('52', '用户登录', 'admin', '2018-08-25 11:26:15', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('53', '用户登录', 'admin', '2018-08-25 12:27:06', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('54', '用户登录', 'admin', '2018-08-25 12:32:28', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('55', '用户登录', 'admin', '2018-08-25 12:32:42', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('56', '用户登录', 'admin', '2018-08-25 12:33:57', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('57', '用户登录', 'admin', '2018-08-25 12:40:57', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('58', '用户登录', 'admin', '2018-08-25 12:44:20', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('59', '用户登录', 'admin', '2018-08-25 12:45:19', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('60', '用户登录', 'admin', '2018-08-25 12:50:10', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('61', '用户登录', 'admin', '2018-08-25 12:51:39', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('62', '用户登录', 'admin', '2018-08-25 12:56:26', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('63', '用户登录', 'admin', '2018-08-25 12:59:02', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('64', '用户登录', 'admin', '2018-08-25 13:31:53', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('65', '用户登录', 'admin', '2018-08-25 13:37:16', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('66', '用户登录', 'admin', '2018-08-25 13:40:22', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('67', '用户登录', 'admin', '2018-08-25 13:43:13', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('68', '用户登录', 'admin', '2018-08-25 13:45:15', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('69', '用户登录', 'admin', '2018-08-25 14:17:38', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('70', '用户登录', 'admin', '2018-08-25 14:23:38', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('71', '用户登录', 'admin', '2018-08-25 14:25:43', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('72', '用户登录', 'admin', '2018-08-25 14:27:14', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('73', '用户登录', 'admin', '2018-08-25 14:54:06', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('74', '用户登录', 'admin', '2018-08-25 14:56:47', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('75', '用户登录', 'admin', '2018-08-25 14:57:45', 'true', '用户登录成功!', '192.168.56.1');
INSERT INTO `sys_login_log` VALUES ('76', '用户登录', 'admin', '2018-08-25 15:09:58', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('77', '用户登录', 'admin', '2018-08-25 15:19:02', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('78', '用户登录', 'admin', '2018-08-25 15:20:11', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('79', '用户登录', 'admin', '2018-08-25 15:25:19', 'true', '用户登录成功!', '192.168.56.1');
INSERT INTO `sys_login_log` VALUES ('80', '用户登录', 'admin', '2018-08-25 15:30:23', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('81', '用户登录', 'admin', '2018-08-25 15:34:01', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('82', '用户登录', 'admin', '2018-08-25 15:35:05', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('83', '用户登录', 'admin', '2018-08-25 15:38:39', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('84', '用户登录', 'admin', '2018-08-25 15:40:01', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('85', '用户登录', 'admin', '2018-08-25 15:53:03', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('86', '用户登录', 'admin', '2018-08-25 16:25:14', 'false', '账户被锁定!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('87', '用户登录', 'admin', '2018-08-25 16:28:05', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('88', '用户登录', 'admin', '2018-08-25 16:32:12', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('89', '用户登录', 'admin', '2018-08-25 16:52:26', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('90', '用户登录', 'admin', '2018-08-25 16:54:26', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('91', '用户登录', 'admin', '2018-08-25 16:57:36', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('92', '用户登录', 'admin', '2018-08-25 17:08:16', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('93', '用户登录', 'admin', '2018-08-25 17:11:37', 'true', '用户登录成功!', '192.168.56.1');
INSERT INTO `sys_login_log` VALUES ('94', '用户登录', 'admin', '2018-08-25 17:15:31', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('95', '用户登录', 'admin', '2018-08-25 17:17:07', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('96', '用户登录', 'admin', '2018-08-25 17:25:48', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('97', '用户登录', 'admin', '2018-08-25 17:29:08', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('98', '用户登录', 'admin', '2018-08-25 17:31:05', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('99', '用户登录', 'admin', '2018-08-25 17:33:13', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('100', '用户登录', 'admin', '2018-08-25 17:37:07', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('101', '用户登录', 'admin', '2018-08-25 17:37:50', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('102', '用户登录', 'admin', '2018-08-25 17:39:43', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('103', '用户登录', 'admin', '2018-08-25 17:43:56', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('104', '用户登录', 'admin', '2018-08-26 10:08:51', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('105', '用户登录', 'admin', '2018-08-26 10:10:56', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('106', '用户登录', 'admin', '2018-08-26 10:12:18', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('107', '用户登录', 'admin', '2018-08-26 10:13:21', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('108', '用户登录', 'admin', '2018-08-26 10:16:23', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('109', '用户登录', 'admin', '2018-08-26 10:21:44', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('110', '用户登录', 'admin', '2018-08-26 10:26:13', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('111', '用户登录', 'admin', '2018-08-26 10:28:43', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('112', '用户登录', 'admin', '2018-08-26 10:33:14', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('113', '用户登录', 'admin', '2018-08-26 10:34:31', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('114', '用户登录', 'admin', '2018-08-26 10:36:26', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('115', '用户登录', 'admin', '2018-08-26 10:38:24', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('116', '用户登录', 'admin', '2018-08-26 10:40:46', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('117', '用户登录', 'admin', '2018-08-26 10:40:52', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('118', '用户登录', 'admin22', '2018-08-26 10:41:10', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('119', '用户登录', 'admin', '2018-08-26 10:41:14', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('120', '用户登录', 'admin', '2018-08-26 10:42:36', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('121', '用户登录', 'admin', '2018-08-26 13:14:53', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('122', '用户登录', 'admin', '2018-08-26 13:16:29', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('123', '用户登录', 'admin', '2018-08-26 13:18:04', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('124', '用户登录', 'admin', '2018-08-26 13:25:33', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('125', '用户登录', 'admin', '2018-08-26 13:27:47', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('126', '用户登录', 'admin', '2018-08-26 14:04:51', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('127', '用户登录', 'admin', '2018-08-26 14:04:59', 'false', '用户名或密码错误!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('128', '用户登录', 'admin', '2018-08-26 14:06:45', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('129', '用户登录', 'admin', '2018-08-26 14:13:42', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('130', '用户登录', 'admin', '2018-08-26 14:20:08', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('131', '用户登录', 'admin', '2018-08-26 14:34:04', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('132', '用户登录', 'admin', '2018-08-26 14:37:46', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('133', '用户登录', 'admin', '2018-08-26 14:40:17', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('134', '用户登录', 'admin', '2018-08-26 14:44:55', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('135', '用户登录', 'admin', '2018-08-26 14:47:48', 'true', '用户登录成功!', '192.168.56.1');
INSERT INTO `sys_login_log` VALUES ('136', '用户登录', 'admin', '2018-08-26 14:49:31', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('137', '用户登录', 'admin', '2018-08-28 19:20:16', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('138', '用户登录', 'admin', '2018-08-28 19:58:39', 'false', '用户已在线!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('139', '用户登录', 'admin', '2018-08-28 19:58:45', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('140', '用户登录', 'admin', '2018-08-28 20:04:58', 'false', '用户已在线!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('141', '用户登录', 'admin', '2018-08-28 20:05:03', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('142', '用户登录', 'admin', '2018-08-28 20:06:04', 'false', '用户已在线!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('143', '用户登录', 'admin', '2018-08-28 20:06:10', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('144', '用户登录', 'admin', '2018-08-28 20:12:35', 'false', '用户已在线!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('145', '用户登录', 'admin', '2018-08-28 20:12:41', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('146', '用户登录', 'admin', '2018-08-28 20:13:51', 'false', '用户已在线!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('147', '用户登录', 'admin', '2018-08-28 20:13:57', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('148', '用户登录', 'admin', '2018-08-28 20:17:14', 'false', '用户已在线!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('149', '用户登录', 'admin', '2018-08-28 20:17:19', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('150', '用户登录', 'admin', '2018-08-28 20:18:23', 'false', '用户已在线!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('151', '用户登录', 'admin', '2018-08-28 20:18:28', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('152', '用户登录', 'admin', '2018-08-28 20:35:06', 'false', '用户已在线!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('153', '用户登录', 'admin', '2018-08-28 20:35:12', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('154', '用户登录', 'admin', '2018-08-28 20:39:02', 'false', '用户已在线!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('155', '用户登录', 'admin', '2018-08-28 20:39:06', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('156', '用户登录', 'admin', '2018-08-28 20:42:13', 'false', '用户已在线!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('157', '用户登录', 'admin', '2018-08-28 20:42:18', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('158', '用户登录', 'admin', '2018-08-28 20:58:44', 'false', '用户已在线!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('159', '用户登录', 'admin', '2018-08-28 20:58:57', 'true', '用户登录成功!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('160', '用户登录', 'admin', '2018-08-28 21:04:04', 'false', '用户已在线!', '127.0.0.1');
INSERT INTO `sys_login_log` VALUES ('161', '用户登录', 'admin', '2018-08-28 21:04:10', 'true', '用户登录成功!', '127.0.0.1');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(50) NOT NULL COMMENT '菜单编号',
  `pcode` varchar(50) DEFAULT NULL COMMENT '菜单父编号',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(100) DEFAULT NULL COMMENT 'url地址',
  `num` int(10) NOT NULL COMMENT '菜单排序号',
  `levels` int(10) DEFAULT NULL COMMENT '菜单层级',
  `is_menu` int(1) NOT NULL DEFAULT '0' COMMENT '是否是菜单（1：是  0：不是）',
  `tips` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_open` int(1) NOT NULL DEFAULT '0' COMMENT '是否打开:    1:打开   0:不打开',
  `dis_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标记：0：有效1：无效',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `update_date` datetime NOT NULL COMMENT '更新日期',
  `create_user` varchar(20) NOT NULL COMMENT '创建者',
  `update_user` varchar(20) NOT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', 'sys_manager', '0', '系统管理', null, null, '1', null, '1', null, '1', '0', '2018-08-26 09:52:48', '2018-08-26 09:52:52', 'admin', 'admin');
INSERT INTO `sys_menu` VALUES ('2', 'sys_user', 'sys_manager', '用户管理', null, '/systemUserController/userList', '1', null, '1', null, '0', '0', '2018-08-26 09:54:52', '2018-08-26 09:54:54', 'admin', 'admin');
INSERT INTO `sys_menu` VALUES ('3', 'sys_notice', '0', '通知管理', null, null, '2', null, '1', null, '1', '0', '2018-08-26 09:56:06', '2018-08-26 09:56:08', 'admin', 'admin');
INSERT INTO `sys_menu` VALUES ('4', 'sys_system', 'sys_notice', '系统通知管理', null, '/systemUserController/userList', '1', null, '1', null, '0', '0', '2018-08-26 09:57:06', '2018-08-26 09:57:09', 'admin', 'admin');
INSERT INTO `sys_menu` VALUES ('5', 'sys_menu', 'sys_manager', '菜单管理', null, '/systemMenuController/menuList', '2', null, '1', null, '0', '0', '2018-08-28 20:02:55', '2018-08-28 20:02:57', 'admin', 'admin');

-- ----------------------------
-- Table structure for `sys_menu_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_role`;
CREATE TABLE `sys_menu_role` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `menu_id` int(10) NOT NULL COMMENT '菜单id',
  `role_id` int(10) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='菜单角色关联表';

-- ----------------------------
-- Records of sys_menu_role
-- ----------------------------
INSERT INTO `sys_menu_role` VALUES ('1', '1', '1');
INSERT INTO `sys_menu_role` VALUES ('2', '2', '1');
INSERT INTO `sys_menu_role` VALUES ('3', '3', '1');
INSERT INTO `sys_menu_role` VALUES ('4', '4', '1');
INSERT INTO `sys_menu_role` VALUES ('5', '5', '1');

-- ----------------------------
-- Table structure for `sys_operation_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_type` varchar(50) NOT NULL COMMENT '日志类型',
  `log_name` varchar(80) NOT NULL COMMENT '日志名称',
  `create_user` varchar(20) NOT NULL COMMENT '用户登录名',
  `class_name` varchar(80) NOT NULL COMMENT '类名称',
  `method` int(10) NOT NULL COMMENT '方法名称',
  `succeed` varchar(50) NOT NULL COMMENT '是否成功',
  `message` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(10) NOT NULL COMMENT '序号',
  `code` varchar(20) NOT NULL COMMENT '父角色id',
  `name` varchar(25) NOT NULL COMMENT '角色名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `temp` int(10) DEFAULT NULL COMMENT '保留字段',
  `dis_flag` int(1) NOT NULL DEFAULT '0' COMMENT '删除标记：0：有效1：无效',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `update_date` datetime NOT NULL COMMENT '更新日期',
  `create_user` varchar(20) NOT NULL COMMENT '创建者',
  `update_user` varchar(20) NOT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '1', 'administrator', '超级管理员', null, null, '0', '2018-08-26 11:15:16', '2018-08-26 11:15:18', 'admin', 'admin');
INSERT INTO `sys_role` VALUES ('2', '2', 'manager', '管理员', null, null, '0', '2018-08-26 11:19:32', '2018-08-26 11:19:36', 'admin', 'admin');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_name` varchar(20) NOT NULL COMMENT '登录名',
  `REAL_NAME` varchar(50) NOT NULL COMMENT '用户名',
  `avatar` varchar(50) DEFAULT NULL COMMENT '头像',
  `sex` int(1) NOT NULL COMMENT '性别（1：男 2：女）',
  `PASSWORD` varchar(50) NOT NULL COMMENT '密码',
  `SALT` varchar(50) NOT NULL COMMENT '盐',
  `birthday` datetime NOT NULL COMMENT '生日',
  `is_online` int(1) NOT NULL DEFAULT '0' COMMENT '是否在线：0：不在线1：在线',
  `role_id` varchar(20) DEFAULT NULL COMMENT '角色id',
  `dept_id` int(10) DEFAULT NULL COMMENT '部门id',
  `dis_flag` int(1) NOT NULL DEFAULT '0' COMMENT '状态(0：启用 1：删除 2：冻结  ）',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `create_user` varchar(20) NOT NULL COMMENT '创建者',
  `update_user` varchar(20) NOT NULL COMMENT '更新者',
  PRIMARY KEY (`id`,`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '管理员', null, '1', 'ecfadcde9305f8891bcfe5a1e28c253e', '8pgby', '2018-08-23 19:28:47', '1', '1,2', '1', '0', '2018-08-23 19:29:05', '2018-08-28 21:04:10', 'admin', 'admin');
INSERT INTO `sys_user` VALUES ('3', 'admin22', '11', null, '111', '111', '111', '2018-08-26 13:55:22', '0', '1', '1', '0', '2018-08-26 13:55:26', '2018-08-26 13:55:28', 'admin', 'admin');
