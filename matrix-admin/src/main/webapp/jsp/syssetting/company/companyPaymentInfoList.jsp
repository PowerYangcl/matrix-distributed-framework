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
				<h1 class="pagetitle">${companyInfo.name} 支付配置列表页面</h1>
				<!-- <span class="pagedesc"> 本页面用于系统管理员来创建系统中的用户。 </span>  -->
				<span style="display: none">jsp/syssetting/company/companyPaymentInfoList.jsp</span>
			</div>

			<div id="contentwrapper" class="contentwrapper">

				<%-- table-form 这个id分页使用 --%>
				<div id="table-form" class="dataTables_wrapper">
					<div class="contenttitle2">
						<p>
						<a onclick="toAddPage()" class="btn btn_orange btn_home radius50 security-btn" key="company_info_list:add" style="display:none; cursor: pointer;"> 
								<span> 添 加 </span>
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
								<th class="head1" width="150px">支付方式</th>
								<th class="head1" width="200px">appid</th>
								<th class="head1"  width="100px">商户号</th>
								<th class="head1"  width="100px">商户私钥</th>
								<th class="head1" width="200px">应用公钥</th>
								<th class="head1" width="150px">应用秘钥</th>
								<th class="head1 " width="200px">操作</th>
							</tr>
						</thead>
						<tbody id="ajax-tbody-list" class="page-list">
							　

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
		var url_ = '${basePath}company/company_payment_info_list.do?cid=${companyInfo.id}';
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
				var payType="";
				if(list[i].payType==1){
					payType="支付宝h5支付";
				}else if(list[i].payType==2){
					payType="支付宝扫码支付";
				}else if(list[i].payType==3){
					payType="微信h5支付";
				}else if(list[i].payType==4){
					payType="微信扫码支付";
				}else if(list[i].payType==5){
					payType="微信公众号";
				}else if(list[i].payType==6){
					payType="微信小程序支付";
				}
				html_ += '<tr>' + '<td width="150px">' + payType + '</td>'
						+ '<td class="center" width="200px">' + list[i].appid + '</td>'
						+ '<td class="center">' + list[i].partner + '</td>'
						+ '<td align="center" width="150px">' + list[i].privateKey + '</td>'
						+ '<td align="center" width="100px">' + list[i].publicKey + '</td>'
						+ '<td align="center" width="150px">' + list[i].appSecret + '</td>'
						+ '<td width="200px" align="center">'
						+ '<a onclick="deleteThis(this)" company_payment_info_id="' + list[i].id + '" title="删除" class="security-btn" key="company_payment_info_list:delete" style="display:none;cursor: pointer;">删除</a>  '
						+ '<a href="${basePath}company/company_payment_info_edit_page.do?id=' + list[i].id + '" title="修改" class="security-btn" key="company_payment_info_list:edit" style="display:none;cursor: pointer;">修改</a> '
						+ '</td></tr>'
			}
		}else{
			html_ = '<tr><td colspan="11" style="text-align: center;">' + obj.msg + '</td></tr>';
		}

		$('#ajax-tbody-list').append(html_);
	}
	
	// 前往添加界面 
	function toAddPage(){
		window.location.href = "${basePath}company/company_payment_info_add_page.do?cid=${companyInfo.id}";
	}

	// 删除一个
	function deleteThis(obj) {
		var o = obj; // 自定义弹窗后参数obj消失，故这里保存
		mconfirm('您确定要删除这条记录吗？', '系统提示', function(flag) {
			if (flag) {
				var type_ = 'post';
				var url_ = '${basePath}company/del_company_payment_info.do';
				var data_ = {
					id : $(o).attr("company_payment_info_id") 
				}; 
				var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
				if (obj.status == 'success') {
					window.location.reload();
				} else {
				}
				malert(obj.msg, '系统提示');
			}
		});
	}

	

	
	
   
</script>
























