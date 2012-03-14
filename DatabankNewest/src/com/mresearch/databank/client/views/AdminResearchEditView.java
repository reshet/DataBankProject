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

public class AdminResearchEditView extends Composite implements AdminResearchDetailedPresenter.EditDisplay{

	private static AdminResearchEditViewUiBinder uiBinder = GWT
			.create(AdminResearchEditViewUiBinder.class);

	interface AdminResearchEditViewUiBinder extends
			UiBinder<Widget, AdminResearchEditView> {
	}
	//AdminResearchPerspectivePresenter p;
	@UiField TextBox nameResearch,selectionSize;
	@UiField ListBox orgImpl,orgPrompt,weights;
	@UiField Anchor orgImplAdd,orgPromptAdd;
	//@UiField RichTextArea method;
	//@UiField DateLabel date_start;
	//@UiField DatePicker date_p_start;
	@UiField DatePicker date_p_start,date_p_end;
	//@UiField TextArea concepts;
	
	
	@UiField HorizontalPanel genPanel,methodPanel,concepts_panel; 
	
	private SuggestBox genGeathering,method,researchers,concepts;
	private MySuggestTextBox my_researches,my_concepts;
	//@UiField HorizontalPanel calPanel;
	@UiField Button confirmBtn,deleteBtn;
	@UiField FlexTable flexPubl_tbl;
	@UiField Button addPublBtn,delPublBtn;
	@UiField RadioButton random_btn_true,random_btn_false,complex_btn_unistep,complex_btn_multistep;
	@UiField VerticalPanel researches_panel; 
	@UiField VerticalPanel elasticDBfields;
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
	public AdminResearchEditView(SocioResearchDTO dto,MetaUnitMultivaluedEntityDTO db_entity)
	{
		this();
		this._db_ = db_entity;
		this.dto = dto;
		research_id = dto.getId();
		nameResearch.setText(dto.getName());
		nameResearch.setText(dto.getName());
		org_order_id = dto.getOrg_order_id();
		org_impl_id = dto.getOrg_impl_id();
		weight_var_id = dto.getVar_weight_id();
		def_gengeath_text = dto.getGen_geathering();
		def_method_text = dto.getMethod();
		//orgPrompt.setS
		//orgPrompt.setText(dto.getOrg_order_id());
		//orgImpl.setText(dto.getOrg_impl_id());
		def_concepts_text = UserResearchDetailedView.arrToStr(dto.getConcepts());
		def_researches_text = UserResearchDetailedView.arrToStr(dto.getResearchers());
		
		//this.publ.setText(UserResearchDetailedView.arrToStr(dto.getPublications()));
		this.date_p_start.setSelectedDate(dto.getStart_date());
		this.date_p_end.setSelectedDate(dto.getEnd_date());
		
		this.selectionSize.setText(String.valueOf(dto.getSelection_size()));
	
		this.random_btn_true.setValue(false);
		this.random_btn_false.setValue(false);
		this.complex_btn_unistep.setValue(false);
		this.complex_btn_multistep.setValue(false);
		if(dto.getSel_randomity() != null && dto.getSel_randomity().equals(SearchTaskResearchDTO.SELECTION_APPR_RANDOM))
			this.random_btn_true.setChecked(true);
		else 
			this.random_btn_false.setChecked(true);
		if(dto.getSel_complexity() != null && dto.getSel_complexity().equals(SearchTaskResearchDTO.SELECTION_APPR_COMPLEXITY_UNI))
			this.complex_btn_unistep.setChecked(true);
		else
			this.complex_btn_multistep.setChecked(true);
				
		//this.selectionAppr.setText(dto.getSelection_appr());
		//this.method.setText(dto.getMethod());
		//this.weights.setText(dto.getVar_weight_id());
		date_p_start.setOldestDate(new Date());
		date_p_end.setOldestDate(new Date());
		int i = 0;
		if(dto.getPublications()!=null)
		for (String publName:dto.getPublications())
		{
			String doi=null,url=null;
			if (i < dto.getPublications_dois().size())doi = dto.getPublications_dois().get(i);
			if (i < dto.getPublications_urls().size())url = dto.getPublications_urls().get(i);
			VerticalPanel panel = new VerticalPanel();
			panel.add(new Label(publName));
			panel.add(new Label(doi));
			panel.add(new Label(url));
			flexPubl_tbl.setWidget(i, 0, panel);	
			i++;
		}
		addOrgPopupPanel.add(addOrgView);
		renderDBfillers();
		//date_p_start.setValue(new Date());
		//date_start.setValue(new Date());
		//CalendarWidget w  = new CalendarWidget(new Date());
		//calPanel.add(w);
	}
	private void renderDBfillers()
	{
		elasticDBfields.clear();
		mv = new MultiValuedField(_db_, null,dto.getFilling());
		elasticDBfields.add(mv);
	}
	public void addPublication(String name,String doi,String url)
	{
		VerticalPanel panel = new VerticalPanel();
		panel.add(new Label(name));
		panel.add(new Label(doi));
		panel.add(new Label(url));
		flexPubl_tbl.setWidget(flexPubl_tbl.getRowCount(), 0, panel);	
	}
	@UiHandler(value="addPublBtn")
	public void doAddPubl(ClickEvent e)
	{
		PopupPanel popup = new PopupPanel(true);
		popup.add(new AddPublicationView(this,popup));
		popup.setPopupPosition(500, 100);
		popup.show();
	}
	@UiHandler(value="delPublBtn")
	public void doDelPubl(ClickEvent e)
	{
		flexPubl_tbl.removeRow(flexPubl_tbl.getRowCount()-1);
	}
	@Override
	public ArrayList<String> getConcepts() {
		ArrayList<String> arr = new ArrayList<String>();
		String text = my_concepts.getTotalText();
		String [] ar = text.split(",");
		for(String str:ar)
		{
			arr.add(str);
		}
		return arr;
	}
	@Override
	public ArrayList<String> getPublications() {
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < flexPubl_tbl.getRowCount();i++)
		{
			VerticalPanel panel = (VerticalPanel)flexPubl_tbl.getWidget(i, 0);
			Label label = (Label)panel.getWidget(0);
			arr.add(label.getText());
		}
//		String [] ar = publ.getText().split(";");
//		for(String str:ar)
//		{
//			arr.add(str);
//		}
		return arr;
	}
	@Override
	public ArrayList<String> getPublications_dois() {
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < flexPubl_tbl.getRowCount();i++)
		{
			VerticalPanel panel = (VerticalPanel)flexPubl_tbl.getWidget(i, 0);
			Label label = (Label)panel.getWidget(1);
			arr.add(label.getText());
		}
		return arr;
	}
	@Override
	public ArrayList<String> getPublications_urls() {
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < flexPubl_tbl.getRowCount();i++)
		{
			VerticalPanel panel = (VerticalPanel)flexPubl_tbl.getWidget(i, 0);
			Label label = (Label)panel.getWidget(2);
			arr.add(label.getText());
		}
		return arr;
	}
	@Override
	public ArrayList<String> getResearchers() {
		ArrayList<String> arr = new ArrayList<String>();
		String text = my_researches.getTotalText();
		String [] ar = text.split(",");
		for(String str:ar)
		{
			arr.add(str);
		}
		return arr;
	}
	@Override
	public String getMethod() {
		return method.getText();
	}
	@Override
	public Long getOrgImplID() {
		int sel = orgImpl.getSelectedIndex();
		if (sel < 0) return null;
		return org_impl_ids.get(sel);
	}
	@Override
	public Long getOrgOrderedID() {
		int sel = orgPrompt.getSelectedIndex();
		if (sel < 0) return null;
		return org_order_ids.get(sel);
	}
	
	@Override
	public int getSelectionSize() {
		int sel = Integer.parseInt(selectionSize.getText());
		return sel;
	}
	@Override
	public String getGenerealG() {
		return this.genGeathering.getText();
	}
	@Override
	public Date getStartDate() {
		return date_p_start.getSelectedDate();
	}
	@Override
	public Date getEndDate() {
		return date_p_end.getSelectedDate();
	}
	@Override
	public String getName() {
		return this.nameResearch.getText();
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
	public void setOrgImpl(ArrayList<String> names, ArrayList<Long> ids) {
		org_impl_names = names;
		org_impl_ids = ids;
		orgImpl.clear();
		for(String name:org_impl_names)
		{
			orgImpl.addItem(name);
		}
		if (org_impl_id != null)
		{
			orgImpl.setSelectedIndex(org_impl_ids.indexOf(org_impl_id));
		}
	}
	
	@Override
	public void setOrgOrder(ArrayList<String> names,ArrayList<Long> ids) {
		org_order_names = names;
		org_order_ids = ids;
		orgPrompt.clear();
		for(String name:org_order_names)
		{
			orgPrompt.addItem(name);
		}
		if (org_order_id != null)
		{
			orgPrompt.setSelectedIndex(org_order_ids.indexOf(org_order_id));
		}

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
	public HasClickHandlers getAddOrgImplBtn() {
		return addOrgView.getAddBtn();
	}
	@Override
	public HasClickHandlers getAddOrgOrderBtn() {
		return addOrgView.getAddBtn();
	}
	@Override
	public OrgDTO getAddOrgDTO() {
		return addOrgView.getOrgDTO();
	}
	@Override
	public HasClickHandlers getAddOrgImplPopup() {
		return orgImplAdd;
	}
	@Override
	public HasClickHandlers getAddOrgOrderPopup() {
		return orgPromptAdd;
	}
	@Override
	public Widget getPopupAddOrg() {
		return addOrgView;
	}
	@Override
	public void setOrgPopupPosition(int x, int y) {
		addOrgPopupPanel.setPopupPosition(x, y);
	}
	@Override
	public void setOrgPopupVisibility(boolean b) {
		addOrgPopupPanel.setVisible(b);
		if (b) addOrgPopupPanel.show();
			else addOrgPopupPanel.hide();
	}
	@Override
	public long getResearchID() {
		return research_id;
	}
	@Override
	public String getOrgImplName(long org_impl_id) {
		return org_impl_names.get(org_impl_ids.indexOf(org_impl_id));
	}
	@Override
	public String getOrgOrderName(long org_order_id) {
		return org_order_names.get(org_order_ids.indexOf(org_order_id));
	}
	@Override
	public String getWeightVarName(long weight_var_id) {
		if (weight_var_id != 0 && var_ids.contains(weight_var_id))
			return var_names.get(var_ids.indexOf(weight_var_id));
		return "";
	}
	@Override
	public String getSelectionApprchCompl() {
		if (complex_btn_unistep.getValue()) return SearchTaskResearchDTO.SELECTION_APPR_COMPLEXITY_UNI;
			else return SearchTaskResearchDTO.SELECTION_APPR_COMPLEXITY_MULTI;
	}
	@Override
	public String getSelectionApprchRand() {
		if (random_btn_true.getValue()) return SearchTaskResearchDTO.SELECTION_APPR_RANDOM;
			else return SearchTaskResearchDTO.SELECTION_APPR_NOT_RANDOM;
	}
	@UiHandler(value="random_btn_true") public void onRandomBtnCheck(ClickEvent e)
	{
		random_btn_false.setValue(false);
	}
	@UiHandler(value="random_btn_false") public void onNotRandomBtnCheck(ClickEvent e)
	{
		random_btn_true.setValue(false);
	}
	@UiHandler(value="complex_btn_unistep") public void onUniBtnCheck(ClickEvent e)
	{
		complex_btn_multistep.setValue(false);
	}
	@UiHandler(value="complex_btn_multistep") public void onMultiBtnCheck(ClickEvent e)
	{
		complex_btn_unistep.setValue(false);
	}
	@Override
	public void setGenGeaths(ArrayList<SSE_DTO> sses) {
		MultiWordSuggestOracle oracle1 = new MultiWordSuggestOracle();
		for(SSE_DTO dto:sses)
		{
			oracle1.add(dto.getContents());
		}
		genGeathering = new SuggestBox(oracle1);
		genGeathering.setText(def_gengeath_text);
		genPanel.clear();
		genPanel.add(genGeathering);
		
		
	}

	@Override
	public void setMethods(ArrayList<SSE_DTO> sses) {
		MultiWordSuggestOracle oracle1 = new MultiWordSuggestOracle();
		for(SSE_DTO dto:sses)
		{
			oracle1.add(dto.getContents());
		}
		method = new SuggestBox(oracle1);
		method.setText(def_method_text);
		methodPanel.clear();
		methodPanel.add(method);
	}

	@Override
	public void setResearches(ArrayList<SSE_DTO> sses) {
		MultiWordSuggestOracle oracle1 = new MultiWordSuggestOracle();
		for(SSE_DTO dto:sses)
		{
			oracle1.add(dto.getContents());
		}
		researches_panel.clear();
		my_researches = new  MySuggestTextBox();
		researchers = new SuggestBox(oracle1,my_researches);
		researchers.setWidth("500px");
		researchers.setHeight("40px");
		researchers.setText(def_researches_text);
		//box.s
		researches_panel.add(researchers);
	}

	@Override
	public void setConcepts(ArrayList<SSE_DTO> sses) {
		MultiWordSuggestOracle oracle1 = new MultiWordSuggestOracle();
		for(SSE_DTO dto:sses)
		{
			oracle1.add(dto.getContents());
		}
		concepts_panel.clear();
		my_concepts = new  MySuggestTextBox();
		concepts = new SuggestBox(oracle1,my_concepts);
		concepts.setText(def_concepts_text);
		concepts.setWidth("500px");
		concepts.setHeight("40px");
		concepts_panel.add(concepts);
	}

	
	@Override
	public HasClickHandlers getCancelAddOrgBtn() {
		return addOrgView.getCancelBtn();
	}
	@Override
	public MetaUnitFiller getDBfiller() {
		return mv;
	}
	@Override
	public MetaUnitCollector getDBcollector() {
		return mv;
	}
}
