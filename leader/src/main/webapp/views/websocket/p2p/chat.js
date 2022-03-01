/**
 * 系统权限配置 / 系统工具 / 一对一聊天
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
  	  	var userInfo = JSON.parse(localStorage.userInfo);
  	  	
  	  	// 建立连接
  	  	form.on('submit(connect)', function(o){
  	  		// 1、连接SockJS的endpoint是
  	  		var socket = new SockJS('/leader/endpoint-chat');
  	  		
  	  		// 2、用stom进行包装，规范协议
  	  		stompClient = Stomp.over(socket);
  	  		// 3、建立通讯
  	  		stompClient.connect({}, function(frame) {
  	  			// 4、订阅发给我的频道！！！！！！！！！！！！！！！！！！！！！！
		  	  	stompClient.subscribe('/subscribe-page/chat/' + userInfo.userName, function(result) {
			  	  	$("#notice-defalut-tr").remove();
		  	  		var body = JSON.parse(result.body);
		  	  		$("#notice").append('<tr><td>' + body.from + '</td><td  colspan="2" align="left">' + body.content + '</td><td>' + body.time + '</td></tr>');
				});
  	  		});
  	  		
  	  		layer.alert('连接已建立' , {title:'系统提示 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4}, function(index) {
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
				$('#connect').removeClass("layui-btn-disabled").attr("disabled",false);
				layer.close(index);
			});
		});
  	  	
  	  	// 发送通告
  	  	form.on('submit(send)', function(o){
  			stompClient.send("/page/p2p/chat", {}, JSON.stringify({
  				'content' : $("#content").val(),
  				'from':userInfo.userName,
  				'to': $('#to').val()
  			}));
  			$("#notice-defalut-tr").remove();
  	  		$("#notice").append('<tr><td><span style="color:red">' + userInfo.userName + '</span></td><td  colspan="2" align="left">' + $("#content").val() + '</td><td>' 
  	  				+ new Date().toISOString().slice(0, 19).replace('T', ' ') + '</td></tr>');
  	  	});
  	  	
  	  	
  	  	
  	  	
  	  	
  	  
  	  	
  	  	$(function(){
  	  		// TODO 初始化内容
  		});
  	  	
	});
























