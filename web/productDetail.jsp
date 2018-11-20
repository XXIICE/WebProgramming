<%-- 
    Document   : product
    Created on : Nov 6, 2018, 6:39:22 PM
    Author     : ariya boonchoo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Imagine | ProductDetail</title>
        <style>
            @import url('https://fonts.googleapis.com/css?family=Abril+Fatface|Questrial');

            .container{
                font-family: 'Abril Fatface', cursive;
                font-family: 'Questrial', sans-serif;
            }
        </style>
    </head>
    <body>
        <jsp:include page="include/Header.jsp?title=Product Detail :: "/>
        <div class="container">
            <table>
                <tr>
                    <td>Image : </td>
                    <td><img src="images/${product.genre}-${product.productid}.jpg" width="120"></td>
                </tr>
                <tr>
                    <td>Product Id: </td>
                    <td>${product.productid}</td>
                </tr>
                <tr>
                    <td>Product Name: </td>
                    <td>${product.productname}</td>
                </tr>


<tr> 
    <td>Tracklist : </td>
                <c:forEach items="${product.tracklistList}" var="p">
                <tr> 
                    <td>${p.songname}</td>
                  </tr>
                       
                </c:forEach>
</tr>
                <tr>
                    <td>Price: </td>
                    <td>${product.price}</td>
                </tr>



            </table>
        </div>
        <form action = "Review" method="post">
            review by ${custom.username}<input type="text" name="comment">

            <input type="submit" value="review">
        </form>
        <a href="ProductList"> Back to shop</a>
    </body>
</html>
