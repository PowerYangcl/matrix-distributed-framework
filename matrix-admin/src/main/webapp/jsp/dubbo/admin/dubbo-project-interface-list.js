/** 
 * @description: Dubbo项目指定部署节点下的RPC接口类-脚本对象封装 
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年2月21日 下午2:11:19 
 * @version 1.0.0.1
 */ 

var dubboProjectRpcList = {
		rowId:null, // 一条记录的id
		
		path : null,
		application_ : null,   // 服务名称
		nodeAddress : null , // host ip+port
		
		init : function(path , application_ , nodeAddress){
			dubboProjectRpcList.path = path;
			dubboProjectRpcList.application_ = application_;
			dubboProjectRpcList.nodeAddress = nodeAddress;
			return dubboProjectRpcList;
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
						+ '<td width="200px" align="left">' + list[i].serviceKey + '</td>'
						+ '<td width="100px" align="center">' + list[i].weight + '</td>'
						+ '<td width="100px" align="center">' + ( list[i].dynamic == true ? '动态' : '静态' ) + '</td>' 
						+ '<td width="100px" align="center">' + ( list[i].enabled == true ? '已启用' : '已禁用' ) + '</td>'
						+ '<td width="200px" align="center">'
						+ '<a style="cursor:pointer;" >编辑</a> | '
						+ '<a style="cursor:pointer;" >半权</a> | '
						+ '<a style="cursor:pointer;" >倍权</a> | '
						+ '<a style="cursor:pointer;" >复制</a> | '
						+ '<a style="cursor:pointer;" >启用</a> | '
						+ '<a style="cursor:pointer;" >删除</a> | '
						+ '<a style="cursor:pointer;" >禁用</a> | '
						+ '<a style="cursor:pointer;" >测试</a>  '
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
			var url_ = dubboProjectRpcList.path + 'application/ajaxFindDubboProjectInterfaceList.do';
			var data_ = {
					application : dubboProjectRpcList.application_ ,
					nodeAddress : dubboProjectRpcList.nodeAddress ,
					serviceKey : $("#service-key").val(),
					dynamic : $("#dynamic").val(),
					enabled : $("#enabled").val()
			}
			var obj = ajaxs.sendAjax(type_, url_, data_);
			dubboProjectRpcList.draw(obj);
		},
		
		searchReset : function(){
			$("#service-key").val(""),
			$("#dynamic").val(""),
			$("#enabled").val("")
			dubboProjectRpcList.search();
		},
		
		
		
		
		
		
}













































