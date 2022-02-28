/**
 * 系统权限配置 / 系统工具 / 接收系统公告
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
  	  	
  	  	$(function(){
  	  		// 1、连接SockJS的endpoint是
  	  		var socket = new SockJS('/leader/matrix-endpoint');
  	  		
  	  		// 2、用stom进行包装，规范协议
  	  		stompClient = Stomp.over(socket);
  	  		// 3、建立通讯
  	  		stompClient.connect({}, function(frame) {
  	  			// 4、通过stompClient.subscribe()订阅服务器的多目标，可以同时订阅多个
	  	  		stompClient.subscribe('/topic/game_chat', function(result) {
					console.log('接收到消息 1 = ' + result)
				});
		  	  	stompClient.subscribe('/app/topic/game_chat', function(result) {
					console.log('接收到消息 3 = ' + result)
				});
		  	  	
		  	  	stompClient.subscribe('/subscribe-page/affiche', function(result) {
		  	  		$("#notice-defalut-tr").remove();
		  	  		var body = JSON.parse(result.body);
		  	  		$("#notice").append('<tr><td>' + body.content + '</td> <td>' + body.time +  '</td></tr>');
				});
  	  		});
  		});
  	  	
	});
























