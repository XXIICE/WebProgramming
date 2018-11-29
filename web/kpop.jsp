<%-- 
    Document   : kpop
    Created on : Nov 29, 2018, 4:15:19 PM
    Author     : waran
--%>
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
        <header>
            <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner" role="listbox">
                    <!-- Slide One - Set the background image for this slide in the line below -->
                    <div class="carousel-item active" style="background-image: url('images/banner-kpop-01.jpg')">
                    </div>
                    <!-- Slide Two - Set the background image for this slide in the line below -->
                    <div class="carousel-item" style="background-image: url('images/banner-kpop-02.jpg')">
                    </div>
                    <!-- Slide Three - Set the background image for this slide in the line below -->
                    <div class="carousel-item" style="background-image: url('images/banner-kpop-03.jpg')">
                    </div>
                </div>
            </div>
        </header>

        <!--Main layout-->
        <main class="mt-5">
            <div class="container">

                <!--Section: Best Features-->
                <section id="best-features" class="text-center">

                    <!-- Heading -->
                    <h2 class="mb-5 font-weight-bold">K-POP</h2>
                    <hr class="my-5">
                    <!--Grid row-->
                    <div class="row">

                        <!--Grid column-->
                        <div class="col-md-4 mb-5">
                            <i class="fa fa-music fa-4x orange-text" style="color:#0061aa;"></i>
                            <h4 class="my-4 font-weight-bold">Music</h4>
                            <p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit maiores nam,
                                aperiam minima
                                assumenda deleniti hic.</p>
                        </div>
                        <!--Grid column-->

                        <c:forEach items="${productList}" var="p" varStatus="vs" > 
                            <div class="col-md-4 mb-5">
                                <div class="thumb-wrapper">
                                    <div class="img-box">
                                        <a href="GetProductDetail?productid=${p.productid}"><img src="images/${p.productid}.jpg" class="img-responsive img-fluid" alt="cover"></a>
                                    </div>
                                    <div class="thumb-content">
                                        <h4><a href="GetProductDetail?productid=${p.productid}">${p.productname}</a></h4>
                                        <p class="item-price"><span>${p.price} ฿</span></p>
                                        <div class="d-flex justify-content-center">
                                            <form action="AddItemToCart" method="post">
                                                <input type="hidden" value="${p.productid}" name="productid"/>
                                                <input type="submit" class="btn btn-primary" value="Add To Cart">
                                            </form>
                                            <form action="FavoriteDetail" method="post">
                                                <input type="hidden" value="${p.productid}" name="productid"/>
                                                <button type="submit" id="fav" class="btn btn-danger"><i class="far fa-heart" style="color:#dd0505;"></i></button>
                                            </form>
                                        </div>
                                    </div>                      
                                </div> 
                            </div>
                        </c:forEach>


                        <!--                        Grid column
                                                <div class="col-md-4 mb-1">
                                                    <i class="fa fa-heart fa-4x orange-text" style="color:#0061aa;"></i>
                                                    <h4 class="my-4 font-weight-bold">Happiness</h4>
                                                    <p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit maiores nam,
                                                        aperiam minima
                                                        assumenda deleniti hic.</p>
                                                </div>
                                                Grid column
                        
                                                Grid column
                                                <div class="col-md-4 mb-1">
                                                    <i class="fa fa-archive fa-4x orange-text" style="color:#0061aa;"></i>
                                                    <h4 class="my-4 font-weight-bold">Shipping</h4>
                                                    <p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit maiores nam,
                                                        aperiam minima
                                                        assumenda deleniti hic.</p>
                                                </div>
                                                Grid column-->

                    </div>
                    <!--Grid row-->

                </section>
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
