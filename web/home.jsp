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

        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
        <style>
            @import url('https://fonts.googleapis.com/css?family=Abril+Fatface|Questrial');

            .container{
                font-family: 'Abril Fatface', cursive;
                font-family: 'Questrial', sans-serif;
            }
        </style>
    </head>
    <body> 
        <jsp:include page="include/Header.jsp?title=Imagine ::"/>
        <a href="profile.jsp">Profile</a><br>
        <a href="Register"> Register </a><br>
        <a href="Login"> Login </a><br>
        <h2>${custom.username}</h2><br>
        <a href="Logout"> Logout </a><br>

        <!--    <form action="Search" method="post">
                    search : <input type="text" name="search">&nbsp;&nbsp;
                    <input type="submit">
                </form>
        -->

        <table class="table" id="example" >
            <thead>
            <th>No.</th>
            <th>Image</th>
            <th>Product ID</th>
            <th>Album Name</th>
            <th>Artist</th>
            <th>Gen</th>
            <th>Price</th>
        </thead>
        <c:forEach items="${productList}" var="p" varStatus="vs" >
            <tr>
                <td>${vs.count}</td>
                <td><img src="images/${p.genre}-${p.productid}.jpg" width="120"></td>
                <td><a href="GetProduct?productid=${p.productid}">${p.productid}</a></td>
                <td>${p.productname}</a></td>
                <td>${p.artist}</td>
                <td>${p.genre}</td>
                <td>${p.price} ฿</td>

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
<!--    <h2>Recent View</h2>
    <table  class="table" id="example" >
        <thead>
        <th>No.</th>
        <th>Image</th>
        <th>Product ID</th>
        <th>Album Name</th>
        <th>Artist</th>
        <th>Gen</th>
        <th>Price</th>
    </thead>
    <%--<c:forEach items="${cookie.productid.value}" var="p" varStatus="vs" >--%>
        <tr>
            <td>${vs.count}</td>
            <td><img src="images/KPOP-${p.productid}.jpg" width="120"></td>
            <td>${p.productid}</a></td>
            <td><a href="GetProduct?productName=${p.productname}">${p.productname}</a></td>
            <td>${p.artist}</td>
            <td>${p.genre}</td>
            <td>${p.price} ฿</td>

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
    <%--</c:forEach>--%>
</table>-->


<script>
    $(document).ready(function () {
        $('#example').DataTable();
    });

</script>
</body>
</html>
