package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;


public class FilterRealDiapasonDTO extends FilterDiapasonDTO{
	/**
	 * 
	 */
	public FilterRealDiapasonDTO(){}
	public FilterRealDiapasonDTO(String target_class,String target_field, String value_start,String value_end)
	{
		super(target_class,target_field,value_start,value_end);
	}
	@Override
	public String getFilter() {
		String toReturn1 = "";
		String toReturn2 = "";
		
		if (this.getFiltering_value_start()!= null && !this.getFiltering_value_start().equals(""))
		{
			toReturn1 = super.getTarget_field_name()+" >= '"+this.getFiltering_value_start()+"'";
		}
		if (this.getFiltering_value_end()!= null && !this.getFiltering_value_end().equals(""))
		{
			toReturn2 = super.getTarget_field_name()+" <= '"+this.getFiltering_value_end()+"'";
		}
		if(!toReturn1.equals("") && !toReturn2.equals("")) return toReturn1+" && "+toReturn2;
		else return toReturn1+toReturn2;
	}
	@Override
	public JSONObject getJSONFilter() {
		  JSONObject obj = new JSONObject();
		  JSONObject q = new JSONObject();
		  JSONObject bounds = new JSONObject();
		  if (filtering_value_start != null) bounds.put("from", new JSONNumber(Double.parseDouble(filtering_value_start)));
		  if (filtering_value_end != null) bounds.put("to", new JSONNumber(Double.parseDouble(filtering_value_end)));

		  q.put(super.getTarget_field_name(), bounds);
		  obj.put("range", q);
		  return obj;
		}

	}
