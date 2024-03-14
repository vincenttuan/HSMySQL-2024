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
                var popupWindow = window.open(url, 'popUpWindow', 'height=500,width=700,left=100,top=100,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,directories=no, status=yes');
            }

            // --切換單位自動變換不同時段 (1)
            function displayCheckBox(gid, u_peroids) {
                console.log(gid);
                var jsonData = JSON.parse('${schedulerItems}');
                console.log(jsonData);
                var tag = '';
                for (var i = 0; i < jsonData.length; i++) {
                    if (jsonData[i]['gid'] == gid) {
                        var no = jsonData[i]['nop_name'];
                        no = no.substring(2);
                        var checkedTag = '';
                        if (u_peroids != 0) {
                            for (var j = 0; j < u_peroids.length; j++) {
                                if (u_peroids[j] == no) {
                                    checkedTag = 'checked';
                                    break;
                                }
                            }
                        }
                        tag += '<input type="checkbox" id="u_peroid" name="u_peroid" ' + checkedTag + ' value="' + no + '"> ' + jsonData[i]['class_name'] + ' ';
                    }
                }
                tag += '<input type="checkbox" onchange="checkAll(\'updateForm1\', \'u_peroid\', this.checked)"> 全選';
                document.getElementById('peroid_span').innerHTML = tag;
            }
            // --切換單位自動變換不同時段 (2)
            function displayCheckBox2(gid, u_peroid) {
                console.log(gid);
                var jsonData = JSON.parse('${schedulerItems}');
                console.log(jsonData);
                var tag = '';
                for (var i = 0; i < jsonData.length; i++) {
                    if (jsonData[i]['gid'] == gid) {
                        var no = jsonData[i]['nop_name'];
                        no = no.substring(2);
                        tag += '<input type="checkbox" id="u_peroid" name="u_peroid" ' + ((u_peroid == no) ? "checked" : "") + ' value="' + no + '"> ' + jsonData[i]['class_name'] + '　';
                    }
                }
                tag += '<input type="checkbox" onchange="checkAll(\'updateForm2\', \'u_peroid\', this.checked)"> 全選';
                document.getElementById('peroid_span2').innerHTML = tag;
            }

            function init() {
                var u_nop = '${sessionScope.formData == null ? "" : sessionScope.formData.u_nop[0]}';
                var u_peroids = ${sessionScope.formData == null ? 0 : sessionScope.formData.u_peroid};
                if (u_nop != '') {
                    var gid = u_nop == 'ho' ? 1 : u_nop == 'co' ? 2 : u_nop == 'ph' ? 3 : 4;
                    console.log(u_peroids);
                    displayCheckBox(gid, u_peroids);
                }
            }

            // 全選核選盒
            // tagName: 目標核選盒
            // flag: 全選checkbox的狀態(true, false)
            function checkAll(formName, tagName, flag) {
                // 取得所有同名的 checkbox
//                var checkboxes = document.getElementsByName(tagName);
                var checkboxes = document.forms[formName].elements[tagName];
                for (var i = 0; i < checkboxes.length; i++) {
                    // 變更 checkbox 的狀態
                    checkboxes[i].checked = flag;
                }
            }
        </script>
    </head>
    <body style="padding: 10px" onload="init()">

        <div id="layout">
            <!-- Toggle -->
            <%@include file="include/toggle.jspf"  %>

            <!-- Menu -->
            <%@include file="include/menu.jspf"  %>

            <div id="main">
                <div class="header">
                    <h1>HS 班表設定（人事用）</h1>
                    <h2>排班人數設定，僅可修改自當下日期隔天起的需求人數</h2>
                    <!-- 顯示執行後得到的數值 -->
                    <!--${sessionScope.formData}-->
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
                            <!-- 集團目前無排班資料，不顯示
                            <c:if test = "${ fn:length(nop.value) <= 0 }">
                                <tr>
                                    <td colspan="35"> 無資料 </td>
                                </tr>
                            </c:if>
                            -->
                            <c:if test = "${ fn:length(nop.value) > 0 }">
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
                                            <td onmouseover = "this.style.backgroundColor = '#FFFFFF';
                                                this.style.fontWeight = 'bold';
                                                this.style.color = '#FF0000'" 
                                                onmouseout  = "this.style.backgroundColor = '${ nop.key[1] }';
                                                    this.style.fontWeight = 'normal';
                                                    this.style.color = '#777777'"
                                                nowrap valign="middle" align="center">
                                                <!-- 目前排定人數 -->
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
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>      
            </div>
            <!--下鉚釘做瀏覽器測試-->
            <!--<span id="updateAnchor"></span>-->            
            <div id="main" style="display:${ showTable }">

                <div class="header">
                    <h1>行事曆批次修改</h1>
                    <h2>---排班人數批次設定，僅可修改自當下日期隔天起的人數---</h2>
                </div>

                <!--檢查批次修改送出的值是否完整-->
                <script>
                    //檢查批次1修改送出的值是否完整
                    function checkUpdateForm1() {
                        //測試console瀏覽器秀出輸出結果
                        //var args = $('#updateForm1').serialize();
                        //console.log(args);
                        //輸出值：u_year=2021&u_month=4&u_week=2&u_weekday=2&u_nop=ho&u_peroid=1&u_people=1
                        var u_year = $('#updateForm1').find('input[name="u_year"]').val();
                        if (u_year == '' || u_year < new Date().getFullYear()) {
                            alert('年分設定不正確，錯誤輸入值：' + u_year);
                            return false;
                        }
                        if (!args.includes("u_month")) {
                            alert('月份沒有選擇');
                            return false;
                        }
                        if (!args.includes("u_week")) {
                            alert('週數沒有選擇');
                            return false;
                        }
                        if (!args.includes("u_weekday")) {
                            alert('星期沒有選擇');
                            return false;
                        }
                        if (!args.includes("u_nop")) {
                            alert('單位沒有選擇');
                            return false;
                        }
                        if (!args.includes("u_peroid")) {
                            alert('時段沒有選擇');
                            return false;
                        }
                        var u_people = $('#updateForm1').find('input[name="u_people"]').val();
                        if (u_people == '' || u_people < 0) {
                            alert('人數設定不正確，錯誤輸入值：' + u_people);
                            return false;
                        }
                        return true;
                    }

                    //檢查批次2修改送出的值是否完整
                    function checkUpdateForm2() {
                        var form = $('#updateForm2');
                        var args = form.serialize();
                        console.log(args);
                        if (!args.includes("u_date")) {
                            alert('日期沒有選擇');
                            return false;
                        }
                        if (!args.includes("u_peroid")) {
                            alert('時段沒有選擇');
                            return false;
                        }
                        var u_people = form.find('input[name="u_people"]').val();
                        if (u_people == '' || u_people < 0) {
                            alert('人數設定不正確: ' + u_people);
                            return false;
                        }
                        return true;
                    }

                </script>

                <form id="updateForm1" name="updateForm1" class="pure-form" method="post" onsubmit="return checkUpdateForm1();" action="/HSMySQL/mvc/console/calendar/update/batch">
                    <!--每年4月, 8月的第二週與第四週的星期二與星期四的上午與晚上各幾人<p />-->
                    第一批次修改：依當月月份(單選)對單位進行 -> 班次(單選)、人數設定<p />
                    年份：<input type="number"  id="u_year" name="u_year" value="${year}"><p />
                    月份：
                    <c:forEach var="i" begin="1" end="12" step="1">
                        <input type="checkbox" id="u_month" name="u_month" value="${ i }"
                               <c:forEach var="m" items="${ sessionScope.formData.u_month }">
                                   ${ m == i ? 'checked' : ''}
                               </c:forEach>
                               > ${ i }、
                    </c:forEach>
                    <input type="checkbox" onchange="checkAll('updateForm1', 'u_month', this.checked)"> 全選
                    <p/>
                    週數：
                    <c:forEach var="i" begin="1" end="5" step="1">
                        <input type="checkbox" id="u_week" name="u_week" value="${ i }"
                               <c:forEach var="m" items="${ sessionScope.formData.u_week }">
                                   ${ m == i ? 'checked' : ''}
                               </c:forEach>
                               > ${ i }、
                    </c:forEach> 
                    <input type="checkbox" onchange="checkAll('updateForm1', 'u_week', this.checked)"> 全選
                    <p />
                    星期：
                    <c:forEach var="i" begin="1" end="7" step="1">
                        <input type="checkbox" id="u_weekday" name="u_weekday" value="${ i }"
                               <c:forEach var="m" items="${ sessionScope.formData.u_weekday }">
                                   ${ m == i ? 'checked' : ''}
                               </c:forEach>
                               > ${ i==1?'日':(i-1) }、
                    </c:forEach>
                    <input type="checkbox" onchange="checkAll('updateForm1', 'u_weekday', this.checked)"> 全選
                    <p />
                    <!--                    單位：
                                            <input type="radio" id="u_nop" name="u_nop" value="ho" ${ sessionScope.formData.u_nop[0] == 'ho' ? 'checked' : '' } onchange="displayCheckBox(1, 1)"> 診所、
                                            <input type="radio" id="u_nop" name="u_nop" value="co" ${ sessionScope.formData.u_nop[0] == 'co' ? 'checked' : '' } onchange="displayCheckBox(2, 1)"> 公司、
                                            <input type="radio" id="u_nop" name="u_nop" value="ph" ${ sessionScope.formData.u_nop[0] == 'ph' ? 'checked' : '' } onchange="displayCheckBox(3, 1)"> 藥局、
                                            <input type="radio" id="u_nop" name="u_nop" value="fa" ${ sessionScope.formData.u_nop[0] == 'fa' ? 'checked' : '' } onchange="displayCheckBox(4, 1)"> 工廠<p/>
                                            時段：
                                            <span id="peroid_span"></span><p />-->

                    單位：
                    <input type="radio" id="u_nop" name="u_nop" value="ho" ${ sessionScope.formData.u_nop[0] == 'ho' ? 'checked' : '' } onchange="displayCheckBox(1, 0)"> 診所、
                    <input type="radio" id="u_nop" name="u_nop" value="co" ${ sessionScope.formData.u_nop[0] == 'co' ? 'checked' : '' } onchange="displayCheckBox(2, 0)"> 公司、
                    <input type="radio" id="u_nop" name="u_nop" value="ph" ${ sessionScope.formData.u_nop[0] == 'ph' ? 'checked' : '' } onchange="displayCheckBox(3, 0)"> 藥局、
                    <input type="radio" id="u_nop" name="u_nop" value="fa" ${ sessionScope.formData.u_nop[0] == 'fa' ? 'checked' : '' } onchange="displayCheckBox(4, 0)"> 工廠<p/>
                    時段：
                    <span id="peroid_span"></span>
                    <p />
                    人數：
                    <input type="number"   id="u_people" name="u_people" value="${ sessionScope.formData.u_people == null ? '1' : sessionScope.formData.u_people[0] }"><p />
                    <button id="submitBtn" type="submit" class="pure-button pure-button-primary">修改</button>
                </form>

                <!--第二批次修改方法建立-->
                <hr>
                第二批次修改：依當月日期(多選)對單位進行 -> 班次(多選)、人數設定<p />
                <form id="updateForm2" name="updateForm2" class="pure-form" method="post" onsubmit="return checkUpdateForm2();" action="/HSMySQL/mvc/console/calendar/update/batch2">
                    年份：<input type="text" id="u_year" name="u_year" value="${ year }" readonly>
                    月份：<input type="text" id="u_month" name="u_month" value="${ month }" readonly><p />
                    日期：
                    <c:forEach var="d" begin="1" end="${ lastDate }">
                        <input type="checkbox" id="u_date" name="u_date" value="${ d }"> ${ d }、 
                    </c:forEach>
                    <input type="checkbox" onchange="checkAll('updateForm2', 'u_date', this.checked)"> 全選
                    <p />
                    單位：
                    <input type="radio" id="u_nop" name="u_nop" value="ho" onchange="displayCheckBox2(1, 0)"> 診所、
                    <input type="radio" id="u_nop" name="u_nop" value="co" onchange="displayCheckBox2(2, 0)"> 公司、
                    <input type="radio" id="u_nop" name="u_nop" value="ph" onchange="displayCheckBox2(3, 0)"> 藥局、
                    <input type="radio" id="u_nop" name="u_nop" value="fa" onchange="displayCheckBox2(4, 0)"> 工廠<p/>
                    時段：
                    <span id="peroid_span2"></span><p />
                    人數：
                    <input type="number" id="u_people" name="u_people" value="0"><p />
                    <button id="submitBtn" type="submit" class="pure-button pure-button-primary">修改</button>
                </form>    
            </div>
        </div>
        <!-- Foot -->
        <%@include file="include/foot.jspf"  %>


    </body>
</html>