
var domains = {
		rowId:null, // 一条记录的id
		
		loadTable : function(url_){
			if (url_ == undefined) { // 首次加载表单
				domains.draw(aForm.jsonObj);
				return;
			}
			// 这种情况是响应上一页或下一页的触发事件
			var type_ = 'post';
			var data_ = {
				domain : $("#domain").val() ,
				companyName : $("#company-name").val() 
			};
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			aForm.launch(url_, 'table-form', obj).init();
			domains.draw(obj);
		},

		draw : function(obj){
			$('#ajax-tbody-1 tr').remove();
			var html_ = '';
			var list = obj.data.list;
			if (list.length > 0) {
				for (var i = 0; i < list.length; i++) {
					html_ += '<tr id="tr-' + list[i].id + '" class="gradeX">' 
							+ '<td width="400px" align="center">' + list[i].domain + '</td>'
							+ '<td width="300px" align="center">' + list[i].companyName + '</td>'
							+ '<td align="center">' + list[i].createTime + '</td>'
							+ '<td align="center">' + list[i].updater + '</td>'
							+ '<td width="200px" align="center">'
								+ '<a onclick="domains.deleteRow(this)" eleId="' + list[i].id + '" title="删除"  style="display:none;cursor:pointer;" class="security-btn" key="include_domain_list:delete">删除</a> '
								+ '<a onclick="domains.openEditDialog(this)"  eleId="' + list[i].id + '"  title="修改"  style="display:none;cursor:pointer;" class="security-btn" key="include_domain_list:edit">修改</a> '
							+ '</td></tr>'
				}
			} else {
				html_ = '<tr><td colspan="11" style="text-align: center;">' + obj.msg + '</td></tr>';
			}
			$('#ajax-tbody-1').append(html_);
		},
		
		search : function(){
			aForm.formPaging(1);
		},
		
		searchReset : function(){
			$(".form-search").val(""); 
			domains.search();
		},
		
		closeDialog : function(){
			window.parent.$.unblockUI();
		},
		
		openAddDialog : function(){
			$("#add-dialog-table").find("input[type='text']").val("");
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
		},
		
		/**
		 * 添加一条记录
		 */
		addDomain : function(){
			var type_ = 'post';
			var url_ = 'ajax_api_domain_add.do';
			var data_ = $("#add-dialog-table" , parent.document).serializeArray() ;
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			if(obj.status == 'success'){
				malert(obj.msg , '系统提示' , function(){
					domains.search();
					domains.closeDialog();
				});
			}else{
				malert(obj.msg , '系统提示');
			}
		},
		
		/**
		 * 打开编辑弹层
		 */
		openEditDialog : function(o){
			domains.drawEditDialog(o);  
			var dialogId = 'edit-dialog-div';   // 弹窗ID
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
		},
		/**
		 * 绘制编辑弹层
		 */
		drawEditDialog:function(o){
			$("#domain-edit").val("");
			$("#company-name-edit").val("");
			var id = $(o).attr("eleId");
			domains.rowId = id;
			var domain_ = $($("#tr-" + id).children("td")[0]).text(); 
			var company_ = $($("#tr-" + id).children("td")[1]).text(); 
			$("#domain-edit").val(domain_);
			$("#company-name-edit").val(company_);
		},
		
		/**
		 * 更新一条记录
		 */
		editDomain:function(){
			var domain_ = $("#domain-edit" , parent.document).val(); 
			var company_ = $("#company-name-edit" , parent.document).val(); 
			mconfirm( '您确定要修改这条记录吗? ' , '系统提示', function(flag) {
				if(flag){
					var type_ = 'post';
					var url_ = 'ajax_api_domain_edit.do';
					var data_ = {
						id:domains.rowId,
						domain : domain_,
						companyName : company_
					};
					var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
					if(obj.status == 'success'){
						domains.search();
						domains.closeDialog();  
					}else{
						malert(obj.msg , '系统提示');
					}
				}
			});
		},
		
		deleteRow:function(o){
			var id = $(o).attr("eleId"); 
			var domain_ = $($("#tr-" + id).children("td")[0]).text(); 
			var company_ = $($("#tr-" + id).children("td")[1]).text(); 
			
			mconfirm('您确定要删除【' + domain_ + '】吗?', '系统提示', function(flag) {
				if(flag){
					var type_ = 'post';
					var url_ = 'ajax_api_domain_edit.do';
					var data_ = {
						id:id ,
						domain : domain_ ,
						companyName : company_ ,
						flag:0 
					};
					var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
					if(obj.status == 'success'){
						domains.search();
					}else{
						malert(obj.msg , '系统提示');
					}
				}
			});
		}



}













