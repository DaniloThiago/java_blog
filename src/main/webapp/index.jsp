<%@page import="com.blog.model.Post"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.blog.dao.PostDAO"%>
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
 	
	PostDAO postDAO = new PostDAO();
	ArrayList<Post> posts = postDAO.getPosts();
	postDAO.close();	
	
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
	<section class="container-fluid">
		<div class="row">
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
			      	<a href="newpost"><button class="btn btn-outline-success me-3">Criar Post</button></a>
			      	<a href="logout"><button class="btn btn-outline-danger">Logout</button></a>
			      <% }  %>
			    </div>
			  </div>
			</nav>
		</div>

			
		  
  	<div class="container">  		
		  <%
		  	if (posts == null) { %>
				  <div class="d-flex justify-content-center">
				  	<div class="row align-items-center mt-4">
					    <div class="col text-center">
								<h2>Sem Posts</h2>
					    </div>
					  </div>
				  </div>
		  <%
		  	} else {
		  %>
		  		<div class="row g-2 py-4">
						<% for (Post post : posts) { %>		  				
				  		<div class="col-6">	    
								<div class="card">
								  <div class="card-header">
								    <h4><%= post.getTitle() %></h4>
								  </div>
								  <div class="card-body">
								  	
								    <h5 class="card-title"><%= post.getSubtitle().isBlank() ? '-' : post.getSubtitle() %></h5>
								    
								    <div class="text-truncate card-text">
								    This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.
								    </div>
								    <p class="card-text"><small class="text-muted"><%= post.getDate() %></small></p>
								  </div>
								  <div class="d-flex justify-content-between align-items-center card-footer text-muted">
								    <div class="btn btn-info">
									    <%= post.getDadosCategory().getDescription() %>
								    </div>
								    <div class="text-end">
									    <%= post.getDadosAuthor().getName() %>
								    </div>	
								  </div>  
								</div>
					    </div>				    
					  <% } %>
				  </div>
		  <% } %>
  	</div>
 		
  
		

	</section>
	
	
	<script src="assets/bootstrap-5.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>