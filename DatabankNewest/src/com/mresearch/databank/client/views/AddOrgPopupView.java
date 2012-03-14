package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.OrgDTO;

public class AddOrgPopupView extends Composite {

	private static AddOrgPopupViewUiBinder uiBinder = GWT
			.create(AddOrgPopupViewUiBinder.class);

	interface AddOrgPopupViewUiBinder extends UiBinder<Widget, AddOrgPopupView> {
	}

	public AddOrgPopupView() {
		initWidget(uiBinder.createAndBindUi(this));
	//	thisPopupPanel.setSize("500px", "300px");
	}
	@UiField TextBox orgName,orgAdress,orgTel;
	@UiField Button submitBtn,cancelBtn;
	//@UiField PopupPanel thisPopupPanel;
	OrgDTO getOrgDTO()
	{
		OrgDTO dto = new OrgDTO(orgName.getText(), orgAdress.getText(), orgTel.getText());
		return dto;
	}
	HasClickHandlers getAddBtn()
	{
		return submitBtn;
	}
	HasClickHandlers getCancelBtn()
	{
		return cancelBtn;
	}
//	PopupPanel getPopupPanel()
//	{
//		//return thisPopupPanel;
//	}
}
