package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.opendatafoundation.data.spss.mod.SPSSUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.RealVarDTO_Detailed;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;

public class RealVariableDetailedView extends Composite {

	private static RealVariableDetailedViewUiBinder uiBinder = GWT
			.create(RealVariableDetailedViewUiBinder.class);

	interface RealVariableDetailedViewUiBinder extends
			UiBinder<Widget, RealVariableDetailedView> {
	}

	@UiField Label varCode,varText,number_of_records,moda_txt,median_txt,average_txt,dispersion_txt,dispersion_root_txt;
	@UiField FlexTable generalizedTbl;
	@UiField HorizontalPanel target_panel;
	@UiField HTMLPanel content_panel;
	public RealVariableDetailedView(RealVarDTO_Detailed dto) {
		initWidget(uiBinder.createAndBindUi(this));
		
		target_panel.add(new SaveHTMLAddon(content_panel));
		varCode.setText(dto.getCode());
		varText.setText(dto.getLabel());
		number_of_records.setText(String.valueOf(dto.getNumber_of_records()));
		int i = 0;
		NumberFormat formatter = NumberFormat.getFormat("0.00");
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
	}

}
