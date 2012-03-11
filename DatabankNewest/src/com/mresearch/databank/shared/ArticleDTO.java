package com.mresearch.databank.shared;

import java.io.Serializable;


public class ArticleDTO implements Serializable,ICatalogizable,IPickableElement{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private long id;
	private String header = "���������";
	private String contents = "���������";
	private String enclosure_key;
	public ArticleDTO(){}
	public ArticleDTO(String header, String contents)
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
}
