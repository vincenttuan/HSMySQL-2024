<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <title>JSP Page</title>
        <script>
            function openSignature() {
                window.open('/HSMySQL/signature.jsp');
            }
            function callBackIntegrationCompleted(code) {
                $("#signature_result").val(code);
                $("#signature_img").attr("src", code);
            }
            window.callBackIntegrationCompleted = callBackIntegrationCompleted;
            
        </script>
    </head>
    <body>
        <input type="button" value="Open Signature" onclick="openSignature()"><p />
        <input type="text" id="signature_result" name="signature_result"><p />
        <img id="signature_img"/><p />
    </body>
</html>
