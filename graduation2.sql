/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50623
 Source Host           : localhost:3306
 Source Schema         : graduation

 Target Server Type    : MySQL
 Target Server Version : 50623
 File Encoding         : 65001

 Date: 16/12/2020 23:32:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NULL DEFAULT NULL,
  `quantity` int(11) NULL DEFAULT NULL,
  `cost` float(11, 0) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `productId`(`product_id`) USING BTREE,
  INDEX `userId`(`user_id`) USING BTREE,
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 186 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (185, 1, 16, 1280, 2, '2020-12-16 15:30:55', '2020-12-16 15:30:55');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` int(11) NOT NULL COMMENT '订单主键',
  `product_id` int(11) NOT NULL COMMENT '商品主键',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `cost` float NOT NULL COMMENT '消费',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `PK__EASYBUY___66E1BD8E2F10007B`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户主键',
  `login_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户地址',
  `cost` float NULL DEFAULT NULL COMMENT '总金额',
  `serialnumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 133 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `description` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `price` float NOT NULL COMMENT '价格',
  `stock` int(11) NOT NULL COMMENT '库存',
  `categorylevelone_id` int(11) NULL DEFAULT NULL COMMENT '分类1',
  `categoryleveltwo_id` int(11) NULL DEFAULT NULL COMMENT '分类2',
  `file_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `PK__EASYBUY___94F6E55132E0915F`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 803 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, '高等数学同济大学第七版上下册教材课本', '大一高数学习指导考研', 80, 20, 1, 5, 'math.png');
INSERT INTO `product` VALUES (2, '零基础C++从入门到精通', '计算机程序开发数据结构基础教程书籍', 59, 46, 1, 5, 'c.png');
INSERT INTO `product` VALUES (3, '全新版大学英语第二版', '英语课本教材配套学习手册', 13, 46, 1, 6, 'English.png');
INSERT INTO `product` VALUES (4, '计算机网络 谢希仁 第七版', '指定计算机教材', 40, 50, 1, 6, 'Interney.png');
INSERT INTO `product` VALUES (5, 'java面向对象程序设计 第3版', 'JAVA语言程序设计第三版', 77, 50, 1, 7, 'javaBook.png');
INSERT INTO `product` VALUES (6, '数据库系统概论王珊', '高校经典教材考研同步辅书', 17, 50, 1, 7, 'sqlBook.png');
INSERT INTO `product` VALUES (7, 'Java EE企业应用实战（第5版）', ' J2EE 实战操作书籍', 70, 50, 1, 8, 'j2eeBook.png');
INSERT INTO `product` VALUES (8, '分布式消息中间件实践', 'Kafka实践应用教程教材书籍', 55, 50, 1, 8, 'kafkaBook.png');
INSERT INTO `product` VALUES (9, '4册全套四大名著原著', '红楼梦西游记水浒传三国演义白话', 40, 50, 2, 9, 'ChineseNovel.png');
INSERT INTO `product` VALUES (10, '爱的教育亚米契斯原著', '书籍世界名著', 13, 50, 2, 10, 'foreignNovel.png');
INSERT INTO `product` VALUES (11, '傲慢与偏见中英文对照版', ' 世界名著文学小说英语读物', 22, 50, 2, 10, 'foreignNovel2.png');
INSERT INTO `product` VALUES (12, '研外语四级考试英语真题备考', '2020年12月历年试卷全套资料大学cet4级词汇阅读理解听力翻译', 39, 50, 3, 11, 'fourTest.png');
INSERT INTO `product` VALUES (13, '英语四级考试真题', '阅读听力专项训练全套', 36, 50, 3, 11, 'fourTest2.png');
INSERT INTO `product` VALUES (14, '黄皮书六级真题', '18套真题+6套模拟', 25, 50, 3, 11, 'sixTest.png');
INSERT INTO `product` VALUES (15, '2020年大学英语六级考试用书', '词汇狂记10年真题高频词汇2本套 ', 24, 50, 3, 11, 'sixTest2.png');
INSERT INTO `product` VALUES (16, '中公初中数学教师证资格证考试用书', '2021年教资考试资料中学教材综合素质教育知识与能力真题试卷教师资格证考试书', 152, 50, 3, 12, 'teacherTest.png');
INSERT INTO `product` VALUES (17, '中公教育国家教师证资格证考试用书', '2021年小学语文数学英语下半年笔试教材', 99, 50, 3, 12, 'teacherTest2.png');
INSERT INTO `product` VALUES (18, '晨光文具答题卡涂卡2B自动铅笔', '快速填卡笔', 10, 50, 4, 16, '2bPencil.png');
INSERT INTO `product` VALUES (19, '艾本C201大学四级听力耳机英语', '四六级考试专用FM调频耳机46级', 33, 50, 4, 14, 'earPhone.png');
INSERT INTO `product` VALUES (20, '语梵F1 英语四六级听力耳机四级', 'FM调频蓝牙耳麦专业头戴式收音机', 40, 50, 4, 14, 'earPhone2.png');
INSERT INTO `product` VALUES (21, '晨光A4纸打印复印纸', '四纸纸品打印机纸草稿纸办公用品', 17, 50, 4, 14, 'paper.png');

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `parent_id` int(11) NOT NULL COMMENT '父级目录id',
  `type` int(11) NULL DEFAULT NULL COMMENT '级别(1:一级 2：二级 3：三级)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `PK__EASYBUY___9EC2A4E236B12243`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 701 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES (1, '大学教材', 0, 1);
INSERT INTO `product_category` VALUES (2, '名著', 0, 1);
INSERT INTO `product_category` VALUES (3, '考证必备', 0, 1);
INSERT INTO `product_category` VALUES (4, '考试用具', 0, 1);
INSERT INTO `product_category` VALUES (5, '大一', 1, 2);
INSERT INTO `product_category` VALUES (6, '大二', 1, 2);
INSERT INTO `product_category` VALUES (7, '大三', 1, 2);
INSERT INTO `product_category` VALUES (8, '大四', 1, 2);
INSERT INTO `product_category` VALUES (9, '国内名著', 2, 2);
INSERT INTO `product_category` VALUES (10, '国外名著', 2, 2);
INSERT INTO `product_category` VALUES (11, '四六级', 3, 2);
INSERT INTO `product_category` VALUES (12, '教师资格证', 3, 2);
INSERT INTO `product_category` VALUES (13, '注册会计师', 3, 2);
INSERT INTO `product_category` VALUES (14, '四六级耳机', 4, 2);
INSERT INTO `product_category` VALUES (15, '纸张', 4, 2);
INSERT INTO `product_category` VALUES (16, '文具', 4, 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `gender` int(11) NOT NULL DEFAULT 1 COMMENT '性别(1:男 0：女)',
  `identity_code` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `email` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `PK__EASYBUY___C96109CC3A81B327`(`login_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'admin', '系统管理员', '123', 0, '130406198302141869', 'hello11@bdqn.com', '1583233515', '7.jpg', '2020-05-18 06:22:27', '2020-05-18 06:22:32');
INSERT INTO `user` VALUES (10, 'cgn', '程广宁', '123', 1, '140225189987854589', '1044732267@qq.com', '13366055011', '2.jpg', '2020-05-18 06:22:34', '2020-05-18 06:22:37');
INSERT INTO `user` VALUES (11, 'hyl', '忽悠了', '123', 0, '12312', '12312', '1231', '9.jpg', '2020-05-18 06:22:35', '2020-12-15 08:17:59');
INSERT INTO `user` VALUES (12, 'ck', '陈康', '123', 1, '140225189987854589', '1044732267@qq.com', '13366055010', '4.jpg', '2020-05-17 22:22:36', '2020-12-16 04:25:46');
INSERT INTO `user` VALUES (31, 'ff', '丰富', '123', 1, '1231', '1231', '12311', '10.jpg', '2020-12-14 05:51:19', '2020-12-16 15:13:01');
INSERT INTO `user` VALUES (32, 'gf', '规范', '123', 0, '440623141241', '12913362@qq.com', '123123123', NULL, '2020-12-16 15:26:55', '2020-12-16 15:26:55');

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户主键',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_address
-- ----------------------------
INSERT INTO `user_address` VALUES (49, 31, '谢边村', '2', '2020-12-16 15:21:49', '2020-12-16 15:22:44');

SET FOREIGN_KEY_CHECKS = 1;
