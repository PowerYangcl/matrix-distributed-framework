
/**
 * pageInit对象用于整个页面绘制和初始化的过程
 * 使用localStorage，当用户关闭浏览器或点击退出按钮则失效。
 */
var pageInit = {

	page:null,
	path:null,

	init:function(obj , path_){
		this.path = path_;
		pageInit.pageInit(obj);
		pageInit.drawNavList();
		pageInit.drawMenuList();

		if(localStorage.nav_id != undefined){
			$("#" + localStorage.nav_id).addClass("current");
			pageInit.leftCheck(); 
		}else{
			$($("#nav-list li")[0]).addClass("current");  // 登陆进入则默认加载第一个导航
			$($("#left-menu>div")[0]).show();
		}
	},
	
	pageInit:function(obj){
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
	
	/**
	 * 初始化导航栏，在top.jsp中
	 */
	drawNavList:function(){
		var arr = this.page;
		var html_ = '';
		if(arr.length == 0){
			return;
		}
		for(var i = 0 ; i < arr.length ; i ++){
			html_ += '<li id="nav-' + arr[i].data.id + '" onclick="pageInit.navChange(this)" style="display:block; width:200px">';
				html_ += '<a href="javascript:void(0)">' + arr[i].data.name + '</a>';
			html_ += '</li>';
		}

		$("#nav-list").append(html_);
	},
		
	/**
	 * 初始化菜单栏，在left.jsp中
	 */
	drawMenuList:function(){
		var arr = this.page;
		var html_ = '';
		if(arr.length == 0){
			return;
		}
		var path_ = this.path ;    
		for(var i = 0 ; i < arr.length ; i ++){
			html_ += '<div id="f-menu-' + arr[i].data.id + '" class="vernav2 iconmenu nav menu-left" style="display: none">';
				html_ += '<ul class="nav-bar-ul">';
					if(arr[i].fmenus.length == 0){
						continue;
					}
					var farr = arr[i].fmenus;
					for(var f = 0 ; f < farr.length ; f ++){
						html_ += '<li class="current">';
							html_ += '<a href="#' + farr[f].data.styleKey + '" class="' + farr[f].data.styleClass + '">' + farr[f].data.name + '</a>';
							html_ += '<span class="arrow"></span>';
							html_ += '<ul id="' + farr[f].data.styleKey + '">';
								var sarr = farr[f].smenus;
								if(sarr.length == 0){
									continue;
								}
								for(var s = 0 ; s < sarr.length ; s ++){
									var url_ = path_ + sarr[s].data.funcUrl;
									var str = "";
									var btns = sarr[s].btns;
									if(btns.length != 0){
										for(var b = 0 ; b < btns.length; b ++){
											str += btns[b].data.btnArea + "@" + btns[b].data.eleValue + "," ; 
										}
										str = str.substring(0 , str.length - 1);
									}
									html_ += '<li id="' + farr[f].data.styleKey + '-' + sarr[s].data.id + '">';
										html_ += '<a href="javascript:void(0)" onclick="pageInit.menuOnclick(this)" btns="' + str + '" target_="' + url_ + '" >' + sarr[s].data.name + '</a>';
									html_ += '</li>';
								}
							html_ += '</ul>';
						html_ += '</li>';
					}
				html_ += '</ul>';
				html_ += '<a class="togglemenu"></a>';
				html_ += '<br />';
				html_ += '<br />';
			html_ += '</div>';
		}
		$("#left-menu").append(html_);
	},
	
	// 显示被隐藏的按钮| pageInit.security();   display:none;
	security:function(){
		if( typeof(localStorage.btns) != "undefined" && localStorage.btns.length != 0){
			var barr = localStorage.btns.split(",");
			for(var i = 0 ; i < barr.length ; i ++){
				var key = barr[i].split("@")[1];
				$(".security-btn[key='" + key + "']").show();
				$(".security-btn[key='" + key + "']").removeClass("security-btn"); 
			}
			$(".security-btn").remove();
		}
	},

	/**
	 * 导航栏切换
	 * @param obj
	 */
	navChange:function(obj){
		$("#nav-list li").removeClass("current");
		$(obj).addClass("current");
		localStorage.nav_id = $(obj)[0].id;
		pageInit.leftCheck();
		$('.vernav2 > ul > li > ul > li').removeClass("current");  // 移除二级菜单已选颜色
	},

	/**
	 * 显示和隐藏左侧菜单栏
	 */
	leftCheck:function(){
		$(".menu-left").hide();
		var mid = 'f-menu-' + localStorage.nav_id.split("-")[1];
		$("#" + mid).show();
	},

	/**
	 * 二级菜单 单击事件
	 * @param obj
	 */
	menuOnclick:function(obj){ 
		var href_ = $(obj).attr("target_"); 
		localStorage.btns = $(obj).attr("btns");  
    	$("#sub-page").attr("src" , href_); 
	}

}













