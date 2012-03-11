package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class ZaconDTO implements Serializable,ICatalogizable,IPickableElement{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private long id;
	private String header = "���������";
	private String contents = "���������";
	private String number;
	private ArrayList<String> key_words,authors;
	private Date date,accept_date,decline_date;
	
	private String enclosure_key;
	public ZaconDTO(){}
	public ZaconDTO(String header, String contents)
	{
		this.header = header;
		this.contents = contents;		
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEnclosure_key() {
		return enclosure_key;
	}
	public void setEnclosure_key(String enclosure_key) {
		this.enclosure_key = enclosure_key;
	}
	@Override
	public String getTextRepresent() {
		return header;
	}
	@Override
	public long getID() {
		return id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDecline_date() {
		return decline_date;
	}
	public void setDecline_date(Date decline_date) {
		this.decline_date = decline_date;
	}
	public Date getAccept_date() {
		return accept_date;
	}
	public void setAccept_date(Date accept_date) {
		this.accept_date = accept_date;
	}
	public ArrayList<String> getKey_words() {
		return key_words;
	}
	public void setKey_words(ArrayList<String> key_words) {
		this.key_words = key_words;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public ArrayList<String> getAuthors() {
		return authors;
	}
	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}
}
