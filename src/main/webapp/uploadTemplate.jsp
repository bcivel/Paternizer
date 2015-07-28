<%-- 
    Document   : uploadTemplate
    Created on : 21 mai 2014, 15:11:23
    Author     : memiks
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload a template</title>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </head>
    <body>
        <%@ include file="include/header.jsp"%>
        <div class="col-sm-12">
            <div class="panel panel-primary">
            <div class="panel-heading">Upload a template</div>
            <div class="panel-body">
        <form method="POST" enctype="multipart/form-data" action="UploadTemplate">
          File to upload: <input type="file" name="template"><br/>
          <br/>
          Folder to store file: <input name="path" value="/"><br/>
          <button type="submit">Press to upload the file!</button>
        </form>
            </div>
            </div>
        </div>
    </body>
</html>
