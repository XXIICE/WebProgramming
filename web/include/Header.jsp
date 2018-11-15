<%-- 
    Document   : Header
    Created on : Aug 10, 2018, 3:12:05 PM
    Author     : INT303
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--<body style="background-color:${cookie.bgColor.value}">-->
<!--<table class="table" style="background-color:${cookie.bgColor.value}">-->
<table class="table">
    <tr>
        <td>
            <a href="index.html"><img src="" width="90" style="float: left"/></a>
            <h1 style="display: inline-block">${param.title}</h1>   
        </td>
        <td>
            <c:if test="${cart!=null}">
                <a href="ShowCart">Your Cart : (${cart.totalQuantity})</a>
            </c:if>
            <c:if test="${fav!=null}">
                <a href="ShowFavorite">Your Favorite : (${fav.totalQuantity})</a>
            </c:if>
            &nbsp;&nbsp;&nbsp;
            <c:choose>
                <c:when test="${sessionScope.custom != null}">
                    Hello <a href="Logout"> ${sessionScope.custom.username}</a>
                </c:when>
                <c:otherwise>
                    Hello <a href="Login"> Guest</a>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>
        <hr>
        Session Id : ${cookie.JSESSIONID.value}
        <hr>
</body>