<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%    // prevent browser cache jsp     
    response.setHeader("Pragma", "No-cache"); // HTTP 1.0
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setDateHeader("Expires", -1); // proxies
    /*
    row[3] 指的是 gid (SchedulerGroup 的 id)
    row[4] 指的是 iid (SchedulerItem 的 id)
    ${row[0]}, ${row[1]}, ${row[2]}, ${row[3]}, ${row[4]}, 
    執行範例：
    早班早段, nop_ho1, nopHo1, 1, 1
     */
%>
<!doctype html>
<!-- get info ... -->
<%@include file="include/utils.jspf"  %>
<html lang="en">
    <head>
        <!-- Head -->
        <%@include file="include/head.jspf"  %>
        <style type="text/css">
            select[disabled] { background-color: blue; }
        </style>
        <script>
            $(document).ready(function() {
                // 左邊 -> 右邊
                $('#leftItems').dblclick(function() {
                    var option = $('#leftItems option:selected');
                    //alert(option.val() + " : " + option.text());
                    $('#rightItems').append(option);
                });
                // 左邊 <- 右邊
                $('#rightItems').dblclick(function() {
                    var option = $('#rightItems option:selected');
                    //alert(option.val() + " : " + option.text());
                    $('#leftItems').append(option);
                });
                // >> Button
                $('#leftButton').click(function() {
                    var option = $('#leftItems option:selected');
                    //alert(option.val() + " : " + option.text());
                    $('#rightItems').append(option);
                });
                // << Button
            });
            
            function switchEmp(gid, iid, sdate) {
                console.log("gid=" + gid);
                console.log("iid=" + iid);
                console.log("sdate=" + sdate);
            }
            
        </script>
    </head>
    <body style="padding: 10px">
        <div id="layout">
            <!-- Toggle -->
            <%@include file="include/toggle.jspf"  %>

            <!-- Menu -->
            <%@include file="include/menu.jspf"  %>

            <div id="main">
                <div class="header">
                    <h1>HS 排班總檢（人事用）</h1>
                    <h2></h2>
                </div>
                
                <!-- 班表人員交換 -->
                <div id="switchTag">
                    <form class="pure-form">
                        <table class="pure-table">
                            <thead>
                                <th>
                                    <input type="text" value="2021-03-02" style="width:135px" readonly>
                                    <select style="width:110px" disabled>
                                        <option value="1" selected>藥局</option>
                                        <option value="2">公司</option>
                                    </select>
                                    <select style="width:110px" disabled>
                                        <option value="1" selected>早班早段</option>
                                        <option value="2">早班午段</option>
                                    </select>
                                </th>
                                <th></th>
                                <th>
                                    <select style="width:70px">
                                        <option value="1">01</option>
                                        <option value="2">02</option>
                                        <option value="3" selected>03</option>
                                    </select>
                                    <select style="width:110px">
                                        <option value="1">藥局</option>
                                        <option value="2">公司</option>
                                    </select>
                                    <select style="width:110px">
                                        <option value="1">早班早段</option>
                                        <option value="2" selected>早班午段</option>
                                    </select>
                                    <input type="button" value="查詢" class="pure-button pure-button-primary" style="width:60px">
                                </th>
                            </thead>
                            <tbody>
                                <td valign="top" align="center">
                                    <select multiple="true" id="leftItems" name="leftItems" style="width:365px;height:118px">
                                        <option value="A01">AAA</option>
                                        <option value="B01">BBB</option>
                                    </select>
                                </td>
                                <td valign="top" align="center">
                                    <input type="button" value=">>" id="leftButton" name="leftButton" class="pure-button pure-button-primary" style="width:60px" onclick="moveLeftToRight()">
                                    <div style="height:2px"></div>
                                    <input type="button" value="<<" id="rightButton" name="rightButton" class="pure-button pure-button-primary" style="width:60px">
                                    <div style="height:2px"></div>
                                    <input type="button" value="交換" id="switchButton" name="switchButton" class="pure-button pure-button-primary" style="width:60px">
                                </td>
                                <td valign="top" align="center">
                                    <select multiple="true" id="rightItems" name="rightItems" style="width:365px;height:118px">
                                        <option value="C01">CCC</option>
                                    </select>
                                </td>
                            </tbody>
                            <thead>
                                <th align="center"><input type="button" value="新增" class="pure-button pure-button-primary" style="width:60px">&nbsp;&nbsp;<input type="button" value="刪除" class="pure-button pure-button-primary" style="width:60px"></th>
                                <th>
                                    
                                </th>
                                <th align="center"><input type="button" value="新增" class="pure-button pure-button-primary" style="width:60px">&nbsp;&nbsp;<input type="button" value="刪除" class="pure-button pure-button-primary" style="width:60px"></th>
                            </thead>
                        </table>
                    </form>
                </div>
                <br><br><br><br>
                <!-- 表單範本 -->
                <form id="myForm" class="pure-form" method="post" action="${pageContext.servletContext.contextPath}/mvc/console/calendar/scheduler/view">
                    <fieldset>

                        <!-- input year and month -->
                        <%@include file="include/input_year_month.jspf"  %>

                        <button id="submitBtn" type="submit" class="pure-button pure-button-primary">查詢</button>
                    </fieldset>
                </form>
                        
                <c:set var="showTable" value="" />
                <c:if test="${ fn:length(holidays) <=0 }">
                    查無資料 !
                    <c:set var="showTable" value="none" />
                </c:if>        
                <!-- 列表範本 -->    
                <table style="display:${ showTable }" class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
                            <td valign="top" align="center"  colspan="2">重要事項</td>
                            <c:forEach var="h" items="${holidays}">
                                <fmt:formatDate var="d" value="${h.date}" pattern="dd" />
                                <c:if test="${h.name eq ''}">
                                    <c:set var="hc" value="${fn:replace(h.holidayCategory, '星期六、星期日', '')}" />
                                    <c:set var="fullData" value="${ hc }" />
                                    <c:set var="displayData" value="${ hc }" />
                                </c:if>
                                <c:if test="${h.name ne ''}">
                                    <c:set var="fullData" value="${ h.name }" />
                                    <!-- 重要事項 最多可顯示 10 個字 -->
                                    <c:set var="displayData" value="${ fn:substring(h.name, 0, 10) }${ fn:length(h.name) > 10 ? '...' : '' }" />
                                </c:if>
                                <td valign="top" align="center">
                                    <span style="direction:ltr; writing-mode:vertical-lr; width:20px">
                                        ${ displayData }
                                    </span>
                                </td>
                            </c:forEach>
                            <td valign="top" align="center"  colspan="2">重要事項</td>    
                        </tr>
                        <tr>
                            <td valign="middle" align="center"  colspan="2">假日</td>
                            <c:forEach var="h" items="${holidays}">
                                <fmt:formatDate var="d" value="${h.date}" pattern="dd" />
                                <td valign="middle" align="center">
                                    ${h.isHoliday}
                                </td>
                            </c:forEach>
                            <td valign="middle" align="center"  colspan="2">假日</td>    
                        </tr>

                    </thead>

                    <tbody>
                        <tr>
                            <td valign="middle" align="center"  colspan="2">日期</td>
                            <c:forEach var="h" items="${holidays}">
                                <td valign="middle" align="center">
                                    <fmt:formatDate value="${h.date}" pattern="dd" />
                                </td>
                            </c:forEach>
                            <td valign="middle" align="center"  colspan="2">日期</td>    
                        </tr>
                        <tr>
                            <td valign="middle" align="center"  colspan="2">星期</td>
                            <c:forEach var="h" items="${holidays}">
                                <fmt:formatDate var="week_full_name" value="${h.date}" pattern="E" />
                                <c:set var="week_short_name" value="${fn:replace(week_full_name, '星期', '')}" />
                                <td valign="middle" align="center">${week_short_name}</td>
                            </c:forEach>
                            <td valign="middle" align="center"  colspan="2">星期</td>    
                        </tr>
                        <!-- 到班人數 -->
                        <%--@ include file="include/nop_map.jspf"  --%>

                        <c:forEach var="nop" items="${ nopMap }">
                            <c:forEach var="row" varStatus="status" items="${ nop.value }" >
                                <tr style="background-color:${ nop.key[1] }">
                                    <c:if test="${ status.index == 0 }">
                                        <td valign="middle" align="center" rowspan="${ fn:length(nop.value) }">${ nop.key[0] }</td>
                                    </c:if>
                                    <td valign="middle" align="center" nowrap>${ row[0] }</td>
                                    <c:forEach var="h" items="${ holidays }">
                                        <fmt:formatDate var="d" value="${ h.date }" pattern="dd" />
                                        <fmt:formatDate var="ymd" value="${ h.date }" pattern="yyyy-MM-dd" />
                                        <fmt:formatDate var="week_full_name" value="${h.date}" pattern="E" />
                                        <c:set var="titleMsg" value="${ nop.key[0]} (${ row[0] }) : ${ ymd } (${ week_full_name })" />
                                        <td onmouseover = "this.style.backgroundColor = '#FFFFFF';this.style.fontWeight = 'bold';this.style.color = '#FF0000'" 
                                            onmouseout  = "this.style.backgroundColor = '${ nop.key[1] }';
                                                    this.style.fontWeight = 'normal';
                                                    this.style.color = '#777777'"
                                            nowrap valign="middle" align="center"
                                            onclick="switchEmp(${ row[3] }, ${ row[4] }, '${ ymd }')">
                                            <span id="showEmpSpan" >
                                                <c:set var="empCount" value="0" />
                                                <c:forEach var="emp" items="${ employees }">
                                                    <fmt:formatDate var="symd" value="${ emp.sdate }" pattern="yyyy-MM-dd" />
                                                    <c:if test="${ ymd == symd and emp.gid == row[3] and emp.iid == row[4] }">
                                                        <span>${ emp.employee.empName }</span><br>
                                                        <c:set var="empCount" value="${ empCount + 1 }" />
                                                    </c:if>
                                                </c:forEach>
                                            </span>
                                            <span>
                                                <c:if test="${ h[row[2]] > 0 }">
                                                    ${ h[row[2]]-empCount } / ${ h[row[2]] }
                                                </c:if>
                                            </span>
                                        </td>

                                    </c:forEach>
                                    <td valign="middle" align="center" nowrap>${ row[0] }</td>   

                                    <c:if test="${ status.index == 0 }">
                                        <td valign="middle" align="center" rowspan="${ fn:length(nop.value) }">${ nop.key[0] }</td>
                                    </c:if>

                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </tbody>
                </table> 

            </div>

            
        </div>
        <!-- Foot -->
        <%@include file="include/foot.jspf"  %>


    </body>
</html>