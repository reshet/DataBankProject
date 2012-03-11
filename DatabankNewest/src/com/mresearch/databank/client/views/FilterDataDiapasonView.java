package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.Set;

import org.zenika.widget.client.datePicker.DatePicker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.FilterBaseDTO;
import com.mresearch.databank.shared.FilterDiapasonDTO;
import com.mresearch.databank.shared.SearchTaskResearchDTO;

public abstract class FilterDataDiapasonView extends Composite implements IFilterProvider{

	private static FilterRealDiapasonViewUiBinder uiBinder = GWT
			.create(FilterRealDiapasonViewUiBinder.class);

	interface FilterRealDiapasonViewUiBinder extends
			UiBinder<Widget, FilterDataDiapasonView> {
	}
	@UiField Label diapason_name;
	@UiField DatePicker from_value,to_value;
	public FilterDataDiapasonView(String diapason_name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.diapason_name.setText(diapason_name);
//		from_value.addKeyDownHandler(new KeyDownHandler() {
//			@Override
//			public void onKeyDown(KeyDownEvent event) {
//				int code = event.getNativeKeyCode();
//				event.
//				KeyCodes
//			}
//		});
//		
		
	}
	public String getFromValue()
	{
		return SearchTaskResearchDTO.date_serialize(from_value.getSelectedDate());		
	}
	public String getToValue()
	{
		return SearchTaskResearchDTO.date_serialize(to_value.getSelectedDate());		
	}
	
//	public double getFromValue()
//	{
//		double d = 0;
//		d = Double.parseDouble(from_value.getText());
//		return d;
//	}
//
//	public double getToValue()
//	{
//		double d = 0;
//		d = Double.parseDouble(to_value.getText());
//		return d;
//	}
}
