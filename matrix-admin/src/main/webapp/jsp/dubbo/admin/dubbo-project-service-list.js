/** 
 * @description:发布服务列表页面列表-脚本对象封装 
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年9月05日 下午2:11:19 
 * @version 1.0.0.1
 */ 

var dubboProjectServiceList = {
		rowId:null, // 一条记录的id
		
		path : null,
		application_ : null,   // 服务名称
		
		init : function(path , application_){
			dubboProjectServiceList.path = path;
			dubboProjectServiceList.application_ = application_;
			return dubboProjectServiceList;
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
						+ '<td width="100px" align="left">' + list[i].service + '</td>'
						+ '<td width="100px" align="center">' + (list[i].version == "" ? "默认版本" : list[i].version) + '</td>'
						+ '<td width="100px" align="center">' + ( list[i].group == "" ? "默认组" : list[i].group ) + '</td>'
						+ '<td width="100px" align="center">' + list[i].protocol + '</td>'
						+ '<td width="200px" align="center">'
						+ '<a style="cursor:pointer;" dubbo-id="' + list[i].id + '" onclick="dubboProjectServiceList.consumerPage(this)">消费者列表</a> | '
						+ '<a style="cursor:pointer;" rpc-name="' + list[i].service + '" onclick="dubboProjectServiceList.functionPage(this)">接口方法列表</a> | '
						+ '<a style="cursor:pointer;">动态配置</a> | '
						+ '<a style="cursor:pointer;">路由配置</a>'
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
			var url_ = dubboProjectServiceList.path + 'application/ajaxFindDubboProjectServiceList.do';
			var data_ = { 
					application : dubboProjectServiceList.application_ , 
					service : $("#service").val(),
					protocol:$("#protocol").val()
			}
			var obj = ajaxs.sendAjax(type_ , url_ , data_);
			dubboProjectServiceList.draw(obj);
		},
		
		searchReset : function(){
			$("#service").val("") 
			$("#protocol").val("") 
			dubboProjectServiceList.search();
		},
		
		// 消费者列表|jsp页面地址
		consumerPage : function(o){
			window.location.href =  dubboProjectServiceList.path + 'application/redirect_application_dubbo_project_service_consumer_list.do?id=' + $(o).attr("dubbo-id");
		},
		
		// 方法列表
		functionPage : function(o){
			window.location.href =  dubboProjectServiceList.path + 'application/redirect_application_dubbo_project_rpc_function_list.do?rpcName=' + $(o).attr("rpc-name");
		}
}













































