/**
 * 矩阵系统配置 / Spring-Cloud-Gateway / 路由规则列表
 */
layui.config({
    	base: '../layuiadmin/' //静态资源所在路径
  	}).extend({
    	index: 'lib/index' //主入口模块
  	}).use(['index','laydate', 'table'], function(){
  	    var table = layui.table;
  	  	var $ = layui.$;
  	  	var layer = layui.layer;				// layer.pageDialog = pageDialog; 弹窗调用父页面保存的对象，需要写在脚本最后
  	  	var laydate = layui.laydate;
		
  	    table.render({
  	    	id: 'page-table-reload',  			// 页面查询按钮需要table.reload 
  	      	elem: '#table-toolbar',				// 表格控制句柄
  	      	title: '路由规则列表',
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

		// 监听行工具事件 	详情 修改 手动 删除 恢复 日志
		table.on('tool(table-toolbar)', function(o) {
			if (o.event === 'detail') {
				pageDialog.detailDialog(o);
			} else if (o.event === 'edit') {
				pageDialog.editDialog(o);
			}else if (o.event === 'exec') {
			}else if (o.event === 'del') {
				pageDialog.deleteJobInfo(o);
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
					title : '添加网关路由规则',
		          	type : 1,	// 1：解析HTML代码段；2：解析url
		          	area : ['920px', '650px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawDialogPage('add' , securityKey , null),
	          		anim : 0 ,		// 弹窗从上掉落
	          		btn : ['提交' , '取消'],
	          		yes : function(index , layero){
	          			var url_ =  layui.setter.path + 'gateway/ajax_btn_gateway_route_add.do';
						var data_ = $("#dialog-gateway-form").serializeArray();
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
          			},
          			success: function(layero, index) {
          				// 因为是弹窗，所以重新渲染时间空间
          				laydate.render({
          					elem: '#test5',
          					type: 'datetime'
          				});
          			}
		        });
			},
			
			
	        // 绘制添加和编辑弹框
			drawDialogPage : function(type , key , e){
				var id = "";
				var alert = '';
				var routeId = '';
				var uri = '';
				var remark = '';
				var env = '';
				var requestType = '';
				var routeTypeProject = ''; 
				var routeTypeUrl = ''; 
				var rateFlowMark0 = '';
				var rateFlowMark1 = '';
				var requestSnapshotMark0 = '';
				var requestSnapshotMark1 = '';
				
				
				var expireTime = '';
				var timeOut = '';
				var trigerTypeIn = '';
				var trigerTypeOut = '';
				var log = '';
				var unlog = '';
				var jobClass = '';
				var jobList = '';
				if(type == 'add') {
					alert = '网关转发规则请务必区分生产环境！';
				}else if(type == 'edit'){
					alert = "路由规则描述：" + e.description;
					id = e.id;
					routeId = e.routeId;
					uri = e.uri;
					env = e.active;
					requestType = e.requestType;
					if(e.routeType == 'project'){
						routeTypeProject = 'checked="checked" '; 
					}else{
						routeTypeUrl = 'checked="checked" ';
					}
					
					if(e.rateFlowMark == 0){
						rateFlowMark0 = 'checked="checked" ';
					}else{
						rateFlowMark1 = 'checked="checked" ';
					}
					
					if(e.requestSnapshotMark == 0){
						requestSnapshotMark0 = 'checked="checked" ';
					}else{
						requestSnapshotMark1 = 'checked="checked" ';
					}
					
					
				}
				
				var title = '<div class="dialog-form-title">';
						title += '<h3>' + alert + '</h3>';
					title += '</div>';
				
				var html = '<form id="dialog-gateway-form">' + title;
					html +=  '<table id="dialog-gateway-table" class="dialog-form-talbe" style="width:95%;margin-left:20px">';
						html += '<tr>';
							html += '<td style="text-align: left;width:80px;">路由规则ID：</td>';
							html += '<td style="text-align: left">';
								html += '<input type="text" autocomplete="off" name="routeId" value="' + routeId + '" class="dialog-form-input" style="width:90%;" placeholder="52个字符以内"  maxlength="52">';
							html += '</td>';
							html += '<td style="text-align: left;width:80px;">生产环境：</td>';
							html += '<td style="text-align: left">';
								html += '<select name="active" class="active-select" style="width:94%;">';
									html += pageDialog.envList(env);
								html += '</select>';
							html += '</td>';
						html += '</tr>';
						
						html += '<tr>';
							html += '<td style="text-align: left;width:160px;" colspan="1">路由转发路径：</td>';
							html += '<td style="text-align: left" colspan="3">';
								html += '<input type="text" autocomplete="off" name="uri" value="' + uri + '" class="dialog-form-input" style="width:96%;" placeholder="256个字符以内"  maxlength="256">';
							html += '</td>';
						html += '</tr>';
						
						html += '<tr>';
							html += '<td style="text-align: left;width:80px;">请求方式：</td>';
							html += '<td style="text-align: left">';
								html += '<select name="requestType" class="request-type-select" style="width:94%;">';
									html += pageDialog.requestTypeList(requestType);
								html += '</select>';
							html += '</td>';
							
							html += '<td style="text-align: left;width:90px;">路由类型：</td>';
							html += '<td style="text-align: left">';
								html += '<span id="" class="field" style="padding-top:5px">';
									html += '<input type="radio" name="routeType" value="project" style="vertical-align:middle;" ' + routeTypeProject + '>';
									html += '<span style="vertical-align:middle;margin-left:5px;">项目</span>';
									html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
									html += '<input type="radio" name="routeType" value="url" style="vertical-align:middle;" ' + routeTypeUrl + '>';
									html += '<span style="vertical-align:middle;margin-left:5px;">单接口</span>';
								html += '</span>';
							html += '</td>';
						html += '</tr>';	
						
						html += '<tr>';
							html += '<td style="text-align: left;width:160px;" colspan="1">是否保存流量快照：</td>';
							html += '<td style="text-align: left" colspan="3">';
								html += '<span id="" class="field" style="padding-top:5px">';
									html += '<input type="radio" name="requestSnapshotMark" value="0" style="vertical-align:middle;" ' + requestSnapshotMark0 + '>';
									html += '<span style="vertical-align:middle;margin-left:5px;">不保存</span>';
									html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
									html += '<input type="radio" name="requestSnapshotMark" value="1" style="vertical-align:middle;" ' + requestSnapshotMark1 + ' onclick="layer.pageDialog.rateFlowMarkModule(\'dialog-gateway-table\')">';
									html += '<span style="vertical-align:middle;margin-left:5px;">保存(用于模拟压测使用)</span>';
								html += '</span>';
							html += '</td>';
						html += '</tr>';	
						
						html += '<tr>';
							html += '<td style="text-align: left;width:80px;" colspan="1">快照请求条数：</td>';
							html += '<td style="text-align: left" colspan="3">';
								html += '<input type="text" autocomplete="off" name="routeId" value="' + routeId + '" class="dialog-form-input" style="width:30%;" placeholder="请输入整数"  maxlength="7">';
							html += '</td>';
						html += '</tr>';
						html += '<tr>';
							html += '<td style="text-align: left;width:80px;" colspan="1">快照时间段：</td>';
							html += '<td style="text-align: left" colspan="3">';
								html += '<div class="layui-input-inline"><input  id="test5" type="text" class="layui-input" placeholder="yyyy-MM-dd HH:mm:ss" lay-key="7"></div>'
							html += '</td>';
						html += '</tr>';
						
						
						
						html += '<tr>';
							html += '<td style="text-align: left;width:160px;" colspan="1">是否开启流量标记：</td>';
							html += '<td style="text-align: left" colspan="3">';
								html += '<span id="" class="field" style="padding-top:5px">';
									html += '<input type="radio" name="rateFlowMark" value="0" style="vertical-align:middle;" ' + rateFlowMark0 + '>';
									html += '<span style="vertical-align:middle;margin-left:5px;">不标记</span>';
									html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
									html += '<input type="radio" name="rateFlowMark" value="1" style="vertical-align:middle;" ' + rateFlowMark1 + ' onclick="layer.pageDialog.rateFlowMarkModule(\'dialog-gateway-table\')">';
									html += '<span style="vertical-align:middle;margin-left:5px;">标记</span>';
								html += '</span>';
							html += '</td>';
						html += '</tr>';	
					html += '</table>';
					
					
					
					if(type == 'edit'){
						html += '<input type="hidden" name="id" value="' + id + '">';
					}
					html += '<input type="hidden" name="eleValue" value="' + key + '">';
				html += '</form>';
				return html;
			},
			
			envList : function(env) {
				var html_ = '<option value="">请选择生产环境</option>';
				html_ += '<option value="dev">dev</option>'
				html_ += '<option value="test">test</option>'
				html_ += '<option value="pre">pre</option>'
				html_ += '<option value="prod">prod</option>'
				return html_;
			},
			
			requestTypeList : function(requestType) {
				var html_ = '<option value="">请选择请求方式</option>';
				html_ += '<option value="get">get</option>'
				html_ += '<option value="post">post</option>'
				html_ += '<option value="put">put</option>'
				html_ += '<option value="delete">delete</option>'
				return html_;
			},
			
			/**
			 * 如果开启流量标记，则展示此组建
			 */
			rateFlowMarkModule : function(trId, a){
				this.rateFlowMarkModuleTable(trId);
				
			},
			
			rateFlowMarkModuleTable : function(trId, a){
				if($(".rate-flow-mark").length != 0){
					return;
				}
				var html = '<tr class="rate-flow-mark">';
					html += '<td style="text-align: left" colspan="3">';
						html += '<input type="text" name="name" value="" class="dialog-form-input" style="width:96%;" placeholder="请输入统计规则描述，25个字符以内"  maxlength="25">';
					html += '</td>';
					html += '<td style="text-align: right" colspan="1">';
						html += '<button class="layui-btn layui-btn-sm">添&nbsp&nbsp&nbsp&nbsp&nbsp加</button>';
					html += '</td>';
				html += '</tr>'
					
				html += '<tr class="" style="">';	
				html += '<table id="aaaaaaaaaa" style="border-collapse: collapse;width:95%;margin-left:20px; border-bottom: 1px solid red;">';
					html += '<tr class="" style="">';
						html += '<th>';
							html += '统计项目';
						html += '</th>';
						html += '<th>';
							html += '统计接口';
						html += '</th>';
						html += '<th>';
							html += '统计参数';
						html += '</th>';
						html += '<th>';
							html += '统计值';
						html += '</th>';
						html += '<th>';
							html += '操作';
						html += '</th>';
					html += '</tr>';
				html += '</table>';
				html += '</tr>'
				$("#" + trId).append(html);
			}
			
			
			
		};
		
		layer.pageDialog = pageDialog;
	});











