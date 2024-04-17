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
        <script>
            function updateItem(tagId, id, columnName){
                var value = window.document.getElementById(tagId).value;
                console.log("value=" + value + ", id=" + id + ", columnName=" + columnName);
                
                var args = new Object();
                args.id = id;
                args.columnName = columnName;
                args.value = value;
                
                $.ajax({
                    url: "${pageContext.servletContext.contextPath}/mvc/console/setup/args/update",
                    type: 'POST',
                    contentType: 'application/json',
                    async: true,
                    data: JSON.stringify(args),
                    success: function (respData, status) {
                        if(respData == 1){
                            alert('修改成功 !');
                        } else if(respData == 0){
                            alert('修改失敗，資料庫無更新 !');
                        } else{
                            alert('資料庫修改筆數：' + respData);
                        }
                        //alert('修改成功!' + respData + ', ' + status);
                        //alert('修改成功!' + JSON.parse(respData));
                    },
                    error: function (thrownError) {
                        alert('修改錯誤! ' + thrownError);
                        console.log(thrownError);
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
                    <h1>後台設定</h1>
                    <h2>Args</h2>
                </div>
                <!-- 列表範本 -->    
                <table class="pure-table pure-table-bordered" width="100%">
                    <thead>
                        <tr>
<!--                            <th>id</th>
                            <th>name</th>
                            <th>intArg1</th>
                            <th>intArg2</th>
                            <th>floatArg1</th>
                            <th>floatArg2</th>
                            <th>strArg1</th>
                            <th>strArg2</th>
                            <th>memo</th>-->
                            <th>序號</th>
                            <th>參數名稱</th>
                            <th>第一整數參數</th>
                            <th>第二整數參數</th>
                            <th>第一浮點參數</th>
                            <th>第二浮點參數</th>
                            <th>第一字串參數</th>
                            <th>第一字串參數</th>
                            <th>備註</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="item" items="${list}" varStatus="loop">
                            <tr>
                                <td>${ item.id }</td>
                                <td>${ item.name }</td>
                                <td>
                                    <input id="args_${loop.index}_intArg1" type="text" value="${ item.intArg1 }" size="1" />
                                    <input type="button" value="修改" onclick="updateItem('args_${loop.index}_intArg1', ${ item.id }, 'int_Arg1')" />
                                </td>
                                <td>
                                    <input id="args_${loop.index}_intArg2"  type="text" value="${ item.intArg2 }" size="1" />
                                    <input type="button" value="修改" onclick="updateItem('args_${loop.index}_intArg2', ${ item.id }, 'int_Arg2')" />
                                </td>
                                <td><input id="args_${loop.index}_floatArg1" type="text" value="${ item.floatArg1 }" size="1" />
                                    <input type="button" value="修改" onclick="updateItem('args_${loop.index}_floatArg1', ${ item.id }, 'float_Arg1')" />
                                </td>
                                <td>
                                    <input id="args_${loop.index}_floatArg2" type="text" value="${ item.floatArg2 }" size="1" />
                                    <input type="button" value="修改" onclick="updateItem('args_${loop.index}_floatArg2', ${ item.id }, 'float_Arg2')" />
                                </td>
                                <td>
                                    <input id="args_${loop.index}_strArg1" type="text" value="${ item.strArg1 }" size="20" />
                                    <input type="button" value="修改" onclick="updateItem('args_${loop.index}_strArg1', ${ item.id }, 'str_Arg1')" />
                                </td>
                                <td>
                                    <input id="args_${loop.index}_strArg2" type="text" value="${ item.strArg2 }" size="20" />
                                    <input type="button" value="修改" onclick="updateItem('args_${loop.index}_strArg2', ${ item.id }, 'str_Arg2')" />
                                </td>
                                <td>
                                    <input id="args_${loop.index}_memo" type="text" value="${ item.memo }" size="50" />
                                    <input type="button" value="修改" onclick="updateItem('args_${loop.index}_memo', ${ item.id }, 'memo')" />
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