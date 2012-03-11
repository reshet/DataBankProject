package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.http.client.URL;

public class SearchTaskResearchDTO implements Serializable,ISearchTask{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private String type = "SocioResearch";
	private String heading_have,gen_g_have,org_impl_name,org_order_name,publ_have,researchers_have,method_have,concepts_have;
	public static String SELECTION_APPR_RANDOM = "random";
	public static String SELECTION_APPR_NOT_RANDOM = "not_random";
	public static String SELECTION_APPR_COMPLEXITY_UNI = "unistep";
	public static String SELECTION_APPR_COMPLEXITY_MULTI = "multistep";
	
	private String sel_appr_r;
	private String sel_appr_c;
	private Date start_date_from,start_date_to,end_date_from,end_date_to;
	public Date getStart_date_to() {
		return start_date_to;
	}
	public void setStart_date_to(Date start_date_to) {
		this.start_date_to = start_date_to;
	}
	public Date getEnd_date_from() {
		return end_date_from;
	}
	public void setEnd_date_from(Date end_date_from) {
		this.end_date_from = end_date_from;
	}
	public Date getEnd_date_to() {
		return end_date_to;
	}
	public void setEnd_date_to(Date end_date_to) {
		this.end_date_to = end_date_to;
	}
	public String getHeading_have() {
		return heading_have;
	}
	public void setHeading_have(String heading_have) {
		this.heading_have = heading_have;
	}
	public String getGen_g_have() {
		return gen_g_have;
	}
	public void setGen_g_have(String gen_g_have) {
		this.gen_g_have = gen_g_have;
	}
	public String getOrg_impl_name() {
		return org_impl_name;
	}
	public void setOrg_impl_name(String org_impl_name) {
		this.org_impl_name = org_impl_name;
	}
	public String getOrg_order_name() {
		return org_order_name;
	}
	public void setOrg_order_name(String org_order_name) {
		this.org_order_name = org_order_name;
	}
	public String getPubl_have() {
		return publ_have;
	}
	public void setPubl_have(String publ_have) {
		this.publ_have = publ_have;
	}
	public String getResearchers_have() {
		return researchers_have;
	}
	public void setResearchers_have(String researchers_have) {
		this.researchers_have = researchers_have;
	}
	public String getMethod_have() {
		return method_have;
	}
	public void setMethod_have(String method_have) {
		this.method_have = method_have;
	}
	public String getConcepts_have() {
		return concepts_have;
	}
	public void setConcepts_have(String concepts_have) {
		this.concepts_have = concepts_have;
	}
	public SearchTaskResearchDTO(){}
	public SearchTaskResearchDTO(String type)
	{
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getStart_date_from() {
		return start_date_from;
	}
	public void setStart_date_from(Date start_date_from) {
		this.start_date_from = start_date_from;
	}
	public String getSel_appr_r() {
		return sel_appr_r;
	}
	public void setSel_appr_r(String sel_appr_r) {
		this.sel_appr_r = sel_appr_r;
	}
	public String getSel_appr_c() {
		return sel_appr_c;
	}
	public void setSel_appr_c(String sel_appr_c) {
		this.sel_appr_c = sel_appr_c;
	}	
	public static String date_serialize(Date date)
	{
		String str = "";
		if (date == null) return null;
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
		if(start_date_from != null)
			{
			if (ser !="")ser+="&";
					ser+="start_date_from="+date_serialize(this.start_date_from);
			}
		if(start_date_to != null){
			if (ser !="")ser+="&";
			ser+="start_date_to="+date_serialize(this.start_date_to);
		}
		if(end_date_from != null){
			if (ser !="")ser+="&";
			ser+="end_date_from="+date_serialize(this.end_date_from);
		}
		if(end_date_to != null){
			if (ser !="")ser+="&";
			ser+="end_date_to="+date_serialize(this.end_date_to);
		}
		//ser = Base64.encode(ser.getBytes());
		ser = URL.encode(ser);
		return ser;
	}
	public void deserialize(ArrayList<String> param_n,ArrayList<String> param_v)
	{
		if(param_n.contains("heading_have"))this.heading_have = param_v.get(param_n.indexOf("heading_have"));
		
		//int t = (int)this.start_date_from.parse(param_v.get(param_n.indexOf("start_date_from")));
		if(param_n.contains("start_date_from"))
		{
			String date = param_v.get(param_n.indexOf("start_date_from"));
			Date dat = new Date();
			date_deserialize(dat, date);
			this.start_date_from = dat;
		}
		if(param_n.contains("start_date_to"))
		{
			String date = param_v.get(param_n.indexOf("start_date_to"));
			Date dat = new Date();
			date_deserialize(dat, date);
			this.start_date_to = dat;
		}
		if(param_n.contains("end_date_from"))
		{
			String date = param_v.get(param_n.indexOf("end_date_from"));
			Date dat = new Date();
			date_deserialize(dat, date);
			this.end_date_from = dat;
		}
		if(param_n.contains("end_date_to"))
		{
			String date = param_v.get(param_n.indexOf("end_date_to"));
			Date dat = new Date();
			date_deserialize(dat, date);
			this.end_date_to = dat;
		}
	//	if(param_n.contains("start_date_to"))this.start_date_to = param_v.get(param_n.indexOf("start_date_to"));
	//	if(param_n.contains("start_date_from"))this.concepts_have = param_v.get(param_n.indexOf("concepts_have"));
	//	if(param_n.contains("start_date_from"))this.concepts_have = param_v.get(param_n.indexOf("concepts_have"));
	//	if(param_n.contains("concepts_have"))this.concepts_have = param_v.get(param_n.indexOf("concepts_have"));
		
	}
}