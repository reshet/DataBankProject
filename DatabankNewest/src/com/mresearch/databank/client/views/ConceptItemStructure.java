package com.mresearch.databank.client.views;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchService.Util;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedStructureDTO;

public class ConceptItemStructure extends ConceptItemEntity
{
  public ConceptItemStructure(String name, Long id,String sys_name,String base_path)
  {
    super(name, id,sys_name,base_path);
  }

  public void refreshContents() {
    new RPCCall<MetaUnitMultivaluedStructureDTO>()
    {
      public void onFailure(Throwable caught)
      {
      }

      
      
      public void onSuccess(MetaUnitMultivaluedStructureDTO result)
      {
        ConceptItemStructure.this.removeItems();
        for (MetaUnitDTO dto : result.getSub_meta_units())
        {
          if ((dto instanceof MetaUnitMultivaluedEntityDTO))
          {
            TreeItem it = ConceptItemStructure.this.composeConceptNode((MetaUnitMultivaluedEntityDTO)dto);
            ConceptItemStructure.this.addItem(it);
          } else {
            if (!(dto instanceof MetaUnitMultivaluedStructureDTO))
              continue;
            TreeItem it = ConceptItemStructure.this.composeConceptsTree((MetaUnitMultivaluedStructureDTO)dto);
            if (it == null) continue; ConceptItemStructure.this.addItem(it);
          }
        }
      }

      protected void callService(AsyncCallback<MetaUnitMultivaluedStructureDTO> cb)
      {
        UserSocioResearchService.Util.getInstance().getMetaUnitMultivaluedStructureDTO(ConceptItemStructure.this.getEntity_id().longValue(), cb);
      }
    }
    .retry(2);
  }
}