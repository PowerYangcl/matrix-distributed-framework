/**
 * 系统权限配置 / 系统开放接口 / 跨域白名单
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
  	      	title: '跨域白名单',
  	      	url : layui.setter.path + 'apicenter/ajax_include_domain_page_list.do',
  	      	toolbar: '#table-search-toolbar',
  	      	height: 'full-100', 
 	      	limit: 16,
  	      	cols: [
 	    	  	[
 	  	         	{field:'domain', title:'域名地址'},  //  fixed: 'left', 
 	  	         	{field:'companyName', title:'所属公司' , width:160},
 	  	         	{field:'createTime', title:'创建时间' , width:160},
 	  	         	{field:'updateTime', title:'更新时间' , width:160},
 	  	         	{field:'updater', title:'更新人', width:150},
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
				pageDialog.deleteMcRole(o);
			} else if (o.event === 'edit') {
				pageDialog.editDialog(o);
			}else if (o.event === 'role') {
				pageDialog.roleFuncDialog(o)
			}
		});
		
		
		// 查询按钮
		var search = {		
			reload : function() {
				var target = $('#target').val();
				table.reload('page-table-reload', {
					page : {
						curr : 1  // 重新从第 1 页开始
					},
					where : {  // 此参数会合并请求到后台
						target:target
					}
				}, 'data');
				
				$('#target').val(target);
			},
			
			reset : function(){
				$('#target').val('');
				search.reload();
			}
		};
		
		
		// 页面弹窗对象
		var pageDialog = {
			// 添加按钮需要进行强数据验证
			addDialog : function(securityKey){
				layer.open({
					title : '添加API项目',
		          	type : 1,	// 1：解析HTML代码段；2：解析url
		          	area : ['600px', '250px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawDialogPage('add' , securityKey , null),
	          		anim : 0 ,		// 弹窗从上掉落
	          		btn : ['提交' , '取消'],
	          		yes : function(index , layero){
	          			var url_ =  layui.setter.path + 'apicenter/ajax_btn_api_domain_add.do';
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
					title : '修改API项目',
		          	type : 1,	// 1：解析HTML代码段；2：解析url
		          	area : ['600px', '250px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawDialogPage('edit' , o.key , o.data),
	          		anim : 0 ,		// 弹窗从上掉落
	          		btn : ['提交' , '取消'],
	          		yes : function(index , layero){
	          			var url_ =  layui.setter.path + 'apicenter/ajax_btn_api_domain_edit.do';
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
			
			deleteMcRole:function(o){
	        	layer.confirm('您确定要删除这个域名【' + o.data.domain + '】吗？' , { title:'系统提示', icon:7, skin: 'layui-layer-molv', anim:4 , btn : [ '确定', '取消' ] }, 
					function(index , ele) {  // 确定按钮
						var type_ = 'post';
			            var url_ = layui.setter.path + 'apicenter/ajax_btn_api_domain_delete.do';
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
				var domain = "";
				var companyName = "";
				
				if(type == 'edit') {
					id = e.id;
					domain = e.domain;
					companyName = e.companyName;
				}
				
				var html = '<form id="dialog-api-form"><table class="dialog-form" style="width:100%">';
					html += '<tr>';
						html += '<td align="right" style="width:195px">跨域域名：</td>';
						html += '<td align="left">';
							html += '<input type="text" name="domain" value="' + domain + '" placeholder="角色名称 20个字以内 " autocomplete="off" style="margin-bottom: 10px;" maxlength="50">';
						html += '</td>';
					html += '</tr>';
					
					html += '<tr>';
						html += '<td align="right">所属公司：</td>';
						html += '<td align="left">';
							html += '<input type="text" name="companyName" value="' + companyName + '" placeholder="请填写公司名称" autocomplete="off" style="margin-bottom: 10px;" maxlength="20">';
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











