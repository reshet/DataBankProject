package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchService.Util;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.shared.FilterBaseDTO;
import com.mresearch.databank.shared.FilterDateDiapasonDTO;
import com.mresearch.databank.shared.FilterDiapasonDTO;
import com.mresearch.databank.shared.FilterMatchDTO;
import com.mresearch.databank.shared.FilterMultiDTO;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitDateDTO;
import com.mresearch.databank.shared.MetaUnitDoubleDTO;
import com.mresearch.databank.shared.MetaUnitIntegerDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedStructureDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;
import java.util.ArrayList;
import java.util.HashMap;
import org.zenika.widget.client.datePicker.DatePicker;

public class RootFilterItemAdvanced extends TreeItem
{
  private VerticalPanel results_viewer;
  private Button doFilterBtn;
  private CheckBox doUseFilters;
  private UserSocioResearchServiceAsync service = (UserSocioResearchServiceAsync)GWT.create(UserSocioResearchService.class);
  private AdminSocioResearchServiceAsync service_adm = AdminSocioResearchService.Util.getInstance();
  private SimpleEventBus bus;
  private final ArrayList<IFilterProvider> allFiltersRegister = new ArrayList();

  private HashMap<String, String> mapping = new HashMap();

  private void buildFiltersTree(TreeItem root, String base_name, ArrayList<MetaUnitDTO> subunits, HashMap<String, String> map)
  {
    for (MetaUnitDTO dto : subunits)
    {
      map.put(base_name + dto.getUnique_name(), dto.getDesc());
      if (((dto instanceof MetaUnitDoubleDTO)) || ((dto instanceof MetaUnitIntegerDTO)))
      {
        TreeItem filt = new TreeItem();
        FilterRealDiapasonView filtSelectionSize = new FilterRealDiapasonView(dto.getDesc(), base_name, dto)
        {
          public FilterDiapasonDTO getFilterDTO() {
            FilterDiapasonDTO dt = new FilterDiapasonDTO(type_to_filter, this.base_name + this.dto.getUnique_name(), getFromValue(), getToValue());
            return dt;
          }

          public boolean isFilterUsed()
          {
            return (!this.from_value.getText().isEmpty()) || (!this.to_value.getText().isEmpty());
          }
        };
        this.allFiltersRegister.add(filtSelectionSize);
        filt.setWidget(filtSelectionSize.asWidget());
        root.addItem(filt);
      }
      if ((dto instanceof MetaUnitStringDTO))
      {
        TreeItem filt = new TreeItem();
        FilterStringContainsView filtSelectionSize = new FilterStringContainsView(dto.getDesc(), base_name, dto)
        {
          public FilterMatchDTO getFilterDTO() {
            FilterMatchDTO dt = new FilterMatchDTO(type_to_filter, this.base_name + this.dto.getUnique_name(), "==", getValue());
            return dt;
          }

          public boolean isFilterUsed()
          {
            return !this.value.getText().isEmpty();
          }
        };
        this.allFiltersRegister.add(filtSelectionSize);
        filt.setWidget(filtSelectionSize.asWidget());
        root.addItem(filt);
      }
      if ((dto instanceof MetaUnitDateDTO))
      {
        TreeItem filt = new TreeItem();
        FilterDataDiapasonView filtFieldStart = new FilterDataDiapasonView(dto.getDesc(), base_name, dto)
        {
          public FilterDiapasonDTO getFilterDTO() {
            FilterDateDiapasonDTO dt = new FilterDateDiapasonDTO(type_to_filter, this.base_name + this.dto.getUnique_name(), getFromValue(), getToValue());
            return dt;
          }

          public boolean isFilterUsed()
          {
            return (!this.from_value.getText().isEmpty()) || (!this.to_value.getText().isEmpty());
          }
        };
        this.allFiltersRegister.add(filtFieldStart);
        filt.setWidget(filtFieldStart.asWidget());
        root.addItem(filt);
      }
      if ((dto instanceof MetaUnitMultivaluedStructureDTO))
      {
        MetaUnitMultivaluedStructureDTO ddto = (MetaUnitMultivaluedStructureDTO)dto;
        TreeItem filt = new TreeItem(ddto.getDesc());
        buildFiltersTree(filt, base_name + ddto.getUnique_name() + "_", ddto.getSub_meta_units(), map);
        root.addItem(filt);
      }
      if (!(dto instanceof MetaUnitMultivaluedEntityDTO))
        continue;
      MetaUnitMultivaluedEntityDTO ddto = (MetaUnitMultivaluedEntityDTO)dto;

      TreeItem filt_gen_selection = new TreeItem();
      FilterMultiMatchView filtGS = new FilterMultiMatchView(ddto.getDesc(), base_name, dto, ddto)
      {
        public FilterBaseDTO getFilterDTO() {
          ArrayList<FilterBaseDTO> filters = new ArrayList();
          ArrayList<String> variants = getVariants();
          int i = 0;
          for (String variant : variants)
          {
            CheckBox cb = (CheckBox)this.root.getChild(i).getWidget();
            if (cb.getValue().booleanValue())
            {
              FilterMatchDTO dt = new FilterMatchDTO(type_to_filter, this.base_name + this.dto.getUnique_name(), "==", variant);
              filters.add(dt);
            }
            i++;
          }
          FilterMultiDTO mult_dto = new FilterMultiDTO(filters);
          return mult_dto;
        }

        public void loadVariants(FilterMultiMatchView.PostProcess postprocess)
        {
          this.variants = this.ddto.getItem_names();
          postprocess.process();
        }

        public boolean isFilterUsed()
        {
          return ((CheckBox)getRoot().getWidget()).getValue().booleanValue();
        }
      };
      filt_gen_selection.setWidget(filtGS.asWidget());
      this.allFiltersRegister.add(filtGS);

      root.addItem(filt_gen_selection);
    }
  }

  private String type_to_filter;
  public RootFilterItemAdvanced(VerticalPanel display,SimpleEventBus ev_bus,String type_to_filter,String filter_caption)
  {
    this.doFilterBtn = new Button("Поехали!");
    this.doUseFilters = new CheckBox(filter_caption);
    this.results_viewer = display;
    this.bus = ev_bus;
    this.type_to_filter = type_to_filter;
    setWidget(this.doUseFilters);

    new RPCCall<MetaUnitMultivaluedEntityDTO>()
    {
      public void onFailure(Throwable caught)
      {
        Window.alert("Error fetching databank structure " + caught.getMessage());
      }

      protected void callService(AsyncCallback<MetaUnitMultivaluedEntityDTO> cb)
      {
        AdminSocioResearchService.Util.getInstance().getDatabankStructure(RootFilterItemAdvanced.this.type_to_filter, cb);
      }

      public void onSuccess(MetaUnitMultivaluedEntityDTO result)
      {
        RootFilterItemAdvanced.this.buildFiltersTree(RootFilterItemAdvanced.this, result.getUnique_name() + "_", result.getSub_meta_units(), RootFilterItemAdvanced.this.mapping);
        RootFilterItemAdvanced.this.addItem(RootFilterItemAdvanced.this.doFilterBtn);
        RootFilterItemAdvanced.this.bindReactions();
      }
    }
    .retry(2);
  }

  private void bindReactions()
  {
    this.doFilterBtn.addClickHandler(new ClickHandler()
    {
      public void onClick(ClickEvent event) {
        RootFilterItemAdvanced.this.doFiltersProcess();
      }
    });
  }

  private void doFiltersProcess()
  {
    ArrayList<FilterBaseDTO> filters = new ArrayList<FilterBaseDTO>();
    for (IFilterProvider provider : this.allFiltersRegister)
    {
      if (provider != null)	
      if (provider.isFilterUsed())
        filters.add(provider.getFilterDTO());
    }
    final String query;
    //String query;
    if (filters.size() > 0)
    {
      JSONObject obj_bool = new JSONObject();
      JSONObject obj_should = new JSONObject();
      JSONArray arr_should = new JSONArray();
      for (int i = 0; i < filters.size(); i++)
      {
        arr_should.set(i, ((FilterBaseDTO)filters.get(i)).getJSONFilter());
      }
      obj_should.put("should", arr_should);
      obj_bool.put("bool", obj_should);

      query = obj_bool.toString();
    }
    else {
      JSONObject obj_b = new JSONObject();
      JSONObject obj_text = new JSONObject();
      obj_text.put("_all", new JSONString("*"));
      obj_b.put("text", obj_text);

      query = obj_b.toString();
    }
    int b = 2;

    String filt_type = new String(type_to_filter);
    if(type_to_filter.equals("socioresearch")) filt_type="research";
    final String [] types_to_search = {filt_type};
    new RPCCall<String>()
    {
      public void onFailure(Throwable caught)
      {
        RootFilterItemAdvanced.this.results_viewer.clear();
        RootFilterItemAdvanced.this.results_viewer.add(new Label("Error on performing search!    " + caught.getMessage()));
      }

      public void onSuccess(String result)
      {
    	
        RootFilterItemAdvanced.this.results_viewer.clear();
        if(result.equals("Error"))
        {
        	 RootFilterItemAdvanced.this.results_viewer.add(new HTML("<H2>ПОИСКОВЫЙ ЗАПРОС:</H2><br><p>" + query + "</p>"));
             RootFilterItemAdvanced.this.results_viewer.add(new HTML("<H3>ОТВЕТ ДВИЖКА:</H3><br><p>" + result + "</p>"));
             return;
        }
        ArrayList<JSONObject> hiters = new ArrayList<JSONObject>();
        JSONObject res = (JSONObject)JSONParser.parse(result);
        JSONObject hits = (JSONObject)res.get("hits");
        JSONNumber total = (JSONNumber)hits.get("total");
        Integer tot = Integer.valueOf((int)total.getValue());
        JSONArray hits_arr = (JSONArray)hits.get("hits");
        for (int i = 0; i < hits_arr.size(); i++)
        {
          JSONObject hit = (JSONObject)hits_arr.get(i);
          hiters.add(hit);
        }

        RootFilterItemAdvanced.this.results_viewer.add(new HTML("<H2>ПОИСКОВЫЙ ЗАПРОС:</H2><br><p>" + query + "</p>"));
        RootFilterItemAdvanced.this.results_viewer.add(new HTML("<H3>ОТВЕТ ДВИЖКА:</H3><br><p>" + result + "</p>"));

        RootFilterItemAdvanced.this.results_viewer.add(new SearchResultsGrid(bus,tot, hiters, RootFilterItemAdvanced.this.mapping));
      }

      protected void callService(AsyncCallback<String> cb)
      {
        RootFilterItemAdvanced.this.service.doIndexSearch(query,types_to_search, cb);
      }
    }
    .retry(2);
  }
}