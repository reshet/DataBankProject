package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.mresearch.databank.client.presenters.AdminResearchDetailedPresenter;
import com.mresearch.databank.client.presenters.AdminStartupPerspectivePresenter;
import com.mresearch.databank.shared.PublicationDTO_Light;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO_Light;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Light;
import com.mresearch.databank.shared.ZaconDTO_Light;

public class AdminStartupPerspectiveView extends Composite implements AdminStartupPerspectivePresenter.Display{

	private static AdminResearchPerspectiveViewUiBinder uiBinder = GWT
			.create(AdminResearchPerspectiveViewUiBinder.class);

	interface AdminResearchPerspectiveViewUiBinder extends
			UiBinder<Widget, AdminStartupPerspectiveView> {
	}
	@UiField VerticalPanel pubsPanel,researchesPanel,lawsPanel;
	@UiField Button save;
	@UiField TextBox pubsShowLast;
	private ArrayList<SocioResearchDTO_Light> res_all,res_sel;
	private ArrayList<ZaconDTO_Light> laws_all,laws_sel;
	private FlexTable res_table = new FlexTable(),laws_table = new FlexTable();
	SimpleEventBus bus;
	public AdminStartupPerspectiveView(SimpleEventBus bus) {
		initWidget(uiBinder.createAndBindUi(this));
		this.bus = bus;
		researchesPanel.add(res_table);
		lawsPanel.add(laws_table);
	}
	private void displayLaws(){
		laws_table.clear();
		int i = 0;
		ArrayList<Long> laws_ids = new ArrayList<Long>();
		for(ZaconDTO_Light dto:laws_sel)laws_ids.add(dto.getId());
		for(ZaconDTO_Light dto:laws_all)
		{
			SelectableElementView sel = new SelectableElementView(dto.getId(), dto.getHeader());
			if(laws_ids.contains(dto.getId()))sel.setChecked(true);
			laws_table.setWidget(i++, 0, sel );
		}
	}
	
	
	private void displayRes(){
		res_table.clear();
		int i = 0;
		ArrayList<Long> res_ids = new ArrayList<Long>();
		for(SocioResearchDTO_Light dto:res_sel)res_ids.add(dto.getId());
		for(SocioResearchDTO_Light dto:res_all)
		{
			SelectableElementView sel = new SelectableElementView(dto.getId(), dto.getName());
			if(res_ids.contains(dto.getId()))sel.setChecked(true);
			res_table.setWidget(i++, 0, sel);
		}
	}
	
	private void collectLaws(){
		int i = 0;
		for(ZaconDTO_Light dto:laws_all)
		{
			SelectableElementView sel = (SelectableElementView)laws_table.getWidget(i++,0);
			if(!sel.isChecked())
			{
				if(laws_sel.contains(dto))laws_sel.remove(dto);
			}else
			{
				laws_sel.add(dto);
			}
		}
	}
	private void  collectRes(){
		int i = 0;
		for(SocioResearchDTO_Light dto:res_all)
		{
			SelectableElementView sel = (SelectableElementView)res_table.getWidget(i++,0);
			if(!sel.isChecked())
			{
				if(res_sel.contains(dto))res_sel.remove(dto);
			}else
			{
				res_sel.add(dto);
			}
		}
	}
	@Override
	public void setLawsAll(ArrayList<ZaconDTO_Light> arr) {
		laws_all = arr;
	}
	@Override
	public void setResearchesAll(ArrayList<SocioResearchDTO_Light> arr) {
		res_all = arr;
	}
	
	@Override
	public void setLawsSel(ArrayList<ZaconDTO_Light> arr) {
		laws_sel = arr;
		displayLaws();
	}
	@Override
	public void setResearchesSel(ArrayList<SocioResearchDTO_Light> arr) {
		res_sel = arr;
		displayRes();
	}
	@Override
	public void setPubsSel(Long howmuch) {
		pubsShowLast.setText(String.valueOf(howmuch));
	}
	@Override
	public ArrayList<ZaconDTO_Light> getLawsSel() {
		collectLaws();
		return laws_sel;
	}
	@Override
	public ArrayList<SocioResearchDTO_Light> getResearchesSel() {
		collectRes();
		return res_sel;
	}
	@Override
	public Long getPubsSel() {
		return Long.parseLong(pubsShowLast.getText());
	}
	@Override
	public HasClickHandlers getSaveBtn() {
		return save;
	}

}
