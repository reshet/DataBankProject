package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminArticleService;
import com.mresearch.databank.client.service.AdminArticleServiceAsync;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.ICatalogizationConcept;
import com.mresearch.databank.shared.SocioResearchDTO;

public class SimpleArticleList extends TreeItem implements ICatalogizationConcept{
	private final AdminArticleServiceAsync articleService = GWT
    .create(AdminArticleService.class);
	public SimpleArticleList()
	{
		super();
		setText("Простой список статей");
		addItem("Загрузка...");
		setStyleName("style.gwt-TreeItem", true);
	}
	@Override
	public void refreshContents() {
		new RPCCall<ArrayList<ArticleDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("error while fetching articles dtos");
			}

			@Override
			public void onSuccess(ArrayList<ArticleDTO> result) {
				SimpleArticleList.this.removeItems();
				for(ArticleDTO dto:result)
				{
					//ResearchDescItem r_desc = new ResearchDescItem(dto);
					//ResearchVarList research_node = new ResearchVarList(dto);
					//for(String )
					//r_desc.addItem(research_node);
					SimpleArticleList.this.addItem(new ArticleDescItem(dto));
					//SimpleResearchList.this.addItem(research_node);
				}
		
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<ArticleDTO>> cb) {
				articleService.getArticlesAll(cb);
			}
		}.retry(3);
//		articleService.
//		catalogService.getResearchList(new AsyncCallback<ArrayList<SocioResearchDTO>>() {
//			@Override
//			public void onSuccess(ArrayList<SocioResearchDTO> result) {
//				SimpleArticleList.this.removeItems();
//				for(SocioResearchDTO dto:result)
//				{
//					ResearchDescItem r_desc = new ResearchDescItem(dto);
//					ResearchVarList research_node = new ResearchVarList(dto);
//					//for(String )
//					r_desc.addItem(research_node);
//					SimpleArticleList.this.addItem(r_desc);
//					//SimpleResearchList.this.addItem(research_node);
//				}
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//			
//				Window.alert("Failure on refresh contents of catalog");
//			}
//		});
	}

}
