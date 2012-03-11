package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.http.client.URL;

public class SearchTaskLawDTO implements Serializable,ISearchTask{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private String type = "SocioResearch";
	private String heading_have,keywords_have,autors_have,number_have,abstract_have;
	
	
	private Date date_from,date_to,accept_date_from,accept_date_to,decline_date_from,decline_date_to;


	public Date getDate_from() {
		return date_from;
	}
	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}
	public Date getDate_to() {
		return date_to;
	}
	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}
	public Date getAccept_date_from() {
		return accept_date_from;
	}
	public void setAccept_date_from(Date accept_date_from) {
		this.accept_date_from = accept_date_from;
	}
	public Date getAccept_date_to() {
		return accept_date_to;
	}
	public void setAccept_date_to(Date accept_date_to) {
		this.accept_date_to = accept_date_to;
	}
	public Date getDecline_date_from() {
		return decline_date_from;
	}
	public void setDecline_date_from(Date decline_date_from) {
		this.decline_date_from = decline_date_from;
	}
	public Date getDecline_date_to() {
		return decline_date_to;
	}
	public void setDecline_date_to(Date decline_date_to) {
		this.decline_date_to = decline_date_to;
	}
	public String getHeading_have() {
		return heading_have;
	}
	public void setHeading_have(String heading_have) {
		this.heading_have = heading_have;
	}
	
	public String getKeywords_have() {
		return keywords_have;
	}
	public void setKeywords_have(String keywords_have) {
		this.keywords_have = keywords_have;
	}
	public String getAutors_have() {
		return autors_have;
	}
	public void setAutors_have(String autors_have) {
		this.autors_have = autors_have;
	}
	public String getNumber_have() {
		return number_have;
	}
	public void setNumber_have(String number_have) {
		this.number_have = number_have;
	}
	public String getAbstract_have() {
		return abstract_have;
	}
	public void setAbstract_have(String abstract_have) {
		this.abstract_have = abstract_have;
	}
	public SearchTaskLawDTO(){}
	public SearchTaskLawDTO(String type)
	{
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

	public static String date_serialize(Date date)
	{
		String str = "";
//		str+=String.valueOf(date.getDate());
//		str+="-"+String.valueOf(date.getTime());
		str+=String.valueOf(date.getYear())+"-"+String.valueOf(date.getMonth())+"-"+String.valueOf(date.getDate())+" ";
		str+=String.valueOf(date.getHours())+":"+String.valueOf(date.getMinutes())+":"+String.valueOf(date.getSeconds());
		return str;
	}
	public static void date_deserialize(Date date,String dat)
	{
		
//		String [] str = dat.split("-");
//		date.setDate(Integer.parseInt(str[0]));
//		date.setTime(Integer.parseInt(str[1]));
		String [] str = dat.split(" ");
		String [] da = str[0].split("-");
		String [] ta = str[1].split(":");
		date.setYear(Integer.parseInt(da[0]));
		date.setMonth(Integer.parseInt(da[1]));
		date.setDate(Integer.parseInt(da[2]));
		date.setHours(Integer.parseInt(ta[0]));
		date.setMinutes(Integer.parseInt(ta[1]));
		date.setSeconds(Integer.parseInt(ta[2]));
//		
		//date.s
		//date.set
		//str+=String.valueOf(date.getYear())+"-"+String.valueOf(date.getMonth())+"-"+String.valueOf(date.getDay())+" ";
		//str+=String.valueOf(date.getHours())+":"+String.valueOf(date.getMinutes())+":"+String.valueOf(date.getSeconds());
		//return str;
	}
	public String serialize()
	{
		String ser = "";
		if (heading_have != null &&!heading_have.equals(""))ser = "heading_have="+this.heading_have;
		if (keywords_have != null &&!keywords_have.equals(""))ser = "keywords_have="+this.keywords_have;
		if (autors_have != null &&!autors_have.equals(""))ser = "autors_have="+this.autors_have;
		if (number_have != null &&!number_have.equals(""))ser = "number_have="+this.number_have;
		if(date_from != null)
			{
			if (ser !="")ser+="&";
					ser+="date_from="+date_serialize(this.date_from);
			}
		if(date_to != null){
			if (ser !="")ser+="&";
			ser+="date_to="+date_serialize(this.date_to);
		}
		if(accept_date_from != null)
		{
		if (ser !="")ser+="&";
				ser+="accept_date_from="+date_serialize(this.accept_date_from);
		}
		if(accept_date_to != null){
			if (ser !="")ser+="&";
			ser+="accept_date_to="+date_serialize(this.accept_date_to);
		}//ser = Base64.encode(ser.getBytes());
		if(decline_date_from != null)
		{
		if (ser !="")ser+="&";
				ser+="decline_date_from="+date_serialize(this.decline_date_from);
		}
		if(decline_date_to != null){
			if (ser !="")ser+="&";
			ser+="decline_date_to="+date_serialize(this.decline_date_to);
		}
		ser = URL.encode(ser);
		return ser;
	}
	private Date deserialize_date_param(ArrayList<String> param_n,ArrayList<String> param_v,String param_name)
	{
		String date = param_v.get(param_n.indexOf(param_name));
		Date dat = new Date();
		date_deserialize(dat, date);
		return dat;
	}
	public void deserialize(ArrayList<String> param_n,ArrayList<String> param_v)
	{
		if(param_n.contains("heading_have"))this.heading_have = param_v.get(param_n.indexOf("heading_have"));
		if(param_n.contains("number_have"))this.number_have = param_v.get(param_n.indexOf("number_have"));
		if(param_n.contains("keywords_have"))this.keywords_have = param_v.get(param_n.indexOf("keywords_have"));
		if(param_n.contains("autors_have"))this.autors_have = param_v.get(param_n.indexOf("autors_have"));
		//if(param_n.contains("heading_have"))this.heading_have = param_v.get(param_n.indexOf("heading_have"));
		
		//int t = (int)this.start_date_from.parse(param_v.get(param_n.indexOf("start_date_from")));
		if(param_n.contains("date_from"))
		{
			this.date_from = deserialize_date_param(param_n, param_v, "date_from");
		}
		if(param_n.contains("date_to"))
		{
			this.date_to = deserialize_date_param(param_n, param_v, "date_to");;
		}
		if(param_n.contains("accept_date_from"))
		{
			this.accept_date_from = deserialize_date_param(param_n, param_v, "accept_date_from");
		}
		if(param_n.contains("accept_date_to"))
		{
			this.accept_date_to = deserialize_date_param(param_n, param_v, "accept_date_to");;
		}
		if(param_n.contains("decline_date_from"))
		{
			this.decline_date_from = deserialize_date_param(param_n, param_v, "decline_date_from");
		}
		if(param_n.contains("decline_date_to"))
		{
			this.decline_date_to = deserialize_date_param(param_n, param_v, "decline_date_to");;
		}
		
	//	if(param_n.contains("start_date_to"))this.start_date_to = param_v.get(param_n.indexOf("start_date_to"));
	//	if(param_n.contains("start_date_from"))this.concepts_have = param_v.get(param_n.indexOf("concepts_have"));
	//	if(param_n.contains("start_date_from"))this.concepts_have = param_v.get(param_n.indexOf("concepts_have"));
	//	if(param_n.contains("concepts_have"))this.concepts_have = param_v.get(param_n.indexOf("concepts_have"));
		
	}
}