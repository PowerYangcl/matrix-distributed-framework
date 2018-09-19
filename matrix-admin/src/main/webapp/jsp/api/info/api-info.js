/**
 * api信息树相关功能
 */
var apiInfo = {
		path:null,
		currentNode:null,
		zTree:null ,  
		
		/**
		 * 实例化对象
		 */
		launch:function(path_){
			apiInfo.path = path_ + 'apicenter/';              
			return apiInfo;
		},

        /**
         * 初始化树
         */
		apiTreeInit: function(){
        	$("#api-tree li").remove();
        	$($("#tree-node-edit")[0].childNodes).remove();
        	var type_ = 'post';
            var url_ = apiInfo.path + 'ajax_api_info_list.do'; 
            var data_ = null;   
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));  
            if(obj.status == 'success'){
                var zNodes = obj.list;
                $.fn.zTree.init($("#api-tree") , setting_ , zNodes);
                apiInfo.zTree = $.fn.zTree.getZTreeObj("api-tree");
                $("#callbackTrigger").bind("change", {}, setting_.setTrigger);
            }
        }, 

        // 允许移动到目标节点前面 即可以将同层最后一个节点放到同层的第一个。
        dropPrev:function(treeId, nodes, targetNode) {
            if(nodes[0].parentId == targetNode.parentId){  // 只允许同层节点之间进行拖拽
            	return true;
            }else{
            	return false;
            }
        },
        
        // 设置是否允许移动到同层节点的最后一个节点的后面 从而使被移动的节点成为最后一个节点
        dropNext:function(treeId, nodes, targetNode) {
            if(nodes[0].parentId == targetNode.parentId){  // 只允许同层节点之间进行拖拽
            	return true;
            }else{
            	return false;
            } 
        },
        
        // 拖拽到目标节点时 设置是否允许成为目标节点的子节点。
        dropInner:function(treeId, nodes, targetNode) {
            return false;
        },

        // 如果是root节点 禁止显示删除按钮
        showRemoveBtn:function(treeId, treeNode){
            if(treeNode.level < 2){
                return false;
            }else{
                return true;
            }
        },

        // 显示添加按钮 以及添加操作
        addHoverDom:function(treeId, treeNode) {
        	if(treeNode.level != 1){   
        		return false
        	}
            var newCount = 1;
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0)
                return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加一个节点' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_"+treeNode.tId);
            if (btn) {
                btn.bind("click", function(){
                    var zTree = apiInfo.zTree;
                    var new_ = { // 新建一个节点元素
                        id:(100 + newCount),
                        pId:treeNode.id,
                        flag:3,  // 新增节点标记
                        name:"新建结点"  // + (newCount++)
                    };
                    zTree.addNodes(treeNode ,  new_);
                    return false;
                });
            }

        },
        
        //  移除添加按钮
        removeHoverDom:function(treeId, treeNode) {
            $("#addBtn_"+treeNode.tId).unbind().remove();
        },
        
        // 捕获节点被删除之前的事件 
        beforeRemove: function(treeId , treeNode){ 
        	// setTimeout("mconfirm('您确定要删除这个节点吗?', '系统提示', function(flag) {return flag;}); ",5000);
        	return true;
        },
        
        // 用于捕获删除节点之后的事件回调函数。
        onRemove: function(event, treeId, tree){
            return false;
        }, 

        beforeDrag:function(treeId, treeNodes) {
            return true;
        },
        
        beforeDrop:function(treeId, treeNodes, targetNode, moveType, isCopy){
            return true;
        },
        
        beforeDragOpen:function(treeId, treeNode){
            return true;
        },
        
        onDrag:function(event, treeId, treeNodes){
            return true;
        },
        
        /**
         * 节点拖拽结束后|此处涉及到批量更新操作|同层节点之间的批量更新
         * @returns {Boolean}
         */
        onDrop:function(event, treeId, treeNodes, targetNode, moveType, isCopy){  
        	if(treeNodes[0].name == "新建结点"){
        		return false;
        	}
            return true;
        },
        
        onExpand:function(event, treeId, treeNode){
            return true;
        },
        
        /**
         * 捕获 勾选 或 取消勾选 之前的事件回调函数
         */
        beforeCheck : function(treeId, node){  
        	return true;  
        }, 
        
        onCheck : function(event, treeId, treeNode){
        	return true; 
        },
        
        /**
         * 商户节点选择唯一性验证
         * @param node -> treeNode
         * @param isSellerNode 是否为商户节点|true是 false 不是
         */
        isSellerNodeBeCheck : function(treeId , node , isSellerNode){ 
        	var flag = false;
        	return flag;
        },
        
        // 响应节点单击事件
        ztOnClick:function(event, treeId, treeNode, clickFlag){
            var level_ = treeNode.level;
            switch(level_){
                case 0: // root节点
                    break;
                case 1: // API项目组节点 不允许编辑、添加、删除
                    break;
                case 2: // 具体的每一个API
                	apiInfo.apiInfo(event , treeNode);
                    break;
            }
        },
        
        // 添加或修改API具体信息
        apiInfo : function(event , treeNode) {  
        	apiInfo.currentNode = treeNode;
            $($("#tree-node-edit")[0].childNodes).remove();
            var flag = true;     // 新建结点 默认为真
            var type_ = 'post';
            var url_ = ''; 
            if(treeNode.name == "新建结点"){
            	url_ = apiInfo.path + 'ajax_api_info_add.do'; 
            }else{
            	flag = false;
            	url_ = apiInfo.path + 'ajax_api_info_edit.do';
            }
            var parent = apiInfo.zTree.getNodeByTId(treeNode.parentTId);
            
            var html_ = '<div>';
            html_ += '接口中文描述：';
            	html_ += '<input type="text" id="name" name="name" class="smallinput " placeholder="比如：订单信息接口" style="width: 300px; margin-bottom: 10px;">'; 
            html_ += '</div>';
            html_ += '<div>';
            html_ += '系统接口名称：';
            	html_ += '<input type="text" id="target" name="target" class="smallinput " placeholder="比如：TEST-PUBLIC-PROCESSOR" style="width: 300px; margin-bottom: 10px;">'; 
            html_ += '</div>';
            html_ += '<input type="hidden" name="atype" value="' + parent.atype +'" >';    // 系统接口类型 private:公司内部使用 public:开放给第三方
            
            html_ += '<div>业务处理实现：<input type="text" id="processor" name="processor" class="smallinput " placeholder="比如：publics.example.TestPublicProcessor" style="width: 300px; margin-bottom: 10px;"></div>';
            html_ += '<div>接口所属工程：<input type="text" id="module" name="module" class="smallinput " placeholder="比如：matrix-file" style="width: 300px; margin-bottom: 10px;" value="matrix-api"></div>';
            
            html_ += '<div style="margin-bottom: 10px;"><span style="vertical-align:middle;">接口跨域限制：</span>&nbsp&nbsp';
	            html_ += '<input type="radio" name="domain" value="0" checked onclick="apiInfo.cleanDomainInfo()" style="vertical-align:middle;"> <span style="vertical-align:middle;">不允许</span> &nbsp&nbsp';
	            html_ += '<input type="radio" name="domain"  value="1" onclick="apiInfo.openDomainDialog()" style="vertical-align:middle;"> <span style="vertical-align:middle;">允许</span>';
	            html_ += '<input type="hidden" id="domain-list" name="domainList"  value="">';
	            html_ += '<input type="hidden" id="domain-content-list" name="domainContentList"  value="">';
            html_ += '</div>';
            
            if(!flag){
            	html_ += '<div style="margin-bottom: 10px;"><span style="vertical-align:middle;">系统接口熔断：</span>&nbsp&nbsp';
		            html_ += '<input type="radio" name="discard" value="1" checked onclick="apiInfo.openDiscardWarning(this)" style="vertical-align:middle;"> <span style="vertical-align:middle;">恢复启用</span> &nbsp&nbsp';
		            html_ += '<input type="radio" name="discard"  value="0" onclick="apiInfo.openDiscardWarning(this)" style="vertical-align:middle;"> <span style="vertical-align:middle;">立刻熔断</span>';
	            html_ += '</div>';
	            html_ += '<input type="hidden" name="id" value="' + treeNode.id +'" >';
            }
            
            html_ += '<textarea cols="80" rows="5" maxlength="260"  id="remark" name="remark"  class="longinput "  placeholder="备注信息描述" style="margin-bottom: 10px;width:386px"></textarea><br/>';
            html_ += '<input type="hidden" name="parentId" value="' + treeNode.parentId +'" >';
            if(flag) {
            	var preNode = treeNode.getPreNode();   // seqnum  需要计算同层所有节点，然后得出顺序码
            	var seqnum_ = 1;
            	if(preNode != null){        // && typeof(preNode.seqnum) != "undefined"
            		seqnum_ = preNode.seqnum + 1;
            	} 
            	html_ += '<input type="hidden" name="seqnum" value="' + seqnum_ +'" >'; 
            }
            html_ += '<button class="stdbtn btn_orange " onclick="apiInfo.addOrUpdate(\'' + url_ +'\')"> 提 交 </button>'
            $("#tree-node-edit").append(html_);
            
            if(treeNode.name != "新建结点") {
            	var data_ = {target:treeNode.target};
            	var api_ = JSON.parse(ajaxs.sendAjax('post' , apiInfo.path + 'ajax_api_info_find.do' , data_));  
            	if(api_.status == 'success'){
            		apiInfo.drawApiEdit(api_);  
            	}else{
            		malert(api_.msg , '系统提示'); 
            	}
            }
        },
        
        // 清空 domainList 隐藏域中的值
        cleanDomainInfo:function(){
        	$("#domain-list").val("");
        	$("#domain-content-list").val("");
        },
        
        // 打开跨域列表弹窗
        openDomainDialog:function(){
        	var dialogId = "#ul-dialog-div";
    		// 自定义滚动条 | 执行此代码自定义滚动条则生效
    		$('#interface-list').slimscroll({
    			color: '#666',
    			size: '10px',
    			width: 'auto',
    			height: '180px' // '208px'
    		});
    		apiInfo.drawDomainDialog();
    		
    		window.parent.$.blockUI({
    			showOverlay:true ,
    			css:{
    				cursor:'auto',
    				left:($(window).width() - $(dialogId).width())/2 + 'px',
    				width:$(dialogId).width()+'px',
    				height:350,
    				top:($(window).height()-$(dialogId).height())/2 + 'px',
    				position:'fixed', //居中
    				textAlign:'left',
    				border: '3px solid #FB9337'   // 边界,
    			},
    			message: $(dialogId),
    			fadeIn: 500,//淡入时间
    			fadeOut: 1000  //淡出时间
    		});
        },
        
        // 请求后台，绘制弹窗数据
        drawDomainDialog:function(){
        	$("#api-include-domain-list li").remove();
        	var type_ = 'post';
            var url_ = apiInfo.path + 'ajax_include_domain_list.do'; 		 
            var data_ = null;   
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));  
            var html_ = '';
            if(obj.status == 'success'){
                var arr = obj.data;
                for(var i = 0 ; i < arr.length ; i ++){
                	html_ += '<li><div class="entry_wrap"><div class=""><h4><span>' + arr[i].companyName + '</span></h4>';
                	html_ += '<span><input type="checkbox" name="domainId"  value="' + arr[i].id + '" domain-data="' + arr[i].domain + '" style="vertical-align:middle;"/>&nbsp&nbsp';
                	html_ += '<span style="vertical-align:middle;">' + arr[i].domain + '</span></span></div></div></li>'; 
                }
            }else{
            	html_ += '<li><div class="entry_wrap"><div class=""><h4><span>跨域白名单尚未配置</span></h4>';
            	html_ += '<span>';
            	html_ += '<span style="vertical-align:middle;">' + obj.msg + '</span></span></div></div></li>'; 
            }
            $("#api-include-domain-list").append(html_);
            var arr = $("#domain-list").val().split(",");
            for(var k = 0 ; k < arr.length ; k ++){
            	$("input[value='" + arr[k] + "']").attr("checked","checked");
            }
        },
        
        // 保存勾选的跨域信息到隐藏域中
        saveOpenDomain:function(){
        	var arr = new Array();
        	var dcl = new Array();
        	$("input[name='domainId']:checked" , window.parent.document).each(function(){ 
        		arr.push($(this).val());
        		dcl.push($(this).attr("domain-data")); 
    		});  
        	$("#domain-list").val(arr.join());
        	$("#domain-content-list").val(dcl.join()); 
        	apiInfo.closeDialog();
        },
        
        // 添加或更新一条记录
        addOrUpdate:function(url_){
            var data_ = $("#tree-node-edit").serializeArray();
            var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
			if(obj.status == 'success'){
				malert(obj.msg , '系统提示' , function(){
					var zTree = apiInfo.zTree;
	            	var parent = zTree.getNodeByTId(apiInfo.currentNode.parentTId);
	            	var e = obj;
	            	
	            	zTree.removeNode(apiInfo.currentNode);
	            	apiInfo.currentNode = null;
	            	
                    var new_ = { // 节点元素重新追加
                        id : e.id,
                        pId : e.parentId,
                        name: e.name ,
                        target:e.target,
                        atype:e.atype,   
                        module:e.module,
                        processor:e.processor,
                        domain:e.domain,
                        seqnum : e.seqnum, 
                        parentId : e.parentId,
                        discard:e.discard,
                        remark:e.remark 
                    };
                    zTree.addNodes(parent ,  new_);
                    apiInfo.parentNode = null;
				});
			}else{
				malert(obj.msg , '系统提示');
			}
        },
        
        openDiscardWarning:function(o){
        	var val_ = $(o).val();
        	if($(o).val() == 0){
        		malert('选择此项并点击提交按钮后, 系统接口将会立刻熔断! 所有对此接口的访问都会失效!' , '高风险操作!');
        	}else{
        		malert('选择此项并点击提交按钮后, 系统接口将会恢复使用! 如果接口曾因为风险被关闭,请仔细确认并核对后开启!' , '系统提示!');
        	}
        },
        
        // 绘制API编辑信息
        drawApiEdit:function(o){
        	$("#name").val(o.name );
        	$("#name").attr("readonly","readonly");
        	$("#target").val(o.target );
        	$("#target").attr("readonly","readonly");
        	$("#processor").val(o.processor );
        	$("#module").val(o.module );
        	if(o.domainIds.length != 0){
        		$("#domain-list").val(o.domainIds.join(",") );
        	}
        	if(o.list.length != 0){
        		$("#domain-content-list").val(o.list.join(",")); 
        	}
        	$("input[name='domain'][value='" + o.domain + "']").attr("checked","checked");
        	$("input[name='discard'][value='" + o.discard + "']").attr("checked","checked");
        	$("#remark").val(o.remark ); 
        },
        
        /**
         * 收起导航栏从而方便操作
         */
        closeNavi:function(treeId){
        	var tree = $.fn.zTree.getZTreeObj(treeId);  
        	var nodes = tree.transformToArray(tree.getNodes()); 
        	if(nodes.length == 0){
        		return;
        	}
        	for(var n = 0 ; n < nodes.length ; n ++){
        		var node = nodes[n];
        		if(node.level == 1){
        			tree.expandNode(node , false , false ,false ,false );
        		}
        	}
        },
        
        closeDialog:function(){
        	window.parent.$.unblockUI();
        }
}
 
/**
 * 定义树的基本属性
 */
var setting_ = {
        view: {
            addHoverDom: apiInfo.addHoverDom,
            removeHoverDom: apiInfo.removeHoverDom, 
            selectedMulti: false    // 不允许同时选中多个节点
        },
        check:{
        	enable:true
        }, 
        edit: {
            drag: {
                autoExpandTrigger: true, // 拖拽时父节点自动展开是否触发 callback.onExpand 事件回调函数
                prev: false, //允许移动到目标节点前面 即可以将同层最后一个节点放到同层的第一个。 
                next: false,  // 设置是否允许移动到同层节点的最后一个节点的后面 从而使被移动的节点成为最后一个节点 
                inner: false // 拖拽到目标节点时 设置是否允许成为目标节点的子节点。
            },
            enable: false,  // 设置 zTree 是否处于编辑状态默认false|false不显示删除按钮，showRemoveBtn事件无效
            showRemoveBtn: false, // 树形控件显示删除按钮 apiInfo.showRemoveBtn 
            showRenameBtn: false  // 树形控件显示编辑按钮
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId",
                rootPId: 0
            }
        },
        callback: {
            beforeDrag: false,         // 捕获节点被拖拽之前的事件回调函数，并且根据返回值确定是否允许开启拖拽操作 |默认值：null
            beforeDrop: false,         // 捕获节点拖拽操作结束之前的事件回调函数，并且根据返回值确定是否允许此拖拽操作 |默认值：null
            beforeDragOpen: false,  // 获拖拽节点移动到折叠状态的父节点后，即将自动展开该父节点之前的事件回调函数，并且根据返回值确定是否允许自动展开操作 |默认值：null
            onDrag: false,                     // 捕获节点被拖拽的事件回调函数 |默认值：null
            onDrop: false,                     // 捕获节点拖拽操作结束的事件回调函数 |默认值：null
            onExpand: false,           // 捕获节点被展开的事件回调函数 |默认值：null
            onClick: apiInfo.ztOnClick,   
            beforeCheck: false,       //apiInfo.beforeCheck,    // 捕获 勾选 或 取消勾选 之前的事件回调函数
            onCheck :false,       // apiInfo.onCheck
            beforeRemove: apiInfo.beforeRemove,       // 捕获删除之前的数据 
            onRemove:apiInfo.onRemove            // 用于捕获删除节点之后的事件回调函数。   
        },
        setTrigger:function(){
            var zTree = apiInfo.zTree;
            zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
        }
    };








































