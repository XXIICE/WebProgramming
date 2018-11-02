<%-- 
    Document   : register
    Created on : Nov 2, 2018, 10:31:38 AM
    Author     : Yanisa Kanchai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Register</h1>
        ${message}
        <form action="Register" method="post">
           username <input type="text" name="username" required><br /><br />
           password <input type="text" name="password" required><br /><br />
           firstname <input type="text" name="firstname" required><br /><br />
           lastname <input type="text" name="lastname" required><br /><br />
           email <input type="text" name="email" required><br /><br />
           address <input type="text" name="address" required><br /><br />
           creditcartnumbet <input type="number" name="creditNo." required><br /><br />
           <input type="submit" value="Register">
        </form>
    </body>
</html>
