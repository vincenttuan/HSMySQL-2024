<%@page contentType="text/html" pageEncoding="UTF-8" import="java.util.Random" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%    // prevent browser cache jsp     
    response.setHeader("Pragma", "No-cache"); // HTTP 1.0
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setDateHeader("Expires", -1); // proxies
%>
<html>
    <head>
    	<script src="../../js/checkargskey.js?<%=new Random().nextInt(100000) %>" />
    	<script src="../../js/checkgps.js?<%=new Random().nextInt(100000) %>" />
        <script src="../../js/checkflow.js?<%=new Random().nextInt(100000) %>" />
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HS 員工簽到表 II</title>
        <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.1/build/pure-min.css">

        <!--匯入jquery提供下方script使用-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <!--呼叫ipcheck.jspf確認ip資料-->
        <%--<%@include file="include/ipcheck.jspf" %>--%>

        <script>
            // 在 empNo 欄位中按下 enter 就可以查詢今日員工簽到時段
            // Window : 放HTML文件(document)的容器
            // 當HTML文件準備好之後所要做的配置
            $(document).ready(function () {

                $(window).keydown(function (event) {
                    if (event.keyCode == 13) {
                        // 停止網頁預設的 enter 行為
                        event.preventDefault();
                        return false;
                    }
                });
                // Get the input field
                var input = document.getElementById("empNo");
                // 在 empNo 欄位中按下 enter 就可以查詢今日員工簽到時段
                input.addEventListener("keyup", function (event) {
                    // Number 13 is the "Enter" key on the keyboard
                    if (event.keyCode === 13) { // 13 是 enter 鍵的碼
                        // 查詢今日員工簽到時段
                        document.getElementById("searchBtn").click();
                    }
                });
            });



            //查詢今日員工簽到時段
            function queryTodaySchedulerEmployee() {
                //查詢得到資料之前對打卡Btn先取消作用
                $('#signInBtn').prop("disabled", true);
                $('#schedulerStatus').html(''); //清空html裡面現有的個人排班的資料
                // 得到員工編號，以#取得empNo物件中 90行的input path="empNo" 建立起來的empId
                var empNo = $('#empNo').val();
                if (empNo == '') {
                    alert('請輸入員工編號');
                    $('#empNo').focus(); //讓游標移回這裡
                    return;
                }
                //alert(empNo);
                //組合json物件
                var obj = new Object();
                //發送一個ajax請求給clock_on
                obj.empNo = empNo;
                //alert(JSON.stringify(obj)); //將JSON.stringify(obj)內容秀出來
                $.ajax({
                    //${pageContext.servletContext.contextPath}：抓取META-INF中context敘述的路徑
                    url: "${pageContext.servletContext.contextPath}/mvc/clock_on/query/scheduler/employee",
                    type: 'POST',
                    contentType: 'application/json',
                    async: true, //true 非同步
                    data: JSON.stringify(obj), //將obj轉成JSON
                    success: function (respData, status) {
                        if (status == 'success') {
                            //alert("Success: " + respData);
                            //console.log(JSON.stringify(respData)); //讓瀏覽器以F12呈現錯誤訊息
                            autoGenSchedulerStatusHTML(respData);
                        } else {
                            alert("錯誤查詢: " + status);
                        }
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert("Error: " + JSON.stringify(xhr).replace(/<[^>]+>/g, ""));
                    }
                });
            }

            //自己刻format方法，塞資料進去 (給"自動產生打卡時段HTML"用)
            String.prototype.format = function () {
                var s = this, i = arguments.length;
                while (i--) {
                    s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
                }
                return s;
            };


            //自動產生打卡時段HTML
            function autoGenSchedulerStatusHTML(respData) {
                var list = respData['scheduler_list'];
                var clockon_list = respData['clockon_list'];
                $('#schedulerStatus').html(''); //清空html裡面的資料
                if (list.length == 0) {
                    alert('查無此員工今日班表資料!');
                    $('#signInBtn').prop("disabled", true);
                    $('#empNo').select();
                    $('#empNo').focus();
                    return;
                }
                for (var i = 0; i < list.length; i++) {
                    //利用上面刻的format方法塞入資料
                    var tag = '<input onclick="openSignInBtn()" id="statusId{0}" name="statusId" type="radio" value="{1}"/>&nbsp;&nbsp;<span style="color:{2}">{3}</span>&nbsp;&nbsp;&nbsp;&nbsp;'
                            .format((i + 1), list[i].statusId, changeClockOnItemColor(list[i].statusId, clockon_list), list[i].statusName);

                    $('#schedulerStatus').append(tag); //加入tag標籤
                }
            }
            // 變更簽到時段項目顏色(已經簽到的時段)
            function changeClockOnItemColor(statusId, clockon_list) {
                if (clockon_list.length == 0) {
                    return '#0000ff';
                }
                for (var i = 0; i < clockon_list.length; i++) {
                    if (clockon_list[i].statusId == statusId) {
                        return '#dddddd';
                    }
                }
                return '#0000ff';
            }

            // 當有選擇簽到時段的選項時，就開放簽到按鈕可以按
            function openSignInBtn() {
                // display signInBtn
                $('#signInBtn').prop("disabled", false);
            }
        </script>

    </head>
    <!--<body id="mybody" style="padding: 10px;visibility: hidden">-->
    <body id="mybody" style="padding: 10px;">
        <a href="${pageContext.servletContext.contextPath}/login.jsp?flag=true">
            <img src="${pageContext.servletContext.contextPath}/images/user.png" width="40" valign="middle" border="0">HS 後台登入</h1>
        </a>
    <center>
        <h1>HS 員工簽到表 II</h1>
        <h2> <font size="3" color="#c7c7c7"></h2>
        <table border="0">
            <tr>
                <td valign="top" style="padding: 30px">
                    <video id="player" width="320" height="240" controls autoplay></video>
                    <!--建立公佈欄-->
                    <p />
                    <form class="pure-form">
                        <fieldset>
                            <legend>公告欄(試用版)：</legend>
                            1.簽到系統第二版(上線測試版)
                            <br>&nbsp;&nbsp;&nbsp;公告日期:2021/03/01
                            <ul>
                                <font size="2">
                                <li><font color="#CC0000">可進行排班打卡功能</font>
                                    <br>預設排班時段與第一版已銜接</li><br>
                                <li><font color="#CC0000">每日回報系統已啟用</font>
                                    <br>每日回報系統已測試完成</li><br>
                                <li><font color="#CC0000">彩蛋驚喜活動進行中</font>
                                    <br>【新版發現彩蛋活動得主出現！】</li><br>
                                <br>【其他彩蛋還在保溫中，記得多找找！】</li><br>
                                </font>
                            </ul>

                            2.打卡標準時間更新如下：
                            <!--                            <br>&nbsp;&nbsp;&nbsp;公告日期:2020/06/02-->
                            <!--                            <ul>
                                                            <font size="2">
                                                            <font color="#0000E3">
                                                            <li>診所周一至周五(早班)<br>上班 0800 中午休息 1300 中午上班 1500</li><br>
                                                            <li>診所周一至周五(晚班)<br>上班 1300 下午休息 1700 小夜上班 1800</li><br>
                                                            <li>診所周一至周五(早晚班)<br>上班 0900 中午休息 1300 小夜上班 1700</li><br>
                                                            </font>
                            
                                                            <font color="#007500">
                                                            <li>公司(早午班)<br>上班 0900 中午休息 1300 中午上班 1400</li><br>
                                                            <li>公司(值日班)<br>上班 0900 中午休息 1200 中午上班 1300</li><br>
                                                            </font>
                            
                                                            <font color="#844200">
                                                            <li>診所周六(早班)<br>上班 0800 中午休息 1300 中午上班 1500</li><br>
                                                            <li>診所周六(晚班)<br>上班 1200 中午休息 1700 中午上班 1730</li><br>
                                                            <li>診所周六(早晚班)<br>上班 0900 中午休息 1300 中午上班 1500</li><br>
                                                            </font>
                                                            </font>
                                                        </ul>-->

                            <br>&nbsp;&nbsp;&nbsp;公告日期:2021/05/25
                            <table class="pure-table pure-table-bordered">
                                <thead>
                                    <tr>
                                        <th>時段</th>
                                        <th>上班</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="s" items="${ list_status }">
                                        <c:if test="${fn:contains(s.statusName, '上班')}">
                                            <tr>
                                                <td>${ s.statusName }</td>
                                                <td>${ s.statusBegin }:00</td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </tbody>
                            </table>


                            <legend>公告欄結尾</legend>
                            <!--第二版已經啟用，先將連結按鈕隱藏-->
                            <!--<button id="clockOn2Btn" type="button" class="pure-button pure-button-primary" onclick="window.location.href = '${pageContext.servletContext.contextPath}/mvc/clock_on/input'">簽到系統第一版</button>-->
                        </fieldset>
                    </form>
                </td>
                <td valign="top" style="padding: 30px">
                    <form:form id="signInForm" class="pure-form" modelAttribute="clockOn" method="post" action="${pageContext.servletContext.contextPath}/mvc/clock_on/in2">
                        <fieldset>
                            <legend>員工編號</legend>
                            <!--關閉自動完成  autocomplete="off"-->
                            <form:input path="empNo" placeholder="請輸入員工編號" autocomplete="off"/>
                            <button id="searchBtn" type="button" class="pure-button pure-button-primary" onclick="queryTodaySchedulerEmployee()">查詢今日簽到時段</button>
                            <p />

                            <legend>簽到時段</legend>

                            <div id="schedulerStatus"></div>
                            <br><br>

                            <form:hidden path="image" />
                            <button id="signInBtn" disabled type="button" class="pure-button pure-button-primary">簽到</button>
                            <button id="resetBtn" type="button" class="pure-button pure-button-primary" onclick="window.location.href = '${pageContext.servletContext.contextPath}/mvc/clock_on/input2'">清空畫面</button>
                            <span style="font-weight:bold;font-family:Arial;font-size:20px;color:red">${message}</span> 
                            <br>打卡後請記得<font color='RED'>【清空畫面】！
                            <br>
                            <br><font color='BLUE'>打卡注意貼士(試用版)：
                            <br><font color='RED'>簽到前請先閱讀公告事項，
                            <br><font color='RED'>簽到表示已知悉事項內容。
                        </fieldset>
                    </form:form>

            <legend><h2 class="content-subhead">${empName}</h2></legend>

            <table class="pure-table pure-table-bordered" width="100%">
                <thead>
                    <tr>
                        <th>ID序號</th>
                        <th>員工編號</th>
                        <th>簽到時段</th>
                        <th>簽到時間</th>
                        <th width="200">簽到影像</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="item" items="${list_clockon}">
                        <tr>
                            <td nowrap>${item.clockId}</td>
                            <td nowrap>${item.empNo} ${item.empName}</td>
                            <td nowrap>${item.statusId} ${item.statusName}</td>
                            <td nowrap>${item.clockOn}</td>
                            <td nowrap><img src="${item.image}" width="50" onclick="this.style.width = '200px'" ondblclick="this.style.width = '50px'"></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table> 

            </td>
            </tr>


        </table>
    </center>

    <button id="capture" style="visibility: hidden">Capture</button>
    <canvas id="snapshot" style="visibility: hidden" width="320" height="240"></canvas>

    <script>
        var player = document.getElementById('player');
        var captureButton = document.getElementById('capture');
        var snapshotCanvas = document.getElementById('snapshot');
        var signInBtn = document.getElementById('signInBtn');

        var handleSuccess = function (stream) {
            // Attach the video stream to the video element and autoplay.
            player.srcObject = stream;
        };

        captureButton.addEventListener('click', function () {
            var context = snapshot.getContext('2d');
            // Draw the video frame to the canvas.
            context.drawImage(player, 0, 0, snapshotCanvas.width,
                    snapshotCanvas.height);
            var base64 = snapshotCanvas.toDataURL();
            //console.log(base64); //讓瀏覽器以F12呈現錯誤訊息
            document.getElementById("image").value = base64;
        });

        signInBtn.addEventListener('click', function () {
            var context = snapshot.getContext('2d');
            // Draw the video frame to the canvas.
            context.drawImage(player, 0, 0, snapshotCanvas.width,
                    snapshotCanvas.height);
            var base64 = snapshotCanvas.toDataURL();
            //console.log(base64); //讓瀏覽器以F12呈現錯誤訊息
            document.getElementById("image").value = base64;
            document.getElementById("signInForm").submit();
        });

        navigator.mediaDevices.getUserMedia({video: true})
                .then(handleSuccess);

        // 取得現在小時
        var h = new Date().getHours();
        // 取得現在分鐘
        var m = new Date().getMinutes();
        //alert(h);
        /*
         statusId1  (早班)上班,     statusId2 (早班)中午休息, 
         statusId3  (早班)中午上班,  statusId4 (早班)下班 
         statusId5  (晚班)上班,     statusId6 (晚班)下午休息, 
         statusId7  (晚班)小夜上班,  statusId8 (晚班)下班 
         statusId9  (早晚班)上班,    statusId10 (早晚班)中午休息, 
         statusId11 (早晚班)小夜上班, statusId12 (早晚班)下班 
         */

        //設定時間轉跳
        var timeout = ${timeout};
        if (timeout != -1) {
            setTimeout(function () {
                window.location.href = "./input2";
            }, timeout);
        }

    </script>
</body>
</html>