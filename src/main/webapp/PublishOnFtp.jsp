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
    </head>
    <body>
        <script>
            function loadJSON(path, success, error)
            {
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function()
                {
                    if (xhr.readyState === 4) {
                        if (xhr.status === 200) {
                            if (success)
                                success(JSON.parse(xhr.responseText));
                        } else {
                            if (error)
                                error(xhr);
                        }
                    }
                };
                xhr.open("GET", path, true);
                xhr.send();
            }

            loadJSON('GetList',"&type=temp",
                    function(data) {
                        for (var i = 0; i < data.length; i++) {
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
        <script>
            function showDoc(){
            window.open('images/consigne.jpg', 'popup',
            'width=600,height=500,scrollbars=yes,menubar=false,location=false');    
            }
        </script>
        <h1>Paternize a template file</h1><h3 style="float:right" onclick="showDoc()">?</h3>
        <span>To upload a template : click <a href="./uploadTemplate.jsp">here</a></span><br><br>
        <h3>The list of uploaded template is :</h3>
        <div id="generatedList"></div>    
        <br>
    </body>
</html>
