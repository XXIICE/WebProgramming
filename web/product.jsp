<%-- 
    Document   : product
    Created on : Nov 6, 2018, 6:39:22 PM
    Author     : ariya boonchoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Imagine | Product</title>
    </head>
    <body>
        <h1>Product</h1>
        <form action = "Review" method="post">
        review <input type="text" name="comment">
        User : ${custom.username}
        <input type="submit" value="review">
        </form>
    </body>
</html>
