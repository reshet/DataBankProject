package com.mresearch.databank.shared;

import java.io.Serializable;


public class SSE_DTO implements Serializable,ICatalogizable,IPickableElement{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private long id;
	private String clas = "clas";
	private String kind = "kind";
	private String contents = "value";
	public SSE_DTO(){}
	public SSE_DTO(String clas,String kind, String contents)
	{
		this.setClas(clas);
		this.setKind(kind);
		this.contents = contents;		
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
	
	@Override
	public String getTextRepresent() {
		return contents;
	}
	
	@Override
	public long getID() {
		return id;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getClas() {
		return clas;
	}
	public void setClas(String clas) {
		this.clas = clas;
	}
}
