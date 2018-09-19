<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp" %>

<div class="field"> 
	<form id="upload-image" action="javascript:void(0)" >
		<a href="javascript:void(0);" class="btn btn_orange btn_search radius50" style="cursor: pointer;"> 
			<span> 上传文件 </span>
		</a>
		<input id="select-pic"  type="file" name="file" class="ae-form-input" style="width: 71px; height: 33px;position: absolute;left: 0;top: 0;opacity: 0;" />
	</form>
</div>


<script type="text/javascript">
	$(function() {
		$("#select-pic").change(function(){
			var file_ = document.getElementById("select-pic").files[0]; // 获取文件对象
            if (typeof (file_) == "undefined" || file_.size <= 0) {
                // 没有择图片
                return;
            }
			
            var formFile = new FormData();
            formFile.append("file", file_); // 加入文件对象
            var data = formFile;
            $.ajax({
                url : localStorage.uploadUrl,  // 独立的文件服务器地址
                data : formFile,
                type : "post",
                dataType : "json",
                cache : false,		// 上传文件无需缓存
                processData : false,		// 用于对data参数进行序列化处理 这里必须false
                contentType : false, 	// 必须
                success : function (e) { 
        			window.parent.uploadFile(e);
                },
                error : function(e){
                }
            })
		});
	});
	
 </script>
