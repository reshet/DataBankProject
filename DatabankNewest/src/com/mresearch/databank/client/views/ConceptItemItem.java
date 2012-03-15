package com.mresearch.databank.client.views;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchService.Util;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.shared.MetaUnitEntityItemDTO;
import com.mresearch.databank.shared.SocioResearchDTO;

import java.util.ArrayList;

public class ConceptItemItem extends ConceptItemEntity
{
  public ConceptItemItem(String name, Long id)
  {
    super(name, id);
    this.addItem("Загрузка...");
    //refreshContents();
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
        refreshTaggedEntities();
      }
      protected void callService(AsyncCallback<ArrayList<MetaUnitEntityItemDTO>> cb)
      {
        AdminSocioResearchService.Util.getInstance().getEntityItemSubitemsDTOs(ConceptItemItem.this.getEntity_id(), cb);
      }
    }
    .retry(2);

  }
	private void displayResearchList(ArrayList<SocioResearchDTO> researchList)
	{
		//simpleResearchListItem.removeItems();
		for(SocioResearchDTO dto:researchList)
		{
			ResearchDescItem research_node = new ResearchDescItem(dto);
			research_node.addItem(new ResearchVarList(dto));
			//for(String )
			this.addItem(research_node);
		}
	}
	private void refreshTaggedEntities()
	{
	    new RPCCall<MetaUnitEntityItemDTO>() {
			@Override
			public void onFailure(Throwable arg0) {
			}
			@Override
			public void onSuccess(MetaUnitEntityItemDTO dto) {
				ArrayList<Long> ids = dto.getTagged_entities_ids();
				ArrayList<String> identifiers = dto.getTagged_entities_identifiers();
				final ArrayList<Long> research_ids = new ArrayList<Long>();
				int i = 0;
				for(String str:identifiers)
				{
					if(str.equals("socioresearch"))research_ids.add(ids.get(i));
						i++;
				}
				new RPCCall<ArrayList<SocioResearchDTO>>() {
					@Override
					public void onFailure(Throwable arg0) {
					}
					@Override
					public void onSuccess(ArrayList<SocioResearchDTO> res) {
						displayResearchList(res);
					}

					@Override
					protected void callService(
							AsyncCallback<ArrayList<SocioResearchDTO>> cb) {
						UserSocioResearchService.Util.getInstance().getResearchDTOs(research_ids, cb);
					}
				}.retry(2);
			}
			@Override
			protected void callService(AsyncCallback<MetaUnitEntityItemDTO> cb) {
				AdminSocioResearchService.Util.getInstance().getEntityItemDTO(getEntity_id(), cb);
			  }
		}.retry(2);		
	}
}