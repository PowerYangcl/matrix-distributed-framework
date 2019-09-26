<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp" %>
<script type="text/javascript">
	$(function() {		
		location.href='http://localhost:9080/authorization.htm?userName=${userInfo.userName}&password=${userInfo.password}';
	});
</script>



