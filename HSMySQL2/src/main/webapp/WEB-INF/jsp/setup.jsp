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
            function checkAgain(url, empName) {
                var r = confirm("請再次確認是否要開啟或關閉 [ " + empName + " ] 資料 ?");
                location.href = url;
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
                    <h1>後台設定</h1>
                    <h2>HS</h2>
                </div>
                <!-- 列表範本 -->    
                <table class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
                            <th>emp_no</th>
                            <th>emp_name</th>
                            <th>emp_pwd</th>
                            <th>emp_active</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="item" items="${list}">
                            <c:if test="${item.empActive == true}">
                            <tr>
                                <td>${item.empNo}</td>
                                <td>${item.empName}</td>
                                <td>${item.empPwd}</td>
                                <td>
                                    ${item.empActive?'啟用':'關閉'}&nbsp;
                                    <c:if test="${item.empActive == true}">
                                        <input type="button" value="關閉" style="color: #000000"
                                               onclick="checkAgain('changeActive?empNo=${item.empNo}&empActive=0', '${item.empName}')">
                                    </c:if>
                                </td>
                            </tr>
                            </c:if>
                        </c:forEach>
                        <tr>
                            <td colspan="4" style="color: red">離職員工：</td>
                        </tr>    
                        <c:forEach var="item" items="${list}">
                            <c:if test="${item.empActive == false}">
                            <tr>
                                <td>${item.empNo}</td>
                                <td>${item.empName}</td>
                                <td>${item.empPwd}</td>
                                <td>
                                    ${item.empActive?'啟用':'關閉'}&nbsp;
                                    <c:if test="${item.empActive == false}">
                                        <input type="button" value="啟用" style="color: #000000"
                                               onclick="checkAgain('changeActive?empNo=${item.empNo}&empActive=1', '${item.empName}')">
                                    </c:if>
                                </td>
                            </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table> 

            </div>
        </div>

        <!-- Foot -->
        <%@include file="include/foot.jspf"  %>

    </body>
</html>