<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
                    <h1>HS 簽到月報表</h1>
                    <h2>資料查詢</h2>
                </div>
                <form class="pure-form" method="post" action="./submitViewClockOnReportMark">
                    <c:if test="${priority <= 900}">
                        <input id="empNo" name="empNo" value="${empNo}" readonly>
                    </c:if>
                    <c:if test="${priority > 900}">
                        <select id="empNo" name="empNo" onchange="this.form.submit()" autofocus>
                            <c:forEach var="e" items="${emps}">
                                <option value="${e.empNo}" ${e.empNo == empNo ? 'selected' : ''} >${e.empNo} - ${e.empName}</option>
                            </c:forEach>
                        </select>
                    </c:if>
                    年：<input type="number" id="year" name="year" value="${year}">
                    月：<input type="number" id="month" name="month" value="${month}">
                    <button type="submit" class="pure-button pure-button-primary">查詢</button>
                </form>
                <p />
                <table class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
                            <th nowrap>日期</th>
                            <th nowrap>星期</th>
                            <th nowrap>班別</th>
                            <th nowrap>員編</th>
                            <th nowrap>姓名</th>

                            <th nowrap>1 上班</th>
                            <th nowrap>1 上班(標準)</th>
                            <th nowrap>1 下班</th>
                            <th nowrap>1 下班(標準)</th>
                            <th nowrap>2 上班</th>

                            <th nowrap>2 上班(標準)</th>
                            <th nowrap>2 下班</th>
                            <th nowrap>2 下班(標準)</th>
                            <th nowrap>上班時數</th>

                            <th nowrap colspan="2">每日工作回報</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="item" items="${list}">
                            <tr style="background-color:${item.week == 6 ? '#DFFFDF' : item.week == 7 ? '#FFE6D9' : ''}">
                                <c:set var="span" value="3"></c:set>
                                <td rowspan="${span}" nowrap><fmt:formatDate type="date" value="${item.reportDate}" /></td>
                                <td rowspan="${span}" nowrap>${item.week}</td>
                                <td rowspan="${span}" nowrap>${item.groupName}</td>
                                <td rowspan="${span}" nowrap>${item.empNo}</td>
                                <td rowspan="${span}" nowrap>${item.empName}</td>

                                <td rowspan="${span}" nowrap style="background-color:${item.mark1 == '*' ? 'red' : ''}"><font style="color:${item.mark1 == '*' ? 'yellow' : 'black'}">${item.clockOn_1}</font></td>
                                <td rowspan="${span}" nowrap>${item.standard_1}</td>
                                <td rowspan="${span}" nowrap style="background-color:${item.mark2 == '*' ? 'red' : ''}"><font style="color:${item.mark2 == '*' ? 'yellow' : 'black'}">${item.clockOn_2}</font></td>
                                <td rowspan="${span}" nowrap>${item.standard_2}</td>
                                <td rowspan="${span}" nowrap style="background-color:${item.mark3 == '*' ? 'red' : ''}"><font style="color:${item.mark3 == '*' ? 'yellow' : 'black'}">${item.clockOn_3}</font></td>

                                <td rowspan="${span}" nowrap>${item.standard_3}</td>
                                <td rowspan="${span}" nowrap style="background-color:${item.mark4 == '*' ? 'red' : ''}"><font style="color:${item.mark4 == '*' ? 'yellow' : 'black'}">${item.clockOn_4}</font></td>
                                <td rowspan="${span}" nowrap>${item.standard_4}</td>
                                <td rowspan="${span}" nowrap align="right" style="background-color:${item.diffHh < 8.0 ? '#8B0000' : ''}"><font style="color:${item.diffHh < 8.0 ? 'yellow' : 'black'}"><fmt:formatNumber pattern="#0.000" value="${item.diffHh}" /></font></td>


                                <td nowrap colspan="2">工作回報：${item.memo}&nbsp;</td>

                            </tr>
                            <!--主管回復及考核評分及回復-->
                            <tr style="display:${span == '1' ? 'none' : ''};background-color:${item.week == 6 ? '#DFFFDF' : item.week == 7 ? '#FFE6D9' : ''}">
                                <td rowspan="2" nowrap style="color: rgb(0, 0, 0);width: 70px;">
                                    主管回覆
                                </td>
                                <td nowrap style="color: rgb(0, 51, 0);"">
                                    回報內容:${fn:length(item.memo) == 0?'不合格':item.replyScore==1?'待加強':item.replyScore==5?'優良':'尚可'}。  主管回覆：${fn:length(item.memo) == 0?'本日工作回報尚未撰寫，不合格！':item.replyContent} 
                                </td>
                            </tr>
                            <tr style="display:${span == '1' ? 'none' : ''};background-color:${item.week == 6 ? '#DFFFDF' : item.week == 7 ? '#FFE6D9' : ''}">
                                <td nowrap style="color: rgb(207, 60, 50);"">
                                    <!--
                                    表現考核:${item.assessmentScore<0?'待加強':item.assessmentScore>0?'優良':'可'}。  考核評語：${item.assessmentContent}
                                    -->
                                    考核尚未啟用
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <center>
                    <br>
                    <form class="pure-form" method="post" action="../clockonreport/updatememo">
                        <fieldset>
                            <legend>
                                </br>
                                </br>
                                </br>
                                <font size='6'>每日工作回報撰寫區</br>
                                <font size='4' style = 'color:#7B7B7B'>請詳細填寫當日工作回報及出勤註記，並且標註標點符號，當日內容於當天23:59之前皆可修正</br>
                                <font size='4' style = 'color:#7B7B7B'>請假申請事項禁止在此敘述
                            </legend>
                        </fieldset>
                        <c:if test="${memo == null || memo == ''}">
                            <c:set var="memo" value="一、工作事項回報：&#13;&#10;1. &#13;&#10;2. &#13;&#10;3. &#13;&#10;4. &#13;&#10;5. &#13;&#10;&#13;&#10;二、交接事項回報：&#13;&#10;1. &#13;&#10;2. &#13;&#10;3. &#13;&#10;4. &#13;&#10;5. &#13;&#10;&#13;&#10;三、患者回復與處理：&#13;&#10;1. 患者姓名：&#13;&#10;&nbsp;&nbsp;&nbsp;發生時間：&#13;&#10;&nbsp;&nbsp;&nbsp;事件敘述：&#13;&#10;&nbsp;&nbsp;&nbsp;處理方式：&#13;&#10;&#13;&#10;2. 患者姓名：&#13;&#10;&nbsp;&nbsp;&nbsp;發生時間：&#13;&#10;&nbsp;&nbsp;&nbsp;事件敘述：&#13;&#10;&nbsp;&nbsp;&nbsp;處理方式：&#13;&#10;&#13;&#10;3. 患者姓名：&#13;&#10;&nbsp;&nbsp;&nbsp;發生時間：&#13;&#10;&nbsp;&nbsp;&nbsp;事件敘述：&#13;&#10;&nbsp;&nbsp;&nbsp;處理方式：&#13;&#10;&#13;&#10;四、突發事件回報：&#13;&#10;1. 發生時間：&#13;&#10;&nbsp;&nbsp;&nbsp;事件敘述：&#13;&#10;&nbsp;&nbsp;&nbsp;處理方式：&#13;&#10;&#13;&#10;2. 發生時間：&#13;&#10;&nbsp;&nbsp;&nbsp;事件敘述：&#13;&#10;&nbsp;&nbsp;&nbsp;處理方式：&#13;&#10;&#13;&#10;3. 發生時間：&#13;&#10;&nbsp;&nbsp;&nbsp;事件敘述：&#13;&#10;&nbsp;&nbsp;&nbsp;處理方式：&#13;&#10;&#13;&#10;"></c:set>
                        </c:if>
                        <textarea id="memo" name="memo" rows="45" cols="100" placeholder="請詳述工作內容...">${memo}</textarea>

                        <p />
                        <button type="submit" class="pure-button pure-button-primary">撰寫完成</button>
                    </form>
                </center>

            </div>
        </div>

        <!-- Foot -->
        <%@include file="include/foot.jspf"  %>

    </body>
</html>