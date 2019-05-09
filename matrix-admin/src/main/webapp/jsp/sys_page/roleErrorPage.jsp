<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp"%>

<div class="centercontent">
	<div class="contentwrapper padding10">
		<div class="errorwrapper error403">
			<div class="errorcontent">
				<h1>服务访问拒绝</h1>

				<h3>服务器拒绝了您访问的功能，原因如下：</h3>

				<p>
					您可能没有这个权限来访问这个功能。 如果您想获得这个功能的使用权请联系系统管理员，他会为您分配这个权限。权限分配完成后<br /> 请在系统右上角中点击【退出】按钮并重新登录即可！注：请不要直接关闭浏览器。
				</p>
				<br />
				<button class="stdbtn btn_black" onclick="history.back()">返回上一页</button>
				&nbsp;
				<button onclick="location.href='/matrix-admin/index.jsp'" class="stdbtn btn_orange">回到首页</button>
			</div>
			<!--errorcontent-->
		</div>
		<!--errorwrapper-->
	</div>
</div>










