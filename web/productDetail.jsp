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
        <link rel="shortcut icon" href="images/icon.ico"/>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <title>Full Slider - Start Bootstrap Template</title>

        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/full-slider.css" rel="stylesheet">

        <!--Font awesome-->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">

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
             <table style="margin-top: 80px;">
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
                
                    <td>${p.songname}</td>
              
                       
                </c:forEach>
</tr>
                <tr>
                    <td>Price: </td>
                    <td>${product.price}</td>
                </tr>
<tr>
                    <td>Releasedate </td>
                    <td>${product.releasedate}</td>
                </tr>
                <tr>
<td><form action="AddItemToCartDetail" method="post">
                        <input type="hidden" value="${product.productid}" name="productid"/>

                        <input type="submit" value="Add To Cart"/>
                    </form>
                </td>
                <td><form action="FavoriteDetail" method="post">
                        <input type="hidden" value="${product.productid}" name="productid"/>
                        <input type="submit" value="Favorite"/>
                    </form>
                </td>
                </tr>
            </table>
        
        <form action = "Review" method="post">
            review by ${custom.username}<input type="text" name="comment">

            <input type="submit" value="review">
        </form>
        <a href="ProductList"> Back to shop</a>
        </div>
    </body>
</html>
