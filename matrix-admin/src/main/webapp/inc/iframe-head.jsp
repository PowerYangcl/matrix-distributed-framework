	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />


    <link rel="stylesheet" href="${css}/style.default.css" type="text/css" />

	<style type="text/css">
		.page-list tr:hover{
		  background:#F2DEDE;
		}
	</style>
	
	
    <script type="text/javascript" src="${js}/jquery-1.10.2.js"></script> 
	<script type="text/javascript" src="${js}/plugins/jquery-ui.js"></script>
	<script type="text/javascript" src="${js}/plugins/colorpicker.js" ></script>
	
	<!-- 自定义滚动条 -->
	<script type="text/javascript" src="${js}/plugins/jquery.slimscroll-1.3.8.js"></script>
	
	<script type="text/javascript" src="${js}/datePicker/WdatePicker.js" ></script>
	
    <script type="text/javascript" src="${js}/utils/ajaxs.js"></script> 
    <script type="text/javascript" src="${js}/system/general.js"></script> 
    
    <script type="text/javascript" src="${js}/system/ajax-form.js"></script>
    <script type="text/javascript" src="${js}/system/pageInit.js"></script> 
    
    <script type="text/javascript">
		$(function(){
			$('body').addClass("withmenucoll2");  
			/**
			 * 自定义对话框封装
			 */
			malert = function(message, title, callback) {
				window.parent.malert(message, title, callback);
			}
			mconfirm = function(message, title, callback) {
				window.parent.mconfirm(message, title, callback);
			};
			mprompt = function(message, value, title, callback) {
				window.parent.mprompt(message, value, title, callback);
			};
		});  

		window.onload = function(){
			pageInit.security();
		}

    </script>
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    