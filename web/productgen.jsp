<%-- 
    Document   : K_pop
    Created on : Nov 30, 2018, 4:47:49 AM
    Author     : ariya boonchoo
--%>

<%-- 
    Document   : productAll
    Created on : Nov 30, 2018, 4:40:22 AM
    Author     : ariya boonchoo
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
       
        <title>Imagine</title>
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

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Imagine | Payment</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css">

   
    </head>
   
    <body style="background-color:#f3f3f3;">
        <jsp:include page="include/Header.jsp"/>

        <main class="mt-5">
            <div class="container">
                <section id="examples" class="text-center" style="margin-top:9%;">

                    <!-- Heading -->
                    <h3 class="mb-4 font-weight-bold" style="background-color:#e5e5e5;padding-top:10px;padding-bottom:10px;letter-spacing:5px;">Product</h3>

                    <div class="row">
                        <div class="col-sm-12 text-md-left" style="margin-bottom:1%;margin-top:2%;font-size:20px;"><b>All</b></div>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <div class="row" style="font-size:18px;">   
                                <div class="col-sm-1">
                                    <strong></strong>
                                </div>    
                                <div class="col-sm-2 text-md-left">
                                    <strong>Album</strong>
                                </div>    
                                <div class="col-sm-3 text-md-left">
                                    <strong></strong>
                                </div>    
                               
                                <div class="col-sm-2">
                                    <strong>Price</strong>
                                </div>    
                                
                            </div> 
                            <hr class="md-5">
                            <%--<c:set var="Items" value="${sessionScope.cart.lineItems}"/>--%>
                            <c:forEach items="${productgenre}" var="p" varStatus="vs">
                                <div class="row" style="font-size:14px;">   
                                    <div class="col-sm-1">
                                        <strong class="align-self-center">${vs.count}</strong>
                                    </div>    
                                    <div class="col-sm-2 text-md-left">
                                        <a class="thumbnail pull-left" href="GetProductDetail?productid=${p.productid}"> <img class="media-object" src="images/${p.productid}.jpg" style="width: 150px;"> </a>
                                    </div>    
                                    <div class="col-sm-3">
                                        <div class="media-body text-md-left" style="line-height:20px;">
                                            <strong><a hhref="GetProductDetail?productid=${p.productid}" style="font-size:18px;">${p.productname}</a></strong><br>
                                            <span> by ${p.artist}</span><br>
                                            <span>Status: </span><span class="text-success"><strong>In Stock</strong></span>
                                        </div>
                                    </div>    
                                        
                                    <div class="col-sm-3">
                                        <p>${p.price} ฿</p>
                                    </div>    
                                </div>
                            </c:forEach>
                            <hr class="md-5">
                            <div class="row">
                                <div class="col-sm-1"></div>
                                <div class="col-sm-2"></div>
                                <div class="col-sm-3"></div>
                                <div class="col-sm-3"></div>
                                <div class="col-sm-3">
                                </div>
                            </div>
                        </div>
                    </div>
<script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
