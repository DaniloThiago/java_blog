package com.blog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.blog.bd.ConnectionFactory;
import com.blog.model.Author;
import com.blog.model.Category;

public class CategoryDAO {
	
	private Connection conn;
	
	public CategoryDAO() {
		ConnectionFactory cf = new ConnectionFactory();
		this.conn = cf.getConnection();
	}
	
	public ArrayList<Category> getCategories() {
		String sql = "SELECT * from category";
		try {
			ArrayList<Category> categories = new ArrayList<Category>();
			PreparedStatement stmt = conn.prepareStatement(sql);			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Category category = new Category();
				category.setId(rs.getInt("id"));
				category.setDescription(rs.getString("description"));				
				categories.add(category);
			}
			
			rs.close();
			stmt.close();
			
			return categories;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public Category findId(int id) {
		String sql = "SELECT * FROM category WHERE id=?";		
		
		try {
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			Category findCategory = null;
			if( rs.next() != false) {
				findCategory = new Category();
				findCategory.setId(rs.getInt("id"));
				findCategory.setDescription(rs.getString("description"));
			}
			rs.close();
			stmt.close();
			
			return findCategory;
			     	  
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
