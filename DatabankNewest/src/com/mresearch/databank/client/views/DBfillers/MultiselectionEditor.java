package com.mresearch.databank.client.views.DBfillers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.shell.remoteui.RemoteMessageProto.Message.Request;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.core.java.util.Arrays;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.smartgwt.client.core.DataClass;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.widgets.TransferImgButton;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VStack;
import java.util.ArrayList;
import java.util.Random;

public class MultiselectionEditor extends Composite
{
  private static SimpleStringFieldUiBinder uiBinder = (SimpleStringFieldUiBinder)GWT.create(SimpleStringFieldUiBinder.class);

  @UiField
  VerticalPanel host;
  private MultiValuedEntityMultiselected ent_w;
  private PopupPanel par;
  final ListGrid selected = new ListGrid();
  final ListGrid avaible = new ListGrid();

  public MultiselectionEditor()
  {
    initWidget((Widget)uiBinder.createAndBindUi(this));
  }

  public MultiselectionEditor(MultiValuedEntityMultiselected entity_w, PopupPanel parent)
  {
    initWidget((Widget)uiBinder.createAndBindUi(this));
    this.par = parent;
    this.ent_w = entity_w;
    initFields();
  }

  
  private void initFields()
  {
    ArrayList<Long> current_sel_ids = new ArrayList<Long>();
    ArrayList<String> current_sel_names = new ArrayList<String>();

    String val = this.ent_w.items_list.getText();
    if (val != null && !val.equals(""))
    {
      if (val.contains(";"))
      {
        String[] tokens = val.split(";");
        for (String token : tokens)
        {
        	
          long index = ((MetaUnitMultivaluedEntityDTO)this.ent_w.getDTO()).getItem_names().indexOf(token);
          if(index >=0)
     	   {
        	  long idd = ((Long)((MetaUnitMultivaluedEntityDTO)this.ent_w.getDTO()).getItem_ids().get((int)index)).longValue();
        	  current_sel_ids.add(Long.valueOf(idd));
              current_sel_names.add(token);
           }
        }
      }
      else
      {
      	   int index = ((MetaUnitMultivaluedEntityDTO)this.ent_w.getDTO()).getItem_names().indexOf(val);
      	   if(index >=0)
      	   {
      		   long idd = ((Long)((MetaUnitMultivaluedEntityDTO)this.ent_w.getDTO()).getItem_ids().get((int)index)).longValue();
      		   current_sel_ids.add(Long.valueOf(idd));
      		   current_sel_names.add(val);
      	   }
      }
    }

    
    
    
    
    
    
    
    
    
    
    
    this.avaible.setWidth(150);
    this.avaible.setHeight(200);
    ArrayList<Long> av_ids_or = ((MetaUnitMultivaluedEntityDTO)this.ent_w.getDTO()).getItem_ids();
    ArrayList<String> av_names_or =  ((MetaUnitMultivaluedEntityDTO)this.ent_w.getDTO()).getItem_names();
    ArrayList<Long> av_ids = new ArrayList<Long>();
    ArrayList<String> av_names = new ArrayList<String>();
    for(Long l:av_ids_or)av_ids.add(l);
    for(String s:av_names_or)av_names.add(s);
    
    for(Long id:current_sel_ids)
    {
    	if(av_ids.contains(id))
    	{
    		//av_names.remove(av_ids.indexOf(id));
    		//av_ids.remove(av_ids.indexOf(id));
    	}
    }
    this.avaible.setDataSource(new PDDataSource("IDD" + String.valueOf(new Random().nextInt()),av_ids,av_names));
    this.avaible.setAutoFetchData(Boolean.valueOf(false));
    this.avaible.setCanDragRecordsOut(Boolean.valueOf(true));
    this.avaible.setDragDataAction(DragDataAction.COPY);
    ListGridField employeeIdField = new ListGridField("ID");
    employeeIdField.setWidth("30%");
    ListGridField employeevalueField = new ListGridField("value");
    employeevalueField.setWidth("70%");
    this.avaible.setFields(new ListGridField[] { employeevalueField });

    Object teamMembersDS = new PDDataSource("IDDR", current_sel_ids,current_sel_names);

    this.selected.setWidth(150);
    this.selected.setHeight(264);
    this.selected.setDataSource((DataSource)teamMembersDS);
    this.selected.setCanAcceptDroppedRecords(Boolean.valueOf(true));
    this.selected.setCanRemoveRecords(Boolean.valueOf(true));
    this.selected.setAutoFetchData(Boolean.valueOf(false));
    this.selected.setPreventDuplicates(Boolean.valueOf(true));

    ListGridField employeeIdField2 = new ListGridField("ID");
    employeeIdField2.setWidth("30%");

    ListGridField employeeNameField2 = new ListGridField("value");
    employeeNameField2.setWidth("70%");

    this.selected.setFields(new ListGridField[] { employeeIdField2, employeeNameField2 });

    HStack hStack = new HStack(10);
    hStack.setHeight(160);

    VStack vStack = new VStack();
    LayoutSpacer spacer = new LayoutSpacer();
    spacer.setHeight(30);
    vStack.addMember(spacer);
    vStack.addMember(this.avaible);

    hStack.addMember(vStack);

    TransferImgButton arrowImg = new TransferImgButton(TransferImgButton.RIGHT);
    arrowImg.addClickHandler(new ClickHandler()
    {
      public void onClick(com.smartgwt.client.widgets.events.ClickEvent event)
      {
        MultiselectionEditor.this.selected.transferSelectedData(MultiselectionEditor.this.avaible);
      }
    });
    hStack.addMember(arrowImg);

    VStack vStack2 = new VStack();

    vStack2.addMember(this.selected);

    hStack.addMember(vStack2);

    this.selected.fetchData();
    this.avaible.fetchData();

    this.host.add(hStack);
  }

  private String composeSelectionString()
  {
    RecordList lst = this.selected.getDataAsRecordList();
    Record[] r = lst.toArray();
    ArrayList new_items_selecteion_ids = new ArrayList();
    StringBuilder bld = new StringBuilder();
    for (int i = 0; i < r.length - 1; i++)
    {
      new_items_selecteion_ids.add(r[i].getAttributeAsLong("ID"));
      bld.append(r[i].getAttributeAsString("value") + ";");
    }
    new_items_selecteion_ids.add(r[(r.length - 1)].getAttributeAsLong("ID"));
    bld.append(r[(r.length - 1)].getAttributeAsString("value"));

    this.ent_w.selected_ids = new_items_selecteion_ids;
    return bld.toString();
  }

  @UiHandler({"doEdit"})
  public void doEdit(com.google.gwt.event.dom.client.ClickEvent ev) {
    this.ent_w.items_list.setText(composeSelectionString());
    this.par.hide();
  }
  @UiHandler({"doCancel"})
  public void doCancel(com.google.gwt.event.dom.client.ClickEvent ev) {
    this.par.hide();
  }

  public class PDDataSource extends DataSource
  {
    DataSourceTextField val = new DataSourceTextField("value");
    DataSourceIntegerField ID = new DataSourceIntegerField("ID");
    DataSourceTextField test3 = new DataSourceTextField("test3");

    int total_rows = 0;
    Record[] data;

    public PDDataSource(String name,ArrayList<Long> ids, ArrayList<String> names)
    {
      setID(id);

      setTestData(new DataClass[0]);
      this.ID.setPrimaryKey(Boolean.valueOf(true));
      setFields(new DataSourceField[] { this.ID, this.val });

      this.data = new Record[names.size()];
      for (int i = 0; i < names.size(); i++)
      {
        this.data[i] = new Record();
        if (names.get(i) != null) this.data[i].setAttribute("value", (String)names.get(i));
        if (ids.get(i) == null) continue; this.data[i].setAttribute("ID", ids.get(i));
      }

      this.total_rows = ids.size();

      setDataProtocol(DSProtocol.CLIENTCUSTOM);
      setDataFormat(DSDataFormat.CUSTOM);

      setClientOnly(Boolean.valueOf(true));
    }

    protected Object transformRequest(DSRequest request) {
      String requestId = request.getRequestId();
      DSResponse response = new DSResponse();
      response.setAttribute("clientContext", request.getAttributeAsObject("clientContext"));
      response.setTotalRows(Integer.valueOf(this.total_rows));

      response.setStatus(0);
      if(request.getOperationType().getValue().equals(DSOperationType.FETCH.getValue()))
      {
    	  executeFetch(requestId, request, response);
      }else
	  if(request.getOperationType().getValue().equals(DSOperationType.ADD.getValue()))
      {
		   executeAdd(requestId, request, response);
      }else
	  if(request.getOperationType().getValue().equals(DSOperationType.UPDATE.getValue()))
      {
		    executeUpdate(requestId, request, response);
      }else
	  if(request.getOperationType().getValue().equals(DSOperationType.REMOVE.getValue()))
      {
		  executeRemove(requestId, request, response);
	                	  
      }
//      switch (request.getOperationType().ordinal()) {
//      case op_f:
//        executeFetch(requestId, request, response);
//        break;
//      case DSOperationType.ADD.ordinal():
//        executeAdd(requestId, request, response);
//        break;
//      case DSOperationType.UPDATE.ordinal():
//        executeUpdate(requestId, request, response);
//        break;
//      case DSOperationType.REMOVE.ordinal():
//        executeRemove(requestId, request, response);
//        break;
//      }

      return request.getData();
    }

    private void executeUpdate(String requestId, DSRequest request, DSResponse response) {
      Record[] dt = request.getExportData();
      this.data = dt;
      response.setData(this.data);
      processResponse(requestId, response);
    }

    private void executeRemove(String requestId, DSRequest request, DSResponse response) {
      Record[] dt = request.getExportData();
     
//      Record[] datanew = new Record[this.data.length - dt.length];
//      int i = 0;
//      for (Record d : this.data)
//      {
//        boolean todeleate = false;
//        for (Record dd : dt)
//        {
//          if (!dd.getAttributeAsInt("ID").equals(d.getAttributeAsInt("ID"))) continue; todeleate = true;
//        }
//        if (todeleate) continue; datanew[(i++)] = d;
//      }
//      this.data = datanew;
      response.setData(dt);
      processResponse(requestId, response);
    }

    private void executeAdd(String requestId, DSRequest request, DSResponse response) {
      Record[] dt = request.getExportData();
//      Record[] datanew = new Record[this.data.length + dt.length];
//      int i = 0;
//      for (Record d : this.data)
//      {
//        datanew[(i++)] = d;
//      }
//      for (Record d : dt)
//      {
//        datanew[(i++)] = d;
//      }
      response.setData(dt);
      processResponse(requestId, response);
    }

    private void executeFetch(String requestId, DSRequest request, DSResponse response)
    {
      response.setData(this.data);
      processResponse(requestId, response);
    }
  }

  static abstract interface SimpleStringFieldUiBinder extends UiBinder<Widget, MultiselectionEditor>
  {
  }
}