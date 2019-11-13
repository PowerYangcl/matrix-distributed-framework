/**
 * 系统权限配置 / 系统用户相关 / 系统角色列表
 */
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
 	  	         	{fixed: 'right', title:'操作', toolbar: '#table-btn-toolbar', width:200}
 	    	  	]
  	      	],
  	    	page: true
  	    });

		// 头部工具栏事件
		table.on('toolbar(table-toolbar)', function(obj) {
			switch (obj.event) {
				case 'search':
					search.reload();
					break;
				case 'reset':
					search.reset();
					break;
				case 'add':
					pageDialog.addDialog();
					break;
			}
		});

		// 监听行工具事件
		table.on('tool(table-toolbar)', function(obj) {
			var data = obj.data;
			if (obj.event === 'del') {
				layer.confirm('真的删除行么', function(index) {
					obj.del();
					layer.close(index);
				});
			} else if (obj.event === 'edit') {
				layer.prompt({
					formType : 2,
					value : data.email
				}, function(value, index) {
					obj.update({
						email : value
					});
					layer.close(index);
				});
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
			}
		};
		
		// 页面弹窗对象
		var pageDialog = {
			addDialog : function(){
				var html = '<form id="dialog-add-form"><table class="dialog-form" style="width:100%">';
					html += '<tr>';
						html += '<td align="right">角色名称：</td>';
						html += '<td align="left"><input type="text" name="roleName" placeholder="角色名称 20个字以内 " autocomplete="off" style="margin-bottom: 10px;" maxlength="20"></td>';
					html += '</tr>';
					html += '<tr>';
						html += '<td align="right">角色描述：</td>';
						html += '<td align="left"><input type="text" name="roleDesc" placeholder="角色名称 50个字以内 " autocomplete="off" style="margin-bottom: 10px;" maxlength="50"></td>';
					html += '</tr>';
					html += '<tr>';
						html += pageDialog.drawPlatformRadio();
					html += '</tr>';
				html += '</table></form>';
				
				layer.open({
					title: '添加数据',
		          	type: 1,	// 1：解析HTML代码段；2：解析url
		          	area: ['600px', '250px'],
		          	fixed: false,
		          	shadeClose: false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content: html,
	          		anim: 1 ,		// 弹窗从上掉落
	          		btn: ['提交' , '取消'],
	          		yes: function(index , layero){
	          			var url_ =  layui.setter.path + 'sysrole/ajax_add_mc_role_only.do';
						var data_ = $("#dialog-add-form").serializeArray();
						var obj = JSON.parse(layui.setter.ajaxs.sendAjax('post' , url_ , data_));
						if(obj.status == 'success'){
			            	layer.alert( obj.msg , {title:'操作成功 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4} , function(a){
			            		search.reload();
			            		layer.close(a);
			            		layer.close(index);
		            		});
			            }else{
			            	layer.alert( obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
			            }
	          		},
          			btn2: function(index, layero){ // 按钮【取消】的回调
          				//return false 开启该代码可禁止点击该按钮关闭
          			}, 
          			cancel: function(){  // 右上角关闭回调
          				// return false; // 开启该代码可禁止点击该按钮关闭
          			}
		        });
				
			},
			
			editDialog : function(){
				
			},
			
			// 角色功能弹层
			roleFuncDialog : function(){
				
			},
			
			
			
			
			// 绘制平台分配Radio框
			drawPlatformRadio : function(){
				var html_ = '<td align="right">平台分配：</td><td align="left">';
				var url_ = layui.setter.path + 'manager/ajax_platform_info_list.do';
				var data_ = null;
				var obj = JSON.parse(layui.setter.ajaxs.sendAjax('post' , url_ , data_));
				if(obj.status == 'success'){
					var sflist = obj.sflist;
					if(sflist.length != 0){
						for(var i = 0 ; i < sflist.length; i ++){
							var isChecked = '';
							var val_ = '';
							if(sflist[i].name.toLocaleLowerCase()  == 'leader'){
								isChecked = ' checked ';
							}
							html_ += '<input type="radio" ' + isChecked + ' name="platform" value="' + sflist[i].platform +'"style="vertical-align:middle;">';
							html_ += '<span style="vertical-align:middle;">' + sflist[i].name +'</span>&nbsp&nbsp&nbsp&nbsp&nbsp';
						}
					}
				}
				return html_ + '</td>';
			},
			
			demoDialog : function(){
				layer.open({
					title: '添加数据',
		          	type: 1,							// 1：解析HTML代码段；2：解析url
		          	area: ['700px', '450px'],
		          	fixed: false,
		          	maxmin: true,				// 开启弹层最小化和最大化按钮
		          	// shade: 0.3,  				// 遮罩透明度，默认0.3。如果为false则去掉遮罩
		          	shadeClose: false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	// content: '../../iframe/layer/iframe.html'
	          		content: '<div style="padding: 20px;">放入任意HTML</div>',
	          		anim: 1 ,							// 弹窗从上掉落
	          		btnAlign: 'r',   				// 按钮排列。.btnAlign: 'l'	按钮左对齐|btnAlign: 'c'	按钮居中对齐|btnAlign: 'r'	按钮右对齐。默认值，不用设置
	          		closeBtn : 1,    				// layer提供了两种风格的关闭按钮，可通过配置1和2来展示，如果不显示，则closeBtn: 0。默认：1
	          		btn: ['提交' , '取消'],
	          		yes: function(index , layero){
	          			 // 按钮【提交】的回调
	          			layer.alert( obj.msg , {title:'操作成功 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4} , function(a){
	          				// search.reload();  // 重新加载数据
		            		layer.close(a);
		            		layer.close(index);
	            		});
	          		},
          			btn2: function(index , layero){ 		// 按钮【取消】的回调。return false 开启该代码可禁止点击该按钮关闭
          			}, 
          			cancel: function(){  // 右上角关闭回调。return false; // 开启该代码可禁止点击该按钮关闭
          			}
		        });
			}
		}

	});




























