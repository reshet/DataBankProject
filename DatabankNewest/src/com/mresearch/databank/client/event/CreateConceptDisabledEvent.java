package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class CreateConceptDisabledEvent extends GwtEvent<CreateConceptDisabledEventHandler> {
	public static Type<CreateConceptDisabledEventHandler> TYPE = new Type<CreateConceptDisabledEventHandler>();
	  
	@Override
	public Type<CreateConceptDisabledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreateConceptDisabledEventHandler handler) {
		handler.onCreateConceptDisabled(this);
	}

}
