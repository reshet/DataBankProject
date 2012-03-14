package com.mresearch.databank.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TestDock extends Composite {

	private static TestDockUiBinder uiBinder = GWT
			.create(TestDockUiBinder.class);

	interface TestDockUiBinder extends UiBinder<Widget, TestDock> {
	}

	public TestDock() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
