package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("serial")
public class VarDTO extends VarDTO_Light implements Serializable{
	private ArrayList<Double> v_label_codes;
	private ArrayList<String> v_label_values;
	private ArrayList<Double> distribution;
	public VarDTO()
	{
		super();
	}
	
	public VarDTO(String code,String label)
	{
		this();
		this.setCode(code);
		this.setLabel(label);
	}
	public void setBasicInfo(String code,String label)
	{
		this.setCode(code);
		this.setLabel(label);
	}
	
	public ArrayList<Double> getV_label_codes() {
		return v_label_codes;
	}

	public void setV_label_codes(ArrayList<Double> v_label_codes) {
		this.v_label_codes = v_label_codes;
	}

	public ArrayList<String> getV_label_values() {
		return v_label_values;
	}

	public void setV_label_values(ArrayList<String> v_label_values) {
		this.v_label_values = v_label_values;
	}

	public ArrayList<Double> getDistribution() {
		return distribution;
	}

	public void setDistribution(ArrayList<Double> distribution) {
		this.distribution = distribution;
	}

	
	
}
