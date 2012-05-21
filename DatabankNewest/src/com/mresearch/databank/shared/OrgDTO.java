package com.mresearch.databank.shared;

import java.io.Serializable;

public class OrgDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private String name = "�������� �����������";
	private String adress = "���������� ������";
	private String tel = "0643452220";
	private Long id;
	public OrgDTO(){}
	public OrgDTO(String name, String adress,String tel)
	{
		this.setAdress(adress);
		this.setName(name);
		this.setTel(tel);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
