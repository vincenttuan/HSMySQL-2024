<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HS 員工簽到表 I</title>
        <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.1/build/pure-min.css">

    </head>
    <body style="padding: 10px">
        <a href="${pageContext.servletContext.contextPath}/login.jsp?flag=true">
            <img src="${pageContext.servletContext.contextPath}/images/user.png" width="40" valign="middle" border="0">HS 後台登入</h1>
        </a>
    <center>
        <h1>HS 員工簽到表 I</h1>
        <h2> <font size="3" color="#EA0000">【自3/10起，開始以第二版打卡系統進行打卡】</h2>
        <table border="0">
            <tr>
                <td valign="top" style="padding: 30px">
                    <video id="player" width="320" height="240" controls autoplay></video>
                    <!--建立公佈欄-->
                    <p />
                    <form class="pure-form">
                        <fieldset>
                            <legend>公告欄(試用版)：</legend>
                            1.後臺功能已啟用(測試版)
                            <br>&nbsp;&nbsp;&nbsp;公告日期:2020/06/11
                            <ul>
                                <font size="2">
                                <li><font color="#CC0000">後台功能已啟用</font>
                                    <br>預設密碼與員編相同，6/13前需登入變更密碼</li><br>
                                <li><font color="#CC0000">每日打卡查詢已啟用</font>
                                    <br>須每日登入確認打卡狀態</li><br>
                                <li><font color="#CC0000">每日回報功能已啟用</font>
                                    <br>須每日登入填寫【當日交接】，【上班回報事項】
                                    <br>依薪資結構計入薪資核算</li><br>
                                </font>
                            </ul>

                            2.打卡標準時間更新如下：
                            <br>&nbsp;&nbsp;&nbsp;公告日期:2020/06/02
                            <ul>
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
                            </ul>

                            <!--                                <li>診所早班<br>上班 0800 中午休息 1300 <br>中午上班 1500 下班 1800</li><br>
                                                            <li>診所晚班<br>上班 1300 下午休息 1700 <br>小夜上班 1800 下班 2200</li><br>
                                                            <li>診所早晚班<br>上班 0900 中午休息 1300 <br>小夜上班 1700 下班 2100</li><br>
                                                            <li>公司早午班<br>上班 0900 中午休息 1300 <br>中午上班 1400 下班 1800</li><br>
                                                            <li>公司值日班<br>上班 0900 中午休息 1200 <br>中午上班 1300 下班 1800</li><br>
                                                            <li>診所周六早班<br>上班 0800 中午休息 1300 <br>中午上班 1500 下班 1800</li><br>
                                                            <li>診所周六晚班<br>上班 1200 中午休息 1700 <br>中午上班 1700 下班 2000</li><br>
                                                            <li>診所周六早晚班<br>上班 0900 中午休息 1300 <br>中午上班 1500 下班 1900</li><br>-->

                            <!--                            2.打卡即將變更時間如下：
                                                        <br>&nbsp;&nbsp;&nbsp;公告日期:2020/06/02
                                                        <ul>
                                                            <li>診所早晚班<br>上班 0900 中午休息 1300 <br>小夜上班 1730 下班 2130</li><br>
                                                        </ul>-->

                            <legend>公告欄結尾</legend>
                            <button id="clockOn2Btn" type="button" class="pure-button pure-button-primary" onclick="window.location.href = '${pageContext.servletContext.contextPath}/mvc/clock_on/input2'">簽到系統第二版(測試)</button>
                        </fieldset>
                    </form>
                </td>
                <td valign="top" style="padding: 30px">
                    <form:form id="signInForm" class="pure-form" modelAttribute="clockOn" method="post" action="${pageContext.servletContext.contextPath}/mvc/clock_on/in">
                        <fieldset>
                            <legend>簽到時段</legend>

                            <c:forEach var="s" items="${list_status}" varStatus="status">
                                <input id="statusId${status.count}" name="statusId" type="radio" value="${s.statusId}"/>&nbsp;&nbsp;
                                <span style="color:${s.color}">${s.statusName}</span>
                                <c:if test="${s.endoflist eq 9}"><br><br></c:if> 
                            </c:forEach>

                                <!--第二版已經啟用，先將打卡按鈕隱藏-->
<!--                            <legend>員工編號</legend>
                            關閉自動完成  autocomplete="off"
                            <form:input path="empNo" placeholder="請輸入員工編號" autocomplete="off"/> <span style="font-weight:bold;font-family:Arial;font-size:20px;color:red">${message}</span> <p />
                            <form:hidden path="image" />
                            <button id="signInBtn" type="button" class="pure-button pure-button-primary">簽到</button>
                            <button id="resetBtn" type="button" class="pure-button pure-button-primary" onclick="window.location.href = '${pageContext.servletContext.contextPath}/mvc/clock_on/input'">清空畫面</button>
                            <br>打卡後請記得<font color='RED'>【清空畫面】！-->
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
            console.log(base64);
            document.getElementById("image").value = base64;
        });

        signInBtn.addEventListener('click', function () {
            var context = snapshot.getContext('2d');
            // Draw the video frame to the canvas.
            context.drawImage(player, 0, 0, snapshotCanvas.width,
                    snapshotCanvas.height);
            var base64 = snapshotCanvas.toDataURL();
            console.log(base64);
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
        switch (h) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                document.getElementById('statusId1').checked = true; // (早班)上班
                break;
            case 8:
                if (m < 30) {
                    document.getElementById('statusId1').checked = true; // (早班)上班
                    break;
                }
            case 9:
            case 10:
                document.getElementById('statusId9').checked = true; // (早晚班)上班
                break;
            case 11:
                if (m < 30) {
                    document.getElementById('statusId9').checked = true; // (早晚班)上班
                }
                break;
            case 12:
                if (m < 30)
                    document.getElementById('statusId2').checked = true; // (早班)中午休息
                else {
                    document.getElementById('statusId5').checked = true; // (晚班)上班
                }
                break;
            case 13:
                if (m < 30) {
                    document.getElementById('statusId10').checked = true; // (早晚班)中午休息
                    break;
                }
            case 14:
            case 15:
                document.getElementById('statusId3').checked = true; // (早班)中午上班
                break;
            case 16:
                if (m < 30) {
                    document.getElementById('statusId3').checked = true; // (早班)中午上班
                    break;
                }
                document.getElementById('statusId11').checked = true; // (早晚班)小夜上班
                break;
            case 17:
                if (m < 30) {
                    document.getElementById('statusId11').checked = true; // (早晚班)小夜上班
                    break;
                }
            case 18:
            case 19:
            case 20:
                document.getElementById('statusId7').checked = true; // (晚班)小夜上班
                break;
            case 21:
                if (m >= 30) {
                    document.getElementById('statusId12').checked = true; // (早晚班)下班
                    break;
                }
            case 22:
                document.getElementById('statusId8').checked = true; // (晚班)下班
                break;
            case 23:
            default:

        }

        //設定時間轉跳
        var timeout = ${timeout};
        if (timeout != -1) {
            setTimeout(function () {
                window.location.href = "./input";
            }, timeout);
        }

    </script>
</body>
</html>