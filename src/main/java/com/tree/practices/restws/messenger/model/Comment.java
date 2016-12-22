package com.tree.practices.restws.messenger.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment {
	private long id;
	private String comment;
	private Date created;
	private String author;
	
public Comment() {
    	
    }
    
    public Comment(long id, String message, String author) {
    	this.id = id;
    	this.comment = message;
    	this.author = author;
    	this.created = new Date();
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String message) {
		this.comment = message;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
    
}
