<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
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
            
            <div id="main">
                <div class="header">
                    <h1>行事曆批次修改</h1>
                </div>

                <form class="pure-form" method="post" action="/HSMySQL/mvc/console/calendar/update/batch" target="_parent;">
                    <!--抓取現在時間:年-->
                    <jsp:useBean id="now" class="java.util.Date" />
                    <fmt:formatDate var="year" value="${now}" pattern="yyyy" />
                    
                    每年4月, 8月的第二週與第四週的星期二與星期四的上午與晚上各幾人<p />
                    年份：<input type="number"  id="u_year" name="u_year" value="${year}"><p />
                    月份：
                    <input type="checkbox" id="u_month" name="u_month" value="1"> 1、
                    <input type="checkbox" id="u_month" name="u_month" value="2"> 2、
                    <input type="checkbox" id="u_month" name="u_month" value="3"> 3、
                    <input type="checkbox" id="u_month" name="u_month" value="4"> 4、
                    <input type="checkbox" id="u_month" name="u_month" value="5"> 5、
                    <input type="checkbox" id="u_month" name="u_month" value="6"> 6、
                    <input type="checkbox" id="u_month" name="u_month" value="7"> 7、
                    <input type="checkbox" id="u_month" name="u_month" value="8"> 8、
                    <input type="checkbox" id="u_month" name="u_month" value="9"> 9、
                    <input type="checkbox" id="u_month" name="u_month" value="10"> 10、
                    <input type="checkbox" id="u_month" name="u_month" value="11"> 11、
                    <input type="checkbox" id="u_month" name="u_month" value="12"> 12<p/>
                    週數：
                    <input type="checkbox" id="u_week" name="u_week" value="1"> 一、
                    <input type="checkbox" id="u_week" name="u_week" value="2"> 二、
                    <input type="checkbox" id="u_week" name="u_week" value="3"> 三、
                    <input type="checkbox" id="u_week" name="u_week" value="4"> 四、
                    <input type="checkbox" id="u_week" name="u_week" value="5"> 五<p /> 
                    星期：
                    <input type="checkbox" id="u_weekday" name="u_weekday" value="1"> 日、
                    <input type="checkbox" id="u_weekday" name="u_weekday" value="2"> 一、
                    <input type="checkbox" id="u_weekday" name="u_weekday" value="3"> 二、
                    <input type="checkbox" id="u_weekday" name="u_weekday" value="4"> 三、
                    <input type="checkbox" id="u_weekday" name="u_weekday" value="5"> 四、
                    <input type="checkbox" id="u_weekday" name="u_weekday" value="6"> 五、
                    <input type="checkbox" id="u_weekday" name="u_weekday" value="7"> 六<p/>
                    單位：
                    <input type="radio" id="u_nop" name="u_nop" value="ho" onchange="displayCheckBox(1)"> 診所、
                    <input type="radio" id="u_nop" name="u_nop" value="co" onchange="displayCheckBox(2)"> 公司、
                    <input type="radio" id="u_nop" name="u_nop" value="ph" onchange="displayCheckBox(3)"> 藥局、
                    <input type="radio" id="u_nop" name="u_nop" value="fa" onchange="displayCheckBox(4)"> 工廠<p/>
<!--                    ｒ時段：
                    <input type="radio" id="u_peroid" name="u_peroid" value="1"> 上午、
                    <input type="radio" id="u_peroid" name="u_peroid" value="2"> 下午、
                    <input type="radio" id="u_peroid" name="u_peroid" value="3"> 晚上<p/>-->
                    時段：
                    <script>
//                      <!--切換單位自動變換不同時段-->
                        function displayCheckBox(gid) {
                            console.log(gid);
                            var jsonData = JSON.parse('${schedulerItems}');
                            console.log(jsonData);
                            var tag = '';
                            for(var i=0;i<jsonData.length;i++) {
                                if(jsonData[i]['gid'] == gid) {
                                    var no = jsonData[i]['nop_name'];
                                    no = no.substring(2);
                                    tag += '<input type="radio" id="u_peroid" name="u_peroid" value="' + no + '"> ' + jsonData[i]['class_name'] + '　';
                                }
                            }
                            document.getElementById('peroid_span').innerHTML = tag;
                        }
                    </script>
                    <span id="peroid_span"></span><p />

                    人數：
                    <input type="number"   id="u_people" name="u_people" value="1"><p />
                    <button id="submitBtn" type="submit" class="pure-button pure-button-primary">修改</button>
                </form>
            </div>
        </div>


    </body>
</html>