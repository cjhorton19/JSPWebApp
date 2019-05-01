<%-- 
    Document   : header
    Created on : Apr 23, 2019, 4:27:54 PM
    Author     : curti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Earn Your Sleep</title>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
              <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
      <a class="navbar-brand" href="">Earn Your Sleep</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item active">
            <a class="nav-link" href="<c:url value='/'/>">Home
              <span class="sr-only">(current)</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/catalog/men'/>">Men</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/catalog/women'/>">Women</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/catalog/accessories'/>">Accessories</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/order/showCart'/>">Cart</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
    </body>
</html>
