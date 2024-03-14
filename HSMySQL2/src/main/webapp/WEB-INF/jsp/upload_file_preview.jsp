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
                    <h1>預覽資料</h1>
                    <h2>${error}</h2>
                </div>
                <!-- 表單範本 -->
                <form method="POST" action="/HSMySQL/mvc/console/fileupload/save">
                    <textarea hidden name="fulldata">${fulldata}</textarea>
                    <button id="submitBtn" type="submit" class="pure-button pure-button-primary">匯入資料庫</button>
                </form>
                <p />
                <!-- 列表範本 -->  
                <table class="pure-table pure-table-bordered" width="100%">
                    <tbody>
                        <c:forEach var="row" items="${rows}">
                            <c:set var="rowArray" value="${fn:split(row, ',')}" />
                            <tr>
                                <c:forEach var="data" items="${rowArray}">
                                <td>
                                    ${data}
                                </td>
                                </c:forEach>
                                <c:if test="${fn:length(rowArray) == 3}">
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </c:if>
                                <c:if test="${fn:length(rowArray) == 4}">
                                    <td>&nbsp;</td>
                                </c:if>
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