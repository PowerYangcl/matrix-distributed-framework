<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<%@ include file="/inc/iframe-head.jsp" %>

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
					
					<p>
						<label>平台分配</label>
						<span id="platform-info" class="field" style="padding-top:5px">
								<!-- 动态代码填充 -->
						</span>
					</p>
				</form>
			</div>
			<br />
			<a onclick="sysrole.addInfo()" class="btn btn_orange btn_home radius50" style=" cursor: pointer;margin-left: 220px">
				<span> 添&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp加 </span>
			</a>
        </div>



<script type="text/javascript" src="${jsp}/syssetting/role/system-role.js"></script>
<script type="text/javascript">
	$(function() {
		sysrole.init('${basePath}').drawRoleAddPage();
	});
</script>


































