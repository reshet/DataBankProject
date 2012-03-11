package com.mresearch.databank.shared;

import java.io.Serializable;

public class SearchResultDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private String heading = "Result entity highlited text matching";
	private String type = "SocioResearch";
	private String text = "Found result entity text representation";
	private String id;
	private double rank = 0;
	public SearchResultDTO(){}
	public SearchResultDTO(String heading,String text,String type,String id)
	{
		this.setHeading(heading);
		this.setText(type);
		this.setText(text);
		this.id = id;
	}public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public double getRank() {
		return rank;
	}
	public void setRank(double rank) {
		this.rank = rank;
	}
	
}
