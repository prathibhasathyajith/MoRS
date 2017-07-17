<%-- 
    Document   : home.jsp
    Created on : Jun 13, 2017, 9:39:54 AM
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

        <title>Mobile Revenue Survey</title>

        <%@include file="/stylelinks.jspf" %>


        <script type="text/javascript">

            function logout() {
                window.open("${pageContext.request.contextPath}/login.jsp");
            }


            function resetAllData() {

            }

            function resetFieldData() {

            }


        </script>
    </head>
    <body style="background-color: gainsboro" >
        
        <div class="container">
            <s:if test="hasActionMessages()">
                <div class="col-md-offset-2 col-md-6 alert alert-dismissible alert-success" style="margin-top: 75px; height: 50px;position: absolute">
                   
                    <p><s:actionmessage/></p>
                </div>
            </s:if>
            <s:if test="hasActionErrors()">

                <div class="col-md-offset-2 col-md-6 alert alert-dismissible alert-warning" style="margin-top: 75px; height: 50px;position: absolute">
                   
                    <p><s:actionerror/></p>
                </div>


            </s:if>
        </div>

        <div class="container-fluid" style="overflow-x:hidden ;background-color: gainsboro ">
            <div class="text-center container-fluid" style="background-color: #34495e;margin-left: -15px;width: 103%;border-bottom: 5px solid #17a086;margin-bottom: 10px;">
                <!--<h1 class="text" style="margin: 0;color: #93a5a5;font-weight: bolder">Welcome</h1>-->
                <h2 class="text" style="margin-top: 10px;color: #93a5a5;font-weight: bolder; float: left"><img width="38" style="border: 3px solid white; border-radius: 12px" src="resources/images/favicon.png" />&ensp;Mobile Revenue Survey</h2>
                <div class="form-group pull-right" style="margin-top: 13px">
                    <%--<s:form action="LogoutSearch" method="post" id="ff">--%>
                        <!--<input type="submit" value="Logout" class="btn btn-default btn-sm"/>-->
                        <!--<input type="button" value="Logout" class="btn btn-default btn-sm"/>-->
                    <%--</s:form>--%>

                    <!--<a href="LogoutSearch.action">Logout</a>-->
                </div>
            </div>

            <div class="container-fluid form-group" >
                <s:form action="ChangePasswordSearch" method="post" id="ss" theme="simple">
                    <div class="col-md-12 text-center" style="margin-top: 65px;"><p class="lead">Change Password</p></div>
                    <div class="row col-md-12">
                        <div class="col-sm-4 col-sm-offset-4">
                            <div class="form-group">
                                <label class="text control-label" style="font-size: 12px;">User Name</label>
                                <s:textfield id="username" name="username" cssClass="form-control" cssStyle="border: 1px solid #17a086" readonly="true" /> 
                            </div>
                        </div>
                    </div>
                    <div class="row col-md-12">
                        <div class="col-sm-4 col-sm-offset-4">
                            <div class="form-group">
                                <label class="text control-label" style="font-size: 12px;">Old Password</label>
                                <s:password  id="oldPassword" name="oldPassword" cssClass="form-control" cssStyle="border: 1px solid #17a086" /> 
                            </div>
                        </div>
                    </div>
                    <div class="row col-md-12">
                        <div class="col-sm-4 col-sm-offset-4">
                            <div class="form-group">
                                <label class="text control-label" style="font-size: 12px;">New Password</label>
                                <s:password id="newPassword" name="newPassword" cssClass="form-control" cssStyle="border: 1px solid #17a086" /> 
                            </div>
                        </div>
                    </div>
                    <div class="row col-md-12">
                        <div class="col-sm-4 col-sm-offset-4">
                            <div class="form-group">
                                <label class="text control-label" style="font-size: 12px;">Confirm Password</label>
                                <s:password id="confirmPassword" name="confirmPassword" cssClass="form-control" cssStyle="border: 1px solid #17a086" /> 
                            </div>
                        </div>
                    </div>

                    <div class="container-fluid">
                        <div class="row form-inline text-center">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <input type="submit"value="Chnage Password" class="btn btn-info"/>
                                </div> 
                                <div class="form-group">
                                    <!--<input type="" value="Cancel" class="btn btn-default" />-->
                                    <a href="SendWelcomeSearch" class="btn btn-default" >cancel</a> 
                                </div>
                            </div>
                        </div>
                    </div>

                </s:form>
            </div>

        </div>

        <div style="height: 10px"></div>

    </body>
</html>
