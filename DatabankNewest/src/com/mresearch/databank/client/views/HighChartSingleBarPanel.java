package com.mresearch.databank.client.views;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HighChartSingleBarPanel extends VerticalPanel{
	private String url;
	public HighChartSingleBarPanel(String url_with_params)
	{
		super();
		this.url = url_with_params;
		this.add(new HTML(getTestHTML2()));
		//chartDO();
	}
	private String getHeader()
	{
		String head = "";
		return head;
	}
	private String getTail()
	{
		String tail = "";
		return tail;
	}
	public static native void chartDO()/*-{
    	var chart;
						$(document).ready(function() {
						   chart = new Highcharts.Chart({
						      chart: {
						         renderTo: 'container_stacked_bar',
						         defaultSeriesType: 'bar'
						      },
						      title: {
						         text: 'Stacked bar chart'
						      },
						      xAxis: {
						         categories: ['Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas']
						      },
						      yAxis: {
						         min: 0,
						         title: {
						            text: 'Total fruit consumption'
						         }
						      },
						      legend: {
						         backgroundColor: Highcharts.theme.legendBackgroundColorSolid || '#FFFFFF',
						         reversed: true
						      },
						      tooltip: {
						         formatter: function() {
						            return ''+
						                this.series.name +': '+ this.y +'';
						         }
						      },
						      plotOptions: {
						         series: {
						            stacking: 'normal'
						         }
						      },
						           series: [{
						         name: 'John',
						         data: [5, 3, 4, 7, 2]
						      }, {
						         name: 'Jane',
						         data: [2, 2, 3, 2, 1]
						      }, {
						         name: 'Joe',
						         data: [3, 4, 4, 2, 5]
						      }]
						   });
						   
						   
						});
  	}-*/;
	
	private String getTestHTML()
	{
		String test = "";
		test = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">" +
				"<html>"+
						"<head>"+
						"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"+
					"<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js\" type=\"text/javascript\"></script>"+
					"<script src=\"https://ajax.googleapis.com/ajax/libs/mootools/1.3.0/mootools-yui-compressed.js\" type=\"text/javascript\"></script>"+
					"<script src=\"highcharts/js/adapters/mootools-adapter.js\" type=\"text/javascript\"></script>"+
					"<script src=\"highcharts/js/highcharts.js\" type=\"text/javascript\"></script>"+
					"<script type=\"text/javascript\" src=\"highcharts/js/themes/gray.js\"></script>";
		test+="<script type=\"text/javascript\">var chart;$(document).ready(function() {chart = new Highcharts.Chart({chart: { renderTo: 'container_stacked_bar', defaultSeriesType: 'bar'"+
						      "},title: {text: 'Stacked bar chart'},xAxis: {categories: ['Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas']},yAxis: {min: 0,title: {text: 'Total fruit consumption'}},"+
						      "legend: {backgroundColor: Highcharts.theme.legendBackgroundColorSolid || '#FFFFFF',reversed: true},tooltip: {formatter: function() {return ''+this.series.name +': '+ this.y +'';"+
						      "}},plotOptions: {series: {stacking: 'normal'}},series: [{name: 'John',data: [5, 3, 4, 7, 2]}, {name: 'Jane',data: [2, 2, 3, 2, 1]}, {name: 'Joe',data: [3, 4, 4, 2, 5]}]});});</script>";
		test+="</head><body><H1 align=\"center\">SARS Stacked Bar Chart</H1><div id=\"container_stacked_bar\" style=\"width: 100%; height: 600px\"></div></body></html>";				
		return test;
	}
	private String getTestHTML2()
	{
		String test = "";
		test+="<H1 align=\"center\">SARS Stacked Bar Chart</H1><div id=\"container_stacked_bar\" style=\"width: 100%; height: 300px\"></div>";				
		return test;
	}
}
