<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <div id="nav-bar-1" class="vernav2 iconmenu nav">
            <ul class="nav-bar-ul">
                <!-- 此处应放入到系统维护导航栏中-->
                 <li class="current">
                    <a href="#example" class="inbox">系统功能主干控制</a>
                    <span class="arrow"></span>
                    <ul id="example">
                        <li class="current">
                        	<!-- 必须添加 $ { basePath } -->
                        	<a href="${basePath}sysrole/tree_page_index.do">导航与菜单维护</a>
                        </li>
                        
                        <li class="gallery">
                        	<a href="${basePath}example/ajaxFormExample.do">返回主页</a>
                        </li>
                    </ul>
                </li>

            <a class="togglemenu"></a>

            <br /><br />
        </div>
 

















