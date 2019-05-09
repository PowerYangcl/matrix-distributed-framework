<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp"%>
<style type="text/css">
	.readme h1,h2,h3,div{
		margin-bottom:20px;
	}
	.title-2{
		margin-left: 50px;
		margin-bottom:50px;
	}
	p{
		font-size: 15px;
		/* color:#9BBB59; */
		color:black;
	}
</style>
<div class="centercontent">
	<div class="pageheader notab">
		<h1 class="pagetitle">针对JavaScript开发的代码统一规范</h1> 
	</div>
	
	<div class="subcontent" style="display: block; margin-top: 20px; margin-left: 20px">
		<div class="readme">
			<h1 >1. JavaScript代码规范</h1> 
			<div class="title-2">
				<h3 >1.1 对象封装约定</h1> 
				<img src="../images/readme/javascript-a.png" title="javascript-a.png" style="margin-bottom:20px;"></br>
				<div>
					<p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp在处理页面的js脚本，约定使用JavaScript对象封装，禁止在JSP页面中乱写脚本，针对特定的业务逻辑要求开发人员对其进行统一封装。</p>
					<p>将使用的局部变量封装在对象内，方便复用、增强代码的可维护性、易读性、复用性。</p>
					<p style="color:red;font-size:25px;margin-top:10px;">注意！</p>
					<p style="margin-top:10px;">&nbsp&nbsp&nbsp&nbsp禁止使用全局变量！！</p>
					<p style="margin-top:5px;">&nbsp&nbsp&nbsp&nbsp最大限度减少在JSP页面中暴露JS脚本！！</p>
				</div>
			</div>
			
			
			<div class="title-2">
				<h3 >1.2 Html标签的 id|class|name 的使用说明</h1> 
				<p>id与name：</p>
				<img src="../images/readme/javascript-b.png" title="javascript-b.png" style="margin-bottom:20px;"></br>
				<p>class的使用：</p>
				<img src="../images/readme/javascript-c.png" title="javascript-c.png" style="margin-bottom:20px;"></br>
				<div>
					<p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp在Html元素的定义中，id代表唯一性标识，与数据库的id很相似，一般不会重复。</p>
					<p>name属性则用于序列化form表单中的输入框类型的标签，比如checkbox、radio、input、select、textarea、select这几种。你几乎看不到div、span、p这样的标签有name属性</p>
					<p>举个id与name结合使用的列子：$("#the-form-id").serializeArray()</p>
					<p>form标签中包含了需要输入的文本框(如：input)；serializeArray()方法则会遍历出所有带有name属性的html标签，并形成一个对象数组，具体请百度一下。</p>
					<p></p>
					<p style="margin-top:10px;">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp class则代表一类有规律的标签，他们可能有相同的样式或相同的行为属性。</p>
					<p>比如在动态追加div标签的时候，就需要使用class 而不是 id</p>
				</div>
			</div>
			
		</div> 
		
		
		
		
		
		
		
		
	</div>
	
	<div class="subcontent" style="display: block; margin-top: 2000px; margin-left: 20px"></div>
</div>












