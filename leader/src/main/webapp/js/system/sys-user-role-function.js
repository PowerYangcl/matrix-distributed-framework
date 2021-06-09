
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
            if(nodes[0].parentId == targetNode.parentId){  // 只允许同层节点之间进行拖拽
            	return true;
            }else{
            	return false;
            }
        },
        
        /**
         * 设置是否允许移动到同层节点的最后一个节点的后面 从而使被移动的节点成为最后一个节点
         * @param treeId
         * @param nodes
         * @param targetNode
         * @returns {boolean}
         */
        dropNext:function(treeId, nodes, targetNode) {
            if(nodes[0].parentId == targetNode.parentId){  // 只允许同层节点之间进行拖拽
            	return true;
            }else{
            	return false;
            } 
        },
        
        /**
         *  拖拽到目标节点时 设置是否允许成为目标节点的子节点。
         * @param treeId
         * @param nodes
         * @param targetNode
         * @returns {boolean}
         */
        dropInner:function(treeId, nodes, targetNode) {
//            console.log("dropInner");
            return false;
        },

        /**
         * 如果是root节点 禁止显示删除按钮
         * @param treeId
         * @param treeNode
         * @returns {boolean}
         */
        showRemoveBtn:function(treeId, treeNode){
            if(treeNode.level == 0){
                return false;
            }else{
                return true;
            }
        },

        /**
         * 显示添加按钮 以及添加操作
         * @param treeId
         * @param treeNode
         */
        addHoverDom:function(treeId, treeNode) {
        	if(treeNode.level == 5){     // (treeNode.level == 0 && treeNode.children.length != 0) || 
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
                    var zTree = $.fn.zTree.getZTreeObj("sys-tree");
                    var new_ = { // 新建一个节点元素
                        id:(100 + newCount),
                        pId:treeNode.id,
                        platform:treeNode.platform,
                        flag:3,  // 新增节点标记
                        name:"新建结点"  // + (newCount++)
                    };
                    zTree.addNodes(treeNode ,  new_);
                    return false;
                });
            }

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
        	var type_ = false;
        	if(confirm("您确定要删除这个节点吗?")){
        		type_ = true;
        	}
        	return type_;	
        },
        
        // 用于捕获删除节点之后的事件回调函数。
        onRemove: function(event, treeId, tree){
        	var type_ = 'post';
            var url_ = surfunc.path + 'sysrole/ajax_delete_node.do';
            var ids = "";
            ids = surfunc.getSubnodeIds(ids , tree);
            var data_ = {ids : ids.substring(0 , ids.length-1)};   
        	
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
            if(obj.status == 'success'){
            	layer.alert( obj.msg , {title:'操作成功 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
            	return true;
            }else{
            	layer.alert( obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
            	return false;
            }
        }, 
        
        getSubnodeIds : function(ids , tree){
        	if(tree.isParent){
            	var arr = tree.children;
            	var ids_ = "";
            	for(var i = 0 ; i < arr.length ; i ++){
            		ids_ += surfunc.getSubnodeIds(ids , arr[i]);
            	}
            	ids += ids_;
            }
        	
        	ids += tree.id + ","; 
        	return ids;
        },


        beforeDrag:function(treeId, treeNodes) {
            // TODO
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
        	
        	var ustring = ""; // id@seqnum,id@seqnum   准备交给服务器解析处理的字符串
        	var uparr = new Array();			// 保存有效的更新信息
        	var parent = treeNodes[0].getParentNode();
        	var arr = parent.children;
        	if(arr.length > 0){
        		for(var i = 0 ; i < arr.length ; i ++){
        			if(arr[i].name != "新建结点"){
        				uparr.push(arr[i]);
        			}
        		}
        		if(uparr.length != 0){
        			for(var k = 0 ; k < uparr.length ; k ++){
        				var node = uparr[k];
        				var seqnum = parseInt(k) + 1;  // node.getIndex() 会受到新建节点的影响造成seqnum值不准。
        				ustring += node.id + "@" + seqnum + ","  ;
        			}
        			ustring = ustring.substring(0, ustring.length-1); 
        			
        			var obj = JSON.parse(ajaxs.sendAjax('post' , surfunc.path + 'sysrole/ajax_update_tree_nodes.do' , {ustring:ustring}));
                    if(obj.status == 'success'){
                    	layer.alert( obj.msg , {title:'操作成功 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
                    	return true;
                    }else{
                    	layer.alert( obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
                    	return false;
                    }
        		}
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
        
        
        ztOnClick:function(event, treeId, treeNode, clickFlag){
            var level_ = treeNode.level;
            switch(level_){
                case 0: // root节点
                    break;
                case 1: // 商户节点(惠家有等)
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
            var type_ = 'post';
            var url_ = ''; 
            if(treeNode.name == "新建结点"){
            	url_ = 'ajax_add_tree_node.do';
            	var html_ = '<input type="text" name="name" class="smallinput " placeholder="系统名称" style="margin-bottom: 10px;">';
            	html_ += '<textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput " placeholder="请输入这个新创建的系统相关信息描述" style="margin-bottom: 10px;"></textarea><br/>';
            	html_ += '<input type="hidden" name="parentId" value="' + treeNode.parentId +'" >';
            	
            	var preNode = treeNode.getPreNode();   // seqnum  需要计算同层所有节点，然后得出顺序码
            	var seqnum_ = 1;
            	if(preNode != null){
            		seqnum_ = preNode.seqnum + 1;
            	} 
            	html_ += '<input type="hidden" name="seqnum" value="' + seqnum_ +'" >';
            	html_ += '<input type="hidden" name="navType"  value="0" >';				// pcode由后台自动生成
            	html_ += '<input type="hidden" name="styleClass" value="" >';
            	html_ += '<input type="hidden" name="styleKey" value="" >';
            	html_ += '<input type="hidden" name="funcUrl" value="" >';  
            	html_ += '<button class="layui-btn layui-btn-radius" onclick="surfunc.addData(\'' + url_ +'\')"> 提 交 </button>'
            }else{
            	url_ = 'ajax_edit_tree_node.do';
            	var html_ = '系统定义名称：<input type="text" name="name" class="smallinput " placeholder="系统名称" style="margin-bottom: 10px;" value="' + treeNode.name + '" ><br/>';
            	
            	if(typeof(treeNode.children) == "undefined" || treeNode.children.length == 0){
            		html_ += '平台唯一标识：<input type="text" name="platform" class="smallinput " placeholder="平台唯一识别码，无子节点可编辑" style="margin-bottom: 10px;" value="' + treeNode.platform + '" >';
            	}else{
            		html_ += '<div style="margin-bottom:10px">平台唯一标识：' + treeNode.platform + '</div>';
            	}
            	html_ += '<textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput "  placeholder="请输入这个新创建的系统相关信息描述" style="margin-bottom: 10px;">' + treeNode.remark + '</textarea><br/>';
            	html_ += '<input type="hidden" name="id" value="' + treeNode.id +'" >'; 
            	html_ += '<button class="layui-btn layui-btn-radius" onclick="surfunc.addData(\'' + url_ +'\')"> 提 交 </button>'
            }
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
            var type_ = 'post';
            var url_ = ''; 
            if(treeNode.name == "新建结点"){
            	url_ = 'ajax_add_tree_node.do';
            	var html_ = '页面导航名称：<input type="text" name="name" class="smallinput " style="margin-bottom: 10px;"><br/>';
            	html_ += '<div style="vertical-align:middle">节点备注信息：</div><textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput "  style="margin-bottom: 10px;"></textarea><br/>';
            	html_ += '<input type="hidden" name="parentId" value="' + treeNode.parentId +'" >';
            	
            	var preNode = treeNode.getPreNode();   // seqnum  需要计算同层所有节点，然后得出顺序码
            	var seqnum_ = 1;
            	if(preNode != null){
            		seqnum_ = preNode.seqnum + 1;
            	} 
            	html_ += '<input type="hidden" name="seqnum" value="' + seqnum_ +'" >';
            	html_ += '<input type="hidden" name="navType"  value="1" >';
            	html_ += '<input type="hidden" name="platform"  value="' + treeNode.platform +'" >';
            	html_ += '<input type="hidden" name="styleClass" value="" >';
            	html_ += '<input type="hidden" name="styleKey" value="" >';
            	html_ += '<input type="hidden" name="funcUrl" value="" >';  
            	html_ += '<button class="layui-btn layui-btn-radius" onclick="surfunc.addData(\'' + url_ +'\')"> 提 交 </button>'
            }else{
            	url_ = 'ajax_edit_tree_node.do';
            	var html_ = '页面导航名称：<input type="text" name="name" class="smallinput " style="margin-bottom: 10px;" value="' + treeNode.name + '" ><br/>';
            	html_ += '<div style="vertical-align:middle">节点备注信息：</div><textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput " style="margin-bottom: 10px;">' + treeNode.remark + '</textarea><br/>';
            	html_ += '<input type="hidden" name="id" value="' + treeNode.id +'" >'; 
            	html_ += '<button class="layui-btn layui-btn-radius" onclick="surfunc.addData(\'' + url_ +'\')"> 提 交 </button>'
            }
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
            var type_ = 'post';
            var url_ = ''; 
            if(treeNode.name == "新建结点"){
            	url_ = 'ajax_add_tree_node.do';
            	var html_ = '一级菜单名称：<input type="text" name="name" class="smallinput " style="margin-bottom: 10px;"><br/>';
            	html_ += '<div style="vertical-align:middle">节点备注信息：</div><textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput " style="margin-bottom: 10px;"></textarea><br/>';
            	html_ += '<input type="hidden" name="parentId" value="' + treeNode.parentId +'" >';
            	
            	var preNode = treeNode.getPreNode();   // seqnum  需要计算同层所有节点，然后得出顺序码
            	var seqnum_ = 1;
            	if(preNode != null){
            		seqnum_ = preNode.seqnum + 1;
            	} 
            	html_ += '<input type="hidden" name="seqnum" value="' + seqnum_ +'" >';
            	html_ += '<input type="hidden" name="navType"  value="2" >'; 
            	html_ += '<input type="hidden" name="platform"  value="' + treeNode.platform +'" >';
            	html_ += '<input type="hidden" name="styleClass" value="editor" >'; 
            	html_ += '<input type="hidden" name="styleKey" value="" >'; 
            	html_ += '<input type="hidden" name="funcUrl" value="" >';  
            	html_ += '<button class="layui-btn layui-btn-radius" onclick="surfunc.addData(\'' + url_ +'\')"> 提 交 </button>'
            }else{
            	url_ = 'ajax_edit_tree_node.do';
            	var html_ = '一级菜单名称：<input type="text" name="name" class="smallinput " style="margin-bottom: 10px;" value="' + treeNode.name + '" ><br/>';
            	html_ += '<div style="vertical-align:middle">节点备注信息：</div><textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput " style="margin-bottom: 10px;">' + treeNode.remark + '</textarea><br/>';
            	html_ += '<input type="hidden" name="id" value="' + treeNode.id +'" >'; 
            	html_ += '<button class="layui-btn layui-btn-radius" onclick="surfunc.addData(\'' + url_ +'\')"> 提 交 </button>'
            }
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
            var type_ = 'post';
            var url_ = ''; 
            if(treeNode.name == "新建结点"){
            	url_ = 'ajax_add_tree_node.do';
            	var html_ = '二级菜单名称：<input type="text" name="name" class="smallinput " style="margin-bottom: 10px;"><br/>';
            	html_ += '页面跳转地址：<input type="text" class="smallinput " placeholder="exa/example.do" style="margin-bottom: 10px;" name="funcUrl" value="" ><br/>';
            	html_ += '<div style="vertical-align:middle">节点备注信息：</div><textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput " style="margin-bottom: 10px;"></textarea><br/>';
            	html_ += '<input type="hidden" name="parentId" value="' + treeNode.parentId +'" >';
            	
            	var preNode = treeNode.getPreNode();   // seqnum  需要计算同层所有节点，然后得出顺序码
            	var seqnum_ = 1;
            	if(preNode != null){
            		seqnum_ = preNode.seqnum + 1;
            	} 
            	html_ += '<input type="hidden" name="seqnum" value="' + seqnum_ +'" >';
            	html_ += '<input type="hidden" name="navType"  value="3" >'; 
            	html_ += '<input type="hidden" name="platform"  value="' + treeNode.platform +'" >';
            	html_ += '<input type="hidden" name="styleClass" value="" >';
            	html_ += '<input type="hidden" name="styleKey" value="" >'; 
            	html_ += '<button class="layui-btn layui-btn-radius" onclick="surfunc.addData(\'' + url_ +'\')"> 提 交 </button>'
            }else{
            	url_ = 'ajax_edit_tree_node.do';
            	var html_ = '二级菜单名称：<input type="text" name="name" class="smallinput " style="margin-bottom: 10px;" value="' + treeNode.name + '" ><br/>';
            	html_ += '页面跳转地址：<input type="text" class="smallinput " placeholder="exa/example.do" style="margin-bottom: 10px;" name="funcUrl" value="' + treeNode.funcUrl + '" ><br/>'; 
            	html_ += '<div style="vertical-align:middle">节点备注信息：</div><textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput " style="margin-bottom: 10px;">' + treeNode.remark + '</textarea><br/>';
            	html_ += '<input type="hidden" name="id" value="' + treeNode.id +'" >'; 
            	html_ += '<button class="layui-btn layui-btn-radius" onclick="surfunc.addData(\'' + url_ +'\')"> 提 交 </button>'
            }
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
            var type_ = 'post';
            var url_ = ''; 
            if(treeNode.name == "新建结点"){
            	url_ = 'ajax_add_tree_node.do';
            	var html_ = '页面按钮名称：<input type="text" name="name" class="smallinput " placeholder="按钮节点|跳转页" style="margin-bottom: 10px;"><br/>';
            	html_ += '页面按钮位置：<select id="btnArea" name="btnArea" class="radius3"  style="margin-left:0px; margin-bottom: 10px;">';
				html_ += '<option value="10001">功能区域</option><option value="10002">查询区域</option><option value="10003">数据区域</option></select><br/>';
				
            	html_ += '页面按钮类型：<select id="navType" name="navType" class="radius3" onchange="surfunc.changeNodeType(1)" style="margin-left:0px; margin-bottom: 10px;">';        
            	html_ += '<option value="4">页面按钮</option><option value="5">内部跳转页面</option></select><br/>';
				html_ += '<div id = "node-type" style="margin-bottom: 10px;">';
				html_ += '按钮请求路径：<input type="text" name="ajaxBtnUrl" class="smallinput " placeholder="如：ajax_btn_func_add"  style="margin-bottom: 10px;" value="" ><br/>';
				html_ += '</div>';	
				
				html_ += '页面按钮标识：<input type="text" name="eleValue" class="smallinput " placeholder="如：二级菜单功能:按钮用途"  style="margin-bottom: 10px;" value="" ><br/>';
            	
            	html_ += '<textarea cols="80" rows="5" maxlength="250"  name="remark"  class="longinput "  placeholder="备注信息描述" style="margin-bottom: 10px;"></textarea><br/>';
            	html_ += '<input type="hidden" name="parentId" value="' + treeNode.parentId +'" >';
            	var preNode = treeNode.getPreNode();   // seqnum  需要计算同层所有节点，然后得出顺序码
            	var seqnum_ = 1;
            	if(preNode != null){
            		seqnum_ = preNode.seqnum + 1;
            	} 
            	html_ += '<input type="hidden" name="seqnum" value="' + seqnum_ +'" >';
            	html_ += '<input type="hidden" name="platform"  value="' + treeNode.platform +'" >';
            	html_ += '<input type="hidden" name="styleClass" value="" >';
            	html_ += '<input type="hidden" name="styleKey" value="" >';
            	html_ += '<button class="layui-btn layui-btn-radius" onclick="surfunc.addData(\'' + url_ +'\')"> 提 交 </button>'
            }else{
            	url_ = 'ajax_edit_tree_node.do';
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
            	html_ += '<input type="hidden" name="id" value="' + treeNode.id +'" >'; 
            	html_ += '<button class="layui-btn layui-btn-radius" onclick="surfunc.addData(\'' + url_ +'\')"> 提 交 </button>'
            }
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
         * 添加一个节点到数据库  
         * @param url_
         */
        addData:function(url__){
        	var url_ = surfunc.path + 'sysrole/' + url__;
            var data_ = $("#tree-node-edit").serializeArray();
            var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
			if(obj.status == 'success'){
				layer.alert( obj.msg , {title:'操作成功!' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4},function(index){	 // layer.close(index);进行主动关闭确定按钮
					var zTree = $.fn.zTree.getZTreeObj("sys-tree");
	            	var parent = zTree.getNodeByTId(surfunc.currentNode.parentTId);
	            	var e = obj.entity;
	            	var node = null;
	            	
	            	if($("input[name='id']").length == 0){		// 新增节点
	            		zTree.removeNode(surfunc.currentNode);
	            		var new_ = { // 节点元素重新追加
	                            id : e.id,
	                            pId : parent.id,
	                            flag:1,  // 新增节点标记
	                            name: e.name ,
	                            parentId : e.parentId,
	                            seqnum : e.seqnum,
	                            navType : e.navType,
	                            platform : e.platform,
	                            styleClass : e.styleClass,
	                            styleKey : e.styleKey,
	                            funcUrl : e.funcUrl,
	                            ajaxBtnUrl : e.ajaxBtnUrl,
	                            remark : e.remark,
	                            btnArea : e.btnArea,
	                            eleValue : e.eleValue
	                        };
                        zTree.addNodes(parent ,  new_);
                        node = zTree.getNodeByParam("id", e.id, null);
	            	}else{		// 修改节点
	            		node = zTree.getNodeByParam("id", e.id, null);
	            		node.id = e.id;
	            		node.pId = parent.id;
	            		node.flag=1;  // 新增节点标记
	            		node.name= e.name ;
	            		node.parentId = e.parentId;
	            		node.seqnum = e.seqnum;
	            		node.navType = e.navType;
	            		node.platform = e.platform;
	            		node.styleClass = e.styleClass;
	            		node.styleKey = e.styleKey;
	            		node.funcUrl = e.funcUrl;
	            		node.ajaxBtnUrl = e.ajaxBtnUrl;
	            		node.remark = e.remark;
	            		node.btnArea = e.btnArea;
	            		node.eleValue = e.eleValue;
	            		
	                    zTree.updateNode(node);
	            	}
                    
                    zTree.selectNode(node);
                    surfunc.currentNode = node;
                    
                    layer.close(index);  // 进行主动关闭确定按钮
				});
				
			}else{
				layer.alert( obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
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
                var zNodes = jsonObj.list;
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
                var zNodes = obj.list;  
                $.fn.zTree.init($("#user-role-tree") , setting_distribution , zNodes);  
                $("#callbackTrigger").bind("change", {}, setting_distribution.setTrigger); 
                $("#user-role-tree_1_check").remove(); // 隐藏root节点的复选框
                if(obj.roles.length == 1){
                	// hidden input value= ids |<input id="func-ids"  type="hidden" value="" >
                	$("#func-ids").val(obj.roles[0].ids);
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








































