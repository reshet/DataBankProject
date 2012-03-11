package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.mresearch.databank.client.DatabankApp;

public class ShowResearchDetailsEvent extends GwtEvent<ShowResearchDetailsEventHandler> {
	public static Type<ShowResearchDetailsEventHandler> TYPE = new Type<ShowResearchDetailsEventHandler>();
	private long research_id;  
	public ShowResearchDetailsEvent(long  res_id) {
		this.research_id = res_id;
		DatabankApp.get().getCurrentUser().setCurrent_research(research_id);
		DatabankApp.get().getCurrentUser().setCurrant_var(0);
		DatabankApp.get().updateUserAccountState();
	}
	@Override
	public Type<ShowResearchDetailsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowResearchDetailsEventHandler handler) {
		handler.onShowResearchDetails(this);
	}

	public long getResearch_id() {
		return research_id;
	}

}
