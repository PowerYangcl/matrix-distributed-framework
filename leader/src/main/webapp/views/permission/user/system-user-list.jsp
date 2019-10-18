<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>系统权限-用户列表</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<link rel="stylesheet" href="${layui}/layui/css/layui.css" media="all">
		<link rel="stylesheet" href="${layui}/style/admin.css" media="all">
	</head>
	<body>
		<div class="layui-fluid">
			<div class="layui-row layui-col-space15">
				<div class="layui-col-md12">
					<div class="layui-card">
						<div class="layui-card-header">
							<a><cite>系统权限配置 / </cite></a> 
							<a><cite>系统用户相关 / </cite></a>
							<a><cite>系统用户列表</cite></a>
						</div>
						<div class="layui-card-body">
							
							<!-- TODO 外挂的表格-无意义 -->
							<table id="table-toolbar" class="layui-hide" lay-filter="table-toolbar"></table>
							
							<script id="table-search-toolbar" type="text/html">
              					<div class="layui-btn-container">
                					<button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>
                					<button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button>
                					<button class="layui-btn layui-btn-sm" lay-event="isAll">验证是否全选</button>
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
  	      url : layui.setter.path + 'userInfo/ajax_system_user_list.do',
  	      toolbar: '#table-search-toolbar',
  	      title: '系统用户列表表',
  	      cols: [
 	    	  [
 	    		 {type: 'checkbox', fixed: 'left'},
 	  	         {field:'id', title:'ID', width:180, fixed: 'left', unresize: true, sort: true},
 	  	         {field:'userName', title:'用户名', width:120, edit: 'text'},
				 {
					field:'email', 
					title:'E-mail', 
					width:150, 
					edit: 'text', 
					templet: function(res){
				  		return '<a>'+ res.email +'</a>'
					}
				 },
  	        	 {field:'sex', title:'性别', width:80, edit: 'text', sort: true},
 	  	         {field:'remark', title:'签名'},
 	  	         {field:'type', title:'用户类型', width:100, sort: true},
 	  	         {field:'password', title:'password', width:120},
 	  	         {field:'mobile', title:'手机号码', width:100, sort: true},
 	  	         {field:'createTime', title:'加入时间', width:120},
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












