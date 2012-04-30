package com.mresearch.databank.client.views;

import java.awt.ScrollPane;
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
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.mresearch.databank.client.event.ShowResearchDetailsEvent;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.presenters.UserResearchPerspectivePresenter;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.ResearchBundleDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO_Light;
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
//	VerticalPanel centralPanel;
	@UiField Tree tree,F_S_tree,F_V_tree;
	@UiField ScrollPanel centerChild;
	@UiField SplitLayoutPanel split_panel;
	//HLayout panel;
//	@UiField CheckBox weights_use,filters_use;
//	@UiField Button filters_details_btn,filters_add_btn,filters_delete_btn;
	SimpleResearchList simpleResearchListItem;
	RootConceptsList rootResearchConcepts, rootVarConcepts;
	private Long research_to_find =null;
	private ArrayList<SocioResearchDTO_Light> researchList;
	public UserResearchPerspectiveView(SimpleEventBus bus) {
		initWidget(uiBinder.createAndBindUi(this));
//		panel = new HLayout();
//		//panel.setWidth100();
//		//panel.setHeight100();
//		VLayout cPanel = new VLayout();
//		cPanel.setWidth("70%");
//		cPanel.setAlign(Alignment.LEFT);  
//		cPanel.setOverflow(Overflow.VISIBLE);
//		
//		
//		//cPanel.setShowResizeBar(true);  
//		centralPanel = new VerticalPanel();
//		cPanel.addMember(centralPanel);
//
//
//
//
//
//		tree = new Tree();
		//tree.setStyleName("research-catalog");
		//TreeItem db = new TreeItem("_Банк данных_");
		
		
		
		
		//split_panel.get
		
		split_panel.setWidgetMinSize(centerChild, 800);
		//split_panel.get
		simpleResearchListItem = new SimpleResearchList();
		tree.addItem(simpleResearchListItem);
		rootResearchConcepts = new RootConceptsList("socioresearch","Концепты каталогизации исследований");
		tree.addItem(rootResearchConcepts);
		
		rootVarConcepts = new RootConceptsList("sociovar","Концепты каталогизации переменных");
		tree.addItem(rootVarConcepts);
		
		F_S_tree.addItem(new RootFilterItemAdvanced(centerPanel,bus,"socioresearch","Фильтровать исследования"));
		F_V_tree.addItem(new RootFilterItemAdvanced(centerPanel,bus,"sociovar","Фильтровать переменные"));


		
		
		//tree.addItem(db);
//		VLayout vLayout = new VLayout();
//		vLayout.setAlign(Alignment.LEFT);  
//	    vLayout.setOverflow(Overflow.SCROLL);  
//	    vLayout.setWidth("30%");  
//	   // vLayout.setHeight100();
//	    vLayout.setShowResizeBar(true);  
//	    vLayout.setResizeBarSize(9);
//	    //vLayout.r
//	    //ScrollPanel scr = new ScrollPanel(tree);
//	    
//	    
//	    
//	    
//	    //scr.setHeight("100%");
//	    //scr.setWidth("100%");
//	    vLayout.addMember(tree);
//		//vLayout.getS
//		//vLayout.sendToBack();
//
//		panel.setWidth("100%");
//		panel.setHeight("100%");
//		panel.addMember(vLayout);
//		panel.addMember(cPanel);
//		panel.sendToBack();
//
//		
//		centerPanel.add(panel);
	}
	private void displayResearchList()
	{
		simpleResearchListItem.removeItems();
		for(SocioResearchDTO_Light dto:researchList)
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
		int b = 2;
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
	public HasClickHandlers getVarItem(int index)  {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	
	@Override
	public void setResearchListData(ArrayList<SocioResearchDTO_Light> data) {
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
	public void showResearchDetailes(final ResearchBundleDTO dto) {
		
		centerPanel.clear();
		UserResearchDescriptionView desc = new UserResearchDescriptionView(dto.getDto());
		UserResearchDetailedView view = new UserResearchDetailedView(dto.getDto(),dto.getMeta());
		UserResearchAdvancedFilesView files = new UserResearchAdvancedFilesView(dto.getDto().getID(),dto.getFiles_dto());
		UserResearchDetailedFrameView frame = new UserResearchDetailedFrameView(desc,view, files);
		centerPanel.add(frame);

//		new RPCCall<MetaUnitMultivaluedEntityDTO>() {
//
//			@Override
//			public void onFailure(Throwable arg0) {
//			}
//
//			@Override
//			public void onSuccess(MetaUnitMultivaluedEntityDTO res) {
//			}
//
//			@Override
//			protected void callService(
//					AsyncCallback<MetaUnitMultivaluedEntityDTO> cb) {
//				UserSocioResearchService.Util.getInstance().getDatabankStructure("socioresearch", cb);
//			}
//		}.retry(2);
		}
	
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
	
}
