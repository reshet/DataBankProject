package com.mresearch.databank.client.presenters;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.event.ShowStartPageMainEvent;
import com.mresearch.databank.client.event.ShowStartPageMainEventHandler;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.StartPageServiceAsync;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.NewsSummaryDTO;

public class StartPagePerspectivePresenter implements Presenter
{
	 public interface Display {
		 HasClickHandlers getNewsList();
//		 HasClickHandlers getMainLink();
//		 HasClickHandlers getResearchLink();
//		 HasClickHandlers getLawLink();
//		 HasClickHandlers getConsultLink();
//		 HasClickHandlers getLoginLink();
		 void setMainPageArticle(String html);
		 void setNewsData(ArrayList<String> data);
		 Widget asWidget();
	 }
	 private final StartPageServiceAsync rpcService;
	 private final SimpleEventBus eventBus;
	 private final Display display;
	 public StartPagePerspectivePresenter(StartPageServiceAsync rpcService,SimpleEventBus eventBus,
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
		 showMainPageArticle();
		 fetchNewsData();
	}
	
	public void bind()
	{
//		eventBus.addHandler(ShowStartPageMainEvent.TYPE, new ShowStartPageMainEventHandler() {
//			@Override
//			public void onShowStartPageMain(ShowStartPageMainEvent event) {
//				showMainPageArticle();
//			}
//		});
	}
	private void showMainPageArticle()
	{
		//Window.alert("Show Main Page Article!");
		new RPCCall<ArticleDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching mainpage article: "
			            + caught.getMessage());
			}

			@Override
			public void onSuccess(ArticleDTO result) {
				display.setMainPageArticle(result.getHeader()+"::"+result.getContents());
			}

			@Override
			protected void callService(AsyncCallback<ArticleDTO> cb) {
				rpcService.getMainPageArticle(cb);
			}
		}.retry(3);
		
		display.setMainPageArticle("Main page article contents");
	}
	private void fetchNewsData()
	{
		final ArrayList<String> newsData = new ArrayList<String>();
		
		new RPCCall<ArrayList<NewsSummaryDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching latest news: "
			            + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<NewsSummaryDTO> result) {
				for(NewsSummaryDTO dto:result)
				{
					newsData.add(dto.getHeader()+":::"+dto.getContents());
				}
				display.setNewsData(newsData);
			}

			@Override
			protected void callService(
					AsyncCallback<ArrayList<NewsSummaryDTO>> cb) {
				rpcService.getNewsSummaries(10, cb);
			}
		}.retry(3);
	}
}
