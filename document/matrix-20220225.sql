/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.23 : Database - matrix
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`matrix` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `matrix`;

/*Table structure for table `ac_api_domain` */

DROP TABLE IF EXISTS `ac_api_domain`;

CREATE TABLE `ac_api_domain` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ac_api_info 与 ac_include_domain 的关联表',
  `ac_api_info_id` bigint DEFAULT NULL,
  `ac_include_domain_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=819 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `ac_api_domain` */

insert  into `ac_api_domain`(`id`,`ac_api_info_id`,`ac_include_domain_id`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(811,80161083,27,'2021-03-15 16:18:13',2,'Yangcl','2021-03-15 16:18:13',2,'Yangcl',1),
(812,80161083,29,'2021-03-15 16:18:13',2,'Yangcl','2021-03-15 16:18:13',2,'Yangcl',1),
(813,80161083,30,'2021-03-15 16:18:13',2,'Yangcl','2021-03-15 16:18:13',2,'Yangcl',1),
(814,80161083,32,'2021-03-15 16:18:13',2,'Yangcl','2021-03-15 16:18:13',2,'Yangcl',1),
(815,80161090,27,'2021-07-16 18:37:40',2,'Yangcl','2021-07-16 18:37:40',2,'Yangcl',1),
(816,80161090,30,'2021-07-16 18:37:40',2,'Yangcl','2021-07-16 18:37:40',2,'Yangcl',1),
(817,80161090,32,'2021-07-16 18:37:40',2,'Yangcl','2021-07-16 18:37:40',2,'Yangcl',1),
(818,80161090,35,'2021-07-16 18:37:40',2,'Yangcl','2021-07-16 18:37:40',2,'Yangcl',1);

/*Table structure for table `ac_api_info` */

DROP TABLE IF EXISTS `ac_api_info`;

CREATE TABLE `ac_api_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'api信息表',
  `name` varchar(50) DEFAULT '' COMMENT '接口中文描述   树展示使用',
  `target` varchar(50) DEFAULT NULL COMMENT '系统接口名称 比如：TEST-PUBLIC-PROCESSOR，访问标识',
  `dto_info` text COMMENT '请求参数信息',
  `atype` varchar(10) DEFAULT 'private' COMMENT '接口类型 private:私有 即公司内部使用的接口| public:公开，即开放给第三方的接口',
  `module` varchar(20) DEFAULT 'matrix-api' COMMENT 'maven sub module name  比如：matrix-file',
  `processor` varchar(300) DEFAULT '' COMMENT '业务处理接口的类 com.matrix.processor.publics.example.TestPublicProcessor',
  `domain` int DEFAULT '0' COMMENT '接口是否拥有跨域行为 0 不允许  1 允许跨域访问|ac_api_domain表作为关联',
  `parent_id` bigint DEFAULT NULL COMMENT '所属内部项目id,用于树形结构展示|ac_api_project表id',
  `seqnum` int DEFAULT NULL COMMENT '顺序码 同一层次显示顺序',
  `discard` int DEFAULT '1' COMMENT '这个api是否废弃|0:废弃 1:使用中',
  `login` int DEFAULT '1' COMMENT '当前接口是否需要登录后访问：1 需要登录后访问 0不需要',
  `remark` longtext COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=80161091 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='alter table `ac_api_info` AUTO_INCREMENT=80160001';

/*Data for the table `ac_api_info` */

insert  into `ac_api_info`(`id`,`name`,`target`,`dto_info`,`atype`,`module`,`processor`,`domain`,`parent_id`,`seqnum`,`discard`,`login`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(80160101,'根据API的target返回DTO的Json文本','API-COMMON-FIND-DTO','{}','private','matrix-api','common.ApiFindDtoProcessor',0,8,6,1,1,'根据API缓存的target返回查询消息体','2018-10-09 11:07:55',2,'Yangcl','2021-07-20 17:11:13',2,'Yangcl',1),
(80160102,'根据Api请求者的key，找到对应的value','API-COMMON-FIND-VALUE-BY-KEY','{}','private','matrix-api','common.ApiFindValueByKey',0,8,7,0,1,'根据请求者的key，找到对应的value','2018-10-09 11:10:39',2,'Yangcl','2021-07-20 17:10:49',2,'Yangcl',1),
(80160155,'用户登录接口','MANAGER-API-100','{\"userName\":\"admin\",\"password\":\"admin@root\",\r\n\"platform\":\"133C9CB27E18\"}','private','matrix-manager-api','privates.ManagerApi100Processor',0,9,3,1,0,'验证用户登录信息|客户端用户：nodejs/IOS平板等','2018-10-11 15:49:38',2,'Yangcl','2021-07-20 15:08:35',2,'Yangcl',1),
(80160156,'用户退出系统接口','MANAGER-API-101','{}','private','matrix-manager-api','privates.ManagerApi101Processor',0,9,4,1,1,'退出系统登录|客户端用户：nodejs/IOS平板等','2018-10-11 15:51:16',2,'Yangcl','2019-03-27 19:27:43',2,'Yangcl',1),
(80160157,'用户列表页数据','MANAGER-API-102','{\"startIndex\":1,\"pageSize\":10,\"cid\":-1}','private','matrix-manager-api','privates.ManagerApi102Processor',0,9,5,1,1,'用户列表页数据展示','2018-10-11 15:52:16',2,'Yangcl','2020-01-10 17:35:11',2,'Yangcl',1),
(80160158,'添加用户界面-绘制所属公司和平台分配两个控件','MANAGER-API-103',NULL,'private','matrix-manager-api','privates.ManagerApi103Processor',0,9,6,1,1,'添加用户界面-绘制所属公司（下拉框列表）和平台分配（单选按钮）','2018-10-12 11:37:29',2,'Yangcl','2018-10-12 11:37:29',2,'Yangcl',1),
(80160183,'添加用户','MANAGER-API-104',NULL,'private','matrix-manager-api','privates.ManagerApi104Processor',0,9,7,1,1,'添加用户|MANAGER-API-104','2018-10-12 19:40:09',2,'Yangcl','2018-10-12 19:53:39',2,'Yangcl',1),
(80160184,'获取用户详情','MANAGER-API-105','{\"id\":20072026234148}','private','matrix-manager-api','privates.ManagerApi105Processor',0,9,8,1,1,'在进入编辑页面时 -> 获取用户详情 -> 展示用户信息','2018-10-12 19:53:32',2,'Yangcl','2019-08-27 14:59:01',2,'Yangcl',1),
(80160185,'修改用户信息','MANAGER-API-106','{\r\n	\"id\": 1,\r\n	\"userName\": \"Yangcl\",\r\n	\"idcard\": \"2\",\r\n	\"sex\": 1,\r\n	\"mobile\": \"2\",\r\n	\"email\": \"2\",\r\n	\"qq\": \"2\",\r\n	\"remark\": \"2\",\r\n	\"userNameOld\": \"2\",\r\n	\"password\": \"2\",\r\n	\"oldPassWord\": \"2\"\r\n}','private','matrix-manager-api','privates.ManagerApi106Processor',0,9,9,1,1,'修改用户信息','2018-10-12 20:07:12',2,'Yangcl','2021-07-20 17:06:21',2,'Yangcl',1),
(80160186,'删除一个用户|不保留数据库中的记录','MANAGER-API-107',NULL,'private','matrix-manager-api','privates.ManagerApi107Processor',0,9,10,1,1,'删除一个用户|不保留数据库中的记录|物理删除','2018-10-12 20:08:00',2,'Yangcl','2018-10-12 20:08:00',2,'Yangcl',1),
(80160187,'用户选择后台页面样式风格','MANAGER-API-108',NULL,'private','matrix-manager-api','privates.ManagerApi108Processor',0,9,11,1,1,'用户自己选择后台页面样式风格','2018-10-12 20:14:21',2,'Yangcl','2018-10-12 20:14:42',2,'Yangcl',1),
(80160188,'获取功能树列表','MANAGER-API-109',NULL,'private','matrix-manager-api','privates.ManagerApi109Processor',0,9,12,1,1,'获取树列表|sys-user-role-function.js使用2次\r\ndto.platform 如果不为空则获取指定平台下的功能节点\r\ndto.type type=list or role|如果type=role则同时获得角色列表，同时dto.id = roleId','2018-10-13 14:49:08',2,'Yangcl','2018-10-13 14:49:08',2,'Yangcl',1),
(80160189,'系统角色列表数据','MANAGER-API-110',NULL,'private','matrix-manager-api','privates.ManagerApi110Processor',0,9,13,1,1,'系统角色列表数据','2018-10-13 15:05:00',2,'Yangcl','2018-10-13 15:05:00',2,'Yangcl',1),
(80160190,'展示权限列表|如果用户已经有权限了则标识出来','MANAGER-API-111',NULL,'private','matrix-manager-api','privates.ManagerApi111Processor',0,9,14,1,1,'展示权限列表|如果用户已经有权限了则标识出来','2018-10-13 15:12:46',2,'Yangcl','2018-10-13 15:12:46',2,'Yangcl',1),
(80160191,'添加一个角色，不勾选系统功能','MANAGER-API-112',NULL,'private','matrix-manager-api','privates.ManagerApi112Processor',0,9,15,1,1,'添加一个角色，不勾选系统功能','2018-10-13 15:34:20',2,'Yangcl','2018-10-13 15:34:20',2,'Yangcl',1),
(80160192,'角色详情','MANAGER-API-113',NULL,'private','matrix-manager-api','privates.ManagerApi113Processor',0,9,16,1,1,'唯一参数：info.id mc_role表自增id。','2018-10-13 15:52:59',2,'Yangcl','2018-10-13 16:01:16',2,'Yangcl',1),
(80160193,'修改角色名称|角色编辑页面的提交按钮','MANAGER-API-114',NULL,'private','matrix-manager-api','privates.ManagerApi114Processor',0,9,17,1,1,'修改角色名称和描述，不勾选系统功能|角色编辑页面的提交按钮','2018-10-13 16:03:13',2,'Yangcl','2018-10-15 16:44:02',2,'Yangcl',1),
(80160217,'添加系统功能','MANAGER-API-115',NULL,'private','matrix-manager-api','privates.ManagerApi115Processor',0,9,18,1,1,'添加系统功能到数据库-mc_sys_function表添加记录','2018-10-15 14:37:25',2,'Yangcl','2018-10-15 14:37:25',2,'Yangcl',1),
(80160218,'更新系统功能','MANAGER-API-116',NULL,'private','matrix-manager-api','privates.ManagerApi116Processor',0,9,19,1,1,'更新系统功能到数据库-mc_sys_function表添加记录','2018-10-15 14:38:18',2,'Yangcl','2018-10-15 14:38:18',2,'Yangcl',1),
(80160219,'系统功能同层节点拖拽更新','MANAGER-API-117',NULL,'private','matrix-manager-api','privates.ManagerApi117Processor',0,9,20,1,1,'更新拖拽后的同层节点|dto.ustring id@seqnum,id@seqnum','2018-10-15 14:57:57',2,'Yangcl','2018-10-15 14:57:57',2,'Yangcl',1),
(80160220,'删除一个系统功能节点及其子节点','MANAGER-API-118',NULL,'private','matrix-manager-api','privates.ManagerApi118Processor',0,9,21,1,1,'删除一个系统功能节点及其子节点','2018-10-15 15:28:12',2,'Yangcl','2018-10-15 15:28:12',2,'Yangcl',1),
(80160221,'创建系统角色','MANAGER-API-119',NULL,'private','matrix-manager-api','privates.ManagerApi119Processor',0,9,22,1,1,'创建系统角色','2018-10-15 15:58:10',2,'Yangcl','2018-10-15 15:58:10',2,'Yangcl',1),
(80160222,'修改角色功能|【角色列表】->【角色功能】->【提交按钮】','MANAGER-API-120',NULL,'private','matrix-manager-api','privates.ManagerApi120Processor',0,9,23,1,1,'修改系统角色功能|【角色列表】->【角色功能】->【提交按钮】','2018-10-15 16:45:16',2,'Yangcl','2018-10-15 16:52:48',2,'Yangcl',1),
(80160223,'删除角色','MANAGER-API-121',NULL,'private','matrix-manager-api','privates.ManagerApi121Processor',0,9,24,1,1,' 需要判断 mc_user_role 表中是否已经关联了用户，如果关联了，则不允许删除；如果想删除则必选先将用户与该角色解除绑定，即：删除mc_user_role表中的关联记录','2018-10-15 16:51:01',2,'Yangcl','2018-10-15 16:51:01',2,'Yangcl',1),
(80160224,'关联用户与某一个角色','MANAGER-API-122',NULL,'private','matrix-manager-api','privates.ManagerApi122Processor',0,9,25,1,1,'关联用户与某一个角色','2018-10-15 17:12:39',2,'Yangcl','2018-10-15 17:12:39',2,'Yangcl',1),
(80160225,'解除角色绑定','MANAGER-API-123',NULL,'private','matrix-manager-api','privates.ManagerApi123Processor',0,9,26,1,1,'解除角色绑定，同时删除缓存','2018-10-15 17:16:21',2,'Yangcl','2018-10-15 17:16:21',2,'Yangcl',1),
(80160262,'公司组织机构数列表数据','MANAGER-API-124',NULL,'private','matrix-manager-api','privates.ManagerApi124Processor',0,9,27,1,1,'公司组织结构数列表数据','2018-10-26 18:05:43',2,'Yangcl','2018-10-26 18:15:56',2,'Yangcl',1),
(80160263,'删除多个组织机构节点','MANAGER-API-127',NULL,'private','matrix-manager-api','privates.ManagerApi127Processor',0,9,28,1,1,'删除多个组织结构节点','2018-10-26 18:06:56',2,'Yangcl','2018-10-26 18:16:42',2,'Yangcl',1),
(80160264,'添加一个组织结构节点到数据库','MANAGER-API-125',NULL,'private','matrix-manager-api','privates.ManagerApi125Processor',0,9,29,1,1,'添加一个组织结构节点到数据库','2018-10-26 18:07:38',2,'Yangcl','2018-10-26 18:16:08',2,'Yangcl',1),
(80160265,'编辑一个组织机构节点','MANAGER-API-126',NULL,'private','matrix-manager-api','privates.ManagerApi126Processor',0,9,30,1,1,'编辑一个组织机构节点','2018-10-26 18:08:19',2,'Yangcl','2018-10-26 18:16:36',2,'Yangcl',1),
(80160266,'系统功能同层节点拖拽更新','MANAGER-API-128',NULL,'private','matrix-manager-api','privates.ManagerApi128Processor',0,9,31,1,1,'系统功能同层节点拖拽更新','2018-10-26 18:08:56',2,'Yangcl','2018-10-26 18:16:16',2,'Yangcl',1),
(80160275,'修改用户密码','MANAGER-API-129',NULL,'private','matrix-api','privates.ManagerApi129Processor',0,9,32,1,1,'修改用户密码','2018-10-30 16:59:55',2,'Yangcl','2018-10-30 16:59:55',2,'Yangcl',1),
(80160276,'数据权限','MANAGER-API-130',NULL,'private','matrix-api','privates.ManagerApi130Processor',0,9,33,1,1,'数据权限','2018-10-31 11:27:20',2,'Yangcl','2018-10-31 11:27:20',2,'Yangcl',1),
(80160277,'文件上传接口','Api-File-Remote-Upload',NULL,'private','matrix-api','common.ApiFileRemoteUpload',0,1,1,1,1,'系统文件上传接口|平台公共接口的一部分','2018-11-01 16:12:56',2,'Yangcl','2018-11-01 16:12:56',2,'Yangcl',0),
(80160318,'门店列表数据','MANAGER-API-131',NULL,'private','matrix-api','privates.ManagerApi131Processor',0,9,34,1,1,'门店列表数据','2018-11-20 17:54:38',2,'Yangcl','2018-11-20 17:54:38',2,'Yangcl',1),
(80160325,'获取API接口项目中的Config配置信息','API-COMMON-CONFIG-INFO','{}','private','matrix-api','common.ApiFindConfigInfoProcessor',0,8,8,1,1,'获取API接口项目中的Config配置信息|如果部署多个API接口服务器，则可能随机获取其中的一个，但会返回一个标识的IP地址作为区分依据','2018-11-26 19:09:15',2,'Yangcl','2020-01-13 09:33:07',2,'Yangcl',1),
(80160326,'获取验证码接口','MANAGER-API-140','{}','private','matrix-manager-api','privates.ManagerApi140Processor',0,9,37,1,0,'API:获取验证码接口','2018-11-27 14:49:48',2,'Yangcl','2019-03-27 19:11:03',2,'Yangcl',1),
(80160327,'用户关联数据权限','MANAGER-API-134',NULL,'private','matrix-manager-api','privates.ManagerApi134Processor',0,9,38,1,1,'用户关联数据权限','2018-11-28 17:28:35',2,'Yangcl','2018-11-28 17:28:35',2,'Yangcl',1),
(80160329,'区域门店集合','MANAGER-API-141',NULL,'private','matrix-manager-api','privates.ManagerApi141Processor',0,9,39,1,1,'获取区域门店集合','2018-12-18 10:46:18',2,'Yangcl','2018-12-18 10:46:18',2,'Yangcl',1),
(80160340,'获取登录用户的角色','MANAGER-API-142',NULL,'private','matrix-manager-api','privates.ManagerApi142Processor',0,9,40,1,1,'获取登录用户的角色','2018-12-19 19:14:11',2,'Yangcl','2018-12-19 19:14:11',2,'Yangcl',1),
(80161090,'用户登录接口2','MANAGER-API-200','{id:1}','private','matrix-manager-api','privates.ManagerApi100Processor',1,32,1,0,0,'用户登录接口2','2021-07-16 18:37:40',2,'Yangcl','2021-07-20 15:16:54',2,'Yangcl',0);

/*Table structure for table `ac_api_project` */

DROP TABLE IF EXISTS `ac_api_project`;

CREATE TABLE `ac_api_project` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'api所属项目|每一个web-api项目会对应这里的一条记录，然后由此向下延展其提供的接口',
  `target` varchar(25) DEFAULT '' COMMENT '项目名称',
  `atype` varchar(10) DEFAULT 'private' COMMENT '接口类型 private:私有 即公司内部使用的接口| public:公开，即开放给第三方的接口',
  `service_url` varchar(256) DEFAULT '' COMMENT '对应一个Tomcat web项目的服务器部署浏览器路径|也可以是一个Nginx前端负载路径',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '1' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT 'admin' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '1' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT 'admin' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `ac_api_project` */

insert  into `ac_api_project`(`id`,`target`,`atype`,`service_url`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(8,'平台公共接口-系统间调用','private','http://127.0.0.1:8080/leader/matrix/api.do','2018-09-27 19:16:11',1,'admin','2021-07-16 18:12:58',2,'Yangcl',1),
(9,'系统后台权限接口','private','http://127.0.0.1:8080/leader/matrix/api.do','2018-09-27 19:16:11',1,'admin','2021-07-16 18:13:12',2,'Yangcl',1),
(32,'内部项目A','private','http://127.0.0.1:8080/leader/matrix/api.do','2021-07-16 18:19:53',2,'Yangcl','2021-07-16 18:20:05',2,'Yangcl',1),
(33,'系统开发调用接口A组','public','http://127.0.0.1:8080/leader/matrix/open-api.do','2021-07-16 18:20:35',2,'Yangcl','2021-07-16 18:34:47',2,'Yangcl',1);

/*Table structure for table `ac_include_domain` */

DROP TABLE IF EXISTS `ac_include_domain`;

CREATE TABLE `ac_include_domain` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录系统允许跨域的域名',
  `domain` varchar(100) DEFAULT '' COMMENT '域名',
  `company_name` varchar(100) DEFAULT '' COMMENT '所属公司',
  `project` varchar(25) DEFAULT '' COMMENT '所属项目名称',
  `flag` int DEFAULT '1' COMMENT '状态位-是否有效 0无效 1有效',
  `remark` longtext COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '1' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT 'admin' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '1' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT 'admin' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `ac_include_domain` */

insert  into `ac_include_domain`(`id`,`domain`,`company_name`,`project`,`flag`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(27,'www.baidu.com','百度科技','',1,NULL,'2019-12-27 17:46:42',2,'Yangcl','2019-12-27 17:46:42',2,'Yangcl',1),
(30,'www.china.com','中国政府','',1,NULL,'2020-01-17 09:37:48',2,'Yangcl','2020-01-17 09:37:48',2,'Yangcl',1),
(32,'www.power-matrix.shop','能量矩阵','',1,NULL,'2020-01-17 09:38:45',2,'Yangcl','2020-01-17 09:38:45',2,'Yangcl',1),
(35,'www.jdcloud.com','jd cloud','',1,NULL,'2021-07-16 18:01:39',2,'Yangcl','2021-07-16 18:01:39',2,'Yangcl',1);

/*Table structure for table `ac_request_info` */

DROP TABLE IF EXISTS `ac_request_info`;

CREATE TABLE `ac_request_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '请求开放接口的组织机构信息表|字典表-缓存化',
  `organization` varchar(50) DEFAULT '' COMMENT '组织机构名称',
  `key` varchar(52) DEFAULT '' COMMENT '组织机构标识',
  `value` varchar(52) DEFAULT '' COMMENT '组织结构秘钥',
  `atype` varchar(8) DEFAULT 'private' COMMENT '请求权限  private:私有 即公司内部使用的接口| public:公开，即开放给第三方的接口',
  `flag` int DEFAULT '1' COMMENT '启用1 禁用 0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `ac_request_info` */

insert  into `ac_request_info`(`id`,`organization`,`key`,`value`,`atype`,`flag`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(1,'电商中台','133C9C129D53','6DFA608D49324E47A5D69A13523BDFDA','private',1,'2018-09-28 10:29:51',1,'admin','2019-07-22 10:25:48',2,'Yangcl',1),
(9,'developer-private','133C9CB27DA0','FD4007DB87B245EEACA7DAD5D4A1CECD','private',1,'2019-07-22 14:18:13',1,'Yangcl','2019-07-22 14:18:13',1,'Yangcl',1);

/*Table structure for table `ac_request_open_api` */

DROP TABLE IF EXISTS `ac_request_open_api`;

CREATE TABLE `ac_request_open_api` (
  `id` bigint NOT NULL COMMENT 'open-api与第三方请求者关联信息',
  `ac_request_info_id` bigint DEFAULT NULL,
  `ac_api_info_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `ac_request_open_api` */

/*Table structure for table `job_exec_log` */

DROP TABLE IF EXISTS `job_exec_log`;

CREATE TABLE `job_exec_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '定时任务执行记录表',
  `job_name` varchar(52) DEFAULT '' COMMENT 'sys_job表id',
  `job_title` varchar(52) DEFAULT '' COMMENT '任务标题',
  `status` varchar(16) DEFAULT '''success''' COMMENT '是否执行成功 success | error | exception；分别代表成功、失败和异常',
  `ip` varchar(52) DEFAULT '' COMMENT '主机地址',
  `run_group_id` bigint DEFAULT '0' COMMENT '运行组id',
  `run_group_name` varchar(20) DEFAULT '' COMMENT '运行组名称',
  `begin_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始执行时间',
  `end_time` datetime DEFAULT NULL COMMENT '执行完成时间',
  `remark` longtext COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `job_exec_log` */

insert  into `job_exec_log`(`id`,`job_name`,`job_title`,`status`,`ip`,`run_group_id`,`run_group_name`,`begin_time`,`end_time`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(1,'70714303-D36F-4F62-93B3-29A9C505ADAA','重力炉','success','222.222.222.222',1,'matrix-quartz','2021-05-06 17:48:30','2021-05-06 17:48:13','{\"ajaxBtnUrl\":\"\",\"authorize\":0,\"btnArea\":\"\",\"eleValue\":\"\",\"funcUrl\":\"\",\"id\":77,\"name\":\"开发示例\",\"navType\":1,\"pageSize\":10,\"parentId\":\"75\",\"platform\":\"133C9CB27E18\",\"remark\":\"为系统开发这提供的示例\",\"seqnum\":2,\"startIndex\":1,\"styleClass\":\"\",\"styleKey\":\"\"}','2021-05-06 17:48:30',1,'1','2021-05-06 17:48:30',2,'2',1);

/*Table structure for table `job_group` */

DROP TABLE IF EXISTS `job_group`;

CREATE TABLE `job_group` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '定时任务分组字典表 - 缓存化处理',
  `group_name` varchar(25) DEFAULT NULL COMMENT '分组名称，如：matrix-job-test|验证唯一性',
  `ip` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '该分组所在主机IP|逗号分隔',
  `remark` text COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '1' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT 'admin' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '1' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT 'admin' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `job_group` */

insert  into `job_group`(`id`,`group_name`,`ip`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(1,'matrix-quartz','10.12.52.41','matrix-quartz-test','2018-08-26 18:09:47',1,'admin','2021-05-06 15:44:29',2,'Yangcl',1),
(14,'cn-north-1','10.12.52.41,10.12.52.42,10.12.52.43,10.12.52.44,10.12.52.45',NULL,'2021-05-06 14:56:49',2,'Yangcl','2021-05-06 14:56:49',2,'Yangcl',1),
(15,'cn-north-2','10.12.52.2',NULL,'2021-05-06 16:20:44',2,'Yangcl','2021-05-06 16:21:29',2,'Yangcl',0),
(16,'aaaaaaaa','bbbbbbbbb',NULL,'2021-07-15 20:23:44',2,'Yangcl','2021-07-15 20:24:03',2,'Yangcl',0);

/*Table structure for table `job_info` */

DROP TABLE IF EXISTS `job_info`;

CREATE TABLE `job_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `job_name` varchar(52) NOT NULL COMMENT '定时任务名称-取uuid做唯一标记',
  `job_title` varchar(52) NOT NULL DEFAULT '' COMMENT '任务标题',
  `job_class` varchar(52) NOT NULL DEFAULT '' COMMENT '任务类名称',
  `job_triger` varchar(52) NOT NULL DEFAULT '' COMMENT '定时周期',
  `run_group_id` bigint NOT NULL DEFAULT '0' COMMENT '运行组|job_group表ID字段',
  `begin_time` datetime DEFAULT NULL COMMENT '开始执行时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束执行时间',
  `next_time` datetime DEFAULT NULL COMMENT '下一次执行时间',
  `pause` int DEFAULT '0' COMMENT '定时任务是否暂停 0否|1是',
  `lock_key` varchar(52) DEFAULT '' COMMENT '分布式锁前缀的key|唯一',
  `expire_time` int DEFAULT '60' COMMENT '默认锁的有效时间(m 秒)|redis中的过期时间',
  `time_out` int DEFAULT '200' COMMENT '默认请求锁的超时时间(ms 毫秒)',
  `job_list` varchar(512) DEFAULT '' COMMENT '触发其他定时任务|保存定时任务名称(uuid)，逗号分隔|不可关联triger_type=1的任务',
  `triger_type` int DEFAULT '1' COMMENT 'Scheduler中轮询状态的任务|1是 2否|2这种任务不会加入到scheduler中触发式的执行,只会被默认调用',
  `log_type` int DEFAULT '1' COMMENT '定时任务是否记录日志 1否 2是',
  `remark` varchar(450) DEFAULT '' COMMENT '备注',
  `max_exec_time` int DEFAULT '0' COMMENT '最长执行秒数|0代表无限期执行',
  `concurrent_type` int DEFAULT '0' COMMENT '是否允许并行启动|0不允许 1允许',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '1' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT 'admin' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '1' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT 'admin' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定时任务列表';

/*Data for the table `job_info` */

insert  into `job_info`(`id`,`job_name`,`job_title`,`job_class`,`job_triger`,`run_group_id`,`begin_time`,`end_time`,`next_time`,`pause`,`lock_key`,`expire_time`,`time_out`,`job_list`,`triger_type`,`log_type`,`remark`,`max_exec_time`,`concurrent_type`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(1,'UUID-9829384-0213410-OOKEIPAOA','定时任务测试类','com.matrix.quartz.job.JobForTestOne','2 * * * * ?',14,'2018-12-20 14:43:02','2018-12-20 14:43:02','2018-12-20 14:44:02',1,'com.matrix.quartz.job.JobForTestOne',60,200,'UUID-9829384-0213410-OOKEIPAOB,UUID-9829384-0213410-OOKEIPAOC',2,1,'每2分钟执行一次',0,0,'2018-08-20 14:41:09',1,'admin','2021-05-06 16:19:34',2,'Yangcl',1),
(2,'UUID-9829384-0213410-OOKEIPAOB','定时任务测试类','com.matrix.quartz.job.JobForTestTwo','8 * * * * ?',14,'2018-12-20 14:43:02','2018-12-20 14:43:02','2018-12-19 16:55:02',1,'com.matrix.quartz.job.JobForTestTwo',60,200,'',1,2,'每8分钟执行一次',0,1,'2018-08-20 14:41:09',1,'admin','2021-05-06 16:19:44',2,'Yangcl',1),
(3,'UUID-9829384-0213410-OOKEIPAOC','定时任务测试类','com.matrix.quartz.job.JobForTestThree','2 * * * * ?',14,'2018-12-20 14:43:02','2018-12-20 14:43:02','2018-12-19 17:00:02',1,'com.matrix.quartz.job.JobForTestThree',60,200,'',2,2,'2',0,1,'2018-08-20 14:41:09',1,'admin','2021-05-06 16:21:20',2,'Yangcl',1),
(35,'70714303-D36F-4F62-93B3-29A9C505ADAA','重力炉','d2412412312','213123',1,NULL,NULL,NULL,0,'70714303-D36F-4F62-93B3-29A9C505ADAA',1,20,'123123123',2,2,'3123123',0,0,'2021-04-19 17:49:05',2,'Yangcl','2021-04-28 16:37:50',2,'Yangcl',1),
(36,'1BCB124B-3334-48BA-9259-9F5137D9F574','安多夫','123','123',1,NULL,NULL,NULL,0,'1BCB124B-3334-48BA-9259-9F5137D9F574',10,20,'123',2,2,'123',0,1,'2021-04-19 20:25:43',2,'Yangcl','2021-04-19 20:25:43',2,'Yangcl',1);

/*Table structure for table `mc_organization` */

DROP TABLE IF EXISTS `mc_organization`;

CREATE TABLE `mc_organization` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '组织机构表|部门是组织机构的最后一级',
  `cid` bigint DEFAULT NULL COMMENT '客户id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  `name` varchar(50) DEFAULT NULL COMMENT '组织机构名称',
  `parent_id` bigint DEFAULT '0' COMMENT 'type=1 则 parent_id=-1',
  `type` int DEFAULT '2' COMMENT '1:总部/总公司/集团|2:区域/子集团/分公司|3:门店|4:部门',
  `platform` varchar(20) DEFAULT '' COMMENT '平台默认标识码|用以应对多个不同的平台和系统',
  `manager_id` bigint DEFAULT NULL COMMENT '负责人',
  `manager_name` varchar(50) DEFAULT '' COMMENT '负责人姓名',
  `store_type` int DEFAULT '0' COMMENT '门店类型：1加盟，2直营|type=3则此字段必填',
  `seqnum` int DEFAULT '1' COMMENT '顺序码 同一层次显示顺序',
  `mobile` varchar(20) DEFAULT '' COMMENT '电话',
  `address` varchar(512) DEFAULT '' COMMENT '地址',
  `remark` varchar(512) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_organization` */

/*Table structure for table `mc_role` */

DROP TABLE IF EXISTS `mc_role`;

CREATE TABLE `mc_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cid` bigint DEFAULT '0' COMMENT 'tc_shop_info表主键',
  `type` varchar(20) DEFAULT 'admin' COMMENT 'leader Leader后台创建的角色|admin 其他平台管理员创建的角色',
  `platform` varchar(20) DEFAULT 'P01' COMMENT '平台标识码',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(200) DEFAULT '' COMMENT '角色描述',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  `company_id` bigint DEFAULT NULL COMMENT '公司id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=197 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_role` */

insert  into `mc_role`(`id`,`cid`,`type`,`platform`,`role_name`,`role_desc`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`,`company_id`) values 
(116,0,'leader','133C9CB27E18','administer','administer','','2019-07-16 10:44:25',1992,'Yangcl','2022-03-02 11:27:02',2,'Yangcl',1,NULL),
(164,0,'leader','133C9CB27E18','开发示例','仅供测试','','2019-09-29 17:23:32',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1,NULL),
(195,0,'leader','134641C1B16A','ceshi-role','ceshi-role',NULL,'2021-07-16 17:00:05',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1,NULL),
(196,0,'leader','133C9CB27E18','公告接收测试','仅做公告接收测试',NULL,'2022-02-21 17:48:09',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1,NULL);

/*Table structure for table `mc_role_function` */

DROP TABLE IF EXISTS `mc_role_function`;

CREATE TABLE `mc_role_function` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'zid',
  `mc_role_id` bigint DEFAULT NULL COMMENT 'mc_role表主键',
  `mc_sys_function_id` bigint DEFAULT NULL COMMENT 'mc_sys_function表主键',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20277 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_role_function` */

insert  into `mc_role_function`(`id`,`mc_role_id`,`mc_sys_function_id`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(19298,164,75,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19299,164,77,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19300,164,250,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19301,164,251,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19302,164,252,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19303,164,79,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19304,164,83,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19305,164,167,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19306,164,308,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19307,164,126,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19308,164,171,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19309,164,192,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19310,164,187,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19311,164,208,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19312,164,127,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19313,164,309,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19314,164,172,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19315,164,310,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19316,164,193,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19317,164,188,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19318,164,258,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19319,164,257,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19320,164,209,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19321,164,210,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19322,164,211,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19323,164,212,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19324,164,213,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19325,164,214,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19326,164,215,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19327,164,259,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19328,164,260,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19329,164,80,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19330,164,84,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19331,164,261,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19332,164,128,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19333,164,227,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19334,164,249,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19335,164,325,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19336,164,326,'','2021-04-02 18:49:12',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(19476,195,1087,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19477,195,1090,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19478,195,1092,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19479,195,1096,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19480,195,1113,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19481,195,1114,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19482,195,1097,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19483,195,1106,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19484,195,1093,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19485,195,1098,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19486,195,1112,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19487,195,1099,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19488,195,1091,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19489,195,1095,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(19490,195,1094,NULL,'2021-07-16 17:00:13',2,'Yangcl','2021-07-16 17:00:13',2,'Yangcl',1),
(20091,116,75,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20092,116,77,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20093,116,250,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20094,116,251,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20095,116,252,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20096,116,79,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20097,116,83,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20098,116,167,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20099,116,308,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20100,116,126,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20101,116,171,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20102,116,192,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20103,116,187,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20104,116,208,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20105,116,127,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20106,116,309,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20107,116,172,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20108,116,310,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20109,116,188,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20110,116,193,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20111,116,258,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20112,116,257,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20113,116,209,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20114,116,210,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20115,116,211,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20116,116,212,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20117,116,213,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20118,116,214,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20119,116,215,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20120,116,259,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20121,116,260,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20122,116,80,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20123,116,84,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20124,116,261,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20125,116,128,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20126,116,227,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20127,116,249,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20128,116,325,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20129,116,326,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20130,116,78,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20131,116,81,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20132,116,117,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20133,116,197,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20134,116,312,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20135,116,313,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20136,116,314,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20137,116,315,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20138,116,473,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20139,116,316,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20140,116,1071,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20141,116,1073,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20142,116,1074,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20143,116,317,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20144,116,1075,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20145,116,118,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20146,116,198,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20147,116,318,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20148,116,177,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20149,116,319,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20150,116,320,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20151,116,321,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20152,116,322,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20153,116,1072,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20154,116,119,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20155,116,253,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20156,116,254,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20157,116,1076,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20158,116,1077,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20159,116,1078,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20160,116,1079,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20161,116,1080,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20162,116,255,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20163,116,1116,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20164,116,1118,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20165,116,1117,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20166,116,1115,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20167,116,1119,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20168,116,1120,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20169,116,1121,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20170,116,1122,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20171,116,1123,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20172,116,1124,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20173,116,1125,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20174,116,1126,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20175,116,287,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20176,116,288,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20177,116,289,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20178,116,290,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20179,116,291,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20180,116,292,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20181,116,293,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20182,116,294,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20183,116,295,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20184,116,296,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20185,116,297,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20186,116,298,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20187,116,299,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20188,116,300,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20189,116,323,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20190,116,1081,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20191,116,1082,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20192,116,301,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20193,116,302,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20194,116,303,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20195,116,304,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20196,116,305,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20197,116,306,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20198,116,465,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20199,116,466,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20200,116,510,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20201,116,600,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20202,116,479,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20203,116,480,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20204,116,481,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20205,116,482,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20206,116,483,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20207,116,484,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20208,116,485,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20209,116,486,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20210,116,487,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20211,116,488,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20212,116,489,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20213,116,490,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20214,116,491,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20215,116,492,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20216,116,1060,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20217,116,493,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20218,116,494,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20219,116,495,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20220,116,496,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20221,116,497,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20222,116,498,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20223,116,499,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20224,116,1084,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20225,116,1085,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20226,116,1086,NULL,'2022-03-02 11:27:03',2,'Yangcl','2022-03-02 11:27:03',2,'Yangcl',1),
(20227,196,75,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20228,196,77,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20229,196,250,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20230,196,251,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20231,196,252,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20232,196,79,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20233,196,83,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20234,196,167,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20235,196,308,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20236,196,126,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20237,196,171,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20238,196,192,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20239,196,187,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20240,196,208,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20241,196,127,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20242,196,309,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20243,196,172,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20244,196,310,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20245,196,188,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20246,196,193,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20247,196,258,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20248,196,257,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20249,196,209,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20250,196,210,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20251,196,211,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20252,196,212,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20253,196,213,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20254,196,214,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20255,196,215,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20256,196,259,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20257,196,260,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20258,196,80,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20259,196,84,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20260,196,261,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20261,196,128,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20262,196,227,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20263,196,249,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20264,196,325,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20265,196,326,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20266,196,78,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20267,196,253,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20268,196,1115,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20269,196,1119,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20270,196,1120,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20271,196,1121,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20272,196,1122,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20273,196,1123,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20274,196,1124,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20275,196,1125,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20276,196,1126,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1);

/*Table structure for table `mc_sys_function` */

DROP TABLE IF EXISTS `mc_sys_function`;

CREATE TABLE `mc_sys_function` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'zid',
  `mc_seller_company_id` bigint NOT NULL DEFAULT '1' COMMENT '1为平台本身|还有其他商户|mc_seller_company表主键|据此过滤权限树',
  `name` varchar(40) NOT NULL COMMENT '功能名称 导航栏与菜单栏所显示的名称',
  `parent_id` varchar(25) NOT NULL COMMENT '父节点。root为0 其下为:商户->导航栏->一级菜单栏->二级菜单栏->页面按钮',
  `seqnum` int NOT NULL COMMENT '顺序码 同一层次显示顺序：导航栏 一级菜单栏 二级菜单栏 对应的显示顺序',
  `nav_type` int NOT NULL COMMENT '-1 根节点 0 平台标记 1 横向导航栏|2 为1级菜单栏|3 2级菜单栏 |4 页面按钮|5 按钮内包含跳转页面(dialog或新页面)',
  `authorize` int DEFAULT '0' COMMENT '用户与角色是否委托Leader创建。0:委托 1:不委托-由其他子系统来创建|nav_type=0此字段生效。',
  `platform` varchar(20) DEFAULT '' COMMENT '平台默认标识码|nav_type=0，此处为系统生成默认值',
  `style_class` varchar(152) DEFAULT '' COMMENT '此项针对一级菜单栏 如：inbox   <a href="#example" class="inbox">开发者快速入门</a>',
  `style_key` varchar(50) DEFAULT '' COMMENT '此项针对一级菜单栏 如：example  <ul id="example">',
  `func_url` varchar(255) DEFAULT '' COMMENT 'nav_type=3或5;标识为一个跳转页面 如：example/page_form_example.do',
  `ajax_btn_url` varchar(152) DEFAULT '' COMMENT 'nav_type=4时保存所请求的接口与本条记录中ele_value的值一一对应|虽然都是按钮但nav_type=5通常此处为空',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `btn_area` varchar(20) DEFAULT '' COMMENT '按钮节点所在页面的位置。10001：功能区域；10002：查询区域；10003：数据区域',
  `ele_value` varchar(50) DEFAULT '' COMMENT '按钮权限唯一标识',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1127 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_sys_function` */

insert  into `mc_sys_function`(`id`,`mc_seller_company_id`,`name`,`parent_id`,`seqnum`,`nav_type`,`authorize`,`platform`,`style_class`,`style_key`,`func_url`,`ajax_btn_url`,`remark`,`btn_area`,`ele_value`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(1,1,'root：系统功能树','-1',1,-1,0,'','root','root','rool','','系统根节点','',NULL,'2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(75,1,'Leader','1',1,0,0,'133C9CB27E18','','','','','Leader底层管理系统','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(77,1,'开发示例','75',2,1,0,'133C9CB27E18','','','','','为系统开发这提供的示例','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(78,1,'矩阵系统配置','75',3,1,0,'133C9CB27E18','','','','','系统核心功能管理与控制','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-26 17:43:08',2,'Yangcl',1),
(79,1,'开发者快速入门','77',2,2,0,'133C9CB27E18','editor','37c694131c304c2588c4b906567631b1','','','开发者快速入门','','','2018-07-28 16:42:40',2,'Yangcl','2020-01-10 15:57:23',2,'Yangcl',1),
(80,1,'实例样本','77',3,2,0,'133C9CB27E18','editor','0cf9aa57b07149d586cc8998af6cfe7d','','','实例样本','','','2018-07-28 16:42:40',2,'Yangcl','2020-01-10 15:57:23',2,'Yangcl',1),
(81,1,'系统用户相关','78',1,2,0,'133C9CB27E18','editor','b06962367f8640fcbf11d4bca147101b','','','系统用户相关','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(83,1,'添加信息示例','79',1,3,0,'133C9CB27E18','','','example/page_example_add_info.do','','添加信息示例|matrix-admin/jsp/example/addExample.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2020-01-10 16:25:16',2,'Yangcl',1),
(84,1,'实际样本-A','80',1,3,0,'133C9CB27E18','','','example/page_example_a.do','','matrix-admin/src/main/webapp/jsp/example/reality/questionQuery.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(117,1,'系统用户列表','81',1,3,0,'133C9CB27E18','','','permissions/page_permissions_system_user_list.do','','leader/src/main/webapp/views/permission/user/system-user-list.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 14:51:18',2,'Yangcl',1),
(118,1,'系统角色列表','81',2,3,0,'133C9CB27E18','','','permissions/page_permissions_system_role_list.do','','leader/src/main/webapp/views/permission/role/system-role-list.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 14:50:20',2,'Yangcl',1),
(119,1,'系统功能列表','81',3,3,0,'133C9CB27E18','','','permissions/page_permissions_system_function.do','','leader/src/main/webapp/views/permission/func/system-func-tree.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 14:50:57',2,'Yangcl',1),
(126,1,'Ajax 分页示例','79',2,3,0,'133C9CB27E18','','','example/page_example_ajax_form.do','','Ajax 分页示例|matrix-admin/jsp/example/ajaxFormExample.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(127,1,'Ajax 分页+弹出窗体分页 示例','79',3,3,0,'133C9CB27E18','','','example/page_example_ajax_form_dialog.do','','Ajax 分页+弹出窗体分页 示例|\r\nmatrix-admin/jsp/example/ajaxFormDialogExample.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(128,1,'实际样本-B','80',2,3,0,'133C9CB27E18','','','example/page_example_b.do','','matrix-admin/src/main/webapp/jsp/example/reality/validate.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(167,1,'添加','83',1,4,0,'133C9CB27E18','','','','ajax_btn_add_info','123123','10001','add_info_example:add','2018-07-28 16:42:40',2,'Yangcl','2020-01-10 16:11:19',2,'Yangcl',1),
(171,1,'查询','126',1,4,0,'133C9CB27E18','','','','','查询','10002','ajax_page_example:search','2018-07-28 16:42:40',2,'Yangcl','2019-12-05 15:39:59',2,'Yangcl',1),
(172,1,'查询','127',2,4,0,'133C9CB27E18','','','','','添加','10002','ajax_page_dialog_example:search','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(177,1,'添加','118',3,4,0,'133C9CB27E18','','','','ajax_btn_add_mc_role_only.do','sysrole/ajax_btn_add_mc_role_only.do','10001','system_role_list:add','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 14:00:38',2,'Yangcl',1),
(187,1,'删除','126',3,4,0,'133C9CB27E18','','','','','删除','10003','ajax_page_example:delete','2018-07-28 16:42:40',2,'Yangcl','2019-11-08 15:58:28',2,'Yangcl',1),
(188,1,'修改','127',4,5,0,'133C9CB27E18','','','asdfasdf','','修改','10003','ajax_page_dialog_example:edit','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(192,1,'重置','126',2,4,0,'133C9CB27E18','','','','','重置','10002','ajax_page_example:reset','2018-07-28 16:42:40',2,'Yangcl','2019-11-08 15:58:28',2,'Yangcl',1),
(193,1,'重置','127',5,4,0,'133C9CB27E18','','','','','查看','10002','ajax_page_dialog_example:reset','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(197,1,'查询','117',1,4,0,'133C9CB27E18','','','','ajax_system_user_list.do','userInfo/ajax_system_user_list.do','10001','system_user_list:search','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(198,1,'查询','118',1,4,0,'133C9CB27E18','','','','ajax_system_role_list.do','sysrole/ajax_system_role_list.do','10001','system_role_list:search','2018-07-28 16:42:40',2,'Yangcl','2021-07-23 16:57:21',2,'Yangcl',1),
(208,1,'修改','126',4,4,0,'133C9CB27E18','','','','','修改','10003','ajax_page_example:edit','2018-07-28 16:42:40',2,'Yangcl','2019-11-08 15:58:28',2,'Yangcl',1),
(209,1,'自定义 alert confirm note 示例','79',4,3,0,'133C9CB27E18','','','example/page_example_alert.do','','自定义 alert confirm note 示例|matrix-admin/jsp/example/alertExample.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(210,1,'基本 Alert','209',1,4,0,'133C9CB27E18','','','','','基本 Alert','10001','custom_dialog_example:alert','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(211,1,'确认对话框 confirm','209',2,4,0,'133C9CB27E18','','','','','确认对话框 confirm','10001','custom_dialog_example:confirm','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(212,1,'输入对话框 prompt','209',3,4,0,'133C9CB27E18','','','','','输入对话框 prompt','10001','custom_dialog_example:prompt','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(213,1,'alert 支持html标签','209',4,4,0,'133C9CB27E18','','','','','alert 支持html标签','10001','custom_dialog_example:html','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(214,1,'弹出层示例','79',5,3,0,'133C9CB27E18','','','example/page_example_block_ui.do','','介绍系统常见的弹出层|matrix-admin/jsp/example/blockUiPageExample.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(215,1,'添加弹层示例','214',1,4,0,'133C9CB27E18','','','','','开头语','10001','dialog_example:add','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(227,1,'ueditor编辑器示例','80',4,3,0,'133C9CB27E18','','','example/page_example_ueditor.do','','matrix-admin/src/main/webapp/jsp/example/ueditorExample.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(249,1,'图片|文件上传','80',5,3,0,'133C9CB27E18','','','example/page_example_file_upload.do','','matrix-admin/src/main/webapp/jsp/example/pageExampleFileUpload.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(250,1,'项目介绍','77',1,2,0,'133C9CB27E18','editor','304180247f1044f6a9a56a78a407fdc3','','','项目介绍','','','2018-07-28 16:42:40',2,'Yangcl','2020-01-10 15:57:23',2,'Yangcl',1),
(251,1,'开发者规约-java','250',1,3,0,'133C9CB27E18','','','example/page_developer_specification.do','','开发者规约','','','2018-07-28 16:42:40',2,'Yangcl','2019-11-08 10:53:42',2,'Yangcl',1),
(252,1,'开发者规约-javascript','250',2,3,0,'133C9CB27E18','','','example/page_developer_specification_js.do','','开发者规约-javascript','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 11:33:25',2,'Yangcl',1),
(253,1,'系统工具','78',2,2,0,'133C9CB27E18','editor','a6a534ca38384f8787a8306ea0f23651','','','系统配置与查询、缓存操作/查看、部署节点列表等','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-23 10:50:07',2,'Yangcl',1),
(254,1,'缓存查看','253',1,3,0,'133C9CB27E18','','','cache/page_cache_system_cache.do','','查看系统中的缓存信息|views\\system\\cache\\system-cache.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-23 14:46:35',2,'Yangcl',1),
(255,1,'发送系统公告','253',2,3,0,'133C9CB27E18','','','websocket/page_websocket_affiche_admin.do','','发送系统公告的页面：views/websocket/affiche/admin.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2022-02-21 17:41:24',2,'Yangcl',1),
(257,1,'删除','127',7,4,0,'133C9CB27E18','','','','','删除','10003','ajax_page_dialog_example:delete','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(258,1,'弹窗分页','127',6,4,0,'133C9CB27E18','','','','','弹窗分页','10003','ajax_page_dialog_example:dialog','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(259,1,'自定义滚动条示例-ul-列表','214',2,4,0,'133C9CB27E18','','','','','自定义滚动条示例-ul-列表','10001','dialog_example:slim_scroll','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(260,1,'自定义滚动条示例-tree','214',3,4,0,'133C9CB27E18','','','','','自定义滚动条示例-tree','10001','dialog_example:slim_scroll:tree','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(261,1,'搜索日志信息','84',1,4,0,'133C9CB27E18','','','','','搜索日志信息','10001','btn-55ee0a123a05484d8cc22235b709c2ff','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(287,1,'系统开放接口','78',3,2,0,'133C9CB27E18','editor','d27673e123c9447f8c789ab260b3adb2','','','包含公司内部接口、开放给第三方的接口等。由这里进行统一管理','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(288,1,'api所属项目','287',1,3,0,'133C9CB27E18','','','apicenter/page_apicenter_api_project_list.do','','api所属项目-应对可能出现的多项目|ac_api_project表的数据|\r\nviews\\api\\project\\api-project-list.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-26 17:28:04',2,'Yangcl',1),
(289,1,'查 询','288',1,4,0,'133C9CB27E18','','','','ajax_api_project_list.do','apicenter/ajax_api_project_list.do\r\n查 询','10002','api_project_list:search','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 13:50:12',2,'Yangcl',1),
(290,1,'重 置','288',2,4,0,'133C9CB27E18','','','','ajax_api_project_list.do','apicenter/ajax_api_project_list.do\r\n重 置','10002','api_project_list:reset','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 13:50:41',2,'Yangcl',1),
(291,1,'添加','288',3,4,0,'133C9CB27E18','','','','ajax_btn_api_project_add.do','添加 apicenter/ajax_btn_api_project_add.do','10001','api_project_list:add','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 13:51:37',2,'Yangcl',1),
(292,1,'删除','288',4,4,0,'133C9CB27E18','','','','ajax_btn_api_project_delete.do','删除 apicenter/ajax_btn_api_project_delete.do','10003','api_project_list:delete','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 13:53:05',2,'Yangcl',1),
(293,1,'修改','288',5,4,0,'133C9CB27E18','','','','ajax_btn_api_project_edit.do','修改 apicenter/ajax_btn_api_project_edit.do','10003','api_project_list:edit','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 13:52:09',2,'Yangcl',1),
(294,1,'跨域白名单','287',2,3,0,'133C9CB27E18','','','apicenter/page_apicenter_include_domain_list.do','','记录系统允许跨域的域名|\r\nviews\\api\\domain\\api-include-domain-list.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 17:30:29',2,'Yangcl',1),
(295,1,'查询','294',1,4,0,'133C9CB27E18','','','','ajax_include_domain_page_list.do','查询','10002','include_domain_list:search','2018-07-28 16:42:40',2,'Yangcl','2021-03-15 16:13:19',2,'Yangcl',1),
(296,1,'重置','294',2,4,0,'133C9CB27E18','','','','ajax_include_domain_page_list.do','重置','10002','include_domain_list:reset','2018-07-28 16:42:40',2,'Yangcl','2021-03-15 16:13:31',2,'Yangcl',1),
(297,1,'添加','294',3,4,0,'133C9CB27E18','','','','ajax_btn_api_domain_add.do','添加 apicenter/ajax_btn_api_domain_add.do','10001','include_domain_list:add','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 17:31:21',2,'Yangcl',1),
(298,1,'删除','294',4,4,0,'133C9CB27E18','','','','ajax_btn_api_domain_delete.do','删除 apicenter/ajax_btn_api_domain_delete.do','10001','include_domain_list:delete','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 11:21:39',2,'Yangcl',1),
(299,1,'修改','294',5,4,0,'133C9CB27E18','','','','ajax_btn_api_domain_edit.do','修改 apicenter/ajax_btn_api_domain_edit.do','10001','include_domain_list:edit','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 09:55:22',2,'Yangcl',1),
(300,1,'api信息树','287',3,3,0,'133C9CB27E18','','','apicenter/page_apicenter_api_tree.do','','api信息树|views\\api\\info\\api-tree.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 16:32:06',2,'Yangcl',1),
(301,1,'请求者信息','287',4,3,0,'133C9CB27E18','','','apicenter/page_apicenter_request_info.do','','请求者信息维护页面|\r\nmatrix-admin/src/main/webapp/jsp/api/request/api-request-info-list.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(302,1,'查 询','301',1,4,0,'133C9CB27E18','','','','ajax_request_info_list.do','查 询 apicenter/ajax_request_info_list.do','10002','api_requester_info:search','2018-07-28 16:42:40',2,'Yangcl','2021-03-21 18:00:01',2,'Yangcl',1),
(303,1,'重 置','301',2,4,0,'133C9CB27E18','','','','ajax_request_info_list.do','重 置 apicenter/ajax_request_info_list.do','10002','api_requester_info:reset','2018-07-28 16:42:40',2,'Yangcl','2021-03-21 18:00:06',2,'Yangcl',1),
(304,1,'添加','301',3,4,0,'133C9CB27E18','','','','','添加','10001','api_requester_info:add','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 16:38:40',2,'Yangcl',1),
(305,1,'启用|禁用','301',4,4,0,'133C9CB27E18','','','','','删除','10003','api_requester_info:delete','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 16:38:40',2,'Yangcl',1),
(306,1,'修改','301',5,4,0,'133C9CB27E18','','','','','修改','10003','api_requester_info:edit','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 16:38:40',2,'Yangcl',1),
(308,1,'删除','83',2,4,0,'133C9CB27E18','','','','ajax_btn_delete_info','删除按钮','10001','add_info_example:delete','2018-07-28 16:42:40',2,'Yangcl','2019-12-10 15:38:52',2,'Yangcl',1),
(309,1,'弹窗-删除','127',1,4,0,'133C9CB27E18','','','','','弹窗中的删除按钮','10001','ajax_page_dialog_example:dialog:delete','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(310,1,'弹窗-编辑','127',3,4,0,'133C9CB27E18','','','','','弹窗中的编辑按钮','10001','ajax_page_dialog_example:dialog:edit','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(312,1,'重置','117',2,4,0,'133C9CB27E18','','','','ajax_system_user_list.do','userInfo/ajax_system_user_list.do','10002','system_user_list:reset','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(313,1,'添加','117',3,4,0,'133C9CB27E18','','','','ajax_btn_add_system_user.do','userInfo/ajax_btn_add_system_user.do','10002','system_user_list:add','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(314,1,'删除','117',4,4,0,'133C9CB27E18','','','','ajax_btn_delete_system_user.do','userInfo/ajax_btn_delete_system_user.do','10003','system_user_list:delete','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(315,1,'修改','117',5,4,0,'133C9CB27E18','','','','ajax_btn_edit_sys_user.do','userInfo/ajax_btn_edit_sys_user.do','10003','system_user_list:edit','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(316,1,'用户角色','117',7,5,0,'133C9CB27E18','','','dialog_permissions_system_role_list.do','','permissions/dialog_permissions_system_role_list.do','10001','system_user_list:user_role','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(317,1,'系统角色列表-弹框-分配','117',11,4,0,'133C9CB27E18','','','','ajax_btn_allot_user_role_submit.do','sysrole/ajax_btn_allot_user_role_submit.do\r\n给指定用户分配一个角色','10003','system_user_list:allot_submit','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(318,1,'重置','118',2,4,0,'133C9CB27E18','','','','ajax_system_role_list.do','sysrole/ajax_system_role_list.do','10001','system_role_list:reset','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 13:59:55',2,'Yangcl',1),
(319,1,'角色功能','118',4,4,0,'133C9CB27E18','','','','default','default：仅做权限控制，不发起ajax请求','10001','system_role_list:role_function','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 14:09:52',2,'Yangcl',1),
(320,1,'修改','118',5,4,0,'133C9CB27E18','','','','ajax_btn_edit_mc_role_only.do','sysrole/ajax_btn_edit_mc_role_only.do','10001','system_role_list:edit','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 14:01:17',2,'Yangcl',1),
(321,1,'删除','118',6,4,0,'133C9CB27E18','','','','ajax_btn_delete_mc_role.do','sysrole/ajax_btn_delete_mc_role.do','10001','system_role_list:delete','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 14:01:41',2,'Yangcl',1),
(322,1,'角色功能弹窗-提交','118',7,4,0,'133C9CB27E18','','','','ajax_btn_edit_mc_role.do','sysrole/ajax_btn_edit_mc_role.do','10001','system_role_list:dialog_submit','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 14:13:08',2,'Yangcl',1),
(323,1,'保存|修改','300',1,4,0,'133C9CB27E18','','','','default','保存|修改。页面展示为【保存】按钮，但针对添加行为对应apicenter/ajax_api_info_add.do，针对修改行为对应apicenter/ajax_api_info_edit.do。由于同一个按钮对应两种Controller请求路径，所以【按钮请求路径】初设置为default','10001','api_tree:submit','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 16:53:39',2,'Yangcl',1),
(325,1,'完整开发流程','77',4,2,0,'133C9CB27E18','editor','51e91e7ba11d4a61918d100dd124d9f0','','','贴近实际业务来展示一个完整的开发流程','','','2018-07-28 16:42:40',2,'Yangcl','2020-01-10 15:57:23',2,'Yangcl',1),
(326,1,'楼盘信息开发实例','325',1,3,0,'133C9CB27E18','','','demos/page_demo_landed_property.do','','复制即所得，抄永远比写来的快。但请小心别抄错了。。','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(465,1,'dubbo-admin','78',4,2,0,'133C9CB27E18','editor','17f2b7b4b60f455e8fdcbb3a6e37fe4b','','','','','','2018-11-04 13:02:19',2,'Yangcl','2018-11-04 13:02:19',2,'Yangcl',1),
(466,1,'Dubbo应用服务列表','465',1,3,0,'133C9CB27E18','','','application/page_application_dubbo_project_list.do','',' @description: Dubbo应用服务列表|Dubbo项目名称列表','','','2018-11-04 13:06:32',2,'Yangcl','2018-11-04 13:25:59',2,'Yangcl',1),
(473,1,'重置密码','117',6,4,0,'133C9CB27E18','','','','ajax_btn_password_reset.do','userInfo/ajax_btn_password_reset.do','10003','system_user_list:password_reset','2018-11-14 16:28:31',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(479,1,'分布式定时任务','78',5,2,0,'133C9CB27E18','editor','4930eab924ee4e10ad016882bb7772a3','','','分布式定时任务','','','2018-12-20 17:42:07',2,'Yangcl','2018-12-20 17:42:07',2,'Yangcl',1),
(480,1,'定时任务列表','479',1,3,0,'133C9CB27E18','','','quartz/page_quartz_job_info_list.do','','定时任务列表','','','2018-12-20 17:43:15',2,'Yangcl','2018-12-20 17:43:15',2,'Yangcl',1),
(481,1,'添加','480',1,4,0,'133C9CB27E18','','','','ajax_btn_job_info_add.do','quartz/ajax_btn_job_info_add.do','10001','job_info_list:add','2018-12-20 17:59:41',2,'Yangcl','2021-04-19 17:41:18',2,'Yangcl',1),
(482,1,'重 置','480',2,4,0,'133C9CB27E18','','','','','job_info_list:reset','10001','job_info_list:reset','2018-12-20 18:00:39',2,'Yangcl','2018-12-20 18:00:39',2,'Yangcl',1),
(483,1,'查 询','480',3,4,0,'133C9CB27E18','','','','','job_info_list:search','10001','job_info_list:search','2018-12-20 18:01:34',2,'Yangcl','2018-12-20 18:01:34',2,'Yangcl',1),
(484,1,'删除','480',4,4,0,'133C9CB27E18','','','','ajax_btn_job_info_delete.do','quartz/ajax_btn_job_info_delete.do','10003','job_info_list:delete','2018-12-20 20:48:17',2,'Yangcl','2021-04-20 11:22:18',2,'Yangcl',1),
(485,1,'修改','480',5,4,0,'133C9CB27E18','','','','ajax_btn_job_info_edit.do','quartz/ajax_btn_job_info_edit.do','10003','job_info_list:edit','2018-12-20 20:48:46',2,'Yangcl','2021-04-19 18:02:29',2,'Yangcl',1),
(486,1,'手动触发','480',6,4,0,'133C9CB27E18','','','','','job_info_list:run','10003','job_info_list:run','2018-12-20 20:49:11',2,'Yangcl','2018-12-20 21:00:13',2,'Yangcl',1),
(487,1,'执行记录','480',7,4,0,'133C9CB27E18','','','','','job_info_list:run_log','10003','job_info_list:run_log','2018-12-20 20:59:48',2,'Yangcl','2018-12-20 20:59:48',2,'Yangcl',1),
(488,1,'暂停','480',8,4,0,'133C9CB27E18','','','','','暂停一个定时任务','10003','job_info_list:pause','2018-12-21 14:22:49',2,'Yangcl','2018-12-21 14:22:49',2,'Yangcl',1),
(489,1,'全部暂停','480',9,4,0,'133C9CB27E18','','','','','暂停全部定时任务','10002','job_info_list:pause_all','2018-12-21 14:24:33',2,'Yangcl','2018-12-21 14:24:33',2,'Yangcl',1),
(490,1,'恢复','480',10,4,0,'133C9CB27E18','','','','','恢复一个指定的定时任务到运行状态','10003','job_info_list:resume','2018-12-21 14:25:52',2,'Yangcl','2018-12-21 14:25:52',2,'Yangcl',1),
(491,1,'全部恢复','480',11,4,0,'133C9CB27E18','','','','','将全部定时任务恢复到运行状态','10002','job_info_list:resume_all','2018-12-21 14:26:59',2,'Yangcl','2018-12-21 14:26:59',2,'Yangcl',1),
(492,1,'详情','480',12,4,0,'133C9CB27E18','','','','','定时任务详情','10003','job_info_list:detail','2018-12-25 14:21:25',2,'Yangcl','2018-12-25 14:21:25',2,'Yangcl',1),
(493,1,'定时任务分组列表','479',2,3,0,'133C9CB27E18','','','quartz/page_quartz_job_group_list.do','','定时任务分组列表：views/quartz/group/job-group-list','','','2018-12-27 10:57:42',2,'Yangcl','2021-04-28 17:05:03',2,'Yangcl',1),
(494,1,'添加','493',1,4,0,'133C9CB27E18','','','','ajax_btn_job_group_add.do','quartz/ajax_btn_job_group_add.do','10001','job_group_list:add','2018-12-27 10:59:42',2,'Yangcl','2021-04-28 17:42:04',2,'Yangcl',1),
(495,1,'重 置','493',2,4,0,'133C9CB27E18','','','','','job_group_list:reset','10001','job_group_list:reset','2018-12-27 11:00:05',2,'Yangcl','2018-12-27 11:00:05',2,'Yangcl',1),
(496,1,'查 询','493',3,4,0,'133C9CB27E18','','','','','job_group_list:search','10001','job_group_list:search','2018-12-27 11:00:27',2,'Yangcl','2018-12-27 11:00:27',2,'Yangcl',1),
(497,1,'删除','493',4,4,0,'133C9CB27E18','','','','ajax_btn_job_group_delete.do','quartz/ajax_btn_job_group_delete.do','10003','job_group_list:delete','2018-12-27 11:00:49',2,'Yangcl','2021-05-06 15:13:48',2,'Yangcl',1),
(498,1,'修改','493',5,4,0,'133C9CB27E18','','','','ajax_btn_job_group_edit.do','quartz/ajax_btn_job_group_edit.do','10003','job_group_list:edit','2018-12-27 11:01:14',2,'Yangcl','2021-05-06 15:13:19',2,'Yangcl',1),
(499,1,'定时任务日志列表','479',3,3,0,'133C9CB27E18','','','quartz/page_quartz_job_log_list.do','','定时任务日志列表','','','2018-12-29 17:00:37',2,'Yangcl','2018-12-29 17:00:37',2,'Yangcl',1),
(510,1,'部署节点列表','465',2,3,0,'133C9CB27E18','','','route/page_route_dubbo_node_list.do','','Dubbo应用服务部署节点列表','','','2019-01-07 14:15:06',2,'Yangcl','2019-01-07 14:15:06',2,'Yangcl',1),
(600,1,'sentinel控制台','465',3,3,0,'133C9CB27E18','','','application/page_goto_sentinel_dashboard.do','','','','','2019-06-13 16:35:50',2,'Yangcl','2019-06-13 16:35:50',2,'Yangcl',1),
(1060,1,'手动','480',13,4,0,'133C9CB27E18','','','','','手动触发定时任务|立刻执行定时任务','10003','job_info_list:handling','2019-09-27 18:12:50',2,'Yangcl','2019-09-27 18:12:50',2,'Yangcl',1),
(1071,1,'权限重置','117',8,4,0,'133C9CB27E18','','','','ajax_btn_user_cache_reload.do','sysrole/ajax_btn_user_cache_reload.do\r\n重置系统用户的所有信息包括：McSysFunc、McRole、McUserRole、UserInfoNp','10001','system_user_list:reload','2019-12-10 15:59:17',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(1072,1,'角色功能弹窗-解绑','118',8,4,0,'133C9CB27E18','','','','ajax_btn_relieve_mc_role.do','sysrole/ajax_btn_relieve_mc_role.do','10001','system_role_list:dialog_cancel','2019-12-11 14:12:31',2,'Yangcl','2019-12-11 14:55:07',2,'Yangcl',1),
(1073,1,'系统角色列表-弹框-查询','117',9,4,0,'133C9CB27E18','','','','ajax_user_role_list.do','sysrole/ajax_user_role_list.do','10002','system_user_list:dialog_search','2019-12-13 17:08:35',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(1074,1,'系统角色列表-弹框-重置','117',10,4,0,'133C9CB27E18','','','','ajax_user_role_list.do','sysrole/ajax_user_role_list.do','10002','system_user_list:dialog_reset','2019-12-13 17:20:38',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(1075,1,'系统角色列表-弹框-移除','117',12,4,0,'133C9CB27E18','','','','ajax_btn_allot_user_role_remove.do','sysrole/ajax_btn_allot_user_role_remove.do\r\n解除角色绑定，同时删除缓存','10003','system_user_list:allot_remove','2019-12-17 17:50:48',2,'Yangcl','2021-07-23 16:57:14',2,'Yangcl',1),
(1076,1,'批量删除缓存','254',1,4,0,'133C9CB27E18','','','','ajax_btn_batch_delete.do','cache/ajax_btn_batch_delete.do\r\n批量删除缓存','10001','system_cache:batch_delete','2019-12-23 11:15:27',2,'Yangcl','2019-12-23 11:45:02',2,'Yangcl',1),
(1077,1,'设置缓存','254',2,4,0,'133C9CB27E18','','','','ajax_btn_reset_cache.do','cache/ajax_btn_reset_cache.do\r\n过期时间半小时','10001','system_cache:reset','2019-12-23 11:24:29',2,'Yangcl','2019-12-23 11:24:29',2,'Yangcl',1),
(1078,1,'设置缓存(永久)','254',3,4,0,'133C9CB27E18','','','','ajax_btn_reset_cache_forever.do','cache/ajax_btn_reset_cache_forever.do\r\n永久设置缓存','10001','system_cache:reset_forever','2019-12-23 11:26:13',2,'Yangcl','2019-12-23 11:26:13',2,'Yangcl',1),
(1079,1,'删除缓存','254',4,4,0,'133C9CB27E18','','','','ajax_btn_delete_cache.do','cache/ajax_btn_delete_cache.do\r\n批量删除缓存','10001','system_cache:delete_cache','2019-12-23 11:38:05',2,'Yangcl','2019-12-23 11:38:05',2,'Yangcl',1),
(1080,1,'获取缓存','254',5,4,0,'133C9CB27E18','','','','ajax_btn_get_cache.do','cache/ajax_btn_get_cache.do','10001','system_cache:get_cache','2019-12-23 11:40:22',2,'Yangcl','2019-12-23 11:40:22',2,'Yangcl',1),
(1081,1,'测试','300',1,4,0,'133C9CB27E18','','','','defalut','API树形结构列表|测试按钮。点击按钮将打开一个弹框，用于测试接口数据','10001','api_tree:test','2020-01-07 16:55:42',2,'Yangcl','2020-01-07 16:55:42',2,'Yangcl',1),
(1082,1,'删除','300',2,4,0,'133C9CB27E18','','','','ajax_btn_api_remove.do','删除一个api节点 apicenter/ajax_btn_api_remove.do','10001','api_tree:remove','2020-01-07 18:12:43',2,'Yangcl','2020-01-07 18:12:43',2,'Yangcl',1),
(1084,1,'查 询','499',1,4,0,'133C9CB27E18','','','','ajax_job_log_list.do','quartz/ajax_job_log_list','10001','job_log_list:search','2021-05-06 18:07:53',2,'Yangcl','2021-05-06 18:07:53',2,'Yangcl',1),
(1085,1,'重 置','499',2,4,0,'133C9CB27E18','','','','ajax_job_log_list.do','quartz/ajax_job_log_list','10001','job_log_list:reset','2021-05-06 18:09:09',2,'Yangcl','2021-05-06 18:09:09',2,'Yangcl',1),
(1086,1,'详情','499',3,4,0,'133C9CB27E18','','','','ajax_job_log_detail.do','quartz/ajax_job_log_detail.do','10001','job_log_list:detail','2021-05-06 18:10:44',2,'Yangcl','2021-05-06 18:10:44',2,'Yangcl',1),
(1115,1,'接受系统公告','253',3,3,0,'133C9CB27E18','','','websocket/page_websocket_affiche_user.do','','接受系统公告的测试页面。','','','2022-02-21 17:46:20',2,'Yangcl','2022-02-21 17:46:20',2,'Yangcl',1),
(1116,1,'建立连接','255',1,4,0,'133C9CB27E18','','','','ajax_btn_affiche_connect.do','建立ws链接','10001','affiche:connect','2022-02-21 19:43:58',2,'Yangcl','2022-03-01 11:09:04',2,'Yangcl',1),
(1117,1,'发送通告','255',3,4,0,'133C9CB27E18','','','','ajax_btn_affiche_send.do','发送通告信息','10001','affiche:send','2022-02-21 19:44:38',2,'Yangcl','2022-03-01 11:09:04',2,'Yangcl',1),
(1118,1,'断开连接','255',2,4,0,'133C9CB27E18','','','','ajax_btn_affiche_disconnect.do','断开长链接','10001','affiche:disconnect','2022-02-21 19:52:59',2,'Yangcl','2022-03-01 11:09:04',2,'Yangcl',1),
(1119,1,'一对一聊天','253',4,3,0,'133C9CB27E18','','','websocket/page_websocket_p2p.do','','系统一对一聊天功能','','','2022-03-01 11:05:49',2,'Yangcl','2022-03-01 11:05:49',2,'Yangcl',1),
(1120,1,'建立连接','1119',1,4,0,'133C9CB27E18','','','','ajax_btn_p2p_connect.do','建立连接','10001','p2p:connect','2022-03-01 11:08:58',2,'Yangcl','2022-03-01 11:09:58',2,'Yangcl',1),
(1121,1,'断开连接','1119',2,4,0,'133C9CB27E18','','','','ajax_btn_p2p_disconnect.do','断开连接','10001','p2p:disconnect','2022-03-01 11:09:53',2,'Yangcl','2022-03-01 11:09:53',2,'Yangcl',1),
(1122,1,'发送消息','1119',3,4,0,'133C9CB27E18','','','','ajax_btn_p2p_send.do','发送消息','10001','p2p:send','2022-03-01 11:10:48',2,'Yangcl','2022-03-01 11:10:48',2,'Yangcl',1),
(1123,1,'多人聊天','253',5,3,0,'133C9CB27E18','','','websocket/page_websocket_group.do','','组群|多人聊天|消息群发','','','2022-03-02 11:22:30',2,'Yangcl','2022-03-02 11:22:30',2,'Yangcl',1),
(1124,1,'建立连接','1123',1,4,0,'133C9CB27E18','','','','ajax_btn_group_connect.do','建立连接','10001','group:connect','2022-03-02 11:25:16',2,'Yangcl','2022-03-02 11:25:16',2,'Yangcl',1),
(1125,1,'断开连接','1123',2,4,0,'133C9CB27E18','','','','ajax_btn_group_disconnect.do','断开连接','10001','group:disconnect','2022-03-02 11:25:58',2,'Yangcl','2022-03-02 11:25:58',2,'Yangcl',1),
(1126,1,'发送消息','1123',3,4,0,'133C9CB27E18','','','','ajax_btn_group_send.do','发送消息','10001','group:send','2022-03-02 11:26:40',2,'Yangcl','2022-03-02 11:26:40',2,'Yangcl',1);

/*Table structure for table `mc_user_info` */

DROP TABLE IF EXISTS `mc_user_info`;

CREATE TABLE `mc_user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'zid',
  `cid` bigint DEFAULT '0' COMMENT 'tc_shop_info表主键,即custom id | Leader后台创建的用户默认为0 | 商家端均为-1代表关联多个店铺;一个店铺则包含具体店铺id',
  `tenant_info_id` bigint DEFAULT '0' COMMENT 'tc_tenant_info表id,如果cid=-1则代表管理多个店铺,其所在的店铺的用户列表不显示这条记录;但会关联租户主键',
  `user_name` varchar(25) DEFAULT '' COMMENT '用户姓名',
  `password` varchar(45) DEFAULT '' COMMENT '密码',
  `user_code` varchar(20) DEFAULT '' COMMENT '员工编号|type=user 则此字段生效',
  `mc_organization_id` bigint DEFAULT '0' COMMENT '组织机构id|type=user 则此字段生效|默认0 代表无归属机构',
  `type` varchar(20) DEFAULT 'user' COMMENT 'leader Leader后台用户|admin 其他平台管理员|user其他平台用户',
  `platform` varchar(16) DEFAULT 'P01' COMMENT '平台标识码',
  `flag` int DEFAULT '1' COMMENT '启用状态；1启用 2停用|type=user 则此字段生效',
  `idcard` varchar(20) DEFAULT '' COMMENT '身份证号',
  `sex` int DEFAULT '2' COMMENT '性别 1：男 2：女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(25) DEFAULT NULL COMMENT 'qq号码',
  `pic_url` varchar(256) DEFAULT '' COMMENT '用户头像',
  `page_css` varchar(40) DEFAULT 'default' COMMENT '后台页面css样式',
  `remark` longtext COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20072026234180 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='API表格';

/*Data for the table `mc_user_info` */

insert  into `mc_user_info`(`id`,`cid`,`tenant_info_id`,`user_name`,`password`,`user_code`,`mc_organization_id`,`type`,`platform`,`flag`,`idcard`,`sex`,`birthday`,`mobile`,`email`,`qq`,`pic_url`,`page_css`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(1,0,0,'admin','0988a08e0e7cdaf2b395133b0fbba289','',0,'leader','133C9CB27E18',1,'2',1,NULL,'2','2','2','','default','2','2018-07-28 23:30:08',1,'admin','2021-07-20 17:10:09',1,'admin',1),
(2,0,0,'Yangcl','71fb5b225e96fc8ec99e8fe85e35b40a','',0,'leader','133C9CB27E18',1,'',1,NULL,'18514037761','yangchenglin@300.cn',NULL,'','default','杨成琳-Leader后台管理员','2018-09-25 17:50:21',1,'admin','2019-07-23 10:59:37',2,'Yangcl',1),
(20072026234178,0,0,'user1','e10adc3949ba59abbe56e057f20f883e','',0,'leader','133C9CB27E18',1,'',1,NULL,'13521215656','13521215656@qq.com',NULL,'','default','密码：123456','2022-02-21 17:51:40',2,'Yangcl','2022-02-21 17:51:40',2,'Yangcl',1),
(20072026234179,0,0,'user2','e10adc3949ba59abbe56e057f20f883e','',0,'leader','133C9CB27E18',1,'',1,NULL,'13567678989','13567678989@qq.com',NULL,'','default','密码：123456','2022-02-21 17:52:16',2,'Yangcl','2022-02-21 17:52:16',2,'Yangcl',1);

/*Table structure for table `mc_user_info_organization` */

DROP TABLE IF EXISTS `mc_user_info_organization`;

CREATE TABLE `mc_user_info_organization` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '平台用户与组织机构关联表|mc_user_info表 type=user,则此表存在记录',
  `cid` bigint DEFAULT '0' COMMENT '客户id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  `mc_user_info_id` bigint DEFAULT NULL,
  `mc_organization_id` bigint DEFAULT NULL,
  `platform` varchar(20) DEFAULT NULL COMMENT '平台标识码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_user_info_organization` */

/*Table structure for table `mc_user_role` */

DROP TABLE IF EXISTS `mc_user_role`;

CREATE TABLE `mc_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'zid',
  `mc_user_id` bigint DEFAULT NULL COMMENT 'mc_user_info 表 主键',
  `mc_role_id` bigint DEFAULT NULL COMMENT 'mc_role表主键',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=521 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_user_role` */

insert  into `mc_user_role`(`id`,`mc_user_id`,`mc_role_id`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(503,2,116,'','2019-09-27 18:13:07',2,'Yangcl','2019-09-27 18:13:07',2,'Yangcl',1),
(504,1,116,'','2019-09-27 18:13:11',2,'Yangcl','2019-09-27 18:13:11',2,'Yangcl',1),
(519,20072026234179,196,NULL,'2022-02-21 17:52:26',2,'Yangcl','2022-02-21 17:52:26',2,'Yangcl',1),
(520,20072026234178,196,NULL,'2022-02-21 17:52:38',2,'Yangcl','2022-02-21 17:52:38',2,'Yangcl',1);

/*Table structure for table `store_info` */

DROP TABLE IF EXISTS `store_info`;

CREATE TABLE `store_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '注册门店表id',
  `cid` bigint DEFAULT '0' COMMENT '客户id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间(消费时间)',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  `name` varchar(40) DEFAULT NULL COMMENT '门店名称',
  `mc_organization_id` bigint DEFAULT NULL COMMENT '门店对应的mc_organization表的主键',
  `province_id` bigint DEFAULT NULL COMMENT '门店省份id',
  `city_id` bigint DEFAULT NULL COMMENT '门店城市id',
  `area_id` bigint DEFAULT NULL COMMENT '门店区县id',
  `address` varchar(128) DEFAULT NULL COMMENT '门店详细地址',
  `phone` varchar(20) DEFAULT NULL COMMENT '门店联系电话',
  `star_level` smallint DEFAULT '0' COMMENT '门店星级',
  `type` smallint DEFAULT '0' COMMENT '1=加盟店 2直营店 3代理商 4连锁店 0其他',
  `status` smallint DEFAULT '1' COMMENT '0 暂停营业 1正常运营',
  `remark` varchar(255) DEFAULT NULL COMMENT '暂停营业原因',
  `dict_vocation_info_id` bigint DEFAULT NULL COMMENT '门店所属行业id',
  `longitude` varchar(52) DEFAULT NULL COMMENT '地理位置:经度',
  `latitude` varchar(52) DEFAULT NULL COMMENT '地理位置:纬度',
  `manager_id` bigint DEFAULT NULL COMMENT '负责人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='注册门店表';

/*Data for the table `store_info` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
