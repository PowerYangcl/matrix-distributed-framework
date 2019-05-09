<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- <html xmlns="http://www.w3.org/1999/xhtml">
<head> -->
    <%@ include file="/inc/iframe-head.jsp" %>
    <style type="text/css">

        .form-search,td,th{
            border-spacing: 0px;
        }
        .form-search td,th{
            padding-bottom: 3px;
            padding-top: 3px;
        }
        .tdright{
            text-align: right;
            width: 100px;
        }
        .tdleft{
            padding-left: 5px;
            width: 300px;
        }

        .form-search input[type=text] {
            padding: 8px 5px;
            border: 1px solid #ccc;
            width: 85%;
            -moz-border-radius: 2px;
            -webkit-border-radius: 2px;
            border-radius: 2px;
            background: #fcfcfc;
            vertical-align: middle;
            -moz-box-shadow: inset 0 1px 3px #ddd;
            -webkit-box-shadow: inset 0 1px 3px #ddd;
            box-shadow: inset 0 1px 3px #ddd;
            color: #666;
        }

    </style>
    <script type="text/javascript">
        $(function(){
            $('#accordion').accordion();
        })

        function searchInfo(){
            var type_ = 'post';
            var url_ = '${basePath}kjt/queryKjtlog.do';
            var data_ = $("#search-form").serializeArray();
            var obj = JSON.parse(ajaxs.sendAjax(type_ , url_ , data_));
            if(obj.entity != 'entity-null'){
                initEntity(obj.entity, true);
                $("#picurl").attr('href' , obj.entity.picurl );
                $("#picurl")[0].innerHTML=' 点 击 查 看 ';
                if(obj.size == '1'){
                    $("#q-desc")[0].innerHTML='';
                }else{
                	$("#q-desc")[0].innerHTML=' - 问题订单 - 数目：' + obj.size;
                }
            }else{
                initEntity(null, false);
                $("#picurl").attr('href' , 'javascript:void(0)');
                $("#picurl")[0].innerHTML=' 该商品无主图 ';
                $("#q-desc")[0].innerHTML='';
            }

            if(obj.logList != 'log-list-null'){
                draw(obj.logList);
            }else{
                draw('');
            }
        }

        function initEntity(obj , flag){
            var ids = $("#product-info input[type=text]");
            for(var i = 0 ; i < ids.length ; i ++){
                var id = ids[i].id;
                if(flag){
                    for(var o in obj){
                        if(id === o){
                            $("#" + id).val(obj[o]);
                        }
                    }
                }else{
                    $("#" + id).val("");
                }
            }
        }

        function draw(list){
            $('#ajax-tbody-1 tr').remove();
            var html_ = '';
            if(list.length>0){
                for(var i = 0 ; i < list.length ; i ++){
                    html_ += '<tr id="tr-' + list[i].zid + '" class="gradeX">'
                    +'<td>' + list[i].uid + '</td>'
                    +'<td>' + list[i].rsyncTarget + '</td>'
                    +'<td>' + list[i].rsyncUrl + '</td>'
                    +'<td>' + list[i].requestTime + '</td>'
                    +'<td align="center" style="width: 420px">'
                        + '<textarea cols="" rows="" style="height: 100px;width: 400px">' + list[i].requestData + '</textarea></td>'
                    +'<td align="center" style="width: 420px">'
                        + '<textarea cols="" rows="" style="height: 100px;width: 400px">' + list[i].responseData + '</textarea></td>'
                    +'</tr>'
                }
            }else{
                html_='<tr><td colspan="11" style="text-align: center;">没有可以显示的日志记录</td></tr>';
            }

            $('#ajax-tbody-1').append(html_);
        }

    </script>
</head>

<%-- <body class="withvernav">

<div class="bodywrapper">
    <%@ include file="/inc/top.jsp" %>
    <%@ include file="/inc/left.jsp" %> --%>

    <div class="centercontent tables">
        <div class="pageheader notab">
            <h1 class="pagetitle">实际样本-A 日志查询与数据对比 </h1>
                    <span class="pagedesc">
                        定位问题订单
                    </span>
            <span style="display:none">jsp/example/reality/validate.jsp</span>
        </div>

        <div id="contentwrapper" class="contentwrapper">
            <div id="table-form" class="dataTables_wrapper" >
                <%-- 查询条件 --%>
                <div class="contenttitle2" style="display: block">
                    <div class="widgetbox" style="width: 800px">
                        <div class="title">
                            <h3>输入查询条件</h3>
                        </div>
                        <div class="widgetcontent padding0 statement">
                            <form id="search-form">
                                <table class="form-search" style="width: 1000px;">
                                    <tbody>
                                    <tr>
                                        <td class="tdright">接口标识</td>
                                        <td class="tdleft">
                                            <input type="text" name="rsyncTarget" placeholder="如：Order.SOCreate">
                                        </td>
                                        <td class="tdright">请求时间</td>
                                        <td class="tdleft">
                                            <input type="text" name="requestTime" placeholder="如：2016-08-08 00:00:00">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdright">请求关键字</td>
                                        <td class="tdleft">
                                            <input type="text" name="requestData" placeholder="如：订单编号 DD4555980102">
                                        </td>
                                        <td class="tdright">响应关键字</td>
                                        <td class="tdleft">
                                            <input type="text" name="responseData" placeholder="如：商品编号 822DEH01m070002 ">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="tdright">订单编号</td>
                                        <td class="tdleft"> 
                                            <input type="text" name="orderCode" placeholder="如：DD4555980102">
                                        </td>
                                        <td class="tdright">跨境商户</td>
                                        <td class="tdleft">
                                            <!-- <input type="text" name="sellerCode" placeholder="如：DD4555980102"> -->
                                            <select id="seven-status" name="sellerCode" class="radius3" style="margin-left:0px; width:370px">
											    <option value="2222">跨境通</option>
											    <option value="4444">民生品粹</option>
											</select>
                                        </td>
                                    </tr>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th colspan="4" style="padding-bottom: 0px; margin-right: 50px;   text-align: right">
                                            <a href="javascript:void(0)" class="btn btn_orange btn_search radius50 security-btn" key="btn-55ee0a123a05484d8cc22235b709c2ff" onclick="searchInfo()">
                                                <span>搜索日志信息</span>
                                            </a>
                                        </th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>


                <div id="accordion" class="accordion ui-accordion ui-widget ui-helper-reset ui-accordion-icons" role="tablist">
                    <h3 class="ui-accordion-header ui-helper-reset ui-state-default ui-corner-all" role="tab" aria-expanded="false" aria-selected="false" tabindex="-1">
                        <span class="ui-icon ui-icon-triangle-1-e"></span>
                        <a href="#">订单详细信息<span id="q-desc"></span></a>
                    </h3>
                    <div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom" role="tabpanel" style="height: 176px; display: none; overflow: hidden; padding-top: 10px; padding-bottom: 10px;">
                        <table id="product-info" class="form-search" style="width: 1000px;">
                            <tbody>
                                <tr>
                                    <td class="tdright">product code</td>
                                    <td class="tdleft">
                                        <input type="text" id="productCode" >
                                    </td>
                                    <td class="tdright">sku code</td>
                                    <td class="tdleft">
                                        <input type="text" id="skuCode" >
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tdright">商品上架状态</td>
                                    <td class="tdleft">
                                        <input type="text" id="productStatus" >
                                    </td>
                                    <td class="tdright">sku name</td>
                                    <td class="tdleft">
                                        <input type="text" id="skuName" >
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tdright">sku price</td>
                                    <td class="tdleft">
                                        <input type="text" id="skuPrice">
                                    </td>
                                    <td class="tdright">产品数量(sku num)</td>
                                    <td class="tdleft">
                                        <input type="text" id="skuNum">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tdright">仓库编号_数量</td>
                                    <td class="tdleft">
                                        <input type="text" id="storeCode" >
                                    </td>
                                    <td class="tdright">赠品标示 1：非赠品  0：赠品</td>
                                    <td class="tdleft">
                                        <input type="text" id="giftFlag">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tdright">成本价(cost price)</td>
                                    <td class="tdleft">
                                        <input type="text" id="costPrice" >
                                    </td>
                                    <td class="tdright">税率(oc_orderdetail 的 tax_ate)</td>
                                    <td class="tdleft">
                                        <input type="text" id="taxRate">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tdright">跨境通商品编号</td>
                                    <td class="tdleft">
                                        <input type="text" id="kjtCode">
                                    </td>
                                    <td class="tdright">商品名称(pc_productinfo)</td>
                                    <td class="tdleft">
                                        <input type="text" id="productName">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tdright">productShortName</td>
                                    <td class="tdleft">
                                        <input type="text" id="productShortName">
                                    </td>
                                    <td class="tdright">sellerCode</td>
                                    <td class="tdleft">
                                        <input type="text" id="sellerCode">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tdright">smallSellerCode</td>
                                    <td class="tdleft">
                                        <input type="text" id="smallSellerCode">
                                    </td>
                                    <td class="tdright">商品重量</td>
                                    <td class="tdleft">
                                        <input type="text" id="weight">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tdright">product create time</td>
                                    <td class="tdleft">
                                        <input type="text" id="productCreateTime">
                                    </td>
                                    <td class="tdright">product update time</td>
                                    <td class="tdleft">
                                        <input type="text" id="productUpdateTime">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="tdright">product cost price</td>
                                    <td class="tdleft">
                                        <input type="text" id="productCostPrice">
                                    </td>
                                    <td class="tdright">product tax rate</td>
                                    <td class="tdleft">
                                        <input type="text" id="productTaxRate">
                                    </td>
                                </tr>

                                <tr>
                                    <td class="tdright">商品主图</td>
                                    <td class="tdleft">
                                        <a id="picurl" style="color:#FB9337" target="_blank" href="javascript:void(0)" >  点 击 查 看 </a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>



                    </div>


                    <%-- 跨境通 日志信息 --%>
                    <h3 class="ui-accordion-header ui-helper-reset ui-state-default ui-corner-all" role="tab" aria-expanded="false" aria-selected="false" tabindex="-1">
                        <span class="ui-icon ui-icon-triangle-1-e"></span>
                        <a href="#">日志记录</a>
                    </h3>
                    <div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content-active"
                         role="tabpanel" style="height: 176px; display: block; overflow: hidden; padding-top: 10px; padding-bottom: 10px;overflow:auto;">
                        <div id="dyntable2_length" class="dataTables_length">
                            <label>
                                当前显示
                                <select id="select-page-size" size="1" name="dyntable2_length" onchange="aForm.formPaging('1')">
                                    <option value="40">40</option>
                                </select>
                                条记录
                            </label>
                        </div>
                        <table id="dyntable2" cellpadding="0" cellspacing="0" border="0" class="stdtable">
                            <thead>
                            <tr>
                                <th class="head0 "style="width: 150px">UUID</th>
                                <th class="head0 " style="width: 150px">接口标识</th>
                                <th class="head0 "style="width: 150px">Url</th>
                                <th class="head0 "style="width: 150px">请求时间</th>
                                <th class="head0 ">请求数据</th>
                                <th class="head0 ">响应数据</th>
                            </tr>
                            </thead>

                            <tbody id="ajax-tbody-1">
                            <%-- 等待填充 --%>
                            </tbody>
                        </table>
                    </div>
                </div>


            </div>
        </div>
    </div>
<!-- </div>

</body>
</html> -->




























