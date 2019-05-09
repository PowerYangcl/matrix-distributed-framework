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
    
    <script type="text/javascript" src="${js}/jqueryValidate/jquery.validate.js"></script> 
   	<script type="text/javascript" src="${js}/jqueryValidate/messages_zh.js"></script> 
    
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
			
			
			 /**
		     * @描述: 格式化数据库里由action或Controller向页面传来的时间
		     * @作者: Yangcl
		     * @时间: 2015-08-22 : 22-29-56
		     * @原理:
		     *             数据库里的时间为：2017-08-17 16:40:24；当传向页面的时候变为了Aug 17, 2017 4:40:24 PM
		     *             而WdatePicker.js时间控件中要显示为2017-08-17 16:40:24格式，所以对Date进行扩展。
		     *             调用方式：new Date("Aug 17, 2017 4:40:24 PM").format("yyyy-MM-dd hh:mm:ss");
		     *									new Date().format("yyyy-MM-dd hh:mm:ss");
		     
										     	var date = new Date(); 
												date.setTime(1545721982); 
												date.format("yyyy-MM-dd hh:mm:ss");
		     * @注意: 这个方法需要提前声明才能使用
		     */
		    Date.prototype.format = function(format){
		        var o = {
		            "M+" : this.getMonth()+1, //month
		            "d+" : this.getDate(),    //day
		            "h+" : this.getHours(),   //hour
		            "m+" : this.getMinutes(), //minute
		            "s+" : this.getSeconds(), //second
		            "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
		            "S" : this.getMilliseconds() //millisecond
		        }
		        if(/(y+)/.test(format))
		            format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
		        for(var k in o)if(new RegExp("("+ k +")").test(format))
		            format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
		        return format;
		    }			
			
		});  

		window.onload = function(){
			pageInit.security();
		}

    </script>
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    