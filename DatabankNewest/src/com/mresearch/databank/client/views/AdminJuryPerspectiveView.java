package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.mresearch.databank.client.presenters.AdminJuryPerspectivePresenter;
import com.mresearch.databank.client.presenters.AdminResearchDetailedPresenter;
import com.mresearch.databank.client.presenters.AdminResearchPerspectivePresenter;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;

public class AdminJuryPerspectiveView extends Composite implements AdminJuryPerspectivePresenter.Display{

	private static AdminResearchPerspectiveViewUiBinder uiBinder = GWT
			.create(AdminResearchPerspectiveViewUiBinder.class);

	interface AdminResearchPerspectiveViewUiBinder extends
			UiBinder<Widget, AdminJuryPerspectiveView> {
	}
	@UiField VerticalPanel centerPanel;
	@UiField Tree tree;
	@UiField 
	Button createBtn,deleteBtn,addBtn;
	
	SimpleConsultationList simpleConsListItem;
	RootConceptsList rootDataLawConcepts;
	SimpleEventBus bus;
	private PopupPanel popup = new PopupPanel(true);
	private AddConceptPopupView add_concept_popup;
	private ArrayList<SocioResearchDTO> researchList;
	private boolean rootConceptUpdateMode = false;
	public AdminJuryPerspectiveView(SimpleEventBus bus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.bus = bus;
		add_concept_popup = new AddConceptPopupView(this.bus);
		//tree = new Tree();
		//tree.setStyleName("research-catalog");
		TreeItem db = new TreeItem("_Банк вопросов_");
		simpleConsListItem = new SimpleConsultationList();
		rootDataLawConcepts = new RootConceptsList("consultation","Рубрики");
		db.addItem(simpleConsListItem);
		db.addItem(rootDataLawConcepts);
		tree.addItem(db);
		//treePanel.add(tree);
	}
	private void displayArticleList()
	{
//		simpleZaconListItem.removeItems();
//		for(SocioResearchDTO dto:researchList)
//		{
//			ResearchDescItem research_node = new ResearchDescItem(dto);
//			research_node.addItem(new ResearchVarList(dto));
//			//for(String )
//			simpleZaconListItem.addItem(research_node);
//		}
	}
	
	@Override
	public HasMouseDownHandlers getTree() {
		return tree;
	}
	@Override
	public HasClickHandlers getResearchItem(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HasClickHandlers getVarItem(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setResearchListData(ArrayList<SocioResearchDTO> data) {
		researchList = data;
		displayArticleList();
	}
	@Override
	public void showLoadingLabel() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public TreeItem getSelectedItem() {
		// TODO Auto-generated method stub
		return tree.getSelectedItem();
	}
//	@Override
//	public void showResearchDetailes(SocioResearchDTO dto) {
//		//centerPanel.clear();
//		
//		//centerPanel.add(ad_view);
//	}
	@Override
	public HasOpenHandlers<TreeItem> getTreeForOpen() {
		return tree;
	}
	@Override
	public VerticalPanel getCenterPanel() {
		// TODO Auto-generated method stub
		return centerPanel;
	}
	@Override
	public HasClickHandlers getEditButton() {
		return createBtn;
	}
	@Override
	public HasClickHandlers getDeleteButton() {
		return deleteBtn;
	}
	@Override
	public HasEnabled getAddArticleBtn() {
		return addBtn;
	}
	@Override
	public HasClickHandlers getAddArticleBt() {
		return addBtn;
	}
	@Override
	public HasEnabled getCreateConceptBtn() {
		return createBtn;
	}
	@Override
	public HasEnabled getDeleteConceptBtn() {
		return deleteBtn;
	}
	@Override
	public HasClickHandlers getCreateConceptBt() {
		return createBtn;
	}
	@Override
	public void showCreateConceptPopup(int x, int y, String c_type) {
		Long parent_id = null;
		TreeItem item = tree.getSelectedItem();
		if (item instanceof ConceptItem<?>)
		{
			parent_id = ((ConceptItem<?>)item).getConcept_id();
		}
		add_concept_popup.setC_type(c_type);
		add_concept_popup.setRootConcept(rootConceptUpdateMode);
		add_concept_popup.setParentConceptID(parent_id);
		popup.add(add_concept_popup);
		popup.setPopupPosition(x,y);
		popup.setPopupPosition(400,400);
		popup.show();
	}
	
	@Override
	public void hideConceptPopup() {
		popup.hide();
		popup.clear();
	}
	@Override
	public void setRootConceptUpdateMode(boolean isRoot) {
		rootConceptUpdateMode = isRoot;
	}
	@Override
	public void updateRootConcepts() {
		rootDataLawConcepts.refreshContents();
	}

}
