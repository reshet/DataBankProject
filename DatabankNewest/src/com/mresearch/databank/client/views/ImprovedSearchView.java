package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.SearchTaskLawDTO;
import com.mresearch.databank.shared.SearchTaskResearchDTO;

import org.zenika.widget.client.datePicker.DatePicker;

public class ImprovedSearchView extends Composite {

	private static ImprovedSearchViewUiBinder uiBinder = GWT
			.create(ImprovedSearchViewUiBinder.class);

	interface ImprovedSearchViewUiBinder extends
			UiBinder<Widget, ImprovedSearchView> {
	}
	@UiField TextBox text_have;
	@UiField TextBox name_have,l_name_have;
	@UiField DatePicker date_start_from,date_start_to,date_end_from,date_end_to;
	@UiField DatePicker l_date_from,l_date_to,date_accept_from,date_accept_to,date_decline_from,date_decline_to;
	
	@UiField TabLayoutPanel tabulator;
	public ImprovedSearchView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public SearchTaskResearchDTO getSearchTaskResearch()
	{
		SearchTaskResearchDTO dto = new SearchTaskResearchDTO();
		dto.setHeading_have(name_have.getText());
		dto.setStart_date_from(date_start_from.getSelectedDate());
		dto.setStart_date_to(date_start_to.getSelectedDate());
		dto.setEnd_date_from(date_end_from.getSelectedDate());
		dto.setEnd_date_to(date_end_to.getSelectedDate());
		return dto;
	}
	public SearchTaskLawDTO getSearchTaskLaw()
	{
		SearchTaskLawDTO dto = new SearchTaskLawDTO();
		dto.setHeading_have(l_name_have.getText());
		//dto.setAbstract_have();
		dto.setDate_from(l_date_from.getSelectedDate());
		dto.setDate_to(l_date_to.getSelectedDate());
		dto.setAccept_date_from(date_accept_from.getSelectedDate());
		dto.setAccept_date_to(date_accept_to.getSelectedDate());
		dto.setDecline_date_from(date_decline_from.getSelectedDate());
		dto.setDecline_date_to(date_decline_to.getSelectedDate());
		
		return dto;
	}
	public void setSearchTaskResearch(SearchTaskResearchDTO dto)
	{
		if(dto.getHeading_have() != null) name_have.setText(dto.getHeading_have());
		if(dto.getStart_date_from() != null) date_start_from.setSelectedDate(dto.getStart_date_from());
		if(dto.getStart_date_to() != null) date_start_to.setSelectedDate(dto.getStart_date_to());
		if(dto.getEnd_date_from() != null) date_end_from.setSelectedDate(dto.getEnd_date_from());
		if(dto.getEnd_date_to() != null) date_end_to.setSelectedDate(dto.getEnd_date_to());	
	}
	public void setSearchTaskLaw(SearchTaskLawDTO dto)
	{
		if(dto.getHeading_have() != null) l_name_have.setText(dto.getHeading_have());
		//if(dto.getAbstract_have() != null) abstract.setText(dto.getHeading_have());
		
		if(dto.getDate_from() != null) l_date_from.setSelectedDate(dto.getDate_from());
		if(dto.getDate_to() != null) l_date_to.setSelectedDate(dto.getDate_to());
		if(dto.getAccept_date_from() != null) date_accept_from.setSelectedDate(dto.getAccept_date_from());
		if(dto.getAccept_date_to() != null) date_accept_to.setSelectedDate(dto.getAccept_date_to());
		if(dto.getDecline_date_from() != null) date_decline_from.setSelectedDate(dto.getDecline_date_from());
		if(dto.getDecline_date_to() != null) date_decline_to.setSelectedDate(dto.getDecline_date_to());	
	}
	@UiHandler(value="searchResearchBtn")
	public void doSearchResearches(ClickEvent e)
	{
		String ser = getSearchTaskResearch().serialize();
		History.newItem("search-results@"+ser+"&advancedSearchResearch=true");
	}
	@UiHandler(value="searchLawsBtn")
	public void doSearchLaws(ClickEvent e)
	{
		String ser = getSearchTaskLaw().serialize();
		History.newItem("search-results@"+ser+"&advancedSearchLaw=true");
	}
	@UiHandler(value="searchBtn")
	public void doSearchAll(ClickEvent e)
	{
		//String ser = getSearchTaskResearch().serialize();
		History.newItem("search-results@query="+text_have.getText());
	}
	public void setResearchTabVisible()
	{
		//tabulator.setVisible(tabulator., true);
		tabulator.selectTab(1);
	}
	public void setLawTabVisible()
	{
		//tabulator.setVisible(tabulator., true);
		tabulator.selectTab(2);
	}
	public void setGeneralTabVisible()
	{
		//tabulator.setVisible(tabulator., true);
		tabulator.selectTab(0);
	}
	public void setQueryText(String query)
	{
		text_have.setText(query);
	}

}
