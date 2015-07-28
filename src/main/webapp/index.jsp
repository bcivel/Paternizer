<%@page import="com.paternizer.constants.FileConstants"%>
<%@page import="java.util.List"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.MalformedURLException"%>
<%@page import="com.paternizer.service.TemplateService"%>
<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
//            loadJSON('GetList', "&type=templates",
//                    function(data) {
//                        for (var i = 0; i < data.length; i++) {
//                            var li = document.createElement("li");
//                            li.setAttribute('class', 'folder');
//                            var newLink = document.createElement("a");
//                            newLink.href = "?fileURL=<%= FileConstants.DOCUMENT_URL%>templates/" + data[i].name;
//                            newLink.setAttribute('id', data[i].name);
//                            li.appendChild(newLink);
//                            document.getElementById("templateUl").appendChild(li);
//                            document.getElementById(data[i].name).innerHTML = data[i].name;
//                        }
//                         $('#jstree_demo_div').jstree();
//                    },
//                    function(xhr) {
//                        console.error(xhr);
//                    }
//            );
//        
            $(document).ready(function() {
                $('#jstree_demo_div').on('changed.jstree', function(e, data) {
                    var i, j, r = [];
                    for (i = 0, j = data.selected.length; i < j; i++) {
                        r.push(data.instance.get_node(data.selected[i]).text);
                    }
                    console.log('Selected: ' + r.join(', '));
                    window.location.replace("?fileURL=<%= FileConstants.DOCUMENT_URL%>templates/" + r.join(', '));
                    $('#event_result').html('Selected: ' + r.join(', '));
                }).jstree({
                    'core': {
                        'data': {
                            'url': 'GetList?type=templates',
                            'data': function(node) {
                                return {'id': node.id};
                            }
                        }
                    }});
            });


        </script> 
        <%@ include file="include/header.jsp"%>

        <%
            String template;
            TemplateService templateService = new TemplateService();
            try {
                URL templateURL = new URL(request.getParameter("fileURL"));
                template = templateService.getFile(templateURL);
            } catch (MalformedURLException e) {
                System.err.println(" Unknow URL : " + request.getParameter("fileURL") + ".");
                template = null;
            } catch (IOException e) {
                System.err.println(e);
                template = null;
            }
            List<String> parameters = templateService.getParameters(template);


        %>
        <div class="col-sm-12">
            <div class="col-sm-2">
                <div class="panel panel-primary" style="overflow:auto">
                    <div class="panel-heading">Select a Template :</div>
                    <div id="jstree_demo_div">
                        <ul style="height: 600px" id="templateUl" ></ul>
                    </div>
                </div>  
            </div>

            <%    if (parameters != null) {
            %>
            <div class="col-sm-10">
                <div class="col-sm-10">
                <div class=" panel panel-primary" style="overflow:auto">
                    <div class="panel-heading">Template :</div>
                    <span><xmp><%=template%></xmp></span>
                </div>
            </div>
            <div class="col-sm-10">
                <div class=" panel panel-primary" style="overflow:auto">
                    <div class="panel-heading">Parameters :</div>
                    <form action="GenerateFile">
                        <div class="form-group">
                            <label class="label label-primary" for="fileURL">template URL : </label><input class="form-control input-normal" id="fileURL" name="fileURL" type="text" value="<%=request.getParameter("fileURL")%>">
                        </div>
                        <div class="form-group">
                            <label class="label label-primary" for="fileName">file name : </label><input class="form-control input-normal" id="fileName" name="fileName" type="text">
                        </div>
                        <%
                            for (String parameter : parameters) {
                        %>
                        <div class="form-group">
                            <label class="label label-primary" for="<%=parameter%>"><%=parameter%> : </label><input class="form-control input-normal" id="<%=parameter%>" name="<%=parameter%>" type="text">
                        </div>
                        <%
                            }
                        %>
                        <input name="fromGui" value="Y" style="display:none">
                        <button class="btn btn-success" id="submit" type="submit">Generate</button>
                    </form>
                </div></div>
            </div>
        </div>
                        <div id="event_result"></div>
        <%
            }

        %>

    </body>
</html>
