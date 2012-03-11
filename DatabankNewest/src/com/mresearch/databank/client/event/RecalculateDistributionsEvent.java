package com.mresearch.databank.client.event;

import java.util.ArrayList;

import com.google.gwt.event.shared.GwtEvent;
import com.mresearch.databank.shared.CatalogConceptDTO;

public class RecalculateDistributionsEvent extends GwtEvent<RecalculateDistributionsEventHandler> {
	public static Type<RecalculateDistributionsEventHandler> TYPE = new Type<RecalculateDistributionsEventHandler>();
	private Integer weights_use,filters_use;
	private ArrayList<String> filters;
	public RecalculateDistributionsEvent(Integer weights_use,Integer filters_use,ArrayList<String> filters)
	{
		this.weights_use = weights_use;
		this.filters_use = filters_use;
		this.filters = filters;
	}
	@Override
	public Type<RecalculateDistributionsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RecalculateDistributionsEventHandler handler) {
		handler.onRecalculateDistributions(this);
	}

	public Integer getWeights_use() {
		return weights_use;
	}
	public void setWeights_use(Integer weights_use) {
		this.weights_use = weights_use;
	}
	public Integer getFilters_use() {
		return filters_use;
	}
	public void setFilters_use(Integer filters_use) {
		this.filters_use = filters_use;
	}
	public ArrayList<String> getFilters() {
		return filters;
	}
	public void setFilters(ArrayList<String> filters) {
		this.filters = filters;
	}
	
}
