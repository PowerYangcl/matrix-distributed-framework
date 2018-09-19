<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp" %>


<script type="text/javascript" src="${jsp}/example/demo/example-landed-property.js"></script>
<script type="text/javascript">
	$(function() {
		var type_ = 'post';
		var url_ = '${basePath}demos/ajax_demo_landed_property_list.do';
		var data_ = null; // 可以为null，后台会进行默认处理
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		aForm.launch(url_, 'table-form', obj).init().drawForm(demo.loadTable);
	});
</script>

<div class="centercontent tables">
	<!--这个跳转页面的功能及跳转路径等等-->
	<div class="pageheader notab">
		<h1 class="pagetitle">贴近实际业务来展示一个完整的开发流程</h1>
		<span class="pagedesc">示例页面：jsp/example/demo/page-landed-property-list.jsp</span>
	</div>

	<div id="contentwrapper" class="contentwrapper">

		<%-- table-form 这个id分页使用 --%>
		<div id="table-form" class="dataTables_wrapper">
			<div class="contenttitle2">
				<p style="margin: 0px">
					<label>小区名称：</label> 
					<span class="field"> 
						<input id="title-list-page" type="text" name="title" class="form-search" />
					</span> 
					
					<!-- <label>所属城市：</label> 
					<span class="field"> 
						<input id="" type="text" name="" class="form-search" />
					</span>  -->
					
					<label>所属城市：</label> 
					<span class="field"> 
						<select id="city-list-page" name="city" class="form-search">
							<option value="">请选择---</option>
							<option value="北京">北京</option>
							<option value="天津">天津</option>
							<option value="上海">上海</option>
							<option value="广州">广州</option>
							<option value="深圳">深圳</option> 
							<option value="成都">成都</option> 
						</select>
					</span> 
					
					<a onclick="demo.searchReset()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 10px"> 
						<span> 重 置 </span>
					</a> 
					<a onclick="demo.search()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 20px"> 
						<span> 查 询 </span>
					</a>
				</p>
			</div>

			<div id="dyntable2_length" class="dataTables_length dialog-show-count">
				<label> 当前显示 <%-- TODO 注意：select-page-size 这个ID是写定的，如果没有这个显示条数，则默认显示10条 - Yangcl --%> 
					<select id="select-page-size" size="1" name="dyntable2_length" onchange="aForm.formPaging('1')">
						<option value="10">10</option>
						<option value="25">25</option>
						<option value="50">50</option>
						<option value="100">100</option>
					</select> 条记录
				</label>
			</div>

			<table id="dyntable2" cellpadding="0" cellspacing="0" border="0" class="stdtable">
				<thead>
					<tr>
						<th class="head0 sorting_asc" width="200px">小区名称</th> <%-- sorting_asc 代表升序排列--%>
						<th class="head0 sorting_desc" width="100px">城市</th> <%-- sorting_desc 代表降序排列--%>
						<th class="head0 sorting" width="100px">楼盘编号</th>
						<th class="head0 sorting" width="100px">入住数量</th>
						<th class="head0 sorting" width="100px">价格</th>
						<th class="head0 sorting" width="150px">绿化率</th>
						<th class="head0 sorting" width="150px">创建时间</th> 
						<th class="head0 " width="100px">操作</th>
					</tr>
				</thead>
				<tbody id="ajax-tbody-page" class="page-list">
					<!--  class="page-list" 标识是页面数据列表 行变色使用 -->
					<%-- 等待填充 --%>
				</tbody>
			</table>

		</div>
	</div>

</div>



























