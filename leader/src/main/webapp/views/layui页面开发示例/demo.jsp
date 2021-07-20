<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<!-- 
	此处示例代码参考
			permission/role/system-role-list.js
			permission/role/system-role-list.jsp
 -->




<html>
	<head>
		<meta charset="utf-8">
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$【开始替换你的代码 - 第1步】$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
		<title>demo demo demo demo demo demo demo</title>
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$【完成替换你的代码 - 第1步】$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<link rel="stylesheet" href="${layui}/layui/css/layui.css" media="all">
		<link rel="stylesheet" href="${layui}/style/admin.css" media="all">
		<link rel="stylesheet" href="${css}/ztree/zTreeStyle.css" type="text/css" />
		<script src="${layui}/layui/layui.js"></script>
		
		<!-- 
			当前页面自定义的样式，可以根据实际业务需要修改和删除
		 -->
		<style type="text/css">
			form input[type=text],select,textarea{
			    border: 1px solid #ccc;
			    padding: 8px 5px;
			    min-width: 40%;
			    border-radius: 2px;
			 	box-shadow: inset 1px 1px 2px #cddc398f;
			    color: #666;
			    font-size: 12px;
			    letter-spacing: normal;
			    word-spacing: normal;
			    text-transform: none;
			    text-indent: 0px;
			    text-shadow: none;
			    text-align: start;
			    outline: none;
			    display: inline-block;
			    margin: 0;
			}
			
			form input[type=text] {
			    width: 255px; 
			}
			
			form select {
			    width: 280px; 
			}		   	
		</style>
	</head>
	<body>
		<div class="layui-fluid">
			<div class="layui-row layui-col-space15">
				<div class="layui-col-md12">
					<div class="layui-card">
						<div class="layui-card-header">
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$【开始替换你的代码 - 第2步】$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
							<a><cite>系统权限配置 / </cite></a> 
							<a><cite>系统用户相关 / </cite></a>
							<a><cite>系统角色列表</cite></a>
 <!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$【完成替换你的代码 - 第2步】$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
						</div>
						<div class="layui-card-body">
							
							<!-- TODO 外挂的表格-无意义 -->
							<table id="table-toolbar" class="layui-hide" lay-filter="table-toolbar"></table>
							
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$【开始替换你的代码 - 第3步】$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
							<script id="table-search-toolbar" type="text/html">
              					<div class="layui-btn-container">
									<div class="layui-table-search-div" >角色名称：</div>
									<input id="role-name" name="roleName" class="layui-table-search" autocomplete="off">
									<div class="layui-table-search-div" >平台名称：</div>
									<input id="type" name="type" class="layui-table-search" autocomplete="off">
                					<button class="security-btn layui-btn layui-btn-sm" key="system_role_list:search" lay-event="search">查&nbsp&nbsp&nbsp&nbsp&nbsp询</button>
                					<button class="security-btn layui-btn layui-btn-sm" key="system_role_list:reset" lay-event="reset">重&nbsp&nbsp&nbsp&nbsp置</button>
                					<button class="security-btn layui-btn layui-btn-sm" key="system_role_list:add" lay-event="add">添&nbsp&nbsp&nbsp&nbsp加</button>
              					</div>
            				</script>
							<script id="table-btn-toolbar" type="text/html">
								<a class="security-btn layui-btn layui-btn-xs" key="system_role_list:role_function" lay-event="role">角色功能</a>
              					<a class="security-btn layui-btn layui-btn-xs" key="system_role_list:edit" lay-event="edit">修改</a>
              					<a class="security-btn layui-btn layui-btn-danger layui-btn-xs" key="system_role_list:delete" lay-event="del">删除</a>
            				</script>
 <!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$【完成替换你的代码 - 第3步】$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>


<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$【开始替换你的代码 - 第4步】$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->
<script type="text/javascript" src="${js}/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${js}/utils/ajaxs.js"></script>
<script type="text/javascript" src="${js}/plugins/jquery.slimscroll-1.3.8.js"></script>
<script type="text/javascript" src="${js}/ztree/jquery.ztree.all.js"></script>
<!-- 系统角色权限 -->
<script type="text/javascript" src="${js}/system/sys-user-role-function.js"></script>

<script type="text/javascript" src="${views}/permission/role/system-role-list.js"></script>
<!-- $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$【完成替换你的代码 - 第4步】$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ -->

































