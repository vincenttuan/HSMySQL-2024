<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%    // prevent browser cache jsp     
    response.setHeader("Pragma", "No-cache"); // HTTP 1.0
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setDateHeader("Expires", -1); // proxies
%>
<!doctype html>
<!-- get info ... -->
<%@include file="include/utils.jspf"  %>
<html lang="en">
    <head>
        <!-- Head -->
        <%@include file="include/head.jspf"  %>
        <script>
            function checkNop(d, colName) {
                var args = new Object();
                args.sdate = ${year} + '/' + ${month} + '/' + d;
                args.colName = colName;
                // 每一個可排人數的 td id
                var colNameId = document.getElementById((d<10?'0':'') + d +'_'+colName);
                $.ajax({
                    url: "/HSMySQL/mvc/console/calendar/get/scheduler/nop",
                    type: 'POST',
                    contentType: 'application/json',
                    async: true,
                    data: JSON.stringify(args),
                    success: function (respData, status) {
                        colNameId.innerHTML = respData;
                    },
                    error: function (thrownError) {
                        alert(thrownError);
                    }
                });
            }

            
            function updateSchedulerEmployee(empNo, d,colName, gid, iid, tdTag) {
                var args = new Object();
                args.empNo = empNo;
                args.sdate = ${year} + '/' + ${month} + '/' + d;
                args.gid = gid;
                args.iid = iid;
                args.colName = colName;
                
                
                $.ajax({
                    url: "/HSMySQL/mvc/console/calendar/update/scheduler/employee",
                    type: 'POST',
                    contentType: 'application/json',
                    async: true,
                    data: JSON.stringify(args),
                    success: function (respData, status) {
                        if(respData == 1) {
                            tdTag.innerHTML = 'V';
                        } else if(respData == 2) {
                            tdTag.innerHTML = '';
                        } else {
                            alert('排班失敗');
                        }
                        checkNop(d, colName);
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(thrownError);
                    }
                });

//                alert(JSON.stringify(args));

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
                    <h1>HS 排班表</h1>
                    <h2>${ schedulerEmployees }</h2>
                </div>
                <!-- 表單範本 -->
                <form id="myForm" class="pure-form" method="post" action="${pageContext.servletContext.contextPath}/mvc/console/calendar/scheduler/input">
                    <fieldset>
                        年：<input type="number" name="year"  value="${year}" />
                        月：<input type="number" name="month" value="${month}" />
                        <button id="submitBtn" type="submit" class="pure-button pure-button-primary">查詢</button>
                    </fieldset>
                </form>
                <!-- 該部門排班列表 -->    
                <table class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
                            <td valign="top" align="center"  colspan="2">重要事項</td>
                            <c:forEach var="h" items="${holidays}">
                                <fmt:formatDate var="d" value="${h.date}" pattern="dd" />
                                <td valign="top" align="center" onclick="updateName(${d}, this)" title="按我一下可以修改" style="cursor: pointer;">
                                    <c:if test="${h.name eq ''}">
                                        <c:set var="hc" value="${fn:replace(h.holidayCategory, '星期六、星期日', '')}" />
                                        ${hc}
                                    </c:if>
                                    <c:if test="${h.name ne ''}">
                                        ${h.name}
                                    </c:if>
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
                        <%@ include file="include/nop_map_dept.jspf"  %>
                        
                        <c:forEach var="nop" items="${ nopMap }">
                            <c:forEach var="row" varStatus="status" items="${ nop.value }" >
                                <tr style="background-color:${ nop.key[1] }">
                                    <c:if test="${ status.index == 0 }">
                                        <td valign="middle" align="center" rowspan="${ fn:length(nop.value) }">${ nop.key[0] }</td>
                                    </c:if>

                                    <td valign="middle" align="center">${ row[0] }</td>
                                    <c:forEach var="h" items="${ holidays }">
                                        <fmt:formatDate var="d" value="${ h.date }" pattern="dd" />
                                        <fmt:formatDate var="ymd" value="${ h.date }" pattern="yyyy-MM-dd" />
                                        <fmt:formatDate var="week_full_name" value="${h.date}" pattern="E" />
                                        <c:set var="titleMsg" value="${ nop.key[0]} (${ row[0] }) : ${ ymd } (${ week_full_name })" />
                                        <td onmouseover = "this.style.backgroundColor = '#FFFFFF';this.style.fontWeight = 'bold';this.style.color = '#FF0000'" 
                                            onmouseout  = "this.style.backgroundColor = '${ nop.key[1] }';this.style.fontWeight = 'normal';this.style.color = '#777777'"
                                            valign="middle" align="center" onclick="updateNop(${ d }, '${ row[1] }', this)" title="${ titleMsg }" style="cursor: pointer">
                                            <span id = "${ d }_${ row[1] }" >${ h[row[2]] }</span>
                                        </td>
                                    </c:forEach>
                                    <td valign="middle" align="center">${ row[0] }</td>   

                                    <c:if test="${ status.index == 0 }">
                                        <td valign="middle" align="center" rowspan="${ fn:length(nop.value) }">${ nop.key[0] }</td>
                                    </c:if>

                                </tr>
                            </c:forEach>
                        </c:forEach>
                        <!-- 個人排班表 -->
                        <tr>
                            <td colspan="35">
                                ${ sessionScope.employee.empName } ${ year } 年 ${ month } 月 個人排班表 :
                            </td>
                        </tr>
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
                                <c:set var="titleMsg" value="${ nop.key[0]} (${ row[0] }) : ${ ymd } (${ week_full_name })" />
                                <c:set var="week_short_name" value="${fn:replace(week_full_name, '星期', '')}" />
                                <td valign="middle" align="center">${week_short_name}</td>
                            </c:forEach>
                            <td valign="middle" align="center"  colspan="2">星期</td>    
                        </tr>
                        <c:forEach var="nop" items="${ nopMap }">
                            <c:forEach var="row" varStatus="status" items="${ nop.value }" >
                                <tr style="background-color:${ nop.key[1] }">
                                    <c:if test="${ status.index == 0 }">
                                        <td valign="middle" align="center" rowspan="${ fn:length(nop.value) }">${ nop.key[0] }</td>
                                    </c:if>

                                    <td valign="middle" align="center">${ row[0] }</td>
                                    
                                    <c:forEach var="h" items="${ holidays }">
                                        <fmt:formatDate var="dd" value="${ h.date }" pattern="d" />
                                        <fmt:formatDate var="ymd" value="${ h.date }" pattern="yyyy-MM-dd" />
                                        <fmt:formatDate var="week_full_name" value="${h.date}" pattern="E" />
                                        <c:set var="titleMsg" value="按我一下可排班 ${ nop.key[0]} (${ row[0] }) : ${ ymd } (${ week_full_name })" />
                                        <td style="cursor: pointer" title="${ titleMsg }" onclick="updateSchedulerEmployee('${ sessionScope.employee.empNo }', ${ dd }, '${ row[1] }', ${ row[3] }, ${ row[4] }, this)">
                                            <!-- 個人排班選擇 -->
                                            <!-- ${ sessionScope.employee.empNo }_${ dd }_${ row[3] }_${ row[4] } -->
                                            <c:forEach var="e" items="${ schedulerEmployees }">
                                                <c:if test="${ h.date == e.sdate && row[3] == e.gid && row[4] == e.iid }">
                                                    V
                                                </c:if>
                                            </c:forEach>

                                        </td>
                                    </c:forEach>

                                    <td valign="middle" align="center">${ row[0] }</td>   

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