package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.ArticleDTO;

public class ArticlesShortView extends Composite {

	private static ArticlesShortViewUiBinder uiBinder = GWT
			.create(ArticlesShortViewUiBinder.class);

	interface ArticlesShortViewUiBinder extends
			UiBinder<Widget, ArticlesShortView> {
	}

	@UiField VerticalPanel vertPanel;
	public ArticlesShortView(ArrayList<ArticleDTO> dtos) {
		initWidget(uiBinder.createAndBindUi(this));
		for(ArticleDTO dto:dtos)
		{
			vertPanel.add(new ArticleBarView(dto));
		}
	}

}
