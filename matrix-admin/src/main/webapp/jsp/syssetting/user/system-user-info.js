/**
 * 
 * sysuser.addSysUserInfo
 * 
 * 
 * 
 */
var sysuser = {
		
		path:null,
		
		init:function(path){
			sysuser.path = path;
			return sysuser;
		},
		
		// 绘制添加页面
		drawUserAddPage : function(){
			var url_ = sysuser.path + 'manager/ajax_draw_add_user_page.do';
			var data_ = $("#form-add-user").serializeArray();
			var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
			if(obj.status == 'success'){
				var clist = obj.clist;
				var sflist = obj.sflist;
				
				if(clist.length != 0){
					var html_ = '<select id="cid" name="cid" class="ae-form-input" style="min-width: 31%;">';
					for(var i = 0 ; i < clist.length; i ++){
						html_ += '<option value="' + clist[i].id +'">' + clist[i].name + '</option>';
					}
					html_ += '</select>';
					$("#company-info").append(html_);
				}
				
				if(sflist.length != 0){
					var html_ = '';
					for(var i = 0 ; i < sflist.length; i ++){
						var isChecked = '';
						var val_ = '';
						if(sflist[i].name.toLocaleLowerCase()  == 'leader'){
							val_ = 'leader@' + sflist[i].platform;
							isChecked = ' checked ';
						}else{
							val_ = 'admin@' + sflist[i].platform;
						}
						html_ += '<input type="radio" ' + isChecked + ' name="platform" value="' + val_ +'"style="vertical-align:middle;">';
						html_ += '<span style="vertical-align:middle;">' + sflist[i].name +'</span>&nbsp&nbsp&nbsp&nbsp&nbsp';
					}
					$("#platform-info").append(html_);
				}
				
			}
		},
		
		addSysUserInfo : function(){
			var url_ = sysuser.path + 'manager/add_sys_user.do';
			var data_ = $("#form-add-user").serializeArray();
			var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
			if(obj.status == 'success'){
				$("input[type='text']").val("");
			}
			malert(obj.msg, '系统提示'); 
		},
		
		
		
		
		openEditDialog : function(o){
			sysuser.cleanUserInfo();
			sysuser.userInfoDetail(o);
			
			var dialogId = 'edit-dialog-div';   // 弹窗ID
			window.parent.$.blockUI({
	            showOverlay:true ,
	            css:  {
	                cursor:'auto',
	                left:($(window.parent).width() - $("#" + dialogId).width())/2 + 'px',
	                width:$("#" + dialogId).width()+'px',
	                top:($(window.parent).height()-$("#" + dialogId).height())/2 + 'px',
	                position:'fixed', //居中
	                border: '3px solid #FB9337'  // 边界
	            },
	            message: $('#' + dialogId),
	            fadeIn: 500,//淡入时间
	            fadeOut: 1000  //淡出时间
	        });
		},
		
		// 用户信息详情
		userInfoDetail:function(o){
			var type_ = 'post';
			var url_ = sysuser.path + 'manager/ajax_find_sys_user.do';
			var data_ = {
				id : $(o).attr("userId") 
			}; 
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			if (obj.status == 'success'){
				var userId = $(o).attr("userId");
				$("#id").val(userId);
				$("#dialog-user-name").val(obj.entity.userName);
				$("#user-name-old").val(obj.entity.userName);
				$("#sex-" + obj.entity.sex).prop("checked", true);
				$("#dialog-mobile").val(obj.entity.mobile);
				$("#qq").val(obj.entity.qq);
				$("#email").val(obj.entity.email);
				$("#idcard").val(obj.entity.idcard);
				$("#remark").val(obj.entity.remark);
			}
		},
		
		cleanUserInfo : function(){ 
			$("input").val("");
			$("textarea").val("");
		},
		
		// 提交表单，更新用户信息
		updateUserInfo : function(o){
			var url_ = sysuser.path + 'manager/edit_sys_user.do';
			var data_ = $("#edit-dialog-table" , window.parent.document).serializeArray();
			var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
			
			malert(obj.msg, '系统提示' , function(){
				var currentPageNumber = $(".paginate_active").html(); // 定位到当前分页的页码，然后重新加载数据
				aForm.formPaging(currentPageNumber);
				if(obj.status == 'success'){
					sysuser.closeDialog();
				}
			}); 
		},
		
		// 重置密码
		passwordReset : function(o){
			mprompt('请输入新密码', '', '系统提示：您正在重置密码', function(content) {
				if (content) {
					var url_ = sysuser.path + 'manager/ajax_password_reset.do';
					var data_ ={
							id:$(o).attr("userId"),
							password:content
					};
					var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
					if(obj.status == 'success'){
						malert('密码更新成功', '系统提示' );
					}
				}
			});
		},
		
		closeDialog:function(){
        	window.parent.$.unblockUI();
        }
}















































