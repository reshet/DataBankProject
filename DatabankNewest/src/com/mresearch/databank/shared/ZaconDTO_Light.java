package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class ZaconDTO_Light implements Serializable,ICatalogizable,IPickableElement{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private long id;
	private String header = "���������";
	private String number;
	public ZaconDTO_Light(){}
	public ZaconDTO_Light(String header)
	{
		this.header = header;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String getTextRepresent() {
		return header;
	}
	@Override
	public long getID() {
		return id;
	}
	
//	public Date getDate() {
//		return date;
//	}
//	public void setDate(Date date) {
//		this.date = date;
//	}
//	public Date getDecline_date() {
//		return decline_date;
//	}
//	public void setDecline_date(Date decline_date) {
//		this.decline_date = decline_date;
//	}
//	public Date getAccept_date() {
//		return accept_date;
//	}
//	public void setAccept_date(Date accept_date) {
//		this.accept_date = accept_date;
//	}
//	public ArrayList<String> getKey_words() {
//		return key_words;
//	}
//	public void setKey_words(ArrayList<String> key_words) {
//		this.key_words = key_words;
//	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
//	public ArrayList<String> getAuthors() {
//		return authors;
//	}
//	public void setAuthors(ArrayList<String> authors) {
//		this.authors = authors;
//	}
}
