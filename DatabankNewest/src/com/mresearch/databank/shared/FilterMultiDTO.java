package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class FilterMultiDTO extends FilterBaseDTO implements Serializable{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -2443613870378329241L;
	private ArrayList<FilterBaseDTO> filters;
	public FilterMultiDTO(){}
	public FilterMultiDTO(ArrayList<FilterBaseDTO> filters)
	{
		this.filters = filters;
	}

	public ArrayList<FilterBaseDTO> getFilters() {
		return filters;
	}
	public String getFiltersSum() {
		String sum = "";
		for(int i = 0;i < filters.size() - 1;i++)
		{
			sum+=filters.get(i)+" || ";
		}
		sum+=filters.get(filters.size()-1);
		return sum;
		//	return super.getTarget_field_name()+" == "+this.getFiltering_value();
	}
	public String getFiltersMult() {
		String sum = "";
		for(int i = 0;i < filters.size() - 1;i++)
		{
			sum+=filters.get(i)+" && ";
		}
		sum+=filters.get(filters.size()-1);
		return sum;
		//	return super.getTarget_field_name()+" == "+this.getFiltering_value();
	}
	@Override
	public String getFilter() {
		//in general, not suitable for AppEngine filtering restrictions;
		return getFiltersSum();
	}
	@Override
	public JSONObject getJSONFilter() {
	    JSONObject obj_bool = new JSONObject();
	    JSONObject obj_sh = new JSONObject();
	    JSONArray arr_should = new JSONArray();
	    for (int i = 0; i < this.filters.size(); i++)
	    {
	      arr_should.set(i, ((FilterBaseDTO)this.filters.get(i)).getJSONFilter());
	    }
	    obj_sh.put("should", arr_should);
	    obj_bool.put("bool", obj_sh);

	    return obj_bool;
	}
}
