package com.mresearch.databank.client.presenters;

import java.util.ArrayList;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.event.ShowPublicationDetailsEvent;
import com.mresearch.databank.client.event.ShowPublicationDetailsEventHandler;
import com.mresearch.databank.client.event.ShowPublicationIndexEvent;
import com.mresearch.databank.client.event.ShowPublicationIndexEventHandler;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminArticleService;
import com.mresearch.databank.client.service.AdminArticleServiceAsync;
import com.mresearch.databank.client.views.ConceptItemItem;
import com.mresearch.databank.shared.PublicationDTO;
import com.mresearch.databank.shared.PublicationDTO_Light;
import com.mresearch.databank.shared.TopicDTO;

public class UserPubPerspectivePresenter implements Presenter
{
	 public interface Display {
		 void setPublicationListData(ArrayList<PublicationDTO_Light> data);
		 Widget asWidget();
		 void showLoadingLabel();
		 void showPublicationDetailes(PublicationDTO dto,String path);
		 void showPublicationIndex(ArrayList<PublicationDTO> dtos,String path);
		 void showTopics(ArrayList<TopicDTO> topics);
		 VerticalPanel getCenterPanel();
		 void findInPublicationList(Long id);
	 }
	 
	 public static String PRESENTER_PATH="Публикации";
	 private final AdminArticleServiceAsync rpcService;
	 private final SimpleEventBus eventBus;
	 private final Display display;
	 public UserPubPerspectivePresenter(AdminArticleServiceAsync rpcService,SimpleEventBus eventBus,
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
		 fetchPublicationListData();
		 fetchPublicationTopics();
		 if (p_names.contains("showPublication"))
		 {
			 int index = p_names.indexOf("showPublication");
			 String id = p_values.get(index);
			 Long idd = Long.parseLong(id);
			 display.findInPublicationList(idd);
			 fetchPublicationDetailes(idd,UserPubPerspectivePresenter.PRESENTER_PATH);
			 // eventBus.fireEvent(new ShowResearchDetailsEvent(id));
		 }
	}
	
	
	public void bind()
	{
//		if (it instanceof ConceptItemItem)
//		{
//			ConceptItemItem rv = (ConceptItemItem)it;
//			rv.refreshTaggedEntitiesIDs();
//			if(rv.getLaw_ids().size()>0)fetchPublicationIndex(rv.getLaw_ids(),rv.getCatalog_path());
//			//fetchResearchVarData(it, rv.getResearch_id());
//			//eventBus.fireEvent(new ShowPublicationDetailsEvent(rv.getContents_id()));
//		}
//		
		eventBus.addHandler(ShowPublicationDetailsEvent.TYPE, new ShowPublicationDetailsEventHandler() {
			@Override
			public void onShowPublicationDetails(ShowPublicationDetailsEvent event) {
				display.getCenterPanel().clear();
				display.getCenterPanel().add(new HTML("<h2>Загрузка данных...</h2>"));
				fetchPublicationDetailes(event.getPublication_id(),UserPubPerspectivePresenter.PRESENTER_PATH);
			}
		});
		
		eventBus.addHandler(ShowPublicationIndexEvent.TYPE, new ShowPublicationIndexEventHandler() {
			@Override
			public void onShowPublicationIndex(ShowPublicationIndexEvent event) {
				fetchPublicationIndex(event.getElems(),UserPubPerspectivePresenter.PRESENTER_PATH+"/"+event.getPath());
			}
		});
	}
	
	private void fetchPublicationTopics()
	{
		new RPCCall<ArrayList<TopicDTO>>() {

			@Override
			public void onFailure(Throwable arg0) {
			}

			@Override
			public void onSuccess(ArrayList<TopicDTO> res) {
				display.showTopics(res);
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<TopicDTO>> cb) {
				rpcService.getTopics(cb);
			}
		}.retry(2);
	}
	private void fetchPublicationListData()
	{
		new RPCCall<ArrayList<PublicationDTO_Light>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching " +
						" publications: "
			            + caught.getMessage());
			}
			@Override
			public void onSuccess(ArrayList<PublicationDTO_Light> result) {
				display.setPublicationListData(result);
				ArrayList<Long> ids = new ArrayList<Long>();
				for(PublicationDTO_Light l:result)
				{
					ids.add(l.getId());
				}
				fetchPublicationIndex(ids, UserPubPerspectivePresenter.PRESENTER_PATH+"/"+"Последние");
			}

			@Override
			protected void callService(
					AsyncCallback<ArrayList<PublicationDTO_Light>> cb) {
				rpcService.getPublications(10,0,cb);
			}
		}.retry(3);
	}
	
	
	
	private void fetchPublicationDetailes(final Long id_Publication,final String path)
	{
		new RPCCall<PublicationDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on getting Publication detailes :"+caught.getMessage());
			}

			@Override
			public void onSuccess(PublicationDTO result) {
				display.showPublicationDetailes(result,path+"/"+result.getHeader());
			}

			@Override
			protected void callService(AsyncCallback<PublicationDTO> cb) {
				rpcService.getPublication(id_Publication, cb);
			}
		}.retry(3);
	}
	private void fetchPublicationIndex(final ArrayList<Long> pub_ids,final String path)
	{
		new RPCCall<ArrayList<PublicationDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on getting Publication detailes index:"+caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<PublicationDTO> result) {
				display.showPublicationIndex(result,path);
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<PublicationDTO>> cb) {
				AdminArticleService.Util.getInstance().getPublicationDTOs_Normal(pub_ids, cb);
			}
		}.retry(3);
	}
}
