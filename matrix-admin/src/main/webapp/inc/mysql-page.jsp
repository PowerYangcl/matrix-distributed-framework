<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*" %>
<% 
	// http://localhost:8080/springMatrix/
    String bpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	
	//  /user/getListForPage.do?  取出Controller的请求路径 注意user前有斜杠 
	String pageUrl = request.getAttribute( "javax.servlet.forward.servlet_path").toString() + "?"; 
    
    String method = request.getMethod().toUpperCase();   //  GET or POST 获取form的提交方法
    if ("GET".equals(method) && null != request.getQueryString()) {
        String[] values = request.getQueryString().split("&");
        for (int i = 0; i < values.length; i++) {
            if (null != values[i] && values[i].length() > 0
                    && !("pageNum").equals(values[i].substring(0,
                    values[i].indexOf("=")))) {
                pageUrl += values[i] + "&";
            }
        }
    }else if("POST".equals(method) && null != request.getParameterNames()) {
        Enumeration pageEnum = request.getParameterNames(); // request.getParameterNames() 封装了form表单中所要提交的参数
        while (pageEnum.hasMoreElements())   // 
        {
            String paramName = (String) pageEnum.nextElement();
            String[] values = request.getParameterValues(paramName);
            for (int i = 0; i < values.length; i++) {
                if (null != values[i] && values[i].length() > 0 && !("pageNum").equals(paramName) && !("pageSize").equals(paramName)) {
                    pageUrl += paramName + "=" + values[i] + "&";
                }
            }
        }
    } 
    pageUrl = pageUrl.substring(1); // 去掉第一个斜杠 '/'        如 "user/getListForPage.do?address=亮晶晶之家&"

    pageContext.setAttribute("bpath", bpath);
    pageContext.setAttribute("pageUrl", pageUrl);
     
%>

    <script type="text/javascript">

        $(function(){
            var curpage = parseInt('${pageList.pageNum}');
            var pclassAc = 'paginate_active';  // 当前按钮样式类
            var pclassBt = 'paginate_button'; // 非当前样式类
            var pageCount = parseInt('${pageList.pages}');
            var html_ = '';
            if(pageCount > 0 && pageCount < 8){
                for(var i =1 ; i < pageCount+1 ; i++){
                    if(curpage == i){
                        html_ += '<span class="' + pclassAc + '">' + i + '</span>';
                    }else{
                        html_ += '<span class="' + pclassBt + '"  onclick="formPaging(\'' + i +'\')" >' + i + '</span>';
                    }
                }
            }else if(pageCount > 7){
                if(curpage < 5){
                    for(var i =1 ; i < 8 ; i++){
                        if(curpage == i){
                            html_ += '<span class="' + pclassAc + '">' + i + '</span>';
                        }else{
                            html_ += '<span class="' + pclassBt + '"  onclick="formPaging(\'' + i +'\')" >' + i + '</span>';
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
                            html_ += '<span class="' + pclassBt + '"  onclick="formPaging(\'' + arr[i] +'\')" >' + arr[i] + '</span>';
                        }
                    }
                }

            }
            $("#dynamic-page-num").append(html_);
        });

        /**
         * @描述: form 分页与联动|分页专属
         * @作者: Yangcl
         * @时间: 2015-08-20 : 17-20-56
         *
         * @param pn pageNum
         *
         * @原理:
         *              page 	user/getListForPage.do?address=狗屌亮&&cqdtpage=1
         *              path 	http://localhost:8080/springMatrix/
         *              actions = path + page = http://localhost:8080/springMatrix/user/getListForPage.do?address=狗屌亮&&curpage=1
         */
        function formPaging(pn){
        	var ps = $("#select-page-size").val();
            var actions = '${bpath}' + '${pageUrl}&pageNum=' + pn +'&pageSize=' + ps;
            $('#page-form').attr("action", actions);
            $("#page-form").submit();
        }


    

    </script>

    <div id="a1" class="dataTables_info" >
        当前第${pageList.pageNum }页|正在显示 ${pageList.startRow } 到 ${pageList.endRow } 条|共 <span style="color:#ff0000">${pageList.pages} </span>页 ${pageList.total} 条记录
    </div>
    <div id="a2" class="dataTables_paginate paging_full_numbers" >
        <span id="first-page" class="first paginate_button paginate_button_disabled"
                onclick="formPaging('1')">
            首页
        </span>
        <c:if test="${!pageList.isFirstPage}"> <!-- 如果是第一页，则只显示下一页、尾页 -->
            <span id="previous-page" class="previous paginate_button paginate_button_disabled"
                 onclick="formPaging('${pageList.prePage}')">
                上一页
            </span>
        </c:if>
        <span id="dynamic-page-num">
            <%-- 等待追加节点 --%>
        </span>
        <c:if test="${!pageList.isLastPage}"> <!-- 如果是末页，则只显示上一页 -->
	        <span id="next-page" class="next paginate_button"  
	        	onclick="formPaging('${pageList.nextPage}')">
	        	下一页
        	</span>
	        <span id="last-page" class="last paginate_button"
                onclick="formPaging('${pageList.pages}')"   >
                末页
            </span>
        </c:if>
    </div>


























