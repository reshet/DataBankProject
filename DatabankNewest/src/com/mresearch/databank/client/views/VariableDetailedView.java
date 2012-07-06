package com.mresearch.databank.client.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Legend.Align;
import org.moxieapps.gwt.highcharts.client.Point;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.Style;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.labels.PieDataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.PiePlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.event.ShowVarPlotEvent;
import com.mresearch.databank.client.event.ShowVarPlotEventHandler;
import com.mresearch.databank.client.presenters.UserResearchPerspectivePresenter;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.SocioResearchDTO_Light;
import com.mresearch.databank.shared.UserAccountDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;
import com.smartgwt.client.types.ChartType;

public class VariableDetailedView extends Composite {

	private static VariableDetailedViewUiBinder uiBinder = GWT
			.create(VariableDetailedViewUiBinder.class);

	interface VariableDetailedViewUiBinder extends
			UiBinder<Widget, VariableDetailedView> {
	}

	@UiField Label varText;
	@UiField FlexTable codeSchemeTbl,generalizedTbl;
	@UiField HTMLPanel main_html;
//	@UiField Button save_html_btn;
	//@UiField HorizontalPanel save_pnl;
	//@UiField VerticalPanel graph_pnl;
	//@UiField VerticalPanel elasticDBfields;
	@UiField Label concept_name,concept_value;
	@UiField HorizontalPanel analysis_bar;
	@UiField VerticalPanel research_link;
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
	
	
	private native JavaScriptObject getJSON()/*-{
		return eval(
		{
			"title":{"text":"ПРодажі Васі", "style":"font-size: 14px; font-family: Verdana; text-align: center;"}, 
			"legend":{"visible":true, "bg_colour":"#fefefe", "position":"right", "border":true, "shadow":true},
			"bg_colour":"#ffffff", 
			"elements":
				[
					{"type":"pie",
					 "tip":"#label# $#val#<br>#percent#", 
					"values":[
						{"value":1000, "label":"AU", "text":"Австралія"},
						{"value":84000, "label":"USA", "text":"USA"},
						{"value":37000, "label":"UK", "text":"United Kingdom"},
						{"value":9000, "label":"JP", "text":"Japan"},
						{"value":32000, "label":"EU", "text":"Europe"}],
					"radius":130, 
					"highlight":"alpha", 
					"animate":true, 
					"gradient-fill":true, 
					"alpha":0.5, 
					"no-labels":true, 
					"colours":["#ff0000","#00aa00","#0000ff","#ff9900","#ff00ff"]
					}
				]
			}
	)
 }-*/;
	
	
	
	
	
	
	
	private MetaUnitMultivaluedEntityDTO db;
	private VarDTO_Detailed dto;
	public VariableDetailedView(VarDTO_Detailed dto,MetaUnitMultivaluedEntityDTO dt,SimpleEventBus bus,UserResearchPerspectivePresenter.Display display)
	{
		initWidget(uiBinder.createAndBindUi(this));
		this.db = dt;
		this.dto = dto;
		
		
		research_link.add(new ResearchDescItemView(new SocioResearchDTO_Light(dto.getResearch_id(),dto.getResearch_name())));
		UserAccountDTO user = DatabankApp.get().getCurrentUser();
		analysis_bar.add(new AnalisysBarView(bus, display,user.getFilters_use()>0?true:false,user.getWeights_use()>0?true:false,(int)dto.getId()));
		//form.a
		
		
		
		
		
		//save_pnl.add(new SaveHTMLAddon(main_html));
		
		//varCode.setText(dto.getCode());
		varText.setWidth("400px");
		varText.setWordWrap(true);
		varText.setText(dto.getLabel());
		ArrayList<Double> codes = dto.getV_label_codes();
		ArrayList<String> values = dto.getV_label_values();
		
		int i = 0;
		codeSchemeTbl.setSize("450px", "350px");
		codeSchemeTbl.setWidget(0, 0, new Label("Текст альтернативы"));
		codeSchemeTbl.setWidget(0, 1, new Label("Проценты"));
		//codeSchemeTbl.insertCell(beforeRow, beforeColumn)
		Double total = new Double(0);
		
		for(Double cnt:dto.getDistribution())
		{
			total+=cnt;
		}
        for(Double key:codes)
        {
 		   String label = values.get(i);
          // codeSchemeTbl.setWidget(i+1, 0, new Label(key.toString()));
           codeSchemeTbl.setWidget(i+1, 0, new Label(label.toString()));
           
           //NumberFormat formatter = NumberFormat.getInstance();
           NumberFormat formatter = NumberFormat.getFormat("0.0");
          // formatter.
           //formatter.setMaximumFractionDigits(2);
           String myNumber = formatter.format(new Double(dto.getDistribution().get(i)/total)*100);
           
          // new NumberFormat();
           codeSchemeTbl.setWidget(i+1, 1, new Label(myNumber+"%"));
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
		
		
		//ChartFactory f = new ChartFactory();
//		ChartWidget widg = new ChartWidget();
//		//widg.s
//		//JavaScriptObject obj = getJSON();
//		//JSONObject obj_json = new JSONObject(obj);
//		JSONObject obj_json = new JSON_Construct(dto).getGraph();
//		//obj.toString();
//		String json = obj_json.toString();
//		widg.setJsonData(json);
//		
//		
		//graph_pnl.add(widg);
		
		renderDBfillers();
		
		bus.addHandler(ShowVarPlotEvent.TYPE, new ShowVarPlotEventHandler() {
			@Override
			public void onShowVarPlot(ShowVarPlotEvent event) {
				if(VariableDetailedView.this.dto.getId() == event.getVar_id())showPlot();
			}
		});
	}
	
	private void renderDBfillers()
	{
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
		//elasticDBfields.clear();
		//VarMultiValuedFieldViewer mv = new VarMultiValuedFieldViewer(db,dto.getFilling(),"");
		//elasticDBfields.add(mv);
	}
	private boolean plot_viewed = false;
	public static String wrap(String in,int len) {
		in=in.trim();
		if(in.length()<len) return in;
		if(in.substring(0, len).contains("<br/>"))
		return in.substring(0, in.indexOf("<br/>")).trim() + "\n\n" + wrap(in.substring(in.indexOf("<br/>") + 1), len);
		int place=Math.max(Math.max(in.lastIndexOf(" ",len),in.lastIndexOf("\t",len)),in.lastIndexOf("-",len));
		return in.substring(0,place).trim()+"<br/>"+wrap(in.substring(place),len);
		}
	private void showPlot()
	{
		if(!plot_viewed)
		{
			plot_viewed = true;
			
//			ChartWidget widg = new ChartWidget();
//			JSONObject obj_json = new JSON_Construct(dto).getGraph();
//			String json = obj_json.toString();
//			widg.setJsonData(json);
//			widg.setPixelSize(600, 600);
//			
			
			//StringUtils.wordWrap(dto.getLabel(),50,Locale.getDefault());
			double dum = 0;
			for(Double d:dto.getDistribution())dum+=d;
			Chart chart = new Chart()
			   .setType(Series.Type.PIE)
//			   .setChartTitleText(wrap(dto.getLabel(),70))
	
			   .setChartTitle(new ChartTitle().setText(wrap(dto.getLabel(),70)).setAlign(org.moxieapps.gwt.highcharts.client.ChartTitle.Align.LEFT))
			   .setSizeToMatchContainer()
			   .setMarginRight(10)
//			   .setWidth100()
//			   .setHeight100()
////			    .setLegend(new Legend()
//				      .setAlign(Legend.Align.RIGHT)
//				      .setVerticalAlign(Legend.Align.)
//				      .setBackgroundColor("#CCCCCC")
//				      .setShadow(true)
//				   )
//				
				   .setPlotBorderWidth(null)  
            .setPlotShadow(true)  
            .setPiePlotOptions(new PiePlotOptions()  
                .setAllowPointSelect(true)  
                .setCursor(PlotOptions.Cursor.POINTER)  
                .setPieDataLabels(new PieDataLabels()  
                    .setEnabled(false)  
                )  
                .setShowInLegend(true)  
            )  
            .setToolTip(new ToolTip()  
                .setFormatter(new ToolTipFormatter() {  
                    public String format(ToolTipData toolTipData) {  
                        return "<b>" + toolTipData.getPointName() + "</b>: " + toolTipData.getYAsDouble() + " %";  
                    }  
                })  
            )   
				   ;
			
			Point [] points = new Point[dto.getV_label_codes().size()];
			int i = 0;
			for(String name:dto.getV_label_values())
			{
				  NumberFormat formatter = NumberFormat.getFormat("0.0");
		          // formatter.
		           //formatter.setMaximumFractionDigits(2);
		           double myNumber = Double.parseDouble(formatter.format(new Double(dto.getDistribution().get(i)/dum)*100));
		         
		           
				points[i] = (new Point(name,myNumber));
				i++;
			}
			
			points[0].setSelected(true).setSliced(true);
//			new Point[]{  
//	                new Point("Firefox", 45.0),  
//	                new Point("IE", 26.8),  
//	                new Point("Chrome", 12.8)  
//	                    .setSliced(true)  
//	                    .setSelected(true),  
//	                new Point("Safari", 8.5),  
//	                new Point("Opera", 6.2),  
//	                new Point("Others", 0.7)  
//	            } 
//		   )
			Series series = chart.createSeries()
					   .setName("Варианты ответа")
					   .setPoints(points);
					chart.addSeries(series);
			
			final PopupPanel dialogBox = createDialogBox(chart);
		    dialogBox.setGlassEnabled(true);
		    dialogBox.setAnimationEnabled(true);
		    
			dialogBox.center();
	        dialogBox.show();
		}

	}
	
	private PopupPanel createDialogBox(Widget w) {
	    // Create a dialog box and set the caption text
	    final PopupPanel dialogBox = new PopupPanel();
	    //dialogBox.setText("График распределения");
	    dialogBox.setHeight("450px");
	    dialogBox.setWidth("700px");
//	    dialogBox.setHeight("100%");
//	    dialogBox.setWidth("100%");
	    // Create a table to layout the content
	    VerticalPanel dialogContents = new VerticalPanel();
	    dialogContents.setWidth("100%");
	    dialogContents.setHeight("100%");
	    
	    dialogContents.setSpacing(4);
	    
	 
	    // Add some text to the top of the dialog
	   

	    // Add a close button at the bottom of the dialog
	    Button closeButton = new Button("Закрыть", new ClickHandler() {
	          public void onClick(ClickEvent event) {
	            dialogBox.hide();
	            plot_viewed = false;
	          }
	        });
	    dialogContents.add(w);
	    dialogContents.add(closeButton);
	    dialogBox.setWidget(dialogContents);
	    return dialogBox;
	    
	    
	  }
}
