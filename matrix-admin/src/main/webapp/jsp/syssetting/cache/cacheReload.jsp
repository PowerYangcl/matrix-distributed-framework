<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<%@ include file="/inc/iframe-head.jsp" %>

<div class="centercontent tables">

	<div class="pageheader notab">
		<h1 class="pagetitle">重置所有用户的缓存【危险的权限操作！】</h1>  
		<span style="display:none">jsp/syssetting/cache/cacheReload.jsp</span>
	</div>

	<div id="contentwrapper" class="contentwrapper">
		<div id="dyntable2_wrapper" class="dataTables_wrapper"> 
			<div class="subcontent" style="display: block; margin-top: 100px; margin-left: 20px">
				<a id="reload-all-user" href="javascript:void(0);" class="anchorbutton">用户缓存重置</a> &nbsp; 
			</div>
		</div>
	</div>

</div>

<script type="text/javascript">
$(function() {
	$('#reload-all-user').click(function() {
		mconfirm('您确定要重置所有用户级别的字典缓存吗? 这是一个高风险操作！', '系统提示', function(flag) {
			if(flag){
				var url_ = '${basePath}sysrole/system_sysrole_dict_cache_reload.do';
		        var data_ = null;
		        var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
	        	malert(obj.msg, '系统提示'); 
			}
		});
		return false;
	});

})
</script>




























