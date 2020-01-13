/**
 * api信息树相关功能
 */
var apiInfo = {
		path:null,
		currentNode:null,
		zTree:null ,  
		
		apiServiceUrl:null,   // 部署的api服务器路径 
		
		security : function(btnMap){
			var checkArr = $(".layui-this" , parent.document);
			var tabId = null;
			switch(checkArr.length) {
				 case 2:
					 // 此种情况：主窗体左侧百叶窗焦点未丢失
			    	 tabId = checkArr[1].id;
			    	 break;
			     case 1:
			    	 // 此种情况：主窗体左侧百叶窗焦点已经丢失，只剩下tab页
			    	 tabId = checkArr[0].id;
			    	 break;
			     case 0:		
			    	 // 此种情况：layer.open打开的弹窗中引入的是一个新的列表页面
			    	 checkArr = $(".layui-this" , parent.parent.document);
			    	 switch(checkArr.length) {
				    	case 2:
					    	tabId = checkArr[1].id;
					    	break;
					    case 1:
					    	tabId = checkArr[0].id;
					    	break;
			    	 }
			    	 break;			     
			}
			
			var result = btnMap.get('btns-' + tabId);
			if(typeof result == 'undefined' || result == null || result.length == 0){
				return;
			}
			
			var barr = result.split(",");
			for(var i = 0 ; i < barr.length ; i ++){
				$(".security-btn[key='" + barr[i] + "']").show();
				$(".security-btn[key='" + barr[i] + "']").removeClass("security-btn"); 
			}
			$("a.layui-btn").removeAttr("style");	// 数据表格 操作列中的按钮移除元素本身的style样式，保留css样式
			$(".security-btn").remove();
		},
		
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
        
        
        trim:function(str){
			 return str.replace(/(^\s+$)/g, "");
		},

        // 允许移动到目标节点前面 即可以将同层最后一个节点放到同层的第一个。
        dropPrev:function(treeId, nodes, targetNode) {
        	return false;
        },
        
        // 设置是否允许移动到同层节点的最后一个节点的后面 从而使被移动的节点成为最后一个节点
        dropNext:function(treeId, nodes, targetNode) {
        	return false;
        },
        
        // 拖拽到目标节点时 设置是否允许成为目标节点的子节点。
        dropInner:function(treeId, nodes, targetNode) {
            return false;
        },

        // 如果是root节点 禁止显示删除按钮
        showRemoveBtn:function(treeId, treeNode){
        	return false;
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
            	html_ += '<input type="text" id="name" name="name"  placeholder="比如：订单信息接口" style="margin-bottom: 10px;" autocomplete="off">'; 
            html_ += '</div>';
            html_ += '<div>';
            html_ += '系统接口名称：';
            	html_ += '<input type="text" id="target" name="target"  placeholder="比如：TEST-PUBLIC-PROCESSOR" style="margin-bottom: 10px;" autocomplete="off">'; 
            html_ += '</div>';
            html_ += '<input type="hidden" name="atype" value="' + parent.atype +'" >';    // 系统接口类型 private:公司内部使用 public:开放给第三方
            
            html_ += '<div>业务处理实现：';
            	html_ += '<input type="text" id="processor" name="processor"  placeholder="比如：publics.example.TestPublicProcessor" style="margin-bottom: 10px;" autocomplete="off">';
            html_ += '</div>';
            html_ += '<div>接口所属工程：';
            	html_ += '<input type="text" id="module" name="module"  placeholder="比如：matrix-api" style="margin-bottom: 10px;" autocomplete="off">';
            html_ += '</div>';
            
            html_ += '<div style="margin-bottom: 10px;"><span style="vertical-align:middle;">接口跨域限制：</span>&nbsp&nbsp';
	            html_ += '<input type="radio" name="domain" value="0" checked onclick="apiInfo.cleanDomainInfo()" style="vertical-align:middle;"> <span style="vertical-align:middle;">不允许</span> &nbsp&nbsp';
	            html_ += '<input type="radio" name="domain"  value="1" onclick="apiInfo.openDomainDialog()" style="vertical-align:middle;"> <span style="vertical-align:middle;">允许</span>';
	            html_ += '<input type="hidden" id="domain-list" name="domainList"  value="">';
	            html_ += '<input type="hidden" id="domain-content-list" name="domainContentList"  value="">';
            html_ += '</div>';
            
            html_ += '<div style="margin-bottom: 10px;"><span style="vertical-align:middle;">接口登录限制：</span>&nbsp&nbsp';
	            html_ += '<input type="radio" name="login" value="0" style="vertical-align:middle;"> <span style="vertical-align:middle;">免登录访问</span> &nbsp&nbsp';
	            html_ += '<input type="radio" name="login" value="1" style="vertical-align:middle;" checked> <span style="vertical-align:middle;">登录后访问</span>';
            html_ += '</div>';
            
            if(!flag){
            	html_ += '<div style="margin-bottom: 10px;"><span style="vertical-align:middle;">系统接口熔断：</span>&nbsp&nbsp';
		            html_ += '<input type="radio" name="discard" value="1" checked onclick="apiInfo.openDiscardWarning(this)" style="vertical-align:middle;"> <span style="vertical-align:middle;">恢复启用</span> &nbsp&nbsp';
		            html_ += '<input type="radio" name="discard"  value="0" onclick="apiInfo.openDiscardWarning(this)" style="vertical-align:middle;"> <span style="vertical-align:middle;">立刻熔断</span>';
	            html_ += '</div>';
	            html_ += '<input type="hidden" name="id" value="' + treeNode.id +'" >';
            }
            
            html_ += '<textarea cols="80" rows="5" id="dto-info" name="dtoInfo"  class="longinput "  placeholder="请求Json描述" style="margin-bottom: 10px;"></textarea><br/>';
            
            html_ += '<textarea cols="80" rows="5" maxlength="260"  id="remark" name="remark"  class="longinput "  placeholder="备注信息描述" style="margin-bottom: 10px;"></textarea><br/>';
            html_ += '<input type="hidden" name="parentId" value="' + treeNode.parentId +'" >';
            if(flag) {
            	var preNode = treeNode.getPreNode();   // seqnum  需要计算同层所有节点，然后得出顺序码
            	var seqnum_ = 1;
            	if(preNode != null){        // && typeof(preNode.seqnum) != "undefined"
            		seqnum_ = preNode.seqnum + 1;
            	} 
            	html_ += '<input type="hidden" name="seqnum" value="' + seqnum_ +'" >'; 
            }
            html_ += '<button class="security-btn layui-btn layui-btn-radius" key="api_tree:submit" onclick="apiInfo.addOrUpdate(\'' + url_ +'\')"> 提 交 </button>'
            $("#tree-node-edit").append(html_);
            
            if(treeNode.name != "新建结点") {
            	html_ = '<button class="security-btn layui-btn layui-btn-radius" key="api_tree:remove" onclick="apiInfo.removeApi()" style="margin-left:20px"> 删除 </button>'
            	html_ += '<button class="security-btn layui-btn layui-btn-radius" key="api_tree:test" onclick="apiInfo.openTestDialog(this)" style="margin-left:20px"> 测试 </button>'
        		$("#tree-node-edit").append(html_);
            	var data_ = {target:treeNode.target};
            	var api_ = JSON.parse(ajaxs.sendAjax('post' , apiInfo.path + 'ajax_api_info_find.do' , data_));  
            	if(api_.status == 'success'){
            		apiInfo.drawApiEdit(api_);  
            	}else{
            		layer.alert(api_.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
            	}
            }
            
            if(window.parent.layui.setter.pageBtns.size != 0){
        		apiInfo.security(window.parent.layui.setter.pageBtns);
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
    				left:($(window.parent).width() - $(dialogId).width())/2 + 'px',
    				width:$(dialogId).width()+'px',
    				height:350,
    				top:($(window.parent).height()-$(dialogId).height())/2 + 'px',
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
				layer.alert( obj.msg , {title:'操作成功 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4} , function(a){
					var zTree = apiInfo.zTree;
	            	var parent = zTree.getNodeByTId(apiInfo.currentNode.parentTId);
	            	var e = obj;
	            	
	            	zTree.removeNode(apiInfo.currentNode);
	            	
                    var new_ = { // 节点元素重新追加
                        id : e.id,
                        pId : e.parentId,
                        name: e.name ,
                        target:e.target,
                        dtoInfo:e.dtoInfo,
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
                    
                    var node = zTree.getNodeByParam("id", e.id, null);
                    zTree.selectNode(node);
                    apiInfo.currentNode = node
                    
                    layer.close(a);
        		});
				
			}else{
				layer.alert( obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
			}
        },
        
        // 删除一条记录
        removeApi : function(){
        	var node = apiInfo.currentNode;
        	var data_ = {
        			id : node.id
        	};
        	var url_ = apiInfo.path + "ajax_api_info_remove.do";
            var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
			if(obj.status == 'success'){
				layer.alert( obj.msg , {title:'操作成功 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4} , function(a){
					var zTree = apiInfo.zTree;
	            	zTree.removeNode(node);
	            	apiInfo.currentNode = null;
	            	$($("#tree-node-edit")[0].childNodes).remove();
                    
                    layer.close(a);
        		});
				
			}else{
				layer.alert( obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
			}
        },
        
        openDiscardWarning:function(o){
        	if($(o).attr("checked")){
        		return ;
        	}
        	
        	var node = apiInfo.currentNode;
        	var url_ = apiInfo.path + "ajax_api_info_discard.do";
        	var val_ = $(o).val();
        	if($(o).val() == 0){
        		var msg = '警告! 选择此项并点击提交按钮后, 系统接口将会立刻熔断! 所有对此接口的访问都会失效!'
        		layer.confirm(msg , { title:'高风险操作 !', icon:7, skin: 'layui-layer-molv', anim:4 , btn : [ '确定', '取消' ] }, 
					function(index , ele) {  // 确定按钮
	                	var data_ = {
	                			id : node.id,
	                			discard : 0
	                	};
	                    var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
	        			if(obj.status == 'success'){
	        				layer.alert( obj.msg , {title:'操作成功 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4} , function(a){
	        					var zTree = apiInfo.zTree;
	        					var node_ = zTree.getNodeByParam("id", node.id, null);
	        					node_.discard = 0;
	        					zTree.updateNode(node_);
	        					zTree.selectNode(node_);
	        					
	                            layer.close(a);
	                            layer.close(index);
	                		});
	        			}else{
	        				layer.alert( obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
	        			}
					}, 
					function(index , ele) {  // 取消按钮  
						// 返回以前的选择状态
						$("input[name='discard']").removeAttr("checked");
						$("input[name='discard'][value='1']").prop("checked","checked");
						layer.close(index);
					}
				);
        	}else{
        		var msg = '选择此项并点击提交按钮后, 系统接口将会恢复使用! 如果接口曾因为风险被关闭,请仔细确认并核对后开启!'
        		layer.confirm(msg , { title:'高风险操作 !', icon:7, skin: 'layui-layer-molv', anim:4 , btn : [ '确定', '取消' ] }, 
					function(index , ele) {  // 确定按钮
	        			var data_ = {
	                			id : node.id,
	                			discard : 1
	                	};
	                    var obj = JSON.parse(ajaxs.sendAjax('post' , url_ , data_));
	        			if(obj.status == 'success'){
	        				layer.alert( obj.msg , {title:'操作成功 !' , icon:1, skin: 'layui-layer-molv' ,closeBtn:0, anim:4} , function(a){
	        					var zTree = apiInfo.zTree;
	        					var node_ = zTree.getNodeByParam("id", node.id, null);
	        					node_.discard = 1;
	        					zTree.updateNode(node_);
	        					zTree.selectNode(node_);
	        					
	                            layer.close(a);
	                            layer.close(index);
	                		});
	        			}else{
	        				layer.alert( obj.msg , {title:'系统提示 !' , icon:5, skin: 'layui-layer-molv' ,closeBtn:0, anim:4});
	        			}
					}, 
					function(index , ele) {  // 取消按钮
						// 返回以前的选择状态
						$("input[name='discard']").removeAttr("checked");
						$("input[name='discard'][value='0']").prop("checked","checked");
						layer.close(index);
					}
				);
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
        	$("input[name='login'][value='" + o.login + "']").attr("checked","checked");
        	$("input[name='discard']").removeAttr("checked");
        	$("input[name='discard'][value='" + o.discard + "']").prop("checked","checked");
        	$("#dto-info").val(o.dtoInfo ); 
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
        
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////Api测试相关功能//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 显示测试弹窗列表
        openTestDialog : function(){
        	var dialogId = "#api-test-dialog";
    		apiInfo.drawTestDialog();
    		window.parent.$.blockUI({
    			showOverlay:true ,
    			css:{
    				cursor:'auto',
    				left:($(window.parent).width() - $(dialogId).width())/2 + 'px',
    				width:$(dialogId).width()+'px',
    				height:800,
    				top:($(window.parent).height()-$(dialogId).height())/2 + 'px',
    				position:'fixed', //居中
    				textAlign:'left',
    				border: '3px solid #FB9337'   // 边界,
    			},
    			message: $(dialogId),
    			fadeIn: 500,//淡入时间
    			fadeOut: 1000  //淡出时间
    		});
        },
        
        // 绘制测试弹窗中的数据	serviceUrl		
        drawTestDialog : function(){
        	var n = apiInfo.currentNode; 
        	var serviceUrl = n.getParentNode().serviceUrl;
        	$("#api-target").val(n.target);
        	$("#service-url").val(serviceUrl);
        	$("#request-time").val(new Date().format("yyyy-MM-dd hh:mm:ss"));
        	$("#json-response").val(""); 
//        	apiInfo.apiServiceUrl = serviceUrl + 'matrix/api.do';
        	// 在后续设计中，开始分离API入口，形成网关矩阵；故这里需要显示指定访问的URL，比如：https://api-cloud.300.cn/mip-web/matrix/api.do  2019-03-21 - Yangcl
        	apiInfo.apiServiceUrl = serviceUrl;  //  + 'matrix/api.do'    
        	apiInfo.findRequestDto(n.target);
        },
        
        // 请求数据头部封装
        requestHeadInit : function(target_){
        	var requester = new Object();
			var head_ = new Object();
			head_.target = target_; 
//			head_.accessToken = $("#access-token").val();
			head_.client = $("input[name='client']:checked").val();
			head_.version = $("#version").val();
			head_.requestTime = $("#request-time").val();
			head_.channel = $("#channel").val();
			head_.key = $("input[name='requester']:checked").val(); 
			head_.value = md5(apiInfo.findRequestValue(head_.key) + head_.target + head_.requestTime);   // 模拟md5请求加密
			requester.head = head_; 
			return requester;
        },
        
        // 根据系统接口名称获取请求参数JSON消息体
        findRequestDto : function(target_){
			$("#dto-json-str").val("");  
			if(apiInfo.trim(target_) == ""){
				return;
			}
			var n = apiInfo.currentNode; 
			if(n.dtoInfo != null){
				$("#dto-json-str").val(n.dtoInfo);
				return;
			}
			
			var type_ = 'post';
			var data_ = apiInfo.requestHeadInit("API-COMMON-FIND-DTO"); // 实例化准备请求的公共接口
			data_.target = target_;
			var obj = JSON.parse(ajaxs.sendAjax(type_, apiInfo.apiServiceUrl, {json : JSON.stringify(data_)} ));
			if (obj.status == 'success') {
				$("#dto-json-str").val(JSON.stringify(obj.dto));
			}else{
				if(typeof(obj.msg) != 'undefined' && obj.msg != null){
					malert(obj.msg, '系统提示');
				}
				$("#dto-json-str").val("");  
			}
		},
		
		// 根据请求者的key，找到对应的value
		findRequestValue:function(key){
			var value = '';
			var type_ = 'post';
			var url_ = 'ajax_find_request_value.do';
			var data_ = {key : key};
			var obj = JSON.parse(ajaxs.sendAjax(type_, url_, data_));
			if (obj.status == 'success') {
				value = obj.data;
			}else{
				malert(obj.msg, '系统提示');
				$("#dto-json-str").val("");  
			}
			return value;
		},
		
		// 由于请求的页面试浮层，故这里需要再次封装
		requestHeadDialog : function(target_){
        	var requester = new Object();
			var head_ = new Object();
			head_.target = target_; 
			head_.accessToken = $("#access-token" , parent.document).val();
			head_.client = $("input[name='client']:checked" , parent.document).val();
			head_.version = $("#version" , parent.document).val();
			head_.requestTime = $("#request-time" , parent.document).val();
			head_.channel = $("#channel" , parent.document).val();
			head_.key = $("input[name='requester']:checked" , parent.document).val(); 
			head_.value = md5(apiInfo.findRequestValue(head_.key) + head_.target + head_.requestTime);   // 模拟md5请求加密
			requester.head = head_; 
			return requester;
        },
        
		// 获取请求结果
		getResponseMsg : function(){
			var jsonStr = $("#dto-json-str" , parent.document).val();
			if(apiInfo.trim($("#api-target" , parent.document).val()) == '' || apiInfo.trim(jsonStr) == ''){
				malert('缺少关键请求值!', '系统提示');
			}else{
				var dto = JSON.parse(jsonStr); 
				var type_ = 'post';
				var data_ = apiInfo.requestHeadDialog($("#api-target" , parent.document).val());
				data_.data = dto;
				var obj = JSON.parse(ajaxs.sendAjax(type_, apiInfo.apiServiceUrl, {json : JSON.stringify(data_)} ));
				if (obj.status == 'success') {
					$("#json-response" , parent.document).val(JSON.stringify(obj));
				}else{
					malert(obj.msg, '系统提示');
					$("#json-response" , parent.document).val("");  
				}
			}
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
        	enable:false
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








































