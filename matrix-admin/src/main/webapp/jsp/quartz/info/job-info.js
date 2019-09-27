/**
 * 
 * 
 */
var jobinfo = {
		
		path:null,
		
		init:function(path){
			jobinfo.path = path;
			return jobinfo;
		},
		
		// 绘制添加页面
		loadTable : function(url_){
			if (url_ == undefined) { // 首次加载表单
				jobinfo.draw(aForm.jsonObj);
				return;
			}
			// 这种情况是响应上一页或下一页的触发事件
			var type_ = 'post';
			var data_ = {
				jobName : $("#job-name").val(),
				jobTitle : $("#job-title").val()
			};
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			aForm.launch(url_, 'table-form', obj).init();
			jobinfo.draw(obj);
		},

		draw : function(obj){
			$('#ajax-tbody-1 tr').remove();
			var html_ = '';
			var list = obj.data.list;
			if (list.length > 0) {
				for (var i = 0; i < list.length; i++) {
					var triger = '否';
					if(list[i].trigerType == 2){
						triger = '是';
					}
					var log_ = '记录';
					if(list[i].logType == 1){
						log_ = '否';
					}
					var resume = '<a onclick="jobinfo.pauseOneJob(this)" eleId="' + list[i].jobName + '" pause="1" title="暂停定时任务"  style="display:none;cursor:pointer;" class="security-btn" key="job_info_list:pause">暂停</a> '; 
					var pause = '运行中';
					if(list[i].pause == 1){
						pause = '<p style="margin:0px;color:red">已暂停</p>';
						resume = '<a onclick="jobinfo.pauseOneJob(this)" eleId="' + list[i].jobName + '" pause="0" title="恢复定时任务"  style="display:none;cursor:pointer;" class="security-btn" key="job_info_list:resume">恢复</a> '; 
					}
					
						
					
					html_ += '<tr id="tr-' + list[i].id + '" class="gradeX">' 
							+ '<td width="350px">' 
								+ '<p style="margin:0px;">' + list[i].jobName + '</p>'
								+ '<p style="margin:0px;">' + list[i].jobTitle + '</p>'
							+ '</td>'
							+ '<td width="150px" align="center">' + list[i].jobTriger + '</td>'
							+ '<td width="200px"align="center">' 
								+ '<p style="margin:0px;">开始时间: ' + list[i].beginTime + '</p>'
								+ '<p style="margin:0px;">结束时间: ' + list[i].endTime + '</p>'
								+ '<p style="margin:0px;">下次时间: ' + list[i].nextTime + '</p>'
							+ '</td>'
							+ '<td width="150px" align="left">'
								+ '<p style="margin:0px;">组: ' + list[i].groupName + '</p>'
								+ '<p style="margin:0px;">IP: ' + list[i].ip + '</p>'
							+ '</td>' 
							+ '<td width="60px" align="center">' + list[i].expireTime + '(s)</td>'
							+ '<td width="60px" align="center">' + list[i].timeOut + '(ms)</td>'
							+ '<td width="40px" align="center">' + triger + '</td>'
							+ '<td width="40px" align="center">' + log_ + '</td>'
							+ '<td width="60px" align="center">' + pause + '</td>'
							
							+ '<td width="200px" align="center">'
								+ '<a onclick="jobinfo.openDetailDialog(this)"  eleId="' + list[i].jobName + '"  title="详情"  style="display:none;cursor:pointer;" class="security-btn" key="job_info_list:detail">详情</a> '
								+ '<a onclick="jobinfo.openEditDialog(this)"  eleId="' + list[i].jobName + '"  title="修改"  style="display:none;cursor:pointer;" class="security-btn" key="job_info_list:edit">修改</a> '
								+ '<a onclick="jobinfo.jobExce(this)"  eleId="' + list[i].id + '" ips="' + list[i].ip + '"  title="立刻执行定时任务"  style="display:none;cursor:pointer;" class="security-btn" key="job_info_list:handling">手动</a> '
								+ '<a onclick="jobinfo.deleteJob(this)" eleId="' + list[i].jobName + '" title="删除"  style="display:none;cursor:pointer;" class="security-btn" key="job_info_list:delete">删除</a> '
								
								// + '<a onclick="" eleId="' + list[i].jobName + '" title="立刻执行定时任务"  style="display:none;cursor:pointer;" class="security-btn" key="job_info_list:run">手动</a> '
								+ resume
								+ '<a onclick="jobinfo.logPageList(this)" eleId="' + list[i].jobName + '" title="查看执行状态记录"  style="display:none;cursor:pointer;" class="security-btn" key="job_info_list:run_log">日志</a> '
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
			jobinfo.search();
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
		
		// 打开添加弹窗 jobinfo.openAddDialog()
		openAddDialog:function(){
			jobinfo.jobGroupList();
			$("#add-dialog-table input").val("");
			jobinfo.openDialog("#add-dialog-div" , "#add-interface-list");
		},
		
		// 打开编辑弹窗 jobinfo.openEditDialog()
		openEditDialog:function(o){
			jobinfo.jobGroupList();
			if(jobinfo.drawEditDialog(o)){
				jobinfo.openDialog("#edit-dialog-div" , "#edit-interface-list");
			}
		},
		
		// 打开详情弹窗
		openDetailDialog:function(o){
			jobinfo.jobGroupList();
			if(jobinfo.drawDetailDialog(o)){
				jobinfo.openDialog("#detail-dialog-div" , "#detail-interface-list");
			}
		},
		
		// 添加或修改页面的下拉框
		jobGroupList:function(){
			$(".job-group option").remove()
			var type_ = 'post';
			var url_ = jobinfo.path + 'quartz/ajax_job_group_list.do';
			var data_ = null;
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 var html_ = '<option value="">请选择任务组</option>';
				 for(var i = 0 ; i < o.list.length ; i ++){
					 var k = o.list[i];
					 html_ += '<option value="' + k.id + '">' + k.groupName + '</option>'
				 }
				 $(".job-group").append(html_);
			 }else{
				 malert(o.msg , '系统提示'); 
			 }
			
		},
		
		
		// 添加定时任务
		addJobInfo:function(){
			var type_ = 'post';
			var url_ = jobinfo.path + 'quartz/ajax_job_info_add.do';
			var data_ = $("#add-dialog-table" , parent.document).serializeArray();
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 malert(o.msg , '系统提示' , function(){
					 jobinfo.closeDialog();
					 jobinfo.search();
				 });
			 }else{
				 malert(o.msg , '系统提示'); 
			 }
		},
		
		// 绘制编辑弹窗
		drawEditDialog:function(e){
			$("#id-edit").val("");
			$("#job-name-edit").text("");
			$("#job-title-edit").val("");
			$("#job-triger-edit").val("");
			$("#remark-edit").val("");
			$(".concurrent-type-edit[value='0']").attr("checked","checked");
			$("#expire-time-edit").val("");
			$("#time-out-edit").val("");
			$(".triger-type-edit[value='1']").attr("checked","checked");
			$(".log-type-edit[value='1']").attr("checked","checked");
			$("#job-class-edit").val("");
			$("#job-list-edit").val("");
			
			var type_ = 'post';
			var url_ = jobinfo.path + 'quartz/ajax_job_info_detail.do';
			var data_ = {
					jobName:$(e).attr("eleId")
			}
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 $("#id-edit").val(o.id);
				$("#job-name-edit").text(o.jobName);
				$("#job-title-edit").val(o.jobTitle);
				$("#job-triger-edit").val(o.jobTriger);
				$("#remark-edit").val(o.remark);
				$(".concurrent-type-edit[value='" + o.concurrentType +"']").attr("checked","checked");
				$("#expire-time-edit").val(o.expireTime);
				$("#time-out-edit").val(o.timeOut);
				$(".triger-type-edit[value='" + o.trigerType +"']").attr("checked","checked");
				$(".log-type-edit[value='" + o.logType +"']").attr("checked","checked");
				$("#job-class-edit").val(o.jobClass);
				$("#job-list-edit").val(o.jobList);
				$("#run-group-id-edit").find("option[value='" + o.runGroupId + "']").attr("selected",true);
				 return true;
			 }else{
				 malert(o.msg , '系统提示'); 
				 return false;
			 }
			 return false;
		},
		
		// 修改定时任务
		editJobInfo:function(){
			var type_ = 'post';
			var url_ = jobinfo.path + 'quartz/ajax_job_info_edit.do';
			var data_ = $("#edit-dialog-table" , parent.document).serializeArray();
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 malert(o.msg , '系统提示' , function(){
					 jobinfo.closeDialog();
					 jobinfo.search();
				 });
			 }else{
				 malert(o.msg , '系统提示'); 
			 }
		},
		
		
		// 绘制详情弹窗
		drawDetailDialog:function(e){
			$("#pause-detail").text("");
			$("#job-name-detail").val("");
			$("#job-title-detail").text("");
			$("#job-triger-detail").text("");
			$("#remark-detail").text("");
			$(".concurrent-type-detail[value='0']").attr("checked","checked");
			$("#expire-time-detail").val("");
			$("#time-out-detail").val("");
			$(".triger-type-detail[value='1']").attr("checked","checked");
			$(".log-type-detail[value='1']").attr("checked","checked");
			$("#job-class-detail").text("");
			$("#job-list-detail").text("");
			
			$("#create-time-detail").text("");
			$("#create-user-name-detail").text("");
			$("#update-time-detail").text("");
			$("#update-user-name-detail").text("");
			
			var type_ = 'post';
			var url_ = jobinfo.path + 'quartz/ajax_job_info_detail.do';
			var data_ = {
					jobName:$(e).attr("eleId")
			}
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 if(o.pause == 0){
					 $("#pause-detail").text("运行中");
				 }else{
					 $("#pause-detail").text("已暂停");
				 }
				$("#job-name-detail").val(o.jobName);
				$("#job-title-detail").text(o.jobTitle);
				$("#job-triger-detail").text(o.jobTriger);
				$("#remark-detail").text(o.remark);
				$(".concurrent-type-detail[value='" + o.concurrentType +"']").attr("checked","checked");
				$("#expire-time-detail").text(o.expireTime);
				$("#time-out-detail").text(o.timeOut);
				$(".triger-type-detail[value='" + o.trigerType +"']").attr("checked","checked");
				$(".log-type-detail[value='" + o.logType +"']").attr("checked","checked");
				$("#job-class-detail").text(o.jobClass);
				$("#job-list-detail").text(o.jobList);
				$("#run-group-id-detail").find("option[value='" + o.runGroupId + "']").attr("selected",true);
				
				var date = new Date(); 
				date.setTime(o.createTime); 
				$("#create-time-detail").text(date.format("yyyy-MM-dd hh:mm:ss"));
				$("#create-user-name-detail").text(o.createUserName);
				date.setTime(o.updateTime); 
				$("#update-time-detail").text(date.format("yyyy-MM-dd hh:mm:ss"));
				$("#update-user-name-detail").text(o.updateUserName);
				 return true;
			 }else{
				 malert(o.msg , '系统提示'); 
				 return false;
			 }
			 return false;
		},
		
		
		// 删除定时任务
		deleteJob:function(e){
			mconfirm('您确定要删除这条定时任务吗?', '系统提示', function(flag) {
				if(flag){
					var type_ = 'post';
					var url_ = jobinfo.path + 'quartz/ajax_job_info_delete.do';
					var data_ = {
							jobName:$(e).attr("eleId")
					};
					 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
					 if(o.status == "success"){
						 malert(o.msg , '系统提示' , function(){
							 jobinfo.closeDialog();
							 jobinfo.search();
						 });
					 }else{
						 malert(o.msg , '系统提示'); 
					 }
				}
			});
		},
		
		
		// 暂停一条定时任务jobinfo.pauseOneJob()
		pauseOneJob:function(e){
			var title = '恢复';
			if($(e).attr("pause") == 1){
				title = '暂停';
			}
			mconfirm('您确定要' + title + '这条定时任务吗?', '系统提示', function(flag) {
				if(flag){
					var type_ = 'post';
					var url_ = jobinfo.path + 'quartz/ajax_job_info_pause.do';
					var data_ = {
							jobName:$(e).attr("eleId"),
							pause:$(e).attr("pause"),
							pauseType:'one'
					};
					 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
					 if(o.status == "success"){
						 malert(o.msg , '系统提示' , function(){
							 jobinfo.closeDialog();
							 jobinfo.search();
						 });
					 }else{
						 malert(o.msg , '系统提示'); 
					 }
				}
			});
		},
		
		// 暂停所有定时任务jobinfo.pauseAllJob()
		pauseAllJob:function(pause_){
			var title = '全部恢复';
			if(pause_ == 1){
				title = '全部暂停';
			}
			mconfirm('您确定要' + title + '所有定时任务吗?', '系统提示', function(flag) {
				if(flag){
					var type_ = 'post';
					var url_ = jobinfo.path + 'quartz/ajax_job_info_pause.do';
					var data_ = {
							pause:pause_,
							pauseType:'all'
					};
					 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
					 if(o.status == "success"){
						 malert(o.msg , '系统提示' , function(){
							 jobinfo.closeDialog();
							 jobinfo.search();
						 });
					 }else{
						 malert(o.msg , '系统提示'); 
					 }
				}
			});
		},
		
		// 手动触发定时任务
		jobExce:function(e){
			var type_ = 'post';
			var url_ = jobinfo.path + 'quartz/ajax_job_info_exec.do';
			var data_ = {
					id : $(e).attr("eleId"),
					guardType : "triger",
					ips : $(e).attr("ips")
			};
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 malert(o.msg , '系统提示');
			 }else{
				 malert(o.msg , '系统提示'); 
			 }
		},
		
		// 前往日志详情页面
		logPageList:function(e){
			window.location.href = jobinfo.path +  'quartz/page_quartz_job_log_list.do?jobName=' + $(e).attr("eleId");
		}
}















































