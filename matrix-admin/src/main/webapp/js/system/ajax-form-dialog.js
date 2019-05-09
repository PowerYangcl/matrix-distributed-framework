/**
 *
 *@description:  ajax-弹窗分页控件 - Yangcl
 * 		dForm equals "dialog form" 
 * 
 * 	    原理：父页面(home.jsp)内嵌了子页面(iframe)，BlockUI控件和dForm控件被引入在home.jsp中。
 * BlockUI会将div弹窗的html代码块剥离到home.jsp中(这些代码是写在iframe中的)，这就造成了js脚本
 * 还在iframe中，但是html元素已经脱离了iframe，进入到了home.jsp中。
 * 也就是说：
 * 		第一种情况：弹框中的html文档还在iframe中，没有被剥离到父页面中(首次初始化)。这就需要由
 * 								父页面来调用iframe中的html元素，$("#sub-page").contents()将返回子页面的jquery
 * 								对象，代码段this.iframe_ = $("#sub-page").contents(); 即为此意。
 * 								这种情况是父页面调用子页面对象。
 * 
 * 		第二种情况：弹框中的html文档已经被剥离到父页面中，由formPaging()方法来响应上一页、下一页
 * 								首页、末页这些按钮事件。此时已经初始化完成。formPaging()所回调的方法在iframe
 * 								中，故被回调的方法需要使用【window.parent.dForm】
 * 								比如：window.parent.dForm.launch(url_, 'dialog-table-form', obj).init();
 * 								因为dForm控件被引入在home.jsp，这种情况是子页面调用父页面对象。
 * 
 * 
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年2月24日 上午10:26:08 
 * @version 1.0.0.1
 */
var dForm = {

    jsonObj:null,
    url:'',
    formId:'',
    callName:null,
    iframe_:null,

    launch : function(url , formId , obj){
        this.url = url;
        this.formId = formId;
        this.jsonObj = obj;
        this.iframe_ = $("#sub-page").contents(); 
        return dForm;
    },

    /**
     * 实例化分页html部分
     *
     * @param formId formId 一般写定为 ‘table-form’ 如：示例ajaxFormExample.jsp中 < div id="table-form" class="dataTables_wrapper" >
     * @param pageNum
     * @param startRow
     * @param endRow
     * @param pages
     * @param total
     * @param isFirstPage true or false 是否是第一页
     * @param prePage
     * @param isLastPage true or false 是否是最后一页
     * @param nextPage
     */
    init : function(){
        var formId = this.formId;
        var obj = this.jsonObj.data;
        var pageNum = obj.pageNum;
        var startRow = obj.startRow;
        var endRow = obj.endRow;
        var pages = obj.pages;
        var total = obj.total;
        var isFirstPage = obj.isFirstPage;
        var prePage = obj.prePage;
        var isLastPage = obj.isLastPage;
        var nextPage = obj.nextPage;

        if($(".dialog-page-info").length == 0){
        	// 此时弹框中的html文档还在iframe中，没有被剥离到父页面中。
        	this.iframe_.find(".dialog-page-info").remove();
        }else{
        	// 已经被父页面托管BlockUI弹出层
        	$(".dialog-page-info").remove();
        }
        

        var html_ =
            '<div id="d1" class="dataTables_info dialog-page-info" >'
            +'当前第' + pageNum +'页|正在显示 ' + startRow +' 到 ' + endRow + ' 条|共 <span style="color:#ff0000">' + pages + ' </span>页 ' + total + ' 条记录'
            +'</div>'
            +'<div id="d2" class="dataTables_paginate paging_full_numbers dialog-page-info" >'
            +'<span id="dialog-first-page" class="first paginate_button paginate_button_disabled" onclick="dForm.formPaging(1)">'
            +'首页'
            +'</span>';

        if(!isFirstPage){ // 如果是第一页，则只显示下一页、尾页
            html_ += '<span id="dialog-previous-page" class="previous paginate_button paginate_button_disabled" onclick="dForm.formPaging(\'' + prePage + '\')">'
            +'上一页'
            +'</span>';
        }
        html_ += '<span id="dialog-dynamic-page-num"></span>';
        if(!isLastPage){ // 如果是末页，则只显示上一页
            html_ += '<span id="dialog-next-page" class="next paginate_button"   onclick="dForm.formPaging(\'' + nextPage + '\')">'
            +'下一页'
            +'</span>'
            +'<span id="dialog-last-page" class="last paginate_button" onclick="dForm.formPaging(\'' + pages +'\')" >'
            +'末页'
            +'</span>';
        }
        html_ += '</div>';
        
        if($("#" + formId).length == 0){
        	this.iframe_.find("#" + formId).append(html_);
        }else{
        	$("#" + formId).append(html_);
        }
        
        dForm.oneToSeven(pageNum , pages);
        return dForm;
    },

    /**
     * 上一页与下一页中间的7个数字的切换与颜色变换
     * @param pageNum
     * @param pages
     */
    oneToSeven : function(pageNum , pages ){
        var curpage = parseInt(pageNum);
        var pclassAc = 'paginate_active';       // 当前按钮样式类
        var pclassBt = 'paginate_button';       // 非当前样式类
        var pageCount =  parseInt(pages) ;
        var html_ = '';
        if(pageCount > 0 && pageCount < 8){
            for(var i =1 ; i < pageCount+1 ; i++){
                if(curpage == i){
                    html_ += '<span class="' + pclassAc + '">' + i + '</span>';
                }else{
                    html_ += '<span class="' + pclassBt + '"  onclick="dForm.formPaging(\'' + i +'\')" >' + i + '</span>';
                }
            }
        }else if(pageCount > 7){
            if(curpage < 5){
                for(var i =1 ; i < 8 ; i++){
                    if(curpage == i){
                        html_ += '<span class="' + pclassAc + '">' + i + '</span>';
                    }else{
                        html_ += '<span class="' + pclassBt + '"  onclick="dForm.formPaging(\'' + i +'\')" >' + i + '</span>';
                    }
                }
            }else{
                var arr = new Array();
                if((pageCount - curpage) < 7 ){ // 最后7页
                    for(var i = 0 ; i < 7 ; i ++){
                        arr[i] = pageCount - 6 + i;
                    }
                }else{
                    arr[0] = curpage - 3;
                    arr[1] = curpage - 2;
                    arr[2] = curpage - 1;
                    arr[3] = curpage;
                    arr[4] = curpage + 1;
                    arr[5] = curpage + 2;
                    arr[6] = curpage + 3;
                }
                for(var i = 0 ; i < arr.length ; i ++){
                    if(curpage == arr[i]){
                        html_ += '<span class="' + pclassAc + '">' + arr[i] + '</span>';
                    }else{
                        html_ += '<span class="' + pclassBt + '"  onclick="dForm.formPaging(\'' + arr[i] +'\')" >' + arr[i] + '</span>';
                    }
                }
            }

        }
        if($("#dialog-dynamic-page-num").length == 0){
        	this.iframe_.find("#dialog-dynamic-page-num").append(html_);
        }else{
        	$("#dialog-dynamic-page-num").append(html_);
        }
    },

    /**
     * 在父页面，触发分页事件
     * @param pn
     */
    formPaging : function(pn){
        var parse = dForm.parseUrl(this.url);
        var url = parse.protocol + '://' + parse.host + ':' + parse.port + parse.path;
        var s = parseInt($("#" +  this.formId + " > .dialog-show-count > label > select").val());
        if(isNaN(s)){
        	alert(s);
            s = 10;
        }
        var actions = url + '?pageNum=' + pn +'&pageSize=' + s;
        if(this.callName != null && (typeof this.callName=="function")){
            this.callName(actions); // 所回调的方法在iframe中，故被回调的方法需要使用【window.parent.dForm】比如：window.parent.dForm.launch(url_, 'dialog-table-form', obj).init();
            pageInit.security();
        }
    },

    // 绘制表单
    drawForm : function(callback){
        callback();
        eval("this.callName = callback;");
        dForm.security();
        return dForm;
    } ,

    /**
     * 强效解析地址信息
     * @param url
     * @returns {{source: *, protocol: string, host: string, port: (Function|string|c.validator.methods.remote.port|*), query: (Function|string|ui.autocomplete.search|search), params, file: (string|*), hash: string, path: string, relative: (*|string), segments: (Array|string[])}}
     */
    parseUrl : function(url) {
        var a =  document.createElement('a');
        a.href = url;
        return {
            source: url,
            protocol: a.protocol.replace(':',''),
            host: a.hostname,
            port: a.port,
            query: a.search,
            params: (function(){
                var ret = {},
                    seg = a.search.replace(/^\?/,'').split('&'),
                    len = seg.length, i = 0, s;
                for (;i<len;i++) {
                    if (!seg[i]) { continue; }
                    s = seg[i].split('=');
                    ret[s[0]] = s[1];
                }
                return ret;
            })(),
            file: (a.pathname.match(/\/([^\/?#]+)$/i) || [,''])[1],
            hash: a.hash.replace('#',''),
            path: a.pathname.replace(/^([^\/])/,'/$1'),
            relative: (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [,''])[1],
            segments: a.pathname.replace(/^\//,'').split('/')
        };
    },

    // 在父页面实例化iframe子页面中的按钮权限，此时弹框中的html文档还在iframe中，没有被剥离到父页面中。
    security:function(){
		if( typeof(localStorage.btns) != "undefined" && localStorage.btns.length != 0){
			var barr = localStorage.btns.split(",");
			for(var i = 0 ; i < barr.length ; i ++){
				var key = barr[i].split("@")[1]; 
				this.iframe_.find(".security-btn[key='" + key + "']").show();
				this.iframe_.find(".security-btn[key='" + key + "']").removeClass("security-btn"); 
			}
			this.iframe_.find(".security-btn").remove();
		}
	},

}






