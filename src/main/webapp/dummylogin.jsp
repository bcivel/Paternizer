<%-- 
    Document   : login
    Created on : 28 juil. 2015, 08:37:35
    Author     : bcivel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/mycss.css" />
    </head>
    <body>
        <div style="padding-top: 7%; padding-left: 30%">
            <div id="login-box">
                <H2>WMS Login</H2><br><br><br>
                Please login in order to do the Stuff.<br>
                If you don't have login, please contact <a href="/">an Administrator</a>
                <br>
                <br>
                    <div class="login-box-name" style="margin-top:20px;">
                        Username:
                    </div>
                    <div class="login-box-field" style="margin-top:20px;">
                        <input name="j_username" class="form-login" title="Username" value="" size="30" maxlength="10">
                    </div>
                    <div class="login-box-name">
                        Password:
                    </div>
                    <div class="login-box-field">
                        <input name="j_password" type="password" class="form-login" title="Password" value="" size="30" maxlength="20">
                    </div>
                    <input id="Login" name="Login" type="image" src="images/login-btn.png" style="margin-left:90px;" onclick="redirect()">
           </div>
        </div>
        <script>
            function redirect(){
            window.location.href = "./dummytransport.jsp"
        }
        </script>
            
    </body>
</html>
