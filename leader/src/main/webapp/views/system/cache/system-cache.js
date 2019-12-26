/**
 * 系统权限配置 / 系统工具 / 缓存查看
 */
layui.config({
    	base: '../layuiadmin/' //静态资源所在路径
  	}).extend({
		setter : 'config', 		// 配置模块
	}).use(['form' , 'setter'] , function(){
  	  	var $ = layui.$;
  	  	var layer = layui.layer;
  	  	var setter = layui.setter;
  	  	var form = layui.form;
  	  	
  	  	// 获取缓存
  	  	form.on('submit(get-cache)', function(o){
	        var url_ = layui.setter.path + 'cache/ajax_btn_get_cache.do';
	        var data_ = $('#cache-form').serializeArray();
	        var o = new Object();
	        o.name='eleValue';
	        o.value=$(this).attr("key");	// 获取安全key，固定写法
	        data_.push(o);
	        var obj = JSON.parse(layui.setter.ajaxs.sendAjax('post' , url_ , data_));
	        if(obj.status == 'success'){
	        	$("#json-str").val(JSON.stringify(obj.data));
	        }else{
	        	$("#json-str").val("");
	        	layer.alert(obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
	        }
		});
  	  	
  	  	// 删除缓存
  	  	form.on('submit(delete-cache)', function(o){
			var url_ = layui.setter.path + 'cache/ajax_btn_delete_cache.do';
			var data_ = $('#cache-form').serializeArray();
			var o = new Object();
			o.name='eleValue';
			o.value=$(this).attr("key");	// 获取安全key，固定写法
			data_.push(o);
			var obj = JSON.parse(layui.setter.ajaxs.sendAjax('post' , url_ , data_));
			if(obj.status == 'success'){
				$("#json-str").val("");
			}
			layer.alert(obj.msg , {title:'系统提示 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
  	  	});
  	  	
  	  	// 设置缓存(永久)
  	  	form.on('submit(add-cache-forever)', function(o){
			var url_ = layui.setter.path + 'cache/ajax_btn_reset_cache_forever.do';
			var data_ = $('#cache-form').serializeArray();
			var o = new Object();
			o.name='eleValue';
			o.value=$(this).attr("key");	// 获取安全key，固定写法
			data_.push(o);
			var o1 = new Object();
			o1.name='jsonStr';
			o1.value=$("#json-str").val();	 
			data_.push(o1);
			
			var obj = JSON.parse(layui.setter.ajaxs.sendAjax('post' , url_ , data_));
			if(obj.status == 'success'){
				$("#json-str").val(JSON.stringify(obj.data));
			}else{
				layer.alert(obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
			}
	  	});
  	  
  	  	// 设置缓存
  	  	form.on('submit(add-cache)', function(o){
			var url_ = layui.setter.path + 'cache/ajax_btn_reset_cache.do';
			var data_ = $('#cache-form').serializeArray();
			var o = new Object();
			o.name='eleValue';
			o.value=$(this).attr("key");	// 获取安全key，固定写法
			data_.push(o);
			var o1 = new Object();
			o1.name='jsonStr';
			o1.value=$("#json-str").val();	 
			data_.push(o1);
			
			var obj = JSON.parse(layui.setter.ajaxs.sendAjax('post' , url_ , data_));
			if(obj.status == 'success'){
				$("#json-str").val(JSON.stringify(obj.data));
			}else{
				layer.alert(obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
			}
	  	});

  	  	// 批量删除缓存
  	  	form.on('submit(batch-delete-cache)', function(o){
			var url_ = layui.setter.path + 'cache/ajax_btn_batch_delete.do';
			var data_ = $('#cache-form').serializeArray();
			var o = new Object();
			o.name='eleValue';
			o.value=$(this).attr("key");	// 获取安全key，固定写法
			data_.push(o);
			var obj = JSON.parse(layui.setter.ajaxs.sendAjax('post' , url_ , data_));
			if(obj.status == 'success'){
				$("#json-str").val("");
			}
			layer.alert(obj.msg , {title:'系统提示 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
  	  	});
  	  	
  	  	
  	  	$(function(){
  	  		$("input[type=text]").on("input",function(e){
  	  			var key = $("#type > option:selected").attr("dict") + "-" + $("#prefix").val() + "-" + $("#cache-key").val();
  	  			$("#redis-key").html(key);
  	  		});
  	  		
  	  		// TODO  onchange 
  	  		$("#type").on("change",function(e){
	  			var key = $("#type > option:selected").attr("dict") + "-" + $("#prefix").val() + "-" + $("#cache-key").val();
	  			$("#redis-key").html(key);
	  		});
  		});
  	  	
	});
























