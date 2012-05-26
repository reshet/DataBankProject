package com.mresearch.databank.client.presenters;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
//import com.mresearch.databank.client.event.ShowConsultationDetailsEvent;
//import com.mresearch.databank.client.event.ShowConsultationDetailsEventHandler;
import com.mresearch.databank.client.event.ShowConsultationIndexEvent;
import com.mresearch.databank.client.event.ShowConsultationIndexEventHandler;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminArticleService;
import com.mresearch.databank.client.service.AdminArticleServiceAsync;
import com.mresearch.databank.client.views.ConceptItemItem;
import com.mresearch.databank.shared.ConsultationDTO;
import com.mresearch.databank.shared.ConsultationDTO_Light;
import com.mresearch.databank.shared.JuryBundleDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.TopicDTO;

public class UserJuryPerspectivePresenter implements Presenter
{
	 public interface Display {
		 void setConsultationListData(ArrayList<ConsultationDTO_Light> data);
		 Widget asWidget();
		 void showLoadingLabel();
		 void showConsultationDetailes(ConsultationDTO dto,String path);
		 void showTopics(ArrayList<TopicDTO> topics);
		 VerticalPanel getCenterPanel();
		 void findInConsultationList(Long id);
		 HasClickHandlers getAskBtn();
		 ConsultationDTO getQuestion();
		 void showSuccessSend();
	    	void showConsultationIndex(ArrayList<ConsultationDTO> dtos,
				String path, MetaUnitMultivaluedEntityDTO meta);
	 }
	 
	 public static String PRESENTER_PATH="Публикации";
	 private final AdminArticleServiceAsync rpcService;
	 private final SimpleEventBus eventBus;
	 private final Display display;
	 private JuryBundleDTO bundle;
	 public UserJuryPerspectivePresenter(AdminArticleServiceAsync rpcService,SimpleEventBus eventBus,
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
		 fetchStartup();
		 //fetchConsultationListData();
		 //fetchConsultationTopics();
		 if (p_names.contains("showConsultation"))
		 {
			 int index = p_names.indexOf("showConsultation");
			 String id = p_values.get(index);
			 Long idd = Long.parseLong(id);
			 display.findInConsultationList(idd);
			 fetchConsultationDetailes(idd,UserJuryPerspectivePresenter.PRESENTER_PATH);
			 // eventBus.fireEvent(new ShowResearchDetailsEvent(id));
		 }
	}
	
	
	public void bind()
	{
//		if (it instanceof ConceptItemItem)
//		{
//			ConceptItemItem rv = (ConceptItemItem)it;
//			rv.refreshTaggedEntitiesIDs();
//			if(rv.getLaw_ids().size()>0)fetchConsultationIndex(rv.getLaw_ids(),rv.getCatalog_path());
//			//fetchResearchVarData(it, rv.getResearch_id());
//			//eventBus.fireEvent(new ShowConsultationDetailsEvent(rv.getContents_id()));
//		}
//		
//		eventBus.addHandler(ShowConsultationDetailsEvent.TYPE, new ShowConsultationDetailsEventHandler() {
//			@Override
//			public void onShowConsultationDetails(ShowConsultationDetailsEvent event) {
//				display.getCenterPanel().clear();
//				display.getCenterPanel().add(new HTML("<h2>Загрузка данных...</h2>"));
//				fetchConsultationDetailes(event.getConsultation_id(),UserJuryPerspectivePresenter.PRESENTER_PATH);
//			}
//		});
		
		eventBus.addHandler(ShowConsultationIndexEvent.TYPE, new ShowConsultationIndexEventHandler() {
			@Override
			public void onShowConsultationIndex(ShowConsultationIndexEvent event) {
				fetchConsultationIndex(event.getElems(),UserJuryPerspectivePresenter.PRESENTER_PATH+"/"+event.getPath());
			}
		});
		
		display.getAskBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				new RPCCall<ConsultationDTO>() {

					@Override
					public void onFailure(Throwable arg0) {
						
					}

					@Override
					public void onSuccess(ConsultationDTO arg0) {
						display.showSuccessSend();
					}

					@Override
					protected void callService(AsyncCallback<ConsultationDTO> cb) {
						rpcService.updateConsultation(display.getQuestion(), cb);
					}
				}.retry(2);
			}
		});
	}
	
	private void fetchConsultationTopics()
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
				rpcService.getJuryTopics(cb);
			}
		}.retry(2);
	}
	
	
	
	private void fetchConsultationListData()
	{
		new RPCCall<ArrayList<ConsultationDTO_Light>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching " +
						" Consultations: "
			            + caught.getMessage());
			}
			@Override
			public void onSuccess(ArrayList<ConsultationDTO_Light> result) {
				display.setConsultationListData(result);
				ArrayList<Long> ids = new ArrayList<Long>();
				for(ConsultationDTO_Light l:result)
				{
					ids.add(l.getId());
				}
				fetchConsultationIndex(ids, UserJuryPerspectivePresenter.PRESENTER_PATH+"/"+"Последние");
			}

			@Override
			protected void callService(
					AsyncCallback<ArrayList<ConsultationDTO_Light>> cb) {
				rpcService.getConsultations(10,0,cb);
			}
		}.retry(3);
	}
	
	
	
	private void fetchConsultationDetailes(final Long id_Consultation,final String path)
	{
		new RPCCall<ConsultationDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on getting Consultation detailes :"+caught.getMessage());
			}

			@Override
			public void onSuccess(ConsultationDTO result) {
				display.showConsultationDetailes(result,path+"/"+result.getquestion());
			}

			@Override
			protected void callService(AsyncCallback<ConsultationDTO> cb) {
				rpcService.getConsultation(id_Consultation, cb);
			}
		}.retry(3);
	}
	private void fetchConsultationIndex(final ArrayList<Long> pub_ids,final String path)
	{
		if(pub_ids!=null && pub_ids.size()>0)
		new RPCCall<ArrayList<ConsultationDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on getting Consultation detailes index:"+caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<ConsultationDTO> result) {
				display.showConsultationIndex(result,path,bundle.getJuryMeta());
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<ConsultationDTO>> cb) {
				AdminArticleService.Util.getInstance().getConsultationDTOs_Normal(pub_ids, cb);
			}
		}.retry(3);
	}
	
	
	private void fetchStartup()
	{
		new RPCCall<JuryBundleDTO>() {

			@Override
			public void onFailure(Throwable paramThrowable) {
				
			}

			@Override
			public void onSuccess(JuryBundleDTO res) {
				bundle = res;
				display.showTopics(res.getTops());
				display.showConsultationIndex(res.getLast_index_dtos(),UserJuryPerspectivePresenter.PRESENTER_PATH+"/"+"Последние",res.getJuryMeta());
			}

			@Override
			protected void callService(AsyncCallback<JuryBundleDTO> cb) {
				rpcService.getJuryStartup(cb);
			}
		}.retry(2);
	}
}
