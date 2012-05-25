package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.mresearch.databank.client.DatabankApp;

public class ShowPublicationDetailsEvent extends GwtEvent<ShowPublicationDetailsEventHandler> {
	public static Type<ShowPublicationDetailsEventHandler> TYPE = new Type<ShowPublicationDetailsEventHandler>();
	private long Publication_id;  
	public ShowPublicationDetailsEvent(long  res_id) {
		this.Publication_id = res_id;
//		DatabankApp.get().getCurrentUser().setCurrent_Publication(Publication_id);
//		DatabankApp.get().updateUserAccountState();
	}
	@Override
	public Type<ShowPublicationDetailsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowPublicationDetailsEventHandler handler) {
		handler.onShowPublicationDetails(this);
	}

	public long getPublication_id() {
		return Publication_id;
	}

}
