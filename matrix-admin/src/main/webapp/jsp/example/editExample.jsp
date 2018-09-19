<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp"%>
<script type="text/javascript">
	function updateInfo(id_) {
		if (confirm('您确定要修改这条记录吗？')) {
			var type_ = 'post';
			var url_ = '${basePath}example/ajax_edit_info.do';
			var data_ = $("#edit-form-example").serializeArray();
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			if (obj.status == 'success') {
				alert(obj.msg);
			} else {
				alert(obj.msg);
			}
		}
	}
</script>

<div class="centercontent">
	<div id="validation" class="subcontent" style="display: block">
		<form id="edit-form-example" class="stdform">
			<input type="hidden" name="id" value="${entity.id}" />
			<p>
				<label>用户姓名</label> 
				<span class="field"> 
					<input type="text" name="userName" id="userName" class="longinput" value="${entity.userName}" />
				</span>
			</p>
			
			<p>
				<label>登陆密码</label> 
				<span class="field"> 
					<input type="text" name="password" id="password" class="longinput" value="${entity.password}" />
				</span>
			</p>

			<p>
				<label>身份证号</label> 
				<span class="field"> 
					<input type="text" name="idNumber" id="idNumber" class="longinput" value="${entity.idNumber}" />
				</span>
			</p>
			<p>
				<label>注册手机</label> 
				<span class="field"> 
					<input type="text" name="mobile" id="mobile" class="longinput" value="${entity.mobile}" />
				</span>
			</p>

			<br />
			<p class="stdformbutton">
				<button type="button" class="submit radius2" onclick="updateInfo()" />修改</button>
			</p>
		</form>
	</div>
</div>












