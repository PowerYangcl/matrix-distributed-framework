/**
 * 系统权限配置 / 系统用户相关 / 系统用户列表/用户角色列表弹窗
 */

parentTr : null;	// 保存父页面传递到子页面的当前行数据对象(请谨慎使用全局变量)
// 父页面layer.open中的success方法调用此方法传入一个对象参数
function currentTr (e){ // 当前行(tr)
	parentTr = e;
}


layui.config({
    	base: '../layuiadmin/' //静态资源所在路径
  	}).extend({
    	index: 'lib/index' //主入口模块
  	}).use(['index', 'table'], function(){
  	    var table = layui.table;
  	  	var $ = layui.$;
  	  	var layer = layui.layer;
  	    table.render({
  	    	id: 'page-table-reload',  			// 页面查询按钮需要table.reload
  	      	elem: '#table-toolbar',				// 表格控制句柄
  	      	url : layui.setter.path + 'sysrole/ajax_user_role_list.do',
  	      	where: {
  	      		userId : parentTr.id,
  	      		platform : parentTr.platform
      		},
  	      	toolbar: '#table-search-toolbar',
  	      	title: '系统用户列表/用户角色列表弹窗',
  	      	height: 'full-100', 
  	      	cols: [
 	    	  	[
 	  	         	{field:'id', title:'角色ID', width:100,unresize: true, sort: true},  //  fixed: 'left', 
 	  	         	{field:'platformName', title:'归属平台', width:150},
 	  	         	{field:'roleName', title:'角色名称', width:200},
 	  	         	{field:'roleDesc', title:'角色描述'},
 	  	         	{field:'createTime', title:'创建时间', width:180},
 	  	         	{fixed: 'right', title:'操作', toolbar: '#table-btn-toolbar', width:80}
 	    	  	]
  	      	],
  	    	page: true
  	    });

		// 头部工具栏事件
		table.on('toolbar(table-toolbar)', function(o) {
			switch (o.event) {
				case 'search':
					search.reload();
					break;
				case 'reset':
					search.reset();
					break;
			}
		});

		// 监听行工具事件
		table.on('tool(table-toolbar)', function(o) {
			if (o.event === 'allotSubmit') {
				pageDialog.allotSubmit(o);
			} else if (o.event === 'allotRemove') {
				pageDialog.allotRemove(o);
			}
		});
		
		
		// 查询按钮
		var search = {		
			reload : function() {
				var roleName = $('#role-name').val();
				var type = $('#type').val();
				table.reload('page-table-reload', {
					page : {
						curr : 1  // 重新从第 1 页开始
					},
					where : {
						roleName:roleName
					}
				}, 'data');
				
				$('#role-name').val(roleName);
			},
			
			reset : function(){
				$('#role-name').val('');
				search.reload();
			},
		};
		
		// 页面弹窗对象
		var pageDialog = {
			allotSubmit:function(o){
	        	layer.confirm('您确定要分配这个角色【' + o.data.roleName + '】给用户：' + parentTr.userName +' 吗？' , { title:'系统提示', icon:7, skin: 'layui-layer-molv', anim:4 , btn : [ '确定', '取消' ] }, 
					function(index , ele) {  // 确定按钮
						var type_ = 'post';
			            var url_ = layui.setter.path + 'sysrole/ajax_btn_allot_user_role_submit.do';
			        	var data_ = {
			        			mcRoleId : o.data.id ,
			        			mcUserId : parentTr.id,
			        			eleValue : o.key
		        			}; 
			        	var obj = JSON.parse(layui.setter.ajaxs.sendAjax(type_ , url_ , data_));
			            if(obj.status == 'success'){
			            	layer.alert( obj.msg , {title:'操作成功 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4} , function(a){
			            		$(".layui-laypage-btn").click();  // 定位在当前页同时刷新数据
			            		layer.close(a);
			            		layer.close(index);
		            		});
			            }else{
			            	layer.alert( obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
			            }
					}, 
					function(index , ele) {  // 取消按钮
						layer.close(index);
					}
				);
	        },
	        
	        allotRemove:function(o){
	        	layer.confirm('您确定要为用户：' + parentTr.userName +' 移除这个角色【' + o.data.roleName + '】吗？' , { title:'系统提示', icon:7, skin: 'layui-layer-molv', anim:4 , btn : [ '确定', '取消' ] }, 
					function(index , ele) {  // 确定按钮
						var type_ = 'post';
			            var url_ = layui.setter.path + 'sysrole/ajax_btn_allot_user_role_remove.do';
			        	var data_ = {
			        			mcRoleId : o.data.id ,
			        			userId : parentTr.id,
			        			eleValue : o.key
		        			}; 
			        	var obj = JSON.parse(layui.setter.ajaxs.sendAjax(type_ , url_ , data_));
			            if(obj.status == 'success'){
			            	layer.alert( obj.msg , {title:'操作成功 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4} , function(a){
			            		$(".layui-laypage-btn").click();  // 定位在当前页同时刷新数据
			            		layer.close(a);
			            		layer.close(index);
		            		});
			            }else{
			            	layer.alert( obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
			            }
					}, 
					function(index , ele) {  // 取消按钮
						layer.close(index);
					}
				);
	        },
		}

	});











