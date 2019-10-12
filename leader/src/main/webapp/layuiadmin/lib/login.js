
layui.extend(
{
	setter : 'config', 		// 配置模块
}).define([ 'setter'] , function(exports) {
			var setter = layui.setter;
			
			$ = layui.$, 
			$win = $(window);

			// 将模块根路径设置为 controller 目录
			layui.config({
				base : setter.base + 'modules/'
			});
			//对外输出
			exports('login', {});
		});



















