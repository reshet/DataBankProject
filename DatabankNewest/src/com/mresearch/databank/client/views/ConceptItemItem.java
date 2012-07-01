package com.mresearch.databank.client.views;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminArticleService;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchService.Util;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.shared.MetaUnitEntityItemDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO_Light;
import com.mresearch.databank.shared.VarDTO_Light;
import com.mresearch.databank.shared.ZaconDTO_Light;

import java.util.ArrayList;

public class ConceptItemItem extends ConceptItemEntity
{
  public ConceptItemItem(String name, Long id,String sys_name,String base_path)
  {
    super(name, id,sys_name,base_path);
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
          ConceptItemItem.this.addItem(new ConceptItemItem(dto.getV_value(), dto.getId(),getEntity_system_name(),getCatalog_path()));
        }
        refreshTaggedEntities();
      }
      protected void callService(AsyncCallback<ArrayList<MetaUnitEntityItemDTO>> cb)
      {
        UserSocioResearchService.Util.getInstance().getEntityItemSubitemsDTOs(ConceptItemItem.this.getEntity_id(), cb);
      }
    }
    .retry(2);

  }
	private void displayResearchList(ArrayList<SocioResearchDTO_Light> researchList)
	{
		//simpleResearchListItem.removeItems();
		//TreeItem res = new TreeItem("Исследования");
		for(SocioResearchDTO_Light dto:researchList)
		{
			ResearchDescItem research_node = new ResearchDescItem(dto);
			research_node.addItem(new ResearchVarList(dto));
			//for(String )
			this.addItem(research_node);
		}
		//this.addItem(res);
	}
	private void displayVarList(ArrayList<VarDTO_Light> varList)
	{
		//simpleResearchListItem.removeItems();
		//TreeItem res = new TreeItem("Переменные");
		for(VarDTO_Light dto:varList)
		{
			VarDescItem var_node = new VarDescItem(dto);
			//var_node.addItem(new ResearchVarList(dto));
			//for(String )
			this.addItem(var_node);
		}
	//	this.addItem(res);
	}
	private void displayZaconList(ArrayList<ZaconDTO_Light> zList)
	{
		//simpleResearchListItem.removeItems();
		//TreeItem res = new TreeItem("Переменные");
		for(ZaconDTO_Light dto:zList)
		{
			ZaconDescItem var_node = new ZaconDescItem(dto);
			//var_node.addItem(new ResearchVarList(dto));
			//for(String )
			this.addItem(var_node);
		}
		//this.addItem(res);
	}
	
	private ArrayList<Long> research_ids = new ArrayList<Long>();
	private ArrayList<Long> var_ids = new ArrayList<Long>();
	private ArrayList<Long> law_ids = new ArrayList<Long>();
	
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
				 research_ids = new ArrayList<Long>();
				 var_ids = new ArrayList<Long>();
				 law_ids = new ArrayList<Long>();
				int i = 0;
				for(String str:identifiers)
				{
					if(str.equals("socioresearch"))research_ids.add(ids.get(i));
					if(str.equals("sociovar"))var_ids.add(ids.get(i));
					if(str.equals("law"))law_ids.add(ids.get(i));
						i++;
				}
				refreshResearches(research_ids);
				refreshVars(var_ids);
				refreshLaws(law_ids);
			}
			@Override
			protected void callService(AsyncCallback<MetaUnitEntityItemDTO> cb) {
				UserSocioResearchService.Util.getInstance().getEntityItemDTO(getEntity_id(), cb);
			  }
		}.retry(2);		
	}
	
	public void refreshTaggedEntitiesIDs()
	{
		
		
		
		
		
	    new RPCCall<MetaUnitEntityItemDTO>() {
			@Override
			public void onFailure(Throwable arg0) {
			}
			@Override
			public void onSuccess(MetaUnitEntityItemDTO dto) {
				ArrayList<Long> ids = dto.getTagged_entities_ids();
				ArrayList<String> identifiers = dto.getTagged_entities_identifiers();
				 research_ids = new ArrayList<Long>();
				 var_ids = new ArrayList<Long>();
				 law_ids = new ArrayList<Long>();
				int i = 0;
				for(String str:identifiers)
				{
					if(str.equals("socioresearch"))research_ids.add(ids.get(i));
					if(str.equals("sociovar"))var_ids.add(ids.get(i));
					if(str.equals("law"))law_ids.add(ids.get(i));
						i++;
				}
				//refreshResearches(research_ids);
				//refreshVars(var_ids);
				//refreshLaws(law_ids);
			}
			@Override
			protected void callService(AsyncCallback<MetaUnitEntityItemDTO> cb) {
				UserSocioResearchService.Util.getInstance().getEntityItemDTO(getEntity_id(), cb);
			  }
		}.retry(2);		
	}
	public ArrayList<Long> getResearch_ids() {
		return research_ids;
	}

	public ArrayList<Long> getVar_ids() {
		return var_ids;
	}

	public ArrayList<Long> getLaw_ids() {
		return law_ids;
	}

	private void refreshResearches(final ArrayList<Long> research_ids)
	{
		if(research_ids.size()>0)
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
	}
	
	private void refreshVars(final ArrayList<Long> var_ids)
	{
		if(var_ids.size()>0)
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
	private void refreshLaws(final ArrayList<Long> law_ids)
	{
		if(law_ids.size()>0)
		{
			new RPCCall<ArrayList<ZaconDTO_Light>>() {
				@Override
				public void onFailure(Throwable arg0) {
				}
				@Override
				public void onSuccess(ArrayList<ZaconDTO_Light> res) {
					displayZaconList(res);
				}
	
				@Override
				protected void callService(
						AsyncCallback<ArrayList<ZaconDTO_Light>> cb) {
					AdminArticleService.Util.getInstance().getZaconDTOs(law_ids, cb);
				}
			}.retry(2);
		}
	}
}