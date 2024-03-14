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
    </head>
    <body style="padding: 10px">

        <div id="layout">
            <!-- Toggle -->
            <%@include file="include/toggle.jspf"  %>

            <!-- Menu -->
            <%@include file="include/menu.jspf"  %>

            <div id="main">
                <div class="header">
                    <h1>HS 簽到查詢明細（財務專用）</h1>
                    <h2>資料查詢</h2>
                </div>
                <form class="pure-form" method="post" action="./queryBetween">
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
                    起：<input type="date" id="d1" name="d1" value="${d1}">
                    迄：<input type="date" id="d2" name="d2" value="${d2}">
                    <button type="submit" class="pure-button pure-button-primary">查詢</button>
                </form>
                <p />
                <table class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
                            <th>ID序號</th>
                            <th>員工編號</th>
                            <th>員工姓名</th>
                            <th>簽到時段</th>
                            <th>時段名稱</th>
                            <th>簽到時間</th>
                            <th width="200">簽到影像</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="item" items="${list}">
                            <tr>
                                <td>${item.clockId}</td>
                                <td>${item.empNo}</td>
                                <td>${item.empName}</td>
                                <td>${item.statusId}</td>
                                <td>${item.statusName}</td>
                                <td>${item.clockOn}</td>
                                <td><img src="${item.image}" width="50" onclick="this.style.width = '200px'" ondblclick="this.style.width = '50px'"></td>
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