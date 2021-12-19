package com.blog.controller;

import java.io.IOException;
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
import com.blog.model.Author;
import com.blog.model.Category;


/**
 * Servlet implementation class BlogController
 */
@WebServlet(urlPatterns = {"/login", "/validar", "/logout", "/newpost", "/createpost"})
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
		
		else if( action.equals("/newpost") ) {
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
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();				
				
		if( action.equals("/validar")) {
			validar(request, response);
		}
		
		if( action.equals("/createpost")) {
			createPost(request, response);
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
				Cookie cookieName = new Cookie("name",findAuthor.getName());
				Cookie cookieEmail = new Cookie("email",findAuthor.getEmail());
				cookieName.setMaxAge(60*5);
				cookieEmail.setMaxAge(60*5);
				response.addCookie(cookieName);
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
				Cookie cookieName = new Cookie("name",findAuthor.getName());
				Cookie cookieEmail = new Cookie("email",findAuthor.getEmail());
				cookieName.setMaxAge(60*5);
				cookieEmail.setMaxAge(60*5);
				response.addCookie(cookieName);
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

}
