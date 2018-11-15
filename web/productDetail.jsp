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
        <jsp:include page="include/Header.jsp?title=Product :: "/>
        <h1>Product</h1>
        Image<br>
        ${product.productid}<br>
        ${product.productname}<br>
        ${product.productid.songname}<br>
        ${product.price}
        <form action = "Review" method="post">
        review by ${custom.username}<input type="text" name="comment">
        
        <input type="submit" value="review">
        </form>
    </body>
</html>
