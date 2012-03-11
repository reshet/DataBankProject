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
import com.mresearch.databank.shared.MetaUnitDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public abstract class FilterRealDiapasonView extends Composite
  implements IFilterProvider
{
  private static FilterRealDiapasonViewUiBinder uiBinder = (FilterRealDiapasonViewUiBinder)GWT.create(FilterRealDiapasonViewUiBinder.class);

  @UiField
  Label diapason_name;

  @UiField
  TextBox from_value;

  @UiField
  TextBox to_value;

  protected String base_name;
  protected MetaUnitDTO dto;
  public FilterRealDiapasonView(String diapason_name,String b_name,MetaUnitDTO dt) { 
	initWidget((Widget)uiBinder.createAndBindUi(this));
    this.diapason_name.setText(diapason_name);
    this.dto = dt;
    this.base_name = b_name;
    this.from_value.addKeyPressHandler(new KeyPressHandler() {
      private char[] allowedSet = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.' };

      public void onKeyPress(KeyPressEvent event) {
        char code = event.getCharCode();
        boolean allowed = false;
        for (int i = 0; i < this.allowedSet.length; i++)
        {
          if (this.allowedSet[i] != code)
            continue;
          allowed = true;
          break;
        }

        String s = FilterRealDiapasonView.this.from_value.getText();
        if (!allowed) FilterRealDiapasonView.this.from_value.setText(s.substring(0, s.length() - 1)); 
      }
    }); }

  public String getFromValue()
  {
    return this.from_value.getText();
  }

  public String getToValue() {
    return this.to_value.getText();
  }

  static abstract interface FilterRealDiapasonViewUiBinder extends UiBinder<Widget, FilterRealDiapasonView>
  {
  }
}