<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<%@ include file="/inc/iframe-head.jsp" %>

        <div class="centercontent">
			<div class="pageheader notab">
	            <h1 class="pagetitle">创建公司信息</h1>
	            <span style="display:none">jsp/syssetting/company/companyInfoAdd.jsp</span>
	        </div>
	        
			<div id="validation" class="subcontent" style="display: block">
				<form id="form-add-company-info" class="stdform">
					<p>
						<label>公司名称</label>
						<span class="field">
							<input type="text" name="name" id="name" class="ae-form-input" required/>
						</span>
					</p>
					
					<p>
						<label>公司介绍</label>
						<span class="field">
							<textarea type="text" name="intro" id="intro" class="ae-form-input" required></textarea>
						</span>
					</p>
					
					<p>
						<label>营业执照</label>
						<span class="field">
							<div id="show-upload-image-div"  class="field" style="margin-bottom:20px">
								<!-- 等待填充 -->
							</div>
							<iframe src="../jsp/sys_page/uploadImage.jsp" style="height:40px;"></iframe>
							<input type="hidden" name="businessLicense" id="businessLicense" class="ae-form-input" />
						</span>
					</p>
					
					<p>
						<label>关键字</label>
						<span class="field">
							<input type="text" name="keyWord" id="keyWord" class="ae-form-input" />
						</span>
					</p>
					
					<p>
						<label>公司logo</label>
						<span class="field">
							<div id="show-upload-image-div"  class="field" style="margin-bottom:20px">
								<!-- 等待填充 -->
							</div>
							<iframe src="../jsp/sys_page/uploadImage.jsp" style="height:40px;"></iframe>
							
							<input type="hidden" name="logo" id="logo" class="ae-form-input" />
						</span>
					</p>
					
					<p>
						<label>联系电话</label>
						<span class="field">
							<input type="text" name="officePhone" id="officePhone" class="ae-form-input" required/>
						</span>
					</p>
					
					<p>
						<label>公司地址</label>
						<span class="field">
							<input type="text" name="address" id="address" class="ae-form-input" required/>
						</span>
					</p>
					
					<p>
						<label>客户类型</label>
						
						<span id="type-info" class="field" style="padding-top:5px">
							<input type="radio"  name="type" value="1" style="vertical-align:middle;">
							<span style="vertical-align:middle;">连锁</span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="type" value="2" style="vertical-align:middle;">
							<span style="vertical-align:middle;">单店</span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  </span>
						
					</p>
					
					<p>
						<label>合同附件</label>
						<span class="field">
							<div id="show-upload-image-div"  class="field" style="margin-bottom:20px">
								<!-- 等待填充 -->
							</div>
							<iframe src="../jsp/sys_page/uploadImage.jsp" style="height:40px;"></iframe>
							
							<input type="hidden" name="leaseCcessories" id="leaseCcessories" class="ae-form-input" />
						</span>
					</p>
					
					<p>
						<label>客户联系人</label>
						<span class="field">
							<input type="text" name="contactPerson" id="contactPerson" class="ae-form-input" required/>
						</span>
					</p>
					
					<p>
						<label>联系方式</label>
						<span class="field">
							<input type="text" name="contactPersonPhone" id="contactPersonPhone" class="ae-form-input" required/>
						</span>
					</p>
					
					<p>
						<label>联系人职位</label>
						<span class="field">
							<input type="text" name="contactPersonPosition" id="contactPersonPosition" class="ae-form-input" />
						</span>
					</p>					
					
					<p>
						<label>联系人地址</label>
						<span class="field">
							<input type="text" name="contactPersonAddress" id="contactPersonAddress" class="ae-form-input" />
						</span>
					</p>					
					
					 <p>
						<label>短信充值数量</label>
						<span class="field">
							<input type="text" name="smsNum" id="smsNum" class="ae-form-input" />
						</span>
					 </p>	
					
						<p>
						<label>标记用户是否进行过充值</label>
						<span id="smsRecharge-info" class="field" style="padding-top:5px">
							<input type="radio"  name="smsRecharge" value="0" style="vertical-align:middle;">
							<span style="vertical-align:middle;">未充值</span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="smsRecharge" value="1" style="vertical-align:middle;">
							<span style="vertical-align:middle;">充过</span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 	 </span>
					  
					  
					 	</p>	
					 	
					 <p>
						<label>公司全局短信阀值</label>
						<span class="field">
							<input type="text" name="smsThreshold" id="smsThreshold" class="ae-form-input" />
						</span>
					 </p>	

					
					 <p>
						<label>公司行业</label>
						<span class="field">
							<input type="text" name="dictVocationInfoId" id="dictVocationInfoId" class="ae-form-input" />
						</span>
					 </p>	
					 	
					 					
					 <p>
						<label>是否进行了微信授权</label>
						
						<span id="wechatAuth-info" class="field" style="padding-top:5px">
							<input type="radio"  name="wechatAuth" value="0" style="vertical-align:middle;">
							<span style="vertical-align:middle;">未授权</span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="wechatAuth" value="1" style="vertical-align:middle;">
							<span style="vertical-align:middle;">已授权</span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 	 </span>
					 	 
						
					 </p>						
					
					 <p>
						<label>统一社会信用代码</label>
						<span class="field">
							<input type="text" name="unifiedSocialCreditCode" id="unifiedSocialCreditCode" class="ae-form-input" />
						</span>
					 </p>	
					
					
					<p>
						<label>注册资本</label>
						<span class="field">
							<input type="text" name="registeredCapital" id="registeredCapital" class="ae-form-input" />
						</span>
					 </p>	
					
					<p>
						<label>注册机构|等级机关</label>
						<span class="field">
							<input type="text" name="registrationAuthority" id="registrationAuthority" class="ae-form-input" />
						</span>
					  </p>	
					
					<p>
						<label>经营范围</label>
						<span class="field">
							<input type="text" name="remark" id="remark" class="ae-form-input" />
						</span>
					  </p>	
					
					<!-- p>
						<label>公司成立时间</label>
						<span class="field">
							<input type="text" name="establishTime" id="establishTime" class="ae-form-input" />
						</span>
					  </p>	
					<p -->
						<label>公司类型</label>
						<span class="field">
							<input type="text" name="companyType" id="companyType" class="ae-form-input" />
						</span>
					  </p>	
				</form>
			</div>
			<br />
			<a onclick="addCompanyInfo()" class="btn btn_orange btn_home radius50" style=" cursor: pointer;margin-left: 220px">
				<span> 添&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp加 </span>
			</a>
        </div>



<script type="text/javascript">
	$(function() {
		//初始化表单验证
		$("#form-add-company-info").validate();
	});
	
	
	function addCompanyInfo(){
		//调用表单验证方法
		if (!$("#form-add-company-info").valid()) {
	        return;
	    }
		var url_ ='${basePath}company/add_company_info.do';
		var data_ = $("#form-add-company-info").serializeArray();
		var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
		if(obj.status == 'success'){
			alert(obj.msg);
			window.location.href = "${basePath}company/page_company_info_list.do";
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
































