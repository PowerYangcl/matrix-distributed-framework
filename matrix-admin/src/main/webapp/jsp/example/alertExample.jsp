<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript">
	/**           使用说明！！！！
	 * 如果包含在 form表单内，需要将action="javascript:void(0)"，否则自定义的alert会跳转  
	 * <form id="tree-node-edit"  action="javascript:void(0)"> </form>
	 *
	 */
	$(function() {
		$('#alert-box-button').click(function() {
			malert('自定义 alert 展示', '系统提示');
			return false;
		});
		/*
		 * 针对malert函数，他支持一个回调方法，也可以像下面这样使用：
		 		malert(obj.msg, '系统提示' , function(){
					window.location.href = '${basePath}/manager/sys_user_list_page.do'  
				});
		 * 此时会走这个回调函数 
		 */

		$('#confirm-button').click(function() {
			// 注意：flag = ture or false - Yangcl
			mconfirm('Can you confirm this?', '系统提示', function(flag) {
				malert('您选择了: ' + flag, '系统提示');
			});
			return false;
		});

		$('#prompt-button').click(function() {
			// 注意：content = 你输入的内容  如果content == null 则代表你点击了取消按钮，请根据实际需要做判定 - Yangcl
			mprompt('请输入：', '', '系统提示', function(content) {
				if (content) {
					alert('您输入了：' + content);
				}
			});
			return false;
		});

		$('#alert-html-button').click(function() {
			var html_ = '<a href="${basePath}example/page_example_ajax_form_dialog.do" target="_blank" class="anchorbutton">Ajax 分页+弹出窗体分页 示例</a>';
			var title = 'alert 支持html标签';
			malert(html_, title);
			return false;
		});

	})
</script>

<div class="centercontent tables">
	<!--这个跳转页面的功能及跳转路径等等-->
	<div class="pageheader notab">
		<h1 class="pagetitle">自定义封装 alert confirm prompt 示例</h1>
		<!-- <span class="pagedesc">
                  这个页面描述了系统中最常用的功能：自定义提示框。
              </span> -->
		<span style="display: none">jsp/example/alertExample.jsp</span>
	</div>

	<div id="contentwrapper" class="contentwrapper">
		<div id="dyntable2_wrapper" class="dataTables_wrapper">
			<div class="contenttitle2">
				<h3>请选择你的用例 本页面包含JavaScript调用方式</h3>
			</div>
			<div class="subcontent" style="display:block; margin-top: 100px; margin-left: 20px">
				<a id="alert-box-button" href="javascript:void(0)" class="anchorbutton security-btn" key="custom_dialog_example:alert" style="display: none;">基本 Alert</a> &nbsp; 
				<a id="confirm-button" href="javascript:void(0)" class="anchorbutton security-btn" key="custom_dialog_example:confirm" style="display: none;">确认对话框 confirm</a> &nbsp; 
				<a id="prompt-button" href="javascript:void(0)" class="anchorbutton security-btn" key="custom_dialog_example:prompt" style="display: none;">输入对话框 prompt</a> &nbsp; 
				<a id="alert-html-button" href="javascript:void(0)" class="anchorbutton security-btn" key="custom_dialog_example:html" style="display: none;">alert 支持html标签</a>
			</div>
		</div>
	</div>

</div>













