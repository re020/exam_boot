-- MySQL dump 10.13  Distrib 5.5.49, for Win32 (x86)
--
-- Host: localhost    Database: exam_boot
-- ------------------------------------------------------
-- Server version	5.5.49

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ex_auth`
--

DROP TABLE IF EXISTS `ex_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_auth` (
  `auth_id` varchar(32) NOT NULL COMMENT '权限id',
  `auth_name` varchar(32) NOT NULL DEFAULT '' COMMENT '权限名，用于展示',
  `auth_code` varchar(32) NOT NULL DEFAULT '' COMMENT '权限代码，用于后台写代码用',
  `auth_father` varchar(32) NOT NULL DEFAULT '' COMMENT '父级权限',
  `auth_index` int(11) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `auth_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `auth_delete` int(11) NOT NULL DEFAULT '1' COMMENT '1正常0删除',
  PRIMARY KEY (`auth_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_auth`
--

LOCK TABLES `ex_auth` WRITE;
/*!40000 ALTER TABLE `ex_auth` DISABLE KEYS */;
INSERT INTO `ex_auth` VALUES ('0','所有权限','all','',0,1,1),('1','题库管理','bank','0',1,1,1),('11','添加题库','bank:add','1',11,1,1),('12','修改题库','bank:update','1',12,1,1),('13','删除题库','bank:delete','1',13,1,1),('14','查看题库','bank:list','1',14,1,1),('15','知识点管理','know','1',15,1,1),('151','查看知识点','know:list','15',151,1,1),('152','添加知识点','know:add','15',152,1,1),('153','修改知识点','know:update','15',153,1,1),('154','删除知识点','know:delete','15',154,1,1),('155','知识点题型','know:type','15',155,1,1),('1551','查看题型','know:type:list','155',1551,1,1),('1552','分配题型','know:type:add','155',1552,1,1),('1553','移除题型','know:type:delete','155',1553,1,1),('1554','题目管理','question','155',1554,1,1),('15541','查看题目','question:list','1554',15541,1,1),('15542','添加题目','question:add','1554',15542,1,1),('15543','删除题目','question:delete','1554',15543,1,1),('15544','修改题目','question:update','1554',15544,1,1),('2','题型管理','type','0',2,1,1),('21','添加题型','type:add','2',21,1,1),('22','修改题型','type:update','2',22,1,1),('23','查看题目','type:list','2',23,1,1),('24','删除题目','type:delete','2',24,1,1),('3','用户管理','user','0',3,1,1),('31','教师管理','user:teacher','3',31,1,1),('311','添加教师','user:teacher:add','31',311,1,1),('312','修改教师','user:teacher:update','31',312,1,1),('313','删除教师','user:teacher:delete','31',313,1,1),('314','查看教师','user:teacher:list','31',314,1,1),('315','修改角色','user:teacher:role','31',315,1,1),('32','学生管理','user:student','3',32,1,1),('321','添加学生','user:student:add','32',321,1,1),('322','查看学生','user:student:list','32',322,1,1),('323','修改学生','user:student:update','32',323,1,1),('324','删除学生','user:student:delete','32',324,1,1),('4','试卷管理','paper','0',4,1,1),('41','创建试卷','paper:add','4',41,1,1),('42','修改试卷','paper:update','4',42,1,1),('43','删除试卷','paper:delete','4',43,1,1),('44','查看试卷','paper:list','4',44,1,1),('45','组卷','paper:submit','4',45,1,1),('5','系统管理','sys','0',5,1,1),('51','学院管理','sys:college','5',51,1,1),('511','添加学院','sys:college:add','51',511,1,1),('512','查看学院','sys:college:list','51',512,1,1),('513','修改学院','sys:college:update','51',513,1,1),('514','删除学院','sys:college:delete','51',514,1,1),('52','专业管理','sys:major','5',52,1,1),('521','添加专业','sys:major:add','52',521,1,1),('522','查看专业','sys:major:list','52',522,1,1),('523','修改专业','sys:major:update','52',523,1,1),('524','删除专业','sys:major:delete','52',524,1,1),('53','职务管理','sys:job','5',53,1,1),('531','添加职务','sys:job:add','53',531,1,1),('532','查看职务','sys:job:list','53',532,1,1),('533','修改职务','sys:job:update','53',533,1,1),('534','删除职务','sys:job:delete','53',534,1,1),('54','职称管理','sys:title','5',54,1,1),('541','添加职称','sys:title:add','54',541,1,1),('542','查看职称','sys:title:list','54',542,1,1),('543','修改职称','sys:title:update','54',543,1,1),('544','删除职称','sys:title:delete','54',544,1,1),('55','科目管理','sys:subject','5',55,1,1),('551','添加科目','sys:subject:add','55',551,1,1),('552','查看科目','sys:subject:list','55',552,1,1),('553','修改科目','sys:subject:update','55',553,1,1),('554','删除科目','sys:subject:delete','55',554,1,1),('6','角色权限管理','ar','0',6,1,1),('61','查看权限','ar:auth:list','6',61,1,1),('62','角色管理','ar:role','6',62,1,1),('621','查看角色','ar:role:list','62',621,1,1),('622','添加角色','ar:role:add','62',622,1,1),('623','修改角色','ar:role:update','62',623,1,1),('624','删除角色','ar:role:delete','62',624,1,1),('625','分配权限','ar:role:auth','62',625,1,1);
/*!40000 ALTER TABLE `ex_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_bank`
--

DROP TABLE IF EXISTS `ex_bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_bank` (
  `bank_id` varchar(32) NOT NULL COMMENT 'id',
  `bank_name` varchar(64) NOT NULL COMMENT '题库名称',
  `bank_img` varchar(128) NOT NULL COMMENT '图片',
  `bank_college` varchar(32) NOT NULL COMMENT '学院，数据字典id',
  `bank_subject` varchar(32) NOT NULL COMMENT '科目。数据字典id',
  `bank_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `bank_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0正常1删除',
  PRIMARY KEY (`bank_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='题库表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_bank`
--

LOCK TABLES `ex_bank` WRITE;
/*!40000 ALTER TABLE `ex_bank` DISABLE KEYS */;
INSERT INTO `ex_bank` VALUES ('1111632855139622912','MySQL','http://tn20898453.51mypc.cn/file/a1f9fae597784506937ef61ed027c64e.jpg','1111164554571681792','1114754695726927872',10,1),('1111912642504581120','Vue.js前端开发','http://tn20898453.51mypc.cn/file/1007b32c37ac4d31841dcfadf08443fb.jpg','1111164554571681792','1114753044974702592',5,1),('1111913413874835456','JavaSe程序设计','http://tn20898453.51mypc.cn/file/7ac30415dbfd4afca7d4a1819f24ba0c.jpg','1111164554571681792','1111243864053760000',8,1),('1111917882515705856','JavaEE企业级开发技术','http://tn20898453.51mypc.cn/file/6313fa1a54f24bd29c179b7f3f01818e.jpg','1111164554571681792','1111243864053760000',5,1),('1114753665597476864','概论（一）','http://tn20898453.51mypc.cn/file/d9c7337fec114f04b24d91948c2f203b.jpg','1111171604940967936','1114753254572462080',2,1);
/*!40000 ALTER TABLE `ex_bank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_bank_knowledge`
--

DROP TABLE IF EXISTS `ex_bank_knowledge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_bank_knowledge` (
  `know_id` varchar(32) NOT NULL,
  `know_name` varchar(128) NOT NULL COMMENT '知识点名',
  `know_bank` varchar(32) NOT NULL COMMENT '题库id',
  `know_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `know_delete` int(11) NOT NULL DEFAULT '1' COMMENT '1正常0删除',
  PRIMARY KEY (`know_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='知识点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_bank_knowledge`
--

LOCK TABLES `ex_bank_knowledge` WRITE;
/*!40000 ALTER TABLE `ex_bank_knowledge` DISABLE KEYS */;
INSERT INTO `ex_bank_knowledge` VALUES ('1117389471029940224','Java基础知识','1111913413874835456',3,1),('1117389538126221312','三大结构','1111913413874835456',1,1),('1117389655386378240','面向对象','1111913413874835456',1,1),('1117389707320250368','类与接口','1111913413874835456',1,1),('1117389727968808960','常用类','1111913413874835456',1,1),('1117389756901117952','正则表达式','1111913413874835456',1,1),('1117389793030852608','集合','1111913413874835456',1,1),('1117389819777929216','文件操作与IO流','1111913413874835456',1,1),('1117389851184877568','反射','1111913413874835456',1,1),('1117389881937514496','多线程','1111913413874835456',1,1),('1117389900388257792','网络编程','1111913413874835456',1,1),('1117389913839390720','GUI','1111913413874835456',1,1);
/*!40000 ALTER TABLE `ex_bank_knowledge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_bank_type`
--

DROP TABLE IF EXISTS `ex_bank_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_bank_type` (
  `id` varchar(32) NOT NULL,
  `bank_id` varchar(32) NOT NULL COMMENT '题库id',
  `bank_type` varchar(32) NOT NULL COMMENT '题型id',
  `bank_know` varchar(32) NOT NULL COMMENT '知识点id',
  `bank_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `bank_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='题库-题型对应表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_bank_type`
--

LOCK TABLES `ex_bank_type` WRITE;
/*!40000 ALTER TABLE `ex_bank_type` DISABLE KEYS */;
INSERT INTO `ex_bank_type` VALUES ('1117390081330532352','1111913413874835456','2','1117389793030852608',1,1),('1118061106913345536','1111913413874835456','1','1117389793030852608',1,1),('1118061121291419648','1111913413874835456','3','1117389793030852608',1,1),('1118104670640836608','1111913413874835456','4','1117389793030852608',1,1),('1118848825591603200','1111913413874835456','1111960837909667840','1117389471029940224',1,1),('1119108213132849152','1111913413874835456','1','1117389471029940224',1,1),('1119229036275490816','1111913413874835456','5','1117389471029940224',1,1),('1120194843872649216','1111913413874835456','3','1117389471029940224',1,1),('1120194857009209344','1111913413874835456','4','1117389471029940224',1,1),('1120194952903581696','1111913413874835456','5','1117389793030852608',1,1),('1120195725674733568','1111913413874835456','1','1117389538126221312',1,1),('1120195739306221568','1111913413874835456','3','1117389538126221312',1,1),('1120195749653569536','1111913413874835456','4','1117389538126221312',1,1),('1120195770142744576','1111913413874835456','5','1117389538126221312',1,1),('1120196127514222592','1111913413874835456','1','1117389655386378240',1,1),('1120196141229596672','1111913413874835456','4','1117389655386378240',1,1),('1120196148326359040','1111913413874835456','3','1117389655386378240',1,1),('1120283207053402112','1111913413874835456','3','1117389727968808960',1,1),('1120285304721301504','1111913413874835456','1','1117389727968808960',1,1),('1120291373291986944','1111913413874835456','1120290556442894336','1117389471029940224',1,0);
/*!40000 ALTER TABLE `ex_bank_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_choice`
--

DROP TABLE IF EXISTS `ex_choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_choice` (
  `choice_id` varchar(32) NOT NULL COMMENT '题目id',
  `choice_title` mediumtext NOT NULL COMMENT '题干',
  `choice_type` int(11) NOT NULL COMMENT '1单选2多选3判断',
  `choice_score` decimal(3,1) NOT NULL COMMENT '分值',
  `choice_difficulty` int(11) NOT NULL COMMENT '难度系数',
  `choice_bank` varchar(32) NOT NULL COMMENT '所属题库',
  `choice_know` varchar(32) NOT NULL COMMENT '所属知识点',
  `choice_resolve` mediumtext COMMENT '解析',
  `choice_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `choice_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`choice_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='选择题表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_choice`
--

LOCK TABLES `ex_choice` WRITE;
/*!40000 ALTER TABLE `ex_choice` DISABLE KEYS */;
INSERT INTO `ex_choice` VALUES ('1117390639416233984','ArrayList类的底层数据结构是',2,2.0,1,'1111913413874835456','1117389793030852608','无',8,1),('1117390973396078592','LinkedList类的特点是',2,2.0,1,'1111913413874835456','1117389793030852608','',1,1),('1117391096586981376','Vector类的特点是',2,2.0,3,'1111913413874835456','1117389793030852608','',1,1),('1117391221375913984','关于迭代器说法错误的是',2,2.0,4,'1111913413874835456','1117389793030852608','',1,1),('1117391336345980928','实现下列哪个接口，可以启用比较功能',2,2.0,4,'1111913413874835456','1117389793030852608','',1,1),('1117391708556906496','下面代码运行的结果是(     )\n\nArrayList<String> al = newArrayList<String>();\n\nal.add(true);\n\nal.add(123);\n\nal.add(“abc”);\n\nSystem.out.println(al);',2,2.0,1,'1111913413874835456','1117389793030852608','',1,1),('1117391831835889664','ArrayList和Vector的区别说法正确的是',2,2.0,2,'1111913413874835456','1117389793030852608','',1,1),('1119116960571899904','测试',1,2.0,1,'1111913413874835456','1117389471029940224','啊啊',1,0),('1120197515501690880','JAVA所定义的版本中不包括',1,2.0,1,'1111913413874835456','1117389471029940224','',1,1),('1120197674755219456','下列说法正确的是',1,2.0,1,'1111913413874835456','1117389471029940224','',1,1),('1120197891026116608','Java中，在如下所示的Test类中，共有（）个构造方法\npublic class Test{\n\nprivate int x;\n\npublic Test(){\n\nx=35;\n\n}\n\npublic void Test(double f){\n\nThis.x=(int)f;\n\n}\n\npublic Test(String s){}\n\n}',1,2.0,3,'1111913413874835456','1117389471029940224','',1,1),('1120208673830920192','变量命名规范说法正确的是',1,2.0,3,'1111913413874835456','1117389471029940224','',1,1),('1120208825035579392','下列javaDoc注释正确的是',1,2.0,3,'1111913413874835456','1117389471029940224','',1,1),('1120209308634636288','为一个boolean类型变量赋值时，可以使用()方式',1,2.0,2,'1111913413874835456','1117389471029940224','',2,1),('1120209794326650880','以下()不是合法的标识符',1,2.0,1,'1111913413874835456','1117389471029940224','',1,1),('1120209887758966784','表达式(11+3*8)/4%3的值是',1,2.0,4,'1111913413874835456','1117389471029940224','',1,1),('1120285711522652160','下列哪个叙述是正确的？',1,2.0,3,'1111913413874835456','1117389727968808960','',1,1),('1120286561477386240','下列哪个是正确的？',1,2.0,4,'1111913413874835456','1117389727968808960','',1,1),('1120286885357346816','对于如下代码，下列哪个叙述是正确的？\npublic class E{ \n   public static void main(String[] args){ \n      String strOne=\"bird\"; \n      String strTwo=strOne; \n      strOne=\"fly\"; \n      System.out.println(strTwo); \n  } \n}',1,2.0,5,'1111913413874835456','1117389727968808960','',1,1),('1120287156120641536','对于如下代码，下列哪个叙述是正确的？\npublic class E { \n   public static void main(String args[]){ \n      String strOne = new String(\"你好\"); \n      String strTwo = strOne; \n      strOne = new String(\"hello\");\n      System.out.println(strTwo); \n   }\n}',1,2.0,5,'1111913413874835456','1117389727968808960','',2,1),('1120288228037943296','对于如下代码，下列哪个叙述是正确的？\npublic class E {\n   public static void main (String args[]) {\n     String s0 = args[0];\n     String s1 = args[1];\n     String s2 = args[2];\n     System.out.println(s2); \n   }\n}',1,2.0,3,'1111913413874835456','1117389727968808960','',1,1),('1120288402831368192','下列哪个叙述是错误的？',1,2.0,4,'1111913413874835456','1117389727968808960','',1,1),('1120288594594947072','下列哪个叙述是错误的？',1,2.0,2,'1111913413874835456','1117389727968808960','',1,1),('1120289459158441984','下列哪个叙述是错误的？',1,2.0,4,'1111913413874835456','1117389727968808960','',1,1),('1120296762054074368','下列哪个是ＪＤＫ提供的编译器？',1,2.0,2,'1111913413874835456','1117389471029940224','',1,1),('1120297939550396416','下列哪个是Java应用程序主类中正确的main方法？',1,2.0,4,'1111913413874835456','1117389471029940224','',1,1),('1120298974176141312','下列哪个叙述是正确的？',1,2.0,3,'1111913413874835456','1117389471029940224','',1,1),('1120300211575513088','下列哪个叙述是正确的？',1,2.0,2,'1111913413874835456','1117389471029940224','',1,1),('1120302113184866304','下列哪个叙述是正确的？',1,2.0,3,'1111913413874835456','1117389471029940224','',2,1),('1120307181401464832','对于下列源文件，哪个叙述是正确的？\npublic class E{\n      public static void main(String []args){\n            System.out.println(\"ok\");\n            System.out.println(\"您好\");\n       }\n}\nclass A{\n    public static void main(String []args){\n          System.out.println(\"ok\");\n          System.out.println(\"您好\");\n       }\n}    \n',1,2.0,2,'1111913413874835456','1117389471029940224','',1,1),('1120308784477036544','下列哪个叙述是正确的？',1,2.0,2,'1111913413874835456','1117389471029940224','',1,1),('1120310026200735744','对于下列源文件，哪个叙述是正确的？\n　public class Cat{\n         public void cry(){\n               System.out.println(\"maiomaio\");\n         }\n}',1,2.0,2,'1111913413874835456','1117389471029940224','',1,1),('1120311600947978240','对于下列源文件，哪个叙述是正确的？\n　public class Cat{\n         public void cry(){\n               System.out.println(\"maiomaio\");\n         }\n}\nclass E{\n    public static void  main(String args[]){\n          System.out.println(\"ok\");\n    }\n}',1,2.0,2,'1111913413874835456','1117389471029940224','',1,1),('1120317167213010944','java程序运行入口的 main方法（即主类的main方法）的返回类型是什么？',1,2.0,3,'1111913413874835456','1117389471029940224','',1,1),('1120319068310659072','在Java中，若要使用一个包中的类时，首先要求对该包进行导入，关键字是什么？',1,2.0,3,'1111913413874835456','1117389471029940224','',1,1),('1120319707497422848','继承是面向对象编程的一个重要特征，它可以降低程序的复杂性并使代码（）。',1,2.0,3,'1111913413874835456','1117389471029940224','',1,1);
/*!40000 ALTER TABLE `ex_choice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_choice_answer`
--

DROP TABLE IF EXISTS `ex_choice_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_choice_answer` (
  `answer_id` varchar(32) NOT NULL COMMENT 'id',
  `answer_number` varchar(3) DEFAULT NULL COMMENT '选项，ABCDEFG',
  `answer_content` mediumtext NOT NULL COMMENT '选项内容',
  `answer_choice` varchar(32) NOT NULL COMMENT '所属选择题id',
  `answer_true` int(11) NOT NULL COMMENT '1正确0错误',
  `answer_resolve` mediumtext COMMENT '解析',
  `answer_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `answer_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='选项表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_choice_answer`
--

LOCK TABLES `ex_choice_answer` WRITE;
/*!40000 ALTER TABLE `ex_choice_answer` DISABLE KEYS */;
INSERT INTO `ex_choice_answer` VALUES ('1116637426526203905','A','312','1116637426526203904',0,NULL,1,1),('1116637426526203906','B','4','1116637426526203904',1,NULL,1,1),('1116637426526203907','C','3213','1116637426526203904',0,NULL,1,1),('1116637426526203908','D','432','1116637426526203904',0,NULL,1,1),('1116639907410898944','A','选项1','1116639907406704640',0,NULL,1,1),('1116639907410898945','B','选项2','1116639907406704640',1,NULL,1,1),('1116639907410898946','C','选项3','1116639907406704640',0,NULL,1,1),('1116639907410898947','D','选项4','1116639907406704640',1,NULL,1,1),('1116639907410898948','E','选项5','1116639907406704640',0,NULL,1,1),('1117390639416233985','A','数组结构','1117390639416233984',1,NULL,8,1),('1117390639416233986','B','链表结构','1117390639416233984',0,NULL,8,1),('1117390639416233987','C','哈希表结构','1117390639416233984',0,NULL,8,1),('1117390973396078593','A','查询快','1117390973396078592',0,NULL,1,1),('1117390973396078594','B','增删快 ','1117390973396078592',1,NULL,1,1),('1117390973396078595','C','元素不重复','1117390973396078592',0,NULL,1,1),('1117390973396078596','D','元素自然排序','1117390973396078592',0,NULL,1,1),('1117391096586981377','A','线程同步','1117391096586981376',1,NULL,1,1),('1117391096586981378','B','线程不同步','1117391096586981376',0,NULL,1,1),('1117391096586981379','C','增删快','1117391096586981376',0,NULL,1,1),('1117391096586981380','D','底层是链表结构','1117391096586981376',0,NULL,1,1),('1117391221375913985','A','迭代器是取出集合元素的方式','1117391221375913984',0,NULL,1,1),('1117391221375913986','B','迭代器的hasNext()方法返回值是布尔类型','1117391221375913984',0,NULL,1,1),('1117391221375913987','C','List集合有特有迭代器','1117391221375913984',0,NULL,1,1),('1117391221375913988','D','next()方法将返回集合中的上一个元素','1117391221375913984',1,NULL,1,1),('1117391336345980929','A','Runnable接口','1117391336345980928',0,NULL,1,1),('1117391336345980930','B','Iterator接口','1117391336345980928',0,NULL,1,1),('1117391336345980931','C','Serializable接口','1117391336345980928',0,NULL,1,1),('1117391336345980932','D','Comparator接口','1117391336345980928',1,NULL,1,1),('1117391708556906497','A','编译失败 ','1117391708556906496',1,NULL,1,1),('1117391708556906498','B','[true,123]','1117391708556906496',0,NULL,1,1),('1117391708556906499','C','[true,123,abc];','1117391708556906496',0,NULL,1,1),('1117391708556906500','D','[abc];','1117391708556906496',0,NULL,1,1),('1117391831835889665','A','ArrayList是线程安全的，Vector是线程不安全','1117391831835889664',0,NULL,1,1),('1117391831835889666','B','ArrayList是线程不安全的，Vector是线程安全的','1117391831835889664',1,NULL,1,1),('1117391831835889667','C','ArrayList底层是数组结构，Vector底层是链表结构','1117391831835889664',0,NULL,1,1),('1117391831835889668','D','ArrayList底层是链表结构，Vector底层是数组结构','1117391831835889664',0,NULL,1,1),('1118480352487149568','D','红黑树结构','1117390639416233984',0,NULL,2,1),('1119116960576094208','A','哈哈','1119116960571899904',0,NULL,1,1),('1119116960576094209','B','啊啊','1119116960571899904',1,NULL,1,1),('1119116960576094210','C','请求','1119116960571899904',0,NULL,1,1),('1119116960576094211','D','嗯嗯','1119116960571899904',0,NULL,1,1),('1120197515505885184','A','JAVA2 EE','1120197515501690880',0,NULL,1,1),('1120197515505885185','B','JAVA2 Card','1120197515501690880',0,NULL,1,1),('1120197515505885186','C','JAVA2 ME','1120197515501690880',0,NULL,1,1),('1120197515505885187','D','JAVA2 HE','1120197515501690880',1,NULL,1,1),('1120197515505885188','E','JAVA2 SE','1120197515501690880',0,NULL,1,1),('1120197674755219457','A','JAVA程序的main方法必须写在类里面','1120197674755219456',1,NULL,1,1),('1120197674755219458','B','JAVA程序中可以有多个main方法','1120197674755219456',0,NULL,1,1),('1120197674755219459','C','JAVA程序中类名必须与文件名一样','1120197674755219456',0,NULL,1,1),('1120197674755219460','D','JAVA程序的main方法中如果只有一条语句，可以不用{}(大括号)括起来','1120197674755219456',0,NULL,1,1),('1120197891026116609','A','0','1120197891026116608',0,NULL,1,1),('1120197891026116610','B','1','1120197891026116608',0,NULL,1,1),('1120197891026116611','C','2','1120197891026116608',1,NULL,1,1),('1120197891026116612','D','3','1120197891026116608',0,NULL,1,1),('1120208673830920193','A','变量由字母、下划线、数字、$符号随意组成','1120208673830920192',0,NULL,1,1),('1120208673830920194','B','变量不能以数字作为开头','1120208673830920192',1,NULL,1,1),('1120208673830920195','C','A和a在java中是同一个变量','1120208673830920192',0,NULL,1,1),('1120208673830920196','D','不同类型的变量，可以起相同的名字','1120208673830920192',0,NULL,1,1),('1120208825035579393','A','/*我爱北京天安门*/','1120208825035579392',0,NULL,1,1),('1120208825035579394','B','//我爱北京天安门*/','1120208825035579392',0,NULL,1,1),('1120208825035579395','C','/**我爱北京天安门*/','1120208825035579392',1,NULL,1,1),('1120208825035579396','D','/*我爱北京天安门**/','1120208825035579392',0,NULL,1,1),('1120209308634636289','A','boolean b = a = (0<=10)','1120209308634636288',0,NULL,2,1),('1120209308634636290','B','boolean a = (9 >= 10)','1120209308634636288',1,NULL,2,1),('1120209308634636291','C','boolean a=\"真\"','1120209308634636288',0,NULL,2,1),('1120209308634636292','D',' boolean a = = false','1120209308634636288',0,NULL,2,1),('1120209794326650881','A','STRING','1120209794326650880',0,NULL,1,1),('1120209794326650882','B','x3x','1120209794326650880',0,NULL,1,1),('1120209794326650883','C','void','1120209794326650880',1,NULL,1,1),('1120209794326650884','D','de$f','1120209794326650880',0,NULL,1,1),('1120209887758966785','A','31','1120209887758966784',0,NULL,1,1),('1120209887758966786','B','0','1120209887758966784',0,NULL,1,1),('1120209887758966787','C','1','1120209887758966784',0,NULL,1,1),('1120209887758966788','D','2','1120209887758966784',1,NULL,1,1),('1120285711522652161','A','String 类是final 类，不可以有子类。','1120285711522652160',1,NULL,1,1),('1120285711522652162','B','String 类在java.util包中。','1120285711522652160',0,NULL,1,1),('1120285711522652163','C','\"abc\"==\"abc\"的值是false .','1120285711522652160',0,NULL,1,1),('1120285711522652164','D','\"abc\".equals(\"Abc\")的值是true答案：A','1120285711522652160',0,NULL,1,1),('1120286561477386241','A','int m =Float.parseFloat(\"567\"); ','1120286561477386240',0,NULL,1,1),('1120286561477386242','B','int m =Short.parseShort(\"567\");','1120286561477386240',1,NULL,1,1),('1120286561477386243','C','byte m =Integer.parseInt(\"2\");','1120286561477386240',0,NULL,1,1),('1120286561477386244','D','float m =Float.parseDouble(\"2.9\");','1120286561477386240',0,NULL,1,1),('1120286885357346817','A','程序编译出现错误。','1120286885357346816',0,NULL,1,1),('1120286885357346818','B','程序标注的【代码】的输出结果是bird。','1120286885357346816',1,NULL,1,1),('1120286885357346819','C','程序标注的【代码】的输出结果是fly。','1120286885357346816',0,NULL,1,1),('1120286885357346820','D','程序标注的【代码】的输出结果是null。 ','1120286885357346816',0,NULL,1,1),('1120287156120641537','A','程序编译出现错误。','1120287156120641536',0,NULL,2,1),('1120287156120641538','B','程序标注的【代码】的输出结果是hello。','1120287156120641536',0,NULL,2,1),('1120287156120641539','C','程序标注的【代码】的输出结果是你好。','1120287156120641536',1,NULL,2,1),('1120287156120641540','D','程序标注的【代码】的输出结果是null。','1120287156120641536',0,NULL,2,1),('1120288228037943297','A','程序出现编译错误。','1120288228037943296',0,NULL,1,1),('1120288228037943298','B','无编译错误，在命令行执行程序：“java E I love this game”，程序输出game。','1120288228037943296',0,NULL,1,1),('1120288228037943299','C','无编译错误，在命令行执行程序：“java E go on”，运行异常:ArrayIndexOutOfBoundsException: 2。','1120288228037943296',1,NULL,1,1),('1120288228037943300','D','无编译错误，在命令行执行程序：“java E you are ok”程序输出you。 ','1120288228037943296',0,NULL,1,1),('1120288402831368193','A','\"9dog\".matches(\"\\\\ddog\")的值是true。','1120288402831368192',0,NULL,1,1),('1120288402831368194','B','\"12hello567\".replaceAll(\"[123456789]+\",\"@\")返回的字符串是@hello@。','1120288402831368192',0,NULL,1,1),('1120288402831368195','C','new Date(1000)对象含有的时间是公元后1000小时的时间','1120288402831368192',1,NULL,1,1),('1120288402831368196','D','\"\\\\hello\\n\"是正确的字符串常量','1120288402831368192',0,NULL,1,1),('1120288594594947073','A','Integer.parseInt(\"12.9\");会触发NumberFormatException异常。','1120288594594947072',0,NULL,1,1),('1120288594594947074','B','表达式\"bird\".contentEquals(\"bird\")的值是true。','1120288594594947072',0,NULL,1,1),('1120288594594947075','C','表达式\"Bird\" == \"bird\"的值是false。','1120288594594947072',0,NULL,1,1),('1120288594594947076','D','new String(\"bird\") == \"bird\"的值是true。','1120288594594947072',1,NULL,1,1),('1120289459158441985','A','表达式\"D:/java/book\".lastIndexOf(\"/\")的值是8。','1120289459158441984',1,NULL,1,1),('1120289459158441986','B','表达式\"3.14\".matches(\"[0-9]+[.]{1}[0-9]+\")的值是true。','1120289459158441984',0,NULL,1,1),('1120289459158441987','C','表达式\"220301200210250286\".startsWith(\"2203\")的值是true。','1120289459158441984',0,NULL,1,1),('1120289459158441988','D','表达式\"220301200210250286\".endsWith(\"286\")的值是true。','1120289459158441984',0,NULL,1,1),('1120296762054074369','A','java.exe','1120296762054074368',0,NULL,1,1),('1120296762054074370','B','javac.exe','1120296762054074368',1,NULL,1,1),('1120296762054074371','C','javap.exe','1120296762054074368',0,NULL,1,1),('1120296762054074372','D','javaw.exe','1120296762054074368',0,NULL,1,1),('1120297939550396417','A','public void main(String args[])','1120297939550396416',0,NULL,1,1),('1120297939550396418','B','static void main(String args[])','1120297939550396416',0,NULL,1,1),('1120297939550396419','C','public static void Main(String args[])','1120297939550396416',0,NULL,1,1),('1120297939550396420','D','public static void main(String args[])','1120297939550396416',1,NULL,1,1),('1120298974176141313','A','Java语言是２００５年５月Sun公司推出的编程语言。','1120298974176141312',0,NULL,1,1),('1120298974176141314','B','Java语言是１９９５年５月ＩＢＭ公司推出的编程语言。','1120298974176141312',0,NULL,1,1),('1120298974176141315','C','Java语言的名字是印度尼西亚一个盛产咖啡的岛名。','1120298974176141312',0,NULL,1,1),('1120298974176141316','D','Java语言的主要贡献者是比尔盖茨。','1120298974176141312',1,NULL,1,1),('1120300211575513089','A','Java源文件是由若干个书写形式互相独立的类组成。','1120300211575513088',1,NULL,1,1),('1120300211575513090','B','Java源文件中只能有一个类。','1120300211575513088',0,NULL,1,1),('1120300211575513091','C','如果源文件中有多个类，那么至少有一个类必须是public类','1120300211575513088',0,NULL,1,1),('1120300211575513092','D','Java源文件的拓展名是.txt。','1120300211575513088',0,NULL,1,1),('1120302113184866305','A','源文件名字必须是A.java。','1120302113184866304',0,NULL,2,1),('1120302113184866306','B','如果源文件中有多个类，这些类可以都不是public类。','1120302113184866304',1,NULL,2,1),('1120302113184866307','C','如果源文件中只有一个类，这个类必须是public类','1120302113184866304',0,NULL,2,1),('1120302113184866308','D','如果源文件中只有一个类，这个类必须是主类','1120302113184866304',0,NULL,2,1),('1120307181401464833','A','源文件名字必须是A.java。','1120307181401464832',0,NULL,1,1),('1120307181401464834','B','源文件有错误。','1120307181401464832',0,NULL,1,1),('1120307181401464835','C','源文件必须命名为E.java，编译无错误。有两个主类，E和A，程序可以执行主类E也可以执行主类A。','1120307181401464832',1,NULL,1,1),('1120307181401464836','D','如果源文件中只有一个类，这个类必须是主类','1120307181401464832',0,NULL,1,1),('1120308784477036545','A','JDK的全称是Java Development Kit。','1120308784477036544',1,NULL,1,1),('1120308784477036546','B','可以用Microsoft Word 2010编写java源文件','1120308784477036544',0,NULL,1,1),('1120308784477036547','C','不用NetBean或MyEclipse就无法开发Java程序','1120308784477036544',0,NULL,1,1),('1120308784477036548','D','为了运行Java程序，必须要安装NetBean或MyEclipse。','1120308784477036544',0,NULL,1,1),('1120310026200735745','A','源文件的名字必须是Cat.java。','1120310026200735744',1,NULL,1,1),('1120310026200735746','B','源文件的名字可以是cat.java。','1120310026200735744',0,NULL,1,1),('1120310026200735747','C','源文件的名字可以任意。','1120310026200735744',0,NULL,1,1),('1120310026200735748','D','Cat类是主类。','1120310026200735744',0,NULL,1,1),('1120311600947978241','A','源文件的名字必须是Cat.java。','1120311600947978240',0,NULL,1,1),('1120311600947978242','B','源文件的名字可以是E.java。','1120311600947978240',1,NULL,1,1),('1120311600947978243','C','编译源文件得到Cat.class和E.class两个字节码文件。','1120311600947978240',0,NULL,1,1),('1120311600947978244','D','E类是主类，java E来运行程序。','1120311600947978240',0,NULL,1,1),('1120317167213010945','A','void','1120317167213010944',1,NULL,1,1),('1120317167213010946','B','static','1120317167213010944',0,NULL,1,1),('1120317167213010947','C','char','1120317167213010944',0,NULL,1,1),('1120317167213010948','D','int','1120317167213010944',0,NULL,1,1),('1120319068310659073','A','import','1120319068310659072',1,NULL,1,1),('1120319068310659074','B','package','1120319068310659072',0,NULL,1,1),('1120319068310659075','C','include','1120319068310659072',0,NULL,1,1),('1120319068310659076','D','packet','1120319068310659072',0,NULL,1,1),('1120319707497422849','A','可读性好','1120319707497422848',0,NULL,1,1),('1120319707497422850','B','可重用','1120319707497422848',1,NULL,1,1),('1120319707497422851','C','可跨包访问','1120319707497422848',0,NULL,1,1),('1120319707497422852','D','运行更安全','1120319707497422848',0,NULL,1,1);
/*!40000 ALTER TABLE `ex_choice_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_code`
--

DROP TABLE IF EXISTS `ex_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_code` (
  `code_id` varchar(32) NOT NULL,
  `code_title` mediumtext NOT NULL COMMENT '题干',
  `code_imgs` mediumtext COMMENT '图片',
  `code_type` varchar(32) DEFAULT NULL COMMENT '所属题型',
  `code_score` decimal(3,1) NOT NULL DEFAULT '0.0' COMMENT '分值',
  `code_difficulty` int(11) NOT NULL DEFAULT '1' COMMENT '难度系数',
  `code_bank` varchar(32) NOT NULL COMMENT '所属题库',
  `code_know` varchar(32) NOT NULL COMMENT '所属知识点',
  `code_compile` varchar(32) NOT NULL COMMENT '编译器，Java、C/C++、Python等',
  `code_resolve` mediumtext COMMENT '解析',
  `code_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `code_delete` int(11) NOT NULL DEFAULT '1' COMMENT '1正常0删除',
  PRIMARY KEY (`code_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='编程题';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_code`
--

LOCK TABLES `ex_code` WRITE;
/*!40000 ALTER TABLE `ex_code` DISABLE KEYS */;
INSERT INTO `ex_code` VALUES ('1119500099022520320','企业发放<的>奖金根据利润提成。利润(I)低于或等于 10 万元时，奖金可提 10%；利润高于 10 万元，低于 20 万元时，低于 10 万元的部分按 10%提成，高于 10 万元的部分，可可提成 7.5%；20 万到 40 万之间时，高于 20 万元的部分，可提成 5%；40 万到 60 万之间时高于 40 万元的部分， 可提成 3%； 60 万到 100 万之间时， 高于 60 万元的部分， 可提成 1.5%，高于 100 万元时，超过 100 万元的部分按 1%提成，从键盘输入当月利润',NULL,'5',4.0,1,'1111913413874835456','1117389471029940224','1','package com.day3;\nimport java.util.*;\npublic class test12 {\n    public static void main(String[] args) {\n        double x = 0,y = 0;\n        System.out.print(\"输入当月利润（万） ：\");\n        Scanner s = new Scanner(System.in);\n        x = s.nextInt();\n        if(x > 0 && x <= 10) {\n            y = x * 0.1;\n        } else if(x > 10 && x <= 20) {\n            y = 10 * 0.1 + (x - 10) * 0.075;\n        } else if(x > 20 && x <= 40) {\n            y = 10 * 0.1 + 10 * 0.075 + (x - 20) * 0.05;\n        } else if(x > 40 && x <= 60) {\n            y = 10 * 0.1 + 10 * 0.075 + 20 * 0.05 + (x - 40) * 0.03;\n        } else if(x > 60 && x <= 100) {\n            y = 20 * 0.175 + 20 * 0.05 + 20 * 0.03 + (x - 60) * 0.015;\n        } else if(x > 100) {\n            y = 20 * 0.175 + 40 * 0.08 + 40 * 0.015 + (x - 100) * 0.01;\n        }\n        System.out.println(\"应该提取的奖金是 \" + y + \"万\");\n    }\n}\n\ntest12',4,1),('1119501836202577920','猴子吃桃问题：猴子第一天摘下若干个桃子，当即吃了一半，还不瘾，又多吃了一个 第二天早上又将剩下的桃子吃掉一半，又多吃了一个。以后每天早上都吃了前一天剩下 的一半零一个。 到第10天早上想再吃时， 见只剩下一个桃子了',NULL,'5',5.0,2,'1111913413874835456','1117389471029940224','1','package com.day4;\npublic class test17 {\n    public static void main(String[] args) {\n        // TODO Auto-generated method stub\n        int num = 1;\n        for (int i = 9; i >= 1; i--) {\n            num = (num + 1) * 2;\n        }\n        System.out.println(\"猴子第一天摘的桃子的个数是：\"+num);\n    }\n\n}\n\ntest17',4,1);
/*!40000 ALTER TABLE `ex_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_code_answer`
--

DROP TABLE IF EXISTS `ex_code_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_code_answer` (
  `answer_id` varchar(32) NOT NULL COMMENT 'id',
  `answer_problem` mediumtext NOT NULL COMMENT '小题',
  `answer_content` mediumtext NOT NULL COMMENT '答案内容',
  `answer_number` int(3) DEFAULT NULL COMMENT '答案编号',
  `answer_code` varchar(32) NOT NULL COMMENT '对应题目编号',
  `answer_score` decimal(3,0) NOT NULL COMMENT '分值',
  `answer_resolve` mediumtext COMMENT '解析',
  `answer_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `answer_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0正常1删除',
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='编程题-答案';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_code_answer`
--

LOCK TABLES `ex_code_answer` WRITE;
/*!40000 ALTER TABLE `ex_code_answer` DISABLE KEYS */;
INSERT INTO `ex_code_answer` VALUES ('1119500099026714624','求应发放奖金总数？','package com.day3;\nimport java.util.*;\npublic class test12 {\n    public static void main(String[] args) {\n        double x = 0,y = 0;\n        System.out.print(\"输入当月利润（万） ：\");\n        Scanner s = new Scanner(System.in);\n        x = s.nextInt();\n        if(x > 0 && x <= 10) {\n            y = x * 0.1;\n        } else if(x > 10 && x <= 20) {\n            y = 10 * 0.1 + (x - 10) * 0.075;\n        } else if(x > 20 && x <= 40) {\n            y = 10 * 0.1 + 10 * 0.075 + (x - 20) * 0.05;\n        } else if(x > 40 && x <= 60) {\n            y = 10 * 0.1 + 10 * 0.075 + 20 * 0.05 + (x - 40) * 0.03;\n        } else if(x > 60 && x <= 100) {\n            y = 20 * 0.175 + 20 * 0.05 + 20 * 0.03 + (x - 60) * 0.015;\n        } else if(x > 100) {\n            y = 20 * 0.175 + 40 * 0.08 + 40 * 0.015 + (x - 100) * 0.01;\n        }\n        System.out.println(\"应该提取的奖金是 \" + y + \"万\");\n    }\n}\n\ntest12',1,'1119500099022520320',4,NULL,4,1),('1119501836210966528','求第一天共摘了多少。','package com.day4;\npublic class test17 {\n    public static void main(String[] args) {\n        // TODO Auto-generated method stub\n        int num = 1;\n        for (int i = 9; i >= 1; i--) {\n            num = (num + 1) * 2;\n        }\n        System.out.println(\"猴子第一天摘的桃子的个数是：\"+num);\n    }\n\n}\n\ntest17',1,'1119501836202577920',5,NULL,4,1);
/*!40000 ALTER TABLE `ex_code_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_compile`
--

DROP TABLE IF EXISTS `ex_compile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_compile` (
  `compile_id` varchar(32) NOT NULL,
  `compile_number` int(3) NOT NULL COMMENT '编译器编号',
  `compile_name` varchar(32) NOT NULL COMMENT '编译器名',
  PRIMARY KEY (`compile_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='编译器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_compile`
--

LOCK TABLES `ex_compile` WRITE;
/*!40000 ALTER TABLE `ex_compile` DISABLE KEYS */;
INSERT INTO `ex_compile` VALUES ('1',1,'JAVA'),('2',2,'C/C++'),('3',3,'Python'),('4',4,'JavaScript');
/*!40000 ALTER TABLE `ex_compile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_completion`
--

DROP TABLE IF EXISTS `ex_completion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_completion` (
  `comp_id` varchar(32) NOT NULL,
  `comp_title` mediumtext NOT NULL COMMENT '内容',
  `comp_score` decimal(3,1) NOT NULL COMMENT '分值',
  `comp_difficulty` int(11) NOT NULL COMMENT '难度系数',
  `comp_bank` varchar(32) NOT NULL COMMENT '所属题库',
  `comp_know` varchar(32) NOT NULL COMMENT '知识点id',
  `comp_resolve` mediumtext COMMENT '解析',
  `comp_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `comp_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`comp_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='填空题表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_completion`
--

LOCK TABLES `ex_completion` WRITE;
/*!40000 ALTER TABLE `ex_completion` DISABLE KEYS */;
INSERT INTO `ex_completion` VALUES ('1118468407142936576','测试第一个空___第二个空___第三个空___',2.0,3,'1111913413874835456','1117389793030852608','我是解析',2,1),('1118470836475416576','哈哈___测试第一个空___第二个空___第三个___哈哈哈',3.0,1,'1111913413874835456','1117389793030852608','我是解析',10,1);
/*!40000 ALTER TABLE `ex_completion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_completion_answer`
--

DROP TABLE IF EXISTS `ex_completion_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_completion_answer` (
  `answer_id` varchar(32) NOT NULL COMMENT '主键',
  `answer_number` int(3) DEFAULT NULL COMMENT '答案编号,1,2,3...',
  `answer_content` mediumtext NOT NULL COMMENT '答案内容',
  `answer_comp` varchar(32) NOT NULL COMMENT '所属填空题',
  `answer_resolve` mediumtext COMMENT '解析',
  `answer_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `answer_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='填空题答案表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_completion_answer`
--

LOCK TABLES `ex_completion_answer` WRITE;
/*!40000 ALTER TABLE `ex_completion_answer` DISABLE KEYS */;
INSERT INTO `ex_completion_answer` VALUES ('1118470836475416577',1,'第一','1118470836475416576','1',10,1),('1118470836475416578',2,'第二','1118470836475416576',NULL,10,1),('1118470836475416579',3,'第三','1118470836475416576',NULL,10,1),('1118472875616022528',4,'第四','1118470836475416576',NULL,6,1);
/*!40000 ALTER TABLE `ex_completion_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_dict`
--

DROP TABLE IF EXISTS `ex_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_dict` (
  `dict_id` varchar(32) NOT NULL COMMENT '数据字典id',
  `dict_name` varchar(64) NOT NULL COMMENT '数据字典名',
  `dict_type` varchar(16) NOT NULL COMMENT '数据字典类型',
  `dict_father` varchar(32) DEFAULT NULL COMMENT '父级字典id',
  `dict_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `dict_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0已删除，1未删除',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='数据字典表\r\ncollege-学院\r\nmajor-专业\r\njob-职务\r\ntitle-职称\r\nsubject-科目';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_dict`
--

LOCK TABLES `ex_dict` WRITE;
/*!40000 ALTER TABLE `ex_dict` DISABLE KEYS */;
INSERT INTO `ex_dict` VALUES ('1111164554571681792','电子与信息工程学院','college',NULL,7,1),('1111164565703364608','机电工程学院','college',NULL,4,1),('1111164855676571648','外国语学院','college',NULL,2,1),('1111168385552486400','人文学院','college',NULL,2,1),('1111171604940967936','马克思主义学院','college',NULL,2,1),('1111171754350465024','建筑工程学院','college',NULL,3,1),('1111173035584507904','教育学院','college',NULL,2,1),('1111173091377139712','体育学院','college',NULL,2,1),('1111173098645868544','艺术学院','college',NULL,2,1),('1111173105935568896','商学院','college',NULL,2,1),('1111173112583540736','数理学院','college',NULL,2,1),('1111173174202060800','机电工程学院','college',NULL,2,1),('1111182824423047168','化学化工学院','college',NULL,2,1),('1111187013471584256','生命科学学院','college',NULL,2,1),('1111187063224418304','医学部','college',NULL,2,1),('1111189642587160576','国防生学院','college',NULL,2,1),('1111211629313277952','软件工程','major','1111164554571681792',2,1),('1111211697013538816','计算机科学与技术','major','1111164554571681792',2,1),('1111223441308008448','任课教师','job',NULL,1,1),('1111223476665991168','班主任','job',NULL,2,1),('1111223574124838912','教授','title',NULL,1,1),('1111223591413760000','副教授','title',NULL,1,1),('1111223649802665984','团委书记','title',NULL,1,1),('1111243811151003648','高等数学','subject',NULL,1,1),('1111243839034736640','软件工程专业英语','subject',NULL,1,1),('1111243864053760000','Java','subject',NULL,2,1),('1111549081068511232','创新创业学院','college',NULL,2,1),('1114751719062933504','计算机科学与技术（一本）','major','1111164554571681792',1,1),('1114751745679986688','网络工程','major','1111164554571681792',1,1),('1114751786427650048','商务英语','major','1111164855676571648',1,1),('1114751812155510784','日语','major','1111164855676571648',1,1),('1114751849807777792','小学教育','major','1111173035584507904',1,1),('1114751878618451968','学前教育','major','1111173035584507904',1,1),('1114751916128112640','中医','major','1111187063224418304',1,1),('1114752023569403904','临床医学','major','1111187063224418304',1,1),('1114752052367495168','通信工程','major','1111164554571681792',1,1),('1114753044974702592','Web前端开发技术','subject',NULL,1,1),('1114753254572462080','毛泽东概论','subject',NULL,1,1),('1114754695726927872','数据库','subject',NULL,1,1);
/*!40000 ALTER TABLE `ex_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_paper`
--

DROP TABLE IF EXISTS `ex_paper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_paper` (
  `paper_id` varchar(32) NOT NULL,
  `paper_title` varchar(32) NOT NULL COMMENT '试卷标题',
  `paper_start_year` varchar(16) NOT NULL COMMENT '起始年度',
  `paper_end_year` varchar(16) NOT NULL COMMENT '结束年度',
  `paper_seme` int(2) NOT NULL DEFAULT '1' COMMENT '第几学期，1第一学期，2第二学期',
  `paper_comment` varchar(128) DEFAULT NULL COMMENT '备注',
  `paper_college` varchar(32) NOT NULL COMMENT '学院，数据字典id',
  `paper_major` varchar(32) NOT NULL COMMENT '专业，数据字典id',
  `paper_bank` varchar(32) NOT NULL COMMENT '题库id',
  `paper_flag` int(11) NOT NULL DEFAULT '0' COMMENT '0未启用，1已启用，用于在线考试',
  `paper_style` varchar(8) NOT NULL DEFAULT 'A' COMMENT 'ABC卷',
  `paper_type` int(11) NOT NULL DEFAULT '0' COMMENT '组卷类型，0未组卷，1手动组卷，2智能组卷',
  `paper_difficulty` decimal(3,1) NOT NULL DEFAULT '1.0' COMMENT '难度系数',
  `paper_score` decimal(3,1) NOT NULL DEFAULT '0.0' COMMENT '总分',
  `paper_submit` int(3) NOT NULL DEFAULT '0' COMMENT '是否提交组卷，提交组卷后不能修改，0未提交，1已提交 ',
  `paper_download` varchar(128) DEFAULT NULL COMMENT '试卷下载链接',
  `paper_question_num` int(3) NOT NULL DEFAULT '0' COMMENT '题量',
  `paper_create_time` varchar(32) NOT NULL COMMENT '创建时间',
  `paper_update_time` varchar(32) NOT NULL COMMENT '更新时间',
  `paper_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `paper_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`paper_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='试卷表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_paper`
--

LOCK TABLES `ex_paper` WRITE;
/*!40000 ALTER TABLE `ex_paper` DISABLE KEYS */;
INSERT INTO `ex_paper` VALUES ('1119583382574010368','测试试卷2','2018','2019',1,'哈哈哈','1111164554571681792','1111211629313277952','1111632855139622912',0,'B',0,1.0,0.0,0,NULL,0,'2019-04-20','2019-04-20',3,1),('1119583424651268096','再测','2018','2019',1,'哈哈哈','1111164554571681792','1111211629313277952','1111912642504581120',0,'A',0,1.0,0.0,0,NULL,0,'2019-04-20','2019-04-20',1,1),('1119583466745303040','java考试','2018','2019',1,'Java考试试卷，用来测试组卷','1111164554571681792','1111211629313277952','1111913413874835456',0,'A',2,2.5,40.0,1,'http://tn20898453.51mypc.cn/file/java考试.docx',20,'2019-04-20','2019-05-05',45,1),('1119583503990722560','Javaee考试','2018','2019',1,'哈哈哈','1111164554571681792','1111211629313277952','1111917882515705856',0,'A',0,1.0,0.0,0,NULL,0,'2019-04-20','2019-04-20',1,1),('1119583545036181504','万恶的马原考试','2018','2019',1,'哈哈哈','1111164554571681792','1111211629313277952','1114753665597476864',0,'A',0,1.0,0.0,0,NULL,0,'2019-04-20','2019-04-20',1,1),('1124661597796397056','Java智能组卷','2019','2020',1,'用来测试智能组卷','1111164554571681792','1111211629313277952','1111913413874835456',0,'A',2,2.0,40.0,1,'http://tn20898453.51mypc.cn/file/Java智能组卷.docx',20,'2019-05-04','2019-05-05',22,1);
/*!40000 ALTER TABLE `ex_paper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_paper_config`
--

DROP TABLE IF EXISTS `ex_paper_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_paper_config` (
  `config_id` varchar(32) NOT NULL,
  `config_paper` varchar(32) NOT NULL COMMENT '所属试卷',
  `config_question_num` int(11) NOT NULL DEFAULT '0' COMMENT '题目量',
  `config_score` decimal(3,1) NOT NULL DEFAULT '0.0' COMMENT '分值',
  `config_type` varchar(32) NOT NULL COMMENT '所属题型',
  `config_know` varchar(32) DEFAULT NULL COMMENT '知识点',
  `config_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `config_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='试卷配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_paper_config`
--

LOCK TABLES `ex_paper_config` WRITE;
/*!40000 ALTER TABLE `ex_paper_config` DISABLE KEYS */;
INSERT INTO `ex_paper_config` VALUES ('1125001056798760960','1124661597796397056',10,20.0,'1',NULL,1,1),('1125001057067196416','1124661597796397056',10,20.0,'3',NULL,1,1),('1125001412551237632','1119583466745303040',10,20.0,'1',NULL,1,1),('1125001412706426881','1119583466745303040',10,20.0,'3',NULL,1,1);
/*!40000 ALTER TABLE `ex_paper_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_paper_config_question`
--

DROP TABLE IF EXISTS `ex_paper_config_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_paper_config_question` (
  `id` varchar(32) NOT NULL,
  `question_config` varchar(32) NOT NULL COMMENT '配置id',
  `question_id` varchar(32) NOT NULL COMMENT '题目id',
  `question_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='试卷配置-题目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_paper_config_question`
--

LOCK TABLES `ex_paper_config_question` WRITE;
/*!40000 ALTER TABLE `ex_paper_config_question` DISABLE KEYS */;
INSERT INTO `ex_paper_config_question` VALUES ('1125001057079779328','1125001056798760960','1120296762054074368',1),('1125001057079779329','1125001056798760960','1120302113184866304',1),('1125001057079779330','1125001056798760960','1120209794326650880',1),('1125001057079779331','1125001056798760960','1120197515501690880',1),('1125001057079779332','1125001056798760960','1120288594594947072',1),('1125001057079779333','1125001056798760960','1120288228037943296',1),('1125001057079779334','1125001056798760960','1120300211575513088',1),('1125001057079779335','1125001056798760960','1120308784477036544',1),('1125001057079779336','1125001056798760960','1120197674755219456',1),('1125001057079779337','1125001056798760960','1120319707497422848',1),('1125001057079779338','1125001057067196416','1120279382951256064',1),('1125001057079779339','1125001057067196416','1120281544838144000',1),('1125001057079779340','1125001057067196416','1120278433260167168',1),('1125001057079779341','1125001057067196416','1120278555796758528',1),('1125001057079779342','1125001057067196416','1120282602603864064',1),('1125001057079779343','1125001057067196416','1118102798962036736',1),('1125001057079779344','1125001057067196416','1120278477291970560',1),('1125001057079779345','1125001057067196416','1120285238623264768',1),('1125001057079779346','1125001057067196416','1120285064047943680',1),('1125001057079779347','1125001057067196416','1120283426818154496',1),('1125001412756758529','1125001412551237632','1120297939550396416',1),('1125001412756758530','1125001412551237632','1120209887758966784',1),('1125001412756758531','1125001412551237632','1120300211575513088',1),('1125001412756758532','1125001412551237632','1120197674755219456',1),('1125001412756758533','1125001412551237632','1120286885357346816',1),('1125001412756758534','1125001412551237632','1120197891026116608',1),('1125001412756758535','1125001412551237632','1120285711522652160',1),('1125001412756758536','1125001412551237632','1120209308634636288',1),('1125001412756758537','1125001412551237632','1120308784477036544',1),('1125001412756758538','1125001412551237632','1120286561477386240',1),('1125001412756758539','1125001412706426881','1120283508040851456',1),('1125001412756758540','1125001412706426881','1120281437153583104',1),('1125001412756758541','1125001412706426881','1120278841965731840',1),('1125001412756758542','1125001412706426881','1120283540030808064',1),('1125001412756758543','1125001412706426881','1120285159640326144',1),('1125001412756758544','1125001412706426881','1120278477291970560',1),('1125001412756758545','1125001412706426881','1120278648323104768',1),('1125001412756758546','1125001412706426881','1120282768480198656',1),('1125001412756758547','1125001412706426881','1120283362037129216',1),('1125001412756758548','1125001412706426881','1120278555796758528',1);
/*!40000 ALTER TABLE `ex_paper_config_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_paper_log`
--

DROP TABLE IF EXISTS `ex_paper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_paper_log` (
  `pl_id` varchar(32) NOT NULL COMMENT '日志id',
  `pl_teacher` varchar(32) NOT NULL COMMENT '组卷教师的id',
  `pl_difficulty` decimal(3,1) NOT NULL DEFAULT '0.0' COMMENT '难度系数',
  `pl_score` decimal(3,1) NOT NULL DEFAULT '0.0' COMMENT '试卷分值',
  `pl_time` varchar(64) NOT NULL COMMENT '组卷时间',
  `pl_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `pl_delete` int(11) NOT NULL DEFAULT '1' COMMENT '1正常0删除',
  PRIMARY KEY (`pl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组卷日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_paper_log`
--

LOCK TABLES `ex_paper_log` WRITE;
/*!40000 ALTER TABLE `ex_paper_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `ex_paper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_pwd`
--

DROP TABLE IF EXISTS `ex_pwd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_pwd` (
  `pwd_id` varchar(32) NOT NULL,
  `pwd_plaintext` varchar(64) NOT NULL COMMENT '明文',
  `pwd_ciphertext` varchar(64) NOT NULL COMMENT '密文',
  PRIMARY KEY (`pwd_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_pwd`
--

LOCK TABLES `ex_pwd` WRITE;
/*!40000 ALTER TABLE `ex_pwd` DISABLE KEYS */;
INSERT INTO `ex_pwd` VALUES ('1112348581047590912','123456','e10adc3949ba59abbe56e057f20f883e'),('1112348603667472384','123456','e10adc3949ba59abbe56e057f20f883e'),('1112348607169716224','123456','e10adc3949ba59abbe56e057f20f883e'),('1112348610583879680','123456','e10adc3949ba59abbe56e057f20f883e'),('1112348612878163968','123456','e10adc3949ba59abbe56e057f20f883e'),('1112601301109231616','144933','ee1d5f64d02ffb04aed535582dcccb71'),('1112601317483794432','144933','ee1d5f64d02ffb04aed535582dcccb71'),('1112601320579190784','144933','ee1d5f64d02ffb04aed535582dcccb71'),('1112601324026908672','144933','ee1d5f64d02ffb04aed535582dcccb71'),('1113037981574234112','101010','6d071901727aec1ba6d8e2497ef5b709'),('1113057602649980928','160230131','c408c4ce33a10658314072025460007e'),('1113057899694784512','645','5e9f92a01c986bafcabbafd145520b13'),('1113058512327409664','c4ca4238a0b923820dcc509a6f75849b','28c8edde3d61a0411511d3b1866f0636'),('1113420404002660352','132342','69805ef3731a3c56beb0256575d8e4d4'),('1113420581719515136','54','a684eceee76fc522773286a895bc8436'),('1113420988155961344','97','e2ef524fbf3d9fe611d5a8e90fefdc9c'),('1113421334412533760','1','c4ca4238a0b923820dcc509a6f75849b'),('1114492785756483584','12121','de872154ffbf91a5dcc0e539dd2d5106'),('1114734231340224512','123','202cb962ac59075b964b07152d234b70'),('1114863169689382912','1111111111','e11170b8cbd2d74102651cb967fa28e5'),('1114880900740530176','92a35d8b542d4b11a6430f4db05c7e8d','d593db4e52fa9ddca35ad15f2e5ed1bc'),('1114888479600394240','14b3e6ee522a0e8bcd8b4f2397b7ec56','c060caa5730b537e3f1de90d8629d177'),('1114890104738004992','06ffbfe2d6d73a12d89537dcbda17397','f1bc93abb939c6f1c4b1a5de9e262b3c');
/*!40000 ALTER TABLE `ex_pwd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_question`
--

DROP TABLE IF EXISTS `ex_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_question` (
  `question_id` varchar(32) NOT NULL,
  `question_title` mediumtext NOT NULL COMMENT '题干',
  `question_img` varchar(128) NOT NULL COMMENT '题目图片',
  `question_type` varchar(32) NOT NULL COMMENT '所属题型',
  `question_score` decimal(3,1) DEFAULT NULL COMMENT '分值',
  `question_difficulty` int(11) NOT NULL COMMENT '难度系数',
  `question_bank` varchar(32) NOT NULL COMMENT '所属题库',
  `question_style` int(11) DEFAULT NULL COMMENT '题目类型，1选择，2多选，3判断，4填空，5编程，6其他',
  `question_know` varchar(32) NOT NULL COMMENT '知识点',
  `question_resolve` mediumtext COMMENT '解析',
  `question_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `question_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='其他题型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_question`
--

LOCK TABLES `ex_question` WRITE;
/*!40000 ALTER TABLE `ex_question` DISABLE KEYS */;
INSERT INTO `ex_question` VALUES ('1119145243728453632','测试','','1111960837909667840',2.0,2,'1111913413874835456',6,'1117389471029940224','解析',6,0),('1119193350415622144','测试题目','','1111960837909667840',3.0,3,'1111913413874835456',6,'1117389471029940224','我是解析',1,1),('1119193460629348352','测试题目','','1111960837909667840',9.0,3,'1111913413874835456',6,'1117389471029940224','我是解析',1,1),('1119197064438276096','由于找不到什么好题目，所以就找马克思主义原理的题目啦。那么请回答下面三题','','1111960837909667840',20.0,5,'1111913413874835456',6,'1117389471029940224','就是这样，我也看不懂，反正内容是不少了',1,1);
/*!40000 ALTER TABLE `ex_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_question_answer`
--

DROP TABLE IF EXISTS `ex_question_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_question_answer` (
  `answer_id` varchar(32) NOT NULL,
  `answer_number` int(3) DEFAULT NULL COMMENT '小题编号',
  `answer_problem` mediumtext NOT NULL COMMENT '问题内容',
  `answer_content` mediumtext NOT NULL COMMENT '答案',
  `answer_score` decimal(3,1) NOT NULL COMMENT '分值',
  `answer_question` varchar(32) NOT NULL COMMENT '题目id',
  `answer_resolve` mediumtext COMMENT '解析',
  `answer_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `answer_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='其他题目答案';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_question_answer`
--

LOCK TABLES `ex_question_answer` WRITE;
/*!40000 ALTER TABLE `ex_question_answer` DISABLE KEYS */;
INSERT INTO `ex_question_answer` VALUES ('1119145243732647936',1,'第一小题','答案',2.0,'1119145243728453632',NULL,6,1),('1119167103283277824',2,'第二','答案2',0.0,'1119145243728453632',NULL,1,1),('1119193350424010752',1,'第一小题','第一小题答案',2.0,'1119193350415622144',NULL,1,1),('1119193350424010753',2,'第二小题','第二小题答案',1.0,'1119193350415622144',NULL,1,1),('1119193460629348353',1,'第一小题','第一小题答案',2.0,'1119193460629348352',NULL,1,1),('1119193460629348354',2,'第二小题','第二小题答案',1.0,'1119193460629348352',NULL,1,1),('1119193460629348355',3,'哈哈','2123',4.0,'1119193460629348352',NULL,1,1),('1119193460629348356',4,'啊啊','啊实打实',2.0,'1119193460629348352',NULL,1,1),('1119197064446664704',1,'试述垄断资本主义国家的宏观经济管理与调控','按照马克思主义的观点来考察资本主义国家干预经济的问题,归根到底是社会生产力的发展,社会化大生产在资本主义制度内部存在和发展的内在要求,使得资本主义国家必须在一定程度上承担起管理与调节社会经济生活的任务。同时,从市场经济运行条件的角度来看,一些私有制市场经济主体所不愿或不能承担的社会经济任务,也必须由国家来完成。',6.0,'1119197064438276096',NULL,1,1),('1119197064446664705',2,'试述垄断资本主义在世界范围内的扩展。','私人垄断资本主义阶段,垄断资本的统治在其国内不断发展,并积极向外扩张势力,对不发达国家和地区实行经济乃至政治、军事等多方面的侵略和扩张,剥削、奴役和掠夺这些国家和地区的人民,以实现、垄断资本在国外的统治。国家垄断资本主义阶段,垄断资本在世界范围的扩展规模更大,形式更加多样,建立起发达资本主义国家主导的世界经济政治体系。',6.0,'1119197064438276096',NULL,1,1),('1119197064446664706',3,'简述20世纪80年代以来经济全球化快速发展的原因。','(1)新科学技术,特别是计算机、通信技术日新月异的进步和在社会经济生活中的广泛应用,加强了国际经济联系。\n\n(2)国际贸易的自由程度大大提高。\n\n(3)国际资本流动的大幅增加。',8.0,'1119197064438276096',NULL,1,1);
/*!40000 ALTER TABLE `ex_question_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_role`
--

DROP TABLE IF EXISTS `ex_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_role` (
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `role_name` varchar(32) NOT NULL COMMENT '角色名',
  `role_father` varchar(32) DEFAULT NULL COMMENT '父级角色',
  `role_index` int(11) DEFAULT NULL COMMENT '排序字段',
  `role_comment` varchar(256) DEFAULT NULL COMMENT '角色描述',
  `role_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `role_delete` int(11) NOT NULL DEFAULT '1' COMMENT '1正常0删除',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_role`
--

LOCK TABLES `ex_role` WRITE;
/*!40000 ALTER TABLE `ex_role` DISABLE KEYS */;
INSERT INTO `ex_role` VALUES ('1125322203910127616','超级管理员','',1,'系统最高角色',1,1),('1125322597721718784','学院管理员','',2,'每一个学院的管理员',1,1);
/*!40000 ALTER TABLE `ex_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_role_auth`
--

DROP TABLE IF EXISTS `ex_role_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_role_auth` (
  `ra_id` varchar(32) NOT NULL COMMENT 'id',
  `ra_role` varchar(32) NOT NULL COMMENT '角色id',
  `ra_auth` varchar(32) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`ra_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色-权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_role_auth`
--

LOCK TABLES `ex_role_auth` WRITE;
/*!40000 ALTER TABLE `ex_role_auth` DISABLE KEYS */;
INSERT INTO `ex_role_auth` VALUES ('1125764979927818240','1125322203910127616','1'),('1125764979927818241','1125322203910127616','11'),('1125764979927818242','1125322203910127616','12'),('1125764979927818243','1125322203910127616','13'),('1125764979927818244','1125322203910127616','14'),('1125764979927818245','1125322203910127616','15'),('1125764979927818246','1125322203910127616','151'),('1125764979927818247','1125322203910127616','152'),('1125764979927818248','1125322203910127616','153'),('1125764979927818249','1125322203910127616','154'),('1125764979927818250','1125322203910127616','155'),('1125764979927818251','1125322203910127616','1551'),('1125764979927818252','1125322203910127616','1552'),('1125764979927818253','1125322203910127616','1553'),('1125764979927818254','1125322203910127616','1554'),('1125764979927818255','1125322203910127616','15541'),('1125764979927818256','1125322203910127616','15542'),('1125764979927818257','1125322203910127616','15543'),('1125764979927818258','1125322203910127616','15544'),('1125764979927818259','1125322203910127616','2'),('1125764979927818260','1125322203910127616','21'),('1125764979927818261','1125322203910127616','22'),('1125764979927818262','1125322203910127616','23'),('1125764979927818263','1125322203910127616','24'),('1125764979927818264','1125322203910127616','3'),('1125764979927818265','1125322203910127616','31'),('1125764979927818266','1125322203910127616','311'),('1125764979927818267','1125322203910127616','312'),('1125764979927818268','1125322203910127616','313'),('1125764979927818269','1125322203910127616','314'),('1125764979927818270','1125322203910127616','315'),('1125764979927818271','1125322203910127616','32'),('1125764979927818272','1125322203910127616','321'),('1125764979927818273','1125322203910127616','322'),('1125764979927818274','1125322203910127616','323'),('1125764979927818275','1125322203910127616','324'),('1125764979927818276','1125322203910127616','4'),('1125764979927818277','1125322203910127616','41'),('1125764979927818278','1125322203910127616','42'),('1125764979927818279','1125322203910127616','43'),('1125764979927818280','1125322203910127616','44'),('1125764979927818281','1125322203910127616','45'),('1125764979927818282','1125322203910127616','5'),('1125764979927818283','1125322203910127616','51'),('1125764979927818284','1125322203910127616','511'),('1125764979927818285','1125322203910127616','512'),('1125764979927818286','1125322203910127616','513'),('1125764979927818287','1125322203910127616','514'),('1125764979927818288','1125322203910127616','52'),('1125764979927818289','1125322203910127616','521'),('1125764979927818290','1125322203910127616','522'),('1125764979927818291','1125322203910127616','523'),('1125764979927818292','1125322203910127616','524'),('1125764979927818293','1125322203910127616','53'),('1125764979927818294','1125322203910127616','531'),('1125764979927818295','1125322203910127616','532'),('1125764979927818296','1125322203910127616','533'),('1125764979927818297','1125322203910127616','534'),('1125764979927818298','1125322203910127616','54'),('1125764979927818299','1125322203910127616','541'),('1125764979927818300','1125322203910127616','542'),('1125764979927818301','1125322203910127616','543'),('1125764979927818302','1125322203910127616','544'),('1125764979927818303','1125322203910127616','55'),('1125764979927818304','1125322203910127616','551'),('1125764979927818305','1125322203910127616','552'),('1125764979927818306','1125322203910127616','553'),('1125764979927818307','1125322203910127616','554'),('1125764979927818308','1125322203910127616','61'),('1125764979927818309','1125322203910127616','621'),('1125764979927818310','1125322203910127616','622'),('1125764979927818311','1125322203910127616','624'),('1125764979927818312','1125322203910127616','625');
/*!40000 ALTER TABLE `ex_role_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_student`
--

DROP TABLE IF EXISTS `ex_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_student` (
  `stu_id` varchar(32) NOT NULL,
  `stu_number` varchar(32) NOT NULL COMMENT '学号',
  `stu_password` varchar(64) NOT NULL COMMENT '密码',
  `stu_name` varchar(32) NOT NULL COMMENT '姓名',
  `stu_sex` int(11) NOT NULL COMMENT '1男2女',
  `stu_age` int(11) NOT NULL COMMENT '年龄',
  `stu_college` varchar(32) NOT NULL COMMENT '学院，数据字典id',
  `stu_major` varchar(32) NOT NULL COMMENT '专业，数据字典id',
  `stu_img` varchar(256) DEFAULT NULL COMMENT '照片',
  `stu_card` varchar(64) NOT NULL COMMENT '身份证号码',
  `stu_entrance_time` varchar(32) DEFAULT NULL COMMENT '入学时间',
  `stu_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `stu_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`stu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='学生表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_student`
--

LOCK TABLES `ex_student` WRITE;
/*!40000 ALTER TABLE `ex_student` DISABLE KEYS */;
INSERT INTO `ex_student` VALUES ('1114880900740530176','13544545','d593db4e52fa9ddca35ad15f2e5ed1bc','闺女给的',2,18,'1111164554571681792','1111211629313277952','http://tn20898453.51mypc.cn/file/1c3934809b784ecca973e8f6c38854ca.jpg','45645445645','2019-04-10',4,1),('1114888479600394240','16090504070','c060caa5730b537e3f1de90d8629d177','道理',1,3,'1111164554571681792','1111211697013538816','http://tn20898453.51mypc.cn/file/f972c287484643d4a8f4ebf2cca3a04c.jpg','411303111111111111','2019-04-01',2,1),('1114890104738004992','1609422222','f1bc93abb939c6f1c4b1a5de9e262b3c','朕乃陆小凤',1,6,'1111164855676571648','1114751786427650048','http://tn20898453.51mypc.cn/file/d0d14ed617b449e78bc12bb13d10169a.jpg','1233555777777777','2019-04-09',2,1);
/*!40000 ALTER TABLE `ex_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_teacher`
--

DROP TABLE IF EXISTS `ex_teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_teacher` (
  `teacher_id` varchar(32) NOT NULL COMMENT '教师id',
  `teacher_number` varchar(32) NOT NULL COMMENT '工号',
  `teacher_username` varchar(32) NOT NULL COMMENT '账号',
  `teacher_password` varchar(64) NOT NULL COMMENT '密码',
  `teacher_card` varchar(64) DEFAULT NULL COMMENT '身份证号',
  `teacher_name` varchar(32) NOT NULL COMMENT '姓名',
  `teacher_sex` int(11) NOT NULL COMMENT '性别，1男2女',
  `teacher_age` int(11) NOT NULL COMMENT '年龄',
  `teacher_job` varchar(32) DEFAULT NULL COMMENT '职务，数据字典id',
  `teacher_title` varchar(32) DEFAULT NULL COMMENT '职称，数据字典id',
  `teacher_mobile` varchar(16) DEFAULT NULL COMMENT '手机号',
  `teacher_email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `teacher_img` varchar(256) DEFAULT NULL COMMENT '照片',
  `teacher_entry_time` varchar(32) DEFAULT NULL COMMENT '入职时间',
  `teacher_college` varchar(32) DEFAULT NULL COMMENT '学院，数据字典id',
  `teacher_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `teacher_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`teacher_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='教师表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_teacher`
--

LOCK TABLES `ex_teacher` WRITE;
/*!40000 ALTER TABLE `ex_teacher` DISABLE KEYS */;
INSERT INTO `ex_teacher` VALUES ('1','12345678','admin','21232f297a57a5a743894a0e4a801fc3','34152487645316478','超级管理员',1,35,'1','1','18296666666','435156413@qq.com','http://tn20898453.51mypc.cn/file/d7b27e081c5849948a5baddd9d4196ee.jpg','2019-03-31','1',1,1),('1113421334412533760','11111','11111','c4ca4238a0b923820dcc509a6f75849b','1','咕哒子',2,1,'1111223441308008448','1111223574124838912','1','1','http://tn20898453.51mypc.cn/file/d7b27e081c5849948a5baddd9d4196ee.jpg','2019-04-15','1111173174202060800',7,1),('1114492785756483584','12121','12121','de872154ffbf91a5dcc0e539dd2d5106','31232131','呆毛王',1,12,'1111223441308008448','1111223591413760000','123213','123123','http://tn20898453.51mypc.cn/file/b938216f4cfb4b7d9b84a87aefd7c158.jpg','2019-04-06','1111164554571681792',2,1),('1114734231340224512','1609','1609','202cb962ac59075b964b07152d234b70','411303','沈万三',1,22,'1111223476665991168','1111223649802665984','13525','2057','http://tn20898453.51mypc.cn/file/218d060bed3941fcb620e8a79ce966bb.jpg','2019-04-16','1111164554571681792',2,1);
/*!40000 ALTER TABLE `ex_teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_teacher_role`
--

DROP TABLE IF EXISTS `ex_teacher_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_teacher_role` (
  `tr_id` varchar(32) NOT NULL COMMENT 'id',
  `tr_teacher` varchar(32) NOT NULL COMMENT '教师id',
  `tr_role` varchar(32) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`tr_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='教师-角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_teacher_role`
--

LOCK TABLES `ex_teacher_role` WRITE;
/*!40000 ALTER TABLE `ex_teacher_role` DISABLE KEYS */;
INSERT INTO `ex_teacher_role` VALUES ('1114472066330976258','1','1125322203910127616'),('1125670543805976576','1114492785756483584','1125322203910127616');
/*!40000 ALTER TABLE `ex_teacher_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_true_false`
--

DROP TABLE IF EXISTS `ex_true_false`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_true_false` (
  `tf_id` varchar(32) NOT NULL COMMENT '主键',
  `tf_title` mediumtext NOT NULL COMMENT '题目内容',
  `tf_score` decimal(3,1) NOT NULL DEFAULT '0.0' COMMENT '分值',
  `tf_difficulty` int(11) NOT NULL DEFAULT '0' COMMENT '难度系数',
  `tf_bank` varchar(32) NOT NULL COMMENT '所属题库',
  `tf_resolve` mediumtext COMMENT '解析',
  `tf_true` int(11) NOT NULL COMMENT '是否正确，1正确0错误',
  `tf_know` varchar(32) NOT NULL COMMENT '知识点id',
  `tf_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `tf_delete` int(11) NOT NULL DEFAULT '1' COMMENT '1正常0删除',
  PRIMARY KEY (`tf_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='判断题表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_true_false`
--

LOCK TABLES `ex_true_false` WRITE;
/*!40000 ALTER TABLE `ex_true_false` DISABLE KEYS */;
INSERT INTO `ex_true_false` VALUES ('1118077112897540096','测试2',1.0,3,'1111913413874835456','测',0,'1117389793030852608',4,1),('1118102798962036736','哈哈哈',2.0,1,'1111913413874835456','23132',0,'1117389793030852608',2,1),('1118102845560754176','emmmm',1.0,1,'1111913413874835456','23132',0,'1117389793030852608',1,1),('1120278433260167168','Java语言的主要贡献者是James Gosling',2.0,1,'1111913413874835456',NULL,1,'1117389471029940224',1,1),('1120278477291970560','Java语言是1995年5月Sun公司推出的编程语言',2.0,1,'1111913413874835456',NULL,1,'1117389471029940224',1,1),('1120278555796758528','\nJava语言是1995年5月IBM司推出的编程语言答案',2.0,1,'1111913413874835456',NULL,0,'1117389471029940224',1,1),('1120278648323104768','开发Java应用程序的基本步骤是：\n1 编写源文件，\n2.编译源文件，\n3.运行程序。',2.0,1,'1111913413874835456',NULL,1,'1117389471029940224',1,1),('1120278841965731840','Java源文件是由若干个书写形式互相独立的类组成。',2.0,1,'1111913413874835456',NULL,1,'1117389471029940224',1,1),('1120278875096539136','Java源文件中只能有一个类',2.0,3,'1111913413874835456',NULL,1,'1117389471029940224',1,1),('1120278912119660544','如果源文件中有多个类，那么至多有一个类可以是public类',2.0,3,'1111913413874835456',NULL,1,'1117389471029940224',1,1),('1120279382951256064','如果源文件中有多个类，那么必须要有一个类是public类',2.0,5,'1111913413874835456',NULL,0,'1117389471029940224',1,1),('1120281437153583104','如果源文件中有多个类，这些类可以都不是public类',2.0,4,'1111913413874835456',NULL,1,'1117389471029940224',1,1),('1120281504761569280','如果源文件中只有一个类，这个类必须是public类',2.0,4,'1111913413874835456',NULL,0,'1117389471029940224',1,1),('1120281544838144000','如果源文件中只有一个类，这个类必须是主类',2.0,2,'1111913413874835456',NULL,0,'1117389471029940224',1,1),('1120282534580641792','java源文件中可以没有主类',2.0,2,'1111913413874835456',NULL,1,'1117389471029940224',1,1),('1120282602603864064','java源文件中可以没有public类',2.0,2,'1111913413874835456',NULL,1,'1117389471029940224',1,1),('1120282723324321792','java源文件的扩展名是.java',2.0,2,'1111913413874835456',NULL,1,'1117389471029940224',1,1),('1120282768480198656','java源文件的扩展名是.class',2.0,2,'1111913413874835456',NULL,0,'1117389471029940224',1,1),('1120282822842572800','编译java源文件得到的字节码文件的扩展名是.class',2.0,2,'1111913413874835456',NULL,1,'1117389471029940224',1,1),('1120282868128473088','java应用程序的主类必须是public类',2.0,5,'1111913413874835456',NULL,0,'1117389471029940224',1,1),('1120283283704307712','\"\\hello\"是正确的字符串常量',2.0,5,'1111913413874835456',NULL,0,'1117389727968808960',1,1),('1120283323948654592','\"\\\\hello\"是正确的字符串常量',2.0,2,'1111913413874835456',NULL,1,'1117389727968808960',1,1),('1120283362037129216','\"\\nhello\"是正确的字符串常量',2.0,2,'1111913413874835456',NULL,1,'1117389727968808960',1,1),('1120283426818154496','表达式\"Hello\".equals(\"hello\")的值时true',2.0,4,'1111913413874835456',NULL,0,'1117389727968808960',1,1),('1120283469235150848','表达式\"java\".equals(\"java\")的值是true',2.0,3,'1111913413874835456',NULL,1,'1117389727968808960',1,1),('1120283508040851456','表达式\"Bird\".compareTo(\"Bird fly\")的值是负数',2.0,3,'1111913413874835456',NULL,1,'1117389727968808960',1,1),('1120283540030808064','表达式\"I love this game\".contains(\"love\")的值是true',2.0,3,'1111913413874835456',NULL,1,'1117389727968808960',1,1),('1120283592061149184','表达式\"RedBird\".indexOf(\"Bird\")的值是4',2.0,3,'1111913413874835456',NULL,1,'1117389727968808960',1,1),('1120283648692641792','表达式\"RedBird\".indexOf(\"Bird\")的值是3',2.0,3,'1111913413874835456',NULL,1,'1117389727968808960',1,1),('1120283677796917248','\n表达式\"RedBird\".indexOf(\"Cat\")的值是-1',2.0,3,'1111913413874835456',NULL,1,'1117389727968808960',1,1),('1120283713645633536','Integer.parseInt(\"12.9\");会触发NumberFormatException异常',2.0,3,'1111913413874835456',NULL,1,'1117389727968808960',1,1),('1120283749808922624','表达式\"bird\".contentEquals(\"bird\")的值是true',2.0,2,'1111913413874835456',NULL,1,'1117389727968808960',1,1),('1120285064047943680','表达式\"Bird\".contentEquals(\"bird\")的值是false',2.0,2,'1111913413874835456',NULL,1,'1117389727968808960',1,1),('1120285095635247104','表达式\"Bird\".equalsIgnoreCase(\"bird\")的值是true',2.0,2,'1111913413874835456',NULL,1,'1117389727968808960',1,1),('1120285122462015488','表达式\"D:/java/book/E.java\".lastIndexOf(\"/\")的值是12',2.0,2,'1111913413874835456',NULL,1,'1117389727968808960',1,1),('1120285159640326144','表达式\"89762.34\".matches(\"[0-9.]+\")的值是true',2.0,2,'1111913413874835456',NULL,0,'1117389727968808960',1,1),('1120285209229582336','表达式 new String(\"abc\")== \"abc\"的值是true',2.0,5,'1111913413874835456',NULL,0,'1117389727968808960',1,1),('1120285238623264768','表达式 new String(\"abc\")== \"abc\"的值是false',2.0,1,'1111913413874835456',NULL,1,'1117389727968808960',1,1);
/*!40000 ALTER TABLE `ex_true_false` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ex_type`
--

DROP TABLE IF EXISTS `ex_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ex_type` (
  `type_id` varchar(32) NOT NULL COMMENT '题型id',
  `type_name` varchar(32) NOT NULL COMMENT '题型名称',
  `type_version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `type_delete` int(11) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='题型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ex_type`
--

LOCK TABLES `ex_type` WRITE;
/*!40000 ALTER TABLE `ex_type` DISABLE KEYS */;
INSERT INTO `ex_type` VALUES ('1','单项选择题',1,1),('1111960837909667840','简答题',1,1),('1111970261143412736','论述题',3,1),('1120290556442894336','选择题',2,0),('2','多项选择题',1,1),('3','判断题',1,1),('4','填空题',1,1),('5','编程题',1,1);
/*!40000 ALTER TABLE `ex_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-09 11:16:39
