package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.MetaUnitDTO;

public abstract class FilterStringContainsView extends Composite
implements IFilterProvider
{
private static FilterStringContainsViewUiBinder uiBinder = (FilterStringContainsViewUiBinder)GWT.create(FilterStringContainsViewUiBinder.class);

@UiField
Label name;

@UiField
TextBox value;
protected String base_name;
protected MetaUnitDTO dto;
public FilterStringContainsView(String name,String b_name,MetaUnitDTO dt) { initWidget((Widget)uiBinder.createAndBindUi(this));
  this.name.setText(name); 
  this.dto = dt;
  this.base_name = b_name;
}

public String getValue()
{
  return this.value.getText();
}

static abstract interface FilterStringContainsViewUiBinder extends UiBinder<Widget, FilterStringContainsView>
{
}
}
