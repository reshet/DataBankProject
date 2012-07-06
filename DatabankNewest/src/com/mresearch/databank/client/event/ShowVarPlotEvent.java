package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.mresearch.databank.client.DatabankApp;

public class ShowVarPlotEvent extends GwtEvent<ShowVarPlotEventHandler> {
	public static Type<ShowVarPlotEventHandler> TYPE = new Type<ShowVarPlotEventHandler>();
	private Long Var_id;  
	public ShowVarPlotEvent(long res_id) {
		this.Var_id = res_id;
	//	DatabankApp.get().getCurrentUser().setCurrant_var(Var_id);
	}
	@Override
	public Type<ShowVarPlotEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowVarPlotEventHandler handler) {
		handler.onShowVarPlot(this);
	}

	public long getVar_id() {
		return Var_id;
	}

}
