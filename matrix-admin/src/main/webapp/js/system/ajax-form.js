/**
 *
 * ajax 分页 - Yangcl
 * 2016-08-18
 *
 * javascript 回调函数
 * http://blog.csdn.net/luoweifu/article/details/41466537
 *
 * http://blog.sina.com.cn/s/blog_601b97ee0101f1rc.html
 *
 *
 */
var aForm = {

    jsonObj:null,
    url:'',
    formId:'',
    callName:null,

    launch : function(url , formId , obj){
        this.url = url;
        this.formId = formId;
        this.jsonObj = obj;
        return aForm;
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

        $(".page-info").remove();

        var html_ =
            '<div id="a1" class="dataTables_info page-info" >'
                +'当前第' + pageNum +'页|正在显示 ' + startRow +' 到 ' + endRow + ' 条|共 <span style="color:#ff0000">' + pages + ' </span>页 ' + total + ' 条记录'
            +'</div>'
            +'<div id="a2" class="dataTables_paginate paging_full_numbers page-info" >'
                +'<span id="first-page" class="first paginate_button paginate_button_disabled" onclick="aForm.formPaging(1)">'
                    +'首页'
                +'</span>';

        if(!isFirstPage){ // 如果是第一页，则只显示下一页、尾页
            html_ += '<span id="previous-page" class="previous paginate_button paginate_button_disabled" onclick="aForm.formPaging(\'' + prePage + '\')">'
                                +'上一页'
                            +'</span>';
        }
        html_ += '<span id="dynamic-page-num"></span>';
        if(!isLastPage){ // 如果是末页，则只显示上一页
            html_ += '<span id="next-page" class="next paginate_button"   onclick="aForm.formPaging(\'' + nextPage + '\')">'
                                +'下一页'
                        +'</span>'
                        +'<span id="last-page" class="last paginate_button" onclick="aForm.formPaging(\'' + pages +'\')" >'
                            +'末页'
                        +'</span>';
        }
        html_ += '</div>';
        $("#" + formId).append(html_);
        aForm.oneToSeven(pageNum , pages);

        return aForm;
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
                    html_ += '<span class="' + pclassBt + '"  onclick="aForm.formPaging(\'' + i +'\')" >' + i + '</span>';
                }
            }
        }else if(pageCount > 7){
            if(curpage < 5){
                for(var i =1 ; i < 8 ; i++){
                    if(curpage == i){
                        html_ += '<span class="' + pclassAc + '">' + i + '</span>';
                    }else{
                        html_ += '<span class="' + pclassBt + '"  onclick="aForm.formPaging(\'' + i +'\')" >' + i + '</span>';
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
                        html_ += '<span class="' + pclassBt + '"  onclick="aForm.formPaging(\'' + arr[i] +'\')" >' + arr[i] + '</span>';
                    }
                }
            }

        }
        $("#dynamic-page-num").append(html_);
    },

    /**
     * 触发分页事件
     * @param pn
     */
    formPaging : function(pn){
        var parse = aForm.parseUrl(this.url);
        var url = parse.protocol + '://' + parse.host + ':' + parse.port + parse.path;
        var s = parseInt($("#" +  this.formId + " > .dialog-show-count > label > select").val());
        if(isNaN(s)){
            s = 10;
        }
        var actions = url + '?pageNum=' + pn +'&pageSize=' + s;
        if(this.callName != null && (typeof this.callName=="function")){
            this.callName(actions);
            pageInit.security();
        }
    },

    // 绘制表单
    drawForm : function(callback){
        callback();
        eval("this.callName = callback;");
        return aForm;
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
    }
}












