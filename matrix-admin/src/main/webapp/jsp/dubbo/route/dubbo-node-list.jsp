<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/inc/iframe-head.jsp" %>


<script type="text/javascript" src="${jsp}/dubbo/route/dubbo-node-list.js"></script>
<script type="text/javascript">
	$(function() {
		var type_ = 'post';
		var url_ = '${basePath}application/ajax_dubbo_node_list.do';
		var data_ = null; // 可以为null，后台会进行默认处理
		dubboNodeList.init('${basePath}').draw(ajaxs.sendAjax(type_, url_, data_));
	});
</script>

<div class="centercontent tables">
	<!--这个跳转页面的功能及跳转路径等等-->
	<div class="pageheader notab">
		<h1 class="pagetitle">Dubbo应用节点路由</h1>
		<span class="pagedesc" style="display:none">示例页面：jsp/dubbo/route/dubbo-node-list.jsp</span>
	</div>

	<div id="contentwrapper" class="contentwrapper">

		<%-- table-form 这个id分页使用 --%>
		<div id="table-form" class="dataTables_wrapper">
			<div class="contenttitle2">
				<p style="margin: 0px">
					<label>项目名称：</label> 
					<span class="field"> 
						<input id="application" type="text" name="application" class="form-search" />
					</span> 
					
					<label>&nbsp&nbsp&nbsp&nbsp</label> 
					
					<label>拥有者：</label> 
					<span class="field"> 
						<input id="username" type="text" name="username" class="form-search" />
					</span>
					
					<label>&nbsp&nbsp&nbsp&nbsp</label>
					
					<label>类型：</label> 
					<span class="field"> 
						<select id="type" name="type" class="form-search">
							<option value="">所有---</option>
							<option value="1">生产者</option>
							<option value="2">消费者</option>
							<option value="3">生产者-消费者</option>
						</select>
					</span> 
					
					<a onclick="dubboNodeList.openExecuteDialog()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 10px"> 
						<span> 执行 </span>
					</a>
					<a onclick="dubboNodeList.openBatchOperateDialog()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 10px"> 
						<span> 批处理 </span>
					</a> 
					<a onclick="dubboNodeList.searchReset()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 10px"> 
						<span> 重 置 </span>
					</a> 
					<a onclick="dubboNodeList.search()" class="btn btn_orange btn_search radius50 " style="float: right; cursor: pointer; margin-left: 20px"> 
						<span> 查 询 </span>
					</a>
				</p>
			</div>

			<table id="dyntable2" cellpadding="0" cellspacing="0" border="0" class="stdtable">
				<thead>
					<tr>
						<th class="head0 " width="30px">
							<input id="check-all" type="checkbox"  onclick="dubboNodeList.selectAll()"/>
						</th>
						<th class="head0 " >项目名称</th> 
						<th class="head0 " width="150px">拥有者</th> 
						<th class="head0 " width="100px">类型</th> 
						<th class="head0 " width="100px">部署节点</th> 
						<th class="head0 " width="400px">操作</th>
					</tr>
				</thead>
				<tbody id="ajax-tbody-page" class="page-list">
					<%-- 等待填充 --%>
				</tbody>
			</table>

		</div>
	</div>

</div>


<!-- 操作一级缓存弹窗|单独一条记录 -> 带自定义滚动条 -->
<div id="find-dialog-div" class="dialog-page-div" style="display: none;width: 900px;height: 600px">
	<p class="dialog-title">
		<a href="javascript:void(0)" onclick="closeDialog()" class="dialog-close"></a>
		<span>
			高级系统功能，有可能引起系统崩溃 ! 
		</span>
	</p>
	<div class="contentwrapper">
		<div class="last">
			<div class="widgetbox" style="height: inherit">
				<div  class="title">
					<h3 style="text-transform: none;">
						项目：<span id="find-project" style="color:red"></span>
					</h3>
				</div>
				<div class="widgetcontent">
					<div id="find-interface-list" class="mousescroll">
						<!-- 等待填充要展示的内容，如果超出div的高度则会展示出自定义的滚动条 -->
						<ul class="entrylist">	<!-- entrylist 是一个系统css类 -->
							<li>
								<form id="find-dialog-table">
									<table class="dialog-table" style="width:100%;border-collapse:separate; border-spacing:0px 12px;">
						                <tbody>
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			缓存名称：
						                		</td>
						                		<td style="text-align: left">
						                			<input type="text" id="find-cache-name" name="cacheName" class="dialog-form-input" style="width:90%;" placeholder="必填项，不得为空"  maxlength="20">
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			缓存主键：
						                		</td>
						                		<td style="text-align: left">
						                			<input type="text" name="key" class="dialog-form-input" style="width:90%;" placeholder="例如：matrix-core.owner"  maxlength="140">
						                			<input type="hidden" id="find-dubbo-addr" name="dubboAddr" value="">
						                		</td>
						                	</tr>
						                </tbody>
						            </table>
									<textarea id="find-result" name="value" rows="20" cols="450" style="width:800px"></textarea>
					            </form>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<form class="stdform" action="javascript:void(0)">
			<table class="dialog-table" style="width:400px;border-collapse:separate;float:right">
                <tbody>
                	<tr>
                		<td style="text-align: right;">
                			<button onclick="subpage.dubboNodeList.routeFind()" type="button" style="width: 100px;" class="submit radius2">查询</button>
                		</td>
                		<td style="text-align: right;">
                			<button onclick="subpage.dubboNodeList.dialogReset()" type="button" style="width: 100px;" class="submit radius2">清空</button>
                		</td>
                		<td style="text-align: right;">
                			<button onclick="subpage.dubboNodeList.routeAdd('find')" type="button" style="width: 100px;" class="submit radius2">添加</button>
                		</td>
                		<td style="text-align: right;">
                			<button onclick="subpage.dubboNodeList.routeUpdate('find')" type="button" style="width: 100px;" class="submit radius2">修改</button>
                		</td>
                		<td style="text-align: right">
                			<button onclick="subpage.dubboNodeList.routeRemove('find')" type="button" style="width: 100px;" class="submit radius2">删除</button>
                		</td>
                	</tr>
                </tbody>
            </table>
		</form>
	</div>
</div>


<!-- 操作一级缓存弹窗|批量操作多条记录 -> 带自定义滚动条 -->
<div id="batch-dialog-div" class="dialog-page-div" style="display: none;width: 900px;height: 600px">
	<p class="dialog-title">
		<a href="javascript:void(0)" onclick="closeDialog()" class="dialog-close"></a>
		<span>
			警告! 您正在尝试批量操作系统一级缓存! 
		</span>
	</p>
	<div class="contentwrapper">
		<div class="last">
			<div class="widgetbox" style="height: inherit">
				<div  class="title">
					<h3 style="text-transform: none;">
						处理项目数量：<span id="batch-project" style="color:red"></span> 个
					</h3>
				</div>
				<div class="widgetcontent">
					<div id="batch-interface-list" class="mousescroll">
						<!-- 等待填充要展示的内容，如果超出div的高度则会展示出自定义的滚动条 -->
						<ul class="entrylist">	<!-- entrylist 是一个系统css类 -->
							<li>
								<form id="batch-dialog-table">
									<table class="dialog-table" style="width:100%;border-collapse:separate; border-spacing:0px 12px;">
						                <tbody>
						                	<tr>
						                		<td style="text-align: left;width:80px;">
						                			缓存名称：
						                		</td>
						                		<td style="text-align: left">
						                			<input type="text" id="batch-cache-name" name="cacheName" class="dialog-form-input" style="width:90%;" placeholder="必填项，不得为空"  maxlength="20">
						                		</td>
						                		<td style="text-align: left;width:80px;">
						                			缓存主键：
						                		</td>
						                		<td style="text-align: left">
						                			<input type="text" name="key" class="dialog-form-input" style="width:90%;" placeholder="例如：matrix-core.owner"  maxlength="140">
						                			<input type="hidden" id="batch-dubbo-addr" name="dubboAddr" value="">
						                		</td>
						                	</tr>
						                </tbody>
						            </table>
									<textarea id="batch-result" name="value" rows="20" cols="450" style="width:800px"></textarea>
					            </form>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<form class="stdform" action="javascript:void(0)">
			<table class="dialog-table" style="width:400px;border-collapse:separate;float:right">
                <tbody>
                	<tr>
                		<td style="text-align: right;">
                			<button onclick="subpage.dubboNodeList.routeFindAll()" type="button" style="width: 100px;" class="submit radius2">查询</button>
                		</td>
                		<td style="text-align: right;">
                			<button onclick="subpage.dubboNodeList.dialogReset()" type="button" style="width: 100px;" class="submit radius2">清空</button>
                		</td>
                		<td style="text-align: right;">
                			<button onclick="subpage.dubboNodeList.routeAdd('batch')" type="button" style="width: 100px;" class="submit radius2">添加</button>
                		</td>
                		<td style="text-align: right;">
                			<button onclick="subpage.dubboNodeList.routeUpdate('batch')" type="button" style="width: 100px;" class="submit radius2">修改</button>
                		</td>
                		<td style="text-align: right">
                			<button onclick="subpage.dubboNodeList.routeRemove('batch')" type="button" style="width: 100px;" class="submit radius2">删除</button>
                		</td>
                	</tr>
                </tbody>
            </table>
		</form>
	</div>
</div>


<!-- 执行系统内置命令|批量操作多条记录 -> 带自定义滚动条 -->
<div id="execute-dialog-div" class="dialog-page-div" style="display: none;width: 900px;height: 600px">
	<p class="dialog-title">
		<a href="javascript:void(0)" onclick="closeDialog()" class="dialog-close"></a>
		<span>
			警告! 您正在尝试执行系统内置命令! 
		</span>
	</p>
	<div class="contentwrapper">
		<div class="last">
			<div class="widgetbox" style="height: inherit">
				<div  class="title">
					<h3 style="text-transform: none;">
						处理项目数量：<span id="execute-project" style="color:red"></span> 个
					</h3>
				</div>
				<div class="widgetcontent">
					<div id="execute-interface-list" class="mousescroll">
						<!-- 等待填充要展示的内容，如果超出div的高度则会展示出自定义的滚动条 -->
						<ul class="entrylist">	<!-- entrylist 是一个系统css类 -->
							<li>
								<form id="execute-dialog-table">
									<table class="dialog-table" style="width:100%;border-collapse:separate; border-spacing:0px 12px;">
						                <tbody>
						                	<tr>
						                		<td style="text-align: left;width:120px;">
						                			系统内置命令：
						                		</td>
						                		<td style="text-align: left">
						                			<select id="execute-key" name="key" class="dialog-form-input" style="width:50%;" onchange="subpage.dubboNodeList.selectExecuteKey(this)">
														<option value="">----请选择----</option>
														<option value="guard_data_base" paramters=''>data-base-guard</option>
														<option value="guard_file" paramters='{"type":"read/write/list/del","file":"//->根目录 or a real path in linux","content":"写入的文本","flag":"true->只显示第一层"}'>file-guard</option>
														<option value="guard_system_org" paramters=''>system-org-guard</option>
														<option value="guard_shell"  paramters='{"cmd":"sh","args":"-c,"}'>shell-guard</option>
														<option value="guard_logger" paramters='{"line":"300","file":"stdout.log"}'>logger-guard</option>
														<option value="guard_syringe" paramters=''>syringe-guard</option>
														<option value="guard_job_exec" paramters=''>job-exec-guard</option>
													</select>
						                			<input type="hidden" id="execute-dubbo-addr" name="dubboAddr" value="">
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:120px;">
						                			命令相关参数：
						                		</td>
						                		<td style="text-align: left"></td>
						                	</tr>
						                	<tr>
						                		<td style="text-align: left" colspan="2">
						                			<textarea id="execute-value" name="value" rows="5" cols="450" style="width:800px"></textarea>
						                		</td>
						                	</tr>
						                	
						                	<tr>
						                		<td style="text-align: left;width:120px;">
						                			命令执行结果：
						                		</td>
						                		<td style="text-align: left"></td>
						                	</tr>
						                	<tr>
						                		<td style="text-align: left" colspan="2">
						                			<textarea id="execute-result" rows="10" cols="450" style="width:800px"></textarea>
						                		</td>
						                	</tr>
						                </tbody>
						            </table>
					            </form>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<form class="stdform" action="javascript:void(0)">
			<table class="dialog-table" style="width:400px;border-collapse:separate;float:right">
                <tbody>
                	<tr>
                		<td style="text-align: right;">
                			<button onclick="subpage.dubboNodeList.routeExecute()" type="button" style="width: 100px;" class="submit radius2">执行</button>
                		</td>
                	</tr>
                </tbody>
            </table>
		</form>
	</div>
</div>















