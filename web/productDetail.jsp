<%-- 
    Document   : product
    Created on : Nov 6, 2018, 6:39:22 PM
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
                <div class="row">
                    <div class="col-sm-12" style="margin-bottom: 1%;margin-top: 2%;">Album Detail/ <b>${product.productname}</b></div>
                </div>
                <div class="card">
                    <div class="row">
                        <aside class="col-sm-5 border-right" style="padding-right:0 !important;">
                            <article class="gallery-wrap"> 
                                <div class="img-big-wrap">
                                    <div><a href="images/${product.productid}.jpg"><img src="images/${product.productid}.jpg"></a></div>
                                </div> <!-- slider-product.// -->
                            </article> <!-- gallery-wrap .end// -->
                        </aside>
                        <aside class="col-sm-7">
                            <article class="card-body p-5">
                                <h3 class="title mb-3">${product.productname}</h3>

                                <p class="price-detail-wrap"> 
                                    <span class="price h3 text-warning"> 
                                        <span class="num">${product.price}</span><span class="currency"> ฿</span>
                                    </span> 
                                </p> <!-- price-detail-wrap .// -->
                                <dl class="item-property">
                                    <dt>Description</dt>
                                    <dd class="des"><p>Artist: ${product.artist}
                                            <br>Genre: ${product.genre}
                                            <br>Release Date: <fmt:formatDate value="${product.releasedate}" pattern="yyyy-MM-dd" />
                                        </p></dd>
                                </dl>
                                <dl class="param param-feature">
                                    <dt>Delivery</dt>
                                    <dd class="des">EMS World Wide 490฿</dd>

                                </dl>  <!-- item-property-hor .// -->
                                <hr>
                                <div class="row" style="margin-top:15px;">
                                    <div class="col-sm-12" >         
                                        <form action="FavoriteDetail" method="post">
                                            <input type="hidden" value="${product.productid}" name="productid"/>
                                            <button type="submit" class="btn btn-primary btn-danger">
                                                <i class="fas fa-heart"></i> &nbsp;Favorite
                                            </button>

                                        </form>
                                    </div> 
                                </div>

                                <div class="row" style="margin-top:7px;">            
                                    <div class="col-sm-12">
                                        <form action="AddItemToCartDetail" method="post">
                                            <input type="hidden" value="${product.productid}" name="productid"/>
                                            <button type="submit" class="btn btn-primary">
                                                <i class="fas fa-shopping-cart"></i> &nbsp;Add To Cart
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </article> <!-- card-body.// -->
                        </aside> <!-- col.// -->
                    </div> <!-- row.// -->
                </div> <!-- card.// -->

                <div class="tracklist">
                    <div class="tracklist-head">
                        <div class="row">
                            <div class="col-sm-12" style="margin-bottom: 1%;margin-top: 2%;"><span class="tl-head">Tracklist</span></div>
                        </div> <!--row-->
                    </div>
                    <hr class="my-5">
                    <div class="tracklist-detail">
                        <div class="row">
                            <c:forEach items="${product.tracklistList}" var="p">
                            <div class="col-sm-5">
                                <audio controls id="player" style="width:450px;">
                                    <source src="music/${product.productid}_${p.tracklistPK.songname}.mp3" type="audio/mpeg">
                                </audio>
                            </div>
                            <div class="col-sm-7 d-flex align-items-center">
                                <p><b>${p.tracklistPK.songname}</b></p>
                            </div>
                            </c:forEach>
                        </div> <!--row-->
<!--                        <div class="row">
                            <div class="col-sm-5">
                                <audio controls id="player" style="width:450px;">
                                    <source src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/15309/test.mp3" type="audio/mpeg">
                                </audio>
                            </div>
                            <div class="col-sm-7 d-flex align-items-center">
                                <p><b>${product.productname}</b></p>
                            </div>
                        </div> row-->
                    </div><!--tracklist-detail-->
                </div> <!--tracklist-->


                <div class="review">
                    <div class="review-head">
                        <div class="row">
                            <div class="col-sm-12" style="margin-bottom: 1%;margin-top: 2%;"><span class="tl-head">Review</span></div>
                        </div> <!--row-->
                    </div>
                    <hr class="my-5">
                    <div class="review-detail">
                        <div class="row">
                            <div class="col-sm-2">
                                <p><fmt:formatDate value="${product.releasedate}" pattern="yyyy-MM-dd" /></p>
                            </div>
                            <div class="col-sm-3">
                                <p>${custom.username}</p>
                            </div>
                            <div class="col-sm-7">
                                <p>${product.productname}888888888888ddsfdmsflmdslm</p>
                            </div>
                        </div> <!--row-->
                    </div><!--tracklist-detail-->
                </div> <!--tracklist-->

                <div class="back-btn" style="margin-bottom:20px;margin-top:70px;">
                    <div class="row">
                        <div class="col-sm-12">
                            <a href="ProductList"><button type="button" class="btn btn-light btn-sm">Back</button></a>
                        </div>
                    </div> <!--row-->
                </div> <!--tracklist-->
            </div><!--container.//-->
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
            var
                    $player = $(".audio-ctrl"),
                    $transcript = $(".audio-transcript");

            $(".audio-ctrl").each(function () {

                $(this).attr("aria-pressed", "false");

                $(this).click(function (e) {
                    e.preventDefault();

                    $player.attr("aria-pressed", "false");

                    if ($(this).hasClass("play")) {
                        $(this).attr("aria-pressed", "true");
                        // 2 sec delay to allow screen reader
                        // to read button state
                        setTimeout(function () {
                            $("#player")[0].play();
                        }, 2000);
                    }
                    if ($(this).hasClass("pause")) {
                        $("#player")[0].pause();
                        $(this).attr("aria-pressed", "true");
                    }
                    if ($(this).hasClass("read")) {
                        $transcript.removeClass("visually-hidden").focus();
                    }
                });
            });
        </script>
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    </body>
</html>
