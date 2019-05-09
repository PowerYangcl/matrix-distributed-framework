/** 
 * @description: Dubbo项目名称列表-脚本对象封装 
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年2月21日 下午2:11:19 
 * @version 1.0.0.1
 */ 
var dubboNodeList = {
		rowId:null, // 一条记录的id
		
		path : null,
		
		init : function(path){
			dubboNodeList.path = path;
			return dubboNodeList;
		},
		
		/**
		 * 加载列表页表单完成后，准备绘制表单
		 */
		draw : function(obj){
			obj = JSON.parse(obj);
			$('#ajax-tbody-page tr').remove();
			var html_ = '';
			if(obj.status == 'error'){
				html_ = '<tr><td colspan="11" style="text-align: center;">' + obj.msg + '</td></tr>';
			}else{
				var list = obj.list;
				if (list.length > 0) {
					for (var i = 0; i < list.length; i++) {
						var type = "";
						if(list[i].type == '1'){
							type = "生产者";
						}else if(list[i].type == '2'){
							type = "消费者";
						}else{
							type = "生产者-消费者";
						}
						var href = dubboNodeList.path +  'route/show_route_dubbo_logger.do?host=' + list[i].dubboAddr + '&app=' + list[i].application;
						var hrefLog = dubboNodeList.path +  'route/show_route_service_files.do?host=' + list[i].dubboAddr + '&app=' + list[i].application;
						html_ += '<tr id="tr-' + list[i].application + '" class="gradeX">' 
						+ '<td width="30px" align="center"><input type="checkbox" class="line-check" value="' + list[i].dubboAddr + '"/></td>'
						+ '<td align="center">' + list[i].application + '</td>'
						+ '<td width="150px" align="center">' + list[i].username + '</td>'
						+ '<td width="100px" align="center">' + type + '</td>'
						+ '<td width="100px" align="center">' + list[i].dubboAddr + '</td>'
						+ '<td width="400px" align="center">'
							+ '<a style="cursor:pointer;" dubbo-addr="' + list[i].dubboAddr + '"  onclick="dubboNodeList.openOperateDialog(this)">操作</a> | '
							+ '<a style="cursor:pointer;" href="'+ href + '" title="显示catalina.out中的日志" target="_blank">系统日志</a> | '
							+ '<a style="cursor:pointer;" href="'+ hrefLog + '" title="显示服务器中的文件信息" target="_blank">系统文件</a> '
						+ '</td></tr>'
					}
				} else {
					html_ = '<tr><td colspan="11" style="text-align: center;">未发现可以显示的结果</td></tr>';
				}
			}
			$('#ajax-tbody-page').append(html_);
		},
		
		search : function(){
			var type_ = 'post';
			var url_ = dubboNodeList.path + 'application/ajax_dubbo_node_list.do';
			var data_ = {
					application : $("#application").val(),
					username : $("#username").val(),
					type : $("#type").val()
			}
			var obj = ajaxs.sendAjax(type_, url_, data_);
			dubboNodeList.draw(obj);
		},
		
		searchReset : function(){
			$("#application").val(""),
			$("#username").val(""),
			$("#type").val("")
			dubboNodeList.search();
		},
		
		selectAll : function(){
		    if($("#check-all")[0].checked == true){
				$(".line-check").prop("checked", true);        
			}else{
				$(".line-check").prop("checked", false);
			}
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
		
		openOperateDialog:function(e){
			$("#find-dialog-table input").val("");
			$("#find-result").val("");
			$("#find-project").text("");
			
			$("#find-cache-name").val("PropConfig");
			$("#find-dubbo-addr").val($(e).attr("dubbo-addr"));
			$("#find-project").text($(e).parent().parent()[0].children[1].textContent + ":" + $(e).parent().parent()[0].children[4].textContent);
			dubboNodeList.openDialog("#find-dialog-div" , "#find-interface-list");
		},
		
		// 打开批处理弹窗
		openBatchOperateDialog:function(){
			var arr = $(".line-check:checked");
			if(arr.length == 0){
				malert("请至少勾选一条记录!" , '系统提示'); 
				return;
			}
			var host = '';
			for(var i = 0 ; i < arr.length ; i ++){
				host += $(arr[i]).val() + ",";
			}
			host = host.substring(0 , host.length-1);
			
			$("#batch-dialog-table input").val("");
			$("#batch-result").val("");
			$("#batch-project").text(arr.length);
			
			$("#batch-dubbo-addr").val(host);
			$("#batch-cache-name").val("PropConfig");
			dubboNodeList.openDialog("#batch-dialog-div" , "#batch-interface-list");
		},
		
		// 打开内置命令执行弹框
		openExecuteDialog:function(){
			var arr = $(".line-check:checked");
			if(arr.length == 0){
				malert("请至少勾选一条记录!" , '系统提示'); 
				return;
			}
			var host = '';
			for(var i = 0 ; i < arr.length ; i ++){
				host += $(arr[i]).val() + ",";
			}
			host = host.substring(0 , host.length-1);
			
			$("#execute-dialog-table input").val("");
			$("#execute-result").val("");
			$("#execute-project").text(arr.length);
			
			$("#execute-dubbo-addr").val(host);
			$("#execute-cache-name").val("PropConfig");
			dubboNodeList.openDialog("#execute-dialog-div" , "#execute-interface-list");
		},
		
		routeFind:function(){
			var type_ = 'post';
			var url_ = dubboNodeList.path + 'route/ajax_route_find.do';
			var data_ = $("#find-dialog-table" , parent.document).serializeArray();
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 $("#find-result"  , parent.document).val(o.data);
			 }else{
				 malert(o.msg , '系统提示'); 
				 $("#find-result" , parent.document).val("");
			 }
		},
		
		routeFindAll:function(){
			var type_ = 'post';
			var url_ = dubboNodeList.path + 'route/ajax_route_find_all.do';
			var data_ = $("#batch-dialog-table" , parent.document).serializeArray();
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 $("#batch-result"  , parent.document).val(o.data);
			 }else{
				 malert(o.msg , '系统提示'); 
				 $("#batch-result" , parent.document).val("");
			 }
		},
		
		dialogReset:function(){
			$("textarea"  , parent.document).val("");
		},
		
		
		routeAdd:function(id_){
			var type_ = 'post';
			var url_ = dubboNodeList.path + 'route/ajax_route_add.do';
			var data_ = $("#"+id_+"-dialog-table" , parent.document).serializeArray();
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 $("#find-result"  , parent.document).val("");
			 }else{
			 }
			 malert(o.msg , '系统提示');
		},
		
		routeUpdate:function(id_){
			var type_ = 'post';
			var url_ = dubboNodeList.path + 'route/ajax_route_update.do';
			var data_ = $("#"+id_+"-dialog-table" , parent.document).serializeArray();
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 $("#find-result"  , parent.document).val("");
			 }else{
			 }
			 malert(o.msg , '系统提示');
		},
		
		routeRemove:function(id_){
			var type_ = 'post';
			var url_ = dubboNodeList.path + 'route/ajax_route_remove.do';
			var data_ = $("#"+id_+"-dialog-table" , parent.document).serializeArray();
			 var o = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			 if(o.status == "success"){
				 $("#find-result"  , parent.document).val("");
			 }else{
			 }
			 malert(o.msg , '系统提示');
		},
		
		// 系统内置命令下拉框触发事件
		selectExecuteKey:function(o){
			var val_ = $("#execute-key option:checked" , parent.document).attr("paramters");
			$("#execute-value" , parent.document).val(val_);
		},
		
		// 执行系统内置命令
		routeExecute:function(){
			var type_ = 'post';
			var url_ = dubboNodeList.path + 'route/ajax_route_execute.do';
			var data_ = $("#execute-dialog-table" , parent.document).serializeArray();
			var result = ajaxs.sendAjax(type_, url_, data_);
			 var o = JSON.parse(result);
			 if(o.status == "success"){
				 $("#execute-result"  , parent.document).val(result);
			 }else{
				 malert("执行失败!" , '系统提示');
				 $("#execute-result"  , parent.document).val(result);
			 }
		},
		
}













































