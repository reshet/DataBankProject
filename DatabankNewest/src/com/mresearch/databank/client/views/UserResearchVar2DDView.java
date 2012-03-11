package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Light;
//There MVP pattern is ommited)
public class UserResearchVar2DDView extends Composite {

	private static UserResearchVar2DDViewUiBinder uiBinder = GWT
			.create(UserResearchVar2DDViewUiBinder.class);
	private UserSocioResearchServiceAsync service = GWT.create(UserSocioResearchService.class);
	interface UserResearchVar2DDViewUiBinder extends
			UiBinder<Widget, UserResearchVar2DDView> {
	}
	private long research_id;
	@UiField ListBox var1_lbox,var2_lbox;
	@UiField Button build_btn;
	@UiField FlexTable the2DD_table;
	//@UiField HTMLPanel percents_type_pnl;
	@UiField RadioButton show_percents,show_frequences,percents_colomn,percents_row,percents_table;
	private ArrayList<String> var_names;
	private ArrayList<Long> var_ids;
	
	private VarDTO var1,var2;
	private ArrayList<Double> distribution;
	@UiField HorizontalPanel target_panel;
	@UiField HTMLPanel content_panel;
	@UiField VerticalPanel selected_vars;
	public UserResearchVar2DDView(long research_id) {
		initWidget(uiBinder.createAndBindUi(this));
		
		DatabankApp.get().getCurrentUser().setCurrent_research(research_id);
		DatabankApp.get().updateUserAccountState();
		
		target_panel.add(new SaveHTMLAddon(content_panel));
		var1_lbox.addItem("Загрузка переменных...");
		var2_lbox.addItem("Загрузка переменных...");
		this.research_id = research_id;
		fetchVarsList();
	}
	@UiHandler(value="build_btn")
	public void onBuildBtnClick(ClickEvent e)
	{
		the2DD_table.clear();
		the2DD_table.removeAllRows();
		the2DD_table.setWidget(0,0, new HTML("<h2>Идет построение распределения. Подождите пожалуйста...</h2>"));
		fetchSelectedVarDTOs();
	}
	@UiHandler(value="show_percents")
	public void onShowPercentsChoice(ClickEvent e)
	{
		show_frequences.setValue(false);
		percents_colomn.setVisible(true);
		percents_row.setVisible(true);
		percents_table.setVisible(true);
		fill2DDtable(var1, var2, distribution);
		//percents_type_pnl.setVisible(true);
	}
	@UiHandler(value="show_frequences")
	public void onShowFrequencesChoice(ClickEvent e)
	{
		percents_colomn.setVisible(false);
		percents_row.setVisible(false);
		percents_table.setVisible(false);
		show_percents.setValue(false);
		fill2DDtable(var1, var2, distribution);
	//	percents_type_pnl.setVisible(false);
	}
	@UiHandler(value="percents_colomn")
	public void onPercentsColomnChoice(ClickEvent e)
	{
		percents_row.setValue(false);
		percents_table.setValue(false);
		fill2DDtable(var1, var2, distribution);
	}
	@UiHandler(value="percents_row")
	public void onPercentsRowChoice(ClickEvent e)
	{
		percents_colomn.setValue(false);
		percents_table.setValue(false);
		fill2DDtable(var1, var2, distribution);
	}
	@UiHandler(value="percents_table")
	public void onPercentsTableChoice(ClickEvent e)
	{
		percents_row.setValue(false);
		percents_colomn.setValue(false);
		fill2DDtable(var1, var2, distribution);
	}
	private void fetchVarsList()
	{
		new RPCCall<ArrayList<VarDTO_Light>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on getting vars dtos:"+caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<VarDTO_Light> result) {
				fillListsWithVarDTOs(result);
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<VarDTO_Light>> cb) {
				
				service.getResearchVarsSummaries(UserResearchVar2DDView.this.research_id, cb);
			}
		}.retry(2);
	}
	private void fillListsWithVarDTOs(ArrayList<VarDTO_Light> arr)
	{
		var1_lbox.clear();
		var2_lbox.clear();
		if(var_ids == null)var_ids = new ArrayList<Long>();
		if(var_names == null)var_names = new ArrayList<String>();
		var_ids.clear();
		var_names.clear();
		
		for(VarDTO_Light dto:arr)
		{
			var_ids.add(dto.getId());
			var_names.add(dto.getLabel());
			var1_lbox.addItem(dto.getCode()+"::"+dto.getLabel());
			var2_lbox.addItem(dto.getCode()+"::"+dto.getLabel());
		}
	}
	private void fill2DDtable(VarDTO var1,VarDTO var2,ArrayList<Double> distr)
	{
		selected_vars.clear();
		selected_vars.add(new HTML("<h3>Распределение "+var1.getCode()+" : "+var1.getLabel()+"</h3>"));
		selected_vars.add(new HTML("<h3>относительно "+var2.getCode()+" : "+var2.getLabel()+"</h3>"));
		
		Double total = new Double(0);
		ArrayList<Double> total1 = new ArrayList<Double>();
		ArrayList<Double> total2 = new ArrayList<Double>();
		double[][] table_distr = new double[var1.getV_label_codes().size()][var2.getV_label_codes().size()];
		
		int frame_size = var1.getV_label_codes().size();
		int frames_col = var2.getV_label_codes().size();
		int col = 0;
		for(int i = 0; i < distr.size();i+=frame_size)
		{
			int kol = 0;
			double total1elem = 0;
			//table_distr[col] = new double[frames_col];
			for(int j = i;j<i+frame_size;j++)
			{
				table_distr[kol++][col] = distr.get(j);
				total1elem+=distr.get(j);
			}
			col++;
			total+=total1elem;
			total1.add(total1elem);
		}
		for(int i = 0; i < frame_size;i++)
		{
			double total2elem = 0;
			for(int j = 0; j < frames_col;j++)
			{
				total2elem+=table_distr[i][j];
			}		
			total2.add(total2elem);
		}
				
		
		the2DD_table.clear();
		the2DD_table.setBorderWidth(2);
		the2DD_table.setWidget(0, 0, new Label(var1.getCode()+"/"+var2.getCode()));
		for(int i = 0; i < var1.getV_label_values().size();i++)
		{
			the2DD_table.setWidget(i+1, 0, new Label(var1.getV_label_values().get(i)));
		}
		for(int i = 0; i < var2.getV_label_values().size();i++)
		{
			the2DD_table.setWidget(0, i+1, new Label(var2.getV_label_values().get(i)));
		}
		
		NumberFormat formatter = NumberFormat.getFormat("0.00");
        
		if(show_frequences.getValue() == true)
		{
			for(int i = 1; i < var1.getV_label_values().size()+1;i++)
			{
				//Double freq2_i = var2.getDistribution().get(i-1)/total2;
				for(int j = 1; j < var2.getV_label_values().size()+1;j++)
				{ 
					//Double freq_ij = (var1.getDistribution().get(j-1)/total1)*freq2_i;
					String myNumber = formatter.format(table_distr[i-1][j-1]);       		
					the2DD_table.setWidget(i, j, new Label(myNumber));
				}	
			}
		}else
		if(show_percents.getValue() == true)
		{
			if(percents_row.getValue() == true)
			{
				for(int i = 1; i < var1.getV_label_values().size()+1;i++)
				{
					//Double freq2_i = var2.getDistribution().get(i-1)/total2;
					for(int j = 1; j < var2.getV_label_values().size()+1;j++)
					{ 
						//Double freq_ij = (var1.getDistribution().get(j-1)/total1)*freq2_i;
						double value = 0;
						if(total2.get(i-1) > 0) value = table_distr[i-1][j-1]/total2.get(i-1)*100;
						
						String myNumber = formatter.format(value);       		
						the2DD_table.setWidget(i, j, new Label(myNumber+"%"));
					}	
				}
			}else
			if(percents_colomn.getValue() == true)
			{
				for(int i = 1; i < var1.getV_label_values().size()+1;i++)
				{
					//Double freq2_i = var2.getDistribution().get(i-1)/total2;
					for(int j = 1; j < var2.getV_label_values().size()+1;j++)
					{ 
						//Double freq_ij = (var1.getDistribution().get(j-1)/total1)*freq2_i;
						double value = 0;
						if(total1.get(j-1) > 0) value = table_distr[i-1][j-1]/total1.get(j-1)*100;
						String myNumber = formatter.format(value);       		
						the2DD_table.setWidget(i, j, new Label(myNumber+"%"));
					}	
				}
			}else
			if(percents_table.getValue() == true)
			{
				for(int i = 1; i < var1.getV_label_values().size()+1;i++)
				{
					//Double freq2_i = var2.getDistribution().get(i-1)/total2;
					for(int j = 1; j < var2.getV_label_values().size()+1;j++)
					{ 
						//Double freq_ij = (var1.getDistribution().get(j-1)/total1)*freq2_i;
						double value = 0;
						if(total > 0) value = table_distr[i-1][j-1]/total*100;
						String myNumber = formatter.format(value);       		
						the2DD_table.setWidget(i, j, new Label(myNumber+"%"));
					}	
				}
			}		
		}
		
	}
	private void process2DDbuilding(final VarDTO var1,final VarDTO var2)
	{
		this.var1 = var1;
		this.var2 = var2;
		
		//apply weights and filters
		 // formatter.
          //formatter.setMaximumFractionDigits(2);
		new RPCCall<ArrayList<Double>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error building 2dd distr:"+caught.getMessage());
				
			}
			@Override
			public void onSuccess(ArrayList<Double> result) {
				UserResearchVar2DDView.this.distribution = result;
				fill2DDtable(UserResearchVar2DDView.this.var1, UserResearchVar2DDView.this.var2, distribution);
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<Double>> cb) {
			       service.get2DDistribution(var1.getId(), var2.getId(), cb);
			}
		}.retry(2);
		
		//default faked&wrong table filling
//		for(int i = 1; i < var2.getV_label_values().size()+1;i++)
//		{
//			Double freq2_i = var2.getDistribution().get(i-1)/total2;
//			for(int j = 1; j < var1.getV_label_values().size()+1;j++)
//			{ 
//				Double freq_ij = (var1.getDistribution().get(j-1)/total1)*freq2_i;
//				String myNumber = formatter.format(freq_ij*100);       		
//				the2DD_table.setWidget(j, i, new Label(myNumber+"%"));
//			}	
//		}
	
 		
	}
	private void fetchSelectedVarDTOs()
	{	
		final long var1_id = var_ids.get(var1_lbox.getSelectedIndex());
		final long var2_id = var_ids.get(var2_lbox.getSelectedIndex());
		new RPCCall<VarDTO>() {
			private VarDTO var1 = null;
			private VarDTO var2 = null;
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fethcing first var:"+caught.getMessage());
			}

			@Override
			public void onSuccess(VarDTO result) {
				var1 = result;
				new RPCCall<VarDTO>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error fethcing first var:"+caught.getMessage());
					}

					@Override
					public void onSuccess(VarDTO result) {
						var2 = result;
						process2DDbuilding(var1, var2);
					}
					@Override
					protected void callService(AsyncCallback<VarDTO> cb) {
						service.getVar(var2_id, cb);
					}
				}.retry(2);

			}

			@Override
			protected void callService(AsyncCallback<VarDTO> cb) {
				service.getVar(var1_id, cb);
			}
		}.retry(2);
	}
}
