<%-- 
    Document   : Payment
    Created on : Nov 15, 2018, 6:23:36 PM
    Author     : ariya boonchoo
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Imagine | Payment</title>
          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">
       
    </head>
    <body>
        <h1>Payment</h1>
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
         
        <table>
            <tr>
                <td>Payment id : </td>
                <td>${pay.paymentid}</td>
            </tr>
            <tr>
                <td>Username : </td>
                <td>${custom.username}</td>
            </tr>
             <tr>
                <td>Name : </td>
                <td>${custom.firstname} &nbsp;&nbsp;&nbsp;${custom.lastname}</td>
            </tr>
             <tr>
                <td>Email : </td>
                <td>${custom.email}</td>
            </tr>
             <tr>
                <td>Address : </td>
                <td>${custom.address}</td> 
                <td>   or New address
             <form action="NewAddress" method="post">
                 <input type="text" name="newaddress" size="200">
                 <input type="submit">
            </form>
                </td>
            </tr>
            
           
             <tr>
                <td>Payment Status : </td>
                <td>${pay.paymentstatus}</td>
            </tr>
            <tr>
                <td><form action="ConfirmToPay" method="post">
                        <input type="submit" value="Confirm">
                
            </form>
                </td>
        </tr>
        </table>
    </body>
</html>
