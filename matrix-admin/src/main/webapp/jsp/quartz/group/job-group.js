/**
 * 
 * 
 */
var jobgroup = {
		
		path:null,
		
		init:function(path){
			jobgroup.path = path;
			return jobgroup;
		},
		
		// 绘制添加页面
		loadTable : function(url_){
			if (url_ == undefined) { // 首次加载表单
				jobgroup.draw(aForm.jsonObj);
				return;
			}
			// 这种情况是响应上一页或下一页的触发事件
			var type_ = 'post';
			var data_ = {
				groupName : $("#group-name").val(),
				ip : $("#ip").val()
			};
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			aForm.launch(url_, 'table-form', obj).init();
			jobgroup.draw(obj);
		},

		draw : function(obj){
			$('#ajax-tbody-1 tr').remove();
			var html_ = '';
			var list = obj.data.list;
			if (list.length > 0) {
				for (var i = 0; i < list.length; i++) {
					
					html_ += '<tr id="tr-' + list[i].id + '" class="gradeX">' 
							+ '<td width="150px" align="center">' + list[i].groupName + '</td>'
							+ '<td align="center">' + list[i].ip + '</td>'
							+ '<td width="130px" align="center">' + list[i].createTime + '</td>'
							+ '<td width="90px" align="center">' + list[i].createUserName + '</td>'
							+ '<td width="130px" align="center">' + list[i].updateTime + '</td>'
							+ '<td width="90px" align="center">' + list[i].updateUserName + '</td>'
							
							+ '<td width="200px" align="center">'
								+ '<a onclick="jobgroup.deleteJobGroup(this)" eleId="' + list[i].id + '" title="删除"  style="display:none;cursor:pointer;" class="security-btn" key="job_group_list:delete">删除</a> '
								+ '<a onclick="jobgroup.openEditDialog(this)"  eleId="' + list[i].id + '"  title="修改"  style="display:none;cursor:pointer;" class="security-btn" key="job_group_list:edit">修改</a> '
							+ '</td></tr>'
				}
			} else {
				html_ = '<tr><td colspan="11" style="text-align: center;">' + obj.msg + '</td></tr>';
			}
			$('#ajax-tbody-1').append(html_);
		},
		
		search : function(){
			aForm.formPaging(1);
		},
		
		searchReset : function(){
			$(".form-search").val(""); 
			jobgroup.search();
		},
		
		openDialog:function(dialogId , slimscrollId_){
			// 自定义滚动条 | 执行此代码自定义滚动条则生效
			$(slimscrollId_).slimscroll({
				color: '#666',
				alwaysVisible: true, //是否 始终显示组件
				railVisible: false, //是否 显示轨道 
				size: '10px',
				width: 'auto',
				height: '150px' // '208px'
			});
			
			window.parent.$.blockUI({
				showOverlay:true ,
				css:{
					cursor:'auto',
					left:($(window.parent).width() - $(dialogId).width())/2 + 'px',
					width:$(dialogId).width()+'px',
					height:320,
					top:($(window.parent).height()-$(dialogId).height())/2 + 'px',
					position:'fixed', //居中
					textAlign:'left',
					border: '3px solid #FB9337'   // 边界,
				},
				message: $(dialogId),
				fadeIn: 500,//淡入时间
				fadeOut: 1000  //淡出时间
			});
		},
		
		closeDialog : function(){
			window.parent.$.unblockUI();
		},
		
		// 打开添加弹窗 jobgroup.openAddDialog()
		openAddDialog:function(){
			$("#add-dialog-table input").val("");
			jobgroup.openDialog("#add-dialog-div" , "#add-interface-list");
		},
		
		// 打开编辑弹窗 jobgroup.openEditDialog()
		openEditDialog:function(o){
			if(jobgroup.drawEditDialog(o)){
				jobgroup.openDialog("#edit-dialog-div" , "#edit-interface-list");
			}
		},
		
		
		// 添加定时任务
		addJobGroup:function(){
			var type_ = 'post';
			var url_ = jobgroup.path + 'quartz/ajax_job_group_add.do';
			var data_ = $("#add-dialog-table" , parent.document).serializeArray();
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 malert(o.msg , '系统提示' , function(){
					 jobgroup.closeDialog();
					 jobgroup.search();
				 });
			 }else{
				 malert(o.msg , '系统提示'); 
			 }
		},
		
		// 绘制编辑弹窗
		drawEditDialog:function(e){
			$("#id-edit").val("");
			$("#group-name-edit").val("");
			$("#ip-edit").val("");
			
			var type_ = 'post';
			var url_ = jobgroup.path + 'quartz/ajax_job_group_detail.do';
			var data_ = {
					id:$(e).attr("eleId")
			}
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 $("#id-edit").val(o.id);
				 $("#group-name-edit").val(o.groupName);
				 $("#ip-edit").val(o.ip); 
				 return true;
			 }else{
				 malert(o.msg , '系统提示'); 
				 return false;
			 }
			 return false;
		},
		
		
		// 提交编辑后的信息
		editJobGroup:function(){
			var type_ = 'post';
			var url_ = jobgroup.path + 'quartz/ajax_job_group_edit.do';
			var data_ = $("#edit-dialog-table" , parent.document).serializeArray();
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 malert(o.msg , '系统提示' , function(){
					 jobgroup.closeDialog();
					 jobgroup.search();
				 });
			 }else{
				 malert(o.msg , '系统提示'); 
			 }
		},
		
		// 删除定时任务
		deleteJobGroup:function(e){
			mconfirm('您确定要删除这条定时任务分组吗?', '系统提示', function(flag) {
				if(flag){
					var type_ = 'post';
					var url_ = jobgroup.path + 'quartz/ajax_job_group_delete.do';
					var data_ = {
							id:$(e).attr("eleId")
					};
					 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
					 if(o.status == "success"){
						 malert(o.msg , '系统提示' , function(){
							 jobgroup.closeDialog();
							 jobgroup.search();
						 });
					 }else{
						 malert(o.msg , '系统提示'); 
					 }
				}
			});
		},
}















































