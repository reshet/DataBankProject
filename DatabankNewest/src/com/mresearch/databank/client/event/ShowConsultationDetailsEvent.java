package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.mresearch.databank.client.DatabankApp;

public class ShowConsultationDetailsEvent extends GwtEvent<ShowConsultationDetailsEventHandler> {
	public static Type<ShowConsultationDetailsEventHandler> TYPE = new Type<ShowConsultationDetailsEventHandler>();
	private long Consultation_id;  
	public ShowConsultationDetailsEvent(long  res_id) {
		this.Consultation_id = res_id;
//		DatabankApp.get().getCurrentUser().setCurrent_Consultation(Consultation_id);
//		DatabankApp.get().updateUserAccountState();
	}
	@Override
	public Type<ShowConsultationDetailsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowConsultationDetailsEventHandler handler) {
		handler.onShowConsultationDetails(this);
	}

	public long getConsultation_id() {
		return Consultation_id;
	}

}
