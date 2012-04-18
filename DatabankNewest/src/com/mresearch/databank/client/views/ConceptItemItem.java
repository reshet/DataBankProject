package com.mresearch.databank.client.views;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchService.Util;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.shared.MetaUnitEntityItemDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO_Light;
import com.mresearch.databank.shared.VarDTO_Light;

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
	//	Window.alert("Starts Refreshing!");
	  new RPCCall<ArrayList<MetaUnitEntityItemDTO>>()
    {
      public void onFailure(Throwable caught)
      {
    	  Window.alert("Error:"+caught.getMessage());
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
	private void displayResearchList(ArrayList<SocioResearchDTO_Light> researchList)
	{
		//simpleResearchListItem.removeItems();
		TreeItem res = new TreeItem("Исследования");
		for(SocioResearchDTO_Light dto:researchList)
		{
			ResearchDescItem research_node = new ResearchDescItem(dto);
			research_node.addItem(new ResearchVarList(dto));
			//for(String )
			res.addItem(research_node);
		}
		this.addItem(res);
	}
	private void displayVarList(ArrayList<VarDTO_Light> varList)
	{
		//simpleResearchListItem.removeItems();
		TreeItem res = new TreeItem("Переменные");
		for(VarDTO_Light dto:varList)
		{
			VarDescItem var_node = new VarDescItem(dto);
			//var_node.addItem(new ResearchVarList(dto));
			//for(String )
			res.addItem(var_node);
		}
		this.addItem(res);
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
				final ArrayList<Long> var_ids = new ArrayList<Long>();
				
				int i = 0;
				for(String str:identifiers)
				{
					if(str.equals("socioresearch"))research_ids.add(ids.get(i));
					if(str.equals("sociovar"))var_ids.add(ids.get(i));
						i++;
				}
				refreshResearches(research_ids);
				refreshVars(var_ids);
			}
			@Override
			protected void callService(AsyncCallback<MetaUnitEntityItemDTO> cb) {
				AdminSocioResearchService.Util.getInstance().getEntityItemDTO(getEntity_id(), cb);
			  }
		}.retry(2);		
	}
	
	private void refreshResearches(final ArrayList<Long> research_ids)
	{
		new RPCCall<ArrayList<SocioResearchDTO_Light>>() {
			@Override
			public void onFailure(Throwable arg0) {
			}
			@Override
			public void onSuccess(ArrayList<SocioResearchDTO_Light> res) {
				displayResearchList(res);
			}

			@Override
			protected void callService(
					AsyncCallback<ArrayList<SocioResearchDTO_Light>> cb) {
				UserSocioResearchService.Util.getInstance().getResearchDTOs(research_ids, cb);
			}
		}.retry(2);
	}
	
	private void refreshVars(final ArrayList<Long> var_ids)
	{
		new RPCCall<ArrayList<VarDTO_Light>>() {
			@Override
			public void onFailure(Throwable arg0) {
			}
			@Override
			public void onSuccess(ArrayList<VarDTO_Light> res) {
				displayVarList(res);
			}

			@Override
			protected void callService(
					AsyncCallback<ArrayList<VarDTO_Light>> cb) {
				UserSocioResearchService.Util.getInstance().getVarDTOs(var_ids, cb);
			}
		}.retry(2);
	}
}