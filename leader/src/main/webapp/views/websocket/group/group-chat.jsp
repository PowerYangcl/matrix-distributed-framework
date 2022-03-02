<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>矩阵系统配置-系统工具-多人聊天</title>
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
							<a><cite>多人聊天</cite></a>
						</div>
						<div class="layui-card-header">
							<h3>
								<a herf="javascript:void(0)"style="cursor: pointer; color:#FB9337; ">首先建立链接，然后发送消息</a><span id=""></span>
							</h3>
						</div>
						<div class="layui-card-body" style="width:40%;height: auto; float:left;">
							<form id="websocket-admin-form"  action="javascript:void(0)" autocomplete="off">
								<table>
									<tr>
										<td style="border-right:1px solid #ece7e7;width:40%;">
											选择群聊：<select id="group-id" name="groupId" style="margin-left:0px; margin-bottom: 10px;">
												<option value="group-id-1">数码大冒险</option>
												<option value="group-id-2">Dota全图外挂群</option>
												<option value="group-id-3">比特币交流群-1</option>
											</select>
										</td>
									</tr>
									<tr>
										<td style="border-right:1px solid #ece7e7;width:40%;">
											<div style="margin-bottom: 10px;">
												<span style="vertical-align:middle;">保存消息：</span>
												<input type="radio" name="save" value="0" checked="checked" style="vertical-align:middle;"> 
												<span style="vertical-align:middle;">保存聊天记录</span> &nbsp;&nbsp;&nbsp;&nbsp;
												<input type="radio" name="save" value="1" style="vertical-align:middle;"> 
												<span style="vertical-align:middle;">不保存记录</span>
											</div>
										</td>
									</tr>
									<tr>
										<td style="border-right:1px solid #ece7e7;width:40%;">
											<textarea id="content" style="margin-bottom: 10px;height: 200px; width: 640px" placeholder="请输入通告信息"></textarea>
											<br>
										</td>
									</tr>
									<tr>
										<td style="border-right:1px solid #ece7e7;width:40%;" align="right">
											<!-- 
												lay-submit lay-filter="add-cache" layui在js文件中提交表单使用，如：form.on('submit(add-cache)', function(o){}
											 -->
											<button id="disconnect" class="security-btn layui-btn layui-btn-sm"  key="group:disconnect"  lay-submit lay-filter="disconnect">断开链接</button>
											<button id="connect" class="security-btn layui-btn layui-btn-sm"  key="group:connect"  lay-submit lay-filter="connect">建立链接</button>
											<button id="send" class="security-btn layui-btn layui-btn-sm"  key="group:send"  lay-submit lay-filter="send">发送消息</button>
										</td>
									</tr>
								</table>
								<table  id="table-online-user" style="margin-top:10px; width:100%;">
									<tr>
										<td ><div><h2 style="margin-bottom:4px">在线用户：</h2></div></td>
									</tr>
									<tr id="" class="online-user">
										<td><div style="border-top:1px solid #ece7e7;padding:4px 0px 0px 42px;">在线用户1</div></td>
									</tr>
									<tr  id="" class="online-user">
										<td><div style="border-top:1px solid #ece7e7;padding:4px 0px 0px 42px;">在线用户2</div></td>
									</tr>
								</table>
							</form>
						</div>
						
						
						<div class="layui-table-body layui-table-main" style="width:50%;height: 600px; float:left; ">
							<table class="layui-table" lay-skin="line">
								<colgroup>
									<col width="80">
							    	<col width="400">
							    	<col width="100">
							  	</colgroup>
								<thead>
							    	<tr>
							    		<th>用户名</th>
							      		<th colspan="2" align="left">消息内容</th>
							      		<th>发送时间</th>
							    	</tr> 
							  	</thead>
								<tbody id="notice">
									<tr id="notice-defalut-tr"><td colspan="4" align="center">暂无消息</td></tr>
							  	</tbody>
							</table>
						</div>
						
					</div>
				</div>
				
			</div>
		</div>
	</body>
</html>
<script type="text/javascript" src="${views}/websocket/group/group-chat.js"></script>





































