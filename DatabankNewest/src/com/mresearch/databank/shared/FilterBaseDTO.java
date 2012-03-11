package com.mresearch.databank.shared;

import java.io.Serializable;


public abstract class FilterBaseDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private String id;
	private String target_field_name = "name";
	private String target_class_name = "name";
	public FilterBaseDTO(){}
	public abstract String getFilter();
	public FilterBaseDTO(String target_class,String target_field)
	{
		this.setTarget_class_name(target_class);
		this.target_field_name = target_field;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getTarget_field_name() {
		return target_field_name;
	}
	public void setTarget_field_name(String target_field_name) {
		this.target_field_name = target_field_name;
	}
	public String getTarget_class_name() {
		return target_class_name;
	}
	public void setTarget_class_name(String target_class_name) {
		this.target_class_name = target_class_name;
	}
}
