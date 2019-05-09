<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String js = path + "/js";
	String css = path + "/css";
	String img = path + "/images";
	String resources = path + "/resources";
	
	pageContext.setAttribute("js", js);
	pageContext.setAttribute("css", css);
	pageContext.setAttribute("img", img);
	pageContext.setAttribute("basePath", basePath);
	pageContext.setAttribute("resources", resources);
	
	if(session.getAttribute("kjt-key") == null){
		String url = basePath + "jsp/example/reality/validate.jsp";
		response.sendRedirect(url);
		return;
	}
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- <html xmlns="http://www.w3.org/1999/xhtml">
<head> -->
    <%@ include file="/inc/iframe-head.jsp" %>
    <script type="text/javascript">
        function trim(str) {
            return str.replace(/(^\s+$)/g, "");
        }
    </script>
<%-- </head>

<body class="withvernav">

    <div class="bodywrapper">
        <%@ include file="/inc/top.jsp" %>
        <%@ include file="/inc/left.jsp" %> --%>

        <div class="centercontent tables">
            <!--这个跳转页面的功能及跳转路径等等-->
            <div class="pageheader notab">
                <h1 class="pagetitle">实际样本-B 页面</h1>
                    <span class="pagedesc">
                        实际样本-B 所在的页面样例。 | <a href="${basePath}example/leave.do" style="color: #ff0000">【离开此页面】</a>
                    </span>
                <span style="display:none">jsp/example/reality/index.jsp</span>
            </div>

            <div id="contentwrapper" class="contentwrapper">
                <div id="fuck-one">
                    <div class="contenttitle2">
                        <h3>A  批量拉取商品价格变动信息|接口标识：Product.ProudctInfoBatchGet</h3>
                    </div>

                    <div class="statusbox" style="width: 800px">
                        <form id="json-post" action="#">
                            <div class="status_thumb">商品编号Json串：</div>
                            <div style="padding-right:20px;">
                                <textarea id="json-str" name="" cols="" rows="" style="height: 200px;width: 790px" placeholder='["032JPB03k440001","032JPB03k440001","AZEJPB06k440005"]'></textarea>
                            </div>
                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcOne()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>

                </div>

                <div id="fuck-two">
                    <div class="contenttitle2">
                        <h3>B  拉取指定时间段内商品价格变动信息|接口标识：Product.ProudctInfoBatchGet</h3>
                    </div>

                    <div class="statusbox" style="width: 800px">
                        <form id="date-post" action="#">
                            <div style="padding-right:20px;">
                                <div class="comment_authorimg"  >开始时间</div>
                                <div class="commentcontent" style="padding-right: 12px;margin-left:65px; margin-bottom: 10px">
                                    <input  id="s-time" type="text" value="这是一个示例：2016-08-08 00:00:00">
                                </div>

                                <div class="comment_authorimg">结束时间</div>
                                <div class="commentcontent" style="padding-right: 12px;margin-left:65px;">
                                    <input  id="e-time" type="text" placeholder="2016-08-08 00:00:00">
                                </div>
                            </div>
                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcTwo()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>


                </div>

                <div id="fuck-three">
                    <div class="contenttitle2">
                        <h3>C  重新同步跨境通问题订单|接口标识：Order.SOCreate</h3>
                    </div>

                    <div class="statusbox" style="width: 800px">
                        <form id="poststatus" action="#">
                            <div class="status_thumb">备注信息：</div>
                            <div style="padding-right:20px;margin-bottom: 20px">
                                <textarea id="remark" name="" cols="" rows="" style="height: 100px;width: 790px"></textarea>
                            </div>

                            <div class="comment_authorimg"  >执行时间</div>
                            <div class="commentcontent" style="padding-right: 12px;margin-left:65px; margin-bottom: 10px">
                                <input  id="exec-time" type="text" placeholder="这是一个示例：2016-08-08 00:00:00  默认则为当前时间">
                            </div>

                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcThree()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>


                </div>

                <div id="fuck-four">
                    <div class="contenttitle2">
                        <h3>D  根据产品编号(product_code)Json批量置空跨境通商品编号(pc_productinfo 表 product_code_old 字段将被置空)</h3>
                    </div>
                    <div class="statusbox" style="width: 800px">
                        <form action="#">
                            <div class="status_thumb">产品编号Json串：</div>
                            <div style="padding-right:20px;">
                                <textarea id="json-str-p" name="" cols="" rows="" style="height: 200px;width: 790px" placeholder='惠家有编号 ["8016468747","8016468748","8016468749","8016468880"]'></textarea>
                            </div>
                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcFour()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>

                </div>


                <div id="fuck-five">
                    <div class="contenttitle2">
                        <h3>E  批量拉取商品库存信息|跨境通商品库存同步|接口标识：Inventory.ChannelQ4SBatchGet</h3>
                    </div>

                    <div class="statusbox" style="width: 800px">
                        <form action="#">
                            <div class="status_thumb">商品编号Json串：</div>
                            <div style="padding-right:20px;">
                                <textarea id="json-str-e" name="" cols="" rows="" style="height: 200px;width: 790px" placeholder='["032JPB03k440001","032JPB03k440001","AZEJPB06k440005"]'></textarea>
                            </div>
                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcFive()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>

                </div>


                <div id="fuck-six">
                    <div class="contenttitle2">
                        <h3>F  批量刷新商品缓存信息</h3>
                    </div>

                    <div class="statusbox" style="width: 800px">
                        <form action="#">
                            <div class="status_thumb"> product_code Json串：</div>
                            <div style="padding-right:20px;">
                                <textarea id="json-str-s" name="" cols="" rows="" style="height: 200px;width: 790px" placeholder='惠家有编号 ["8016468747","8016468748","8016468749","8016468880"]'></textarea>
                            </div>
                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcSix()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>

                </div>

                <div id="fuck-seven">
                    <div class="contenttitle2">
                        <h3>G  批量上下架商品</h3>
                    </div>

                    <div class="statusbox" style="width: 800px">
                        <form action="#">
                            <div class="status_thumb_list" style="width: 308px">
                                上下架状态：
                            </div>
                            <select id="seven-status" class="radius3" style="margin-top:5px; margin-bottom: 15px;">
                                <option value="">---请选择---</option>
                                <option value="4497153900060002">商品批量上架</option>
                                <option value="4497153900060004">商品批量下架</option>
                            </select>

                            <div class="status_thumb_list" style="width: 308px"> product_code Json串：</div>
                            <div style="padding-right:20px; margin-top:5px; margin-bottom: 15px;">
                                <textarea id="json-str-seven" name="" cols="" rows="" style="height: 200px;width: 790px"
                                          placeholder='惠家有编号 ["8016468747","8016468748","8016468749","8016468880"]|为空则更新全部商品'></textarea>
                            </div>

                            <div class="status_thumb_list" style="width: 308px;"> 执行原因说明：</div>
                            <div style="padding-right:20px; margin-top:5px; ">
                                <textarea id="json-str-seven-reason" name="" cols="" rows="" style="height: 200px;width: 790px" placeholder='上下架原因描述 + 邮件发送人'></textarea>
                            </div>

                            <div class="submit">
                                <button class="stdbtn btn_orange" onclick="funcSeven()">执 行 任 务</button>
                            </div>
                        </form>
                    </div>

                </div>

            </div>
        </div>
<!--     </div>

</body>
</html> -->




























