package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.Date;

public class NewsDTO implements Serializable{
	private String header = "Заголовок новости полный";
	private String contents = "Содержание новости которое немного длиннее заголовка, и это полное содержание";
	private Date date = new Date();
	private String author = "Администратор";
	public NewsDTO(){}
	public NewsDTO(String header, String contents,Date date,String author)
	{
		this.header = header;
		this.contents = contents;		
		this.setDate(date);
		this.setAuthor(author);
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}
