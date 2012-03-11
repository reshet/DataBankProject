package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.presenters.AdminResearchDetailedPresenter;

public class AdminResearchDetailedView extends Composite implements AdminResearchDetailedPresenter.Display {

	private static AdminResearchDetailedViewUiBinder uiBinder = GWT
			.create(AdminResearchDetailedViewUiBinder.class);

	interface AdminResearchDetailedViewUiBinder extends
			UiBinder<Widget, AdminResearchDetailedView> {
	}
	@UiField VerticalPanel viewPanel,editPanel,groupEditPanel,filesEditPanel;
	public AdminResearchDetailedView(UserResearchDetailedView view) {
		initWidget(uiBinder.createAndBindUi(this));
		viewPanel.add(view);
	}
	public VerticalPanel getViewPanel()
	{
		return viewPanel;
	}
	public VerticalPanel getEditPanel()
	{
		return editPanel;
	}
	@Override
	public VerticalPanel getGroupEditPanel() {
		return groupEditPanel;
	}
	@Override
	public VerticalPanel getFilesPanel() {
		return filesEditPanel;
	}

}
