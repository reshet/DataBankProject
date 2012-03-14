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
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.SearchTaskResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO;

public class AdminResearchGroupEditView extends Composite implements AdminResearchDetailedPresenter.GroupEditDisplay{

	private static AdminResearchGroupEditViewUiBinder uiBinder = GWT
			.create(AdminResearchGroupEditViewUiBinder.class);

	interface AdminResearchGroupEditViewUiBinder extends
			UiBinder<Widget, AdminResearchGroupEditView> {
	}
	//AdminResearchPerspectivePresenter p;
	//@UiField TextBox nameResearch,selectionSize;
	@UiField ListBox ethalonSelector;
	@UiField VerticalPanel researchesToPropogate;
	@UiField ListBox orgImpl,orgPrompt;
	//,weights;
	@UiField Anchor orgImplAdd,orgPromptAdd;
	//@UiField RichTextArea method;
	//@UiField DateLabel date_start;
	//@UiField DatePicker date_p_start;
	@UiField DatePicker date_p_start,date_p_end;
	@UiField TextArea concepts,researchers,method,genGeathering;
	//@UiField HorizontalPanel calPanel;
	@UiField Button confirmBtn,deleteBtn;
	@UiField FlexTable flexPubl_tbl;
	@UiField Button addPublBtn,delPublBtn;
	@UiField RadioButton random_btn_true,random_btn_false,complex_btn_unistep,complex_btn_multistep;
	private final PopupPanel addOrgPopupPanel = new PopupPanel();
	public AdminResearchGroupEditView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	private ArrayList<String> org_impl_ids,org_order_ids;
	private ArrayList<String> org_impl_names,org_order_names;
	private ArrayList<String> researches_names;
	private ArrayList<Long> researches_ids;
	private AddOrgPopupView addOrgView = new AddOrgPopupView();
	private Long org_impl_id,org_order_id;
	private Long research_id;
	public AdminResearchGroupEditView(SocioResearchDTO dto)
	{
		this();
		updateViewedDTO(dto);
		addOrgPopupPanel.add(addOrgView);
		//date_p_start.setValue(new Date());
		//date_start.setValue(new Date());
		//CalendarWidget w  = new CalendarWidget(new Date());
		//calPanel.add(w);
	}
	
	
	@Override
	public void updateViewedDTO(SocioResearchDTO dto)
	{
		research_id = dto.getId();
		org_order_id = dto.getOrg_order_id();
		org_impl_id = dto.getOrg_impl_id();
		//weight_var_id = dto.getVar_weight_id();
		//orgPrompt.setS
		//orgPrompt.setText(dto.getOrg_order_id());
		//orgImpl.setText(dto.getOrg_impl_id());
		this.concepts.setText(UserResearchDetailedView.arrToStr(dto.getConcepts()));
		this.researchers.setText(UserResearchDetailedView.arrToStr(dto.getResearchers()));
		//this.publ.setText(UserResearchDetailedView.arrToStr(dto.getPublications()));
		this.date_p_start.setSelectedDate(dto.getStart_date());
		this.date_p_end.setSelectedDate(dto.getEnd_date());
		
		this.genGeathering.setText(dto.getGen_geathering());
		//this.selectionSize.setText(String.valueOf(dto.getSelection_size()));
	
		this.random_btn_true.setValue(false);
		this.random_btn_false.setValue(false);
		this.complex_btn_unistep.setValue(false);
		this.complex_btn_multistep.setValue(false);
		if(dto.getSel_randomity() != null && dto.getSel_randomity().equals(SearchTaskResearchDTO.SELECTION_APPR_RANDOM))
			this.random_btn_true.setValue(true);
		else 
			this.random_btn_false.setValue(true);
		if(dto.getSel_complexity() != null && dto.getSel_complexity().equals(SearchTaskResearchDTO.SELECTION_APPR_COMPLEXITY_UNI))
			this.complex_btn_unistep.setValue(true);
		else
			this.complex_btn_multistep.setValue(true);
				
		//this.selectionAppr.setText(dto.getSelection_appr());
		this.method.setText(dto.getMethod());
		//this.weights.setText(dto.getVar_weight_id());
		date_p_start.setOldestDate(new Date());
		date_p_end.setOldestDate(new Date());
		int i = 0;
		if(dto.getPublications() != null)
		for (String publName:dto.getPublications())
		{
			String doi = dto.getPublications_dois().get(i);
			VerticalPanel panel = new VerticalPanel();
			panel.add(new Label(publName));
			panel.add(new Label(doi));
			flexPubl_tbl.setWidget(i, 0, panel);	
			i++;
		}
		
	}
	
	
	public void addPublication(String name,String doi)
	{
		VerticalPanel panel = new VerticalPanel();
		panel.add(new Label(name));
		panel.add(new Label(doi));
		flexPubl_tbl.setWidget(flexPubl_tbl.getRowCount(), 0, panel);	
	}
	@UiHandler(value="addPublBtn")
	public void doAddPubl(ClickEvent e)
	{
		PopupPanel popup = new PopupPanel(true);
		//popup.add(new AddPublicationView(this,popup));
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
		String [] ar = concepts.getText().split(";");
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
	public ArrayList<String> getResearchers() {
		ArrayList<String> arr = new ArrayList<String>();
		String [] ar = researchers.getText().split(";");
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
	public String getOrgImplID() {
		int sel = orgImpl.getSelectedIndex();
		if (sel < 0) return null;
		return org_impl_ids.get(sel);
	}
	@Override
	public String getOrgOrderedID() {
		int sel = orgPrompt.getSelectedIndex();
		if (sel < 0) return null;
		return org_order_ids.get(sel);
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
	public HasClickHandlers getCondirmBtn() {
		return confirmBtn;
	}
	@Override
	public HasClickHandlers getDeleteBtn() {
		return deleteBtn;
	}
	@Override
	public void setOrgImpl(ArrayList<String> names, ArrayList<String> ids) {
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
	public void setOrgOrder(ArrayList<String> names,ArrayList<String> ids) {
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
	public String getOrgImplName(String org_impl_id) {
		return org_impl_names.get(org_impl_ids.indexOf(org_impl_id));
	}
	@Override
	public String getOrgOrderName(String org_order_id) {
		return org_order_names.get(org_order_ids.indexOf(org_order_id));
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

}
