package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddArticleEnabledEvent extends GwtEvent<AddArticleEnabledEventHandler> {
	public static Type<AddArticleEnabledEventHandler> TYPE = new Type<AddArticleEnabledEventHandler>();
	  
	@Override
	public Type<AddArticleEnabledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddArticleEnabledEventHandler handler) {
		handler.onAddArticleEnabled(this);
	}

}
