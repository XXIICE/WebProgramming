<%-- 
    Document   : ShowFavorite
    Created on : Nov 15, 2018, 5:06:50 PM
    Author     : ariya boonchoo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
       
    </head>
    <body>

        <div class="container">
            <table class="table">
                <tr>
                <h1>Your Favorite</h1>
                </tr>

            </table>

            <hr>
            <table class="table" id="example" >
                <thead>
                <th>Images</th>
                <th>No</th>
                <th>Product ID</th>            
                <th>Product Name</th> 
                <th>Price per piece</th> 
               
                </thead>
                <c:set var="Items" value="${sessionScope.fav.lineItems}"/>
                
                <c:forEach items="${fav.lineItems}" var="f" varStatus="vs">
                    <tr>
                        <td><img src="model-images/${f.product.productid}.jpg" width="120"></td>
                        <td>${vs.count}</td>
                        <td><a href="GetProduct?productid=${f.product.productid}">${f.product.productid}</a></td>
                        <td>${f.product.productname}</td>
                        <td>${f.salePrice} $</td>
                        <td> <table>
                                <tr> 
                                    <td>
                                        <form action="RemoveFavorite" method="post">
                                            <input type="hidden" value="${f.product.productid}" name="productid"/>
                                            <input type="submit" value="x"/>
                                        </form></td> 
                                </tr>
                            </table>
                        </td>
                        
                    </tr>
                </c:forEach>
            </table>
                <h5 align ="right"> <a href="ProductList">Back to home</a></h5>
    </body>
</html>
