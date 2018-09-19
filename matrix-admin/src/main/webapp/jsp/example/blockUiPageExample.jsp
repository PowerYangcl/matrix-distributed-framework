<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp"%>
<link rel="stylesheet" href="${css}/ztree/zTreeStyle.css" type="text/css" />
   
<script type="text/javascript" src="${js}/ztree/jquery.ztree.all.js"></script>
<!-- 系统角色权限 -->
<script type="text/javascript" src="${js}/system/sysUserRoleFunction.js"></script>

<style type="text/css">
	.a-btn {
		cursor: pointer;
		color: #FB9337;
	}
	.a-btn:hover {
		color: red;
	}
	.dialog-table {
		width: 100%;
		border-collapse: separate;
		border-spacing: 10px;
	}
</style>

<div class="centercontent tables">
	<!--这个跳转页面的功能及跳转路径等等-->
	<div class="pageheader notab">
		<h1 class="pagetitle">常用弹层示例</h1>
		<span class="pagedesc"> 本页面用于介绍系统常见的弹出层。 </span>
		<span style="display: none">jsp/example/dialogExample.jsp</span>
	</div>

	<div id="contentwrapper" class="contentwrapper">

		<%-- table-form 这个id分页使用 --%>
		<div id="table-form" class="dataTables_wrapper">
			<div class="contenttitle2">
				<p style="margin: 0px" class="security-btn" key="dialog_example:add" style="display: none;">
					<button  onclick="openAddDialog()" class="stdbtn btn_orange">添加弹层示例</button>
				</p>
				
				<p style="margin-top: 20px" class="security-btn" key="dialog_example:slim_scroll" style="display: none;">
					<button onclick="openULDialog()" class="stdbtn btn_lime">自定义滚动条示例-ul-列表</button>
				</p>
				
				<p style="margin-top: 20px" class="security-btn" key="dialog_example:slim_scroll:tree" style="display: none;">
					<button  onclick="openTreeDialog()" class="stdbtn btn_orange">自定义滚动条示例-tree</button>
				</p>
			</div>
		</div>
	</div>

</div>



<script type="text/javascript">
	function openAddDialog(){
		var dialogId = 'add-dialog-div';   // 弹窗ID
		window.parent.$.blockUI({
            showOverlay:true ,
            css:  {
                cursor:'auto',
                left:($(window.parent).width() - $("#" + dialogId).width())/2 + 'px',
                width:$("#" + dialogId).width()+'px',
                top:($(window.parent).height()-$("#" + dialogId).height())/2 + 'px',
                position:'fixed', //居中
                border: '3px solid #FB9337'  // 边界
            },
            message: $('#' + dialogId),
            fadeIn: 500,//淡入时间
            fadeOut: 1000  //淡出时间
        });
	}
</script>
<div id="add-dialog-div" class="dialog-page-div" style="display: none;width: 400px;height: 300px">
    <p class="dialog-title">	<!-- dialog-title是必填的一个类，修饰弹窗的头部 -->
        <a href="#" onclick="closeDialog()" class="dialog-close"></a>
        添加用户
    </p>

    <div id="dialog-content-wrapper" class="contentwrapper">
        <div id="dialog-table-form" class="dataTables_wrapper" >
            <form id="dialog-table" >
	            <table class="dialog-table">
	                <tbody>
	                	<tr >
	                		<td style="text-align: right">
	                			用户姓名：
	                		</td>
	                		<td style="text-align: left">
	                			<input type="text" name="" class="dialog-form-input" style="width:200px;"/>
	                		</td>
	                	</tr>
	                	
	                	<tr class="add-dialog">
	                		<td style="text-align: right">
	                			手 机 号：
	                		</td>
	                		<td style="text-align: left">
	                			<input type="text" name="" class="dialog-form-input" style="width:200px;"/>
	                		</td>
	                	</tr>
	                	
	                	<tr>
	                		<td style="text-align: right">
	                			电子邮件：
	                		</td>
	                		<td style="text-align: left">
	                			<input type="text" name="" class="dialog-form-input" style="width:200px;"/>
	                		</td>
	                	</tr>
	                </tbody>
	                <tfoot>
		                <tr>
					      <td colspan="2" style="text-align: right">
					      	<button class="stdbtn btn_orange" style="opacity:1">提 &nbsp&nbsp&nbsp&nbsp&nbsp 交</button>
					      </td> 
					    </tr>
	                </tfoot>
	            </table>
            </form>
        </div>
    </div>
</div>



<script type="text/javascript">
	function openULDialog(){
		var dialogId = "#ul-dialog-div";
		
		// 自定义滚动条 | 执行此代码自定义滚动条则生效
		$('#interface-list').slimscroll({
			color: '#666',
			alwaysVisible: true, //是否 始终显示组件
			railVisible: false, //是否 显示轨道 
			size: '10px',
			width: 'auto',
			height: '400px' // '208px'
		});
		
		window.parent.$.blockUI({
			showOverlay:true ,
			css:{
				cursor:'auto',
				left:($(window.parent).width() - $(dialogId).width())/2 + 'px',
				width:$(dialogId).width()+'px',
				height:580,
				top:($(window.parent).height()-$(dialogId).height())/2 + 'px',
				position:'fixed', //居中
				textAlign:'left',
				border: '3px solid #FB9337'   // 边界,
			},
			message: $(dialogId),
			fadeIn: 500,//淡入时间
			fadeOut: 1000  //淡出时间
		});
		
		
	}
</script>

<!-- 自定义列表弹出框 -> 带自定义滚动条 -->
<div id="ul-dialog-div" class="dialog-page-div" style="display: none;width: 900px;height: 600px">
	<p class="dialog-title">
		<a href="javascript:void(0)" onclick="closeDialog()" class="dialog-close"></a>
		<span>
			等待填充 弹层标题
		</span>
	</p>
	<div id="dialog-content-wrapper" class="contentwrapper">
		<div class="last">
			<div class="widgetbox" style="height: inherit">
				<div  class="title">
					<h3 id="platform-title">
						等待填充一个描述
					</h3>
				</div>
				<div class="widgetcontent">
					<div id="interface-list" class="mousescroll">
						<!-- 等待填充要展示的内容，如果超出div的高度则会展示出自定义的滚动条 -->
						<ul id="api-list" class="entrylist">	<!-- entrylist 是一个系统css类 -->
							<li>
								<div class="entry_wrap">
									<div class="">
										<h4>
											<span>Product.pushProducts</span>
										</h4>
										<span><span>接口名称：根据日期推送商品到第三方</span> | <a>接口状态：已开通</a></span><br>
										<span>根据日期推送商品到第三方……</span><br>
										<span style="margin-top: 10px">
											<!-- <button class="stdbtn btn_orange">授权使用</button>
											<button class="stdbtn btn_lime">取消使用</button> -->
											<input type="radio"  name="" value=""/>授权使用 |
											<input type="radio"  name="" value=""/>取消使用
										</span>
									</div>
								</div>
							</li>

							<li>
								<div class="entry_wrap">
									<div class="">
										<h4>
											<span>Product.Insert</span>
										</h4>
										<span><span>接口名称：添加商品信息</span> | <a>接口状态：已开通</a></span><br>
										<span>添加一条商品信息到数据库中……</span><br>
										<span style="margin-top: 10px">
											<button class="stdbtn btn_lime">授权使用</button>
											<button class="stdbtn ">取消使用</button>
										</span>
									</div>
								</div>
							</li>

							<li>
								<div class="entry_wrap">
									<div class="">
										<h4>
											<span>Product.pushProducts</span>
										</h4>
										<span><span>接口名称：根据日期推送商品到第三方</span> | <a>接口状态：已开通</a></span><br>
										<span>根据日期推送商品到第三方……</span><br>
										<span style="margin-top: 10px">
											<!-- <button class="stdbtn btn_orange">授权使用</button>
											<button class="stdbtn btn_lime">取消使用</button> -->
											<input type="radio"  name="" value=""/>授权使用 |
											<input type="radio"  name="" value=""/>取消使用
										</span>
									</div>
								</div>
							</li>

							<li>
								<div class="entry_wrap">
									<div class="">
										<h4>
											<span>Product.Insert</span>
										</h4>
										<span><span>接口名称：添加商品信息</span> | <a>接口状态：已开通</a></span><br>
										<span>添加一条商品信息到数据库中……</span><br>
										<span style="margin-top: 10px">
											<button class="stdbtn btn_lime">授权使用</button>
											<button class="stdbtn ">取消使用</button>
										</span>
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<form class="stdform">
			<p>
				<span id="dialog-operate" style="position: relative;">
					<!-- 等待填充 弹窗操作按钮 如添加和修改等等 -->
					<button id="submit-btn"   type="button" style="width: 100px;margin-left: 600px;" class="submit radius2">提交</button>
				</span>
			</p>
		</form>
	</div>
</div>





<script type="text/javascript">
	function openTreeDialog(){
		var dialogId = "#tree-dialog-div";  
		$("#user-role-tree li").remove();
    	var type_ = 'post';
        var url_ = '../sysrole/tree_list.do?type=role&id=30';
        var data_ = null;  // 可以为null，后台会进行默认处理
        var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
        if(obj.status == 'success'){
            var zNodes = obj.list;  
            $.fn.zTree.init($("#user-role-tree") , setting_distribution , zNodes);  
            $("#callbackTrigger").bind("change", {}, setting_distribution.setTrigger); 
            $("#user-role-tree_1_check").remove(); // 隐藏root节点的复选框
            if(obj.roles.length == 1){
            	// hidden input value= ids |<input id="func-ids"  type="hidden" value="" >
            	$("#func-ids").val(obj.roles[0].ids);
            	surfunc.showFuncInTree($("#func-ids")[0]);  // 复用showFuncInTree方法    
            }
        }
		
		$('#func-list').slimscroll({
			color: '#666',
			size: '10px',
			width: 'auto',
			height: '630px' // '208px'
		});
		
		window.parent.$.blockUI({
			showOverlay:true ,
			css:{
				cursor:'auto',
				left:($(window.parent).width() - $(dialogId).width())/2 + 'px',
				width:$(dialogId).width()+'px',
				// height:580,
				top:($(window.parent).height()-$(dialogId).height())/2 + 'px',
				position:'fixed', //居中
				textAlign:'left',
				border: '3px solid #FB9337'   // 边界,
			},
			message: $(dialogId),
			fadeIn: 500,//淡入时间
			fadeOut: 1000  //淡出时间
		});
	}
</script>
<!-- 树形组件弹出框 -> 带自定义滚动条 -->
<div id="tree-dialog-div" class="dialog-page-div" style="display: none;width: 600px;height: 800px">
	<p id="dialog-title" class="dialog-title">
		<a href="javascript:void(0)" onclick="closeDialog()" class="dialog-close"></a>
		<span>
			<%-- 等待填充 弹层标题 --%>
			等待填充 弹层标题
		</span>
	</p>
	<div id="dialog-content-wrapper" class="contentwrapper">
		<div class="last">
			<div class="widgetbox" style="height: inherit">
				<div  class="title">
					<h3 id="platform-title">
						<%-- 等待填充--%>
						<a herf="javascript:void(0)" onclick="subpage.surfunc.closeNavi('user-role-tree')" class="a-btn" title="收起导航栏从而方便您的操作">收起导航</a>|
                        <a herf="javascript:void(0)" onclick="subpage.surfunc.closeMenu('user-role-tree')" class="a-btn" title="收起一级菜单栏从而方便您的操作">收起菜单</a>
					</h3>
				</div>
				<div class="widgetcontent">
					<div id="func-list" class="mousescroll">
						<!-- 等待填充要展示的内容，如果超出div的高度则会展示出自定义的滚动条 -->
						<ul id="user-role-tree" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
		<form class="stdform">
			<input id="func-ids"  type="hidden" value="" >
			<p>
				<span id="dialog-operate" style="position: relative;">
					<%-- 等待填充 弹窗操作按钮 如添加和修改等等--%>
					<button id="tree-submit-btn"   type="button" style="width: 100px;margin-left: 300px;" class="submit radius2">提交</button>
				</span>
			</p>
		</form>
	</div>
</div>











