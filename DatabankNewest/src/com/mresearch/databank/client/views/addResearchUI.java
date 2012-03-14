package com.mresearch.databank.client.views;

import java.util.ArrayList;

import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnStartUploaderHandler;
import gwtupload.client.MultiUploader;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.shared.SocioResearchDTO;

public class addResearchUI extends Composite {

	private static addResearchUIUiBinder uiBinder = GWT
			.create(addResearchUIUiBinder.class);
	private SocioResearchDTO currentSR_DTO =new SocioResearchDTO();
	private final AdminSocioResearchServiceAsync adminResearchService = GWT
      .create(AdminSocioResearchService.class);

	interface addResearchUIUiBinder extends UiBinder<Widget, addResearchUI> {
	}

	
//	@UiField
//	FileUpload fileUpload;
	
//	@UiField
//	Button submitButton;
	//,submitOriginalUpload;
	
//	@UiField
//	FormPanel formPanel;
	
	@UiField
	VerticalPanel uploadPanel;
	
//	@UiField
//	TextBox _name;
	//_org_prompter;
	
	//@UiField
	//FormPanel original_upload;
	@UiField VerticalPanel states_panel;
	private Label status;
	private Button parse_spss_cmd;
	private boolean spss_uploaded = false;
	private Long blobkey = null;
	private String current_uploading_name = "";
	private ArrayList<String> uploading_names = new ArrayList<String>();
	private ArrayList<Label> uploading_states = new ArrayList<Label>();
	private ArrayList<Long> uploaded_files = new ArrayList<Long>();
	private long blob_length;
	private String upload_url = "/upload";
	@UiConstructor
	public addResearchUI(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		//status = new Label("Выбрать файл массива...");
//		parse_spss_cmd = new Button("Обработать массив!");
//		//parse_spss_cmd.setVisible(false);
//		parse_spss_cmd.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				parseSPSScmd();
//			}
		
//		});
	
		
		
		
		
		final SingleUploader upm = new SingleUploader();
		//upm.setMaximumFiles(5);
		upm.setValidExtensions("sav");
	//SingleUploader up = new SingleUploader();
		//upm.
		upm.addOnStartUploadHandler(new OnStartUploaderHandler() {
			@Override
			public void onStart(IUploader uploader) {
				//uploading_names.add(uploader.getBasename());
				//uploading_states.add(new Label("Выгрузка массива "+uploading_names.get(uploading_names.size()-1)));
				//states_panel.add(uploading_states.get(uploading_states.size()-1));
			}
		});
		upm.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
			
			@Override
			public void onFinish(IUploader uploader) {
				//Window.alert(uploader.getStatus());
				//Window.alert(uploader.getInputName());
				//Window.alert(uploader.getServerResponse());
				String response = uploader.getServerResponse();
				//current_uploading_name = getUploadedName(response);
				//status.setText("Массив загружен: "+current_uploading_name);
//				if(uploading_names.contains(current_uploading_name))
//					uploading_states.get(uploading_names.indexOf(current_uploading_name)).setText("Массив загружен: "+current_uploading_name);
//				//Window.alert(response);
				processUploadResponse(response);
				upm.reuse();
				upm.reset();
			}
		});
		
		
		
	//	uploadPanel.add(status);
		uploadPanel.add(upm);
		//uploadPanel.add(parse_spss_cmd);
		
		//BlobstoreService b_serv = BlobstoreServiceFactory.getBlobstoreService();
	//	original_upload.setAction(b_serv.createUploadUrl("/upload"));
		//original_upload.setMethod("post");
		//original_upload.setEncoding("multipart/form-data");
//		adminResearchService.getBlobstoreUploadURL(new AsyncCallback<String>() {
//			@Override
//			public void onSuccess(String result) {
//				addResearchUI.this.upload_url = result;
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
	private String getUploadedName(String response)
	{
		int start2 = response.indexOf("<name>")+6;
		int end2 = response.lastIndexOf("</name>");
		String name = response.substring(start2,end2);
		return name;
	}
	private void processUploadResponse(String response)
	{
		try
		{
			int start = response.indexOf("<RxStoreId>")+11;
			int end = response.indexOf("</RxStoreId>");
			String keyy = response.substring(start,end);
		//	int start2 = response.indexOf("<totalBytes>")+12;
		//	int end2 = response.lastIndexOf("</totalBytes>");
			int start2 = response.indexOf("<size>")+6;
			int end2 = response.indexOf("</size>");
			String len = response.substring(start2,end2);
			
			
			
	
			
			
			
			
//			Window.alert(keyy);
//			com.google.gwt.xml.client.Document respDoc = XMLParser.parse(response);
//			Window.alert(respDoc.toString());
//			Node keyNode =respDoc.getElementsByTagName("blobkey").item(0);
//			String blobkey = keyNode.getFirstChild().getNodeValue();
//			Node lengthNode =respDoc.getElementsByTagName("totalBytes").item(0);
//			long length = Integer.parseInt(lengthNode.getFirstChild().getNodeValue());
//			Window.alert("BLOBKEY: "+blobkey);
//			
			//status.setText(status.getText()+" blobkey = "+keyy);
			if(keyy != null)
			{
				Long id = Long.parseLong(keyy);
				if(!uploaded_files.contains(id))
				{
					uploaded_files.add(id);
					this.blobkey = id;
					this.blob_length = Integer.parseInt(len);
					spss_uploaded = true;
					//parse_spss_cmd.setVisible(true);
					//status.setText("Обработка "+current_uploading_name);
//					if(uploading_names.contains(current_uploading_name))
//						uploading_states.get(uploading_names.indexOf(current_uploading_name)).setText("Обработка: "+current_uploading_name);
//					
					parseSPSScmd();
				}
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		//Window.alert(response);
//		int start = response.indexOf("<blobkey>")+9;
//		int end = response.lastIndexOf("</blobkey>");
//		
		
	}
	
//	@UiHandler("submitButton")
//	void onSubmitClicked(ClickEvent e)
//	{
//		//Window.alert("Submitted!");
//		//formPanel.submit();
//		createResearch();
//	}
	
	private void fillFormValues()
	{
		//currentSR_DTO.setName(_name.getText());
		currentSR_DTO.setName(current_uploading_name);
		//currentSR_DTO.setOrg_prompter(_org_prompter.getText());
	}
	private void parseSPSScmd()
	{
		new RPCCall<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				 Window.alert("An error occurred: "+caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Research Parsed!");
			}

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				adminResearchService.parseSPSS(blobkey, blob_length, cb);
			}
		}.retry(2);
//		adminResearchService.parseSPSS(blobkey, blob_length,new AsyncCallback<Void>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				 Window.alert("An error occurred: "+caught.getMessage());
//			}
//
//			@Override
//			public void onSuccess(Void result) {
//				// TODO Auto-generated method stub
//				//currentSR_DTO.setId(result);
//				// Window.alert("Parsed!"+result);
//				//createResearch();
////	    		if(uploading_names.contains(current_uploading_name))
////	      			uploading_states.get(uploading_names.indexOf(current_uploading_name)).setText("Добавленo в базу: "+current_uploading_name);
////	  
//				Window.alert("Research Parsed!");
//			}	
//		});
	}
	private void createResearch()
	{
		fillFormValues();
		status.setText("Добавление в базу "+current_uploading_name);

		if(uploading_names.contains(current_uploading_name))
			uploading_states.get(uploading_names.indexOf(current_uploading_name)).setText("Добавление в базу: "+current_uploading_name);
		
		adminResearchService.updateResearch(currentSR_DTO,
			        new AsyncCallback<SocioResearchDTO>() {
			          public void onFailure(Throwable caught) {
			            Window.alert("An error occurred");
			          }

			          public void onSuccess(SocioResearchDTO result) {
			        	  status.setText("Добавлено в базу "+current_uploading_name);

			      		if(uploading_names.contains(current_uploading_name))
			      			uploading_states.get(uploading_names.indexOf(current_uploading_name)).setText("Добавленo в базу: "+current_uploading_name);
			      		
			      		  //Window.alert("Updated");
			            //ConnectrApp.get().showFriendList();
			            //ConnectrApp.get().showMessages();
			          }
			        });
	}

}
