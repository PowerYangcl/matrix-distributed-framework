<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<%@ include file="/inc/iframe-head.jsp" %>

        <div class="centercontent">
			<div class="pageheader notab">
	            <h1 class="pagetitle">${companyInfo.name} 编辑支付信息</h1>
	            <span style="display:none">jsp/syssetting/company/companyPaymentInfoEdit.jsp</span>
	        </div>
	        
			<div id="validation" class="subcontent" style="display: block">
				<form id="form-edit-company-payment-info" class="stdform">
					<input type="hidden" name="id" id="id" value="${entity.id}">
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
								<option value="1" ${entity.payType == "1"?'selected':''}>支付宝h5支付</option>
								<option value="2" ${entity.payType == "2"?'selected':''}>支付宝扫码支付</option>
								<option value="3" ${entity.payType == "3"?'selected':''}>微信h5支付</option>
								<option value="4" ${entity.payType == "4"?'selected':''}>微信扫码支付</option>
								<option value="5" ${entity.payType == "5"?'selected':''}>微信公众号</option>
								<option value="6" ${entity.payType == "6"?'selected':''}>微信小程序支付</option>
						
							</select>
						</span>
					</p>
					<p>
						<label>商户号</label>
						<span class="field">
							<input type="text" name="partner" id="partner" value="${entity.partner}" class="ae-form-input" required/>
						</span>
					</p>
					<p>
						<label>appid</label>
						<span class="field">
							<input type="text" name="appid" id="appid"  value="${entity.appid}" class="ae-form-input" />
						</span>
					</p>
					
					<p>
						<label>应用公钥</label>
						<span class="field">
							<input type="text" name="publicKey" id="publicKey"  value="${entity.publicKey}" class="ae-form-input" />
						</span>
					</p>
					
					
					<p>
						<label>商户私钥</label>
						<span class="field">
							<input type="text" name="privateKey" id="privateKey" value="${entity.privateKey}" class="ae-form-input" />
						</span>
					</p>
					<p>
						<label>应用秘钥</label>
						<span class="field">
							<input type="text" name="appSecret" id="appSecret"  value="${entity.appSecret}" class="ae-form-input" />
						</span>
					</p>
				</form>
			</div>
			<br />
			<a onclick="editInfo()" class="btn btn_orange btn_home radius50" style=" cursor: pointer;margin-left: 220px">
				<span> 修&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp改</span>
			</a>
        </div>



<script type="text/javascript">
	$(function() {
		//初始化表单验证
		$("#form-edit-company-payment-info").validate();
	});
	
	
	function editInfo(){ 
		//调用表单验证方法
		if (!$("#form-edit-company-payment-info").valid()) {
	        return;
	    }
		mconfirm('您确定要修改这条记录吗？', '系统提示', function(flag) {
			if(flag){
				var type_ = 'post';
				var url_ = '${basePath}company/edit_company_payment_info.do';
				var data_ = $("#form-edit-company-payment-info").serializeArray();
				var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
				if(obj.status == 'success'){
					malert(obj.msg, '系统提示' , function(){
						window.location.href = '${basePath}/company/company_payment_info_list_page.do?cid=${companyInfo.id}'  
					});
				}else{
					malert(obj.msg, '系统提示'); 
				}
			}
		});
	}
	
	
	
</script>
































