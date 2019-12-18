/**
 * 系统权限配置 / 系统用户相关 / 系统用户列表
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
      	title: '系统用户列表',
      	url : layui.setter.path + 'userInfo/ajax_system_user_list.do',
      	toolbar: '#table-search-toolbar',
      	height: 'full-100', 
      	limit: 16,
      	cols: [
      		[
      			/* {type: 'checkbox', fixed: 'left'}, */
  	         	{field:'id', title:'ID', width:100,sort: true},  // 	unresize: true,  fixed: 'left', 
  	         	{field:'userName', title:'用户名', width:120},
				{field:'email', title:'E-mail', width:200 },
	        	{
					 field:'sex', 
					 title:'性别', 
					 width:80, 
					 sort: true, 
					 templet: function(res){
						 var html_ = '男';
						 if(res.sex == 2){
							 html_ = '女';
						 }
				  		 return '<a>'+ html_ +'</a>'
					 }
				 },
	  	         {field:'remark', title:'备注'},
	  	         {field:'type', title:'用户类型', width:100, sort: true},
	  	         {field:'mobile', title:'手机号码', width:120, sort: true},
	  	         {field:'createTime', title:'加入时间', width:160},
	  	         {fixed: 'right', title:'操作', toolbar: '#table-btn-toolbar', width:240}
    	  	]
      	],
      	page: true	
    });
    
	// 头部工具栏事件
    table.on('toolbar(table-toolbar)', function(o){
    	switch (o.event) {
			case 'search':
				search.reload();
				break;
			case 'reset':
				search.reset();
				break;
			case 'add':
				pageDialog.addDialog(o);
				break;
			case 'reload':
				pageDialog.userInfoReload(o);
				break;
		}
    });
    
    // 监听行工具事件
    table.on('tool(table-toolbar)', function(o){
    	if (o.event === 'del') {
			pageDialog.deleteSystemUser(o);
		} else if (o.event === 'edit') {
			pageDialog.editDialog(o);
		}else if (o.event === 'password') {
			pageDialog.resetPassword(o)
		}else if(o.event === 'userRoleList'){
			pageDialog.userRoleList(o);
		}
    });
    
    
    // 查询按钮
	var search = {		
		reload : function() {
			var userName = $('#user-name').val();
			var mobile = $('#mobile').val();
			table.reload('page-table-reload', {
				page : {
					curr : 1  // 重新从第 1 页开始
				},
				where : {
					userName:userName,
					mobile:mobile
				}
			}, 'data');
			
			$('#user-name').val(userName);
			$('#mobile').val(mobile);
		},
		
		reset : function(){
			$('#user-name').val('');
			$('#mobile').val('');
			search.reload();
		}
	};
	
	var pageDialog = {
		// 添加按钮需要进行强数据验证
		addDialog : function(o){
			layer.open({
				title : '添加系统用户',
	          	type : 1,	// 1：解析HTML代码段；2：解析url
	          	area : ['600px', '450px'],
	          	fixed : false,
	          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
	          	resize : false,        // 是否允许拉伸 默认：true
          		content : pageDialog.drawDialogPage('add' , o.key , null),
          		anim : 0 ,		// 弹窗从上掉落
          		btn : ['提交' , '取消'],
          		yes : function(index , layero){
          			var url_ =  layui.setter.path + 'userInfo/ajax_btn_add_system_user.do';
					var data_ = $("#dialog-user-form").serializeArray();
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
				title : '修改用户信息',
	          	type : 1,	// 1：解析HTML代码段；2：解析url
	          	area : ['600px', '450px'],
	          	fixed : false,
	          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
	          	resize : false,        // 是否允许拉伸 默认：true
          		content : pageDialog.drawDialogPage('edit' , o.key , o.data),
          		anim : 0 ,		// 弹窗从上掉落
          		btn : ['提交' , '取消'],
          		yes : function(index , layero){
					var url_ =  layui.setter.path + 'userInfo/ajax_btn_edit_sys_user.do';
					var data_ = $("#dialog-user-form").serializeArray();
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
		
		// 重置密码
		resetPassword : function(o){
			layer.prompt(
				{
					title: '系统提示：您正在重置密码', 
					formType: 0, 	 // 输入框类型。0：文本 | 1：密码(默认) | 2：多行文本
					maxlength: 40, // 可输入文本的最大长度，默认500
				}, 
				function(value , index){
					var url_ =  layui.setter.path + 'userInfo/ajax_btn_password_reset.do';
					var data_ ={
							id:o.data.id,
							password:value,
		        			eleValue:o.key
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
		        }
			);
		},
		
		// 删除系统用户
	    deleteSystemUser : function(o){
			layer.confirm('您确定要删除这个用户【' + o.data.userName + '】吗？' , { title:'系统提示', icon:7, skin: 'layui-layer-molv', anim:4 , btn : [ '确定', '取消' ] }, 
				function(index , ele) {  // 确定按钮
					var type_ = 'post';
		            var url_ = layui.setter.path + 'userInfo/ajax_btn_delete_system_user.do';
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
		
		// 重置所有用户的信息
		userInfoReload : function(o){
			layer.confirm('您确定要重置所有用户的信息吗？' , { title:'系统提示', icon:7, skin: 'layui-layer-molv', anim:4 , btn : [ '确定', '取消' ] }, 
				function(index , ele) {  // 确定按钮
					var type_ = 'post';
		            var url_ = layui.setter.path + 'userInfo/ajax_btn_user_cache_reload.do';
		        	var data_ = {
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
		
		// 用户角色列表
		userRoleList : function(o){
			layer.open({
				title : '系统角色列表',
	          	type : 2,	// 1：解析HTML代码段；2：解析url
	          	area : ['1500px', '722px'],
	          	maxmin : true,				// 开启弹层最小化和最大化按钮
	          	fixed : false,
	          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
	          	resize : false,        // 是否允许拉伸 默认：true
          		content : layui.setter.path + 'permissions/dialog_permissions_system_role_list.do',
          		anim : 0 ,		// 弹窗从上掉落
          		success: function(layero, index){	// 弹层绘制完成后的回调方法，携带两个参数，分别是当前层DOM当前层索引。
                    var iframe = window['layui-layer-iframe' + index];  // 获取子页面的iframe
                    iframe.currentTr(o.data); // 向子页面的全局函数child传参
          		},
	        });
		},
		
		
		drawDialogPage : function(type , key , e){
			var id = "";
			var userName = "";
			var remark = "";
			var mobile = "";
			var email = "";
			var sex1 = "checked";
			var sex2 = "";
			var oldUserName = "";
			var qq = "";
			var idcard = "";
			if(type == 'edit'){
				id = e.id;
				userName = e.userName;
				remark = e.remark;
				mobile = e.mobile;
				email = e.email;
				if(e.sex == 2){
					sex1 = "";
					sex2 = "checked";
				}
				qq = e.qq;
				idcard = e.idcard;
				oldUserName = e.userName;
			}
			
			var html = '<form id="dialog-user-form"><table class="dialog-form" style="width:100%">';
				html += '<tr>';
					html += '<td align="right" style="width:195px">用户名称：</td>';
					html += '<td align="left"><input type="text" id="userName" name="userName" value="' + userName + '" placeholder="用户名称 20个字以内 " autocomplete="off" style="margin-bottom: 10px;" maxlength="20"></td>';
				html += '</tr>';
				if(type == 'add'){
					html += '<tr>';
						html += '<td align="right">默认密码：</td>';
						html += '<td align="left"><input type="text" id="password" name="password" value="123456" placeholder="默认密码123456 密码45个字以内 " autocomplete="off" style="margin-bottom: 10px;" maxlength="45"></td>';
					html += '</tr>';
				}else{
					html += '<tr>';
						html += '<td align="right">Q Q号码：</td>';
						html += '<td align="left"><input type="text" id="qq" name="qq" value="' + qq + '" placeholder="20个字以内 " autocomplete="off" style="margin-bottom: 10px;" maxlength="20"></td>';
					html += '</tr>';
					html += '<tr>';
						html += '<td align="right">身份证号：</td>';
						html += '<td align="left"><input type="text" id="idcard" name="idcard" value="' + idcard + '" placeholder="身份证号18个字以内 " autocomplete="off" style="margin-bottom: 10px;" maxlength="18"></td>';
					html += '</tr>';
				}
				
				html += '<tr>';
					html += '<td align="right">备注描述：</td>';
					html += '<td align="left"><input type="text" id="remark" name="remark" value="' + remark + '" placeholder="备注描述50个字以内 " autocomplete="off" style="margin-bottom: 10px;" maxlength="50"></td>';
				html += '</tr>';
				html += '<tr>';
					html += '<td align="right">手机号码：</td>';
					html += '<td align="left"><input type="text" id="mobile" name="mobile" value="' + mobile + '" placeholder="手机号码 11个字" autocomplete="off" style="margin-bottom: 10px;" maxlength="11"></td>';
				html += '</tr>';	
				html += '<tr>';
					html += '<td align="right">电子邮箱：</td>';
					html += '<td align="left"><input type="text" id="email" name="email" value="' + email + '" placeholder="电子邮箱 50个字以内 " autocomplete="off" style="margin-bottom: 10px;" maxlength="50"></td>';
				html += '</tr>';	
				html += '<tr>';
					html += '<td align="right">用户性别：</td>';
					html += '<td align="left"><input type="radio" name="sex" value="1"style="vertical-align:middle;" ' + sex1 + '><span style="vertical-align:middle;">男</span>';
					html += '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp';
					html += '<input type="radio" name="sex" value="2"style="vertical-align:middle;" ' + sex2 + '><span style="vertical-align:middle;">女</span></td>';
				html += '</tr>';	
				
				
				if(type == 'add'){
					html += pageDialog.drawPlatformRadio();
				}else{
					html += '<input type="hidden" name="id" value="' + id + '">';
					html += '<input type="hidden" name="userNameOld" value="' + oldUserName + '">';
				}
				html += '<input type="hidden" name="eleValue" value="' + key + '">';
			html += '</table></form>';
			
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
							val_ = 'leader@' + sflist[i].platform;
							isChecked = ' checked ';
						}else{
							val_ = 'admin@' + sflist[i].platform;
						}
						html_ += '<input type="radio" ' + isChecked + ' name="platform" value="' + val_ +'"style="vertical-align:middle;">';
						html_ += '<span style="vertical-align:middle;">' + sflist[i].name +'</span>&nbsp&nbsp&nbsp&nbsp&nbsp';
					}
				}
			}
			return html_ + '</td></tr>';
		},
	
	
	
	
	
	};
	
	
	
	
	
});

































