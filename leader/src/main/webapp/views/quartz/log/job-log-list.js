/**
 * 矩阵系统配置 / 分布式定时任务 / 定时任务日志列表
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
  	      	title: '定时任务日志列表',
  	      	url : layui.setter.path + 'quartz/ajax_job_log_list.do',
  	      	toolbar: '#table-search-toolbar',
  	      	height: 'full-100', 
 	      	limit: 16,
  	      	cols: [
 	    	  	[
 	  	         	{field:'jobName', title:'任务名称', width:340,unresize: true, sort: true},  //  fixed: 'left', 
 	  	         	{field:'jobTitle', title:'任务标题'}, 
 	  	         	{
 	  	         		field:'status', 
 	  	         		title:'执行状态', 
 	  	         		width:100,
 	  	         		unresize: true,
 	  	         		templet: function(res){
		 	  	         	var html_ = '';
							if(res.status == 'success'){
								html_ = '<span>' + res.status + '</span>';
							}else{
								html_ = '<span style="color:red">' + res.status + '</span>';
							}
					  		return html_;
 	  	         		}
 	  	         	},
 	  	         	{field:'runGroupName', title:'分组名称' , width:160},
 	  	         	{field:'ip', title:'IP地址' , width:160},
 	  	         	{field:'beginTime', title:'开始时间' , width:160},
 	  	         	{field:'endTime', title:'结束时间' , width:160},
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
			}
		});

		// 监听行工具事件
		table.on('tool(table-toolbar)', function(o) {
			if (o.event === 'detail') {
				pageDialog.detailDialog(o);
			}
		});
		
		// 查询按钮
		var search = {		
			reload : function() {
				var runGroupName = $('#run-group-name').val();
				var ip = $('#ip').val();
				table.reload('page-table-reload', {
					page : {
						curr : 1  // 重新从第 1 页开始
					},
					where : {  // 此参数会合并请求到后台
						runGroupName:runGroupName,
						ip:ip
					}
				}, 'data');
				
				$('#run-group-name').val(runGroupName);
				$('#ip').val(ip);
			},
			
			reset : function(){
				$('#run-group-name').val('');
				$('#ip').val('');
				search.reload();
			}
		};
		
		// 页面弹窗对象
		var pageDialog = {
			detailDialog : function(o){
				layer.open({
					title : '定时任务日志详情',
		          	type : 1,	// 1：解析HTML代码段；2：解析url
		          	area : ['920px', '550px'],
		          	fixed : false,
		          	shadeClose : false,	// 鼠标点击遮罩层是否可以关闭弹框，默认false
		          	resize : false,        // 是否允许拉伸 默认：true
	          		content : pageDialog.drawDetailDialog('detail' , o.key , o.data),
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
			
			drawDetailDialog : function(type , key , e){
				var alert = '任务执行状态：' + '<span style="color:red">' + e.status + '</span>';
				var title = '<div class="dialog-form-title">';
						title += '<h3>' + alert + '</h3>';
					title += '</div>';
				var html = '<form id="dialog-api-form">' + title + '<table class="dialog-form-talbe" style="width:95%;margin-left:20px">';
					html += '<tr>';
						html += '<td style="text-align: left">';
							html += '<textarea id="remark-detail" rows="20" cols="450" style="width:800px">' + e.remark + '</textarea>';
						html += '</td>';
					html += '</tr>';			
				html += '</table></form>';
				return html;
			},
		}
	});











