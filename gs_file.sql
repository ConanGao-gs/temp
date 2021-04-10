/*
Navicat MySQL Data Transfer

Source Server         : mysql1
Source Server Version : 80021
Source Host           : localhost:3306
Source Database       : test9

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2021-04-10 16:54:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gs_file
-- ----------------------------
DROP TABLE IF EXISTS `gs_file`;
CREATE TABLE `gs_file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_size` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `file_type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `file_original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `createdon` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `file_save_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `file_uuid` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
