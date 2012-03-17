package com.mresearch.databank.client.views;

import java.util.ArrayList;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.plaf.basic.BasicTreeUI.TreeSelectionHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.mresearch.databank.client.event.ShowResearchDetailsEvent;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.presenters.UserResearchPerspectivePresenter;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Light;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class UserResearchPerspectiveView extends Composite implements UserResearchPerspectivePresenter.Display{

	private static UserResearchPerspectiveViewUiBinder uiBinder = GWT
			.create(UserResearchPerspectiveViewUiBinder.class);

	interface UserResearchPerspectiveViewUiBinder extends
			UiBinder<Widget, UserResearchPerspectiveView> {
	}
	@UiField VerticalPanel centerPanel;
	VerticalPanel centralPanel;
	@UiField CheckBox weights_use,filters_use;
	@UiField Button filters_details_btn,filters_add_btn,filters_delete_btn;
	SimpleResearchList simpleResearchListItem;
	RootConceptsList rootResearchConcepts;
	Tree tree;
	HLayout panel;
	private Long research_to_find =null;
	private ArrayList<SocioResearchDTO> researchList;
	public UserResearchPerspectiveView() {
		initWidget(uiBinder.createAndBindUi(this));
		panel = new HLayout();
		//panel.setWidth100();
		//panel.setHeight100();
		VLayout cPanel = new VLayout();
		cPanel.setWidth("70%");
		cPanel.setAlign(Alignment.CENTER);  
		cPanel.setOverflow(Overflow.AUTO);  
		//cPanel.setShowResizeBar(true);  
		centralPanel = new VerticalPanel();
		cPanel.addMember(centralPanel);
		
		
		
		
		
		tree = new Tree();
		tree.setStyleName("research-catalog");
		TreeItem db = new TreeItem("_Банк данных_");
		simpleResearchListItem = new SimpleResearchList();
		db.addItem(simpleResearchListItem);
		rootResearchConcepts = new RootConceptsList("SocioResearch","Концепты каталогизации исследований");
		db.addItem(rootResearchConcepts);
		db.addItem(new RootFilterItemAdvanced(centralPanel));
		
		
		
		
		
		tree.addItem(db);
		db.setState(true);
		VLayout vLayout = new VLayout();
		vLayout.setAlign(Alignment.LEFT);  
	    vLayout.setOverflow(Overflow.AUTO);  
	    vLayout.setWidth("30%");  
	   // vLayout.setHeight100();
	    vLayout.setShowResizeBar(true);  
		vLayout.addMember(tree);
		vLayout.sendToBack();
		
		panel.setWidth100();
		panel.setHeight100();
		panel.addMember(vLayout);
		panel.addMember(cPanel);
		panel.sendToBack();
		
		centerPanel.add(panel);
	}
	private void displayResearchList()
	{
		simpleResearchListItem.removeItems();
		for(SocioResearchDTO dto:researchList)
		{
			ResearchDescItem research_node = new ResearchDescItem(dto);
			research_node.addItem(new ResearchVarList(dto));
			//for(String )
			simpleResearchListItem.addItem(research_node);
			if (research_to_find!= null && research_node.getContents_id() == research_to_find)
			{
				research_node.setSelected(true);
				tree.setSelectedItem(research_node);
				//tree.fireEvent();
				research_to_find = null;
			}
		}
	}
	@UiHandler(value="weights_use")
	public void onWeightsUseClick(ClickEvent c)
	{
		if(weights_use.getValue()) weights_use.setText("Взвешивание включено");
			else weights_use.setText("Взвешивание отключено");
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
		displayResearchList();
	}
	@Override
	public void setVarListData(TreeItem item, ArrayList<VarDTO_Light> data) {
		item.removeItems();
		for(VarDTO_Light dto:data)
		{
			
			VarDescItem var_node = new VarDescItem(dto);
			//var_node.addItem(dto.getLabel());
			item.addItem(var_node);
		}
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
	@Override
	public void showResearchDetailes(final SocioResearchDTO dto) {
		
		new RPCCall<MetaUnitMultivaluedEntityDTO>() {

			@Override
			public void onFailure(Throwable arg0) {
			}

			@Override
			public void onSuccess(MetaUnitMultivaluedEntityDTO res) {
				centralPanel.clear();
				UserResearchDetailedView view = new UserResearchDetailedView(dto,res);
				UserResearchAdvancedFilesView files = new UserResearchAdvancedFilesView(dto.getID());
				UserResearchDetailedFrameView frame = new UserResearchDetailedFrameView(view, files);
				centralPanel.add(frame);
			}

			@Override
			protected void callService(
					AsyncCallback<MetaUnitMultivaluedEntityDTO> cb) {
				AdminSocioResearchService.Util.getInstance().getDatabankStructure("socioresearch", cb);
			}
		}.retry(2);
		}
	
	@Override
	public HasOpenHandlers<TreeItem> getTreeForOpen() {
		return tree;
	}
	@Override
	public VerticalPanel getCenterPanel() {
		// TODO Auto-generated method stub
		return centralPanel;
	}
	@Override
	public void findInResearchList(Long id) {
		simpleResearchListItem.setState(true);
		research_to_find = id;
//		displayResearchList();
//		for(int i = 0; i < simpleResearchListItem.getChildCount();i++)
//		{
//			TreeItem it = simpleResearchListItem.getChild(i);
//			if (it instanceof ResearchDescItem)
//			{
//				ConceptContentsItem item = (ConceptContentsItem)simpleResearchListItem.getChild(i);
//				if (item.getContents_id().equals(id))
//				{
//					item.setSelected(true);
//					break;
//				}
//			}
//		}
	}
	@Override
	public HasSelectionHandlers<TreeItem> getTreeForSelection() {
		return tree;
	}
	@Override
	public HasClickHandlers getWeightsUse() {
		return weights_use;
	}
	@Override
	public HasClickHandlers getFiltersUse() {
		return filters_use;
	}
	@Override
	public HasClickHandlers getFiltersDetailesBtn() {
		return filters_details_btn;
	}
	@Override
	public Integer getWeightsUseState() {
		return weights_use.getValue()?1:0;
	}
	@Override
	public Integer getFiltersUseState() {
		return filters_use.getValue()?1:0;
	}
	@Override
	public HasClickHandlers getFiltersAddBtn() {
		// TODO Auto-generated method stub
		return filters_add_btn;
	}
	@Override
	public HasClickHandlers getFiltersDeleteBtn() {
		// TODO Auto-generated method stub
		return filters_delete_btn;
	}
}
