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
import com.mresearch.databank.shared.ICatalogizable;
import com.mresearch.databank.shared.ICatalogizationConcept;
import com.mresearch.databank.shared.SocioResearchDTO;

public class RootConceptsList extends TreeItem implements ICatalogizationConcept{
	private final CatalogServiceAsync catalogService = GWT
    .create(CatalogService.class);
	private String concept_type;
	private String concept_name;
	public RootConceptsList(String concept_type,String concepts_name)
	{
		super();
		this.concept_type = concept_type;
		this.concept_name = concepts_name;
		setText(this.concept_name);
		addItem("Загрузка...");
		setStyleName("style.gwt-TreeItem", true);
	}
	@Override
	public void refreshContents() {
		new RPCCall<ArrayList<CatalogConceptDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failure on refresh contents of root concepts of "+concept_type);
			}

			@Override
			public void onSuccess(ArrayList<CatalogConceptDTO> result) {
				RootConceptsList.this.removeItems();
				for(CatalogConceptDTO dto:result)
				{
//					try {
//						//Class<ICatalogizable<Type>> cl = Class.forName(dto.getC_type());
//						//Object o = Class.forName(dto.getC_type()).newInstance();
//						//ICatalogizable<Type> cata = cl.cast(o);
//						//cata.getType();
//						//ConceptItem<cata.getType()> item = new ConceptItem<Class>(dto);
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					ConceptItem<? extends ICatalogizable> r_concept = ConceptItemMapper.getConceptItemByType(dto);
					//ConceptItem r_concept = new ConceptItem(dto);
					r_concept.addItem("Загрузка...");				
					RootConceptsList.this.addItem(r_concept);
				}
			}
			@Override
			protected void callService(
					AsyncCallback<ArrayList<CatalogConceptDTO>> cb) {
				catalogService.getRootCatalogConcepts(concept_type, cb);
			}
		}.retry(3);
		
	}

}
