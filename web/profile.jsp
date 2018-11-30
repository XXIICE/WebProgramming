<%-- 
    Document   : profile
    Created on : Nov 16, 2018, 9:53:21 AM
    Author     : ariya boonchoo
--%>

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
        <main class="mt-5" style="margin-bottom: 5%;">
            <div class="container">
                <section id="examples" class="text-center" style="margin-top:9%;">

                    <!-- Heading -->
                    <div class="row">
                        <div class="col-sm-12 text-md-left" style="margin-bottom:1%;margin-top:2%;font-size:25px;"><b>Profile</b></div>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <!-- PRODUCT -->
                            <div class="row">
                                <div class="col-2">
                                    <div class="text-md-left" style="margin:5%;"><img src="images/user.png" width="100px;"></div>
                                </div>
                                <div class="col-3 text-md-left">
                                    <strong style="font-size:18px;">Username</strong><br><span style="font-size:14px;">${custom.username}</span>
                                </div>
                                <div class="col-3 text-md-left">
                                    <strong style="font-size:18px;">Name</strong><br><span style="font-size:14px;">${custom.firstname}&nbsp;&nbsp;&nbsp; ${custom.lastname}</span>
                                </div>  
                                <div class="col-2 text-md-left">
                                    <c:if test="${fav!=null}">
                                        <a href="ShowFavorite"><img src="images/heart-i.png" width="50px;"></a>
                                        </c:if>
                                </div>  
                                <div class="col-2 text-md-left">
                                    <a href="order.jsp"><img src="images/cart-i.png" width="50px;"></a>
                                </div>  
                            </div>
                            <div class="row">
                                <div class="col-2">

                                </div>
                                <div class="col-3 text-md-left">
                                    <strong style="font-size:18px;">E-mail</strong><br><span style="font-size:14px;">${custom.email}</span>
                                </div>
                                <div class="col-3 text-md-left">
                                    <strong style="font-size:18px;">Address</strong><br><span style="font-size:14px;">${custom.address}</span>
                                </div>  
                            </div>
                        </div>
                        <!-- END PRODUCT -->
                    </div>
                </section>
                <hr class="my-5">
                <div class="text-md-left">
                    <a href="productDiv1"><button class="btn btn-light btn-sm">Back</button></a>
                </div><br>
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
