package com.blog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.blog.bd.ConnectionFactory;
import com.blog.model.Author;

public class AuthorDAO {

	private Connection conn;
	
	public AuthorDAO() {
		ConnectionFactory cf = new ConnectionFactory();
		this.conn = cf.getConnection();
	}
	
	public String insert(Author author) {
		String sql = "INSERT INTO author (name, email, password) VALUES (?,?,?)";		
		
		try {			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, author.getName());
			stmt.setString(2, author.getEmail());
			stmt.setString(3, author.getPassword());			
			
			int  i = stmt.executeUpdate();
			stmt.close();
            
			if (i > 0) {
            	return "Success";             
            }
                 	  
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return "Fail";
	}
	
	public Author find(Author author) {		
		
		String sql = "SELECT * FROM author WHERE email=? AND password=?";		
		
		try {
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, author.getEmail());
			stmt.setString(2, author.getPassword());			
			
			ResultSet rs = stmt.executeQuery();
			Author findAuthor = null;
			if( rs.next() != false) {
				findAuthor = new Author();
				findAuthor.setId(rs.getInt("id"));
				findAuthor.setName(rs.getString("name"));
				findAuthor.setEmail(rs.getString("Email"));
			}
			rs.close();
			stmt.close();
			
			return findAuthor;
			     	  
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String update(Author author) {
		String sql = "UPDATE author SET name=?, email=?, password=password WHERE id=?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, author.getName());
			stmt.setString(2, author.getEmail());			
			stmt.setInt(3, author.getId());
			
			System.out.println(author.getPassword());
			int  i = stmt.executeUpdate();
			stmt.close();
            
			if (i > 0) {
            	return "Success";             
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return "Fail";
	}
	
	public String delete(Author author) {
		String sql = "DELETE from author WHERE id=?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, author.getId());			
			
			int  i = stmt.executeUpdate();
			stmt.close();
            
			if (i > 0) {
            	return "Success";             
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return "Fail";
	}
	
	public ArrayList<Author> getAuthors() {
		String sql = "SELECT * from author";
		try {
			ArrayList<Author> authors = new ArrayList<Author>();
			PreparedStatement stmt = conn.prepareStatement(sql);			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Author author = new Author();
				author.setId(rs.getInt("id"));
				author.setName(rs.getString("name"));
				author.setEmail(rs.getString("email"));
				authors.add(author);
			}
			
			rs.close();
			stmt.close();
			
			return authors;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public void close() {
		try {
			this.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
