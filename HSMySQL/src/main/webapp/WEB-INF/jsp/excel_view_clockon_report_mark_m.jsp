<%@page import="com.hs.mvc.entity.views.ViewClockOnReportMark"%>
<%@page import="com.hs.mvc.entity.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="application/vnd.ms-excel"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%    // prevent browser cache jsp     
    response.setHeader("Pragma", "No-cache"); // HTTP 1.0
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setDateHeader("Expires", -1); // proxies
    List<ViewClockOnReportMark> list = (List<ViewClockOnReportMark>)request.getAttribute("list");
    String fname = list.get(0).getEmpNo() + list.get(0).getEmpName() + ".xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + fname);
%>

<html lang="en">
    <head>
        
    </head>
    <body style="padding: 10px">

        <div id="layout">
            
            <div id="main">
                
                <table class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
                            <th nowrap>日期</th>
                            <th nowrap>星期</th>
                            <th nowrap>班別</th>
                            <th nowrap>員編</th>
                            <th nowrap>姓名</th>
                            <th nowrap>1 上班</th>
                            <th nowrap>1 下班</th>
                            <th nowrap>2 上班</th>
                            <th nowrap>2 下班</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${list}">
                            <tr style="background-color:${item.week == 6 ? '#DFFFDF' : item.week == 7 ? '#FFE6D9' : ''}">
                                <td nowrap><fmt:formatDate type="date" value="${item.reportDate}" /></td>
                                <td nowrap>${item.week}</td>
                                <td nowrap>${item.groupName}</td>
                                <td nowrap>${item.empNo}</td>
                                <td nowrap>${item.empName}</td>
                                <td nowrap style="background-color:${item.mark1 == '*' ? 'red' : ''}"><font style="color:${item.mark1 == '*' ? 'yellow' : 'black'}">${item.clockOn_1}</font></td>
                                <td nowrap style="background-color:${item.mark2 == '*' ? 'red' : ''}"><font style="color:${item.mark2 == '*' ? 'yellow' : 'black'}">${item.clockOn_2}</font></td>
                                <td nowrap style="background-color:${item.mark3 == '*' ? 'red' : ''}"><font style="color:${item.mark3 == '*' ? 'yellow' : 'black'}">${item.clockOn_3}</font></td>
                                <td nowrap style="background-color:${item.mark4 == '*' ? 'red' : ''}"><font style="color:${item.mark4 == '*' ? 'yellow' : 'black'}">${item.clockOn_4}</font></td>
                                
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