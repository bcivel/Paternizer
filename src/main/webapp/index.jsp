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
    </head>
    <body>
        <h1>Paternize a template file</h1>
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
            
            if(parameters != null) {
                for(String parameter: parameters) {
                    %><span>PARAMETER Template: <%=parameter%></span><br><%
                }
            }
            
        %>
    </body>
</html>
