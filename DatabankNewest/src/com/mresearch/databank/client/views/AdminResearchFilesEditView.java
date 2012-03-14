package com.mresearch.databank.client.views;

import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.presenters.AdminResearchDetailedPresenter;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.shared.SocioResearchFilesDTO;
import com.sun.java.swing.plaf.windows.resources.windows;

public class AdminResearchFilesEditView extends Composite{
	private static AdminResearchFilesEditViewUiBinder uiBinder = GWT
			.create(AdminResearchFilesEditViewUiBinder.class);

	interface AdminResearchFilesEditViewUiBinder extends
			UiBinder<Widget, AdminResearchFilesEditView> {
	}
	private final AdminSocioResearchServiceAsync adminResearchService = GWT
		      .create(AdminSocioResearchService.class);
	private final UserSocioResearchServiceAsync userResearchService = GWT
		      .create(UserSocioResearchService.class);
	
	private SingleUploader uploader;
	private long research_id;
	private String category;
	public AdminResearchFilesEditView(long research_id,String category,SocioResearchFilesDTO dto) {
		initWidget(uiBinder.createAndBindUi(this));
		this.research_id = research_id;
		this.category = category;
		uploader = new SingleUploader();
		uploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
			private int counter_bugfix = 0;
			@Override
			public void onFinish(IUploader uploader) {
				String response = uploader.getServerResponse();
				if(counter_bugfix % 2 == 0)processUploadResponse(response);
				counter_bugfix++;
			}
		});
		upload_panel.add(uploader);
		uploader.setEnabled(true);
//		file_desc.addKeyPressHandler(new KeyPressHandler() {
//			@Override
//			public void onKeyPress(KeyPressEvent event) {
//				if(file_desc.getText().length() > 0 && !file_desc.getText().equals(""))
//				{
//					uploader.setEnabled(true);
//				}else
//				{
//					uploader.setEnabled(false);
//				}
//			}
//		});
//		file_desc.addKeyDownHandler(new KeyDownHandler() {
//			@Override
//			public void onKeyDown(KeyDownEvent event) {
//				if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
//				{
//					if(file_desc.getText().length() > 0 && !file_desc.getText().equals(""))
//					{
//						uploader.setEnabled(true);
//					}else
//					{
//						uploader.setEnabled(false);
//					}
//				}
//			}
//		});
		fillWithFetched(dto);
	}
	
	
	@UiField FlexTable files_table;
	@UiField VerticalPanel upload_panel;
//	@UiField TextBox file_desc;
	@UiHandler(value="addBtn")
	public void addFile(ClickEvent e)
	{
		uploader.reset();
		uploader.reuse();
		//uploader.setEnabled(false);
		upload_panel.setVisible(true);
	}
	private void processUploadResponse(String response)
	{
		int start = response.indexOf("<RxStoreId>")+11;
		int end = response.lastIndexOf("</RxStoreId>");
		String keyy = response.substring(start,end);
		int start2 = response.indexOf("<name>")+6;
		int end2 = response.lastIndexOf("</name>");
		//Window.alert(response);
		String desc = response.substring(start2,end2);
		if(keyy != null)
		{
			doAddFile(Integer.parseInt(keyy), desc);
		}
	}
	private void doAddFile(final long key,final String desc)
	{
		new RPCCall<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on adding file to research "+caught.getMessage());
			}

			@Override
			public void onSuccess(Boolean result) {
				upload_panel.setVisible(false);
				//file_desc.setText("");
				doFetchFiles();
			}

			@Override
			protected void callService(AsyncCallback<Boolean> cb) {
				adminResearchService.addFileToAccessor(research_id, key, desc,category, cb);
			}
		}.retry(2);
		
	}
	private void fillWithFetched(SocioResearchFilesDTO dto)
	{
		files_table.clear();
		for(int i = 0; i < dto.getFiles_ids().size();i++)
		{
			files_table.setWidget(i, 0, new Label(String.valueOf(i)));
			files_table.setWidget(i, 1, new Label(dto.getFiles_descs().get(i)));
			Button del_btn = new Button("Удалить");
			final long file_id = dto.getFiles_ids().get(i);
			del_btn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					doDeleteFile(file_id);
				}
			});
			files_table.setWidget(i, 2, new HTML("<a href=\""+"/databanknewest/serve?blob-key="+file_id+"\">Скачать</a>"));
			files_table.setWidget(i, 3, del_btn);
		}
	}
	
	
	
	
	
	
	
	private void doFetchFiles()
	{
		new RPCCall<SocioResearchFilesDTO>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on fetching files "+caught.getMessage());
			}

			@Override
			public void onSuccess(SocioResearchFilesDTO result) {
				fillWithFetched(result);
			}

			@Override
			protected void callService(AsyncCallback<SocioResearchFilesDTO> cb) {
				userResearchService.getResearchFilesInCategory(research_id, category, cb);
			}
		}.retry(2);
	}
	private void doDeleteFile(final long file_id)
	{
		new RPCCall<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on deleting file "+caught.getMessage());
			}

			@Override
			public void onSuccess(Boolean result) {
				doFetchFiles();
			}
			@Override
			protected void callService(
					AsyncCallback<Boolean> cb) {
				adminResearchService.deleteFileFromAccessor(research_id, file_id, cb);
			}
		}.retry(1);
	}
}
