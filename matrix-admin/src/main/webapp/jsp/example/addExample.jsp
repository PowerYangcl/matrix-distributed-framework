<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp"%>
<script type="text/javascript">
	function addInfo() {
		var type_ = 'post';
		var url_ = '${basePath}example/ajax_add_info.do';
		var data_ = $("#form-example").serializeArray();
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		if (obj.status) {

		}
		malert(obj.msg, 'title');
	}
</script>

<div class="centercontent">
	<div class="pageheader notab">
		<h1 class="pagetitle">添加记录示例</h1>
		<span class="pagedesc"> 添加一条记录到test库的user_demo表中，页面路径：【jsp/example/addExample.jsp】 </span> 
		<span style="display: none">jsp/example/addExample.jsp</span>
	</div>

	<div id="validation" class="subcontent" style="display: block">
		<form id="form-example" class="stdform" method="post">
			<p>
				<label>用户名称</label> 
				<span class="field"> 
					<input type="text" name="userName" id="userName" class="ae-form-input" />
				</span>
			</p>
			<p>
				<label>密码</label> 
				<span class="field"> 
					<input type="password" name="password" id="password" class="ae-form-input" />
				</span>
			</p>
			<p>
				<label>身份证号</label> 
				<span class="field"> 
					<input type="text" name="idNumber" id="idNumber" class="ae-form-input" />
				</span>
			</p>

			<p>
				<label>性别</label>
				<span class="field"> <%-- class="radius3" style="margin-left:0px; width:370px" --%> 
				<select id="sex" name="sex" class="ae-form-input" style="min-width: 31%;">
						<option value="1">男</option>
						<option value="2">女</option>
				</select>
				</span>
			</p>

			<p>
				<label>出生日期</label>
				<%-- date iput--%>
				<span class="field"> <%--<input type="text" name="birthday" id="birthday" class="ae-form-input" />--%> 
					<input type="text" name="birthday" id="birthday" class="ae-form-input" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</span>
			</p>

			<p>
				<label>mobile</label> 
				<span class="field"> 
					<input type="text" name="mobile" id="mobile" class="ae-form-input" />
				</span>
			</p>

		</form>
	</div>
	<br /> 
	<a onclick="addInfo()" class="btn btn_orange btn_search radius50  security-btn" key="add_info_example:add" style="display:none;cursor: pointer; margin-left: 220px"> 
		<span> 添&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp加 </span>
	</a>
	<a onclick="" class="btn btn_orange btn_search radius50  security-btn" key="add_info_example:delete" style="display:none;cursor: pointer; margin-left: 220px"> 
		<span> 删&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp除 </span>
	</a>
</div>












