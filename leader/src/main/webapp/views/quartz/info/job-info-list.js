/**
 * 矩阵系统配置 / 分布式定时任务 / 定时任务列表
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
  	      	title: '定时任务列表',
  	      	url : layui.setter.path + 'quartz/ajax_job_info_list.do',
  	      	toolbar: '#table-search-toolbar',
  	      	height: 'full-100', 
 	      	limit: 16,
  	      	cols: [
 	    	  	[
 	  	         	{
 	  	         		field:'jobTitle', 
 	  	         		title:'任务名称 | 标题', 
 	  	         		width:340,
 	  	         		unresize: true,
 	  	         		templet: function(res){
	 	  	         		var html_ = res.jobName + '</br> <a>' + res.jobTitle +'</a>';
					  		return html_;
 	  	         		}
 	  	         	}, 
 	  	         	{field:'jobTriger', title:'触发器规则'},
 	  	         	{
 	  	         		field:'beginTime', 
 	  	         		title:'时间状态', 
 	  	         		width:240,
 	  	         		unresize: true,
 	  	         		templet: function(res){
	 	  	         		var html_ = '<p style="margin:0px;">开始时间: ' + res.beginTime + '</p>'
								+ '<p style="margin:0px;">结束时间: ' + res.endTime + '</p>'
								+ '<p style="margin:0px;">下次时间: ' + res.nextTime + '</p>';
					  		return html_;
 	  	         		}
 	  	         	}, 
 	  	         	{
 	  	         		field:'groupName', 
 	  	         		title:'任务组', 
 	  	         		width:180,
 	  	         		templet: function(res){
	 	  	         		var html_ = '<p style="margin:0px;">组: ' + res.groupName + '</p>'
								+ '<p style="margin:0px;">IP: ' + res.ip + '</p>';
					  		return html_;
 	  	         		}
 	  	         	}, 
 	  	         	{
 	  	         		field:'expireTime', 
 	  	         		title:'锁有效时间' ,
 	  	         		width:100, 
 	  	         		unresize: true,
 	  	         		templet: function(res){
 	  	         			return res.expireTime +  '(秒)';
 	  	         		}
 	  	         	},
 	  	         	{
 	  	         		field:'timeOut', 
 	  	         		title:'锁超时时间' ,
 	  	         		width:100, 
 	  	         		unresize: true,
 	  	         		templet: function(res){
 	  	         			return res.timeOut +  '(毫秒)';
 	  	         		}
 	  	         	},
 	  	         	{
						 field:'trigerType', 
						 title:'被触发', 
						 width:80, 
 	  	         		 unresize: true,
						 templet: function(res){
							 var triger = '否';
							 if(res.trigerType == 2){
								 triger = '是';
							 }
					  		 return triger;
						 }
					 },
 	  	         	 {
						 field:'logType', 
						 title:'日志', 
						 width:70, 
	 	  	         	 unresize: true,
						 templet: function(res){
							 var log_ = '记录';
							 if(res.logType == 1){
								log_ = '否';
							 }
					  		 return log_;
						 }
					 },
 	  	         	 {
						 field:'pause', 
						 title:'状态', 
						 width:70, 
	 	  	         	 unresize: true,
						 templet: function(res){
							 var pause = '运行';
							 if(res.pause == 1){
								 pause = '<p style="margin:0px;color:red">暂停</p>';
							 }
					  		 return pause;
						 }
					 },
 	  	         	{fixed: 'right', title:'操作', toolbar: '#table-btn-toolbar',width:160}
 	    	  	]
  	      	],
  	    	page: true
  	    });
  	    
		// 头部工具栏事件  TODO 全部暂停、全部恢复两个按钮
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
				case 'pause-all-job':
					pageDialog.addDialog(o.key);
					break;
				case 'resume-all-job':
					pageDialog.addDialog(o.key);
					break;					
			}
		});

		// 监听行工具事件 	详情 修改 手动 删除 恢复 日志
		table.on('tool(table-toolbar)', function(o) {
			if (o.event === 'detail') {
				pageDialog.detailDialog(o);
			} else if (o.event === 'edit') {
				pageDialog.editDialog(o);
			}else if (o.event === 'exec') {
			}else if (o.event === 'del') {
			}else if (o.event === 'resume') {
			}else if (o.event === 'log') {
			}
		});
		
		
		// 查询按钮
		var search = {		
			reload : function() {
				var jobName = $('#job-name').val();
				var jobTitle = $('#job-title').val();
				table.reload('page-table-reload', {
					page : {
						curr : 1  // 重新从第 1 页开始
					},
					where : {  // 此参数会合并请求到后台
						jobName:jobName,
						jobTitle:jobTitle
					}
				}, 'data');
				
				$('#job-name').val(jobName);
				$('#job-title').val(jobTitle);
			},
			
			reset : function(){
				$('#job-name').val('');
				$('#job-title').val('');
				search.reload();
			}
		};
		
		
		// 页面弹窗对象
		var pageDialog = {
			// 添加按钮需要进行强数据验证
			addDialog : function(securityKey){
				layer.open({
					title : '添加定时任务',
		          	type : 1,	// 1：解析HTML代码段；2：解析url
		          	area : ['920px', '550px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawDialogPage('add' , securityKey , null),
	          		anim : 0 ,		// 弹窗从上掉落
	          		btn : ['提交' , '取消'],
	          		yes : function(index , layero){
	          			var url_ =  layui.setter.path + 'quartz/ajax_btn_job_info_add.do';
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
					title : '修改定时任务信息',
		          	type : 1,	// 1：解析HTML代码段；2：解析url
		          	area : ['920px', '550px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawDialogPage('edit' , o.key , o.data),
	          		anim : 0 ,		// 弹窗从上掉落
	          		btn : ['提交' , '取消'],
	          		yes : function(index , layero){
	          			var url_ =  layui.setter.path + 'quartz/ajax_btn_job_info_edit.do';
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
			
			detailDialog : function(o){
				layer.open({
					title : '定时任务详情',
		          	type : 1,	// 1：解析HTML代码段；2：解析url
		          	area : ['920px', '600px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawJobInfo('detail' , o.key , o.data),
	          		anim : 0 ,		// 弹窗从上掉落
	          		btn : ['提交' , '取消'],
	          		yes : function(index , layero){
	          			
	          		},
          			btn2 : function(index, layero){ // 按钮【取消】的回调
          				//return false 开启该代码可禁止点击该按钮关闭
          			}, 
          			cancel : function(){  // 右上角关闭回调
          				// return false; // 开启该代码可禁止点击该按钮关闭
          			}
		        });
			},
			
			deleteJobInfo:function(o){
	        	layer.confirm('您确定要删除【' + o.data.jobTitle + '】这条定时任务吗？' , 
	        			{ title:'系统提示', icon:7, skin: 'layui-layer-molv', anim:4 , btn : [ '确定', '取消' ] }, 
					function(index , ele) {  // 确定按钮
						var type_ = 'post';
			            var url_ = layui.setter.path + 'quartz/ajax_btn_job_info_delete.do';
			        	var data_ = {
								jobName:o.data.jobName,
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
				var alert = '';
				var jobTitle = '';
				var jobTriger = '';
				var remark = '';
				var runGroupId = '';
				var ctypeNo = '';   // 并发执行
				var ctypeYes = '';   // 并发执行
				var expireTime = '';
				var timeOut = '';
				var trigerTypeIn = '';
				var trigerTypeOut = '';
				var log = '';
				var unlog = '';
				var jobClass = '';
				var jobList = '';
				if(type == 'add') {
					ctypeNo = 'checked="checked" ';
					trigerTypeIn = 'checked="checked"';
					unlog = 'checked="checked"';
					alert = 'Scheduler中轮询选择否，则不会不会被Quartz定时器轮询';
				}else if(type == 'edit'){
					alert = "定时任务名称：" + e.jobName;
					id = e.id;
					jobTitle = e.jobTitle;
					jobTriger = e.jobTriger;
					remark = e.remark;
					runGroupId = e.runGroupId;
					if(e.concurrentType == 1){
						ctypeYes = 'checked="checked" '; 
					}else{
						ctypeNo = 'checked="checked" ';
					}
					expireTime = e.expireTime;
					timeOut = e.timeOut;
					if(e.trigerType == 1){		// Scheduler中轮询状态的任务|1是 2否| 2这种任务不会加入到scheduler中触发式的执行,只会被默认调用
						trigerTypeIn = 'checked="checked"';
					}else{
						trigerTypeOut = 'checked="checked"';
					}
					if(e.logType == 1){		// 定时任务是否记录日志 1否 2是
						unlog = 'checked="checked"';
					}else{
						log = 'checked="checked"';
					}
					jobClass = e.jobClass;
					jobList = e.jobList;
				}
				
				var title = '<div class="dialog-form-title">';
						title += '<h3>' + alert + '</h3>';
					title += '</div>';
				
				var html = '<form id="dialog-api-form">' + title + '<table class="dialog-form-talbe" style="width:95%;margin-left:20px">';
					html += '<tr>';
						html += '<td style="text-align: left;width:80px;">任务标题：</td>';
						html += '<td style="text-align: left">';
							html += '<input type="text" name="jobTitle" value="' + jobTitle + '" class="dialog-form-input" style="width:90%;" placeholder="20个字符以内"  maxlength="20">';
						html += '</td>';
						html += '<td style="text-align: left;width:90px;">定时周期：</td>';
						html += '<td style="text-align: left">';
							html += '<input type="text" name="jobTriger" value="' + jobTriger + '" class="dialog-form-input" style="width:90%;" placeholder="请输入Cron表达式：http://cron.qqe2.com/"  maxlength="40">';
						html += '</td>';
					html += '</tr>';
					
					html += '<tr>';
						html += '<td style="text-align: left;width:160px;" colspan="1">定时周期描述：</td>';
						html += '<td style="text-align: left" colspan="3">';
							html += '<input type="text" name="remark" value="' + remark + '" class="dialog-form-input" style="width:96%;" placeholder="请输入Cron表达式的含义描述, 如：每天0点执行"  maxlength="512">';
						html += '</td>';
					html += '</tr>';
					
					html += '<tr>';
						html += '<td style="text-align: left;width:80px;">任务运行组：</td>';
						html += '<td style="text-align: left">';
							html += '<select name="runGroupId" class="job-group" style="width:94%;">';
								html += pageDialog.jobGroupList(runGroupId);
							html += '</select>';
						html += '</td>';
						html += '<td style="text-align: left;width:90px;">并发执行：</td>';
						html += '<td style="text-align: left">';
							html += '<span id="" class="field" style="padding-top:5px">';
								html += '<input type="radio" name="concurrentType" value="0" style="vertical-align:middle;" ' + ctypeNo + '>';
								html += '<span style="vertical-align:middle;margin-left:5px;">否</span>';
								html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
								html += '<input type="radio" name="concurrentType" value="1" style="vertical-align:middle;" ' + ctypeYes + '>';
								html += '<span style="vertical-align:middle;margin-left:5px;">是</span>';
							html += '</span>';
						html += '</td>';
					html += '</tr>';	
					
					html += '<tr>';
						html += '<td style="text-align: left;width:80px;">锁有效时间：</td>';
						html += '<td style="text-align: left">';
							html += '<input type="text" name="expireTime" value="' + expireTime +'" class="dialog-form-input" style="width:90%;" placeholder="单位：秒"  maxlength="2">';
						html += '</td>';
						html += '<td style="text-align: left;width:90px;">锁超时时间：</td>';
						html += '<td style="text-align: left">';
							html += '<input type="text" name="timeOut" value="' + timeOut + '" class="dialog-form-input" style="width:90%;"  placeholder="锁超时时间，单位：毫秒" maxlength="4">';
						html += '</td>';
					html += '</tr>';					
					
					html += '<tr>';
						html += '<td style="text-align: left;width:80px;">Scheduler中轮询：</td>';
						html += '<td style="text-align: left">';
							html += '<span id="" class="field" style="padding-top:5px">';
								html += '<input type="radio" name="trigerType" value="1" style="vertical-align:middle;" ' + trigerTypeIn + '>';
								html += '<span style="vertical-align:middle;margin-left:5px;">是</span>';
								html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
								html += '<input type="radio" name="trigerType" value="2" style="vertical-align:middle;" ' + trigerTypeOut + '>';
								html += '<span style="vertical-align:middle;margin-left:5px;">否</span>';
							html += '</span>';
						html += '</td>';
						html += '<td style="text-align: left;width:90px;">记录日志：</td>';
						html += '<td style="text-align: left">';
							html += '<span id="" class="field" style="padding-top:5px">';
								html += '<input type="radio" name="logType" value="1" style="vertical-align:middle;" ' + unlog + '>';
								html += '<span style="vertical-align:middle;margin-left:5px;">否</span>';
								html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
								html += '<input type="radio" name="logType" value="2" style="vertical-align:middle;" ' + log + '>';
								html += '<span style="vertical-align:middle;margin-left:5px;">是</span>';
							html += '</span>';
						html += '</td>';
					html += '</tr>';						
					
					html += '<tr>';
						html += '<td style="text-align: left;width:160px;" colspan="1">定时任务执行类路径：</td>';
						html += '<td style="text-align: left" colspan="3">';
							html += '<input type="text" name="jobClass" value="' + jobClass + '" class="dialog-form-input" style="width:96%;" placeholder="如：com.matrix.quartz.JobForTestOne"  maxlength="256">';
						html += '</td>';
					html += '</tr>';					
					
					html += '<tr>';
						html += '<td style="text-align: left;width:160px;" colspan="1">触发定时任务名称：</td>';
						html += '<td style="text-align: left" colspan="3">';
							html += '<input type="text" name="jobList" value="' + jobList + '" class="dialog-form-input" style="width:96%;" placeholder="UUID：UUID-9829384-0213410-OOKEIPAOA,UUID-9829384-0213410-OOKEIPAOB"  maxlength="2000">';
						html += '</td>';
					html += '</tr>';					
					
					
					if(type == 'edit'){
						html += '<input type="hidden" name="id" value="' + id + '">';
					}
					html += '<input type="hidden" name="eleValue" value="' + key + '">';
				html += '</table></form>';
				return html;
			},
			
			jobGroupList : function(runGroupId) {
				var html_ = '<option value="">请选择任务组</option>';
				var url_ =  layui.setter.path + 'quartz/ajax_job_group_list.do';
				var data_ = null;
				var obj = JSON.parse(layui.setter.ajaxs.sendAjax('post' , url_ , data_));
				if(obj.status == 'success') {
					 for(var i = 0 ; i < obj.list.length ; i ++){
						 if(obj.list[i].id == runGroupId){
							 html_ += '<option value="' + obj.list[i].id + '" selected="selected">' + obj.list[i].groupName + '</option>'
						 }else{
							 html_ += '<option value="' + obj.list[i].id + '">' + obj.list[i].groupName + '</option>'
						 }
						 
					 }
				}
				return html_;
			},
			
			// 详情信息展示
			drawJobInfo : function(type , key , e){
				var ctypeNo = '';   // 并发执行
				var ctypeYes = '';   // 并发执行
				var trigerTypeIn = '';
				var trigerTypeOut = '';
				var log = '';
				var unlog = '';
				var pause = '运行';
				 if(e.pause == 1) {
					 pause = '暂停';
				 }
				 var alert = "任务运行状态：" + pause;
				 var runGroupId = e.runGroupId;
				 if(e.concurrentType == 1){
					 ctypeYes = 'checked="checked" '; 
				 }else{
					 ctypeNo = 'checked="checked" ';
				 }
				 if(e.trigerType == 1){		// Scheduler中轮询状态的任务|1是 2否| 2这种任务不会加入到scheduler中触发式的执行,只会被默认调用
					 trigerTypeIn = 'checked="checked"';
				 }else{
					 trigerTypeOut = 'checked="checked"';
				 }
				 if(e.logType == 1){		// 定时任务是否记录日志 1否 2是
					 unlog = 'checked="checked"';
				 }else{
					 log = 'checked="checked"';
				 }
				
				var title = '<div class="dialog-form-title">';
						title += '<h3>' + alert + '</h3>';
					title += '</div>';
				
				var html = '<form id="dialog-api-form">' + title + '<table class="dialog-form-talbe" style="width:95%;margin-left:20px">';
					html += '<tr>';
						html += '<td style="text-align: left;width:160px;" colspan="1">任务名称：</td>';
						html += '<td style="text-align: left" colspan="3">';
							html += '<input type="text" value="' + e.jobName + '" class="dialog-form-input" style="width:96%;" >';
						html += '</td>';
					html += '</tr>';
					html += '<tr>';
						html += '<td style="text-align: left;width:80px;">任务标题：</td>';
						html += '<td style="text-align: left">';
							html +=  e.jobTitle;
						html += '</td>';
						html += '<td style="text-align: left;width:90px;">定时周期：</td>';
						html += '<td style="text-align: left">';
							html += e.jobTriger;
						html += '</td>';
					html += '</tr>';
					
					html += '<tr>';
						html += '<td style="text-align: left;width:160px;" colspan="1">定时周期描述：</td>';
						html += '<td style="text-align: left" colspan="3">';
							html += e.remark;
						html += '</td>';
					html += '</tr>';
					
					html += '<tr>';
						html += '<td style="text-align: left;width:80px;">任务运行组：</td>';
						html += '<td style="text-align: left">';
							html += '<select name="runGroupId" class="job-group" style="width:94%;">';
								html += pageDialog.jobGroupList(runGroupId);
							html += '</select>';
						html += '</td>';
						html += '<td style="text-align: left;width:90px;">并发执行：</td>';
						html += '<td style="text-align: left">';
							html += '<span id="" class="field" style="padding-top:5px">';
								html += '<input type="radio" name="concurrentType" value="0" style="vertical-align:middle;" ' + ctypeNo + '>';
								html += '<span style="vertical-align:middle;margin-left:5px;">否</span>';
								html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
								html += '<input type="radio" name="concurrentType" value="1" style="vertical-align:middle;" ' + ctypeYes + '>';
								html += '<span style="vertical-align:middle;margin-left:5px;">是</span>';
							html += '</span>';
						html += '</td>';
					html += '</tr>';	
					
					html += '<tr>';
						html += '<td style="text-align: left;width:80px;">锁有效时间：</td>';
						html += '<td style="text-align: left">';
							html += e.expireTime;
						html += '</td>';
						html += '<td style="text-align: left;width:90px;">锁超时时间：</td>';
						html += '<td style="text-align: left">';
							html += e.timeOut;
						html += '</td>';
					html += '</tr>';					
					
					html += '<tr>';
						html += '<td style="text-align: left;width:80px;">Scheduler中轮询：</td>';
						html += '<td style="text-align: left">';
							html += '<span id="" class="field" style="padding-top:5px">';
								html += '<input type="radio" name="trigerType" value="1" style="vertical-align:middle;" ' + trigerTypeIn + '>';
								html += '<span style="vertical-align:middle;margin-left:5px;">是</span>';
								html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
								html += '<input type="radio" name="trigerType" value="2" style="vertical-align:middle;" ' + trigerTypeOut + '>';
								html += '<span style="vertical-align:middle;margin-left:5px;">否</span>';
							html += '</span>';
						html += '</td>';
						html += '<td style="text-align: left;width:90px;">记录日志：</td>';
						html += '<td style="text-align: left">';
							html += '<span id="" class="field" style="padding-top:5px">';
								html += '<input type="radio" name="logType" value="1" style="vertical-align:middle;" ' + unlog + '>';
								html += '<span style="vertical-align:middle;margin-left:5px;">否</span>';
								html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
								html += '<input type="radio" name="logType" value="2" style="vertical-align:middle;" ' + log + '>';
								html += '<span style="vertical-align:middle;margin-left:5px;">是</span>';
							html += '</span>';
						html += '</td>';
					html += '</tr>';						
					
					html += '<tr>';
						html += '<td style="text-align: left;width:160px;" colspan="1">定时任务执行类路径：</td>';
						html += '<td style="text-align: left" colspan="3">';
							html += e.jobClass;
						html += '</td>';
					html += '</tr>';					
					
					html += '<tr>';
						html += '<td style="text-align: left;width:160px;" colspan="1">触发定时任务名称：</td>';
						html += '<td style="text-align: left" colspan="3">';
							html += e.jobList;
						html += '</td>';
					html += '</tr>';	
					
					html += '<tr>';
						html += '<td style="text-align: left;width:80px;">创建时间：</td>';
						html += '<td style="text-align: left">';
							html +=  e.createTime;
						html += '</td>';
						html += '<td style="text-align: left;width:90px;">创建人：</td>';
						html += '<td style="text-align: left">';
							html += e.createUserName;
						html += '</td>';
					html += '</tr>';
					
					html += '<tr>';
						html += '<td style="text-align: left;width:80px;">更新时间：</td>';
						html += '<td style="text-align: left">';
							html +=  e.updateTime;
						html += '</td>';
						html += '<td style="text-align: left;width:90px;">更新人：</td>';
						html += '<td style="text-align: left">';
							html += e.updateUserName;
						html += '</td>';
					html += '</tr>';
				html += '</table></form>';
				return html;
			},
		}

	});











