<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>定时任务列表页面</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<link rel="stylesheet" href="${layui}/layui/css/layui.css" media="all">
		<link rel="stylesheet" href="${layui}/style/admin.css" media="all">
		<script src="${layui}/layui/layui.js"></script>
		
		<style type="text/css">
			form input[type=text],select,textarea{
			    border: 1px solid #ccc;
			    padding: 8px 5px;
			    min-width: 40%;
			    border-radius: 2px;
			 	box-shadow: inset 1px 1px 2px #cddc398f;
			    color: #666;
			    font-size: 12px;
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
			    width: 255px; 
			}
			
			form select {
			    width: 280px; 
			}
			
			/* 重新定义表格高度 */
			.layui-table-cell {
	            height: auto !important;
	            white-space: normal;
	        }
		</style>
	</head>
	<body>
		<div class="layui-fluid">
			<div class="layui-row layui-col-space15">
				<div class="layui-col-md12">
					<div class="layui-card">
						<div class="layui-card-header">
							<a><cite>矩阵系统配置 / </cite></a> 
							<a><cite>分布式定时任务 / </cite></a>
							<a><cite>定时任务列表</cite></a>
						</div>
						<div class="layui-card-body">
						
							<table id="table-toolbar" class="layui-hide" lay-filter="table-toolbar"></table>
							
							<script id="table-search-toolbar" type="text/html">
              					<div class="layui-btn-container">
									<div class="layui-table-search-div" >定时任务名称：</div>
									<input id="job-name" name="job-name" class="layui-table-search" autocomplete="off">
									<div class="layui-table-search-div" >定时任务标题：</div>
									<input id="job-title" name="job-title" class="layui-table-search" autocomplete="off">

                					<button class="security-btn layui-btn layui-btn-sm" key="job_info_list:search" lay-event="search">查&nbsp&nbsp&nbsp&nbsp&nbsp询</button>
                					<button class="security-btn layui-btn layui-btn-sm" key="job_info_list:reset" lay-event="reset">重&nbsp&nbsp&nbsp&nbsp置</button>
                					<button class="security-btn layui-btn layui-btn-sm" key="job_info_list:add" lay-event="add">添&nbsp&nbsp&nbsp&nbsp加</button>
									<button class="security-btn layui-btn layui-btn-sm" key="job_info_list:pause_all" lay-event="pause-all-job">全部暂停</button>
									<button class="security-btn layui-btn layui-btn-sm" key="job_info_list:resume_all" lay-event="resume-all-job">全部恢复</button>
              					</div>
            				</script>
							<script id="table-btn-toolbar" type="text/html">
              					<a class="security-btn layui-btn layui-btn-xs" key="job_info_list:detail" lay-event="edit">详情</a>
              					<a class="security-btn layui-btn layui-btn-xs" key="job_info_list:edit" lay-event="edit">修改</a>
              					<a class="security-btn layui-btn layui-btn-xs" key="job_info_list:run" lay-event="edit">手动</a></br>
              					<a class="security-btn layui-btn layui-btn-xs" key="job_info_list:delete" lay-event="edit">删除</a>
              					<a class="security-btn layui-btn layui-btn-xs" key="job_info_list:resume" lay-event="edit">恢复</a>
              					<a class="security-btn layui-btn layui-btn-xs" key="job_info_list:run_log" lay-event="edit">日志</a>
            				</script>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>


<script type="text/javascript" src="${views}/quartz/info/job-info-list.js"></script>

































