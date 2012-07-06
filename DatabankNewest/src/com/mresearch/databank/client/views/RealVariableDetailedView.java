package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.opendatafoundation.data.spss.mod.SPSSUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.event.ShowVarPlotEvent;
import com.mresearch.databank.client.event.ShowVarPlotEventHandler;
import com.mresearch.databank.client.presenters.UserResearchPerspectivePresenter;
import com.mresearch.databank.client.views.DBviewers.MultiValuedFieldViewer;
import com.mresearch.databank.client.views.DBviewers.VarMultiValuedFieldViewer;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.RealVarDTO_Detailed;
import com.mresearch.databank.shared.SocioResearchDTO_Light;
import com.mresearch.databank.shared.UserAccountDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;

public class RealVariableDetailedView extends Composite {

	private static RealVariableDetailedViewUiBinder uiBinder = GWT
			.create(RealVariableDetailedViewUiBinder.class);

	interface RealVariableDetailedViewUiBinder extends
			UiBinder<Widget, RealVariableDetailedView> {
	}

	@UiField Label varText,number_of_records,moda_txt,median_txt,average_txt,dispersion_txt,dispersion_root_txt;
	@UiField FlexTable generalizedTbl;
//	@UiField HorizontalPanel target_panel;
	//@UiField HTMLPanel content_panel;
	@UiField Label concept_name,concept_value;

	private MetaUnitMultivaluedEntityDTO db;
	private VarDTO_Detailed dto;
	@UiField VerticalPanel research_link;
	@UiField HorizontalPanel analysis_bar;

	
	
	public RealVariableDetailedView(RealVarDTO_Detailed dto,MetaUnitMultivaluedEntityDTO dt,SimpleEventBus bus,UserResearchPerspectivePresenter.Display display) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		this.db = dt;
		UserAccountDTO user = DatabankApp.get().getCurrentUser();
		analysis_bar.add(new AnalisysBarView(bus, display,user.getFilters_use()>0?true:false,user.getWeights_use()>0?true:false,(int)dto.getId()));
		
		//target_panel.add(new SaveHTMLAddon(content_panel));
		//varCode.setText(dto.getCode());
		varText.setWidth("400px");
		varText.setWordWrap(true);
		varText.setText(dto.getLabel());
		number_of_records.setText(String.valueOf(dto.getNumber_of_records()));
		research_link.add(new ResearchDescItemView(new SocioResearchDTO_Light(dto.getResearch_id(),dto.getResearch_name())));
		int i = 0;
		NumberFormat formatter = NumberFormat.getFormat("0.0");
        // formatter.
         //formatter.setMaximumFractionDigits(2);
        // String myNumber = formatter.format(new Double(dto.getDistribution().get(i)/total)*100);
		//codeSchemeTbl.insertCell(beforeRow, beforeColumn)
		moda_txt.setText(formatter.format(dto.getModa()));
		median_txt.setText(formatter.format(dto.getMediana()));
		average_txt.setText(formatter.format(dto.getAverage()));
		dispersion_txt.setText(formatter.format(dto.getDispersion()));
		dispersion_root_txt.setText(formatter.format(dto.getRoot_dispersion()));
						
        generalizedTbl.setSize("600px", "350px");
		for(int j = 0; j < dto.getGen_var_names().size();j++)
		{
			generalizedTbl.setWidget(j, 0, new Label(" как "));
			generalizedTbl.setWidget(j, 1, new Label(dto.getGen_var_names().get(j)));
			generalizedTbl.setWidget(j, 2, new Label(" в исследовании "));
			generalizedTbl.setWidget(j, 3, new Label(dto.getGen_research_names().get(j)));
		}
//		bus.addHandler(ShowVarPlotEvent.TYPE, new ShowVarPlotEventHandler() {
//			@Override
//			public void onShowVarPlot(ShowVarPlotEvent event) {
//				if(RealVariableDetailedView.this.dto.getId() == event.getVar_id())showPlot();
//			}
//		});
		renderDBfillers();
	}
	private void renderDBfillers()
	{
//		elasticDBfields.clear();
//		VarMultiValuedFieldViewer mv = new VarMultiValuedFieldViewer(db,dto.getFilling(),"");
//		elasticDBfields.add(mv);
		
		Collection<MetaUnitDTO> base = db.getSub_meta_units();
		int i = 0;
		if(base!=null)
		for(MetaUnitDTO dto:base)
		{
			if(dto instanceof MetaUnitMultivaluedEntityDTO)
			{
				MetaUnitMultivaluedEntityDTO dto_str = (MetaUnitMultivaluedEntityDTO)dto;
				concept_name.setText(dto_str.getDesc());
				String base_name = this.db.getUnique_name()+"_"+dto.getUnique_name();
				if(this.dto.getFilling()!=null)
					if(this.dto.getFilling().containsKey(base_name))
					{
						  String val = (String)this.dto.getFilling().get(base_name);
					      if (val != null)
					      {
					    	  concept_value.setText(val);
					      }
					}
				break;
			}
		}
	

	}
}
