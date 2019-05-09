<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp" %>
<script type="text/javascript">
	/**
	 * Ajax 页面分页示例
	 *
	 * var data_ = null; 这里暂设置为null，这两处为空的地方可以根据实际情况处理。注意loadTable()也有。
	 */
	$(function() {
		// aForm.formPaging('$ { num }'); 如果从修改页面返回到列表页面，则可以进入这段代码 直接定位。
		var type_ = 'post';
		var url_ = '${basePath}example/ajax_page_data.do';
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
			userName : $("#user-name").val(),
			mobile : $("#mobile").val(),
			sex : $("#sex").val()
		};
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		aForm.launch(url_, 'table-form', obj).init();
		draw(obj);
	}

	// 画表格
	function draw(obj) {
		$('#ajax-tbody-1 tr').remove();
		var html_ = '';
		var list = obj.data.list;
		if (list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				html_ += '<tr id="tr-' + list[i].id + '" class="gradeX">'
						+ '<td align="center"><span class="center"> <input type="checkbox"/> </span></td>'
						+ '<td width="100px">' + list[i].id + '</td>'
						+ '<td>' + list[i].userName + '</td>'
						+ '<td>' + list[i].mobile + '</td>'
						+ '<td class="center">' + list[i].idNumber + '</td>'
						+ '<td class="center">' + list[i].email + '</td>'
						+ '<td width="150px" align="center">'
						+ '<a onclick="deleteOne(this)" eleId=' + list[i].id + '" title="删除"  style="display:none;cursor: pointer;" class="security-btn" key="ajax_page_dialog_example:delete">删除</a>   '
						+ '<a href="${basePath}example/edit_info_page.do?id=' + list[i].id + '" title="修改"  style="display:none;cursor: pointer;" class="security-btn" key="ajax_page_dialog_example:edit">修改</a>   '
						+ '<a onclick="openDialogPage(this)" eleId=' + list[i].id + '" style="display:none;cursor: pointer;" class="security-btn" key="ajax_page_dialog_example:dialog">弹窗分页</a>'
						+ '</td></tr>'
			}
		} else {
			html_ = '<tr><td colspan="11" style="text-align: center;">' + obj.msg + '</td></tr>';
		}
		$('#ajax-tbody-1').append(html_);
	}

	function deleteOne(ele) {
		mconfirm('您确定要删除这条记录吗？', '系统提示', function(flag) {
			if (flag) {
				var type_ = 'post';
				var url_ = '${basePath}example/ajax_delete_one.do';
				var data_ = {
					id : $(ele).attr("eleId")
				};
				var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
				if (obj.status == 'success') {
					var currentPageNumber = $(".paginate_active").html(); // 定位到当前分页的页码，然后重新加载数据
					aForm.formPaging(currentPageNumber);
				} else {
				}
				malert(obj.msg, 'title');
			}
		});
	}

	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// ajax弹框分页示例开始 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @描述: 打开dialog insert BlockUI弹框
	 * @作者: Yangcl
	 * @时间: 2016-08-19 : 15-20-56
	 */
	function openDialogPage(ele) {
		var type_ = 'post';
		var url_ = '${basePath}example/ajax_page_data.do';
		var data_ = null;
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		
		// 因为此时还在iframe中，但是dForm对象在父窗体中，所以这里使用window.parent来调用父窗体的对象
		window.parent.dForm.launch(url_, 'dialog-table-form', obj).init().drawForm(loadDialogTable);

		var eleId = $(ele).attr("eleId");  // 取出所需信息 
		
		var dialogId = 'dialog-page-div'; // 弹窗ID
		window.parent.$.blockUI({
			showOverlay : true,
			css : {
				cursor : 'auto',
				left : ($(window.parent).width() - $("#" + dialogId).width()) / 2 + 'px',
				width : $("#" + dialogId).width() + 'px',
				top : ($(window.parent).height() - $("#" + dialogId).height()) / 2 + 'px',
				position : 'fixed', //居中
				border : '3px solid #FB9337' // 边界
			},
			message : $('#' + dialogId),
			fadeIn : 500, //淡入时间
			fadeOut : 1000 //淡出时间
		});
	}

	// 回调函数
	function loadDialogTable(url_) {
		if (url_ == undefined) { // 首次加载表单
			drawDialog(window.parent.dForm.jsonObj);
			return;
		}
		// 这种情况是响应上一页或下一页的触发事件
		var type_ = 'post';
		var data_ = null;
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		window.parent.dForm.launch(url_, 'dialog-table-form', obj).init();		// 绘制当前第**页，首页/上一页/下一页 
		drawDialog(obj);
	}

	// 开始具体绘制表格中的数据
	function drawDialog(obj) {
		// 弹窗分页-表格绘制-固定代码段 -> First start
		if($('#dialog-ajax-tbody tr').length == 0){ 
			$('#dialog-ajax-tbody tr' , window.parent.document).remove();
		}else{
			$('#dialog-ajax-tbody tr').remove();
		}
		// 弹窗分页-表格绘制-固定代码段 -> First end
		
		var html_ = '';
		var list = obj.data.list;
		if (list.length > 0) {
			for (var i = 0; i < list.length; i++) {
				html_ += '<tr id="tr-d-' + list[i].id + '" class="gradeX">' // TODO 注意：这里的 tr 的 id 是以 tr-d-****  作为开头的 - Yangcl
						+ '<td align="center"><span class="center"> <input type="checkbox"/> </span></td>'
						+ '<td width="100px">' + list[i].id + '</td>'
						+ '<td>' + list[i].userName + '</td>'
						+ '<td>' + list[i].mobile + '</td>'
						+ '<td class="center">' + list[i].idNumber + '</td>'
						+ '<td class="center">' + list[i].email + '</td>'
						+ '<td width="150px" align="center">'
						+ '<a onclick="deleteOne(this)" eleId=' + list[i].id + '" title="删除" class="security-btn" key="ajax_page_dialog_example:dialog:delete" style="display:none;cursor: pointer;">删除</a>    '
						+ '<a href="${basePath}example/edit_info_page.do?id=' + list[i].id + '" title="修改" class="security-btn" key="ajax_page_dialog_example:dialog:edit" style="display:none;cursor: pointer;">修改</a> '
						+ '</td></tr>'
			}
		} else {
			html_ = '<tr><td colspan="11" style="text-align: center;">' + obj.msg + '</td></tr>';
		}
		
		// 弹窗分页-表格绘制-固定代码段 -> Second start 
		if($('#dialog-ajax-tbody').length == 0){ 
			$('#dialog-ajax-tbody' , window.parent.document).append(html_);
		}else{
			$('#dialog-ajax-tbody').append(html_);
		}
		// 弹窗分页-表格绘制-固定代码段 -> Second end
	}

	//搜索
	function searchUser() {
		aForm.formPaging(1);
	}

	// 重置查询条件
	function searchReset() {
		$(".form-search").val(""); 
		aForm.formPaging(1);
	}
</script>

<div class="centercontent tables">
	<!--这个跳转页面的功能及跳转路径等等-->
	<div class="pageheader notab">
		<h1 class="pagetitle">常用分页表单示例</h1>
		<!-- <span class="pagedesc">
                这个页面描述了系统中较高级的功能：Ajax分页 且弹窗同时分页。该页面所在的路径已经隐藏在下面
                【鼠标右键】 -> 【审查元素】可以看到如下路径【jsp/example/ajaxFormDialogExample】
            </span> -->
		<span style="display: none">jsp/example/ajaxFormDialogExample</span>
	</div>

	<div id="contentwrapper" class="contentwrapper">
		<div id="table-form" class="dataTables_wrapper">
			<div class="contenttitle2">
				<p style="margin: 0px">
					<label>姓名：</label> 
					<span class="field"> 
						<input id="user-name" type="text" name="userName" class="form-search" />
					</span> 
					
					<label>手机号：</label> 
					<span class="field"> 
						<input id="mobile" type="text" name="mobile" class="form-search" />
					</span> 
					
					<label>性别：</label> 
					<span class="field"> 
						<select id="sex" name="sex" class="form-search">
							<option value="">请选择---</option>
							<option value="1">男</option>
							<option value="2">女</option>
						</select>
					</span> 
					
					<a onclick="searchReset()" class="btn btn_orange btn_search radius50 security-btn" key="ajax_page_dialog_example:reset"  style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
						<span> 重 置 </span>
					</a> 
					<a onclick="searchUser()" class="btn btn_orange btn_search radius50 security-btn" key="ajax_page_dialog_example:search"  style="display:none;float: right; cursor: pointer; margin-left: 20px"> 
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
						<th class="head0 nosort"><input type="checkbox" /></th> <%-- sorting 代表可排序--%>
						<th class="head0 sorting_asc">ID(升序排序)</th> <%-- sorting_asc 代表升序排列--%>
						<th class="head0 sorting_desc">姓名(降序排序)</th> <%-- sorting_desc 代表降序排列--%>
						<th class="head0 sorting">手机(s)</th>
						<th class="head0 sorting">身份证号</th>
						<th class="head0 sorting">E-mail</th>
						<th class="head0 " width="100px">操作</th>
					</tr>
				</thead>
				<tbody id="ajax-tbody-1" class="page-list">
					<!--  class="page-list" 标识是页面数据列表 行变色使用 -->
					<%-- 等待填充 --%>
				</tbody>
			</table>
		</div>
	</div>

</div>




<%-- Ajax分页 且弹窗同时分页 --%>
<div id="dialog-page-div" class="dialog-page-div" style="display: none;width: 1200px;height: 600px">
    <p class="dialog-title">
        <a href="#" onclick="closeDialog()" class="dialog-close"></a>
        Ajax分页 且弹窗同时分页
    </p>

    <div id="dialog-content-wrapper" class="contentwrapper">
        <div id="dialog-table-form" class="dataTables_wrapper" >
            <div id="dialog-dyntable" class=" dialog-show-count" >
                <label>
                    当前显示 
                    <select>
                        <option value="10">10</option>
                        <%--注意：这里最好只给10条，因为悬浮窗体并不会随之变大 onchange()事件也最好不要，但这里作为示例给出 - Yangcl--%>
                    </select>
                    条记录
                </label>
            </div>

            <%-- 以下内容根据你自己的业务需要进行修改--%>
            <table id="dialog-table" cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <thead>
	                <tr>
	                    <th class="head0 nosort">
	                        <input type="checkbox"/>
	                    </th>                                                                           <%-- sorting 代表可排序--%>
	                    <th class="head0 sorting_asc">ID(升序排序)</th>  <%-- sorting_asc 代表升序排列--%>
	                    <th class="head0 sorting_desc"> 姓名(降序排序)</th>   <%-- sorting_desc 代表降序排列--%>
	                    <th class="head0 sorting">手机(s)</th>
	                    <th class="head0 sorting">身份证号</th>
	                    <th class="head0 sorting">E-mail</th>
	                    <th class="head0 " width="100px">操作</th>
	                </tr>
                </thead>

                <tbody id="dialog-ajax-tbody" class="page-list"><!--  class="page-list" 标识是页面数据列表 行变色使用 -->
                <%-- 等待填充 --%>
                </tbody>
            </table>

        </div>
    </div>

</div>
























