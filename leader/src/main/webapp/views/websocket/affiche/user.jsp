<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>矩阵系统配置-系统工具-接收系统公告</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<link rel="stylesheet" href="${layui}/layui/css/layui.css" media="all">
		<link rel="stylesheet" href="${layui}/style/admin.css" media="all">
		<script src="${layui}/layui/layui.js"></script>
		
		<script src="https://cdn.bootcdn.net/ajax/libs/sockjs-client/1.5.2/sockjs.js"></script>	<!--  -->
		<script src="https://cdn.bootcdn.net/ajax/libs/stomp.js/2.3.2/stomp.js"></script> <!-- -->
		<!-- <script src="https://cdn.bootcdn.net/ajax/libs/sockjs-client/1.5.2/sockjs.min.js"></script> -->
		<!-- <script src="https://cdn.bootcdn.net/ajax/libs/stomp.js/2.3.2/stomp.min.js"></script> -->
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
							<a><cite>矩阵系统配置 / </cite></a> 
							<a><cite>系统工具 / </cite></a>
							<a><cite>接收系统公告</cite></a>
						</div>
						<div class="layui-card-header">
							<h3>
								<a herf="javascript:void(0)"style="cursor: pointer; color:#FB9337; ">实时系统公告如下：</a>
							</h3>
						</div>
						<!-- layui-table-main 样式指定table高度最大值显示滚动条 -->
						<div class="layui-table-body layui-table-main" style="width: 1200px;height: 600px;">
							<table class="layui-table" lay-skin="line">
							  	<colgroup>
							    	<col width="300">
							    	<col width="100">
							  	</colgroup>
							  	<thead>
							    	<tr>
							      		<th>通告内容</th>
							      		<th>发布时间</th>
							    	</tr> 
							  	</thead>
							  	<tbody id="notice">
							    	<tr id="notice-defalut-tr">
							      		<td colspan="4" align="center">暂无公告</td>
							    	</tr>
							  	</tbody>
							</table>
						</div>
						
					</div>
				</div>
				
			</div>
		</div>
	</body>
</html>
<script type="text/javascript" src="${views}/websocket/affiche/user.js"></script>





































