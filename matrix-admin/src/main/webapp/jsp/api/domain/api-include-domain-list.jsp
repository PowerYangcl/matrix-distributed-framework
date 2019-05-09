<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp" %>

<script type="text/javascript" src="${jsp}/api/domain/api-include-domain.js"></script>
<script type="text/javascript">
	$(function() {
		var type_ = 'post';
		var url_ = '${basePath}apicenter/ajax_include_domain_page_list.do';
		var data_ = null; // 可以为null，后台会进行默认处理
		var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
		aForm.launch(url_, 'table-form', obj).init().drawForm(domains.loadTable);
	});

	
</script>

<div class="centercontent tables">
	<!--这个跳转页面的功能及跳转路径等等-->
	<div class="pageheader notab">
		<h1 class="pagetitle">展示开放接口允许的跨域信息-增强安全性</h1>
		<span class="pagedesc"> 
			当开放一个接口的时候，如果不勾选跨域信息，则默认拒绝任何跨域请求，JavaScript将无法调用此接口。
			列表中都是可信任的域名，但请注意！生产线禁止加入localhost:8080和127.0.0.1:8080等ip域名。
		</span> 
		<span style="display: none">jsp/api/domain/api-include-domain-list.jsp</span>
	</div>

	<div id="contentwrapper" class="contentwrapper">

		<%-- table-form 这个id分页使用 --%>
		<div id="table-form" class="dataTables_wrapper">
			<div class="contenttitle2">
				<p style="margin: 0px">
					<label>域名：</label> 
					<span class="field"> 
						<input id="domain" name="domain" type="text"  class="form-search" />
					</span> 
					<label>所属公司：</label> 
					<span class="field"> 
						<input id="company-name" name="companyName" type="text" class="form-search" />
					</span> 
					
					<a onclick="domains.openAddDialog()" class="btn btn_orange btn_search radius50 security-btn" key="include_domain_list:add" style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
						<span> 添 加 </span>
					</a> 
					<a onclick="domains.searchReset()" class="btn btn_orange btn_search radius50 security-btn" key="include_domain_list:reset" style="display:none;float: right; cursor: pointer; margin-left: 10px"> 
						<span> 重 置 </span>
					</a> 
					<a onclick="domains.search()" class="btn btn_orange btn_search radius50 security-btn" key="include_domain_list:search" style="display:none;float: right; cursor: pointer; margin-left: 20px"> 
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
						<th class="head0" width="400px" >域名</th>  
						<th class="head0" width="300px">所属公司</th>
						<th class="head0">创建时间</th> 
						<th class="head0">更新人</th>
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


<!-- 添加弹层 -->
<div id="add-dialog-div" class="dialog-page-div" style="display: none;width: 350px;height: 200px">
    <p class="dialog-title">
        <a href="#" onclick="closeDialog()" class="dialog-close"></a>
        添加跨域白名单
    </p>

    <div id="dialog-content-wrapper" class="contentwrapper">
        <div id="dialog-table-form" class="dataTables_wrapper" >
            <form id="add-dialog-table" action="javascript:void(0)">   
	            <table class="dialog-table">
	                <tbody>
	                	<tr >
	                		<td style="text-align: right">
	                			域名：
	                		</td>
	                		<td style="text-align: left">
	                			<input type="text" id="domain-add" name="domain" class="dialog-form-input" style="width:200px;"/>
	                		</td>
	                	</tr>
	                	<tr >
	                		<td style="text-align: right">
	                			所属公司：
	                		</td>
	                		<td style="text-align: left">
	                			<input type="text" id="company-name-add" name="companyName" class="dialog-form-input" style="width:200px;"/>
	                		</td>
	                	</tr>
	                </tbody>
	                <tfoot>
		                <tr>
					      <td colspan="2" style="text-align: right;">
					      	<button class="stdbtn btn_orange" style="opacity:1; margin-top:20px;" onclick='subpage.domains.addDomain()' >提 &nbsp&nbsp&nbsp&nbsp&nbsp 交</button>
					      </td> 
					    </tr>
	                </tfoot>
	            </table>
            </form>
        </div>
    </div>
</div>


<!-- 修改弹层 -->
<div id="edit-dialog-div" class="dialog-page-div" style="display: none;width: 350px;height: 200px">
    <p class="dialog-title">
        <a href="#" onclick="closeDialog()" class="dialog-close"></a>
        修改跨域白名单
    </p>

    <div id="dialog-content-wrapper-edit" class="contentwrapper">
        <div id="dialog-table-form-edit" class="dataTables_wrapper" >
            <form id="edit-dialog-table" action="javascript:void(0)">   
	            <table class="dialog-table">
	                <tbody>
	                	<tr >
	                		<td style="text-align: right">
	                			域名：
	                		</td>
	                		<td style="text-align: left">
	                			<input type="text" id="domain-edit" name="domain" class="dialog-form-input" style="width:200px;"/>
	                		</td>
	                	</tr>
	                	<tr >
	                		<td style="text-align: right">
	                			所属公司：
	                		</td>
	                		<td style="text-align: left">
	                			<input type="text" id="company-name-edit" name="companyName" class="dialog-form-input" style="width:200px;"/>
	                		</td>
	                	</tr>
	                </tbody>
	                <tfoot>
		                <tr>
					      <td colspan="2" style="text-align: right;">
					      	<button class="stdbtn btn_orange" style="opacity:1; margin-top:20px;" onclick='subpage.domains.editDomain()' >提 &nbsp&nbsp&nbsp&nbsp&nbsp 交</button>
					      </td> 
					    </tr>
	                </tfoot>
	            </table>
            </form>
        </div>
    </div>
</div>




















