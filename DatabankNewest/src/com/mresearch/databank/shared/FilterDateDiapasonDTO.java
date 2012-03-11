package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;


public class FilterDateDiapasonDTO extends FilterDiapasonDTO{
	/**
	 * 
	 */
	public FilterDateDiapasonDTO(){}
	public FilterDateDiapasonDTO(String target_class,String target_field, String value_start,String value_end)
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
	}
