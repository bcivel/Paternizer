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
        <title>Publish on the FTP</title>
        <script src="javascript/functions.js"></script>
    </head>
    <body>
        <script>
            loadJSON('GetList',"&type=temp",
                    function(data) {
                        for (var i = 0; i < data.length; i++) {
                            var newInput = document.createElement("input");
                            newInput.setAttribute('type','checkbox');
                            newInput.setAttribute('value',data[i]);
                            newInput.setAttribute('id','fileName');
                            document.getElementById("generatedList").appendChild(newInput);
                            var newLink = document.createElement("a");
                            newLink.href = "?fileURL=http://192.168.134.35/PaternizerDocuments/temp/" + data[i];
                            newLink.setAttribute('id', data[i]);
                            document.getElementById("generatedList").appendChild(newLink);
                            var br = document.createElement("br");
                            document.getElementById("generatedList").appendChild(br);
                            document.getElementById(data[i]).innerHTML = data[i];
                        }
                    },
                    function(xhr) {
                        console.error(xhr);
                    }
            );
        </script> 
        <h1>Paternize a template file</h1><h3 style="float:right" onclick="showDoc()">?</h3>
        <span>To upload a template : click <a href="./uploadTemplate.jsp">here</a></span><br><br>
        <h3>The list of uploaded template is :</h3>
        <form action="PublishOnFtp">
            <div id="generatedList"></div>
            <br>
            <label for="host">FTP Host</label>
            <input type="text" id="host"/>
            &nbsp;
            <label for="port">Port</label>
            <input type="text" id="port"/>
            <br>
            <label for="user">User</label>
            <input type="text" id="user"/>
            &nbsp;
            <label for="password">Password</label>
            <input type="password" id="password"/>
            <br>
            <label for="foler">Folder</label>
            <input type="text" id="folder"/>
            <br>
            <button type="submit">Upload</button>
        </form>
        <br>
    </body>
</html>
