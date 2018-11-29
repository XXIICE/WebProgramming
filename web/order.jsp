<%-- 
    Document   : order
    Created on : Nov 30, 2018, 2:59:33 AM
    Author     : ariya boonchoo
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Imagine | Order</title>
    </head>
    <body>
        <h1>Order</h1>
        <table class="table" id="example" >
                <thead>
               <th>Images</th>
                <th>No</th>
                <th>Product ID</th>            
                <th>Product Name</th> 
                <th>Price per piece</th>  
                <th>Quantity</th>

                <th>Total Price</th> 
                </thead>
                <c:set var="Items" value="${sessionScope.cart.lineItems}"/>
                <%--<c:set var="bgColorX" value="lightgray"/>--%>
                <%--<c:set var="bgColorY" value="white"/>--%>
                <c:forEach items="${cart.lineItems}" var="p" varStatus="vs">
                    <tr>
                        <td><img src="images/${p.product.genre}-${p.product.productid}.jpg" width="120"></td>
                        <td>${vs.count}</td>
                        <td><a href="GetProduct?productId=${p.product.productid}">${p.product.productid}</a></td>
                        <td>${p.product.productname}</td>
                        <td>${p.salePrice} ฿</td>
                         <td>&nbsp;&nbsp; ${p.quantity} &nbsp;&nbsp;</td>


                        <%--<c:set var = "balance" value="${p.totalPrice}"/>--%> 
                        <%--<fmt:formatNumber type = "number"  maxIntegerDigits = "10" value = "${balance}" />--%>
                        <td ><fmt:formatNumber value="${p.totalPrice}" pattern="#,###.00"/> ฿</td>
                        
                    </tr>

                </c:forEach>

            </table>
            <h4 align="right" > All Total Price : 
                <fmt:formatNumber value="${cart.totalPrice}" pattern="#,###.00"/>
                <%--<c:set var = "balance" value="${cart.totalPrice}"/>--%> 
                <%--<fmt:formatNumber type = "number"  maxIntegerDigits = "10" value = "${balance}" />--%>  
                ฿</h4><br>
                <!--<h2>Point you get : ${custom.point}</h2>-->
    
    </body>
</html>
