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
            function showEmp() {
                var elms = $("[id='showEmpSpan']");
                if (elms.length > 0) {
                    var displayValue = elms[0].style.display == 'none' ? '' : 'none';
                    $('#showEmpBtn').html(displayValue == 'none' ? '顯示' : '隱藏');
                    for (var i = 0; i < elms.length; i++) {
                        elms[i].style.display = displayValue;
                    }
                }
            }

            function updateNop(d, k, tdTag) {
                var nop = tdTag.innerText.trim();
                nop = (nop == '') ? 0 : nop;
                var data = prompt(tdTag.title + " 請輸入修改的內容 ?", nop);
                if (data == null) { // 按下「取消」
                    return;
                }
                //組合json物件
                var obj = new Object();
                obj.date = ${year} + '/' + ${month} + '/' + d;
                obj.key = k;
                obj.value = data;
                $.ajax({
                    url: "/HSMySQL/mvc/console/calendar/update/nop",
                    type: 'POST',
                    contentType: 'application/json',
                    async: true,
                    data: JSON.stringify(obj),
                    success: function (respData, status) {
                        if (respData) {
                            location.reload();
                        } else {
                            alert('修改失敗 !');
                        }
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(thrownError);
                    }
                });

            }
            // d: 指定日期, tdTag: td 標籤
            function updateName(d, tdTag, fullData) {
                //var data = prompt("請輸入修改的內容 ?", tdTag.innerText);
                var data = prompt("請輸入修改的內容 ?", fullData);
                if (data == null) { // 按下「取消」
                    return;
                }
                var obj = new Object();
                obj.date = ${year} + '/' + ${month} + '/' + d;
                obj.name = data;
                $.ajax({
                    url: "/HSMySQL/mvc/console/calendar/update/name",
                    type: 'POST',
                    contentType: 'application/json',
                    async: true,
                    data: JSON.stringify(obj),
                    success: function (respData, status) {
                        if (respData) {
                            location.reload();
                        } else {
                            alert('修改失敗 !');
                        }
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(thrownError);
                    }
                });
            }

            function updateIsHoliday(d, tdTag) {
                var data = prompt("請輸入修改的內容 ?", tdTag.innerText);
                if (data == null) { // 按下「取消」
                    return;
                }
                var obj = new Object();
                obj.date = ${year} + '/' + ${month} + '/' + d;
                obj.isholiday = data;
                $.ajax({
                    url: "/HSMySQL/mvc/console/calendar/update/isholiday",
                    type: 'POST',
                    contentType: 'application/json',
                    async: true,
                    data: JSON.stringify(obj),
                    success: function (respData, status) {
                        if (respData) {
                            location.reload();
                        } else {
                            alert('修改失敗 !');
                        }
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(thrownError);
                    }
                });
            }
            
            function popWin() {
                var url = '${pageContext.servletContext.contextPath}/mvc/console/calendar/modify/popwin';
                var popupWindow = window.open(url,'popUpWindow','height=500,width=700,left=100,top=100,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no, status=yes');
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
                    <h1>HS 班表設定（人事用）</h1>
                    <h2>人事確認版</h2>
                </div>
                <!-- 表單範本 -->
                <form id="myForm" class="pure-form" method="post" action="${pageContext.servletContext.contextPath}/mvc/console/calendar/view">
                    <fieldset>
                        <!--已將年、月、上一月、下一月功能做成include-->

                        <!-- input year and month -->
                        <%@include file="include/input_year_month.jspf"  %>

                        <button id="submitBtn" type="submit" class="pure-button pure-button-primary">查詢</button>
                        <button id="showEmpBtn" type="button" class="pure-button pure-button-primary" onclick="showEmp()">隱藏</button>
                        <button id="popWinBtn" type="button" class="pure-button pure-button-primary" onclick="popWin()">批次修改</button>
                    </fieldset>
                </form>
                        
                <!--  設定如果沒有月份資料，則表格不顯示，並且秀出:查無資料 
                        style="display:none":完全消失，跟没有一樣 
                        style=" visibility:hidden":仍舊存在，但不顯示  !-->
                <set id="showTable" value="" />
                <c:if test="${ fn:length(holidays) <=0 }">
                    查無資料，請重新輸入搜尋月份 !
                    <set id="showTable" style="display:none" />
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
                                <td valign="top" align="center" onclick="updateName(${d}, this, '${ fullData }')" title="按我一下可以修改" style="cursor: pointer;">
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
                                <td valign="middle" align="center" onclick="updateIsHoliday(${d}, this)" title="按我一下可以修改" style="cursor: pointer">
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
                                            nowrap valign="middle" align="center">
                                            <!--目前排定需求人數-->
                                            <span onclick="updateNop(${ d }, '${ row[1] }', this)" title="${ titleMsg }" style="cursor: pointer">
                                                ${ h[row[2]] == 0 ? '&nbsp;&nbsp;' : h[row[2]] }
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

            <div id="main">
                <div class="header">
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
                    單位：
                    <input type="radio" id="u_nop" name="u_nop" value="ho" onchange="displayCheckBox(1)"> 診所、
                    <input type="radio" id="u_nop" name="u_nop" value="co" onchange="displayCheckBox(2)"> 公司、
                    <input type="radio" id="u_nop" name="u_nop" value="ph" onchange="displayCheckBox(3)"> 藥局、
                    <input type="radio" id="u_nop" name="u_nop" value="fa" onchange="displayCheckBox(4)"> 工廠<p/>
<!--                    ｒ時段：
                    <input type="radio" id="u_peroid" name="u_peroid" value="1"> 上午、
                    <input type="radio" id="u_peroid" name="u_peroid" value="2"> 下午、
                    <input type="radio" id="u_peroid" name="u_peroid" value="3"> 晚上<p/>-->
                    時段：
                    <script>
//                      <!--切換單位自動變換不同時段-->
                        function displayCheckBox(gid) {
                            console.log(gid);
                            var jsonData = JSON.parse('${schedulerItems}');
                            console.log(jsonData);
                            var tag = '';
                            for(var i=0;i<jsonData.length;i++) {
                                if(jsonData[i]['gid'] == gid) {
                                    var no = jsonData[i]['nop_name'];
                                    no = no.substring(2);
                                    tag += '<input type="radio" id="u_peroid" name="u_peroid" value="' + no + '"> ' + jsonData[i]['class_name'] + '　';
                                }
                            }
                            document.getElementById('peroid_span').innerHTML = tag;
                        }
                    </script>
                    <span id="peroid_span"></span><p />

                    人數：
                    <input type="number"   id="u_people" name="u_people" value="1"><p />
                    <button id="submitBtn" type="submit" class="pure-button pure-button-primary">修改</button>
                </form>
            </div>
        </div>
        <!-- Foot -->
        <%@include file="include/foot.jspf"  %>


    </body>
</html>