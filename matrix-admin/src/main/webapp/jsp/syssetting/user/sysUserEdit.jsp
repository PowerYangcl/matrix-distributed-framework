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
	            <h1 class="pagetitle">修改用户</h1>
                    <span class="pagedesc">
                    </span>
	            <span style="display:none">jsp/syssetting/user/sysUserEdit.jsp</span>
	        </div>
	        
			<div id="validation" class="subcontent" style="display: block">
				<form id="form-edit-user" class="stdform">
					<input type="hidden" name="id" value="${entity.id}"/>
					<p>
						<label>用户名称</label>
						<span class="field">
							<input type="text" name="userName" id="userName" class="ae-form-input"  value="${entity.userName}"/>
						</span>
					</p>
					
					<p>
						<label>密码</label>
						<span class="field">
							<input type="password" name="password" class="ae-form-input" value="111111"/>
							<span style="color:red">*默认111111</span>
						</span>
					</p>
					
					<p>
						<label>手机号码</label>
						<span class="field">
							<input type="text" name="mobile" id="mobile" class="ae-form-input" value="${entity.mobile}"/>
						</span>
					</p>
					
					
					<p>
						<label>电子邮箱</label>
						<span class="field">
							<input type="text" name="email" id="email" class="ae-form-input" value="${entity.email}"/>
						</span>
					</p>

					<p>
						<label>用户性别</label>  <%-- select --%>
						<span class="field">
							 <select id="sex" name="sex" class="ae-form-input" style="min-width: 31%;">
							 	
							 	<c:if test="${entity.sex == 1}">
							 		<option value="1" selected="selected">男</option>
								 	<option value="2">女</option>
							 	</c:if>
							 	<c:if test="${entity.sex == 2}">
							 		<option value="1">男</option>
								 	<option value="2" selected="selected">女</option> 
							 	</c:if>
								 
							 </select>
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
	function editUserInfo(){ 
		mconfirm('您确定要修改这条记录吗？', '系统提示', function(flag) {
			if(flag){
				var type_ = 'post';
				var url_ = '${basePath}manager/edit_sys_user.do';
				var data_ = $("#form-edit-user").serializeArray();
				var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
				if(obj.status == 'success'){
					malert(obj.msg, '系统提示' , function(){
						window.location.href = '${basePath}/manager/page_manager_user_list.do'  
					});
				}else{
					malert(obj.msg, '系统提示'); 
				}
			}
		});
	}
</script>








