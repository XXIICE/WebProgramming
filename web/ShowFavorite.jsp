<%-- 
    Document   : ShowFavorite
    Created on : Nov 15, 2018, 5:06:50 PM
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

    </head>
    <body>
        <jsp:include page="include/Header.jsp"/>

        <main class="mt-5">
            <div class="container">
                <section id="examples" class="text-center" style="margin-top:9%;">

                    <!-- Heading -->
                    <div class="container-product">    
                        <h3 class="mb-5 font-weight-bold">Favorite</h3>
                        <hr class="my-5">
                        <div class="row">
                            <div class="col-md-12">
                                <div id="carouselExample" class="carousel slide" data-ride="carousel" data-interval="0">

                                    <!-- Wrapper for carousel items -->

                                    <div class="carousel-inner">

                                        <div class="item carousel-item active" id="slide-product">

                                            <div class="row">
                                                <c:set var="Items" value="${sessionScope.fav.lineItems}"/>
                                                <c:forEach items="${fav.lineItems}" var="f" varStatus="vs"> 
                                                    <div class="col-sm-3">
                                                        <div class="thumb-wrapper">
                                                            <div class="img-box">
                                                                <a href="GetProductDetail?productid=${f.product.productid}"><img src="images/${f.product.productid}.jpg" class="img-responsive img-fluid" alt="cover"></a>
                                                            </div>
                                                            <div class="thumb-content">
                                                                <h4><a href="GetProductDetail?productid=${f.product.productid}">${f.product.productname}</a></h4>
                                                                <p class="item-price"><span>${f.salePrice} ฿</span></p>
                                                                <div class="d-flex justify-content-center">
                                                                    <form action="AddItemToCart" method="post">
                                                                        <input type="hidden" value="${f.product.productid}" name="productid"/>
                                                                        <input type="submit" class="btn btn-primary" value="Add To Cart">
                                                                    </form>
                                                                </div>
                                                            </div>                      
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                
                <div class="back-btn" style="margin-bottom:20px;margin-top:70px;">
                    <hr>
                    <div class="row">
                        <div class="col-sm-12">
                            <a href="productDiv1"><button type="button" class="btn btn-light btn-sm">Back</button></a>
                        </div>
                    </div> <!--row-->
                </div>
            </div>
        </main>

        <!-- Footer -->
        <footer class="py-5 bg-dark">
            <div class="container">
                <!--            <p class="m-0 text-center text-white">Copyright &copy; Your Website 2018</p>-->
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
