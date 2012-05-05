package com.mresearch.databank.client.views;

import java.util.ArrayList;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.plaf.basic.BasicTreeUI.TreeSelectionHandler;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.mresearch.databank.client.event.ShowResearchDetailsEvent;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.presenters.UserLawPerspectivePresenter;
import com.mresearch.databank.client.presenters.UserResearchPerspectivePresenter;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.ZaconDTO;

public class UserLawPerspectiveView extends Composite implements UserLawPerspectivePresenter.Display{

	private static UserResearchPerspectiveViewUiBinder uiBinder = GWT
			.create(UserResearchPerspectiveViewUiBinder.class);

	interface UserResearchPerspectiveViewUiBinder extends
			UiBinder<Widget, UserLawPerspectiveView> {
	}
	@UiField VerticalPanel centerPanel;
	@UiField Tree tree;
	SimpleZaconList simpleZaconListItem;
	RootConceptsList rootDataLawConcepts;
	private Long zacon_to_find =null;
	private ArrayList<ZaconDTO> researchList;
	public UserLawPerspectiveView(SimpleEventBus bus) {
		initWidget(uiBinder.createAndBindUi(this));
		//tree = new Tree();
		//tree.setStyleName("research-catalog");
		//TreeItem db = new TreeItem("_Банк законодательства_");
		simpleZaconListItem = new SimpleZaconList();
		tree.addItem(simpleZaconListItem);
		rootDataLawConcepts = new RootConceptsList("law", "Рубрикатор");
		tree.addItem(rootDataLawConcepts);
		tree.addItem(new RootFilterItemAdvanced(centerPanel,bus,"law","Фильтр"));

		//tree.addItem(db);
		
		
	
		
		
		
		
		
		//db.setState(true);
		//treePanel.add(tree);
	}
	private void displayZaconList()
	{
		simpleZaconListItem.removeItems();
		for(ZaconDTO dto:researchList)
		{
			ZaconDescItem research_node = new ZaconDescItem(dto);
			//research_node.addItem(new ResearchVarList(dto));
			//for(String )
			simpleZaconListItem.addItem(research_node);
			if (zacon_to_find!= null && research_node.getContents_id()==zacon_to_find)
			{
				research_node.setSelected(true);
				tree.setSelectedItem(research_node);
				//tree.fireEvent();
				zacon_to_find = null;
			}
		}
	}
	@Override
	public HasMouseDownHandlers getTree() {
		return tree;
	}
	
	
	@Override
	public void setZaconListData(ArrayList<ZaconDTO> data) {
		researchList = data;
		displayZaconList();
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
	public void showZaconDetailes(final ZaconDTO dto,final String path) {
		centerPanel.clear();
		
		new RPCCall<MetaUnitMultivaluedEntityDTO>() {

			@Override
			public void onFailure(Throwable arg0) {
			}

			@Override
			public void onSuccess(MetaUnitMultivaluedEntityDTO res) {
				ZaconDetailedView view = new ZaconDetailedView(dto,res,path);
				centerPanel.add(view);
			}

			@Override
			protected void callService(
					AsyncCallback<MetaUnitMultivaluedEntityDTO> cb) {
				UserSocioResearchService.Util.getInstance().getDatabankStructure("law",cb);
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
		return centerPanel;
	}
	@Override
	public void findInZaconList(Long id) {
		simpleZaconListItem.setState(true);
		zacon_to_find = id;
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
	public void showZaconIndex(final ArrayList<ZaconDTO> dtos, final String path) {
		centerPanel.clear();
		
		new RPCCall<MetaUnitMultivaluedEntityDTO>() {

			@Override
			public void onFailure(Throwable arg0) {
			}

			@Override
			public void onSuccess(MetaUnitMultivaluedEntityDTO res) {
				centerPanel.add(new HTML("<p class=\"breadcrumbs\"><a href=\"#\" class=\"dark-red\">"+path+"</a></p><div class=\"spacer50\"></div>"));
				for(ZaconDTO dt:dtos)
				{
					ZaconIndexedView view = new ZaconIndexedView(dt,res);
					centerPanel.add(view);
				}
			}

			@Override
			protected void callService(
					AsyncCallback<MetaUnitMultivaluedEntityDTO> cb) {
				UserSocioResearchService.Util.getInstance().getDatabankStructure("law",cb);
			}
		}.retry(2);
	}
	
}
