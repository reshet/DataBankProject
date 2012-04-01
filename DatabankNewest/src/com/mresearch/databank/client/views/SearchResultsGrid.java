package com.mresearch.databank.client.views;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mresearch.databank.client.event.ShowResearchDetailsEvent;
import com.mresearch.databank.client.event.ShowVarDetailsEvent;
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
    countryGrid.setWidth("100%");
    countryGrid.setHeight("100%");
    countryGrid.setShowAllRecords(Boolean.valueOf(true));
    countryGrid.setWrapCells(Boolean.valueOf(true));
    countryGrid.setCellHeight(46);

    HashMap<String,String> used_map = new HashMap<String,String>();
    final ListGridRecord[] records = new ListGridRecord[hits.size()];
    int i = 0;
    for (JSONObject hit_c : hits)
    {
      JSONObject hit = (JSONObject)hit_c.get("_source");

      ListGridRecord rec = new ListGridRecord();
      rec.setAttribute("_id", ((JSONString)hit_c.get("_id")).stringValue());
      rec.setAttribute("_type", ((JSONString)hit_c.get("_type")).stringValue());
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

    ListGridField[] fields = new ListGridField[2 + used_map.size()];
    ListGridField id_f = new ListGridField("_id", "ID");
    ListGridField type_f = new ListGridField("_type", "Тип сущности");
    int j = 2;
    fields[0] = id_f;
    fields[1] = type_f;

    for (String key : used_map.keySet())
    {
      ListGridField field = new ListGridField(key, (String)used_map.get(key), 120);
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
			if(type.equals("var"))
			{
				bus.fireEvent(new ShowVarDetailsEvent(id));
			}
		}
	});
    add(countryGrid);
  }
}