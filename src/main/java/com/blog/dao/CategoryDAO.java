package com.blog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.blog.bd.ConnectionFactory;
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
	
	public void close() {
		try {
			this.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
