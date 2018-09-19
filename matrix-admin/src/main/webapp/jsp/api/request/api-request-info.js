
var requestInfo = {
		rowId:null, // 一条记录的id
		
		loadTable : function(url_){
			if (url_ == undefined) { // 首次加载表单
				requestInfo.draw(aForm.jsonObj);
				return;
			}
			// 这种情况是响应上一页或下一页的触发事件
			var type_ = 'post';
			var data_ = {
				organization : $("#organization").val() ,
				key : $("#key").val() 
			};
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			aForm.launch(url_, 'table-form', obj).init();
			requestInfo.draw(obj);
		},

		draw : function(obj){
			$('#ajax-tbody-1 tr').remove();
			var html_ = '';
			var list = obj.data.list;
			if (list.length > 0) {
				for (var i = 0; i < list.length; i++) {
					var type_ = '公司内部使用';
					if(list[i].atype == 'public'){
						type_ = '对外开放接口';
					}
					
					var btitle = '启用';
					var flag_ = 1;
					if(list[i].flag == 1){
						btitle = '禁用';
						flag_ = 0;
					} 
					html_ += '<tr id="tr-' + list[i].id + '" class="gradeX">' 
							+ '<td width="400px" align="center">' + list[i].organization + '</td>'
							+ '<td width="300px" align="center">' + list[i].key + '</td>'
							+ '<td align="center">' + type_ + '</td>'
							+ '<td align="center">' + list[i].createTime + '</td>'
							+ '<td align="center">' + list[i].updater + '</td>'
							+ '<td width="200px" align="center">'
								+ '<a onclick="requestInfo.deleteRow(this)" eleId="' + list[i].id + '" flag="' + flag_ + '" style="display:none;cursor: pointer;" class="security-btn" key="api_requester_info:delete"> ' + btitle + ' </a> '
								+ '<a onclick="requestInfo.openEditDialog(this)"  eleId="' + list[i].id + '"  atype="' + list[i].atype + '"  title="修改"  style="display:none;cursor: pointer;" class="security-btn" key="api_requester_info:edit">修改</a> '
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
			requestInfo.search();
		},
		
		closeDialog : function(){
			window.parent.$.unblockUI();
		},
		
		openAddDialog : function(){
			$("#add-dialog-form").find("input[type='text']").val("");
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
		addRequestInfo : function(){
			var type_ = 'post';
			var url_ = 'ajax_request_info_add.do';
			var data_ = $("#add-dialog-form" , parent.document).serializeArray() ;
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			if(obj.status == 'success'){
				malert(obj.msg , '系统提示' , function(){
					requestInfo.search();
					requestInfo.closeDialog();
				});
			}else{
				malert(obj.msg , '系统提示');
			}
		},
		
		/**
		 * 打开编辑弹层
		 */
		openEditDialog : function(o){
			requestInfo.drawEditDialog(o);  
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
			var id = $(o).attr("eleId");
			var atype = $(o).attr("atype");
			requestInfo.rowId = id;
			var name_ = $($("#tr-" + id).children("td")[0]).text();  
			$("#organization-edit").val(name_);
			$("#atype-edit").val(atype);
			$("#atype-edit").find("option[value='" + atype + "']").attr("selected",true);
		},
		
		/**
		 * 更新一条记录
		 */
		editApiRequestInfo:function(){
			var organization_ = $("#organization-edit" , parent.document).val(); 
			var atype_ = $("#atype-edit" , parent.document).val(); 
			mconfirm( '您确定要修改这条记录吗? ' , '系统提示', function(flag) {
				if(flag){
					var type_ = 'post';
					var url_ = 'ajax_request_info_edit.do';
					var data_ = {
						id:requestInfo.rowId,
						isallot:0,
						organization : organization_,
						atype : atype_
					};
					var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
					if(obj.status == 'success'){
						requestInfo.search();
						requestInfo.closeDialog();  
					}else{
						malert(obj.msg , '系统提示');
					}
				}
			});
		},
		
		deleteRow:function(o){
			var id = $(o).attr("eleId");  
			var msg = '禁用';
			if($(o).attr("flag") == 1){
				msg = '启用';
			}
			
			mconfirm('您确定要' + msg + '【' + $($("#tr-" + id).children("td")[0]).text() + '】吗?', '系统提示', function(flag) {
				if(flag){
					var type_ = 'post';
					var url_ = 'ajax_request_info_edit.do';
					var data_ = {
						id:id ,
						isallot:0,
						flag : $(o).attr("flag")   
					};
					var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
					if(obj.status == 'success'){
						requestInfo.search();
					}else{
						malert(obj.msg , '系统提示');
					}
				}
			});
		}



}













