package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SelectableElementView extends Composite {

	private static PickableElementViewUiBinder uiBinder = GWT
			.create(PickableElementViewUiBinder.class);

	interface PickableElementViewUiBinder extends
			UiBinder<Widget, SelectableElementView> {
	}
	@UiField CheckBox c_box;
	private Long id;
	public SelectableElementView(Long id,String name) {
		initWidget(uiBinder.createAndBindUi(this));
		this.id = id;
		c_box.setText(name);
		c_box.setChecked(false);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
