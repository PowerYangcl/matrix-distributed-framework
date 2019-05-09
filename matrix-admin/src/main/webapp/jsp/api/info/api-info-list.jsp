<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp" %>

<!-- 调用md5方法|已经备份blueimp-md5-2.10.0-md5.js -->
<!-- <script src="https://cdn.bootcss.com/blueimp-md5/2.10.0/js/md5.js"></script> -->
<script type="text/javascript" src="${js}/utils/blueimp-md5-2.10.0-md5.js"></script>

<link rel="stylesheet" href="${css}/ztree/zTreeStyle.css" type="text/css" />

<script type="text/javascript" src="${js}/ztree/jquery.ztree.all.js"></script>
<script type="text/javascript" src="${jsp}/api/info/api-info.js"></script>

<style type="text/css">
    .tree-left{
        /* border: solid #FB9337 2px; */
        height: 100%;
        width: 30%; 
        float: left;
    }
    .tree-right{
        /* border: solid #78CE07 2px; */
        height: 100%;
        width: 30%;
        margin-right: 400px;
        float: right;
        position:relative;
    }
    .tree-table-right{
        border: solid #78CE07 2px;
        height: 100%;
        width: 40%;
        margin-right: 400px;
        float: right;
        position:relative;
    }
    .right-padding{
        padding-top: 10px;
        padding-bottom: 10px;
    }
    .right-size{
        height: 25px;
        width: 200px;
    }
    .a-btn{
    	cursor: pointer;
    	color:#FB9337; 
    }
    .a-btn:hover {
    	color: red;
   	} 
   	
</style>


<div class="centercontent">
	<div class="pageheader notab">
		<h1 class="pagetitle">API树形结构列表</h1>
		<span class="pagedesc">同层节点之间可以进行拖动来调整其先后顺序 </span>
		<span style="display: none">jsp/api/info/api-info-list.jsp</span>
	</div>
	<div class="contentwrapper" style="padding-top: 0px;">
		<div id="nav-menu" class="subcontent">
			<div class="contenttitle2" style="margin-top: 5px; margin-bottom: 5px;">
				<h3>
					<a herf="javascript:void(0)" onclick="subpage.apiInfo.closeNavi('api-tree')" class="a-btn" title="收起导航栏从而方便您的操作">收起</a>
				</h3>
			</div>
			<div class="stdform">
				<div class="tree-left">
					<div id="api-list" class="mousescroll">
						<ul id="api-tree" class="ztree"></ul>
					</div>
				</div>
				<div class="tree-right" style="padding: 5px">
					<form id="tree-node-edit" action="javascript:void(0)"></form>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		// 开始初始化树型结构
		apiInfo.launch('${basePath}').apiTreeInit();  
		// 自定义滚动条 | 执行此代码自定义滚动条则生效
		$('#api-list').slimscroll({
			color: '#666',
			size: '10px',
			width: 'auto',
			height: '630px' // '208px'
		});
	});  
</script>



<!-- 域名列表弹窗 -->
<div id="ul-dialog-div" class="dialog-page-div" style="display: none;width: 500px;height: 300px">
	<p class="dialog-title">
		<a href="javascript:void(0)" onclick="subpage.apiInfo.closeDialog()" class="dialog-close"></a>
		<span>
			可用域名列表
		</span>
	</p>
	<div id="dialog-content-wrapper" class="contentwrapper">
		<div class="last">
			<div class="widgetbox" style="height: inherit">
				<div  class="title">
					<h3 id="platform-title">
						每个api可对多个域名开放
					</h3>
				</div>
				<div class="widgetcontent">
					<div id="interface-list" class="mousescroll">
						<!-- 等待填充要展示的内容，如果超出div的高度则会展示出自定义的滚动条 -->
						<ul id="api-include-domain-list" class="entrylist">	<!-- entrylist 是一个系统css类 -->
						</ul>
					</div>
				</div>
			</div>
		</div>
		<form class="stdform">
			<p>
				<span id="dialog-operate" style="position: relative;">
					<!-- 等待填充 弹窗操作按钮 如添加和修改等等 -->
					<button type="button"  class="submit radius2 security-btn" key="api_tree:open_domain" style="display:none;width:100px;margin-left: 350px;" onclick="subpage.apiInfo.saveOpenDomain();">确认开放</button>
				</span>
			</p>
		</form>
	</div>
</div>





<div id="api-test-dialog" class="dialog-page-div" style="display: none;width: 880px;height:800px">
	<p class="dialog-title">
		<a href="javascript:void(0)" onclick="subpage.apiInfo.closeDialog()" class="dialog-close"></a>
		<span>
			模拟测试接口请求
		</span>
	</p>
	<div id="dialog-content-wrapper" class="contentwrapper">
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
						<input type="text" id="api-target" name="target" class="smallinput " placeholder="比如：TEST-PRIVATE-PROCESSOR" style="width: 300px; margin-bottom: 10px;">
					</div>
					<div>
						用户登录令牌：
						<input type="text" id="access-token" name="accessToken" class="smallinput " placeholder="比如：63a9f0ea7bb98050796b6490796b649e85481845" style="width: 300px; margin-bottom: 10px;">
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
						<input type="text" id="request-time" name="requestTime" class="smallinput " value=""  placeholder="比如：2017-11-28 11:17:47" style="width: 300px; margin-bottom: 10px;">
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
					
					<input type="hidden" id="service-url" name="serviceUrl" value="">
					
					<div class="submit">
						<button class="stdbtn btn_orange" onclick="subpage.apiInfo.getResponseMsg()">获取结果</button> 
					</div>
				</form>
			</div>
		</div>
	</div>
</div>



























