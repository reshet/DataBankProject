package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class CreateConceptEnabledEvent extends GwtEvent<CreateConceptEnabledEventHandler> {
	public static Type<CreateConceptEnabledEventHandler> TYPE = new Type<CreateConceptEnabledEventHandler>();
	private boolean isRootConcept;  
	public CreateConceptEnabledEvent(boolean isRoot)
	{
		isRootConcept = isRoot;
	}
	@Override
	public Type<CreateConceptEnabledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreateConceptEnabledEventHandler handler) {
		handler.onCreateConceptEnabled(this);
	}

	public boolean isRootConcept() {
		return isRootConcept;
	}

	public void setRootConcept(boolean isRootConcept) {
		this.isRootConcept = isRootConcept;
	}

}
