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
                    <h1>上傳檔案</h1>
                    <h2>提供各種上傳到資料庫的功能</h2>
                    <h3 style="color: red">${ex}</h3>
                </div>
                <!-- 表單範本 -->
                <form id="myForm" class="pure-form" method="post" enctype="multipart/form-data" action="${pageContext.servletContext.contextPath}/mvc/console/fileupload/">
                    <fieldset>
                        <legend>
                            上傳 <select id="ds" name="ds">
                                <option value="holiday">Holiday 行事曆</option>
                            </select>
                        </legend>

                        請選擇上傳導案(CSV檔):<input type="file" id="myfile" name="file"><p />

                        <button id="submitBtn" type="submit" class="pure-button pure-button-primary">上傳</button>
                    </fieldset>
                </form>

                資料來源：
                <ol>
                    <li>
                        <a href="https://data.ntpc.gov.tw/datasets/308DCD75-6434-45BC-A95F-584DA4FED251">新北市政府資料開放平臺-政府行政機關辦公日曆表</a>
                    </li>
                </ol>


            </div>
        </div>

        <!-- Foot -->
        <%@include file="include/foot.jspf"  %>

    </body>
</html>