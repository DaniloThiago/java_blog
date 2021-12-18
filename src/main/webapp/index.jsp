<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	Cookie[] cookies = request.getCookies();
 	String name = "";
 	String email = "";
 	if (cookies != null) {
  	for (int i = 0; i < cookies.length; i++) {
   		Cookie cookie = cookies[i];
   		if (cookie.getName().equals("name")) {
  			name = cookie.getValue();
   		} else if (cookie.getName().equals("email")) {
  			email = cookie.getValue();
   		}
  	}
 	}
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Blog Java</title>
<link rel="stylesheet" type="text/css" href="assets/bootstrap-5.1.3/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="assets/css/style.css" />
</head>
<body>


	<nav class="navbar navbar-dark bg-dark">
	  <div class="container-fluid">
	    <a class="navbar-brand">Blog Java</a>
	    <div class="d-flex">
    		<% if(name.isEmpty()) { %>
	      	<a href="login"><button class="btn btn-outline-success">Login</button></a>
	      <% } else { %>
	       	<span class="navbar-text me-4">
			      Olá <%= name %>
			    </span>
	      	<a href="logout"><button class="btn btn-outline-danger">Logout</button></a>
	      <% }  %>
	    </div>
	  </div>
	</nav>
	
	<script src="assets/bootstrap-5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>