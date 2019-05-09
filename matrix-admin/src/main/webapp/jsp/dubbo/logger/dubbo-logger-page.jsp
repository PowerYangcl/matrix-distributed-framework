<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp" %>


<script type="text/javascript" src="${jsp}/dubbo/logger/dubbo-logger-page.js"></script>
<script type="text/javascript">
	$(function() {
		$(document).attr("title","${host}");
		logger.init('${basePath}' ,'${host}').search();
	});
</script>

<div class="centercontent tables">
	<!--这个跳转页面的功能及跳转路径等等-->
	<div class="pageheader notab">
		<h1 class="pagetitle">${app} 服务器日志信息显示如下：</h1>
		<span class="pagedesc" >日志信息默认10秒钟自动更新一次</span>
	</div>

	<div id="contentwrapper" class="contentwrapper">

		<%-- table-form 这个id分页使用 --%>
		<div id="table-form" class="dataTables_wrapper">
			<div class="contenttitle2" >
				<p style="margin: 0px">
					<label>日志行数：</label> 
					<select id="type" name="type" class="form-search" style="margin-bottom: 5px;">
						<option value="500">500行</option>
						<option value="1000">1000行</option>
						<option value="2000">2000行</option>
						<option value="3000">3000行</option>
						<option value="4000">4000行</option>
						<option value="5000">5000行</option>
						<option value="6000">6000行</option>
					</select>
					
					<label>指定文件：</label> 
					<span class="field"> 
						<input id="file-name" type="text" class="form-search" style="width:300px"  />
					</span>
					
					<button class="stdbtn btn_yellow" style="margin-left:50px"> 暂停输出</button>
					<button class="stdbtn btn_yellow" style="margin-left:10px" onclick="logger.search()">查 &nbsp&nbsp&nbsp&nbsp 询</button>
				</p>
			</div>

			<table id="dyntable2" cellpadding="0" cellspacing="0" border="0" class="stdtable" style="margin-bottom:200px">
				<tbody id="ajax-tbody-page" class="page-list">
					
				</tbody>
			</table>

		</div>
	</div>

</div>
















