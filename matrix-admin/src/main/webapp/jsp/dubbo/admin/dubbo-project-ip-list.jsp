<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp" %>


<script type="text/javascript" src="${jsp}/dubbo/admin/dubbo-project-ip-list.js"></script>
<script type="text/javascript">
	$(function() {
		var type_ = 'post';
		var url_ = '${basePath}application/ajaxFindDubboProjectIpList.do';
		var data_ = {application : '${application}'};  
		dubboProjectHostList.init('${basePath}' , '${application}').draw(ajaxs.sendAjax(type_, url_, data_)); 
	});
</script>

<div class="centercontent tables">
	<!--这个跳转页面的功能及跳转路径等等-->
	<div class="pageheader notab">
		<h1 class="pagetitle">Dubbo项目部署节点列表</h1>
		<span class="pagedesc" style="display:none">示例页面：jsp/dubbo/admin/dubbo-project-ip-list.jsp</span>
	</div>

	<div id="contentwrapper" class="contentwrapper">

		<%-- table-form 这个id分页使用 --%>
		<div id="table-form" class="dataTables_wrapper">
			<div class="contenttitle2">
				<p style="margin: 0px">
					<label>节点地址信息：</label> 
					<span class="field"> 
						<input id="node-address" type="text" name="nodeAddress" class="form-search" />
					</span>
					
					<a onclick="dubboProjectHostList.searchReset()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 10px"> 
						<span> 重 置 </span>
					</a> 
					<a onclick="dubboProjectHostList.search()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 20px"> 
						<span> 查 询 </span>
					</a>
				</p>
			</div>

			<table id="dyntable2" cellpadding="0" cellspacing="0" border="0" class="stdtable">
				<thead>
					<tr>
						<th class="head0 " width="200px">序号</th> 
						<th class="head0 " width="100px">节点地址信息</th> 
						<th class="head0 " width="100px">类型</th> 
						<th class="head0 " width="300px">操作</th>
					</tr>
				</thead>
				<tbody id="ajax-tbody-page" class="page-list">
					<%-- 等待填充 --%>
				</tbody>
			</table>

		</div>
	</div>

</div>



























