package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.user.client.rpc.core.java.util.Arrays;

@SuppressWarnings("serial")
public class TextVarDTO_Detailed extends VarDTO_Detailed{
	private ArrayList<String> filtered_cortage;
	
	public ArrayList<String> getFiltered_cortage() {
		return filtered_cortage;
	}
	public void setFiltered_cortage(ArrayList<String> filtered_cortage) {
		this.filtered_cortage = filtered_cortage;
	}
	public TextVarDTO_Detailed()
	{
		super();	
	}
		
}
