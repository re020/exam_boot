/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50549
 Source Host           : localhost:3306
 Source Schema         : exam_boot

 Target Server Type    : MySQL
 Target Server Version : 50549
 File Encoding         : 65001

 Date: 01/05/2019 20:42:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ex_auth
-- ----------------------------
DROP TABLE IF EXISTS `ex_auth`;
CREATE TABLE `ex_auth`  (
  `auth_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  `auth_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限名，用于展示',
  `auth_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限代码，用于后台写代码用',
  `auth_father` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '父级权限',
  `auth_menu` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单名',
  `auth_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'url',
  `auth_index` int(11) NOT NULL DEFAULT 0 COMMENT '排序字段',
  `auth_ismenu` int(11) NOT NULL DEFAULT 1 COMMENT '是否生成菜单，1生成0不生成',
  `auth_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `auth_delete` int(11) NOT NULL DEFAULT 1 COMMENT '1正常0删除',
  PRIMARY KEY (`auth_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ex_bank
-- ----------------------------
DROP TABLE IF EXISTS `ex_bank`;
CREATE TABLE `ex_bank`  (
  `bank_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `bank_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题库名称',
  `bank_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片',
  `bank_college` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学院，数据字典id',
  `bank_subject` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '科目。数据字典id',
  `bank_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `bank_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0正常1删除',
  PRIMARY KEY (`bank_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '题库表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_bank
-- ----------------------------
INSERT INTO `ex_bank` VALUES ('1111632855139622912', 'MySQL', 'http://tn20898453.51mypc.cn/file/a1f9fae597784506937ef61ed027c64e.jpg', '1111164554571681792', '1114754695726927872', 10, 1);
INSERT INTO `ex_bank` VALUES ('1111912642504581120', 'Vue.js前端开发', 'http://tn20898453.51mypc.cn/file/1007b32c37ac4d31841dcfadf08443fb.jpg', '1111164554571681792', '1114753044974702592', 5, 1);
INSERT INTO `ex_bank` VALUES ('1111913413874835456', 'JavaSe程序设计', 'http://tn20898453.51mypc.cn/file/7ac30415dbfd4afca7d4a1819f24ba0c.jpg', '1111164554571681792', '1111243864053760000', 8, 1);
INSERT INTO `ex_bank` VALUES ('1111917882515705856', 'JavaEE企业级开发技术', 'http://tn20898453.51mypc.cn/file/6313fa1a54f24bd29c179b7f3f01818e.jpg', '1111164554571681792', '1111243864053760000', 5, 1);
INSERT INTO `ex_bank` VALUES ('1114753665597476864', '概论（一）', 'http://tn20898453.51mypc.cn/file/d9c7337fec114f04b24d91948c2f203b.jpg', '1111171604940967936', '1114753254572462080', 2, 1);

-- ----------------------------
-- Table structure for ex_bank_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `ex_bank_knowledge`;
CREATE TABLE `ex_bank_knowledge`  (
  `know_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `know_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点名',
  `know_bank` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题库id',
  `know_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `know_delete` int(11) NOT NULL DEFAULT 1 COMMENT '1正常0删除',
  PRIMARY KEY (`know_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '知识点表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_bank_knowledge
-- ----------------------------
INSERT INTO `ex_bank_knowledge` VALUES ('1117389471029940224', 'Java基础知识', '1111913413874835456', 3, 1);
INSERT INTO `ex_bank_knowledge` VALUES ('1117389538126221312', '三大结构', '1111913413874835456', 1, 1);
INSERT INTO `ex_bank_knowledge` VALUES ('1117389655386378240', '面向对象', '1111913413874835456', 1, 1);
INSERT INTO `ex_bank_knowledge` VALUES ('1117389707320250368', '类与接口', '1111913413874835456', 1, 1);
INSERT INTO `ex_bank_knowledge` VALUES ('1117389727968808960', '常用类', '1111913413874835456', 1, 1);
INSERT INTO `ex_bank_knowledge` VALUES ('1117389756901117952', '正则表达式', '1111913413874835456', 1, 1);
INSERT INTO `ex_bank_knowledge` VALUES ('1117389793030852608', '集合', '1111913413874835456', 1, 1);
INSERT INTO `ex_bank_knowledge` VALUES ('1117389819777929216', '文件操作与IO流', '1111913413874835456', 1, 1);
INSERT INTO `ex_bank_knowledge` VALUES ('1117389851184877568', '反射', '1111913413874835456', 1, 1);
INSERT INTO `ex_bank_knowledge` VALUES ('1117389881937514496', '多线程', '1111913413874835456', 1, 1);
INSERT INTO `ex_bank_knowledge` VALUES ('1117389900388257792', '网络编程', '1111913413874835456', 1, 1);
INSERT INTO `ex_bank_knowledge` VALUES ('1117389913839390720', 'GUI', '1111913413874835456', 1, 1);

-- ----------------------------
-- Table structure for ex_bank_type
-- ----------------------------
DROP TABLE IF EXISTS `ex_bank_type`;
CREATE TABLE `ex_bank_type`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bank_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题库id',
  `bank_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题型id',
  `bank_know` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点id',
  `bank_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `bank_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0删除1正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '题库-题型对应表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_bank_type
-- ----------------------------
INSERT INTO `ex_bank_type` VALUES ('1117390081330532352', '1111913413874835456', '2', '1117389793030852608', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1118061106913345536', '1111913413874835456', '1', '1117389793030852608', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1118061121291419648', '1111913413874835456', '3', '1117389793030852608', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1118104670640836608', '1111913413874835456', '4', '1117389793030852608', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1118848825591603200', '1111913413874835456', '1111960837909667840', '1117389471029940224', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1119108213132849152', '1111913413874835456', '1', '1117389471029940224', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1119229036275490816', '1111913413874835456', '5', '1117389471029940224', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1120194843872649216', '1111913413874835456', '3', '1117389471029940224', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1120194857009209344', '1111913413874835456', '4', '1117389471029940224', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1120194952903581696', '1111913413874835456', '5', '1117389793030852608', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1120195725674733568', '1111913413874835456', '1', '1117389538126221312', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1120195739306221568', '1111913413874835456', '3', '1117389538126221312', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1120195749653569536', '1111913413874835456', '4', '1117389538126221312', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1120195770142744576', '1111913413874835456', '5', '1117389538126221312', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1120196127514222592', '1111913413874835456', '1', '1117389655386378240', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1120196141229596672', '1111913413874835456', '4', '1117389655386378240', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1120196148326359040', '1111913413874835456', '3', '1117389655386378240', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1120283207053402112', '1111913413874835456', '3', '1117389727968808960', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1120285304721301504', '1111913413874835456', '1', '1117389727968808960', 1, 1);
INSERT INTO `ex_bank_type` VALUES ('1120291373291986944', '1111913413874835456', '1120290556442894336', '1117389471029940224', 1, 0);

-- ----------------------------
-- Table structure for ex_choice
-- ----------------------------
DROP TABLE IF EXISTS `ex_choice`;
CREATE TABLE `ex_choice`  (
  `choice_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目id',
  `choice_title` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题干',
  `choice_type` int(11) NOT NULL COMMENT '1单选2多选3判断',
  `choice_score` decimal(3, 1) NOT NULL COMMENT '分值',
  `choice_difficulty` int(11) NOT NULL COMMENT '难度系数',
  `choice_bank` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属题库',
  `choice_know` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属知识点',
  `choice_resolve` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '解析',
  `choice_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `choice_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0删除1正常',
  PRIMARY KEY (`choice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '选择题表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_choice
-- ----------------------------
INSERT INTO `ex_choice` VALUES ('1117390639416233984', 'ArrayList类的底层数据结构是', 2, 2.0, 2, '1111913413874835456', '1117389793030852608', '无', 8, 1);
INSERT INTO `ex_choice` VALUES ('1117390973396078592', 'LinkedList类的特点是', 2, 2.0, 1, '1111913413874835456', '1117389793030852608', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1117391096586981376', 'Vector类的特点是', 2, 2.0, 3, '1111913413874835456', '1117389793030852608', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1117391221375913984', '关于迭代器说法错误的是', 2, 2.0, 4, '1111913413874835456', '1117389793030852608', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1117391336345980928', '实现下列哪个接口，可以启用比较功能', 2, 2.0, 4, '1111913413874835456', '1117389793030852608', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1117391708556906496', '下面代码运行的结果是(     )\n\nArrayList<String> al = newArrayList<String>();\n\nal.add(true);\n\nal.add(123);\n\nal.add(“abc”);\n\nSystem.out.println(al);', 2, 2.0, 2, '1111913413874835456', '1117389793030852608', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1117391831835889664', 'ArrayList和Vector的区别说法正确的是', 2, 2.0, 2, '1111913413874835456', '1117389793030852608', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1119116960571899904', '测试', 1, 2.0, 2, '1111913413874835456', '1117389471029940224', '啊啊', 1, 0);
INSERT INTO `ex_choice` VALUES ('1120197515501690880', 'JAVA所定义的版本中不包括', 1, 2.0, 1, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120197674755219456', '下列说法正确的是', 1, 2.0, 1, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120197891026116608', 'Java中，在如下所示的Test类中，共有（）个构造方法\npublic class Test{\n\nprivate int x;\n\npublic Test(){\n\nx=35;\n\n}\n\npublic void Test(double f){\n\nThis.x=(int)f;\n\n}\n\npublic Test(String s){}\n\n}', 1, 2.0, 3, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120208673830920192', '变量命名规范说法正确的是', 1, 2.0, 3, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120208825035579392', '下列javaDoc注释正确的是', 1, 2.0, 3, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120209308634636288', '为一个boolean类型变量赋值时，可以使用()方式', 1, 2.0, 2, '1111913413874835456', '1117389471029940224', '', 2, 1);
INSERT INTO `ex_choice` VALUES ('1120209794326650880', '以下()不是合法的标识符', 1, 2.0, 2, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120209887758966784', '表达式(11+3*8)/4%3的值是', 1, 2.0, 2, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120285711522652160', '下列哪个叙述是正确的？', 1, 2.0, 3, '1111913413874835456', '1117389727968808960', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120286561477386240', '下列哪个是正确的？', 1, 2.0, 4, '1111913413874835456', '1117389727968808960', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120286885357346816', '对于如下代码，下列哪个叙述是正确的？\npublic class E{ \n   public static void main(String[] args){ \n      String strOne=\"bird\"; \n      String strTwo=strOne; \n      strOne=\"fly\"; \n      System.out.println(strTwo); \n  } \n}', 1, 2.0, 5, '1111913413874835456', '1117389727968808960', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120287156120641536', '对于如下代码，下列哪个叙述是正确的？\npublic class E { \n   public static void main(String args[]){ \n      String strOne = new String(\"你好\"); \n      String strTwo = strOne; \n      strOne = new String(\"hello\");\n      System.out.println(strTwo); \n   }\n}', 1, 2.0, 5, '1111913413874835456', '1117389727968808960', '', 2, 1);
INSERT INTO `ex_choice` VALUES ('1120288228037943296', '对于如下代码，下列哪个叙述是正确的？\npublic class E {\n   public static void main (String args[]) {\n     String s0 = args[0];\n     String s1 = args[1];\n     String s2 = args[2];\n     System.out.println(s2); \n   }\n}', 1, 2.0, 3, '1111913413874835456', '1117389727968808960', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120288402831368192', '下列哪个叙述是错误的？', 1, 2.0, 4, '1111913413874835456', '1117389727968808960', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120288594594947072', '下列哪个叙述是错误的？', 1, 2.0, 2, '1111913413874835456', '1117389727968808960', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120289459158441984', '下列哪个叙述是错误的？', 1, 2.0, 2, '1111913413874835456', '1117389727968808960', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120296762054074368', '下列哪个是ＪＤＫ提供的编译器？', 1, 2.0, 2, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120297939550396416', '下列哪个是Java应用程序主类中正确的main方法？', 1, 2.0, 2, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120298974176141312', '下列哪个叙述是正确的？', 1, 2.0, 2, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120300211575513088', '下列哪个叙述是正确的？', 1, 2.0, 2, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120302113184866304', '下列哪个叙述是正确的？', 1, 2.0, 2, '1111913413874835456', '1117389471029940224', '', 2, 1);
INSERT INTO `ex_choice` VALUES ('1120307181401464832', '对于下列源文件，哪个叙述是正确的？\npublic class E{\n      public static void main(String []args){\n            System.out.println(\"ok\");\n            System.out.println(\"您好\");\n       }\n}\nclass A{\n    public static void main(String []args){\n          System.out.println(\"ok\");\n          System.out.println(\"您好\");\n       }\n}    \n', 1, 2.0, 2, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120308784477036544', '下列哪个叙述是正确的？', 1, 2.0, 2, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120310026200735744', '对于下列源文件，哪个叙述是正确的？\n　public class Cat{\n         public void cry(){\n               System.out.println(\"maiomaio\");\n         }\n}', 1, 2.0, 2, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120311600947978240', '对于下列源文件，哪个叙述是正确的？\n　public class Cat{\n         public void cry(){\n               System.out.println(\"maiomaio\");\n         }\n}\nclass E{\n    public static void  main(String args[]){\n          System.out.println(\"ok\");\n    }\n}', 1, 2.0, 2, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120317167213010944', 'java程序运行入口的 main方法（即主类的main方法）的返回类型是什么？', 1, 2.0, 3, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120319068310659072', '在Java中，若要使用一个包中的类时，首先要求对该包进行导入，关键字是什么？', 1, 2.0, 3, '1111913413874835456', '1117389471029940224', '', 1, 1);
INSERT INTO `ex_choice` VALUES ('1120319707497422848', '继承是面向对象编程的一个重要特征，它可以降低程序的复杂性并使代码（）。', 1, 2.0, 3, '1111913413874835456', '1117389471029940224', '', 1, 1);

-- ----------------------------
-- Table structure for ex_choice_answer
-- ----------------------------
DROP TABLE IF EXISTS `ex_choice_answer`;
CREATE TABLE `ex_choice_answer`  (
  `answer_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `answer_number` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项，ABCDEFG',
  `answer_content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '选项内容',
  `answer_choice` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属选择题id',
  `answer_true` int(11) NOT NULL COMMENT '1正确0错误',
  `answer_resolve` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '解析',
  `answer_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `answer_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0删除1正常',
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '选项表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_choice_answer
-- ----------------------------
INSERT INTO `ex_choice_answer` VALUES ('1116637426526203905', 'A', '312', '1116637426526203904', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1116637426526203906', 'B', '4', '1116637426526203904', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1116637426526203907', 'C', '3213', '1116637426526203904', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1116637426526203908', 'D', '432', '1116637426526203904', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1116639907410898944', 'A', '选项1', '1116639907406704640', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1116639907410898945', 'B', '选项2', '1116639907406704640', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1116639907410898946', 'C', '选项3', '1116639907406704640', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1116639907410898947', 'D', '选项4', '1116639907406704640', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1116639907410898948', 'E', '选项5', '1116639907406704640', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117390639416233985', 'A', '数组结构', '1117390639416233984', 1, NULL, 8, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117390639416233986', 'B', '链表结构', '1117390639416233984', 0, NULL, 8, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117390639416233987', 'C', '哈希表结构', '1117390639416233984', 0, NULL, 8, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117390973396078593', 'A', '查询快', '1117390973396078592', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117390973396078594', 'B', '增删快 ', '1117390973396078592', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117390973396078595', 'C', '元素不重复', '1117390973396078592', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117390973396078596', 'D', '元素自然排序', '1117390973396078592', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391096586981377', 'A', '线程同步', '1117391096586981376', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391096586981378', 'B', '线程不同步', '1117391096586981376', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391096586981379', 'C', '增删快', '1117391096586981376', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391096586981380', 'D', '底层是链表结构', '1117391096586981376', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391221375913985', 'A', '迭代器是取出集合元素的方式', '1117391221375913984', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391221375913986', 'B', '迭代器的hasNext()方法返回值是布尔类型', '1117391221375913984', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391221375913987', 'C', 'List集合有特有迭代器', '1117391221375913984', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391221375913988', 'D', 'next()方法将返回集合中的上一个元素', '1117391221375913984', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391336345980929', 'A', 'Runnable接口', '1117391336345980928', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391336345980930', 'B', 'Iterator接口', '1117391336345980928', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391336345980931', 'C', 'Serializable接口', '1117391336345980928', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391336345980932', 'D', 'Comparator接口', '1117391336345980928', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391708556906497', 'A', '编译失败 ', '1117391708556906496', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391708556906498', 'B', '[true,123]', '1117391708556906496', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391708556906499', 'C', '[true,123,abc];', '1117391708556906496', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391708556906500', 'D', '[abc];', '1117391708556906496', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391831835889665', 'A', 'ArrayList是线程安全的，Vector是线程不安全', '1117391831835889664', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391831835889666', 'B', 'ArrayList是线程不安全的，Vector是线程安全的', '1117391831835889664', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391831835889667', 'C', 'ArrayList底层是数组结构，Vector底层是链表结构', '1117391831835889664', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1117391831835889668', 'D', 'ArrayList底层是链表结构，Vector底层是数组结构', '1117391831835889664', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1118480352487149568', 'D', '红黑树结构', '1117390639416233984', 0, NULL, 2, 1);
INSERT INTO `ex_choice_answer` VALUES ('1119116960576094208', 'A', '哈哈', '1119116960571899904', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1119116960576094209', 'B', '啊啊', '1119116960571899904', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1119116960576094210', 'C', '请求', '1119116960571899904', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1119116960576094211', 'D', '嗯嗯', '1119116960571899904', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120197515505885184', 'A', 'JAVA2 EE', '1120197515501690880', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120197515505885185', 'B', 'JAVA2 Card', '1120197515501690880', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120197515505885186', 'C', 'JAVA2 ME', '1120197515501690880', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120197515505885187', 'D', 'JAVA2 HE', '1120197515501690880', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120197515505885188', 'E', 'JAVA2 SE', '1120197515501690880', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120197674755219457', 'A', 'JAVA程序的main方法必须写在类里面', '1120197674755219456', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120197674755219458', 'B', 'JAVA程序中可以有多个main方法', '1120197674755219456', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120197674755219459', 'C', 'JAVA程序中类名必须与文件名一样', '1120197674755219456', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120197674755219460', 'D', 'JAVA程序的main方法中如果只有一条语句，可以不用{}(大括号)括起来', '1120197674755219456', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120197891026116609', 'A', '0', '1120197891026116608', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120197891026116610', 'B', '1', '1120197891026116608', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120197891026116611', 'C', '2', '1120197891026116608', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120197891026116612', 'D', '3', '1120197891026116608', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120208673830920193', 'A', '变量由字母、下划线、数字、$符号随意组成', '1120208673830920192', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120208673830920194', 'B', '变量不能以数字作为开头', '1120208673830920192', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120208673830920195', 'C', 'A和a在java中是同一个变量', '1120208673830920192', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120208673830920196', 'D', '不同类型的变量，可以起相同的名字', '1120208673830920192', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120208825035579393', 'A', '/*我爱北京天安门*/', '1120208825035579392', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120208825035579394', 'B', '//我爱北京天安门*/', '1120208825035579392', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120208825035579395', 'C', '/**我爱北京天安门*/', '1120208825035579392', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120208825035579396', 'D', '/*我爱北京天安门**/', '1120208825035579392', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120209308634636289', 'A', 'boolean b = a = (0<=10)', '1120209308634636288', 0, NULL, 2, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120209308634636290', 'B', 'boolean a = (9 >= 10)', '1120209308634636288', 1, NULL, 2, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120209308634636291', 'C', 'boolean a=\"真\"', '1120209308634636288', 0, NULL, 2, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120209308634636292', 'D', ' boolean a = = false', '1120209308634636288', 0, NULL, 2, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120209794326650881', 'A', 'STRING', '1120209794326650880', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120209794326650882', 'B', 'x3x', '1120209794326650880', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120209794326650883', 'C', 'void', '1120209794326650880', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120209794326650884', 'D', 'de$f', '1120209794326650880', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120209887758966785', 'A', '31', '1120209887758966784', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120209887758966786', 'B', '0', '1120209887758966784', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120209887758966787', 'C', '1', '1120209887758966784', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120209887758966788', 'D', '2', '1120209887758966784', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120285711522652161', 'A', 'String 类是final 类，不可以有子类。', '1120285711522652160', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120285711522652162', 'B', 'String 类在java.util包中。', '1120285711522652160', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120285711522652163', 'C', '\"abc\"==\"abc\"的值是false .', '1120285711522652160', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120285711522652164', 'D', '\"abc\".equals(\"Abc\")的值是true答案：A', '1120285711522652160', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120286561477386241', 'A', 'int m =Float.parseFloat(\"567\"); ', '1120286561477386240', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120286561477386242', 'B', 'int m =Short.parseShort(\"567\");', '1120286561477386240', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120286561477386243', 'C', 'byte m =Integer.parseInt(\"2\");', '1120286561477386240', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120286561477386244', 'D', 'float m =Float.parseDouble(\"2.9\");', '1120286561477386240', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120286885357346817', 'A', '程序编译出现错误。', '1120286885357346816', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120286885357346818', 'B', '程序标注的【代码】的输出结果是bird。', '1120286885357346816', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120286885357346819', 'C', '程序标注的【代码】的输出结果是fly。', '1120286885357346816', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120286885357346820', 'D', '程序标注的【代码】的输出结果是null。 ', '1120286885357346816', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120287156120641537', 'A', '程序编译出现错误。', '1120287156120641536', 0, NULL, 2, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120287156120641538', 'B', '程序标注的【代码】的输出结果是hello。', '1120287156120641536', 0, NULL, 2, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120287156120641539', 'C', '程序标注的【代码】的输出结果是你好。', '1120287156120641536', 1, NULL, 2, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120287156120641540', 'D', '程序标注的【代码】的输出结果是null。', '1120287156120641536', 0, NULL, 2, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120288228037943297', 'A', '程序出现编译错误。', '1120288228037943296', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120288228037943298', 'B', '无编译错误，在命令行执行程序：“java E I love this game”，程序输出game。', '1120288228037943296', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120288228037943299', 'C', '无编译错误，在命令行执行程序：“java E go on”，运行异常:ArrayIndexOutOfBoundsException: 2。', '1120288228037943296', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120288228037943300', 'D', '无编译错误，在命令行执行程序：“java E you are ok”程序输出you。 ', '1120288228037943296', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120288402831368193', 'A', '\"9dog\".matches(\"\\\\ddog\")的值是true。', '1120288402831368192', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120288402831368194', 'B', '\"12hello567\".replaceAll(\"[123456789]+\",\"@\")返回的字符串是@hello@。', '1120288402831368192', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120288402831368195', 'C', 'new Date(1000)对象含有的时间是公元后1000小时的时间', '1120288402831368192', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120288402831368196', 'D', '\"\\\\hello\\n\"是正确的字符串常量', '1120288402831368192', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120288594594947073', 'A', 'Integer.parseInt(\"12.9\");会触发NumberFormatException异常。', '1120288594594947072', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120288594594947074', 'B', '表达式\"bird\".contentEquals(\"bird\")的值是true。', '1120288594594947072', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120288594594947075', 'C', '表达式\"Bird\" == \"bird\"的值是false。', '1120288594594947072', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120288594594947076', 'D', 'new String(\"bird\") == \"bird\"的值是true。', '1120288594594947072', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120289459158441985', 'A', '表达式\"D:/java/book\".lastIndexOf(\"/\")的值是8。', '1120289459158441984', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120289459158441986', 'B', '表达式\"3.14\".matches(\"[0-9]+[.]{1}[0-9]+\")的值是true。', '1120289459158441984', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120289459158441987', 'C', '表达式\"220301200210250286\".startsWith(\"2203\")的值是true。', '1120289459158441984', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120289459158441988', 'D', '表达式\"220301200210250286\".endsWith(\"286\")的值是true。', '1120289459158441984', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120296762054074369', 'A', 'java.exe', '1120296762054074368', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120296762054074370', 'B', 'javac.exe', '1120296762054074368', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120296762054074371', 'C', 'javap.exe', '1120296762054074368', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120296762054074372', 'D', 'javaw.exe', '1120296762054074368', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120297939550396417', 'A', 'public void main(String args[])', '1120297939550396416', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120297939550396418', 'B', 'static void main(String args[])', '1120297939550396416', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120297939550396419', 'C', 'public static void Main(String args[])', '1120297939550396416', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120297939550396420', 'D', 'public static void main(String args[])', '1120297939550396416', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120298974176141313', 'A', 'Java语言是２００５年５月Sun公司推出的编程语言。', '1120298974176141312', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120298974176141314', 'B', 'Java语言是１９９５年５月ＩＢＭ公司推出的编程语言。', '1120298974176141312', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120298974176141315', 'C', 'Java语言的名字是印度尼西亚一个盛产咖啡的岛名。', '1120298974176141312', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120298974176141316', 'D', 'Java语言的主要贡献者是比尔盖茨。', '1120298974176141312', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120300211575513089', 'A', 'Java源文件是由若干个书写形式互相独立的类组成。', '1120300211575513088', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120300211575513090', 'B', 'Java源文件中只能有一个类。', '1120300211575513088', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120300211575513091', 'C', '如果源文件中有多个类，那么至少有一个类必须是public类', '1120300211575513088', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120300211575513092', 'D', 'Java源文件的拓展名是.txt。', '1120300211575513088', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120302113184866305', 'A', '源文件名字必须是A.java。', '1120302113184866304', 0, NULL, 2, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120302113184866306', 'B', '如果源文件中有多个类，这些类可以都不是public类。', '1120302113184866304', 1, NULL, 2, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120302113184866307', 'C', '如果源文件中只有一个类，这个类必须是public类', '1120302113184866304', 0, NULL, 2, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120302113184866308', 'D', '如果源文件中只有一个类，这个类必须是主类', '1120302113184866304', 0, NULL, 2, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120307181401464833', 'A', '源文件名字必须是A.java。', '1120307181401464832', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120307181401464834', 'B', '源文件有错误。', '1120307181401464832', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120307181401464835', 'C', '源文件必须命名为E.java，编译无错误。有两个主类，E和A，程序可以执行主类E也可以执行主类A。', '1120307181401464832', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120307181401464836', 'D', '如果源文件中只有一个类，这个类必须是主类', '1120307181401464832', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120308784477036545', 'A', 'JDK的全称是Java Development Kit。', '1120308784477036544', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120308784477036546', 'B', '可以用Microsoft Word 2010编写java源文件', '1120308784477036544', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120308784477036547', 'C', '不用NetBean或MyEclipse就无法开发Java程序', '1120308784477036544', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120308784477036548', 'D', '为了运行Java程序，必须要安装NetBean或MyEclipse。', '1120308784477036544', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120310026200735745', 'A', '源文件的名字必须是Cat.java。', '1120310026200735744', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120310026200735746', 'B', '源文件的名字可以是cat.java。', '1120310026200735744', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120310026200735747', 'C', '源文件的名字可以任意。', '1120310026200735744', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120310026200735748', 'D', 'Cat类是主类。', '1120310026200735744', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120311600947978241', 'A', '源文件的名字必须是Cat.java。', '1120311600947978240', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120311600947978242', 'B', '源文件的名字可以是E.java。', '1120311600947978240', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120311600947978243', 'C', '编译源文件得到Cat.class和E.class两个字节码文件。', '1120311600947978240', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120311600947978244', 'D', 'E类是主类，java E来运行程序。', '1120311600947978240', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120317167213010945', 'A', 'void', '1120317167213010944', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120317167213010946', 'B', 'static', '1120317167213010944', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120317167213010947', 'C', 'char', '1120317167213010944', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120317167213010948', 'D', 'int', '1120317167213010944', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120319068310659073', 'A', 'import', '1120319068310659072', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120319068310659074', 'B', 'package', '1120319068310659072', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120319068310659075', 'C', 'include', '1120319068310659072', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120319068310659076', 'D', 'packet', '1120319068310659072', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120319707497422849', 'A', '可读性好', '1120319707497422848', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120319707497422850', 'B', '可重用', '1120319707497422848', 1, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120319707497422851', 'C', '可跨包访问', '1120319707497422848', 0, NULL, 1, 1);
INSERT INTO `ex_choice_answer` VALUES ('1120319707497422852', 'D', '运行更安全', '1120319707497422848', 0, NULL, 1, 1);

-- ----------------------------
-- Table structure for ex_code
-- ----------------------------
DROP TABLE IF EXISTS `ex_code`;
CREATE TABLE `ex_code`  (
  `code_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code_title` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题干',
  `code_imgs` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片',
  `code_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属题型',
  `code_score` decimal(3, 1) NOT NULL DEFAULT 0.0 COMMENT '分值',
  `code_difficulty` int(11) NOT NULL DEFAULT 1 COMMENT '难度系数',
  `code_bank` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属题库',
  `code_know` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属知识点',
  `code_compile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编译器，Java、C/C++、Python等',
  `code_resolve` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '解析',
  `code_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `code_delete` int(11) NOT NULL DEFAULT 1 COMMENT '1正常0删除',
  PRIMARY KEY (`code_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '编程题' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_code
-- ----------------------------
INSERT INTO `ex_code` VALUES ('1119500099022520320', '企业发放<的>奖金根据利润提成。利润(I)低于或等于 10 万元时，奖金可提 10%；利润高于 10 万元，低于 20 万元时，低于 10 万元的部分按 10%提成，高于 10 万元的部分，可可提成 7.5%；20 万到 40 万之间时，高于 20 万元的部分，可提成 5%；40 万到 60 万之间时高于 40 万元的部分， 可提成 3%； 60 万到 100 万之间时， 高于 60 万元的部分， 可提成 1.5%，高于 100 万元时，超过 100 万元的部分按 1%提成，从键盘输入当月利润', NULL, '5', 4.0, 1, '1111913413874835456', '1117389471029940224', '1', 'package com.day3;\nimport java.util.*;\npublic class test12 {\n    public static void main(String[] args) {\n        double x = 0,y = 0;\n        System.out.print(\"输入当月利润（万） ：\");\n        Scanner s = new Scanner(System.in);\n        x = s.nextInt();\n        if(x > 0 && x <= 10) {\n            y = x * 0.1;\n        } else if(x > 10 && x <= 20) {\n            y = 10 * 0.1 + (x - 10) * 0.075;\n        } else if(x > 20 && x <= 40) {\n            y = 10 * 0.1 + 10 * 0.075 + (x - 20) * 0.05;\n        } else if(x > 40 && x <= 60) {\n            y = 10 * 0.1 + 10 * 0.075 + 20 * 0.05 + (x - 40) * 0.03;\n        } else if(x > 60 && x <= 100) {\n            y = 20 * 0.175 + 20 * 0.05 + 20 * 0.03 + (x - 60) * 0.015;\n        } else if(x > 100) {\n            y = 20 * 0.175 + 40 * 0.08 + 40 * 0.015 + (x - 100) * 0.01;\n        }\n        System.out.println(\"应该提取的奖金是 \" + y + \"万\");\n    }\n}\n\ntest12', 4, 1);
INSERT INTO `ex_code` VALUES ('1119501836202577920', '猴子吃桃问题：猴子第一天摘下若干个桃子，当即吃了一半，还不瘾，又多吃了一个 第二天早上又将剩下的桃子吃掉一半，又多吃了一个。以后每天早上都吃了前一天剩下 的一半零一个。 到第10天早上想再吃时， 见只剩下一个桃子了', NULL, '5', 5.0, 2, '1111913413874835456', '1117389471029940224', '1', 'package com.day4;\npublic class test17 {\n    public static void main(String[] args) {\n        // TODO Auto-generated method stub\n        int num = 1;\n        for (int i = 9; i >= 1; i--) {\n            num = (num + 1) * 2;\n        }\n        System.out.println(\"猴子第一天摘的桃子的个数是：\"+num);\n    }\n\n}\n\ntest17', 4, 1);

-- ----------------------------
-- Table structure for ex_code_answer
-- ----------------------------
DROP TABLE IF EXISTS `ex_code_answer`;
CREATE TABLE `ex_code_answer`  (
  `answer_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `answer_problem` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小题',
  `answer_content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '答案内容',
  `answer_number` int(3) NULL DEFAULT NULL COMMENT '答案编号',
  `answer_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应题目编号',
  `answer_score` decimal(3, 0) NOT NULL COMMENT '分值',
  `answer_resolve` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '解析',
  `answer_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `answer_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0正常1删除',
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '编程题-答案' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_code_answer
-- ----------------------------
INSERT INTO `ex_code_answer` VALUES ('1119500099026714624', '求应发放奖金总数？', 'package com.day3;\nimport java.util.*;\npublic class test12 {\n    public static void main(String[] args) {\n        double x = 0,y = 0;\n        System.out.print(\"输入当月利润（万） ：\");\n        Scanner s = new Scanner(System.in);\n        x = s.nextInt();\n        if(x > 0 && x <= 10) {\n            y = x * 0.1;\n        } else if(x > 10 && x <= 20) {\n            y = 10 * 0.1 + (x - 10) * 0.075;\n        } else if(x > 20 && x <= 40) {\n            y = 10 * 0.1 + 10 * 0.075 + (x - 20) * 0.05;\n        } else if(x > 40 && x <= 60) {\n            y = 10 * 0.1 + 10 * 0.075 + 20 * 0.05 + (x - 40) * 0.03;\n        } else if(x > 60 && x <= 100) {\n            y = 20 * 0.175 + 20 * 0.05 + 20 * 0.03 + (x - 60) * 0.015;\n        } else if(x > 100) {\n            y = 20 * 0.175 + 40 * 0.08 + 40 * 0.015 + (x - 100) * 0.01;\n        }\n        System.out.println(\"应该提取的奖金是 \" + y + \"万\");\n    }\n}\n\ntest12', 1, '1119500099022520320', 4, NULL, 4, 1);
INSERT INTO `ex_code_answer` VALUES ('1119501836210966528', '求第一天共摘了多少。', 'package com.day4;\npublic class test17 {\n    public static void main(String[] args) {\n        // TODO Auto-generated method stub\n        int num = 1;\n        for (int i = 9; i >= 1; i--) {\n            num = (num + 1) * 2;\n        }\n        System.out.println(\"猴子第一天摘的桃子的个数是：\"+num);\n    }\n\n}\n\ntest17', 1, '1119501836202577920', 5, NULL, 4, 1);

-- ----------------------------
-- Table structure for ex_compile
-- ----------------------------
DROP TABLE IF EXISTS `ex_compile`;
CREATE TABLE `ex_compile`  (
  `compile_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `compile_number` int(3) NOT NULL COMMENT '编译器编号',
  `compile_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编译器名',
  PRIMARY KEY (`compile_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '编译器表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_compile
-- ----------------------------
INSERT INTO `ex_compile` VALUES ('1', 1, 'JAVA');
INSERT INTO `ex_compile` VALUES ('2', 2, 'C/C++');
INSERT INTO `ex_compile` VALUES ('3', 3, 'Python');
INSERT INTO `ex_compile` VALUES ('4', 4, 'JavaScript');

-- ----------------------------
-- Table structure for ex_completion
-- ----------------------------
DROP TABLE IF EXISTS `ex_completion`;
CREATE TABLE `ex_completion`  (
  `comp_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comp_title` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `comp_score` decimal(3, 1) NOT NULL COMMENT '分值',
  `comp_difficulty` int(11) NOT NULL COMMENT '难度系数',
  `comp_bank` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属题库',
  `comp_know` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点id',
  `comp_resolve` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '解析',
  `comp_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `comp_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0删除1正常',
  PRIMARY KEY (`comp_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '填空题表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_completion
-- ----------------------------
INSERT INTO `ex_completion` VALUES ('1118468407142936576', '测试第一个空___第二个空___第三个空___', 2.0, 3, '1111913413874835456', '1117389793030852608', '我是解析', 2, 1);
INSERT INTO `ex_completion` VALUES ('1118470836475416576', '哈哈___测试第一个空___第二个空___第三个___哈哈哈', 3.0, 1, '1111913413874835456', '1117389793030852608', '我是解析', 10, 1);

-- ----------------------------
-- Table structure for ex_completion_answer
-- ----------------------------
DROP TABLE IF EXISTS `ex_completion_answer`;
CREATE TABLE `ex_completion_answer`  (
  `answer_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `answer_number` int(3) NULL DEFAULT NULL COMMENT '答案编号,1,2,3...',
  `answer_content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '答案内容',
  `answer_comp` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属填空题',
  `answer_resolve` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '解析',
  `answer_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `answer_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0删除1正常',
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '填空题答案表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_completion_answer
-- ----------------------------
INSERT INTO `ex_completion_answer` VALUES ('1118470836475416577', 1, '第一', '1118470836475416576', '1', 10, 1);
INSERT INTO `ex_completion_answer` VALUES ('1118470836475416578', 2, '第二', '1118470836475416576', NULL, 10, 1);
INSERT INTO `ex_completion_answer` VALUES ('1118470836475416579', 3, '第三', '1118470836475416576', NULL, 10, 1);
INSERT INTO `ex_completion_answer` VALUES ('1118472875616022528', 4, '第四', '1118470836475416576', NULL, 6, 1);

-- ----------------------------
-- Table structure for ex_dict
-- ----------------------------
DROP TABLE IF EXISTS `ex_dict`;
CREATE TABLE `ex_dict`  (
  `dict_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据字典id',
  `dict_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据字典名',
  `dict_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据字典类型',
  `dict_father` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级字典id',
  `dict_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `dict_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0已删除，1未删除',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典表\r\ncollege-学院\r\nmajor-专业\r\njob-职务\r\ntitle-职称\r\nsubject-科目' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_dict
-- ----------------------------
INSERT INTO `ex_dict` VALUES ('1111164554571681792', '电子与信息工程学院', 'college', NULL, 7, 1);
INSERT INTO `ex_dict` VALUES ('1111164565703364608', '机电工程学院', 'college', NULL, 4, 1);
INSERT INTO `ex_dict` VALUES ('1111164855676571648', '外国语学院', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111168385552486400', '人文学院', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111171604940967936', '马克思主义学院', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111171754350465024', '建筑工程学院', 'college', NULL, 3, 1);
INSERT INTO `ex_dict` VALUES ('1111173035584507904', '教育学院', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111173091377139712', '体育学院', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111173098645868544', '艺术学院', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111173105935568896', '商学院', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111173112583540736', '数理学院', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111173174202060800', '机电工程学院', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111182824423047168', '化学化工学院', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111187013471584256', '生命科学学院', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111187063224418304', '医学部', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111189642587160576', '国防生学院', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111211629313277952', '软件工程', 'major', '1111164554571681792', 2, 1);
INSERT INTO `ex_dict` VALUES ('1111211697013538816', '计算机科学与技术', 'major', '1111164554571681792', 2, 1);
INSERT INTO `ex_dict` VALUES ('1111223441308008448', '任课教师', 'job', NULL, 1, 1);
INSERT INTO `ex_dict` VALUES ('1111223476665991168', '班主任', 'job', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111223574124838912', '教授', 'title', NULL, 1, 1);
INSERT INTO `ex_dict` VALUES ('1111223591413760000', '副教授', 'title', NULL, 1, 1);
INSERT INTO `ex_dict` VALUES ('1111223649802665984', '团委书记', 'title', NULL, 1, 1);
INSERT INTO `ex_dict` VALUES ('1111243811151003648', '高等数学', 'subject', NULL, 1, 1);
INSERT INTO `ex_dict` VALUES ('1111243839034736640', '软件工程专业英语', 'subject', NULL, 1, 1);
INSERT INTO `ex_dict` VALUES ('1111243864053760000', 'Java', 'subject', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1111549081068511232', '创新创业学院', 'college', NULL, 2, 1);
INSERT INTO `ex_dict` VALUES ('1114751719062933504', '计算机科学与技术（一本）', 'major', '1111164554571681792', 1, 1);
INSERT INTO `ex_dict` VALUES ('1114751745679986688', '网络工程', 'major', '1111164554571681792', 1, 1);
INSERT INTO `ex_dict` VALUES ('1114751786427650048', '商务英语', 'major', '1111164855676571648', 1, 1);
INSERT INTO `ex_dict` VALUES ('1114751812155510784', '日语', 'major', '1111164855676571648', 1, 1);
INSERT INTO `ex_dict` VALUES ('1114751849807777792', '小学教育', 'major', '1111173035584507904', 1, 1);
INSERT INTO `ex_dict` VALUES ('1114751878618451968', '学前教育', 'major', '1111173035584507904', 1, 1);
INSERT INTO `ex_dict` VALUES ('1114751916128112640', '中医', 'major', '1111187063224418304', 1, 1);
INSERT INTO `ex_dict` VALUES ('1114752023569403904', '临床医学', 'major', '1111187063224418304', 1, 1);
INSERT INTO `ex_dict` VALUES ('1114752052367495168', '通信工程', 'major', '1111164554571681792', 1, 1);
INSERT INTO `ex_dict` VALUES ('1114753044974702592', 'Web前端开发技术', 'subject', NULL, 1, 1);
INSERT INTO `ex_dict` VALUES ('1114753254572462080', '毛泽东概论', 'subject', NULL, 1, 1);
INSERT INTO `ex_dict` VALUES ('1114754695726927872', '数据库', 'subject', NULL, 1, 1);

-- ----------------------------
-- Table structure for ex_paper
-- ----------------------------
DROP TABLE IF EXISTS `ex_paper`;
CREATE TABLE `ex_paper`  (
  `paper_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `paper_title` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '试卷标题',
  `paper_start_year` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '起始年度',
  `paper_end_year` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '结束年度',
  `paper_seme` int(2) NOT NULL DEFAULT 1 COMMENT '第几学期，1第一学期，2第二学期',
  `paper_comment` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `paper_college` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学院，数据字典id',
  `paper_major` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '专业，数据字典id',
  `paper_bank` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题库id',
  `paper_flag` int(11) NOT NULL DEFAULT 0 COMMENT '0未启用，1已启用',
  `paper_style` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'A' COMMENT 'ABC卷',
  `paper_type` int(11) NOT NULL DEFAULT 0 COMMENT '组卷类型，0未组卷，1手动组卷，2智能组卷',
  `paper_difficulty` decimal(3, 1) NOT NULL DEFAULT 1.0 COMMENT '难度系数',
  `paper_score` decimal(3, 1) NOT NULL DEFAULT 0.0 COMMENT '总分',
  `paper_submit` int(3) NOT NULL DEFAULT 0 COMMENT '是否提交组卷，提交组卷后不能修改，0未提交，1已提交 ',
  `paper_download` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '试卷下载链接',
  `paper_question_num` int(3) NOT NULL DEFAULT 0 COMMENT '题量',
  `paper_create_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `paper_update_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新时间',
  `paper_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `paper_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0删除1正常',
  PRIMARY KEY (`paper_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '试卷表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_paper
-- ----------------------------
INSERT INTO `ex_paper` VALUES ('1119583382574010368', '测试试卷2', '2018', '2019', 1, '哈哈哈', '1111164554571681792', '1111211629313277952', '1111632855139622912', 0, 'B', 0, 1.0, 0.0, 0, NULL, 0, '2019-04-20', '2019-04-20', 3, 1);
INSERT INTO `ex_paper` VALUES ('1119583424651268096', '再测', '2018', '2019', 1, '哈哈哈', '1111164554571681792', '1111211629313277952', '1111912642504581120', 0, 'A', 0, 1.0, 0.0, 0, NULL, 0, '2019-04-20', '2019-04-20', 1, 1);
INSERT INTO `ex_paper` VALUES ('1119583466745303040', 'java考试', '2018', '2019', 1, '哈哈哈', '1111164554571681792', '1111211629313277952', '1111913413874835456', 0, 'A', 1, 1.6, 30.0, 1, 'http://tn20898453.51mypc.cn/file/java考试.docx', 11, '2019-04-20', '2019-04-20', 34, 1);
INSERT INTO `ex_paper` VALUES ('1119583503990722560', 'Javaee考试', '2018', '2019', 1, '哈哈哈', '1111164554571681792', '1111211629313277952', '1111917882515705856', 0, 'A', 0, 1.0, 0.0, 0, NULL, 0, '2019-04-20', '2019-04-20', 1, 1);
INSERT INTO `ex_paper` VALUES ('1119583545036181504', '万恶的马原考试', '2018', '2019', 1, '哈哈哈', '1111164554571681792', '1111211629313277952', '1114753665597476864', 0, 'A', 0, 1.0, 0.0, 0, NULL, 0, '2019-04-20', '2019-04-20', 1, 1);

-- ----------------------------
-- Table structure for ex_paper_config
-- ----------------------------
DROP TABLE IF EXISTS `ex_paper_config`;
CREATE TABLE `ex_paper_config`  (
  `config_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `config_paper` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属试卷',
  `config_question_num` int(11) NOT NULL DEFAULT 0 COMMENT '题目量',
  `config_score` decimal(3, 1) NOT NULL DEFAULT 0.0 COMMENT '分值',
  `config_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属题型',
  `config_know` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点',
  `config_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `config_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0删除1正常',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '试卷配置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_paper_config
-- ----------------------------
INSERT INTO `ex_paper_config` VALUES ('1122461152130641920', '1119583466745303040', 3, 6.0, '1', '1117389471029940224', 4, 1);
INSERT INTO `ex_paper_config` VALUES ('1122461353855692800', '1119583466745303040', 5, 10.0, '3', '1117389727968808960', 1, 1);
INSERT INTO `ex_paper_config` VALUES ('1122461412479479808', '1119583466745303040', 2, 11.0, '5', '1117389471029940224', 1, 1);
INSERT INTO `ex_paper_config` VALUES ('1122461556327329792', '1119583466745303040', 1, 3.0, '4', '1117389793030852608', 1, 1);

-- ----------------------------
-- Table structure for ex_paper_config_question
-- ----------------------------
DROP TABLE IF EXISTS `ex_paper_config_question`;
CREATE TABLE `ex_paper_config_question`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `question_config` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置id',
  `question_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目id',
  `question_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '试卷配置-题目表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_paper_config_question
-- ----------------------------
INSERT INTO `ex_paper_config_question` VALUES ('1122461152130641922', '1122461152130641920', '1120209308634636288', 1);
INSERT INTO `ex_paper_config_question` VALUES ('1122461152130641924', '1122461152130641920', '1120296762054074368', 1);
INSERT INTO `ex_paper_config_question` VALUES ('1122461152130641925', '1122461152130641920', '1120209887758966784', 1);
INSERT INTO `ex_paper_config_question` VALUES ('1122461353855692801', '1122461353855692800', '1120285064047943680', 1);
INSERT INTO `ex_paper_config_question` VALUES ('1122461353855692802', '1122461353855692800', '1120285159640326144', 1);
INSERT INTO `ex_paper_config_question` VALUES ('1122461353855692803', '1122461353855692800', '1120283508040851456', 1);
INSERT INTO `ex_paper_config_question` VALUES ('1122461353855692804', '1122461353855692800', '1120283362037129216', 1);
INSERT INTO `ex_paper_config_question` VALUES ('1122461353855692805', '1122461353855692800', '1120283323948654592', 1);
INSERT INTO `ex_paper_config_question` VALUES ('1122461412479479809', '1122461412479479808', '1119500099022520320', 1);
INSERT INTO `ex_paper_config_question` VALUES ('1122461412479479810', '1122461412479479808', '1119501836202577920', 1);
INSERT INTO `ex_paper_config_question` VALUES ('1122461556327329793', '1122461556327329792', '1118470836475416576', 1);

-- ----------------------------
-- Table structure for ex_paper_log
-- ----------------------------
DROP TABLE IF EXISTS `ex_paper_log`;
CREATE TABLE `ex_paper_log`  (
  `pl_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志id',
  `pl_teacher` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组卷教师的id',
  `pl_difficulty` decimal(3, 1) NOT NULL DEFAULT 0.0 COMMENT '难度系数',
  `pl_score` decimal(3, 1) NOT NULL DEFAULT 0.0 COMMENT '试卷分值',
  `pl_time` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组卷时间',
  `pl_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `pl_delete` int(11) NOT NULL DEFAULT 1 COMMENT '1正常0删除',
  PRIMARY KEY (`pl_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组卷日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ex_pwd
-- ----------------------------
DROP TABLE IF EXISTS `ex_pwd`;
CREATE TABLE `ex_pwd`  (
  `pwd_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pwd_plaintext` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '明文',
  `pwd_ciphertext` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密文',
  PRIMARY KEY (`pwd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_pwd
-- ----------------------------
INSERT INTO `ex_pwd` VALUES ('1112348581047590912', '123456', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `ex_pwd` VALUES ('1112348603667472384', '123456', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `ex_pwd` VALUES ('1112348607169716224', '123456', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `ex_pwd` VALUES ('1112348610583879680', '123456', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `ex_pwd` VALUES ('1112348612878163968', '123456', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `ex_pwd` VALUES ('1112601301109231616', '144933', 'ee1d5f64d02ffb04aed535582dcccb71');
INSERT INTO `ex_pwd` VALUES ('1112601317483794432', '144933', 'ee1d5f64d02ffb04aed535582dcccb71');
INSERT INTO `ex_pwd` VALUES ('1112601320579190784', '144933', 'ee1d5f64d02ffb04aed535582dcccb71');
INSERT INTO `ex_pwd` VALUES ('1112601324026908672', '144933', 'ee1d5f64d02ffb04aed535582dcccb71');
INSERT INTO `ex_pwd` VALUES ('1113037981574234112', '101010', '6d071901727aec1ba6d8e2497ef5b709');
INSERT INTO `ex_pwd` VALUES ('1113057602649980928', '160230131', 'c408c4ce33a10658314072025460007e');
INSERT INTO `ex_pwd` VALUES ('1113057899694784512', '645', '5e9f92a01c986bafcabbafd145520b13');
INSERT INTO `ex_pwd` VALUES ('1113058512327409664', 'c4ca4238a0b923820dcc509a6f75849b', '28c8edde3d61a0411511d3b1866f0636');
INSERT INTO `ex_pwd` VALUES ('1113420404002660352', '132342', '69805ef3731a3c56beb0256575d8e4d4');
INSERT INTO `ex_pwd` VALUES ('1113420581719515136', '54', 'a684eceee76fc522773286a895bc8436');
INSERT INTO `ex_pwd` VALUES ('1113420988155961344', '97', 'e2ef524fbf3d9fe611d5a8e90fefdc9c');
INSERT INTO `ex_pwd` VALUES ('1113421334412533760', '1', 'c4ca4238a0b923820dcc509a6f75849b');
INSERT INTO `ex_pwd` VALUES ('1114492785756483584', '12121', 'de872154ffbf91a5dcc0e539dd2d5106');
INSERT INTO `ex_pwd` VALUES ('1114734231340224512', '123', '202cb962ac59075b964b07152d234b70');
INSERT INTO `ex_pwd` VALUES ('1114863169689382912', '1111111111', 'e11170b8cbd2d74102651cb967fa28e5');
INSERT INTO `ex_pwd` VALUES ('1114880900740530176', '92a35d8b542d4b11a6430f4db05c7e8d', 'd593db4e52fa9ddca35ad15f2e5ed1bc');
INSERT INTO `ex_pwd` VALUES ('1114888479600394240', '14b3e6ee522a0e8bcd8b4f2397b7ec56', 'c060caa5730b537e3f1de90d8629d177');
INSERT INTO `ex_pwd` VALUES ('1114890104738004992', '06ffbfe2d6d73a12d89537dcbda17397', 'f1bc93abb939c6f1c4b1a5de9e262b3c');

-- ----------------------------
-- Table structure for ex_question
-- ----------------------------
DROP TABLE IF EXISTS `ex_question`;
CREATE TABLE `ex_question`  (
  `question_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `question_title` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题干',
  `question_img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目图片',
  `question_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属题型',
  `question_score` decimal(3, 1) NULL DEFAULT NULL COMMENT '分值',
  `question_difficulty` int(11) NOT NULL COMMENT '难度系数',
  `question_bank` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属题库',
  `question_style` int(11) NULL DEFAULT NULL COMMENT '题目类型，1选择，2多选，3判断，4填空，5编程，6其他',
  `question_know` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点',
  `question_resolve` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '解析',
  `question_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `question_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0删除1正常',
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '其他题型' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_question
-- ----------------------------
INSERT INTO `ex_question` VALUES ('1119145243728453632', '测试', '', '1111960837909667840', 2.0, 2, '1111913413874835456', 6, '1117389471029940224', '解析', 6, 0);
INSERT INTO `ex_question` VALUES ('1119193350415622144', '测试题目', '', '1111960837909667840', 3.0, 3, '1111913413874835456', 6, '1117389471029940224', '我是解析', 1, 1);
INSERT INTO `ex_question` VALUES ('1119193460629348352', '测试题目', '', '1111960837909667840', 9.0, 3, '1111913413874835456', 6, '1117389471029940224', '我是解析', 1, 1);
INSERT INTO `ex_question` VALUES ('1119197064438276096', '由于找不到什么好题目，所以就找马克思主义原理的题目啦。那么请回答下面三题', '', '1111960837909667840', 20.0, 5, '1111913413874835456', 6, '1117389471029940224', '就是这样，我也看不懂，反正内容是不少了', 1, 1);

-- ----------------------------
-- Table structure for ex_question_answer
-- ----------------------------
DROP TABLE IF EXISTS `ex_question_answer`;
CREATE TABLE `ex_question_answer`  (
  `answer_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `answer_number` int(3) NULL DEFAULT NULL COMMENT '小题编号',
  `answer_problem` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '问题内容',
  `answer_content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '答案',
  `answer_score` decimal(3, 1) NOT NULL COMMENT '分值',
  `answer_question` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目id',
  `answer_resolve` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '解析',
  `answer_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `answer_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0删除1正常',
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '其他题目答案' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_question_answer
-- ----------------------------
INSERT INTO `ex_question_answer` VALUES ('1119145243732647936', 1, '第一小题', '答案', 2.0, '1119145243728453632', NULL, 6, 1);
INSERT INTO `ex_question_answer` VALUES ('1119167103283277824', 2, '第二', '答案2', 0.0, '1119145243728453632', NULL, 1, 1);
INSERT INTO `ex_question_answer` VALUES ('1119193350424010752', 1, '第一小题', '第一小题答案', 2.0, '1119193350415622144', NULL, 1, 1);
INSERT INTO `ex_question_answer` VALUES ('1119193350424010753', 2, '第二小题', '第二小题答案', 1.0, '1119193350415622144', NULL, 1, 1);
INSERT INTO `ex_question_answer` VALUES ('1119193460629348353', 1, '第一小题', '第一小题答案', 2.0, '1119193460629348352', NULL, 1, 1);
INSERT INTO `ex_question_answer` VALUES ('1119193460629348354', 2, '第二小题', '第二小题答案', 1.0, '1119193460629348352', NULL, 1, 1);
INSERT INTO `ex_question_answer` VALUES ('1119193460629348355', 3, '哈哈', '2123', 4.0, '1119193460629348352', NULL, 1, 1);
INSERT INTO `ex_question_answer` VALUES ('1119193460629348356', 4, '啊啊', '啊实打实', 2.0, '1119193460629348352', NULL, 1, 1);
INSERT INTO `ex_question_answer` VALUES ('1119197064446664704', 1, '试述垄断资本主义国家的宏观经济管理与调控', '按照马克思主义的观点来考察资本主义国家干预经济的问题,归根到底是社会生产力的发展,社会化大生产在资本主义制度内部存在和发展的内在要求,使得资本主义国家必须在一定程度上承担起管理与调节社会经济生活的任务。同时,从市场经济运行条件的角度来看,一些私有制市场经济主体所不愿或不能承担的社会经济任务,也必须由国家来完成。', 6.0, '1119197064438276096', NULL, 1, 1);
INSERT INTO `ex_question_answer` VALUES ('1119197064446664705', 2, '试述垄断资本主义在世界范围内的扩展。', '私人垄断资本主义阶段,垄断资本的统治在其国内不断发展,并积极向外扩张势力,对不发达国家和地区实行经济乃至政治、军事等多方面的侵略和扩张,剥削、奴役和掠夺这些国家和地区的人民,以实现、垄断资本在国外的统治。国家垄断资本主义阶段,垄断资本在世界范围的扩展规模更大,形式更加多样,建立起发达资本主义国家主导的世界经济政治体系。', 6.0, '1119197064438276096', NULL, 1, 1);
INSERT INTO `ex_question_answer` VALUES ('1119197064446664706', 3, '简述20世纪80年代以来经济全球化快速发展的原因。', '(1)新科学技术,特别是计算机、通信技术日新月异的进步和在社会经济生活中的广泛应用,加强了国际经济联系。\n\n(2)国际贸易的自由程度大大提高。\n\n(3)国际资本流动的大幅增加。', 8.0, '1119197064438276096', NULL, 1, 1);

-- ----------------------------
-- Table structure for ex_role
-- ----------------------------
DROP TABLE IF EXISTS `ex_role`;
CREATE TABLE `ex_role`  (
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `role_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `role_father` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级角色',
  `role_index` int(11) NULL DEFAULT NULL COMMENT '排序字段',
  `role_comment` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `role_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `role_delete` int(11) NOT NULL DEFAULT 1 COMMENT '1正常0删除',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_role
-- ----------------------------
INSERT INTO `ex_role` VALUES ('1', '技术部', NULL, 1, '技术部', 1, 1);
INSERT INTO `ex_role` VALUES ('1114087209218109440', '测试', '5', 2, '1', 2, 1);
INSERT INTO `ex_role` VALUES ('1114087251429584896', '第一', '5', 1, '1', 2, 1);
INSERT INTO `ex_role` VALUES ('2', 'java', '1', 2, NULL, 1, 1);
INSERT INTO `ex_role` VALUES ('3', '前端初级', '1', 3, NULL, 1, 1);
INSERT INTO `ex_role` VALUES ('4', 'java初级', '2', 4, NULL, 1, 1);
INSERT INTO `ex_role` VALUES ('5', '业务部', NULL, 5, '业务部', 1, 1);
INSERT INTO `ex_role` VALUES ('6', '业务员', '5', 6, NULL, 1, 1);
INSERT INTO `ex_role` VALUES ('8', 'java高级', '2', 8, NULL, 1, 1);

-- ----------------------------
-- Table structure for ex_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `ex_role_auth`;
CREATE TABLE `ex_role_auth`  (
  `ra_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `ra_role` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `ra_auth` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  PRIMARY KEY (`ra_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色-权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ex_student
-- ----------------------------
DROP TABLE IF EXISTS `ex_student`;
CREATE TABLE `ex_student`  (
  `stu_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stu_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学号',
  `stu_password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `stu_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `stu_sex` int(11) NOT NULL COMMENT '1男2女',
  `stu_age` int(11) NOT NULL COMMENT '年龄',
  `stu_college` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学院，数据字典id',
  `stu_major` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '专业，数据字典id',
  `stu_img` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片',
  `stu_card` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证号码',
  `stu_entrance_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入学时间',
  `stu_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `stu_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0删除1正常',
  PRIMARY KEY (`stu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_student
-- ----------------------------
INSERT INTO `ex_student` VALUES ('1114880900740530176', '13544545', 'd593db4e52fa9ddca35ad15f2e5ed1bc', '闺女给的', 2, 18, '1111164554571681792', '1111211629313277952', 'http://tn20898453.51mypc.cn/file/1c3934809b784ecca973e8f6c38854ca.jpg', '45645445645', '2019-04-10', 4, 1);
INSERT INTO `ex_student` VALUES ('1114888479600394240', '16090504070', 'c060caa5730b537e3f1de90d8629d177', '道理', 1, 3, '1111164554571681792', '1111211697013538816', 'http://tn20898453.51mypc.cn/file/f972c287484643d4a8f4ebf2cca3a04c.jpg', '411303111111111111', '2019-04-01', 2, 1);
INSERT INTO `ex_student` VALUES ('1114890104738004992', '1609422222', 'f1bc93abb939c6f1c4b1a5de9e262b3c', '朕乃陆小凤', 1, 6, '1111164855676571648', '1114751786427650048', 'http://tn20898453.51mypc.cn/file/d0d14ed617b449e78bc12bb13d10169a.jpg', '1233555777777777', '2019-04-09', 2, 1);

-- ----------------------------
-- Table structure for ex_teacher
-- ----------------------------
DROP TABLE IF EXISTS `ex_teacher`;
CREATE TABLE `ex_teacher`  (
  `teacher_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教师id',
  `teacher_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工号',
  `teacher_username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `teacher_password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `teacher_card` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `teacher_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `teacher_sex` int(11) NOT NULL COMMENT '性别，1男2女',
  `teacher_age` int(11) NOT NULL COMMENT '年龄',
  `teacher_job` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职务，数据字典id',
  `teacher_title` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职称，数据字典id',
  `teacher_mobile` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `teacher_email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `teacher_img` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片',
  `teacher_entry_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入职时间',
  `teacher_college` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学院，数据字典id',
  `teacher_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `teacher_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0删除1正常',
  PRIMARY KEY (`teacher_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教师表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_teacher
-- ----------------------------
INSERT INTO `ex_teacher` VALUES ('1', '12345678', 'admin', '21232f297a57a5a743894a0e4a801fc3', '34152487645316478', '超级管理员', 1, 35, '1', '1', '18296666666', '435156413@qq.com', 'http://tn20898453.51mypc.cn/file/d7b27e081c5849948a5baddd9d4196ee.jpg', '2019-03-31', '1', 1, 1);
INSERT INTO `ex_teacher` VALUES ('1113421334412533760', '11111', '11111', 'c4ca4238a0b923820dcc509a6f75849b', '1', '咕哒子', 2, 1, '1111223441308008448', '1111223574124838912', '1', '1', 'http://tn20898453.51mypc.cn/file/d7b27e081c5849948a5baddd9d4196ee.jpg', '2019-04-15', '1111173174202060800', 7, 1);
INSERT INTO `ex_teacher` VALUES ('1114492785756483584', '12121', '12121', 'de872154ffbf91a5dcc0e539dd2d5106', '31232131', '呆毛王', 1, 12, '1111223441308008448', '1111223591413760000', '123213', '123123', 'http://tn20898453.51mypc.cn/file/b938216f4cfb4b7d9b84a87aefd7c158.jpg', '2019-04-06', '1111164554571681792', 2, 1);
INSERT INTO `ex_teacher` VALUES ('1114734231340224512', '1609', '1609', '202cb962ac59075b964b07152d234b70', '411303', '沈万三', 1, 22, '1111223476665991168', '1111223649802665984', '13525', '2057', 'http://tn20898453.51mypc.cn/file/218d060bed3941fcb620e8a79ce966bb.jpg', '2019-04-16', '1111164554571681792', 2, 1);

-- ----------------------------
-- Table structure for ex_teacher_auth
-- ----------------------------
DROP TABLE IF EXISTS `ex_teacher_auth`;
CREATE TABLE `ex_teacher_auth`  (
  `ua_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `ua_teacher` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教师id',
  `ua_auth` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id',
  PRIMARY KEY (`ua_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教师-权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ex_teacher_role
-- ----------------------------
DROP TABLE IF EXISTS `ex_teacher_role`;
CREATE TABLE `ex_teacher_role`  (
  `tr_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `tr_teacher` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教师id',
  `tr_role` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  PRIMARY KEY (`tr_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教师-角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_teacher_role
-- ----------------------------
INSERT INTO `ex_teacher_role` VALUES ('1114472066330976256', '1', '4');
INSERT INTO `ex_teacher_role` VALUES ('1114472066330976257', '1', '1');
INSERT INTO `ex_teacher_role` VALUES ('1114472066330976258', '1', '2');
INSERT INTO `ex_teacher_role` VALUES ('1114472066330976259', '1', '8');
INSERT INTO `ex_teacher_role` VALUES ('1114472066330976260', '1', '3');

-- ----------------------------
-- Table structure for ex_true_false
-- ----------------------------
DROP TABLE IF EXISTS `ex_true_false`;
CREATE TABLE `ex_true_false`  (
  `tf_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `tf_title` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目内容',
  `tf_score` decimal(3, 1) NOT NULL DEFAULT 0.0 COMMENT '分值',
  `tf_difficulty` int(11) NOT NULL DEFAULT 0 COMMENT '难度系数',
  `tf_bank` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属题库',
  `tf_resolve` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '解析',
  `tf_true` int(11) NOT NULL COMMENT '是否正确，1正确0错误',
  `tf_know` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识点id',
  `tf_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `tf_delete` int(11) NOT NULL DEFAULT 1 COMMENT '1正常0删除',
  PRIMARY KEY (`tf_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '判断题表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_true_false
-- ----------------------------
INSERT INTO `ex_true_false` VALUES ('1118077112897540096', '测试2', 1.0, 3, '1111913413874835456', '测', 0, '1117389793030852608', 4, 1);
INSERT INTO `ex_true_false` VALUES ('1118102798962036736', '哈哈哈', 2.0, 1, '1111913413874835456', '23132', 0, '1117389793030852608', 2, 1);
INSERT INTO `ex_true_false` VALUES ('1118102845560754176', 'emmmm', 1.0, 1, '1111913413874835456', '23132', 0, '1117389793030852608', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120278433260167168', 'Java语言的主要贡献者是James Gosling', 2.0, 1, '1111913413874835456', NULL, 1, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120278477291970560', 'Java语言是1995年5月Sun公司推出的编程语言', 2.0, 1, '1111913413874835456', NULL, 1, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120278555796758528', '\nJava语言是1995年5月IBM司推出的编程语言答案', 2.0, 1, '1111913413874835456', NULL, 0, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120278648323104768', '开发Java应用程序的基本步骤是：\n1 编写源文件，\n2.编译源文件，\n3.运行程序。', 2.0, 1, '1111913413874835456', NULL, 1, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120278841965731840', 'Java源文件是由若干个书写形式互相独立的类组成。', 2.0, 1, '1111913413874835456', NULL, 1, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120278875096539136', 'Java源文件中只能有一个类', 2.0, 3, '1111913413874835456', NULL, 1, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120278912119660544', '如果源文件中有多个类，那么至多有一个类可以是public类', 2.0, 3, '1111913413874835456', NULL, 1, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120279382951256064', '如果源文件中有多个类，那么必须要有一个类是public类', 2.0, 5, '1111913413874835456', NULL, 0, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120281437153583104', '如果源文件中有多个类，这些类可以都不是public类', 2.0, 4, '1111913413874835456', NULL, 1, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120281504761569280', '如果源文件中只有一个类，这个类必须是public类', 2.0, 4, '1111913413874835456', NULL, 0, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120281544838144000', '如果源文件中只有一个类，这个类必须是主类', 2.0, 2, '1111913413874835456', NULL, 0, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120282534580641792', 'java源文件中可以没有主类', 2.0, 2, '1111913413874835456', NULL, 1, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120282602603864064', 'java源文件中可以没有public类', 2.0, 2, '1111913413874835456', NULL, 1, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120282723324321792', 'java源文件的扩展名是.java', 2.0, 2, '1111913413874835456', NULL, 1, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120282768480198656', 'java源文件的扩展名是.class', 2.0, 2, '1111913413874835456', NULL, 0, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120282822842572800', '编译java源文件得到的字节码文件的扩展名是.class', 2.0, 2, '1111913413874835456', NULL, 1, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120282868128473088', 'java应用程序的主类必须是public类', 2.0, 5, '1111913413874835456', NULL, 0, '1117389471029940224', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120283283704307712', '\"\\hello\"是正确的字符串常量', 2.0, 5, '1111913413874835456', NULL, 0, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120283323948654592', '\"\\\\hello\"是正确的字符串常量', 2.0, 2, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120283362037129216', '\"\\nhello\"是正确的字符串常量', 2.0, 2, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120283426818154496', '表达式\"Hello\".equals(\"hello\")的值时true', 2.0, 4, '1111913413874835456', NULL, 0, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120283469235150848', '表达式\"java\".equals(\"java\")的值是true', 2.0, 3, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120283508040851456', '表达式\"Bird\".compareTo(\"Bird fly\")的值是负数', 2.0, 3, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120283540030808064', '表达式\"I love this game\".contains(\"love\")的值是true', 2.0, 3, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120283592061149184', '表达式\"RedBird\".indexOf(\"Bird\")的值是4', 2.0, 3, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120283648692641792', '表达式\"RedBird\".indexOf(\"Bird\")的值是3', 2.0, 3, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120283677796917248', '\n表达式\"RedBird\".indexOf(\"Cat\")的值是-1', 2.0, 3, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120283713645633536', 'Integer.parseInt(\"12.9\");会触发NumberFormatException异常', 2.0, 3, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120283749808922624', '表达式\"bird\".contentEquals(\"bird\")的值是true', 2.0, 2, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120285064047943680', '表达式\"Bird\".contentEquals(\"bird\")的值是false', 2.0, 2, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120285095635247104', '表达式\"Bird\".equalsIgnoreCase(\"bird\")的值是true', 2.0, 2, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120285122462015488', '表达式\"D:/java/book/E.java\".lastIndexOf(\"/\")的值是12', 2.0, 2, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120285159640326144', '表达式\"89762.34\".matches(\"[0-9.]+\")的值是true', 2.0, 2, '1111913413874835456', NULL, 0, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120285209229582336', '表达式 new String(\"abc\")== \"abc\"的值是true', 2.0, 5, '1111913413874835456', NULL, 0, '1117389727968808960', 1, 1);
INSERT INTO `ex_true_false` VALUES ('1120285238623264768', '表达式 new String(\"abc\")== \"abc\"的值是false', 2.0, 1, '1111913413874835456', NULL, 1, '1117389727968808960', 1, 1);

-- ----------------------------
-- Table structure for ex_type
-- ----------------------------
DROP TABLE IF EXISTS `ex_type`;
CREATE TABLE `ex_type`  (
  `type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题型id',
  `type_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题型名称',
  `type_version` int(11) NOT NULL DEFAULT 1 COMMENT '乐观锁',
  `type_delete` int(11) NOT NULL DEFAULT 1 COMMENT '0删除1正常',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '题型表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ex_type
-- ----------------------------
INSERT INTO `ex_type` VALUES ('1', '单项选择题', 1, 1);
INSERT INTO `ex_type` VALUES ('1111960837909667840', '简答题', 1, 1);
INSERT INTO `ex_type` VALUES ('1111970261143412736', '论述题', 3, 1);
INSERT INTO `ex_type` VALUES ('1120290556442894336', '选择题', 2, 0);
INSERT INTO `ex_type` VALUES ('2', '多项选择题', 1, 1);
INSERT INTO `ex_type` VALUES ('3', '判断题', 1, 1);
INSERT INTO `ex_type` VALUES ('4', '填空题', 1, 1);
INSERT INTO `ex_type` VALUES ('5', '编程题', 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
