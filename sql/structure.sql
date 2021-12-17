CREATE DATABASE BLOG;
USE BLOG;

CREATE TABLE IF NOT EXISTS author (
	id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(80) NOT NULL
);

CREATE TABLE IF NOT EXISTS post (
	id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    author INTEGER,
    title VARCHAR(20),
    post TEXT,
    date DATE,
    FOREIGN KEY(author) REFERENCES author(id)
);