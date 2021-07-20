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
			
			form input[type=text],select,textarea{
			    border: 1px solid #ccc;
			    padding: 8px 5px;
			    min-width: 40%;
			    border-radius: 2px;
			 	box-shadow: inset 1px 1px 2px #cddc398f;
			    color: #666;
			    font-size: 12px;
			    font-family: "Century Schoolbook", Arial, Helvetica, sans-serif;
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
			    width: 398px
			}
			
			form select {
			    width: 410px; 
			}		   	
		</style>
		
	</head>
	<body>
		<div class="layui-fluid">
			<div class="layui-row layui-col-space15">
				<div class="layui-col-md12" >
					<div class="layui-card" style="height: 850px;">
						<div class="layui-card-header">
							<a><cite>矩阵系统配置 / </cite></a> 
							<a><cite>系统用户相关 / </cite></a>
							<a><cite>系统功能列表</cite></a>
						</div>
						<div class="layui-card-header">
							<h3>
								<a herf="javascript:void(0)" onclick="surfunc.closeNavi('sys-tree')" style="cursor: pointer; color:#FB9337; " title="收起导航栏从而方便您的操作">收起导航</a> | 
								<a herf="javascript:void(0)" onclick="surfunc.closeMenu('sys-tree')" style="cursor: pointer; color:#FB9337; " title="收起一级菜单栏从而方便您的操作">收起菜单</a>
							</h3>
						</div>
						<div id="func-list" class="layui-card-body mousescroll" style="width: 1200px;height: auto;">
							<table class="" style="width:100%">
								<tr>
									<td style="border-right:1px solid #ece7e7;width:40%;">
										<ul id="sys-tree" class="ztree flow-default" >
											<!-- ztree -->
										</ul>
									</td>
									<td style="vertical-align:top">
										<div class="tree-right" style="padding: 5px; margin-left:40px; margin-top:20px;">
											<form id="tree-node-edit" action="javascript:void(0)"></form>
										</div>
									</td>
								</tr>
							</table>
						</div>
						
						
					</div>
				</div>
				
			</div>
		</div>
	</body>
</html>

<script type="text/javascript" src="${js}/jquery-1.10.2.js"></script>

<script type="text/javascript" src="${layui}/layui/lay/modules/layer.js"></script>

<script type="text/javascript" src="${js}/utils/ajaxs.js"></script>
<script type="text/javascript" src="${js}/plugins/jquery.slimscroll-1.3.8.js"></script>
<script type="text/javascript" src="${js}/ztree/jquery.ztree.all.js"></script>
<!-- 系统角色权限 -->
<script type="text/javascript" src="${js}/system/sys-user-role-function.js"></script>
<script>
	$(function(){
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




































