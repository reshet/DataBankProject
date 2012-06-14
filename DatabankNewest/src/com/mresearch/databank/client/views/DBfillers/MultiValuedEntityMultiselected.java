package com.mresearch.databank.client.views.DBfillers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.client.service.AdminSocioResearchService.Util;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.shared.JSON_Representation;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitEntityItemDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import java.util.ArrayList;
import java.util.HashMap;

public class MultiValuedEntityMultiselected extends Composite
  implements MetaUnitFiller, MetaUnitEntityItemRegistrator
{
  private static MultiValuedEntityMultiselectedUiBinder uiBinder = (MultiValuedEntityMultiselectedUiBinder)GWT.create(MultiValuedEntityMultiselectedUiBinder.class);

  private AdminSocioResearchServiceAsync service = AdminSocioResearchService.Util.getInstance();
	private UserSocioResearchServiceAsync userService = UserSocioResearchService.Util.getInstance();

  @UiField
  Label entity_name;

  @UiField
  TextBox items_list;
  private MetaUnitMultivaluedEntityDTO dto;
  private JSON_Representation current_json;
  private HashMap<String, String> filling;
  public ArrayList<Long> initial_selected_ids = new ArrayList();
  public ArrayList<Long> selected_ids = new ArrayList();
  private String base_name;
  public MultiValuedEntityMultiselected()
  {
    initWidget((Widget)uiBinder.createAndBindUi(this));
  }

  public MultiValuedEntityMultiselected(MetaUnitMultivaluedEntityDTO dto, JSON_Representation represent, HashMap<String, String> filling,String base_name)
  {
    initWidget((Widget)uiBinder.createAndBindUi(this));
    this.dto = dto;
    this.entity_name.setText(dto.getDesc());
    this.filling = filling;
    this.base_name = base_name.equals("")?dto.getUnique_name():base_name+"_"+dto.getUnique_name();
    if(this.filling!=null)
    if (this.filling.containsKey(this.base_name))
    {
      String val = (String)filling.get(this.base_name);
      if (val != null)
      {
        this.items_list.setText(val);
        if (val.contains(";"))
        {
          String[] tokens = val.split(";");
          for (String token : tokens)
          {
            int index = dto.getItem_names().indexOf(token);
            long idd = ((Long)dto.getItem_ids().get(index)).longValue();
            this.initial_selected_ids.add(Long.valueOf(idd));
            this.selected_ids.add(Long.valueOf(idd));
          }
        }else
        {
        	int index = dto.getItem_names().indexOf(val);
        	if(index != -1)
        	{
        	      long idd = ((Long)dto.getItem_ids().get(index)).longValue();
                  this.initial_selected_ids.add(Long.valueOf(idd));
                  this.selected_ids.add(Long.valueOf(idd));
           }
        }
      }
    }
  }

  
  
  
  
  @UiHandler({"edit_field"})
  public void editFieldCmd(ClickEvent ev)
  {
    PopupPanel p = new PopupPanel();
    p.setTitle("Редактирование поля...");
    p.setModal(true);
    p.setPopupPosition(200, 200);
    p.setSize("700px", "400px");
    p.setWidget(new FieldEditor(new MultiValuedField(this.dto, null, this.filling,base_name), p));
    p.show();
  }

  
  private DialogBox createDialogBox(String text) {
	    // Create a dialog box and set the caption text
	    DialogBox dialogBox = new DialogBox();
	    return dialogBox;
	  }

  
  
  
  
  @UiHandler({"edit_selection"})
  public void editSelectionCmd(ClickEvent ev) {
    //DialogBox dialogBox = createDialogBox("Редактирование выбора...");
    //dialogBox.setGlassEnabled(true);
    //dialogBox.setAnimationEnabled(true);
	  PopupPanel p = new PopupPanel(true);
	  p.setModal(false);
	  p.setPopupPosition(200, 200);
	  p.setAnimationEnabled(true);
	 p.setTitle("Редактирование выбора...");
    //dialogBox.setModal(true);
    //dialogBox.center();
    p.setSize("500px", "350px");
    p.setWidget(new MultiselectionEditor(this, p));
    p.setVisible(true);
    p.show();
  }
  @UiHandler({"edit_items"})
  public void editItemsCmd(ClickEvent ev) {
    PopupPanel p = new PopupPanel(true);
    p.setTitle("Редактирование элементов...");
    p.setModal(false);
    p.setPopupPosition(200, 200);
    p.setSize("700px", "400px");
    p.setWidget(new MultiValuedEntity(this.dto, null, this.filling,""));
    p.show();
  }

  
  private void rebuildJSON()
  {
    JSONObject obj = new JSONObject();

    obj.put(base_name, new JSONString(this.items_list.getText()));

    this.current_json = new JSON_Representation(obj);
  }

  public String getUniqueName()
  {
    return this.dto.getUnique_name();
  }

  public JSON_Representation getJSON() {
    rebuildJSON();
    return this.current_json;
  }

  public String getFilledValue()
  {
    return this.items_list.getText();
  }

  public MetaUnitDTO getDTO() {
    return this.dto;
  }

  public void populateItemsLinksTo(final Long id, final String identifier)
  {
    int index = 0;
    if (!this.initial_selected_ids.equals(this.selected_ids))
    {
      ArrayList<Long> A = new ArrayList<Long>();
      ArrayList<Long> B = new ArrayList<Long>();
      for (Long el : this.initial_selected_ids)
      {
        if (this.selected_ids.contains(el)) continue; A.add(el);
      }
      for (Long el : this.selected_ids)
      {
        if (this.initial_selected_ids.contains(el)) continue; B.add(el);
      }

      for (final Long oldl : A) {
        new RPCCall<MetaUnitEntityItemDTO>()
        {
          public void onFailure(Throwable caught)
          {
          }

          public void onSuccess(final MetaUnitEntityItemDTO result) {
            if (result.getTagged_entities_ids().contains(id))
            {
              result.getTagged_entities_ids().remove(result.getTagged_entities_ids().indexOf(id));
              result.getTagged_entities_identifiers().remove(result.getTagged_entities_ids().indexOf(id));
            }

            new RPCCall<Void>()
            {
              public void onFailure(Throwable caught) {
              }

              public void onSuccess(Void result) {
              }

              protected void callService(AsyncCallback<Void> cb) {
                MultiValuedEntityMultiselected.this.service.updateMetaUnitEntityItemLinks(result, cb);
              }
            }.retry(2);
          }

          protected void callService(AsyncCallback<MetaUnitEntityItemDTO> cb)
          {
            MultiValuedEntityMultiselected.this.userService.getEntityItemDTO(oldl, cb);
          }
        }
        .retry(2);
      }
      for (final Long oldl : B)
        new RPCCall<MetaUnitEntityItemDTO>()
        {
          public void onFailure(Throwable caught) {
          }

          public void onSuccess(final MetaUnitEntityItemDTO result2) {
            if (!result2.getTagged_entities_ids().contains(id))
            {
              result2.getTagged_entities_ids().add(id);
              result2.getTagged_entities_identifiers().add(identifier);
            }
            new RPCCall<Void>()
            {
              public void onFailure(Throwable caught)
              {
            	  Window.alert("Error link upd:"+caught.getMessage());
              }

              public void onSuccess(Void result) {
            	  Window.alert("Links populated!");
              }

              protected void callService(AsyncCallback<Void> cb) {
                MultiValuedEntityMultiselected.this.service.updateMetaUnitEntityItemLinks(result2, cb);
              }
            }
            .retry(2);
          }

          
          protected void callService(AsyncCallback<MetaUnitEntityItemDTO> cb)
          {
            MultiValuedEntityMultiselected.this.userService.getEntityItemDTO(oldl, cb);
          }
        }
        .retry(2);
    }
  }

  static abstract interface MultiValuedEntityMultiselectedUiBinder extends UiBinder<Widget, MultiValuedEntityMultiselected>
  {
  }
}