<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<%@ include file="/inc/iframe-head.jsp" %>

        <div class="centercontent">
			<div class="pageheader notab">
	            <h1 class="pagetitle">${companyInfo.name} 创建支付信息</h1>
	            <span style="display:none">jsp/syssetting/company/companyPaymentInfoAdd.jsp</span>
	        </div>
	        
			<div id="validation" class="subcontent" style="display: block">
				<form id="form-add-company-payment-info" class="stdform">
					<input type="hidden" name="cid" id="cid" value="${companyInfo.id}">
					<p>
						<label>公司名称</label>
						<span class="field" style="padding-top:5px;">
							${companyInfo.name}
						</span>
					</p>
					<p>
						<label>支付类型</label>
						<span class="field">
							<select id="payType" name="payType" class="ae-form-input"style="min-width: 31%;"  required>
								<option value="">请选择</option>
								<option value="1">支付宝h5支付</option>
								<option value="2">支付宝扫码支付</option>
								<option value="3">微信扫码支付</option>
								<option value="4">微信h5支付</option>
								<option value="5">微信公众号</option>
								<option value="6">微信小程序支付</option>
						
							</select>
						</span>
					</p>
					<p>
						<label>商户号</label>
						<span class="field">
							<input type="text" name="partner" id="partner" class="ae-form-input" required/>
						</span>
					</p>
					<p>
						<label>appid</label>
						<span class="field">
							<input type="text" name="appid" id="appid" class="ae-form-input" />
						</span>
					</p>
					
					<p>
						<label>应用公钥</label>
						<span class="field">
							<input type="text" name="publicKey" id="publicKey" class="ae-form-input" />
						</span>
					</p>
					
					
					<p>
						<label>商户私钥</label>
						<span class="field">
							<input type="text" name="privateKey" id="privateKey" class="ae-form-input" />
						</span>
					</p>
					<p>
						<label>应用秘钥</label>
						<span class="field">
							<input type="text" name="appSecret" id="appSecret" class="ae-form-input" />
						</span>
					</p>
					
					
					
				</form>
			</div>
			<br />
			<a onclick="addCompanyPaymentInfo()" class="btn btn_orange btn_home radius50" style=" cursor: pointer;margin-left: 220px">
				<span> 添&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp加 </span>
			</a>
        </div>



<script type="text/javascript">
	$(function() {
		//初始化表单验证
		$("#form-add-company-payment-info").validate();
	});
	
	
	function addCompanyPaymentInfo(){
		//调用表单验证方法
		if (!$("#form-add-company-payment-info").valid()) {
	        return;
	    }
		var url_ ='${basePath}company/add_company_payment_info.do';
		var data_ = $("#form-add-company-payment-info").serializeArray();
		var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
		if(obj.status == 'success'){
			alert(obj.msg);
			window.location.href = "${basePath}company/company_payment_info_list_page.do?cid=${companyInfo.id}";
		}
		
	}
	
	
	/**
	 * uploadImage.jsp 回调此方法，此处的方法名固定，不可修改。
	 */
	function uploadFile(e) {
		var html_ = '';
		if(e.status == 'success'){
			if(e.type == 'image'){
				html_ = '<img src="' + e.url + '" title= "' + e.original + '" style="margin-bottom:20px;width:20%;" /></br>';
			}else{
				html_ = '<a href="' + e.url + '" style="margin-bottom:20px">' + e.original + '</a></br>';
			}
			$("#show-upload-image-div").append(html_);
			$("#businessLicense").val(e.url);
		}
	}
	
</script>
































