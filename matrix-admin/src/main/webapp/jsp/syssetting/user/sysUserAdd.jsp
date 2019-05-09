<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<%@ include file="/inc/iframe-head.jsp" %>

        <div class="centercontent">
			<div class="pageheader notab">
	            <h1 class="pagetitle">创建用户</h1>
	            <span style="display:none">jsp/syssetting/user/sysUserAdd.jsp</span>
	        </div>
	        
			<div id="validation" class="subcontent" style="display: block">
				<form id="form-add-user" class="stdform">
					<p>
						<label>用户名称</label>
						<span class="field">
							<input type="text" name="userName" id="userName" class="ae-form-input" />
						</span>
					</p>
					
					<p>
						<label>密码</label>
						<span class="field">
							<input type="password" name="password"  class="ae-form-input" value="111111" maxlength="20"/>
							<span style="color:red">*默认111111</span>
						</span>
					</p>
					
					<p>
						<label>备注描述</label>
						<span class="field">
							<input type="text" name="remark" id="remark" class="ae-form-input" />
						</span>
					</p>
					
					<p>
						<label>手机号码</label>
						<span class="field">
							<input type="text" name="mobile" id="mobile" class="ae-form-input" maxlength="11"/>
						</span>
					</p>
					
					
					<p>
						<label>电子邮箱</label>
						<span class="field">
							<input type="text" name="email" id="email" class="ae-form-input" />
						</span>
					</p>

					<p>
						<label>用户性别</label>  <%-- select --%>
						<span class="field">
							<%-- class="radius3" style="margin-left:0px; width:370px" --%>
							 <select id="sex" name="sex" class="ae-form-input" style="min-width: 31%;">
								 <option value="1">男</option>
								 <option value="2">女</option>
							 </select>
						</span>
					</p>
					
					<p>
						<label>所属公司</label>  <%-- select --%>
						<span id="company-info" class="field">
							 <!-- 动态代码填充 -->
						</span>
					</p>
					
					<p>
						<label>平台分配</label>
						<span id="platform-info" class="field" style="padding-top:5px">
								<!-- 动态代码填充 -->
						</span>
					</p>
				</form>
			</div>
			<br />
			<a onclick="sysuser.addSysUserInfo()" class="btn btn_orange btn_home radius50" style=" cursor: pointer;margin-left: 220px">
				<span> 添&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp加 </span>
			</a>
        </div>



<script type="text/javascript" src="${jsp}/syssetting/user/system-user-info.js"></script>
<script type="text/javascript">
	$(function() {
		sysuser.init('${basePath}').drawUserAddPage();
	});
</script>
































