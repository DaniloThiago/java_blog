<!DOCTYPE html>
<%@page import="com.blog.model.Author"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Blog Java - Edit Author</title>
<link rel="stylesheet" type="text/css" href="assets/bootstrap-5.1.3/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="assets/css/style.css" />

	<%
		if ( request.getAttribute("error") != null ) {
	%>
		<script type="text/javascript">
			alert("<%= request.getAttribute("error") %>");
		</script>
	<%
		}
	%>

</head>
<body>

<% 
	Author author = (Author) request.getAttribute("author");
%>

	<nav class="navbar navbar-dark bg-dark">
	  <div class="container-fluid">
	    <a class="navbar-brand">Blog Java</a>
	    <div class="d-flex">	    	
	      <a href="/Blog"><button class="btn btn-outline-danger">Voltar</button></a>
	    </div>
	  </div>
	</nav>

	<div class="container">
	  <div class="row justify-content-md-center">
	    <div class="col col-lg-4 py-4">
	      
				<form action="updateAuthor" method="get">
				<input type="hidden" name="id" value="<%= author.getId() %>">
				  <div class="mb-3">
					  <label class="form-label">Name:</label>
					  <input type="text" name="name" class="form-control" value="<%= author.getName() %>" id="formControlName">
					</div>
				  <div class="mb-3">
					  <label for="formControlEmail" class="form-label">Email:</label>
					  <input type="email" name="email" class="form-control" value="<%= author.getEmail() %>" id="formControlEmail">
					</div>
					
					<div class="mb-3">
					  <label for="formControPassword" class="form-label">Password:</label>
					  <input type="text" name="password" class="form-control" value="<%= author.getPassword() %>" id="formControPassword">
					</div>

				  <div class="d-grid gap-2 col-4 mx-auto pt-4">
					  <button class="btn btn-primary" type="submit">Edit</button>
					</div>
				</form>
	    </div>
		</div>
	</div>
	
</body>
</html>