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
        <style>
            @page {
                size: A4 portrait;
                margin: 1%;
            }
        </style>
        <!-- signature -->
        <script>
            
            // 依日期條件開啟主管專用 hs每日工作回報 查詢
            // day:日期
            // HSMySQL/mvc/console/view/queryViewClickOnMemoReport?empNo=HS10330&d1=2021-01-04&d2=2021-01-04
            function openViewClickOnMemoReport(day) {
                // alert(day);
                var url = '/HSMySQL/mvc/console/view/queryViewClickOnMemoReport?menu_display=none&empNo=${empNo}&d1=${year}-${month<10?0:""}${month}-' + (day<10?0:"")+day + '&d2=${year}-${month<10?0:""}${month}-' + (day<10?0:"")+day;
                //alert(url);
                
                // 多視窗或單一視窗參數選擇
                // 如果是參數1之外，則為單視窗
                var multiPopWin = ${ applicationScope.args[5].intArg1 }   // args 資料表 id=6 的 int_arg1 設定
                var popWinName = 'mywindow';
                if( multiPopWin == 1 ){  // 如果是參數1，則為多視窗
                    var popWinName = 'mywindow${year}${month}'+day;
                }
                
                // 視窗開啟大小，以及視窗相關預設功能關閉
                var features = 'width=840,height=400, location=no, menubar=no, toolbar=no';
                window.open(url, popWinName, features);
            }
            
            function updateException(form) {
                var obj = new Object();
                obj.empNo = $("#empNo").val();
                obj.reportDate = form.reportDate.value;
                obj.exceptionMemo = form.exceptionMemo.value;
                obj.exceptionCheck = form.exceptionCheck.checked;

                //alert(JSON.stringify(obj));
                $.ajax({
                    url: "/HSMySQL/mvc/console/signature/add_clockon_exception",
                    type: 'POST',
                    contentType: 'application/json',
                    async: true,
                    data: JSON.stringify(obj),
                    success: function (data, status) {
                        if (data == 'OK') {
                            form.exceptionMemo.style.backgroundColor = 'white';
                            form.exceptionCheck.style.outline = 'thick solid white';
                            form.exceptionMemo.disabled = true;
                            form.exceptionCheck.disabled = true;
                            form.submitBtn.disabled = true;
                            form.authorEmpno.value = '${sessionScope.employee.empNo}';
                        } else {
                            alert('撰寫失敗: ' + data)
                        }
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert('撰寫失敗: ' + thrownError);
                    }

                });
                return false;
            }

            function openSignature() {
                window.open('/HSMySQL/signature.jsp');
            }
            function callBackIntegrationCompleted(code) {
                $("#signature_result").val(code);
                $("#signature_img").attr("src", code);

                var obj = new Object();
                obj.empNo = $("#empNo").val();
                obj.year = $("#year").val();
                obj.month = $("#month").val();
                obj.signature_result = $("#signature_result").val();

                $.ajax({
                    url: "/HSMySQL/mvc/console/signature/save",
                    type: 'POST',
                    contentType: 'application/json',
                    async: true,
                    data: JSON.stringify(obj),
                    success: function (data, status) {
                        alert('簽名檔存檔: ' + data);
                        location.reload();
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(thrownError);
                    }

                });
            }
            window.callBackIntegrationCompleted = callBackIntegrationCompleted;

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
                    <h1>HS 簽到確認表（財務專用）</h1>
                    <h2>資料查詢</h2>
                </div>
                <form class="pure-form" method="post" action="./signatureSubmitViewClockOnReportMark_M">
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
                    <button type="button" onclick="window.print();" class="pure-button pure-button-primary">表單列印</button>

                </form>
                <p />
                <table class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
                            <th nowrap>日期</th>
                            <th nowrap>星期</th>
                            <th nowrap>姓名</th>
                            
                            <th nowrap>1 班別</th>
                            <th nowrap>1 上班</th>
                            <th nowrap>1 下班</th>
                            
                            <th nowrap>2 班別</th>
                            <th nowrap>2 上班</th>
                            <th nowrap>2 下班</th>

                            <th nowrap>上班<br>狀況</th>
                            <th nowrap>遲到</th>
                            <th nowrap>下班<br>狀況</th>
                            <th nowrap>早退</th>
                            <th nowrap>出勤<br>狀況</th>
                            <th nowrap>異常<br>備註</th>
                            <th nowrap>異常<br>檢核</th>
                            <th nowrap>異常<br>修改</th>
                            <th nowrap>主管<br>員編</th>
                        </tr>
                    </thead>
                    <c:set var="late" value="0"/> 
                    <c:set var="leave" value="0"/>
                    <tbody>
                        <c:forEach var="d" begin="1" end="${end_day}">
                            <c:set var="found" value="0"/>
                            <c:forEach var="item" items="${list}">
                                <fmt:formatDate var="day" value="${item.reportDate}" pattern="dd" />
                                <c:if test="${d == day}">
                                    <c:set var="found" value="1" />
                                    <!-- 簽到資料 begin -->
                                <form onsubmit="return updateException(this)">
                                    <tr style="background-color:${item.week == 6 ? '#DFFFDF' : item.week == 7 ? '#FFE6D9' : ''}">
                                        <td>
                                            <span onclick="openViewClickOnMemoReport(${day})"
                                                    tytle="按我一下開啟每日回報"
                                                    style="color:blue; text-decoration: underline; text-decoration-color: blue; cursor: pointer" >
                                                
                                                    ${day}
                                            </span>

                                        </td>
                                        <td nowrap>${item.week}</td>
                                        <td nowrap>${item.empName}</td>
                                        
                                        <td nowrap>${item.statusShortname_1}</td>
                                        <td nowrap style="background-color:${item.mark1 == '*' ? 'red' : ''}"><font style="color:${item.mark1 == '*' ? 'yellow' : 'black'}">${item.clockOn_1}</font></td>
                                        <td nowrap style="background-color:${item.mark2 == '*' ? 'red' : ''}"><font style="color:${item.mark2 == '*' ? 'yellow' : 'black'}">${item.clockOn_2}</font></td>
                                        <td nowrap>${item.statusShortname_3}</td>
                                        <td nowrap style="background-color:${item.mark3 == '*' ? 'red' : ''}"><font style="color:${item.mark3 == '*' ? 'yellow' : 'black'}">${item.clockOn_3}</font></td>
                                        <td nowrap style="background-color:${item.mark4 == '*' ? 'red' : ''}"><font style="color:${item.mark4 == '*' ? 'yellow' : 'black'}">${item.clockOn_4}</font></td>

                                        <td nowrap>${item.mark1 == '*' or item.mark3 == '*' ? '異常' : '準時'}</td>
                                        <td nowrap align="right">
                                            <c:if test="${item.mark1 == '*' or item.mark3 == '*'}">
                                                <c:set var="late" value="${late + 1}" />
                                                <c:out value="${late}" />
                                            </c:if>
                                        </td>
                                        <td nowrap>${item.mark2 == '*' or item.mark4 == '*' ? '異常' : '準時'}</td>
                                        <td nowrap align="right">
                                            <c:if test="${item.mark2 == '*' or item.mark4 == '*'}">
                                                <c:set var="leave" value="${leave + 1}" />
                                                <c:out value="${leave}" />
                                            </c:if>
                                        </td>
                                        <td nowrap>-</td>
                                        <!-- 異常說明處置 begin -->
                                        <c:set var="exception_memo" value="" />
                                        <c:set var="exception_check" value="" />
                                        <c:set var="author_empno" value="" />

                                        <c:forEach var="e" items="${clockOnExceptions}">
                                            <c:if test="${item.reportDate == e.reportDate}">
                                                <c:set var="exception_memo" value="${e.exceptionMemo}" />
                                                <c:if test="${e.exceptionCheck}">
                                                    <c:set var="exception_check" value="checked" />
                                                </c:if>
                                                <c:set var="author_empno" value="${e.authorEmpno}" />
                                            </c:if>
                                        </c:forEach>
                                        <td nowrap>
                                            <c:if test="${item.mark1 == '*' or item.mark2 == '*' or item.mark3 == '*' or item.mark4 == '*'}">
                                                <input name="exceptionMemo" type="text" size="20" autocomplete="off" value="${exception_memo}" onkeydown="this.style.backgroundColor = 'pink';" onpaste="this.style.backgroundColor = 'pink';" onselect="this.style.backgroundColor = 'pink';" ${author_empno == ''?'':'disabled'}>
                                            </c:if>
                                        </td>
                                        <td nowrap>
                                            <c:if test="${item.mark1 == '*' or item.mark2 == '*' or item.mark3 == '*' or item.mark4 == '*'}">
                                                <input name="exceptionCheck" type="checkbox" ${exception_check}  onclick="this.style.outline = 'thick solid pink';" ${author_empno == ''?'':'disabled'}>
                                            </c:if>
                                        </td>
                                        <td nowrap>
                                            <c:if test="${item.mark1 == '*' or item.mark2 == '*' or item.mark3 == '*' or item.mark4 == '*'}">
                                                <input name="reportDate" type="hidden" value="<fmt:formatDate type="date" value="${item.reportDate}" />">
                                                <input type="submit" name="submitBtn" value="修改" ${author_empno == ''?'':'disabled'}>
                                            </c:if>
                                        </td>
                                        <td>
                                            <input name="authorEmpno" type="text" size="10" value="${author_empno}" style="border:none" readonly>
                                        </td>
                                        <!-- 異常說明處置 end -->
                                    </tr>
                                </form>
                                <!-- 簽到資料 end -->
                            </c:if>
                        </c:forEach>
                        <c:if test="${found eq 0}">
                            <c:set var="cdate" value="${year}/${month}/${d}" />
                            <fmt:parseDate value="${cdate}" var="date" pattern="yyyy/MM/dd"/>
                            <fmt:formatDate var="week" value="${date}" pattern="u" />
                            <tr style="background-color:${week == 6 ? '#DFFFDF' : week == 7 ? '#FFE6D9' : ''}">
                                <td>${d < 10 ? '0' : ''}${d}</td>
                                <td>
                                    ${week}
                                </td>
                                <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                                <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                                <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr bgcolor="#eeeeee">
                            <td colspan="10" rowspan="2">
                                <c:set var="amount_weekend" value="0" />
                                <c:forEach var="d" begin="1" end="${end_day}">
                                    <c:set var="cdate" value="${year}/${month}/${d}" />
                                    <fmt:parseDate value="${cdate}" var="date" pattern="yyyy/MM/dd"/>
                                    <fmt:formatDate var="week" value="${date}" pattern="u" />
                                    <c:if test="${week == 6}">
                                        <c:set var="amount_weekend" value="${amount_weekend + 1}" /> 
                                    </c:if>
                                </c:forEach>
                                
                                <c:set var="amount_weekend_clockon" value="0" />
                                <c:forEach var="item" items="${list}">
                                    <fmt:formatDate var="week" value="${item.reportDate}" pattern="u" />
                                    <c:if test="${week == 6}">
                                        <c:set var="amount_weekend_clockon" value="${amount_weekend_clockon + 1}" /> 
                                    </c:if>
                                </c:forEach>
                                當月打卡日數統計：${fn:length(list)} 次<br>
                                本月週六日數：${amount_weekend} 次<br>
                                本月週六打卡日數：${amount_weekend_clockon} 次
                            </td>
                            <td><b>遲到</b></td>
                            <td rowspan="2">&nbsp;</td>
                            <td><b>早退</b></td>
                            <td colspan="5" rowspan="2">&nbsp;</td>
                        </tr>
                        <tr bgcolor="#eeeeee">
                            <td align="right"><b>${late}</b></td>
                            <td align="right"><b>${leave}</b></td>
                        </tr>
                    </tfoot>
                </table>
            </div>
            <!-- signature -->
            <p />
            <button type="button" onclick="openSignature()" class="pure-button pure-button-primary">按我簽名</button>
            <p />
            <img width="400" id="signature_img" src="${signature.signImage}"/>
            <p />
            <input type="hidden" id="signature_result" name="signature_result">

        </div>
        <!-- Foot -->
        <%@include file="include/foot.jspf"  %>

    </body>
</html>