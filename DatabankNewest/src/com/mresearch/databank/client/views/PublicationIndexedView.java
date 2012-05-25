package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
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
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.event.ShowPublicationDetailsEvent;
import com.mresearch.databank.client.views.DBviewers.MultiValuedFieldViewer;
import com.mresearch.databank.client.views.DBviewers.NiceMultiValuedFieldViewer;
import com.mresearch.databank.client.views.DBviewers.NiceMultiValuedValuesViewer;
import com.mresearch.databank.client.views.DBviewers.NiceMultiValuedViewer;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.PublicationDTO;
import com.sun.org.apache.xpath.internal.operations.And;

public class PublicationIndexedView extends Composite {

	private static PublicationDetailedViewUiBinder uiBinder = GWT
			.create(PublicationDetailedViewUiBinder.class);

	interface PublicationDetailedViewUiBinder extends
			UiBinder<Widget, PublicationIndexedView> {
	}

	
	
	//@UiField Label _name,_abstract,_number;
	//keywords,authors,date,date_accept,date_decline;
	//@UiField Hyperlink _enc_link;
	@UiField HorizontalPanel link_panel;
	@UiField Anchor show_meta;
	@UiField HTML html;
	@UiField Label name;
	VerticalPanel elasticDBfieldNames = new VerticalPanel();
	//VerticalPanel elasticDBfieldValues = new VerticalPanel();
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
	private PublicationDTO dto;
	private MetaUnitMultivaluedEntityDTO db;
	public PublicationIndexedView(PublicationDTO dto,MetaUnitMultivaluedEntityDTO dt) {
		initWidget(uiBinder.createAndBindUi(this));
		//this._name.setText(dto.getHeader());
		//this._abstract.setText(dto.getContents());
		//this._number.setText(dto.getNumber());
		this.dto =  dto;
		this.db = dt;
		String cont = dto.getContents()==null?"":dto.getContents().replaceAll("\\<.*?>","");
		if(cont.length()>450)cont = cont.substring(0, 450);
		this.html.setHTML(cont);
		this.name.setText(dto.getHeader());
//		authors.setText(arrToStr(dto.getAuthors()));
//		keywords.setText(arrToStr(dto.getKey_words()));
//		if (dto.getDate() != null)date.setText(dto.getDate().toString());
//		if (dto.getAccept_date() != null)date_accept.setText(dto.getAccept_date().toString());
//		if (dto.getDecline_date() != null)date_decline.setText(dto.getDecline_date().toString());
//		
		String realPath = GWT.getModuleBaseURL();
		
		//_enc_link.setTargetHistoryToken();
		if(dto.getEnclosure_key()!=null)link_panel.add(new HTML("<a href=\""+realPath+"serve?blob-key="+dto.getEnclosure_key()+"\">Скачать документ </a>"));
		//renderDBfillers();
		}
		
	
	@UiHandler(value="show_meta")
	public void onShowMeta(ClickEvent ev)
	{
		renderDBfillers();
	}
	@UiHandler(value="show_deeper")
	public void onShowDeeper(ClickEvent ev)
	{
		DatabankApp.get().getEventBus().fireEvent(new ShowPublicationDetailsEvent(dto.getId()));
	}
		private void renderDBfillers()
		{
			
			elasticDBfieldNames.clear();
			//elasticDBfieldValues.clear();
			
			if(dto.getFilling()== null)dto.setFilling(new HashMap<String, String>());
			ArrayList<String> exclude = new ArrayList<String>();
			exclude.add("contents");
			NiceMultiValuedViewer mv = new NiceMultiValuedViewer(db,dto.getFilling(),"",exclude);
			//NiceMultiValuedFieldViewer mv = new NiceMultiValuedFieldViewer(db,dto.getFilling(),"");
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
		   // dialogContents.
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
