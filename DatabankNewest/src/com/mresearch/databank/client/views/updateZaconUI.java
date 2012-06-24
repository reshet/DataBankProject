package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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
import com.mresearch.databank.shared.SocioResearchFilesDTO;
import com.mresearch.databank.shared.ZaconDTO;
import com.smartgwt.client.widgets.RichTextEditor;

public class updateZaconUI extends Composite {

	private static addZaconUIUiBinder uiBinder = GWT
			.create(addZaconUIUiBinder.class);
	private ZaconDTO currentArt_DTO =new ZaconDTO();
	private final AdminArticleServiceAsync adminArticleService = GWT
      .create(AdminArticleService.class);
	private final CatalogServiceAsync catalogService = GWT
		      .create(CatalogService.class);
			

	interface addZaconUIUiBinder extends UiBinder<Widget, updateZaconUI> {
	}

	
//	@UiField
//	FileUpload fileUpload;
	
	@UiField
	Button submitButton;
	//,submitOriginalUpload;
	
//	@UiField
//	FormPanel formPanel;
	
	@UiField
	VerticalPanel uploadPanel;
	
	//@UiField
	//TextBox _name,_number;
	//@UiField TextArea _abstract;
	//, _keywords;
	//@UiField DatePicker date,date_accept,date_decline;
	//_org_prompter;
	
	//@UiField
	//FormPanel original_upload;
	
	private Label status;
	private Button parse_spss_cmd;
	private boolean doc_uploaded = false;
	private Long blobkey = null;
	private long blob_length;
	private String upload_url = "/upload";
	private Long root_concept;
	private ArrayList<Long> uploaded_files = new ArrayList<Long>();
	private MetaUnitMultivaluedEntityDTO db;
	private MultiValuedField mv;
	@UiField VerticalPanel elasticDBfields;
	@UiField Button delButton;
	
	@UiField VerticalPanel descriptionEditor;
	private RichTextEditor richTextEditor;
	
	private void renderDBfillers(ZaconDTO dto,MetaUnitMultivaluedEntityDTO res,String cat_sys_name,String catalogization_str)
	{
		
		descriptionEditor.clear();
		
        richTextEditor = new RichTextEditor();
        richTextEditor.setHeight(400);  
        richTextEditor.setWidth(600);
        //richTextEditor.setOverflow(Overflow.HIDDEN);  
        richTextEditor.setCanDragResize(true);  
        //richTextEditor.setShowEdges(true);  
  
        // Standard control group options include  
        // "fontControls", "formatControls", "styleControls" and "colorControls"  
        //richTextEditor.setControlGroups(new String[]{"fontControls", "styleControls"});  
        richTextEditor.setValue("Краткое содержание");  
        
        descriptionEditor.add(richTextEditor);
		
		elasticDBfields.clear();
		final HashMap<String,String> map = dto.getFilling();
		  if(map.containsKey("law_contents"))richTextEditor.setValue(map.get("law_contents"));
	      
		//map.put("law_catalog", catalogization_str);
		map.put(cat_sys_name, catalogization_str);
		
		db = res;
		mv = new MultiValuedField(db,null,map,"");
		elasticDBfields.add(mv);
		
	}
	
	@UiConstructor
	public updateZaconUI(ZaconDTO dto,MetaUnitMultivaluedEntityDTO res,final String root_concept_sysname,final Long root_concept,final String root_concept_name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.root_concept = root_concept;
		//status = new Label("Добавить документ...");
		currentArt_DTO = dto;
//		SingleUploader up = new SingleUploader();
//		up.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
//			
//			@Override
//			public void onFinish(IUploader uploader) {
//				//Window.alert(uploader.getStatus());
//				//Window.alert(uploader.getInputName());
//				//Window.alert(uploader.getServerResponse());
//				String response = uploader.getServerResponse();
//				status.setText("Документ загружен");
//				Window.alert(response);
//				processUploadResponse(response);
//			}
//		});
//		uploadPanel.add(status);
		uploadPanel.add(new AdminLawFilesEditView(currentArt_DTO.getID(), "Вложения:", new SocioResearchFilesDTO()));
		
		//BlobstoreService b_serv = BlobstoreServiceFactory.getBlobstoreService();
	//	original_upload.setAction(b_serv.createUploadUrl("/upload"));
		//original_upload.setMethod("post");
		//original_upload.setEncoding("multipart/form-data");
//		adminResearchService.getBlobstoreUploadURL(new AsyncCallback<String>() {
//			@Override
//			public void onSuccess(String result) {
//				addArticleUI.this.upload_url = result;
//				Window.alert("Upload URL: "+result);
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert("Error on getting upload URL!");
//			}
//		});
//		//original_upload.setAction(upload_url);
//		original_upload.addSubmitHandler(new FormPanel.SubmitHandler() {
//			@Override
//			public void onSubmit(SubmitEvent event) {
//				Window.alert("Form submitted :: "+event.toDebugString());
//			}
//		});
//		submitOriginalUpload.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				original_upload.submit();
//			}
//		});
		/*
		formPanel.setAction("/myFileUploadHndler");
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		FileUpload fileUpload = new FileUpload();
		fileUpload.setName("upload SPSS file");
		formPanel.add(fileUpload);
		Button submit = new Button("Submit");
		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				formPanel.submit();
			}
		});
		formPanel.add(submit);
		 formPanel.addSubmitHandler(new SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				Window.alert("Form submit handler!");
			}
		    });
		    */
		
		
		renderDBfillers(dto,res,root_concept_sysname,root_concept_name);
	}
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
				adminArticleService.deleteZacon(currentArt_DTO.getId(), cb);
			}
		}.retry(2);
	}
//	private void processUploadResponse(String response)
//	{
//		try
//		{
//			int start = response.indexOf("<RxStoreId>")+11;
//			int end = response.indexOf("</RxStoreId>");
//			String keyy = response.substring(start,end);
//		//	int start2 = response.indexOf("<totalBytes>")+12;
//		//	int end2 = response.lastIndexOf("</totalBytes>");
//			int start2 = response.indexOf("<size>")+6;
//			int end2 = response.indexOf("</size>");
//			String len = response.substring(start2,end2);
//			if(keyy != null)
//			{
//				Long id = Long.parseLong(keyy);
//				if(!uploaded_files.contains(id))
//				{
//					uploaded_files.add(id);
//					this.blobkey = id;
//					this.blob_length = Integer.parseInt(len);
//					doc_uploaded = true;
//				}
//			}
//		}catch(Exception ex)
//		{
//			ex.printStackTrace();
//		}
//		
//		//Window.alert(response);
////		int start = response.indexOf("<blobkey>")+9;
////		int end = response.lastIndexOf("</blobkey>");
////		
//		
//	}
	
	@UiHandler("submitButton")
	void onSubmitClicked(ClickEvent e)
	{
		//Window.alert("Статью успешно создано!");
		//formPanel.submit();
		createZacon();
	}
	
	
	private void fillFormValues()
	{
		currentArt_DTO.setContents(richTextEditor.getValue());
		currentArt_DTO.setEnclosure_key(blobkey);
	
		
		JSON_Representation json = mv.getJSON();
		//mv.populateItemsLinksTo(currentArt_DTO.getId(), "law");
		if(json.getObj().containsKey("law_contents"))
		{
			json.getObj().put("law_contents",new JSONString(richTextEditor.getValue().replaceAll("\\<.*?\\>", "")));
		}
		currentArt_DTO.setJson_desctiptor(json.getObj().toString());
		HashMap<String, String> mapp = mv.returnCollectedMap();
		//curr.setName(mapp.get("socioresearch_name"));
		if(!richTextEditor.getValue().equals(""))mapp.put("law_contents", richTextEditor.getValue().replaceAll("\\<.*?\\>", ""));
		currentArt_DTO.setFilling(mapp);
		currentArt_DTO.setHeader(mapp.get("law_name"));
		currentArt_DTO.setNumber(mapp.get("law_number"));
		
	}
	private void catagolize(final Long zacon_id)
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
	
	private void createZacon()
	{
		fillFormValues();
		new RPCCall<ZaconDTO>() {

			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				 Window.alert("ERROR Updated ZACON!");
			}

			@Override
			public void onSuccess(ZaconDTO res) {
				  Window.alert("Updated ZACON!");
				mv.populateItemsLinksTo(res.getId(), "law");
			}
			

			@Override
			protected void callService(AsyncCallback<ZaconDTO> cb) {
				adminArticleService.updateZacon(currentArt_DTO, cb);
			}
		}.retry(2);
//		adminArticleService.updateZacon(currentArt_DTO,
//			        new AsyncCallback<ZaconDTO>() {
//			          public void onFailure(Throwable caught) {
//			            Window.alert("An error occurred");
//			          }
//			          public void onSuccess(ZaconDTO result) {
//			             Window.alert("Updated");
//			             catagolize(result.getId());
//			             //ConnectrApp.get().showFriendList();
//			            //ConnectrApp.get().showMessages();
//			          }
//			        });
	}
}
