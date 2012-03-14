package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.opendatafoundation.data.spss.mod.SPSSUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.VarDTO_Detailed;
import com.rednels.ofcgwt.client.ChartWidget;

public class VariableDetailedView extends Composite {

	private static VariableDetailedViewUiBinder uiBinder = GWT
			.create(VariableDetailedViewUiBinder.class);

	interface VariableDetailedViewUiBinder extends
			UiBinder<Widget, VariableDetailedView> {
	}

	@UiField Label varCode,varText;
	@UiField FlexTable codeSchemeTbl,generalizedTbl;
	@UiField HTMLPanel main_html;
//	@UiField Button save_html_btn;
	@UiField HorizontalPanel save_pnl;
	@UiField VerticalPanel graph_pnl;
	
//	Hyperlink link;
//	private FormPanel form;
//	private TextBox hidden_box;
//	private TextBox hidden_box2;
//	private VarDTO_Detailed dto;
//	@UiHandler(value="save_html_btn")
//	public void save_html(ClickEvent e)
//	{
//		String s = main_html.toString();
//		hidden_box.setText(s);
//		hidden_box2.setText("DB_saved_var_distr_"+dto.getCode());
//		form.submit();
//		//s = URL.encode(s);
//		//save_pnl.clear();
//		//save_pnl.add(new HTML("<a href=\"/databank/htmlSave?tosave="+s+"\" target=\"_blank\">Скачать файл!</a>"));
//	}
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
	
	private native JavaScriptObject getJSON()/*-{
		return eval(
		{
			"title":{"text":"ПРодажі Васі", "style":"font-size: 14px; font-family: Verdana; text-align: center;"}, 
			"legend":{"visible":true, "bg_colour":"#fefefe", "position":"right", "border":true, "shadow":true},
			"bg_colour":"#ffffff", 
			"elements":
				[
					{"type":"pie",
					 "tip":"#label# $#val#<br>#percent#", 
					"values":[
						{"value":1000, "label":"AU", "text":"Австралія"},
						{"value":84000, "label":"USA", "text":"USA"},
						{"value":37000, "label":"UK", "text":"United Kingdom"},
						{"value":9000, "label":"JP", "text":"Japan"},
						{"value":32000, "label":"EU", "text":"Europe"}],
					"radius":130, 
					"highlight":"alpha", 
					"animate":true, 
					"gradient-fill":true, 
					"alpha":0.5, 
					"no-labels":true, 
					"colours":["#ff0000","#00aa00","#0000ff","#ff9900","#ff00ff"]
					}
				]
			}
	)
 }-*/;
	public VariableDetailedView(VarDTO_Detailed dto) {
		initWidget(uiBinder.createAndBindUi(this));
//		this.dto = dto;
		//form.a
		save_pnl.add(new SaveHTMLAddon(main_html));
		
		varCode.setText(dto.getCode());
		varText.setText(dto.getLabel());
		ArrayList<Double> codes = dto.getV_label_codes();
		ArrayList<String> values = dto.getV_label_values();
		
		int i = 0;
		codeSchemeTbl.setSize("600px", "350px");
		codeSchemeTbl.setWidget(0, 0, new Label("Код"));
		codeSchemeTbl.setWidget(0, 1, new Label("Текст альтернативы"));
		codeSchemeTbl.setWidget(0, 2, new Label("Частота"));
		//codeSchemeTbl.insertCell(beforeRow, beforeColumn)
		Double total = new Double(0);
		
		for(Double cnt:dto.getDistribution())
		{
			total+=cnt;
		}
        for(Double key:codes)
        {
 		   String label = values.get(i);
           codeSchemeTbl.setWidget(i+1, 0, new Label(key.toString()));
           codeSchemeTbl.setWidget(i+1, 1, new Label(label.toString()));
           
           //NumberFormat formatter = NumberFormat.getInstance();
           NumberFormat formatter = NumberFormat.getFormat("0.00");
          // formatter.
           //formatter.setMaximumFractionDigits(2);
           String myNumber = formatter.format(new Double(dto.getDistribution().get(i)/total)*100);
           
          // new NumberFormat();
           codeSchemeTbl.setWidget(i+1, 2, new Label(myNumber+"%"));
           //com.google.gwt.user.client.ui.MultiWordSuggestOracle c = new MultiWordSuggestOracle();
           //c.requestSuggestions(request, callback)
           //com.google.gwt.user.client.ui.
           i++;
        }
        
        generalizedTbl.setSize("600px", "350px");
		for(int j = 0; j < dto.getGen_var_names().size();j++)
		{
			generalizedTbl.setWidget(j, 0, new Label("как"));
			generalizedTbl.setWidget(j, 1, new VarLink(dto.getGen_vars_ids().get(j),dto.getGen_var_names().get(j)));
			generalizedTbl.setWidget(j, 2, new Label("в исследовании"));
			generalizedTbl.setWidget(j, 3, new ResearchLink(dto.getGen_research_ids().get(j),dto.getGen_research_names().get(j)));
		}
		
		
		//ChartFactory f = new ChartFactory();
		ChartWidget widg = new ChartWidget();
		//widg.s
		//JavaScriptObject obj = getJSON();
		//JSONObject obj_json = new JSONObject(obj);
		JSONObject obj_json = new JSON_Construct(dto).getGraph();
		//obj.toString();
		String json = obj_json.toString();
		widg.setJsonData(json);
		graph_pnl.add(widg);
		
	}
	
	
}
