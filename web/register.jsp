<%-- 
    Document   : login
    Created on : Nov 2, 2018, 10:14:19 AM
    Author     : INT303
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
    <body id="LoginForm">
        <div class="container">
            <div class="login-form">
                <div class="main-div-regis">
                    <div class="panel">
                        <h2>Register</h2>
                        <p>Please enter your information</p>
                    </div>

                    <form action="Register" method="post">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Username" name="username" required>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <div class="form-group">
                                    <input type="password" class="form-control" placeholder="Password" name="password" required>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="form-group">
                                    <input type="password" class="form-control" placeholder="Retype Password" name="retypepassword" required>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="First Name" name="firstname" required>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Last Name" name="lastname" required>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <input type="email" class="form-control" placeholder="Email" name="email" required>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Address" name="address" required>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Credit Card Number" name="creditNo" maxlength="16" required/>
                        </div>
                        <div class="agree">
                            <input type="checkbox" required> &nbsp;I accept the Terms of Service.
                        </div>
                        <button type="submit" class="btn btn-primary">Register</button>
                        <div class="messagere" style="font-size:12px;color:red;margin-top:10px;">
                            <p>${messagere}</p>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
