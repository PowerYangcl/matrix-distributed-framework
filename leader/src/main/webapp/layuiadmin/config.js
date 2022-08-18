
/**
 * @description: layui 全局配置 |layui.setter属性配置
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年10月10日 下午14:17:56 
 * @version 1.0.0.1
 */
layui.define([ 'laytpl', 'layer', 'element', 'util' ], function(exports) {
	
	exports(
		'setter', {
			container : 'LAY_app' , 		//	容器ID
			base : layui.cache.base , 	//	记录静态资源所在路径
			path : parseUrl.defaultPath() , 	
			ajaxs : ajaxs,
			parseUrl : parseUrl,
			pageBtns : new Map(),			
			views : layui.cache.base + 'tpl/' , 	 // 动态模板所在目录
			entry : 'index'  , 	// 默认视图文件名
			engine : '.html'  , 	// 视图文件后缀名
			pageTabs : true  , 	// 是否开启页面选项卡功能。iframe版推荐开启
			name :'Leader',
			tableName : 'matrix-page-cache'  , 	// 本地存储表名|localStorage 持久化存储
			MOD_NAME : 'admin'  , 	// 模块事件名
			debug : true 	 , 	// 是否开启调试模式。如开启，接口异常时会抛出异常 URL 等信息
			
			/**
			 * 安全权限操作
			 * table.js：Class.prototype.pullData = function(curr) {} 使用2次
			 * 
			 */
			security : function(btnMap){
				var checkArr = layui.$(".layui-this" , parent.document);
				var tabId = null;
				
				switch(checkArr.length) {
					 case 2:
						 // 此种情况：主窗体左侧百叶窗焦点未丢失
				    	 tabId = checkArr[1].id;
				    	 break;
				     case 1:
				    	 // 此种情况：主窗体左侧百叶窗焦点已经丢失，只剩下tab页
				    	 tabId = checkArr[0].id;
				    	 break;
				     case 0:		
				    	 // 此种情况：layer.open打开的弹窗中引入的是一个新的列表页面
				    	 checkArr = layui.$(".layui-this" , parent.parent.document);
				    	 switch(checkArr.length) {
					    	case 2:
						    	tabId = checkArr[1].id;
						    	break;
						    case 1:
						    	tabId = checkArr[0].id;
						    	break;
				    	 }
				    	 break;			     
				}
				
				var result = btnMap.get('btns-' + tabId);
				if(typeof result == 'undefined' || result == null || result.length == 0){
					return;
				}
				
				var barr = result.split(",");
				for(var i = 0 ; i < barr.length ; i ++){
	//				var key = barr[i];
					layui.$(".security-btn[key='" + barr[i] + "']").show();
					layui.$(".security-btn[key='" + barr[i] + "']").removeClass("security-btn"); 
				}
				layui.$("a.layui-btn").removeAttr("style");	// 数据表格 操作列中的按钮移除元素本身的style样式，保留css样式
				layui.$(".security-btn").remove();
			},
			
			request : {	// 自定义请求字段
				tokenName : false //自动携带 token 的字段名（如：access_token）。可设置 false 不携带。
			} , 	
	
			// 自定义响应字段
			response : {
				statusName : 'code' 		 , 	//数据状态的字段名称
				statusCode : {
					ok : 0 	 , 	// 数据状态一切正常的状态码
					logout : 1001 //登录状态失效的状态码
				},
				msgName : 'msg' 	 , 	//状态信息的字段名称
				dataName : 'data' //数据详情的字段名称
			} , 	
			
			//扩展的第三方模块
			extend : [ 
				'echarts' , //echarts 核心包
				'echartsTheme' //echarts 主题
			] , 	
	
			//主题配置
			theme : { 	// 内置主题配色方案
				color : [ {
					main : '#20222A'  , 		 // 主题色
					selected : '#009688' , 	 // 选中色
					alias : 'default' 				 // 默认别名
				}, {
					main : '#03152A',
					selected : '#3B91FF',
					alias : 'dark-blue' // 藏蓝
				}, {
					main : '#2E241B',
					selected : '#A48566',
					alias : 'coffee' // 咖啡
				}, {
					main : '#50314F',
					selected : '#7A4D7B',
					alias : 'purple-red' // 紫红
				}, {
					main : '#344058',
					logo : '#1E9FFF',
					selected : '#1E9FFF',
					alias : 'ocean' // 海洋
				}, {
					main : '#3A3D49',
					logo : '#2F9688',
					selected : '#5FB878',
					alias : 'green' // 墨绿
				}, {
					main : '#20222A',
					logo : '#F78400',
					selected : '#F78400',
					alias : 'red' // 橙色
				}, {
					main : '#28333E',
					logo : '#AA3130',
					selected : '#AA3130',
					alias : 'fashion-red' // 时尚红
				}, {
					main : '#24262F',
					logo : '#3A3D49',
					selected : '#009688',
					alias : 'classic-black' // 经典黑
				}, {
					logo : '#226A62',
					header : '#2F9688',
					alias : 'green-header' // 墨绿头
				}, {
					main : '#344058',
					logo : '#0085E8',
					selected : '#1E9FFF',
					header : '#1E9FFF',
					alias : 'ocean-header' // 海洋头
				}, {
					header : '#393D49',
					alias : 'classic-black-header' // 经典黑头
				}, {
					main : '#50314F',
					logo : '#50314F',
					selected : '#7A4D7B',
					header : '#50314F',
					alias : 'purple-red-header' // 紫红头
				}, {
					main : '#28333E',
					logo : '#28333E',
					selected : '#AA3130',
					header : '#AA3130',
					alias : 'fashion-red-header' // 时尚红头
				}, {
					main : '#28333E',
					logo : '#009688',
					selected : '#009688',
					header : '#009688',
					alias : 'green-header' // 墨绿头
				} ] , 	
	
				//	初始的颜色索引，对应上面的配色方案数组索引
				//	如果本地已经有主题色记录，则以本地记录为优先，除非请求本地数据（localStorage）
				initColorIndex : 0
			}
		});
});




/**
 * @description: 强效解析地址信息
 *
 * @author Yangcl
 * @date 2019年10月10日 下午14:46:52
 * @version 1.0.0.1
 */
var parseUrl = {
		
		/**
		 * 默认解析当前浏览器地址 | parseUrl.defaultPath()
		 */
		defaultPath : function(){
			var o = parseUrl.parse(window.location.href);
			return o.protocol + "://" + o.host + ":" + o.port + "/" + o.segments[0] + "/";
		},
		
		/**
	     * @param url
	     */
	    parse : function(url) {
	        var a =  document.createElement('a');
	        a.href = url;
	        return {
	            source: url,
	            protocol: a.protocol.replace(':',''),
	            host: a.hostname,
	            port: a.port,
	            query: a.search,
	            params: (function(){
	                var ret = {},
	                    seg = a.search.replace(/^\?/,'').split('&'),
	                    len = seg.length, i = 0, s;
	                for (;i<len;i++) {
	                    if (!seg[i]) { continue; }
	                    s = seg[i].split('=');
	                    ret[s[0]] = s[1];
	                }
	                return ret;
	            })(),
	            file: (a.pathname.match(/\/([^\/?#]+)$/i) || [,''])[1],
	            hash: a.hash.replace('#',''),
	            path: a.pathname.replace(/^([^\/])/,'/$1'),
	            relative: (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [,''])[1],
	            segments: a.pathname.replace(/^\//,'').split('/')
	        };
	    }
}


var ajaxs = {
	    /**
	     * @descriptions 封装同步ajax请求
	     *
	     * @param type_  必填post or get，一般默认填写post
	     * @param url_ 所请求的后台路径  var url_ =  "login/login.do";
	     * @param data_ 请求参数  {name:name_ , password:'0001'}  or $("#the-form-id").serializeArray()，一般选择form序列化的数组
	     * @returns json 后台向页面返回的json字符串
	     *
	     * @date 2016年5月23日上午10:34:27
	     * @author Yangcl
	     * @version 1.0.1
	     */
	    sendAjax:function(type_, url_ , data_){
	        var msg_ = null;
	        layui.$.ajax({
	            dataType : "text",
	            type :type_,
	            url : url_,
	            data : data_,
	            async : false,
	            success : function(msg) {
	                msg_ = msg;
	            },
	            error: function(msg) {
	                msg_ = msg;
	            }
	        });
	        return msg_;
	    },
	    
	    //  例如：contentType_ = 'application/json'，spring-boot配合@RequestBody 接手数组类型参数，参考添加网关路由规则
	    sendAjaxContentType:function(type_, url_ , data_, contentType_){
	        var msg_ = null;
	        layui.$.ajax({
	            dataType : "text",
	            type :type_,
	            url : url_,
	            data : data_,
	            contentType:contentType_,
	            async : false,
	            success : function(msg) {
	                msg_ = msg;
	            },
	            error: function(msg) {
	                msg_ = msg;
	            }
	        });
	        return msg_;
	    },
	    
	    /**
	     * @descriptions 封装异步ajax请求
	     *
	     * @param type_  必填post or get，一般默认填写post
	     * @param url_ 所请求的后台路径  var url_ =  "login/login.do";
	     * @param data_ 请求参数  {name:name_ , password:'0001'}  or $("#the-form-id").serializeArray()，一般选择form序列化的数组
	     * @returns json 后台向页面返回的json字符串
	     *
	     * @date 2016年5月23日上午10:34:27
	     * @author Yangcl
	     * @version 1.0.1
	     */
	    asyncAjax:function(type_, url_ , data_){
	        var msg_ = null;
	        layui.$.ajax({
	            dataType : "text",
	            type :type_,
	            url : url_,
	            data : data_,
	            async : true,
	            success : function(msg) {
	                msg_ = msg;
	            },
	            error: function(msg) {
	                msg_ = msg;
	            }
	        });
	        return msg_;
	    }
	}








