package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.shared.CatalogConceptDTO;
import com.mresearch.databank.shared.ICatalogizationConceptBindable;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.ICatalogizable;
import com.mresearch.databank.shared.ConceptBinder;



public class ConceptItem<ContentsType extends ICatalogizable> extends TreeItem implements ICatalogizationConceptBindable<ContentsType>{
	private Long concept_id;
	public  ContentsType TYPE;
	public ContentsType getTYPE() {
		return TYPE;
	}
	private String contents_class_name;
	private ArrayList<Long> contents_keys;
	private ArrayList<ContentsType> contents_dtos = new ArrayList<ContentsType>();
	private final CatalogServiceAsync catalogService = GWT
		    .create(CatalogService.class);
	private ConceptBinder<ContentsType> CBinder;
	public class LoadMonitor
	{
		public void loadSucess()
		{
			refreshDTOs();
		}
	}
	public void attachCBinder(ConceptBinder<ContentsType> binder)
	{
		if (this.CBinder == null)
			this.CBinder = binder;
	}
	private LoadMonitor loadMonitor = new LoadMonitor();
	public ConceptItem(CatalogConceptDTO dto)
	{
		super();
		this.concept_id = dto.getId();
		this.setText(dto.getName());
		this.contents_class_name = dto.getC_type();
		this.addItem("Загрузка...");
	}
	public long getConcept_id() {
		return concept_id;
	}
	private void refreshDTOs()
	{
		for (int i = 0; i < getChildCount();i++)
		{
			if (getChild(i) instanceof ICatalogizable) removeItem(getChild(i));
		}
		for(ContentsType dto:getContents_dtos())
		{
			addItem(CBinder.composeContentsItem(dto));
		}
	}
	@Override
	public void refreshContents() {
		this.removeItems();
		this.addItem("Загрузка...");
		new RPCCall<ArrayList<CatalogConceptDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching subconcepts");
			}
			@Override
			public void onSuccess(ArrayList<CatalogConceptDTO> result) {
				final ArrayList<CatalogConceptDTO> concepts_arr = result;
				new RPCCall<ArrayList<Long>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error while fetching catalog contents ids");
					}
					@Override
					public void onSuccess(ArrayList<Long> result) {
						removeItems();
						for (int i = 0; i < getChildCount();i++)
						{
							if (getChild(i) instanceof ConceptItem<?>) removeItem(getChild(i));
						}
						
						for(CatalogConceptDTO dto:concepts_arr)
						{
							ConceptItem<? extends ICatalogizable> concept = ConceptItemMapper.getConceptItemByType(dto);
							addItem(concept);
						}
						
						contents_keys =  result;
						CBinder.setAsyncResultHandler(getContents_dtos());
						CBinder.setLoadMonitor(loadMonitor);
						CBinder.loadContents(getContents_keys());
					}

					@Override
					protected void callService(AsyncCallback<ArrayList<Long>> cb) {
						catalogService.getCatalogContentsIDs(concept_id, cb);
					}
				}.retry(3);
				
				
			}

			@Override
			protected void callService(
					AsyncCallback<ArrayList<CatalogConceptDTO>> cb) {
				catalogService.getCatalogSubConcepts(concept_id, cb);
			}
		}.retry(3);
		
	}
	@Override
	public boolean isBinderAttached() {
		return CBinder!=null;
	}
	public ArrayList<ContentsType> getContents_dtos() {
		return contents_dtos;
	}
	public ArrayList<Long> getContents_keys() {
		return contents_keys;
	}

}
