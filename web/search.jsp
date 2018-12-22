<%-- 
    Document   : search
    Created on : Nov 28, 2018, 11:14:22 PM
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
                    <div class="col-sm-12 text-sm-center" style="margin-bottom:1%;margin-top:4%;margin-bottom:4%;font-size:18px;">Search: <b>${msg}</b></div>
                </div>
                <c:forEach items="${productList}" var="p" varStatus="vs" >

                    <div class="card">
                        <div class="row">
                            <aside class="col-sm-5 border-right" style="padding-right:0 !important;">
                                <article class="gallery-wrap"> 
                                    <div class="img-big-wrap">
                                        <div><a href="images/${p.productid}.jpg"><img src="images/${p.productid}.jpg"></a></div>
                                    </div> <!-- slider-p.// -->
                                </article> <!-- gallery-wrap .end// -->
                            </aside>
                            <aside class="col-sm-7">
                                <article class="card-body p-5">
                                    <h3 class="title mb-3"><a href="GetProductDetail?productid=${p.productid}">${p.productname}</a></h3>

                                    <p class="price-detail-wrap"> 
                                        <span class="price h3 text-warning"> 
                                            <span class="num">${p.price}</span><span class="currency"> ฿</span>
                                        </span> 
                                    </p> <!-- price-detail-wrap .// -->
                                    <dl class="item-property">
                                        <dt>Description</dt>
                                        <dd class="des"><p>Artist: ${p.artist}
                                                <br>Genre: ${p.genre}
                                        <br>Release Date: <fmt:formatDate value="${p.releasedate}" pattern="yyyy-MM-dd" />
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
                                                <input type="hidden" value="${p.productid}" name="productid"/>
                                                <button type="submit" class="btn btn-primary btn-danger">
                                                    <i class="fas fa-heart"></i> &nbsp;Favorite
                                                </button>

                                            </form>
                                        </div> 
                                    </div>

                                    <div class="row" style="margin-top:7px;">            
                                        <div class="col-sm-12">
                                            <form action="AddItemToCartDetail" method="post">
                                                <input type="hidden" value="${p.productid}" name="productid"/>
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
                    <hr class="my-5">
                </c:forEach>
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
        <script>
            $(document).ready(function () {
                $('#example').DataTable();
            });

        </script>
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
