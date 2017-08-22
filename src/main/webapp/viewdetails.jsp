<%-- 
    Document   : viewdetails
    Created on : Jul 13, 2017, 1:55:39 PM
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
        <title>JSP Page</title>

        <script>
            $(document).ready(function () {

                var img = "<s:property value="actionBean.imgBase"/>"
                if (!img) {
                    $('#imageID').attr('src', "resources/images/noimage.jpg");
                } else {

                }

            });

            function PrintElem(elem)
            {
                var mywindow = window.open('', 'PRINT', 'height=600,width=800');

                mywindow.document.write('<html><head><title>' + document.title + '</title>');
                mywindow.document.write('</head><body >');
                mywindow.document.write('<h1>' + document.title + '</h1>');
                mywindow.document.write(document.getElementById(elem).innerHTML);
                mywindow.document.write('</body></html>');

                mywindow.document.close(); // necessary for IE >= 10
                mywindow.focus(); // necessary for IE >= 10*/

                mywindow.print();
                mywindow.close();

                return true;
            }

            //img rotate
            var angle = 0;
            var img = document.getElementById('container');
            document.getElementById('button').onclick = function () {
                angle = (angle + 90) % 360;
                img.className = "rotate" + angle;
            }


        </script>
        <style>
            #container {
                border: 5px solid #e9e9e9;
                overflow: auto;
                background-color: #e9e9e9;
            }
            #container.rotate90,
            #container.rotate270 {
                width: 100px;
                height: 820px
            }
            #imageID {
                transform-origin: top left;
                /* IE 10+, Firefox, etc. */
                -webkit-transform-origin: top left;
                /* Chrome */
                -ms-transform-origin: top left;
                /* IE 9 */
            }
            #container.rotate90 #imageID {
                transform: rotate(90deg) translateY(-100%);
                -webkit-transform: rotate(90deg) translateY(-100%);
                -ms-transform: rotate(90deg) translateY(-100%);
            }
            #container.rotate180 #imageID {
                transform: rotate(180deg) translate(-100%, -100%);
                -webkit-transform: rotate(180deg) translate(-100%, -100%);
                -ms-transform: rotate(180deg) translateX(-100%, -100%);
            }
            #container.rotate270 #imageID {
                transform: rotate(270deg) translateX(-100%);
                -webkit-transform: rotate(270deg) translateX(-100%);
                -ms-transform: rotate(270deg) translateX(-100%);
            }
        </style>
    </head>
    <body>
        <div class="container" style="width: 950px;margin-top: 10px">
            <div id="aa">

                <div class="text-left" style="float: left">
                    <div class="row form-group">
                        <div class="col-md-5">
                            <label class="text label label-default" style="font-size: 12px;">Survey ID</label>
                        </div>
                        <div class="col-md-7"> 
                            <label class="text label label-danger" style="font-size: 12px;"><s:property value="actionBean.sid"/></label>


                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="col-md-5">
                            <label class="text label label-default" style="font-size: 12px;">Revenue Source</label>
                        </div>
                        <div class="col-md-7"> 
                            <label class="text label label-success" style="font-size: 12px;"><s:property value="actionBean.revenus"/></label>
                        </div>

                    </div>
                    <div class="row form-group">
                        <div class="col-md-5">
                            <label class="text label label-default" style="font-size: 12px;">Latitude and Longitude</label>
                        </div>
                        <div class="col-md-7">    
                            <label class="text label label-success" style="font-size: 12px;">(<s:property value="actionBean.lat"/>,<s:property value="actionBean.lng"/>)</label>
                        </div>

                    </div>



                    <table class="table table-hover">
                        <thead>
                            <tr>

                                <th>Question</th>
                                <th style="padding-left: 20px">Answer</th>
                            </tr>
                        </thead>
                        <tbody>
                            <s:iterator status="stat" value="questionAnswer">
                                <tr>
                                    <td style="padding: 1px;"><s:property value="ques"/> </td>
                                    <td style="padding: 1px;padding-left: 20px"><s:property value="answ"/></td>
                                </tr>

                            </s:iterator>
                        </tbody>
                    </table>
                </div>
                <div id="img" style="float: right">
                    <div id="container" style="border: 5px solid #e9e9e9;width: 410px;height: auto;">
                        <img o id="imageID" style="border: 0px solid #e9e9e9" width="400" height="auto" src="data:image/jpeg;base64,<s:property value="actionBean.imgBase"/>" alt="User Image" >

                    </div>
                        <button style="margin-left: 180px;margin-top: 10px;display: none" id="button" class="btn btn-xs btn-success" ><i class="fa fa-repeat" aria-hidden="true"></i> Rotate</button>

                </div> 
                <!--                <form>
                                    <input type="button" value="Print this page" onClick="PrintElem('aa')">
                                </form>-->
            </div>




        </div>
        <script>
//           function imgTOcenter() {
//               var imgHeight = $('#imageID').height();
//               var centerVal = (400 - imgHeight) / 2;
//               alert("imgHeight - " + imgHeight + " centerVal - " + centerVal);
//
//               $('#imageID').css('margin-top', centerVal + 'px');
//
//           }
//           imgTOcenter();
        </script>

    </body>


</html>
