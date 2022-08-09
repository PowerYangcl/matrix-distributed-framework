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
			<h1 class="pagetitle">定时任务列表页面</h1>
			<span style="display: none">jsp/quartz/info/job-info.jsp</span>
		</div>
	
		<div id="contentwrapper" class="contentwrapper">
			<div id="table-form" class="dataTables_wrapper">
				<div class="contenttitle2">
					<p style="margin: 0px">
						<label>定时任务名称：</label> 
						<span class="field"> 
							<input id="job-name" type="text" name="jobName" class="form-search" />
						</span>  
						<label>定时任务标题：</label> 
						<span class="field"> 
							<input id="job-title" type="text" name="jobTitle" class="form-search" />
						</span>  
						<a onclick="jobinfo.pauseAllJob('0')" class="btn btn_orange btn_home radius50 security-btn" key="job_info_list:resume_all" style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
							<span> 全部恢复</span>
						</a>
						<a onclick="jobinfo.pauseAllJob('1')" class="btn btn_orange btn_home radius50 security-btn" key="job_info_list:pause_all" style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
							<span> 全部暂停 </span>
						</a>
						<a onclick="jobinfo.openAddDialog()" class="btn btn_orange btn_home radius50 security-btn" key="job_info_list:add" style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
							<span> 添 加 </span>
						</a> 
						<a onclick="jobinfo.searchReset()" class="btn btn_orange btn_search radius50 security-btn" key="job_info_list:reset" style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
							<span> 重 置 </span>
						</a> 
						<a onclick="jobinfo.search()" class="btn btn_orange btn_search radius50 security-btn" key="job_info_list:search" style="display:none;float: right; cursor: pointer; margin-left: 20px"> 
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
							<th class="head1" width="350px">任务名称 | 标题</th> 
							<th class="head1" style="width:150px">触发器规则</th>
							<th class="head1" style="width:200px">时间状态</th>
							<th class="head1" style="width:150px">任务组</th>
							<th class="head1" style="width:60px">锁有效时间</th>
							<th class="head1" style="width:60px">锁超时时间</th>
							<th class="head1" style="width:40px">被触发</th>
							<th class="head1" style="width:40px">日志</th>
							<th class="head1" style="width:60px">运行状态</th>
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




<!-- 添加定时任务弹出框 -> 带自定义滚动条 -->
<div id="add-dialog-div" class="dialog-page-div" style="display: none;width: 900px;height: 600px">
	<p class="dialog-title">
		<a href="javascript:void(0)" onclick="closeDialog()" class="dialog-close"></a>
		<span>
			添加定时任务
		</span>
	</p>
	<div class="contentwrapper">
		<div class="last">
			<div class="widgetbox" style="height: inherit">
				<div  class="title">
					<h3 style="text-transform: none;">
						Scheduler中轮询选择否，则不会不会被Quartz定时器轮询
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
						                		<td style="text-align: left;width:80px;">
						                			任务标题：
						                		</td>
						                		<td style="text-align: left">
						                			<input type="text" name="jobTitle" class="dialog-form-input" style="width:90%;" placeholder="20个字符以内"  maxlength="20">
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			定时周期：
						                		</td>
						                		<td style="text-align: left">
						                			<input type="text" name="jobTriger" class="dialog-form-input" style="width:90%;" placeholder="请输入Cron表达式：http://cron.qqe2.com/"  maxlength="40">
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			定时周期描述：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<input type="text" name="remark" class="dialog-form-input" style="width:96%;" placeholder="请输入Cron表达式的含义描述, 如：每天0点执行"  maxlength="512">
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			任务运行组：
						                		</td>
						                		<td style="text-align: left">
						                			<!-- <input type="text" name="runGroupId" class="dialog-form-input" style="width:90%;"> -->
						                			<select name="runGroupId" class="job-group" style="width:94%;">
						                				<!-- TODO 等待填充 -->
					                				</select>
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			并发执行：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="" class="field" style="padding-top:5px">
							                			<input type="radio" name="concurrentType" value="0" style="vertical-align:middle;" checked>
							                			<span style="vertical-align:middle;"> 否</span>
							                				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                			<input type="radio" name="concurrentType" value="1" style="vertical-align:middle;">
							                			<span style="vertical-align:middle;"> 是</span>
						                			</span>
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			锁有效时间：
						                		</td>
						                		<td style="text-align: left">
						                			<input type="text" name="expireTime" class="dialog-form-input" style="width:90%;" placeholder="单位：秒"  maxlength="2">
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			锁超时时间：
						                		</td>
						                		<td style="text-align: left">
						                			<input type="text" name="timeOut" class="dialog-form-input" style="width:90%;"  placeholder="单位：毫秒" maxlength="4">
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			Scheduler中轮询：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="" class="field" style="padding-top:5px">
							                			<input type="radio" name="trigerType" value="1" style="vertical-align:middle;" checked>
							                			<span style="vertical-align:middle;">是</span>
							                				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                			<input type="radio" name="trigerType" value="2" style="vertical-align:middle;">
							                			<span style="vertical-align:middle;">否</span>
						                			</span>
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			记录日志：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="" class="field" style="padding-top:5px">
							                			<input type="radio" name="logType" value="1" style="vertical-align:middle;" checked>
							                			<span style="vertical-align:middle;">否</span>
							                				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                			<input type="radio" name="logType" value="2" style="vertical-align:middle;">
							                			<span style="vertical-align:middle;">是</span>
						                			</span>
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			定时任务执行类完整路径：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<input type="text" name="jobClass" class="dialog-form-input" style="width:96%;" placeholder="如：com.matrix.quartz.JobForTestOne"  maxlength="256">
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			触发定时任务名称：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<input type="text" name="jobList" class="dialog-form-input" style="width:96%;" placeholder="UUID：UUID-9829384-0213410-OOKEIPAOA,UUID-9829384-0213410-OOKEIPAOB"  maxlength="2000">
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
					<button onclick="subpage.jobinfo.addJobInfo()" type="button" style="width: 100px;margin-left: 600px;" class="submit radius2">提交</button>
				</span>
			</p>
		</form>
	</div>
</div>



<!-- 修改定时任务弹出框 -> 带自定义滚动条 -->
<div id="edit-dialog-div" class="dialog-page-div" style="display: none;width: 900px;height: 600px">
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
					<h3 style="text-transform: none;">
						任务名称：<span id="job-name-edit"></span>
					</h3>
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
						                		<td style="text-align: left;width:80px;">
						                			任务标题：
						                		</td>
						                		<td style="text-align: left">
						                			<input type="text" id="job-title-edit" name="jobTitle" class="dialog-form-input" style="width:90%;" placeholder="20个字符以内"  maxlength="20">
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			定时周期：
						                		</td>
						                		<td style="text-align: left">
						                			<input type="text" id="job-triger-edit" name="jobTriger" class="dialog-form-input" style="width:90%;" placeholder="请输入Cron表达式：http://cron.qqe2.com/"  maxlength="40">
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			定时周期描述：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<input type="text" id="remark-edit" name="remark" class="dialog-form-input" style="width:96%;" placeholder="请输入Cron表达式的含义描述, 如：每天0点执行"  maxlength="512">
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			任务运行组：
						                		</td>
						                		<td style="text-align: left">
						                			<select id="run-group-id-edit" name="runGroupId" class="job-group" style="width:94%;">
						                				<!-- TODO 等待填充 -->
					                				</select>
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			并发执行：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="" class="field" style="padding-top:5px">
							                			<input type="radio" name="concurrentType" class="concurrent-type-edit" value="0" style="vertical-align:middle;" checked>
							                			<span style="vertical-align:middle;">否</span>
							                				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                			<input type="radio" name="concurrentType" class="concurrent-type-edit" value="1" style="vertical-align:middle;">
							                			<span style="vertical-align:middle;">是</span>
						                			</span>
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			锁有效时间：
						                		</td>
						                		<td style="text-align: left">
						                			<input type="text" id="expire-time-edit" name="expireTime" class="dialog-form-input" style="width:90%;" placeholder="单位：秒"  maxlength="2">
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			锁超时时间：
						                		</td>
						                		<td style="text-align: left">
						                			<input type="text" id="time-out-edit" name="timeOut" class="dialog-form-input" style="width:90%;"  placeholder="单位：毫秒" maxlength="4">
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			Scheduler中轮询：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="" class="field" style="padding-top:5px">
							                			<input type="radio" name="trigerType" class="triger-type-edit" value="1" style="vertical-align:middle;" checked>
							                			<span style="vertical-align:middle;">是</span>
							                				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                			<input type="radio" name="trigerType" class="triger-type-edit" value="2" style="vertical-align:middle;">
							                			<span style="vertical-align:middle;">否</span>
						                			</span>
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			记录日志：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="" class="field" style="padding-top:5px">
							                			<input type="radio" name="logType" class="log-type-edit" value="1" style="vertical-align:middle;" checked>
							                			<span style="vertical-align:middle;">否</span>
							                				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                			<input type="radio" name="logType" class="log-type-edit" value="2" style="vertical-align:middle;">
							                			<span style="vertical-align:middle;">是</span>
						                			</span>
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			定时任务执行类完整路径：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<input type="text" id="job-class-edit" name="jobClass" class="dialog-form-input" style="width:96%;" placeholder="如：com.matrix.quartz.JobForTestOne"  maxlength="256">
						                			<input type="hidden" id="id-edit" name="id" value=""><!-- 设置一个id保存隐藏域 -->
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			触发定时任务名称：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<input type="text" id="job-list-edit" name="jobList" class="dialog-form-input" style="width:96%;" placeholder="UUID：UUID-9829384-0213410-OOKEIPAOA,UUID-9829384-0213410-OOKEIPAOB"  maxlength="2000">
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
					<button onclick="subpage.jobinfo.editJobInfo()" type="button" style="width: 100px;margin-left: 600px;" class="submit radius2">提交</button>
				</span>
			</p>
		</form>
	</div>
</div>


<!-- 详情定时任务弹出框 -> 带自定义滚动条 -->
<div id="detail-dialog-div" class="dialog-page-div" style="display: none;width: 900px;height: 600px">
	<p class="dialog-title">
		<a href="javascript:void(0)" onclick="closeDialog()" class="dialog-close"></a>
		<span>
			定时任务详情
		</span>
	</p>
	<div class="contentwrapper">
		<div class="last">
			<div class="widgetbox" style="height: inherit">
				<div  class="title">
					<h3 style="text-transform: none;">
						任务运行状态：<span id="pause-detail"></span>
					</h3>
				</div>
				<div class="widgetcontent">
					<div id="detail-interface-list" class="mousescroll">
						<!-- 等待填充要展示的内容，如果超出div的高度则会展示出自定义的滚动条 -->
						<ul class="entrylist">	<!-- entrylist 是一个系统css类 -->
							<li>
								<form id="detail-dialog-table">
						            <table class="dialog-table" style="width:100%;border-collapse:separate; border-spacing:0px 12px;">
						                <tbody>
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			任务名称：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<input type="text" id="job-name-detail"  class="dialog-form-input" style="width:96%;" placeholder="UUID是唯一标识" >
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			任务标题：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="job-title-detail"></span>
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			定时周期：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="job-triger-detail"></span>
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			定时周期描述：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<span id="remark-detail"></span>
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			任务运行组：
						                		</td>
						                		<td style="text-align: left">
						                			<select id="run-group-id-detail" name="runGroupId" class="job-group" style="width:94%;">
						                				<!-- TODO 等待填充 -->
					                				</select>
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			并发执行：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="" class="field" style="padding-top:5px">
							                			<input type="radio" name="concurrentType" class="concurrent-type-detail" value="0" style="vertical-align:middle;" checked>
							                			<span style="vertical-align:middle;">否</span>
							                				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                			<input type="radio" name="concurrentType" class="concurrent-type-detail" value="1" style="vertical-align:middle;">
							                			<span style="vertical-align:middle;">是</span>
						                			</span>
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			锁有效时间：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="expire-time-detail"></span>
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			锁超时时间：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="time-out-detail"></span>
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			Scheduler中轮询：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="" class="field" style="padding-top:5px">
							                			<input type="radio" name="trigerType" class="triger-type-detail" value="1" style="vertical-align:middle;" checked>
							                			<span style="vertical-align:middle;">是</span>
							                				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                			<input type="radio" name="trigerType" class="triger-type-detail" value="2" style="vertical-align:middle;">
							                			<span style="vertical-align:middle;">否</span>
						                			</span>
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			记录日志：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="" class="field" style="padding-top:5px">
							                			<input type="radio" name="logType" class="log-type-detail" value="1" style="vertical-align:middle;" checked>
							                			<span style="vertical-align:middle;">否</span>
							                				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                			<input type="radio" name="logType" class="log-type-detail" value="2" style="vertical-align:middle;">
							                			<span style="vertical-align:middle;">是</span>
						                			</span>
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			定时任务执行类完整路径：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<span id="job-class-detail"></span>
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:160px;" colspan="1">
						                			触发定时任务名称：
						                		</td>
						                		<td style="text-align: left" colspan="3">
						                			<span id="job-list-detail"></span>
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			创建时间：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="create-time-detail"></span>
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			创建人：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="create-user-name-detail"></span>
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			更新时间：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="update-time-detail"></span>
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			更新人：
						                		</td>
						                		<td style="text-align: left">
						                			<span id="update-user-name-detail"></span>
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
					<button onclick="closeDialog()" type="button" style="width: 100px;margin-left: 600px;" class="submit radius2">确定</button>
				</span>
			</p>
		</form>
	</div>
</div>

<script type="text/javascript" src="${jsp}/quartz/info/job-info.js"></script>
<script type="text/javascript">
	$(function() {
		var type_ = 'post';
		var url_ = '${basePath}quartz/ajax_job_info_list.do';
		var data_ = null;
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		aForm.launch(url_, 'table-form', obj).init().drawForm(jobinfo.loadTable);
		
		jobinfo.init('${basePath}');
	});
</script>





















































