/**
 * 矩阵系统配置 / 分布式定时任务 / 定时任务分组列表
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
  	      	title: '定时任务分组列表',
  	      	url : layui.setter.path + 'quartz/ajax_job_group_page_list.do',
  	      	toolbar: '#table-search-toolbar',
  	      	height: 'full-100', 
 	      	limit: 16,
  	      	cols: [
 	    	  	[
 	  	         	{field:'groupName', title:'分组名称', width:250,unresize: true, sort: true},  //  fixed: 'left', 
 	  	         	{field:'ip', title:'ip地址'}, 
 	  	         	{field:'createTime', title:'创建时间' , width:160},
	  	         	{field:'createUserName', title:'创建人', width:150},
 	  	         	{field:'updateTime', title:'更新时间' , width:160},
 	  	         	{field:'updateUserName', title:'更新人', width:150},
 	  	         	{fixed: 'right', title:'操作', toolbar: '#table-btn-toolbar', width:120}
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
				case 'add':
					pageDialog.addDialog(o.key);
					break;
			}
		});

		// 监听行工具事件
		table.on('tool(table-toolbar)', function(o) {
			if (o.event === 'del') {
				pageDialog.deleteRequestInfo(o);
			} else if (o.event === 'edit') {
				pageDialog.editDialog(o);
			}
		});
		
		// 查询按钮
		var search = {		
			reload : function() {
				var groupName = $('#group-name').val();
				var ip = $('#ip').val();
				table.reload('page-table-reload', {
					page : {
						curr : 1  // 重新从第 1 页开始
					},
					where : {  // 此参数会合并请求到后台
						groupName:groupName,
						ip:ip
					}
				}, 'data');
				
				$('#group-name').val(groupName);
				$('#ip').val(ip);
			},
			
			reset : function(){
				$('#group-name').val('');
				$('#ip').val('');
				search.reload();
			}
		};
		
		// 页面弹窗对象
		var pageDialog = {
			// 添加按钮需要进行强数据验证
			addDialog : function(securityKey){
				layer.open({
					title : '添加定时任务分组',
		          	type : 1,	// 1：解析HTML代码段；2：解析url
		          	area : ['920px', '350px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawDialogPage('add' , securityKey , null),
	          		anim : 0 ,		// 弹窗从上掉落
	          		btn : ['提交' , '取消'],
	          		yes : function(index , layero){
	          			var url_ =  layui.setter.path + 'quartz/ajax_btn_job_group_add.do';
						var data_ = $("#dialog-api-form").serializeArray();
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
          			btn2 : function(index, layero){ // 按钮【取消】的回调
          				//return false 开启该代码可禁止点击该按钮关闭
          			}, 
          			cancel : function(){  // 右上角关闭回调
          				// return false; // 开启该代码可禁止点击该按钮关闭
          			}
		        });
			},
			
			// 编辑按钮需要进行强数据验证
			editDialog : function(o){
				layer.open({
					title : '修改定时任务分组',
		          	type : 1,	// 1：解析HTML代码段；2：解析url
		          	area : ['920px', '350px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawDialogPage('edit' , o.key , o.data),
	          		anim : 0 ,		// 弹窗从上掉落
	          		btn : ['提交' , '取消'],
	          		yes : function(index , layero){
	          			var url_ =  layui.setter.path + 'quartz/ajax_btn_job_group_edit.do';
						var data_ = $("#dialog-api-form").serializeArray();
						var obj = JSON.parse(layui.setter.ajaxs.sendAjax('post' , url_ , data_));
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
          			btn2 : function(index, layero){ // 按钮【取消】的回调
          				//return false 开启该代码可禁止点击该按钮关闭
          			}, 
          			cancel : function(){  // 右上角关闭回调
          				// return false; // 开启该代码可禁止点击该按钮关闭
          			}
		        });
			},
			
			deleteRequestInfo:function(o){
	        	layer.confirm('您确定要删除这个定时任务分组【' + o.data.groupName + '】吗？' , 
	        			{ title:'系统提示', icon:7, skin: 'layui-layer-molv', anim:4 , btn : [ '确定', '取消' ] }, 
					function(index , ele) {  // 确定按钮
						var type_ = 'post';
			            var url_ = layui.setter.path + 'quartz/ajax_btn_job_group_delete.do';
			        	var data_ = {
		        			id : o.data.id ,
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
			
	        // 绘制添加和编辑弹框
			drawDialogPage : function(type , key , e){
				var id = "";
				var alert = '已经关联定时任务的分组将无法删除';
				var groupName = '';
				var ip = '';
				if(type == 'edit') {
					id = e.id;
					alert = '修改IP地址可以控制定时任务在服务器集群中的执行强度(个数) 非并发类的定时任务会显著受到影响';
					groupName = e.groupName;
					ip = e.ip;
				}
				
				var title = '<div class="dialog-form-title">';
						title += '<h3>' + alert + '</h3>';
					title += '</div>';
				var html = '<form id="dialog-api-form">' + title + '<table class="dialog-form-talbe" style="width:95%;margin-left:20px">';
					html += '<tr>';
						html += '<td style="text-align: left;width:160px;" colspan="1">定时任务分组名称：</td>';
						html += '<td style="text-align: left" colspan="3">';
							html += '<input type="text" name="groupName" value="' + groupName + '" class="dialog-form-input" style="width:96%;" placeholder="25个字以内"  maxlength="25">';
						html += '</td>';
					html += '</tr>';
					
					html += '<tr>';
						html += '<td style="text-align: left;width:160px;" colspan="1">分组包含IP地址段：</td>';
						html += '<td style="text-align: left" colspan="3">';
							html += '<input type="text" name="ip" value="' + ip + '" class="dialog-form-input" style="width:96%;" placeholder="IP地址段以逗号分隔。如：172.22.134.33,172.22.134.34"  maxlength="2000">';
						html += '</td>';
					html += '</tr>';			
					
					
					if(type == 'edit'){
						html += '<input type="hidden" name="id" value="' + id + '">';
					}
					html += '<input type="hidden" name="eleValue" value="' + key + '">';
				html += '</table></form>';
				return html;
			},
		}
	});











