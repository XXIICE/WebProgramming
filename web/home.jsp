<%-- 
    Document   : home
    Created on : Nov 6, 2018, 3:43:57 PM
    Author     : ariya boonchoo
--%>

<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
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
                    <li data-target="#carouselExampleIndicators" data-slide-to="3"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="4"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="5"></li>
                </ol>
                <div class="carousel-inner" role="listbox">
                    <!-- Slide One - Set the background image for this slide in the line below -->
                    <div class="carousel-item active" style="background-image: url('images/01.jpg')">
                        <div class="carousel-caption d-none d-md-block">
                            <h3>CLASSIC</h3>
                            <p>Heard melodies are sweet, but those unheard are sweeter. Therefore, ye soft pipes, play on.</p>
                        </div>
                    </div>
                    <!-- Slide Two - Set the background image for this slide in the line below -->
                    <div class="carousel-item" style="background-image: url('images/02.jpg')">
                        <div class="carousel-caption d-none d-md-block">
                            <h3>COUNTRY</h3>
                            <p>True country music is honesty, sincerity, and real life to the hilt. </p>
                        </div>
                    </div>
                    <!-- Slide Three - Set the background image for this slide in the line below -->
                    <div class="carousel-item" style="background-image: url('images/03.jpg')">
                        <div class="carousel-caption d-none d-md-block">
                            <h3>DANCE / SOUL</h3>
                            <p>Longevity and reaching and touching people on a human level - and that's never going to get lost.</p>
                        </div>
                    </div>
                    <!-- Slide Three - Set the background image for this slide in the line below -->
                    <div class="carousel-item" style="background-image: url('images/04.jpg')">
                        <div class="carousel-caption d-none d-md-block">
                            <h3>J-POP</h3>
                            <p>One good thing about J-Pop. When it hits you, you feel no pain.</p>
                        </div>
                    </div>
                    <!-- Slide Three - Set the background image for this slide in the line below -->
                    <div class="carousel-item" style="background-image: url('images/05.jpg')">
                        <div class="carousel-caption d-none d-md-block">
                            <h3>K-POP</h3>
                            <p>You can just enjoy music without understanding it, muusic is a universal language.</p>
                        </div>
                    </div>
                    <!-- Slide Three - Set the background image for this slide in the line below -->
                    <div class="carousel-item" style="background-image: url('images/06.jpg')">
                        <div class="carousel-caption d-none d-md-block">
                            <h3>POP / ROCK</h3>
                            <p>Good fashion is like rock music: all anarchy and revolt.
                            </p>
                        </div>
                    </div>
                </div>
                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </header>


        <!--Main Navigation-->

        <!--Main layout-->
        <main class="mt-5">
            <div class="container">

                <!--Section: Best Features-->
                <section id="best-features" class="text-center">

                    <!-- Heading -->
                    <h2 class="mb-5 font-weight-bold">Imagine</h2>

                    <!--Grid row-->
                    <div class="row d-flex justify-content-center mb-4">

                        <!--Grid column-->
                        <div class="col-md-8">

                            <!-- Description -->
                            <p class="grey-text">...</p>

                        </div>
                        <!--Grid column-->

                    </div>
                    <!--Grid row-->

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

                        <!--Grid column-->
                        <div class="col-md-4 mb-1">
                            <i class="fa fa-heart fa-4x orange-text" style="color:#0061aa;"></i>
                            <h4 class="my-4 font-weight-bold">Happiness</h4>
                            <p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit maiores nam,
                                aperiam minima
                                assumenda deleniti hic.</p>
                        </div>
                        <!--Grid column-->

                        <!--Grid column-->
                        <div class="col-md-4 mb-1">
                            <i class="fa fa-archive fa-4x orange-text" style="color:#0061aa;"></i>
                            <h4 class="my-4 font-weight-bold">Shipping</h4>
                            <p class="grey-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reprehenderit maiores nam,
                                aperiam minima
                                assumenda deleniti hic.</p>
                        </div>
                        <!--Grid column-->

                    </div>
                    <!--Grid row-->

                </section>

                <hr class="my-5">

                <section id="examples" class="text-center">

                    <!-- Heading -->
                    <div class="container-product">    
                        <h2 class="mb-5 font-weight-bold">New Release</h2>
                        <div class="row">
                            <div class="col-md-12">
                                <div id="carouselExample" class="carousel slide" data-ride="carousel" data-interval="0">
                                    <!-- Carousel indicators -->
                                    <ol class="carousel-indicators">
                                        <li data-target="#carouselExample" data-slide-to="0" class="active"></li>
                                        <li data-target="#carouselExample" data-slide-to="1"></li>
                                    </ol>     
                                    <!-- Wrapper for carousel items -->



                                    <div class="carousel-inner">

                                        <div class="item carousel-item active" id="slide-product">

                                            <div class="row">
                                                <c:forEach items="${productDiv1}" var="p" varStatus="vs" > 
                                                    <div class="col-sm-3">
                                                        <div class="thumb-wrapper">
                                                            <div class="img-box">
                                                                <a href="GetProductDetail?productid=${p.productid}"><img src="images/${p.productid}.jpg" class="img-responsive img-fluid" alt="cover"></a>
                                                            </div>
                                                            <div class="thumb-content">
                                                                <h4><a href="GetProductDetail?productid=${p.productid}">${p.productname}</a></h4>
                                                                <p class="item-price"><span>${p.price} ฿</span></p>
                                                                <!--<div class="d-flex justify-content-center">-->
<!--                                                                    <form action="AddItemToCart" method="post">
                                                                        <input type="hidden" value="${p.productid}" name="productid"/>
                                                                        <button type="submit" class="btn btn-primary">Add To Cart</button>
                                                                    </form>
                                                                    <form action="FavoriteDetail" method="post">
                                                                        <input type="hidden" value="${p.productid}" name="productid"/>
                                                                        <button type="submit" id="fav" class="btn btn-danger"><i class="far fa-heart" style="color:#dd0505;"></i></button>
                                                                    </form>-->
                                                                <!--</div>-->
                                                            </div>                      
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>

                                        <div class="item carousel-item" id="slide-product">

                                            <div class="row">
                                                <c:forEach items="${productDiv2}" var="p" varStatus="vs" > 
                                                    <div class="col-sm-3">
                                                        <div class="thumb-wrapper">
                                                            <div class="img-box">
                                                                <a href="GetProductDetail?productid=${p.productid}"><img src="images/${p.productid}.jpg" class="img-responsive img-fluid" alt="cover"></a>
                                                            </div>
                                                            <div class="thumb-content">
                                                                <h4><a href="GetProductDetail?productid=${p.productid}">${p.productname}</a></h4>
                                                                <p class="item-price"><span>${p.price} ฿</span></p>
<!--                                                                <form action="AddItemToCart" method="post">
                                                                    <input type="hidden" value="${p.productid}" name="productid"/>
                                                                    <input type="submit" class="btn btn-primary" value="Add To Cart">
                                                                </form>-->
                                                            </div>                      
                                                        </div> 
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>

                                    </div>
                                    <!-- Carousel controls -->

                                    <a class="carousel-control-prev" href="#carouselExample" role="button" data-slide="prev">
                                        <i class="fa fa-chevron-left fa-lg text-muted"></i>
                                        <span class="sr-only">Previous</span>
                                    </a>
                                    <a class="carousel-control-next text-faded" href="#carouselExample" role="button" data-slide="next">
                                        <i class="fa fa-chevron-right fa-lg text-muted"></i>
                                        <span class="sr-only">Next</span>
                                    </a>

                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                <hr class="my-5">
            </div>
        </main>    
        <h2><a href="ProductList">See All Product</a></h2>
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

        <!-- Bootstrap core JavaScript -->
        <script type="text/javascript">
            $('#carouselExample').on('slide.bs.carousel', function (e) {
                var $e = $(e.relatedTarget);
                var idx = $e.index();
                var itemsPerSlide = 4;
                var totalItems = $('.carousel-item').length;

                if (idx >= totalItems - (itemsPerSlide - 1)) {
                    var it = itemsPerSlide - (totalItems - idx);
                    for (var i = 0; i < it; i++) {
                        // append slides to end
                        if (e.direction == "left") {
                            $('.carousel-item').eq(i).appendTo('.carousel-inner');
                        } else {
                            $('.carousel-item').eq(0).appendTo('.carousel-inner');
                        }
                    }
                }
            });
            $('#carouselExample2').on('slide.bs.carousel', function (e) {

                var $e = $(e.relatedTarget);
                var idx = $e.index();
                var itemsPerSlide = 4;
                var totalItems = $('.carousel-item').length;

                if (idx >= totalItems - (itemsPerSlide - 1)) {
                    var it = itemsPerSlide - (totalItems - idx);
                    for (var i = 0; i < it; i++) {
                        // append slides to end
                        if (e.direction == "left") {
                            $('.carousel-item').eq(i).appendTo('.carousel-inner');
                        } else {
                            $('.carousel-item').eq(0).appendTo('.carousel-inner');
                        }
                    }
                }
            });
        </script>
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
