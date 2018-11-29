<%-- 
    Document   : Header
    Created on : Aug 10, 2018, 3:12:05 PM
    Author     : INT303
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top"> 
    <div class="container">
        <a class="navbar-brand" href="ProductList"><img src="images/logo-white.png" height="25px" class="imagine"></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="ProductList">Home
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Department</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="#">Classic</a>
                        <a class="dropdown-item" href="#">Country</a>
                        <a class="dropdown-item" href="#">Dance/Soul</a>
                        <a class="dropdown-item" href="#">J-Pop</a>
                        <a class="dropdown-item" href="#">Jazz</a>
                        <a class="dropdown-item" href="#">K-Pop</a>
                        <a class="dropdown-item" href="#">Pop-Rock</a>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Contact</a>
                </li>             
                <li class="nav-item">
                    <a class="nav-link" href="ShowCart">
                        <c:choose>
                            <c:when test="${cart!=null}">
                                <img src="images/cart.png" width="17px" class="cart">
                                <span class="outer">
                                    <svg viewBox="0 0 20 20" preserveAspectRatio="xMinYMin meet">
                                    <g>
                                    <circle r="50%" cx="50%" cy="50%" class="circle-back" />
                                    <text x="50%" y="50%" text-anchor="middle" dy="0.3em">${cart.totalQuantity}</text>
                                    </g>
                                    </svg> 
                                </span>
                            </c:when>
                            <c:otherwise>
                                <img src="images/cart.png" width="17px" class="cart">
                            </c:otherwise>    
                        </c:choose>
                    </a>
                </li>

                <!-- Dropdown -->
                <li class="nav-item dropdown" id="nav-user">
                    <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                        <c:choose>
                            <c:when test="${sessionScope.custom != null}">
                                <img src="images/man-user.png" width="15px" class="man-user">${sessionScope.custom.username}
                            </c:when>
                            <c:otherwise>
                                <img src="images/man-user.png" width="15px" class="man-user">Guest
                            </c:otherwise>    
                        </c:choose>
                    </a>
                    <div class="dropdown-menu">

                        <c:choose>
                            <c:when test="${sessionScope.custom != null}">
                                <a class="dropdown-item" href="profile.jsp">My Account</a>
                                <a class="dropdown-item" href="ShowFavorite">Favorite</a>
                                <a class="dropdown-item" href="#">My Order</a>
                                <a class="dropdown-item" href="Logout">Logout</a>
                            </c:when>
                            <c:otherwise>
                                <a class="dropdown-item" href="Login">Login</a>
                                <a class="dropdown-item" href="Register">Register</a>
                                <a class="dropdown-item" href="ShowFavorite">Favorite</a>
                            </c:otherwise>    
                        </c:choose>
                    </div>
                </li>
            </ul>
            <form action="Search" class="form-inline my-2 my-lg-0">
                <input class="form-control form-control-sm mr-sm-2" type="search" name="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-sm btn-outline-secondary my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </div>  
</nav>
