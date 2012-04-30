package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.mresearch.databank.client.DatabankApp;

public class SaveHTMLEvent extends GwtEvent<SaveHTMLEventHandler> {
	public static Type<SaveHTMLEventHandler> TYPE = new Type<SaveHTMLEventHandler>();
	//private Long Var_id;  
	public SaveHTMLEvent() {
		//this.Var_id = res_id;
	//	DatabankApp.get().getCurrentUser().setCurrant_var(Var_id);
	//	DatabankApp.get().updateUserAccountState();
	}
	@Override
	public Type<SaveHTMLEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SaveHTMLEventHandler handler) {
		handler.onSaveHTML(this);
	}

//	public long getVar_id() {
//		return Var_id;
//	}

}
