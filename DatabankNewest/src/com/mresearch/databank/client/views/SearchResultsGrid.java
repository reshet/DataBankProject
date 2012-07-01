package com.mresearch.databank.client.views;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mresearch.databank.client.event.ShowConsultationDetailsEvent;
import com.mresearch.databank.client.event.ShowConsultationIndexEvent;
import com.mresearch.databank.client.event.ShowPublicationDetailsEvent;
import com.mresearch.databank.client.event.ShowResearchDetailsEvent;
import com.mresearch.databank.client.event.ShowVarDetailsEvent;
import com.mresearch.databank.client.event.ShowZaconDetailsEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.tree.TreeGrid;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultsGrid extends VerticalPanel
{
  private Integer total;
  private ArrayList<JSONObject> hits;
  private HashMap<String, String> map;
  private SimpleEventBus bus;
  public SearchResultsGrid(SimpleEventBus ev_bus,Integer total, ArrayList<JSONObject> hits, HashMap<String, String> map)
  {
    this.map = map;
    this.hits = hits;
    this.total = total;
    this.bus = ev_bus;
    //TreeGrid g = new TreeGrid();
    //g.setShowFilterEditor(true);
    //g.setFilt
    
    
    

    ListGrid countryGrid = new ListGrid();
    countryGrid.setWidth("1024");
    countryGrid.setHeight("100%");
    countryGrid.setShowAllRecords(Boolean.valueOf(true));
    countryGrid.setWrapCells(Boolean.valueOf(true));
    countryGrid.setCellHeight(46);
    countryGrid.setAutoFitFieldWidths(true);

    HashMap<String,String> used_map = new HashMap<String,String>();
    final ListGridRecord[] records = new ListGridRecord[hits.size()];
    int i = 0;
    for (JSONObject hit_c : hits)
    {
      JSONObject hit = (JSONObject)hit_c.get("_source");

      ListGridRecord rec = new ListGridRecord();
      rec.setAttribute("_id", ((JSONString)hit_c.get("_id")).stringValue());
      rec.setAttribute("_type", ((JSONString)hit_c.get("_type")).stringValue());
      rec.setAttribute("_type_vis", ((JSONString)hit_c.get("_type")).stringValue());
      
      if(rec.getAttribute("_type_vis").equals("research"))rec.setAttribute("_type_vis", "исследование");
      if(rec.getAttribute("_type_vis").equals("sociovar"))rec.setAttribute("_type_vis", "переменная");
      if(rec.getAttribute("_type_vis").equals("law"))rec.setAttribute("_type_vis", "закон");
      if(rec.getAttribute("_type_vis").equals("publication"))rec.setAttribute("_type_vis", "публикация");
      if(rec.getAttribute("_type_vis").equals("consultation"))rec.setAttribute("_type_vis", "консультация");
      
      
      for (String key : map.keySet())
      {
        if (!hit.containsKey(key))
          continue;
        if(hit.get(key) instanceof JSONString)
        	rec.setAttribute(key, ((JSONString)hit.get(key)).stringValue());
        if(hit.get(key) instanceof JSONArray)
        	rec.setAttribute(key, ((JSONArray)hit.get(key)).toString());
        
        used_map.put(key, (String)map.get(key));
      }

      records[(i++)] = rec;
    }

    ListGridField[] fields = new ListGridField[1 + used_map.size()];
    //ListGridField id_f = new ListGridField("_id", "ID");
    ListGridField type_f = new ListGridField("_type_vis", "Тип сущности");
    
    //id_f.setWidth(25);
    type_f.setWidth(140);
    
    int j = 1;
    //fields[0] = id_f;
    fields[0] = type_f;

    for (String key : used_map.keySet())
    {
      ListGridField field = new ListGridField(key, (String)used_map.get(key));
     // field.setA
      fields[(j++)] = field;
    }

    
    
    
    
    
    
    
    
    countryGrid.setFields(fields);

    countryGrid.setData(records);
    countryGrid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
		@Override
		public void onCellDoubleClick(CellDoubleClickEvent event) {
			int row = event.getRowNum();
			int id = Integer.parseInt(records[row].getAttributeAsString("_id"));
			String type = records[row].getAttributeAsString("_type");
			if(type.equals("research"))
			{
				bus.fireEvent(new ShowResearchDetailsEvent(id));
			}
			if(type.equals("sociovar"))
			{
				bus.fireEvent(new ShowVarDetailsEvent(id));
			}
			if(type.equals("law"))
			{
				bus.fireEvent(new ShowZaconDetailsEvent(id));
			}
			if(type.equals("publication"))
			{
				bus.fireEvent(new ShowPublicationDetailsEvent(id));
			}
			if(type.equals("consultation"))
			{
				bus.fireEvent(new ShowConsultationDetailsEvent(id));
			}
		}
	});
    add(countryGrid);
  }
}