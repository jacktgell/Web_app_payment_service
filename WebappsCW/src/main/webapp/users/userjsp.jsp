<%-- 
    Document   : userjsp
    Created on : 01-May-2020, 23:57:26
    Author     : jack
--%>

<%@page import="java.util.Enumeration"%>
<%@page import="com.sun.xml.rpc.processor.modeler.j2ee.xml.string"%>
<%@page import="com.MyCoursework.entity.SystemUser"%>
<%@page import="com.MyCoursework.entity.SystemUser_"%>
<%@page import="com.MyCoursework.jsf.LoginBean"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            if (request.getUserPrincipal().getName() != null ){
                //out.print(" - " + request.getUserPrincipal().getName() + " - ");
                response.sendRedirect(request.getContextPath() + "/faces/main.xhtml");
            }
        %>
    </body>
</html>
