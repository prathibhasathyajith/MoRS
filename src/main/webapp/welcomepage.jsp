<%-- 
    Document   : welcomepage
    Created on : Jun 6, 2014, 1:45:39 PM
    Author     : thushanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.png">
        <title>Mobile Revenue Survey</title>
        <%@include file="/stylelinks.jspf" %>


    </head>
    <script type="text/javascript">

        function myFunction() {
//            window.open("${pageContext.request.contextPath}/login.jsp", "MsgWindow", "width=1350, height=660,scrollbars=1,resizable=yes");
            window.open("${pageContext.request.contextPath}/login.jsp");
        }

    </script>

    <style>

        @font-face {
            font-family: "allerB1";
            src: url("resources/font/AllerDisplay.woff") format('woff');
        }
        @font-face {
            font-family: "allerB2";
            src: url("resources/font/Aller_Bd.woff") format('woff');
        }
        #box{
            margin-left: 50%;
            margin-top: 18%;
            position: absolute;
            padding: 14px;
            width: 220px;
            border-radius: 29px;
            height: 142px;
            background-color: white;
            transition: 0.5s;

        }
        .text {
            font-family: 'allerB2';
            font-size: 47px;
            margin-bottom: -29px;
            margin-top: -16px;
        }

        #boxx:hover{
            cursor: pointer;

        }

        #boxx:hover ~ #box{
            box-shadow: 0px 0px 20px gray;

        }





    </style>
    <body>

        <div class="container">
            <div class="container">
                <div class="col-md-12" style="margin-top: 9%"></div>
                <div class="col-md-3"></div>

                <div class="col-md-6" id="boxx">
                    <img src="resources/images/mapmaker.png" width="512" height="auto" onclick="myFunction()"/>
                </div>
                <div class="col-md-3"></div>
                <div id="box">
                    <div id="text1" class="text">Mobile</div>
                    <div id="text2" class="text">Revenue</div>
                    <div id="text3" class="text">Survey</div>
                </div>
            </div>
        </div>

    </body>

</html>
