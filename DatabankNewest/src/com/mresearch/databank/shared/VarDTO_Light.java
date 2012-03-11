package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("serial")
public class VarDTO_Light implements Serializable{
	private long id;
	private String code;
	private String label;
	public VarDTO_Light()
	{}
	
	public VarDTO_Light(long id,String code,String label)
	{
		this();
        this.id = id;
		this.setCode(code);
		this.setLabel(label);
	}
	public void setBasicInfo(String code,String label)
	{
		this.setCode(code);
		this.setLabel(label);
	}
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

		
}
