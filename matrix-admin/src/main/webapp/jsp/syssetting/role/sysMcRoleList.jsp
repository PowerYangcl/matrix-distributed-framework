<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- <html xmlns="http://www.w3.org/1999/xhtml">
<head> -->
	<%@ include file="/inc/iframe-head.jsp"%>
    <link rel="stylesheet" href="${css}/ztree/zTreeStyle.css" type="text/css" />
    
	<script type="text/javascript" src="${js}/ztree/jquery.ztree.all.js"></script>
	<!-- 系统角色权限 -->
	<script type="text/javascript" src="${js}/system/sys-user-role-function.js"></script>
	<style type="text/css">
		.a-btn{
        	cursor: pointer;
        	color:#FB9337; 
        }
        .a-btn:hover {
        	color: red;
       	} 
	</style>
<%-- </head>

<body class="withvernav">

	<div class="bodywrapper">
		<%@ include file="/inc/top.jsp"%>
		<%@ include file="/inc/left.jsp"%> --%>

		<div class="centercontent tables">
			<!--这个跳转页面的功能及跳转路径等等-->
			<div class="pageheader notab">
				<h1 class="pagetitle">系统角色列表页面</h1>
				<!-- <span class="pagedesc"> 本页面用于系统管理员来创建系统中的角色。 </span>  -->
				<span style="display: none">jsp/syssetting/role/sysMcRoleList.jsp</span>
			</div>

			<div id="contentwrapper" class="contentwrapper">

				<%-- table-form 这个id分页使用 --%>
				<div id="table-form" class="dataTables_wrapper">
					<div class="contenttitle2">
						<p style="margin: 0px">
							<label>权限名称：</label> 
							<span class="field"> 
								<input id="role-name" type="text" name="roleName" class="form-search" />
							</span>  
							<a onclick="toUserAddPage()" class="btn btn_orange btn_home radius50 security-btn" key="system_role_list:add" style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
								<span> 添 加 </span>
							</a> 
							<a onclick="searchReset()" class="btn btn_orange btn_search radius50 security-btn" key="system_role_list:reset" style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
								<span> 重 置 </span>
							</a> 
							<a onclick="searchUser()" class="btn btn_orange btn_search radius50 security-btn" key="system_role_list:search" style="display:none;float: right; cursor: pointer; margin-left: 20px"> 
								<span> 查 询 </span>
							</a>
						</p>
					</div>

					<div id="dyntable2_length" class="dataTables_length dialog-show-count">
						<label> 当前显示 
							<select id="select-page-size" size="1" name="dyntable2_length" onchange="aForm.formPaging('1')">
								<option value="10">10</option>
								<option value="25">25</option>
								<option value="50">50</option>
								<option value="100">100</option>
							</select> 条记录
						</label>
					</div>

					<table id="dyntable2" cellpadding="0" cellspacing="0" border="0" class="stdtable">
						<thead>
							<tr>
								<th class="head1">角色名称</th> 
								<th class="head1">角色描述</th> 
								<th class="head1">平台名称</th>
								<th class="head1">创建时间</th>
								<th class="head1 " width="100px">操作</th>
							</tr>
						</thead>

						<tbody id="ajax-tbody-user-list" class="page-list">
							<%-- 等待填充 --%>
						</tbody>
					</table>

				</div>
			</div>

		</div>


<!-- 	</div>

</body>
</html> -->

<!-- 树形组件弹出框 -> 带自定义滚动条 -->
<div id="tree-dialog-div" class="dialog-page-div" style="display: none;width: 600px;height: 800px">
	<p id="dialog-title" class="dialog-title">
		<a href="javascript:void(0)" onclick="closeDialog()" class="dialog-close"></a>
		<span>
			<%-- 等待填充 弹层标题 --%>
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
					<button id="submit-btn" roleId=""  onclick="subpage.submitRoleFunc(this)" type="button" 
							key="system_role_list:dialog_submit" style="display:none;width: 100px;margin-left: 400px;" class="submit radius2 security-btn">提交</button>
				</span>
			</p>
		</form>
	</div>
</div>




<script type="text/javascript">
	$(function() {
		var type_ = 'post';
		var url_ = '${basePath}sysrole/sys_role_list.do';
		var data_ = null;
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		aForm.launch(url_, 'table-form', obj).init().drawForm(loadTable);
	});

	// 回调函数
	function loadTable(url_) {
		if (url_ == undefined) { // 首次加载表单
			draw(aForm.jsonObj);
			return;
		}
		// 这种情况是响应上一页或下一页的触发事件
		var type_ = 'post';
		var data_ = {
			roleName : $("#role-name").val() 
		}
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		aForm.launch(url_, 'table-form', obj).init();
		draw(obj);
	}

	// 画表格
	function draw(obj) {
		$('#ajax-tbody-user-list tr').remove();
		var html_ = '';
		var list = obj.data.list;
		if (list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				html_ += '<tr>' + '<td align="center" width="200px">' + list[i].roleName + '</td>'
						+ '<td>' + list[i].roleDesc + '</td>'
						+ '<td align="center">' + list[i].platformName + '</td>'
						+ '<td align="center" width="200px">' + list[i].createTime + '</td>'
						+ '<td width="200px" align="center">'
						+ '<a href="javascript:void(0)" platform="' + list[i].platform + '" roleId="' + list[i].id + '" onclick="openTreeDialog(this , true)" title="分配系统功能到这个角色中" class=" security-btn" key="system_role_list:role_function" style="display:none;cursor: pointer;">角色功能</a> ' 
						// + '<a href="javascript:void(0)" platform="' + list[i].platform + '" roleId="' + list[i].id + '" onclick="openTreeDialog(this , true)" title="分配系统功能到这个角色中" style="cursor: pointer;">角色功能</a> ' 
						
						+ '<a href="${basePath}sysrole/show_role_edit_page.do?id=' + list[i].id + '" title="修改"  class=" security-btn" key="system_role_list:edit" style="display:none;cursor: pointer;">修改</a>  '
						+ '<a roleId="' + list[i].id + '" onclick="surfunc.deleteMcRole(this)" title="删除"  class=" security-btn" key="system_role_list:delete" style="display:none;cursor: pointer;">删除</a>'
						+ '</td></tr>'
			}
		}else{
			html_ = '<tr><td colspan="11" style="text-align: center;">' + obj.msg + '</td></tr>';
		}

		$('#ajax-tbody-user-list').append(html_);
	}
	
	// 前往添加界面 
	function toUserAddPage(){
		window.location.href = "${basePath}sysrole/show_role_add_page.do";
	}
	
	//搜索
	function searchUser() {
		aForm.formPaging(1);
	}

	// 重置查询条件
	function searchReset() {
		$("#role-name").val(""); 
		aForm.formPaging(1);
	}
	
	/**
	 * 接口管理 弹窗层
	 * @param obj 
	 * @param flag true:编辑，false:查看
	 */
	function openTreeDialog(obj , flag){
		var roleId = $(obj).attr("roleId");  
		var platform = $(obj).attr("platform");  // 平台标识码
		var roleName = $(obj).parent().parent().children()[0].innerText;
		var dialogId = "#tree-dialog-div";
		if(flag){
			$("#dialog-title").children("span").html("为角色【" + roleName + "】赋予系统功能");
			// var html = '<button id="submit-btn" roleId="' + roleId + '"  onclick="submitRoleFunc(this)" type="button" style="width: 100px;margin-left: 400px;" class="submit radius2">提交2</button>';
			// $("#dialog-operate").append(html);
			$("#submit-btn").attr("roleId" , roleId);  
		}else{
			$("#dialog-title").children("span").html("查看角色【" + roleName + "】所有功能");  // 此功能暂时不用
		}
		surfunc.distributeUserRole(roleId , platform);  // 填充弹窗中的数据 
		// 自定义滚动条 | 执行此代码自定义滚动条则生效
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
	
	/**
	 * 提交与角色关联好的功能 
	 * @param obj 
	 * @param flag true:编辑，false:查看
	 */
	function submitRoleFunc(obj){ 
		var roleId = $(obj).attr("roleId");  
    	var tree = $.fn.zTree.getZTreeObj("user-role-tree");
    	var checkArray = tree.getChangeCheckedNodes(); // 获取所有被选节点 
    	var ids = ''; 
    	for(var i = 0 ; i < checkArray.length ; i ++){
    		var t = checkArray[i];
    		if(t.navType != -1){
    			ids += t.id + ",";
    		}
    	}
    	ids = ids.substring(0 , ids.length -1); 
    	var type_ = 'post';
        var url_ = 'edit_mc_role.do';
    	var data_ = {
    			mcRoleId:roleId,
    			ids:ids
    	};  
    	
    	var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
        if(obj.status == 'success'){
        	var cache = obj.cache; // 扩展 暂时不用
        	malert("角色修改成功!" , '系统提示' , function(){
	        	surfunc.closeDialog();
        	});
        }else{
        	malert(obj.msg , '系统提示');
        }
    }
</script>













