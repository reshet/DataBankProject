package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;


public abstract class FilterDiapasonDTO extends FilterBaseDTO
implements Serializable
{
private static final long serialVersionUID = -2443613870378329241L;
protected String filtering_value_start = "3.0";
protected String filtering_value_end = "4.0";
private FilterMultiDTO multi_dto_proxy;

public FilterMultiDTO getMulti_dto_proxy()
{
  return this.multi_dto_proxy;
}
public FilterDiapasonDTO() {
}
public FilterDiapasonDTO(String target_class, String target_field, String value_start, String value_end) {
  super(target_class, target_field);
  setFiltering_value_start(value_start);
  setFiltering_value_end(value_end);
  ArrayList filters = new ArrayList();
  filters.add(new FilterMatchDTO(target_class, target_field, ">=", value_start));
  filters.add(new FilterMatchDTO(target_class, target_field, "<=", value_end));
  this.multi_dto_proxy = new FilterMultiDTO(filters);
}
public String getFiltering_value_start() {
  return this.filtering_value_start;
}
public void setFiltering_value_start(String filtering_value_start) {
  this.filtering_value_start = filtering_value_start;
}
public String getFiltering_value_end() {
  return this.filtering_value_end;
}
public void setFiltering_value_end(String filtering_value_end) {
  this.filtering_value_end = filtering_value_end;
}

public String getFilter() {
  String toReturn1 = "";
  String toReturn2 = "";

  if ((getFiltering_value_start() != null) && (!getFiltering_value_start().equals("")))
  {
    toReturn1 = super.getTarget_field_name() + " >= " + getFiltering_value_start();
  }
  if ((getFiltering_value_end() != null) && (!getFiltering_value_end().equals("")))
  {
    toReturn2 = super.getTarget_field_name() + " <= " + getFiltering_value_end();
  }
  if ((!toReturn1.equals("")) && (!toReturn2.equals(""))) return toReturn1 + " && " + toReturn2;
  return toReturn1 + toReturn2;
}

//public JSONObject getJSONFilter() {
//  JSONObject obj = new JSONObject();
//  JSONObject q = new JSONObject();
//  JSONObject bounds = new JSONObject();
//  if (this.filtering_value_start != null) bounds.put("from", new JSONString(this.filtering_value_start));
//  if (this.filtering_value_end != null) bounds.put("to", new JSONString(this.filtering_value_end));
//
//  q.put(super.getTarget_field_name(), bounds);
//  obj.put("range", q);
//  return obj;
//}

public abstract JSONObject getJSONFilter();



}
