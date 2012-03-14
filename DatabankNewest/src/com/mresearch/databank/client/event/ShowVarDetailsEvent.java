package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.mresearch.databank.client.DatabankApp;

public class ShowVarDetailsEvent extends GwtEvent<ShowVarDetailsEventHandler> {
	public static Type<ShowVarDetailsEventHandler> TYPE = new Type<ShowVarDetailsEventHandler>();
	private Long Var_id;  
	public ShowVarDetailsEvent(long res_id) {
		this.Var_id = res_id;
		DatabankApp.get().getCurrentUser().setCurrant_var(Var_id);
		DatabankApp.get().updateUserAccountState();
	}
	@Override
	public Type<ShowVarDetailsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowVarDetailsEventHandler handler) {
		handler.onShowVarDetails(this);
	}

	public long getVar_id() {
		return Var_id;
	}

}
