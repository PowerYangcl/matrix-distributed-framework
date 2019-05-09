/**
 * 
 * 
 */
var sysrole = {
		
		path:null,
		
		init:function(path){
			sysrole.path = path;
			return sysrole;
		},
		
		// 绘制添加页面
		drawRoleAddPage : function(){
			var url_ = sysrole.path + 'manager/ajax_draw_add_user_page.do';
			var data_ = $("#form-add-user").serializeArray();
			var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
			if(obj.status == 'success'){
				var sflist = obj.sflist;
				if(sflist.length != 0){
					var html_ = '';
					for(var i = 0 ; i < sflist.length; i ++){
						var isChecked = '';
						var val_ = '';
						if(sflist[i].name.toLocaleLowerCase()  == 'leader'){
							isChecked = ' checked ';
						}
						html_ += '<input type="radio" ' + isChecked + ' name="platform" value="' + sflist[i].platform +'"style="vertical-align:middle;">';
						html_ += '<span style="vertical-align:middle;">' + sflist[i].name +'</span>&nbsp&nbsp&nbsp&nbsp&nbsp';
					}
					$("#platform-info").append(html_);
				}
				
			}
		},
		
		addInfo : function(){
			var url_ = sysrole.path + 'sysrole/add_mc_role_only.do';
			var data_ = $("#form-add-role").serializeArray();
			var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
			if(obj.status == 'success'){
				$("input[type='text']").val("");
			}
			malert(obj.msg, '系统提示');
		}
		
		
		
		
}















































