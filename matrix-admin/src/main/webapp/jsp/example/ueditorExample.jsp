<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp"%>

<style type="text/css">

</style>


<div class="centercontent tables">
	<!--这个跳转页面的功能及跳转路径等等-->
	<div class="pageheader notab">
		<h1 class="pagetitle">ueditor 示例</h1>
		<!-- <span class="pagedesc">
                        这个页面描述了系统中最常用的功能：自定义提示框。
                    </span> -->
		<span style="display: none">jsp/example/ueditorExample.jsp</span>
	</div>

	<div id="contentwrapper" class="contentwrapper">
		<div id="dyntable2_wrapper" class="dataTables_wrapper">

			<div class="subcontent" style="display: block; margin-top: 10px; margin-left: 20px">
				<script id="editor" type="text/plain" style="width:1024px;height:500px;"></script> 
				<a href="javascript:void(0)" class="anchorbutton" onclick="saveArticle()">保存</a>  
			</div>

		</div>
	</div>
</div>


<script type="text/javascript" charset="utf-8" src="${js}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${js}/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="${js}/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	var ue = UE.getEditor('editor');
	
	$(function(){
		var pd = $(window.parent.document);
		var w = pd[0].body.clientWidth - pd.find("#left-menu div:visible")[0].clientWidth - 140;
		var h = pd[0].body.clientHeight -350 ;
		$("#editor").width(w);
		$("#editor").height(h);
		
		// 开始自定义
		
		//自定义ueditor的上传文件方法
		UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
	    UE.Editor.prototype.getActionUrl = function(action) {  
	        if (action == 'uploadimage' ) {  
	        	 return '${basePath}example/ajax_upload_file_cfile.do?type=' + action;   // 上传使用自定义的方法
	        	//return 'http://fq.test.com:8081/cfamily/upload1/upload';        
	        } else if(action == 'uploadfile'){
	        	return 'http://172.21.1.159:8081/cfamily/upload/upload';        
	        }else {  
	            return this._bkGetActionUrl.call(this, action);  
	        }  
	    }; 
	    /* //自定义按钮  跳转商品详情的按钮
	     UE.commands['hjy_add_product'] = {
	    		execCommand : function(){
    	    	console.log(0);
    	    	
    	    	var range = this.selection.getRange(),
    	        node = range.getClosedNode();
    	    	node.onclick=function(){window.open('http://test-wx.huijiayou.cn/Product_Detail.html?pid=8016410843&fromshare=1&wxPhone=&goods_num:8016410843')}
    	    	 
    	    } ,
    	    queryCommandState:function(){
    	    	var range = this.selection.getRange(),
    	        node = range.getClosedNode();
    	    	if(undefined != node) {
    	    		return 1;
    	    	} else {
	    	        return -1;
    	    	}
    	    }
    	}; */
	    
	});  
	
	/**
	 * 保存文章 
	 */
	function saveArticle(){
		var meta_ = '<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">'
	    +'<meta content="yes" name="apple-mobile-web-app-capable" />'
	        +'<meta content="black" name="apple-mobile-web-app-status-bar-style" />'
	        +'<meta content="telephone=no" name="format-detection" />'
	        +'<meta content="email=no" name="format-detection" />'
	    	+'<meta charset="UTF-8">'
		var style_ = '<style>img{max-width: 100%;}</style>';
		
		var str = '<html>' + meta_ + '<head>' + style_ + '</head><body>' + ue.getContent()  + '</body></html>';
		str = str.replace(/&#39;/g,"'").toString();
        console.log(str);
	}
</script>






























