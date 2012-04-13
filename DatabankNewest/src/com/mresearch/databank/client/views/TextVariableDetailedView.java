package com.mresearch.databank.client.views;

import java.util.ArrayList;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.presenters.UserResearchPerspectivePresenter;
import com.mresearch.databank.client.views.DBviewers.MultiValuedFieldViewer;
import com.mresearch.databank.client.views.DBviewers.VarMultiValuedFieldViewer;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.RealVarDTO_Detailed;
import com.mresearch.databank.shared.TextVarDTO_Detailed;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;

public class TextVariableDetailedView extends Composite {

	private static RealVariableDetailedViewUiBinder uiBinder = GWT
			.create(RealVariableDetailedViewUiBinder.class);

	interface RealVariableDetailedViewUiBinder extends
			UiBinder<Widget, TextVariableDetailedView> {
	}

	@UiField Label varCode,varText,number_of_records;
	@UiField FlexTable generalizedTbl,values_table;
	private MetaUnitMultivaluedEntityDTO db;
	private VarDTO_Detailed dto;
	@UiField VerticalPanel elasticDBfields;
	@UiField HorizontalPanel analysis_bar;

	public TextVariableDetailedView(TextVarDTO_Detailed dto,MetaUnitMultivaluedEntityDTO dt,SimpleEventBus bus,UserResearchPerspectivePresenter.Display display) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dto = dto;
		this.db = dt;
		analysis_bar.add(new AnalisysBarView(bus, display));
		
		varCode.setText(dto.getCode());
		varText.setText(dto.getLabel());
		number_of_records.setText(String.valueOf(dto.getNumber_of_records()));
		int i = 0;
        // formatter.
         //formatter.setMaximumFractionDigits(2);
        // String myNumber = formatter.format(new Double(dto.getDistribution().get(i)/total)*100);
		//codeSchemeTbl.insertCell(beforeRow, beforeColumn)
		for(String value:dto.getFiltered_cortage())
		{
			values_table.setWidget(i, 0, new Label(value));
		}
						
        generalizedTbl.setSize("600px", "350px");
		for(int j = 0; j < dto.getGen_var_names().size();j++)
		{
			generalizedTbl.setWidget(j, 0, new Label(" как "));
			generalizedTbl.setWidget(j, 1, new Label(dto.getGen_var_names().get(j)));
			generalizedTbl.setWidget(j, 2, new Label(" в исследовании "));
			generalizedTbl.setWidget(j, 3, new Label(dto.getGen_research_names().get(j)));
		}
		renderDBfillers();
	}
	private void renderDBfillers()
	{
		elasticDBfields.clear();
		VarMultiValuedFieldViewer mv = new VarMultiValuedFieldViewer(db,dto.getFilling(),"");
		elasticDBfields.add(mv);
	}
}
