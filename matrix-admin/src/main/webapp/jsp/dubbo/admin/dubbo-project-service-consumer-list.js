/** 
 * @description:发布服务列表页面列表-脚本对象封装 
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年9月05日 下午2:11:19 
 * @version 1.0.0.1
 */ 

var dpConsumer = {
		rowId:null, // 一条记录的id
		
		path : null,
		id : null,   // 服务名称
		
		init : function(path , id){
			dpConsumer.path = path;
			dpConsumer.id = id;
			return dpConsumer;
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
						+ '<td width="200px" align="center">' + i + '</td>'
						+ '<td width="100px" align="left">' + list[i].application + '</td>'
						+ '<td width="100px" align="center">' + (list[i].username == "" ? "未标识" : list[i].username) + '</td>'
						+ '<td width="100px" align="center">' + list[i].flag + '</td>'
						+ '</tr>'
					}
				} else {
					html_ = '<tr><td colspan="11" style="text-align: center;">未发现可以显示的结果</td></tr>';
				}
			}
			$('#ajax-tbody-page').append(html_);
		},
		
		search : function(){
			var type_ = 'post';
			var url_ = dpConsumer.path + 'application/ajaxFindDubboProjectServiceConsumerList.do';
			var data_ = { 
					id : dpConsumer.id 
			}
			var obj = ajaxs.sendAjax(type_ , url_ , data_);
			dpConsumer.draw(obj);
		},
		
		searchReset : function(){ 
			dpConsumer.search();
		},
		
}













































