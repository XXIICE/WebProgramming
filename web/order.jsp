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

    <body style="background-color:#f3f3f3;">
        <jsp:include page="include/Header.jsp"/>

        <main class="mt-5">
            <div class="container">
                <section id="examples" class="text-center" style="margin-top:9%;">

                    <!-- Heading -->
                    <h3 class="mb-4 font-weight-bold" style="background-color:#e5e5e5;padding-top:10px;padding-bottom:10px;letter-spacing:5px;">Your Order</h3>

                    <div class="row">
                        <div class="col-sm-12 text-md-left" style="margin-bottom:1%;margin-top:2%;font-size:20px;"><b>Shipment Details</b></div>
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
                                <div class="col-sm-2 text-md-left">
                                    <strong></strong>
                                </div>    
                                <div class="col-sm-2">
                                    <strong>Quantity</strong>
                                </div>    
                                <div class="col-sm-2">
                                    <strong>Price</strong>
                                </div>    
                                <div class="col-sm-2">
                                    <strong>Total</strong>
                                </div> 
                                <div class="col-sm-1">
                                    <strong>Status</strong>
                                </div>
                            </div> 
                            <hr class="md-5">
                            <c:set var="Items" value="${sessionScope.order.lineItems}"/>
                            <c:forEach items="${order.lineItems}" var="p" varStatus="vs">
                                <div class="row" style="font-size:14px;">   
                                    <div class="col-sm-1">
                                        <strong class="align-self-center">${vs.count}</strong>
                                    </div>    
                                    <div class="col-sm-2 text-md-left">
                                        <a class="thumbnail pull-left" href="GetProductDetail?productid=${p.product.productid}"> <img class="media-object" src="images/${p.product.productid}.jpg" style="width: 150px;"> </a>
                                    </div>    
                                    <div class="col-sm-2">
                                        <div class="media-body text-md-left" style="line-height:20px;">
                                            <strong><a hhref="GetProductDetail?productid=${p.product.productid}" style="font-size:18px;">${p.product.productname}</a></strong><br>
                                            <span> by ${p.product.artist}</span><br>
                                            <span>Status: </span><span class="text-success"><strong>In Stock</strong></span>
                                        </div>
                                    </div>    
                                    <div class="col-sm-2">
                                        <p>${p.quantity}</p>
                                    </div>    
                                    <div class="col-sm-2">
                                        <p>${p.salePrice} ฿</p>
                                    </div>    
                                    <div class="col-sm-2">
                                        <p>${p.totalPrice} ฿</p>
                                    </div>  
                                    <div class="col-sm-1">
                                        <p>Paid</p>
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

                    <br>
                    <%--<c:forEach items="${order.lineItems}" var="p" varStatus="vs">--%>
                    <!--                        <div class="row">
                                                <div class="col-sm-12">
                                                    <div class="d-flex align-items-center" style="text-align:right;margin:5px;">
                                                        <div class="" style="margin:5px;line-height:17px;width:100%">
                                                            <small style="font-size:11px;color:#950514;">Discount by ${custom.point} Point</small><br>
                                                            Total price: <b><fmt:formatNumber value="${p.totalPrice}" pattern="#,###.00"/>฿</b>
                                                        </div>
                                                        <div class="d-inline-block" style="margin-top:2px;margin-right:5px;">
                                                            <a href="ConfirmToPay?productid=${p.product.productid}" class="btn btn-success" style="padding-left:40px;padding-right:40px;">Buy</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>-->
                    <%--</c:forEach>--%>  
                    <br>
                </section>
            </div>
        </main>

    </table>

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
                    <span class="footer-about"><a href="#" style="font-size: 12px;color: #7e7e7e;">Contact us</a><br>
                        <a href="#" style="font-size: 12px;color: #7e7e7e;">Team</a></span>
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