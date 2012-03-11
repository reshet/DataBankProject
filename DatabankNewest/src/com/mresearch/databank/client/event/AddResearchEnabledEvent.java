package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddResearchEnabledEvent extends GwtEvent<AddResearchEnabledEventHandler> {
	public static Type<AddResearchEnabledEventHandler> TYPE = new Type<AddResearchEnabledEventHandler>();
	  
	@Override
	public Type<AddResearchEnabledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddResearchEnabledEventHandler handler) {
		handler.onAddResearchEnabled(this);
	}

}
