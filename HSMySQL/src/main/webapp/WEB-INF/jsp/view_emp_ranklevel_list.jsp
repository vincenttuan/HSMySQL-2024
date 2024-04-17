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
                    <h1>員工職等職級表</h1>
                    <h2>員工職等職級列表</h2>
                </div>
                <!-- 列表範本 -->    
                <table class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>員工編號</th>
                            <th>員工姓名</th>
                            <th>員工RFID</th>
                            <th>內部職稱</th>
                            <th>職等</th>
                            <th>職級</th>
                            <th>薪資</th>
                            <!--<th>外部職稱數量</th>-->
                            <th>外部職稱</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="item" items="${list}" varStatus="status">
                            <tr class="${ status.index %2 == 1 ? 'pure-table-odd' : ''}">
                                <td>${status.index}</td>
                                <td>${item.empNo}</td>
                                <td>${item.empName}</td>
                                <td>${item.empRfid}</td>
                                <td>${item.title}</td>
                                <td align='center'>${item.rankNo}</td>
                                <td align='center'>${item.levelNo}</td>
                                <td align='right'>$ <fmt:formatNumber value="${item.salary}" /></td>
                                <!--<td>${item.cnameCount}</td>-->
                                <td>${item.cnames}</td>
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