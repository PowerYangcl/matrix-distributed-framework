/**
 * 
 * 
 */
var joblog = {
		
		path:null,
		jobName:null,
		init:function(path , jobName){
			joblog.path = path;
			joblog.jobName = jobName;
			return joblog;
		},
		
		// 绘制添加页面
		loadTable : function(url_){
			if (url_ == undefined) { // 首次加载表单
				joblog.draw(aForm.jsonObj);
				return;
			}
			// 这种情况是响应上一页或下一页的触发事件
			var type_ = 'post';
			var data_ = {
				runGroupName : $("#group-name").val(),
				ip : $("#ip").val(),
				jobName:joblog.jobName,
				status:$("#status").val()
			};
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			aForm.launch(url_, 'table-form', obj).init();
			joblog.draw(obj);
		},

		draw : function(obj){
			$('#ajax-tbody-1 tr').remove();
			var html_ = '';
			var list = obj.data.list;
			if (list.length > 0) {
				for (var i = 0; i < list.length; i++) {
					var status_ = '';
					if(list[i].status == 'success'){
						status_ = '<span>' + list[i].status + '</span>';
					}else{
						status_ = '<span style="color:red">' + list[i].status + '</span>';
					}
					html_ += '<tr id="tr-' + list[i].id + '" class="gradeX">' 
							+ '<td width="320px" align="center">' + list[i].jobName + '</td>'
							+ '<td align="center">' + list[i].jobTitle + '</td>'
							+ '<td width="90px" align="center">' + status_ + '</td>'
							+ '<td width="150px" align="center">' + list[i].runGroupName + '</td>'
							+ '<td width="90px" align="center">' + list[i].ip + '</td>'
							+ '<td width="130px" align="center">' + list[i].beginTime + '</td>'
							+ '<td width="90px" align="center">' + list[i].endTime + '</td>' 
							+ '<td width="90px" align="center">'
								+ '<a onclick="joblog.openDetailDialog(this)"  eleId="' + list[i].id + '"  title="详情"  style="cursor:pointer;">详情</a> '
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
			joblog.search();
		},
		
		openDialog:function(dialogId , slimscrollId_){
			// 自定义滚动条 | 执行此代码自定义滚动条则生效
			$(slimscrollId_).slimscroll({
				color: '#666',
				alwaysVisible: true, //是否 始终显示组件
				railVisible: false, //是否 显示轨道 
				size: '10px',
				width: 'auto',
				height: '400px' // '208px'
			});
			
			window.parent.$.blockUI({
				showOverlay:true ,
				css:{
					cursor:'auto',
					left:($(window.parent).width() - $(dialogId).width())/2 + 'px',
					width:$(dialogId).width()+'px',
					height:580,
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
		
		// 打开详情弹窗 joblog.openDetailDialog()
		openDetailDialog:function(o){
			if(joblog.drawDetailDialog(o)){
				joblog.openDialog("#detail-dialog-div" , "#detail-interface-list");
			}
		},
		
		
		// 绘制编辑弹窗
		drawDetailDialog:function(e){
			$("#status-detail").text("");
			$("#remark-detail").val("");
			
			var type_ = 'post';
			var url_ = joblog.path + 'quartz/ajax_job_log_detail.do';
			var data_ = {
					id:$(e).attr("eleId")
			}
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 o = o.e;
				 $("#status-detail").text(o.status);
				 $("#remark-detail").val(o.remark);
				 return true;
			 }else{
				 malert(o.msg , '系统提示'); 
				 return false;
			 }
			 return false;
		},
		
		
	
		
}















































