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
        <title>Imagine | Register</title>
    </head>
    <body>
        <h1>Register</h1>
       
        <form action="Register" method="post">
           username <input type="text" name="username" required>${messageus}<br /><br />
           password <input type="password" name="password" required><br /><br />
           retype password <input type="password" name="retypepassword" required> ${messagere}<br /><br />
           firstname <input type="text" name="firstname" required><br /><br />
           lastname <input type="text" name="lastname" required><br /><br />
           email <input type="email" name="email" required><br /><br />
           address <input type="text" name="address" required><br /><br />
           creditcardnumber <input type="number" name="creditNo" required><br /><br /><!-- check duay naja -->
           <input type="submit" value="Register">
        </form>
    </body>
</html>
