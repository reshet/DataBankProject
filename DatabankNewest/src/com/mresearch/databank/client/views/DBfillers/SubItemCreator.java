package com.mresearch.databank.client.views.DBfillers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchService.Util;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.shared.MetaUnitMultivaluedDTO;
import java.util.HashMap;

public class SubItemCreator extends Composite
{
  private static SubItemCreatorUiBinder uiBinder = (SubItemCreatorUiBinder)GWT.create(SubItemCreatorUiBinder.class);

  private AdminSocioResearchServiceAsync service = AdminSocioResearchService.Util.getInstance();
  private MultiValuedField field;
  private PopupPanel par;
  private MultiValuedEntity entity;

  @UiField
  VerticalPanel host;

  @UiField
  TextBox name_field;
  private long parent_item_id;

  public SubItemCreator()
  {
    initWidget((Widget)uiBinder.createAndBindUi(this));
  }

  public SubItemCreator(long par_item_id, MultiValuedField field, MultiValuedEntity entity, PopupPanel parent)
  {
    initWidget((Widget)uiBinder.createAndBindUi(this));
    this.field = field;
    this.par = parent;
    this.entity = entity;
    this.parent_item_id = par_item_id;
    initFields();
  }

  private void initFields() {
    this.host.add(this.field);
  }
  @UiHandler({"doAdd"})
  public void doAdd(ClickEvent ev) {
    String value = this.name_field.getText();
    HashMap map = this.field.returnCollectedMap();
    addItemToDB(this.field.dto.getId(), value, map);
  }

  private void addItemToDB(long id_entity, final String value, final HashMap<String, String> map)
  {
    new RPCCall<Void>()
    {
      public void onFailure(Throwable caught)
      {
        Window.alert("Error on adding Entity Item!:" + caught.getMessage());
      }

      public void onSuccess(Void result)
      {
        Window.alert("Экземпляр успешно добавлен!");
        SubItemCreator.this.entity.refreshMembersList();
        SubItemCreator.this.par.hide();
      }

      protected void callService(AsyncCallback<Void> cb)
      {
        SubItemCreator.this.service.addSubEntityItem(Long.valueOf(SubItemCreator.this.parent_item_id), value, map, cb);
      }
    }
    .retry(2);
  }
  @UiHandler({"doCancel"})
  public void doCancel(ClickEvent ev) {
    this.par.hide();
  }

  static abstract interface SubItemCreatorUiBinder extends UiBinder<Widget, SubItemCreator>
  {
  }
}