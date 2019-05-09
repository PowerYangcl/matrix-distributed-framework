/** 
 * @description: 接口方法列表-脚本对象封装 
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年9月05日 下午2:11:19 
 * @version 1.0.0.1
 */ 

var rpcFunciton = {
		rowId:null, // 一条记录的id
		
		path : null,
		rpcName : null,   // 服务名称
		
		init : function(path , rpcName){
			rpcFunciton.path = path;
			rpcFunciton.rpcName = rpcName;
			return rpcFunciton;
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
						html_ += '<tr class="gradeX">' 
						+ '<td width="350px" align="left">' + list[i].returnType + '</td>'
						+ '<td  align="left">' + list[i].showName + '</td>'
						+ '<td width="100px" align="center">' 
						+ '<a style="cursor:pointer;">测试</a>'
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
			var url_ = rpcFunciton.path + 'application/ajaxFindDubboProjectRpcFunctionList.do';
			var data_ = { 
					rpcName : rpcFunciton.rpcName , 
					functionName : $("#functionName").val() 
			}
			var obj = ajaxs.sendAjax(type_ , url_ , data_);
			rpcFunciton.draw(obj);
		},
		
		searchReset : function(){
			$("#functionName").val("")
			rpcFunciton.search();
		} 
}













































