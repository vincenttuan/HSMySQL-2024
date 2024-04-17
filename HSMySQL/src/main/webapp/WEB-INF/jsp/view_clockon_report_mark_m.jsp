<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
            function exportExcel() {
                var empNo = document.getElementById("empNo").value;
                var year = document.getElementById("year").value;
                var month = document.getElementById("month").value;
                var path = './exportExcelViewClockOnReportMark_M?empNo=' + empNo + '&year=' + year + '&month=' + month;
                window.location.href = path;

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
                    <h1>HS 簽到月報表（財務專用）</h1>
                    <h2>資料查詢</h2>
                </div>
                <form class="pure-form" method="post" action="./submitViewClockOnReportMark_M">
                    <c:if test="${priority <= 900}">
                        <input id="empNo" name="empNo" value="${empNo}" readonly>
                    </c:if>
                    <c:if test="${priority > 900}">
                        <select id="empNo" name="empNo" onchange="this.form.submit()" autofocus>
                            <c:forEach var="e" items="${emps}">
                                <option value="${e.empNo}" ${e.empNo == empNo ? 'selected' : ''} >${e.empNo} - ${e.empName}</option>
                            </c:forEach>
                        </select>
                    </c:if>
                    年：<input type="number" id="year" name="year" value="${year}">
                    月：<input type="number" id="month" name="month" value="${month}">
                    <button type="submit" class="pure-button pure-button-primary">查詢</button>
                    <button type="button" onclick="exportExcel()" class="pure-button pure-button-primary">匯出Excel</button>

                </form>
                <p />
                <table class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
                            <th nowrap>日期</th>
                            <th nowrap>星期</th>
                            <th nowrap>員編</th>
                            <th nowrap>姓名</th>

                            <th nowrap>1 班別</th>
                            <th nowrap>1 上班</th>
                            <th nowrap>1 上班(標準)</th>
                            <th nowrap>1 下班</th>
                            <th nowrap>1 下班(標準)</th>
                            
                            <th nowrap>2 班別</th>
                            <th nowrap>2 上班</th>
                            <th nowrap>2 上班(標準)</th>
                            <th nowrap>2 下班</th>
                            <th nowrap>2 下班(標準)</th>
                            
                            <th nowrap>上班時數</th>
                            <th nowrap>休息時數</th>

                            <th nowrap>回報撰寫耗時</th>
                            <th nowrap>每日工作回報</th>
                            <!--<th nowrap>Result</th>-->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${list}">
                            <tr style="background-color:${item.week == 6 ? '#DFFFDF' : item.week == 7 ? '#FFE6D9' : ''}">
                                <td nowrap><fmt:formatDate type="date" value="${item.reportDate}" /></td>
                                <td nowrap>${item.week}</td>
                                <td nowrap>${item.empNo}</td>
                                <td nowrap>${item.empName}</td>

                                <td nowrap>${item.statusName_1}</td>
                                <td nowrap style="background-color:${item.mark1 == '*' ? 'red' : ''}"><font style="color:${item.mark1 == '*' ? 'yellow' : 'black'}">${item.clockOn_1}</font></td>
                                <td nowrap>${item.standard_1}</td>
                                <td nowrap style="background-color:${item.mark2 == '*' ? 'red' : ''}"><font style="color:${item.mark2 == '*' ? 'yellow' : 'black'}">${item.clockOn_2}</font></td>
                                <td nowrap>${item.standard_2}</td>
                                
                                <td nowrap>${item.statusName_3}</td>
                                <td nowrap style="background-color:${item.mark3 == '*' ? 'red' : ''}"><font style="color:${item.mark3 == '*' ? 'yellow' : 'black'}">${item.clockOn_3}</font></td>
                                <td nowrap>${item.standard_3}</td>
                                <td nowrap style="background-color:${item.mark4 == '*' ? 'red' : ''}"><font style="color:${item.mark4 == '*' ? 'yellow' : 'black'}">${item.clockOn_4}</font></td>
                                <td nowrap>${item.standard_4}</td>
                                <td nowrap align="right"><fmt:formatNumber pattern="#0.00" value="${item.diffHh}" /></td>
                                <td nowrap align="right"><fmt:formatNumber pattern="#0.00" value="${item.restHh}" /></td>

                                <td nowrap align="right">
                                    <c:forEach var="map" items="${memoSpendtimeList}">
                                        <c:forEach var="entry" items="${map}">  
                                            <fmt:formatDate value="${item.reportDate}" type="date" pattern="yyyy/MM/dd" var="rDate"/>
                                            <c:if test="${rDate eq entry.key}">
                                                <c:set var="msg" value="" />
                                                <c:forEach var="mapDetail" items="${memoSpendtimeDetailList}">
                                                    <c:forEach var="entryDetail" items="${mapDetail}">
                                                        <c:if test="${entry.key eq entryDetail.key }">
                                                            <c:set var="msg" value="${msg}\n${entryDetail.value}" />
                                                        </c:if>
                                                    </c:forEach>
                                                </c:forEach>
                                                <a href="javascript:alert('${msg}')">
                                                    <fmt:formatNumber pattern="#0.00" value="${entry.value/1000/60}" />
                                                </a>
                                            </c:if>  
                                        </c:forEach> 
                                    </c:forEach>
                                </td>
                                <td nowrap>${item.memo}</td>
                                <!--<td nowrap>${item.result ? '' : 'X'}</td>-->
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Foot -->
        <%@include file="include/foot.jspf"  %>

    </body>
</html>