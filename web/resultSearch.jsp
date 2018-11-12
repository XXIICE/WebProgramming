<%-- 
    Document   : resultSearch
    Created on : Nov 13, 2018, 12:35:59 AM
    Author     : ariya boonchoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>${custom.username}</h1>
        <table>
            <thead>
            <th>No.</th>
            <th>Album Name</th>
            <th>Artist</th>
            <th>Gen</th>
            <th>Price</th>
            </thead>
            <c:forEach items="${productList}" var="p" varStatus="vs" >
                <tr>
                    <td>${vs.count}</td>
                    <td>${p.productname}</td>
                    <td>${p.artist}</td>
                    <td>${p.genre}</td>
                    <td>${p.price}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
