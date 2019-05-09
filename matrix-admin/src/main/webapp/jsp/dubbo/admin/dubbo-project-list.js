/** 
 * @description: Dubbo项目名称列表-脚本对象封装 
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年2月21日 下午2:11:19 
 * @version 1.0.0.1
 */ 

var dubboProjectList = {
		rowId:null, // 一条记录的id
		
		path : null,
		
		init : function(path){
			dubboProjectList.path = path;
			return dubboProjectList;
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
						html_ += '<tr id="tr-' + list[i].application + '" class="gradeX">' 
						+ '<td width="200px" align="center">' + list[i].application + '</td>'
						+ '<td width="100px" align="center">' + list[i].username + '</td>'
						+ '<td width="100px" align="center">' + type + '</td>'
						+ '<td width="200px" align="center">'
						+ '<a style="cursor:pointer;" project-name="' + list[i].application + '"  onclick="dubboProjectList.hostListPage(this)" >部署节点列表</a> | '
						+ '<a style="cursor:pointer;" project-name="' + list[i].application + '"  onclick="dubboProjectList.serviceListPage(this)">发布服务列表</a> | '
						+ '<a style="cursor:pointer;" >订阅服务列表</a> | '
						+ '<a style="cursor:pointer;" >上层应用</a> '
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
			var url_ = dubboProjectList.path + 'application/ajaxFindApplicationList.do';
			var data_ = {
					application : $("#application").val(),
					username : $("#username").val(),
					type : $("#type").val()
			}
			var obj = ajaxs.sendAjax(type_, url_, data_);
			dubboProjectList.draw(obj);
		},
		
		searchReset : function(){
			$("#application").val(""),
			$("#username").val(""),
			$("#type").val("")
			dubboProjectList.search();
		},
		
		// 前往 Dubbo项目部署节点列表
		hostListPage : function(o){
			window.location.href =  dubboProjectList.path + 'application/redirect_application_dubbo_project_ip_list.do?application=' + $(o).attr("project-name");
		},
		
		// 发布服务列表
		serviceListPage : function(o){
			window.location.href =  dubboProjectList.path + 'application/redirect_application_dubbo_project_service_list.do?application=' + $(o).attr("project-name");
		}
		
		
		
		
		
}













































