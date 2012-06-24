package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminArticleService;
import com.mresearch.databank.client.service.AdminArticleServiceAsync;
import com.mresearch.databank.client.views.DBviewers.MultiValuedFieldViewer;
import com.mresearch.databank.client.views.DBviewers.NiceMultiValuedFieldViewer;
import com.mresearch.databank.client.views.DBviewers.NiceMultiValuedValuesViewer;
import com.mresearch.databank.client.views.DBviewers.NiceMultiValuedViewer;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.SocioResearchFilesDTO;
import com.mresearch.databank.shared.ZaconDTO;
import com.sun.org.apache.xpath.internal.operations.And;

public class ZaconDetailedView extends Composite {

	private static ZaconDetailedViewUiBinder uiBinder = GWT
			.create(ZaconDetailedViewUiBinder.class);

	interface ZaconDetailedViewUiBinder extends
			UiBinder<Widget, ZaconDetailedView> {
	}
	private final AdminArticleServiceAsync adminLawService = GWT
		      .create(AdminArticleService.class);

	//@UiField Label _name,_abstract,_number;
	//keywords,authors,date,date_accept,date_decline;
	//@UiField Hyperlink _enc_link;
	//@UiField HorizontalPanel link_panel;
	@UiField Anchor show_meta,show_files;
	@UiField HTML html;
	@UiField Label name;
	//@UiField Label path;
	VerticalPanel elasticDBfieldNames = new VerticalPanel();
	VerticalPanel elasticDBfieldValues = new VerticalPanel();
	public String arrToStr(ArrayList<String> arr)
	{
		String ans = "";
		for(String str:arr)
		{
			ans+=str+",";
		}
		if (ans.length() > 2)ans = ans.substring(0, ans.length()-2);
		return ans;
	}
	private ZaconDTO dto;
	private MetaUnitMultivaluedEntityDTO db;
	public ZaconDetailedView(ZaconDTO dto,MetaUnitMultivaluedEntityDTO dt,String path) {
		initWidget(uiBinder.createAndBindUi(this));
		//this._name.setText(dto.getHeader());
		//this._abstract.setText(dto.getContents());
		//this._number.setText(dto.getNumber());
		this.dto =  dto;
		this.db = dt;
		this.html.setHTML(dto.getContents()==null?"":dto.getContents());
		this.name.setText(dto.getHeader());
	//	this.path.setText(path);
		
		
//		authors.setText(arrToStr(dto.getAuthors()));
//		keywords.setText(arrToStr(dto.getKey_words()));
//		if (dto.getDate() != null)date.setText(dto.getDate().toString());
//		if (dto.getAccept_date() != null)date_accept.setText(dto.getAccept_date().toString());
//		if (dto.getDecline_date() != null)date_decline.setText(dto.getDecline_date().toString());
//		
		//String realPath = GWT.getModuleBaseURL();
		
		//_enc_link.setTargetHistoryToken();
		//link_panel.add(new HTML("<a href=\""+realPath+"serve?blob-key="+dto.getEnclosure_key()+"\">Скачать документ </a>"));
		//renderDBfillers();
		}
		
	
	
	@UiHandler(value="show_meta")
	public void onShowMeta(ClickEvent ev)
	{
		renderDBfillers();
	}
	@UiHandler(value="show_files")
	public void onShowFiels(ClickEvent ev)
	{
		doShowFiles();
	}
		private void renderDBfillers()
		{
			
			
			elasticDBfieldNames.clear();
			//elasticDBfieldValues.clear();
			
			
			if(dto.getFilling()== null)dto.setFilling(new HashMap<String, String>());
			
			ArrayList<String> exclude = new ArrayList<String>();
			exclude.add("contents");
			NiceMultiValuedViewer mv = new NiceMultiValuedViewer(db,dto.getFilling(),"",exclude);
			//NiceMultiValuedValuesViewer mv2 = new NiceMultiValuedValuesViewer(db,dto.getFilling(),"");
			
			elasticDBfieldNames.add(mv);
			//elasticDBfieldValues.add(mv2);
			
			
			HorizontalPanel h = new HorizontalPanel();
			h.setSpacing(6);
			h.add(elasticDBfieldNames);
			//h.add(elasticDBfieldValues);
			
			//MultiValuedFieldViewer mv = new MultiValuedFieldViewer(db,dto.getFilling(),"");
			
			final PopupPanel dialogBox = createDialogBox(h);
		    dialogBox.setGlassEnabled(true);
		    dialogBox.setAnimationEnabled(true);
		    
			dialogBox.center();
	        dialogBox.show();
		}
		private void doShowFiles()
		{
			new RPCCall<SocioResearchFilesDTO>() {

				@Override
				public void onFailure(Throwable arg0) {
					
				}

				@Override
				public void onSuccess(SocioResearchFilesDTO res) {
					UserLawFilesView h = new UserLawFilesView(dto.getId(), "Вложения:", res);
					final PopupPanel dialogBox = createDialogBox(h);
				    dialogBox.setGlassEnabled(true);
				    dialogBox.setAnimationEnabled(true);
				    
					dialogBox.center();
			        dialogBox.show();
				}

				@Override
				protected void callService(
						AsyncCallback<SocioResearchFilesDTO> cb) {
					adminLawService.getFilesInCategory(dto.getId(), "Вложения:", cb);
				}
			}.retry(2);
			
			
		}
		
		private PopupPanel createDialogBox(Widget w) {
		    // Create a dialog box and set the caption text
		    final PopupPanel dialogBox = new PopupPanel();
		    //dialogBox.setText("График распределения");
		   // dialogBox.setHeight("400px");
		  // dialogBox.setWidth("400px");
		    // Create a table to layout the content
		    VerticalPanel dialogContents = new VerticalPanel();
		    dialogContents.setWidth("100%");
		    dialogContents.setHeight("100%");
		    
		    dialogContents.setSpacing(4);
		    
		 
		    // Add some text to the top of the dialog
		   

		    // Add a close button at the bottom of the dialog
		    Button closeButton = new Button("Закрыть", new ClickHandler() {
		          public void onClick(ClickEvent event) {
		            dialogBox.hide();
		          }
		        });
		    dialogContents.add(w);
		    dialogContents.add(closeButton);
		    dialogBox.setWidget(dialogContents);
		    return dialogBox;
		    
		    
		  }	
	
//	@UiHandler(value="_enc_link")
//	public void onEncLinkClick(ClickEvent e)
//	{
//		//_enc_link.set
//		Window.alert("Enc loaded!");
//	}

}
