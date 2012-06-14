package com.mresearch.databank.client.presenters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
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
import com.mresearch.databank.client.event.AddArticleDisabledEvent;
import com.mresearch.databank.client.event.AddArticleDisabledEventHandler;
import com.mresearch.databank.client.event.AddArticleEnabledEvent;
import com.mresearch.databank.client.event.AddArticleEnabledEventHandler;
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
import com.mresearch.databank.client.service.AdminArticleServiceAsync;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.client.service.StartPageServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.client.views.AdminResearchDetailedView;
import com.mresearch.databank.client.views.AdminResearchEditView;
import com.mresearch.databank.client.views.ArticleDescItem;
import com.mresearch.databank.client.views.ArticleDetailedView;

import com.mresearch.databank.client.views.ConceptContentsItem;
import com.mresearch.databank.client.views.ConceptItem;

import com.mresearch.databank.client.views.IPickBinder;

import com.mresearch.databank.client.views.ConceptItemItem;
import com.mresearch.databank.client.views.PickElementsTableView;
import com.mresearch.databank.client.views.ResearchDescItem;
import com.mresearch.databank.client.views.ResearchVarList;
import com.mresearch.databank.client.views.RootConceptsList;
import com.mresearch.databank.client.views.SearchResultsView;
import com.mresearch.databank.client.views.SimpleArticleList;
import com.mresearch.databank.client.views.SimpleResearchList;
import com.mresearch.databank.client.views.SimpleZaconList;
import com.mresearch.databank.client.views.UserResearchDetailedView;
import com.mresearch.databank.client.views.VarDescItem;
import com.mresearch.databank.client.views.VariableDetailedView;
import com.mresearch.databank.client.views.ZaconDescItem;
import com.mresearch.databank.client.views.ZaconDetailedView;
import com.mresearch.databank.client.views.updateZaconUI;

import com.mresearch.databank.client.views.addResearchUI;
import com.mresearch.databank.client.views.addZaconUI;

import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.CatalogConceptDTO;
import com.mresearch.databank.shared.ConceptBinder;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.NewsDTO;
import com.mresearch.databank.shared.NewsSummaryDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.ZaconDTO;
import com.sun.java.swing.plaf.windows.resources.windows;

public class AdminLawPerspectivePresenter implements Presenter
{
	 public interface Display {
		 HasMouseDownHandlers getTree();
		 HasOpenHandlers<TreeItem> getTreeForOpen();
		 
		 HasClickHandlers getResearchItem(int index);
		 HasClickHandlers getVarItem(int index);
		 void setResearchListData(ArrayList<SocioResearchDTO> data);
		 Widget asWidget();
		 void showLoadingLabel();
		 TreeItem getSelectedItem();
		// String getSelectedConceptID();
		// void showResearchDetailes(AdminResearchDetailedPresenter presenter);
		 VerticalPanel getCenterPanel();
		 HasClickHandlers getEditButton();
		 HasClickHandlers getDeleteButton();
		 HasEnabled getAddArticleBtn();
		 HasClickHandlers getAddArticleBt();
		 HasEnabled getCreateConceptBtn();
		 HasEnabled getDeleteConceptBtn();
		 HasClickHandlers getCreateConceptBt();
		 void showCreateConceptPopup(int x, int y,String c_type);
		 void hideConceptPopup();
		 void setRootConceptUpdateMode(boolean isRoot);
		 void updateRootConcepts();
	 }
	
	 //private final UserSocioResearchServiceAsync rpcUserService;
	 //private final AdminSocioResearchServiceAsync rpcAdminService;
	 private final AdminArticleServiceAsync rpcArticleService;
	 private final CatalogServiceAsync rpcCatalogService;
	 
	 private final SimpleEventBus eventBus;
	 private final Display display;
	 
	 public AdminLawPerspectivePresenter(AdminArticleServiceAsync rpcArticleService, SimpleEventBus eventBus,
		      Display view) {
		    //this.rpcUserService = rpcUserService;
		    //this.rpcAdminService = rpcAdminService;
		    this.rpcArticleService = rpcArticleService;
		  	this.eventBus = eventBus;
		    this.display = view;
		    this.rpcCatalogService = GWT.create(CatalogService.class);
		    bind();
		  }
	@Override
	public void go(HasWidgets container,ArrayList<String> p_names,ArrayList<String> p_values) {
		 container.clear();
		 container.add(display.asWidget());
//		 fetchResearchListData();
	//	 fetchRootCatalogConcepts();
	}
	
	public void bind()
	{
		display.getTree().addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				TreeItem it = display.getSelectedItem();
				if (it instanceof SimpleZaconList)
				{
					//fetchResearchListData();
					eventBus.fireEvent(new AddArticleEnabledEvent());
				}else if (it instanceof ZaconDescItem)
				{
					final ZaconDescItem rv = (ZaconDescItem)it;
					new RPCCall<ZaconDTO>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Error on fetching Zacon det");
						}
						@Override
						public void onSuccess(final ZaconDTO result) {
							display.getCenterPanel().clear();
							
							new RPCCall<MetaUnitMultivaluedEntityDTO>() {

								@Override
								public void onFailure(Throwable arg0) {
								}
								

								@Override
								public void onSuccess(MetaUnitMultivaluedEntityDTO res) {
									//display.getCenterPanel().add(new ZaconDetailedView(result,res,"Все законы").asWidget());
									display.getCenterPanel().clear();
									TreeItem it = display.getSelectedItem();
									Long key = null;
									String name = null;
									String sys_name = null;
									if (it instanceof ConceptItem<?>)
									{
										key = ((ConceptItem<?>)it).getConcept_id();
									}
									if(it instanceof ConceptItemItem)
									{
										ConceptItemItem item = (ConceptItemItem)it;
										name = item.getText();
										key = item.getEntity_id();
										sys_name = item.getEntity_system_name();
									}
									
									display.getCenterPanel().add(new updateZaconUI(result, res,sys_name,key,name));
								}

								@Override
								protected void callService(
										AsyncCallback<MetaUnitMultivaluedEntityDTO> cb) {
									UserSocioResearchService.Util.getInstance().getDatabankStructure("law",cb);
								}
							}.retry(2);
							
							
						}

						@Override
						protected void callService(AsyncCallback<ZaconDTO> cb) {
							rpcArticleService.getZacon(rv.getContents_id(), cb);
						}
					}.retry(2);
					//fetchResearchVarData(it, rv.getResearch_id());
	//				eventBus.fireEvent(new ShowResearchDetailsEvent(rv.getContents_id()));
				//	eventBus.fireEvent(new AddResearchDisabledEvent());
				}else if (it instanceof RootConceptsList)
				{
					//RootConceptsList rcl = (RootConceptsList)it;
					//rcl.refreshContents();
					eventBus.fireEvent(new CreateConceptEnabledEvent(true));
				}
				else if (it instanceof ConceptItemItem)
				{
					//RootConceptsList rcl = (RootConceptsList)it;
					//rcl.refreshContents();
					eventBus.fireEvent(new AddArticleEnabledEvent());
				}
				else if (it instanceof ConceptItem<?>)
				{
//					eventBus.fireEvent(new CreateConceptEnabledEvent(false));
//					eventBus.fireEvent(new AddArticleEnabledEvent());
//					display.getCenterPanel().clear();
//					display.getCenterPanel().add(new HTML("<h1>Загрузка, подождите...</h1>"));
//					final Long concept_id = ((ConceptItem<?>)it).getConcept_id();
//					ArrayList<ZaconDTO> dtos = new ArrayList<ZaconDTO>();
//					
//					new RPCCall<ArrayList<ZaconDTO>>() {
//						@Override
//						public void onFailure(Throwable caught) {
//							Window.alert("Error getting researh summaries");
//						}
//						@Override
//						public void onSuccess(ArrayList<ZaconDTO> result) {
//							final ArrayList<IPickableElement> arr = new ArrayList<IPickableElement>();
//							for(ZaconDTO dto:result)
//							{
//								arr.add(dto);
//							}
//							new RPCCall<ArrayList<String>>() {
//								@Override
//								public void onFailure(Throwable caught) {
//									Window.alert("Error while fetching already selected elements");
//								}
//
//								@Override
//								public void onSuccess(ArrayList<String> result) {
//									PickElementsTableView pickResearchesToTag = new PickElementsTableView(arr, result,
//										new IPickBinder() {
//											@Override
//											public void processPickChoice( ArrayList<String> selected_keys) {
//												final ArrayList<String> sel_keys = selected_keys;
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
//												return "Доступные елементы:";
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
//										AsyncCallback<ArrayList<String>> cb) {
//									rpcCatalogService.getCatalogContentsIDs(concept_id, cb);
//								}
//							}.retry(2);
//						}
//						@Override
//						protected void callService(
//								AsyncCallback<ArrayList<ZaconDTO>> cb) {
//							rpcArticleService.getZaconsAll(cb);
//						}
//					}.retry(2);
				}
				
				if (!(it instanceof RootConceptsList) && !(it instanceof ConceptItem<?>))
				{
					eventBus.fireEvent(new CreateConceptDisabledEvent());
				}
//				if (!(it instanceof SimpleZaconList))
//				{
//					eventBus.fireEvent(new AddZaconDisabledEvent());
//				}
			}
		});
		
		
		display.getTreeForOpen().addOpenHandler(new OpenHandler<TreeItem>() {
			@Override
			public void onOpen(OpenEvent<TreeItem> event) {
				TreeItem it = event.getTarget();
				if (it instanceof SimpleZaconList)
				{
//					fetchResearchListData();
					((SimpleZaconList)it).refreshContents();
					eventBus.fireEvent(new AddArticleEnabledEvent());
				}else if (it instanceof ResearchVarList)
				{
					ResearchVarList rv = (ResearchVarList)it;
//					fetchResearchVarData(it, rv.getResearch_id());
					//eventBus.fireEvent(new ShowResearchDetailsEvent(rv.getResearch_id()));
					eventBus.fireEvent(new AddArticleDisabledEvent());
				}else if (it instanceof RootConceptsList)
				{
					RootConceptsList rcl = (RootConceptsList)it;
					rcl.refreshContents();
					//eventBus.fireEvent(new CreateConceptEnabledEvent());
				}else if (it instanceof ConceptItem<?>)
				{
					try{
						//expecting there only SocioResearch concepts ((( not safe but eatable;
						@SuppressWarnings("unchecked")
						ConceptItem<ZaconDTO> ct = (ConceptItem<ZaconDTO>)it;
						if (!ct.isBinderAttached())
						{
							ct.attachCBinder(new ConceptBinder<ZaconDTO>(){
								@Override
								public void loadContents(
										final ArrayList<Long> keys) {
									new RPCCall<ArrayList<ZaconDTO>>() {

										@Override
										public void onFailure(Throwable caught) {
											Window.alert("Error fetching research dtos by ids");	
										}

										@Override
										public void onSuccess(ArrayList<ZaconDTO> result) {
											ArrayList<ZaconDTO> handler = getDtos_handler();
											handler.clear();
											for(ZaconDTO dto:result)
											{
												//if (!handler.contains(dto))
													handler.add(dto);
											}
											setLoaded(true);
										}

										@Override
										protected void callService(
												AsyncCallback<ArrayList<ZaconDTO>> cb) {
											rpcArticleService.getZaconDTOs_Normal(keys, cb);
										}
									}.retry(3);
								}

								@Override
								public ConceptContentsItem composeContentsItem(
										ZaconDTO dto) {
									ZaconDescItem item = new ZaconDescItem(dto);
									return item;
								}
								
								});
						}
						
						ct.refreshContents();
					}
					finally{}	
				}
			}			
		});
		
		display.getAddArticleBt().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.getCenterPanel().clear();
				TreeItem it = display.getSelectedItem();
				Long key = null;
				String name = null;
				String sys_name = null;
				if (it instanceof ConceptItem<?>)
				{
					key = ((ConceptItem<?>)it).getConcept_id();
				}
				if(it instanceof ConceptItemItem)
				{
					ConceptItemItem item = (ConceptItemItem)it;
					name = item.getText();
					key = item.getEntity_id();
					sys_name = item.getEntity_system_name();
				}
				display.getCenterPanel().add(new addZaconUI(sys_name,key,name));
			}
		});
		eventBus.addHandler(ShowResearchDetailsEvent.TYPE, new ShowResearchDetailsEventHandler() {
			@Override
			public void onShowResearchDetails(ShowResearchDetailsEvent event) {
				display.getCenterPanel().clear();
				display.getCenterPanel().add(new HTML("<h2>Загрузка данных...</h2>"));
			//	fetchResearchDetailes(event.getResearch_id());
			}
		});
		//ShowVarDetailsEventHandler
		
		eventBus.addHandler(AddArticleEnabledEvent.TYPE, new AddArticleEnabledEventHandler() {
			@Override
			public void onAddArticleEnabled(AddArticleEnabledEvent event) {
				display.getAddArticleBtn().setEnabled(true);
			}
		});
		
		eventBus.addHandler(AddArticleDisabledEvent.TYPE, new AddArticleDisabledEventHandler() {
			//@Override
			public void onAddArticleDisabled(AddArticleDisabledEvent event) {
				display.getAddArticleBtn().setEnabled(false);
			}
		});
		eventBus.addHandler(CreateConceptEnabledEvent.TYPE,new CreateConceptEnabledEventHandler() {
			@Override
			public void onCreateConceptEnabled(CreateConceptEnabledEvent event) {
				display.getCreateConceptBtn().setEnabled(true);
				display.getDeleteConceptBtn().setEnabled(true);
				display.setRootConceptUpdateMode(event.isRootConcept());
			}
		});
		eventBus.addHandler(CreateConceptDisabledEvent.TYPE,new CreateConceptDisabledEventHandler() {
			@Override
			public void onCreateConceptDisabled(CreateConceptDisabledEvent event) {
				display.getCreateConceptBtn().setEnabled(false);
				display.getDeleteConceptBtn().setEnabled(false);
			}
		});
		display.getCreateConceptBt().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.showCreateConceptPopup(event.getClientX(), event.getClientY(),"DataLaw");
			}
		});
		display.getDeleteButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TreeItem it = display.getSelectedItem();
				if (it instanceof ConceptItem<?>)
				{
					Long key = ((ConceptItem<?>)it).getConcept_id();
					rpcCatalogService.deleteConcept(key, new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("error deleting concept");
						}

						@Override
						public void onSuccess(Boolean result) {
							Window.alert("Cocnept deleted!");
							display.updateRootConcepts();
						}
					});

				}
			}
		});
		eventBus.addHandler(CreateConceptEvent.TYPE, new CreateConceptEventHandler() {
			@Override
			public void onCreateConcept(CreateConceptEvent event) {
				CatalogConceptDTO dto = event.getDto();
				display.hideConceptPopup();
				updateCatalogueConcept(dto);
			}
		});
		
//		eventBus.addHandler(ShowStartPageMainEvent.TYPE, new ShowStartPageMainEventHandler() {
//			@Override
//			public void onShowStartPageMain(ShowStartPageMainEvent event) {
//				showMainPageZacon();
//			}
//		});
	}

//	private void fetchResearchListData()
//	{
//		//final ArrayList<NewsDTO> newsData = new ArrayList<NewsDTO>();
//		
//		new RPCCall<ArrayList<SocioResearchDTO>>() {
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert("Error fetching " +
//						" news: "
//			            + caught.getMessage());
//			}
//
//			@Override
//			public void onSuccess(ArrayList<SocioResearchDTO> result) {
//				display.setResearchListData(result);
//			}
//
//			@Override
//			protected void callService(
//					AsyncCallback<ArrayList<SocioResearchDTO>> cb) {
//				rpcUserService.getResearchSummaries(cb);
//			}
//		}.retry(3);
//	}
/*	private void fetchRootCatalogConcepts()
	{
		new RPCCall<ArrayList<CatalogConceptDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error while fetching root catalog concepts");
			}

			@Override
			public void onSuccess(ArrayList<CatalogConceptDTO> result) {
				Window.alert("Success fetching root catalog concepts!");
			}

			@Override
			protected void callService(
					AsyncCallback<ArrayList<CatalogConceptDTO>> cb) {
				rpcCatalogService.getRootCatalogConcepts("DataLaw", cb);
			}
		}.retry(3);
	}*/
	private void updateCatalogueConcept(final CatalogConceptDTO dto)
	{
		new RPCCall<CatalogConceptDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error while updating concept");
			}

			@Override
			public void onSuccess(CatalogConceptDTO result) {
				display.updateRootConcepts();
			}

			@Override
			protected void callService(AsyncCallback<CatalogConceptDTO> cb) {
				rpcCatalogService.updateCatalogConcept(dto, cb);
			}
		}.retry(3);
	}

//	private void fetchResearchDetailes(final String id_research)
//	{
//		new RPCCall<SocioResearchDTO>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert("Error on getting research detailes :"+caught.getMessage());
//			}
//
//			@Override
//			public void onSuccess(SocioResearchDTO result) {
//				AdminResearchDetailedView ad_view = new AdminResearchDetailedView(new UserResearchDetailedView(result));
//				AdminResearchEditView ed_view = new AdminResearchEditView(result);
//				AdminResearchDetailedPresenter presenter = new AdminResearchDetailedPresenter(rpcUserService,rpcAdminService, eventBus, ad_view, ed_view);
//				presenter.go(display.getCenterPanel(),null,null);
//			}
//
//			@Override
//			protected void callService(AsyncCallback<SocioResearchDTO> cb) {
//				rpcUserService.getResearch(id_research, cb);
//			}
//		}.retry(3);
//	}
}
