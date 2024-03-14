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
        <!--script-->
        <script>
            // 自動依照勾選狀態新增或刪除權限Priority的輸入框
            // 參數：cb(核選盒)、spanId(權限Priority輸入框的配置位置)
            function addOrDeletePriorityInput(cb, spanId) {
                var span = document.getElementById(spanId);
                var inputHtml = '權限：<input type="number" id="css_priority" name="css_priority" value="1">';
                if (cb.checked) {
                    // cb(核選盒)已核選
                    span.innerHTML = inputHtml; // 塞入權限riority輸入框
                } else {
                    span.innerHTML = ''; //清空
                }
            }
            
            // 取得最員工編號
            function getNextEmpNo() {
                var args = new Object();
                args.deptId = document.getElementById('dept_id1').value;
                $.ajax({
                    url: "${pageContext.servletContext.contextPath}/mvc/console/employee/getNextEmpNo",
                    type: 'POST',
                    contentType: 'application/json',
                    async: true,
                    data: JSON.stringify(args),
                    success: function (respData, status) {
                        document.getElementById('emp_no').value = respData;
                    },
                    error: function (thrownError) {
                        document.getElementById('emp_no').value = 'HS';
                    }
                });
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
                    <h1>HS 員工基本資料維護</h1>
                    <h2 style="color : blue">
                        <c:if test="${ not empty param.action }">
                            ${ employee.empNo } ${ employee.empName } "修改成功!"
                        </c:if>
                    </h2>
                </div>
                <form class="pure-form" method="post" onsubmit="return true" action="${pageContext.servletContext.contextPath}/mvc/console/employee/${ actionPath }">
                    <table>
                        <tr>
                            <td valign="top" style="padding: 15px">
                                <fieldset>
                                    <legend style="font-size: 30px">員工資料列表 <button style="font-size: 15px" type="button" class="pure-button pure-button-primary" onclick="window.location.href = '${pageContext.servletContext.contextPath}/mvc/console/employee/input?csId=13'">員工新增</button></legend>
                                    
                                    <table class="pure-table pure-table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Dept2</th>
                                                <th>DeptName</th>
                                                <th>Id</th>
                                                <th>No</th>
                                                <th>Name</th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:forEach var="emp" items="${ employeeList }">
                                                <tr onmouseover="this.bgColor = '#ECFFFF'"
                                                    onmouseout="this.bgColor = '#FFFFFF'"
                                                    title="點擊可以顯示資料"
                                                    style="cursor: pointer" 
                                                    onclick="window.location.href = '${pageContext.servletContext.contextPath}/mvc/console/employee/find?empId=${ emp.empId }'">
                                                    <td>${ emp.deptId2 }</td>
                                                    <td>
                                                        <c:forEach var="d" items="${ deptList }">
                                                            <c:if test="${d.id == emp.deptId2}">
                                                                ${d.fullName}
                                                            </c:if>
                                                        </c:forEach>
                                                    </td>
                                                    <td>${ emp.empId }</td>
                                                    <td>${ emp.empNo }</td>
                                                    <td>${ emp.empName }</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>


                                </fieldset>
                            </td>
                            
                            <td valign="top" style="padding: 15px">
                                <fieldset>
                                    <legend style="font-size: 30px">員工基本資料<button style="font-size: 15px" type="submit" class="pure-button pure-button-primary">員工${ actionText }</button></legend>
                                    主要部門： 
                                    <select id="dept_id1" name="dept_id1" onchange="document.getElementById('dept_id2').value = this.value" >
                                        <c:forEach var="d" items="${ deptList }">
                                            <option value="${ d.id }" ${ d.id == employee.deptId1 ? "selected" : "" }  >${ d.id } - ${ d.fullName }</option> 
                                        </c:forEach>
                                    </select>(員工編號以主要部門為主)<p/>
                                    支援部門： 
                                    <select id="dept_id2" name="dept_id2">
                                        <c:forEach var="d" items="${ deptList }">
                                            <option value="${ d.id }" ${ d.id == employee.deptId2 ? "selected" : "" }  >${ d.id } - ${ d.fullName }</option> 
                                        </c:forEach>
                                    </select>(有支援部門設定，以支援部門為出勤打卡依據)<p/>
                                    員工編號：  <input size="10" type="text" id="emp_no" name="emp_no" value="${ employee.empNo }" ${ fn:length(employee.empNo) > 0 ? "readonly" : ""} >
                                    <button type="button" 
                                            ${ fn:length(employee.empNo) > 0 ? "disabled" : ""}
                                            onclick="getNextEmpNo()"
                                            class="pure-button pure-button-primary">自動</button><p/>
                                    員工姓名：  <input type="text" id="emp_name" name="emp_name"" value="${ employee.empName }"><p/>
                                    員工RFID： <input type="text" id="emp_rfid" name="emp_rfid"" value="${ employee.empRfid }"><p/>
                                    代理人編號：
                                    <select id="agent1_id" name="agent1_id">
                                        <c:forEach var="e" items="${ employeeList }">
                                            <option value="${ e.empId }" ${ e.empId == employee.agent1Id ? "selected" : "" }  >${ e.empId } - ${ e.empName }</option> 
                                        </c:forEach>
                                    </select>
                                    <p/>
                                    員工權限：  <input type="number" value="0" id="emp_priority" name="emp_priority"" value="${ employee.empPriority }"><p/>
                                    
                                </fieldset>
                            </td>

                            <td valign="top" style="padding: 15px">
                                <fieldset>
                                    <legend style="font-size: 30px">使用功能與權限 <button style="font-size: 15px" type="submit" class="pure-button pure-button-primary">員工${ actionText }</button></legend>
                                    <c:forEach var="cs" items="${consoleServiceList}">
                                        ${cs.csId}:
                                        <input type="checkbox"
                                               id="cs_id"
                                               name="cs_id"

                                               <c:forEach var="css" items="${ consoleServiceSetupList }">
                                                   ${ cs.csId == css.csId ? "checked" : "" }
                                               </c:forEach>

                                               value="${cs.csId}"
                                               onclick="addOrDeletePriorityInput(this, 'p${ cs.csId }')">
                                        ${ cs.csName },

                                        <span id="p${ cs.csId }">
                                            <c:forEach var="css" items="${ consoleServiceSetupList }">
                                                <c:if test="${ cs.csId == css.csId }">
                                                    權限：<input type="number" id="css_priority" name="css_priority" value="${ css.cssPriority }">
                                                </c:if>
                                            </c:forEach>
                                        </span><p/>

                                    </c:forEach>

                                    <button type="submit" class="pure-button pure-button-primary">員工${ actionText }</button>

                                </fieldset>
                            </td>

                            

                        </tr>
                    </table>




                </form>
                <p />
            </div>
        </div>

        <!-- Foot -->
        <%@include file="include/foot.jspf"  %>

    </body>
</html>