<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- <html xmlns="http://www.w3.org/1999/xhtml">
<head> -->
	<%@ include file="/inc/iframe-head.jsp"%>
	<style type="text/css">
	</style>

	<div class="centercontent tables">
		<!--这个跳转页面的功能及跳转路径等等-->
		<div class="pageheader notab">
			<h1 class="pagetitle">定时任务分组列表页面</h1>
			<span style="display: none">jsp/quartz/group/job-group.jsp</span>
			<span class="pagedesc">一个定时任务分组可以关联多个IP地址段，请注意!他们是以英文半角逗号分隔</span>
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
						
						<a onclick="jobgroup.openAddDialog()" class="btn btn_orange btn_home radius50 security-btn" key="job_group_list:add" style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
							<span> 添 加 </span>
						</a> 
						<a onclick="jobgroup.searchReset()" class="btn btn_orange btn_search radius50 security-btn" key="job_group_list:reset" style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
							<span> 重 置 </span>
						</a> 
						<a onclick="jobgroup.search()" class="btn btn_orange btn_search radius50 security-btn" key="job_group_list:search" style="display:none;float: right; cursor: pointer; margin-left: 20px"> 
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
							<th class="head1" width="150px">分组名称</th> 
							<th class="head1" >ip地址</th>
							<th class="head1" style="width:130px">创建时间</th>
							<th class="head1" style="width:90px">创建人</th>
							<th class="head1" style="width:130px">更新时间</th> 
							<th class="head1" style="width:90px">更新人</th>
							<th class="head1 " width="200px">操作</th>
						</tr>
					</thead>
	
					<tbody id="ajax-tbody-1" class="page-list">
						<%-- 等待填充 --%>
					</tbody>
				</table>
	
			</div>
		</div>
	
	</div>

<!-- 添加定时任务分组弹出框 -> 带自定义滚动条 -->
<div id="add-dialog-div" class="dialog-page-div" style="display: none;width: 900px;height: 300px">
	<p class="dialog-title">
		<a href="javascript:void(0)" onclick="closeDialog()" class="dialog-close"></a>
		<span>
			添加定时任务分组
		</span>
	</p>
	<div class="contentwrapper">
		<div class="last">
			<div class="widgetbox" style="height: inherit">
				<div  class="title">
					<h3 style="text-transform: none;">
						已经关联定时任务的分组将无法删除
					</h3>
				</div>
				<div class="widgetcontent">
					<div id="add-interface-list" class="mousescroll">
						<!-- 等待填充要展示的内容，如果超出div的高度则会展示出自定义的滚动条 -->
						<ul class="entrylist">	<!-- entrylist 是一个系统css类 -->
							<li>
								<form id="add-dialog-table">
						            <table class="dialog-table" style="width:100%;border-collapse:separate; border-spacing:0px 12px;">
						                <tbody>
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			定时任务分组名称：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<input type="text" name="groupName" class="dialog-form-input" style="width:96%;" placeholder="25个字以内"  maxlength="25">
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			分组包含IP地址段：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<input type="text" name="ip" class="dialog-form-input" style="width:96%;" placeholder="IP地址段以逗号分隔。如：172.22.134.33,172.22.134.34"  maxlength="2000">
						                		</td>
						                	</tr>
						                </tbody>
						            </table>
					            </form>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<form class="stdform" action="javascript:void(0)">
			<p>
				<span id="dialog-operate" style="position: relative;"> 
					<button onclick="subpage.jobgroup.addJobGroup()" type="button" style="width: 100px;margin-left: 600px;" class="submit radius2">提交</button>
				</span>
			</p>
		</form>
	</div>
</div>



<!-- 修改定时任务弹出框 -> 带自定义滚动条 -->
<div id="edit-dialog-div" class="dialog-page-div" style="display: none;width: 900px;height: 300px">
	<p class="dialog-title">
		<a href="javascript:void(0)" onclick="closeDialog()" class="dialog-close"></a>
		<span>
			修改定时任务
		</span>
	</p>
	<div class="contentwrapper">
		<div class="last">
			<div class="widgetbox" style="height: inherit">
				<div  class="title">
					<h4 style="text-transform: none;">
						修改IP地址可以控制定时任务在服务器集群中的执行强度(个数) 非并发类的定时任务会显著受到影响
					</h4>
				</div>
				<div class="widgetcontent">
					<div id="edit-interface-list" class="mousescroll">
						<!-- 等待填充要展示的内容，如果超出div的高度则会展示出自定义的滚动条 -->
						<ul class="entrylist">	<!-- entrylist 是一个系统css类 -->
							<li>
								<form id="edit-dialog-table">
						            <table class="dialog-table" style="width:100%;border-collapse:separate; border-spacing:0px 12px;">
						                <tbody>
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			定时任务分组名称：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<input id="id-edit" type="hidden" name="id" value=""><!-- 设置一个id保存隐藏域 -->
						                			<input id="group-name-edit" type="text" name="groupName" class="dialog-form-input" style="width:96%;" placeholder="25个字以内"  maxlength="25">
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			分组包含IP地址段：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<input id="ip-edit" type="text" name="ip" class="dialog-form-input" style="width:96%;" placeholder="IP地址段以逗号分隔。如：172.22.134.33,172.22.134.34"  maxlength="2000">
						                		</td>
						                	</tr>
						                </tbody>
						            </table>
					            </form>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<form class="stdform" action="javascript:void(0)">
			<p>
				<span id="dialog-operate" style="position: relative;"> 
					<button onclick="subpage.jobgroup.editJobGroup()" type="button" style="width: 100px;margin-left: 600px;" class="submit radius2">提交</button>
				</span>
			</p>
		</form>
	</div>
</div>




<script type="text/javascript" src="${jsp}/quartz/group/job-group.js"></script>
<script type="text/javascript">
	$(function() {
		var type_ = 'post';
		var url_ = '${basePath}quartz/ajax_job_group_page_list.do';
		var data_ = null;
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		aForm.launch(url_, 'table-form', obj).init().drawForm(jobgroup.loadTable);
		
		jobgroup.init('${basePath}');
	});
</script>





















































