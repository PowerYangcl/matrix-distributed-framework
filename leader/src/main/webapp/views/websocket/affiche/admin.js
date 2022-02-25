/**
 * 系统权限配置 / 系统工具 / 发送系统公告
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
  	  	var stompClient = null;
  	  	
  	  	// 建立连接
  	  	form.on('submit(connect)', function(o){
  	  		// 1、连接SockJS的endpoint是
  	  		var socket = new SockJS('/leader/matrix-endpoint');
  	  		
  	  		// 2、用stom进行包装，规范协议
  	  		stompClient = Stomp.over(socket);
  	  		stompClient.connect({}, function(frame) {
  	  			
  	  			// 3、建立通讯
  	  			console.log('@@@@@@@@ Connected: ' + frame);
  	  			
  	  			// 4、通过stompClient.subscribe()订阅服务器的多目标
	  	  		stompClient.subscribe('/topic/game_chat', function(result) {
					console.log('接收到消息 1 = ' + result)
				});
	  	  		
		  	  	stompClient.subscribe('/subscribe-page/affiche', function(result) {
					console.log('接收到消息 2 = ' + result)
				});
		  	  	
		  	  	stompClient.subscribe('/app/topic/game_chat', function(result) {
					console.log('接收到消息 3 = ' + result)
				});
  	  		});
  	  		
  	  		
  	  		layer.alert('连接已建立' , {title:'系统提示 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4}, function(index) {
  	  			$('#channle').attr("disabled",true);
  	  			$('#connect').addClass("layui-btn-disabled").attr("disabled",true);
  	  			layer.close(index);
  	  		});
  	  	});
  	  	
  	  	// 断开连接
  	  	form.on('submit(disconnect)', function(o){
	  	  	if (stompClient !== null) {
				stompClient.disconnect();
			}
	  	  	layer.alert('连接已断开' , {title:'系统提示 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4}, function(index) {
				$('#channle').attr("disabled",false);
				$('#connect').removeClass("layui-btn-disabled").attr("disabled",false);
				layer.close(index);
			});
		});
  	  	
  	  	// 发送通告
  	  	form.on('submit(send)', function(o){
  	  		//1、通过stompClient.send 向/affiche/pagechat目标发送消息，这个是在控制器的@messageMapping中定义
  	  		var channel = $('#channle').val();
  			stompClient.send("/page/affiche/pagechat", {}, JSON.stringify({
  				'content' : $("#content").val(),
  				'channel': channel
  			}));
  	  	});
  	  	
  	  	
  	  	
  	  	
  	  	
  	  
  	  	
  	  	$(function(){
  	  		// TODO 初始化内容
  		});
  	  	
	});
























