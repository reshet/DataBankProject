package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


//@SuppressWarnings("serial")
public class SocioResearchDTO_Light implements Serializable,ICatalogizable,IPickableElement{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2708602147113054043L;
	private static SocioResearchDTO_Light type;
	private Long id;
	private String name;
	public SocioResearchDTO_Light()
	{
	}
	
	
	
	
	public SocioResearchDTO_Light(Long id,String name)
	{
		this();
                this.id = id;
		this.name=name;
	}
	public void setBasicInfo(String name,Long org_pr)
	{
		this.setName(name);
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}


	@Override
	public String getTextRepresent() {
		
		return name;
	}


	@Override
	public long getID() {
		return id;
	}	
	
}
