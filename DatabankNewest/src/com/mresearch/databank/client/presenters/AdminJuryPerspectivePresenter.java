package com.mresearch.databank.client.presenters;

import java.util.ArrayList;

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
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.event.AddArticleDisabledEvent;
import com.mresearch.databank.client.event.AddArticleDisabledEventHandler;
import com.mresearch.databank.client.event.AddArticleEnabledEvent;
import com.mresearch.databank.client.event.AddArticleEnabledEventHandler;
import com.mresearch.databank.client.event.CreateConceptDisabledEvent;
import com.mresearch.databank.client.event.CreateConceptDisabledEventHandler;
import com.mresearch.databank.client.event.CreateConceptEnabledEvent;
import com.mresearch.databank.client.event.CreateConceptEnabledEventHandler;
import com.mresearch.databank.client.event.CreateConceptEvent;
import com.mresearch.databank.client.event.CreateConceptEventHandler;
import com.mresearch.databank.client.event.ShowResearchDetailsEvent;
import com.mresearch.databank.client.event.ShowResearchDetailsEventHandler;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminArticleServiceAsync;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.views.ConceptContentsItem;
import com.mresearch.databank.client.views.ConceptItem;
import com.mresearch.databank.client.views.ConceptItemItem;
import com.mresearch.databank.client.views.ConsultationDescItem;
import com.mresearch.databank.client.views.ConsultationDetailedView;
import com.mresearch.databank.client.views.updateConsultationUI;
//import com.mresearch.databank.client.views.ConsultationDescItem;
//import com.mresearch.databank.client.views.ConsultationDetailedView;
import com.mresearch.databank.client.views.ResearchVarList;
import com.mresearch.databank.client.views.RootConceptsList;
import com.mresearch.databank.client.views.SimpleConsultationList;
//import com.mresearch.databank.client.views.addConsultationUI;
import com.mresearch.databank.shared.CatalogConceptDTO;
import com.mresearch.databank.shared.ConceptBinder;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.ConsultationDTO;
import com.mresearch.databank.shared.SocioResearchDTO;

public class AdminJuryPerspectivePresenter implements Presenter
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
	 
	 public AdminJuryPerspectivePresenter(AdminArticleServiceAsync rpcArticleService, SimpleEventBus eventBus,
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
				if (it instanceof SimpleConsultationList)
				{
					//fetchResearchListData();
					eventBus.fireEvent(new AddArticleEnabledEvent());
				}else if (it instanceof ConsultationDescItem)
				{
					final ConsultationDescItem rv = (ConsultationDescItem)it;
					display.getCenterPanel().clear();
					display.getCenterPanel().add(new updateConsultationUI(rv.getContents_id(),null, (long) 0, null));
					
//					new RPCCall<ConsultationDTO>() {
//
//						@Override
//						public void onFailure(Throwable caught) {
//							Window.alert("Error on fetching Consultation det");
//						}
//						@Override
//						public void onSuccess(final ConsultationDTO result) {
//							display.getCenterPanel().clear();
//							
//							new RPCCall<MetaUnitMultivaluedEntityDTO>() {
//
//								@Override
//								public void onFailure(Throwable arg0) {
//								}
//
//								@Override
//								public void onSuccess(MetaUnitMultivaluedEntityDTO res) {
//									display.getCenterPanel().add(new ConsultationDetailedView(result,res,"Все публикации").asWidget());
//								}
//
//								@Override
//								protected void callService(
//										AsyncCallback<MetaUnitMultivaluedEntityDTO> cb) {
//									UserSocioResearchService.Util.getInstance().getDatabankStructure("Consultation",cb);
//								}
//							}.retry(2);
//							
//							
//						}
//
//						@Override
//						protected void callService(AsyncCallback<ConsultationDTO> cb) {
//							rpcArticleService.getConsultation(rv.getContents_id(), cb);
//						}
//					}.retry(2);
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
//					ArrayList<ConsultationDTO> dtos = new ArrayList<ConsultationDTO>();
//					
//					new RPCCall<ArrayList<ConsultationDTO>>() {
//						@Override
//						public void onFailure(Throwable caught) {
//							Window.alert("Error getting researh summaries");
//						}
//						@Override
//						public void onSuccess(ArrayList<ConsultationDTO> result) {
//							final ArrayList<IPickableElement> arr = new ArrayList<IPickableElement>();
//							for(ConsultationDTO dto:result)
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
//								AsyncCallback<ArrayList<ConsultationDTO>> cb) {
//							rpcArticleService.getConsultationsAll(cb);
//						}
//					}.retry(2);
				}
				
				if (!(it instanceof RootConceptsList) && !(it instanceof ConceptItem<?>))
				{
					eventBus.fireEvent(new CreateConceptDisabledEvent());
				}
//				if (!(it instanceof SimpleConsultationList))
//				{
//					eventBus.fireEvent(new AddConsultationDisabledEvent());
//				}
			}
		});
		
		
		display.getTreeForOpen().addOpenHandler(new OpenHandler<TreeItem>() {
			@Override
			public void onOpen(OpenEvent<TreeItem> event) {
				TreeItem it = event.getTarget();
				if (it instanceof SimpleConsultationList)
				{
//					fetchResearchListData();
					((SimpleConsultationList)it).refreshContents();
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
						ConceptItem<ConsultationDTO> ct = (ConceptItem<ConsultationDTO>)it;
						if (!ct.isBinderAttached())
						{
							ct.attachCBinder(new ConceptBinder<ConsultationDTO>(){
								@Override
								public void loadContents(
										final ArrayList<Long> keys) {
									new RPCCall<ArrayList<ConsultationDTO>>() {

										@Override
										public void onFailure(Throwable caught) {
											Window.alert("Error fetching research dtos by ids");	
										}

										@Override
										public void onSuccess(ArrayList<ConsultationDTO> result) {
											ArrayList<ConsultationDTO> handler = getDtos_handler();
											handler.clear();
											for(ConsultationDTO dto:result)
											{
												//if (!handler.contains(dto))
													handler.add(dto);
											}
											setLoaded(true);
										}

										@Override
										protected void callService(
												AsyncCallback<ArrayList<ConsultationDTO>> cb) {
											rpcArticleService.getConsultationDTOs_Normal(keys, cb);
										}
									}.retry(3);
								}

								@Override
								public ConceptContentsItem composeContentsItem(
										ConsultationDTO dto) {
									ConsultationDescItem item = new ConsultationDescItem(dto);
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
		
//		display.getAddArticleBt().addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				display.getCenterPanel().clear();
//				TreeItem it = display.getSelectedItem();
//				Long key = null;
//				String name = null;
//				String sys_name = null;
//				if (it instanceof ConceptItem<?>)
//				{
//					key = ((ConceptItem<?>)it).getConcept_id();
//				}
//				if(it instanceof ConceptItemItem)
//				{
//					ConceptItemItem item = (ConceptItemItem)it;
//					name = item.getText();
//					key = item.getEntity_id();
//					sys_name = item.getEntity_system_name();
//				}
//				display.getCenterPanel().add(new addConsultationUI(sys_name,key,name));
//			}
//		});
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
				display.showCreateConceptPopup(event.getClientX(), event.getClientY(),"DataPub");
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
//				showMainPageConsultation();
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
