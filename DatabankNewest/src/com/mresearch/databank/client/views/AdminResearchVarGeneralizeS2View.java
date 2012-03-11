package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;
import com.mresearch.databank.shared.VarDTO_Light;

class GenListBox extends ListBox
{
	private long research_id;
	public GenListBox(long research_id)
	{
		super();
		this.setResearch_id(research_id);
	}
	public long getResearch_id() {
		return research_id;
	}
	public void setResearch_id(long research_id) {
		this.research_id = research_id;
	}
}
public class AdminResearchVarGeneralizeS2View extends Composite {

	private static AdminResearchVarGeneralizeS1ViewUiBinder uiBinder = GWT
			.create(AdminResearchVarGeneralizeS1ViewUiBinder.class);
	private UserSocioResearchServiceAsync service = GWT.create(UserSocioResearchService.class);
	private AdminSocioResearchServiceAsync service_admin = GWT.create(AdminSocioResearchService.class);
	interface AdminResearchVarGeneralizeS1ViewUiBinder extends
			UiBinder<Widget, AdminResearchVarGeneralizeS2View> {
	}
	private long research_id;
	private Map<Long,ArrayList<String>> varnames_map;
	private Map<Long,ArrayList<Long>> ids_map,incidents_map;
	private ArrayList<GenListBox> listboxes;
	@UiField Button generalize_btn;
	@UiField FlexTable the_table;
	private int base_size = 0;
	//@UiField HTMLPanel percents_type_pnl;
	private ArrayList<Long> research_ids;
	private ArrayList<String> research_names;
	public AdminResearchVarGeneralizeS2View(long research_id,ArrayList<Long> research_ids,ArrayList<String> research_names) {
		initWidget(uiBinder.createAndBindUi(this));
	//	var1_lbox.addItem("Загрузка переменных...");
	//	var2_lbox.addItem("Загрузка переменных...");
		ids_map = new HashMap<Long, ArrayList<Long>>();
		varnames_map = new HashMap<Long, ArrayList<String>>();
		//stores indecies of vars which correspond to vars in base array under index in store.
		incidents_map = new HashMap<Long, ArrayList<Long>>();
		this.research_id = research_id;
		this.research_ids = research_ids;
		this.research_names = research_names;
		this.listboxes = new ArrayList<GenListBox>();
		the_table.getColumnFormatter().setStyleName(0, "varsColomn");
		
		the_table.setWidget(0, 0, new Label("Генерализация переменных по массивам"));
		int col = 1;
		for(String name:this.research_names)
		{
			the_table.setWidget(0,col, new Label(name));
			the_table.getColumnFormatter().setWidth(col, "370px");
			col++;
		}
		//the_table.setStyleName("panel flexTable");
		fetchInitVarsList(this.research_id);
		
	}
	@UiHandler(value="generalize_btn")
	public void onBuildBtnClick(ClickEvent e)
	{
	}
	
	private void fetchInitVarsList(final long researchID)
	{
		new RPCCall<ArrayList<VarDTO_Light>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on getting vars dtos:"+caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<VarDTO_Light> result) {
				fillTableInitWithVarDTOs(result);
				for(long id:research_ids)
				{
					fetchVarsList(id);
				}
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<VarDTO_Light>> cb) {
				
				service.getResearchVarsSummaries(researchID, cb);
			}
		}.retry(2);
	}
	private void fetchVarsList(final long researchID)
	{
		new RPCCall<ArrayList<VarDTO_Light>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on getting vars dtos:"+caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<VarDTO_Light> result) {
				addListBoxWithVarDTOs(researchID,result);
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<VarDTO_Light>> cb) {
				
				service.getResearchVarsSummaries(researchID, cb);
			}
		}.retry(2);
	}
	private void addListBoxWithVarDTOs(long research_id,ArrayList<VarDTO_Light> arr)
	{
		GenListBox box = new GenListBox(research_id);
		box.clear();
		box.addItem("Нет");
//		var1_lbox.clear();
		ArrayList<Long> ids = new ArrayList<Long>();
		ArrayList<String> names = new ArrayList<String>();
		for(int i = 0;i < arr.size();i++)
		{
			VarDTO_Light dto = arr.get(i);
			ids.add(dto.getId());
			names.add(dto.getLabel());
	//		the_table.setWidget(i, 0, new Label(dto.getCode()+"::"+dto.getLabel()));
			box.addItem(dto.getCode()+"::"+dto.getLabel());
		}
		ids_map.put(research_id, ids);
		varnames_map.put(research_id, names);
		listboxes.add(box);
		
		final int curr_col = listboxes.size()-1;
		if (curr_col >= 0)
		{
			for(int i = 1;i < base_size+1;i++)
			{
				//GenListBox curr_box = cloneBox(listboxes.get(curr_col));
				//curr_box.setSize("300px", "35px");
				//the_table.setWidget(i, curr_col+1,curr_box);
				final int index = i-1;
				final VerticalPanel p = new VerticalPanel();
				final Anchor anc = new Anchor("Нет");
				anc.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						p.clear();
						final GenListBox curr_box = cloneBox(listboxes.get(curr_col));
						curr_box.setSize("300px", "35px");
						curr_box.addChangeHandler(new ChangeHandler() {
							@Override
							public void onChange(ChangeEvent event) {
								String selected = curr_box.getValue(curr_box.getSelectedIndex());
								anc.setText(selected);
								p.clear();
								p.add(anc);
								Long curr_research_id = curr_box.getResearch_id();
								Long var_id = ids_map.get(curr_research_id).get(curr_box.getSelectedIndex()-1);
								incidents_map.get(curr_research_id).set(index, var_id);
							}
						});
						p.add(curr_box);
					}
				});
				p.add(anc);
				the_table.setWidget(i, curr_col+1,p);
				the_table.getCellFormatter().setWidth(i, curr_col+1, "350px");
			}
		}
	}
	private GenListBox cloneBox(GenListBox origin)
	{
		GenListBox n = new GenListBox(origin.getResearch_id());
		for(int i = 0; i < origin.getItemCount();i++)
		{
			n.addItem(origin.getValue(i));
		}
		return n;
	}
	private void fillTableInitWithVarDTOs(ArrayList<VarDTO_Light> arr)
	{
		ArrayList<Long> ids = new ArrayList<Long>();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Long> base_incidents = new ArrayList<Long>();
		this.base_size = arr.size();
		for(int i = 0;i < arr.size();i++)
		{
			VarDTO_Light dto = arr.get(i);
			ids.add(dto.getId());
			names.add(dto.getLabel());
			Label l = new Label(dto.getCode()+"::"+dto.getLabel());
		//	l.setSize("400px", "30px");
			l.setWidth("400px");
			the_table.setWidget(i+1, 0, l);
			base_incidents.add(null);
		}
		ids_map.put(research_id, ids);
		varnames_map.put(research_id, names);
		for(Long key:research_ids) incidents_map.put(key, base_incidents);
				
	}
	@UiHandler(value="generalize_btn")
	public void processGeneralization(ClickEvent ev)
	{
		for(int i = 0;i < base_size;i++)
		{
			ArrayList<Long> var_ids_generalize = new ArrayList<Long>();
			Long base_id = ids_map.get(research_id).get(i);
			var_ids_generalize.add(base_id);
			for(Long key:research_ids)
			{
				Long id_var = incidents_map.get(key).get(i);
				var_ids_generalize.add(id_var);
			}
			for(Long var_id:var_ids_generalize)
			{
				if(var_id == null) continue;
				
				final long var_idd = var_id;
				final ArrayList<Long> others_arr = new ArrayList<Long>();
				for(Long var_id2:var_ids_generalize)
				{
					if(!var_id.equals(var_id2) && var_id2 != null)others_arr.add(var_id2);
				}
				if(others_arr.size() == 0) continue;
				new RPCCall<VarDTO_Detailed>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error while uodating var:"+caught.getMessage());
					}
					@Override
					public void onSuccess(VarDTO_Detailed result) {
						Window.alert(result.getGen_var_names().toString());
					}

					@Override
					protected void callService(AsyncCallback<VarDTO_Detailed> cb) {
						service_admin.generalizeVar(var_idd, others_arr, cb);
					}
				}.retry(3);
			}
		}
	}

}
