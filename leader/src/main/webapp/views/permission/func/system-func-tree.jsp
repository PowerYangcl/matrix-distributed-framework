<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>系统权限-系统功能树</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<link rel="stylesheet" href="${layui}/layui/css/layui.css" media="all">
		<link rel="stylesheet" href="${layui}/style/admin.css" media="all">
		
		<link rel="stylesheet" href="${css}/ztree/zTreeStyle.css" type="text/css" />
		<style type="text/css">
			.flow-default{width: 500px; height: auto; overflow: auto; font-size: 16; }		   	
		</style>
		
	</head>
	<body>
		<div class="layui-fluid">
			<div class="layui-row layui-col-space15">
				<div class="layui-col-md12" >
					<div class="layui-card" style="height: 850px;">
						<div class="layui-card-header">
							<a><cite>系统权限配置 / </cite></a> 
							<a><cite>系统用户相关 / </cite></a>
							<a><cite>系统功能列表</cite></a>
						</div>
						<div class="layui-card-header">
							<h3>
								<a herf="javascript:void(0)" onclick="" class="a-btn" style="cursor: pointer; color:#FB9337; " title="收起导航栏从而方便您的操作">收起导航</a> | 
								<a herf="javascript:void(0)" onclick="" class="a-btn" style="cursor: pointer; color:#FB9337; " title="收起一级菜单栏从而方便您的操作">收起菜单</a>
							</h3>
						</div>
						<div id="func-list" class="layui-card-body mousescroll" style="width: 500px;height: auto; border-right:1px solid #ece7e7">
							<ul id="sys-tree" class="ztree flow-default" >
								<!-- ztree -->
							</ul>
						</div>
						<div class="tree-right" style="padding: 5px; display:none;">
							<form id="tree-node-edit" action="javascript:void(0)"></form>
						</div>
						
					</div>
				</div>
				
			</div>
		</div>
	</body>
</html>


<script type="text/javascript" src="${js}/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${js}/utils/ajaxs.js"></script>
<script type="text/javascript" src="${js}/plugins/jquery.slimscroll-1.3.8.js"></script>
<script type="text/javascript" src="${js}/ztree/jquery.ztree.all.js"></script>
<!-- 系统角色权限 -->
<script type="text/javascript" src="${js}/system/sys-user-role-function.js"></script>
<script>
	$(document).ready(function(){
		surfunc.init(window.top.basePath).sysTreeOperation();
		
		// 自定义滚动条 | 执行此代码自定义滚动条则生效
		$('#sys-tree').slimscroll({
			color: '#666',
			size: '10px',
			width: 'auto',
			height: '700px'  
		});
	});
	
</script>




































