<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>请求者信息</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<link rel="stylesheet" href="${layui}/layui/css/layui.css" media="all">
		<link rel="stylesheet" href="${layui}/style/admin.css" media="all">
		<link rel="stylesheet" href="${css}/ztree/zTreeStyle.css" type="text/css" />
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
							<a><cite>矩阵系统配置 / </cite></a> 
							<a><cite>系统开放接口 / </cite></a>
							<a><cite>请求者信息</cite></a>
						</div>
						<div class="layui-card-body">
						
							<table id="table-toolbar" class="layui-hide" lay-filter="table-toolbar"></table>
							
							<script id="table-search-toolbar" type="text/html">
              					<div class="layui-btn-container">
									<div class="layui-table-search-div" >请求者名称：</div>
									<input id="organization" name="organization" class="layui-table-search" autocomplete="off">
									<div class="layui-table-search-div" >公钥标识：</div>
									<input id="key" name="key" class="layui-table-search" autocomplete="off">

                					<button class="security-btn layui-btn layui-btn-sm" key="api_requester_info:search" lay-event="search">查&nbsp&nbsp&nbsp&nbsp&nbsp询</button>
                					<button class="security-btn layui-btn layui-btn-sm" key="api_requester_info:reset" lay-event="reset">重&nbsp&nbsp&nbsp&nbsp置</button>
                					<button class="security-btn layui-btn layui-btn-sm" key="api_requester_info:add" lay-event="add">添&nbsp&nbsp&nbsp&nbsp加</button>
              					</div>
            				</script>
							<script id="table-btn-toolbar" type="text/html">
              					<a class="security-btn layui-btn layui-btn-xs" key="api_requester_info:edit" lay-event="edit">修改</a>
								{{#  if(d.flag == 1){ }}
              						<a class="security-btn layui-btn layui-btn-danger layui-btn-xs" key="api_requester_info:delete" lay-event="del">禁用</a>
                				{{#  }else{ }}
              						<a class="security-btn layui-btn layui-btn-danger layui-btn-xs" key="api_requester_info:delete" lay-event="del">启用</a>
               	 				{{#  } }}

            				</script>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>


<script type="text/javascript" src="${views}/api/request/api-request-info-list.js"></script>

































