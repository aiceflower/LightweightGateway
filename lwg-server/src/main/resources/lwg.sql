/*
 Navicat Premium Data Transfer

 Source Server         : 39.96.89.236
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 39.96.89.236
 Source Database       : lwg

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : utf-8

 Date: 05/27/2021 17:39:52 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `ht`
-- ----------------------------
DROP TABLE IF EXISTS `ht`;
CREATE TABLE `ht` (
                      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
                      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                      `site` varchar(64) DEFAULT NULL COMMENT '位置',
                      `temperature` float(3,1) DEFAULT NULL COMMENT '温度',
  `humidity` float(3,1) DEFAULT NULL COMMENT '湿度',
  `gateway_id` varchar(64) DEFAULT NULL COMMENT '网关id',
  `device_id` varchar(64) DEFAULT NULL COMMENT '设备id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ht_create_time_index` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17750 DEFAULT CHARSET=utf8 COMMENT='温湿度表';

SET FOREIGN_KEY_CHECKS = 1;
