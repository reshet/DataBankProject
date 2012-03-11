package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddArticleDisabledEvent extends GwtEvent<AddArticleDisabledEventHandler> {
	public static Type<AddArticleDisabledEventHandler> TYPE = new Type<AddArticleDisabledEventHandler>();
	  
	@Override
	public Type<AddArticleDisabledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddArticleDisabledEventHandler handler) {
		handler.onAddArticleDisabled(this);
	}

}
