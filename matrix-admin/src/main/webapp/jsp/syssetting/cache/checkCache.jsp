<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<%@ include file="/inc/iframe-head.jsp" %>

<div class="centercontent tables">
	<div class="pageheader notab">
		<h1 class="pagetitle">查看缓存中的信息</h1>
		<span style="display:none">jsp/syssetting/cache/checkCache.jsp</span>  
	</div>

	<div class="contentwrapper">
		<div> 
			<div class="statusbox" style="width: 800px">
				<form action="javascript:void(0)">
					<div class="status_thumb_list" style="width: 308px">缓存类型：</div>
					<select id="type" name="type" class="radius3" style="margin-top: 5px;">
						<option value="dict">字典缓存</option>
						<option value="serv">服务缓存</option>
					</select>

					<div class="status_thumb_list" style="margin-top: 15px; width: 308px;">前缀：</div>
					<input id="prefix" name="prefix" type="text" placeholder="McUserRole" style="width: 308px">
					<div class="status_thumb_list" style="margin-top: 15px; width: 308px;">缓存中的key：</div> 
					<input id="cache-key" name="key" type="text" placeholder="80160001" style="width: 308px">


					<div style="padding-right: 20px; margin-top: 10px; margin-bottom: 15px;">
						<textarea id="json-str" name="" cols="" rows="" style="height: 400px; width: 790px" placeholder='这里将显示缓存信息'></textarea>
					</div>
					<div class="submit">
						<button class="stdbtn btn_orange" onclick="batchDeleteCache()">批量删除缓存</button>&nbsp&nbsp
						<button class="stdbtn btn_orange" onclick="addCache()">设置缓存</button>&nbsp&nbsp
						<button class="stdbtn btn_orange" onclick="addCacheForever()">设置缓存(永久)</button>&nbsp&nbsp
						<button class="stdbtn btn_orange" onclick="deleteCache()">删除缓存</button>&nbsp&nbsp
						<button class="stdbtn btn_orange" onclick="getCache()">获取缓存</button>
					</div>
				</form>
			</div>
		</div>
	</div>


</div>

<script type="text/javascript">
	function getCache(){
		var type_ = 'post';
		var url_ = '${basePath}cache/ajax_get_cache_value.do?prefix=' + $("#prefix").val() + '&key=' + $("#cache-key").val() + '&type=' + $("#type").val();
		var data_ = null;
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		if (obj.status == 'success') {
			$("#json-str").val(JSON.stringify(obj.data));
		}else{
			$("#json-str").val("");
			malert(obj.msg, '系统提示');
		}
	}

	function deleteCache(){
		var type_ = 'post';
		var url_ = '${basePath}cache/ajax_delete_cache.do?prefix=' + $("#prefix").val() + '&key=' + $("#cache-key").val() + '&type=' + $("#type").val();
		var data_ = null;
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		$("#json-str").val("");
		malert(obj.msg, '系统提示');
	}

	function batchDeleteCache() {
        var type_ = 'post';
        var url_ = '${basePath}cache/ajax_batch_delete_cache.do?prefix=' + $("#prefix").val() + '&key=' + $("#cache-key").val() + '&type=' + $("#type").val();
        var data_ = null;
        var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
        $("#json-str").val("");
        malert(obj.msg, '系统提示');
    }

	
	function addCache(){
		var type_ = 'post';
		var url_ = '${basePath}cache/ajax_reset_cache.do?prefix=' + $("#prefix").val() + '&key=' + $("#cache-key").val() + '&type=' + $("#type").val();
		var data_ = {
			jsonStr:$("#json-str").val()
		};
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		$("#json-str").val(""); 
		malert(obj.msg, '系统提示');
	}
	
	function addCacheForever(){
		var type_ = 'post';
		var url_ = '${basePath}cache/ajax_reset_cache_forever.do?prefix=' + $("#prefix").val() + '&key=' + $("#cache-key").val() + '&type=' + $("#type").val();
		var data_ = {
			jsonStr:$("#json-str").val()
		};
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		$("#json-str").val(""); 
		malert(obj.msg, '系统提示');
	}
</script>




























