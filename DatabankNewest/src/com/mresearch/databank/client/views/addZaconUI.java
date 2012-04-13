package com.mresearch.databank.client.views;

import java.util.ArrayList;

import org.zenika.widget.client.datePicker.DatePicker;

import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.mresearch.databank.shared.ZaconDTO;

public class addZaconUI extends Composite {

	private static addZaconUIUiBinder uiBinder = GWT
			.create(addZaconUIUiBinder.class);
	private ZaconDTO currentArt_DTO =new ZaconDTO();
	private final AdminArticleServiceAsync adminArticleService = GWT
      .create(AdminArticleService.class);
	private final CatalogServiceAsync catalogService = GWT
		      .create(CatalogService.class);
			

	interface addZaconUIUiBinder extends UiBinder<Widget, addZaconUI> {
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
	
	@UiField
	TextBox _name,_number;
	@UiField TextArea _abstract, _keywords;
	@UiField DatePicker date,date_accept,date_decline;
	//_org_prompter;
	
	//@UiField
	//FormPanel original_upload;
	
	private Label status;
	private Button parse_spss_cmd;
	private boolean doc_uploaded = false;
	private String blobkey = null;
	private long blob_length;
	private String upload_url = "/upload";
	private Long root_concept;
	@UiConstructor
	public addZaconUI(String firstName,Long root_concept) {
		initWidget(uiBinder.createAndBindUi(this));
		this.root_concept = root_concept;
		status = new Label("Добавить документ...");
		SingleUploader up = new SingleUploader();
		up.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
			
			@Override
			public void onFinish(IUploader uploader) {
				//Window.alert(uploader.getStatus());
				//Window.alert(uploader.getInputName());
				//Window.alert(uploader.getServerResponse());
				String response = uploader.getServerResponse();
				status.setText("Документ загружен");
				Window.alert(response);
				processUploadResponse(response);
			}
		});
		uploadPanel.add(status);
		uploadPanel.add(up);
		
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
		
	}

	private void processUploadResponse(String response)
	{
		int start = response.indexOf("<blobkey>")+9;
		int end = response.lastIndexOf("</blobkey>");
		String keyy = response.substring(start,end);
		int start2 = response.indexOf("<totalBytes>")+12;
		int end2 = response.lastIndexOf("</totalBytes>");
		String len = response.substring(start2,end2);
		
//		Window.alert(keyy);
//		com.google.gwt.xml.client.Document respDoc = XMLParser.parse(response);
//		Window.alert(respDoc.toString());
//		Node keyNode =respDoc.getElementsByTagName("blobkey").item(0);
//		String blobkey = keyNode.getFirstChild().getNodeValue();
//		Node lengthNode =respDoc.getElementsByTagName("totalBytes").item(0);
//		long length = Integer.parseInt(lengthNode.getFirstChild().getNodeValue());
//		Window.alert("BLOBKEY: "+blobkey);
//		
		status.setText(status.getText()+" blobkey = "+keyy);
		if(keyy != null)
		{
			this.blobkey = keyy;
			this.blob_length = Integer.parseInt(len);
			doc_uploaded = true;
		}
	}
	
	@UiHandler("submitButton")
	void onSubmitClicked(ClickEvent e)
	{
		Window.alert("Статью успешно создано!");
		//formPanel.submit();
		createZacon();
	}
	
	private void fillFormValues()
	{
		currentArt_DTO.setHeader(_name.getText());
		currentArt_DTO.setContents(_abstract.getText());
		//currentArt_DTO.setEnclosure_key(blobkey);
		//currentArt_DTO.setDate(date.getSelectedDate());
		//currentArt_DTO.setAccept_date(date_accept.getSelectedDate());
		//currentArt_DTO.setDecline_date(date_decline.getSelectedDate());
		ArrayList<String> key_words = new ArrayList<String>();
		String [] keywords = _keywords.getText().split(",");
		
		for(String keyword:keywords)
		{
			key_words.add(keyword);
		}
		//currentArt_DTO.setKey_words(key_words);
		currentArt_DTO.setNumber(_number.getText());
		//currentSR_DTO.setOrg_prompter(_org_prompter.getText());
	}
	private void catagolize(final String article_id)
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
				
			}

			@Override
			public void onSuccess(ZaconDTO arg0) {
				  Window.alert("Updated ZACON!");
			}

			@Override
			protected void callService(AsyncCallback<ZaconDTO> cb) {
				adminArticleService.updateZacon(currentArt_DTO, cb);
			}
		};
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
