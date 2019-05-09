<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- <html xmlns="http://www.w3.org/1999/xhtml">
<head> -->
	<%@ include file="/inc/iframe-head.jsp" %>
<%-- </head>

<body class="withvernav">

    <div class="bodywrapper">
		<%@ include file="/inc/top.jsp" %>
		<%@ include file="/inc/left.jsp" %> --%>

        <div class="centercontent">
			<div class="pageheader notab">
	            <h1 class="pagetitle">修改角色</h1>
                <span class="pagedesc"></span>
	            <span style="display:none">jsp/syssetting/role/sysMcRoleEdit.jsp</span>
	        </div>
	        
			<div id="validation" class="subcontent" style="display: block">
				<form id="form-edit-role" class="stdform">
					<input type="hidden" name="id" value="${entity.id}"/>
					<p>
						<label>角色名称</label>
						<span class="field">
							<input type="text" name="roleName" class="ae-form-input" maxlength="20" placeholder="20个字以内"  value="${entity.roleName}"/>
							<input type="hidden" name="oldRoleName" value="${entity.roleName}"/>  
							<input type="hidden" name="platform" value="${entity.platform}"/>  
						</span>
					</p>
					
					<p>
						<label>角色描述</label>
						<span class="field">
							<input type="text" name="roleDesc" id="roleDesc" class="ae-form-input" maxlength="200"  placeholder="200个字以内" value="${entity.roleDesc}"/>
						</span>
					</p>
				</form>
			</div>
			<br />
			<a onclick="editUserInfo()" class="btn btn_orange btn_home radius50" style=" cursor: pointer;margin-left: 220px">
				<span> 修&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp改 </span>
			</a>
        </div>
<!--     </div>
</body>
</html> -->

<script type="text/javascript">
	function editUserInfo(id_){
		mconfirm('您确定要修改这条记录吗?', '系统提示', function(flag) {
			if(flag){
				var type_ = 'post';
				var url_ = '${basePath}sysrole/edit_mc_role_only.do';
				var data_ = $("#form-edit-role").serializeArray();
				var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
				if(obj.status == 'success'){
					malert(obj.msg, '系统提示' , function(){
						window.location.href = '${basePath}sysrole/page_sysrole_mc_role_list.do'  
					});
				}else{
					malert(obj.msg, '系统提示'); 
				}
			}
		});
	}
</script>








