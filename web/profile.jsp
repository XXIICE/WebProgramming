<%-- 
    Document   : profile
    Created on : Nov 16, 2018, 9:53:21 AM
    Author     : ariya boonchoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Imagine | Profile</title>
       
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
        <div class="container">
        <h1>Profile</h1>
        <table>
            <tr>
            <th>Username : </th>
            <td>${custom.username}</td>
            </tr>
            <tr>
                <th>Name : </th>
                <td>${custom.firstname}
                     &nbsp;&nbsp;&nbsp; ${custom.lastname}</td>
            </tr>
            <tr>
                <th>Email : </th> 
                <td>${custom.email}</td>
            </tr>
            <tr>
                <th>Address : </th>
                <td>${custom.address}</td>
            </tr>

            </tr>
            <tr>
                 <th>Point</th>
                  <td>${custom.point}</td>
            </tr>

            
        </table>
            <c:if test="${fav!=null}">
                <a href="ShowFavorite.jsp">See Your Favorite</a>
            </c:if>
                <a href="ProductList">Back to home</a>
        </div>
    </body>
</html>
