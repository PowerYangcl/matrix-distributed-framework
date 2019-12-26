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
		<script src="${layui}/layui/layui.js"></script>  
		<style type="text/css">
			
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
			    width: 409px; 
			}		   	
		</style>
		
	</head>
	<body>
		<div class="layui-fluid">
			<div class="layui-row layui-col-space15">
				<div class="layui-col-md12" >
					<div class="layui-card" style="height: 850px;">
						<div class="layui-card-header">
							<a><cite>系统权限配置 / </cite></a> 
							<a><cite>系统工具 / </cite></a>
							<a><cite>缓存查看</cite></a>
						</div>
						<div class="layui-card-header">
							<h3>
								<a herf="javascript:void(0)"style="cursor: pointer; color:#FB9337; ">完整缓存key：</a><span id="redis-key"></span>
							</h3>
						</div>
						<div class="layui-card-body" style="width: 1200px;height: auto;">
							<!-- 表单体系所在的父元素加上class="layui-form"，一切的工作都会在你加载完form模块后，自动完成 -->
							<!-- 表单体系所在的父元素加上class="layui-form"，一切的工作都会在你加载完form模块后，自动完成 -->
							<!-- 表单体系所在的父元素加上class="layui-form"，一切的工作都会在你加载完form模块后，自动完成 -->
							<form id="cache-form" class="layui-form" action="javascript:void(0)" autocomplete="off">
								<table class="" style="width:70%">
									<tr>
										<td style="border-right:1px solid #ece7e7;width:40%;">
											缓存类型：<select id="type" name="type" style="margin-left:0px; margin-bottom: 10px;">
												<option value="dict" dict="xd">字典缓存</option>
												<option value="serv" dict="xs">服务缓存</option>
											</select>
										</td>
									</tr>
									<tr>
										<td style="border-right:1px solid #ece7e7;width:40%;">
											缓存前缀：<input id="prefix" name="prefix" type="text"  style="margin-bottom: 10px;" value="" placeholder="McUserRole" lay-verify="required"><br> 
											缓存后缀：<input id="cache-key" name="key" type="text" style="margin-bottom: 10px;" value="" placeholder="80160001"><br>
											<textarea id="json-str" style="margin-bottom: 10px;height: 400px; width: 790px" placeholder="这里将显示缓存信息"></textarea>
											<br>
										</td>
									</tr>
									
									<tr>
										<td style="border-right:1px solid #ece7e7;width:40%;" align="right">
											<button class="security-btn layui-btn layui-btn-sm layui-btn-danger"  key="system_cache:batch_delete"  lay-submit lay-filter="batch-delete-cache">批量删除缓存</button>
											<button class="security-btn layui-btn layui-btn-sm"  key="system_cache:reset"  lay-submit lay-filter="add-cache">设置缓存</button>
											<button class="security-btn layui-btn layui-btn-sm"  key="system_cache:reset_forever"  lay-submit lay-filter="add-cache-forever">设置缓存(永久)</button>
											<button class="security-btn layui-btn layui-btn-sm layui-btn-danger"  key="system_cache:delete_cache"  lay-submit lay-filter="delete-cache">删除缓存</button>
											<button class="security-btn layui-btn layui-btn-sm"  key="system_cache:get_cache"  lay-submit lay-filter="get-cache" style="margin-right: 30px;">获取缓存</button>
										</td>
									</tr>
								</table>
							</form>
						</div>
						
						
					</div>
				</div>
				
			</div>
		</div>
	</body>
</html>



<script type="text/javascript" src="${views}/system/cache/system-cache.js"></script>





































