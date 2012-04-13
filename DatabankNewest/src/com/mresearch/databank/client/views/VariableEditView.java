package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.opendatafoundation.data.spss.mod.SPSSUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.views.DBfillers.VarMultiValuedField;
import com.mresearch.databank.client.views.DBviewers.MultiValuedFieldViewer;
import com.mresearch.databank.client.views.DBviewers.VarMultiValuedFieldViewer;
import com.mresearch.databank.shared.JSON_Representation;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;
import com.rednels.ofcgwt.client.ChartWidget;

public class VariableEditView extends Composite {

	private static VariableDetailedViewUiBinder uiBinder = GWT
			.create(VariableDetailedViewUiBinder.class);

	interface VariableDetailedViewUiBinder extends
			UiBinder<Widget, VariableEditView> {
	}

	@UiField Label varCode,varText;
	@UiField FlexTable codeSchemeTbl,generalizedTbl;
	@UiField HTMLPanel main_html;
//	@UiField Button save_html_btn;
	@UiField HorizontalPanel save_pnl;
	//@UiField VerticalPanel graph_pnl;
	@UiField VerticalPanel elasticDBfields;
	@UiField Button confirmBtn;
//	Hyperlink link;
//	private FormPanel form;
//	private TextBox hidden_box;
//	private TextBox hidden_box2;
//	private VarDTO_Detailed dto;
//	@UiHandler(value="save_html_btn")
//	public void save_html(ClickEvent e)
//	{
//		String s = main_html.toString();
//		hidden_box.setText(s);
//		hidden_box2.setText("DB_saved_var_distr_"+dto.getCode());
//		form.submit();
//		//s = URL.encode(s);
//		//save_pnl.clear();
//		//save_pnl.add(new HTML("<a href=\"/databank/htmlSave?tosave="+s+"\" target=\"_blank\">Скачать файл!</a>"));
//	}
	
	
	
	
	
	private VarMultiValuedField mv;
	private MetaUnitMultivaluedEntityDTO db;
	private VarDTO_Detailed dto;
	public VariableEditView(VarDTO_Detailed dto,MetaUnitMultivaluedEntityDTO dt) {
		initWidget(uiBinder.createAndBindUi(this));
		this.db = dt;
		this.dto = dto;
		if(dto.getFilling() == null) dto.setFilling(new HashMap<String, String>());
		//form.a
		save_pnl.add(new SaveHTMLAddon(main_html));
		
		varCode.setText(dto.getCode());
		varText.setText(dto.getLabel());
		ArrayList<Double> codes = dto.getV_label_codes();
		ArrayList<String> values = dto.getV_label_values();
		
		int i = 0;
		codeSchemeTbl.setSize("600px", "350px");
		codeSchemeTbl.setWidget(0, 0, new Label("Код"));
		codeSchemeTbl.setWidget(0, 1, new Label("Текст альтернативы"));
		codeSchemeTbl.setWidget(0, 2, new Label("Частота"));
		//codeSchemeTbl.insertCell(beforeRow, beforeColumn)
		Double total = new Double(0);
		
		for(Double cnt:dto.getDistribution())
		{
			total+=cnt;
		}
        for(Double key:codes)
        {
 		   String label = values.get(i);
           codeSchemeTbl.setWidget(i+1, 0, new Label(key.toString()));
           codeSchemeTbl.setWidget(i+1, 1, new Label(label.toString()));
           
           //NumberFormat formatter = NumberFormat.getInstance();
           NumberFormat formatter = NumberFormat.getFormat("0.00");
          // formatter.
           //formatter.setMaximumFractionDigits(2);
           String myNumber = formatter.format(new Double(dto.getDistribution().get(i)/total)*100);
           
          // new NumberFormat();
           codeSchemeTbl.setWidget(i+1, 2, new Label(myNumber+"%"));
           //com.google.gwt.user.client.ui.MultiWordSuggestOracle c = new MultiWordSuggestOracle();
           //c.requestSuggestions(request, callback)
           //com.google.gwt.user.client.ui.
           i++;
        }
        
        generalizedTbl.setSize("600px", "350px");
		for(int j = 0; j < dto.getGen_var_names().size();j++)
		{
			generalizedTbl.setWidget(j, 0, new Label("как"));
			generalizedTbl.setWidget(j, 1, new VarLink(dto.getGen_vars_ids().get(j),dto.getGen_var_names().get(j)));
			generalizedTbl.setWidget(j, 2, new Label("в исследовании"));
			generalizedTbl.setWidget(j, 3, new ResearchLink(dto.getGen_research_ids().get(j),dto.getGen_research_names().get(j)));
		}
		
		
		
		
		renderDBfillers();
		confirmBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent arg0) {
				
				@SuppressWarnings("unused")
				JSON_Representation json = mv.getJSON();
				mv.populateItemsLinksTo(VariableEditView.this.dto.getId(), "sociovar");
				HashMap<String, String> mapp =mv.returnCollectedMap();
				VariableEditView.this.dto.setFilling(mapp);
				new RPCCall<Void>() {

					@Override
					public void onFailure(Throwable arg0) {
					}

					@Override
					public void onSuccess(Void arg0) {
						Window.alert("Переменная успешно обновлена!");
					}

					@Override
					protected void callService(AsyncCallback<Void> cb) {
						AdminSocioResearchService.Util.getInstance().updateVar(VariableEditView.this.dto, cb);
					}
				}.retry(2);
			}
		});
	}
	
	private void renderDBfillers()
	{
		elasticDBfields.clear();
		 mv = new VarMultiValuedField(db,null,dto.getFilling(),"");
		elasticDBfields.add(mv);
	}
}
