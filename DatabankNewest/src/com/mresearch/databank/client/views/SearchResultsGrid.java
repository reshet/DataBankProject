package com.mresearch.databank.client.views;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultsGrid extends VerticalPanel
{
  private Integer total;
  private ArrayList<JSONObject> hits;
  private HashMap<String, String> map;

  public SearchResultsGrid(Integer total, ArrayList<JSONObject> hits, HashMap<String, String> map)
  {
    this.map = map;
    this.hits = hits;
    this.total = total;

    ListGrid countryGrid = new ListGrid();
    countryGrid.setWidth(800);
    countryGrid.setHeight(600);
    countryGrid.setShowAllRecords(Boolean.valueOf(true));
    countryGrid.setWrapCells(Boolean.valueOf(true));
    countryGrid.setCellHeight(56);

    HashMap<String,String> used_map = new HashMap<String,String>();
    ListGridRecord[] records = new ListGridRecord[hits.size()];
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
        rec.setAttribute(key, ((JSONString)hit.get(key)).stringValue());
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
    add(countryGrid);
  }
}