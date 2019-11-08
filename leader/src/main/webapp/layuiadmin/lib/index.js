/**
 * 
 * @Name：layuiAdmin iframe版主入口 | 核心方法
 * @Author：贤心
 * @Site：http://www.layui.com/admin/
 * @License：LPPL
 */

layui.extend(
{
	admin : 'lib/admin', // 核心模块
	view : 'lib/view', 			// 视图渲染模块
}).define(['admin'] , function(exports) {
			var setter = layui.setter;
			var element = layui.element;
			var admin = layui.admin;
			var tabsPage = admin.tabsPage;
			var view = layui.view;

			// 打开标签页|tab标签如果不存在则新加，如果存在则找到那个标签，然后设置为显示
			// admin.js调用此方法
			var openTabsPage = function(url, text , othis) {
				// 遍历页签选项卡
				var matchTo;
				var tabs = $('#LAY_app_tabsheader>li');
				var path = url.replace(/(^http(s*):)|(\?[\s\S]*$)/g, '');
				var aid = othis.attr('aid');

				tabs.each(function(index) {
					var li = $(this), layid = li.attr('lay-id');
					if (layid === url) {
						matchTo = true;
						tabsPage.index = index;
					}
				});

				text = text || '新标签页';

				if (setter.pageTabs) {
					if (!matchTo) {	// 如果未在选项卡中匹配到，则追加选项卡
						layui.setter.pageBtns.set('btns-' + aid , othis.attr("btns"));  // 保存按钮标识
						localStorage.btns = othis.attr("btns");
						
						$(APP_BODY).append(['<div class="layadmin-tabsbody-item layui-show">', '<iframe src="' + url + '" frameborder="0" class="layadmin-iframe"></iframe>', '</div>' ].join(''));
						tabsPage.index = tabs.length;
						element.tabAdd(						// 调用element.js的方法				
							FILTER_TAB_TBAS, 
							{
								title : '<span>' + text + '</span>',
								id : url,
								attr : path,
								tabId : aid
							}
						);
						
					}
				} else {
					var iframe = admin.tabsBody(admin.tabsPage.index).find('.layadmin-iframe');
					iframe[0].contentWindow.location.href = url;
				}

				// 定位当前tabs
				element.tabChange(FILTER_TAB_TBAS, url);
				admin.tabsBodyChange(tabsPage.index, {
					url : url,
					text : text
				});
			}, 
			
			APP_BODY = '#LAY_app_body', 
			FILTER_TAB_TBAS = 'layadmin-layout-tabs', 
			$ = layui.$, 
			$win = $(window);

			// 初始
			if (admin.screen() < 2)
				admin.sideFlexible();

			// 将模块根路径设置为 controller 目录
			layui.config({
				base : setter.base + 'modules/'
			});

			// 扩展 lib 目录下的其它模块
			layui.each(setter.extend,
					function(index, item) {
						var mods = {};
						mods[item] = '{/}' + setter.base + 'lib/extend/' + item;
						layui.extend(mods);
					});

			view().autoRender();

			// 加载公共模块
//			layui.use('common');

			//对外输出
			exports('index', { openTabsPage : openTabsPage });
		});



















