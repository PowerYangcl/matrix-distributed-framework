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
	            <h1 class="pagetitle">创建角色</h1>
                    <!-- <span class="pagedesc">
                        添加一条记录系统用户表
                    </span> -->
	            <span style="display:none">jsp/syssetting/role/sysMcRoleAdd.jsp</span>
	        </div>
	        
			<div id="validation" class="subcontent" style="display: block">
				<form id="form-add-role" class="stdform">
					<p>
						<label>角色名称</label>
						<span class="field">
							<input type="text" name="roleName" id="roleName" class="ae-form-input" maxlength="20" placeholder="20个字以内"/>
						</span>
					</p>
					
					<p>
						<label>角色描述</label>
						<span class="field">
							<input type="text" name="roleDesc" id="roleDesc" class="ae-form-input" maxlength="200"  placeholder="200个字以内"/>
						</span>
					</p>
				</form>
			</div>
			<br />
			<a onclick="addInfo()" class="btn btn_orange btn_home radius50" style=" cursor: pointer;margin-left: 220px">
				<span> 添&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp加 </span>
			</a>
        </div>
<!--     </div>
</body>
</html> -->

<script type="text/javascript">
	function addInfo(){
		var type_ = 'post';
		var url_ = '${basePath}sysrole/add_mc_role_only.do';
		var data_ = $("#form-add-role").serializeArray();
		var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
		if(obj.status == 'success'){
			$("input[type='text']").val("");
		}
		malert(obj.msg, '系统提示');  
	}
</script>





































