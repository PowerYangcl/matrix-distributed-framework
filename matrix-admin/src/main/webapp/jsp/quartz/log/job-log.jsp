<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp"%>
<style type="text/css">
</style>

<div class="centercontent tables">
	<!--这个跳转页面的功能及跳转路径等等-->
	<div class="pageheader notab">
		<h1 class="pagetitle">定时任务日志列表页面</h1>
		<span style="display: none">jsp/quartz/log/job-log.jsp</span>
		<span class="pagedesc">定时任务需要开启日志功能才能进行查询，否则不会记录定时任务的执行日志信息</span>
	</div>

	<div id="contentwrapper" class="contentwrapper">
		<div id="table-form" class="dataTables_wrapper">
			<div class="contenttitle2">
				<p style="margin: 0px">
					<label>分组名称：</label> 
					<span class="field"> 
						<input id="group-name" type="text" name="groupName" class="form-search" />
					</span>  
					<label>IP地址段：</label> 
					<span class="field"> 
						<input id="ip" type="text" name="ip" class="form-search" />
					</span>  
					<label>执行状态：</label> 
					<span class="field"> 
						<select id="status" name="status" class="form-search">
							<option value="">----所有----</option>
							<option value="success">success</option>
							<option value="error">error</option>
							<option value="exception">exception</option>
						</select>
					</span>  
					
					<a onclick="joblog.searchReset()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 10px"> 
						<span> 重 置 </span>
					</a> 
					<a onclick="joblog.search()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 20px"> 
						<span> 查 询 </span>
					</a>
				</p>
			</div>

			<div id="dyntable2_length" class="dataTables_length dialog-show-count">
				<label> 当前显示 
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
						<th class="head1" width="320px">任务名称</th> 
						<th class="head1" >任务标题</th>
						<th class="head1" style="width:90px">执行状态</th>
						<th class="head1" style="width:150px">分组名称</th>
						<th class="head1" style="width:90px">IP地址</th>
						<th class="head1" style="width:130px">开始时间</th> 
						<th class="head1" style="width:130px">结束时间</th> 
						<th class="head1 " width="90px">操作</th>
					</tr>
				</thead>

				<tbody id="ajax-tbody-1" class="page-list">
					<%-- 等待填充 --%>
				</tbody>
			</table>

		</div>
	</div>

</div>


<!-- 详情定时任务弹出框 -> 带自定义滚动条 -->
<div id="detail-dialog-div" class="dialog-page-div" style="display: none;width: 900px;height: 600px">
	<p class="dialog-title">
		<a href="javascript:void(0)" onclick="closeDialog()" class="dialog-close"></a>
		<span>
			定时任务日志详情
		</span>
	</p>
	<div class="contentwrapper">
		<div class="last">
			<div class="widgetbox" style="height: inherit">
				<div  class="title">
					<h3 style="text-transform: none;">
						任务执行状态：<span id="status-detail" style="color:red"></span>
					</h3>
				</div>
				<div class="widgetcontent">
					<div id="detail-interface-list" class="mousescroll">
						<!-- 等待填充要展示的内容，如果超出div的高度则会展示出自定义的滚动条 -->
						<ul class="entrylist">	<!-- entrylist 是一个系统css类 -->
							<li>
								<form id="detail-dialog-table">
									<textarea id="remark-detail" rows="20" cols="450" style="width:800px"></textarea>
					            </form>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>



<script type="text/javascript" src="${jsp}/quartz/log/job-log.js"></script>
<script type="text/javascript">
	$(function() {
		var type_ = 'post';
		var url_ = '${basePath}quartz/ajax_job_log_list.do';
		var data_ = {
				jobName:'${jobName}'
		};
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		aForm.launch(url_, 'table-form', obj).init().drawForm(joblog.loadTable);
		
		joblog.init('${basePath}' , '${jobName}');
	});
</script>





















































