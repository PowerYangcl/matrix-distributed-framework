/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.36-log : Database - matrix
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`matrix` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `matrix`;

/*Table structure for table `ac_api_domain` */

DROP TABLE IF EXISTS `ac_api_domain`;

CREATE TABLE `ac_api_domain` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ac_api_info 与 ac_include_domain 的关联表',
  `ac_api_info_id` bigint(20) DEFAULT NULL,
  `ac_include_domain_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=807 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `ac_api_domain` */

insert  into `ac_api_domain`(`id`,`ac_api_info_id`,`ac_include_domain_id`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values (803,80161083,27,'2020-01-17 15:25:48',2,'Yangcl','2020-01-17 15:25:48',2,'Yangcl',1),(804,80161083,29,'2020-01-17 15:25:48',2,'Yangcl','2020-01-17 15:25:48',2,'Yangcl',1),(805,80161083,30,'2020-01-17 15:25:48',2,'Yangcl','2020-01-17 15:25:48',2,'Yangcl',1),(806,80161083,32,'2020-01-17 15:25:48',2,'Yangcl','2020-01-17 15:25:48',2,'Yangcl',1);

/*Table structure for table `ac_api_info` */

DROP TABLE IF EXISTS `ac_api_info`;

CREATE TABLE `ac_api_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'api信息表',
  `name` varchar(50) DEFAULT '' COMMENT '接口中文描述   树展示使用',
  `target` varchar(50) DEFAULT NULL COMMENT '系统接口名称 比如：TEST-PUBLIC-PROCESSOR，访问标识',
  `dto_info` text COMMENT '请求参数信息',
  `atype` varchar(10) DEFAULT 'private' COMMENT '接口类型 private:私有 即公司内部使用的接口| public:公开，即开放给第三方的接口',
  `module` varchar(20) DEFAULT 'matrix-api' COMMENT 'maven sub module name  比如：matrix-file',
  `processor` varchar(300) DEFAULT '' COMMENT '业务处理接口的类 com.matrix.processor.publics.example.TestPublicProcessor',
  `domain` int(2) DEFAULT '0' COMMENT '接口是否拥有跨域行为 0 不允许  1 允许跨域访问|ac_api_domain表作为关联',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '所属内部项目id,用于树形结构展示|ac_api_project表id',
  `seqnum` int(11) DEFAULT NULL COMMENT '顺序码 同一层次显示顺序',
  `discard` int(2) DEFAULT '1' COMMENT '这个api是否废弃|0:废弃 1:使用中',
  `login` int(2) DEFAULT '1' COMMENT '当前接口是否需要登录后访问：1 需要登录后访问 0不需要',
  `remark` longtext COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=80161084 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='alter table `ac_api_info` AUTO_INCREMENT=80160001';

/*Data for the table `ac_api_info` */

insert  into `ac_api_info`(`id`,`name`,`target`,`dto_info`,`atype`,`module`,`processor`,`domain`,`parent_id`,`seqnum`,`discard`,`login`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values (80160101,'根据API的target返回DTO的Json文本','API-COMMON-FIND-DTO',NULL,'private','matrix-api','common.ApiFindDtoProcessor',0,8,6,1,1,'根据API缓存的target返回查询消息体','2018-10-09 11:07:55',1992,'Yangcl','2018-11-16 11:31:33',1992,'Yangcl',1),(80160102,'根据Api请求者的key，找到对应的value','API-COMMON-FIND-VALUE-BY-KEY','{}','private','matrix-api','common.ApiFindValueByKey',0,8,7,0,1,'根据请求者的key，找到对应的value','2018-10-09 11:10:39',1992,'Yangcl','2020-01-10 18:02:00',2,'Yangcl',1),(80160155,'用户登录接口','MANAGER-API-100','{\"userName\":\"admin-mdl\",\"password\":\"123456\",\r\n\"platform\":\"134160222D87\"}','private','matrix-manager-api','privates.ManagerApi100Processor',0,9,3,1,0,'验证用户登录信息|客户端用户：nodejs/IOS平板等','2018-10-11 15:49:38',1992,'Yangcl','2019-09-18 14:09:55',20072026234128,'XuTao',1),(80160156,'用户退出系统接口','MANAGER-API-101','{}','private','matrix-manager-api','privates.ManagerApi101Processor',0,9,4,1,1,'退出系统登录|客户端用户：nodejs/IOS平板等','2018-10-11 15:51:16',1992,'Yangcl','2019-03-27 19:27:43',1992,'Yangcl',1),(80160157,'用户列表页数据','MANAGER-API-102','{\"startIndex\":1,\"pageSize\":10,\"cid\":-1}','private','matrix-manager-api','privates.ManagerApi102Processor',0,9,5,1,1,'用户列表页数据展示','2018-10-11 15:52:16',1992,'Yangcl','2020-01-10 17:35:11',2,'Yangcl',1),(80160158,'添加用户界面-绘制所属公司和平台分配两个控件','MANAGER-API-103',NULL,'private','matrix-manager-api','privates.ManagerApi103Processor',0,9,6,1,1,'添加用户界面-绘制所属公司（下拉框列表）和平台分配（单选按钮）','2018-10-12 11:37:29',1992,'Yangcl','2018-10-12 11:37:29',1992,'Yangcl',1),(80160183,'添加用户','MANAGER-API-104',NULL,'private','matrix-manager-api','privates.ManagerApi104Processor',0,9,7,1,1,'添加用户|MANAGER-API-104','2018-10-12 19:40:09',1992,'Yangcl','2018-10-12 19:53:39',1992,'Yangcl',1),(80160184,'获取用户详情','MANAGER-API-105','{\"id\":20072026234148}','private','matrix-manager-api','privates.ManagerApi105Processor',0,9,8,1,1,'在进入编辑页面时 -> 获取用户详情 -> 展示用户信息','2018-10-12 19:53:32',1992,'Yangcl','2019-08-27 14:59:01',20072026234128,'XuTao',1),(80160185,'修改用户信息','MANAGER-API-106',NULL,'private','matrix-manager-api','privates.ManagerApi106Processor',0,9,9,1,1,'修改用户信息','2018-10-12 20:07:12',1992,'Yangcl','2018-10-12 20:07:12',1992,'Yangcl',1),(80160186,'删除一个用户|不保留数据库中的记录','MANAGER-API-107',NULL,'private','matrix-manager-api','privates.ManagerApi107Processor',0,9,10,1,1,'删除一个用户|不保留数据库中的记录|物理删除','2018-10-12 20:08:00',1992,'Yangcl','2018-10-12 20:08:00',1992,'Yangcl',1),(80160187,'用户选择后台页面样式风格','MANAGER-API-108',NULL,'private','matrix-manager-api','privates.ManagerApi108Processor',0,9,11,1,1,'用户自己选择后台页面样式风格','2018-10-12 20:14:21',1992,'Yangcl','2018-10-12 20:14:42',1992,'Yangcl',1),(80160188,'获取功能树列表','MANAGER-API-109',NULL,'private','matrix-manager-api','privates.ManagerApi109Processor',0,9,12,1,1,'获取树列表|sys-user-role-function.js使用2次\r\ndto.platform 如果不为空则获取指定平台下的功能节点\r\ndto.type type=list or role|如果type=role则同时获得角色列表，同时dto.id = roleId','2018-10-13 14:49:08',1992,'Yangcl','2018-10-13 14:49:08',1992,'Yangcl',1),(80160189,'系统角色列表数据','MANAGER-API-110',NULL,'private','matrix-manager-api','privates.ManagerApi110Processor',0,9,13,1,1,'系统角色列表数据','2018-10-13 15:05:00',1992,'Yangcl','2018-10-13 15:05:00',1992,'Yangcl',1),(80160190,'展示权限列表|如果用户已经有权限了则标识出来','MANAGER-API-111',NULL,'private','matrix-manager-api','privates.ManagerApi111Processor',0,9,14,1,1,'展示权限列表|如果用户已经有权限了则标识出来','2018-10-13 15:12:46',1992,'Yangcl','2018-10-13 15:12:46',1992,'Yangcl',1),(80160191,'添加一个角色，不勾选系统功能','MANAGER-API-112',NULL,'private','matrix-manager-api','privates.ManagerApi112Processor',0,9,15,1,1,'添加一个角色，不勾选系统功能','2018-10-13 15:34:20',1992,'Yangcl','2018-10-13 15:34:20',1992,'Yangcl',1),(80160192,'角色详情','MANAGER-API-113',NULL,'private','matrix-manager-api','privates.ManagerApi113Processor',0,9,16,1,1,'唯一参数：info.id mc_role表自增id。','2018-10-13 15:52:59',1992,'Yangcl','2018-10-13 16:01:16',1992,'Yangcl',1),(80160193,'修改角色名称|角色编辑页面的提交按钮','MANAGER-API-114',NULL,'private','matrix-manager-api','privates.ManagerApi114Processor',0,9,17,1,1,'修改角色名称和描述，不勾选系统功能|角色编辑页面的提交按钮','2018-10-13 16:03:13',1992,'Yangcl','2018-10-15 16:44:02',1992,'Yangcl',1),(80160217,'添加系统功能','MANAGER-API-115',NULL,'private','matrix-manager-api','privates.ManagerApi115Processor',0,9,18,1,1,'添加系统功能到数据库-mc_sys_function表添加记录','2018-10-15 14:37:25',1992,'Yangcl','2018-10-15 14:37:25',1992,'Yangcl',1),(80160218,'更新系统功能','MANAGER-API-116',NULL,'private','matrix-manager-api','privates.ManagerApi116Processor',0,9,19,1,1,'更新系统功能到数据库-mc_sys_function表添加记录','2018-10-15 14:38:18',1992,'Yangcl','2018-10-15 14:38:18',1992,'Yangcl',1),(80160219,'系统功能同层节点拖拽更新','MANAGER-API-117',NULL,'private','matrix-manager-api','privates.ManagerApi117Processor',0,9,20,1,1,'更新拖拽后的同层节点|dto.ustring id@seqnum,id@seqnum','2018-10-15 14:57:57',1992,'Yangcl','2018-10-15 14:57:57',1992,'Yangcl',1),(80160220,'删除一个系统功能节点及其子节点','MANAGER-API-118',NULL,'private','matrix-manager-api','privates.ManagerApi118Processor',0,9,21,1,1,'删除一个系统功能节点及其子节点','2018-10-15 15:28:12',1992,'Yangcl','2018-10-15 15:28:12',1992,'Yangcl',1),(80160221,'创建系统角色','MANAGER-API-119',NULL,'private','matrix-manager-api','privates.ManagerApi119Processor',0,9,22,1,1,'创建系统角色','2018-10-15 15:58:10',1992,'Yangcl','2018-10-15 15:58:10',1992,'Yangcl',1),(80160222,'修改角色功能|【角色列表】->【角色功能】->【提交按钮】','MANAGER-API-120',NULL,'private','matrix-manager-api','privates.ManagerApi120Processor',0,9,23,1,1,'修改系统角色功能|【角色列表】->【角色功能】->【提交按钮】','2018-10-15 16:45:16',1992,'Yangcl','2018-10-15 16:52:48',1992,'Yangcl',1),(80160223,'删除角色','MANAGER-API-121',NULL,'private','matrix-manager-api','privates.ManagerApi121Processor',0,9,24,1,1,' 需要判断 mc_user_role 表中是否已经关联了用户，如果关联了，则不允许删除；如果想删除则必选先将用户与该角色解除绑定，即：删除mc_user_role表中的关联记录','2018-10-15 16:51:01',1992,'Yangcl','2018-10-15 16:51:01',1992,'Yangcl',1),(80160224,'关联用户与某一个角色','MANAGER-API-122',NULL,'private','matrix-manager-api','privates.ManagerApi122Processor',0,9,25,1,1,'关联用户与某一个角色','2018-10-15 17:12:39',1992,'Yangcl','2018-10-15 17:12:39',1992,'Yangcl',1),(80160225,'解除角色绑定','MANAGER-API-123',NULL,'private','matrix-manager-api','privates.ManagerApi123Processor',0,9,26,1,1,'解除角色绑定，同时删除缓存','2018-10-15 17:16:21',1992,'Yangcl','2018-10-15 17:16:21',1992,'Yangcl',1),(80160262,'公司组织机构数列表数据','MANAGER-API-124',NULL,'private','matrix-manager-api','privates.ManagerApi124Processor',0,9,27,1,1,'公司组织结构数列表数据','2018-10-26 18:05:43',2000,'Shaohua.ma','2018-10-26 18:15:56',2000,'Shaohua.ma',1),(80160263,'删除多个组织机构节点','MANAGER-API-127',NULL,'private','matrix-manager-api','privates.ManagerApi127Processor',0,9,28,1,1,'删除多个组织结构节点','2018-10-26 18:06:56',2000,'Shaohua.ma','2018-10-26 18:16:42',2000,'Shaohua.ma',1),(80160264,'添加一个组织结构节点到数据库','MANAGER-API-125',NULL,'private','matrix-manager-api','privates.ManagerApi125Processor',0,9,29,1,1,'添加一个组织结构节点到数据库','2018-10-26 18:07:38',2000,'Shaohua.ma','2018-10-26 18:16:08',2000,'Shaohua.ma',1),(80160265,'编辑一个组织机构节点','MANAGER-API-126',NULL,'private','matrix-manager-api','privates.ManagerApi126Processor',0,9,30,1,1,'编辑一个组织机构节点','2018-10-26 18:08:19',2000,'Shaohua.ma','2018-10-26 18:16:36',2000,'Shaohua.ma',1),(80160266,'系统功能同层节点拖拽更新','MANAGER-API-128',NULL,'private','matrix-manager-api','privates.ManagerApi128Processor',0,9,31,1,1,'系统功能同层节点拖拽更新','2018-10-26 18:08:56',2000,'Shaohua.ma','2018-10-26 18:16:16',2000,'Shaohua.ma',1),(80160275,'修改用户密码','MANAGER-API-129',NULL,'private','matrix-api','privates.ManagerApi129Processor',0,9,32,1,1,'修改用户密码','2018-10-30 16:59:55',2000,'Shaohua.ma','2018-10-30 16:59:55',2000,'Shaohua.ma',1),(80160276,'数据权限','MANAGER-API-130',NULL,'private','matrix-api','privates.ManagerApi130Processor',0,9,33,1,1,'数据权限','2018-10-31 11:27:20',2000,'Shaohua.ma','2018-10-31 11:27:20',2000,'Shaohua.ma',1),(80160277,'文件上传接口','Api-File-Remote-Upload',NULL,'private','matrix-api','common.ApiFileRemoteUpload',0,1,1,1,1,'系统文件上传接口|平台公共接口的一部分','2018-11-01 16:12:56',1992,'Yangcl','2018-11-01 16:12:56',1992,'Yangcl',0),(80160318,'门店列表数据','MANAGER-API-131',NULL,'private','matrix-api','privates.ManagerApi131Processor',0,9,34,1,1,'门店列表数据','2018-11-20 17:54:38',1991,'wangju','2018-11-20 17:54:38',1991,'wangju',1),(80160325,'获取API接口项目中的Config配置信息','API-COMMON-CONFIG-INFO','{}','private','matrix-api','common.ApiFindConfigInfoProcessor',0,8,8,1,1,'获取API接口项目中的Config配置信息|如果部署多个API接口服务器，则可能随机获取其中的一个，但会返回一个标识的IP地址作为区分依据','2018-11-26 19:09:15',1992,'Yangcl','2020-01-13 09:33:07',2,'Yangcl',1),(80160326,'获取验证码接口','MANAGER-API-140','{}','private','matrix-manager-api','privates.ManagerApi140Processor',0,9,37,1,0,'API:获取验证码接口','2018-11-27 14:49:48',1994,'wanghao','2019-03-27 19:11:03',1992,'Yangcl',1),(80160327,'用户关联数据权限','MANAGER-API-134',NULL,'private','matrix-manager-api','privates.ManagerApi134Processor',0,9,38,1,1,'用户关联数据权限','2018-11-28 17:28:35',2000,'Shaohua.ma','2018-11-28 17:28:35',2000,'Shaohua.ma',1),(80160329,'区域门店集合','MANAGER-API-141',NULL,'private','matrix-manager-api','privates.ManagerApi141Processor',0,9,39,1,1,'获取区域门店集合','2018-12-18 10:46:18',2000,'Shaohua.ma','2018-12-18 10:46:18',2000,'Shaohua.ma',1),(80160340,'获取登录用户的角色','MANAGER-API-142',NULL,'private','matrix-manager-api','privates.ManagerApi142Processor',0,9,40,1,1,'获取登录用户的角色','2018-12-19 19:14:11',2000,'Shaohua.ma','2018-12-19 19:14:11',2000,'Shaohua.ma',1),(80161081,'1111','1222222222','{}','private','aaaaa','private.aaaawwwwww',0,8,9,1,1,'3124','2020-01-13 09:32:57',2,'Yangcl','2020-01-13 09:33:39',2,'Yangcl',0),(80161082,'13123123','125123123','{}','private','122222222222','private.sdi25woieutae',0,8,9,1,1,'q25140000000','2020-01-13 09:46:31',2,'Yangcl','2020-01-13 16:16:34',2,'Yangcl',0),(80161083,'1241231231','23123123123','{}','private','sfrqr','private.asdiealkdf',1,8,9,1,1,'23123123','2020-01-13 16:19:31',2,'Yangcl','2020-01-17 15:33:51',2,'Yangcl',1);

/*Table structure for table `ac_api_project` */

DROP TABLE IF EXISTS `ac_api_project`;

CREATE TABLE `ac_api_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'api所属项目|每一个web-api项目会对应这里的一条记录，然后由此向下延展其提供的接口',
  `target` varchar(25) DEFAULT '' COMMENT '项目名称',
  `atype` varchar(10) DEFAULT 'private' COMMENT '接口类型 private:私有 即公司内部使用的接口| public:公开，即开放给第三方的接口',
  `service_url` varchar(256) DEFAULT '' COMMENT '对应一个Tomcat web项目的服务器部署浏览器路径|也可以是一个Nginx前端负载路径',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '1' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT 'admin' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '1' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT 'admin' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `ac_api_project` */

insert  into `ac_api_project`(`id`,`target`,`atype`,`service_url`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values (8,'平台公共接口-系统间调用','private','http://127.0.0.1:8085/api-public-web/manager/api.do','2018-09-27 19:16:11',1,'admin','2019-09-09 15:37:19',20072026234125,'JingHao.Sun',1),(9,'系统后台权限接口','private','http://10.12.52.41:8080/matrix-admin/matrix/api.do','2018-09-27 19:16:11',1,'admin','2019-09-04 15:59:02',2,'Yangcl',1);

/*Table structure for table `ac_include_domain` */

DROP TABLE IF EXISTS `ac_include_domain`;

CREATE TABLE `ac_include_domain` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录系统允许跨域的域名',
  `domain` varchar(100) DEFAULT '' COMMENT '域名',
  `company_name` varchar(100) DEFAULT '' COMMENT '所属公司',
  `project` varchar(25) DEFAULT '' COMMENT '所属项目名称',
  `flag` int(2) DEFAULT '1' COMMENT '状态位-是否有效 0无效 1有效',
  `remark` longtext COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '1' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT 'admin' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '1' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT 'admin' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `ac_include_domain` */

insert  into `ac_include_domain`(`id`,`domain`,`company_name`,`project`,`flag`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values (27,'www.baidu.com','百度科技','',1,NULL,'2019-12-27 17:46:42',2,'Yangcl','2019-12-27 17:46:42',2,'Yangcl',1),(29,'www.jd.wuliu.com','京东物流','',1,NULL,'2020-01-16 17:31:21',2,'Yangcl','2020-01-16 17:31:21',2,'Yangcl',1),(30,'www.china.com','中国政府','',1,NULL,'2020-01-17 09:37:48',2,'Yangcl','2020-01-17 09:37:48',2,'Yangcl',1),(31,'www.OSchain.com','OS-Chain','',1,NULL,'2020-01-17 09:38:19',2,'Yangcl','2020-01-17 09:38:19',2,'Yangcl',1),(32,'www.power-matrix.shop','能量矩阵','',1,NULL,'2020-01-17 09:38:45',2,'Yangcl','2020-01-17 09:38:45',2,'Yangcl',1),(33,'www.taobao.com.cn','淘宝','',1,NULL,'2020-01-17 09:39:07',2,'Yangcl','2020-01-17 09:39:07',2,'Yangcl',1),(34,'www.nanhai.com','南海控股','',1,NULL,'2020-01-17 09:39:37',2,'Yangcl','2020-01-17 09:39:37',2,'Yangcl',1);

/*Table structure for table `ac_request_info` */

DROP TABLE IF EXISTS `ac_request_info`;

CREATE TABLE `ac_request_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '请求开放接口的组织机构信息表|字典表-缓存化',
  `organization` varchar(50) DEFAULT '' COMMENT '组织机构名称',
  `key` varchar(52) DEFAULT '' COMMENT '组织机构标识',
  `value` varchar(52) DEFAULT '' COMMENT '组织结构秘钥',
  `atype` varchar(8) DEFAULT 'private' COMMENT '请求权限  private:私有 即公司内部使用的接口| public:公开，即开放给第三方的接口',
  `flag` int(2) DEFAULT '1' COMMENT '启用1 禁用 0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `ac_request_info` */

insert  into `ac_request_info`(`id`,`organization`,`key`,`value`,`atype`,`flag`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values (1,'电商中台','133C9C129D53','6DFA608D49324E47A5D69A13523BDFDA','private',1,'2018-09-28 10:29:51',1,'admin','2019-07-22 10:25:48',2,'Yangcl',1),(9,'developer-private','133C9CB27DA0','FD4007DB87B245EEACA7DAD5D4A1CECD','private',1,'2019-07-22 14:18:13',1,'Yangcl','2019-07-22 14:18:13',1,'Yangcl',1);

/*Table structure for table `ac_request_open_api` */

DROP TABLE IF EXISTS `ac_request_open_api`;

CREATE TABLE `ac_request_open_api` (
  `id` bigint(20) NOT NULL COMMENT 'open-api与第三方请求者关联信息',
  `ac_request_info_id` bigint(20) DEFAULT NULL,
  `ac_api_info_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `ac_request_open_api` */

/*Table structure for table `job_exec_log` */

DROP TABLE IF EXISTS `job_exec_log`;

CREATE TABLE `job_exec_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '定时任务执行记录表',
  `job_name` varchar(52) DEFAULT '' COMMENT 'sys_job表id',
  `job_title` varchar(52) DEFAULT '' COMMENT '任务标题',
  `status` varchar(16) DEFAULT '''success''' COMMENT '是否执行成功 success | error | exception；分别代表成功、失败和异常',
  `ip` varchar(52) DEFAULT '' COMMENT '主机地址',
  `run_group_id` bigint(20) DEFAULT '0' COMMENT '运行组id',
  `run_group_name` varchar(20) DEFAULT '' COMMENT '运行组名称',
  `begin_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始执行时间',
  `end_time` datetime DEFAULT NULL COMMENT '执行完成时间',
  `remark` longtext COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=213 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `job_exec_log` */

/*Table structure for table `job_group` */

DROP TABLE IF EXISTS `job_group`;

CREATE TABLE `job_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '定时任务分组字典表 - 缓存化处理',
  `group_name` varchar(25) DEFAULT NULL COMMENT '分组名称，如：matrix-job-test|验证唯一性',
  `ip` varchar(20) DEFAULT NULL COMMENT '该分组所在主机IP|逗号分隔',
  `remark` text COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '1' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT 'admin' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '1' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT 'admin' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `job_group` */

insert  into `job_group`(`id`,`group_name`,`ip`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values (1,'matrix-quartz-test','10.12.52.41','matrix-quartz-test','2018-08-26 18:09:47',1,'admin','2019-07-29 19:55:40',2,'Yangcl',1);

/*Table structure for table `job_info` */

DROP TABLE IF EXISTS `job_info`;

CREATE TABLE `job_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(52) NOT NULL COMMENT '定时任务名称-取uuid做唯一标记',
  `job_title` varchar(52) NOT NULL DEFAULT '' COMMENT '任务标题',
  `job_class` varchar(52) NOT NULL DEFAULT '' COMMENT '任务类名称',
  `job_triger` varchar(52) NOT NULL DEFAULT '' COMMENT '定时周期',
  `run_group_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '运行组|job_group表ID字段',
  `begin_time` datetime DEFAULT NULL COMMENT '开始执行时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束执行时间',
  `next_time` datetime DEFAULT NULL COMMENT '下一次执行时间',
  `pause` int(2) DEFAULT '0' COMMENT '定时任务是否暂停 0否|1是',
  `lock_key` varchar(52) DEFAULT '' COMMENT '分布式锁前缀的key|唯一',
  `expire_time` int(11) DEFAULT '60' COMMENT '默认锁的有效时间(m 秒)|redis中的过期时间',
  `time_out` int(11) DEFAULT '200' COMMENT '默认请求锁的超时时间(ms 毫秒)',
  `job_list` varchar(512) DEFAULT '' COMMENT '触发其他定时任务|保存定时任务名称(uuid)，逗号分隔|不可关联triger_type=1的任务',
  `triger_type` int(2) DEFAULT '1' COMMENT 'Scheduler中轮询状态的任务|1是 2否|2这种任务不会加入到scheduler中触发式的执行,只会被默认调用',
  `log_type` int(2) DEFAULT '1' COMMENT '定时任务是否记录日志 1否 2是',
  `remark` varchar(450) DEFAULT '' COMMENT '备注',
  `max_exec_time` int(11) DEFAULT '0' COMMENT '最长执行秒数|0代表无限期执行',
  `concurrent_type` int(2) DEFAULT '0' COMMENT '是否允许并行启动|0不允许 1允许',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '1' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT 'admin' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '1' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT 'admin' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定时任务列表';

/*Data for the table `job_info` */

insert  into `job_info`(`id`,`job_name`,`job_title`,`job_class`,`job_triger`,`run_group_id`,`begin_time`,`end_time`,`next_time`,`pause`,`lock_key`,`expire_time`,`time_out`,`job_list`,`triger_type`,`log_type`,`remark`,`max_exec_time`,`concurrent_type`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values (1,'UUID-9829384-0213410-OOKEIPAOA','定时任务测试类','com.matrix.quartz.job.JobForTestOne','2 * * * * ?',1,'2018-12-20 14:43:02','2018-12-20 14:43:02','2018-12-20 14:44:02',1,'com.matrix.quartz.job.JobForTestOne',60,200,'UUID-9829384-0213410-OOKEIPAOB,UUID-9829384-0213410-OOKEIPAOC',1,2,'每2分钟执行一次',0,1,'2018-08-20 14:41:09',1,'admin','2019-07-31 09:34:24',2,'Yangcl',1),(2,'UUID-9829384-0213410-OOKEIPAOB','定时任务测试类','com.matrix.quartz.job.JobForTestTwo','2 * * * * ?',1,'2018-12-20 14:43:02','2018-12-20 14:43:02','2018-12-19 16:55:02',1,'com.matrix.quartz.job.JobForTestTwo',60,200,'',2,2,'',0,1,'2018-08-20 14:41:09',1,'admin','2019-05-17 17:48:04',1,'admin',1),(3,'UUID-9829384-0213410-OOKEIPAOC','定时任务测试类','com.matrix.quartz.job.JobForTestThree','2 * * * * ?',1,'2018-12-20 14:43:02','2018-12-20 14:43:02','2018-12-19 17:00:02',1,'com.matrix.quartz.job.JobForTestThree',60,200,'',2,2,'',0,1,'2018-08-20 14:41:09',1,'admin','2019-08-05 08:32:20',20072026234128,'XuTao',1);

/*Table structure for table `mc_organization` */

DROP TABLE IF EXISTS `mc_organization`;

CREATE TABLE `mc_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '组织机构表|部门是组织机构的最后一级',
  `cid` bigint(20) DEFAULT NULL COMMENT '客户id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  `name` varchar(50) DEFAULT NULL COMMENT '组织机构名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT 'type=1 则 parent_id=-1',
  `type` int(2) DEFAULT '2' COMMENT '1:总部/总公司/集团|2:区域/子集团/分公司|3:门店|4:部门',
  `platform` varchar(20) DEFAULT '' COMMENT '平台默认标识码|用以应对多个不同的平台和系统',
  `manager_id` bigint(20) DEFAULT NULL COMMENT '负责人',
  `manager_name` varchar(50) DEFAULT '' COMMENT '负责人姓名',
  `store_type` int(2) DEFAULT '0' COMMENT '门店类型：1加盟，2直营|type=3则此字段必填',
  `seqnum` int(6) DEFAULT '1' COMMENT '顺序码 同一层次显示顺序',
  `mobile` varchar(20) DEFAULT '' COMMENT '电话',
  `address` varchar(512) DEFAULT '' COMMENT '地址',
  `remark` varchar(512) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_organization` */

/*Table structure for table `mc_role` */

DROP TABLE IF EXISTS `mc_role`;

CREATE TABLE `mc_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cid` bigint(20) DEFAULT '0' COMMENT 'tc_shop_info表主键',
  `type` varchar(20) DEFAULT 'admin' COMMENT 'leader Leader后台创建的角色|admin 其他平台管理员创建的角色',
  `platform` varchar(20) DEFAULT 'P01' COMMENT '平台标识码',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(200) DEFAULT '' COMMENT '角色描述',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  `company_id` bigint(20) DEFAULT NULL COMMENT '公司id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=192 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_role` */

insert  into `mc_role`(`id`,`cid`,`type`,`platform`,`role_name`,`role_desc`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`,`company_id`) values (116,0,'leader','133C9CB27E18','administer','administer','','2019-07-16 10:44:25',1992,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1,NULL),(164,0,'leader','133C9CB27E18','开发示例','仅供测试','','2019-09-29 17:23:32',2,'Yangcl','2019-09-29 17:24:01',2,'Yangcl',1,NULL),(182,0,'leader','133C9CB27E18','4','4','','2019-11-19 09:32:00',2,'Yangcl','2019-11-19 09:32:00',2,'Yangcl',1,NULL),(183,0,'leader','133C9CB27E18','人','额','','2019-11-19 09:32:12',2,'Yangcl','2019-11-19 09:32:12',2,'Yangcl',1,NULL),(184,0,'leader','133C9CB27E18','1','1','','2019-11-19 12:19:07',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1,NULL),(185,0,'leader','133C9CB27E18','22','22','','2019-11-19 12:19:11',2,'Yangcl','2019-11-22 11:07:54',2,'Yangcl',1,NULL),(187,0,'leader','133C9CB27E18','5','5','','2019-11-19 12:19:21',2,'Yangcl','2019-11-19 12:19:21',2,'Yangcl',1,NULL),(188,0,'leader','133C9CB27E18','2','2','','2019-12-09 14:07:46',2,'Yangcl','2019-12-13 16:02:49',2,'Yangcl',1,NULL),(189,0,'leader','133C9CB27E18','2而威尔','3','','2019-12-09 14:08:00',2,'Yangcl','2019-12-12 21:22:43',2,'Yangcl',1,NULL),(190,0,'leader','133C9CB27E18','2344天为前提','闪电发货我','','2019-12-09 14:08:07',2,'Yangcl','2019-12-09 14:08:07',2,'Yangcl',1,NULL),(191,0,'leader','133C9CB27E18','温热二奥群无','24124','','2019-12-09 14:08:17',2,'Yangcl','2019-12-09 14:08:17',2,'Yangcl',1,NULL);

/*Table structure for table `mc_role_function` */

DROP TABLE IF EXISTS `mc_role_function`;

CREATE TABLE `mc_role_function` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'zid',
  `mc_role_id` bigint(20) DEFAULT NULL COMMENT 'mc_role表主键',
  `mc_sys_function_id` bigint(20) DEFAULT NULL COMMENT 'mc_sys_function表主键',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19298 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_role_function` */

insert  into `mc_role_function`(`id`,`mc_role_id`,`mc_sys_function_id`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values (18195,164,75,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18196,164,77,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18197,164,250,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18198,164,251,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18199,164,252,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18200,164,79,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18201,164,83,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18202,164,167,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18203,164,308,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18204,164,126,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18205,164,171,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18206,164,192,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18207,164,187,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18208,164,208,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18209,164,127,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18210,164,309,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18211,164,172,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18212,164,310,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18213,164,193,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18214,164,257,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18215,164,188,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18216,164,258,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18217,164,209,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18218,164,210,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18219,164,211,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18220,164,212,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18221,164,213,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18222,164,214,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18223,164,215,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18224,164,259,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18225,164,260,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18226,164,80,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18227,164,84,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18228,164,261,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18229,164,128,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18230,164,227,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18231,164,249,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18232,164,325,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18233,164,326,'','2019-09-29 17:23:45',2,'Yangcl','2019-09-29 17:23:45',2,'Yangcl',1),(18333,184,75,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18334,184,77,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18335,184,250,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18336,184,251,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18337,184,252,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18338,184,79,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18339,184,83,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18340,184,167,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18341,184,308,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18342,184,126,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18343,184,171,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18344,184,192,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18345,184,187,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18346,184,208,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18347,184,127,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18348,184,309,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18349,184,172,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18350,184,310,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18351,184,193,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18352,184,188,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18353,184,258,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18354,184,257,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18355,184,209,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18356,184,210,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18357,184,211,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18358,184,212,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18359,184,213,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18360,184,214,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18361,184,215,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18362,184,259,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18363,184,260,'','2019-11-21 09:58:03',2,'Yangcl','2019-11-21 09:58:03',2,'Yangcl',1),(18701,189,75,'','2019-12-12 21:22:43',2,'Yangcl','2019-12-12 21:22:43',2,'Yangcl',1),(18702,189,77,'','2019-12-12 21:22:43',2,'Yangcl','2019-12-12 21:22:43',2,'Yangcl',1),(18703,189,250,'','2019-12-12 21:22:43',2,'Yangcl','2019-12-12 21:22:43',2,'Yangcl',1),(18704,189,251,'','2019-12-12 21:22:43',2,'Yangcl','2019-12-12 21:22:43',2,'Yangcl',1),(18705,189,252,'','2019-12-12 21:22:43',2,'Yangcl','2019-12-12 21:22:43',2,'Yangcl',1),(18706,188,75,'','2019-12-13 16:02:49',2,'Yangcl','2019-12-13 16:02:49',2,'Yangcl',1),(18707,188,77,'','2019-12-13 16:02:49',2,'Yangcl','2019-12-13 16:02:49',2,'Yangcl',1),(18708,188,250,'','2019-12-13 16:02:49',2,'Yangcl','2019-12-13 16:02:49',2,'Yangcl',1),(18709,188,251,'','2019-12-13 16:02:49',2,'Yangcl','2019-12-13 16:02:49',2,'Yangcl',1),(18710,188,252,'','2019-12-13 16:02:49',2,'Yangcl','2019-12-13 16:02:49',2,'Yangcl',1),(19177,116,75,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19178,116,77,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19179,116,250,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19180,116,251,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19181,116,252,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19182,116,79,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19183,116,83,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19184,116,167,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19185,116,308,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19186,116,126,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19187,116,171,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19188,116,192,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19189,116,187,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19190,116,208,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19191,116,127,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19192,116,309,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19193,116,172,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19194,116,310,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19195,116,193,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19196,116,188,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19197,116,258,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19198,116,257,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19199,116,209,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19200,116,210,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19201,116,211,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19202,116,212,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19203,116,213,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19204,116,214,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19205,116,215,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19206,116,259,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19207,116,260,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19208,116,80,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19209,116,84,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19210,116,261,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19211,116,128,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19212,116,227,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19213,116,249,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19214,116,325,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19215,116,326,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19216,116,78,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19217,116,81,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19218,116,117,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19219,116,197,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19220,116,312,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19221,116,313,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19222,116,314,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19223,116,315,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19224,116,473,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19225,116,316,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19226,116,1071,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19227,116,1073,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19228,116,1074,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19229,116,317,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19230,116,1075,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19231,116,118,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19232,116,198,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19233,116,318,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19234,116,177,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19235,116,319,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19236,116,320,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19237,116,321,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19238,116,322,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19239,116,1072,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19240,116,119,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19241,116,253,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19242,116,254,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19243,116,1076,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19244,116,1077,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19245,116,1078,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19246,116,1079,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19247,116,1080,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19248,116,255,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19249,116,287,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19250,116,288,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19251,116,289,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19252,116,290,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19253,116,291,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19254,116,292,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19255,116,293,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19256,116,294,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19257,116,295,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19258,116,296,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19259,116,297,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19260,116,298,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19261,116,299,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19262,116,300,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19263,116,1081,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19264,116,323,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19265,116,1082,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19266,116,301,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19267,116,302,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19268,116,303,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19269,116,304,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19270,116,305,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19271,116,306,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19272,116,465,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19273,116,466,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19274,116,510,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19275,116,600,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19276,116,479,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19277,116,480,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19278,116,481,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19279,116,482,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19280,116,483,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19281,116,484,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19282,116,485,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19283,116,486,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19284,116,487,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19285,116,488,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19286,116,489,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19287,116,490,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19288,116,491,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19289,116,492,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19290,116,1060,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19291,116,493,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19292,116,494,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19293,116,495,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19294,116,496,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19295,116,497,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19296,116,498,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1),(19297,116,499,'','2020-01-07 18:12:54',2,'Yangcl','2020-01-07 18:12:54',2,'Yangcl',1);

/*Table structure for table `mc_sys_function` */

DROP TABLE IF EXISTS `mc_sys_function`;

CREATE TABLE `mc_sys_function` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'zid',
  `mc_seller_company_id` bigint(11) NOT NULL DEFAULT '1' COMMENT '1为平台本身|还有其他商户|mc_seller_company表主键|据此过滤权限树',
  `name` varchar(40) NOT NULL COMMENT '功能名称 导航栏与菜单栏所显示的名称',
  `parent_id` varchar(25) NOT NULL COMMENT '父节点。root为0 其下为:商户->导航栏->一级菜单栏->二级菜单栏->页面按钮',
  `seqnum` int(6) NOT NULL COMMENT '顺序码 同一层次显示顺序：导航栏 一级菜单栏 二级菜单栏 对应的显示顺序',
  `nav_type` int(6) NOT NULL COMMENT '-1 根节点 0 平台标记 1 横向导航栏|2 为1级菜单栏|3 2级菜单栏 |4 页面按钮|5 按钮内包含跳转页面(dialog或新页面)',
  `authorize` int(2) DEFAULT '0' COMMENT '用户与角色是否委托Leader创建。0:委托 1:不委托-由其他子系统来创建|nav_type=0此字段生效。',
  `platform` varchar(20) DEFAULT '' COMMENT '平台默认标识码|nav_type=0，此处为系统生成默认值',
  `style_class` varchar(152) DEFAULT '' COMMENT '此项针对一级菜单栏 如：inbox   <a href="#example" class="inbox">开发者快速入门</a>',
  `style_key` varchar(50) DEFAULT '' COMMENT '此项针对一级菜单栏 如：example  <ul id="example">',
  `func_url` varchar(255) DEFAULT '' COMMENT 'nav_type=3或5;标识为一个跳转页面 如：example/page_form_example.do',
  `ajax_btn_url` varchar(152) DEFAULT '' COMMENT 'nav_type=4时保存所请求的接口与本条记录中ele_value的值一一对应|虽然都是按钮但nav_type=5通常此处为空',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `btn_area` varchar(20) DEFAULT '' COMMENT '按钮节点所在页面的位置。10001：功能区域；10002：查询区域；10003：数据区域',
  `ele_value` varchar(50) DEFAULT '' COMMENT '按钮权限唯一标识',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1084 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_sys_function` */

insert  into `mc_sys_function`(`id`,`mc_seller_company_id`,`name`,`parent_id`,`seqnum`,`nav_type`,`authorize`,`platform`,`style_class`,`style_key`,`func_url`,`ajax_btn_url`,`remark`,`btn_area`,`ele_value`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values (1,1,'root：系统功能树','-1',1,-1,0,'','root','root','rool','','系统根节点','',NULL,'2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(75,1,'Leader','1',1,0,0,'133C9CB27E18','','','','','Leader底层管理系统','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(77,1,'开发示例','75',2,1,0,'133C9CB27E18','','','','','为系统开发这提供的示例','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(78,1,'矩阵系统配置','75',3,1,0,'133C9CB27E18','','','','','系统核心功能管理与控制','','','2018-07-28 16:42:40',1,'admin','2019-12-26 17:43:08',2,'Yangcl',1),(79,1,'开发者快速入门','77',2,2,0,'133C9CB27E18','editor','37c694131c304c2588c4b906567631b1','','','开发者快速入门','','','2018-07-28 16:42:40',1,'admin','2020-01-10 15:57:23',2,'Yangcl',1),(80,1,'实例样本','77',3,2,0,'133C9CB27E18','editor','0cf9aa57b07149d586cc8998af6cfe7d','','','实例样本','','','2018-07-28 16:42:40',1,'admin','2020-01-10 15:57:23',2,'Yangcl',1),(81,1,'系统用户相关','78',1,2,0,'133C9CB27E18','editor','b06962367f8640fcbf11d4bca147101b','','','系统用户相关','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(83,1,'添加信息示例','79',1,3,0,'133C9CB27E18','','','example/page_example_add_info.do','','添加信息示例|matrix-admin/jsp/example/addExample.jsp','','','2018-07-28 16:42:40',1,'admin','2020-01-10 16:25:16',2,'Yangcl',1),(84,1,'实际样本-A','80',1,3,0,'133C9CB27E18','','','example/page_example_a.do','','matrix-admin/src/main/webapp/jsp/example/reality/questionQuery.jsp','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(117,1,'系统用户列表','81',1,3,0,'133C9CB27E18','','','permissions/page_permissions_system_user_list.do','','leader/src/main/webapp/views/permission/user/system-user-list.jsp','','','2018-07-28 16:42:40',1,'admin','2019-12-11 14:51:18',2,'Yangcl',1),(118,1,'系统角色列表','81',2,3,0,'133C9CB27E18','','','permissions/page_permissions_system_role_list.do','','leader/src/main/webapp/views/permission/role/system-role-list.jsp','','','2018-07-28 16:42:40',1,'admin','2019-12-11 14:50:20',2,'Yangcl',1),(119,1,'系统功能列表','81',3,3,0,'133C9CB27E18','','','permissions/page_permissions_system_function.do','','leader/src/main/webapp/views/permission/func/system-func-tree.jsp','','','2018-07-28 16:42:40',1,'admin','2019-12-11 14:50:57',2,'Yangcl',1),(126,1,'Ajax 分页示例','79',2,3,0,'133C9CB27E18','','','example/page_example_ajax_form.do','','Ajax 分页示例|matrix-admin/jsp/example/ajaxFormExample.jsp','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(127,1,'Ajax 分页+弹出窗体分页 示例','79',3,3,0,'133C9CB27E18','','','example/page_example_ajax_form_dialog.do','','Ajax 分页+弹出窗体分页 示例|\r\nmatrix-admin/jsp/example/ajaxFormDialogExample.jsp','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(128,1,'实际样本-B','80',2,3,0,'133C9CB27E18','','','example/page_example_b.do','','matrix-admin/src/main/webapp/jsp/example/reality/validate.jsp','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(167,1,'添加','83',1,4,0,'133C9CB27E18','','','','ajax_btn_add_info','123123','10001','add_info_example:add','2018-07-28 16:42:40',1,'admin','2020-01-10 16:11:19',2,'Yangcl',1),(171,1,'查询','126',1,4,0,'133C9CB27E18','','','','','查询','10002','ajax_page_example:search','2018-07-28 16:42:40',1,'admin','2019-12-05 15:39:59',2,'Yangcl',1),(172,1,'查询','127',2,4,0,'133C9CB27E18','','','','','添加','10002','ajax_page_dialog_example:search','2018-07-28 16:42:40',1,'admin','2019-11-01 15:21:17',2,'Yangcl',1),(177,1,'添加','118',3,4,0,'133C9CB27E18','','','','ajax_btn_add_mc_role_only.do','sysrole/ajax_btn_add_mc_role_only.do','10001','system_role_list:add','2018-07-28 16:42:40',1,'admin','2019-12-11 14:00:38',2,'Yangcl',1),(187,1,'删除','126',3,4,0,'133C9CB27E18','','','','','删除','10003','ajax_page_example:delete','2018-07-28 16:42:40',1,'admin','2019-11-08 15:58:28',2,'Yangcl',1),(188,1,'修改','127',5,5,0,'133C9CB27E18','','','asdfasdf','','修改','10003','ajax_page_dialog_example:edit','2018-07-28 16:42:40',1,'admin','2019-11-01 15:21:17',2,'Yangcl',1),(192,1,'重置','126',2,4,0,'133C9CB27E18','','','','','重置','10002','ajax_page_example:reset','2018-07-28 16:42:40',1,'admin','2019-11-08 15:58:28',2,'Yangcl',1),(193,1,'重置','127',4,4,0,'133C9CB27E18','','','','','查看','10002','ajax_page_dialog_example:reset','2018-07-28 16:42:40',1,'admin','2019-11-01 15:21:17',2,'Yangcl',1),(197,1,'查询','117',1,4,0,'133C9CB27E18','','','','ajax_system_user_list.do','userInfo/ajax_system_user_list.do','10001','system_user_list:search','2018-07-28 16:42:40',1,'admin','2019-12-17 17:51:12',2,'Yangcl',1),(198,1,'查询','118',1,4,0,'133C9CB27E18','','','','ajax_system_role_list.do','sysrole/ajax_system_role_list.do','10001','system_role_list:search','2018-07-28 16:42:40',1,'admin','2019-12-11 13:59:35',2,'Yangcl',1),(208,1,'修改','126',4,4,0,'133C9CB27E18','','','','','修改','10003','ajax_page_example:edit','2018-07-28 16:42:40',1,'admin','2019-11-08 15:58:28',2,'Yangcl',1),(209,1,'自定义 alert confirm note 示例','79',4,3,0,'133C9CB27E18','','','example/page_example_alert.do','','自定义 alert confirm note 示例|matrix-admin/jsp/example/alertExample.jsp','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(210,1,'基本 Alert','209',1,4,0,'133C9CB27E18','','','','','基本 Alert','10001','custom_dialog_example:alert','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(211,1,'确认对话框 confirm','209',2,4,0,'133C9CB27E18','','','','','确认对话框 confirm','10001','custom_dialog_example:confirm','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(212,1,'输入对话框 prompt','209',3,4,0,'133C9CB27E18','','','','','输入对话框 prompt','10001','custom_dialog_example:prompt','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(213,1,'alert 支持html标签','209',4,4,0,'133C9CB27E18','','','','','alert 支持html标签','10001','custom_dialog_example:html','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(214,1,'弹出层示例','79',5,3,0,'133C9CB27E18','','','example/page_example_block_ui.do','','介绍系统常见的弹出层|matrix-admin/jsp/example/blockUiPageExample.jsp','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(215,1,'添加弹层示例','214',1,4,0,'133C9CB27E18','','','','','开头语','10001','dialog_example:add','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(227,1,'ueditor编辑器示例','80',4,3,0,'133C9CB27E18','','','example/page_example_ueditor.do','','matrix-admin/src/main/webapp/jsp/example/ueditorExample.jsp','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(249,1,'图片|文件上传','80',5,3,0,'133C9CB27E18','','','example/page_example_file_upload.do','','matrix-admin/src/main/webapp/jsp/example/pageExampleFileUpload.jsp','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(250,1,'项目介绍','77',1,2,0,'133C9CB27E18','editor','304180247f1044f6a9a56a78a407fdc3','','','项目介绍','','','2018-07-28 16:42:40',1,'admin','2020-01-10 15:57:23',2,'Yangcl',1),(251,1,'开发者规约-java','250',1,3,0,'133C9CB27E18','','','example/page_developer_specification.do','','开发者规约','','','2018-07-28 16:42:40',1,'admin','2019-11-08 10:53:42',2,'Yangcl',1),(252,1,'开发者规约-javascript','250',2,3,0,'133C9CB27E18','','','example/page_developer_specification_js.do','','开发者规约-javascript','','','2018-07-28 16:42:40',1,'admin','2019-12-11 11:33:25',2,'Yangcl',1),(253,1,'系统工具','78',2,2,0,'133C9CB27E18','editor','a6a534ca38384f8787a8306ea0f23651','','','系统配置与查询、缓存操作/查看、部署节点列表等','','','2018-07-28 16:42:40',1,'admin','2019-12-23 10:50:07',2,'Yangcl',1),(254,1,'缓存查看','253',1,3,0,'133C9CB27E18','','','cache/page_cache_system_cache.do','','查看系统中的缓存信息|views\\system\\cache\\system-cache.jsp','','','2018-07-28 16:42:40',1,'admin','2019-12-23 14:46:35',2,'Yangcl',1),(255,1,'缓存重置','253',2,3,0,'133C9CB27E18','','','cache/page_cache_reload.do','','缓存重置，用户的、字典的、其他的|/matrix-admin/jsp/syssetting/cache/cacheReload.jsp','','','2018-07-28 16:42:40',1,'admin','2019-12-23 11:11:32',2,'Yangcl',1),(257,1,'删除','127',7,4,0,'133C9CB27E18','','','','','删除','10003','ajax_page_dialog_example:delete','2018-07-28 16:42:40',1,'admin','2019-11-01 15:21:17',2,'Yangcl',1),(258,1,'弹窗分页','127',6,4,0,'133C9CB27E18','','','','','弹窗分页','10003','ajax_page_dialog_example:dialog','2018-07-28 16:42:40',1,'admin','2019-11-01 15:21:17',2,'Yangcl',1),(259,1,'自定义滚动条示例-ul-列表','214',2,4,0,'133C9CB27E18','','','','','自定义滚动条示例-ul-列表','10001','dialog_example:slim_scroll','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(260,1,'自定义滚动条示例-tree','214',3,4,0,'133C9CB27E18','','','','','自定义滚动条示例-tree','10001','dialog_example:slim_scroll:tree','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(261,1,'搜索日志信息','84',1,4,0,'133C9CB27E18','','','','','搜索日志信息','10001','btn-55ee0a123a05484d8cc22235b709c2ff','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(287,1,'系统开放接口','78',3,2,0,'133C9CB27E18','editor','d27673e123c9447f8c789ab260b3adb2','','','包含公司内部接口、开放给第三方的接口等。由这里进行统一管理','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(288,1,'api所属项目','287',1,3,0,'133C9CB27E18','','','apicenter/page_apicenter_api_project_list.do','','api所属项目-应对可能出现的多项目|ac_api_project表的数据|\r\nviews\\api\\project\\api-project-list.jsp','','','2018-07-28 16:42:40',1,'admin','2019-12-26 17:28:04',2,'Yangcl',1),(289,1,'查 询','288',1,4,0,'133C9CB27E18','','','','ajax_api_project_list.do','apicenter/ajax_api_project_list.do\r\n查 询','10002','api_project_list:search','2018-07-28 16:42:40',1,'admin','2019-12-27 13:50:12',2,'Yangcl',1),(290,1,'重 置','288',2,4,0,'133C9CB27E18','','','','ajax_api_project_list.do','apicenter/ajax_api_project_list.do\r\n重 置','10002','api_project_list:reset','2018-07-28 16:42:40',1,'admin','2019-12-27 13:50:41',2,'Yangcl',1),(291,1,'添加','288',3,4,0,'133C9CB27E18','','','','ajax_btn_api_project_add.do','添加 apicenter/ajax_btn_api_project_add.do','10001','api_project_list:add','2018-07-28 16:42:40',1,'admin','2019-12-27 13:51:37',2,'Yangcl',1),(292,1,'删除','288',4,4,0,'133C9CB27E18','','','','ajax_btn_api_project_delete.do','删除 apicenter/ajax_btn_api_project_delete.do','10003','api_project_list:delete','2018-07-28 16:42:40',1,'admin','2019-12-27 13:53:05',2,'Yangcl',1),(293,1,'修改','288',5,4,0,'133C9CB27E18','','','','ajax_btn_api_project_edit.do','修改 apicenter/ajax_btn_api_project_edit.do','10003','api_project_list:edit','2018-07-28 16:42:40',1,'admin','2019-12-27 13:52:09',2,'Yangcl',1),(294,1,'跨域白名单','287',2,3,0,'133C9CB27E18','','','apicenter/page_apicenter_include_domain_list.do','','记录系统允许跨域的域名|\r\nviews\\api\\domain\\api-include-domain-list.jsp','','','2018-07-28 16:42:40',1,'admin','2019-12-27 17:30:29',2,'Yangcl',1),(295,1,'查询','294',1,4,0,'133C9CB27E18','','','','','查询','10002','include_domain_list:search','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(296,1,'重置','294',2,4,0,'133C9CB27E18','','','','','重置','10002','include_domain_list:reset','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(297,1,'添加','294',3,4,0,'133C9CB27E18','','','','ajax_btn_api_domain_add.do','添加 apicenter/ajax_btn_api_domain_add.do','10001','include_domain_list:add','2018-07-28 16:42:40',1,'admin','2019-12-27 17:31:21',2,'Yangcl',1),(298,1,'删除','294',4,4,0,'133C9CB27E18','','','','ajax_btn_api_domain_delete.do','删除 apicenter/ajax_btn_api_domain_delete.do','10001','include_domain_list:delete','2018-07-28 16:42:40',1,'admin','2020-01-07 11:21:39',2,'Yangcl',1),(299,1,'修改','294',5,4,0,'133C9CB27E18','','','','ajax_btn_api_domain_edit.do','修改 apicenter/ajax_btn_api_domain_edit.do','10001','include_domain_list:edit','2018-07-28 16:42:40',1,'admin','2020-01-07 09:55:22',2,'Yangcl',1),(300,1,'api信息树','287',3,3,0,'133C9CB27E18','','','apicenter/page_apicenter_api_tree.do','','api信息树|views\\api\\info\\api-tree.jsp','','','2018-07-28 16:42:40',1,'admin','2020-01-07 16:32:06',2,'Yangcl',1),(301,1,'请求者信息','287',4,3,0,'133C9CB27E18','','','apicenter/page_apicenter_request_info.do','','请求者信息维护页面|\r\nmatrix-admin/src/main/webapp/jsp/api/request/api-request-info-list.jsp','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(302,1,'查 询','301',1,4,0,'133C9CB27E18','','','','','查 询','10002','api_requester_info:search','2018-07-28 16:42:40',1,'admin','2020-01-07 16:38:40',2,'Yangcl',1),(303,1,'重 置','301',2,4,0,'133C9CB27E18','','','','','重 置','10002','api_requester_info:reset','2018-07-28 16:42:40',1,'admin','2020-01-07 16:38:40',2,'Yangcl',1),(304,1,'添加','301',3,4,0,'133C9CB27E18','','','','','添加','10001','api_requester_info:add','2018-07-28 16:42:40',1,'admin','2020-01-07 16:38:40',2,'Yangcl',1),(305,1,'启用|禁用','301',4,4,0,'133C9CB27E18','','','','','删除','10003','api_requester_info:delete','2018-07-28 16:42:40',1,'admin','2020-01-07 16:38:40',2,'Yangcl',1),(306,1,'修改','301',5,4,0,'133C9CB27E18','','','','','修改','10003','api_requester_info:edit','2018-07-28 16:42:40',1,'admin','2020-01-07 16:38:40',2,'Yangcl',1),(308,1,'删除','83',2,4,0,'133C9CB27E18','','','','ajax_btn_delete_info','删除按钮','10001','add_info_example:delete','2018-07-28 16:42:40',1,'admin','2019-12-10 15:38:52',2,'Yangcl',1),(309,1,'弹窗-删除','127',1,4,0,'133C9CB27E18','','','','','弹窗中的删除按钮','10001','ajax_page_dialog_example:dialog:delete','2018-07-28 16:42:40',1,'admin','2019-11-01 15:21:17',2,'Yangcl',1),(310,1,'弹窗-编辑','127',3,4,0,'133C9CB27E18','','','','','弹窗中的编辑按钮','10001','ajax_page_dialog_example:dialog:edit','2018-07-28 16:42:40',1,'admin','2019-11-01 15:21:17',2,'Yangcl',1),(312,1,'重置','117',2,4,0,'133C9CB27E18','','','','ajax_system_user_list.do','userInfo/ajax_system_user_list.do','10002','system_user_list:reset','2018-07-28 16:42:40',1,'admin','2019-12-17 17:51:12',2,'Yangcl',1),(313,1,'添加','117',3,4,0,'133C9CB27E18','','','','ajax_btn_add_system_user.do','userInfo/ajax_btn_add_system_user.do','10002','system_user_list:add','2018-07-28 16:42:40',1,'admin','2019-12-17 17:51:12',2,'Yangcl',1),(314,1,'删除','117',4,4,0,'133C9CB27E18','','','','ajax_btn_delete_system_user.do','userInfo/ajax_btn_delete_system_user.do','10003','system_user_list:delete','2018-07-28 16:42:40',1,'admin','2019-12-17 17:51:12',2,'Yangcl',1),(315,1,'修改','117',5,4,0,'133C9CB27E18','','','','ajax_btn_edit_sys_user.do','userInfo/ajax_btn_edit_sys_user.do','10003','system_user_list:edit','2018-07-28 16:42:40',1,'admin','2019-12-17 17:51:12',2,'Yangcl',1),(316,1,'用户角色','117',7,5,0,'133C9CB27E18','','','dialog_permissions_system_role_list.do','','permissions/dialog_permissions_system_role_list.do','10001','system_user_list:user_role','2018-07-28 16:42:40',1,'admin','2019-12-17 17:51:12',2,'Yangcl',1),(317,1,'系统角色列表-弹框-分配','117',11,4,0,'133C9CB27E18','','','','ajax_btn_allot_user_role_submit.do','sysrole/ajax_btn_allot_user_role_submit.do\r\n给指定用户分配一个角色','10003','system_user_list:allot_submit','2018-07-28 16:42:40',1,'admin','2019-12-17 17:51:12',2,'Yangcl',1),(318,1,'重置','118',2,4,0,'133C9CB27E18','','','','ajax_system_role_list.do','sysrole/ajax_system_role_list.do','10001','system_role_list:reset','2018-07-28 16:42:40',1,'admin','2019-12-11 13:59:55',2,'Yangcl',1),(319,1,'角色功能','118',4,4,0,'133C9CB27E18','','','','default','default：仅做权限控制，不发起ajax请求','10001','system_role_list:role_function','2018-07-28 16:42:40',1,'admin','2019-12-11 14:09:52',2,'Yangcl',1),(320,1,'修改','118',5,4,0,'133C9CB27E18','','','','ajax_btn_edit_mc_role_only.do','sysrole/ajax_btn_edit_mc_role_only.do','10001','system_role_list:edit','2018-07-28 16:42:40',1,'admin','2019-12-11 14:01:17',2,'Yangcl',1),(321,1,'删除','118',6,4,0,'133C9CB27E18','','','','ajax_btn_delete_mc_role.do','sysrole/ajax_btn_delete_mc_role.do','10001','system_role_list:delete','2018-07-28 16:42:40',1,'admin','2019-12-11 14:01:41',2,'Yangcl',1),(322,1,'角色功能弹窗-提交','118',7,4,0,'133C9CB27E18','','','','ajax_btn_edit_mc_role.do','sysrole/ajax_btn_edit_mc_role.do','10001','system_role_list:dialog_submit','2018-07-28 16:42:40',1,'admin','2019-12-11 14:13:08',2,'Yangcl',1),(323,1,'保存|修改','300',1,4,0,'133C9CB27E18','','','','default','保存|修改。页面展示为【保存】按钮，但针对添加行为对应apicenter/ajax_api_info_add.do，针对修改行为对应apicenter/ajax_api_info_edit.do。由于同一个按钮对应两种Controller请求路径，所以【按钮请求路径】初设置为default','10001','api_tree:submit','2018-07-28 16:42:40',1,'admin','2020-01-07 16:53:39',2,'Yangcl',1),(325,1,'完整开发流程','77',4,2,0,'133C9CB27E18','editor','51e91e7ba11d4a61918d100dd124d9f0','','','贴近实际业务来展示一个完整的开发流程','','','2018-07-28 16:42:40',1,'admin','2020-01-10 15:57:23',2,'Yangcl',1),(326,1,'楼盘信息开发实例','325',1,3,0,'133C9CB27E18','','','demos/page_demo_landed_property.do','','复制即所得，抄永远比写来的快。但请小心别抄错了。。','','','2018-07-28 16:42:40',1,'admin','2018-07-28 16:42:44',1,'admin',1),(465,1,'dubbo-admin','78',4,2,0,'133C9CB27E18','editor','17f2b7b4b60f455e8fdcbb3a6e37fe4b','','','','','','2018-11-04 13:02:19',1990,'jinghao.sun','2018-11-04 13:02:19',1990,'jinghao.sun',1),(466,1,'Dubbo应用服务列表','465',1,3,0,'133C9CB27E18','','','application/page_application_dubbo_project_list.do','',' @description: Dubbo应用服务列表|Dubbo项目名称列表','','','2018-11-04 13:06:32',1990,'jinghao.sun','2018-11-04 13:25:59',1990,'jinghao.sun',1),(473,1,'重置密码','117',6,4,0,'133C9CB27E18','','','','ajax_btn_password_reset.do','userInfo/ajax_btn_password_reset.do','10003','system_user_list:password_reset','2018-11-14 16:28:31',1992,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),(479,1,'分布式定时任务','78',5,2,0,'133C9CB27E18','editor','4930eab924ee4e10ad016882bb7772a3','','','分布式定时任务','','','2018-12-20 17:42:07',1992,'Yangcl','2018-12-20 17:42:07',1992,'Yangcl',1),(480,1,'定时任务列表','479',1,3,0,'133C9CB27E18','','','quartz/page_quartz_job_info_list.do','','定时任务列表','','','2018-12-20 17:43:15',1992,'Yangcl','2018-12-20 17:43:15',1992,'Yangcl',1),(481,1,'添加','480',1,4,0,'133C9CB27E18','','','','','job_info_list:search','10001','job_info_list:add','2018-12-20 17:59:41',1992,'Yangcl','2018-12-20 18:05:47',1992,'Yangcl',1),(482,1,'重 置','480',2,4,0,'133C9CB27E18','','','','','job_info_list:reset','10001','job_info_list:reset','2018-12-20 18:00:39',1992,'Yangcl','2018-12-20 18:00:39',1992,'Yangcl',1),(483,1,'查 询','480',3,4,0,'133C9CB27E18','','','','','job_info_list:search','10001','job_info_list:search','2018-12-20 18:01:34',1992,'Yangcl','2018-12-20 18:01:34',1992,'Yangcl',1),(484,1,'删除','480',4,4,0,'133C9CB27E18','','','','','job_info_list:delete','10003','job_info_list:delete','2018-12-20 20:48:17',1992,'Yangcl','2018-12-20 20:48:17',1992,'Yangcl',1),(485,1,'修改','480',5,4,0,'133C9CB27E18','','','','','job_info_list:edit','10003','job_info_list:edit','2018-12-20 20:48:46',1992,'Yangcl','2018-12-20 20:48:46',1992,'Yangcl',1),(486,1,'手动触发','480',6,4,0,'133C9CB27E18','','','','','job_info_list:run','10003','job_info_list:run','2018-12-20 20:49:11',1992,'Yangcl','2018-12-20 21:00:13',1992,'Yangcl',1),(487,1,'执行记录','480',7,4,0,'133C9CB27E18','','','','','job_info_list:run_log','10003','job_info_list:run_log','2018-12-20 20:59:48',1992,'Yangcl','2018-12-20 20:59:48',1992,'Yangcl',1),(488,1,'暂停','480',8,4,0,'133C9CB27E18','','','','','暂停一个定时任务','10003','job_info_list:pause','2018-12-21 14:22:49',1992,'Yangcl','2018-12-21 14:22:49',1992,'Yangcl',1),(489,1,'全部暂停','480',9,4,0,'133C9CB27E18','','','','','暂停全部定时任务','10002','job_info_list:pause_all','2018-12-21 14:24:33',1992,'Yangcl','2018-12-21 14:24:33',1992,'Yangcl',1),(490,1,'恢复','480',10,4,0,'133C9CB27E18','','','','','恢复一个指定的定时任务到运行状态','10003','job_info_list:resume','2018-12-21 14:25:52',1992,'Yangcl','2018-12-21 14:25:52',1992,'Yangcl',1),(491,1,'全部恢复','480',11,4,0,'133C9CB27E18','','','','','将全部定时任务恢复到运行状态','10002','job_info_list:resume_all','2018-12-21 14:26:59',1992,'Yangcl','2018-12-21 14:26:59',1992,'Yangcl',1),(492,1,'详情','480',12,4,0,'133C9CB27E18','','','','','定时任务详情','10003','job_info_list:detail','2018-12-25 14:21:25',1992,'Yangcl','2018-12-25 14:21:25',1992,'Yangcl',1),(493,1,'定时任务分组列表','479',2,3,0,'133C9CB27E18','','','quartz/page_quartz_job_group_list.do','','定时任务分组列表','','','2018-12-27 10:57:42',1992,'Yangcl','2018-12-27 10:57:42',1992,'Yangcl',1),(494,1,'添加','493',1,4,0,'133C9CB27E18','','','','','job_group_list:add','10001','job_group_list:add','2018-12-27 10:59:42',1992,'Yangcl','2018-12-27 10:59:42',1992,'Yangcl',1),(495,1,'重 置','493',2,4,0,'133C9CB27E18','','','','','job_group_list:reset','10001','job_group_list:reset','2018-12-27 11:00:05',1992,'Yangcl','2018-12-27 11:00:05',1992,'Yangcl',1),(496,1,'查 询','493',3,4,0,'133C9CB27E18','','','','','job_group_list:search','10001','job_group_list:search','2018-12-27 11:00:27',1992,'Yangcl','2018-12-27 11:00:27',1992,'Yangcl',1),(497,1,'删除','493',4,4,0,'133C9CB27E18','','','','','job_group_list:delete','10003','job_group_list:delete','2018-12-27 11:00:49',1992,'Yangcl','2018-12-27 11:01:21',1992,'Yangcl',1),(498,1,'修改','493',5,4,0,'133C9CB27E18','','','','','job_group_list:edit','10003','job_group_list:edit','2018-12-27 11:01:14',1992,'Yangcl','2018-12-27 11:01:14',1992,'Yangcl',1),(499,1,'定时任务日志列表','479',3,3,0,'133C9CB27E18','','','quartz/page_quartz_job_log_list.do','','定时任务日志列表','','','2018-12-29 17:00:37',1992,'Yangcl','2018-12-29 17:00:37',1992,'Yangcl',1),(510,1,'部署节点列表','465',2,3,0,'133C9CB27E18','','','route/page_route_dubbo_node_list.do','','Dubbo应用服务部署节点列表','','','2019-01-07 14:15:06',1992,'Yangcl','2019-01-07 14:15:06',1992,'Yangcl',1),(600,1,'sentinel控制台','465',3,3,0,'133C9CB27E18','','','application/page_goto_sentinel_dashboard.do','','','','','2019-06-13 16:35:50',1994,'wanghao','2019-06-13 16:35:50',1994,'wanghao',1),(1060,1,'手动','480',13,4,0,'133C9CB27E18','','','','','手动触发定时任务|立刻执行定时任务','10003','job_info_list:handling','2019-09-27 18:12:50',2,'Yangcl','2019-09-27 18:12:50',2,'Yangcl',1),(1071,1,'权限重置','117',8,4,0,'133C9CB27E18','','','','ajax_btn_user_cache_reload.do','sysrole/ajax_btn_user_cache_reload.do\r\n重置系统用户的所有信息包括：McSysFunc、McRole、McUserRole、UserInfoNp','10001','system_user_list:reload','2019-12-10 15:59:17',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),(1072,1,'角色功能弹窗-解绑','118',8,4,0,'133C9CB27E18','','','','ajax_btn_relieve_mc_role.do','sysrole/ajax_btn_relieve_mc_role.do','10001','system_role_list:dialog_cancel','2019-12-11 14:12:31',2,'Yangcl','2019-12-11 14:55:07',2,'Yangcl',1),(1073,1,'系统角色列表-弹框-查询','117',9,4,0,'133C9CB27E18','','','','ajax_user_role_list.do','sysrole/ajax_user_role_list.do','10002','system_user_list:dialog_search','2019-12-13 17:08:35',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),(1074,1,'系统角色列表-弹框-重置','117',10,4,0,'133C9CB27E18','','','','ajax_user_role_list.do','sysrole/ajax_user_role_list.do','10002','system_user_list:dialog_reset','2019-12-13 17:20:38',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),(1075,1,'系统角色列表-弹框-移除','117',12,4,0,'133C9CB27E18','','','','ajax_btn_allot_user_role_remove.do','sysrole/ajax_btn_allot_user_role_remove.do\r\n解除角色绑定，同时删除缓存','10003','system_user_list:allot_remove','2019-12-17 17:50:48',2,'Yangcl','2019-12-17 17:51:12',2,'Yangcl',1),(1076,1,'批量删除缓存','254',1,4,0,'133C9CB27E18','','','','ajax_btn_batch_delete.do','cache/ajax_btn_batch_delete.do\r\n批量删除缓存','10001','system_cache:batch_delete','2019-12-23 11:15:27',2,'Yangcl','2019-12-23 11:45:02',2,'Yangcl',1),(1077,1,'设置缓存','254',2,4,0,'133C9CB27E18','','','','ajax_btn_reset_cache.do','cache/ajax_btn_reset_cache.do\r\n过期时间半小时','10001','system_cache:reset','2019-12-23 11:24:29',2,'Yangcl','2019-12-23 11:24:29',2,'Yangcl',1),(1078,1,'设置缓存(永久)','254',3,4,0,'133C9CB27E18','','','','ajax_btn_reset_cache_forever.do','cache/ajax_btn_reset_cache_forever.do\r\n永久设置缓存','10001','system_cache:reset_forever','2019-12-23 11:26:13',2,'Yangcl','2019-12-23 11:26:13',2,'Yangcl',1),(1079,1,'删除缓存','254',4,4,0,'133C9CB27E18','','','','ajax_btn_delete_cache.do','cache/ajax_btn_delete_cache.do\r\n批量删除缓存','10001','system_cache:delete_cache','2019-12-23 11:38:05',2,'Yangcl','2019-12-23 11:38:05',2,'Yangcl',1),(1080,1,'获取缓存','254',5,4,0,'133C9CB27E18','','','','ajax_btn_get_cache.do','cache/ajax_btn_get_cache.do','10001','system_cache:get_cache','2019-12-23 11:40:22',2,'Yangcl','2019-12-23 11:40:22',2,'Yangcl',1),(1081,1,'测试','300',1,4,0,'133C9CB27E18','','','','defalut','API树形结构列表|测试按钮。点击按钮将打开一个弹框，用于测试接口数据','10001','api_tree:test','2020-01-07 16:55:42',2,'Yangcl','2020-01-07 16:55:42',2,'Yangcl',1),(1082,1,'删除','300',2,4,0,'133C9CB27E18','','','','ajax_btn_api_remove.do','删除一个api节点 apicenter/ajax_btn_api_remove.do','10001','api_tree:remove','2020-01-07 18:12:43',2,'Yangcl','2020-01-07 18:12:43',2,'Yangcl',1);

/*Table structure for table `mc_user_info` */

DROP TABLE IF EXISTS `mc_user_info`;

CREATE TABLE `mc_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'zid',
  `cid` bigint(20) DEFAULT '0' COMMENT 'tc_shop_info表主键,即custom id | Leader后台创建的用户默认为0 | 商家端均为-1代表关联多个店铺;一个店铺则包含具体店铺id',
  `tenant_info_id` bigint(20) DEFAULT '0' COMMENT 'tc_tenant_info表id,如果cid=-1则代表管理多个店铺,其所在的店铺的用户列表不显示这条记录;但会关联租户主键',
  `user_name` varchar(25) DEFAULT '' COMMENT '用户姓名',
  `password` varchar(45) DEFAULT '' COMMENT '密码',
  `user_code` varchar(20) DEFAULT '' COMMENT '员工编号|type=user 则此字段生效',
  `mc_organization_id` bigint(20) DEFAULT '0' COMMENT '组织机构id|type=user 则此字段生效|默认0 代表无归属机构',
  `type` varchar(20) DEFAULT 'user' COMMENT 'leader Leader后台用户|admin 其他平台管理员|user其他平台用户',
  `platform` varchar(16) DEFAULT 'P01' COMMENT '平台标识码',
  `flag` int(2) DEFAULT '1' COMMENT '启用状态；1启用 2停用|type=user 则此字段生效',
  `idcard` varchar(20) DEFAULT '' COMMENT '身份证号',
  `sex` int(6) DEFAULT '2' COMMENT '性别 1：男 2：女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(25) DEFAULT NULL COMMENT 'qq号码',
  `pic_url` varchar(256) DEFAULT '' COMMENT '用户头像',
  `page_css` varchar(40) DEFAULT 'default' COMMENT '后台页面css样式',
  `remark` longtext COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20072026234174 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='API表格';

/*Data for the table `mc_user_info` */

insert  into `mc_user_info`(`id`,`cid`,`tenant_info_id`,`user_name`,`password`,`user_code`,`mc_organization_id`,`type`,`platform`,`flag`,`idcard`,`sex`,`birthday`,`mobile`,`email`,`qq`,`pic_url`,`page_css`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values (1,0,0,'admin','0988a08e0e7cdaf2b395133b0fbba289','',0,'leader','133C9CB27E18',NULL,'4677',1,NULL,'13511112221','admin-y@300.cn',NULL,'','default','Leader平台超级系统管理员','2018-07-28 23:30:08',1,'admin','2019-07-16 11:26:32',1,'admin',1),(2,0,0,'Yangcl','71fb5b225e96fc8ec99e8fe85e35b40a','',0,'leader','133C9CB27E18',NULL,'',1,NULL,'18514037761','yangchenglin@300.cn',NULL,'','default','杨成琳-Leader后台管理员','2018-09-25 17:50:21',1,'admin','2019-07-23 10:59:37',2,'Yangcl',1),(20072026234172,0,0,'SBYXQ','c9f5ae4e58d6e8df2b78b10066b64e35','',0,'leader','133C9CB27E18',1,'222222222222222222',2,NULL,'144444444444','SBXT@300.CN','22222222222222222222','','default','DSB','2019-12-05 11:07:41',2,'Yangcl','2020-01-17 15:35:55',2,'Yangcl',1);

/*Table structure for table `mc_user_info_organization` */

DROP TABLE IF EXISTS `mc_user_info_organization`;

CREATE TABLE `mc_user_info_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '平台用户与组织机构关联表|mc_user_info表 type=user,则此表存在记录',
  `cid` bigint(20) DEFAULT '0' COMMENT '客户id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  `mc_user_info_id` bigint(20) DEFAULT NULL,
  `mc_organization_id` bigint(20) DEFAULT NULL,
  `platform` varchar(20) DEFAULT NULL COMMENT '平台标识码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_user_info_organization` */

/*Table structure for table `mc_user_role` */

DROP TABLE IF EXISTS `mc_user_role`;

CREATE TABLE `mc_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'zid',
  `mc_user_id` bigint(20) DEFAULT NULL COMMENT 'mc_user_info 表 主键',
  `mc_role_id` bigint(20) DEFAULT NULL COMMENT 'mc_role表主键',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=514 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `mc_user_role` */

insert  into `mc_user_role`(`id`,`mc_user_id`,`mc_role_id`,`remark`,`create_time`,`create_user_id`,`create_user_name`,`update_time`,`update_user_id`,`update_user_name`,`delete_flag`) values (503,2,116,'','2019-09-27 18:13:07',2,'Yangcl','2019-09-27 18:13:07',2,'Yangcl',1),(504,1,116,'','2019-09-27 18:13:11',2,'Yangcl','2019-09-27 18:13:11',2,'Yangcl',1),(510,2,164,'','2019-09-29 17:40:52',2,'Yangcl','2019-09-29 17:40:52',2,'Yangcl',1);

/*Table structure for table `store_info` */

DROP TABLE IF EXISTS `store_info`;

CREATE TABLE `store_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '注册门店表id',
  `cid` bigint(20) DEFAULT '0' COMMENT '客户id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间(消费时间)',
  `create_user_id` bigint(20) DEFAULT '0' COMMENT '创建人id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建人姓名',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT '0' COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新者姓名',
  `delete_flag` int(2) DEFAULT '1' COMMENT '删除标记: 0删除|1未删除',
  `name` varchar(40) DEFAULT NULL COMMENT '门店名称',
  `mc_organization_id` bigint(20) DEFAULT NULL COMMENT '门店对应的mc_organization表的主键',
  `province_id` bigint(20) DEFAULT NULL COMMENT '门店省份id',
  `city_id` bigint(20) DEFAULT NULL COMMENT '门店城市id',
  `area_id` bigint(20) DEFAULT NULL COMMENT '门店区县id',
  `address` varchar(128) DEFAULT NULL COMMENT '门店详细地址',
  `phone` varchar(20) DEFAULT NULL COMMENT '门店联系电话',
  `star_level` smallint(2) DEFAULT '0' COMMENT '门店星级',
  `type` smallint(2) DEFAULT '0' COMMENT '1=加盟店 2直营店 3代理商 4连锁店 0其他',
  `status` smallint(2) DEFAULT '1' COMMENT '0 暂停营业 1正常运营',
  `remark` varchar(255) DEFAULT NULL COMMENT '暂停营业原因',
  `dict_vocation_info_id` bigint(20) DEFAULT NULL COMMENT '门店所属行业id',
  `longitude` varchar(52) DEFAULT NULL COMMENT '地理位置:经度',
  `latitude` varchar(52) DEFAULT NULL COMMENT '地理位置:纬度',
  `manager_id` bigint(20) DEFAULT NULL COMMENT '负责人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='注册门店表';

/*Data for the table `store_info` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
