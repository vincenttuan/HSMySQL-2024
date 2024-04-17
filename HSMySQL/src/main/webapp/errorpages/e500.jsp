<%-- 
    Document   : e500
    Created on : 2021/3/23, 下午 03:31:21
    Author     : kevin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>500</title>
    </head>
    <body>
        <h1>Sorry! 系統內部發生錯誤(錯誤代碼：500)</h1>
        <!--<h3>錯誤訊息內容：<%=exception %></h3>-->
        <h2>
            <a href="${pageContext.servletContext.contextPath}">請回打卡首頁重新執行</a>
        </h2>
    </body>
</html>
<%@include file="saveErrorLog.jspf" %>