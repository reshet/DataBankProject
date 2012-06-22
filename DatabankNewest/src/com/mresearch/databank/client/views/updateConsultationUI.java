package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.persistence.internal.libraries.asm.util.CheckAnnotationAdapter;
import org.zenika.widget.client.datePicker.DatePicker;

import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminArticleService;
import com.mresearch.databank.client.service.AdminArticleServiceAsync;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.views.DBfillers.MultiValuedField;
import com.mresearch.databank.client.views.DBviewers.MultiValuedFieldViewer;
import com.mresearch.databank.shared.JSON_Representation;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.ConsultationDTO;
import com.smartgwt.client.widgets.RichTextEditor;

public class updateConsultationUI extends Composite {

	private static addConsultationUIUiBinder uiBinder = GWT
			.create(addConsultationUIUiBinder.class);
	private final AdminArticleServiceAsync adminArticleService = GWT
      .create(AdminArticleService.class);
	private final CatalogServiceAsync catalogService = GWT
		      .create(CatalogService.class);
			

	interface addConsultationUIUiBinder extends UiBinder<Widget, updateConsultationUI> {
	}

	@UiField
	Button submitButton;
	
	
	private Long root_concept;
	private ArrayList<Long> uploaded_files = new ArrayList<Long>();
	private MetaUnitMultivaluedEntityDTO db;
	private MultiValuedField mv;
	@UiField VerticalPanel elasticDBfields;
	@UiField Label question;
	@UiField TextArea answer;
	@UiField CheckBox do_publish;
	@UiField Button delButton;
	private ConsultationDTO dto;
	
	@UiHandler(value="delButton")
	public void doDel(ClickEvent e)
	{
		delete();
	}
	private void delete()
	{
		new RPCCall<Boolean>() {
			@Override
			public void onFailure(Throwable arg0) {
				Window.alert("Consult delete failed!");
			}
			@Override
			public void onSuccess(Boolean arg0) {
				Window.alert("Consult deleted!");
			}
			@Override
			protected void callService(AsyncCallback<Boolean> cb) {
				adminArticleService.deleteConsultation(dto.getId(), cb);
			}
		}.retry(2);
	}
	private void renderDBfillers(ConsultationDTO dt,String cat_sys_name,String catalogization_str)
	{
		elasticDBfields.clear();
		this.dto = dt;
		this.question.setText(dto.getquestion());
		this.answer.setText(dto.getanswer());
		this.do_publish.setValue(dto.getIsPublished()==1?true:false);
		
		final HashMap<String,String> map = dto.getFilling()!=null?dto.getFilling():new HashMap<String, String>();
		//map.put("law_catalog", catalogization_str);
		if(cat_sys_name!=null && catalogization_str!=null)
		map.put(cat_sys_name, catalogization_str);
		
		new RPCCall<MetaUnitMultivaluedEntityDTO>() {

			@Override
			public void onFailure(Throwable arg0) {
			}

			@Override
			public void onSuccess(MetaUnitMultivaluedEntityDTO res) {
				db = res;
				mv = new MultiValuedField(db,null,map,"");
				elasticDBfields.add(mv);
			}

			@Override
			protected void callService(
					AsyncCallback<MetaUnitMultivaluedEntityDTO> cb) {
				UserSocioResearchService.Util.getInstance().getDatabankStructure("consultation",cb);
			}
		}.retry(2);
	}
	
	@UiConstructor
	public updateConsultationUI(final Long dto_id,final String root_concept_sysname,final Long root_concept,final String root_concept_name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.root_concept = root_concept;
		new RPCCall<ConsultationDTO>() {

			@Override
			public void onFailure(Throwable arg0) {
				Window.alert("ERROR getting consltation dto!");
			}

			@Override
			public void onSuccess(ConsultationDTO res) {
				renderDBfillers(res,root_concept_sysname,root_concept_name);
			}

			@Override
			protected void callService(AsyncCallback<ConsultationDTO> cb) {
				adminArticleService.getConsultation(dto_id, cb);
			}
		}.retry(2);
		

		
	}

	
	
	@UiHandler("submitButton")
	void onSubmitClicked(ClickEvent e)
	{
		//Window.alert("Статью успешно создано!");
		//formPanel.submit();
		createConsultation();
	}
	
	private void fillFormValues()
	{
		dto.setquestion(question.getText());
		dto.setanswer(answer.getText());
		dto.setIsPublished(do_publish.getValue()?1:0);
		
	
		
		
		JSON_Representation json = mv.getJSON();
		//mv.populateItemsLinksTo(currentArt_DTO.getId(), "law");
		if(json.getObj().containsKey("consultation_answer"))
		{
			json.getObj().put("consultation_answer",new JSONString(answer.getText().replaceAll("\\<.*?\\>", "")));
		}
		if(json.getObj().containsKey("consultation_question"))
		{
			json.getObj().put("consultation_question",new JSONString(question.getText().replaceAll("\\<.*?\\>", "")));
		}
		dto.setJson_desctiptor(json.getObj().toString());
		HashMap<String, String> mapp = mv.returnCollectedMap();
		if(!answer.getText().equals(""))mapp.put("consultation_answer",answer.getText().replaceAll("\\<.*?\\>", ""));
		if(!question.getText().equals(""))mapp.put("consultation_question",question.getText().replaceAll("\\<.*?\\>", ""));
		dto.setFilling(mapp);
	}
	private void catagolize(final Long Consultation_id)
	{
		
		
//        if (root_concept != null)
//        {
//       	 	catalogService.getCatalogContentsIDs(root_concept, new AsyncCallback<ArrayList<String>>() {
//				@Override
//				public void onSuccess(ArrayList<String> result) {
//					result.add(article_id);
//					catalogService.setCatalogContentsIDs(root_concept, result, new AsyncCallback<Boolean>() {
//						@Override
//						public void onSuccess(Boolean result) {
//							Window.alert("Catalogization succeded");
//						}
//						@Override
//						public void onFailure(Throwable caught) {
//							Window.alert("Error uodating catalog keys");
//						}
//					});
//				}
//				@Override
//				public void onFailure(Throwable caught) {
//					Window.alert("Error getting contents keys");
//				}
//			});
//        }
	}
	
	private void createConsultation()
	{
		fillFormValues();
		new RPCCall<ConsultationDTO>() {

			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				 Window.alert("ERROR Updated Consultation!");
			}

			@Override
			public void onSuccess(ConsultationDTO res) {
				  Window.alert("Updated Consultation!");
				mv.populateItemsLinksTo(res.getId(), "consultation");
			}
			

			@Override
			protected void callService(AsyncCallback<ConsultationDTO> cb) {
				adminArticleService.updateConsultation(dto, cb);
			}
		}.retry(2);
//		adminArticleService.updateConsultation(currentArt_DTO,
//			        new AsyncCallback<ConsultationDTO>() {
//			          public void onFailure(Throwable caught) {
//			            Window.alert("An error occurred");
//			          }
//			          public void onSuccess(ConsultationDTO result) {
//			             Window.alert("Updated");
//			             catagolize(result.getId());
//			             //ConnectrApp.get().showFriendList();
//			            //ConnectrApp.get().showMessages();
//			          }
//			        });
	}
}
