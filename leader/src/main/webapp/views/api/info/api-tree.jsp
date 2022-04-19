<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>API信息树</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<link rel="stylesheet" href="${layui}/layui/css/layui.css" media="all">
		<link rel="stylesheet" href="${layui}/style/admin.css" media="all">
		<link rel="stylesheet" href="${css}/ztree/zTreeStyle.css" type="text/css" />
		<style type="text/css">
			.flow-default{width: 500px; height: auto; overflow: auto; font-size: 16; }
			
			form input[type=text],select,textarea{
			    border: 1px solid #ccc;
			    padding: 8px 5px;
			    min-width: 40%;
			    border-radius: 2px;
			 	box-shadow: inset 1px 1px 2px #cddc398f;
			    color: #666;
			    font-size: 12px;
			    font-family: "Century Schoolbook", Arial, Helvetica, sans-serif;
			    letter-spacing: normal;
			    word-spacing: normal;
			    text-transform: none;
			    text-indent: 0px;
			    text-shadow: none;
			    text-align: start;
			    outline: none;
			    display: inline-block;
			    margin: 0;
			}
			
			form input[type=text] {
			    width: 398px
			}
			
			form select {
			    width: 410px; 
			}	
			
			.dialog-domain-list-head{
				margin-top:10px;
				margin-left:20px;
				margin-right:20px;
				border-bottom:thick double #009f95;
			}
			
			.dialog-domain-list{
				margin-top:10px;
				margin-left:20px;
				margin-right:20px;
				border-bottom:medium double #009f95;
			}
			
			.dialog-test-form{
			    border: 1px solid #ddd;
			    padding: 10px;
			    overflow: hidden;
			   	margin-top:10px;
			   	margin-bottom:10px;
			   	margin-left:20px;
			   	margin-right:20px;
			    border-radius: 2px;
			    background: #fcfcfc
		    }
		</style>
		
	</head>
	<body>
		<div class="layui-fluid">
			<div class="layui-row layui-col-space15">
				<div class="layui-col-md12" >
					<div class="layui-card" style="height: 850px;">
						<div class="layui-card-header">
							<a><cite>矩阵系统配置 / </cite></a> 
							<a><cite>系统开放接口 / </cite></a>
							<a><cite>API树形结构列表</cite></a>
						</div>
						<div class="layui-card-header">
							<h3>
								<a herf="javascript:void(0)" onclick="apiInfo.closeNavi('api-tree')" style="cursor: pointer; color:#FB9337; " title="收起导航栏从而方便您的操作">收  起</a>
							</h3>
						</div>
						<div id="func-list" class="layui-card-body mousescroll" style="width: 1200px;height: auto;">
							<table class="" style="width:100%">
								<tr>
									<td style="border-right:1px solid #ece7e7;width:40%;">
										<ul id="api-tree" class="ztree flow-default" >
											<!-- ztree -->
										</ul>
									</td>
									<td style="vertical-align:top">
										<div class="tree-right" style="padding: 5px; margin-left:40px; margin-top:20px;">
											<form id="tree-node-edit" action="javascript:void(0)"></form>
										</div>
									</td>
								</tr>
							</table>
						</div>
						
						
					</div>
				</div>
				
			</div>
		</div>
	</body>
</html>

<script type="text/javascript" src="${js}/jquery-1.10.2.js"></script>

<!-- 调用md5方法|已经备份blueimp-md5-2.10.0-md5.js -->
<!-- <script src="https://cdn.bootcss.com/blueimp-md5/2.10.0/js/md5.js"></script> -->
<script type="text/javascript" src="${js}/utils/blueimp-md5-2.10.0-md5.js"></script>

<script type="text/javascript" src="${layui}/layui/lay/modules/layer.js"></script>

<script type="text/javascript" src="${js}/utils/ajaxs.js"></script>
<script type="text/javascript" src="${js}/plugins/jquery.slimscroll-1.3.8.js"></script>
<script type="text/javascript" src="${js}/ztree/jquery.ztree.all.js"></script>

<!-- 传递复杂对象参数 -->
<script src="https://cdn.bootcdn.net/ajax/libs/axios/0.21.1/axios.min.js"></script>

<script type="text/javascript" src="${views}/api/info/api-tree.js"></script>


<script>
	$(function(){
		
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
	            "d+" : this.getDate(),    // day
	            "h+" : this.getHours(),   // hour
	            "m+" : this.getMinutes(), // minute
	            "s+" : this.getSeconds(), // second
	            "q+" : Math.floor((this.getMonth()+3)/3),	// quarter
	            "S" : this.getMilliseconds() //millisecond
	        }
	        if(/(y+)/.test(format))
	            format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
	        for(var k in o)if(new RegExp("("+ k +")").test(format))
	            format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
	        return format;
	    }
		
		
		// 开始初始化树型结构
		apiInfo.launch('${basePath}').apiTreeInit();  
		
		// 自定义滚动条 | 执行此代码自定义滚动条则生效
		$('#api-tree').slimscroll({
			color: '#666',
			size: '10px',
			width: 'auto',
			height: '700px'  
		});
	});

	
</script>




































