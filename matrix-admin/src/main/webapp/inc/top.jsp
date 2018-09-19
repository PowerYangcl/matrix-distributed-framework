<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<div class="topheader">
            <div class="left">
            	<h1 class="logo"><a id="menu-open-close" style="cursor:pointer;" >矩阵管理系统</a></h1>
                <!-- <h1 class="logo" style="display:none"><a id="menu-open-close" style="cursor:pointer;" >Power.</a><span>Matrix</span></h1> -->
                <span class="slogan"  style="display:none">矩阵后台管理系统</span>
                <br clear="all" />

            </div><!--left-->

            <div class="right">
                <!--用户消息提示 可以隐藏 弹层包括class='noticontent'的元素 general.js 进行添加-->
                <!--<div class="notification">-->
                    <!--<a class="count" href="ajax/notifications.html"><span>9</span></a>-->
                <!--</div>-->
                
                <!--用户摘要信息-->
                <div class="userinfo">
                    <img src="${img}/thumbs/avatar.png" alt="" />
                    <span id="simple-user-name"><!-- Juan Dela Cruz --></span>
                </div>
                <!--用户详细信息弹窗-->
                <div class="userinfodrop">
                    <div class="avatar">
                        <a href="">
                            <img src="${img}/thumbs/avatarbig.png" alt="用户头像" />  <!--用户头像-->
                        </a>
                        <div class="changetheme">
                            更换主题: <br />
                            <a class="default"></a>
                            <a class="blueline"></a>
                            <a class="greenline"></a>
                            <a class="contrast"></a>
                            <!-- <a class="custombg"></a> -->
                        </div>
                    </div><!--avatar-->
                    <!--登录用户信息-->
                    <div class="userdata">
                        <h4 id="user-name"><!-- Juan Dela Cruz --></h4></br>
                        <span id="user-email" class="email"><!-- youremail@yourdomain.com --></span>
                        <ul>
                            <li>
                                <a href="javascript:void(0)">修改资料</a>
                            </li>
                            <li id="li-reload-cache">
                                <a href="javascript:void(0)" >刷新缓存</a>
                            </li>
                            <li>
                                <a href="javascript:void(0)">帮助</a> 
                            </li>
                            <li>
                                <a href="javascript:void(0)" onclick="login.logout('${basePath}')">退出</a> 
                            </li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>

        <div class="header">
            <ul id="nav-list" class="headermenu">
                <!-- 等待填充 -->
            </ul>
        </div>	




























