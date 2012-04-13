package com.mresearch.databank.client.views;

import java.util.Date;
import java.util.Random;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.mresearch.databank.shared.VarDTO_Detailed;

public class JSON_Construct
{
	private VarDTO_Detailed dto;
	public JSON_Construct(VarDTO_Detailed dto)
	{
		this.dto = dto;
		rand.setSeed(new Date().getTime());
	}
	public JSONObject getGraph()
	{
		JSONObject json = new JSONObject();
		json.put("title", getTitle());
		json.put("legend", getLegend());
		json.put("bg_colour", new JSONString("#ffffff"));
		JSONArray elements = new JSONArray();
		elements.set(0, getDataElemObj());
		json.put("elements", elements);
		return json;
	}
	private JSONObject getTitle()
	{
		JSONObject obj = new JSONObject();
		obj.put("text", new JSONString(dto.getLabel()));
		obj.put("style", new JSONString("font-size: 14px; font-family: Verdana; text-align: center;"));
		return obj;
	}
	private JSONObject getLegend()
	{
		JSONObject obj = new JSONObject();
		obj.put("visible", JSONBoolean.getInstance(true));
		obj.put("bg_colour", new JSONString("#fefefe"));
		obj.put("position", new JSONString("right"));
		obj.put("border", JSONBoolean.getInstance(true));
		obj.put("shadow", JSONBoolean.getInstance(true));
		return obj;
	}
	private JSONArray getDataValues()
	{
		JSONArray arr = new JSONArray();
		int i = 0;
		for(String value:dto.getV_label_values())
		{
			Double cd = dto.getV_label_codes().get(i);
			Double dist = dto.getDistribution().get(i);
			JSONObject ob = new JSONObject();
			ob.put("value", new JSONNumber(dist));
			ob.put("label", new JSONString(String.valueOf(cd)));
			ob.put("text", new JSONString(String.valueOf(cd)+" "+value));
			arr.set(i,ob);
			i++;
		}
		return arr;
	}
	private Random rand = new Random();
	private String getRandomColorElem()
	{
		String [] arr = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
		int index = rand.nextInt(16);
		return arr[index];
	}
	private String getRandomColour()
	{
		String color = "#";
		for(int i = 0; i < 6;i++)
		{
			color+=getRandomColorElem();
		}
		return color;
	}
	private JSONArray getRandomColours()
	{
		JSONArray arr = new JSONArray();
		int i = 0;
		for(String value:dto.getV_label_values())
		{
			arr.set(i,new JSONString(getRandomColour()));
			i++;
		}
		return arr;
	}
	private JSONObject getDataElemObj()
	{
		JSONObject obj = new JSONObject();
		obj.put("type", new JSONString("pie"));
		obj.put("tip", new JSONString("#label# $#val#<br>#percent#"));
		obj.put("values", getDataValues());
		obj.put("radius", new JSONNumber(210));
		obj.put("highlight", new JSONString("alpha"));
		obj.put("animate", JSONBoolean.getInstance(true));
		obj.put("gradient-fill", JSONBoolean.getInstance(true));
		obj.put("alpha", new JSONNumber(0.7));
		obj.put("no-labels", JSONBoolean.getInstance(false));
		obj.put("colours", getRandomColours());
		return obj;
	}
}
