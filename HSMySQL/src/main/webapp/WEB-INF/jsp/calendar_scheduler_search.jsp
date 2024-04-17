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
                    <h1>排班總檢(人事用)</h1>
                    <h2>${ list }</h2>
                </div>
                <!-- 表單範本 -->
                <form id="myForm" class="pure-form" method="post" action="${pageContext.servletContext.contextPath}/mvc/console/calendar/scheduler/search">
                    <fieldset>
                        年：<input type="number" name="year"  value="${year}" />
                        月：<input type="number" name="month" value="${month}" />
                        日：<input type="number" name="day" value="${day}" />
                        <button id="submitBtn" type="submit" class="pure-button pure-button-primary">查詢</button>
                    </fieldset>
                </form>
                <!-- 列表範本 -->    
                <table class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
                            <th>日期</th>
                            <th>員工</th>
                            <th>班別</th>
                            <th>單位</th>
                            <th>打卡時間</th>
                            
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="item" items="${list}">
                            <tr>
                                <td>${item.sdate}</td>
                                <td>${item.emp_name}</td>
                                <td>${item.status_name}</td>
                                <td>${item.name}</td>
                                <td>${item.status_begin}</td>
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