<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<%@ include file="/inc/iframe-head.jsp" %>
<!-- 调用md5方法 -->
<script src="https://cdn.bootcss.com/blueimp-md5/2.10.0/js/md5.js"></script>
<script type="text/javascript" src="${jsp}/api/test/api-test.js"></script>
<div class="centercontent tables">
	<div class="pageheader notab">
		<h1 class="pagetitle">模拟测试接口请求</h1>
		<span class="pagedesc">调用之前请先确定是否已经在【api信息树】中添加了该接口信息</span>
		<span style="display: none">jsp/api/test/api-test-page.jsp</span>
	</div>
	<div class="contentwrapper" style="padding-top:0px;padding-left:0px">
		<div> 
			<div class="statusbox" style="width: 800px">
				<form id="api-test-form" action="javascript:void(0)">
					<div style="margin-bottom: 10px;">
						<span style="vertical-align:middle;">接 口 请 求 者：</span>&nbsp;&nbsp;
						<input type="radio" name="requester" value="133C9CB27DA0" style="vertical-align:middle;" checked="checked"> 
						<span style="vertical-align:middle;">developer-private</span> &nbsp;&nbsp;
						<input type="radio" name="requester" value="133C9CB27E18" style="vertical-align:middle;"> 
						<span style="vertical-align:middle;">developer-public<span style="color:red">&nbsp;&nbsp (请注意! 接口请求者列表中的这两条记录不可删除!)</span></span> 
					</div>
					<div>
						系统接口名称：
						<input type="text" id="target" name="target" class="smallinput " onmouseout="apiTest.findRequestDto(this)" placeholder="比如：TEST-PRIVATE-PROCESSOR" style="width: 300px; margin-bottom: 10px;">
					</div>
					<div style="margin-bottom: 10px;">
						<span style="vertical-align:middle;">客 户 端 类 型：</span>&nbsp;&nbsp;
						<input type="radio" name="client" value="0" style="vertical-align:middle;"> 
						<span style="vertical-align:middle;">IOS</span> &nbsp;&nbsp;
						<input type="radio" name="client" value="1" style="vertical-align:middle;"> 
						<span style="vertical-align:middle;">Android</span>&nbsp;&nbsp;
						<input type="radio" name="client" value="2" style="vertical-align:middle;"> 
						<span style="vertical-align:middle;">微信</span>&nbsp;&nbsp;
						<input type="radio" name="client" value="3" style="vertical-align:middle;" checked="checked"> 
						<span style="vertical-align:middle;">服务器</span> 
					</div>
					<div>
						客 户 端 版 本：
						<input type="text" id="version" name="version" class="smallinput "  value="vsesion-2.0.0.1" placeholder="比如：vsesion-2.0.0.1" style="width: 300px; margin-bottom: 10px;">
					</div>
					<div>
						请求发起时间：
						<input type="text" id="request-time" name="requestTime" class="smallinput " value="${date}"  placeholder="比如：2017-11-28 11:17:47" style="width: 300px; margin-bottom: 10px;">
					</div>
					<div>
						请求通路类型：
						<input type="text" id="channel" name="channel" class="smallinput " value="页面测试" style="width: 300px; margin-bottom: 10px;">
					</div>
					<div style="padding-right: 20px; margin-top: 10px; margin-bottom: 15px;">
						<textarea id="dto-json-str" name="" cols="" rows="" style="height: 100px; width: 790px" placeholder='这里将显示请求参数信息, 请回填关键数据'></textarea>
					</div>
					
					<div style="padding-right: 20px; margin-top: 10px; margin-bottom: 15px;">
						<textarea id="json-response" name="" cols="" rows="" style="height: 200px; width: 790px" placeholder="这里将显示数据请求结果"></textarea>
					</div>
					<div class="submit">
						<button class="stdbtn btn_orange" onclick="apiTest.getResponseMsg('${basePath}')">获取结果</button> 
					</div>
				</form>
			</div>
		</div>
	</div>


</div>




























