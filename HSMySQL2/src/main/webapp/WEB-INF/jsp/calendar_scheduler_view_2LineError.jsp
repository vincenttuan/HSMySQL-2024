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

        <script>
            //以JQuery方式撰寫
            $(document).ready(function () {
                //把左邊快點兩下移到右邊方法
                $("#leftItems").dblclick(function () {
                    //建立選取
                    var option = $('#leftItems option:selected');
                    //以alert秀出獲得的資訊
                    //alert(option.val() + "："　+ option.text());
                    //把左邊移到右邊
                    $("#rightItems").append(option);
                });

                //把右邊快點兩下移到左邊方法
                $("#rightItems").dblclick(function () {
                    //建立選取
                    var option = $('#rightItems option:selected');
                    //以alert秀出獲得的資訊
                    //alert(option.val() + "："　+ option.text());
                    //把右邊移到左邊
                    $("#leftItems").append(option);
                });

                //把左邊按壓按鈕移到右邊 Button : > > >
                $("#leftButton").click(function () {
                    //建立選取
                    var option = $('#leftItems option:selected');
                    //以alert秀出獲得的資訊
                    //alert(option.val() + "："　+ option.text());
                    //把左邊移到右邊
                    $("#rightItems").append(option);
                });

                //把右邊按壓按鈕移到左邊 Button : < < <
                $("#rightButton").click(function () {
                    //建立選取
                    var option = $('#rightItems option:selected');
                    //以alert秀出獲得的資訊
                    //alert(option.val() + "："　+ option.text());
                    //把左邊移到右邊
                    $("#leftItems").append(option);
                });
                
                //左右點選後，按壓交換按鈕互換 Button : 交換
                $("#switchButton").click(function () {
                    //建立選取
                    var option1 = $('#leftItems option:selected');
                    var option2 = $('#rightItems option:selected');
                    //左右交換
                    $("#leftItems").append(option2);
                    $("#rightItems").append(option1);
                });
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
                    <h2>只能查詢</h2>
                </div>

                <!--班表人員交換-->
                <div id="switchTag">
                    <form class="pure-form">
                        <table class="pure-table">
                            <thead>
                            <th>
                                <input type="text" value="2021-03-02" style="width:130px" readonly>
                                <select style="width:120px" disabled>
                                    <option value="1" selected>藥局一部</option>
                                    <option value="2">公司一部</option>
                                </select>
                                <select style="width:120px" disabled>
                                    <option value="1" selected>早班早段</option>
                                    <option value="2">早班晚段</option>
                                </select>
                                <!--<input type="text" value="藥局一部" style="width:100px" readonly>
                                    <input type="text" value="早班晚段" style="width:100px" readonly>-->
                            </th>
                            <th valign="top" align="center">
                                <!--秀出登入者名字-->
                                ${sessionScope.employee.empName}<br>
                                你好，功能開發中！
                                <!--秀出問候語 ----- 功能出不來-->
                                <div id="greetDiv"></div>
                                <!--秀出時間 ----- 功能出不來-->
                                <div id="showTimeDiv"></div>
                            </th>
                            <th>
                                <select style="width:70px">
                                    <option value="1" selected>29</option>
                                    <option value="2">30</option>
                                    <option value="3">31</option>
                                </select>
                                <select style="width:120px">
                                    <option value="1" selected>診所一部</option>
                                    <option value="2">藥局一部</option>
                                    <option value="3">公司一部</option>
                                    <option value="4">工廠一部</option>
                                </select>
                                <select style="width:120px">
                                    <option value="1" selected>早班早段</option>
                                    <option value="2">早班午段</option>
                                    <option value="3">晚班午段</option>
                                    <option value="4">晚班晚段</option>
                                    <option value="5">早晚早段</option>
                                    <option value="6">早晚晚段</option>
                                </select>
                                <input type="button" value="查詢" class="pure-button pure-button-primary" style="width:60px">
                            </th>
                            </thead>
                            <tbody>
                            <td valign="center" align="center">
                                <select multiple="true" id="leftItems" name="leftItems" style="width:381px;height:118px">
                                    <option value="A01">AAA</option>
                                    <option value="B01">BBB</option>
                                </select>
                            </td>
                            <td valign="top" align="center">
                                <input type="button" value="> > >" id="leftButton" name="leftButton" class="pure-button pure-button-primary" style="width:110px">
                                <div style="height:2px"></div>
                                <input type="button" value="< < <" id="rightButton" name="rightButton"  class="pure-button pure-button-primary" style="width:110px">
                                <div style="height:2px"></div>
                                <input type="button" value="交  換" id="switchButton" name="switchButton"  class="pure-button pure-button-primary" style="width:110px">
                            </td>
                            <td valign="top" align="center">
                                <select multiple="true"  id="rightItems" name="rightItems" style="width:381px;height:118px">
                                    <option value="C01">CCC</option>
                                </select>
                            </td>
                            </tbody>
                            <thead>
                            <th valign="top" align="center">
                                <input align="center" type="button" value="新增" class="pure-button pure-button-primary" style="width:70px">&nbsp;&nbsp;
                                <input type="button" value="刪除" class="pure-button pure-button-primary" style="width:70px">
                            </th>
                            <th></th>
                            <th valign="top" align="center">
                                <input type="button" value="新增" class="pure-button pure-button-primary" style="width:70px">&nbsp;&nbsp;
                                <input type="button" value="刪除" class="pure-button pure-button-primary" style="width:70px">
                            </th>
                            </thead>
                        </table>
                    </form>
                </div>
                <br><br><br><br>
                <!-- 表單範本 -->
                <form id="myForm" class="pure-form" method="post" action="${pageContext.servletContext.contextPath}/mvc/console/calendar/scheduler/view">

                    <fieldset>
                        <!--已將年、月、上一月、下一月()功能做成include-->
                        <!-- input year and month -->
                        <%@include file="include/input_year_month.jspf"  %>
                        <button id="submitBtn" type="submit" class="pure-button pure-button-primary">查詢</button>
                    </fieldset>

                </form>

                <!--  設定如果沒有月份資料，則表格不顯示，並且秀出:查無資料 
                        style="display:none":完全消失，跟没有一樣 
                        style=" visibility:hidden":仍舊存在，但不顯示  !-->
                <c:set var="showTable" value="" />
                <c:if test="${ fn:length(holidays) <=0 }">
                    查無資料，請重新輸入搜尋月份 !
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

                        <!-- 到班人數 -->
                        <%--@ include file="include/nop_map.jspf"  --%>

                        <c:forEach var="nop" items="${ nopMap }">
                            <!--行事曆日期/星期表頭 開始-->
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
                            <!--行事曆日期/星期表頭 結束-->
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
                                            <!--秀出當班全部排班者的姓名-->
                                            <span id="showEmpSpan">
                                                <c:set var="empCount" value="0" />
                                                <c:forEach var="emp" items="${ employees }">
                                                    <fmt:formatDate var="symd" value="${ emp.sdate }" pattern="yyyy-MM-dd" />
                                                    <c:if test="${ ymd == symd and emp.gid == row[3] and emp.iid == row[4] }">
                                                        <span>${ emp.employee.empName }</span><br>
                                                        <c:set var="empCount" value="${ empCount + 1 }" />
                                                    </c:if>
                                                </c:forEach>
                                            </span>
                                            <!--計算並秀出排班剩餘人數/需求人數-->
                                            <span>
                                                <c:if test="${ h[row[2]] > 0 }" >
                                                    ${ h[row[2]] - empCount} / ${ h[row[2]] }
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

            <div id="main" style="display:${ showTable }">
                <!--                <div class="header">
                                    <h1>行事曆批次修改</h1>
                                </div>
                
                                <form class="pure-form" method="post" action="/HSMySQL/mvc/console/calendar/update/batch">
                                    每年4月, 8月的第二週與第四週的星期二與星期四的上午與晚上各幾人<p />
                                    年份：<input type="number"  id="u_year" name="u_year" value="${year}"><p />
                                    月份：
                                    <input type="checkbox" id="u_month" name="u_month" value="1"> 1、
                                    <input type="checkbox" id="u_month" name="u_month" value="2"> 2、
                                    <input type="checkbox" id="u_month" name="u_month" value="3"> 3、
                                    <input type="checkbox" id="u_month" name="u_month" value="4"> 4、
                                    <input type="checkbox" id="u_month" name="u_month" value="5"> 5、
                                    <input type="checkbox" id="u_month" name="u_month" value="6"> 6、
                                    <input type="checkbox" id="u_month" name="u_month" value="7"> 7、
                                    <input type="checkbox" id="u_month" name="u_month" value="8"> 8、
                                    <input type="checkbox" id="u_month" name="u_month" value="9"> 9、
                                    <input type="checkbox" id="u_month" name="u_month" value="10"> 10、
                                    <input type="checkbox" id="u_month" name="u_month" value="11"> 11、
                                    <input type="checkbox" id="u_month" name="u_month" value="12"> 12<p/>
                                    週數：
                                    <input type="checkbox" id="u_week" name="u_week" value="1"> 一、
                                    <input type="checkbox" id="u_week" name="u_week" value="2"> 二、
                                    <input type="checkbox" id="u_week" name="u_week" value="3"> 三、
                                    <input type="checkbox" id="u_week" name="u_week" value="4"> 四、
                                    <input type="checkbox" id="u_week" name="u_week" value="5"> 五<p /> 
                                    星期：
                                    <input type="checkbox" id="u_weekday" name="u_weekday" value="1"> 日、
                                    <input type="checkbox" id="u_weekday" name="u_weekday" value="2"> 一、
                                    <input type="checkbox" id="u_weekday" name="u_weekday" value="3"> 二、
                                    <input type="checkbox" id="u_weekday" name="u_weekday" value="4"> 三、
                                    <input type="checkbox" id="u_weekday" name="u_weekday" value="5"> 四、
                                    <input type="checkbox" id="u_weekday" name="u_weekday" value="6"> 五、
                                    <input type="checkbox" id="u_weekday" name="u_weekday" value="7"> 六<p/>
                                    時段：
                                    <input type="radio" id="u_peroid" name="u_peroid" value="1"> 上午、
                                    <input type="radio" id="u_peroid" name="u_peroid" value="2"> 下午、
                                    <input type="radio" id="u_peroid" name="u_peroid" value="3"> 晚上<p/>
                                    單位：
                                    <input type="radio" id="u_nop" name="u_nop" value="co"> 公司、
                                    <input type="radio" id="u_nop" name="u_nop" value="ho"> 診所、
                                    <input type="radio" id="u_nop" name="u_nop" value="ph"> 藥局、
                                    <input type="radio" id="u_nop" name="u_nop" value="fa"> 工廠<p/>
                                    人數：
                                    <input type="number"   id="u_people" name="u_people" value="1"><p />
                                    <button id="submitBtn" type="submit" class="pure-button pure-button-primary">修改</button>
                                </form>
                            </div>-->
            </div>
            <!-- Foot -->
            <%@include file="include/foot.jspf"  %>


    </body>
</html>