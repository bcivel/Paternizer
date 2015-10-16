<%@page import="java.util.List"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.MalformedURLException"%>
<%@page import="com.paternizer.service.TemplateService"%>
<%@page import="com.paternizer.constants.FileConstants"%>
<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Publish on the FTP</title>
        <script src="javascript/functions.js"></script>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
        <script src="js/jquery-ui-1.10.2.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/dist/jstree.min.js"></script>
        <link rel="stylesheet" href="js/dist/themes/default/style.min.css" />
        <link href="css/ui.fancytree.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <script>
            function loadFilesFromFTP() {
                $("#resultFile").empty();
                $("#contentFtpFiles").empty().append("<div id='jstree_demo_div2'></div>");
                var sftp = $("#sftp:checked").val() === undefined ? "" : "&sftp=" + $("#sftp:checked").val();
//                
                $('#jstree_demo_div2').on('changed.jstree', function(e, data) {
                    var i, j, r = [], path;
                    for (i = 0, j = data.selected.length; i < j; i++) {
                        r.push(data.instance.get_node(data.selected[i]).text);
                    }
                    if (data.node.icon === true){
                        path = data.node.original.folder + data.node.original.id;
                    }
                    var parents = "";
                    for (var par = data.node.parents.length-1 ; par >= 0 ; par --){
                      parents += "/" + data.node.parents[par]; 
                    }
                    loadFromFTP(r.join(', '), parents);
                    $('#event_result').html('Selected: ' + r.join(', '));
                }).jstree({
                    'core': {
                        'data': {
                            'url': 'GetFileListFromFtp?host=' + $("#host").val() + '&port=' + $("#port").val() + sftp + '&user=' + $("#user").val() + '&password=' + $("#password").val() + '&folder=' + $("#folder").val() ,
                            'data': function(node) {
                                return {'id': node.id};
                            }
                        }
                    }});

            }
                            

            function loadFromFTP(file, parents) {
                $("#resultFile").empty();
                var sftp = $("#sftp:checked").val() === undefined ? "" : "&sftp=" + $("#sftp:checked").val();
                $.getJSON("GetFromFtp?fileName=" + file + "&host=" + $("#host").val() + "&port=" + $("#port").val() + sftp + "&user=" + $("#user").val() + "&password=" + $("#password").val() + "&folder=" + $("#folder").val()+parents.split("/#")[1],
                        function(data) {
                            $("#resultFile").empty();
                            var doc = document.createElement("xmp");
                            doc.innerHTML = data.content;
                            document.getElementById("resultFile").appendChild(doc);
                        },
                        function(xhr) {
                            console.error(xhr);
                        }
                );
            }
            ;


        </script> 
        <script>
            $(document).ready(function() {
                $('#jstree_demo_div').jstree({
                    'core': {
                        'data': {
                            'url': 'GetList?type=temp',
                            'data': function(node) {
                                return {'id': node.id,
                                    'name': "fileName"};
                            }
                        }
                    },
                    'checkbox': {
                        "keep_selected_style": false
                    },
                    'plugins': ["wholerow", "checkbox"]});

            });

        </script>
        <%@ include file="include/header.jsp"%>
        <%
            String host;
            if (request.getParameter("host") != null && request.getParameter("host").compareTo("") != 0) {
                host = request.getParameter("host");
            } else {
                host = new String("");
            }
            String port;
            if (request.getParameter("port") != null && request.getParameter("port").compareTo("") != 0) {
                port = request.getParameter("port");
            } else {
                port = new String("");
            }
            String sftp;
            if (request.getParameter("sftp") != null && request.getParameter("sftp").compareTo("") != 0) {
                sftp = request.getParameter("sftp");
            } else {
                sftp = new String("");
            }
            String user;
            if (request.getParameter("user") != null && request.getParameter("user").compareTo("") != 0) {
                user = request.getParameter("user");
            } else {
                user = new String("");
            }
            String password;
            if (request.getParameter("password") != null && request.getParameter("password").compareTo("") != 0) {
                password = request.getParameter("password");
            } else {
                password = new String("");
            }
            String folder;
            if (request.getParameter("folder") != null && request.getParameter("folder").compareTo("") != 0) {
                folder = request.getParameter("folder");
            } else {
                folder = new String("");
            }
        %>
        <input hidden="hidden" id="defHost" value="<%=host%>">
        <input hidden="hidden" id="defPort" value="<%=port%>">
        <input hidden="hidden" id="defSftp" value="<%=sftp%>">
        <input hidden="hidden" id="defUser" value="<%=user%>">
        <input hidden="hidden" id="defPass" value="<%=password%>">
        <input hidden="hidden" id="defFolder" value="<%=folder%>">
        <div class="col-sm-12">
            <div class="panel panel-primary">
                <div class="panel-heading">FTP Information
                <button class="btn btn-success" onclick="loadFilesFromFTP()">Connection</button>
                </div>
                <div class="panel-body">
                    <div class="form-group col-sm-2">
                        <label class="label label-primary" for="host">FTP Host</label>
                        <input type="text" class="form-control input-normal" id="host" name="host" onBlur="recordCookie('host', 'PaternizerHost')"/>
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="label label-primary" for="port">Port</label>
                        <input type="text" class="form-control input-normal" id="port" name="port" onBlur="recordCookie('port', 'PaternizerPort')"/>
                    </div>
                    <div class="checkbox col-sm-2">
                        <label class="label label-primary"><input type="checkbox" id="sftp" name="sftp" onBlur="recordCookie('sftp', 'PaternizerSftp')"/>Is SFTP ?</label>

                    </div>
                    <div class="form-group col-sm-2">
                        <label class="label label-primary" for="user">User</label>
                        <input type="text" class="form-control input-normal" id="user" name="user" onBlur="recordCookie('user', 'PaternizerUser')"/>
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="label label-primary" for="password">Password</label>
                        <input type="password" class="form-control input-normal" id="password" name="password" onBlur="recordCookie('password', 'PaternizerPass')"/>
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="label label-primary" for="foler">Destination Folder</label>
                        <input type="text" class="form-control input-normal" id="folder" name="folder" onBlur="recordCookie('folder', 'PaternizerFolder')"/>
                    </div>
                </div></div>
        </div>
        <div class="col-sm-3">
            <div class="panel panel-primary" style="overflow:auto">
                <div class="panel-heading">Select Files to upload on FTP
                <button class="btn btn-danger" type="button" onclick="saveForm()">Upload</button>
                </div>
                <div id="jstree_demo_div">
                    <ul style="height: 600px" id="generatedList" ></ul>
                </div>
            </div>
        </div>
        <div class="col-sm-2">
            <div class="panel panel-primary" style="overflow:auto">
                <div class="panel-heading">FTP Tree</div>
                <div id="contentFtpFiles" class="panel-body">
                    <div id="jstree_demo_div2">
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-7">
            <div class="panel panel-primary" style="overflow:auto">
                <div class="panel-heading">File View</div>
                <div class="panel-body">
                    <div id="resultFile"></div>
                </div>
            </div>
        </div>

        <br>
        <script type="text/javascript">
            $(document).ready(function() {
                var fields = ["host", "port", "sftp", "user", "password", "folder"];
                var defFields = ["defHost", "defPort", "defSftp", "defUser", "defPass", "defFolder"];
                var cookies = ["PaternizerHost", "PaternizerPort", "PaternizerSftp", "PaternizerUser", "PaternizerPass", "PaternizerFolder"];

                for (var a = 0; a < fields.length; a++) {
                    $("#" + fields[a]).empty();
                    var host = document.getElementById(defFields[a]).value;
                    if (host !== "") {
                        document.getElementById(fields[a]).value = host;
                    } else {
                        setCookie(cookies[a], fields[a]);
                    }
                }

//                $("#host").empty();
//                var host = document.getElementById("defHost").value;
//                if (host !== "") {
//                    document.getElementById("host").value = host;
//                } else {
//                    setCookie('PaternizerHost', 'host');
//                }
//                $("#port").empty();
//                var port = document.getElementById("defPort").value;
//                if (port !== "") {
//                    document.getElementById("port").value = port;
//                } else {
//                    setCookie('PaternizerPort', 'port');
//                }
//                $("#sftp").empty();
//                var sftp = document.getElementById("defSftp").value;
//                if (sftp !== "") {
//                    document.getElementById("sftp").value = sftp;
//                } else {
//                    setCookie('PaternizerSftp', 'sftp');
//                }
//                $("#user").empty();
//                var user = document.getElementById("defUser").value;
//                if (user !== "") {
//                    document.getElementById("user").value = user;
//                } else {
//                    setCookie('PaternizerUser', 'user');
//                }
//                $("#password").empty();
//                var pass = document.getElementById("defPass").value;
//                if (pass !== "") {
//                    document.getElementById("password").value = pass;
//                } else {
//                    setCookie('PaternizerPass', 'pass');
//                }
//                $("#folder").empty();
//                var folder = document.getElementById("defFolder").value;
//                if (folder !== "") {
//                    document.getElementById("folder").value = folder;
//                } else {
//                    setCookie('PaternizerFolder', 'folder');
//                }
            });

        </script>
        <script>
            function saveForm() {
                var param = "";
                $('#jstree_demo_div').find("li[aria-selected=true]").each(function(i, e) {
                    param += "&fileName=" + e.id;
                });

                param += "&host=" + $('#host').val();
                param += "&port=" + $('#port').val();
                param += "&user=" + $('#user').val();
                param += "&password=" + $('#password').val();
                param += "&folder=" + $('#folder').val();
                if ($('#sftp:checked').val() !== undefined){
                    param += "&sftp=" + $('#sftp:checked').val();
                }
                console.log(param);
                loadJSON('PublishOnFtp', param,
                        function(data) {
                            
                        },
                        function(xhr) {
                            console.error(xhr);
                        }
                );
                loadFilesFromFTP();
            }

        </script>
    </body>
</html>
