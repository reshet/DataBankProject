package com.mresearch.databank.client.views;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tree.TreeGrid;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultsGenericGrid extends VerticalPanel
{
  private Integer total;
  private ArrayList<JSONObject> hits;
  private HashMap<String, String> map;

  public SearchResultsGenericGrid(Integer total, ArrayList<JSONObject> hits)
  {
    this.hits = hits;
    this.total = total;
    
    //TreeGrid g = new TreeGrid();
    //g.setShowFilterEditor(true);
    //g.setFilt
    
    

    ListGrid countryGrid = new ListGrid();
    countryGrid.setWidth("100%");
    countryGrid.setHeight("100%");
    countryGrid.setShowAllRecords(Boolean.valueOf(true));
    countryGrid.setWrapCells(Boolean.valueOf(true));
    countryGrid.setCellHeight(46);

    ListGridRecord[] records = new ListGridRecord[hits.size()];
    int i = 0;
    for (JSONObject hit_c : hits)
    {
      JSONObject hit = (JSONObject)hit_c.get("_source");

      ListGridRecord rec = new ListGridRecord();
      rec.setAttribute("_id", ((JSONString)hit_c.get("_id")).stringValue());
      rec.setAttribute("_type", ((JSONString)hit_c.get("_type")).stringValue());
      rec.setAttribute("_contents", hit.toString());
      records[(i++)] = rec;
    }

    ListGridField[] fields = new ListGridField[3];
    ListGridField id_f = new ListGridField("_id", "ID");
    ListGridField type_f = new ListGridField("_type", "Тип сущности");
    ListGridField contents_f = new ListGridField("_contents", "Содержание");

    fields[0] = id_f;
    fields[1] = type_f;
    fields[2] = contents_f;

    countryGrid.setFields(fields);
    countryGrid.setData(records);
    add(countryGrid);
  }
}