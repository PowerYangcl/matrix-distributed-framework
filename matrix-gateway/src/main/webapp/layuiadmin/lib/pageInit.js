
/**
 * @description: pageInit对象用于整个页面绘制和初始化的过程 | 使用localStorage，当用户关闭浏览器或点击退出按钮则失效。
 * 	lib/admin.js使用此对象
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年10月14日 上午10:32:47 
 * @version 1.0.0.1
 */
/*layui.extend(
{
	setter : 'config', 		// 配置模块
}).define([ 'setter'] , function(exports){*/
	
layui.define([ 'view'] , function(exports){	
	var pageInit = {
		page : null,
		path : layui.setter.path,
		
		systemPageInit:function(obj){
			var narr = new Array();
			var arr = obj.msfList; // 保存导航信息
			for(var i = 0 ; i < arr.length ; i ++){
				var nav = new Object();
				nav.seqnum = null;  // 排序信息
				nav.data = null;  // 导航栏详细信息
				nav.fmenus = new Array; // 一级菜单栏列表
				if(arr[i].navType == 1){
					nav.seqnum = arr[i].seqnum;
					nav.data = arr[i];
					var id = arr[i].id;
					// 开始查找 一级菜单栏
					for(var f = 0 ; f < arr.length ; f ++){
						if(arr[f].parentId == id){
							var fmenu = new Object();
							fmenu.seqnum = arr[f].seqnum;  // 排序信息
							fmenu.data = arr[f];  //  一级菜单栏详细信息
							var fid = arr[f].id;  // 一级菜单栏id
							fmenu.smenus = new Array();      // 二级菜单栏列表
							for(var s = 0 ; s < arr.length ; s ++){
								if(arr[s].parentId == fid){
									var smenu = new Object();
									smenu.seqnum = arr[s].seqnum;  // 排序信息
									smenu.data = arr[s];   //  二级菜单栏详细信息
									var sid = arr[s].id; // 二级菜单栏id
									smenu.btns = new Array();  // 二级菜单栏下的按钮集合
									for(var b = 0 ; b < arr.length ; b ++){
										if(arr[b].parentId == sid){
											var btn = new Object();
											btn.seqnum = arr[b].seqnum;
											btn.data = arr[b];
											smenu.btns.push(btn);
										}
									}
									fmenu.smenus.push(smenu);
									fmenu.smenus.sort(function(a,b){
							            return a.seqnum - b.seqnum;
						            });
								}
							}
							nav.fmenus.push(fmenu);
							nav.fmenus.sort(function(a,b){
					            return a.seqnum - b.seqnum;
				            });
						}
					}
					narr.push(nav);
					narr.sort(function(a,b){
			            return a.seqnum - b.seqnum;
		            });
				}
			}

			this.page = narr;
		},
		
		drawSystemPage : function(){
			var arr = this.page;
			var html_ = '';
			if(arr.length == 0){
				return;
			}
			
			for(var i = 0 ; i < arr.length ; i ++){
				html_ += '<li id="nav-' + arr[i].data.id + '" data-name="component" class="layui-nav-item">';
					html_ += '<a href="javascript:void(0)" lay-tips="' + arr[i].data.name + '" lay-direction="2">';
						html_ += '<i class="layui-icon layui-icon-home"></i>';
						html_ += '<cite>' + arr[i].data.name + '</cite>';
						html_ += '<span class="layui-nav-more"></span>';
					html_ += '</a>';
					html_ += '<dl class="layui-nav-child">';
						if(arr[i].fmenus.length != 0){
							var farr = arr[i].fmenus;
							for(var f = 0 ; f < farr.length ; f ++){
								html_ += '<dd data-name="grid">';
									html_ += '<a href="javascript:void(0)" style="color:#FB9337;">' + farr[f].data.name + '</a>';
									html_ += '<dl class="layui-nav-child">';
									var sarr = farr[f].smenus;
									if(sarr.length !== 0){
										for(var s = 0 ; s < sarr.length ; s ++){
											
											var url_ = this.path + sarr[s].data.funcUrl;
											var str = "";
											var btns = sarr[s].btns;
											if(btns.length != 0){
												for(var b = 0 ; b < btns.length; b ++){
													str += btns[b].data.eleValue + "," ; 
												}
												str = str.substring(0 , str.length - 1);
											}
											html_ += '<dd data-name="' + sarr[s].data.id + '">';
												html_ += '<a aid="' + sarr[s].data.id + '" lay-href="' + url_ + '" btns="' + str + '">' + sarr[s].data.name + '</a>';
											html_ += '</dd>';
										}
									}
									html_ += '</dl>';
								html_ += '</dd>';
							}
						}
					html_ += '</dl>';
				html_ += '</li>';
			}
			
			layui.$("#LAY-system-side-menu").append(html_);
			layui.$(".layui-nav-bar").attr("class", "zorder:999;");   // 动态生成左边菜单layui-nav-bar不起效果
		},
		
	}
	
	pageInit.drawPage = function(){
		var obj = JSON.parse(localStorage.pageJson);
		pageInit.systemPageInit(obj);
		pageInit.drawSystemPage();
		layui.element.render(); // 动态生成菜单后 render 左侧下导航
	},
	
	
	//对外暴露的接口
	exports('pageInit', pageInit);
});




















