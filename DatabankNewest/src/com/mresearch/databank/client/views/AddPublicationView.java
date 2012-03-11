package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.DatabankApp;

public class AddPublicationView extends Composite {

	private static AddPublicationViewUiBinder uiBinder = GWT
			.create(AddPublicationViewUiBinder.class);

	interface AddPublicationViewUiBinder extends UiBinder<Widget,AddPublicationView> {
	}
	@UiField TextBox name_box,doi_box,url_box;
	private AdminResearchEditView view;
	private PopupPanel parent;
	public AddPublicationView(AdminResearchEditView view,PopupPanel parent) {
		initWidget(uiBinder.createAndBindUi(this));
		//this.main = main;
		this.view = view;
		this.parent = parent;
	}
	@UiHandler(value="addBtn")
	public void doLogin(ClickEvent e)
	{	
		parent.hide();
		view.addPublication(name_box.getText(), doi_box.getText(),url_box.getText());
	//	main.login(login_box.getText(), pswd_box.getText());
	}
}
