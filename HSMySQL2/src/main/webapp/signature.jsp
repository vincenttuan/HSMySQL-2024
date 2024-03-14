<%@page import="java.util.Date"%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="zh-TW">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <title>簽名手寫輸入</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            .body,
            .wrapper {
                /* Break the flow */
                position: absolute;
                top: 0px;

                /* Give them all the available space */
                width: 100%;
                height: 400px;

                /* Remove the margins if any */
                margin: 0;

                /* Allow them to scroll down the document */
                overflow-y: hidden;
            }

            .body {
                /* Sending body at the bottom of the stack */
                z-index: 1;
                overflow:hidden;
            }

            .wrapper {
                /* Making the wrapper stack above the body */
                z-index: 2;
            }
            #canvas{  }
            #canvasDiv{
                background-color: #eee;
                background-image: url("/HSMySQL/images/signature.png");
                background-size:     cover;                     
                background-repeat:   no-repeat;
                background-position: center center;
            }
        </style>
    </head>
    <body class="body" id="bb" oncontextmenu="return false" >
        <div class="wrapper">
            <center>
                <button id="btn_clear" style="font-size:50px">清除重寫</button>
                <button id="btn_submit" style="font-size:50px">簽名完畢</button>
            </center>
            
            <div id="canvasDiv" style="border-color:#ff0000;border-width:3px;border-style:solid;"></div><br>
            <script language="javascript" src="/HSMySQL/js/signature.js?time=<%=new Date().getTime()%>"></script>
            <img id="qmimg"/>
            <div id="text"></div>
        </div>
    </body>
</html>