<%-- 
    Document   : dummytransport
    Created on : 28 juil. 2015, 08:51:51
    Author     : bcivel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dummy_Transport</title>
    </head>
    <body>
        <h1>WMS Transport Page</h1>
        <br><br>
        <h4>Select the Rendez-vous</h4>
        <select multiple id="transport" name="transport">
            <option value="1">Norbert</option>
            <option value="2">Philippe</option>
            <option value="3">Thierry</option>
        </select>
        <h4>Select the Quai</h4>
        <select multiple id="quai" name="quai">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
        </select>
        <br><br>
         <button type="button" id="validate">Start Reception</button> 
    </body>
</html>
