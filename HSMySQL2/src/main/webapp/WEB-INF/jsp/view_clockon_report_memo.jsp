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
        <script>
            function reply(form) {
                var obj = new Object();
                obj.reportDate = form.reportDate.value;
                obj.empNo = form.empNo.value;
                obj.replyScore = form.replyScore.value;
                obj.replyContent = form.replyContent.value;
                /*
                if (obj.replyScore == 0) {
                    alert('每日回報評分不可為 0');
                    return false;
                }
                */
                if (obj.replyContent == '') {
                    obj.replyContent = '已讀';
                }

                $.ajax({
                    url: "/HSMySQL/mvc/console/clockonreport/updateReply",
                    type: 'POST',
                    contentType: 'application/json',
                    async: true,
                    data: JSON.stringify(obj),
                    success: function (data, status) {
                        if (data == '1') {
                            alert('資料傳送成功');
                        } else {
                            alert(data + '\n' + status);
                        }
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(thrownError);
                    }

                });
                return false;
            }

            function assessment(form) {
                var obj = new Object();
                obj.reportDate = form.reportDate.value;
                obj.empNo = form.empNo.value;
                obj.assessmentScore = form.assessmentScore.value;
                obj.assessmentContent = form.assessmentContent.value;
                $.ajax({
                    url: "/HSMySQL/mvc/console/clockonreport/updateAssessment",
                    type: 'POST',
                    contentType: 'application/json',
                    async: true,
                    data: JSON.stringify(obj),
                    success: function (data, status) {
                        if (data == '1') {
                            alert('資料傳送成功');
                        } else {
                            alert(data + '\n' + status);
                        }
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(thrownError);
                    }

                });
                return false;
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
                    <h1>HS 每日工作回報（主管專用）</h1>
                    <h2>資料查詢</h2>
                </div>
                <form style="display: ${ menu_display }" class="pure-form" method="post" action="./queryViewClickOnMemoReport">
                    <select id="empNo" name="empNo" onchange="this.form.submit()" autofocus>
                        <option value='all'>全部</option>
                        <c:forEach var="e" items="${emps}">
                            <option value="${e.empNo}" ${e.empNo == empNo ? 'selected' : ''} >${e.empNo} - ${e.empName}</option>
                        </c:forEach>
                    </select>
                    <input type="date" id="d1" name="d1" value="${d1}">
                    <input type="date" id="d2" name="d2" value="${d2}">
                    <button type="submit" class="pure-button pure-button-primary">查詢</button>
                </form>
                <p />
                <table class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
                            <th nowrap>日期</th>
                            <th nowrap>星期</th>
                            <th nowrap>員編</th>
                            <th nowrap>姓名</th>

                            <th nowrap>1 班別</th>
                            <th nowrap>1 上班</th>
                            <th nowrap>1 上班(標準)</th>
                            <th nowrap>1 下班</th>
                            <th nowrap>1 下班(標準)</th>
                            
                            <th nowrap>2 班別</th>
                            <th nowrap>2 上班</th>
                            <th nowrap>2 上班(標準)</th>
                            <th nowrap>2 下班</th>
                            <th nowrap>2 下班(標準)</th>
                            <th nowrap>上班時數</th>
                            <th nowrap>休息時數</th>

                            <!--<th nowrap>Result</th>-->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${list}">
                            <tr style="background-color:${item.week == 6 ? '#DFFFDF' : item.week == 7 ? '#FFE6D9' : ''}">
                                <td nowrap><fmt:formatDate type="date" value="${item.reportDate}" /></td>
                                <td nowrap>${item.week}</td>
                                <td nowrap>${item.empNo}</td>
                                <td nowrap>${item.empName}</td>

                                <td nowrap>${item.statusName_1}</td>
                                <td nowrap style="background-color:${item.mark1 == '*' ? 'red' : ''}"><font style="color:${item.mark1 == '*' ? 'yellow' : 'black'}">${item.clockOn_1}</font></td>
                                <td nowrap>${item.standard_1}</td>
                                <td nowrap style="background-color:${item.mark2 == '*' ? 'red' : ''}"><font style="color:${item.mark2 == '*' ? 'yellow' : 'black'}">${item.clockOn_2}</font></td>
                                <td nowrap>${item.standard_2}</td>
                                
                                <td nowrap>${item.statusName_3}</td>
                                <td nowrap style="background-color:${item.mark3 == '*' ? 'red' : ''}"><font style="color:${item.mark3 == '*' ? 'yellow' : 'black'}">${item.clockOn_3}</font></td>
                                <td nowrap>${item.standard_3}</td>
                                <td nowrap style="background-color:${item.mark4 == '*' ? 'red' : ''}"><font style="color:${item.mark4 == '*' ? 'yellow' : 'black'}">${item.clockOn_4}</font></td>
                                <td nowrap>${item.standard_4}</td>
                                <td nowrap align="right"><fmt:formatNumber pattern="#0.00" value="${item.diffHh}" /></td>
                                <td nowrap align="right"><fmt:formatNumber pattern="#0.00" value="${item.restHh}" /></td>

                            </tr>
                            <tr>
                                <td colspan="8" valign="top">
                                    <textarea readonly cols="100" rows="10" style="border: none">${item.memo}</textarea>
                                </td>
                                <td colspan="4" valign="top">
                                    <form onsubmit="return reply(this)">
                                        <!-- 指定回覆日期與員編 -->
                                        <input type='hidden' name='reportDate' value='<fmt:formatDate type="date" value="${item.reportDate}" />'>
                                        <input type='hidden' name='empNo' value='${item.empNo}'>
                                        每日回報評分：
                                        <select id='replyScore' name='replyScore'>
                                            <c:forEach var="i" begin="0" end="5" varStatus="loop">
                                                <option value='${i}' ${item.replyScore == i ? 'selected' : ''}>${i}</option>
                                            </c:forEach>
                                        </select>
                                        <br>
                                        主管回覆內容：<br>
                                        <textarea id='replyContent' name='replyContent' rows="5" cols="45" >${item.replyContent}</textarea><br>
                                        <button id="postReplyBtn" type="submit" class="pure-button pure-button-primary">寫完傳送</button>
                                    </form>
                                </td>
                                <td colspan="4" valign="top">
                                    <form onsubmit="return assessment(this)">
                                        <!-- 指定回覆日期與員編 -->
                                        <input type='hidden' name='reportDate' value='<fmt:formatDate type="date" value="${item.reportDate}" />'>
                                        <input type='hidden' name='empNo' value='${item.empNo}'>
                                        每日考核評分：
                                        <select id='assessmentScore' name='assessmentScore'>
                                            <c:forEach var="i" begin="0" end="10" varStatus="loop">
                                                <option value='${i-5}' ${item.assessmentScore == (i-5) ? 'selected' : ''}>${i-5}</option>
                                            </c:forEach>
                                        </select>
                                        <br>
                                        主管考核內容：<br>
                                        <textarea id='assessmentContent' name='assessmentContent' rows="5" cols="45" >${item.assessmentContent}</textarea><br>
                                        <button id="postReplyBtn" type="submit" class="pure-button pure-button-primary">寫完傳送</button>
                                    </form>
                                </td>
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