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
                    <h1>HS 行事曆修改確認頁</h1>
                </div>
                <!-- 表單範本 -->
                <form id="myForm" class="pure-form" method="post" action="${pageContext.servletContext.contextPath}/mvc/console/calendar/update/batch/commit">
                    <fieldset>
                        <textarea name="update_sql" style="display: none">${update_sql}</textarea>
                        <button id="submitBtn" type="submit" class="pure-button pure-button-primary">確認修改</button>
                        <!--「時段」：1:上午、2:下午、3:晚上
                        「單位」：co:公司、ho:診所、ph:藥局、fa:工廠-->
                    </fieldset>
                </form>
                <!-- 列表範本 -->    
                <table class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
                            <c:forEach var="item" items="${list}" end="0">
                            <tr>
                                <c:forEach var="h" items="${item}">
                                    <th>${h.key}</th>
                                </c:forEach>
                            </tr>
                            </c:forEach>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="item" items="${list}">
                            <tr>
                                <c:forEach var="h" items="${item}">
                                    <td>
                                        <c:choose>
                                            <c:when test="${h.key == '星期'}">
                                                ${h.value - 1}
                                            </c:when>
                                            <c:when test="${h.key == '部門'}">
                                                ${h.value}
                                                <c:set var="dept" value="${h.value}" />
                                            </c:when>
                                            <c:when test="${h.key == '時段'}">
                                                <c:forEach var="item" items="${schedulerItems}">
                                                    <c:if test="${item['nop_name'] == (dept + h.value)}">
													    ${item['class_name']}
													</c:if>
                                                </c:forEach> 
                                            </c:when>
                                            <c:otherwise>
                                                ${h.value}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </c:forEach>
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