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
 	  	         	{fixed: 'right', title:'操作', toolbar: '#table-btn-toolbar', width:180}
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
			// 添加按钮需要进行强数据验证
			addDialog : function(securityKey){
				layer.open({
					title : '添加角色',
		          	type : 1,	// 1：解析HTML代码段；2：解析url
		          	area : ['600px', '250px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawDialogPage('add' , securityKey , null),
	          		anim : 0 ,		// 弹窗从上掉落
	          		btn : ['提交' , '取消'],
	          		yes : function(index , layero){
	          			var url_ =  layui.setter.path + 'sysrole/ajax_btn_add_mc_role_only.do';
						var data_ = $("#dialog-role-form").serializeArray();
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
					title : '修改角色',
		          	type : 1,	// 1：解析HTML代码段；2：解析url
		          	area : ['600px', '250px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawDialogPage('edit' , o.key , o.data),
	          		anim : 0 ,		// 弹窗从上掉落
	          		btn : ['提交' , '取消'],
	          		yes : function(index , layero){
	          			var url_ =  layui.setter.path + 'sysrole/ajax_btn_edit_mc_role_only.do';
						var data_ = $("#dialog-role-form").serializeArray();
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
	        	layer.confirm('您确定要删除这个角色【' + o.data.roleName + '】吗？' , { title:'系统提示', icon:7, skin: 'layui-layer-molv', anim:4 , btn : [ '确定', '取消' ] }, 
					function(index , ele) {  // 确定按钮
						var type_ = 'post';
			            var url_ = layui.setter.path + 'sysrole/ajax_btn_delete_mc_role.do';
			        	var data_ = {
			        			mcRoleId : o.data.id ,
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
	        
			// 角色功能弹层
			roleFuncDialog : function(o){
				var title = '为角色【' + o.data.roleName + '】赋予系统功能';
				layer.open({
					title : title,
		          	type : 1,	// 1：解析HTML代码段；2：解析url
		          	area : ['600px', '800px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawRoleFuncDialog(o),
	          		anim : 0 ,		// 弹窗从上掉落
	          		btn : ['提交' , '解绑'],
	          		securityKey : ['system_role_list:dialog_submit' , 'system_role_list:dialog_cancel'],
	          		userPageBtns : window.parent.layui.setter.pageBtns,
	          		success: function(layero, index){	// 开始追加ztree节点
	          			surfunc.init(layui.setter.path).distributeUserRole(o.data.id , o.data.platform);  // 填充弹窗中的数据
	          			$('#func-list').slimscroll({		// 自定义滚动条
	          				color: '#666',
	          				size: '10px',
	          				width: 'auto',
	          				height: '640px'  
	          			});
	          		},
	          		yes : function(index , layero , btn){
	          			pageDialog.submitRoleFunc(index , o , btn.attr("key"));
	          		},
          			btn2 : function(index, layero , btn){ // 按钮【解绑】的回调
          				pageDialog.relieveRoleFunc(index , o , btn.attr("key"));
          				return false;
          			}, 
          			cancel : function(){  // 右上角关闭回调
          				// return false; // 开启该代码可禁止点击该按钮关闭
          			}
		        });
			},
			
			// 提交与角色关联好的功能 
			submitRoleFunc : function(index , o , eleValue_){ 
				var roleId = o.data.id;  
		    	var tree = $.fn.zTree.getZTreeObj("user-role-tree");
		    	var checkArray = tree.getChangeCheckedNodes(); // 获取所有被选节点 
		    	var ids = ''; 
		    	for(var i = 0 ; i < checkArray.length ; i ++){
		    		var t = checkArray[i];
		    		if(t.navType != -1){
		    			ids += t.id + ",";
		    		}
		    	}
		    	ids = ids.substring(0 , ids.length -1); 
		    	
		        var url_ = layui.setter.path + 'sysrole/ajax_btn_edit_mc_role.do';
		    	var data_ = {
		    			mcRoleId:roleId,
		    			ids:ids,
	        			eleValue:eleValue_
		    	};  
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
		    
		    // 解绑按钮
		    relieveRoleFunc : function(index , o , eleValue_){
		    	var roleId = o.data.id;  
		    	var tree = $.fn.zTree.getZTreeObj("user-role-tree");
		    	var checkArray = tree.getChangeCheckedNodes(); // 获取所有被选节点 
		    	var ids = ''; 
		    	for(var i = 0 ; i < checkArray.length ; i ++){
		    		var t = checkArray[i];
		    		if(t.navType != -1){
		    			ids += t.id + ",";
		    		}
		    	}
		    	ids = ids.substring(0 , ids.length -1); 
		    	
		        var url_ = layui.setter.path + 'sysrole/ajax_btn_relieve_mc_role.do';
		    	var data_ = {
		    			mcRoleId:roleId,
		    			eleValue:eleValue_
		    	};  
		    	var obj = JSON.parse(layui.setter.ajaxs.sendAjax('post' , url_ , data_));
				if(obj.status == 'success'){
	            	layer.alert( obj.msg , {title:'操作成功 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4} , function(a){
	            		$(".layui-laypage-btn").click();  // 定位在当前页同时刷新数据
	            		tree.checkAllNodes(false);
	            		layer.close(a);
            		});
	            }else{
	            	layer.alert( obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
	            }
		    },
			
			// 绘制添加和编辑弹框
			drawDialogPage : function(type , key , e){
				var id = "";
				var roleName = "";
				var roleDesc = "";
				var oldRoleName = "";
				var platform = "";
				if(type == 'edit'){
					id = e.id;
					roleName = e.roleName;
					roleDesc = e.roleDesc;
					oldRoleName = e.roleName;
					platform = e.platform;
				}
				
				var html = '<form id="dialog-role-form"><table class="dialog-form" style="width:100%">';
					html += '<tr>';
						html += '<td align="right" style="width:195px">角色名称：</td>';
						html += '<td align="left"><input type="text" id="name" name="roleName" value="' + roleName + '" placeholder="角色名称 20个字以内 " autocomplete="off" style="margin-bottom: 10px;" maxlength="20"></td>';
					html += '</tr>';
					html += '<tr>';
						html += '<td align="right">角色描述：</td>';
						html += '<td align="left"><input type="text" id="roleDesc" name="roleDesc" value="' + roleDesc + '" placeholder="角色名称 50个字以内 " autocomplete="off" style="margin-bottom: 10px;" maxlength="50"></td>';
					html += '</tr>';
					if(type == 'add'){
						html += pageDialog.drawPlatformRadio();
					}else{
						html += '<input type="hidden" name="id" value="' + id + '">';
						html += '<input type="hidden" name="oldRoleName" value="' + oldRoleName + '">';
						html += '<input type="hidden" name="platform" value="' + platform + '">';
					}
					html += '<input type="hidden" name="eleValue" value="' + key + '">';
				html += '</table></form>';
				
				return html;
			},
			
			// 绘制角色功能弹层
			drawRoleFuncDialog : function(o){
				var html = '<div class="layui-card-header"><h3>';
					html += '<a herf="javascript:void(0)" onclick="surfunc.closeNavi(\'user-role-tree\')" style="cursor: pointer; color:#FB9337; " title="收起导航栏从而方便您的操作">收起导航</a> | ';
					html += '<a herf="javascript:void(0)" onclick="surfunc.closeMenu(\'user-role-tree\')" style="cursor: pointer; color:#FB9337; " title="收起一级菜单栏从而方便您的操作">收起菜单</a>';
				html += '</h3></div>';
				html += '<div id="func-list" class="layui-card-body mousescroll" style="width: 520px;height: auto;">';
					html += '<input id="func-ids"  type="hidden" value="" >';
					html += '<ul id="user-role-tree" class="ztree" ></ul>';
				html += '</div>';
				return html;
			},
			
			
			
			
			// 绘制平台分配Radio框
			drawPlatformRadio : function(){
				var html_ = '<tr><td align="right">平台分配：</td><td align="left">';
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
				return html_ + '</td></tr>';
			},
			
			demoDialog : function(){
				layer.open({
					title : '添加数据',
		          	type : 1,								// 1：解析HTML代码段；2：解析url
		          	area : ['700px', '450px'],
		          	fixed : false,
		          	maxmin : true,				// 开启弹层最小化和最大化按钮
		          	// shade : 0.3,  					// 遮罩透明度，默认0.3。如果为false则去掉遮罩
		          	shadeClose : false,			// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	// content : '../../iframe/layer/iframe.html'
	          		content : '<div style="padding: 20px;">放入任意HTML</div>',
	          		skin: 'layui-layer-molv', 	// 
	          		anim : 0 ,							// 0 默认 | 1 弹窗从上掉落 | 2 由下方向上出现
	          		btnAlign : 'r',   					// 按钮排列。.btnAlign: 'l'	按钮左对齐|btnAlign: 'c'	按钮居中对齐|btnAlign: 'r'	按钮右对齐。默认值，不用设置
	          		closeBtn : 1,    					// layer提供了两种风格的关闭按钮，可通过配置1和2来展示，如果不显示，则closeBtn: 0。默认：1
	          		btn : ['提交' , '取消'],
	          		
					 /**	securityKey：非必选项。与userPageBtns属性结合使用 - Yangcl
					  * 	只用在以layer.open所打开的弹框中，以【数组】形式保存这个弹框中每个按钮所对应的权限key - Yangcl
					  * 	例如，layer.open中的属性：btn : ['提交' , '解绑' , '流转']。代表弹框中的三个按钮，其默认渲染
					  * 	顺序为0-提交，1-解绑，2-流转；其中【流转】按钮不需要权限控制。
					  * 	那么securityKey的值为：securityKey : ['system_role_list:submit' , 'system_role_list:cancel' , '']
					  * 	其中：【提交】对应'system_role_list:submit'；【解绑】对应'system_role_list:cancel'
					  * 	【流转】按钮则对应一个空值，占位使用。
					  */
	          		securityKey : ['system_role_list:submit' , 'system_role_list:cancel' , '' , '' , ''    ],
	          		/** userPageBtns：非必选项。与securityKey属性结合使用 - Yangcl
	          		 * 	系统用户在当前页面所拥有的权限；与securityKey配合使用。
	          		 * 	只用在以layer.open所打开的弹框中，以【数组】形式保存。
	          		 */
	          		userPageBtns : window.parent.layui.setter.pageBtns,  // 因为是iframe嵌套所以这里获取父窗体的setter对象(此页面在子窗体中嵌套)。
	          		
	          		success: function(layero, index){	// 弹层绘制完成后的回调方法，携带两个参数，分别是当前层DOM当前层索引。
	          		},
          			end : function(){  // 弹窗销毁后的回调函数
	          		},
	          		yes : function(index , layero){
	          			 // 按钮【提交】的回调
	          			layer.alert( obj.msg , {title:'操作成功 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4} , function(a){
	          				$(".layui-laypage-btn").click();  // 定位在当前页同时刷新数据
		            		layer.close(a);
		            		layer.close(index);
	            		});
	          		},
          			btn2 : function(index , layero){ 		// 按钮【取消】的回调。return false 开启该代码可禁止点击该按钮关闭
          				layer.confirm('您确定要删除吗？' , { title:'系统提示', icon:7, skin: 'layui-layer-molv', anim:4 , btn : [ '确定', '取消' ] }, 
          					function(index , ele) {  // 确定按钮
          							
      						}, 
      						function(index , ele) {  // 取消按钮  
      							layer.close(index);
      						});
          			}, 
          			cancel : function(){  // 右上角关闭回调。return false; // 开启该代码可禁止点击该按钮关闭
          			}
		        });
			},
			
			demoPrompt : function(o){
				layer.prompt(
					{
						title: '系统提示：*******************', 
						formType: 0, 	 // 输入框类型。0：文本 | 1：密码(默认) | 2：多行文本
						maxlength: 40, // 可输入文本的最大长度，默认500
					}, 
					function(value , index){
						layer.close(index);
			        }
				);
			},
		}

	});











