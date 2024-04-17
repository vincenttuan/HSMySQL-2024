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
<%@include file="../include/utils.jspf"  %>
<html lang="en">
    <head>
        <!-- Head -->
        <%@include file="../include/head.jspf"  %>
    </head>
    <body style="padding: 10px">

        <div id="layout">
            <!-- Toggle -->
            <%@include file="../include/toggle.jspf"  %>

            <!-- Menu -->
            <%@include file="../include/menu.jspf"  %>

            <div id="main">
                <div class="header">
                    <h1>Salary</h1>
                    <h2>Master</h2>
                </div>
                <!-- Salary Master 表單 -->
                <form class="pure-form" method="post">
                    <c:if test="${priority <= 900}">
                        <input id="empNo" name="empNo" value="${empNo}" readonly>
                    </c:if>
                    <c:if test="${priority > 900}">
                        <select id="empNo" name="empNo" autofocus>
                            <c:forEach var="e" items="${emps}">
                                <option value="${e.empNo}" ${e.empNo == empNo ? 'selected' : ''} >${e.empNo} - ${e.empName}</option>
                            </c:forEach>
                        </select>
                    </c:if>
                    <%@include file="../include/input_year_month.jspf"  %>
                    <button type="submit" class="pure-button pure-button-primary">查詢</button>
                </form>
                <div class="header">
                    <h1>基本薪資：$ 24,000</h1>
                    <h2><a href='/HSMySQL/mvc/console/salary/detial'>基本薪資明細</a></h2>
                </div>
                <div class="header">
                    <h1>本月獎金：$ 11,000</h1>
                    <h2><a href='/HSMySQL/mvc/console/salary/detial'>本月獎金明細</a></h2>
                </div>
            </div>
        </div>

        <!-- Foot -->
        <%@include file="../include/foot.jspf"  %>

    </body>
</html>