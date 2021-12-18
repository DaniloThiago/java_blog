package com.blog.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.bd.ConnectionFactory;
import com.blog.dao.AuthorDAO;
import com.blog.model.Author;

/**
 * Servlet implementation class BlogController
 */
@WebServlet(urlPatterns = {"/login", "/validar"})
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
			response.sendRedirect("login.html");
			
		} else if( action.equals("/validar")) {
			validar(request, response);
		}
//		ConnectionFactory cf = new ConnectionFactory();
//		cf.testConnection();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void validar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Author author = new Author();
		author.setEmail(request.getParameter("email"));
		author.setPassword(request.getParameter("password"));
		
		AuthorDAO authorDAO = new AuthorDAO();
		Author findAuthor = authorDAO.find(author);
		authorDAO.close();
		
		request.setAttribute("author", findAuthor);
		
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}

}
