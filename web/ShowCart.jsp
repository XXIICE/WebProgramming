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

    </head>
    <body tyle="background-color:#f3f3f3;">

        <jsp:include page="include/Header.jsp"/>

        <main class="mt-5" style="margin-bottom:10%;">
            <div class="container">
                <section id="examples" class="text-center" style="margin-top:9%;">

                    <!-- Heading -->
                    <div class="row">
                        <div class="col-sm-12 text-md-left" style="margin-bottom:1%;margin-top:2%;font-size:25px;"><b>Cart</b></div>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <!-- PRODUCT -->
                            <c:choose>
                                <c:when test="${cart!=null}">
                                    <c:set var="Items" value="${sessionScope.cart.lineItems}"/>
                                    <c:forEach items="${cart.lineItems}" var="p" varStatus="vs">
                                        <div class="row">
                                            <div class="col-12 col-sm-12 col-md-2 text-center">
                                                <img class="img-responsive" src="images/${p.product.productid}.jpg" alt="prewiew" width="120">
                                            </div>
                                            <div class="col-12 text-sm-center col-sm-12 text-md-left col-md-6">
                                                <h4 class="product-name"><strong style="color:#0061aa;">${p.product.productname}</strong></h4>
                                                <p style="line-height:18px;">
                                                    <small><strong>Artist:</strong> ${p.product.artist}</small><br>
                                                    <small><strong>Genre:</strong> ${p.product.genre}</small><br>
                                                </p>
                                            </div>
                                            <div class="col-12 col-sm-12 text-sm-center col-md-4 text-md-right row">
                                                <div class="col-3 col-sm-3 col-md-6 text-md-right" style="padding-top: 8px">
                                                    <p><strong><fmt:formatNumber value="${p.salePrice}" pattern="#,##0.00"/>฿<span class="text-muted"> x</span></strong></p>
                                                </div>                    
                                                <div class="col-4 col-sm-4 col-md-4">
                                                    <div class="quantity">
                                                        <a href="PlusItem?productid=${p.product.productid}"><input type="button" value="+" class="plus"></a>
                                                        <input type="text" value="${p.quantity}" title="Qty" class="qty"
                                                               size="4">
                                                        <a href="RemoveItemFromCart?productid=${p.product.productid}"><input type="button" value="-" class="minus"></a>
                                                    </div>
                                                </div>
                                                <div class="col-2 col-sm-2 col-md-2 text-right">
                                                    <form action="RemoveAll" method="post">
                                                        <input type="hidden" value="${p.product.productid}" name="productid"/>
                                                        <button type="submit" class="btn btn-outline-danger btn-xs">
                                                            <i class="fa fa-trash" aria-hidden="true"></i>
                                                        </button>
                                                    </form>
                                                </div>           
                                            </div>
                                        </div>
                                        <hr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <h5><strong><center>No album in the cart.</center></strong></h5>
                                            </c:otherwise>    
                                        </c:choose>

                            <!-- END PRODUCT -->
                        </div>
                        <div class="card-footer">
                            <div class="row">
                                <div class="col-sm-4">
                                    <div style="text-align:left;margin:5px;">
                                        <div style="margin:10px;text-align:left;">
                                            <a href="productDiv1" class="btn btn-outline-info">Back to shopping</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-8">
                                    <div class="d-flex align-items-center" style="text-align:right;margin:5px;">
                                        <div class="" style="margin:5px;line-height:17px;width:100%">
                                            Total Price: <b><fmt:formatNumber value="${cart.totalPrice}" pattern="#,##0.00"/>฿</b><br>
                                        </div>
                                        <div class="d-inline-block" style="margin-top:2px;margin-right:5px;">
                                            <a href="Payment?productid=${p.product.productid}" class="btn btn-success">Checkout</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </main>

        <!-- Footer -->
        <footer class="py-5 bg-dark">
            <div class="container">
                <div class="row">
                    <div class="col-sm-8">
                        <h1><a href="ProductList"><img src="images/logo-white.png" height="40px" class="imagine"></a></h1>
                        <p style="color: #7e7e7e;font-size: 12px;">126 Pracha Uthit Rd., <br>Bang Mod, Thung Khru, <br>Bangkok 10140, <br>Thailand
                            <br><span class="fa fa-phone-square"> +66 2470 8000</span>
                            <br><span class="fa fa-envelope">  info@imagine.com</span>
                        </p>
                    </div>
                    <div class="col-sm-4">
                        <p style="font-size: 18px;font-weight: bold;color: #7e7e7e;padding-top: 20px;">About</p>
                        <span class="footer-about"><a href="contact.jsp" style="font-size: 12px;color: #7e7e7e;">Contact us</a><br>
                            <a href="team.jsp" style="font-size: 12px;color: #7e7e7e;">Team</a></span>
                        <p style="color: #7e7e7e;font-size: 12px;line-height: 80px;">&copy; imagine All rights reserved</p>
                    </div>
                </div>
            </div>
            <!-- /.container -->
        </footer>
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
