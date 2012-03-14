package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowStartPageMainEvent extends GwtEvent<ShowStartPageMainEventHandler> {
	public static Type<ShowStartPageMainEventHandler> TYPE = new Type<ShowStartPageMainEventHandler>();
	  
	@Override
	public Type<ShowStartPageMainEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowStartPageMainEventHandler handler) {
		handler.onShowStartPageMain(this);
	}

}
