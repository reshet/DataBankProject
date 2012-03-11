package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PickableElementView extends Composite {

	private static PickableElementViewUiBinder uiBinder = GWT
			.create(PickableElementViewUiBinder.class);

	interface PickableElementViewUiBinder extends
			UiBinder<Widget, PickableElementView> {
	}
	@UiField CheckBox c_box;
	public PickableElementView(String name) {
		initWidget(uiBinder.createAndBindUi(this));
		c_box.setText(name);
	}
	public boolean isChecked()
	{
		return c_box.isChecked();
	}
	public void setChecked(boolean check_state)
	{
		c_box.setChecked(check_state);
	}

}
