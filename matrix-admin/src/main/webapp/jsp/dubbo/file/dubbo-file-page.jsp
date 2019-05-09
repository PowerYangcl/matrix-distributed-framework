<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp" %>


<script type="text/javascript" src="${jsp}/dubbo/file/dubbo-file-page.js"></script>
<script type="text/javascript">
	$(function() {
		$(document).attr("title","${host}");
		files.init('${basePath}' ,'${host}').search();
	});
</script>

<div class="centercontent tables">
	<!--这个跳转页面的功能及跳转路径等等-->
	<div class="pageheader notab">
		<h1 class="pagetitle">${app} 服务器文件信息显示如下：</h1>
	</div>

	<div id="contentwrapper" class="contentwrapper">

		<%-- table-form 这个id分页使用 --%>
		<div id="table-form" class="dataTables_wrapper">
			<div class="contenttitle2" >
				<p style="margin: 0px">
					<label>指定文件：</label> 
					<span class="field"> 
						<input id="file-name" type="text" class="form-search" style="width:300px" />
					</span>
					
					<label style="margin-left:20px">展示层级：</label>  
					<span>
					    <input name="flag" type="radio"  value="true" style="vertical-align:middle;" checked/>
					    <span style="vertical-align:middle;">第一层目录/文件</span> | 
					    <input name="flag" type="radio"  value="false" style="vertical-align:middle;"/>
					    <span style="vertical-align:middle;">全部展示</span>
					</span>
					
					<button class="stdbtn btn_yellow" style="margin-left:50px"> 暂停输出</button>
					<button class="stdbtn btn_yellow" style="margin-left:10px" onclick="files.search()">查 &nbsp&nbsp&nbsp&nbsp 询</button>
				</p>
			</div>

			<table id="dyntable2" cellpadding="0" cellspacing="0" border="0" class="stdtable" style="margin-bottom:200px">
				<tbody id="ajax-tbody-page" class="page-list">
					
				</tbody>
			</table>

		</div>
	</div>

</div>
















