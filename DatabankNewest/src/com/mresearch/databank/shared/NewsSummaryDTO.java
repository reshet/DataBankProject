package com.mresearch.databank.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NewsSummaryDTO implements Serializable{
	private String header = "«аголовок новости";
	private String contents = "—одержание новости которое немного длиннее заголовка";
	public NewsSummaryDTO(){}
	public NewsSummaryDTO(String header, String contents)
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
}
