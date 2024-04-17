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
                    <h1>主標題</h1>
                    <h2>副標題</h2>
                </div>
                <!-- 表單範本 -->
                <form:form id="myForm" class="pure-form" modelAttribute="employee" method="post" action="${pageContext.servletContext.contextPath}/mvc/console/employee/${action}">
                    <fieldset>
                        <legend>HS 員工資料</legend>
                        ID序號：<form:input path="empId" readonly /><p />
                        員工編號：<form:input path="empNo" placeholder="請輸入員工編號" /><p />
                        
                        <button id="submitBtn" type="button" class="pure-button pure-button-primary">${action}</button>
                    </fieldset>
                </form:form>
                <!-- 列表範本 -->    
                <table class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
                            <th>欄位一</th>
                            <th>欄位二</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="item" items="${list}">
                            <tr>
                                <td>${item.empId}</td>
                                <td>${item.name}</td>
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