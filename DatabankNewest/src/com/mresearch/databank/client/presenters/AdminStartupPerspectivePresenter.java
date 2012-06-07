package com.mresearch.databank.client.presenters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.event.AddResearchDisabledEvent;
import com.mresearch.databank.client.event.AddResearchDisabledEventHandler;
import com.mresearch.databank.client.event.AddResearchEnabledEvent;
import com.mresearch.databank.client.event.AddResearchEnabledEventHandler;
import com.mresearch.databank.client.event.CreateConceptDisabledEventHandler;
import com.mresearch.databank.client.event.CreateConceptEnabledEvent;
import com.mresearch.databank.client.event.CreateConceptDisabledEvent;
import com.mresearch.databank.client.event.CreateConceptEnabledEventHandler;
import com.mresearch.databank.client.event.CreateConceptEvent;
import com.mresearch.databank.client.event.CreateConceptEventHandler;
import com.mresearch.databank.client.event.ShowResearchDetailsEvent;
import com.mresearch.databank.client.event.ShowResearchDetailsEventHandler;
import com.mresearch.databank.client.event.ShowStartPageMainEvent;
import com.mresearch.databank.client.event.ShowStartPageMainEventHandler;
import com.mresearch.databank.client.event.ShowVarDetailsEvent;
import com.mresearch.databank.client.event.ShowVarDetailsEventHandler;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminArticleService;
import com.mresearch.databank.client.service.AdminArticleServiceAsync;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.client.service.StartPageServiceAsync;
import com.mresearch.databank.client.service.UserAccountServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
//import com.mresearch.databank.client.service.StartPageServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.client.views.AdminResearchAdvancedFilesEditView;
import com.mresearch.databank.client.views.AdminResearchDetailedView;
import com.mresearch.databank.client.views.AdminResearchEditView;
import com.mresearch.databank.client.views.AdminResearchFilesEditView;
import com.mresearch.databank.client.views.AdminResearchGroupEditView;
import com.mresearch.databank.client.views.AdminResearchVarGeneralizeS1View;
import com.mresearch.databank.client.views.ConceptContentsItem;
import com.mresearch.databank.client.views.ConceptItem;
import com.mresearch.databank.client.views.ConceptItemEntity;
import com.mresearch.databank.client.views.ConceptItemItem;
import com.mresearch.databank.client.views.IPickBinder;
import com.mresearch.databank.client.views.PickElementsTableView;
import com.mresearch.databank.client.views.RealVariableDetailedView;
import com.mresearch.databank.client.views.ResearchDescItem;
import com.mresearch.databank.client.views.ResearchVarList;
import com.mresearch.databank.client.views.RootConceptsList;
import com.mresearch.databank.client.views.SearchResultsView;
import com.mresearch.databank.client.views.SimpleResearchList;
import com.mresearch.databank.client.views.TextVariableDetailedView;
import com.mresearch.databank.client.views.UserResearchDetailedView;
import com.mresearch.databank.client.views.UserResearchVar2DDView;
import com.mresearch.databank.client.views.VarDescItem;
import com.mresearch.databank.client.views.VariableDetailedView;
import com.mresearch.databank.client.views.VariableEditView;
import com.mresearch.databank.client.views.addResearchUI;
//import com.mresearch.databank.server.CatalogServiceImpl;
//import com.mresearch.databank.server.domain.SocioResearch;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.CatalogConceptDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedStructureDTO;
import com.mresearch.databank.shared.NewsDTO;
import com.mresearch.databank.shared.NewsSummaryDTO;
import com.mresearch.databank.shared.PublicationDTO_Light;
import com.mresearch.databank.shared.RealVarDTO_Detailed;
import com.mresearch.databank.shared.ResearchBundleDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO_Light;
import com.mresearch.databank.shared.StartupBundleDTO;
import com.mresearch.databank.shared.TextVarDTO_Detailed;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;
import com.mresearch.databank.shared.VarDTO_Light;
import com.mresearch.databank.shared.ICatalogizable;
import com.mresearch.databank.shared.IPickableElement;
import com.mresearch.databank.shared.ConceptBinder;
import com.mresearch.databank.shared.ZaconDTO_Light;


import com.sun.java.swing.plaf.windows.resources.windows;

public class AdminStartupPerspectivePresenter implements Presenter
{
	
	
	 public interface Display {	
		 Widget asWidget();
		 void setLawsAll(ArrayList<ZaconDTO_Light> arr);
		 void setResearchesAll(ArrayList<SocioResearchDTO_Light> arr);
		 void setLawsSel(ArrayList<ZaconDTO_Light> arr);
		 void setResearchesSel(ArrayList<SocioResearchDTO_Light> arr);
		 void setPubsSel(Long howmuch);
		 ArrayList<ZaconDTO_Light> getLawsSel();
		 ArrayList<SocioResearchDTO_Light> getResearchesSel();
		 Long getPubsSel();
		 HasClickHandlers getSaveBtn();
	 }
	
	 
	 private final StartPageServiceAsync rpcService;
	 private final AdminSocioResearchServiceAsync rpcAdminService;
	 private final UserSocioResearchServiceAsync researchUserService = GWT.create(UserSocioResearchService.class);
	 private final AdminArticleServiceAsync articleUserService = GWT.create(AdminArticleService.class);
	 
	 private final SimpleEventBus eventBus;
	 private final Display display;
	 
	 public AdminStartupPerspectivePresenter(StartPageServiceAsync rpcUserService, AdminSocioResearchServiceAsync rpcAdminService, SimpleEventBus eventBus,
		      Display view) {
		    this.rpcService = rpcUserService;
		    this.rpcAdminService = rpcAdminService;
		    this.eventBus = eventBus;
		    this.display = view;
		    bind();
		  }
	@Override
	public void go(HasWidgets container,ArrayList<String> p_names,ArrayList<String> p_values) {
		 container.clear();
		 container.add(display.asWidget());
		 fetchResearches();
	}
	private void fetchResearches()
	{
		new RPCCall<ArrayList<SocioResearchDTO_Light>>() {
			@Override
			public void onFailure(Throwable arg0) {
			}
			@Override
			public void onSuccess(ArrayList<SocioResearchDTO_Light> res) {
				display.setResearchesAll(res);
				fetchLaws();
			}
			@Override
			protected void callService(
					AsyncCallback<ArrayList<SocioResearchDTO_Light>> cb) {
				researchUserService.getResearchSummaries(cb);
			}
		}.retry(2);
	}
	private void fetchLaws()
	{
		new RPCCall<ArrayList<ZaconDTO_Light>>() {
			@Override
			public void onFailure(Throwable arg0) {
			}
			@Override
			public void onSuccess(ArrayList<ZaconDTO_Light> arg0) {
				display.setLawsAll(arg0);
				fetchSelected();
			}
			@Override
			protected void callService(
					AsyncCallback<ArrayList<ZaconDTO_Light>> cb) {
				articleUserService.getZaconsAll(cb);
			}
		}.retry(2);
	}
	private void fetchSelected()
	{
		new RPCCall<StartupBundleDTO>() {

			@Override
			public void onFailure(Throwable arg0) {
				
			}

			@Override
			public void onSuccess(StartupBundleDTO res) {
				display.setLawsSel(res.getImportant_laws());
				display.setResearchesSel(res.getTop_researchs());
				display.setPubsSel(res.getPubs_last_count());
			}

			@Override
			protected void callService(AsyncCallback<StartupBundleDTO> cb) {
				rpcService.getStartupContent(cb);
			}
		}.retry(2);
	}
	public void bind()
	{
		display.getSaveBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				final StartupBundleDTO dto = new StartupBundleDTO();
				dto.setImportant_laws(display.getLawsSel());
				dto.setTop_researchs(display.getResearchesSel());
				dto.setPubs_last_count(display.getPubsSel());
				new RPCCall<Void>() {

					@Override
					public void onFailure(Throwable arg0) {
						Window.alert("Failed!");
					}

					@Override
					public void onSuccess(Void arg0) {
						Window.alert("Success!");
					}

					@Override
					protected void callService(AsyncCallback<Void> cb) {
						rpcAdminService.setStartupContent(dto, cb);
					}
				}.retry(2);
			}
		});
//				if (it instanceof ConceptItemItem)
//				{
//					eventBus.fireEvent(new CreateConceptEnabledEvent(true));
//					display.getCenterPanel().clear();
//					display.getCenterPanel().add(new HTML("<h1>Загрузка, подождите...</h1>"));
//					final long concept_id = ((ConceptItemItem)it).getEntity_id();
//					//ArrayList<SocioResearchDTO> dtos = new ArrayList<SocioResearchDTO>();
//					new RPCCall<ArrayList<SocioResearchDTO_Light>>() {
//						@Override
//						public void onFailure(Throwable caught) {
//							Window.alert("Error getting researh summaries");
//						}
//						@Override
//						public void onSuccess(ArrayList<SocioResearchDTO_Light> result) {
//							final ArrayList<IPickableElement> arr = new ArrayList<IPickableElement>();
//							for(SocioResearchDTO_Light dto:result)
//							{
//								arr.add(dto);
//							}
//							new RPCCall<ArrayList<Long>>() {
//								@Override
//								public void onFailure(Throwable caught) {
//									Window.alert("Error while fetching already selected elements");
//								}
//
//								@Override
//								public void onSuccess(ArrayList<Long> result) {
//									PickElementsTableView pickResearchesToTag = new PickElementsTableView(arr, result,
//										new IPickBinder() {
//											@Override
//											public void processPickChoice( ArrayList<Long> selected_keys) {
//												final ArrayList<Long> sel_keys = selected_keys;
//												new RPCCall<Void>() {
//													@Override
//													public void onFailure(
//															Throwable caught) {
//													}
//													@Override
//													public void onSuccess(Void v) {
//														Window.alert("Концепт успешно распространен!");
//													}
//													@Override
//													protected void callService(
//															AsyncCallback<Void> cb) {
//													rpcAdminService.updateMetaUnitEntityItemLinks(concept_id, sel_keys,"socioresearch", cb);
//													}
//												}.retry(2);
//											}
//											
//											@Override
//											public String getCommandName() {
//												return "Добавить концепт к выбранным!";
//											}
//
//											@Override
//											public String getTitle() {
//												// TODO Auto-generated method stub
//												return "Доступные исследования:";
//											}
//										}			
//									);
//									display.getCenterPanel().clear();
//									display.getCenterPanel().add(pickResearchesToTag);
//									//display.getCenterPanel().add(new HTML("<h2>LOADED</h2>"));
//									//display.getCenterPanel().add(new SearchResultsView(result));
//									
//								}
//
//								@Override
//								protected void callService(
//										AsyncCallback<ArrayList<Long>> cb) {
//									rpcUserService.getEntityItemTaggedEntitiesIDs(concept_id,"socioresearch", cb);
//								}
//							}.retry(2);
//						}
//						@Override
//						protected void callService(
//								AsyncCallback<ArrayList<SocioResearchDTO_Light>> cb) {
//							rpcUserService.getResearchSummaries(cb);
//						}
//					}.retry(2);
//				}
//				
//				else if (it instanceof ConceptItem<?>)
//				{
//					eventBus.fireEvent(new CreateConceptEnabledEvent(false));
//					display.getCenterPanel().clear();
//					display.getCenterPanel().add(new HTML("<h1>Загрузка, подождите...</h1>"));
//					final long concept_id = ((ConceptItem<?>)it).getConcept_id();
//					//ArrayList<SocioResearchDTO> dtos = new ArrayList<SocioResearchDTO>();
//					new RPCCall<ArrayList<SocioResearchDTO_Light>>() {
//						@Override
//						public void onFailure(Throwable caught) {
//							Window.alert("Error getting researh summaries");
//						}
//						@Override
//						public void onSuccess(ArrayList<SocioResearchDTO_Light> result) {
//							final ArrayList<IPickableElement> arr = new ArrayList<IPickableElement>();
//							for(SocioResearchDTO_Light dto:result)
//							{
//								arr.add(dto);
//							}
//							new RPCCall<ArrayList<Long>>() {
//								@Override
//								public void onFailure(Throwable caught) {
//									Window.alert("Error while fetching already selected elements");
//								}
//
//								@Override
//								public void onSuccess(ArrayList<Long> result) {
//									PickElementsTableView pickResearchesToTag = new PickElementsTableView(arr, result,
//										new IPickBinder() {
//											@Override
//											public void processPickChoice( ArrayList<Long> selected_keys) {
//												final ArrayList<Long> sel_keys = selected_keys;
//												new RPCCall<Boolean>() {
//													@Override
//													public void onFailure(
//															Throwable caught) {
//													}
//													@Override
//													public void onSuccess(Boolean v) {
//														Window.alert("Концепт успешно распространен!");
//													}
//													@Override
//													protected void callService(
//															AsyncCallback<Boolean> cb) {
//													rpcCatalogService.setCatalogContentsIDs(concept_id, sel_keys, cb);
//													}
//												}.retry(2);
//											}
//											
//											@Override
//											public String getCommandName() {
//												return "Добавить концепт к выбранным!";
//											}
//
//											@Override
//											public String getTitle() {
//												// TODO Auto-generated method stub
//												return "Доступные исследования:";
//											}
//										}			
//									);
//									display.getCenterPanel().clear();
//									display.getCenterPanel().add(pickResearchesToTag);
//									//display.getCenterPanel().add(new HTML("<h2>LOADED</h2>"));
//									//display.getCenterPanel().add(new SearchResultsView(result));
//									
//								}
//
//								@Override
//								protected void callService(
//										AsyncCallback<ArrayList<Long>> cb) {
//									rpcCatalogService.getCatalogContentsIDs(concept_id, cb);
//								}
//							}.retry(2);
//						}
//						@Override
//						protected void callService(
//								AsyncCallback<ArrayList<SocioResearchDTO_Light>> cb) {
//							rpcUserService.getResearchSummaries(cb);
//						}
//					}.retry(2);
//				}

		
//				else if (it instanceof ConceptItem<?>)
//				{
//					try{
//						//expecting there only SocioResearch concepts ((( not safe but eatable;
//						@SuppressWarnings("unchecked")
//						ConceptItem<SocioResearchDTO_Light> ct = (ConceptItem<SocioResearchDTO_Light>)it;
//						if (!ct.isBinderAttached())
//						{
//							ct.attachCBinder(new ConceptBinder<SocioResearchDTO_Light>(){
//								@Override
//								public void loadContents(
//										final ArrayList<Long> keys) {
//									new RPCCall<ArrayList<SocioResearchDTO_Light>>() {
//
//										@Override
//										public void onFailure(Throwable caught) {
//											Window.alert("Error fetching research dtos by ids");	
//										}
//
//										@Override
//										public void onSuccess(ArrayList<SocioResearchDTO_Light> result) {
//											ArrayList<SocioResearchDTO_Light> handler = getDtos_handler();
//											handler.clear();
//											for(SocioResearchDTO_Light dto:result)
//											{
//												//if (!handler.contains(dto))
//													handler.add(dto);
//											}
//											setLoaded(true);
//										}
//
//										@Override
//										protected void callService(
//												AsyncCallback<ArrayList<SocioResearchDTO_Light>> cb) {
//											rpcUserService.getResearchDTOs(keys, cb);
//										}
//									}.retry(3);
//								}
//
//								@Override
//								public ConceptContentsItem composeContentsItem(
//										SocioResearchDTO_Light dto) {
//									ResearchDescItem item = new ResearchDescItem(dto);
//									ResearchVarList research_node = new ResearchVarList(dto);
//									item.addItem(research_node);
//									return item;
//								}
//								
//								});
//						}
//						
//						ct.refreshContents();
//					}
//					finally{}	
//				}

	}
}
