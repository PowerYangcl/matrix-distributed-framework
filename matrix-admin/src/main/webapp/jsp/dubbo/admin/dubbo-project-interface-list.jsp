<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp" %>


<script type="text/javascript" src="${jsp}/dubbo/admin/dubbo-project-interface-list.js"></script>
<script type="text/javascript">
	$(function() {
		var type_ = 'post';
		var url_ = '${basePath}application/ajaxFindDubboProjectInterfaceList.do';
		var data_ = {
				application : '${application}',
				nodeAddress : '${nodeAddress}' 
		};
		dubboProjectRpcList.init('${basePath}' , '${application}' , '${nodeAddress}').draw(ajaxs.sendAjax(type_, url_, data_));
	});
</script>

<div class="centercontent tables">
	<!--这个跳转页面的功能及跳转路径等等-->
	<div class="pageheader notab">
		<h1 class="pagetitle">Dubbo项目指定部署节点下的RPC接口类列表集合</h1>
		<span class="pagedesc" style="display:none">示例页面：jsp/dubbo/admin/dubbo-project-interface-list.jsp</span>
	</div>

	<div id="contentwrapper" class="contentwrapper">

		<%-- table-form 这个id分页使用 --%>
		<div id="table-form" class="dataTables_wrapper">
			<div class="contenttitle2">
				<p style="margin: 0px">
					<label>Rpc接口名称：</label> 
					<span class="field"> 
						<input id="service-key" type="text" name="serviceKey" class="form-search" />
					</span> 
					
					<label>&nbsp&nbsp&nbsp&nbsp</label>
					
					<label>类型：</label> 
					<span class="field"> 
						<select id="dynamic" name="dynamic" class="form-search">
							<option value="">所有---</option>
							<option value="true">动态</option>
							<option value="false">静态</option>
						</select>
					</span> 
					
					<label>&nbsp&nbsp&nbsp&nbsp</label>
					
					<label>状态：</label> 
					<span class="field"> 
						<select id="enabled" name="enabled" class="form-search">
							<option value="">所有---</option>
							<option value="true">已启用</option>
							<option value="false">已禁用</option>
						</select>
					</span> 
					
					<a onclick="dubboProjectRpcList.searchReset()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 10px"> 
						<span> 重 置 </span>
					</a> 
					<a onclick="dubboProjectRpcList.search()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 20px"> 
						<span> 查 询 </span>
					</a>
				</p>
			</div>

			<table id="dyntable2" cellpadding="0" cellspacing="0" border="0" class="stdtable">
				<thead>
					<tr>
						<th class="head0 " width="300px">Rpc接口名称</th> 
						<th class="head0 " width="100px">权重</th> 
						<th class="head0 " width="100px">类型</th> 
						<th class="head0 " width="100px">状态</th> 
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



























