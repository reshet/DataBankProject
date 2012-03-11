package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.Set;

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

public abstract class FilterRealDiapasonView extends Composite implements IFilterProvider{

	private static FilterRealDiapasonViewUiBinder uiBinder = GWT
			.create(FilterRealDiapasonViewUiBinder.class);

	interface FilterRealDiapasonViewUiBinder extends
			UiBinder<Widget, FilterRealDiapasonView> {
	}
	@UiField Label diapason_name;
	@UiField TextBox from_value,to_value;
	public FilterRealDiapasonView(String diapason_name) {
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
		from_value.addKeyPressHandler(new KeyPressHandler() {
			private char[] allowedSet = {'0','1','2','3','4','5','6','7','8','9','.'};
			@Override
			public void onKeyPress(KeyPressEvent event) {
				char code = event.getCharCode();
				boolean allowed = false;
				for(int i = 0;i < allowedSet.length;i++)
				{
					if(allowedSet[i] == code)
					{
						allowed = true;
						break;
					}
				}
				String s = from_value.getText();
				if (!allowed) from_value.setText(s.substring(0,s.length()-1));
			}
		});
	}
	public String getFromValue()
	{
		return from_value.getText();
	}
	public String getToValue()
	{
		return to_value.getText();
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
