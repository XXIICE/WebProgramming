 <%-- 
    Document   : ShowCart
    Created on : Aug 9, 2018, 4:04:17 PM
    Author     : INT303
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                <h1>Your Cart</h1>
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
                <th>Quantity</th>

                <th>Total Price</th> 
                </thead>
                <c:set var="Items" value="${sessionScope.cart.lineItems}"/>
                <%--<c:set var="bgColorX" value="lightgray"/>--%>
                <%--<c:set var="bgColorY" value="white"/>--%>
                <c:forEach items="${cart.lineItems}" var="p" varStatus="vs">
                    <tr>
                        <td><img src="images/KPOP-${p.product.productid}.jpg" width="120"></td>
                        <td>${vs.count}</td>
                        <td><a href="GetProduct?productId=${p.product.productid}">${p.product.productid}</a></td>
                        <td>${p.product.productname}</td>
                        <td>${p.salePrice} ฿</td>
                        <td> 
                            <table>
                                <tr> 
                                    <td>
                                        <form action="RemoveItemFromCart" method="post">
                                            <input type="hidden" value="${p.product.productid}" name="productid"/>
                                            <input type="submit" value="-"/>
                                        </form></td> 
                                    <td>&nbsp;&nbsp; ${p.quantity} &nbsp;&nbsp;</td>

                                    <td> <form action="PlusItem" method="post">
                                            <input type="hidden" value="${p.product.productid}" name="productid"/>
                                            <input type="submit" value="+"  /></form> 
                                    </td>

                                </tr>
                            </table>
                        </td> 

                        <%--<c:set var = "balance" value="${p.totalPrice}"/>--%> 
                        <%--<fmt:formatNumber type = "number"  maxIntegerDigits = "10" value = "${balance}" />--%>
                        <td ><fmt:formatNumber value="${p.totalPrice}" pattern="#,###.00"/> ฿</td>
                        <td>
                            <form action="RemoveAll" method="post">
                                <input type="hidden" value="${p.product.productid}" name="productid"/>
                                <input type="submit" value="Remove"/>
                            </form></td> 
                    </tr>

                </c:forEach>

            </table>
            <h4 align="right" > All Total Price : 
                <fmt:formatNumber value="${cart.totalPrice}" pattern="#,###.00"/>
                <%--<c:set var = "balance" value="${cart.totalPrice}"/>--%> 
                <%--<fmt:formatNumber type = "number"  maxIntegerDigits = "10" value = "${balance}" />--%>  
                ฿</h4><br>
                <h5 align ="right">
                    <form action="Payment" method="post">
                                <input type="hidden" value="${p.product.productid}" name="productid"/>
                                <input type="submit" value="Pay"/>
                            </form></h5>
            <h5 align ="right"><a href="ProductList"> Back to shop </a></h5>
            
        </div>

    </body>
</html>
