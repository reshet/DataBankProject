package com.mresearch.databank.client.event;

import java.util.ArrayList;

import com.google.gwt.event.shared.GwtEvent;
import com.mresearch.databank.client.DatabankApp;

public class ShowConsultationIndexEvent extends GwtEvent<ShowConsultationIndexEventHandler> {
	public static Type<ShowConsultationIndexEventHandler> TYPE = new Type<ShowConsultationIndexEventHandler>();
	private long topic_id;  
	private String path;
	private ArrayList<Long> elems;
	public ShowConsultationIndexEvent(long  topic_id,String path,ArrayList<Long> elements) {
		this.topic_id = topic_id;
		this.setPath(path);
		this.setElems(elements);
//		DatabankApp.get().getCurrentUser().setCurrent_Consultation(Consultation_id);
//		DatabankApp.get().updateUserAccountState();
	}
	@Override
	public Type<ShowConsultationIndexEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowConsultationIndexEventHandler handler) {
		handler.onShowConsultationIndex(this);
	}

	public long getConsultation_id() {
		return topic_id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public ArrayList<Long> getElems() {
		return elems;
	}
	public void setElems(ArrayList<Long> elems) {
		this.elems = elems;
	}

}
