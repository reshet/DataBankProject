package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class CatalogConceptDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private Long id;
	private String name;
	private String c_type;
	private boolean isRoot;
	private long super_concept_ID;
	//private Class c_type;
	private ArrayList<String> contents_keys;
	private ArrayList<String> sub_categs;
	public CatalogConceptDTO(){}
	public CatalogConceptDTO(String name)
	{
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
//	public Class getC_type() {
//		return c_type;
//	}
//	public void setC_type(Class c_type) {
//		this.c_type = c_type;
//	}
	public ArrayList<String> getContents_keys() {
		return contents_keys;
	}
	public void setContents_keys(ArrayList<String> contents_keys) {
		this.contents_keys = contents_keys;
	}
	public ArrayList<String> getSub_categs() {
		return sub_categs;
	}
	public void setSub_categs(ArrayList<String> sub_categs) {
		this.sub_categs = sub_categs;
	}
	public String getC_type() {
		return c_type;
	}
	public void setC_type(String c_type) {
		this.c_type = c_type;
	}
	public boolean isRoot() {
		return isRoot;
	}
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	public long getSuper_concept_ID() {
		return super_concept_ID;
	}
	public void setSuper_concept_ID(long super_concept_ID) {
		this.super_concept_ID = super_concept_ID;
	}
}
