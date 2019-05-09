<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <title>能量矩阵后台</title>

    <link rel="stylesheet" href="css/style.default.css" type="text/css" /> 
    <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="js/plugins/jquery.alerts.js" ></script>
    <!--页面JavaScript脚本-->
    <script type="text/javascript" src="js/system/login.js"></script>
    <script type="text/javascript" src="js/utils/ajaxs.js"></script>
    
    <script type="text/javascript">
    	$(function(){
    		if(window !=top){
    			top.location.href=location.href;
    		}
    	})
    </script>

</head>

<body class="loginpage">
	<div class="loginbox">
    	<div class="loginboxinner">
        	
            <div class="logo">
            	<h1 class="logo"><span>Power Matrix</span></h1>
				<span style="color:white;font-size: 25px;font-family:SimSun;margin-top:10px">后台管理系统</span>
            </div>
            
            <br clear="all" /><br />
            
            <div class="nousername">
				<div class="loginmsg">密码不正确.</div>
            </div>
            
            <div class="nopassword">
				<div class="loginmsg">密码不正确.</div>
                <div class="loginf">
                    <div class="thumb"><img alt="" src="images/thumbs/avatar1.png" /></div>
                    <div class="userlogged">
                        <h4></h4>
                        <a href="index.html">Not <span></span>?</a> 
                    </div>
                </div>
            </div>
            
            <form id="form-login" action="javascript:void(0)">
                <div class="username">
                	<div class="usernameinner">
                    	<input type="text" name="userName" id="username" maxlength="20" value=""/>
                    </div>
                </div>
                <div class="password">
                	<div class="passwordinner">
                    	<input type="password" name="password" id="password" maxlength="20" value=""/>
                    </div>
                </div>
                <input id="page-login" type="button" class="button-login" value="登录" onclick="login.login('form-login')"/>
                
                <div class="keep"><input type="checkbox" /> 记住密码</div>
            </form>
            
        </div>
    </div>

</body>
</html>






















































