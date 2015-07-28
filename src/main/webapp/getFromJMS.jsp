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
        <title>Get From JMS</title>
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
            function loadMessagesFromJMS() {

//                $.getJSON("ReadJmsQueue?host=" + $("#host").val() + "&port=" + $("#port").val() + "&user=" + $("#user").val() + "&password=" + $("#password").val() + "&queueName=" + $("#queueName").val(),
//                        function(data) {
//                            $("#resultJMSQueue").empty();
//                            $("#resultFile").empty();
//                            for (var i = 0; i < data.length; i++) {
//                                var li = document.createElement("li");
//                                var newLink = document.createElement("a");
//                                newLink.setAttribute('id', data[i].id);
//                                li.appendChild(newLink);
//                                li.setAttribute('onClick', 'loadFromJMS("Text_' + data[i].id+ '")');
//                                document.getElementById("resultJMSQueue").appendChild(li);
//                                document.getElementById(data[i].id).innerHTML = data[i].id;
//                                
//                                var span = document.createElement("span");
//                                var div = document.createElement("xmp");
//                                div.setAttribute('id', 'Xmp_'+data[i].id)
//                                span.setAttribute('style', 'display:none');
//                                span.setAttribute('id', 'Text_'+data[i].id);
//                                span.appendChild(div);
//                                document.getElementById("resultFile").appendChild(span);
//                                document.getElementById('Xmp_'+data[i].id).innerHTML = data[i].text;
//                                
//                                
//                            }
//                        },
//                        function(xhr) {
//                            console.error(xhr);
//                        }
//                );

$('#jstree_demo_div2').on('changed.jstree', function(e, data) {
                    var i, j, r = [], path;
                    for (i = 0, j = data.selected.length; i < j; i++) {
                        r.push(data.instance.get_node(data.selected[i]).text);
                    }
                    console.log(data);
                    loadFromJMS(data.node.original.content);
                    $('#event_result').html('Selected: ' + r.join(', '));
                }).jstree({
                    'core': {
                        'data': {
                            'url': 'ReadJmsQueue?host=' + $("#host").val() + '&port=' + $("#port").val() + '&user=' + $("#user").val() + '&password=' + $("#password").val() + '&queueName=' + $("#queueName").val() ,
                            'data': function(node) {
                                return {'id': node.id};
                            }
                        }
                    }});

            }

            function loadFromJMS(data) {
                $("#resultFile").empty();
                var div = document.createElement("xmp");
                div.innerHTML = data;
                        $("#resultFile").append(div);
                     
            }
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
            String queueName;
            if (request.getParameter("queueName") != null && request.getParameter("queueName").compareTo("") != 0) {
                queueName = request.getParameter("queueName");
            } else {
                queueName = new String("");
            }
        %>
        <input hidden="hidden" id="defHost" value="<%=host%>">
        <input hidden="hidden" id="defPort" value="<%=port%>">
        <input hidden="hidden" id="defUser" value="<%=user%>">
        <input hidden="hidden" id="defPass" value="<%=password%>">
        <input hidden="hidden" id="defQueueName" value="<%=queueName%>">
        <div class="col-sm-12">
            <div class="panel panel-primary">
                <div class="panel-heading">QueueJMS Information
                <button class="btn btn-success" onclick="loadMessagesFromJMS()">Get Messages</button></div>
                <div class="panel-body">
                    <div class="form-group col-sm-2">
                        <label class="label label-primary" for="host">FTP Host</label>
                        <input type="text" class="form-control input-normal" id="host" name="host" onBlur="recordCookie('host', 'PaternizerJMSHost')"/>
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="label label-primary" for="port">Port</label>
                        <input type="text" class="form-control input-normal" id="port" name="port" onBlur="recordCookie('port', 'PaternizerJMSPort')"/>
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="label label-primary" for="user">User</label>
                        <input type="text" class="form-control input-normal" id="user" name="user" onBlur="recordCookie('user', 'PaternizerJMSUser')"/>
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="label label-primary" for="password">Password</label>
                        <input type="password" class="form-control input-normal" id="password" name="password" onBlur="recordCookie('password', 'PaternizerJMSPass')"/>
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="label label-primary" for="foler">Queue Name</label>
                        <input type="text" class="form-control input-normal" id="queueName" name="queueName" onBlur="recordCookie('queueName', 'PaternizerJMSQueueName')"/>
                    </div>
                    
                </div></div>
        </div>
        <div class="col-sm-3">
            <div class="panel panel-primary" style="overflow:auto">
                <div class="panel-heading">Select Files to Send to Queue JMS
                <button class="btn btn-danger" type="button" onclick="saveForm()">Send</button>
                </div>
                <div id="jstree_demo_div" style="overflow:auto">
                    <ul style="height: 600px" id="generatedList" ></ul>
                </div>
            </div>
        </div>
        <div class="col-sm-3">
            <div class="panel panel-primary" style="overflow:auto">
                <div class="panel-heading">Queue List</div>
                <div class="panel-body">
                    <div id="jstree_demo_div2">
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6">
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
                var fields = ["host", "port",  "user", "password", "queueName"];
                var defFields = ["defHost", "defPort", "defUser", "defPass", "defQueueName"];
                var cookies = ["PaternizerJMSHost", "PaternizerJMSPort", "PaternizerJMSUser", "PaternizerJMSPass", "PaternizerJMSQueueName"];
                
                for (var a = 0 ; a < fields.length ; a++){
                    $("#"+fields[a]).empty();
                var host = document.getElementById(defFields[a]).value;
                if (host !== "") {
                    document.getElementById(fields[a]).value = host;
                } else {
                    setCookie(cookies[a], fields[a]);
                }
                }
                   
            });

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
                param += "&queueName=" + $('#queueName').val();
                
                console.log(param);
                loadJSON('PublishOnQJMS', param,
                        function(data) {

                        },
                        function(xhr) {
                            console.error(xhr);
                        }
                );
            }

        </script>
    </body>
</html>
