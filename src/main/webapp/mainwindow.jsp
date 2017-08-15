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
        <title>Mobile Revenue Survey</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/images/favicon.png">
        <%@include file="/stylelinks.jspf" %>

        <style>
            .ui-jqgrid .ui-jqgrid-title {
                font-size: 13px;
                padding-left: 10px;
            }
            .dialogclass {
                height: 100%;
                width: 100%;
                top: 0px;
                left: 0px;
                display: block;
                margin-left: 0px;
                z-index: 10000;
                margin-top: 0;
                position: fixed;
            }
        </style>


        <script type="text/javascript">

            $(document).ready(function () {

                $(document).ajaxStart(function () {
                    $.blockUI({css: {
                            border: 'transparent',
                            backgroundColor: 'transparent',
                            zIndex: '100000000'
                        },
                        message: '<img height="136" width="136" src="${pageContext.request.contextPath}/resources/images/Ellipsis.svg" />',
                        baseZ: 20000
                    });

                });

                $(document).ajaxStop(function () {
                    $.unblockUI();
                });

            });

            function logout() {
                window.open("${pageContext.request.contextPath}/login.jsp");
            }

            function onclicksearch() {
//                $('#message').empty();

//                $('#questionSearch').val("");
//                $('#revenueSourceSearch').val("");
                var question = $('#questionSearch').val();
                var revenueSource = $('#revenueSourceSearch').val();
                var user = $('#userSearch').val();
                var localAuthority = $('#localAuthoritySearch').val();
                var gnd = $('#gndSearch').val();
                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        question: question,
                        revenueSource: revenueSource,
                        user: user,
                        localAuthority: localAuthority,
                        gnd: gnd,
                        search: true
                    }
                });
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function viewformatter(cellvalue) {
//                return "<a href='viewDetailTransactionExplorer.action?transactionID=" + cellvalue + "' title='View Transaction' ><img class='ui-icon ui-icon-newwin' style='display: block;margin-left: auto;margin-right: auto;'/></a>";
                return "<a href='#' title='Action' onClick='javascript:viewActionInit(&#34;" + cellvalue + "&#34;)'><img width='auto' height='13' src='${pageContext.request.contextPath}/resources/images/view.svg'/></a>";
            }
            function viewActionInit(keyval) {
                $("#viewdialog").data('sid', keyval).dialog('open');
            }
            $.subscribe('openviewtasktopage', function (event, data) {
                var $led = $("#viewdialog");
                $led.html("Loading..");
                $led.load("ViewPopupSearch.action?sid=" + $led.data('sid'));
            });


            $.subscribe('anyerrors', function (event, data) {
                window.location = "${pageContext.request.contextPath}/LogoutSearch.action?";
            });

            function loaddata() {
                $.ajax({
                    url: '${pageContext.request.contextPath}/ResetSearch.action',
//                    data: {revenueSource: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        var qlist = data.revenueSourceList;
                        $("#revenueSourceSearch option").remove();
                        $('#revenueSourceSearch').append('<option value="">--Select Revenue Source--</option>');
                        $.each(qlist, function (index, item) {
                            $('#revenueSourceSearch').append("<option value='" + item.code + "'>" + item.description + "</option>");
                        });

                        var qlist2 = data.localAuthorityList;
                        $("#localAuthoritySearch option").remove();
                        $('#localAuthoritySearch').append('<option value="">--Select Local Authority--</option>');
                        $.each(qlist2, function (index, item) {
                            $('#localAuthoritySearch').append("<option value='" + item.code + "'>" + item.description + "</option>");
                        });

                        var qlist3 = data.questionList;
                        $("#questionSearch option").remove();
                        $('#questionSearch').append('<option value="">--Select Question--</option>');
                        $.each(qlist3, function (index, item) {
                            $('#questionSearch').append("<option value='" + item.QCode + "'>" + item.question + "</option>");
                        });
                        var qlist4 = data.gndList;
                        $("#gndSearch option").remove();
                        $('#gndSearch').append('<option value="">--Select GND--</option>');
                        $.each(qlist4, function (index, item) {
                            $('#gndSearch').append("<option value='" + item.code + "'>" + item.description + "</option>");
                        });
                        var qlist5 = data.userList;
                        $("#userSearch option").remove();
                        $('#userSearch').append('<option value="">--Select User--</option>');
                        $.each(qlist5, function (index, item) {
                            $('#userSearch').append("<option value='" + item.keycode + "'>" + item.username + "</option>");
                        });




                    },
                    error: function (data) {
                        //                        $("#errordialog").html("Error occurred while processing.").dialog('open');
//                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function resetAllData() {
                loaddata();
                $('#questionSearch').val('');
//                var array = $('#revenueSourceSearch').val('');
//
//                alert(($.inArray("--Select Revenue Source--", $.map(array, function(v) { return v[0]; }))));
//                $('#revenueSourceSearch').prepend('<option value="">--Select Revenue Source--</option>');
//                $('#revenueSourceSearch').val('');
                $('#userSearch').val('');
                $('#gndSearch').val('');
//                $('#localAuthoritySearch').prepend('<option value="">--Select Local Authority--</option>');
//                $('#localAuthoritySearch').val('');
//                onclicksearch();


                $("#gridtable").jqGrid('setGridParam', {
                    postData: {
                        question: '',
                        revenueSource: '',
                        user: '',
                        localAuthority: '',
                        gnd: '',
                        search: false
                    }
                });
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function resetFieldData() {

                $('#questionSearch').val();
                $('#revenueSourceSearch').val();
                $('#userSearch').val();
                $('#localAuthoritySearch').val();
                $('#gndSearch').val();
                $("#gridtable").jqGrid('setGridParam', {postData: {search: false}});
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function dialog() {
                $("#dialog").dialog({
                    height: 650,
                    width: 900,
                    modal: true,
                    close: function () {
                        $('#dialog').dialog('destroy');
                    }
                });
            }



            function changeQuestion(keyval) {


                if (keyval == '') {
                    keyval = null;
                }

                $.ajax({
                    url: '${pageContext.request.contextPath}/FindQuestionSearch.action',
                    data: {revenueSource: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        var qlist = data.questionList;
                        $("#questionSearch option").remove();
                        $('#questionSearch').append('<option value="">--Select Question--</option>');
                        $.each(qlist, function (index, item) {
                            $('#questionSearch').append("<option value='" + item.QCode + "'>" + item.question + "</option>");
                        });
                    },
                    error: function (data) {
                        //                        $("#errordialog").html("Error occurred while processing.").dialog('open');
//                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function changeGND(keyval) {


                if (keyval == '') {
                    keyval = null;
                }

                $.ajax({
                    url: '${pageContext.request.contextPath}/FindGNDSearch.action',
                    data: {localAuthority: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        var glist = data.gndList;
                        $("#gndSearch option").remove();
                        $('#gndSearch').append('<option value="">--Select GND--</option>');
                        $.each(glist, function (index, item) {
                            $('#gndSearch').append("<option value='" + item.code + "'>" + item.description + "</option>");
                        });
                    },
                    error: function (data) {
                        //                        $("#errordialog").html("Error occurred while processing.").dialog('open');
//                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function changerevenueSource(keyval) {


                if (keyval == '' || keyval == "--Select Revenue Source--") {
                    keyval = "empty";
                }
//                alert(keyval);
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindRevenueSourceSearch.action',
                    data: {question: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        if (keyval == "empty") {
                            var qlist = data.questionList;
                            $("#questionSearch option").remove();
                            $('#questionSearch').append('<option value="">--Select Question--</option>');
                            $.each(qlist, function (index, item) {
                                $('#questionSearch').append("<option value='" + item.QCode + "'>" + item.question + "</option>");
                            });
                        } else {
                            var rlist = data.revenueSourceList;
                            $("#revenueSourceSearch option").remove();
//                        $('#revenueSourceSearch').append('<option value="">--Select Question--</option>');
                            $.each(rlist, function (index, item) {
                                $('#revenueSourceSearch').append("<option value='" + item.code + "'>" + item.description + "</option>");
                            });
                        }

                    },
                    error: function (data) {
                        //                        $("#errordialog").html("Error occurred while processing.").dialog('open');
//                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function changeLA(keyval) {


                if (keyval == '' || keyval == "--Select GND--") {
                    keyval = "empty";
                }
//                alert(keyval);
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindLASearch.action',
                    data: {gnd: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        if (keyval == "empty") {
                            var glist = data.gndList;
                            $("#gndSearch option").remove();
                            $('#gndSearch').append('<option value="">--Select GND--</option>');
                            $.each(glist, function (index, item) {
                                $('#gndSearch').append("<option value='" + item.code + "'>" + item.description + "</option>");
                            });
                        } else {
                            var rlist = data.localAuthorityList;
                            $("#localAuthoritySearch option").remove();
//                        $('#revenueSourceSearch').append('<option value="">--Select Question--</option>');
                            $.each(rlist, function (index, item) {
                                $('#localAuthoritySearch').append("<option value='" + item.code + "'>" + item.description + "</option>");
                            });
                        }

                    },
                    error: function (data) {
                        //                        $("#errordialog").html("Error occurred while processing.").dialog('open');
//                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

//            function summary() {
//                $.ajax({
//                    url: '${pageContext.request.contextPath}/SummarySearch.action',
//                    dataType: "json",
//                    type: "POST",
//                    success: function (data) {
//                        dialog = $("#dialogSummary").dialog({
//                            height: 600,
//                            width: 600,
//                            modal: true,
//                            open: function (event, ui) {
//                                $(".ui-dialog-titlebar-close", ui.dialog | ui).text("X");
//                                $(".ui-dialog-titlebar").hide();
//                            },
//                            close: function () {
//                                dialog.dialog('destroy');
//                            }
//                        });
//                        var list = data.latLngList;
//                        $.each(list, function (index, item) {
//
//                            palceslatlng[index] = [item.placenamela, Number(item.lat), Number(item.lng), Number(item.zindex)];
//                        });
//                        initMap();
//                        console.log(palceslatlng);
//                    },
//                    error: function (data) {
//                        //                        $("#errordialog").html("Error occurred while processing.").dialog('open');
////                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
//                    }
//                });
//
//            }


            var palceslatlng = [];
            function loadLatLng() {

                palceslatlng = [];
                $.ajax({
                    url: '${pageContext.request.contextPath}/LoadLatLngSearch.action',
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        dialog = $("#dialog").dialog({
                            height: 630,
                            width: 800,
                            modal: true,
                            position: {my: "center center", at: "center center"},
                            open: function (event, ui) {
//                                $(".ui-dialog-titlebar-close", ui.dialog | ui).text("X");
//                                $(".ui-dialog-titlebar").hide();

                            },
                            close: function () {
                                dialog.dialog('destroy');
                            }
                        });
                        var list = data.latLngList;
                        $.each(list, function (index, item) {

                            palceslatlng[index] = [item.placenamela, Number(item.lat), Number(item.lng), Number(item.zindex)];
                        });
                        initMap();
                        console.log(palceslatlng);
                    },
                    error: function (data) {
                        //                        $("#errordialog").html("Error occurred while processing.").dialog('open');
//                        window.location = "${pageContext.request.contextPath}/LogoutUserLogin.action?";
                    }
                });
            }

            function downloadCSV() {
                $.ajax({
                    url: '${pageContext.request.contextPath}/DownloadCsvSearch.action',
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                    },
                    error: function (data) {

                    }
                });
            }


        </script>
        <style>
            #tab10{
                width: 95%;
                margin: 0;
                padding: 0;
            }
            #tab20{
                width: 95%;
                margin: 0;
                padding: 0;
            }
        </style>
    </head>
    <body style="background-color: gainsboro" >

        <div class="container-fluid" style="overflow-x:hidden ;background-color: gainsboro ">

            <div class="text-center container-fluid" style="background-color: #34495e;margin-left: -15px;width: 103%;border-bottom: 5px solid #17a086;margin-bottom: 10px;">
                <!--<h1 class="text" style="margin: 0;color: #93a5a5;font-weight: bolder">Welcome</h1>-->
                <h2 class="text" style="margin-top: 10px;color: #93a5a5;font-weight: bolder; float: left"><img width="38" style="border: 3px solid white; border-radius: 12px" src="resources/images/favicon.png" />&ensp;Mobile Revenue Survey</h2>
                <div class="pull-right form-inline" style="margin-top: 13px">

                    <s:form action="ViewCPSearch" method="post" id="f" theme="simple" cssStyle="float:left">
                        <input type="submit" value="Change Password" class="btn btn-default btn-sm" style="margin-right: 5px"/>
                    </s:form>
                    <s:form action="LogoutSearch" method="post" id="ff" theme="simple" cssStyle="float:left">
                        <input type="submit" value="Logout" class="btn btn-default btn-sm" />
                    </s:form>
                    <!--<a href="LogoutSearch.action">Logout</a>-->
                </div>
            </div>

            <div class="container-fluid">
                <s:form cssClass="form" action="ListSearch" method="post" theme="simple">




                    <div class="row col-md-12">
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label class="text label label-default" style="font-size: 12px;">Revenue Source</label>
                                <s:select  cssClass="form-control" cssStyle="border: 1px solid #17a086" onchange="changeQuestion(this.value)" name="revenueSource" id="revenueSourceSearch" list="%{revenueSourceList}"   headerKey=""  headerValue="--Select Revenue Source--" listKey="code" listValue="description" value="%{revenueSource}" disabled="false"/>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label  class="text label label-default" style="font-size: 12px;">Question</label>
                                <s:select  cssClass="form-control" cssStyle="border: 1px solid #17a086" onchange="changerevenueSource(this.value)" name="question" id="questionSearch" list="%{questionList}"   headerKey=""  headerValue="--Select Question--" listKey="QCode" listValue="question" value="%{question}" disabled="false"/>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label  class="text label label-default" style="font-size: 12px;">Local Authority</label>
                                <s:select  cssClass="form-control" cssStyle="border: 1px solid #17a086" onchange="changeGND(this.value)" name="localAuthority" id="localAuthoritySearch" list="%{localAuthorityList}"   headerKey=""  headerValue="--Select Local Authority--" listKey="code" listValue="description" value="%{localAuthority}" disabled="false"/>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label  class="text label label-default" style="font-size: 12px;">GND</label>
                                <s:select  cssClass="form-control" cssStyle="border: 1px solid #17a086" onchange="changeLA(this.value)" name="gnd" id="gndSearch" list="%{gndList}"   headerKey=""  headerValue="--Select GND--" listKey="code" listValue="description" value="%{gnd}" disabled="false"/>
                            </div>
                        </div>
                    </div>
                    <div class="row col-md-12">
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label  class="text label label-default" style="font-size: 12px;">Username</label>
                                <s:select  cssClass="form-control" cssStyle="border: 1px solid #17a086" name="user" id="userSearch" list="%{userList}"   headerKey=""  headerValue="--Select User--" listKey="keyid" listValue="username" value="%{user}" disabled="false"/>
                            </div>
                        </div>
                    </div>

                </s:form>
            </div>
            <div class="container-fluid">
                <div class="row form-inline text-center">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <!sj:submit 
                                button="true"
                                value="Search" 
                                href="#"
                                onclick="onclicksearch()"
                                id="searchbut"
                                cssClass="btn btn-primary "

                                />
                            <input type="button" onclick="onclicksearch()" value="Search" class="btn btn-info"/>
                            <!--<input type="button" onclick="loadLatLng()" value="latlng" class="btn btn-info" />-->
                        </div> 
                        <div class="form-group">
                            <!sj:submit 
                                button="true" 
                                value="Reset" 
                                name="reset" 
                                onClick="resetAllData()" 
                                cssClass="btn btn-warning"

                                />
                            <input type="button" onclick="resetAllData()" value="Reset" class="btn btn-default" />
                            <!--<input type="submit" onclick="downloadCSV()" value="Download CSV" class="btn btn-warning" />-->
                            <a type="button" href="DownloadCsvSearch" value="Download CSV" class="btn btn-primary" >Download CSV</a>
                        </div>

                        <s:url id="fileDownload" action="DownloadCsvSearch" ></s:url>


                            <div class="form-group">
                                <button type="button" class="btn btn-primary" onclick="loadLatLng();">View Map&ensp;<i class="fa fa-location-arrow " aria-hidden="true"></i></button>
                            </div>
                            <!--                        <div class="form-group">
                                                        <button type="button" class="btn btn-primary" onclick="summary();">Summary&ensp;<i class="fa fa-refresh " aria-hidden="true"></i></button>
                                                    </div>-->


                        </div>
                    </div>
                </div>
            <sj:dialog     
                draggable="false"
                id="viewdialog"                                 
                autoOpen="false" 
                modal="true" 
                position="center"
                title="Details"
                onOpenTopics="openviewtasktopage" 
                loadingText="Loading .."
                width="980"
                height="520"
                dialogClass= "dialogclass"

                /> 

            <div style="height: 10px"></div>

            <sj:tabbedpanel id="localtabs" cssClass="list form" selectedTab="0" disabledTabs="[]" >
                <sj:tab id="tab1" target="tone" label="Search Results"/>
                <sj:tab id="tab2" target="ttwo" label="Summary" disabled="true"/>

                <div id="tone">

                    <div class="" id="tab10">
                        <div class="text-center" style="margin-bottom: 5px;">
                            <s:url var="listurl" action="ListSearch"/>
                            <sjg:grid
                                id="gridtable"
                                caption="Search Results"
                                dataType="json"
                                href="%{listurl}"
                                pager="true"
                                gridModel="gridModel"
                                rowList="25,50"
                                rowNum="25"
                                autowidth="true"
                                rownumbers="true"
                                onCompleteTopics="completetopics"
                                rowTotal="false"
                                viewrecords="true"
                                shrinkToFit="false"
                                onErrorTopics="anyerrors"
                                >

                                <sjg:gridColumn name="sid" index="sd.ID" title="Survey ID"  sortable="true"/>
                                <sjg:gridColumn name="timeStamp" index="sd.CREATED_TIME" title="Time stamp"  sortable="true"/>
                                <sjg:gridColumn name="user" index="u.USERNAME" title="Enumerator"  sortable="true"/>
                                <sjg:gridColumn name="localAuthority" index="la.DESCRIPTION" title="Local Authority"  sortable="true"/>
                                <sjg:gridColumn name="revenueSource" index="rs.DESCRIPTION" title="Revenue Source"  sortable="true"/>
                                <sjg:gridColumn name="latitude" index="sd.LATITUDE" title="Latitude"  sortable="true"/>
                                <sjg:gridColumn name="longitude" index="sd.LONGITUDE" title="Longitude"  sortable="true"/>
                                <sjg:gridColumn name="gnd" index="g.DESCRIPTION" title="Gnd"  sortable="true"/>
                                <%--<sjg:gridColumn name="question" index="ua.Q_CODE" title="Question"  sortable="true"/>--%>
                                <%--<sjg:gridColumn name="answer" index="ua.ANSWER" title="Answer"  sortable="true"/>--%>
                                <sjg:gridColumn name="district" index="ds.DESCRIPTION" title="District"  sortable="true"/>
                                <sjg:gridColumn name="province" index="pv.DESCRIPTION" title="Province"  sortable="true"/>
                                <sjg:gridColumn name="sid" index="sd.ID" width="50" align="center" title="Action" formatter="viewformatter" sortable="false" frozen="false"/>

                            </sjg:grid>
                        </div>
                    </div>
                </div>

                <div id="ttwo">
                    <div class="" id="tab20">
                        <div class="text-center" style="margin-bottom: 5px;">
                            <s:url var="listurl" action="ListSummarySearch"/>
                            <sjg:grid
                                id="gridtable2"
                                caption="Summary"
                                dataType="json"
                                href="%{listurl}"              
                                gridModel="gridModel2"
                                rowList=""
                                rowNum="10"
                                autowidth="true"
                                onCompleteTopics="completetopics"
                                rowTotal="false"
                                viewrecords="true"
                                onErrorTopics="anyerrors"
                                shrinkToFit="false"
                                >

                                <sjg:gridColumn name="id" title="Sid" width="40" sortable="false"/>
                                <sjg:gridColumn name="lacode" title="Local Authority"  sortable="false"/>
                                <sjg:gridColumn name="coes" title="Commercial Establishments"  sortable="false"/>
                                <sjg:gridColumn name="ladb" title="Large Advertisement Boards"  sortable="false"/>
                                <sjg:gridColumn name="palo" title="Parking Lots"  sortable="false"/>
                                <sjg:gridColumn name="redf" title="Rental Daily Fare"  sortable="false"/>
                                <sjg:gridColumn name="reis" title="Rental Individual Shops"  sortable="false"/>
                                <sjg:gridColumn name="remc" title="Rental Market Complex (Owned by LA)"  sortable="false"/>
                                <sjg:gridColumn name="retm" title="Rental Temporary"  sortable="false"/>
                                <sjg:gridColumn name="sadd" title="Small Advertisement Display"  sortable="false"/>
                                <sjg:gridColumn name="total" title="Total"  sortable="false"/>



                            </sjg:grid>
                        </div>
                    </div>
                </div>
            </sj:tabbedpanel> 
            <div style="height: 30px"></div>
        </div>
        <!--        <div id="dialogSummary" title="Summary" style="display: none;background-color: white">
                    <h1>Hiii</h1>
                    <div class="form-group">
                        <button type="button" class="btn btn-primary" onclick="dialog.dialog('close');">Close</button>
                    </div>
                </div>-->




        <div id="dialog" title="Map Positions" style="display: none;background-color: #dcdcdc;margin: 0px;padding: 0">


            <div>
                <!--                <div class="text-center" style="background-color:#34495e ">
                                    <h3 class="text" style="margin-top: 10px;color: #93a5a5;font-weight: bolder;padding: 5px;margin:0">
                                        <i class="fa fa-map" aria-hidden="true" style="color:white"></i>&ensp;Map Positions
                                    </h3>
                                </div>-->
                <!--<div style="background-color: #179f86;height: 5px;width: 100%;"></div>-->
                <style>
                    #map {
                        height: 515px;
                        width: 96%;
                        margin-top: 2%;
                        margin-left: 2%;
                        border: 1px solid #8e8e8e;

                    }
                </style>
                <div id="map"></div>
                <script>

                    function initMap() {
                        var map = new google.maps.Map(document.getElementById('map'), {
                            zoom: 8,
                            center: {lat: 7.8731, lng: 80.7718}
                        });
                        setMarkers(map);
                    }


                    // Data for the markers consisting of a name, a LatLng and a zIndex for the
                    // order in which these markers should display on top of each other.
                    var beaches = [
                        ['Bondi Beach', -33.890542, 151.274856, 4],
                        ['Coogee Beach', -33.890542, 151.274856, 5],
                        ['Cronulla Beach', -34.028249, 151.157507, 3],
                        ['Manly Beach', -33.80010128657071, 151.28747820854187, 2],
                        ['Maroubra Beach', -33.950198, 151.259302, 1]
                    ];
                    function setMarkers(map) {
                        // Adds markers to the map.

                        // Marker sizes are expressed as a Size of X,Y where the origin of the image
                        // (0,0) is located in the top left of the image.

                        // Origins, anchor positions and coordinates of the marker increase in the X
                        // direction to the right and in the Y direction down.
                        var image = {
                            url: '${pageContext.request.contextPath}/resources/images/marker4.png',
                            // This marker is 20 pixels wide by 32 pixels high.
                            size: new google.maps.Size(20, 32),
                            // The origin for this image is (0, 0).
                            origin: new google.maps.Point(0, 0),
                            // The anchor for this image is the base of the flagpole at (0, 32).
                            anchor: new google.maps.Point(0, 32)
                        };
                        // Shapes define the clickable region of the icon. The type defines an HTML
                        // <area> element 'poly' which traces out a polygon as a series of X,Y points.
                        // The final coordinate closes the poly by connecting to the first coordinate.
                        var shape = {
                            coords: [1, 1, 1, 20, 18, 20, 18, 1],
                            type: 'poly'
                        };
                        for (var i = 0; i < palceslatlng.length; i++) {
                            var beach = palceslatlng[i];
                            var marker = new google.maps.Marker({
                                position: {lat: beach[1], lng: beach[2]},
                                map: map,
                                icon: image,
                                shape: shape,
                                title: beach[0],
                                zIndex: beach[3]
                            });
                        }
                    }

                </script>


            </div>

            <div class="form-group text-center" style="margin-top: 10px;"> 
                <button type="button" style="width: 100px" class="btn btn-danger btn-xs" onclick="dialog.dialog('close');">Close&ensp;<i class="fa fa-times-circle" aria-hidden="true"></i></button>
            </div>

        </div>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDurLciCEPA4JI3O0bvFCqqEGkyCWzw5p8&callback=initMap"
        async defer></script>
    </body>
</html>
