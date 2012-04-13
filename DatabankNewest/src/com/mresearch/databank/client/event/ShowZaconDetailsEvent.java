package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.mresearch.databank.client.DatabankApp;

public class ShowZaconDetailsEvent extends GwtEvent<ShowZaconDetailsEventHandler> {
	public static Type<ShowZaconDetailsEventHandler> TYPE = new Type<ShowZaconDetailsEventHandler>();
	private long Zacon_id;  
	public ShowZaconDetailsEvent(long  res_id) {
		this.Zacon_id = res_id;
//		DatabankApp.get().getCurrentUser().setCurrent_Zacon(Zacon_id);
//		DatabankApp.get().updateUserAccountState();
	}
	@Override
	public Type<ShowZaconDetailsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowZaconDetailsEventHandler handler) {
		handler.onShowZaconDetails(this);
	}

	public long getZacon_id() {
		return Zacon_id;
	}

}
