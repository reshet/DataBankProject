package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.shared.UserAccountDTO;

public class ShowVarDetailsEvent extends GwtEvent<ShowVarDetailsEventHandler> {
	public static Type<ShowVarDetailsEventHandler> TYPE = new Type<ShowVarDetailsEventHandler>();
	private Long Var_id;  
	public ShowVarDetailsEvent(long res_id) {
		this.Var_id = res_id;
		DatabankApp.get().getCurrentUser().setCurrant_var(Var_id);
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
	public Type<ShowVarDetailsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowVarDetailsEventHandler handler) {
		handler.onShowVarDetails(this);
	}

	public long getVar_id() {
		return Var_id;
	}

}
