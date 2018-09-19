<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<div class="topheader">
            <div class="left">
                <span class="slogan" style="border-left:0px;font-size: 28px">系统菜单与权限配置</span>
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
                    <span>Juan Dela Cruz</span>
                </div>
                <!--用户详细信息弹窗-->
                <div class="userinfodrop">
                    <div class="avatar">
                        <a href="">
                            <img src="${img}/thumbs/avatarbig.png" alt="用户头像" />  <!--用户头像-->
                        </a>
                        <div class="changetheme">
                            Change theme: <br />
                            <a class="default"></a>
                            <a class="blueline"></a>
                            <a class="greenline"></a>
                            <a class="contrast"></a>
                            <a class="custombg"></a>
                        </div>
                    </div><!--avatar-->
                    <!--登录用户信息-->
                    <div class="userdata">
                        <h4>Juan Dela Cruz</h4>
                        <span class="email">youremail@yourdomain.com</span>
                        <ul>
                            <li>
                                <a href="">修改资料</a>
                            </li>
                            <li>
                                <%-- TODO 非开发权限人员此处需要隐藏！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！--%>
                                <a href="${basePath}sysrole/tree_page_index.do">功能树维护</a>
                            </li>
                            <li>
                                <a href="">帮助</a>
                            </li>
                            <li>
                                <a href="">退出</a>
                            </li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>





























