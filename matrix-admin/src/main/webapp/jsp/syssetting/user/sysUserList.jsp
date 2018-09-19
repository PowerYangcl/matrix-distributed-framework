<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- <html xmlns="http://www.w3.org/1999/xhtml">
<head> -->
	<%@ include file="/inc/iframe-head.jsp"%>
<%-- </head>

<body class="withvernav">

	<div class="bodywrapper">
		<%@ include file="/inc/top.jsp"%>
		<%@ include file="/inc/left.jsp"%> --%>

		<div class="centercontent tables">
			<!--这个跳转页面的功能及跳转路径等等-->
			<div class="pageheader notab">
				<h1 class="pagetitle">系统用户列表页面</h1>
				<!-- <span class="pagedesc"> 本页面用于系统管理员来创建系统中的用户。 </span>  -->
				<span style="display: none">jsp/syssetting/user/sysUserList.jsp</span>
			</div>

			<div id="contentwrapper" class="contentwrapper">

				<%-- table-form 这个id分页使用 --%>
				<div id="table-form" class="dataTables_wrapper">
					<div class="contenttitle2">
						<p style="margin: 0px">
							<label>姓名：</label> 
							<span class="field"> 
								<input id="user-name" type="text" name="userName" class="form-search" />
							</span> 
							<label>手机号：</label> 
							<span class="field"> 
								<input id="mobile" type="text" name="mobile" class="form-search" />
							</span> 
							<a onclick="toUserAddPage()" class="btn btn_orange btn_home radius50 security-btn" key="system_user_list:add" style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
								<span> 添 加 </span>
							</a> 
							<a onclick="searchReset()" class="btn btn_orange btn_search radius50 security-btn" key="system_user_list:reset" style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
								<span> 重 置 </span>
							</a> 
							<a onclick="searchUser()" class="btn btn_orange btn_search radius50 security-btn" key="system_user_list:search" style="display:none;float: right; cursor: pointer; margin-left: 20px"> 
								<span> 查 询 </span>
							</a>
						</p>
					</div>

					<div id="dyntable2_length" class="dataTables_length dialog-show-count">
						<label> 当前显示 <%-- TODO 注意：select-page-size 这个ID是写定的，如果没有这个显示条数，则默认显示10条 - Yangcl --%>
							<select id="select-page-size" size="1" name="dyntable2_length" onchange="aForm.formPaging('1')">
								<option value="10">10</option>
								<option value="25">25</option>
								<option value="50">50</option>
								<option value="100">100</option>
							</select> 条记录
						</label>
					</div>

					<table id="dyntable2" cellpadding="0" cellspacing="0" border="0" class="stdtable">
						<thead>
							<tr>
								<th class="head1">用户姓名</th>
								<th class="head1">手机号码</th>
								<th class="head1">E-mail</th>
								<th class="head1">创建时间</th>
								<th class="head1 " width="100px">操作</th>
							</tr>
						</thead>

						<tbody id="ajax-tbody-user-list" class="page-list">
							<%-- 等待填充 --%>
						</tbody>
					</table>

				</div>
			</div>

		</div>


<!-- 	</div>

</body>
</html> -->

<script type="text/javascript">
	$(function() {
		var type_ = 'post';
		var url_ = '${basePath}manager/sys_user_list.do';
		var data_ = null; // 可以为null，后台会进行默认处理
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		aForm.launch(url_, 'table-form', obj).init().drawForm(loadTable);
	});

	// 回调函数
	function loadTable(url_) {
		if (url_ == undefined) { // 首次加载表单
			draw(aForm.jsonObj);
			return;
		}
		// 这种情况是响应上一页或下一页的触发事件
		var type_ = 'post';
		var data_ = {
			userName : $("#user-name").val(),
			mobile : $("#mobile").val(),
		}
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		aForm.launch(url_, 'table-form', obj).init();
		draw(obj);
	}

	// 画表格
	function draw(obj) {
		$('#ajax-tbody-user-list tr').remove();
		var html_ = '';
		var list = obj.data.list;
		if (list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				html_ += '<tr>' + '<td>' + list[i].userName + '</td>'
						+ '<td class="center">' + list[i].mobile + '</td>'
						+ '<td class="center">' + list[i].email + '</td>'
						+ '<td class="center">' + list[i].createTime + '</td>'
						+ '<td width="200px" align="center">'
						+ '<a onclick="deleteSystemUser(this)" userId="' + list[i].id + '" title="删除" class="security-btn" key="system_user_list:delete" style="display:none;cursor: pointer;">删除</a>   '
						+ '<a href="${basePath}manager/show_user_edit_page.do?id=' + list[i].id + '" title="修改" class="security-btn" key="system_user_list:edit" style="display:none;cursor: pointer;">修改</a> '
						+ '<a userId="' + list[i].id + '" onclick="userRoleListDialog(this)" title="用户权限" class="security-btn" key="system_user_list:user_role" style="display:none;cursor: pointer;">用户角色</a> '
						+ '</td></tr>'
			}
		}else{
			html_ = '<tr><td colspan="11" style="text-align: center;">' + obj.msg + '</td></tr>';
		}

		$('#ajax-tbody-user-list').append(html_);
	}
	
	// 前往添加用户界面 
	function toUserAddPage(){
		window.location.href = "${basePath}manager/show_user_add_page.do";
	}

	// 删除一个系统用户 
	function deleteSystemUser(obj) {
		var o = obj; // 自定义弹窗后参数obj消失，故这里保存
		mconfirm('您确定要删除这条记录吗？', '系统提示', function(flag) {
			if (flag) {
				var type_ = 'post';
				var url_ = '${basePath}manager/ajax_manager_delete_user.do';
				var data_ = {
					id : $(o).attr("userId") 
				}; 
				var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
				if (obj.status == 'success') {
					var currentPageNumber = $(".paginate_active").html(); // 定位到当前分页的页码，然后重新加载数据
					aForm.formPaging(currentPageNumber);
				} else {
				}
				malert(obj.msg, '系统提示');
			}
		});
	}

	//搜索
	function searchUser() {
		aForm.formPaging(1);
	}

	// 重置查询条件
	function searchReset() {
		$("#user-name").val("");
		$("#mobile").val("");
		aForm.formPaging(1);
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////ajax弹框分页示例开始 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 展示用户权限列表 
	function userRoleListDialog(obj){
		$("#user-id").val($(obj).attr("userId"));
		var type_ = 'post';
        var url_ = '${basePath}sysrole/user_role_list.do'; 
        var data_ = {
        		userId:$(obj).attr("userId")
        };
        var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
        window.parent.dForm.launch(url_ , 'dialog-table-form' , obj).init().drawForm(loadDialogTable);

        var dialogId = 'dialog-page-div';  // 弹窗ID
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
	}
	
	// 回调函数
    function loadDialogTable(url_){
        if(url_ == undefined){ // 首次加载表单
            drawDialog(window.parent.dForm.jsonObj);
            return;
        }
        // 这种情况是响应上一页或下一页的触发事件
        var type_ = 'post';
        var data_ = {
			roleName : $("#role-name" , window.parent.document).val(),
			userId : $("#user-id" , window.parent.document).val()
		};
        var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
        window.parent.dForm.launch(url_ , 'dialog-table-form' , obj).init();
        drawDialog(obj);
    }

    function drawDialog(obj){
    	if($('#dialog-ajax-tbody tr').length == 0){ 
			$('#dialog-ajax-tbody tr' , window.parent.document).remove();
		}else{
			$('#dialog-ajax-tbody tr').remove();
		}
    	
        var html_ = '';
        var list = obj.data.list;
        if (list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				html_ += '<tr>' + '<td align="center" width="200px">' + list[i].roleName + '</td>'
						+ '<td>' + list[i].roleDesc + '</td>'
						+ '<td align="center" width="150px">' + list[i].createTime + '</td>'
						+ '<td width="50px" align="center">';
						if(list[i].userId == -1){
							html_ += '<a href="javascript:void(0)" roleId="' + list[i].id + '" onclick="subpage.addMcUserRole(this)" title="为用户分配这个角色" class="security-btn" key="system_user_list:allot_cancel" style="display:none;cursor: pointer;">分配</a> '  
						}else{
							html_ += '<a href="javascript:void(0)" roleId="' + list[i].id + '" onclick="subpage.deleteMcUserRole(this)" title="为用户删除这个角色" class="security-btn" key="system_user_list:allot_cancel" style="display:none;cursor: pointer;">取消</a> '  
						}
						html_ += '</td></tr>'
			}
		}else{
			html_ = '<tr><td colspan="11" style="text-align: center;">' + obj.msg + '</td></tr>';
		}
        
        
        if($('#dialog-ajax-tbody').length == 0){ 
			$('#dialog-ajax-tbody' , window.parent.document).append(html_);
		}else{
			$('#dialog-ajax-tbody').append(html_);
		}
    }
    
    function dialogSearch(){
    	window.parent.dForm.formPaging(0);
    }
    
    function dialogSearchReset(){
    	$("#role-name" , window.parent.document).val(""); 
    	window.parent.dForm.formPaging(0);
    }
    
    // 为用户分配这个角色
    function addMcUserRole(ele){
    	var userInfoId = $("#user-id" , window.parent.document).val(); 
    	var roleId = $(ele).attr("roleId");
    	var type_ = 'post';
        var url_ = '../sysrole/ajax_sysrole_add_user_role.do';
    	var data_ = {
    			mcRoleId : roleId,
    			mcUserId : userInfoId
			};
    	var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
        if(obj.status == 'success'){
        	var html_ = '<a href="javascript:void(0)" roleId="' + roleId + '" onclick="subpage.deleteMcUserRole(this)" title="为用户删除这个角色"  style="cursor: pointer;">取消</a> ';  
        	$(ele)[0].parentElement.innerHTML = html_;
        }else{
        	malert(obj.msg , '系统提示 ');
        }
    }
    
    // 针对一个用户 删除一个角色
    function deleteMcUserRole(ele){
    	var userId = $("#user-id" , window.parent.document).val(); 
    	var roleId = $(ele).attr("roleId");
    	var type_ = 'post';
        var url_ = '../sysrole/remove_user_role.do';
    	var data_ = {
    			mcRoleId : roleId,
    			userId : userId
			};
    	var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
        if(obj.status == 'success'){ 
        	var html_ = '<a href="javascript:void(0)" roleId="' + roleId + '" onclick="subpage.addMcUserRole(this)" title="为用户分配这个角色"  style="cursor: pointer;">分配</a>';  
        	$(ele)[0].parentElement.innerHTML = html_;
        }else{
        	malert(obj.msg , '系统提示 ');
        }
    }
</script>

<%-- Ajax分页 且弹窗同时分页 --%>
<div id="dialog-page-div" class="dialog-page-div" style="display: none;width: 1200px;height: 600px">
    <p class="dialog-title">
        <a href="#" onclick="closeDialog()" class="dialog-close"></a>
        系统权限列表
    </p>
    <div id="dialog-content-wrapper" class="contentwrapper">
        <div id="dialog-table-form" class="dataTables_wrapper" style="text-align:left;">
        	
        	<div class="contenttitle2">
				<p style="margin: 0px">
					<label>权限名称：</label> 
					<span class="field"> 
						<input id="role-name" type="text" name="roleName" class="form-search" />
					</span>  
		            <input type="hidden" id="user-id" value=""/>	<!-- 保存数据 -->
					<a onclick='subpage.dialogSearchReset()' class="btn btn_orange btn_search radius50" style="float: right; cursor: pointer; margin-left: 10px"> 
						<span> 重 置 </span>
					</a> 
					<a onclick='subpage.dialogSearch()' class="btn btn_orange btn_search radius50" style="float: right; cursor: pointer; margin-left: 20px"> 
						<span> 查 询 </span>
					</a>
				</p>
			</div>
        	
            <div id="dialog-dyntable" class=" dialog-show-count" >
                <label>
                    当前显示 
                    <select id="dialog-select-page-size" >
                        <option value="10">10</option>
                    </select>
                    条记录
                </label>
            </div> 
            <table id="dialog-table" cellpadding="0" cellspacing="0" border="0" class="stdtable" style="text-align:center;"> 
                <thead>
	                <tr>
	                    <!-- <th class="head0 nosort">
	                        <input type="checkbox"/>
	                    </th> -->                                                                            
	                    <th class="head1" width="200px">权限名称</th>   
	                    <th class="head1"> 权限描述</th>    
	                    <th class="head1">创建时间</th>  
	                    <th class="head1 " width="50px">操作</th>
	                </tr>
                </thead>
                <tbody id="dialog-ajax-tbody" class="page-list">
                	<%-- 等待填充 --%>
                </tbody>
            </table>

        </div>
    </div>

</div>
























