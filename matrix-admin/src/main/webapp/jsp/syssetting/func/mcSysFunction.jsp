<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp" %>

<link rel="stylesheet" href="${css}/ztree/zTreeStyle.css" type="text/css" />

<script type="text/javascript" src="${js}/ztree/jquery.ztree.all.js"></script>
<!-- 系统角色权限 -->
<script type="text/javascript" src="${js}/system/sys-user-role-function.js"></script>
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
		<h1 class="pagetitle">系统功能树</h1>
		<span class="pagedesc">【导航与菜单树】同层节点之间可以进行拖动来调整其先后顺序 </span>
		<span style="display: none">jsp/syssetting/func/mcSysFunction.jsp</span>
	</div>
	<div class="contentwrapper" style="padding-top: 0px;">
		<div id="nav-menu" class="subcontent">
			<div class="contenttitle2" style="margin-top: 5px; margin-bottom: 5px;">
				<h3>
					<a herf="javascript:void(0)" onclick="surfunc.closeNavi('sys-tree')" class="a-btn" title="收起导航栏从而方便您的操作">收起导航</a>| 
					<a herf="javascript:void(0)" onclick="surfunc.closeMenu('sys-tree')" class="a-btn" title="收起一级菜单栏从而方便您的操作">收起菜单</a>
				</h3>
			</div>
			<div class="stdform">
				<div class="tree-left">
					<div id="func-list" class="mousescroll">
						<ul id="sys-tree" class="ztree"></ul>
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
		surfunc.sysTreeOperation(); 
		// 自定义滚动条 | 执行此代码自定义滚动条则生效
		$('#func-list').slimscroll({
			color: '#666',
			size: '10px',
			width: 'auto',
			height: '630px' // '208px'
		});
	});  
</script>

































