package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.core.java.util.Collections;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.mresearch.databank.client.presenters.UserResearchPerspectivePresenter;
import com.mresearch.databank.client.presenters.UserSearchPerspectivePresenter;
import com.mresearch.databank.shared.SearchResultDTO;
import com.mresearch.databank.shared.SearchTaskLawDTO;
import com.mresearch.databank.shared.SearchTaskResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;

public class UserSearchPerspectiveView extends Composite implements UserSearchPerspectivePresenter.Display{

	private static UserResearchPerspectiveViewUiBinder uiBinder = GWT
			.create(UserResearchPerspectiveViewUiBinder.class);

	interface UserResearchPerspectiveViewUiBinder extends
			UiBinder<Widget, UserSearchPerspectiveView> {
	}
	
	
	@UiField VerticalPanel centerPanel,root_panel;
	@UiField TextBox contains_one_of,contains_exact,contains_exact_too,contains_or,contains_none_of,not_contains_exact;
	@UiField Button search;
	//@UiField FlexTable res_table;
	//@UiField Label queryStr;
	SimpleResearchList simpleResearchListItem;
	@UiField CheckBox all,socioresearch,sociovars,laws,publications,consults;
	//@UiField ImprovedSearchView impr_search_view;
	private ArrayList<SearchResultDTO> resultsList;
	public UserSearchPerspectiveView() {
		initWidget(uiBinder.createAndBindUi(this));
		//search.addClickHandler(handler)l
	}
	private void displaySearchResults()
	{
//		res_table.clear();
//		int i = 0;
//		for(SearchResultDTO dto:resultsList)
//		{
//			res_table.setWidget(i, 0, new SearchResultView(dto));
//			i++;
//		}
	}
	@Override
	public void setSearchResultsData(ArrayList<SearchResultDTO> data) {
		resultsList = data;
		displaySearchResults();
	}
	@Override
	public VerticalPanel getCenterPanel() {
		// TODO Auto-generated method stub
		return centerPanel;
	}
	@Override
	public void setQueryStr(String str) {
		//queryStr.setText(str);
		contains_one_of.setText(str);
	}
	@Override
	public void setSearchTaskResearchDTO(SearchTaskResearchDTO dto) {
		//impr_search_view.setSearchTaskResearch(dto);
	}
	@Override
	public void setResearchTabVisible() {
		//impr_search_view.setResearchTabVisible();
	}
	@Override
	public void setGeneralTabVisible() {
		//impr_search_view.setGeneralTabVisible();
	}
	@Override
	public void setLawTabVisible() {
		//impr_search_view.setLawTabVisible();
	}
	@Override
	public void setGeneralQueryText(String query) {
		//impr_search_view.setQueryText(query);
	}
	@Override
	public void setSearchTaskLawDTO(SearchTaskLawDTO dto) {
		//impr_search_view.setSearchTaskLaw(dto);
	}
	public HasClickHandlers getSearchButton() {
		return search;
	}
	@Override
	public String getContainsOneOf() {
		return contains_one_of.getText();
	}
	@Override
	public String getContainsExact() {
		return contains_exact.getText();
	}
	@Override
	public String getContainsExactToo() {
		return contains_exact_too.getText();
	}
	@Override
	public String getContainsOr() {
		return contains_or.getText();
	}
	@Override
	public String getContainsNoneOf() {
		return contains_none_of.getText();
	}
	@Override
	public String getNotContainsExact() {
		return not_contains_exact.getText();
	}
	@Override
	public VerticalPanel asRoot() {
		// TODO Auto-generated method stub
		return root_panel;
	}
	
	
	
	
	
	
	@Override
	public String[] getTypesToSearch() {
		ArrayList<String> types = new ArrayList<String>();
		if(all.getValue()) return new String[]{"research","sociovar"
												,"law","consultation","publication"
												};
		
		if(socioresearch.getValue())types.add("research");
		if(sociovars.getValue())types.add("sociovar");
		if(laws.getValue())types.add("law");
		if(publications.getValue())types.add("publication");
		if(consults.getValue())types.add("consultation");
		String [] arr = new String[types.size()];
		types.toArray(arr);

		return arr;
	}
}
