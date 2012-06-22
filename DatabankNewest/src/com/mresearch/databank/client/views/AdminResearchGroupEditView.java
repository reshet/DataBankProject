package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.Date;

import org.zenika.widget.client.datePicker.DatePicker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
//import com.google.gwt.user.datepicker.client.DatePicker;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.LoginView;
import com.mresearch.databank.client.presenters.AdminResearchDetailedPresenter;
import com.mresearch.databank.client.views.DBfillers.MetaUnitCollector;
import com.mresearch.databank.client.views.DBfillers.MetaUnitEntityItemRegistrator;
import com.mresearch.databank.client.views.DBfillers.MetaUnitFiller;
import com.mresearch.databank.client.views.DBfillers.MultiValuedField;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.SearchTaskResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.smartgwt.client.widgets.RichTextEditor;

public class AdminResearchGroupEditView extends Composite implements AdminResearchDetailedPresenter.GroupEditDisplay{

	private static AdminResearchGroupEditViewUiBinder uiBinder = GWT
			.create(AdminResearchGroupEditViewUiBinder.class);

	interface AdminResearchGroupEditViewUiBinder extends
			UiBinder<Widget, AdminResearchGroupEditView> {
	}
	//AdminResearchPerspectivePresenter p;
	//@UiField TextBox nameResearch,selectionSize;
	@UiField ListBox ethalonSelector;
	@UiField VerticalPanel researchesToPropogate,elasticDBfields,descriptionEditor;
	@UiField Button confirmBtn,deleteBtn;
	private final PopupPanel addOrgPopupPanel = new PopupPanel();
	public AdminResearchGroupEditView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	private ArrayList<String> researches_names;
	private ArrayList<Long> researches_ids;
	private Long research_id;
	private SocioResearchDTO dto;
	RichTextEditor richTextEditor;  
	private MetaUnitMultivaluedEntityDTO _db_;
	private MultiValuedField mv;

	
	public AdminResearchGroupEditView(SocioResearchDTO dto,MetaUnitMultivaluedEntityDTO db_entity)
	{
		this();
		this._db_ = db_entity;
		updateViewedDTO(dto);
		//date_p_start.setValue(new Date());
		//date_start.setValue(new Date());
		//CalendarWidget w  = new CalendarWidget(new Date());
		//calPanel.add(w);
	}
	
private void renderDBfillers()
{
	
	descriptionEditor.clear();
	
    richTextEditor = new RichTextEditor();
    richTextEditor.setHeight(400);  
    richTextEditor.setWidth("700px");
    //richTextEditor.setOverflow(Overflow.HIDDEN);  
    richTextEditor.setCanDragResize(true);  
    //richTextEditor.setShowEdges(true);  

    // Standard control group options include  
    // "fontControls", "formatControls", "styleControls" and "colorControls"  
    //richTextEditor.setControlGroups(new String[]{"fontControls", "styleControls"});  
    richTextEditor.setValue(dto.getDesctiption());  
    
    descriptionEditor.add(richTextEditor);
   // layout.addMember();  
	
	
	elasticDBfields.clear();
	mv = new MultiValuedField(_db_, null,dto.getFilling(),"");
	elasticDBfields.add(mv);
	
	
	
	
	
	
	
	
}
	
	@Override
	public void updateViewedDTO(SocioResearchDTO dto)
	{
		research_id = dto.getId();
		this.dto = dto;
		renderDBfillers();
	}
	
	@Override
	public HasClickHandlers getCondirmBtn() {
		return confirmBtn;
	}
	@Override
	public HasClickHandlers getDeleteBtn() {
		return deleteBtn;
	}
	@Override
	public void setResearchesAvaible(ArrayList<String> names, ArrayList<Long> ids) {
		researches_names = names;
		researches_ids = ids;
		ethalonSelector.clear();
		for(String name:researches_names)
		{
			ethalonSelector.addItem(name);
		}
		if (research_id != null)
		{
			ethalonSelector.setSelectedIndex(researches_ids.indexOf(research_id));
		}
	}

	
	@Override
	public HasClickHandlers getEthalonSelector() {
		return ethalonSelector;
	}
	@Override
	public long getEthalonSelectedID() {
		return researches_ids.get(ethalonSelector.getSelectedIndex());
	}
	@Override
	public VerticalPanel getPickResearchesToPropagatePanel() {
		// TODO Auto-generated method stub
		return researchesToPropogate;
	}

	@Override
	public MetaUnitFiller getDBfiller() {
		return mv;
	}

	@Override
	public MetaUnitCollector getDBcollector() {
		return mv;
	}

	@Override
	public MetaUnitEntityItemRegistrator getDBregistrator() {
		return mv;
	}

	@Override
	public String getDescription() {
		return richTextEditor.getValue();
	}

}
