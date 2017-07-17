<%-- 
    Document   : login.jsp
    Created on : Jun 13, 2017, 9:39:43 AM
    Author     : prathibha_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.png">
        <title>Mobile Revenue Survey Login</title>
        <%@include file="/stylelinks.jspf" %>
        <style>

            body,html{
                margin: 0;
                background-color: #f3f3f3; 
            }
            #m1:hover ~ #m1 {
                box-shadow: 1px 1px #0000ff;
            }
            #box{
                width: 100%;
                height: 47px;
                border-top-left-radius: 17px;
                border-top-right-radius: 17px;
                background-color: #7979d3;
                padding: 15px;
                padding-left: 20px;
            }
            #box2{
                width: 100%;
                height: 250px;
                border-bottom-left-radius: 17px;
                border-bottom-right-radius: 17px;

                background: rgb(240, 238, 227); 
                background: -webkit-radial-gradient(top left,rgb(240, 238, 227),rgb(224, 221, 207)); 
                background: -o-radial-gradient(top left,rgb(240, 238, 227),rgb(224, 221, 207)); 
                background: -moz-radial-gradient(top left,rgb(240, 238, 227),rgb(224, 221, 207)); 
                background: radial-gradient(top left, rgb(240, 238, 227), rgb(224, 221, 207)); 

                padding: 30px;

            }
            #circle{
                float: left;
                width: 20px;
                height: 20px;
                border-radius: 10px;
                background-color: #ff3366;
                margin-right: 10px;


            }
            #circle2{
                float: left;
                width: 20px;
                height: 20px;
                border-radius: 10px;
                background-color: orange;


            }
            #boxin{
                width: 100%;
                height: 188px;
                background-color: #bebdb3;
                margin-bottom: 14px;
                padding: 10px;
            }
            #boxin2{
                float: left;
                width: 62%;
                height: 200px;
                background-color: #bebdb3;
                margin-right: 14px;

            }
            #boxin3{
                float: left;
                width: 35%;
                height: 200px;
                background-color: #bebdb3;
            }
            #topmargin{
                margin-top: 13%;
            }
            img {
                -webkit-filter: drop-shadow(5px 5px 5px #222);
                filter: drop-shadow(5px 5px 5px #222);
            }
            #img1{
                position: absolute;
                margin-top: -8.1%;
                margin-left: 14%;
                z-index: -2;
                opacity: 0.5;
            }
            #img2{
                position: absolute;
                margin-top: -9%;
                margin-left: 46%;
                z-index: -2;
            }
        </style>
    </head>
    <body>
        <div class="container">

            <s:if test="hasActionMessages()">
                <div class="col-md-offset-2 col-md-6 alert alert-dismissible alert-success" style="margin-top: 75px; height: 50px;position: absolute">

                    <p><s:actionmessage/></p>
                </div>
            </s:if>

            <s:if test="hasActionErrors()">

                <div class="col-md-offset-2 col-md-6 alert alert-dismissible alert-warning" style="margin-top: 25px; height: 50px;position: absolute">

                    <p><s:actionerror/></p>
                </div>


            </s:if>


        </div>


        <div class="container" >
            <div class="container">
                <form action="CheckSearch" method="post" >
                    <div class="col-md12" id="topmargin"></div>
                    <div class="col-md-3" ></div>
                    <div class="col-md-6">

                        <div id="box">
                            <div id="circle"></div>
                            <div id="circle2"></div>
                            <div class="text pull-right" style="font-size: 16px; color: white; font-weight: bold; font-family: 'allerB2';">Mobile Revenue Survey</div>
                        </div>
                        <div id="box2">
                            <div id="boxin">
                                <div class="col-lg-offset-2 col-lg-8 text-center">
                                    <label class="form-group text-primary pull-right label label-primary" style="width: 87px ;font-size: 14px; color: white; font-weight: bold; font-family: 'allerB2';margin-bottom: 1px;"><b>User Name</b></label>
                                    <input type="text" class="form-control" placeholder="Username" name="loginUserName">
                                    <label class="form-group text-primary pull-right label label-primary" style="width: 87px ;font-size: 14px; color: white; font-weight: bold; font-family: 'allerB2';margin-bottom: 1px;"><b>Password</b></label>
                                    <input type="password" class="form-control" placeholder="Password" name="loginPassword">
                                    <!--<div class="col-lg-3 "></div>-->
                                    <input type="submit" value="LOGIN" class="btn btn-primary text-center" style="width: 159px;margin-top: 11px;" >
                                </div>
                            </div>
                            <!--<div id="boxin2"></div>-->
                            <!--<div id="boxin3"></div>-->
                        </div>
                    </div>
                    <div class="col-md-3"></div>
                </form>
            </div>
        </div>
    </div>


</body>
</html>
