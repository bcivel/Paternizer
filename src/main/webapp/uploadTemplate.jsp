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
    </head>
    <body>
        <h1>Upload a template</h1>
        <form method="POST" enctype="multipart/form-data" action="UploadTemplate">
          File to upload: <input type="file" name="template"><br/>
          <br/>
          <button type="submit">Press to upload the file!</button>
        </form>
    </body>
</html>
