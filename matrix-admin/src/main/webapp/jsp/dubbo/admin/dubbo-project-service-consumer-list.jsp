<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp" %>


<script type="text/javascript" src="${jsp}/dubbo/admin/dubbo-project-service-consumer-list.js"></script>
<script type="text/javascript">
	$(function() {
		var type_ = 'post';
		var url_ = '${basePath}application/ajaxFindDubboProjectServiceConsumerList.do';
		var data_ = {id : '${id}'};  
		dpConsumer.init('${basePath}' , '${id}').draw(ajaxs.sendAjax(type_, url_, data_)); 
	});
</script>

<div class="centercontent tables">
	<!--这个跳转页面的功能及跳转路径等等-->
	<div class="pageheader notab">
		<h1 class="pagetitle">发布服务列表-消费者列表页面</h1>
		<span class="pagedesc" style="display:none">示例页面：jsp/dubbo/admin/dubbo-project-service-consumer-list.jsp</span>
	</div>

	<div id="contentwrapper" class="contentwrapper">

		<%-- table-form 这个id分页使用 --%>
		<div id="table-form" class="dataTables_wrapper">
			<div class="contenttitle2">
				<!-- <p style="margin: 0px">
					<label>服务名称：</label> 
					<span class="field"> 
						<input id="service" type="text" name="service" class="form-search" />
					</span>
					<label>支持协议：</label> 
					<span class="field"> 
						<input id="protocol" type="text" name="protocol" class="form-search" />
					</span>
					
					<a onclick="dpConsumer.searchReset()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 10px"> 
						<span> 重 置 </span>
					</a> 
					<a onclick="dpConsumer.search()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 20px"> 
						<span> 查 询 </span>
					</a>
				</p> -->
			</div>

			<table id="dyntable2" cellpadding="0" cellspacing="0" border="0" class="stdtable">
				<thead>
					<tr>
						<th class="head0 " width="50px">#</th>
						<th class="head0 " width="200px">应用名称</th> 
						<th class="head0 " width="100px">拥有者</th> 
						<th class="head0 " width="100px">服务类型</th>
					</tr>
				</thead>
				<tbody id="ajax-tbody-page" class="page-list">
					<%-- 等待填充 --%>
				</tbody>
			</table>

		</div>
	</div>

</div>



























