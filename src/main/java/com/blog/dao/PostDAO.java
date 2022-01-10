package com.blog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.blog.bd.ConnectionFactory;
import com.blog.model.Author;
import com.blog.model.Post;

public class PostDAO {

	private Connection conn;
	
	public PostDAO() {
		ConnectionFactory cf = new ConnectionFactory();
		this.conn = cf.getConnection();
	}
	
	public String insert(Post post) {
		String sql = "INSERT INTO post (author, category, title, subtitle, text, date) VALUES (?, ?, ?, ?, ?, ?)";
		
		try {			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, post.getAuthor());
			stmt.setInt(2, post.getCategory());
			stmt.setString(3, post.getTitle());
			System.out.println(post.getSubtitle());
			stmt.setString(4, post.getSubtitle());
			stmt.setString(5, post.getText());
			stmt.setTimestamp(6, post.getDate());
			
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
	
	public String update(Post post) {
		String sql = "UPDATE post SET author = ?, category = ?, title = ?, subtitle = ?, text = ?, date = ? WHERE id = ?";
		
		try {			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, post.getAuthor());
			stmt.setInt(2, post.getCategory());
			stmt.setString(3, post.getTitle());
			stmt.setString(4, post.getSubtitle());
			stmt.setString(5, post.getText());
			stmt.setTimestamp(6, post.getDate());
			stmt.setInt(7, post.getId());
			
			int i = stmt.executeUpdate();
			stmt.close();
            
			if (i > 0) {
            	return "Success";             
            }
                 	  
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return "Fail";
	}
	
	public ArrayList<Post> getPosts() {		
		String sql = "SELECT * from post";
		try {
			ArrayList<Post> posts = null;
			PreparedStatement stmt = conn.prepareStatement(sql);			
			ResultSet rs = stmt.executeQuery(sql);
			
			
			while(rs.next()) {
				if(rs.isFirst()) {
					posts = new ArrayList<Post>();
				}
				Post post = new Post();
				post.setId(rs.getInt("id"));
				post.setAuthor(rs.getInt("author"));
				post.setCategory(rs.getInt("category"));
				post.setTitle(rs.getString("title"));
				post.setSubtitle(rs.getString("subtitle"));
				post.setText(rs.getString("text"));
				post.setDate(rs.getTimestamp("date"));
				posts.add(post);
			}
			
			rs.close();
			stmt.close();
			
			return posts;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	//CORRIGIR (TO-DO)
	public Post find(int id) {
		
		String sql = "SELECT * FROM post WHERE id=?";		
		
		try {
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);			
			
			ResultSet rs = stmt.executeQuery();
			Post findPost = null;
			if( rs.next() != false) {
				findPost = new Post();
				findPost.setId(rs.getInt("id"));
				findPost.setTitle(rs.getString("title"));
				findPost.setSubtitle(rs.getString("subtitle"));
				findPost.setText(rs.getString("text"));
				findPost.setCategory(rs.getInt("category"));
			}
			rs.close();
			stmt.close();
			
			return findPost;
			     	  
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
