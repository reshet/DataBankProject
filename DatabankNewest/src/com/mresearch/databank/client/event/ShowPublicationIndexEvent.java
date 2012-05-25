package com.mresearch.databank.client.event;

import java.util.ArrayList;

import com.google.gwt.event.shared.GwtEvent;
import com.mresearch.databank.client.DatabankApp;

public class ShowPublicationIndexEvent extends GwtEvent<ShowPublicationIndexEventHandler> {
	public static Type<ShowPublicationIndexEventHandler> TYPE = new Type<ShowPublicationIndexEventHandler>();
	private long topic_id;  
	private String path;
	private ArrayList<Long> elems;
	public ShowPublicationIndexEvent(long  topic_id,String path,ArrayList<Long> elements) {
		this.topic_id = topic_id;
		this.setPath(path);
		this.setElems(elements);
//		DatabankApp.get().getCurrentUser().setCurrent_Publication(Publication_id);
//		DatabankApp.get().updateUserAccountState();
	}
	@Override
	public Type<ShowPublicationIndexEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowPublicationIndexEventHandler handler) {
		handler.onShowPublicationIndex(this);
	}

	public long getPublication_id() {
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
