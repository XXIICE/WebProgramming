<%-- 
    Document   : search
    Created on : Nov 28, 2018, 11:14:22 PM
    Author     : ariya boonchoo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Imagine | Search</title>
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
         <jsp:include page="include/Header.jsp"/>
         
        
                     <form action="Search" method="post">
                    search : <input type="text" name="search">&nbsp;&nbsp;
                    <input type="submit">
                </form>
        

         <table class="table" id="example" style="margin-top: 100px;">
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
            <form action="RecentView" method="post">
                <input type="hidden" name="productid" value="productid" ${cookie.productid.value=='productid'?'onclick':''}>
                <td><a href="GetProductDetail?productid=${p.productid}">${p.productid}</a></td>
            </form>    
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
         <h2>${messageSearch}</h2>
   <script>
    $(document).ready(function () {
        $('#example').DataTable();
    });

</script>
    </body>
</html>
