/** 
 * @description: Dubbo项目部署节点列表-脚本对象封装 
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年2月21日 下午2:11:19 
 * @version 1.0.0.1
 */ 

var dubboProjectHostList = {
		rowId:null, // 一条记录的id
		
		path : null,
		application_ : null,   // 服务名称
		
		init : function(path , application_){
			dubboProjectHostList.path = path;
			dubboProjectHostList.application_ = application_;
			return dubboProjectHostList;
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
						html_ += '<tr id="tr-' + list[i].id + '" class="gradeX">' 
						+ '<td width="200px" align="center">' + list[i].id + '</td>'
						+ '<td width="100px" align="center">' + list[i].nodeAddress + '</td>'
						+ '<td width="100px" align="center">' + list[i].type + '</td>'
						+ '<td width="200px" align="center">'
						+ '<a style="cursor:pointer;" project-name="' + dubboProjectHostList.application_ + '" host="' + list[i].nodeAddress + '" onclick="dubboProjectHostList.rpcInterfaceListPage(this)">RPC接口类列表</a>' 
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
			var url_ = dubboProjectHostList.path + 'application/ajaxFindDubboProjectIpList.do';
			var data_ = { 
					application : dubboProjectHostList.application_ , 
					nodeAddress : $("#node-address").val()
			}
			var obj = ajaxs.sendAjax(type_, url_, data_);
			dubboProjectHostList.draw(obj);
		},
		
		searchReset : function(){
			$("#node-address").val("") 
			dubboProjectHostList.search();
		},
		
		// Dubbo项目指定部署节点下的RPC接口类列表集合|jsp页面地址
		rpcInterfaceListPage : function(o){
			window.location.href =  dubboProjectHostList.path + 'application/redirect_application_dubbo_project_interface_list.do?application=' + $(o).attr("project-name") + '&nodeAddress=' + $(o).attr("host");
		}
}













































