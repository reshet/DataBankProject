package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class SearchResultsView extends Composite {

	private static SearchResultsViewUiBinder uiBinder = GWT
			.create(SearchResultsViewUiBinder.class);

	interface SearchResultsViewUiBinder extends
			UiBinder<Widget, SearchResultsView> {
	}
	@UiField FlexTable table;
	public SearchResultsView(ArrayList<String> results) {
		initWidget(uiBinder.createAndBindUi(this));
		table.clear();
		int i = 0;
		for(String res:results)
		{
			table.setWidget(i, 0, new CheckBox(res));
		}
	}

}
