
var surfunc = {

		roleElement:null,
		currentNode : null ,  // 当前节点
		path : null,   // 浏览器基础路径
		
		init:function(path_){
			surfunc.path = path_;
			return surfunc;
		},
    	
        /**
         * 允许移动到目标节点前面 即可以将同层最后一个节点放到同层的第一个。
         * @param treeId
         * @param nodes
         * @param targetNode
         * @returns {boolean}
         */
        dropPrev:function(treeId, nodes, targetNode) {
        	return false;
        },
        
        /**
         * 设置是否允许移动到同层节点的最后一个节点的后面 从而使被移动的节点成为最后一个节点
         * @param treeId
         * @param nodes
         * @param targetNode
         * @returns {boolean}
         */
        dropNext:function(treeId, nodes, targetNode) {
        	return false;
        },
        
        /**
         *  拖拽到目标节点时 设置是否允许成为目标节点的子节点。
         * @param treeId
         * @param nodes
         * @param targetNode
         * @returns {boolean}
         */
        dropInner:function(treeId, nodes, targetNode) {
            return false;
        },

        /**
         * 如果是root节点 禁止显示删除按钮
         * @param treeId
         * @param treeNode
         * @returns {boolean}
         */
        showRemoveBtn:function(treeId, treeNode){
        	return false;
        },

        /**
         * 显示添加按钮 以及添加操作
         * @param treeId
         * @param treeNode
         */
        addHoverDom:function(treeId, treeNode) {
            return false;
        },
        
        /**
         * 移除添加按钮
         * @param treeId
         * @param treeNode
         */
        removeHoverDom:function(treeId, treeNode) {
            $("#addBtn_"+treeNode.tId).unbind().remove();
        },
        
        /**
         * 捕获节点被删除之前的事件
         * @param treeId
         * @param treeNode
         */
        beforeRemove: function(treeId , treeNode){ 
        	return false;
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
        	return false;
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
        
        
        ztOnClick:function(event, treeId, treeNode, clickFlag){
            var level_ = treeNode.level;
            switch(level_){
                case 0: // root节点
                    break;
                case 1: // web系统节点
                    surfunc.firstLevelEdit(event , treeNode);
                    break;
                case 2: // 导航栏
                    surfunc.navEdit(event , treeNode);
                    break;
                case 3: // 1级菜单栏
                    surfunc.fMenuEdit(event , treeNode);
                    break;
                case 4: // 2级菜单栏 - 此处对应具体页面
                    surfunc.sMenuEdit(event , treeNode);
                    break;
                case 5: // 页面按钮(添加|删除|修改等)|跳转页等等
                    surfunc.subMenuEdit(event , treeNode);
                    break;
            }
        },
        
        /**
         * 编辑系统节点，创建一个新的系统的起始节点，比如：媒体站系统|结算系统|优惠券系统。 
         * 第一个默认系统节点为Leader节点。
         * 
         * @param event
         * @param treeNode
         */
        firstLevelEdit:function(event , treeNode){
        	surfunc.currentNode = treeNode;
            $($("#tree-node-edit")[0].childNodes).remove();
            var html_ = '系统定义名称：<input type="text" name="name" class="smallinput " placeholder="系统名称" style="margin-bottom: 10px;" value="' + treeNode.name + '" ><br/>';
            
            if(typeof(treeNode.children) == "undefined" || treeNode.children.length == 0){
            	html_ += '平台唯一标识：<input type="text" name="platform" class="smallinput " placeholder="平台唯一识别码，无子节点可编辑" style="margin-bottom: 10px;" value="' + treeNode.platform + '" >';
            }else{
            	html_ += '<div style="margin-bottom:10px">平台唯一标识：' + treeNode.platform + '</div>';
            }
            html_ += '<textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput "  placeholder="请输入这个新创建的系统相关信息描述" style="margin-bottom: 10px;">' + treeNode.remark + '</textarea><br/>';
            html_ += '<input type="hidden" name="id" value="' + treeNode.id +'" >'; 
            $("#tree-node-edit").append(html_);
        },
        
        /**
         * 导航栏相关操作
         * @param event
         * @param treeNode
         */
        navEdit:function(event , treeNode){
        	surfunc.currentNode = treeNode;
        	$($("#tree-node-edit")[0].childNodes).remove();
            var html_ = '页面导航名称：<input type="text" name="name" class="smallinput " style="margin-bottom: 10px;" value="' + treeNode.name + '" ><br/>';
            html_ += '<div style="vertical-align:middle">节点备注信息：</div><textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput " style="margin-bottom: 10px;">' + treeNode.remark + '</textarea><br/>';
            html_ += '<input type="hidden" name="id" value="' + treeNode.id +'" >'; 
            $("#tree-node-edit").append(html_);
        },
        
        /**
         * 编辑一级菜单栏
         * @param event
         * @param treeNode
         */
        fMenuEdit:function(event , treeNode){
        	surfunc.currentNode = treeNode;
			$($("#tree-node-edit")[0].childNodes).remove();
            var html_ = '一级菜单名称：<input type="text" name="name" class="smallinput " style="margin-bottom: 10px;" value="' + treeNode.name + '" ><br/>';
            html_ += '<div style="vertical-align:middle">节点备注信息：</div><textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput " style="margin-bottom: 10px;">' + treeNode.remark + '</textarea><br/>';
            html_ += '<input type="hidden" name="id" value="' + treeNode.id +'" >'; 
            $("#tree-node-edit").append(html_);
        },
        
        /**
         * 编辑二级菜单栏
         * @param event
         * @param treeNode
         */
        sMenuEdit:function(event , treeNode){
        	surfunc.currentNode = treeNode;
        	$($("#tree-node-edit")[0].childNodes).remove();
            var html_ = '二级菜单名称：<input type="text" name="name" class="smallinput " style="margin-bottom: 10px;" value="' + treeNode.name + '" ><br/>';
            html_ += '页面跳转地址：<input type="text" class="smallinput " placeholder="exa/example.do" style="margin-bottom: 10px;" name="funcUrl" value="' + treeNode.funcUrl + '" ><br/>'; 
            html_ += '<div style="vertical-align:middle">节点备注信息：</div><textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput " style="margin-bottom: 10px;">' + treeNode.remark + '</textarea><br/>';
            html_ += '<input type="hidden" name="id" value="' + treeNode.id +'" >'; 
            $("#tree-node-edit").append(html_);
        },
        
        /**
         * 编辑按钮节点|跳转页等内容
         * @param event
         * @param treeNode
         */
        subMenuEdit:function(event ,treeNode){
        	surfunc.currentNode = treeNode;
        	$($("#tree-node-edit")[0].childNodes).remove();
            var html_ = '页面按钮名称：<input type="text" name="name" class="smallinput " style="margin-bottom: 10px;" value="' + treeNode.name + '" ><br/>'; 
            // 按钮位置勾选判定
            html_ += '页面按钮位置：<select id="btnArea" name="btnArea" class="radius3"  style="margin-left:0px; margin-bottom: 10px;">';
            if(treeNode.btnArea == '10001'){
            	html_ += '<option value="10001" selected>功能区域</option><option value="10002">查询区域</option><option value="10003">数据区域</option>';
            }else if(treeNode.btnArea == '10002'){
            	html_ += '<option value="10001">功能区域</option><option value="10002" selected>查询区域</option><option value="10003">数据区域</option>';
            }else{
            	html_ += '<option value="10001">功能区域</option><option value="10002">查询区域</option><option value="10003" selected>数据区域</option>';
            }
            html_ += '</select><br/>';
            
            html_ += '页面按钮类型：<select id="navType" name="navType" class="radius3" onchange="surfunc.changeNodeType(2)" style="margin-left:0px; margin-bottom: 10px;">'; 
            if(treeNode.navType == 4){
            	html_ += '<option value="4" selected>页面按钮</option><option value="5">内部跳转页面</option></select><br/>';
            	html_ += '<div id = "node-type" style="margin-bottom: 10px;">';
            	html_ += '<input type="hidden" name="funcUrl" value="" >';  				// 更新时，此处置空
            	html_ += '按钮请求路径：<input type="text" name="ajaxBtnUrl" class="smallinput " placeholder="如：ajax_btn_func_add"  style="margin-bottom: 10px;" value="' + treeNode.ajaxBtnUrl + '" ><br/>' 
            	html_ += '</div>';
            }else{
            	html_ += '<option value="4">页面按钮</option><option value="5"  selected>内部跳转页面</option></select><br/>';
            	html_ += '<div id = "node-type" style="margin-bottom: 10px;">';
            	html_ += '按钮跳转地址：<input type="text" class="smallinput " placeholder="funcUrl" style="margin-bottom: 10px;" name="funcUrl" value="' + treeNode.funcUrl + '" ><br/>';
            	html_ += '<input type="hidden" name="ajaxBtnUrl" value="" >';  				// 更新时，此处置空
            	html_ += '</div>';
            }
            
            html_ += '页面按钮标识：<input type="text" name="eleValue" class="smallinput " placeholder="如：二级菜单功能:按钮用途"  style="margin-bottom: 10px;" value="' + treeNode.eleValue + '" ><br/>' 
            html_ += '<textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput "  placeholder="备注信息描述" style="margin-bottom: 10px;">' + treeNode.remark + '</textarea><br/>';
            $("#tree-node-edit").append(html_);
        },

        changeNodeType:function(flag){
        	var funcUrl_ = "";
        	var ajaxBtnUrl_ = "";
        	if(flag == 2){
        		funcUrl_ = surfunc.currentNode.funcUrl;
        		ajaxBtnUrl_ = surfunc.currentNode.ajaxBtnUrl;
        	}
        	$("#node-type").empty(); 
        	var val_ = $("#navType").val();
        	// $("input[name='funcUrl']").remove();
        	var html_ = '';
        	if(val_ == 4){      // 页面按钮
        		html_ += '<input type="hidden" name="funcUrl" value="" >';   		// 更新时，此处置空
        		html_ += '按钮请求路径：<input type="text" name="ajaxBtnUrl" class="smallinput " placeholder="如：ajax_btn_func_add"  style="margin-bottom: 10px;" value="' + ajaxBtnUrl_ + '" ><br/>' 
        		$("#node-type").append(html_); 
        	}else{  // 内部跳转页面 
        		html_ += '按钮跳转地址：<input type="text" class="smallinput " placeholder="exa/example.do" style="margin-bottom: 10px;" name="funcUrl" value="' + funcUrl_ + '" ><br/>';
        		html_ += '<input type="hidden" name="ajaxBtnUrl" value="" >';   // 更新时，此处置空
        		$("#node-type").append(html_); 
        	}
        },
        
        
        /**
         * 初始化【系统功能】菜单树
         */
        sysTreeOperation: function(){
        	$("#sys-tree li").remove();
        	$($("#tree-node-edit")[0].childNodes).remove();
        	var type_ = 'post';
            var url_ = surfunc.path + 'sysrole/ajax_tree_list.do?type=list';
            var data_ = null;   
            var jsonObj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));  
            if(jsonObj.status == 'success'){
                var zNodes = jsonObj.data.list;
                $.fn.zTree.init($("#sys-tree") , setting_nav , zNodes);
                $("#callbackTrigger").bind("change", {}, setting_nav.setTrigger);
            }
        }, 







		///////////////////////////////////////////////////////////////////////////////////////////////////////////////  功能与角色相关内容 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * 初始化【权限分配】菜单树
         */
        distributeUserRole: function(roleId , platform){
        	$("#user-role-tree li").remove();
        	var type_ = 'post';
            var url_ = surfunc.path + 'sysrole/ajax_tree_list.do?type=role&roleId=' + roleId;
            var data_ = {  // 只有Leader平台会固定传入platform字段，用于区分【角色功能】显示哪些树节点下的内容
            		platform : platform
            };
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
            if(obj.status == 'success'){
                var zNodes = obj.data.list;  
                $.fn.zTree.init($("#user-role-tree") , setting_distribution , zNodes);  
                $("#callbackTrigger").bind("change", {}, setting_distribution.setTrigger); 
                $("#user-role-tree_1_check").remove(); // 隐藏root节点的复选框
                if(obj.data.roles.length == 1){
                	// hidden input value= ids |<input id="func-ids"  type="hidden" value="" >
                	$("#func-ids").val(obj.data.roles[0].ids);
                	surfunc.showFuncInTree($("#func-ids")[0]);  // 复用showFuncInTree方法    
                }
            }
        },
        
        
        /**
         * 系统权限分配 - 选择右侧已创建的角色列表时，展示已选权限到左侧的树上    
         */
        showFuncInTree:function(obj){
        	if(obj.value.length == 0){
        		return;
        	}
        	var tree = $.fn.zTree.getZTreeObj("user-role-tree");
        	var nodes = tree.transformToArray(tree.getNodes()); 
        	if(nodes.length == 0){
        		return;
        	}
        	for(var n = 0 ; n < nodes.length ; n ++){
        		tree.checkNode(nodes[n], false, false); 
        	}
        	
        	var node; 
        	var arr = obj.value.split(",");  // sys_function array
        	for(var n = 0 ; n < nodes.length ; n ++){
        		for(var i = 0 ; i < arr.length ; i ++){
            		if(nodes[n].id == arr[i]){
            			tree.checkNode(nodes[n], true, false);
            			if(i == 0){
            				node = nodes[n];
            			}
            		}
            	} 
        	} 
        	$($(".seller-node")[0]).removeClass("seller-node");  
        	$("#" + node.tId).addClass("seller-node"); 
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
        		if(node.navType == 1){
        			tree.expandNode(node , false , false ,false ,false );
        		}
        	}
        },
        
        /**
         * 收起一级菜单栏从而方便操作
         */
        closeMenu:function(treeId){ 
        	var tree = $.fn.zTree.getZTreeObj(treeId);  
        	var nodes = tree.transformToArray(tree.getNodes()); 
        	if(nodes.length == 0){
        		return;
        	}
        	for(var n = 0 ; n < nodes.length ; n ++){
        		var node = nodes[n];
        		if(node.navType == 2){
        			tree.expandNode(node , false , false ,false ,false );
        		}
        	}
        },
        
        
        closeDialog:function(){
        	window.parent.$.unblockUI();
        }
    };
    
    
    
    
    // 请参阅：zTree_v3-master/api/API_cn.html 和 文件路径: exedit/drag_super.html
    // 导航与菜单树 
    var setting_nav = {
        view: {
            addHoverDom: surfunc.addHoverDom,
            removeHoverDom: surfunc.removeHoverDom,
            selectedMulti: false    // 不允许同时选中多个节点
        },
        check:{
        	enable:false
        }, 
        edit: {
            drag: {
                autoExpandTrigger: true, // 拖拽时父节点自动展开是否触发 callback.onExpand 事件回调函数
                prev: surfunc.dropPrev, //允许移动到目标节点前面 即可以将同层最后一个节点放到同层的第一个。 
                next: surfunc.dropNext,  // 设置是否允许移动到同层节点的最后一个节点的后面 从而使被移动的节点成为最后一个节点 
                inner: surfunc.dropInner  // 拖拽到目标节点时 设置是否允许成为目标节点的子节点。
            },
            enable: true,  // 设置 zTree 是否处于编辑状态默认false|初始化后需要改变编辑状态请使用 zTreeObj.setEditable() 方法|false不显示删除按钮，showRemoveBtn事件无效
            showRemoveBtn: surfunc.showRemoveBtn, // 树形控件显示删除按钮
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
            beforeDrag: surfunc.beforeDrag,         // 捕获节点被拖拽之前的事件回调函数，并且根据返回值确定是否允许开启拖拽操作 |默认值：null
            beforeDrop: surfunc.beforeDrop,         // 捕获节点拖拽操作结束之前的事件回调函数，并且根据返回值确定是否允许此拖拽操作 |默认值：null
            beforeDragOpen: surfunc.beforeDragOpen,  // 获拖拽节点移动到折叠状态的父节点后，即将自动展开该父节点之前的事件回调函数，并且根据返回值确定是否允许自动展开操作 |默认值：null
            onDrag: surfunc.onDrag,                     // 捕获节点被拖拽的事件回调函数 |默认值：null
            onDrop: surfunc.onDrop,                     // 捕获节点拖拽操作结束的事件回调函数 |默认值：null
            onExpand: surfunc.onExpand,           // 捕获节点被展开的事件回调函数 |默认值：null
            onClick: surfunc.ztOnClick,
            beforeRemove: surfunc.beforeRemove,       // 捕获删除之前的数据 
            onRemove:surfunc.onRemove            // 用于捕获删除节点之后的事件回调函数。   
        },
        setTrigger:function(){
            var zTree = $.fn.zTree.getZTreeObj("sys-tree");
            zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
        }
    };

    // 系统权限分配
    var setting_distribution = {
        view: {
            addHoverDom: false,
            removeHoverDom: false,
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
            enable: false,  // 设置 zTree 是否处于编辑状态默认false|初始化后需要改变编辑状态请使用 zTreeObj.setEditable() 方法|false不显示删除按钮，showRemoveBtn事件无效
            showRemoveBtn: false, // 树形控件显示删除按钮
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
            onClick: false,
            beforeRemove: false,       // 捕获删除之前的数据 
            beforeCheck: surfunc.beforeCheck,    // 捕获 勾选 或 取消勾选 之前的事件回调函数
            onCheck : surfunc.onCheck
        },
        setTrigger:function(){
            var zTree = $.fn.zTree.getZTreeObj("user-role-tree");
            zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
        }
    };








































