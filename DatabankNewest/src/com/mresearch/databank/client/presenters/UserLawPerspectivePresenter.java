package com.mresearch.databank.client.presenters;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.event.CreateConceptEnabledEvent;
import com.mresearch.databank.client.event.ShowResearchDetailsEvent;
import com.mresearch.databank.client.event.ShowResearchDetailsEventHandler;
import com.mresearch.databank.client.event.ShowStartPageMainEvent;
import com.mresearch.databank.client.event.ShowStartPageMainEventHandler;
import com.mresearch.databank.client.event.ShowVarDetailsEvent;
import com.mresearch.databank.client.event.ShowVarDetailsEventHandler;
import com.mresearch.databank.client.event.ShowZaconDetailsEvent;
import com.mresearch.databank.client.event.ShowZaconDetailsEventHandler;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminArticleServiceAsync;
import com.mresearch.databank.client.service.StartPageServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.client.views.ArticleDescItem;
import com.mresearch.databank.client.views.ArticleDetailedView;
import com.mresearch.databank.client.views.ArticlesShortView;

import com.mresearch.databank.client.views.ConceptContentsItem;
import com.mresearch.databank.client.views.ConceptItem;
import com.mresearch.databank.client.views.IPickBinder;

import com.mresearch.databank.client.views.PickElementsTableView;
import com.mresearch.databank.client.views.ResearchDescItem;
import com.mresearch.databank.client.views.ResearchVarList;
import com.mresearch.databank.client.views.RootConceptsList;
import com.mresearch.databank.client.views.SimpleArticleList;
import com.mresearch.databank.client.views.SimpleResearchList;
import com.mresearch.databank.client.views.SimpleZaconList;
import com.mresearch.databank.client.views.VarDescItem;
import com.mresearch.databank.client.views.VariableDetailedView;
import com.mresearch.databank.client.views.ZaconDescItem;
import com.mresearch.databank.client.views.ZaconDetailedView;
import com.mresearch.databank.client.views.ZaconsShortView;
import com.mresearch.databank.shared.NewsDTO;
import com.mresearch.databank.shared.NewsSummaryDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.ZaconDTO;
import com.mresearch.databank.shared.ZaconDTO_Light;

public class UserLawPerspectivePresenter implements Presenter
{
	 public interface Display {
		 HasMouseDownHandlers getTree();
		 HasOpenHandlers<TreeItem> getTreeForOpen();
		 HasSelectionHandlers<TreeItem> getTreeForSelection();
		 void setZaconListData(ArrayList<ZaconDTO> data);
		 Widget asWidget();
		 void showLoadingLabel();
		 TreeItem getSelectedItem();
		 void showZaconDetailes(ZaconDTO dto);
		 VerticalPanel getCenterPanel();
		 void findInZaconList(Long id);
	 }
	 private final AdminArticleServiceAsync rpcService;
	 private final SimpleEventBus eventBus;
	 private final Display display;
	 public UserLawPerspectivePresenter(AdminArticleServiceAsync rpcService,SimpleEventBus eventBus,
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
		 fetchZaconListData();
		 if (p_names.contains("showZacon"))
		 {
			 int index = p_names.indexOf("showZacon");
			 String id = p_values.get(index);
			 Long idd = Long.parseLong(id);
			 display.findInZaconList(idd);
			 fetchZaconDetailes(idd);
			 // eventBus.fireEvent(new ShowResearchDetailsEvent(id));
		 }
	}
	
	public void bind()
	{
		display.getTree().addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				TreeItem it = display.getSelectedItem();
				if (it instanceof SimpleZaconList)
				{
						fetchZaconListData();
				}else if (it instanceof ZaconDescItem)
				{
					final ZaconDescItem rv = (ZaconDescItem)it;
					new RPCCall<ZaconDTO>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Error on fetching Zacon det");
						}
						@Override
						public void onSuccess(ZaconDTO result) {
							display.getCenterPanel().clear();
							display.getCenterPanel().add(new ZaconDetailedView(result).asWidget());
						}

						@Override
						protected void callService(AsyncCallback<ZaconDTO> cb) {
							rpcService.getZacon(rv.getContents_id(), cb);
						}
					}.retry(2);
				}
			}
		});
		display.getTreeForSelection().addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem it = display.getSelectedItem();
				if (it instanceof SimpleZaconList)
				{
					//fetchResearchListData();
				}else if (it instanceof ZaconDescItem)
				{
					ZaconDescItem rv = (ZaconDescItem)it;
					//fetchResearchVarData(it, rv.getResearch_id());
					eventBus.fireEvent(new ShowZaconDetailsEvent(rv.getContents_id()));
				}
			}
		});
		display.getTreeForOpen().addOpenHandler(new OpenHandler<TreeItem>() {
			@Override
			public void onOpen(OpenEvent<TreeItem> event) {
				TreeItem it = event.getTarget();
				if (it instanceof SimpleZaconList)
				{
					((SimpleZaconList)it).refreshContents();
//					fetchResearchListData();
				}else if (it instanceof RootConceptsList)
				{
					RootConceptsList rcl = (RootConceptsList)it;
					rcl.refreshContents();
				}
			}
		});
		
		eventBus.addHandler(ShowZaconDetailsEvent.TYPE, new ShowZaconDetailsEventHandler() {
			@Override
			public void onShowZaconDetails(ShowZaconDetailsEvent event) {
				display.getCenterPanel().clear();
				display.getCenterPanel().add(new HTML("<h2>Загрузка данных...</h2>"));
				fetchZaconDetailes(event.getZacon_id());
			}
		});
//		eventBus.addHandler(ShowVarDetailsEvent.TYPE, new ShowVarDetailsEventHandler() {
//			@Override
//			public void onShowVarDetails(ShowVarDetailsEvent event) {
//				display.getCenterPanel().clear();
//				//display.getCenterPanel().add(new VariableDetailedView(event.get))
//				display.getCenterPanel().add(new HTML("<h2>Загрузка данных...</h2>"));
//				//fetchVariableDetailes(event.getVar_id());
//				//fetchResearchDetailes(event.getResearch_id());
//			}
//		});
//		eventBus.addHandler(ShowStartPageMainEvent.TYPE, new ShowStartPageMainEventHandler() {
//			@Override
//			public void onShowStartPageMain(ShowStartPageMainEvent event) {
//				showMainPageArticle();
//			}
//		});
	//	eventBus.addHandler(AddA, handler)
	}
	
	private void fetchZaconListData()
	{
		new RPCCall<ArrayList<ZaconDTO_Light>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching " +
						" news: "
			            + caught.getMessage());
			}
			@Override
			public void onSuccess(ArrayList<ZaconDTO_Light> result) {
//				display.setResearchListData(result);
			}

			@Override
			protected void callService(
					AsyncCallback<ArrayList<ZaconDTO_Light>> cb) {
				rpcService.getZaconsAll(cb);
			}
		}.retry(3);
	}
	
	private void fetchZaconDetailes(final Long id_Zacon)
	{
		new RPCCall<ZaconDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on getting zacon detailes :"+caught.getMessage());
			}

			@Override
			public void onSuccess(ZaconDTO result) {
				display.showZaconDetailes(result);
			}

			@Override
			protected void callService(AsyncCallback<ZaconDTO> cb) {
				rpcService.getZacon(id_Zacon, cb);
			}
		}.retry(3);
	}
}
