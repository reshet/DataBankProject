package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.Date;

import org.zenika.widget.client.datePicker.DatePicker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
//import com.google.gwt.user.datepicker.client.DatePicker;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.LoginView;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.presenters.AdminResearchDetailedPresenter;
import com.mresearch.databank.client.views.DBfillers.MetaUnitCollector;
import com.mresearch.databank.client.views.DBfillers.MetaUnitEntityItemRegistrator;
import com.mresearch.databank.client.views.DBfillers.MetaUnitFiller;
import com.mresearch.databank.client.views.DBfillers.MultiValuedField;
import com.mresearch.databank.client.views.DBfillers.SimpleStringField;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.SSE_DTO;
import com.mresearch.databank.shared.SearchTaskResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.RichTextEditor;

public class AdminResearchEditView extends Composite implements AdminResearchDetailedPresenter.EditDisplay{

	private static AdminResearchEditViewUiBinder uiBinder = GWT
			.create(AdminResearchEditViewUiBinder.class);

	interface AdminResearchEditViewUiBinder extends
			UiBinder<Widget, AdminResearchEditView> {
	}
	//AdminResearchPerspectivePresenter p;
	//@UiField RichTextArea method;
	//@UiField DateLabel date_start;
	//@UiField DatePicker date_p_start;
	//@UiField TextArea concepts;
	private SuggestBox genGeathering,method,researchers,concepts;
	private MySuggestTextBox my_researches,my_concepts;
	//@UiField HorizontalPanel calPanel;
	@UiField Button confirmBtn,deleteBtn;
	@UiField VerticalPanel elasticDBfields;
	@UiField ListBox weights;
	@UiField VerticalPanel descriptionEditor;
	private final PopupPanel addOrgPopupPanel = new PopupPanel();
	public AdminResearchEditView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	private ArrayList<Long> org_impl_ids,org_order_ids;
	private ArrayList<String> org_impl_names,org_order_names;
	private ArrayList<String> var_names;
	private ArrayList<Long> var_ids;
	private AddOrgPopupView addOrgView = new AddOrgPopupView();
	private Long org_impl_id,org_order_id;
	private Long research_id,weight_var_id;
	private String def_gengeath_text,def_method_text,def_researches_text,def_concepts_text;
	private MetaUnitMultivaluedEntityDTO _db_;
	private MultiValuedField mv;
	private SocioResearchDTO dto;
	RichTextEditor richTextEditor;  
	public AdminResearchEditView(SocioResearchDTO dto,MetaUnitMultivaluedEntityDTO db_entity)
	{
		this();
		this._db_ = db_entity;
		this.dto = dto;
		research_id = dto.getId();
		weight_var_id = dto.getVar_weight_id();
//		int i = 0;
//		if(dto.getPublications()!=null)
//		for (String publName:dto.getPublications())
//		{
//			String doi=null,url=null;
//			if (i < dto.getPublications_dois().size())doi = dto.getPublications_dois().get(i);
//			if (i < dto.getPublications_urls().size())url = dto.getPublications_urls().get(i);
//			VerticalPanel panel = new VerticalPanel();
//			panel.add(new Label(publName));
//			panel.add(new Label(doi));
//			panel.add(new Label(url));
//		//	flexPubl_tbl.setWidget(i, 0, panel);	
//			i++;
//		}
		renderDBfillers();
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
	public void addPublication(String name,String doi,String url)
	{
		VerticalPanel panel = new VerticalPanel();
		panel.add(new Label(name));
		panel.add(new Label(doi));
		panel.add(new Label(url));
		//flexPubl_tbl.setWidget(flexPubl_tbl.getRowCount(), 0, panel);	
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
	public void setVarsWeight(ArrayList<String> names, ArrayList<Long> ids) {
		var_names = names;
		var_ids = ids;
		weights.clear();
		weights.addItem("Без взвешивания");
		for(String name:var_names)
		{
			weights.addItem(name);
		}
		if (weight_var_id != null)
		{
			weights.setSelectedIndex(var_ids.indexOf(weight_var_id)+1);
		}
	}

	@Override
	public long getWeightVarID() {
		int sel = weights.getSelectedIndex();
		if (sel <= 0) return 0;
			else
				return var_ids.get(sel-1);
	}
	
	@Override
	public long getResearchID() {
		return research_id;
	}
	
	@Override
	public String getWeightVarName(long weight_var_id) {
		if (weight_var_id != 0 && var_ids.contains(weight_var_id))
			return var_names.get(var_ids.indexOf(weight_var_id));
		return "";
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
