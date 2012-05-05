package com.mresearch.databank.client.presenters;

import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.event.RecalculateDistributionsEvent;
import com.mresearch.databank.client.event.ShowResearchDetailsEvent;
import com.mresearch.databank.client.event.ShowResearchDetailsEventHandler;
import com.mresearch.databank.client.event.ShowStartPageMainEvent;
import com.mresearch.databank.client.event.ShowStartPageMainEventHandler;
import com.mresearch.databank.client.event.ShowVar2DDEvent;
import com.mresearch.databank.client.event.ShowVar2DDEventHandler;
import com.mresearch.databank.client.event.ShowVarDetailsEvent;
import com.mresearch.databank.client.event.ShowVarDetailsEventHandler;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.StartPageServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.client.views.ConceptItemItem;
import com.mresearch.databank.client.views.ConceptItemEntity;
import com.mresearch.databank.client.views.IPickBinder;
import com.mresearch.databank.client.views.PickElementsTableView;
import com.mresearch.databank.client.views.RealVariableDetailedView;
import com.mresearch.databank.client.views.ResearchDescItem;
import com.mresearch.databank.client.views.ResearchVarList;
import com.mresearch.databank.client.views.SimpleResearchList;
import com.mresearch.databank.client.views.TextVariableDetailedView;
import com.mresearch.databank.client.views.UserResearchAdvancedFilesView;
import com.mresearch.databank.client.views.UserResearchDetailedFrameView;
import com.mresearch.databank.client.views.UserResearchDetailedView;
import com.mresearch.databank.client.views.VarDescItem;
import com.mresearch.databank.client.views.VariableDetailedView;
import com.mresearch.databank.client.views.UserResearchVar2DDView;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.NewsDTO;
import com.mresearch.databank.shared.NewsSummaryDTO;
import com.mresearch.databank.shared.RealVarDTO_Detailed;
import com.mresearch.databank.shared.ResearchBundleDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO_Light;
import com.mresearch.databank.shared.TextVarDTO_Detailed;
import com.mresearch.databank.shared.UserAccountDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;
import com.mresearch.databank.shared.VarDTO_Light;
import com.mresearch.databank.shared.IPickableElement;


public class UserResearchPerspectivePresenter implements Presenter
{
	 public interface AnalisysDisplay
	 {
		 HasClickHandlers getWeightsUse();
		 Integer getWeightsUseState();
		 HasClickHandlers getFiltersUse();
		 Integer getFiltersUseState();
		 HasClickHandlers getFiltersDetailesBtn();
		 HasClickHandlers getFiltersAddBtn();
		 HasClickHandlers getFiltersDeleteBtn();
	 }
	 public interface Display {
		 HasMouseDownHandlers getTree();
		 HasOpenHandlers<TreeItem> getTreeForOpen();
		 HasSelectionHandlers<TreeItem> getTreeForSelection();
		 HasClickHandlers getResearchItem(int index);
		 HasClickHandlers getVarItem(int index);
		 void setResearchListData(ArrayList<SocioResearchDTO_Light> data);
		 void setVarListData(TreeItem item, ArrayList<VarDTO_Light> data);
		 Widget asWidget();
		 void showLoadingLabel();
		 TreeItem getSelectedItem();
		 void showResearchDetailes(ResearchBundleDTO dto);
		 VerticalPanel getCenterPanel();
		 void findInResearchList(Long id);
		 
	 }
	 
	 private final UserSocioResearchServiceAsync rpcService;
	// private final UserSocioResearchServiceAsync rpcService;
	 private final SimpleEventBus eventBus;
	 private final Display display;
	 private Long current_research_id = null;
		
	 public UserResearchPerspectivePresenter(UserSocioResearchServiceAsync rpcService,SimpleEventBus eventBus,
		      Display view) {
		    this.rpcService = rpcService;
		    this.eventBus = eventBus;
		    this.display = view;
		    bind();
		  }
	@Override
	public void go(HasWidgets container,ArrayList<String> p_names,ArrayList<String> p_values) {
		 container.clear();
		 container.add(display.asWidget());
		 fetchResearchListData();
		 if (p_names.contains("showResearch"))
		 {
			 int index = p_names.indexOf("showResearch");
			 String id = p_values.get(index);
			 display.findInResearchList(Long.parseLong(id));
			 display.getCenterPanel().clear();
				display.getCenterPanel().add(new HTML("<h2>Загрузка данных...</h2>"));
				fetchResearchDetailes(Long.parseLong(id));
			 // eventBus.fireEvent(new ShowResearchDetailsEvent(id));
		 }
		 if (p_names.contains("showVar"))
		 {
			 int index = p_names.indexOf("showVar");
			 String id = p_values.get(index);
			 display.getCenterPanel().clear();
			 display.getCenterPanel().add(new HTML("<h2>Загрузка данных...</h2>"));
			 fetchVariableDetailes(Long.parseLong(id));
		 }
	}
	
	public void bind()
	{
//		display.getTree().addMouseDownHandler(new MouseDownHandler() {
//			@Override
//			public void onMouseDown(MouseDownEvent event) {
//				TreeItem it = display.getSelectedItem();
//				if (it instanceof SimpleResearchList)
//				{
//					//fetchResearchListData();
//				}
//				
//			}
//		});
		display.getTreeForSelection().addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem it = display.getSelectedItem();
				if (it instanceof SimpleResearchList)
				{
					//fetchResearchListData();
				}else if (it instanceof ResearchDescItem)
				{
					ResearchDescItem rv = (ResearchDescItem)it;
					//fetchResearchVarData(it, rv.getResearch_id());
					
					current_research_id = rv.getContents_id();
					eventBus.fireEvent(new ShowResearchDetailsEvent(rv.getContents_id()));
				}else if (it instanceof ResearchVarList)
				{
					
				}
				else if (it instanceof ResearchDescItem)
				{
					ResearchDescItem rv = (ResearchDescItem)it;
					current_research_id = rv.getContents_id();
					//fetchResearchVarData(it, rv.getResearch_id());
					eventBus.fireEvent(new ShowResearchDetailsEvent(rv.getContents_id()));
				}else if (it instanceof VarDescItem)
				{
					VarDescItem rv = (VarDescItem)it;
					//fetchResearchVarData(it, rv.getResearch_id());
					eventBus.fireEvent(new ShowVarDetailsEvent(rv.getVar_id()));
				//	eventBus.fireEvent(new AddResearchDisabledEvent());
				}
			}
		});
		display.getTreeForOpen().addOpenHandler(new OpenHandler<TreeItem>() {
			@Override
			public void onOpen(OpenEvent<TreeItem> event) {
				TreeItem it = event.getTarget();
				if (it instanceof SimpleResearchList)
				{
					fetchResearchListData();
				}else if (it instanceof ResearchVarList)
				{
					ResearchVarList rv = (ResearchVarList)it;
					fetchResearchVarData(it, rv.getResearch_id());
					current_research_id = rv.getResearch_id();
					
					//eventBus.fireEvent(new ShowResearchDetailsEvent(rv.getResearch_id()));
				}
				else if (it instanceof ConceptItemItem)
				{
					ConceptItemItem rcl = (ConceptItemItem)it;
					rcl.refreshContents();
					//eventBus.fireEvent(new CreateConceptEnabledEvent());
				}
				else if (it instanceof ConceptItemEntity)
				{
					ConceptItemEntity rcl = (ConceptItemEntity)it;
					rcl.refreshContents();
					//eventBus.fireEvent(new CreateConceptEnabledEvent());
				}
				
			
			}
		});
		
		
		
		
		
		
		eventBus.addHandler(ShowResearchDetailsEvent.TYPE, new ShowResearchDetailsEventHandler() {
			@Override
			public void onShowResearchDetails(ShowResearchDetailsEvent event) {
				display.getCenterPanel().clear();
				display.getCenterPanel().add(new HTML("<h2>Загрузка данных...</h2>"));
				current_research_id = DatabankApp.get().getCurrentUser().getCurrent_research();
				fetchResearchDetailes(event.getResearch_id());
			}
		});
		eventBus.addHandler(ShowVarDetailsEvent.TYPE, new ShowVarDetailsEventHandler() {
			@Override
			public void onShowVarDetails(ShowVarDetailsEvent event) {
				display.getCenterPanel().clear();
				//display.getCenterPanel().add(new VariableDetailedView(event.get))
				display.getCenterPanel().add(new HTML("<h2>Загрузка данных...</h2>"));
				fetchVariableDetailes(event.getVar_id());
				//fetchResearchDetailes(event.getResearch_id());
			}
		});
		eventBus.addHandler(ShowVar2DDEvent.TYPE, new ShowVar2DDEventHandler() {
			@Override
			public void onShowVar2DD(ShowVar2DDEvent event) {
				display.getCenterPanel().clear();
				
				display.getCenterPanel().add(new UserResearchVar2DDView(event.getRes_id(),eventBus,display));
			}
		});
		//		eventBus.addHandler(ShowStartPageMainEvent.TYPE, new ShowStartPageMainEventHandler() {
//			@Override
//			public void onShowStartPageMain(ShowStartPageMainEvent event) {
//				showMainPageArticle();
//			}
//		});
	}
	
	private void fetchVariableDetailes(final Long id_var)
	{
		new RPCCall<VarDTO_Detailed>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on getting var detailes :"+caught.getMessage());
			}

			@Override
			public void onSuccess(final VarDTO_Detailed result) {
			//	AdminResearchDetailedView ad_view = new AdminResearchDetailedView(new UserResearchDetailedView(result));
			//	AdminResearchEditView ed_view = new AdminResearchEditView(result);
			//	AdminResearchDetailedPresenter presenter = new AdminResearchDetailedPresenter(rpcUserService,rpcAdminService, eventBus, ad_view, ed_view);
			//	presenter.go(display.getCenterPanel());
				
				
					new RPCCall<MetaUnitMultivaluedEntityDTO>() {

						@Override
						public void onFailure(Throwable arg0) {
						}

						@Override
						public void onSuccess(MetaUnitMultivaluedEntityDTO res) {
							display.getCenterPanel().clear();
							if (result instanceof RealVarDTO_Detailed)
								display.getCenterPanel().add(new RealVariableDetailedView((RealVarDTO_Detailed)result,res,eventBus,display));
							else if (result instanceof TextVarDTO_Detailed)
								display.getCenterPanel().add(new TextVariableDetailedView((TextVarDTO_Detailed)result,res,eventBus,display));
							else
								display.getCenterPanel().add(new VariableDetailedView(result,res,eventBus,display));
						}

						@Override
						protected void callService(
								AsyncCallback<MetaUnitMultivaluedEntityDTO> cb) {
							UserSocioResearchService.Util.getInstance().getDatabankStructure("sociovar", cb);
						}
					}.retry(2);
		
					
				
				
				
			}

			@Override
			protected void callService(AsyncCallback<VarDTO_Detailed> cb) {
				rpcService.getVarDetailed(id_var, cb);
			}
		}.retry(3);
	}
	private void fetchResearchListData()
	{
		//final ArrayList<NewsDTO> newsData = new ArrayList<NewsDTO>();
		
		new RPCCall<ArrayList<SocioResearchDTO_Light>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching " +
						" news: "
			            + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<SocioResearchDTO_Light> result) {
				display.setResearchListData(result);
			}

			@Override
			protected void callService(
					AsyncCallback<ArrayList<SocioResearchDTO_Light>> cb) {
				rpcService.getResearchSummaries(cb);
			}
		}.retry(3);
	}
	private void fetchResearchVarData(final TreeItem item,final Long id_research)
	{
		new RPCCall<ArrayList<VarDTO_Light>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching " +
						" news: "
			            + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<VarDTO_Light> result) {
				display.setVarListData(item,result);
			}

			@Override
			protected void callService(
					AsyncCallback<ArrayList<VarDTO_Light>> cb) {
				rpcService.getResearchVarsSummaries(id_research, cb);
			}
		}.retry(3);
	}
	private void fetchResearchDetailes(final Long id_research)
	{
		new RPCCall<ResearchBundleDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on getting research detailes :"+caught.getMessage());
			}

			@Override
			public void onSuccess(ResearchBundleDTO result) {
				display.showResearchDetailes(result);
			}

			@Override
			protected void callService(AsyncCallback<ResearchBundleDTO> cb) {
				rpcService.getResearchBundle(id_research, cb);
			}
		}.retry(3);
	}
}
