<%-- 
    Document   : home
    Created on : Nov 6, 2018, 3:43:57 PM
    Author     : ariya boonchoo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            table,thead,th,tr,td{
                border: 1px black solid;
            }
        </style>
    </head>
    <body> 
        <jsp:include page="include/Header.jsp?title=ProductListing ::"/>
        <h1>Hello World!</h1>
        <a href="Register"> Register </a><br>
        <a href="Login"> Login </a><br>
        <h2>${custom.username}</h2><br>
        <a href="Logout"> Logout </a><br>
        
        <form action="Search" method="post">
            search : <input type="text" name="search">&nbsp;&nbsp;
            <input type="submit">
        </form>
       
        <table>
            <thead>
            <th>No.</th>
            <th>Product ID</th>
            <th>Album Name</th>
            <th>Artist</th>
            <th>Gen</th>
            <th>Price</th>
            </thead>
            <c:forEach items="${productList}" var="p" varStatus="vs" >
                <tr>
                    <td>${vs.count}</td>
                    <td>${p.productid}</td>
                    <td><a href="GetProduct?productName=${p.productname}">${p.productname}</a></td>
                    <td>${p.artist}</td>
                    <td>${p.genre}</td>
                    <td>${p.price}</td>
                    <td><form action="AddItemToCart" method="post">
                                <input type="hidden" value="${p.productid}" name="productid"/>
                                <input type="submit" value="Add To Cart"/>
                            </form>
                    </td>
                    <td><form action="Favorite" method="post">
                                <input type="hidden" value="${p.productid}" name="productid"/>
                                <input type="submit" value="Favorite"/>
                            </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
