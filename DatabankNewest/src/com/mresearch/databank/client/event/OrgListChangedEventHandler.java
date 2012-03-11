package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface OrgListChangedEventHandler extends EventHandler {
	void onOrgListChanged(OrgListChangedEvent event);
}
