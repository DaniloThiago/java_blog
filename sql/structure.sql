CREATE DATABASE BLOG;
USE BLOG;
 
CREATE TABLE IF NOT EXISTS author (
	id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(80) NOT NULL
);

CREATE TABLE IF NOT EXISTS category (
	id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,    
    description VARCHAR(30)
);
 
INSERT INTO category (description) VALUES ('Tecnologia');
INSERT INTO category (description) VALUES ('Saúde');

CREATE TABLE IF NOT EXISTS post (
	id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    author INTEGER,
    category INTEGER,
    title VARCHAR(100),
    subtitle VARCHAR(100),
    text TEXT,
    date TIMESTAMP,
    FOREIGN KEY(author) REFERENCES author(id),
    FOREIGN KEY(category) REFERENCES category(id)
);
 
