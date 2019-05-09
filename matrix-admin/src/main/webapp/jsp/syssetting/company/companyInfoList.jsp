<%@ include file="/inc/resource.inc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- <html xmlns="http://www.w3.org/1999/xhtml">
<head> -->
	<%@ include file="/inc/iframe-head.jsp"%>
<%-- </head>

<body class="withvernav">

	<div class="bodywrapper">
		<%@ include file="/inc/top.jsp"%>
		<%@ include file="/inc/left.jsp"%> --%>

		<div class="centercontent tables">
			<!--这个跳转页面的功能及跳转路径等等-->
			<div class="pageheader notab">
				<h1 class="pagetitle">系统公司列表页面</h1>
				<!-- <span class="pagedesc"> 本页面用于系统管理员来创建系统中的用户。 </span>  -->
				<span style="display: none">jsp/syssetting/company/companyInfoList.jsp</span>
			</div>

			<div id="contentwrapper" class="contentwrapper">

				<%-- table-form 这个id分页使用 --%>
				<div id="table-form" class="dataTables_wrapper">
					<div class="contenttitle2">
						<p style="margin: 0px">
							<label>公司名称：</label> 
							<span class="field"> 
								<input id="name" type="text" name="name" class="form-search" />
							</span> 
							<label>客户联系人：</label> 
							<span class="field"> 
								<input id="contactPerson" type="text" name="contactPerson" class="form-search" />
							</span> 
							<a onclick="toAddPage()" class="btn btn_orange btn_home radius50 security-btn" key="company_info_list:add" style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
								<span> 添 加 </span>
							</a> 
							
							<a onclick="searchList()" class="btn btn_orange btn_search radius50 security-btn" key="company_info_list:search" style="display:none;float: right; cursor: pointer; margin-left: 20px"> 
								<span> 查 询 </span>
							</a>
						</p>
					</div>

					<div id="dyntable2_length" class="dataTables_length dialog-show-count">
						<label> 当前显示 <%-- TODO 注意：select-page-size 这个ID是写定的，如果没有这个显示条数，则默认显示10条 - Yangcl --%>
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
								<th class="head1" width="150px">公司名称</th>
								<th class="head1" width="200px">公司介绍</th>
								<th class="head1" width="200px">公司地址</th>
								<th class="head1"  width="100px">客户联系人</th>
								<th class="head1"  width="100px">联系方式</th>
								<th class="head1" width="150px">创建时间</th>
								<th class="head1 " width="200px">操作</th>
							</tr>
						</thead>

						<tbody id="ajax-tbody-list" class="page-list">
							<%-- 等待填充 --%>
						</tbody>
					</table>

				</div>
			</div>

		</div>


<!-- 	</div>

</body>
</html> -->

<script type="text/javascript">
	 $(function() {
		var type_ = 'post';
		var url_ = '${basePath}company/company_info_list.do';
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
			name : $("#name").val(),
			contactPerson : $("#contactPerson").val()
		}
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		aForm.launch(url_, 'table-form', obj).init();
		draw(obj);
	}

	// 画表格
	function draw(obj) {
		$('#ajax-tbody-list tr').remove();
		var html_ = '';
		var list = obj.data.list;
		if (list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				html_ += '<tr>' + '<td width="150px">' + list[i].name + '</td>'
						+ '<td class="center" width="200px">' + list[i].intro + '</td>'
						+ '<td class="center">' + list[i].address + '</td>'
						+ '<td align="center" width="150px">' + list[i].contactPerson + '</td>'
						+ '<td align="center" width="100px">' + list[i].contactPersonPhone + '</td>'
						+ '<td align="center" width="150px">' + list[i].createTime + '</td>'
						+ '<td width="200px" align="center">'
						+ '<a onclick="deleteSystemUser(this)" company_info_id="' + list[i].id + '" title="删除" class="security-btn" key="company_info_list:delete" style="display:none;cursor: pointer;">删除</a>  '
						+ '<a href="${basePath}company/company_info_edit_page.do?id=' + list[i].id + '" title="修改" class="security-btn" key="company_info_list:edit" style="display:none;cursor: pointer;">修改</a> '
						+ '<a href="${basePath}company/company_payment_info_list_page.do?cid=' + list[i].id + '" title="支付配置" class="security-btn" key="company_info_list:company_payment_info_list" style="display:none;cursor: pointer;">支付配置</a> '
						+ '</td></tr>'
			}
		}else{
			html_ = '<tr><td colspan="11" style="text-align: center;">' + obj.msg + '</td></tr>';
		}

		$('#ajax-tbody-list').append(html_);
	}
	
	// 前往添加界面 
	function toAddPage(){
		window.location.href = "${basePath}company/company_info_add_page.do";
	}

	// 删除一个系统用户 
	function deleteSystemUser(obj) {
		var o = obj; // 自定义弹窗后参数obj消失，故这里保存
		mconfirm('您确定要删除这条记录吗？', '系统提示', function(flag) {
			if (flag) {
				var type_ = 'post';
				var url_ = '${basePath}company/del_company_info.do';
				var data_ = {
					id : $(o).attr("company_info_id") 
				}; 
				var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
				if (obj.status == 'success') {
					var currentPageNumber = $(".paginate_active").html(); // 定位到当前分页的页码，然后重新加载数据
					aForm.formPaging(currentPageNumber);
				} else {
				}
				malert(obj.msg, '系统提示');
			}
		});
	}

	//搜索
	function searchList() {
		aForm.formPaging(1);
	}

	// 重置查询条件
	function searchReset() {
		$("#name").val("");
		$("#contactPerson").val("");
		aForm.formPaging(1);
	}
	
	
	// 回调函数
    function loadDialogTable(url_){
        if(url_ == undefined){ // 首次加载表单
            drawDialog(window.parent.dForm.jsonObj);
            return;
        }
        // 这种情况是响应上一页或下一页的触发事件
        var type_ = 'post';
        var data_ = {
			roleName : $("#role-name" , window.parent.document).val(),
			userId : $("#user-id" , window.parent.document).val()
		};
        var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
        window.parent.dForm.launch(url_ , 'dialog-table-form' , obj).init();
        drawDialog(obj);
    }

    function drawDialog(obj){
    	if($('#dialog-ajax-tbody tr').length == 0){ 
			$('#dialog-ajax-tbody tr' , window.parent.document).remove();
		}else{
			$('#dialog-ajax-tbody tr').remove();
		}
    	
        var html_ = '';
        var list = obj.data.list;
        if (list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				html_ += '<tr>' + '<td align="center" width="200px">' + list[i].roleName + '</td>'
						+ '<td>' + list[i].roleDesc + '</td>'
						+ '<td align="center" width="150px">' + list[i].createTime + '</td>'
						+ '<td width="50px" align="center">';
						if(list[i].userId == -1){
							html_ += '<a href="javascript:void(0)" roleId="' + list[i].id + '" onclick="subpage.addMcUserRole(this)" title="为用户分配这个角色" class="security-btn" key="system_user_list:allot_cancel" style="display:none;cursor: pointer;">分配</a> '  
						}else{
							html_ += '<a href="javascript:void(0)" roleId="' + list[i].id + '" onclick="subpage.deleteMcUserRole(this)" title="为用户删除这个角色" class="security-btn" key="system_user_list:allot_cancel" style="display:none;cursor: pointer;">取消</a> '  
						}
						html_ += '</td></tr>'
			}
		}else{
			html_ = '<tr><td colspan="11" style="text-align: center;">' + obj.msg + '</td></tr>';
		}
        
        
        if($('#dialog-ajax-tbody').length == 0){ 
			$('#dialog-ajax-tbody' , window.parent.document).append(html_);
		}else{
			$('#dialog-ajax-tbody').append(html_);
		}
    }
    
    function dialogSearch(){
    	window.parent.dForm.formPaging(1);
    }
    
    function dialogSearchReset(){
    	$("#role-name" , window.parent.document).val(""); 
    	window.parent.dForm.formPaging(1);
    }
    
   
</script>
























