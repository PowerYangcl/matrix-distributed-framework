<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>系统权限-角色列表</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<link rel="stylesheet" href="${layui}/layui/css/layui.css" media="all">
		<link rel="stylesheet" href="${layui}/style/admin.css" media="all">
		
		<style type="text/css">
			form input,select,textarea{
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
							<a><cite>系统权限配置 / </cite></a> 
							<a><cite>系统用户相关 / </cite></a>
							<a><cite>系统角色列表</cite></a>
						</div>
						<div class="layui-card-body">
							
							<!-- <div class="demoTable">
							  	搜索ID：
							  	<div class="layui-inline">
							    	<input class="layui-input" name="id" id="demoReload" autocomplete="off">
							  	</div>
							  	<button class="layui-btn" data-type="reload">搜索</button>
							</div> -->
							
							<!-- TODO 外挂的表格-无意义 -->
							<table id="table-toolbar" class="layui-hide" lay-filter="table-toolbar"></table>
							
							<script id="table-search-toolbar" type="text/html">
              					<div class="layui-btn-container">
									<div class="layui-table-search-div" >权限名称：</div>
									<input id="demoReload" name="id" class="layui-table-search" autocomplete="off">
									<div class="layui-table-search-div" >平台名称：</div>
									<input id="demoReload" name="id" class="layui-table-search" autocomplete="off">
                					<button class="layui-btn layui-btn-sm" lay-event="getCheckData">查&nbsp&nbsp&nbsp&nbsp&nbsp询</button>
                					<button class="layui-btn layui-btn-sm" lay-event="getCheckLength">重&nbsp&nbsp&nbsp&nbsp置</button>
                					<button class="layui-btn layui-btn-sm" lay-event="isAll">添&nbsp&nbsp&nbsp&nbsp加</button>
              					</div>
            				</script>
							<script id="table-btn-toolbar" type="text/html">
              					<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
              					<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
            				</script>
            				
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>


<script src="${layui}/layui/layui.js"></script>  
<script>
  	layui.config({
    	base: '../layuiadmin/' //静态资源所在路径
  	}).extend({
    	index: 'lib/index' //主入口模块
  	}).use(['index', 'table'], function(){
  	    var table = layui.table;
  	    table.render({
  	      elem: '#table-toolbar',
  	      url : layui.setter.path + 'sysrole/ajax_system_role_list.do',
  	      toolbar: '#table-search-toolbar',
  	      title: '系统角色列表',
  	      height: 'full-100', 
 	      limit: 16,
  	      cols: [
 	    	  [
 	  	         {field:'id', title:'角色ID', width:100,unresize: true, sort: true},  //  fixed: 'left', 
 	  	         {field:'roleName', title:'角色名称', width:200},
 	  	         {field:'type', title:'平台名称', width:250, sort: true},
 	  	         {field:'roleDesc', title:'角色描述'},
 	  	         {field:'createTime', title:'创建时间', width:180},
 	  	         {fixed: 'right', title:'操作', toolbar: '#table-btn-toolbar', width:150}
 	    	  ]
  	      ]
  	      ,page: true
  	    });
  	    
  	    //头工具栏事件
  	    table.on('toolbar(table-toolbar)', function(obj){
  	      	var checkStatus = table.checkStatus(obj.config.id);
  	      	switch(obj.event){
  		        case 'getCheckData':
  		          	var data = checkStatus.data;
  		          	layer.alert(JSON.stringify(data));
  		        	break;
  		        case 'getCheckLength':
  		          	var data = checkStatus.data;
  		          	layer.msg('选中了：'+ data.length + ' 个');
  		        	break;
  		        case 'isAll':
  		          	layer.msg(checkStatus.isAll ? '全选': '未全选');
  		        	break;
  		      };
  	    });
  	    
  	    //监听行工具事件
  	    table.on('tool(table-toolbar)', function(obj){
  	      	var data = obj.data;
  			if(obj.event === 'del'){
  			  	layer.confirm('真的删除行么', function(index){
  			    	obj.del();
  			    	layer.close(index);
  			  	});
  			}else if(obj.event === 'edit'){
  			  	layer.prompt({
  			    	formType: 2,
  			    	value: data.email
  			  	},function(value, index){
  			  		obj.update({
  			      		email: value
  			    	});
  			    	layer.close(index);
  			  	});
  			}
  	    });
  	    
  	});
</script>












