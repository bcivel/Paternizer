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
    <style>
        .custom-menu {
            display: none;
            z-index: 1000;
            position: absolute;
            overflow: hidden;
            border: 1px solid #CCC;
            white-space: nowrap;
            font-family: sans-serif;
            background: #FFF;
            color: #333;
            border-radius: 5px;
            padding: 0;
        }

        /* Each of the items in the list */
        .custom-menu li {
            padding: 8px 12px;
            cursor: pointer;
            list-style-type: none;
        }

        .custom-menu li:hover {
            background-color: #DEF;
        }
    </style>
    <script>
        function loadTree() {
            //$('#jstree_demo_div').children().empty();
            $('#jstree_demo_div').on('changed.jstree', function(e, data) {
                var i, j, r = [];
                for (i = 0, j = data.selected.length; i < j; i++) {
                    r.push(data.instance.get_node(data.selected[i]).text);
                }
                var urlFile = data.selected[0];
                loadJSON('GetFileWithParameters', "&fileURL=" + urlFile,
                        function(data) {
                            $("#templateContent").empty();
                            $("#templateContent").text(data.text);
                            $("#fileURL").empty();
                            $("#fileURL").val(urlFile);
                            $("#parameterList").empty();
                            if (data.parameters !== null) {
                                for (var d in data.parameters) {
                                    $("#parameterList").append("<div class='form-group'></div>")
                                            .append("<label class='label label-primary' for='" + data.parameters[d] + "'>" + data.parameters[d] + ":</label>")
                                            .append("<input class='form-control input-normal' id='" + data.parameters[d] + "' name='" + data.parameters[d] + "' type='text'>");
                                }
                            }
//                            
                        });
                //window.location.replace("?fileURL=<%= FileConstants.DOCUMENT_URL%>templates/" + r.join(', '));
                $('#event_result').html('Selected: ' + r.join(', '));
            }).jstree({
                'core': {
                    'data': {
                        'url': 'GetList?type=',
                        'data': function(node) {
                            return {'id': node.id, 'data-id': node.dataid};
                        }
                    }
                }})
//                    .bind("hover_node.jstree", function(e, data)
//            {
//
//var escpedId = data.node.id.replace('.', '\.');
//escpedId = data.node.id.replace(':', '\:');
//escpedId = data.node.id.replace('/', '\/');
//escpedId = data.node.id.replace('\\', '\\\\');
//console.log('[id="'+escpedId+'"]');  
//console.log($('[id="'+escpedId+'"]'));  
//            $('[id="'+escpedId+'"]').attr("title", data.node.original.text);
//            $('[id="'+escpedId+'"]').tooltip();
//            });
        }
    </script>
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
            $('#uploadTemplateDiv').load("include/uploadTemplate.html");
            $('#addEntryModal').on('hidden.bs.modal', {extra: "#addEntryModal"}, buttonCloseHandler);
            loadTree();
            $("#jstree_demo_div").bind("contextmenu", function(event) {
                // Avoid the real one
                console.log($(event.target.parentNode).parent().parent());
                event.preventDefault();
                $("#deleteFileButton").attr("data-file", event.target.parentNode.id);
                $("#addFileButton").attr("data-parent", event.target.parentNode.id);
                console.log(event.target.parentNode);
                // Show contextmenu
                $(".custom-menu").finish().toggle(100).
                        // In the right position (the mouse)
                        css({
                            top: event.pageY - 100 + "px",
                            left: event.pageX + "px"
                        });
            });
// If the document is clicked somewhere
            $(document).bind("mousedown", function(e) {

                // If the clicked element is not the menu
                if (!$(e.target).parents(".custom-menu").length > 0) {

                    // Hide it
                    $(".custom-menu").hide(100);
                }
            });
// If the menu element is clicked
            $(".custom-menu li").click(function() {

                // This is the triggered action name
                switch ($(this).attr("data-action")) {

                    // A case for each action. Your actions here
                    case "addfile":
                        $('#addEntryModal').modal('show');
                        break;
                    case "delete":
                        var file = $(this);
                        $.get("DeleteFile", "&filePath=" + file.attr("data-file")).done(
                                function() {
                                    $('#jstree_demo_div').jstree(true).refresh();
                                }
                        )
                        break;
                    case "third":
                        alert("third");
                        break;
                }

                // Hide it AFTER the action was triggered
                $(".custom-menu").hide(100);
            });
        });
        function buttonCloseHandler(event) {
            var modalID = event.data.extra;
            // reset form values
            $(modalID + " " + modalID + "Form")[0].reset();
            // remove all errors on the form fields
            $(this).find('div.has-error').removeClass("has-error");
            // clear the response messages of the modal
            clearResponseMessage($(modalID));
        }


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
            <div class="panel panel-default" style="overflow:auto">
                <div class="panel-heading">Select a Template :</div>
                <div id="jstree_demo_div">
                    <ul style="height: 600px" id="templateUl"></ul>
                </div>
            </div>  
        </div>


        <div class="col-sm-10">
            <div>
                <div class="panel panel-default">
                    <div class="panel-heading card">
                        <label>Template :</label>
                        <a data-toggle="collapse" data-target="#functionChart">
                            <span class="toggle glyphicon glyphicon-chevron-right pull-right"></span>
                        </a>
                    </div>
                    <div class="panel-body collapse in" id="functionChart" style="overflow:auto">
                        <span><xmp id="templateContent"><%=template%></xmp></span>
                    </div>
                </div>

            </div>

            <div>
                <div class="panel panel-default">
                    <div class="panel-heading card">
                        <label>Parameters :</label>
                        <a data-toggle="collapse" data-target="#parametersPart">
                            <span class="toggle glyphicon glyphicon-chevron-right pull-right"></span>
                        </a>
                    </div>
                    <div class="panel-body collapse in" id="parametersPart" style="overflow:auto">
                        <form action="GenerateFile">
                            <div class="form-group">
                                <label class="label label-primary" for="fileURL">template URL : </label><input class="form-control input-normal" id="fileURL" name="fileURL" type="text">
                            </div>
                            <div class="form-group">
                                <label class="label label-primary" for="fileName">file name : </label><input class="form-control input-normal" id="fileName" name="fileName" type="text">
                            </div>
                            <div id="parameterList"></div>
                            <%if (parameters != null) {
                                    for (String parameter : parameters) {
                            %>
                            <div class="form-group">
                                <label class="label label-primary" for="<%=parameter%>"><%=parameter%> : </label><input class="form-control input-normal" id="<%=parameter%>" name="<%=parameter%>" type="text">
                            </div>
                            <%
                                    }
                                }
                            %>
                            <input name="fromGui" value="Y" style="display:none">
                            <button class="btn btn-success" id="submit" type="submit">Generate</button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
        <div id="event_result"></div>
        <div id="uploadTemplateDiv"></div>
        <ul class='custom-menu'>
            <li data-action="createrepo">Create Repository</li>
            <li id="addFileButton" data-action="addfile" data-parent="/">Upload New File</li>
            <li id="deleteFileButton" data-action="delete" data-file="/toto">Delete</li>
        </ul>
    </body>
</html>
