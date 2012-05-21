package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.shared.UserAccountDTO;

public class ShowResearchDetailsEvent extends GwtEvent<ShowResearchDetailsEventHandler> {
	public static Type<ShowResearchDetailsEventHandler> TYPE = new Type<ShowResearchDetailsEventHandler>();
	private long research_id;  
	public ShowResearchDetailsEvent(long  res_id) {
		this.research_id = res_id;
		DatabankApp.get().getCurrentUser().setCurrent_research(research_id);
		DatabankApp.get().getCurrentUser().setCurrant_var(0);
		new RPCCall<UserAccountDTO>() {
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on updating account state!");
			}

			@Override
			public void onSuccess(UserAccountDTO result) {
				DatabankApp.get().setCurrentUser(result);
			}

			@Override
			protected void callService(AsyncCallback<UserAccountDTO> cb) {
				DatabankApp.get().getUserService().updateResearchState(DatabankApp.get().getCurrentUser(),cb);
			}
		}.retry(2);
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
