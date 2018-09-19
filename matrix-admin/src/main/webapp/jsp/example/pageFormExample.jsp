<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/inc/head.jsp" %>
    <script type="text/javascript">


        function deleteOne(id_){
            if(confirm('您确定要删除这条记录吗？')){
                var type_ = 'post';
                var url_ = '${basePath}example/deleteOne.do';
                var data_ = {id:id_};
                var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
                if(obj.status == 'success'){
                    alert(obj.msg);
                    $("#tr-" + id_).remove();
                }else{
                    alert(obj.msg);
                }
            }

        }

    </script>
</head>

<body class="withvernav">

    <div class="bodywrapper">
        <%@ include file="/inc/top.jsp" %>
        <%@ include file="/inc/left.jsp" %>

        <div class="centercontent tables">
            <!--这个跳转页面的功能及跳转路径等等-->
            <div class="pageheader notab">
                <h1 class="pagetitle">常用分页表单示例</h1>
                    <span class="pagedesc">
                        这个页面描述了系统中最常用的功能：分页表单。该页面所在的路径已经隐藏在下面
                        【鼠标右键】 -> 【审查元素】可以看到如下路径【jsp/example/pageFormExample】
                    </span>
                <span style="display:none">jsp/example/pageFormExample</span>
            </div>

            <div id="contentwrapper" class="contentwrapper">

                <div id="dyntable2_wrapper" class="dataTables_wrapper" >
                    <div class="contenttitle2">
                        <h3>Dynamic Table with Checkbox Column</h3>
                    </div>

                    <div id="dyntable2_length" class="dataTables_length dialog-show-count">
                        <label>
                            当前显示
                            <select id="select-page-size" size="1" name="dyntable2_length" onchange="formPaging('1')">
                                <option value="10"  <c:if test="${pageList.pageSize == 10}">selected="selected"</c:if>>10</option>
                                <option value="25"  <c:if test="${pageList.pageSize == 25}">selected="selected"</c:if>>25</option>
                                <option value="50" <c:if test="${pageList.pageSize == 50}">selected="selected"</c:if>>50</option>
                                <option value="100" <c:if test="${pageList.pageSize == 100}">selected="selected"</c:if>>100</option>
                            </select>
                            条记录
                        </label>
                    </div>
                    <div id="dyntable2_filter"  class="dataTables_filter" >
                        <label>
                            Search: <input type="text" id="search-1" class="page-form-search">
                        </label>
                    </div>

                    <table id="dyntable2" cellpadding="0" cellspacing="0" border="0" class="stdtable">
                        <colgroup>
                            <col class="con0" style="width: 4%"/>
                            <col class="con1"/>
                            <col class="con0"/>
                            <col class="con1"/>
                            <col class="con0"/>
                        </colgroup>
                        <thead>
                            <tr>
                                <th class="head0 nosort">
                                    <input type="checkbox"/>
                                </th>                                                                           <%-- sorting 代表可排序--%>
                                <th class="head0 sorting_asc">ID(升序排序)</th>  <%-- sorting_asc 代表升序排列--%>
                                <th class="head1 sorting_desc"> 姓名(降序排序)</th>   <%-- sorting_desc 代表降序排列--%>
                                <th class="head0 sorting">手机(s)</th>
                                <th class="head1 sorting">身份证号</th>
                                <th class="head0 sorting">E-mail</th>
                                <th class="head1 " width="100px">操作</th>
                            </tr>
                        </thead>

                        <tfoot>
                            <tr>
                                <th class="head0">
                                        <span class="center">
                                            <input type="checkbox"/>
                                         </span>
                                </th>
                                <th class="head0">Rendering engine</th>
                                <th class="head1">Browser</th>
                                <th class="head0">Platform(s)</th>
                                <th class="head1">Engine version</th>
                                <th class="head0">CSS grade</th>
                                <th class="head1">To do it</th>
                            </tr>
                        </tfoot>

                        <tbody>
                            <c:if test="${status == true}">
                                <c:forEach items="${pageList.list}" var="c">
                                    <tr id="tr-${c.id }" class="gradeX">
                                        <td align="center">
                                            <span class="center">
                                                <input type="checkbox"/>
                                            </span>
                                        </td>
                                        <td>${c.id }</td>
                                        <td>${c.userName }</td>
                                        <td>${c.mobile }</td>
                                        <td class="center">${c.idNumber }</td>
                                        <td class="center">${c.email }</td>
                                        <td width="100px" align="center">
                                            <a onclick="deleteOne('${c.id }')" title="删除" class="btn btn3 btn_trash" style="cursor: pointer;"></a>
                                            <a href="${basePath}example/editInfoPage.do?id=${c.id }" title="修改" class="btn btn3 btn_book" style="cursor: pointer;"></a>
                                        </td>
                                    </tr>

                                </c:forEach>
                            </c:if>
                        </tbody>
                    </table>

					<%@ include file="/inc/mysql-page.jsp" %>
                </div>
				<%@ include file="/inc/page-form.jsp" %>
            </div>

        </div>


    </div>

</body>
</html>




























