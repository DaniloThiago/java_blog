package com.blog.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.bd.ConnectionFactory;
import com.blog.dao.AuthorDAO;
import com.blog.dao.CategoryDAO;
import com.blog.dao.PostDAO;
import com.blog.model.Author;
import com.blog.model.Category;
import com.blog.model.Post;


/**
 * Servlet implementation class BlogController
 */

@WebServlet(urlPatterns = {"/login", "/validar", "/logout", "/newpost", "/createpost", "/cadastrar", "/signup", "/edita-autor", "/updateAuthor", "/editpost", "/updatepost"})

public class BlogController extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BlogController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		if( action.equals("/login") ) {			
			response.sendRedirect("login.jsp");
		}
		
		else if(action.equals("/signup")) {
			response.sendRedirect("signup.jsp");
		}
		
		else if(action.equals("/updateAuthor")) {
			updateAuthor(request, response);
		}
		
		else if(action.equals("/edita-autor")) {
			selecionaAutor(request, response);
		}
		
		else if( action.equals("/newpost") ) {
			if(getCookie("id", request, response ) == null) {
				response.sendRedirect("login.jsp");
				return;
			}

			CategoryDAO categoryDAO = new CategoryDAO();
			ArrayList<Category> categories = categoryDAO.getCategories();
			categoryDAO.close();

			request.setAttribute("categorias", categories);			
			request.getRequestDispatcher("/new-post.jsp").forward(request, response);
		}
		
		else if( action.equals("/validar")) {
			validar(request, response);
		}
	
		else if( action.equals("/logout")) {
			Cookie cookie = null;
			Cookie[] cookies = null;
			cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					cookie = cookies[i];					
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
			response.sendRedirect("/Blog");

		} else if (action.equals("/editpost")) {
			String idpost = request.getParameter("id");
			
			PostDAO postDAO = new PostDAO();
			Post findPost = postDAO.find(Integer.parseInt(idpost));
			postDAO.close();
			
			CategoryDAO categoryDAO = new CategoryDAO();
			ArrayList<Category> categories = categoryDAO.getCategories();
			categoryDAO.close();

			request.setAttribute("post", findPost);
			request.setAttribute("categorias", categories);
			
			request.getRequestDispatcher("/edit-post.jsp").forward(request, response);
		} 
		
		else if( action.equals("/cadastrar")) {
			newAuthor(request, response);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();				
				
		if( action.equals("/validar")) {
			validar(request, response);
			return;
		}
		
		if( action.equals("/createpost")) {
			createPost(request, response);
			return;
		}
		
		if( action.equals("/editpost")) {
			doPost(request, response);
			return;
		}
		
		if( action.equals("/updatepost")) {
			updatePost(request, response);
			return;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void validar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Author author = new Author();
		
		String mail = request.getParameter("email");
		String pass = request.getParameter("password");

		if ( mail != null && pass != null && !mail.isEmpty() && !pass.isEmpty() ) {
			author.setEmail(mail);
			author.setPassword(pass);
			
			AuthorDAO authorDAO = new AuthorDAO();
			Author findAuthor = authorDAO.find(author);
			authorDAO.close();
			
			if(findAuthor != null) {
				int minute = 10;
				
				Cookie cookieId = new Cookie("id", String.valueOf(findAuthor.getId()));
				cookieId.setMaxAge(60*minute);
				response.addCookie(cookieId);

				Cookie cookieName = new Cookie("name",findAuthor.getName());
				cookieName.setMaxAge(60*minute);
				response.addCookie(cookieName);

				Cookie cookieEmail = new Cookie("email",findAuthor.getEmail());
				cookieEmail.setMaxAge(60*minute);
				response.addCookie(cookieEmail);
			
				response.sendRedirect("/Blog");
			} else {
				request.setAttribute("error", "Email/Password errado");
				request.getRequestDispatcher("/login.jsp").forward(request, response);	
			}
				
		} else {
			request.setAttribute("error", "Email ou Password em branco");
			request.getRequestDispatcher("/login.jsp").forward(request, response);	
		}
		
	}

	protected void createPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String titulo = request.getParameter("titulo");
		String subtitulo = request.getParameter("subtitulo");
		String texto = request.getParameter("texto");
		String categoria = request.getParameter("categoria");
		
		if ( titulo != null   && texto != null    && categoria != null
		 && !titulo.isEmpty() && !texto.isEmpty() && !categoria.isEmpty() ) {
			
			Cookie idAuthor = getCookie("id", request, response);
			
			if(idAuthor == null) {
				response.sendRedirect("login.jsp");
				return;
			}
			
			System.out.println();
			
			Post post = new Post();
			post.setAuthor(Integer.valueOf(idAuthor.getValue()));
			post.setCategory(Integer.valueOf(categoria));
			post.setTitle(titulo);
			post.setSubtitle(subtitulo);
			post.setText(texto);
			
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			post.setDate(timestamp);

			PostDAO postDAO = new PostDAO();
			String result = postDAO.insert(post);
			postDAO.close();
			
			if(result.equals("Success")) {
				response.sendRedirect("/Blog");
			} else {
				request.setAttribute("error", "Algo de errado n?o est? correto =( !");
				request.getRequestDispatcher("/newpost.jsp").forward(request, response);
			}
				
		} else {
			request.setAttribute("error", "Campo(s) obrigat?rio(s) em branco.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
	}

	protected void updatePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		int id = Integer.parseInt(request.getParameter("id"));
		String titulo = request.getParameter("titulo");
		String subtitulo = request.getParameter("subtitulo");
		String texto = request.getParameter("texto");
		String categoria = request.getParameter("categoria");
		
		if ( titulo != null   && texto != null    && categoria != null
		 && !titulo.isEmpty() && !texto.isEmpty() && !categoria.isEmpty() ) {
			
			Cookie idAuthor = getCookie("id", request, response);
			
			if(idAuthor == null) {
				response.sendRedirect("login.jsp");
				return;
			}
			
			System.out.println();
			
			Post post = new Post();
			post.setId(id);
			post.setAuthor(Integer.valueOf(idAuthor.getValue()));
			post.setCategory(Integer.valueOf(categoria));
			post.setTitle(titulo);
			post.setSubtitle(subtitulo);
			post.setText(texto);
			
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			post.setDate(timestamp);

			PostDAO postDAO = new PostDAO();
			String result = postDAO.update(post);
			postDAO.close();
			
			if(result.equals("Success")) {
				response.sendRedirect("/Blog");
			} else {
				request.setAttribute("error", "Algo de errado n?o est? correto =( !");
				request.getRequestDispatcher("/editpost?id="+id).forward(request, response);
			}
				
		} else {
			request.setAttribute("error", "Campo(s) obrigat?rio(s) em branco.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
	}
	
	protected Cookie getCookie(String name, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
		 for (Cookie cookie : cookies) {
		   if (cookie.getName().equals(name)) {
		     return cookie;
		    }
		  }
		}
		return null;
	}
	
	protected void newAuthor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Author author = new Author();
		String nome = request.getParameter("name").replaceAll(" ", "");
		
		author.setName(nome);
		author.setEmail(request.getParameter("email"));
		author.setPassword(request.getParameter("password"));
		
		AuthorDAO authorDAO = new AuthorDAO();
		
		authorDAO.insert(author);
		
		authorDAO.close();
		
		System.out.println("Autor adicionado com sucesso!");
		
		response.sendRedirect("/Blog");
	}
	
	protected void selecionaAutor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		Author author = new Author();
		
		AuthorDAO authorDAO = new AuthorDAO();
		
		author = authorDAO.findId(id);
		
		authorDAO.close();
		
		System.out.println(author.getName());
		
		request.setAttribute("author", author);
		
		RequestDispatcher rd = request.getRequestDispatcher("editAuthor.jsp");
		
		rd.forward(request, response);
		
	}
	
	protected void updateAuthor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Author author = new Author();
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		String nome = request.getParameter("name").replaceAll(" ", "");
		
		author.setName(nome);
		
		author.setId(id);
		author.setEmail(request.getParameter("email"));
		author.setPassword(request.getParameter("password"));
		
		AuthorDAO authorDAO = new AuthorDAO();
		authorDAO.update(author);
		authorDAO.close();
		
		Cookie cookieName = new Cookie("name",author.getName());
		response.addCookie(cookieName);

		Cookie cookieEmail = new Cookie("email",author.getEmail());
		response.addCookie(cookieEmail);
		
		response.sendRedirect("/Blog");
		
	}
	
	

}
