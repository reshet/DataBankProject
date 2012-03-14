package com.mresearch.databank.client.views;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchService.Util;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.shared.MetaUnitEntityItemDTO;
import java.util.ArrayList;

public class ConceptItemItem extends ConceptItemEntity
{
  public ConceptItemItem(String name, Long id)
  {
    super(name, id);
    refreshContents();
  }

  public void refreshContents() {
    new RPCCall<ArrayList<MetaUnitEntityItemDTO>>()
    {
      public void onFailure(Throwable caught)
      {
      }

      public void onSuccess(ArrayList<MetaUnitEntityItemDTO> result)
      {
        ConceptItemItem.this.removeItems();
        for (MetaUnitEntityItemDTO dto : result)
        {
          ConceptItemItem.this.addItem(new ConceptItemItem(dto.getV_value(), dto.getId()));
        }
      }

      protected void callService(AsyncCallback<ArrayList<MetaUnitEntityItemDTO>> cb)
      {
        AdminSocioResearchService.Util.getInstance().getEntityItemSubitemsDTOs(ConceptItemItem.this.getEntity_id(), cb);
      }
    }
    .retry(2);
  }
}