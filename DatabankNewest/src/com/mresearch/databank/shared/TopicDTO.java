package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class TopicDTO implements Serializable,ICatalogizable,IPickableElement{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private long id;
	private String header = "";
	private ArrayList<Long> items;
	public TopicDTO(){}
	public TopicDTO(long id,String header,ArrayList<Long> items)
	{
        this.id = id;
		this.header = header;
		 this.items = items;
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
	
	public ArrayList<Long> getItems() {
		return items;
	}
	public void setItems(ArrayList<Long> items) {
		this.items = items;
	}
}
