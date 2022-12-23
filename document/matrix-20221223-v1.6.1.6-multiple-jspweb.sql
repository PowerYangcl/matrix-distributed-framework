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
) ENGINE=InnoDB AUTO_INCREMENT=80161092 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='alter table `ac_api_info` AUTO_INCREMENT=80160001';

/*Data for the table `ac_api_info` */

insert  into `ac_api_info`(`id`,`name`,`target`,`dto_info`,`atype`,`module`,`processor`,`domain`,`parent_id`,`seqnum`,`discard`,`login`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(80160101,'根据API的target返回DTO的Json文本','API-COMMON-FIND-DTO','{}','private','matrix-api','common.ApiFindDtoProcessor',0,8,6,1,1,'根据API缓存的target返回查询消息体','2018-10-09 11:07:55',2,'Yangcl','2021-07-20 17:11:13',2,'Yangcl',1),
(80160102,'根据Api请求者的key，找到对应的value','API-COMMON-FIND-VALUE-BY-KEY','{}','private','matrix-api','common.ApiFindValueByKey',0,8,7,0,1,'根据请求者的key，找到对应的value','2018-10-09 11:10:39',2,'Yangcl','2021-07-20 17:10:49',2,'Yangcl',1),
(80160155,'用户登录接口','MANAGER-API-100','{\"userName\":\"admin\",\"password\":\"admin@root\",\r\n\"platform\":\"133C9CB27E18\"}','private','matrix-manager-api','privates.ManagerApi100Processor',0,9,3,1,0,'验证用户登录信息|客户端用户：nodejs/IOS平板等','2018-10-11 15:49:38',2,'Yangcl','2022-08-16 19:43:24',2,'Yangcl',1),
(80160156,'用户退出系统接口','MANAGER-API-101','{}','private','matrix-manager-api','privates.ManagerApi101Processor',0,9,4,1,1,'退出系统登录|客户端用户：nodejs/IOS平板等','2018-10-11 15:51:16',2,'Yangcl','2019-03-27 19:27:43',2,'Yangcl',1),
(80160157,'用户列表页数据','MANAGER-API-102','{\"startIndex\":1,\"pageSize\":10,\"cid\":-1}','private','matrix-manager-api','privates.ManagerApi102Processor',0,9,5,1,1,'用户列表页数据展示','2018-10-11 15:52:16',2,'Yangcl','2020-01-10 17:35:11',2,'Yangcl',1),
(80160158,'添加用户界面-绘制所属公司和平台分配两个控件','MANAGER-API-103',NULL,'private','matrix-manager-api','privates.ManagerApi103Processor',0,9,6,1,1,'添加用户界面-绘制所属公司（下拉框列表）和平台分配（单选按钮）','2018-10-12 11:37:29',2,'Yangcl','2018-10-12 11:37:29',2,'Yangcl',1),
(80160183,'添加用户','MANAGER-API-104','{}','private','matrix-manager-api','privates.ManagerApi104Processor',0,9,7,1,1,'添加用户|MANAGER-API-104','2018-10-12 19:40:09',2,'Yangcl','2022-04-19 17:43:23',2,'Yangcl',1),
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
(80160275,'修改用户密码','MANAGER-API-129',NULL,'private','matrix-api','privates.ManagerApi129Processor',0,9,32,1,1,'修改用户密码','2018-10-30 16:59:55',2,'Yangcl','2018-10-30 16:59:55',2,'Yangcl',1),
(80160277,'文件上传接口','Api-File-Remote-Upload',NULL,'private','matrix-api','common.ApiFileRemoteUpload',0,1,1,1,1,'系统文件上传接口|平台公共接口的一部分','2018-11-01 16:12:56',2,'Yangcl','2018-11-01 16:12:56',2,'Yangcl',0),
(80160325,'获取API接口项目中的Config配置信息','API-COMMON-CONFIG-INFO','{}','private','matrix-api','common.ApiFindConfigInfoProcessor',0,8,8,1,1,'获取API接口项目中的Config配置信息|如果部署多个API接口服务器，则可能随机获取其中的一个，但会返回一个标识的IP地址作为区分依据','2018-11-26 19:09:15',2,'Yangcl','2020-01-13 09:33:07',2,'Yangcl',1),
(80160326,'获取验证码接口','MANAGER-API-140','{}','private','matrix-manager-api','privates.ManagerApi140Processor',0,9,37,1,0,'API:获取验证码接口','2018-11-27 14:49:48',2,'Yangcl','2019-03-27 19:11:03',2,'Yangcl',1),
(80160340,'获取登录用户的角色','MANAGER-API-142',NULL,'private','matrix-manager-api','privates.ManagerApi142Processor',0,9,40,1,1,'获取登录用户的角色','2018-12-19 19:14:11',2,'Yangcl','2018-12-19 19:14:11',2,'Yangcl',1),
(80161090,'用户登录接口2','MANAGER-API-200','{id:1}','private','matrix-manager-api','privates.ManagerApi100Processor',1,32,1,0,0,'用户登录接口2','2021-07-16 18:37:40',2,'Yangcl','2021-07-20 15:16:54',2,'Yangcl',0),
(80161091,'Validate 测试','API-VALIDATION-TEST','{\"id\":1111,\"target\":\"\",\"atype\":\"\"}','private','matrix-api','common.ApiValidationTestProcessor',0,8,4,1,0,'validate 测试','2022-03-10 18:24:40',2,'Yangcl','2022-03-10 18:24:40',2,'Yangcl',1);

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

/*Table structure for table `gw_route` */

DROP TABLE IF EXISTS `gw_route`;

CREATE TABLE `gw_route` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '网关路由规则表',
  `route_id` varchar(52) NOT NULL COMMENT '路由规则ID，必填项，且唯一',
  `uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '路由转发路径',
  `active` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '环境：dev|test|pre|master or prod',
  `request_type` varchar(8) NOT NULL COMMENT '请求方式：get|post|put|delete',
  `route_type` char(10) DEFAULT '''project''' COMMENT '路由规则类型：project-项目 url-单接口',
  `rate_flow_mark` int DEFAULT '0' COMMENT '是否开启流量标记：0-不标记 1-标记(gw_route_rate_flow_key_words)',
  `request_snapshot_mark` int DEFAULT '0' COMMENT '是否保存流量快照：0-不保存 1-保存(用于模拟压测使用)',
  `snapshot_count` int DEFAULT '0' COMMENT '保存流量快照的请求条数：比如5000条',
  `snapshot_begin_time` datetime DEFAULT NULL COMMENT '保存流量快照的开始时间：保存此时间段内的请求',
  `snapshot_end_time` datetime DEFAULT NULL COMMENT '保存流量快照的结束时间',
  `status` int DEFAULT '0' COMMENT '此条规则是否生效：0-不生效 1-生效；默认不生效，生效需要在列表触发',
  `description` varchar(52) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `routeId` (`route_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `gw_route` */

insert  into `gw_route`(`id`,`route_id`,`uri`,`active`,`request_type`,`route_type`,`rate_flow_mark`,`request_snapshot_mark`,`snapshot_count`,`snapshot_begin_time`,`snapshot_end_time`,`status`,`description`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(21,'routeId-prod','http://10.226.192.114:8080/bmp-ironic-api/api.do','prod','post','project',1,1,5000000,'2022-08-10 00:00:00','2022-08-30 00:00:00',1,'线上会员接口服务','2022-08-18 14:03:49',2,'Yangcl','2022-11-17 18:47:43',2,'Yangcl',1),
(24,'routeId-pre','http://122.10.20.115:8080/matrix-vip-api/api.do','pre','get','url',1,1,1000,'2022-12-01 00:00:00','2022-12-31 00:00:00',0,'预发会员接口服务','2022-08-18 14:12:56',2,'Yangcl','2022-11-17 18:45:30',2,'Yangcl',1);

/*Table structure for table `gw_route_predicates` */

DROP TABLE IF EXISTS `gw_route_predicates`;

CREATE TABLE `gw_route_predicates` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '网关路由规则表-断言规则表',
  `route_id` varchar(52) NOT NULL DEFAULT '' COMMENT 'route_id 一对多',
  `predicate` varchar(52) NOT NULL DEFAULT '' COMMENT '断言规则：Path|After|Header等',
  `predicate_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '断言规则对应值',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `gw_route_predicates` */

insert  into `gw_route_predicates`(`id`,`route_id`,`predicate`,`predicate_value`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(11,'routeId-prod','Path','bmp/ironic/api.do','2022-08-18 14:03:49',2,'Yangcl','2022-08-18 14:03:49',2,'Yangcl',1),
(12,'routeId-prod','After','2022-10-20T17:42:47.789-07:00[America/Denver]','2022-08-18 14:03:49',2,'Yangcl','2022-08-18 14:03:49',2,'Yangcl',1),
(13,'routeId-pre','Path','matrix/vip/api.do','2022-08-18 14:12:56',2,'Yangcl','2022-11-16 18:23:30',2,'Yangcl',1),
(14,'routeId-pre','After','2022-10-20T17:42:47.789-07:00[America/Denver]','2022-08-18 14:12:56',2,'Yangcl','2022-11-16 18:23:30',2,'Yangcl',1),
(15,'routeId-prod','Header','X-Request-Id,jd-inner','2022-08-19 11:14:18',2,'Yangcl','2022-08-19 11:13:37',2,'Yangcl',1),
(16,'routeId-pre','Path','matrix/vip/api.do','2022-11-16 18:23:30',2,'Yangcl','2022-11-17 18:04:25',2,'Yangcl',1),
(17,'routeId-pre','After','2022-10-20T17:42:47.789-07:00[America/Denver]','2022-11-16 18:23:30',2,'Yangcl','2022-11-17 18:04:25',2,'Yangcl',1),
(18,'routeId-pre','Host','127.0.0.1','2022-11-16 18:23:30',2,'Yangcl','2022-11-17 18:04:25',2,'Yangcl',1);

/*Table structure for table `gw_route_rate_flow` */

DROP TABLE IF EXISTS `gw_route_rate_flow`;

CREATE TABLE `gw_route_rate_flow` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '流量统计落库，针对指定接口的关键词',
  `route_id` varchar(52) NOT NULL COMMENT 'routeId，一对多',
  `key_word_id` bigint NOT NULL COMMENT 'gw_route_rate_flow_key_words表主键',
  `redis_key` varchar(52) NOT NULL COMMENT '统计维度的key值',
  `rate` int DEFAULT '0' COMMENT '流量值',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `gw_route_rate_flow` */

/*Table structure for table `gw_route_rate_flow_key_words` */

DROP TABLE IF EXISTS `gw_route_rate_flow_key_words`;

CREATE TABLE `gw_route_rate_flow_key_words` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'gw_route记录开启流量标记的记录',
  `route_id` varchar(52) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联外键',
  `name` varchar(25) DEFAULT '' COMMENT '统计规则描述',
  `type` char(4) NOT NULL DEFAULT '1' COMMENT '统计整个接口项目流量 type=1 or 2 or 3 or 4',
  `target` varchar(52) DEFAULT '' COMMENT '统计当前接口项目下某个接口流量,如：MANAGER-API-901',
  `param` varchar(25) DEFAULT '' COMMENT '统计当前接口项目下指定接口的某个或某几个参数产生的流量,如：productType,productName',
  `value` varchar(52) DEFAULT '' COMMENT '统计当前接口项目下指定接口的某个参数对应的值所产生的流量,如：时间简史',
  `statistical_dimension` varchar(8) NOT NULL COMMENT '统计维度：month|day|hour|minute|second，不按周统计。',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `gw_route_rate_flow_key_words` */

insert  into `gw_route_rate_flow_key_words`(`id`,`route_id`,`name`,`type`,`target`,`param`,`value`,`statistical_dimension`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(5,'routeId-prod','项目访问量统计','1','','','','day','2022-08-18 14:03:49',2,'Yangcl','2022-08-18 14:03:49',2,'Yangcl',1),
(6,'routeId-prod','具体登录人信息统计','4','MANAGER-API-100','userName','admin','day','2022-08-18 14:03:49',2,'Yangcl','2022-08-18 14:03:49',2,'Yangcl',1),
(7,'routeId-pre','项目访问量统计','1','','','','day','2022-08-18 14:12:56',2,'Yangcl','2022-11-17 18:04:25',2,'Yangcl',1),
(8,'routeId-pre','具体登录人信息统计','4','MANAGER-API-100','userName','admin','day','2022-08-18 14:12:56',2,'Yangcl','2022-11-17 18:04:25',2,'Yangcl',1),
(9,'routeId-pre','项目访问量统计','1','','','','day','2022-11-16 18:23:30',2,'Yangcl','2022-11-17 18:04:25',2,'Yangcl',1),
(10,'routeId-pre','具体登录人信息统计','4','MANAGER-API-100','userName','admin','day','2022-11-16 18:23:30',2,'Yangcl','2022-11-17 18:04:25',2,'Yangcl',1),
(11,'routeId-pre','测试','2','MANAGER-API-101','','','month','2022-11-16 18:23:30',2,'Yangcl','2022-11-17 18:04:25',2,'Yangcl',1);

/*Table structure for table `gw_route_snapshot` */

DROP TABLE IF EXISTS `gw_route_snapshot`;

CREATE TABLE `gw_route_snapshot` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '流量快照表，考虑特殊性考虑后续单独拆分到其他库',
  `route_iden` bigint DEFAULT NULL COMMENT 'route_id对应的自增ID',
  `route_id` varchar(52) DEFAULT '' COMMENT '路由标识',
  `url` varchar(256) DEFAULT '' COMMENT 'url',
  `header` varchar(256) DEFAULT '' COMMENT '请求头信息',
  `cookies` varchar(52) DEFAULT '' COMMENT 'cookies',
  `request_type` char(8) DEFAULT '''post''' COMMENT '请求类型：get|post|put|delete',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `gw_route_snapshot` */

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

/*Table structure for table `mc_goods_purchase` */

DROP TABLE IF EXISTS `mc_goods_purchase`;

CREATE TABLE `mc_goods_purchase` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '物资采购记录|归属EHR系统',
  `serial_number` varchar(52) DEFAULT '' COMMENT '采购批次流水号',
  `produce_name` varchar(52) NOT NULL DEFAULT '' COMMENT '采购商品名称',
  `sku` varchar(18) DEFAULT '' COMMENT '采购商品的sku',
  `pic_url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '采购商品图片，多图以逗号分隔',
  `purchase_type` int DEFAULT '1' COMMENT '采购类型，1-大棚生产物资 2-员工福利商品 3-员工食堂物资 4-办公耗材(纸张/油墨/门锁/饮用水) 5-拜访客户礼品 6-敬老助学礼品',
  `purchase_channle` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '采购渠道：京东|淘宝|拼多多|阿里巴巴|实体商户',
  `shop_name` varchar(52) NOT NULL DEFAULT '' COMMENT '采购店铺名称',
  `unit_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品单价',
  `quantity` int NOT NULL DEFAULT '0' COMMENT '商品采购数量',
  `real_get_quantity` int DEFAULT '0' COMMENT '实际入库数量，由入库员更新',
  `date_of_minimum_durability` int DEFAULT '0' COMMENT '商品保质期，单位：天',
  `order_time` datetime DEFAULT NULL COMMENT '商品下单时间',
  `arrival_time` datetime DEFAULT NULL COMMENT '商品到达时间',
  `drawee_bank_name` varchar(20) DEFAULT '' COMMENT '付款人银行名称',
  `drawee_bank_card` varchar(20) DEFAULT '' COMMENT '付款人银行账户',
  `allocate_funds` decimal(10,2) DEFAULT '0.00' COMMENT '财务实际拨款金额',
  `payment_funds` decimal(10,2) DEFAULT NULL COMMENT '采购人员实际付款金额',
  `payment_funds_time` datetime DEFAULT NULL COMMENT '采购人员实际付款日期',
  `refund` decimal(10,2) DEFAULT NULL COMMENT '退款金额，如果出现商品采购数量大于入库数量则会产生退款',
  `refund_status` int DEFAULT '0' COMMENT '退款状态，由财务更新 0-未产生退款 1-退款未归还 2-退款已归还财务',
  `status` int DEFAULT NULL COMMENT '审批流转状态，0-申请中 1-通过 2-驳回 3-财务拨款中 4-拨款完成 5-采购中 6-采购完成 7-已到货 8-验货完成 9-已收货 10-已拒收 11-已入库 12-产生退款(计算后自动触发-通知付款人) 13-退款归还完成(财务触发) 14-流转完成(触发条件：驳回|已拒收|入库完成|退款完成)',
  `invoice_url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '发票图片，多图以逗号分隔。采购负责人进行更新。',
  `applicant_user_id` bigint DEFAULT NULL COMMENT '采购申请人id',
  `responsible_user_id` bigint DEFAULT NULL COMMENT '采购负责人id，如果采购商品质量出现问题则由此人负责',
  `surveyor_user_id` bigint DEFAULT NULL COMMENT '验货人id，如果采购商品质量出现问题则此人共同担责',
  `storeroom_user_id` bigint DEFAULT NULL COMMENT '入库人id',
  `remark` varchar(52) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '备注，实际入库数量与采购数量不一致需要描述清楚，货物破损是否重发了',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mc_goods_purchase` */

/*Table structure for table `mc_role` */

DROP TABLE IF EXISTS `mc_role`;

CREATE TABLE `mc_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(20) DEFAULT 'admin' COMMENT 'leader Leader后台创建的角色|admin 其他平台管理员创建的角色',
  `platform` varchar(20) DEFAULT 'P01' COMMENT '平台标识码',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(200) DEFAULT '' COMMENT '角色描述',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_role` */

insert  into `mc_role`(`id`,`type`,`platform`,`role_name`,`role_desc`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(116,'leader','133C9CB27E18','administer','administer','','2019-07-16 10:44:25',1992,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(164,'leader','133C9CB27E18','开发示例','仅供测试','','2019-09-29 17:23:32',2,'Yangcl','2021-04-02 18:49:12',2,'Yangcl',1),
(196,'leader','133C9CB27E18','公告接收测试','仅做公告接收测试','','2022-02-21 17:48:09',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(200,'leader','1348CC633E1C','Ehr全部角色','包涵全部功能点','','2022-11-27 15:45:05',2,'Yangcl','2022-12-21 11:00:42',2,'Yangcl',1),
(201,'leader','1348B1D284FA','matrix-jsp-demo正式角色','所有功能点','','2022-12-21 10:47:22',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(202,'admin','1348B1D284FA','jsp-demo-角色A','系统设置','','2022-12-22 15:38:58',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(203,'admin','1348B1D284FA','jsp-demo-角色B','导航栏','','2022-12-22 15:40:09',20072026234189,'multiple-1','2022-12-22 16:15:17',20072026234189,'multiple-1',1),
(205,'admin','1348CC633E1C','Ehr-all','Ehr全部功能点','','2022-12-22 15:43:19',20072026234189,'multiple-1','2022-12-22 16:39:27',20072026234189,'multiple-1',1);

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
) ENGINE=InnoDB AUTO_INCREMENT=20499 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

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
(20276,196,1126,NULL,'2022-03-02 11:27:23',2,'Yangcl','2022-03-02 11:27:23',2,'Yangcl',1),
(20280,116,75,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20281,116,77,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20282,116,250,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20283,116,251,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20284,116,252,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20285,116,79,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20286,116,83,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20287,116,167,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20288,116,308,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20289,116,126,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20290,116,171,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20291,116,192,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20292,116,187,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20293,116,208,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20294,116,127,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20295,116,309,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20296,116,172,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20297,116,310,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20298,116,188,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20299,116,193,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20300,116,258,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20301,116,257,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20302,116,209,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20303,116,210,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20304,116,211,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20305,116,212,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20306,116,213,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20307,116,214,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20308,116,215,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20309,116,259,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20310,116,260,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20311,116,80,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20312,116,84,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20313,116,261,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20314,116,128,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20315,116,227,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20316,116,249,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20317,116,325,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20318,116,326,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20319,116,78,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20320,116,81,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20321,116,117,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20322,116,197,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20323,116,312,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20324,116,313,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20325,116,314,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20326,116,315,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20327,116,473,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20328,116,316,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20329,116,1071,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20330,116,1073,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20331,116,1074,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20332,116,317,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20333,116,1075,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20334,116,118,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20335,116,198,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20336,116,318,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20337,116,177,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20338,116,319,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20339,116,320,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20340,116,321,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20341,116,322,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20342,116,1072,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20343,116,119,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20344,116,253,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20345,116,254,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20346,116,1076,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20347,116,1077,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20348,116,1078,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20349,116,1079,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20350,116,1080,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20351,116,255,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20352,116,1116,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20353,116,1118,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20354,116,1117,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20355,116,1115,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20356,116,1119,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20357,116,1120,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20358,116,1121,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20359,116,1122,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20360,116,1123,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20361,116,1124,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20362,116,1125,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20363,116,1126,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20364,116,287,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20365,116,288,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20366,116,289,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20367,116,290,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20368,116,291,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20369,116,292,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20370,116,293,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20371,116,294,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20372,116,295,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20373,116,296,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20374,116,297,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20375,116,298,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20376,116,299,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20377,116,300,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20378,116,323,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20379,116,1081,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20380,116,1082,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20381,116,301,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20382,116,302,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20383,116,303,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20384,116,304,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20385,116,305,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20386,116,306,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20387,116,465,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20388,116,466,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20389,116,1130,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20390,116,1131,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20391,116,1132,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20392,116,1133,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20393,116,1134,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20394,116,1135,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20395,116,1136,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20396,116,1137,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20397,116,1138,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20398,116,1139,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20399,116,510,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20400,116,479,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20401,116,480,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20402,116,481,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20403,116,482,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20404,116,483,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20405,116,484,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20406,116,485,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20407,116,486,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20408,116,487,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20409,116,488,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20410,116,489,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20411,116,490,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20412,116,491,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20413,116,492,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20414,116,1060,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20415,116,493,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20416,116,494,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20417,116,495,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20418,116,496,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20419,116,497,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20420,116,498,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20421,116,499,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20422,116,1084,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20423,116,1085,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20424,116,1086,NULL,'2022-08-09 14:28:05',2,'Yangcl','2022-08-09 14:28:05',2,'Yangcl',1),
(20425,200,1140,NULL,'2022-12-20 16:55:06',2,'Yangcl','2022-12-20 16:55:06',2,'Yangcl',1),
(20426,200,1143,NULL,'2022-12-20 16:55:06',2,'Yangcl','2022-12-20 16:55:06',2,'Yangcl',1),
(20427,200,1145,NULL,'2022-12-20 16:55:06',2,'Yangcl','2022-12-20 16:55:06',2,'Yangcl',1),
(20428,200,1144,NULL,'2022-12-20 16:55:06',2,'Yangcl','2022-12-20 16:55:06',2,'Yangcl',1),
(20429,201,1127,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20430,201,1128,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20431,201,1146,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20432,201,1147,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20433,201,1150,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20434,201,1151,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20435,201,1152,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20436,201,1153,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20437,201,1154,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20438,201,1155,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20439,201,1156,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20440,201,1157,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20441,201,1158,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20442,201,1159,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20443,201,1160,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20444,201,1161,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20445,201,1148,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20446,201,1162,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20447,201,1163,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20448,201,1164,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20449,201,1165,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20450,201,1166,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20451,201,1167,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20452,201,1168,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20453,201,1169,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20454,201,1149,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20455,201,1129,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20456,201,1170,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20457,201,1171,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20458,201,1172,NULL,'2022-12-21 10:59:39',2,'Yangcl','2022-12-21 10:59:39',2,'Yangcl',1),
(20464,202,1127,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20465,202,1128,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20466,202,1146,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20467,202,1147,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20468,202,1150,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20469,202,1151,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20470,202,1152,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20471,202,1153,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20472,202,1154,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20473,202,1155,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20474,202,1156,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20475,202,1157,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20476,202,1158,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20477,202,1159,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20478,202,1160,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20479,202,1161,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20480,202,1148,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20481,202,1162,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20482,202,1163,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20483,202,1164,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20484,202,1165,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20485,202,1166,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20486,202,1167,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20487,202,1168,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20488,202,1169,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20489,202,1149,NULL,'2022-12-22 16:14:55',20072026234189,'multiple-1','2022-12-22 16:14:55',20072026234189,'multiple-1',1),
(20490,203,1127,NULL,'2022-12-22 16:15:17',20072026234189,'multiple-1','2022-12-22 16:15:17',20072026234189,'multiple-1',1),
(20491,203,1129,NULL,'2022-12-22 16:15:17',20072026234189,'multiple-1','2022-12-22 16:15:17',20072026234189,'multiple-1',1),
(20492,203,1170,NULL,'2022-12-22 16:15:17',20072026234189,'multiple-1','2022-12-22 16:15:17',20072026234189,'multiple-1',1),
(20493,203,1171,NULL,'2022-12-22 16:15:17',20072026234189,'multiple-1','2022-12-22 16:15:17',20072026234189,'multiple-1',1),
(20494,203,1172,NULL,'2022-12-22 16:15:17',20072026234189,'multiple-1','2022-12-22 16:15:17',20072026234189,'multiple-1',1),
(20495,205,1140,NULL,'2022-12-22 16:39:27',20072026234189,'multiple-1','2022-12-22 16:39:27',20072026234189,'multiple-1',1),
(20496,205,1143,NULL,'2022-12-22 16:39:27',20072026234189,'multiple-1','2022-12-22 16:39:27',20072026234189,'multiple-1',1),
(20497,205,1145,NULL,'2022-12-22 16:39:27',20072026234189,'multiple-1','2022-12-22 16:39:27',20072026234189,'multiple-1',1),
(20498,205,1144,NULL,'2022-12-22 16:39:27',20072026234189,'multiple-1','2022-12-22 16:39:27',20072026234189,'multiple-1',1);

/*Table structure for table `mc_sys_function` */

DROP TABLE IF EXISTS `mc_sys_function`;

CREATE TABLE `mc_sys_function` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'zid',
  `name` varchar(40) NOT NULL COMMENT '功能名称 导航栏与菜单栏所显示的名称',
  `parent_id` varchar(25) NOT NULL COMMENT '父节点。root为0 其下为:商户->导航栏->一级菜单栏->二级菜单栏->页面按钮',
  `seqnum` int NOT NULL COMMENT '顺序码 同一层次显示顺序：导航栏 一级菜单栏 二级菜单栏 对应的显示顺序',
  `nav_type` int NOT NULL COMMENT '-1 根节点 0 平台标记 1 横向导航栏|2 为1级菜单栏|3 2级菜单栏 |4 页面按钮|5 按钮内包含跳转页面(dialog或新页面)',
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
) ENGINE=InnoDB AUTO_INCREMENT=1177 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_sys_function` */

insert  into `mc_sys_function`(`id`,`name`,`parent_id`,`seqnum`,`nav_type`,`platform`,`style_class`,`style_key`,`func_url`,`ajax_btn_url`,`remark`,`btn_area`,`ele_value`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(1,'root：系统功能树','-1',1,-1,'','root','root','rool','','系统根节点','',NULL,'2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(75,'Leader','1',1,0,'133C9CB27E18','','','','','矩阵分布式框架控制台','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(77,'开发示例','75',2,1,'133C9CB27E18','','','','','为系统开发这提供的示例','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(78,'矩阵系统配置','75',3,1,'133C9CB27E18','','','','','系统核心功能管理与控制','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-26 17:43:08',2,'Yangcl',1),
(79,'开发者快速入门','77',2,2,'133C9CB27E18','editor','37c694131c304c2588c4b906567631b1','','','开发者快速入门','','','2018-07-28 16:42:40',2,'Yangcl','2020-01-10 15:57:23',2,'Yangcl',1),
(80,'实例样本','77',3,2,'133C9CB27E18','editor','0cf9aa57b07149d586cc8998af6cfe7d','','','实例样本','','','2018-07-28 16:42:40',2,'Yangcl','2020-01-10 15:57:23',2,'Yangcl',1),
(81,'系统用户相关','78',1,2,'133C9CB27E18','editor','b06962367f8640fcbf11d4bca147101b','','','系统用户相关','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(83,'添加信息示例','79',1,3,'133C9CB27E18','','','example/page_example_add_info.do','','添加信息示例|matrix-admin/jsp/example/addExample.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2020-01-10 16:25:16',2,'Yangcl',1),
(84,'实际样本-A','80',1,3,'133C9CB27E18','','','example/page_example_a.do','','matrix-admin/src/main/webapp/jsp/example/reality/questionQuery.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(117,'系统用户列表','81',1,3,'133C9CB27E18','','','permissions/page_permissions_system_user_list.do','','leader/src/main/webapp/views/permission/user/system-user-list.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 14:51:18',2,'Yangcl',1),
(118,'系统角色列表','81',2,3,'133C9CB27E18','','','permissions/page_permissions_system_role_list.do','','leader/src/main/webapp/views/permission/role/system-role-list.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 14:50:20',2,'Yangcl',1),
(119,'系统功能列表','81',3,3,'133C9CB27E18','','','permissions/page_permissions_system_function.do','','leader/src/main/webapp/views/permission/func/system-func-tree.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 14:50:57',2,'Yangcl',1),
(126,'Ajax 分页示例','79',2,3,'133C9CB27E18','','','example/page_example_ajax_form.do','','Ajax 分页示例|matrix-admin/jsp/example/ajaxFormExample.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(127,'Ajax 分页+弹出窗体分页 示例','79',3,3,'133C9CB27E18','','','example/page_example_ajax_form_dialog.do','','Ajax 分页+弹出窗体分页 示例|\r\nmatrix-admin/jsp/example/ajaxFormDialogExample.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(128,'实际样本-B','80',2,3,'133C9CB27E18','','','example/page_example_b.do','','matrix-admin/src/main/webapp/jsp/example/reality/validate.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(167,'添加','83',1,4,'133C9CB27E18','','','','ajax_btn_add_info','123123','10001','add_info_example:add','2018-07-28 16:42:40',2,'Yangcl','2020-01-10 16:11:19',2,'Yangcl',1),
(171,'查询','126',1,4,'133C9CB27E18','','','','','查询','10002','ajax_page_example:search','2018-07-28 16:42:40',2,'Yangcl','2019-12-05 15:39:59',2,'Yangcl',1),
(172,'查询','127',2,4,'133C9CB27E18','','','','','添加','10002','ajax_page_dialog_example:search','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(177,'添加','118',3,4,'133C9CB27E18','','','','ajax_btn_add_mc_role_only.do','sysrole/ajax_btn_add_mc_role_only.do','10001','system_role_list:add','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 14:00:38',2,'Yangcl',1),
(187,'删除','126',3,4,'133C9CB27E18','','','','','删除','10003','ajax_page_example:delete','2018-07-28 16:42:40',2,'Yangcl','2019-11-08 15:58:28',2,'Yangcl',1),
(188,'修改','127',4,5,'133C9CB27E18','','','asdfasdf','','修改','10003','ajax_page_dialog_example:edit','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(192,'重置','126',2,4,'133C9CB27E18','','','','','重置','10002','ajax_page_example:reset','2018-07-28 16:42:40',2,'Yangcl','2019-11-08 15:58:28',2,'Yangcl',1),
(193,'重置','127',5,4,'133C9CB27E18','','','','','查看','10002','ajax_page_dialog_example:reset','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(197,'查询','117',1,4,'133C9CB27E18','','','','ajax_system_user_list.do','userInfo/ajax_system_user_list.do','10001','system_user_list:search','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(198,'查询','118',1,4,'133C9CB27E18','','','','ajax_system_role_list.do','sysrole/ajax_system_role_list.do','10001','system_role_list:search','2018-07-28 16:42:40',2,'Yangcl','2021-07-23 16:57:21',2,'Yangcl',1),
(208,'修改','126',4,4,'133C9CB27E18','','','','','修改','10003','ajax_page_example:edit','2018-07-28 16:42:40',2,'Yangcl','2019-11-08 15:58:28',2,'Yangcl',1),
(209,'自定义 alert confirm note 示例','79',4,3,'133C9CB27E18','','','example/page_example_alert.do','','自定义 alert confirm note 示例|matrix-admin/jsp/example/alertExample.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(210,'基本 Alert','209',1,4,'133C9CB27E18','','','','','基本 Alert','10001','custom_dialog_example:alert','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(211,'确认对话框 confirm','209',2,4,'133C9CB27E18','','','','','确认对话框 confirm','10001','custom_dialog_example:confirm','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(212,'输入对话框 prompt','209',3,4,'133C9CB27E18','','','','','输入对话框 prompt','10001','custom_dialog_example:prompt','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(213,'alert 支持html标签','209',4,4,'133C9CB27E18','','','','','alert 支持html标签','10001','custom_dialog_example:html','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(214,'弹出层示例','79',5,3,'133C9CB27E18','','','example/page_example_block_ui.do','','介绍系统常见的弹出层|matrix-admin/jsp/example/blockUiPageExample.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(215,'添加弹层示例','214',1,4,'133C9CB27E18','','','','','开头语','10001','dialog_example:add','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(227,'ueditor编辑器示例','80',4,3,'133C9CB27E18','','','example/page_example_ueditor.do','','matrix-admin/src/main/webapp/jsp/example/ueditorExample.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(249,'图片|文件上传','80',5,3,'133C9CB27E18','','','example/page_example_file_upload.do','','matrix-admin/src/main/webapp/jsp/example/pageExampleFileUpload.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(250,'项目介绍','77',1,2,'133C9CB27E18','editor','304180247f1044f6a9a56a78a407fdc3','','','项目介绍','','','2018-07-28 16:42:40',2,'Yangcl','2020-01-10 15:57:23',2,'Yangcl',1),
(251,'开发者规约-java','250',1,3,'133C9CB27E18','','','example/page_developer_specification.do','','开发者规约','','','2018-07-28 16:42:40',2,'Yangcl','2022-11-18 17:25:39',2,'Yangcl',1),
(252,'开发者规约-javascript','250',2,3,'133C9CB27E18','','','example/page_developer_specification_js.do','','开发者规约-javascript','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 11:33:25',2,'Yangcl',1),
(253,'系统工具','78',2,2,'133C9CB27E18','editor','a6a534ca38384f8787a8306ea0f23651','','','系统配置与查询、缓存操作/查看、部署节点列表等','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-23 10:50:07',2,'Yangcl',1),
(254,'缓存查看','253',1,3,'133C9CB27E18','','','cache/page_cache_system_cache.do','','查看系统中的缓存信息|views\\system\\cache\\system-cache.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-23 14:46:35',2,'Yangcl',1),
(255,'发送系统公告','253',2,3,'133C9CB27E18','','','websocket/page_websocket_affiche_admin.do','','发送系统公告的页面：views/websocket/affiche/admin.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2022-02-21 17:41:24',2,'Yangcl',1),
(257,'删除','127',7,4,'133C9CB27E18','','','','','删除','10003','ajax_page_dialog_example:delete','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(258,'弹窗分页','127',6,4,'133C9CB27E18','','','','','弹窗分页','10003','ajax_page_dialog_example:dialog','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(259,'自定义滚动条示例-ul-列表','214',2,4,'133C9CB27E18','','','','','自定义滚动条示例-ul-列表','10001','dialog_example:slim_scroll','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(260,'自定义滚动条示例-tree','214',3,4,'133C9CB27E18','','','','','自定义滚动条示例-tree','10001','dialog_example:slim_scroll:tree','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(261,'搜索日志信息','84',1,4,'133C9CB27E18','','','','','搜索日志信息','10001','btn-55ee0a123a05484d8cc22235b709c2ff','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(287,'系统开放接口','78',3,2,'133C9CB27E18','editor','d27673e123c9447f8c789ab260b3adb2','','','包含公司内部接口、开放给第三方的接口等。由这里进行统一管理','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(288,'api所属项目','287',1,3,'133C9CB27E18','','','apicenter/page_apicenter_api_project_list.do','','api所属项目-应对可能出现的多项目|ac_api_project表的数据|\r\nviews\\api\\project\\api-project-list.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-26 17:28:04',2,'Yangcl',1),
(289,'查 询','288',1,4,'133C9CB27E18','','','','ajax_api_project_list.do','apicenter/ajax_api_project_list.do\r\n查 询','10002','api_project_list:search','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 13:50:12',2,'Yangcl',1),
(290,'重 置','288',2,4,'133C9CB27E18','','','','ajax_api_project_list.do','apicenter/ajax_api_project_list.do\r\n重 置','10002','api_project_list:reset','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 13:50:41',2,'Yangcl',1),
(291,'添加','288',3,4,'133C9CB27E18','','','','ajax_btn_api_project_add.do','添加 apicenter/ajax_btn_api_project_add.do','10001','api_project_list:add','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 13:51:37',2,'Yangcl',1),
(292,'删除','288',4,4,'133C9CB27E18','','','','ajax_btn_api_project_delete.do','删除 apicenter/ajax_btn_api_project_delete.do','10003','api_project_list:delete','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 13:53:05',2,'Yangcl',1),
(293,'修改','288',5,4,'133C9CB27E18','','','','ajax_btn_api_project_edit.do','修改 apicenter/ajax_btn_api_project_edit.do','10003','api_project_list:edit','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 13:52:09',2,'Yangcl',1),
(294,'跨域白名单','287',2,3,'133C9CB27E18','','','apicenter/page_apicenter_include_domain_list.do','','记录系统允许跨域的域名|\r\nviews\\api\\domain\\api-include-domain-list.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 17:30:29',2,'Yangcl',1),
(295,'查询','294',1,4,'133C9CB27E18','','','','ajax_include_domain_page_list.do','查询','10002','include_domain_list:search','2018-07-28 16:42:40',2,'Yangcl','2021-03-15 16:13:19',2,'Yangcl',1),
(296,'重置','294',2,4,'133C9CB27E18','','','','ajax_include_domain_page_list.do','重置','10002','include_domain_list:reset','2018-07-28 16:42:40',2,'Yangcl','2021-03-15 16:13:31',2,'Yangcl',1),
(297,'添加','294',3,4,'133C9CB27E18','','','','ajax_btn_api_domain_add.do','添加 apicenter/ajax_btn_api_domain_add.do','10001','include_domain_list:add','2018-07-28 16:42:40',2,'Yangcl','2019-12-27 17:31:21',2,'Yangcl',1),
(298,'删除','294',4,4,'133C9CB27E18','','','','ajax_btn_api_domain_delete.do','删除 apicenter/ajax_btn_api_domain_delete.do','10001','include_domain_list:delete','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 11:21:39',2,'Yangcl',1),
(299,'修改','294',5,4,'133C9CB27E18','','','','ajax_btn_api_domain_edit.do','修改 apicenter/ajax_btn_api_domain_edit.do','10001','include_domain_list:edit','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 09:55:22',2,'Yangcl',1),
(300,'api信息树','287',3,3,'133C9CB27E18','','','apicenter/page_apicenter_api_tree.do','','api信息树|views\\api\\info\\api-tree.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 16:32:06',2,'Yangcl',1),
(301,'请求者信息','287',4,3,'133C9CB27E18','','','apicenter/page_apicenter_request_info.do','','请求者信息维护页面|\r\nmatrix-admin/src/main/webapp/jsp/api/request/api-request-info-list.jsp','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(302,'查 询','301',1,4,'133C9CB27E18','','','','ajax_request_info_list.do','查 询 apicenter/ajax_request_info_list.do','10002','api_requester_info:search','2018-07-28 16:42:40',2,'Yangcl','2021-03-21 18:00:01',2,'Yangcl',1),
(303,'重 置','301',2,4,'133C9CB27E18','','','','ajax_request_info_list.do','重 置 apicenter/ajax_request_info_list.do','10002','api_requester_info:reset','2018-07-28 16:42:40',2,'Yangcl','2021-03-21 18:00:06',2,'Yangcl',1),
(304,'添加','301',3,4,'133C9CB27E18','','','','','添加','10001','api_requester_info:add','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 16:38:40',2,'Yangcl',1),
(305,'启用|禁用','301',4,4,'133C9CB27E18','','','','','删除','10003','api_requester_info:delete','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 16:38:40',2,'Yangcl',1),
(306,'修改','301',5,4,'133C9CB27E18','','','','','修改','10003','api_requester_info:edit','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 16:38:40',2,'Yangcl',1),
(308,'删除','83',2,4,'133C9CB27E18','','','','ajax_btn_delete_info','删除按钮','10001','add_info_example:delete','2018-07-28 16:42:40',2,'Yangcl','2019-12-10 15:38:52',2,'Yangcl',1),
(309,'弹窗-删除','127',1,4,'133C9CB27E18','','','','','弹窗中的删除按钮','10001','ajax_page_dialog_example:dialog:delete','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(310,'弹窗-编辑','127',3,4,'133C9CB27E18','','','','','弹窗中的编辑按钮','10001','ajax_page_dialog_example:dialog:edit','2018-07-28 16:42:40',2,'Yangcl','2021-12-31 11:09:58',2,'Yangcl',1),
(312,'重置','117',2,4,'133C9CB27E18','','','','ajax_system_user_list.do','userInfo/ajax_system_user_list.do','10002','system_user_list:reset','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(313,'添加','117',3,4,'133C9CB27E18','','','','ajax_btn_add_system_user.do','userInfo/ajax_btn_add_system_user.do','10002','system_user_list:add','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(314,'删除','117',4,4,'133C9CB27E18','','','','ajax_btn_delete_system_user.do','userInfo/ajax_btn_delete_system_user.do','10003','system_user_list:delete','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(315,'修改','117',5,4,'133C9CB27E18','','','','ajax_btn_edit_sys_user.do','userInfo/ajax_btn_edit_sys_user.do','10003','system_user_list:edit','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(316,'用户角色','117',7,5,'133C9CB27E18','','','dialog_permissions_system_role_list.do','','permissions/dialog_permissions_system_role_list.do','10001','system_user_list:user_role','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(317,'系统角色列表-弹框-分配','117',11,4,'133C9CB27E18','','','','ajax_btn_allot_user_role_submit.do','sysrole/ajax_btn_allot_user_role_submit.do\r\n给指定用户分配一个角色','10003','system_user_list:allot_submit','2018-07-28 16:42:40',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(318,'重置','118',2,4,'133C9CB27E18','','','','ajax_system_role_list.do','sysrole/ajax_system_role_list.do','10001','system_role_list:reset','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 13:59:55',2,'Yangcl',1),
(319,'角色功能','118',4,4,'133C9CB27E18','','','','default','default：仅做权限控制，不发起ajax请求','10003','system_role_list:role_function','2018-07-28 16:42:40',2,'Yangcl','2022-12-21 10:44:13',2,'Yangcl',1),
(320,'修改','118',5,4,'133C9CB27E18','','','','ajax_btn_edit_mc_role_only.do','sysrole/ajax_btn_edit_mc_role_only.do','10003','system_role_list:edit','2018-07-28 16:42:40',2,'Yangcl','2022-12-21 10:44:05',2,'Yangcl',1),
(321,'删除','118',6,4,'133C9CB27E18','','','','ajax_btn_delete_mc_role.do','sysrole/ajax_btn_delete_mc_role.do','10003','system_role_list:delete','2018-07-28 16:42:40',2,'Yangcl','2022-12-21 10:44:43',2,'Yangcl',1),
(322,'角色功能弹窗-提交','118',7,4,'133C9CB27E18','','','','ajax_btn_edit_mc_role.do','sysrole/ajax_btn_edit_mc_role.do','10001','system_role_list:dialog_submit','2018-07-28 16:42:40',2,'Yangcl','2019-12-11 14:13:08',2,'Yangcl',1),
(323,'保存|修改','300',1,4,'133C9CB27E18','','','','default','保存|修改。页面展示为【保存】按钮，但针对添加行为对应apicenter/ajax_api_info_add.do，针对修改行为对应apicenter/ajax_api_info_edit.do。由于同一个按钮对应两种Controller请求路径，所以【按钮请求路径】初设置为default','10001','api_tree:submit','2018-07-28 16:42:40',2,'Yangcl','2020-01-07 16:53:39',2,'Yangcl',1),
(325,'完整开发流程','77',4,2,'133C9CB27E18','editor','51e91e7ba11d4a61918d100dd124d9f0','','','贴近实际业务来展示一个完整的开发流程','','','2018-07-28 16:42:40',2,'Yangcl','2020-01-10 15:57:23',2,'Yangcl',1),
(326,'楼盘信息开发实例','325',1,3,'133C9CB27E18','','','demos/page_demo_landed_property.do','','复制即所得，抄永远比写来的快。但请小心别抄错了。。','','','2018-07-28 16:42:40',2,'Yangcl','2018-07-28 16:42:44',2,'Yangcl',1),
(465,'Spring-Cloud-Gateway','78',4,2,'133C9CB27E18','editor','17f2b7b4b60f455e8fdcbb3a6e37fe4b','','','网关功能配置','','','2018-11-04 13:02:19',2,'Yangcl','2022-08-05 16:32:58',2,'Yangcl',1),
(466,'路由规则列表','465',1,3,'133C9CB27E18','','','gateway/page_gateway_route_list.do','','路由规则列表','','','2018-11-04 13:06:32',2,'Yangcl','2022-08-08 18:25:39',2,'Yangcl',1),
(473,'重置密码','117',6,4,'133C9CB27E18','','','','ajax_btn_password_reset.do','userInfo/ajax_btn_password_reset.do','10003','system_user_list:password_reset','2018-11-14 16:28:31',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(479,'分布式定时任务','78',5,2,'133C9CB27E18','editor','4930eab924ee4e10ad016882bb7772a3','','','分布式定时任务','','','2018-12-20 17:42:07',2,'Yangcl','2018-12-20 17:42:07',2,'Yangcl',1),
(480,'定时任务列表','479',1,3,'133C9CB27E18','','','quartz/page_quartz_job_info_list.do','','定时任务列表','','','2018-12-20 17:43:15',2,'Yangcl','2018-12-20 17:43:15',2,'Yangcl',1),
(481,'添加','480',1,4,'133C9CB27E18','','','','ajax_btn_job_info_add.do','quartz/ajax_btn_job_info_add.do','10001','job_info_list:add','2018-12-20 17:59:41',2,'Yangcl','2021-04-19 17:41:18',2,'Yangcl',1),
(482,'重 置','480',2,4,'133C9CB27E18','','','','','job_info_list:reset','10001','job_info_list:reset','2018-12-20 18:00:39',2,'Yangcl','2018-12-20 18:00:39',2,'Yangcl',1),
(483,'查 询','480',3,4,'133C9CB27E18','','','','','job_info_list:search','10001','job_info_list:search','2018-12-20 18:01:34',2,'Yangcl','2018-12-20 18:01:34',2,'Yangcl',1),
(484,'删除','480',4,4,'133C9CB27E18','','','','ajax_btn_job_info_delete.do','quartz/ajax_btn_job_info_delete.do','10003','job_info_list:delete','2018-12-20 20:48:17',2,'Yangcl','2021-04-20 11:22:18',2,'Yangcl',1),
(485,'修改','480',5,4,'133C9CB27E18','','','','ajax_btn_job_info_edit.do','quartz/ajax_btn_job_info_edit.do','10003','job_info_list:edit','2018-12-20 20:48:46',2,'Yangcl','2021-04-19 18:02:29',2,'Yangcl',1),
(486,'手动触发','480',6,4,'133C9CB27E18','','','','','job_info_list:run','10003','job_info_list:run','2018-12-20 20:49:11',2,'Yangcl','2018-12-20 21:00:13',2,'Yangcl',1),
(487,'执行记录','480',7,4,'133C9CB27E18','','','','','job_info_list:run_log','10003','job_info_list:run_log','2018-12-20 20:59:48',2,'Yangcl','2018-12-20 20:59:48',2,'Yangcl',1),
(488,'暂停','480',8,4,'133C9CB27E18','','','','','暂停一个定时任务','10003','job_info_list:pause','2018-12-21 14:22:49',2,'Yangcl','2018-12-21 14:22:49',2,'Yangcl',1),
(489,'全部暂停','480',9,4,'133C9CB27E18','','','','','暂停全部定时任务','10002','job_info_list:pause_all','2018-12-21 14:24:33',2,'Yangcl','2018-12-21 14:24:33',2,'Yangcl',1),
(490,'恢复','480',10,4,'133C9CB27E18','','','','','恢复一个指定的定时任务到运行状态','10003','job_info_list:resume','2018-12-21 14:25:52',2,'Yangcl','2018-12-21 14:25:52',2,'Yangcl',1),
(491,'全部恢复','480',11,4,'133C9CB27E18','','','','','将全部定时任务恢复到运行状态','10002','job_info_list:resume_all','2018-12-21 14:26:59',2,'Yangcl','2018-12-21 14:26:59',2,'Yangcl',1),
(492,'详情','480',12,4,'133C9CB27E18','','','','','定时任务详情','10003','job_info_list:detail','2018-12-25 14:21:25',2,'Yangcl','2018-12-25 14:21:25',2,'Yangcl',1),
(493,'定时任务分组列表','479',2,3,'133C9CB27E18','','','quartz/page_quartz_job_group_list.do','','定时任务分组列表：views/quartz/group/job-group-list','','','2018-12-27 10:57:42',2,'Yangcl','2021-04-28 17:05:03',2,'Yangcl',1),
(494,'添加','493',1,4,'133C9CB27E18','','','','ajax_btn_job_group_add.do','quartz/ajax_btn_job_group_add.do','10001','job_group_list:add','2018-12-27 10:59:42',2,'Yangcl','2021-04-28 17:42:04',2,'Yangcl',1),
(495,'重 置','493',2,4,'133C9CB27E18','','','','','job_group_list:reset','10001','job_group_list:reset','2018-12-27 11:00:05',2,'Yangcl','2018-12-27 11:00:05',2,'Yangcl',1),
(496,'查 询','493',3,4,'133C9CB27E18','','','','','job_group_list:search','10001','job_group_list:search','2018-12-27 11:00:27',2,'Yangcl','2018-12-27 11:00:27',2,'Yangcl',1),
(497,'删除','493',4,4,'133C9CB27E18','','','','ajax_btn_job_group_delete.do','quartz/ajax_btn_job_group_delete.do','10003','job_group_list:delete','2018-12-27 11:00:49',2,'Yangcl','2021-05-06 15:13:48',2,'Yangcl',1),
(498,'修改','493',5,4,'133C9CB27E18','','','','ajax_btn_job_group_edit.do','quartz/ajax_btn_job_group_edit.do','10003','job_group_list:edit','2018-12-27 11:01:14',2,'Yangcl','2021-05-06 15:13:19',2,'Yangcl',1),
(499,'定时任务日志列表','479',3,3,'133C9CB27E18','','','quartz/page_quartz_job_log_list.do','','定时任务日志列表','','','2018-12-29 17:00:37',2,'Yangcl','2018-12-29 17:00:37',2,'Yangcl',1),
(510,'部署节点列表','465',2,3,'133C9CB27E18','','','route/page_route_dubbo_node_list.do','','应用服务部署节点列表','','','2019-01-07 14:15:06',2,'Yangcl','2022-08-05 16:39:16',2,'Yangcl',1),
(1060,'手动','480',13,4,'133C9CB27E18','','','','','手动触发定时任务|立刻执行定时任务','10003','job_info_list:handling','2019-09-27 18:12:50',2,'Yangcl','2019-09-27 18:12:50',2,'Yangcl',1),
(1071,'权限重置','117',8,4,'133C9CB27E18','','','','ajax_btn_user_cache_reload.do','sysrole/ajax_btn_user_cache_reload.do\r\n重置系统用户的所有信息包括：McSysFunc、McRole、McUserRole、UserInfoNp','10001','system_user_list:reload','2019-12-10 15:59:17',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(1072,'角色功能弹窗-解绑','118',8,4,'133C9CB27E18','','','','ajax_btn_relieve_mc_role.do','sysrole/ajax_btn_relieve_mc_role.do','10001','system_role_list:dialog_cancel','2019-12-11 14:12:31',2,'Yangcl','2019-12-11 14:55:07',2,'Yangcl',1),
(1073,'系统角色列表-弹框-查询','117',9,4,'133C9CB27E18','','','','ajax_user_role_list.do','sysrole/ajax_user_role_list.do','10002','system_user_list:dialog_search','2019-12-13 17:08:35',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(1074,'系统角色列表-弹框-重置','117',10,4,'133C9CB27E18','','','','ajax_user_role_list.do','sysrole/ajax_user_role_list.do','10002','system_user_list:dialog_reset','2019-12-13 17:20:38',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),
(1075,'系统角色列表-弹框-移除','117',12,4,'133C9CB27E18','','','','ajax_btn_allot_user_role_remove.do','sysrole/ajax_btn_allot_user_role_remove.do\r\n解除角色绑定，同时删除缓存','10003','system_user_list:allot_remove','2019-12-17 17:50:48',2,'Yangcl','2021-07-23 16:57:14',2,'Yangcl',1),
(1076,'批量删除缓存','254',1,4,'133C9CB27E18','','','','ajax_btn_batch_delete.do','cache/ajax_btn_batch_delete.do\r\n批量删除缓存','10001','system_cache:batch_delete','2019-12-23 11:15:27',2,'Yangcl','2019-12-23 11:45:02',2,'Yangcl',1),
(1077,'设置缓存','254',2,4,'133C9CB27E18','','','','ajax_btn_reset_cache.do','cache/ajax_btn_reset_cache.do\r\n过期时间半小时','10001','system_cache:reset','2019-12-23 11:24:29',2,'Yangcl','2019-12-23 11:24:29',2,'Yangcl',1),
(1078,'设置缓存(永久)','254',3,4,'133C9CB27E18','','','','ajax_btn_reset_cache_forever.do','cache/ajax_btn_reset_cache_forever.do\r\n永久设置缓存','10001','system_cache:reset_forever','2019-12-23 11:26:13',2,'Yangcl','2019-12-23 11:26:13',2,'Yangcl',1),
(1079,'删除缓存','254',4,4,'133C9CB27E18','','','','ajax_btn_delete_cache.do','cache/ajax_btn_delete_cache.do\r\n批量删除缓存','10001','system_cache:delete_cache','2019-12-23 11:38:05',2,'Yangcl','2019-12-23 11:38:05',2,'Yangcl',1),
(1080,'获取缓存','254',5,4,'133C9CB27E18','','','','ajax_btn_get_cache.do','cache/ajax_btn_get_cache.do','10001','system_cache:get_cache','2019-12-23 11:40:22',2,'Yangcl','2019-12-23 11:40:22',2,'Yangcl',1),
(1081,'测试','300',1,4,'133C9CB27E18','','','','defalut','API树形结构列表|测试按钮。点击按钮将打开一个弹框，用于测试接口数据','10001','api_tree:test','2020-01-07 16:55:42',2,'Yangcl','2020-01-07 16:55:42',2,'Yangcl',1),
(1082,'删除','300',2,4,'133C9CB27E18','','','','ajax_btn_api_remove.do','删除一个api节点 apicenter/ajax_btn_api_remove.do','10001','api_tree:remove','2020-01-07 18:12:43',2,'Yangcl','2020-01-07 18:12:43',2,'Yangcl',1),
(1084,'查 询','499',1,4,'133C9CB27E18','','','','ajax_job_log_list.do','quartz/ajax_job_log_list','10001','job_log_list:search','2021-05-06 18:07:53',2,'Yangcl','2021-05-06 18:07:53',2,'Yangcl',1),
(1085,'重 置','499',2,4,'133C9CB27E18','','','','ajax_job_log_list.do','quartz/ajax_job_log_list','10001','job_log_list:reset','2021-05-06 18:09:09',2,'Yangcl','2021-05-06 18:09:09',2,'Yangcl',1),
(1086,'详情','499',3,4,'133C9CB27E18','','','','ajax_job_log_detail.do','quartz/ajax_job_log_detail.do','10001','job_log_list:detail','2021-05-06 18:10:44',2,'Yangcl','2021-05-06 18:10:44',2,'Yangcl',1),
(1115,'接受系统公告','253',3,3,'133C9CB27E18','','','websocket/page_websocket_affiche_user.do','','接受系统公告的测试页面。','','','2022-02-21 17:46:20',2,'Yangcl','2022-02-21 17:46:20',2,'Yangcl',1),
(1116,'建立连接','255',1,4,'133C9CB27E18','','','','ajax_btn_affiche_connect.do','建立ws链接','10001','affiche:connect','2022-02-21 19:43:58',2,'Yangcl','2022-03-01 11:09:04',2,'Yangcl',1),
(1117,'发送通告','255',3,4,'133C9CB27E18','','','','ajax_btn_affiche_send.do','发送通告信息','10001','affiche:send','2022-02-21 19:44:38',2,'Yangcl','2022-03-01 11:09:04',2,'Yangcl',1),
(1118,'断开连接','255',2,4,'133C9CB27E18','','','','ajax_btn_affiche_disconnect.do','断开长链接','10001','affiche:disconnect','2022-02-21 19:52:59',2,'Yangcl','2022-03-01 11:09:04',2,'Yangcl',1),
(1119,'一对一聊天','253',4,3,'133C9CB27E18','','','websocket/page_websocket_p2p.do','','系统一对一聊天功能','','','2022-03-01 11:05:49',2,'Yangcl','2022-03-01 11:05:49',2,'Yangcl',1),
(1120,'建立连接','1119',1,4,'133C9CB27E18','','','','ajax_btn_p2p_connect.do','建立连接','10001','p2p:connect','2022-03-01 11:08:58',2,'Yangcl','2022-03-01 11:09:58',2,'Yangcl',1),
(1121,'断开连接','1119',2,4,'133C9CB27E18','','','','ajax_btn_p2p_disconnect.do','断开连接','10001','p2p:disconnect','2022-03-01 11:09:53',2,'Yangcl','2022-03-01 11:09:53',2,'Yangcl',1),
(1122,'发送消息','1119',3,4,'133C9CB27E18','','','','ajax_btn_p2p_send.do','发送消息','10001','p2p:send','2022-03-01 11:10:48',2,'Yangcl','2022-03-01 11:10:48',2,'Yangcl',1),
(1123,'多人聊天','253',5,3,'133C9CB27E18','','','websocket/page_websocket_group.do','','组群|多人聊天|消息群发','','','2022-03-02 11:22:30',2,'Yangcl','2022-03-02 11:22:30',2,'Yangcl',1),
(1124,'建立连接','1123',1,4,'133C9CB27E18','','','','ajax_btn_group_connect.do','建立连接','10001','group:connect','2022-03-02 11:25:16',2,'Yangcl','2022-03-02 11:25:16',2,'Yangcl',1),
(1125,'断开连接','1123',2,4,'133C9CB27E18','','','','ajax_btn_group_disconnect.do','断开连接','10001','group:disconnect','2022-03-02 11:25:58',2,'Yangcl','2022-03-02 11:25:58',2,'Yangcl',1),
(1126,'发送消息','1123',3,4,'133C9CB27E18','','','','ajax_btn_group_send.do','发送消息','10001','group:send','2022-03-02 11:26:40',2,'Yangcl','2022-03-02 11:26:40',2,'Yangcl',1),
(1127,'matrix-jsp-demo','1',2,0,'1348B1D284FA','','','','','matrix-jsp-demo项目权限','','','2022-07-01 16:51:14',2,'Yangcl','2022-11-18 15:49:01',2,'Yangcl',1),
(1128,'系统设置','1127',1,1,'1348B1D284FA','','','','','系统设置','','','2022-07-01 16:54:39',2,'Yangcl','2022-12-21 10:29:19',2,'Yangcl',1),
(1129,'导航栏','1127',2,1,'1348B1D284FA','','','','','demo','','','2022-07-01 16:55:42',2,'Yangcl','2022-12-21 10:51:21',2,'Yangcl',1),
(1130,'查 询','466',1,4,'133C9CB27E18','','','','忽略查询','真实接口：ajax_gateway_route_list.do','10001','gateway_route_list:search','2022-08-09 11:14:31',2,'Yangcl','2022-08-09 11:25:21',2,'Yangcl',1),
(1131,'重 置','466',2,4,'133C9CB27E18','','','','忽略查询','真实接口：ajax_gateway_route_list.do','10001','gateway_route_list:reset','2022-08-09 11:15:37',2,'Yangcl','2022-08-09 11:25:15',2,'Yangcl',1),
(1132,'添加','466',3,4,'133C9CB27E18','','','','ajax_btn_gateway_route_add.do','gateway/ajax_btn_gateway_route_add.do','10001','gateway_route_list:add','2022-08-09 11:17:02',2,'Yangcl','2022-08-09 15:49:18',2,'Yangcl',1),
(1134,'修改','466',5,4,'133C9CB27E18','','','','ajax_btn_gateway_route_edit.do','gateway/ajax_btn_gateway_route_edit.do','10003','gateway_route_list:edit','2022-08-09 11:23:52',2,'Yangcl','2022-08-09 11:23:52',2,'Yangcl',1),
(1135,'生效','466',6,4,'133C9CB27E18','','','','ajax_btn_gateway_route_enable.do','此条规则是否生效：0-不生效 1-生效','10003','gateway_route_list:enable','2022-08-09 11:31:05',2,'Yangcl','2022-11-17 17:07:42',2,'Yangcl',1),
(1136,'暂停','466',7,4,'133C9CB27E18','','','','ajax_btn_gateway_route_pause.do','此条规则是否生效：0-不生效 1-生效','10003','gateway_route_list:pause','2022-08-09 11:34:29',2,'Yangcl','2022-08-09 11:34:29',2,'Yangcl',1),
(1137,'删除','466',8,4,'133C9CB27E18','','','','ajax_btn_gateway_route_delete.do','软删除','10003','gateway_route_list:delete','2022-08-09 11:35:43',2,'Yangcl','2022-08-09 11:35:43',2,'Yangcl',1),
(1140,'ehr','1',3,0,'1348CC633E1C','','','','','雇员管理系统','','','2022-11-26 21:25:08',2,'Yangcl','2022-11-26 21:31:45',2,'Yangcl',1),
(1141,'operation','1',4,0,'1348CC633E7A','','','','','电商运营系统','','','2022-11-26 21:26:02',2,'Yangcl','2022-11-26 21:32:01',2,'Yangcl',1),
(1142,'producer','1',5,0,'1348CC633FD9','','','','','生产管理系统','','','2022-11-26 21:29:53',2,'Yangcl','2022-11-26 21:32:10',2,'Yangcl',1),
(1143,'导航-1','1140',1,1,'1348CC633E1C','','','','','导航-1','','','2022-12-20 16:54:34',2,'Yangcl','2022-12-20 16:54:34',2,'Yangcl',1),
(1144,'导航-2','1140',2,1,'1348CC633E1C','','','','','导航-2','','','2022-12-20 16:54:43',2,'Yangcl','2022-12-20 16:54:43',2,'Yangcl',1),
(1145,'导航-1-1','1143',1,2,'1348CC633E1C','editor','','','','导航-1-1','','','2022-12-20 16:54:53',2,'Yangcl','2022-12-20 16:54:53',2,'Yangcl',1),
(1146,'系统用户与权限','1128',1,2,'1348B1D284FA','editor','','','','系统用户与权限设置','','','2022-12-21 10:30:07',2,'Yangcl','2022-12-21 10:30:07',2,'Yangcl',1),
(1147,'系统用户列表','1146',1,3,'1348B1D284FA','','','permissions/page_permissions_system_user_list.do','','展示系统用户列表，类型为：user，路径：webapp/views/permission/user/system-user-list.jsp','','','2022-12-21 10:31:55',2,'Yangcl','2022-12-21 10:34:02',2,'Yangcl',1),
(1148,'系统角色列表','1146',2,3,'1348B1D284FA','','','permissions/page_permissions_system_role_list.do','','展示系统角色列表，路径：webapp/views/permission/role/system-role-list.jsp','','','2022-12-21 10:32:22',2,'Yangcl','2022-12-21 10:33:45',2,'Yangcl',1),
(1149,'系统功能列表','1146',3,3,'1348B1D284FA','','','permissions/page_permissions_system_function.do','','展示一颗不可编辑的系统功能树，路径：webapp/views/permission/func/system-func-tree.jsp','','','2022-12-21 10:33:31',2,'Yangcl','2022-12-21 10:33:31',2,'Yangcl',1),
(1150,'查询','1147',1,4,'1348B1D284FA','','','','ajax_system_user_list.do','userInfo/ajax_system_user_list.do','10001','system_user_list:search','2022-12-21 10:34:57',2,'Yangcl','2022-12-21 10:34:57',2,'Yangcl',1),
(1151,'重置','1147',2,4,'1348B1D284FA','','','','ajax_system_user_list.do','userInfo/ajax_system_user_list.do','10001','system_user_list:reset','2022-12-21 10:35:27',2,'Yangcl','2022-12-21 10:35:27',2,'Yangcl',1),
(1152,'添加','1147',3,4,'1348B1D284FA','','','','ajax_btn_add_system_user.do','userInfo/ajax_btn_add_system_user.do','10001','system_user_list:add','2022-12-21 10:35:53',2,'Yangcl','2022-12-21 10:35:53',2,'Yangcl',1),
(1153,'删除','1147',4,4,'1348B1D284FA','','','','ajax_btn_delete_system_user.do','userInfo/ajax_btn_delete_system_user.do','10003','system_user_list:delete','2022-12-21 10:36:37',2,'Yangcl','2022-12-21 10:36:37',2,'Yangcl',1),
(1154,'修改','1147',5,4,'1348B1D284FA','','','','ajax_btn_edit_sys_user.do','userInfo/ajax_btn_edit_sys_user.do','10003','system_user_list:edit','2022-12-21 10:37:09',2,'Yangcl','2022-12-21 10:37:09',2,'Yangcl',1),
(1155,'重置密码','1147',6,4,'1348B1D284FA','','','','ajax_btn_password_reset.do','userInfo/ajax_btn_password_reset.do','10003','system_user_list:password_reset','2022-12-21 10:37:37',2,'Yangcl','2022-12-21 10:37:37',2,'Yangcl',1),
(1156,'用户角色','1147',7,5,'1348B1D284FA','','','dialog_permissions_system_role_list.do','','permissions/dialog_permissions_system_role_list.do','10001','system_user_list:user_role','2022-12-21 10:38:24',2,'Yangcl','2022-12-21 10:38:24',2,'Yangcl',1),
(1157,'权限重置','1147',8,4,'1348B1D284FA','','','','ajax_btn_user_cache_reload.do','sysrole/ajax_btn_user_cache_reload.do\r\n重置系统用户的所有信息包括：McSysFunc、McRole、McUserRole、UserInfoNp','10001','system_user_list:reload','2022-12-21 10:39:11',2,'Yangcl','2022-12-21 10:39:11',2,'Yangcl',1),
(1158,'系统角色列表-弹框-查询','1147',9,4,'1348B1D284FA','','','','ajax_user_role_list.do','sysrole/ajax_user_role_list.do','10002','system_user_list:dialog_search','2022-12-21 10:39:41',2,'Yangcl','2022-12-21 10:39:41',2,'Yangcl',1),
(1159,'系统角色列表-弹框-重置','1147',10,4,'1348B1D284FA','','','','ajax_user_role_list.do','sysrole/ajax_user_role_list.do','10002','system_user_list:dialog_reset','2022-12-21 10:40:02',2,'Yangcl','2022-12-21 10:40:02',2,'Yangcl',1),
(1160,'系统角色列表-弹框-分配','1147',11,4,'1348B1D284FA','','','','ajax_btn_allot_user_role_submit.do','sysrole/ajax_btn_allot_user_role_submit.do\r\n给指定用户分配一个角色','10003','system_user_list:allot_submit','2022-12-21 10:40:31',2,'Yangcl','2022-12-21 10:40:31',2,'Yangcl',1),
(1161,'系统角色列表-弹框-移除','1147',12,4,'1348B1D284FA','','','','ajax_btn_allot_user_role_remove.do','sysrole/ajax_btn_allot_user_role_remove.do\r\n解除角色绑定，同时删除缓存','10001','system_user_list:allot_remove','2022-12-21 10:40:52',2,'Yangcl','2022-12-21 10:40:52',2,'Yangcl',1),
(1162,'查询','1148',1,4,'1348B1D284FA','','','','ajax_system_role_list.do','sysrole/ajax_system_role_list.do','10001','system_role_list:search','2022-12-21 10:41:41',2,'Yangcl','2022-12-21 10:41:41',2,'Yangcl',1),
(1163,'重置','1148',2,4,'1348B1D284FA','','','','ajax_system_role_list.do','sysrole/ajax_system_role_list.do','10001','system_role_list:reset','2022-12-21 10:42:02',2,'Yangcl','2022-12-21 10:42:02',2,'Yangcl',1),
(1164,'添加','1148',3,4,'1348B1D284FA','','','','ajax_btn_add_mc_role_only.do','sysrole/ajax_btn_add_mc_role_only.do','10001','system_role_list:add','2022-12-21 10:42:24',2,'Yangcl','2022-12-21 10:42:24',2,'Yangcl',1),
(1165,'角色功能','1148',4,4,'1348B1D284FA','','','','default','default：仅做权限控制，不发起ajax请求','10003','system_role_list:role_function','2022-12-21 10:43:18',2,'Yangcl','2022-12-21 10:44:24',2,'Yangcl',1),
(1166,'修改','1148',5,4,'1348B1D284FA','','','','ajax_btn_edit_mc_role_only.do','sysrole/ajax_btn_edit_mc_role_only.do','10003','system_role_list:edit','2022-12-21 10:43:43',2,'Yangcl','2022-12-21 10:44:29',2,'Yangcl',1),
(1167,'删除','1148',6,4,'1348B1D284FA','','','','ajax_btn_delete_mc_role.do','sysrole/ajax_btn_delete_mc_role.do','10003','system_role_list:delete','2022-12-21 10:45:06',2,'Yangcl','2022-12-21 10:45:06',2,'Yangcl',1),
(1168,'角色功能弹窗-提交','1148',7,4,'1348B1D284FA','','','','ajax_btn_edit_mc_role.do','sysrole/ajax_btn_edit_mc_role.do','10001','system_role_list:dialog_submit','2022-12-21 10:45:38',2,'Yangcl','2022-12-21 10:45:38',2,'Yangcl',1),
(1169,'角色功能弹窗-解绑','1148',8,4,'1348B1D284FA','','','','ajax_btn_relieve_mc_role.do','sysrole/ajax_btn_relieve_mc_role.do','10001','system_role_list:dialog_cancel','2022-12-21 10:46:00',2,'Yangcl','2022-12-21 10:46:00',2,'Yangcl',1),
(1170,'一级菜单栏','1129',1,2,'1348B1D284FA','editor','','','','一级菜单栏','','','2022-12-21 10:52:09',2,'Yangcl','2022-12-21 10:52:09',2,'Yangcl',1),
(1171,'二级菜单栏','1170',1,3,'1348B1D284FA','','','example/ex.do','','二级菜单栏测试','','','2022-12-21 10:54:11',2,'Yangcl','2022-12-21 10:54:11',2,'Yangcl',1),
(1172,'测试按钮-1','1171',1,4,'1348B1D284FA','','','','ajax_default_list.do','测试按钮','10001','aaaa:bb','2022-12-21 10:55:40',2,'Yangcl','2022-12-21 10:55:40',2,'Yangcl',1),
(1173,'导航栏-1','1141',1,1,'1348CC633E7A','','','','','导航栏-1','','','2022-12-22 17:08:51',2,'Yangcl','2022-12-22 17:08:51',2,'Yangcl',1),
(1174,'导航栏-1|一级菜单栏','1173',1,2,'1348CC633E7A','editor','','','','一级菜单栏','','','2022-12-22 17:09:36',2,'Yangcl','2022-12-22 17:09:36',2,'Yangcl',1),
(1175,'导航栏-1|一级菜单栏|二级菜单栏','1174',1,3,'1348CC633E7A','','','ext/page_test.do','','导航栏-1|一级菜单栏|二级菜单栏','','','2022-12-22 17:10:15',2,'Yangcl','2022-12-22 17:10:15',2,'Yangcl',1),
(1176,'查询','1175',1,4,'1348CC633E7A','','','','ajax_btn_search.do','测试','10001','ss_aaa_list:search','2022-12-22 17:11:27',2,'Yangcl','2022-12-22 17:11:27',2,'Yangcl',1);

/*Table structure for table `mc_user_info` */

DROP TABLE IF EXISTS `mc_user_info`;

CREATE TABLE `mc_user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'zid',
  `user_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '系统用户登录名',
  `password` varchar(45) DEFAULT '' COMMENT '密码',
  `type` varchar(20) DEFAULT 'user' COMMENT 'leader Leader后台用户|admin 其他平台管理员|user其他平台用户',
  `platform` varchar(152) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'P01' COMMENT '平台标识码,逗号分隔|leader类型用户只能分配【矩阵分布式框架控制台】这个系统。',
  `flag` int DEFAULT '1' COMMENT '启用状态；1启用 2停用|type=user 则此字段生效',
  `idcard` varchar(20) DEFAULT '' COMMENT '身份证号',
  `sex` int DEFAULT '2' COMMENT '性别 1：男 2：女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '手机号',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '邮箱',
  `qq` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT 'qq号码',
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
) ENGINE=InnoDB AUTO_INCREMENT=20072026234195 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='API表格';

/*Data for the table `mc_user_info` */

insert  into `mc_user_info`(`id`,`user_name`,`password`,`type`,`platform`,`flag`,`idcard`,`sex`,`birthday`,`mobile`,`email`,`qq`,`pic_url`,`page_css`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(1,'admin','0988a08e0e7cdaf2b395133b0fbba289','leader','133C9CB27E18',1,'2',1,NULL,'2','2','2','','default','2','2018-07-28 23:30:08',1,'admin','2021-07-20 17:10:09',1,'admin',1),
(2,'Yangcl','71fb5b225e96fc8ec99e8fe85e35b40a','leader','133C9CB27E18',1,'',1,NULL,'18514037761','yangchenglin@300.cn',NULL,'','default','杨成琳-Leader后台管理员','2018-09-25 17:50:21',1,'admin','2019-07-23 10:59:37',2,'Yangcl',1),
(20072026234178,'user1','e10adc3949ba59abbe56e057f20f883e','leader','133C9CB27E18',1,'',1,NULL,'13521215656','13521215656@qq.com',NULL,'','default','密码：123456','2022-02-21 17:51:40',2,'Yangcl','2022-02-21 17:51:40',2,'Yangcl',1),
(20072026234179,'user2','e10adc3949ba59abbe56e057f20f883e','leader','133C9CB27E18',1,'',1,NULL,'13567678989','13567678989@qq.com',NULL,'','default','密码：123456','2022-02-21 17:52:16',2,'Yangcl','2022-02-21 17:52:16',2,'Yangcl',1),
(20072026234189,'multiple-1','e10adc3949ba59abbe56e057f20f883e','admin','1348B1D284FA,1348CC633E1C',1,'',1,NULL,'18900000000','multiple-3@ewwt.com','','','default','跨系统账号测试','2022-11-27 23:20:49',2,'Yangcl','2022-12-21 11:03:12',2,'Yangcl',1),
(20072026234192,'multiple-2','e10adc3949ba59abbe56e057f20f883e','user','1348B1D284FA',1,'',2,NULL,'','','','','default',NULL,'2022-12-21 15:46:07',0,'','2022-12-21 15:46:07',0,'',1),
(20072026234194,'multiple-2-user','e10adc3949ba59abbe56e057f20f883e','user','1348B1D284FA,1348CC633E1C',1,'',1,NULL,'18522222222','multiple-2-user@jd.com','','','default','multiple-2-user','2022-12-21 18:42:51',20072026234189,'multiple-1','2022-12-21 18:42:51',20072026234189,'multiple-1',1);

/*Table structure for table `mc_user_info_ext` */

DROP TABLE IF EXISTS `mc_user_info_ext`;

CREATE TABLE `mc_user_info_ext` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '员工扩展信息表,mc_user_info.type=user时生效，用户信息主体属性补充|归属EHR系统',
  `mc_user_id` bigint NOT NULL COMMENT '用户id',
  `name` varchar(10) DEFAULT '' COMMENT '员工真实姓名',
  `salary` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '员工最终薪资=合同薪资+绩效薪资',
  `bank_name` varchar(20) DEFAULT '' COMMENT '开户行名称',
  `bank_card` varchar(20) DEFAULT '' COMMENT '银行卡号/薪资卡',
  `salary_basic` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '员工基础薪资',
  `salary_merit` decimal(10,2) NOT NULL DEFAULT '2500.00' COMMENT '员工绩效薪资',
  `actual_cost` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '员工实际成本：当年实际工作月数*薪资(含企业负担五险一金/可平均计算) + 绩效奖金|四舍五入后的结果',
  `merit` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'B' COMMENT '员工当前年度绩效(即总评，不同于季度绩效)：D(无法胜任工作或效率明显低下)/C(客诉10次以上)/B-(客诉10次以内)/B(客诉5次以内)/A(无客诉或重要责任事故)/A+(突出贡献)',
  `premium` int DEFAULT '100' COMMENT '五险一金缴纳比例，0~100代表百分比',
  `any_past_history_of_illness` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '过往疾病史，此处为描述文本',
  `critical_illness_insurance` decimal(10,2) DEFAULT '0.00' COMMENT '公司是否为员工子女上了重疾险，上了的话每年额度多少',
  `native_place` varchar(52) DEFAULT '' COMMENT '籍贯',
  `present_address` varchar(52) DEFAULT '' COMMENT '当前居住地址',
  `idcard_pic_front` varchar(255) DEFAULT '' COMMENT '身份证正面图片信息',
  `idcard_pic_back` varchar(255) DEFAULT '' COMMENT '身份证反面图片信息',
  `en_proficiency_level` varchar(16) DEFAULT '' COMMENT '英语水平',
  `first_degree` varchar(52) DEFAULT '' COMMENT '第一学历(最高学历)，比如：哈佛大学/计算机学院/软件工程系/计算机学士',
  `first_degree_url` varchar(255) DEFAULT '' COMMENT '第一学历学位证书图片',
  `first_degree_diploma_url` varchar(255) DEFAULT '' COMMENT '第一学历毕业证书图片',
  `second_degree` varchar(52) DEFAULT '' COMMENT '第二学历',
  `second_degree_url` varchar(255) DEFAULT '' COMMENT '第二学历学位证书图片',
  `third_degree` varchar(52) DEFAULT '' COMMENT '第三学历',
  `third_degree_url` varchar(255) DEFAULT '' COMMENT '第三学历学位证书图片',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mc_user_info_ext` */

/*Table structure for table `mc_user_merit` */

DROP TABLE IF EXISTS `mc_user_merit`;

CREATE TABLE `mc_user_merit` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '员工绩效日志表|绩效评定人以create_user_name为准|归属EHR系统',
  `mc_user_id` bigint NOT NULL COMMENT '用户id',
  `year` varchar(4) NOT NULL COMMENT '年度，比如2025',
  `flag` int DEFAULT '1' COMMENT '绩效阶段：1-第一季度，2-第二季度，3-第三季度，4-第四季度',
  `merit` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'B' COMMENT '季度绩效得分：A+(突出贡献)/A(无客诉或重要责任事故)/B(客诉5次以内)/B-(客诉10次以内)/C(客诉10次以上)/D(无法胜任工作或效率明显低下)',
  `remark` varchar(255) DEFAULT '' COMMENT '绩效评语，阐述绩效得分原因',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '创建人姓名|绩效评定人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mc_user_merit` */

/*Table structure for table `mc_user_org_info` */

DROP TABLE IF EXISTS `mc_user_org_info`;

CREATE TABLE `mc_user_org_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '员工归属信息表主键|归属EHR系统',
  `name` varchar(52) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '乡镇/村/棚区编号|生产岗位/大棚编号|岗位点',
  `level` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'town:乡镇名称;village:村名称;area:棚区编号/生产岗位，比如：A区/游乐场;num:大棚编号 1,2,3等',
  `full_name` varchar(52) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT 'org_type=num时记录完整名称，比如：漷县镇-草厂村-A棚区-1号棚/漷县镇-草厂村-餐厅名-后厨',
  `parent_id` bigint NOT NULL COMMENT '父节点。root为0',
  `seqnum` int DEFAULT '1' COMMENT '顺序码,同一层次对应的显示顺序',
  `regions` varchar(52) NOT NULL DEFAULT '' COMMENT '所属省市区备注，比如：北京市-市辖区-通州区',
  `remark` varchar(52) DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mc_user_org_info` */

/*Table structure for table `mc_user_org_position` */

DROP TABLE IF EXISTS `mc_user_org_position`;

CREATE TABLE `mc_user_org_position` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '员工扩展信息关联表,mc_user_info.type=user时生效|归属EHR系统',
  `ext_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'position|org',
  `mc_user_id` bigint NOT NULL COMMENT '用户id',
  `extension_id` bigint NOT NULL COMMENT 'mc_user_position表 或 mc_user_org_info表主键',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mc_user_org_position` */

/*Table structure for table `mc_user_position` */

DROP TABLE IF EXISTS `mc_user_position`;

CREATE TABLE `mc_user_position` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '员工岗位表主键|归属EHR系统',
  `position_type` int NOT NULL DEFAULT '1' COMMENT '岗位类型：1-管理/财务/HR岗 2-运营岗 3-IT技术岗 4-生产岗(自营) 5-生产岗(外采) 6-生产岗(自营餐厅) 7-生产岗(娱乐项目) 8-服务岗',
  `position` varchar(52) NOT NULL DEFAULT '' COMMENT '岗位名称',
  `position_level` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '职级：职员/经理/总经理/副总/CEO/董事长 |学员/初级培育员/中级培育员/高级培育员/技术专家 | 中级/高级/资深/架构',
  `skill_requirement` varchar(1024) DEFAULT '' COMMENT '岗位技能要求',
  `salary_cap` decimal(10,2) DEFAULT '15000.00' COMMENT '薪资上限',
  `salary_floor` decimal(10,2) DEFAULT '3500.00' COMMENT '薪资下限',
  `merit_pay` decimal(10,2) DEFAULT '2500.00' COMMENT '绩效薪资',
  `annual_bonus` int DEFAULT '2' COMMENT '年终奖上限月数，仅限在职人员，标准14薪对应此基数为2',
  `promotion` varchar(52) DEFAULT '' COMMENT '岗位晋升条件',
  `active_staff` int NOT NULL DEFAULT '1' COMMENT '1-在职岗位 2-外包岗位',
  `remark` varchar(512) DEFAULT '' COMMENT '职能描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '1' COMMENT '创建人id',
  `create_user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'admin' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '1' COMMENT '更新人id',
  `update_user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'admin' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

/*Data for the table `mc_user_position` */

insert  into `mc_user_position`(`id`,`position_type`,`position`,`position_level`,`skill_requirement`,`salary_cap`,`salary_floor`,`merit_pay`,`annual_bonus`,`promotion`,`active_staff`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(1,1,'董事长','董事长','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:41:12',1,'','2022-11-23 14:41:12',1,'',1),
(2,1,'董事会成员','副总','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:41:43',1,'','2022-11-23 14:41:43',1,'',1),
(3,1,'首席执行官','CEO','',15000.00,3500.00,2500.00,2,'',1,'管理运营岗/IT技术岗','2022-11-23 14:42:33',1,'','2022-11-23 14:42:33',1,'',1),
(4,1,'生产总监','副总','',15000.00,3500.00,2500.00,4,'',1,'管理生产岗：自营/外采/自营餐厅/娱乐项目','2022-11-23 14:43:14',1,'','2022-11-23 14:43:14',1,'',1),
(5,1,'生产总监助理','职员','行程安排/会议预订/资料整理',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:45:22',1,'admin','2022-11-23 14:45:22',1,'admin',1),
(6,1,'园区总监','副总','',15000.00,3500.00,2500.00,2,'',1,'管理服务岗','2022-11-23 14:45:44',1,'admin','2022-11-23 14:45:44',1,'admin',1),
(7,1,'园区总监助理','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:46:28',1,'admin','2022-11-23 14:46:28',1,'admin',1),
(8,1,'财务总监','副总','财务相关技能',15000.00,3500.00,2500.00,2,'',1,'财务汇总/报税/统计/出纳','2022-11-23 14:46:50',1,'admin','2022-11-23 14:46:50',1,'admin',1),
(9,1,'出纳','职员','财务相关技能',15000.00,3500.00,2500.00,2,'',1,'财务总监助理','2022-11-23 14:48:00',1,'admin','2022-11-23 14:48:00',1,'admin',1),
(10,1,'人力资源经理','经理','人力资源技能/五险/员工基础保障',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:49:00',1,'admin','2022-11-23 14:49:00',1,'admin',1),
(11,1,'人力资源助理','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:49:02',1,'admin','2022-11-23 14:49:02',1,'admin',1),
(12,2,'运营总监','副总','C端电商运营技能',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:50:38',1,'admin','2022-11-23 14:50:38',1,'admin',1),
(13,2,'运营经理','经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:51:12',1,'admin','2022-11-23 14:51:12',1,'admin',1),
(14,2,'运营助理','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:51:22',1,'admin','2022-11-23 14:51:22',1,'admin',1),
(15,2,'售后服务经理','经理','',15000.00,3500.00,2500.00,2,'',1,'客服','2022-11-23 14:51:41',1,'admin','2022-11-23 14:51:41',1,'admin',1),
(16,2,'售后服务助理','职员','',15000.00,3500.00,2500.00,2,'',1,'客服','2022-11-23 14:52:11',1,'admin','2022-11-23 14:52:11',1,'admin',1),
(17,2,'活动策划经理','经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:52:30',1,'admin','2022-11-23 14:52:30',1,'admin',1),
(18,2,'活动策划助理','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:52:43',1,'admin','2022-11-23 14:52:43',1,'admin',1),
(19,2,'法务经理','经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:52:57',1,'admin','2022-11-23 14:52:57',1,'admin',1),
(20,2,'市场总监','副总','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:53:23',1,'admin','2022-11-23 14:53:23',1,'admin',1),
(21,2,'市场推广员','职员','',15000.00,3500.00,2500.00,2,'',1,'在职','2022-11-23 14:53:26',1,'admin','2022-11-23 14:53:26',1,'admin',1),
(22,2,'市场推广员','编外职员','',0.00,0.00,0.00,0,'',2,'非在职','2022-11-23 14:53:40',1,'admin','2022-11-23 14:53:40',1,'admin',1),
(23,2,'政务总监','副总','拥有政府背景能力',15000.00,3500.00,2500.00,2,'',1,'政府关系维护、政策处理、助贫助学','2022-11-23 14:54:12',1,'admin','2022-11-23 14:54:12',1,'admin',1),
(24,3,'技术总监','CTO','CTO',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:55:35',1,'admin','2022-11-23 14:55:35',1,'admin',1),
(25,3,'首席架构师','架构','Java微服务架构能力',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:56:37',1,'admin','2022-11-23 14:56:37',1,'admin',1),
(26,3,'技术经理','经理','资深技术/熟悉业务/团队管理',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:58:27',1,'admin','2022-11-23 14:58:27',1,'admin',1),
(27,3,'资深研发工程师','资深','Java技术方向',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:58:44',1,'admin','2022-11-23 14:58:44',1,'admin',1),
(28,3,'高级研发工程师','高级','Java技术方向',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:58:56',1,'admin','2022-11-23 14:58:56',1,'admin',1),
(29,3,'研发工程师','中级','Java技术方向',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:59:04',1,'admin','2022-11-23 14:59:04',1,'admin',1),
(30,3,'运维总监','架构','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 14:59:30',1,'admin','2022-11-23 14:59:30',1,'admin',1),
(31,3,'运维架构师','架构','中间件方向',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:00:08',1,'admin','2022-11-23 15:00:08',1,'admin',1),
(32,3,'资深运维工程师','资深','',15000.00,3500.00,2500.00,2,'',1,'在职','2022-11-23 15:00:50',1,'admin','2022-11-23 15:00:50',1,'admin',1),
(33,3,'资深运维工程师','编外职员','',0.00,0.00,0.00,0,'',2,'非在职','2022-11-23 15:00:56',1,'admin','2022-11-23 15:00:56',1,'admin',1),
(34,3,'高级运维工程师','高级','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:01:22',1,'admin','2022-11-23 15:01:22',1,'admin',1),
(35,3,'DBA架构师','架构','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:01:29',1,'admin','2022-11-23 15:01:29',1,'admin',1),
(36,3,'资深DBA工程师','资深','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:01:40',1,'admin','2022-11-23 15:01:40',1,'admin',1),
(37,4,'棚区经理','技术专家','管理1个有机蔬菜大棚',15000.00,5000.00,2500.00,8,'',1,'兼顾一个棚区的生产管理工作|全额五险一金','2022-11-23 15:03:33',1,'admin','2022-11-23 15:03:33',1,'admin',1),
(38,4,'蔬菜培育员(技术专家)','技术专家','管理3个有机蔬菜大棚',12000.00,5000.00,5000.00,6,'入职转正满60个月，且连续3年绩效A以上，考核通过晋升经理',1,'全额五险一金','2022-11-23 15:03:54',1,'admin','2022-11-23 15:03:54',1,'admin',1),
(39,4,'蔬菜培育员(高级培育员)','高级培育员','管理2个有机蔬菜大棚',8000.00,5000.00,3000.00,4,'入职转正满48个月，且连续2年绩效A以上，考核通过进阶技术专家',1,'全额五险一金','2022-11-23 15:04:22',1,'admin','2022-11-23 15:04:22',1,'admin',1),
(40,4,'蔬菜培育员(中级培育员)','中级培育员','管理1个有机蔬菜大棚',6000.00,2500.00,2500.00,3,'入职转正满24个月，且当年绩效A以上，考核通过进阶高级培育员',1,'非全额五险一金|社保/住房公积金缴存基数：3500元','2022-11-23 15:04:33',1,'admin','2022-11-23 15:04:33',1,'admin',1),
(41,4,'蔬菜培育员(初级培育员)','初级培育员','管理1个有机蔬菜大棚',5000.00,2500.00,1500.00,3,'入职转正满12个月，且当年绩效B以上，考核通过进阶中级培育员',1,'非全额五险一金|社保/住房公积金缴存基数：3500元','2022-11-23 15:04:41',1,'admin','2022-11-23 15:04:41',1,'admin',1),
(42,4,'蔬菜培育员(学员)','学员','协助管理1个有机蔬菜大棚',4000.00,2500.00,1500.00,2,'试用期6个月以后考核通过进阶初级培育员',1,'非全额五险一金|社保/住房公积金缴存基数：3500元','2022-11-23 15:04:51',1,'admin','2022-11-23 15:04:51',1,'admin',1),
(43,4,'家禽培育员(技术专家)','技术专家','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:05:42',1,'admin','2022-11-23 15:05:42',1,'admin',1),
(44,4,'家禽培育员(高级培育员)','高级培育员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:05:50',1,'admin','2022-11-23 15:05:50',1,'admin',1),
(45,4,'家禽培育员(中级培育员)','中级培育员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:06:10',1,'admin','2022-11-23 15:06:10',1,'admin',1),
(46,4,'家禽培育员(初级培育员)','初级培育员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:06:19',1,'admin','2022-11-23 15:06:19',1,'admin',1),
(47,4,'家禽培育员(学员)','学员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:06:29',1,'admin','2022-11-23 15:06:29',1,'admin',1),
(48,4,'生猪培育员(技术专家)','技术专家','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:16:59',1,'admin','2022-11-23 15:16:59',1,'admin',1),
(49,4,'生猪培育员(高级培育员)','高级培育员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:17:08',1,'admin','2022-11-23 15:17:08',1,'admin',1),
(50,4,'生猪培育员(中级培育员)','中级培育员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:17:21',1,'admin','2022-11-23 15:17:21',1,'admin',1),
(51,4,'生猪培育员(初级培育员)','初级培育员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:17:22',1,'admin','2022-11-23 15:17:22',1,'admin',1),
(52,4,'生猪培育员(学员)','学员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:17:47',1,'admin','2022-11-23 15:17:47',1,'admin',1),
(53,4,'牛羊培育员(技术专家)','技术专家','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:17:58',1,'admin','2022-11-23 15:17:58',1,'admin',1),
(54,4,'牛羊培育员(高级培育员)','高级培育员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:18:09',1,'admin','2022-11-23 15:18:09',1,'admin',1),
(55,4,'牛羊培育员(中级培育员)','中级培育员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:18:18',1,'admin','2022-11-23 15:18:18',1,'admin',1),
(56,4,'牛羊培育员(初级培育员)','初级培育员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:18:27',1,'admin','2022-11-23 15:18:27',1,'admin',1),
(57,4,'牛羊培育员(学员)','学员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:22:07',1,'admin','2022-11-23 15:22:07',1,'admin',1),
(58,4,'物料库管员','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:22:16',1,'admin','2022-11-23 15:22:16',1,'admin',1),
(59,4,'家畜屠宰员','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:22:24',1,'admin','2022-11-23 15:22:24',1,'admin',1),
(60,4,'堆肥师','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:22:26',1,'admin','2022-11-23 15:22:26',1,'admin',1),
(61,5,'外采总经理','总经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:24:21',1,'admin','2022-11-23 15:24:21',1,'admin',1),
(62,5,'外采蔬菜经理','经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:24:30',1,'admin','2022-11-23 15:24:30',1,'admin',1),
(63,5,'外采蔬菜助理','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:24:39',1,'admin','2022-11-23 15:24:39',1,'admin',1),
(64,5,'外采家禽经理(禽蛋)','经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:24:43',1,'admin','2022-11-23 15:24:43',1,'admin',1),
(65,5,'外采家禽助理(禽蛋)','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:24:51',1,'admin','2022-11-23 15:24:51',1,'admin',1),
(66,5,'外采牛羊肉经理','经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:24:59',1,'admin','2022-11-23 15:24:59',1,'admin',1),
(67,5,'外采牛羊肉助理','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:25:10',1,'admin','2022-11-23 15:25:10',1,'admin',1),
(68,5,'外采食用菌经理','经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:25:17',1,'admin','2022-11-23 15:25:17',1,'admin',1),
(69,5,'外采食用菌助理','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:25:25',1,'admin','2022-11-23 15:25:25',1,'admin',1),
(70,5,'外采水果经理','经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:25:32',1,'admin','2022-11-23 15:25:32',1,'admin',1),
(71,5,'外采水果助理','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:25:40',1,'admin','2022-11-23 15:25:40',1,'admin',1),
(72,6,'餐厅经理','经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:26:39',1,'admin','2022-11-23 15:26:39',1,'admin',1),
(73,6,'餐厅厨师','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:26:48',1,'admin','2022-11-23 15:26:48',1,'admin',1),
(74,6,'餐厅切菜工','职员','洗碗/打扫卫生',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:26:51',1,'admin','2022-11-23 15:26:51',1,'admin',1),
(75,6,'餐厅服务员','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:27:01',1,'admin','2022-11-23 15:27:01',1,'admin',1),
(76,6,'餐厅杂物工','编外职员','洗碗/打扫卫生',0.00,0.00,0.00,0,'',2,'非在职','2022-11-23 15:27:03',1,'admin','2022-11-23 15:27:03',1,'admin',1),
(77,7,'娱乐项目经理','经理','',15000.00,3500.00,2500.00,2,'',1,'管理/娱乐物资采购','2022-11-23 15:28:23',1,'admin','2022-11-23 15:28:23',1,'admin',1),
(78,7,'游乐园安全质检员','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:29:39',1,'admin','2022-11-23 15:29:39',1,'admin',1),
(79,7,'游乐园服务员','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:29:44',1,'admin','2022-11-23 15:29:44',1,'admin',1),
(80,7,'垂钓园服务员','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:29:49',1,'admin','2022-11-23 15:29:49',1,'admin',1),
(81,7,'民宿服务员','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:29:55',1,'admin','2022-11-23 15:29:55',1,'admin',1),
(82,8,'客户物资配送经理','经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:31:21',1,'admin','2022-11-23 15:31:21',1,'admin',1),
(83,8,'客户物资配送员','编外职员','',0.00,0.00,0.00,0,'',2,'非在职','2022-11-23 15:32:22',1,'admin','2022-11-23 15:32:22',1,'admin',1),
(84,8,'园区电工','编外职员','',0.00,0.00,0.00,0,'',2,'非在职','2022-11-23 15:32:30',1,'admin','2022-11-23 15:32:30',1,'admin',1),
(85,8,'园区保安经理','经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:32:48',1,'admin','2022-11-23 15:32:48',1,'admin',1),
(86,8,'园区保安','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:32:55',1,'admin','2022-11-23 15:32:55',1,'admin',1),
(87,8,'园区清洁工','','',15000.00,3500.00,2500.00,2,'',1,'非在职','2022-11-23 15:33:11',1,'admin','2022-11-23 15:33:11',1,'admin',1),
(88,8,'园区服务员','职员','',15000.00,3500.00,2500.00,2,'',1,'引导/便利店员','2022-11-23 15:33:19',1,'admin','2022-11-23 15:33:19',1,'admin',1),
(89,8,'园区维修部经理','经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:33:32',1,'admin','2022-11-23 15:33:32',1,'admin',1),
(90,8,'园区维修工','职员','',15000.00,3500.00,2500.00,2,'',1,'棚区生产资料/车辆/娱乐设施','2022-11-23 15:33:39',1,'admin','2022-11-23 15:33:39',1,'admin',1),
(91,8,'园区食堂经理','经理','',15000.00,3500.00,2500.00,2,'',1,'厨师兼任','2022-11-23 15:34:07',1,'admin','2022-11-23 15:34:07',1,'admin',1),
(92,8,'园区食堂厨师','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:34:14',1,'admin','2022-11-23 15:34:14',1,'admin',1),
(93,8,'园区食堂服务员','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:34:22',1,'admin','2022-11-23 15:34:22',1,'admin',1),
(94,8,'员工幼儿托育经理','经理','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:34:31',1,'admin','2022-11-23 15:34:31',1,'admin',1),
(95,8,'员工幼儿托育员','职员','',15000.00,3500.00,2500.00,2,'',1,'','2022-11-23 15:34:38',1,'admin','2022-11-23 15:34:38',1,'admin',1);

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
) ENGINE=InnoDB AUTO_INCREMENT=535 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_user_role` */

insert  into `mc_user_role`(`id`,`mc_user_id`,`mc_role_id`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values 
(504,1,116,'','2019-09-27 18:13:11',2,'Yangcl','2019-09-27 18:13:11',2,'Yangcl',1),
(519,20072026234179,196,NULL,'2022-02-21 17:52:26',2,'Yangcl','2022-02-21 17:52:26',2,'Yangcl',1),
(520,20072026234178,196,NULL,'2022-02-21 17:52:38',2,'Yangcl','2022-02-21 17:52:38',2,'Yangcl',1),
(523,2,116,NULL,'2022-08-09 15:12:02',1,'admin','2022-08-09 15:12:02',1,'admin',1),
(532,20072026234189,201,NULL,'2022-12-21 11:03:20',2,'Yangcl','2022-12-21 11:03:20',2,'Yangcl',1),
(533,20072026234189,200,NULL,'2022-12-22 14:56:24',2,'Yangcl','2022-12-22 14:56:24',2,'Yangcl',1),
(534,20072026234194,203,NULL,'2022-12-22 16:16:05',20072026234189,'multiple-1','2022-12-22 16:16:05',20072026234189,'multiple-1',1);

/*Table structure for table `mc_user_salary_log` */

DROP TABLE IF EXISTS `mc_user_salary_log`;

CREATE TABLE `mc_user_salary_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '员工薪资发放记录表|归属EHR系统',
  `mc_user_id` bigint NOT NULL COMMENT '员工ID',
  `year` varchar(4) NOT NULL DEFAULT '' COMMENT '薪资发放年份',
  `month` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '薪资发放月份',
  `salary_pay` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '实发薪资',
  `salary_basic` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '员工基础薪资',
  `salary_merit` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '应发绩效薪资=合同绩效薪资*绩效得分，即：mc_user_info_ext.salary_merit * this.merit_point',
  `merit_point` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '绩效得分：A+=1.5|A=1.2|B=1|B-=0.5|C=0.1|D=0',
  `social_security_personal` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '社保扣款-个人',
  `social_security_company` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '社保扣款-公司',
  `individual_income_tax` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '个人所得税',
  `housing_fund_personal` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '住房公积金扣款-个人',
  `housing_fund_company` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '住房公积金扣款-公司',
  `bonus` decimal(10,2) DEFAULT '0.00' COMMENT '特殊贡献奖金',
  `bonus_reason` varchar(52) DEFAULT '' COMMENT '特殊贡献奖金发放原因',
  `penalty` decimal(10,2) DEFAULT '0.00' COMMENT '产生罚款金额',
  `penalty_reason` varchar(52) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '罚款金额原因',
  `remark` varchar(52) DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mc_user_salary_log` */

/*Table structure for table `mc_workflow_purchase` */

DROP TABLE IF EXISTS `mc_workflow_purchase`;

CREATE TABLE `mc_workflow_purchase` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'mc_goods_purchase表对应的采购记录流转表|归属EHR系统',
  `action` varchar(20) NOT NULL DEFAULT '' COMMENT '采购类型，如：大棚生产物资|员工福利商品等，即：mc_goods_purchase.purchase_type,区分流转过程的不同',
  `serial_number` varchar(52) NOT NULL DEFAULT '' COMMENT '采购批次流水号',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '流转记录名称，如：一级审批、二级审批、入库等等',
  `mc_user_id` bigint NOT NULL COMMENT '流程处理人员ID',
  `process_user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '流程处理人员真实名称',
  `status` int DEFAULT '0' COMMENT '处理结果：0-处理中 1-通过 2-驳回 3-完成',
  `flag` int DEFAULT '0' COMMENT '是否显示当前记录 0-隐藏 1-显示',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mc_workflow_purchase` */

/*Table structure for table `mc_workflow_template` */

DROP TABLE IF EXISTS `mc_workflow_template`;

CREATE TABLE `mc_workflow_template` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '工作流转模板表(树型结构数据记录)|归属EHR系统',
  `channel` varchar(2) NOT NULL DEFAULT '' COMMENT '流转渠道：A-运营系统 B-EHR系统 C-生产管理系统',
  `service_name` varchar(20) NOT NULL DEFAULT '' COMMENT '所属业务线的流转表名，比如：mc_workflow_*****|mc_workflow_purchase',
  `name` varchar(52) NOT NULL DEFAULT '' COMMENT '工作流模板名称(leve=1) or 工作流过程描述(level=2)',
  `parent_id` bigint NOT NULL COMMENT '父节点ID',
  `level` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '节点等级 1-模板名称 2-工作流过程描述',
  `template_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '当前工作流模板编码，方便获取数据使用',
  `seqnum` int NOT NULL DEFAULT '1' COMMENT '同层节点顺序码，流转顺序以此为依据',
  `mc_user_id` bigint DEFAULT '0' COMMENT '流转处理人id',
  `process_user_name` varchar(10) DEFAULT '' COMMENT '流转处理人真实姓名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mc_workflow_template` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
