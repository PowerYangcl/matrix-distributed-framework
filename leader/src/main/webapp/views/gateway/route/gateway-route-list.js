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
  	      	url : layui.setter.path + 'gateway/ajax_gateway_route_list.do',
  	      	toolbar: '#table-search-toolbar',
  	      	height: 'full-100', 
 	      	limit: 16,
  	      	cols: [
 	    	  	[
 	  	         	{
 	  	         		field:'routeId', 
 	  	         		title:'描述 | 标题|请求类型', 
 	  	         		width:200,
 	  	         		unresize: true,
 	  	         		templet: function(res){
	 	  	         		var html_ = res.description + '</br> <a>' + res.routeId +'</a></br>' + res.requestType +'</a>';
					  		return html_;
 	  	         		}
 	  	         	}, 
 	  	         	{field:'active', width:80, title:'环境'},
 	  	         	{
 	  	         		field:'predicateList', 
 	  	         		title:'断言规则', 
 	  	         		unresize: true,
 	  	         		templet: function(res){
 	  	         			var arr = res.predicateList;
 	  	         			var html_ = '<p style="margin:0px;">转发路径:  ' + res.uri + '</p>';
 	  	         			for(var i = 0 ; i < arr.length ; i ++){
 	  	         				html_ += '<p style="margin:0px;">' + arr[i].predicate + ':  ' + arr[i].predicateValue + '</p>';
 	  	         			}
					  		return html_;
 	  	         		}
 	  	         	}, 
 	  	         	{
 	  	         		field:'requestSnapshotMark', 
 	  	         		title:'快照信息', 
 	  	         		width:225,
 	  	         		templet: function(res){
	 	  	         		var html_ = '<p style="margin:0px;">是否保存快照: ' + res.requestSnapshotMark + '</p>';
	 	  	         			html_ += '<p style="margin:0px;">保存数量: ' + res.snapshotCount + '</p>';
	 	  	         			html_ += '<p style="margin:0px;">开始时间: ' + res.snapshotBeginTime + '</p>';
	 	  	         			html_ += '<p style="margin:0px;">结束时间: ' + res.snapshotEndTime + '</p>';
					  		return html_;
 	  	         		}
 	  	         	}, 
 	  	         	{
 	  	         		field:'rateFlowMark', 
 	  	         		title:'流量标记', 
 	  	         		width:90,
 	  	         		templet: function(res){
 	  	         			return res.rateFlowMark;
 	  	         		}
 	  	         	},
					{
						 field:'status', 
						 title:'状态', 
						 width:80, 
						 unresize: true,
						 templet: function(res){
							 var status = '不生效';
							 if(res.status == '生效'){
								 status = '<p style="margin:0px;color:red">生效</p>';
							 }
					  		 return status;
						 }
					},
					{
						field:'requestSnapshotMark', 
						title:'创建信息', 
						width:225,
						templet: function(res){
					 		var html_ = '<p style="margin:0px;">创建人: ' + res.createUserName + '</p>';
					 			html_ += '<p style="margin:0px;">创建时间: ' + res.createTime + '</p>';
					 			html_ += '<p style="margin:0px;">更新人: ' + res.updateUserName + '</p>';
					 			html_ += '<p style="margin:0px;">更新时间: ' + res.updateTime + '</p>';
					  		return html_;
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
			}
		});

		
		// 查询按钮
		var search = {		
			reload : function() {
				var routeId = $('#route-id').val();
				var description = $('#description').val();
				table.reload('page-table-reload', {
					page : {
						curr : 1  // 重新从第 1 页开始
					},
					where : {  // 此参数会合并请求到后台
						routeId:routeId,
						description:description
					}
				}, 'data');
				
				$('#route-id').val(routeId);
				$('#description').val(description);
			},
			
			reset : function(){
				$('#route-id').val('');
				$('#description').val('');
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
		          	area : ['1200px', '650px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawDialogPage('add' , securityKey , null),
	          		anim : 0 ,		// 弹窗从上掉落
	          		btn : ['提交' , '取消'],
	          		yes : function(index , layero){
	          			var url_ =  layui.setter.path + 'gateway/ajax_btn_gateway_route_add.do';
						var data_ = $("#dialog-gw-form").serializeArray();
						
						var rfmArr = $("#rate-flow-mark-tbody tr");
						if(rfmArr.length != 0) {
							var rfmList = [];
							for(var i = 0; i < rfmArr.length; i ++){
								var rfm = new Object();
								var tds = rfmArr[i].children;
								rfm.type = $(tds[1]).find("select").val();
								rfm.target = $(tds[2]).find("input").val();
								rfm.param = $(tds[3]).find("input").val();
								rfm.value = $(tds[4]).find("input").val();
								rfm.statisticalDimension = $(tds[5]).find("select").val();
								rfm.name = $(tds[6]).find("input").val();
								rfmList.push(rfm);
							}
							data_.rfmList = rfmList;
						}
						
						var predicateArr = $("#gw-predicates-tbody tr");
						if(predicateArr.length != 0) {
							var predicateList = [];
							for(var i = 0; i < predicateArr.length; i ++){
								var e = new Object();
								var tds =  predicateArr[i].children;
								e.predicate = $(tds[1]).find("select").val();
								e.predicateValue = $(tds[2]).find("input").val();
								predicateList.push(e);
							}
							data_.predicateList = predicateList;
						}
						
						var o = new Object();
						for(var i = 0; i < data_.length; i ++){
							o[data_[i].name] = data_[i].value;
						}
						o.rfmList = data_.rfmList;
						o.predicateList = data_.predicateList;
						
						var obj = JSON.parse(layui.setter.ajaxs.sendAjaxContentType('post' , url_ , JSON.stringify(o), 'application/json'));
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
          				// 因为是弹窗，所以重新渲染时间控件
          				laydate.render(
      						{
      							elem: '#snapshot-begin-time',
      							type: 'datetime'
      						}
          				);
          				laydate.render(
      						{
      							elem: '#snapshot-end-time',
      							type: 'datetime'
      						}
          				);
          			}
		        });
			},
			
			// 编辑按钮对应的弹窗
			editDialog : function(o){
				layer.open({
					title : '修改网关路由规则',
		          	type : 1,	// 1：解析HTML代码段；2：解析url
		          	area : ['1200px', '650px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawDialogPage('edit' , o.key , o.data),  // ('edit' , o.key , o.data),
	          		anim : 0 ,		// 弹窗从上掉落
	          		btn : ['提交' , '取消'],
	          		yes : function(index , layero){
	          			var url_ =  layui.setter.path + 'gateway/ajax_btn_gateway_route_edit.do';
						var data_ = $("#dialog-gw-form").serializeArray();
						
						var rfmArr = $("#rate-flow-mark-tbody tr");
						if(rfmArr.length != 0) {
							var rfmList = [];
							for(var i = 0; i < rfmArr.length; i ++){
								var rfm = new Object();
								var tds = rfmArr[i].children;
								rfm.type = $(tds[1]).find("select").val();
								rfm.target = $(tds[2]).find("input").val();
								rfm.param = $(tds[3]).find("input").val();
								rfm.value = $(tds[4]).find("input").val();
								rfm.statisticalDimension = $(tds[5]).find("select").val();
								rfm.name = $(tds[6]).find("input").val();
								rfmList.push(rfm);
							}
							data_.rfmList = rfmList;
						}
						
						var predicateArr = $("#gw-predicates-tbody tr");
						if(predicateArr.length != 0) {
							var predicateList = [];
							for(var i = 0; i < predicateArr.length; i ++){
								var e = new Object();
								var tds =  predicateArr[i].children;
								e.predicate = $(tds[1]).find("select").val();
								e.predicateValue = $(tds[2]).find("input").val();
								predicateList.push(e);
							}
							data_.predicateList = predicateList;
						}
						
						var o = new Object();
						for(var i = 0; i < data_.length; i ++){
							o[data_[i].name] = data_[i].value;
						}
						o.rfmList = data_.rfmList;
						o.predicateList = data_.predicateList;
						
						var obj = JSON.parse(layui.setter.ajaxs.sendAjaxContentType('post' , url_ , JSON.stringify(o), 'application/json'));
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
          				// 因为是弹窗，所以重新渲染时间控件
          				laydate.render(
      						{
      							elem: '#snapshot-begin-time',
      							type: 'datetime'
      						}
          				);
          				laydate.render(
      						{
      							elem: '#snapshot-end-time',
      							type: 'datetime'
      						}
          				);
          				
          				pageDialog.initEditDialogPage(o.key , o.data);
          			}
		        });
			},
			
			// 绘制完成编辑弹窗后实例化其中的数据
			initEditDialogPage : function(securityKey, e){
				// 生产环境下拉框 
				$(".active-select").find("option[value='" + e.active + "']").attr("selected" , true);
				
				// 请求方式下拉框 
				$(".request-type-select").find("option[value='" + e.requestType + "']").attr("selected" , true);
				
				// 是否保存流量快照 1 保存
				if(e.requestSnapshotMark == '保存'){	
					layer.pageDialog.snapshotTrShow();
				}
				
				// 是否开启流量标记 1 标记
				if(e.rateFlowMark == '标记') {
					layer.pageDialog.rateFlowMarkTitle();
					var size = e.rfmList.length;
					for(var i = 0 ; i < size; i ++){
						var tr_ = e.rfmList[i];
						layer.pageDialog.rateFlowMarkBody(tr_);
					}
				}
				
				// 断言设置
				var psize = e.predicateList.length;
				for(var i = 0 ; i < psize; i ++){
					var tr_ = e.predicateList[i];
					layer.pageDialog.addGwPredicatesTable(tr_)
				}
				
			},
			
			
	        // 绘制添加和编辑弹框
			drawDialogPage : function(type , key , e){		// ('edit' , o.key , o.data),
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
				var snapshotCount = '';
				var snapshotBeginTime = '';
				var snapshotEndTime = '';
				var description = '';
				
				
				var log = '';
				var unlog = '';
				var jobClass = '';
				var jobList = '';
				if(type == 'add') {
					alert = '断言规则请参考：https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#gateway-request-predicates-factories';
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
					
					if(e.rateFlowMark == '标记') {		// 是否开启流量标记 1 标记
						rateFlowMark1 = 'checked="checked" ';
					}else{
						rateFlowMark0 = 'checked="checked" ';
					}
					
					if(e.requestSnapshotMark == '保存'){	// 是否保存流量快照 1 保存
						requestSnapshotMark1 = 'checked="checked" ';
					}else{
						requestSnapshotMark0 = 'checked="checked" ';
					}
					snapshotCount = e.snapshotCount;
					snapshotBeginTime = e.snapshotBeginTime;
					snapshotEndTime = e.snapshotEndTime;
					description = e.description;
				}
				
				var title = '<div class="dialog-form-title">';
						title += '<h3>' + alert + '</h3>';
					title += '</div>';
				
				var html = '<form id="dialog-gw-form" action="javascript:void(0)">' + title;
					html +=  '<table id="dialog-gw-table" class="dialog-form-talbe" style="width:95%;margin-left:20px;padding-bottom:5px">';
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
							html += '<td style="text-align: left;width:160px;" colspan="1">基础描述：</td>';
							html += '<td style="text-align: left" colspan="3">';
								html += '<input type="text" autocomplete="off" name="description" value="' + description + '" class="dialog-form-input" style="width:96%;" placeholder="52个字符以内"  maxlength="52">';
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
									html += '<input type="radio" name="requestSnapshotMark" value="0" style="vertical-align:middle;" ' + requestSnapshotMark0 + ' onclick="layer.pageDialog.snapshotTrHiden()">';
									html += '<span style="vertical-align:middle;margin-left:5px;">不保存</span>';
									html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
									html += '<input type="radio" name="requestSnapshotMark" value="1" style="vertical-align:middle;" ' + requestSnapshotMark1 + ' onclick="layer.pageDialog.snapshotTrShow()">';
									html += '<span style="vertical-align:middle;margin-left:5px;">保存(用于模拟压测使用)</span>';
								html += '</span>';
							html += '</td>';
						html += '</tr>';	
						
						html += '<tr class="gw-snapshot" style="display:none">';
							html += '<td style="text-align: left;width:80px;" colspan="1">快照请求条数：</td>';
							html += '<td style="text-align: left" colspan="3">';
								html += '<input type="text" autocomplete="off" name="snapshotCount" value="' + snapshotCount + '" class="dialog-form-input" style="width:30%;" placeholder="请输入整数"  maxlength="7">';
							html += '</td>';
						html += '</tr>';
						html += '<tr class="gw-snapshot" style="display:none">';
							html += '<td style="text-align: left;width:80px;" colspan="1">快照时间段：</td>';
							html += '<td style="text-align: left" colspan="3">';
								html += '<input id="snapshot-begin-time" type="text" autocomplete="off" name="snapshotBeginTime" value="' + snapshotBeginTime + '" class="layui-input" placeholder="开始时间">';
								html += '<span> - </span>';
								html += '<input id="snapshot-end-time" type="text" autocomplete="off" name="snapshotEndTime" value="' + snapshotEndTime + '" class="layui-input" placeholder="结束时间">';
							html += '</td>';
						html += '</tr>';
						
						
						
						html += '<tr>';
							html += '<td style="text-align: left;width:160px;" colspan="1">是否开启流量标记：</td>';
							html += '<td style="text-align: left" colspan="3">';
								html += '<span id="" class="field" style="padding-top:5px">';
									html += '<input type="radio" name="rateFlowMark" value="0" style="vertical-align:middle;" ' + rateFlowMark0 + ' onclick="layer.pageDialog.rateFlowMarkDelete()">';
									html += '<span style="vertical-align:middle;margin-left:5px;">不标记</span>';
									html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
									html += '<input type="radio" name="rateFlowMark" value="1" style="vertical-align:middle;" ' + rateFlowMark1 + ' onclick="layer.pageDialog.rateFlowMarkTitle()">';
									html += '<span id="rate-mark-1" style="vertical-align:middle;margin-left:5px;">标记</span>';
								html += '</span>';
							html += '</td>';
						html += '</tr>';	
					html += '</table>';
					
					// 路由断言规则
					html +=  '<table id="dialog-gw-table-predicates" class="dialog-form-talbe" style="width:95%;margin-left:20px;padding-bottom:5px;padding-top:2px">';
						html += '<tr>';
							html += '<td style="text-align: left;width:160px;" colspan="1">断言设置：</td>';
							html += '<td style="text-align: left" colspan="3">';
								html +='<button class="gw-predicates layui-btn" style="margin-left:80px;height:30px;line-height:30px;font-size:12px" onclick="layer.pageDialog.addGwPredicatesTable()">添&nbsp&nbsp&nbsp&nbsp&nbsp加</button>';
								html += '  ';
								html +='<button class="gw-predicates layui-btn layui-btn-danger" style="height:30px;line-height:30px;font-size:12px"  onclick="layer.pageDialog.predicatesRemove()">移&nbsp&nbsp&nbsp&nbsp&nbsp除</button>';
							html += '</td>';
						html += '</tr>';
					html += '</table>';
					html += '<table id="gw-predicates-table" class="gw-predicates" border="1" style="width:95%;margin-left:10px; border: 1px solid #ddd;">';	// .gw-predicates 用于删除
						html += '<thead style="line-height: 40px">';
							html += '<tr>';
								html += '<th width="40px" bgcolor="#eee">';
									html += '';
								html += '</th>';
								html += '<th width="200px" bgcolor="#eee">';
									html += '断言规则';
								html += '</th>';
								html += '<th>';
									html += '断言规则对应值';
								html += '</th>';
							html += '</tr>';
						html += '</thead>';
						html += '<tbody id="gw-predicates-tbody"></tbody>';
					html += '</table>';
					
					if(type == 'edit'){
						html += '<input type="hidden" name="id" value="' + id + '">';
					}
					html += '<input type="hidden" name="eleValue" value="' + key + '">';  //权限标识
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
			
			///////////////////////////////////////////////////////////////// 流量快照 /////////////////////////////////////////////////////////////////////////////
			snapshotTrShow : function(){
				$(".gw-snapshot").show();
			},
			
			snapshotTrHiden : function(){
				$(".gw-snapshot").hide();
				$("input[name='snapshotCount']").val("");
				$("input[name='snapshotBeginTime']").val("");
				$("input[name='snapshotEndTime']").val("");
			},
			
			
			
			/////////////////////////////////////////////////////////////////  流量标记  /////////////////////////////////////////////////////////////////////////////
			// radio按钮：不标记
			rateFlowMarkDelete : function(){
				$(".rate-flow-mark").remove();
			},
			
			/**
			 * 如果开启流量标记，则展示此组建 以class=rate-flow-mark进行【不标记】后的删除操作
			 */
			rateFlowMarkTitle : function(){
				if($("#rate-flow-mark-table").length != 0){
					return;
				}
				var html ='<button class="rate-flow-mark layui-btn" style="margin-left:80px;height:30px;line-height:30px;font-size:12px"  onclick="layer.pageDialog.rateFlowMarkBody()">添&nbsp&nbsp&nbsp&nbsp&nbsp加</button>';
				html += '  ';
				html +='<button class="rate-flow-mark layui-btn layui-btn-danger" style="height:30px;line-height:30px;font-size:12px"  onclick="layer.pageDialog.rateFlowMarkRemove()">移&nbsp&nbsp&nbsp&nbsp&nbsp除</button>';
				$("#rate-mark-1").append(html);
				this.rateFlowMarkTable();
			},
			
			rateFlowMarkTable : function(){
				var html = '<table id="rate-flow-mark-table" class="rate-flow-mark" border="1" style="width:95%;margin-left:10px; border: 1px solid #ddd;">';
					html += '<thead style="line-height: 40px">';
						html += '<tr class="" style="">';
							html += '<th width="40px" bgcolor="#eee">';
								html += '';
							html += '</th>';
							html += '<th width="120px" bgcolor="#eee">';
								html += '统计项目';
							html += '</th>';
							html += '<th width="200px">';
								html += '统计接口';
							html += '</th>';
							html += '<th width="200px" bgcolor="#eee">';
								html += '统计参数';
							html += '</th>';
							html += '<th width="200px">';
								html += '统计值';
							html += '</th>';
							html += '<th width="120px" bgcolor="#eee">';
								html += '统计维度';
							html += '</th>';
							html += '<th >';
								html += '统计规则描述';
							html += '</th>';
						html += '</tr>';
					html += '</thead>';
					html += '<tbody id="rate-flow-mark-tbody"></tbody>';
				html += '</table>';
				$("#dialog-gw-table").after(html);
			},
			
			rateFlowMarkBody : function(e){
				var type_ = '';				// 统计整个接口项目流量 type=1 or 2 or 3 or 4
				var target_ = '';			// 统计当前接口项目下某个接口流量,如：MANAGER-API-901
				var param_ = '';			// 统计当前接口项目下指定接口的某个或某几个参数产生的流量,如：productType,productName
				var value_ = '';				// 统计当前接口项目下指定接口的某个参数对应的值所产生的流量,如：时间简史
				var sd_ = '';					// 统计维度：month|day|hour|minute|second，不按周统计。
				var name_ = '';				// 统计规则描述
				if(typeof e != 'undefined'){
					var type_ = e.type; 
					var target_ = e.target; 
					var param_ = e.param; 
					var value_ = e.value;
					var sd_ = e.statisticalDimension;
					var name_ = e.name;
				}
				
				var html = '<tr>';
					html += '<td style="text-align: center" width="40px">';
						html += '<input type="checkbox" class="rate-checkbox" style="vertical-align:middle;">';
					html += '</td>';
					html += '<td style="text-align: left" width="120px">';
						html += '<select class="key-words-type-select" style="width:100%;">';
							html += pageDialog.keyWordsTypeList(type_);
						html += '</select>';
					html += '</td>';
					html += '<td style="text-align: left" width="200px">';
						html += '<input type="text" autocomplete="off"  class="dialog-form-input" style="width:94%;" placeholder="52个字符以内"  maxlength="52" value="' + target_ + '">';
					html += '</td>';
					html += '<td style="text-align: left" width="200px">';
						html += '<input type="text" autocomplete="off"  class="dialog-form-input" style="width:94%;" placeholder="52个字符以内"  maxlength="52" value="' + param_ + '">';
					html += '</td>';
					html += '<td style="text-align: left" width="200px">';
						html += '<input type="text" autocomplete="off"  class="dialog-form-input" style="width:94%;" placeholder="52个字符以内"  maxlength="52" value="' + value_ + '">';
					html += '</td>';
					html += '<td style="text-align: left" width="120px">';
						html += '<select class="key-words-statistical-dimension-select" style="width:100%;">';
							html += pageDialog.keyWordsStatisticalDimensionList(sd_);
						html += '</select>';
					html += '</td>';
					html += '<td style="text-align: left">';
						html += '<input type="text" autocomplete="off"  class="dialog-form-input" style="width:94%;" placeholder="52个字符以内"  maxlength="52" value="' + name_ + '">';
					html += '</td>';
				html += '</tr>';
				$("#rate-flow-mark-tbody").append(html);
			},
			
			keyWordsTypeList : function(e) {
				var type_1 = '';
				var type_2 = '';
				var type_3 = '';
				var type_4 = '';
				if(typeof e != 'undefined'){
					if(e == 1){
						type_1 = 'selected';
					}
					if(e == 2){
						type_2 = 'selected';
					}
					if(e == 3){
						type_3 = 'selected';
					}
					if(e == 4){
						type_4 = 'selected';
					}
				}
				var html_ = '<option value="">请选择</option>';
					html_ += '<option value="1" ' + type_1 + '>整个项目</option>';
					html_ += '<option value="2" ' + type_2 + '>指定接口</option>';
					html_ += '<option value="3" ' + type_3 + '>指定参数</option>';
					html_ += '<option value="4" ' + type_4 + '>参数对应值</option>';
				return html_;
			},
			
			// 统计维度：month|day|hour|minute|second，不按周统计。
			keyWordsStatisticalDimensionList : function(e) {
				var month_ = '';
				var day_ = '';
				var hour_ = '';
				var minute_ = '';
				var second_ = '';
				if(typeof e != 'undefined'){
					if(e == 'month'){
						month_ = 'selected';
					}
					if(e == 'day'){
						day_ = 'selected';
					}
					if(e == 'hour'){
						hour_ = 'selected';
					}
					if(e == 'minute'){
						minute_ = 'selected';
					}
					if(e == 'second'){
						second_ = 'selected';
					}
				}
				var html_ = '<option value="">请选择</option>';
					html_ += '<option value="month" ' + month_ + '>按月统计</option>';
					html_ += '<option value="day" ' + day_ + '>按天统计</option>';
					html_ += '<option value="hour" ' + hour_ + '>按小时统计</option>';
					html_ += '<option value="minute" ' + minute_ + '>按分钟统计</option>';
					html_ += '<option value="second" ' + second_ + '>按秒统计</option>';
				return html_;
			},
			
			// 移除一条或多条 流量标记 记录
			rateFlowMarkRemove : function(){
				var arr = $('.rate-checkbox:checked');
				if(arr.length != 0){
					for(var i = 0 ; i < arr.length; i ++){
						$(arr[i].parentElement.parentElement).remove();
					}
				}
			},
			
			/////////////////////////////////////////////////////////////////  断言设置 /////////////////////////////////////////////////////////////////////////////
			addGwPredicatesTable : function(e){
				var predicate_ = '';				// "After"
				var predicateValue_ = '';				// 2022-10-20T17:42:47.789-07:00[America/Denver]
				if(typeof e != 'undefined'){
					var predicate_ = e.predicate; 
					var predicateValue_ = e.predicateValue;
				}
				
				var html = '<tr>';
					html += '<td style="text-align: center" width="40px">';
						html += '<input type="checkbox" class="predicates-checkbox" style="vertical-align:middle;">';
					html += '</td>';
					html += '<td style="text-align: left" width="200px">';
						html += '<select class="predicates-select" style="width:100%;">';
							html += pageDialog.predicateList(predicate_);
						html += '</select>';
					html += '</td>';
					html += '<td style="text-align: left">';
						html += '<input type="text" autocomplete="off"  class="dialog-form-input" style="width: 900px" placeholder="请输入合法的断言规则，100个字符以内"  maxlength="100" value="' + predicateValue_ + '">';
					html += '</td>';
				html += '</tr>';
				$("#gw-predicates-tbody").append(html);
			},
			
			predicateList : function(e){
				var path = '';
				var after = '';
				var before = '';
				var between = '';
				
				var cookie = '';
				var header = '';
				var host = '';
				var method = '';
				
				var query = '';
				var remoteAddr = '';
				var weight = '';
				var xforwardedremoteaddr = '';
				if(typeof e != 'undefined'){
					if(e == 'Path'){
						path = 'selected';
					}
					if(e == 'After'){
						after = 'selected';
					}
					if(e == 'Before'){
						before = 'selected';
					}
					if(e == 'Between'){
						between = 'selected';
					}
					if(e == 'Cookie'){
						cookie = 'selected';
					}
					if(e == 'Header'){
						header = 'selected';
					}
					if(e == 'Host'){
						host = 'selected';
					}
					if(e == 'Method'){
						method = 'selected';
					}
					if(e == 'Query'){
						query = 'selected';
					}
					if(e == 'RemoteAddr'){
						remoteAddr = 'selected';
					}
					if(e == 'Weight'){
						weight = 'selected';
					}
					if(e == 'XForwardedRemoteAddr'){
						xforwardedremoteaddr = 'selected';
					}
				}
				
				var html_ = '<option value="">请选择断言类型</option>';
				html_ += '<option value="Path" ' + path + '>Path</option>';
				html_ += '<option value="After" ' + after + '>After</option>';
				html_ += '<option value="Before" ' + before + '>Before</option>';
				html_ += '<option value="Between" ' + between + '>Between(时间点英文逗号分隔)</option>';
				html_ += '<option value="Cookie" ' + cookie + '>Cookie</option>';
				html_ += '<option value="Header" ' + header + '>Header</option>';
				html_ += '<option value="Host" ' + host + '>Host</option>';
				html_ += '<option value="Method" ' + method + '>Method</option>';
				html_ += '<option value="Query" ' + query + '>Query(匹配某几个参数，英文逗号分隔)</option>';
				html_ += '<option value="RemoteAddr" ' + remoteAddr + '>RemoteAddr(ipv4 cidr)</option>';
				html_ += '<option value="Weight" ' + weight + '>Weight(不常用)</option>';
				html_ += '<option value="XForwardedRemoteAddr" ' + xforwardedremoteaddr + '>XForwardedRemoteAddr</option>';
				return html_;
			},
			
			// 移除一条或多条 断言记录
			predicatesRemove : function(){
				var arr = $('.predicates-checkbox:checked');
				if(arr.length != 0){
					for(var i = 0 ; i < arr.length; i ++){
						$(arr[i].parentElement.parentElement).remove();
					}
				}
			},
			
			
			
			
			
		};
		
		layer.pageDialog = pageDialog;
	});











