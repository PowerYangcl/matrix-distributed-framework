	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <title>能量矩阵后台</title>

    <link rel="stylesheet" href="${css}/style.default.css" type="text/css" />

    <!--[if IE 9]>
    <link rel="stylesheet" media="screen" href="${css}/style.ie9.css"/>
    <![endif]-->
    <!--[if IE 8]>
    <link rel="stylesheet" media="screen" href="${css}/style.ie8.css"/>
    <![endif]-->
    <!--[if lt IE 9]>
    <script src="${js}/plugins/css3-mediaqueries.js"></script>
    <![endif]-->

	<style type="text/css">
		.page-list tr:hover{
		  background:#B8DABD;
		}
	</style>
	
	
    <script type="text/javascript" src="${js}/jquery-1.10.2.js"></script> 
	<script type="text/javascript" src="${js}/plugins/jquery-ui.js"></script>
	<script type="text/javascript" src="${js}/plugins/colorpicker.js" ></script>
	<script type="text/javascript" src="${js}/plugins/jquery.alerts.js" ></script>
	
	<!-- 自定义滚动条 -->
	<script type="text/javascript" src="${js}/plugins/jquery.slimscroll-1.3.8.js"></script>
	
	<script type="text/javascript" src="${js}/datePicker/WdatePicker.js" ></script>
	
	<!-- ajax相关方法 jsonp等 -->
    <script type="text/javascript" src="${js}/utils/ajaxs.js"></script> 
    <script type="text/javascript" src="${js}/system/general.js"></script>
    <!-- 退出相关方法 -->
    <script type="text/javascript" src="${js}/system/login.js"></script> 
    <script type="text/javascript" src="${js}/system/pageInit.js"></script>  
    
    <!-- 主要使用dform对象，针对弹层分页 -->
    <script type="text/javascript" src="${js}/system/ajax-form-dialog.js"></script>
    <script type="text/javascript" src="${js}/blockUI/jquery.blockUI.js" ></script>
    
    <!-- 原则上讲 ztree组件应该放到对应的每个jsp页面中，但如果出现BlockUI弹窗的情况则需要使用这里的资源，因为这里是父层窗体 -->
    <link rel="stylesheet" href="${css}/ztree/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" src="${js}/ztree/jquery.ztree.all.js"></script>


    <!-- 
    	这个页面引入到所html的head标签中 这里控制了浏览器自适应以及整个系统页面的全局样式。
    	注：此页面禁止改动。- Yangcl 
    -->
    
    <!-- 准备实例化权限 -->
    <script type="text/javascript">
		$(function(){
			if(localStorage.pageJson != undefined && localStorage.pageJson != ""){
				var page = JSON.parse(localStorage.pageJson); 
				pageInit.init(page , "${basePath}");
				var userInfo = JSON.parse(localStorage.userInfo);
				var pageCss = userInfo.pageCss;
				if(pageCss != '' && pageCss != 'default'){
					$("#addonstyle").remove();
					$('head').append('<link id="addonstyle" rel="stylesheet" href="${css}/custom/style.' + pageCss + '.css" type="text/css" />');
				}
				// 右上角系统用户简单信息 
				$("#simple-user-name").text('后台用户: ' + userInfo.userName);
				$("#user-name").text(userInfo.userName);
				$("#user-email").text(userInfo.email);
				
				$("#" + localStorage.left_select ).addClass("current");
				$('.vernav2 > ul > li > ul > li').click("onclick" , function(){
                	$('.vernav2 > ul > li > ul > li').removeClass("current");
                    localStorage.left_select = this.id
                    $("#" + this.id ).addClass("current");
                });
			}
			
			 // 修改主题 
		    $('.changetheme a').click(function () {
		    	var ref = '${css}';
		        var c = $(this).attr('class');
		        if ($('#addonstyle').length == 0) {
		            if(c != 'default'){
		                $('head').append('<link id="addonstyle" rel="stylesheet" href="' + ref + '/custom/style.' + c + '.css" type="text/css" />'); 
		            }
		        }else{
		            if (c != 'default') {
		                $('#addonstyle').attr('href', ref + '/custom/style.' + c + '.css'); 
		            } else {
		                $('#addonstyle').remove(); 
		            }
		        }
		        var userInfo = JSON.parse(localStorage.userInfo);
		        var userId = userInfo.id;
		        userInfo.pageCss = c;
		        localStorage.userInfo = JSON.stringify(userInfo); 
		        
		        var url_ = '${basePath}manager/update_page_style.do';
		        var data_ = {
		        	id:userId, 
		        	pageCss:c
		        };
		        ajaxs.sendAjax('post' , url_ , data_);
		    });
			 
		    // 显示和隐藏左侧百叶窗菜单 
		    $('.vernav2 > ul li a').each(function () {
		        var url = $(this).attr('href');
		        if(url.indexOf('#') != 0){  // 修正 - Yangcl
		        	return;
		        }
		        $(this).click(function () {
		            if ($(url).length > 0) {
		                if ($(url).is(':visible')) {      // 左侧菜单最小化会有 .menucoll2 样式类
		                    if (!$(this).parents('div').hasClass('menucoll2'))
		                        $(url).slideUp();
		                } else {
		                    $('.vernav2 ul ul').each(function () {
		                        $(this).slideUp();
		                    });      
		                    if (!$(this).parents('div').hasClass('menucoll2'))
		                        $(url).slideDown();
		                }
		                return false;
		            }
		        });
		    });
		    
		    // 隐藏和显示左侧菜单   此功能暂时不使用
		    /* $('.togglemenu').click(function () {
		        if (!$(this).hasClass('togglemenu_collapsed')) {
		            if ($('.vernav').length > 0) {
		                if ($('.vernav').hasClass('iconmenu')) {
		                    $('body').addClass('withmenucoll');
		                    $('.iconmenu').addClass('menucoll');
		                } else {
		                    $('body').addClass('withmenucoll');
		                    $('.vernav').addClass('menucoll').find('ul').hide();
		                }
		            } else if ($('.vernav2').length > 0) {
		                $('body').addClass('withmenucoll2');
		                $('.iconmenu').addClass('menucoll2');
		            }
		            $(this).addClass('togglemenu_collapsed');
		            $('.iconmenu > ul > li > a').each(function () {
		                var label = $(this).text();
		                $('<li><span>' + label + '</span></li>').insertBefore($(this).parent().find('ul li:first-child'));
		            });
		        } else {
		            if ($('.vernav').length > 0) {
		                if ($('.vernav').hasClass('iconmenu')) {
		                    $('body').removeClass('withmenucoll');
		                    $('.iconmenu').removeClass('menucoll');
		                } else {
		                    $('body').removeClass('withmenucoll');
		                    $('.vernav').removeClass('menucoll').find('ul').show();
		                }
		            } else if ($('.vernav2').length > 0) {
		                $('body').removeClass('withmenucoll2');
		                $('.iconmenu').removeClass('menucoll2');
		            }
		            $(this).removeClass('togglemenu_collapsed');
		            $('.iconmenu ul ul li:first-child').remove();
		        }
		    }); */
		});  

    </script>
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    