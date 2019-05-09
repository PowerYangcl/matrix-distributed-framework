<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp"%>

<div class="centercontent">
	<div class="pageheader notab">
		<h1 class="pagetitle">图片与文件上传应用示例</h1>
		<span class="pagedesc"> 上传图片、文件什么的，页面路径：【jsp/example/pageExampleFileUpload.jsp】 </span> 
		<span style="display: none">jsp/example/pageExampleFileUpload.jsp</span>
	</div>

	 
	
	<div class="subcontent" style="display: block; margin-top: 100px; margin-left: 20px">
		<div id="validation" class="subcontent" style="display: block">
			<div>
				<div id="show-upload-image-div"  class="field" style="margin-bottom:20px">
						<!-- 等待填充 -->
				</div>
				<iframe src="../jsp/sys_page/uploadImage.jsp" style="height:40px;"></iframe>
			</div>
		</div>
	</div>
	
	
</div>


<script type="text/javascript">
	/**
	 * uploadImage.jsp 回调此方法，此处的方法名固定，不可修改。
	 */
	function uploadFile(e) {
		var html_ = '';
		if(e.status == 'success'){
			if(e.type == 'image'){
				html_ = '<img src="' + e.url + '" title= "' + e.original + '" style="margin-bottom:20px;width:20%;" /></br>';
			}else{
				html_ = '<a href="' + e.url + '" style="margin-bottom:20px">' + e.original + '</a></br>';
			}
			$("#show-upload-image-div").append(html_);
		}
	}
</script>









