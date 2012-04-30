package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.mresearch.databank.client.views.DBfillers.MultiValuedField;
import com.mresearch.databank.client.views.DBviewers.MultiValuedFieldViewer;
import com.mresearch.databank.shared.MetaUnitEntityItemDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.SearchTaskResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO;

public class UserResearchDescriptionView extends Composite {

	private static UserResearchDetailedViewUiBinder uiBinder = GWT
			.create(UserResearchDetailedViewUiBinder.class);

	interface UserResearchDetailedViewUiBinder extends
			UiBinder<Widget, UserResearchDescriptionView> {
	}

	public UserResearchDescriptionView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	@UiField HTML html;
	public UserResearchDescriptionView(SocioResearchDTO dto)
	{
		initWidget(uiBinder.createAndBindUi(this));

		html.setHTML(dto.getDesctiption()==null?"":dto.getDesctiption());
	}
}

