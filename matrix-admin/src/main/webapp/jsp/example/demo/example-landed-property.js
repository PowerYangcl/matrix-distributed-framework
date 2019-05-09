/** 
 * @description: 这个类尽可能的贴近实际业务来展示一个完整的开发流程。
 * 	在编写一个新的业务时，禁止使用全局变量来保存信息，所有js页面脚本
 * 	需要进行js对象封装。
 * 
 * 		    因为这是一个功能全面的事例脚本，这里会为这个js对象命名为demo，如果
 * 	你是一个业务开发者，请务必起一个与你业务相关的名字来命名你的js对象。
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年2月21日 下午2:11:19 
 * @version 1.0.0.1
 */ 

var demo = {
		rowId:null, // 一条记录的id
		
		/**
		 * 开始加载列表页表单
		 */
		loadTable : function(url_){
			if (url_ == undefined){ // 首次加载表单
				demo.draw(aForm.jsonObj);
				return;
			}
			// 这种情况是响应上一页或下一页的触发事件
			var type_ = 'post';
			var data_ = {
				title : $("#title-list-page").val() ,
				city : $("#city-list-page").val() 
			};
			var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
			aForm.launch(url_ , 'table-form', obj).init();
			demo.draw(obj);
		},
		
		/**
		 * 加载列表页表单完成后，准备绘制表单
		 */
		draw : function(obj){
			$('#ajax-tbody-page tr').remove();
			var html_ = '';
			var list = obj.data.list;
			if (list.length > 0) {
				for (var i = 0; i < list.length; i++) {
					html_ += '<tr id="tr-' + list[i].id + '" class="gradeX">' 
							+ '<td width="200px" align="center">' + list[i].title + '</td>'
							+ '<td width="100px" align="center">' + list[i].city + '</td>'
							+ '<td width="100px" align="center">' + list[i].code + '</td>'
							+ '<td width="100px" align="center">' + list[i].total + '</td>'
							+ '<td width="100px" align="center">' + list[i].price + '</td>'
							+ '<td width="150px" align="center">' + list[i].greeningRate + '</td>'
							+ '<td width="150px" align="center">' + list[i].createTime + '</td>'
							+ '<td width="200px" align="center">'
								+ '<a onclick="demo.deleteRow(this)" eleId="' + list[i].id + '" title="删除"  style="cursor:pointer;" >删除</a> '
								+ '<a onclick="demo.openEditDialog(this)"  eleId="' + list[i].id + '"  title="修改"  style="cursor:pointer;" >修改</a> '
							+ '</td></tr>'
				}
			} else {
				html_ = '<tr><td colspan="11" style="text-align: center;">' + obj.msg + '</td></tr>';
			}
			$('#ajax-tbody-page').append(html_);
		},
		
		search : function(){
			aForm.formPaging(1);
		},
		
		searchReset : function(){
			$(".form-search").val(""); 
			demo.search();
		},
		
		closeDialog : function(){
			$.unblockUI();
		},
}













































