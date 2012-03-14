package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.event.CreateConceptEvent;
import com.mresearch.databank.shared.CatalogConceptDTO;

public class AddConceptPopupView extends Composite {

	private static AddConceptPopupViewUiBinder uiBinder = GWT
			.create(AddConceptPopupViewUiBinder.class);

	interface AddConceptPopupViewUiBinder extends UiBinder<Widget, AddConceptPopupView> {
	}

	private final SimpleEventBus evBus;
	private boolean isRootConcept = false;
	public AddConceptPopupView(SimpleEventBus eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		evBus = eventBus;
		conceptName.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
				{
					doSubmit();
				}
			}
		});
	//	this.c_type = c_type;
	//	this.parentConceptID = parentConceptID;
	//	thisPopupPanel.setSize("500px", "300px");
	}
	@UiField TextBox conceptName;
	@UiField Button submitBtn;
	private long parentConceptID;
	public TextBox getConceptName() {
		return conceptName;
	}
	public void setConceptName(TextBox conceptName) {
		this.conceptName = conceptName;
	}
	public long getParentConceptID() {
		return parentConceptID;
	}
	public void setParentConceptID(long parentConceptID) {
		this.parentConceptID = parentConceptID;
	}
	public String getC_type() {
		return c_type;
	}
	public void setC_type(String c_type) {
		this.c_type = c_type;
	}
	private String c_type;
	//@UiField PopupPanel thisPopupPanel;
	CatalogConceptDTO getConceptDTO()
	{
		CatalogConceptDTO dto = new CatalogConceptDTO();
		dto.setC_type(c_type);
		dto.setSuper_concept_ID(parentConceptID);
		dto.setName(conceptName.getText());
		dto.setRoot(isRootConcept);
		return dto;
	}
	@UiHandler(value="submitBtn") void submitBtnClick(ClickEvent e)
	{
		doSubmit();
	}
	private void doSubmit()
	{
		evBus.fireEvent(new CreateConceptEvent(getConceptDTO()));
		this.getParent().setVisible(false);
	}
//	PopupPanel getPopupPanel()
//	{
//		//return thisPopupPanel;
//	}
	public boolean isRootConcept() {
		return isRootConcept;
	}
	public void setRootConcept(boolean isRootConcept) {
		this.isRootConcept = isRootConcept;
	}
}
