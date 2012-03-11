package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.SearchResultDTO;

public class SearchResultView extends Composite {

	private static SearchResultViewUiBinder uiBinder = GWT
			.create(SearchResultViewUiBinder.class);

	interface SearchResultViewUiBinder extends
			UiBinder<Widget, SearchResultView> {
	}

	@UiField Hyperlink header;
	@UiField Label text;
	private String id;
	private String type;
	@SuppressWarnings("deprecation")
	public SearchResultView(SearchResultDTO dto) {
		initWidget(uiBinder.createAndBindUi(this));
		header.setText(dto.getHeading());
		text.setText(dto.getText());
		this.id = dto.getId();
		this.type = dto.getType();
		header.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (type.equals("SocioResearch"))
				{
					History.newItem("user-research@showResearch="+id);
				}
				//Window.alert("goes to search entity perspective");
			}
		});
	}

}
