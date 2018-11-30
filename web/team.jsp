<%-- 
    Document   : favorite
    Created on : Nov 27, 2018, 8:41:17 PM
    Author     : waran
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
    <body>
        <jsp:include page="include/Header.jsp"/>

        <main class="mt-5" style="margin-bottom: 5%;">
            <div class="container">
                <section id="examples" class="text-center" style="margin-top:9%;">

                    <!-- Heading -->
                    <div class="card">
                        <div class="card-body">
                            <!-- PRODUCT -->
                            <div class="row">
                                <div class="col-12">
                                    <div class="text-md-left" style="margin:5%;">
                                        <h3>Contact us</h3>
                                        <p>126 Pracha Uthit Rd.,<br>
                                            Bang Mod, Thung Khru,<br>
                                            Bangkok 10140,<br>
                                            Thailand<br>
                                            +66 2470 8000<br>
                                            info@imagine.com</p>

                                        <p>Office Hours: Mondays to Fridays, 10:00 - 17:00<br>
                                        Our office do not operate on weekends, so we will respond to your emails on the next business day, Monday.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- END PRODUCT -->
                    </div>
                </section>
                <hr class="my-5">
                <div class="text-md-left">
                    <a href="productDiv1"><button class="btn btn-light btn-sm">Back</button></a>
                </div>
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
