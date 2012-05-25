package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class PublicationDTO extends PublicationDTO_Light{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private String contents = "";
	private String subheading = "";
	
	//4405882301020404
	private HashMap<String,String> filling;
//	private ArrayList<String> key_words,authors;
//	private Date date,accept_date,decline_date;
	private String json_desctiptor;
	public String getJson_desctiptor() {
		return json_desctiptor;
	}


	public void setJson_desctiptor(String json_desctiptor) {
		this.json_desctiptor = json_desctiptor;
	}
	private Long enclosure_key;
	public PublicationDTO(){}
	public PublicationDTO(long id,String header, String subheading,String contents,Date date,long encl_k,HashMap<String,String> fill)
	{
		super(id,header,date);
		this.contents = contents;
		this.setSubheading(subheading);
                this.enclosure_key = encl_k;
                this.filling = fill;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Long getEnclosure_key() {
		return enclosure_key;
	}
	public void setEnclosure_key(Long enclosure_key) {
		this.enclosure_key = enclosure_key;
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
//	public ArrayList<String> getAuthors() {
//		return authors;
//	}
//	public void setAuthors(ArrayList<String> authors) {
//		this.authors = authors;
//	}
	public HashMap<String,String> getFilling() {
		return filling;
	}
	public void setFilling(HashMap<String,String> filling) {
		this.filling = filling;
	}


	public String getSubheading() {
		return subheading;
	}


	public void setSubheading(String subheading) {
		this.subheading = subheading;
	}
}
