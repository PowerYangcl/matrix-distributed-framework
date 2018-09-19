



var apiTest = {
		
		/**
		 * 获取请求结果
		 */
		getResponseMsg : function(path_){
			var jsonStr = $("#dto-json-str").val();
			if(apiTest.trim($("#target").val()) == '' || apiTest.trim(jsonStr) == ''){
				malert('缺少关键请求值!', '系统提示');
			}else{
				var requester = new Object();
				var head_ = new Object();
				head_.target = $("#target").val(); 
				head_.client = $("input[name='client']:checked").val();
				head_.version = $("#version").val();
				head_.requestTime = $("#request-time").val();
				head_.channel = $("#channel").val();
				head_.key = $("input[name='requester']:checked").val(); 
				head_.value = md5(apiTest.findRequestValue(head_.key) + head_.target + head_.requestTime); 
				var data_ = JSON.parse(jsonStr); 
				requester.head = head_; 
				requester.data = data_; 
				
				var type_ = 'post';
				var url_ = path_ + 'api/matrix.do';
				var data_ = {json : JSON.stringify(requester)}; 
				var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
				if (obj.status == 'success') {
					$("#json-response").val(JSON.stringify(obj));
				}else{
					malert(obj.msg, '系统提示');
					$("#json-response").val("");  
				}
			}
		},
		
		/**
		 * onmouseout 事件|根据系统接口名称获取请求参数JSON消息体
		 */
		findRequestDto : function(o){
			$("#dto-json-str").val("");  
			var target_ = $(o).val();
			if(apiTest.trim(target_) == ""){
				return;
			}
			var type_ = 'post';
			var url_ = 'ajax_find_request_dto.do';
			var data_ = {target : target_};
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			if (obj.status == 'success') {
				$("#dto-json-str").val(JSON.stringify(obj.dto));
			}else{
				malert(obj.msg, '系统提示');
				$("#dto-json-str").val("");  
			}
		},
		
		findRequestValue:function(key){
			var value = '';
			var type_ = 'post';
			var url_ = 'ajax_find_request_value.do';
			var data_ = {key : key};
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			if (obj.status == 'success') {
				value = obj.data;
			}else{
				malert(obj.msg, '系统提示');
				$("#dto-json-str").val("");  
			}
			return value;
		},

		
		trim:function(str){
			 return str.replace(/(^\s+$)/g, "");
		}

}













