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
import com.mresearch.databank.client.event.ShowVarDetailsEvent;
import com.mresearch.databank.client.event.ShowVarDetailsEventHandler;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.StartPageServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.client.views.IPickBinder;
import com.mresearch.databank.client.views.PickElementsTableView;
import com.mresearch.databank.client.views.RealVariableDetailedView;
import com.mresearch.databank.client.views.ResearchDescItem;
import com.mresearch.databank.client.views.ResearchVarList;
import com.mresearch.databank.client.views.SimpleResearchList;
import com.mresearch.databank.client.views.TextVariableDetailedView;
import com.mresearch.databank.client.views.VarDescItem;
import com.mresearch.databank.client.views.VariableDetailedView;
import com.mresearch.databank.client.views.UserResearchVar2DDView;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.NewsDTO;
import com.mresearch.databank.shared.NewsSummaryDTO;
import com.mresearch.databank.shared.RealVarDTO_Detailed;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.TextVarDTO_Detailed;
import com.mresearch.databank.shared.UserAccountDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;
import com.mresearch.databank.shared.VarDTO_Light;
import com.mresearch.databank.shared.IPickableElement;


public class UserResearchPerspectivePresenter implements Presenter
{
	 public interface Display {
		 HasMouseDownHandlers getTree();
		 HasOpenHandlers<TreeItem> getTreeForOpen();
		 HasSelectionHandlers<TreeItem> getTreeForSelection();
		 HasClickHandlers getResearchItem(int index);
		 HasClickHandlers getVarItem(int index);
		 void setResearchListData(ArrayList<SocioResearchDTO> data);
		 void setVarListData(TreeItem item, ArrayList<VarDTO_Light> data);
		 Widget asWidget();
		 void showLoadingLabel();
		 TreeItem getSelectedItem();
		 void showResearchDetailes(SocioResearchDTO dto);
		 VerticalPanel getCenterPanel();
		 void findInResearchList(Long id);
		 
		 HasClickHandlers getWeightsUse();
		 Integer getWeightsUseState();
		 HasClickHandlers getFiltersUse();
		 Integer getFiltersUseState();
		 HasClickHandlers getFiltersDetailesBtn();
		 HasClickHandlers getFiltersAddBtn();
		 HasClickHandlers getFiltersDeleteBtn();
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
			 // eventBus.fireEvent(new ShowResearchDetailsEvent(id));
		 }
	}
	
	public void bind()
	{
		display.getTree().addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				TreeItem it = display.getSelectedItem();
				if (it instanceof SimpleResearchList)
				{
					//fetchResearchListData();
				}else if (it instanceof ResearchDescItem)
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
					ResearchVarList rv = (ResearchVarList)it;
					display.getCenterPanel().clear();
					
					display.getCenterPanel().add(new UserResearchVar2DDView(rv.getResearch_id()));
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
		display.getWeightsUse().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UserAccountDTO dto = DatabankApp.get().getCurrentUser();
				dto.setWeights_use(display.getWeightsUseState());
				DatabankApp.get().updateUserAccountState();
				current_research_id = DatabankApp.get().getCurrentUser().getCurrent_research();
				TreeItem it = display.getSelectedItem();
				if (it instanceof VarDescItem)
				{
					VarDescItem rv = (VarDescItem)it;
					eventBus.fireEvent(new ShowVarDetailsEvent(rv.getVar_id()));
					//weights will be recalculated on server; may be not syncronized!!!
				}
				
				eventBus.fireEvent(new RecalculateDistributionsEvent(display.getWeightsUseState(), display.getWeightsUseState(),
						DatabankApp.get().getCurrentUser().getFiltersToProcess(current_research_id)));
				//rise event!
				//dto.setFilters_use(display.getFiltersUseState());
			}
		});
		display.getFiltersUse().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UserAccountDTO dto = DatabankApp.get().getCurrentUser();
				dto.setFilters_use(display.getFiltersUseState());
				DatabankApp.get().updateUserAccountState();
				current_research_id = DatabankApp.get().getCurrentUser().getCurrent_research();
				eventBus.fireEvent(new RecalculateDistributionsEvent(display.getWeightsUseState(), display.getWeightsUseState(),
						DatabankApp.get().getCurrentUser().getFiltersToProcess(current_research_id)));
			
				//rise event!
							//dto.setFilters_use(display.getFiltersUseState());
			}
		});
		display.getFiltersDetailesBtn().addClickHandler(new ClickHandler() {
			final PopupPanel panel = new PopupPanel(true);
			
			@Override
			public void onClick(ClickEvent event) {
				panel.clear();
				panel.setPopupPosition(event.getClientX(), event.getClientY());
				ArrayList<IPickableElement> elems = new ArrayList<IPickableElement>();
				
//				final ArrayList<String> filters = DatabankApp.get().getCurrentUser().getFilters(current_research_id);
//				for(final String filter:filters)
//				{
//					elems.add(new IPickableElement() {
//						@Override
//						public String getTextRepresent() {
//							return filter;
//						}
//						
//						@Override
//						public String getID() {
//							return String.valueOf(filters.indexOf(filter));
//						}
//					});
//				}
//				//ArrayList<String> used_elems = new ArrayList<String>();
				current_research_id = DatabankApp.get().getCurrentUser().getCurrent_research();
				final ArrayList<String> filters = DatabankApp.get().getCurrentUser().getFilters();
				final ArrayList<Long> filter_categs = DatabankApp.get().getCurrentUser().getFilters_categories();
				int i = 0;
				for(final String filter:filters)
				{
					if (filter_categs.get(i)!=null && filter_categs.get(i).equals(current_research_id))
					{
						final int inter = i;
						elems.add(new IPickableElement() {
							@Override
							public String getTextRepresent() {
								return filter;
							}
							
							@Override
							public long getID() {
								return inter;
							}
						});
					}
					i++;
				}
				
				panel.add(new PickElementsTableView(elems, DatabankApp.get().getCurrentUser().getFiltersToProcessIndecies(current_research_id), new IPickBinder() {
					@Override
					public void processPickChoice(ArrayList<Long> selected_keys) {
						ArrayList<Integer> usage = new ArrayList<Integer>();
						ArrayList<String> filters = DatabankApp.get().getCurrentUser().getFilters(current_research_id);
						for(String touse:filters)
						{
							usage.add(new Integer(0));
						}
						for(Long sel_index:selected_keys)
						{
							Integer index = sel_index.intValue();
							usage.set(index, new Integer(1));
						}
						DatabankApp.get().getCurrentUser().setFilters_usage(usage,current_research_id);
						DatabankApp.get().updateUserAccountState();
						eventBus.fireEvent(new RecalculateDistributionsEvent(display.getWeightsUseState(), display.getWeightsUseState(),
								DatabankApp.get().getCurrentUser().getFiltersToProcess(current_research_id)));
						panel.hide();
					}
					
					@Override
					public String getCommandName() {
						return "Применить!";
					}

					@Override
					public String getTitle() {
						return "Доступные фильтры:";
					}
				}));
				panel.show();
			}
		});
		display.getFiltersAddBtn().addClickHandler(new ClickHandler() {
			final PopupPanel panel = new PopupPanel(true);
			@Override
			public void onClick(ClickEvent event) {
				HorizontalPanel hor = new HorizontalPanel();
				panel.clear();
				panel.setPopupPosition(event.getClientX(), event.getClientY());
				final TextBox t = new TextBox();
				t.setSize("500px", "40px");
				Button doFilter = new Button("Добавить фильтр!");
				doFilter.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						panel.hide();
						current_research_id = DatabankApp.get().getCurrentUser().getCurrent_research();
						final ArrayList<String> filters = DatabankApp.get().getCurrentUser().getFilters();
						final ArrayList<Long> filter_categs = DatabankApp.get().getCurrentUser().getFilters_categories();
						final ArrayList<Integer> filter_usage = DatabankApp.get().getCurrentUser().getFilters_usage();
						filters.add(t.getText());
						filter_categs.add(current_research_id);
						filter_usage.add(0);
						DatabankApp.get().updateUserAccountState();
						Window.alert("Filter created:"+t.getText());
					}
				});
				hor.add(t);
				hor.add(doFilter);
				panel.add(hor);
				panel.show();
			}
		});
		display.getFiltersDeleteBtn().addClickHandler(new ClickHandler() {
			final PopupPanel panel = new PopupPanel(true);
			@Override
			public void onClick(ClickEvent event) {
				panel.clear();
				panel.setPopupPosition(event.getClientX(), event.getClientY());
				ArrayList<IPickableElement> elems = new ArrayList<IPickableElement>();
				current_research_id = DatabankApp.get().getCurrentUser().getCurrent_research();
				final ArrayList<String> filters = DatabankApp.get().getCurrentUser().getFilters();
				final ArrayList<Long> filter_categs = DatabankApp.get().getCurrentUser().getFilters_categories();
				int i = 0;
				for(final String filter:filters)
				{
					if (filter_categs.get(i)==current_research_id)
					{
						final int inter = i;
						elems.add(new IPickableElement() {
							@Override
							public String getTextRepresent() {
								return filter;
							}
							
							@Override
							public long getID() {
								return inter;
							}
						});
					}
					i++;
				}
				//DatabankApp.get().getCurrentUser().getFiltersToProcess(current_research_id)
				panel.add(new PickElementsTableView(elems,new ArrayList<Long>() , new IPickBinder() {
					@Override
					public void processPickChoice(ArrayList<Long> selected_keys) {
						ArrayList<String> filters = DatabankApp.get().getCurrentUser().getFilters();
						ArrayList<Long> filters_categs = DatabankApp.get().getCurrentUser().getFilters_categories();
						ArrayList<Integer> filters_usage = DatabankApp.get().getCurrentUser().getFilters_usage();
						
						for(Long index:selected_keys)
						{
							if(filters_categs.get(index.intValue()).equals(current_research_id))
							{
								filters.remove(index);
								filters_usage.remove(index);
								filters_categs.remove(index);
							}
							//if(filters_usage.contains(index))filters_usage.remove(filters_usage.indexOf(index));
						}
						DatabankApp.get().updateUserAccountState();
						eventBus.fireEvent(new RecalculateDistributionsEvent(display.getWeightsUseState(), display.getWeightsUseState(),
								DatabankApp.get().getCurrentUser().getFiltersToProcess(current_research_id)));
						panel.hide();
					}
					
					@Override
					public String getCommandName() {
						return "Удалить!";
					}

					@Override
					public String getTitle() {
						return "Доступные фильтры:";
					}
				}));
				panel.show();			
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
			public void onSuccess(VarDTO_Detailed result) {
			//	AdminResearchDetailedView ad_view = new AdminResearchDetailedView(new UserResearchDetailedView(result));
			//	AdminResearchEditView ed_view = new AdminResearchEditView(result);
			//	AdminResearchDetailedPresenter presenter = new AdminResearchDetailedPresenter(rpcUserService,rpcAdminService, eventBus, ad_view, ed_view);
			//	presenter.go(display.getCenterPanel());
				display.getCenterPanel().clear();
				if (result instanceof RealVarDTO_Detailed)
					display.getCenterPanel().add(new RealVariableDetailedView((RealVarDTO_Detailed)result));
				else if (result instanceof TextVarDTO_Detailed)
					display.getCenterPanel().add(new TextVariableDetailedView((TextVarDTO_Detailed)result));
				else
					display.getCenterPanel().add(new VariableDetailedView(result));
				
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
		
		new RPCCall<ArrayList<SocioResearchDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching " +
						" news: "
			            + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<SocioResearchDTO> result) {
				display.setResearchListData(result);
			}

			@Override
			protected void callService(
					AsyncCallback<ArrayList<SocioResearchDTO>> cb) {
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
		new RPCCall<SocioResearchDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on getting research detailes :"+caught.getMessage());
			}

			@Override
			public void onSuccess(SocioResearchDTO result) {
				display.showResearchDetailes(result);
			}

			@Override
			protected void callService(AsyncCallback<SocioResearchDTO> cb) {
				rpcService.getResearch(id_research, cb);
			}
		}.retry(3);
	}
}
