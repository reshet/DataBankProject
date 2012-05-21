package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class OrgListChangedEvent extends GwtEvent<OrgListChangedEventHandler> {
	public static Type<OrgListChangedEventHandler> TYPE = new Type<OrgListChangedEventHandler>();
	  
	@Override
	public Type<OrgListChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(OrgListChangedEventHandler handler) {
		handler.onOrgListChanged(this);
	}

}
