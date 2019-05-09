/** 
 * @description: Dubbo项目名称列表-脚本对象封装 
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年2月21日 下午2:11:19 
 * @version 1.0.0.1
 */ 
var logger = {
		rowId:null, // 一条记录的id
		
		path : null,
		host : null,
		
		init : function(path , host){
			logger.path = path;
			logger.host = host;
			return logger;
		},
		
		
		search:function(){
			var value_ = {
					"line":$("#type").val(),
					"file":$("#file-name").val()
			};
			var type_ = 'post';
			var url_ = logger.path + 'route/ajax_route_dubbo_logger.do';
			var data_ = {
					dubboAddr: logger.host,
					value: JSON.stringify(value_)
			}
			logger.draw(ajaxs.sendAjax(type_, url_, data_));
		},
		
		/**
		 * 加载列表页表单完成后，准备绘制表单
		 */
		draw : function(obj){
			obj = JSON.parse(obj);
			if(obj.status == 'success'){
				obj = JSON.parse(obj.data);
			}
			$('#ajax-tbody-page tr').remove();
			var html_ = '';
			if(obj.status == 'error'){
				if(typeof obj.exception == "undefined"){
					html_ = '<tr><td style="text-align: center;border-top: 1px solid #ddd; ">' + obj.msg + '</td></tr>';
				}else{
					html_ = '<tr><td style="text-align: left;border-top: 1px solid #ddd; ">' + obj.exception + '</td></tr>';
				}
			}else{
				var list = obj.lines;
				if (list.length > 0) {
					html_ += '<tr id="" class="gradeX"><td style="border-top: 1px solid #ddd; ">' 
					for (var i = 0; i < list.length; i++) { 
						html_ += list[i] + '</br>'
					}
					+ '</td></tr>'
				} else {
					html_ = '<tr><td style="text-align: center;border-top: 1px solid #ddd; ">未发现可以显示的结果</td></tr>';
				}
			}
			$('#ajax-tbody-page').append(html_);
		},
		
}













































