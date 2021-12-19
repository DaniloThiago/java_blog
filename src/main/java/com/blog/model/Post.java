package com.blog.model;

import java.sql.Timestamp;

import com.blog.dao.AuthorDAO;
import com.blog.dao.CategoryDAO;

public class Post {
	private int id;
	private int author;
	private int category;
	private String title;
	private String subtitle;
	private String text;
	private Timestamp date;
	
	public Post() { }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public Author getDadosAuthor() {
		AuthorDAO authorDAO = new AuthorDAO();		
		return authorDAO.findId(this.author);
	}
	
	public Category getDadosCategory() {
		CategoryDAO categoryDAO = new CategoryDAO();		
		return categoryDAO.findId(this.category);
	}
	
}
