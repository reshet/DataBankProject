package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchService.Util;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedStructureDTO;

public class RootConceptsList extends ConceptItemEntity
{
  private final CatalogServiceAsync catalogService = (CatalogServiceAsync)GWT.create(CatalogService.class);
  private String concept_type;
  private String concept_name;

  public RootConceptsList(String concept_type, String concepts_name)
  {
    super(concepts_name, null);
    this.concept_type = concept_type;
    this.concept_name = concepts_name;
    setText(this.concept_name);
    addItem("Загрузка...");
    setStyleName("style.gwt-TreeItem", true);
    refreshContents();
  }

  public void refreshContents()
  {
    new RPCCall<MetaUnitMultivaluedEntityDTO>()
    {
      public void onFailure(Throwable caught)
      {
      }

      public void onSuccess(MetaUnitMultivaluedEntityDTO result)
      {
        RootConceptsList.this.setEntity_id(Long.valueOf(result.getId()));
        RootConceptsList.this.removeItems();
        for (MetaUnitDTO dto : result.getSub_meta_units())
        {
          if ((dto instanceof MetaUnitMultivaluedEntityDTO))
          {
            TreeItem it = RootConceptsList.this.composeConceptNode((MetaUnitMultivaluedEntityDTO)dto);
            RootConceptsList.this.addItem(it);
          } else {
            if (!(dto instanceof MetaUnitMultivaluedStructureDTO))
              continue;
            TreeItem it = RootConceptsList.this.composeConceptsTree((MetaUnitMultivaluedStructureDTO)dto);
            if (it == null) continue; RootConceptsList.this.addItem(it);
          }
        }
      }

      protected void callService(AsyncCallback<MetaUnitMultivaluedEntityDTO> cb)
      {
        UserSocioResearchService.Util.getInstance().getDatabankStructure(concept_type, cb);
      }
    }
    .retry(2);
  }
}