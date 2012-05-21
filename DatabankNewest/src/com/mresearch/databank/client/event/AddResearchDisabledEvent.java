package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddResearchDisabledEvent extends GwtEvent<AddResearchDisabledEventHandler> {
	public static Type<AddResearchDisabledEventHandler> TYPE = new Type<AddResearchDisabledEventHandler>();
	  
	@Override
	public Type<AddResearchDisabledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddResearchDisabledEventHandler handler) {
		handler.onAddResearchDisabled(this);
	}

}
