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
    <body style="background:linear-gradient(90deg, #e8e8e8 50%, #8493a3 50%);">
        <div class="container">
            <div class="card shopping-cart" style="border:none; !important">
                <div class="card-header bg-dark text-light">
                    <div class="row">
                        <div class="col-sm-4">
                            <i class="fa fa-shopping-cart" aria-hidden="true"></i> &nbsp;Shopping Cart
                        </div>
                        <div class="col-sm-8 text-md-right">
                            <i class="fas fa-bars text-light" data-toggle="modal" data-target="#exampleModal3" style="cursor:pointer;"></i>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal -->          
            <div class="modal fade" id="exampleModal3" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel3" aria-hidden="true">
                <div class="modal-dialog modal-dialog-slideout" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel"><a class="navbar-brand" href="ProductList"><img src="images/logo-black.png" height="25px" class="imagine"></a></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div>
                                <a class="dropdown-item" href="ProductList" style="color:#525252;font-size:16px;font-weight:bold;">Home</a>
                            </div>
                            <div class="myaccount">
                                <div>
                                    <a class="dropdown-item dropdown-toggle" href="#demo2" style="color:#525252;font-size:16px;font-weight:bold;" data-toggle="collapse">My Account</a>
                                </div>
                                <div id="demo2" class="collapse">
                                    <div class="item-d" style="margin-left:20px;">
                                        <c:choose>
                                            <c:when test="${sessionScope.custom != null}">
                                                <a class="dropdown-item" href="profile.jsp" style="font-size:14px;">My Account</a>
                                                <a class="dropdown-item" href="ShowFavorite" style="font-size:14px;">Favorite</a>
                                                <a class="dropdown-item" href="#" style="font-size:14px;">My Order</a>
                                                <a class="dropdown-item" href="Logout" style="font-size:14px;">Logout</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="dropdown-item" href="Login" style="font-size:14px;">Login</a>
                                                <a class="dropdown-item" href="Register" style="font-size:14px;">Register</a>
                                                <a class="dropdown-item" href="ShowFavorite" style="font-size:14px;">Favorite</a>
                                            </c:otherwise>    
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                            <div class="department">
                                <div>
                                    <a class="dropdown-item dropdown-toggle" href="#demo" style="color:#525252;font-size:16px;font-weight:bold;" data-toggle="collapse">Department</a>
                                </div>
                                <div id="demo" class="collapse">
                                    <div class="item-d" style="margin-left:20px;">
                                        <a class="dropdown-item" href="#" style="font-size:14px;">Classic</a>
                                        <a class="dropdown-item" href="#" style="font-size:14px;">Country</a>
                                        <a class="dropdown-item" href="#" style="font-size:14px;">Dance/Soul</a>
                                        <a class="dropdown-item" href="#" style="font-size:14px;">J-Pop</a>
                                        <a class="dropdown-item" href="#" style="font-size:14px;">Jazz</a>
                                        <a class="dropdown-item" href="#" style="font-size:14px;">K-Pop</a>
                                        <a class="dropdown-item" href="#" style="font-size:14px;">Pop-Rock</a>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <a class="dropdown-item" href="ProductList" style="color:#525252;font-size:16px;font-weight:bold;">Contact</a>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card-body" style="background-color:#f5f5f5;">
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
            <div class="card-footer" style="background-color:#dddede;">
                <div class="row">
                    <div class="col-sm-4">
                        <div style="text-align:left;margin:5px;">
                            <div style="margin:10px;text-align:left;">
                                <a href="ProductList" class="btn btn-outline-info">Back to shopping</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-8">
                        <div class="d-flex align-items-center" style="text-align:right;margin:5px;">
                            <div class="" style="margin:5px;line-height:17px;width:100%">
                                Total price: <b><fmt:formatNumber value="${cart.totalPrice}" pattern="#,##0.00"/>฿</b><br>
                                <small style="font-size:11px;color:#950514;"> + Shipping cost 490฿</small>
                            </div>
                            <div class="d-inline-block" style="margin-top:2px;margin-right:5px;">
                                <a href="Payment?productid=${p.product.productid}" class="btn btn-success">Checkout</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
