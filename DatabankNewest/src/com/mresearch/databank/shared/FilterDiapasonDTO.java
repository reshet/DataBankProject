package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;


public class FilterDiapasonDTO extends FilterBaseDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private String filtering_value_start = "3.0";
	private String filtering_value_end = "4.0";
	private FilterMultiDTO multi_dto_proxy;
	public FilterMultiDTO getMulti_dto_proxy() {
		return multi_dto_proxy;
	}
	public FilterDiapasonDTO(){}
	public FilterDiapasonDTO(String target_class,String target_field, String value_start,String value_end)
	{
		super(target_class,target_field);
		this.setFiltering_value_start(value_start);
		this.setFiltering_value_end(value_end);
		ArrayList<FilterBaseDTO> filters = new ArrayList<FilterBaseDTO>();
		filters.add(new FilterMatchDTO(target_class, target_field, ">=",value_start));
		filters.add(new FilterMatchDTO(target_class, target_field, "<=",value_end));
		this.multi_dto_proxy = new FilterMultiDTO(filters);
	}
	public String getFiltering_value_start() {
		return filtering_value_start;
	}
	public void setFiltering_value_start(String filtering_value_start) {
		this.filtering_value_start = filtering_value_start;
	}
	public String getFiltering_value_end() {
		return filtering_value_end;
	}
	public void setFiltering_value_end(String filtering_value_end) {
		this.filtering_value_end = filtering_value_end;
	}
	@Override
	public String getFilter() {
		String toReturn1 = "";
		String toReturn2 = "";
		
		if (this.getFiltering_value_start()!= null && !this.getFiltering_value_start().equals(""))
		{
			toReturn1 = super.getTarget_field_name()+" >= "+this.getFiltering_value_start()+"";
		}
		if (this.getFiltering_value_end()!= null && !this.getFiltering_value_end().equals(""))
		{
			toReturn2 = super.getTarget_field_name()+" <= "+this.getFiltering_value_end()+"";
		}
		if(!toReturn1.equals("") && !toReturn2.equals("")) return toReturn1+" && "+toReturn2;
		else return toReturn1+toReturn2;
	}
	}
