/*
 Navicat Premium Data Transfer

 Source Server         : 系统
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : lithotomeshop

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for appuser
-- ----------------------------
DROP TABLE IF EXISTS `appuser`;
CREATE TABLE `appuser`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `Password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `Email` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `RoleType` int(11) NULL DEFAULT NULL COMMENT '角色',
  `PhoneNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
  `ImageUrls` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `Birth` datetime(0) NULL DEFAULT NULL COMMENT '出生年月',
  `Name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `UserName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appuser
-- ----------------------------
INSERT INTO `appuser` VALUES (1, '2025-03-26 20:41:56', 1, 'admin', NULL, 1, NULL, NULL, NULL, 'admin', 'admin');
INSERT INTO `appuser` VALUES (2, '2025-03-26 20:43:37', 0, 'tianranshicai', '806105498@qq.com', 3, '15616262411', NULL, NULL, '天然石材', 'tianranshicai');
INSERT INTO `appuser` VALUES (3, '2025-03-26 22:01:13', 0, 'bingbing', '632987987@qq.com', 2, '15616262523', 'http://localhost:7245/1743222950126/1.jpg', '2025-03-03 00:00:00', '冰冰', 'bingbing');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `ArticleTypeId` int(11) NULL DEFAULT NULL COMMENT '文章分类',
  `ReadCount` int(11) NULL DEFAULT NULL COMMENT '阅读数',
  `IsTop` tinyint(1) NULL DEFAULT NULL COMMENT '是否置顶',
  `Title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `Cover` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `ArticleTypeId`(`ArticleTypeId`) USING BTREE,
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`ArticleTypeId`) REFERENCES `articletype` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (3, '2025-03-27 09:30:00', 1, 3, 1560, 1, '石材养护新技术：纳米防护处理', '解读2025年最新的石材渗透型防护剂施工工艺及效果对比...', 'http://localhost:7245/1743224018108/1.jpg');
INSERT INTO `article` VALUES (4, '2025-03-27 09:45:00', 1, 4, 870, 0, '别墅装修石材搭配方案', '推荐高档住宅中大理石与花岗岩的经典搭配案例...', 'http://localhost:7245/1743224012523/11.jpg');
INSERT INTO `article` VALUES (5, '2025-03-27 10:00:00', 1, 5, 1120, 1, 'GB/T 2025工程石材验收标准', '最新版国家工程用石材质量验收规范要点解读...', 'http://localhost:7245/1743224005340/5.jpg');
INSERT INTO `article` VALUES (6, '2025-03-27 10:15:00', 1, 6, 1430, 0, '2025国际石材设计趋势报告', '米兰设计周展出的石材创新应用技术与艺术表现手法...', 'http://localhost:7245/1743223996835/1.jpg');
INSERT INTO `article` VALUES (7, '2025-03-27 10:30:00', 1, 1, 760, 0, '进口大理石选购避坑指南', '如何辨别真假意大利卡拉拉白大理石的专业方法...', 'http://localhost:7245/1743223990853/0.jpg');
INSERT INTO `article` VALUES (8, '2025-03-27 10:45:00', 1, 2, 920, 0, '花岗岩放射性检测标准', 'A类装修用花岗岩的放射性指标控制要点...', 'http://localhost:7245/1743223985240/1.jpg');
INSERT INTO `article` VALUES (9, '2025-03-27 11:00:00', 1, 3, 680, 0, '石英石台面日常保养六要素', '预防渗色、划痕的日常维护技巧及注意事项...', 'http://localhost:7245/1743223978600/6.jpg');
INSERT INTO `article` VALUES (10, '2025-03-27 11:15:00', 1, 4, 1050, 0, '酒店大堂石材装饰案例集', '五星级酒店石材装饰工程实景分析与材料选择...', 'http://localhost:7245/1743223970617/10.jpg');

-- ----------------------------
-- Table structure for articletype
-- ----------------------------
DROP TABLE IF EXISTS `articletype`;
CREATE TABLE `articletype`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章类型主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `Sort` int(11) NULL DEFAULT NULL COMMENT '顺序',
  `Name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文章类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of articletype
-- ----------------------------
INSERT INTO `articletype` VALUES (1, '2025-03-26 08:30:45', 1, 10, '大理石分类');
INSERT INTO `articletype` VALUES (2, '2025-03-26 09:15:22', 1, 20, '花岗岩介绍');
INSERT INTO `articletype` VALUES (3, '2025-03-26 10:05:17', 1, 30, '石材保养技巧');
INSERT INTO `articletype` VALUES (4, '2025-03-26 11:40:50', 1, 40, '家装石材推荐');
INSERT INTO `articletype` VALUES (5, '2025-03-26 13:25:30', 1, 50, '工程用石标准');
INSERT INTO `articletype` VALUES (6, '2025-03-26 14:10:05', 1, 60, '石材设计趋势');

-- ----------------------------
-- Table structure for buycard
-- ----------------------------
DROP TABLE IF EXISTS `buycard`;
CREATE TABLE `buycard`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `Qty` int(11) NULL DEFAULT NULL COMMENT '数量',
  `ShopId` int(11) NULL DEFAULT NULL COMMENT '店铺',
  `GoodId` int(11) NULL DEFAULT NULL COMMENT '石材',
  `BelongUserId` int(11) NULL DEFAULT NULL COMMENT '所属用户',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `ShopId`(`ShopId`) USING BTREE,
  INDEX `BelongUserId`(`BelongUserId`) USING BTREE,
  INDEX `GoodId`(`GoodId`) USING BTREE,
  CONSTRAINT `buycard_ibfk_1` FOREIGN KEY (`ShopId`) REFERENCES `shop` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `buycard_ibfk_2` FOREIGN KEY (`BelongUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `buycard_ibfk_3` FOREIGN KEY (`GoodId`) REFERENCES `good` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '购物车表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '优惠卷主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `Title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `ShopId` int(11) NULL DEFAULT NULL COMMENT '店铺',
  `EndActiveTime` datetime(0) NULL DEFAULT NULL COMMENT '截至有效时间',
  `UseMoney` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '使用限制',
  `Money` double(20, 5) NULL DEFAULT NULL COMMENT '面值',
  `Qty` int(11) NULL DEFAULT NULL COMMENT '总数量',
  `BeginActiveTime` datetime(0) NULL DEFAULT NULL COMMENT '起始有效时间',
  `GetQty` int(11) NULL DEFAULT NULL COMMENT '领取数量',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `ShopId`(`ShopId`) USING BTREE,
  CONSTRAINT `coupon_ibfk_1` FOREIGN KEY (`ShopId`) REFERENCES `shop` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '优惠卷表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon
-- ----------------------------
INSERT INTO `coupon` VALUES (1, '2025-03-26 10:00:00', 1, '春季石材采购满减券', 1, '2025-04-30 23:59:59', '5000.00000', 300.00000, 100, '2025-03-26 00:00:00', 42);
INSERT INTO `coupon` VALUES (2, '2025-03-26 10:05:00', 1, '新客户专享立减券', 1, '2025-05-15 23:59:59', '2000.00000', 200.00000, 200, '2025-03-26 00:00:00', 87);
INSERT INTO `coupon` VALUES (3, '2025-03-26 10:10:00', 1, '大理石品类专用券', 1, '2025-06-30 23:59:59', '3000.00000', 250.00000, 150, '2025-03-26 00:00:00', 65);
INSERT INTO `coupon` VALUES (4, '2025-03-26 10:15:00', 1, '工程订单大额优惠', 1, '2025-12-31 23:59:59', '20000.00000', 1500.00000, 50, '2025-03-26 00:00:00', 18);
INSERT INTO `coupon` VALUES (5, '2025-03-26 10:20:00', 1, '周年庆全场通用券', 1, '2025-05-31 23:59:59', '10000.00000', 800.00000, 80, '2025-03-26 00:00:00', 53);
INSERT INTO `coupon` VALUES (6, '2025-03-26 10:25:00', 1, '花岗岩特惠折扣券', 1, '2025-04-15 23:59:59', '4000.00000', 350.00000, 120, '2025-03-26 00:00:00', 76);
INSERT INTO `coupon` VALUES (7, '2025-03-26 10:30:00', 1, '进口石材专享优惠', 1, '2025-07-31 23:59:59', '8000.00000', 600.00000, 60, '2025-03-26 00:00:00', 29);
INSERT INTO `coupon` VALUES (8, '2025-03-26 10:35:00', 1, '石材保养套餐券', 1, '2025-05-20 23:59:59', '1500.00000', 100.00000, 300, '2025-03-26 00:00:00', 134);
INSERT INTO `coupon` VALUES (9, '2025-03-26 10:40:00', 1, '季度促销满减券', 1, '2025-06-30 23:59:59', '6000.00000', 500.00000, 90, '2025-03-26 00:00:00', 47);

-- ----------------------------
-- Table structure for couponrecord
-- ----------------------------
DROP TABLE IF EXISTS `couponrecord`;
CREATE TABLE `couponrecord`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '优惠卷记录主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `RelativeNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '关联单号',
  `UseTime` datetime(0) NULL DEFAULT NULL COMMENT '使用时间',
  `GetUserId` int(11) NULL DEFAULT NULL COMMENT '领取人',
  `ShopId` int(11) NULL DEFAULT NULL COMMENT '店铺',
  `EndActiveTime` datetime(0) NULL DEFAULT NULL COMMENT '截至有效时间',
  `CouponId` int(11) NULL DEFAULT NULL COMMENT '优惠卷',
  `BeginActiveTime` datetime(0) NULL DEFAULT NULL COMMENT '起始有效时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `GetUserId`(`GetUserId`) USING BTREE,
  INDEX `CouponId`(`CouponId`) USING BTREE,
  INDEX `ShopId`(`ShopId`) USING BTREE,
  CONSTRAINT `couponrecord_ibfk_1` FOREIGN KEY (`GetUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `couponrecord_ibfk_2` FOREIGN KEY (`CouponId`) REFERENCES `coupon` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `couponrecord_ibfk_3` FOREIGN KEY (`ShopId`) REFERENCES `shop` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '优惠卷记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for good
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '石材主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `ShopId` int(11) NULL DEFAULT NULL COMMENT '所属店铺',
  `Name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `GoodTypeId` int(11) NULL DEFAULT NULL COMMENT '分类',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '详情内容',
  `Price` double(20, 5) NULL DEFAULT NULL COMMENT '价格',
  `Cover` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  `ShopGoodTypeId` int(11) NULL DEFAULT NULL COMMENT '石材店铺分类',
  `Stock` double(20, 5) NULL DEFAULT NULL COMMENT '库存',
  `ReplenishmentLimit` int(11) NULL DEFAULT NULL COMMENT '库存下限',
  `Unit` varchar(54) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '单位',
  `ImageUrls` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主图',
  `UserId` int(11) NULL DEFAULT NULL COMMENT '所属用户',
  `IsOnSale` tinyint(11) NULL DEFAULT NULL COMMENT '是否出售',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `ShopId`(`ShopId`) USING BTREE,
  INDEX `GoodTypeId`(`GoodTypeId`) USING BTREE,
  INDEX `UserId`(`UserId`) USING BTREE,
  CONSTRAINT `good_ibfk_1` FOREIGN KEY (`ShopId`) REFERENCES `shop` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `good_ibfk_2` FOREIGN KEY (`GoodTypeId`) REFERENCES `goodtype` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `good_ibfk_3` FOREIGN KEY (`UserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '石材表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of good
-- ----------------------------
INSERT INTO `good` VALUES (2, '2025-03-26 09:16:45', 1, 1, '印度黑金沙花岗岩', 2, '黑色基底带金色矿物斑点，适合地板和室外工程', 320.00000, 'http://localhost:7245/1742997239821/16.jpg', 11, 300.00000, 30, '平方米', 'http://localhost:7245/1742997244058/7 - 副本.jpg,http://localhost:7245/1742997244060/13 - 副本.jpg,http://localhost:7245/1742997244060/16 - 副本.jpg', 2, 1);
INSERT INTO `good` VALUES (3, '2025-03-26 09:17:30', 1, 1, '法国石灰石壁炉石', 3, '米黄色天然石灰石，专为壁炉设计', 420.00000, 'http://localhost:7245/1742997226958/17.jpg', 11, 80.00000, 10, '块', 'http://localhost:7245/1742997231629/13.jpg,http://localhost:7245/1742997231630/14.jpg,http://localhost:7245/1742997231630/15.jpg,http://localhost:7245/1742997231631/17.jpg', 2, 1);
INSERT INTO `good` VALUES (4, '2025-03-26 09:18:12', 1, 1, '青石板庭院铺路石', 4, '自然面处理，防滑性能优异', 95.00000, 'http://localhost:7245/1742997216620/12.jpg', 11, 500.00000, 50, '平方米', 'http://localhost:7245/1742997220261/0.jpg,http://localhost:7245/1742997220264/1.jpg,http://localhost:7245/1742997220264/2.jpg,http://localhost:7245/1742997220264/12.jpg', 2, 1);
INSERT INTO `good` VALUES (5, '2025-03-26 09:19:05', 1, 1, '黄砂岩文化石', 5, '仿古面处理，适用于外墙装饰', 75.00000, 'http://localhost:7245/1742997208777/19.jpg', 11, 200.00000, 25, '平方米', 'http://localhost:7245/1742997205922/15.jpg,http://localhost:7245/1742997205924/16.jpg,http://localhost:7245/1742997205925/17.jpg,http://localhost:7245/1742997205925/18.jpg,http://localhost:7245/1742997205926/19.jpg', 2, 1);
INSERT INTO `good` VALUES (9, '2025-03-26 09:23:29', 1, 1, '挪威蓝珍珠花岗岩', 9, '进口稀有品种，蓝色基底带银色斑点', 1200.00000, 'http://localhost:7245/1742997162438/1.jpg', 11, 40.00000, 5, '平方米', 'http://localhost:7245/1742997166415/2.jpg,http://localhost:7245/1742997166416/3.jpg,http://localhost:7245/1742997166419/4.jpg,http://localhost:7245/1742997166417/5.jpg', 2, 1);
INSERT INTO `good` VALUES (10, '2025-03-26 09:24:56', 1, 1, '3D雕刻大理石屏风', 10, '手工雕刻中式图案，可定制设计', 3500.00000, 'http://localhost:7245/1742997092180/0.jpg', 11, 15.00000, 2, '件', 'http://localhost:7245/1742997117325/3.jpg,http://localhost:7245/1742997117325/4.jpg,http://localhost:7245/1742997117328/5.jpg,http://localhost:7245/1742997117326/6.jpg,http://localhost:7245/1742997117327/7.jpg,http://localhost:7245/1742997117327/8.jpg', 2, 1);
INSERT INTO `good` VALUES (11, '2025-03-26 09:25:42', 1, 1, '西班牙米黄大理石', 1, '暖色调，适用于酒店大堂地面', 580.00000, 'http://localhost:7245/1742997082335/1.jpg', 11, 120.00000, 15, '平方米', 'http://localhost:7245/1742997085132/1.jpg,http://localhost:7245/1742997085132/3.jpg,http://localhost:7245/1742997085132/4.jpg', 2, 1);
INSERT INTO `good` VALUES (12, '2025-03-26 09:26:35', 1, 1, '中国红花岗岩台阶石', 2, '枣红色，抗压强度高', 280.00000, 'http://localhost:7245/1742997064839/12.jpg', 11, 180.00000, 20, '平方米', 'http://localhost:7245/1742997068039/12.jpg,http://localhost:7245/1742997068040/13.jpg,http://localhost:7245/1742997068040/14.jpg', 2, 1);
INSERT INTO `good` VALUES (13, '2025-03-26 09:27:18', 1, 1, '葡萄牙石灰石外墙砖', 3, '仿古面处理，规格30×60cm', 65.00000, 'http://localhost:7245/1742997053289/5.jpg', 11, 400.00000, 40, '平方米', 'http://localhost:7245/1742997058047/5.jpg,http://localhost:7245/1742997058051/10.jpg,http://localhost:7245/1742997058051/11.jpg,http://localhost:7245/1742997058052/13.jpg,http://localhost:7245/1742997058051/14.jpg,http://localhost:7245/1742997058052/15.jpg', 2, 1);
INSERT INTO `good` VALUES (14, '2025-03-26 09:28:05', 1, 1, '板岩马赛克拼花', 4, '天然色彩拼接，浴室专用', 120.00000, 'http://localhost:7245/1742997044103/0.jpg', 11, 90.00000, 12, '平方米', 'http://localhost:7245/1742997047006/0.jpg,http://localhost:7245/1742997047009/1.jpg,http://localhost:7245/1742997047010/2.jpg,http://localhost:7245/1742997047009/3.jpg,http://localhost:7245/1742997047010/5.jpg,http://localhost:7245/1742997047010/6.jpg,http://localhost:7245/1742997047017/7.jpg', 2, 1);
INSERT INTO `good` VALUES (15, '2025-03-26 09:29:30', 1, 1, '澳洲砂岩雕塑基座', 5, '定制尺寸，适合户外雕塑安装', 420.00000, 'http://localhost:7245/1742997034071/0.jpg', 11, 24.00000, 3, '个', 'http://localhost:7245/1742997038321/0.jpg,http://localhost:7245/1742997038324/2.jpg,http://localhost:7245/1742997038323/3.jpg,http://localhost:7245/1742997038324/4.jpg,http://localhost:7245/1742997038326/8.jpg,http://localhost:7245/1742997038325/9.jpg', 2, 1);
INSERT INTO `good` VALUES (16, '2025-03-26 09:30:15', 1, 1, '石英石实验室台面', 6, '耐酸碱，厚度2cm', 720.00000, 'http://localhost:7245/1742997024550/3.jpg', 11, 67.00000, 10, '平方米', 'http://localhost:7245/1742997027065/2.jpg,http://localhost:7245/1742997027066/3.jpg,http://localhost:7245/1742997027068/4.jpg,http://localhost:7245/1742997027072/5.jpg,http://localhost:7245/1742997027073/6.jpg', 2, 1);
INSERT INTO `good` VALUES (17, '2025-03-26 09:31:40', 1, 1, '和田玉装饰板材', 7, '新疆和田玉，厚度1cm', 3800.00000, 'http://localhost:7245/1742997015615/4.jpg', 11, 12.00000, 2, '块', 'http://localhost:7245/1742997018381/0.jpg,http://localhost:7245/1742997018387/1.jpg,http://localhost:7245/1742997018385/2.jpg,http://localhost:7245/1742997018386/3.jpg,http://localhost:7245/1742997018386/4.jpg,http://localhost:7245/1742997018387/5.jpg', 2, 1);
INSERT INTO `good` VALUES (18, '2025-03-26 09:32:25', 1, 1, '人造石窗台板', 8, '无缝拼接，多种边缘处理', 150.00000, 'http://localhost:7245/1742996996141/8.jpg', 11, 110.00000, 15, '米', 'http://localhost:7245/1742997009737/1.jpg,http://localhost:7245/1742997009737/4.jpg,http://localhost:7245/1742997009738/6.jpg,http://localhost:7245/1742997009738/7.jpg', 2, 1);
INSERT INTO `good` VALUES (19, '2025-03-26 09:33:10', 1, 1, '土耳其帝国红大理石', 9, '深红色带白色纹理，豪华装饰用', 980.00000, 'http://localhost:7245/1742996981891/2.jpg', 11, 34.00000, 5, '平方米', 'http://localhost:7245/1742996985086/1.jpg,http://localhost:7245/1742996985085/2.jpg,http://localhost:7245/1742996985085/3.jpg,http://localhost:7245/1742996985088/4.jpg,http://localhost:7245/1742996985088/5.jpg,http://localhost:7245/1742996985089/6.jpg,http://localhost:7245/1742996985100/7.jpg', 2, 1);
INSERT INTO `good` VALUES (20, '2025-03-26 09:34:45', 1, 1, '大理石拼花地台', 10, '水刀切割拼花，可定制图案', 4200.00000, 'http://localhost:7245/1742996932604/4.jpg', 11, 22.00000, 1, '套', 'http://localhost:7245/1742996937378/0.jpg,http://localhost:7245/1742996937381/1.jpg,http://localhost:7245/1742996937384/2.jpg,http://localhost:7245/1742996937384/3.jpg,http://localhost:7245/1742996937384/4.jpg,http://localhost:7245/1742996937384/5.jpg,http://localhost:7245/1742996937397/6.jpg', 2, 1);

-- ----------------------------
-- Table structure for goodcollect
-- ----------------------------
DROP TABLE IF EXISTS `goodcollect`;
CREATE TABLE `goodcollect`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '石材收藏主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `GoodId` int(11) NULL DEFAULT NULL COMMENT '石材',
  `CollectUserId` int(11) NULL DEFAULT NULL COMMENT '收藏人',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `CollectUserId`(`CollectUserId`) USING BTREE,
  INDEX `GoodId`(`GoodId`) USING BTREE,
  CONSTRAINT `goodcollect_ibfk_1` FOREIGN KEY (`CollectUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `goodcollect_ibfk_2` FOREIGN KEY (`GoodId`) REFERENCES `good` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '石材收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goodcollect
-- ----------------------------
INSERT INTO `goodcollect` VALUES (1, '2025-03-26 22:59:48', 3, 19, 3);
INSERT INTO `goodcollect` VALUES (2, '2025-03-26 22:59:52', 3, 13, 3);
INSERT INTO `goodcollect` VALUES (3, '2025-03-29 12:25:35', 3, 20, 3);
INSERT INTO `goodcollect` VALUES (4, '2025-03-29 12:25:38', 3, 15, 3);
INSERT INTO `goodcollect` VALUES (5, '2025-03-29 12:25:40', 3, 17, 3);

-- ----------------------------
-- Table structure for goodprop
-- ----------------------------
DROP TABLE IF EXISTS `goodprop`;
CREATE TABLE `goodprop`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '石材属性主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `Value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '属性值',
  `GoodId` int(11) NULL DEFAULT NULL COMMENT '石材',
  `Name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '属性名称',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `GoodId`(`GoodId`) USING BTREE,
  CONSTRAINT `goodprop_ibfk_1` FOREIGN KEY (`GoodId`) REFERENCES `good` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '石材属性表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goodprop
-- ----------------------------
INSERT INTO `goodprop` VALUES (1, '2025-03-26 09:35:22', 1, '白色', NULL, '颜色');
INSERT INTO `goodprop` VALUES (2, '2025-03-26 09:35:23', 1, '2.5cm', NULL, '厚度');
INSERT INTO `goodprop` VALUES (3, '2025-03-26 09:35:24', 1, '意大利', NULL, '产地');
INSERT INTO `goodprop` VALUES (4, '2025-03-26 09:35:25', 1, 'A级', NULL, '等级');
INSERT INTO `goodprop` VALUES (5, '2025-03-26 09:35:26', 1, '抛光面', NULL, '表面处理');
INSERT INTO `goodprop` VALUES (6, '2025-03-26 09:36:10', 1, '黑色', 2, '颜色');
INSERT INTO `goodprop` VALUES (7, '2025-03-26 09:36:11', 1, '3cm', 2, '厚度');
INSERT INTO `goodprop` VALUES (8, '2025-03-26 09:36:12', 1, '印度', 2, '产地');
INSERT INTO `goodprop` VALUES (9, '2025-03-26 09:36:13', 1, '火烧面', 2, '表面处理');
INSERT INTO `goodprop` VALUES (10, '2025-03-26 09:36:14', 1, '280MPa', 2, '抗压强度');
INSERT INTO `goodprop` VALUES (11, '2025-03-26 09:37:05', 1, '米黄色', 3, '颜色');
INSERT INTO `goodprop` VALUES (12, '2025-03-26 09:37:06', 1, '5cm', 3, '厚度');
INSERT INTO `goodprop` VALUES (13, '2025-03-26 09:37:07', 1, '法国', 3, '产地');
INSERT INTO `goodprop` VALUES (14, '2025-03-26 09:37:08', 1, '仿古面', 3, '表面处理');
INSERT INTO `goodprop` VALUES (15, '2025-03-26 09:37:09', 1, '60×40cm', 3, '规格');
INSERT INTO `goodprop` VALUES (16, '2025-03-26 09:38:22', 1, '青灰色', 4, '颜色');
INSERT INTO `goodprop` VALUES (17, '2025-03-26 09:38:23', 1, '3-5cm', 4, '厚度');
INSERT INTO `goodprop` VALUES (18, '2025-03-26 09:38:24', 1, '中国', 4, '产地');
INSERT INTO `goodprop` VALUES (19, '2025-03-26 09:38:25', 1, '自然面', 4, '表面处理');
INSERT INTO `goodprop` VALUES (20, '2025-03-26 09:38:26', 1, '30×30cm', 4, '规格');
INSERT INTO `goodprop` VALUES (21, '2025-03-26 09:39:15', 1, '浅黄色', 5, '颜色');
INSERT INTO `goodprop` VALUES (22, '2025-03-26 09:39:16', 1, '2cm', 5, '厚度');
INSERT INTO `goodprop` VALUES (23, '2025-03-26 09:39:17', 1, '山东', 5, '产地');
INSERT INTO `goodprop` VALUES (24, '2025-03-26 09:39:18', 1, '荔枝面', 5, '表面处理');
INSERT INTO `goodprop` VALUES (25, '2025-03-26 09:39:19', 1, '10×30cm', 5, '规格');
INSERT INTO `goodprop` VALUES (26, '2025-03-26 09:40:30', 1, '白色', NULL, '底色');
INSERT INTO `goodprop` VALUES (27, '2025-03-26 09:40:31', 1, '2cm', NULL, '厚度');
INSERT INTO `goodprop` VALUES (28, '2025-03-26 09:40:32', 1, '巴西', NULL, '产地');
INSERT INTO `goodprop` VALUES (29, '2025-03-26 09:40:33', 1, '抛光面', NULL, '表面处理');
INSERT INTO `goodprop` VALUES (30, '2025-03-26 09:40:34', 1, '无辐射', NULL, '环保认证');
INSERT INTO `goodprop` VALUES (31, '2025-03-26 09:41:45', 1, '翠绿色', NULL, '颜色');
INSERT INTO `goodprop` VALUES (32, '2025-03-26 09:41:46', 1, '1cm', NULL, '厚度');
INSERT INTO `goodprop` VALUES (33, '2025-03-26 09:41:47', 1, '缅甸', NULL, '产地');
INSERT INTO `goodprop` VALUES (34, '2025-03-26 09:41:48', 1, '抛光面', NULL, '表面处理');
INSERT INTO `goodprop` VALUES (35, '2025-03-26 09:41:49', 1, '120×240cm', NULL, '规格');
INSERT INTO `goodprop` VALUES (36, '2025-03-26 09:42:50', 1, '白色', NULL, '颜色');
INSERT INTO `goodprop` VALUES (37, '2025-03-26 09:42:51', 1, '一体成型', NULL, '工艺');
INSERT INTO `goodprop` VALUES (38, '2025-03-26 09:42:52', 1, '亚克力', NULL, '材质');
INSERT INTO `goodprop` VALUES (39, '2025-03-26 09:42:53', 1, '50×40cm', NULL, '规格');
INSERT INTO `goodprop` VALUES (40, '2025-03-26 09:42:54', 1, '防污', NULL, '特性');
INSERT INTO `goodprop` VALUES (41, '2025-03-26 09:43:55', 1, '深蓝色', 9, '颜色');
INSERT INTO `goodprop` VALUES (42, '2025-03-26 09:43:56', 1, '3cm', 9, '厚度');
INSERT INTO `goodprop` VALUES (43, '2025-03-26 09:43:57', 1, '挪威', 9, '产地');
INSERT INTO `goodprop` VALUES (44, '2025-03-26 09:43:58', 1, '抛光面', 9, '表面处理');
INSERT INTO `goodprop` VALUES (45, '2025-03-26 09:43:59', 1, '稀有', 9, '等级');
INSERT INTO `goodprop` VALUES (46, '2025-03-26 09:44:40', 1, '米黄色', 10, '底色');
INSERT INTO `goodprop` VALUES (47, '2025-03-26 09:44:41', 1, '2cm', 10, '厚度');
INSERT INTO `goodprop` VALUES (48, '2025-03-26 09:44:42', 1, '手工雕刻', 10, '工艺');
INSERT INTO `goodprop` VALUES (49, '2025-03-26 09:44:43', 1, '180×90cm', 10, '规格');
INSERT INTO `goodprop` VALUES (50, '2025-03-26 09:44:44', 1, '中式', 10, '风格');
INSERT INTO `goodprop` VALUES (51, '2025-03-26 09:45:35', 1, '米黄色', 11, '颜色');
INSERT INTO `goodprop` VALUES (52, '2025-03-26 09:45:36', 1, '2cm', 11, '厚度');
INSERT INTO `goodprop` VALUES (53, '2025-03-26 09:45:37', 1, '西班牙', 11, '产地');
INSERT INTO `goodprop` VALUES (54, '2025-03-26 09:45:38', 1, '抛光面', 11, '表面处理');
INSERT INTO `goodprop` VALUES (55, '2025-03-26 09:45:39', 1, 'A级', 11, '等级');
INSERT INTO `goodprop` VALUES (56, '2025-03-26 09:46:20', 1, '枣红色', 12, '颜色');
INSERT INTO `goodprop` VALUES (57, '2025-03-26 09:46:21', 1, '3cm', 12, '厚度');
INSERT INTO `goodprop` VALUES (58, '2025-03-26 09:46:22', 1, '福建', 12, '产地');
INSERT INTO `goodprop` VALUES (59, '2025-03-26 09:46:23', 1, '荔枝面', 12, '表面处理');
INSERT INTO `goodprop` VALUES (60, '2025-03-26 09:46:24', 1, '防滑', 12, '特性');
INSERT INTO `goodprop` VALUES (61, '2025-03-26 09:47:15', 1, '浅灰色', 13, '颜色');
INSERT INTO `goodprop` VALUES (62, '2025-03-26 09:47:16', 1, '2cm', 13, '厚度');
INSERT INTO `goodprop` VALUES (63, '2025-03-26 09:47:17', 1, '葡萄牙', 13, '产地');
INSERT INTO `goodprop` VALUES (64, '2025-03-26 09:47:18', 1, '仿古面', 13, '表面处理');
INSERT INTO `goodprop` VALUES (65, '2025-03-26 09:47:19', 1, '30×60cm', 13, '规格');
INSERT INTO `goodprop` VALUES (66, '2025-03-26 09:48:10', 1, '多色', 14, '颜色');
INSERT INTO `goodprop` VALUES (67, '2025-03-26 09:48:11', 1, '1cm', 14, '厚度');
INSERT INTO `goodprop` VALUES (68, '2025-03-26 09:48:12', 1, '巴西', 14, '产地');
INSERT INTO `goodprop` VALUES (69, '2025-03-26 09:48:13', 1, '自然面', 14, '表面处理');
INSERT INTO `goodprop` VALUES (70, '2025-03-26 09:48:14', 1, '30×30cm', 14, '规格');
INSERT INTO `goodprop` VALUES (71, '2025-03-26 09:49:05', 1, '浅黄色', 15, '颜色');
INSERT INTO `goodprop` VALUES (72, '2025-03-26 09:49:06', 1, '10cm', 15, '厚度');
INSERT INTO `goodprop` VALUES (73, '2025-03-26 09:49:07', 1, '澳大利亚', 15, '产地');
INSERT INTO `goodprop` VALUES (74, '2025-03-26 09:49:08', 1, '凿面', 15, '表面处理');
INSERT INTO `goodprop` VALUES (75, '2025-03-26 09:49:09', 1, '50×50cm', 15, '规格');
INSERT INTO `goodprop` VALUES (76, '2025-03-26 09:50:20', 1, '灰色', 16, '颜色');
INSERT INTO `goodprop` VALUES (77, '2025-03-26 09:50:21', 1, '2cm', 16, '厚度');
INSERT INTO `goodprop` VALUES (78, '2025-03-26 09:50:22', 1, '国产', 16, '产地');
INSERT INTO `goodprop` VALUES (79, '2025-03-26 09:50:23', 1, '抛光面', 16, '表面处理');
INSERT INTO `goodprop` VALUES (80, '2025-03-26 09:50:24', 1, '耐酸碱', 16, '特性');
INSERT INTO `goodprop` VALUES (81, '2025-03-26 09:51:35', 1, '白色', 17, '颜色');
INSERT INTO `goodprop` VALUES (82, '2025-03-26 09:51:36', 1, '1cm', 17, '厚度');
INSERT INTO `goodprop` VALUES (83, '2025-03-26 09:51:37', 1, '新疆', 17, '产地');
INSERT INTO `goodprop` VALUES (84, '2025-03-26 09:51:38', 1, '抛光面', 17, '表面处理');
INSERT INTO `goodprop` VALUES (85, '2025-03-26 09:51:39', 1, '60×60cm', 17, '规格');
INSERT INTO `goodprop` VALUES (86, '2025-03-26 09:52:40', 1, '米白色', 18, '颜色');
INSERT INTO `goodprop` VALUES (87, '2025-03-26 09:52:41', 1, '2cm', 18, '厚度');
INSERT INTO `goodprop` VALUES (88, '2025-03-26 09:52:42', 1, '复合材质', 18, '材质');
INSERT INTO `goodprop` VALUES (89, '2025-03-26 09:52:43', 1, '防紫外线', 18, '特性');
INSERT INTO `goodprop` VALUES (90, '2025-03-26 09:52:44', 1, '标准长度2m', 18, '规格');
INSERT INTO `goodprop` VALUES (91, '2025-03-26 09:53:45', 1, '深红色', 19, '颜色');
INSERT INTO `goodprop` VALUES (92, '2025-03-26 09:53:46', 1, '2cm', 19, '厚度');
INSERT INTO `goodprop` VALUES (93, '2025-03-26 09:53:47', 1, '土耳其', 19, '产地');
INSERT INTO `goodprop` VALUES (94, '2025-03-26 09:53:48', 1, '抛光面', 19, '表面处理');
INSERT INTO `goodprop` VALUES (95, '2025-03-26 09:53:49', 1, '豪华装饰', 19, '用途');
INSERT INTO `goodprop` VALUES (96, '2025-03-26 09:54:50', 1, '多色', 20, '颜色');
INSERT INTO `goodprop` VALUES (97, '2025-03-26 09:54:51', 1, '3cm', 20, '厚度');
INSERT INTO `goodprop` VALUES (98, '2025-03-26 09:54:52', 1, '水刀切割', 20, '工艺');
INSERT INTO `goodprop` VALUES (99, '2025-03-26 09:54:53', 1, '200×200cm', 20, '规格');
INSERT INTO `goodprop` VALUES (100, '2025-03-26 09:54:54', 1, '欧式', 20, '风格');

-- ----------------------------
-- Table structure for goodstock
-- ----------------------------
DROP TABLE IF EXISTS `goodstock`;
CREATE TABLE `goodstock`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '库存记录主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `Qty` double(20, 5) NULL DEFAULT NULL COMMENT '调整数量',
  `GoodId` int(11) NULL DEFAULT NULL COMMENT '石材',
  `Remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `ShopId` int(11) NULL DEFAULT NULL COMMENT '店铺',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `ShopId`(`ShopId`) USING BTREE,
  INDEX `GoodId`(`GoodId`) USING BTREE,
  CONSTRAINT `goodstock_ibfk_1` FOREIGN KEY (`ShopId`) REFERENCES `shop` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `goodstock_ibfk_2` FOREIGN KEY (`GoodId`) REFERENCES `good` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '库存记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goodstock
-- ----------------------------
INSERT INTO `goodstock` VALUES (1, '2025-03-26 21:44:22', 2, 15.00000, 20, '正常入库', 1);

-- ----------------------------
-- Table structure for goodtype
-- ----------------------------
DROP TABLE IF EXISTS `goodtype`;
CREATE TABLE `goodtype`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '石材分类主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `Sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `Name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `ShopId` int(11) NULL DEFAULT NULL COMMENT '店铺',
  `IsShop` tinyint(1) NULL DEFAULT NULL COMMENT '是否店铺',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `ShopId`(`ShopId`) USING BTREE,
  CONSTRAINT `goodtype_ibfk_1` FOREIGN KEY (`ShopId`) REFERENCES `shop` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '石材分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goodtype
-- ----------------------------
INSERT INTO `goodtype` VALUES (1, '2025-03-26 09:15:22', 1, 1, '大理石', NULL, 0);
INSERT INTO `goodtype` VALUES (2, '2025-03-26 09:16:45', 1, 2, '花岗岩', NULL, 0);
INSERT INTO `goodtype` VALUES (3, '2025-03-26 09:17:30', 1, 3, '石灰石', NULL, 0);
INSERT INTO `goodtype` VALUES (4, '2025-03-26 09:18:12', 1, 4, '板岩', NULL, 0);
INSERT INTO `goodtype` VALUES (5, '2025-03-26 09:19:05', 1, 5, '砂岩', NULL, 0);
INSERT INTO `goodtype` VALUES (6, '2025-03-26 09:20:33', 1, 6, '石英石', NULL, 0);
INSERT INTO `goodtype` VALUES (7, '2025-03-26 09:21:18', 1, 7, '玉石', NULL, 0);
INSERT INTO `goodtype` VALUES (8, '2025-03-26 09:22:47', 1, 8, '人造石', NULL, 0);
INSERT INTO `goodtype` VALUES (9, '2025-03-26 09:23:29', 1, 9, '进口石材', NULL, 0);
INSERT INTO `goodtype` VALUES (10, '2025-03-26 09:24:56', 1, 10, '特殊定制石材', NULL, 0);
INSERT INTO `goodtype` VALUES (11, '2025-03-26 20:58:59', 2, 1, '精选石材', 1, 1);

-- ----------------------------
-- Table structure for leavefeedback
-- ----------------------------
DROP TABLE IF EXISTS `leavefeedback`;
CREATE TABLE `leavefeedback`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言反馈主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `DoWithUserId` int(11) NULL DEFAULT NULL COMMENT '处理人',
  `Title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '反馈标题',
  `ReplayContent` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '回复内容',
  `FeedbackUserId` int(11) NULL DEFAULT NULL COMMENT '反馈人',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '反馈内容',
  `IsDoWith` tinyint(1) NULL DEFAULT NULL COMMENT '是否处理',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '留言反馈表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of leavefeedback
-- ----------------------------
INSERT INTO `leavefeedback` VALUES (1, '2025-03-29 13:05:24', 1, 1, '系统卡顿了', NULL, 1, '系统卡顿了', NULL);

-- ----------------------------
-- Table structure for ordercomment
-- ----------------------------
DROP TABLE IF EXISTS `ordercomment`;
CREATE TABLE `ordercomment`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单评价主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `OrderId` int(11) NULL DEFAULT NULL COMMENT '订单',
  `GoodId` int(11) NULL DEFAULT NULL COMMENT '石材',
  `ImageUrls` varchar(216) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  `CommentUserId` int(11) NULL DEFAULT NULL COMMENT '评论人',
  `CommentScore` double(20, 5) NULL DEFAULT NULL COMMENT '评分',
  `ShopId` int(11) NULL DEFAULT NULL COMMENT '店铺',
  `Comment` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '评论',
  `ReplyImageUrls` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单回复',
  `ReplyContent` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '回复内容',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `ShopId`(`ShopId`) USING BTREE,
  INDEX `GoodId`(`GoodId`) USING BTREE,
  INDEX `OrderId`(`OrderId`) USING BTREE,
  INDEX `CommentUserId`(`CommentUserId`) USING BTREE,
  CONSTRAINT `ordercomment_ibfk_1` FOREIGN KEY (`ShopId`) REFERENCES `shop` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `ordercomment_ibfk_2` FOREIGN KEY (`GoodId`) REFERENCES `good` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `ordercomment_ibfk_3` FOREIGN KEY (`OrderId`) REFERENCES `orderinfo` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `ordercomment_ibfk_4` FOREIGN KEY (`CommentUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ordercomment
-- ----------------------------
INSERT INTO `ordercomment` VALUES (1, '2025-03-29 12:34:23', 3, 3, 16, 'http://localhost:7245/1743222785081/8851a1b0-4406-43e6-a4d0-2f4a4c9aef82.jpg', 3, 4.00000, 1, '石英石实验室台面是现代实验室环境中非常受欢迎的选择，它以其卓越的耐用性和多功能性而著称。这种材料由大约90%的天然石英颗粒和10%左右的树脂粘合剂及其他添加剂组成，经过高压压制而成。这一组合赋予了石英石台面许多优点：\n\n首先，石英石具有极高的耐化学腐蚀性，能抵抗大多数化学试剂的侵蚀，这使得它在处理酸、碱等化学品的实验中表现尤为出色。其次，由于其天然石英成分，这类台面拥有优秀的耐磨损能力和抗划伤性能，即使长时间使用也能保持表面光滑无损。此外，石英石实验室台面还具备良好的抗高温特性，虽然不建议直接接触明火，但它能承受常见的实验室设备产生的热量。\n\n从美观角度来看，石英石提供了多种颜色和图案的选择，可以根据实验室的整体设计风格和个人偏好进行定制。而且，石英石材质易于清洁维护，只需简单的日常清洁即可保持干净整洁的状态，有助于维持实验室的卫生标准。\n\n总之，石英石实验室台面凭借其坚固耐用、美观大方以及易于维护的特点，成为追求高效、安全和专业环境的实验室的理想选择。无论是学校实验室、科研机构还是工业实验室，都能从中受益。', NULL, NULL);
INSERT INTO `ordercomment` VALUES (2, '2025-03-29 12:55:01', 3, 6, 15, NULL, 3, 4.00000, 1, '很好', 'http://localhost:7245/1743224113486/1.jpg', '是的');
INSERT INTO `ordercomment` VALUES (3, '2025-03-29 12:55:01', 3, 6, 16, NULL, 3, 3.00000, 1, '很好', NULL, NULL);
INSERT INTO `ordercomment` VALUES (4, '2025-03-29 12:55:01', 3, 6, 20, NULL, 3, 4.00000, 1, '很好', NULL, NULL);

-- ----------------------------
-- Table structure for orderdet
-- ----------------------------
DROP TABLE IF EXISTS `orderdet`;
CREATE TABLE `orderdet`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单明细主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `OrderId` int(11) NULL DEFAULT NULL COMMENT '订单',
  `Price` double(20, 5) NULL DEFAULT NULL COMMENT '购买价格',
  `GoodId` int(11) NULL DEFAULT NULL COMMENT '石材',
  `Qty` int(11) NULL DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `GoodId`(`GoodId`) USING BTREE,
  INDEX `OrderId`(`OrderId`) USING BTREE,
  CONSTRAINT `orderdet_ibfk_1` FOREIGN KEY (`GoodId`) REFERENCES `good` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `orderdet_ibfk_2` FOREIGN KEY (`OrderId`) REFERENCES `orderinfo` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderdet
-- ----------------------------
INSERT INTO `orderdet` VALUES (1, '2025-03-26 22:59:41', 3, 1, 980.00000, 19, 1);
INSERT INTO `orderdet` VALUES (2, '2025-03-29 12:25:51', 3, 2, 720.00000, 16, 1);
INSERT INTO `orderdet` VALUES (3, '2025-03-29 12:25:59', 3, 3, 720.00000, 16, 1);
INSERT INTO `orderdet` VALUES (4, '2025-03-29 12:54:30', 3, 6, 420.00000, 15, 1);
INSERT INTO `orderdet` VALUES (5, '2025-03-29 12:54:30', 3, 6, 720.00000, 16, 1);
INSERT INTO `orderdet` VALUES (6, '2025-03-29 12:54:30', 3, 6, 4200.00000, 20, 1);

-- ----------------------------
-- Table structure for orderinfo
-- ----------------------------
DROP TABLE IF EXISTS `orderinfo`;
CREATE TABLE `orderinfo`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `GetOrderTime` datetime(0) NULL DEFAULT NULL COMMENT '收货时间',
  `TotalMoney` double(20, 5) NULL DEFAULT NULL COMMENT '总金额',
  `OrderStatus` int(11) NULL DEFAULT NULL COMMENT '订单状态枚举',
  `OrderNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
  `ExpireTime` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `ShopId` int(11) NULL DEFAULT NULL COMMENT '店铺',
  `PayType` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付类型',
  `TotalQty` int(11) NULL DEFAULT NULL COMMENT '总数量',
  `Remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `ReceiveAddress` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货地址',
  `LogisticsNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物流单号',
  `PayTime` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `PayOrderNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付订单号',
  `OrginMoney` double(20, 5) NULL DEFAULT NULL COMMENT '原始金额',
  `DiscountMoney` double(20, 5) NULL DEFAULT NULL COMMENT '折扣金额',
  `ShopUserId` int(11) NULL DEFAULT NULL COMMENT '商家',
  `DeliveryTime` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `ExpressInfo` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '物流信息',
  `UserId` int(11) NULL DEFAULT NULL COMMENT '用户',
  `ReceiveName` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人',
  `ReceivePhone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货电话',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `ShopId`(`ShopId`) USING BTREE,
  INDEX `UserId`(`UserId`) USING BTREE,
  INDEX `ShopUserId`(`ShopUserId`) USING BTREE,
  CONSTRAINT `orderinfo_ibfk_1` FOREIGN KEY (`ShopId`) REFERENCES `shop` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `orderinfo_ibfk_2` FOREIGN KEY (`UserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `orderinfo_ibfk_3` FOREIGN KEY (`ShopUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderinfo
-- ----------------------------
INSERT INTO `orderinfo` VALUES (1, '2025-03-26 22:59:41', 3, NULL, 980.00000, 2, 'D20250326225941920150', '2025-03-26 23:29:41', 1, '微信支付', 1, NULL, '浙江省/杭州市/西湖区 西湖大道99号[门牌号]:D座1502', 'G12512521', '2025-03-26 22:59:41', 'P20250326225941247806', 980.00000, 0.00000, 2, '2025-03-26 23:03:03', NULL, 3, '陈明', '13734567890');
INSERT INTO `orderinfo` VALUES (2, '2025-03-29 12:25:51', 3, NULL, 720.00000, 1, 'D20250329122550236177', '2025-03-29 12:55:51', 1, '银行卡支付', 1, NULL, '浙江省/杭州市/西湖区 西湖大道99号[门牌号]:D座1502', NULL, '2025-03-29 12:25:51', 'P20250329122550872554', 720.00000, 0.00000, 2, NULL, NULL, 3, '陈明', '13734567890');
INSERT INTO `orderinfo` VALUES (3, '2025-03-29 12:25:59', 3, '2025-03-29 12:27:34', 720.00000, 6, 'D20250329122559881137', '2025-03-29 12:55:59', 1, '微信支付', 1, NULL, '重庆市/渝中区 解放碑步行街66号[门牌号]:C单元8楼', '78815605232715', '2025-03-29 12:25:59', 'P20250329122559529634', 720.00000, 0.00000, 2, '2025-03-29 12:26:40', NULL, 3, '赵敏', '15823456789');
INSERT INTO `orderinfo` VALUES (6, '2025-03-29 12:54:30', 3, '2025-03-29 12:54:48', 5340.00000, 6, 'D20250329125430980138', '2025-03-29 13:24:30', 1, '银行卡支付', 3, NULL, '上海市/浦东新区 中山路188号[门牌号]:A座1203', '78815605232715', '2025-03-29 12:54:30', 'P20250329125430746631', 5340.00000, 0.00000, 2, '2025-03-29 12:54:40', NULL, 3, '张建国', '13812345678');

-- ----------------------------
-- Table structure for orderreturn
-- ----------------------------
DROP TABLE IF EXISTS `orderreturn`;
CREATE TABLE `orderreturn`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单退货主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `IsDiscard` tinyint(1) NULL DEFAULT NULL COMMENT '本次申请是否失效',
  `SellerImageUrls` varchar(216) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '卖家拍照',
  `OrderId` int(11) NULL DEFAULT NULL COMMENT '订单',
  `SellerReply` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '卖家回复',
  `IsApplyCustom` tinyint(1) NULL DEFAULT NULL COMMENT '是否申请客服介入',
  `ApplyReason` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '申请原因',
  `ShopUserId` int(11) NULL DEFAULT NULL COMMENT '商家',
  `UserId` int(11) NULL DEFAULT NULL COMMENT '用户',
  `IsAggren` tinyint(1) NULL DEFAULT NULL COMMENT '卖家是否同意',
  `LogisticsNo` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物流单号',
  `UserImageUrls` varchar(216) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户拍照',
  `OrderReturnType` int(11) NULL DEFAULT NULL COMMENT '退款方式枚举',
  `ReturnMoney` double(18, 6) NULL DEFAULT NULL COMMENT '退款金额',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `ShopUserId`(`ShopUserId`) USING BTREE,
  INDEX `OrderId`(`OrderId`) USING BTREE,
  INDEX `UserId`(`UserId`) USING BTREE,
  CONSTRAINT `orderreturn_ibfk_1` FOREIGN KEY (`ShopUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `orderreturn_ibfk_2` FOREIGN KEY (`OrderId`) REFERENCES `orderinfo` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `orderreturn_ibfk_3` FOREIGN KEY (`UserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单退货表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `Phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机',
  `Ensure` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺保障',
  `LogoCover` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'Logo',
  `AuditUserId` int(11) NULL DEFAULT NULL COMMENT '店铺审核人',
  `UserId` int(11) NULL DEFAULT NULL COMMENT '商家',
  `Address` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺地址',
  `AuditStatus` int(11) NULL DEFAULT NULL COMMENT '审核状态枚举',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '店铺详情',
  `Name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺名称',
  `AuditReason` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '审核原因',
  `Email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺邮箱',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `AuditUserId`(`AuditUserId`) USING BTREE,
  INDEX `UserId`(`UserId`) USING BTREE,
  CONSTRAINT `shop_ibfk_1` FOREIGN KEY (`AuditUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `shop_ibfk_2` FOREIGN KEY (`UserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '店铺表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES (1, '2025-03-26 20:49:34', 2, '15616262411', '质量*品质', 'http://localhost:7245/1742993327598/39910382-a8fa-4746-96c3-22f8c244cc4a.jpg', 1, 2, '北京市朝阳区幸福路123号阳光花园小区5号楼2单元1202室', 2, '<p>欢迎莅临我们的天然石材交易管理系统，这里不仅是石材的集市，更是艺术与自然之美的交汇点。我们专注于为客户提供高质量、多样化的天然石材解决方案，从家庭装修到大型商业项目，我们的产品线能够满足各类客户的需求。</p><p>自成立以来，我们一直致力于挖掘全球最美的石材资源，通过严格的筛选和加工流程，确保每一块石材都能达到最高的品质标准。我们提供的石材种类繁多，包括大理石、花岗岩、板岩等，颜色丰富、纹理独特，无论是现代简约风格还是古典奢华设计，您都能在这里找到理想的材料。</p><p>我们的店铺采用了先进的天然石材交易管理系统，旨在提供无缝的购物体验。通过这一系统，客户可以轻松浏览我们的库存，实时查看石材的详细信息，包括尺寸、颜色、产地等关键数据，并可以直接下单购买。此外，我们还提供了定制服务，根据客户的特殊需求进行切割、打磨，以确保每一块石材都能完美地融入您的设计方案之中。</p><p>除了高品质的产品和服务，我们还非常重视环境保护和社会责任。在开采和加工过程中，我们严格遵守环保标准，努力减少对环境的影响。同时，我们也积极参与社区活动，支持当地经济的发展。</p><p>为了给客户提供更加优质的服务，我们拥有一支经验丰富的专业团队，他们不仅对石材有着深入的理解，而且熟悉市场动态，能够为客户提供专业的建议和解决方案。无论您是设计师、建筑师还是个人消费者，我们都将竭诚为您服务，帮助您实现梦想中的空间设计。</p><p>未来，我们将继续秉持“品质第一、客户至上”的经营理念，不断探索创新，引进更多优质的天然石材资源，拓展我们的产品线和服务范围，力求成为行业内的领军者。欢迎您前来参观选购，让我们一起开启石材之美之旅！</p>', '久石', '欧克了', '806105498@qq.com');

-- ----------------------------
-- Table structure for shopcollect
-- ----------------------------
DROP TABLE IF EXISTS `shopcollect`;
CREATE TABLE `shopcollect`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺收藏主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `CollectUserId` int(11) NULL DEFAULT NULL COMMENT '收藏人',
  `ShopId` int(11) NULL DEFAULT NULL COMMENT '店铺',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `ShopId`(`ShopId`) USING BTREE,
  INDEX `CollectUserId`(`CollectUserId`) USING BTREE,
  CONSTRAINT `shopcollect_ibfk_1` FOREIGN KEY (`ShopId`) REFERENCES `shop` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `shopcollect_ibfk_2` FOREIGN KEY (`CollectUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '店铺收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shopcollect
-- ----------------------------
INSERT INTO `shopcollect` VALUES (1, '2025-03-29 12:25:43', 3, 3, 1);

-- ----------------------------
-- Table structure for sysnotice
-- ----------------------------
DROP TABLE IF EXISTS `sysnotice`;
CREATE TABLE `sysnotice`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统通知主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `IsPutaway` tinyint(1) NULL DEFAULT NULL COMMENT '是否显示',
  `Title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '通知内容',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统通知表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sysnotice
-- ----------------------------
INSERT INTO `sysnotice` VALUES (1, '2025-03-29 08:30:00', 1, 1, '系统维护通知', '尊敬的用户，天然石材交易管理系统将于2025年4月1日凌晨进行系统维护，请提前做好相关准备。');
INSERT INTO `sysnotice` VALUES (2, '2025-03-29 09:15:00', 1, 1, '新功能上线', '我们新增了智能搜索功能，帮助您更高效地查找所需石材信息，请体验并反馈意见。');
INSERT INTO `sysnotice` VALUES (3, '2025-03-29 10:00:00', 1, 0, '促销活动预告', '2025年5月1日将开启全站石材限时折扣活动，敬请期待！');
INSERT INTO `sysnotice` VALUES (4, '2025-03-29 11:45:00', 1, 1, '订单处理延迟提醒', '由于近期订单量激增，部分订单处理可能会有延迟，预计将在2025年4月5日前恢复正常。');
INSERT INTO `sysnotice` VALUES (5, '2025-03-29 12:30:00', 1, 1, '账户安全提示', '请定期更新密码，确保您的账户安全，避免不必要的损失。');
INSERT INTO `sysnotice` VALUES (6, '2025-03-29 13:20:00', 1, 0, '库存清理通知', '2025年第二季度我们将对部分库存进行清理，请及时查看相关清单并提交采购需求。');
INSERT INTO `sysnotice` VALUES (7, '2025-03-29 14:10:00', 1, 1, '物流合作伙伴变更', '自2025年6月1日起，我们的物流服务将更换为新的合作伙伴，请知悉。');
INSERT INTO `sysnotice` VALUES (8, '2025-03-29 15:50:00', 1, 1, '节假日安排通知', '2025年国庆节期间，平台将于10月1日至10月3日暂停服务，感谢您的理解与支持。');
INSERT INTO `sysnotice` VALUES (9, '2025-03-29 16:40:00', 1, 0, '价格调整公告', '受市场波动影响，2025年7月1日起部分石材价格将有所调整，请关注最新报价。');
INSERT INTO `sysnotice` VALUES (10, '2025-03-29 17:30:00', 1, 1, '系统升级完成', '本次系统升级已完成，新增多项功能并优化了操作体验，欢迎您使用。');

-- ----------------------------
-- Table structure for useraddress
-- ----------------------------
DROP TABLE IF EXISTS `useraddress`;
CREATE TABLE `useraddress`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收货地址主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `BelongUserId` int(11) NULL DEFAULT NULL COMMENT '所属用户',
  `Address` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '详细地址',
  `ProviceCityArea` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省市区',
  `Name` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货姓名',
  `Phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `Sex` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `HouseNumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '门牌号',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `BelongUserId`(`BelongUserId`) USING BTREE,
  CONSTRAINT `useraddress_ibfk_1` FOREIGN KEY (`BelongUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '收货地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of useraddress
-- ----------------------------
INSERT INTO `useraddress` VALUES (1, '2025-03-26 11:15:22', 1, 3, '中山路188号', '上海市/浦东新区', '张建国', '13812345678', '男', 'A座1203');
INSERT INTO `useraddress` VALUES (2, '2025-03-26 11:16:45', 1, 3, '人民广场1号', '北京市/朝阳区', '李华', '13987654321', '女', 'B栋305');
INSERT INTO `useraddress` VALUES (3, '2025-03-26 11:17:30', 1, 3, '珠江新城88号', '广东省/广州市/天河区', '王强', '18612349876', '男', '12层1201');
INSERT INTO `useraddress` VALUES (4, '2025-03-26 11:18:12', 1, 3, '解放碑步行街66号', '重庆市/渝中区', '赵敏', '15823456789', '女', 'C单元8楼');
INSERT INTO `useraddress` VALUES (5, '2025-03-26 11:19:05', 1, 3, '西湖大道99号', '浙江省/杭州市/西湖区', '陈明', '13734567890', '男', 'D座1502');

-- ----------------------------
-- Table structure for wechatcollection
-- ----------------------------
DROP TABLE IF EXISTS `wechatcollection`;
CREATE TABLE `wechatcollection`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会话集合主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `OtherUserId` int(11) NULL DEFAULT NULL COMMENT '对方用户',
  `LastTime` datetime(0) NULL DEFAULT NULL COMMENT '最新消息时间',
  `LastMessage` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最新消息',
  `SelfUserId` int(11) NULL DEFAULT NULL COMMENT '本身用户',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `OtherUserId`(`OtherUserId`) USING BTREE,
  INDEX `SelfUserId`(`SelfUserId`) USING BTREE,
  CONSTRAINT `wechatcollection_ibfk_1` FOREIGN KEY (`OtherUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `wechatcollection_ibfk_2` FOREIGN KEY (`SelfUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会话集合表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wechatcollection
-- ----------------------------
INSERT INTO `wechatcollection` VALUES (1, '2025-03-26 22:59:11', 3, 2, '2025-03-29 12:25:27', '还会', 3);
INSERT INTO `wechatcollection` VALUES (2, '2025-03-26 22:59:11', 3, 3, '2025-03-29 12:25:27', '还会', 2);

-- ----------------------------
-- Table structure for wechatmessage
-- ----------------------------
DROP TABLE IF EXISTS `wechatmessage`;
CREATE TABLE `wechatmessage`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会话消息主键',
  `CreationTime` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CreatorId` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `SendTime` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  `Content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '消息内容',
  `OtherUserId` int(11) NULL DEFAULT NULL COMMENT '对方用户',
  `SelfUserId` int(11) NULL DEFAULT NULL COMMENT '自身用户',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `SelfUserId`(`SelfUserId`) USING BTREE,
  INDEX `OtherUserId`(`OtherUserId`) USING BTREE,
  CONSTRAINT `wechatmessage_ibfk_1` FOREIGN KEY (`SelfUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `wechatmessage_ibfk_2` FOREIGN KEY (`OtherUserId`) REFERENCES `appuser` (`Id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '会话消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wechatmessage
-- ----------------------------
INSERT INTO `wechatmessage` VALUES (1, '2025-03-26 22:59:13', 3, '2025-03-26 22:59:13', 'hh', 2, 3);
INSERT INTO `wechatmessage` VALUES (2, '2025-03-27 12:24:56', 3, '2025-03-27 12:24:56', 'enen', 2, 3);
INSERT INTO `wechatmessage` VALUES (3, '2025-03-29 12:25:27', 3, '2025-03-29 12:25:27', '还会', 2, 3);

SET FOREIGN_KEY_CHECKS = 1;
