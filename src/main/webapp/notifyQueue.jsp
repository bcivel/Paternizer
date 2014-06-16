<%-- 
    Document   : notify a jms queue
    Created on : 16 juin 2014
    Author     : memiks
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Notify JMS Queue</title>
    </head>
    <body>
        <h1>Notify JMS Queue</h1>
        <form method="get" action="ExecuteQJMS">
            <label for="server">Server</label> <input type="text" name="server"><br/>
            <label for="user">user</label> <input type="text" name="user"><br/>
            <label for="password">password</label> <input type="text" name="password"><br/>
            <label for="queueName">queueName</label> <input type="text" name="queueName"><br/>
            <label for="fileName">fileName</label> <input type="text" name="fileName"><br/>
            <label for="filePath">filePath</label> <input type="text" name="filePath"><br/>
            <label for="lpar">lpar</label> <input type="text" name="lpar"><br/>
            <label for="job">job</label> <input type="text" name="job"><br/>
          <br/>
          <button type="submit">Press to notify the Queue !</button>
        </form>
    </body>
</html>
