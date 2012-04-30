package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserResearchDetailedFrameView extends Composite {

	private static UserResearchDetailedFrameViewUiBinder uiBinder = GWT
			.create(UserResearchDetailedFrameViewUiBinder.class);

	interface UserResearchDetailedFrameViewUiBinder extends
			UiBinder<Widget, UserResearchDetailedFrameView> {
	}
	@UiField VerticalPanel filesPanel,viewPanel,descPanel;

	public UserResearchDetailedFrameView(UserResearchDescriptionView desc,UserResearchDetailedView view,UserResearchAdvancedFilesView files) {
		initWidget(uiBinder.createAndBindUi(this));
		descPanel.add(desc);
		filesPanel.add(files);
		viewPanel.add(view);
	}

}
