<%-- 
    Document   : login
    Created on : Nov 2, 2018, 10:14:19 AM
    Author     : INT303
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Imagine | Login</title>
    </head>
    <body>
        <h1>Login</h1><br>
        ${message}
        <form action="Login" method="post">
           username <input type="text" name="username" required>
           password <input type="text" name="password" required>
           <input type="submit" value="Login">
        </form>
    </body>
</html>
