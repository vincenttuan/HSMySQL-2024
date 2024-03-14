<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.1/build/pure-min.css">
        <title>HS 後台登入</title>
        <style>
            td {
                padding-bottom: 50px;
                padding-top: 50px;
                padding-left: 150px;
                padding-right: 150px;
                border-width:1px;
                border-style:solid;
                border-color:#CCCCCC;
            }
        </style>
    </head>
    <body style="padding:15px" bgcolor="#DDDDDD">
    <center>
        <table bgcolor="#FFFFFF">
            <td>
                <form class="pure-form" method="post" action="/HSMySQL/mvc/login/">
                    <fieldset>
                        <legend><h1><img src="images/user.png" width="40" valign="middle">HS 後台登入</h1></legend>

                        <input type="text" name="username" placeholder="Username" value=""><p />
                        <input type="password" name="password" placeholder="Password" value=""><p />

                        <button type="submit" class="pure-button pure-button-primary">登入</button>
                        <button type="button" class="pure-button pure-button-primary" onclick='window.location.href="/HSMySQL"'>回打卡主畫面</button>
                        
                        <c:if test="${not param.flag}">
                            帳號或密碼不正確
                        </c:if>
                        
                    </fieldset>
                </form>
            </td>
        </table>
    </center>
</body>
</html>
