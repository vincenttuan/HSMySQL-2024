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
<script>
    function checkPwd() {
        // 檢查 newpwd
        var oldPwd = document.getElementById("oldPwd").value.trim();
        var newPwd1 = document.getElementById("newPwd1").value.trim();
        var newPwd2 = document.getElementById("newPwd2").value.trim();
        if(oldPwd.length == 0) {
            alert('請輸入舊密碼');
            return false;
        }
        if(newPwd1.length < 4) {
            alert('新密碼至少要輸入四碼');
            return false;
        }
        if(newPwd1 != newPwd2) {
            alert('新舊密碼不一致');
            return false;
        }
        if(oldPwd == newPwd1) {
            alert('新密碼與舊密碼不可相同');
            return false;
        }
        return true;
        
    }
</script>
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
                    <h1>HS 員工基本資料</h1>
                    <h2></h2>
                </div>
                <form class="pure-form" method="post" onsubmit="return checkPwd()" action="./updatePwd">
                    <fieldset>
                        <legend style="font-size: 30px">密碼變更（不分大小寫）</legend>
                        原密碼：<input type="password" id="oldPwd" name="oldPwd"><p/>
                        新密碼：<input type="password" id="newPwd1" name="newPwd1"><p/>
                        新密碼：<input type="password" id="newPwd2" name="newPwd2">（請再輸入一次新密碼）<p/>
                        <button type="submit" class="pure-button pure-button-primary">密碼變更</button>
                        <p />
                        <div style="font-size: 20px;color: red">${resp_msg}</div>
                    </fieldset>
                </form>
                <p />
            </div>
        </div>

        <!-- Foot -->
        <%@include file="include/foot.jspf"  %>

    </body>
</html>