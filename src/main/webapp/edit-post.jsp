<%@page import="com.blog.model.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.blog.model.Post"%>
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
 	if ( name.isEmpty() ) {
 		response.sendRedirect("login");
 		return;
 	}
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Blog Java - Login</title>
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
	
		Post post = (Post) request.getAttribute("post");
	%>

</head>
<body>

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
	    <div class="col col-lg-4">
	      <h1 class="py-4 text-center">Edit Post</h1>
				<form name="formLogin" action="updatepost" method="post">
				
				  <div class="mb-3">
					  <label for="titulo" class="form-label">Título</label>
					  <input type="hidden" class="form-control" id="id" name="id" value="<%=post.getId()%>">
					  <input type="text" class="form-control" id="titulo" name="titulo" value="<%=post.getTitle()%>">
					</div>
					
					<div class="mb-3">
					  <label for="subtitulo" class="form-label">Subtítulo</label>
					  <input type="text" class="form-control" id="subtitulo" name="subtitulo" placeholder="Opcional" value="<%= post.getSubtitle()%>">
					</div>
				
					<div class="mb-3">
						<label for="categoria" class="form-label">Categoria</label>
						<select id="categoria" name="categoria" class="form-select">
							<%
								ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categorias");
								for (Category category : categories) {
							%>
						  	<option <%= category.getId() == post.getCategory() ? "selected" : "" %> value="<%=category.getId() %>"><%= category.getDescription() %></option>
						  <% } %>
						</select>
					</div>
					
					<div class="mb-3">
					  <label for="texto" class="form-label">Post</label>
					  <textarea class="form-control" id="texto" name="texto" rows="3"><%=post.getText()%></textarea>
					</div>
					
				  <div class="d-grid gap-2 col-4 mx-auto pt-4">
					  <button class="btn btn-primary" type="submit">Atualizar</button>
					</div>
				</form>
	    </div>
		</div>
	</div>
	
</body>
</html>
