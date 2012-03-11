package com.mresearch.databank.client.views;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SaveHTMLAddon extends VerticalPanel{
	private final HTMLPanel main_panel;
	private TextBox content = new TextBox();
	private Button save_btn = new Button("Сохранить как HTML");
	private FormPanel form;
	public SaveHTMLAddon(HTMLPanel content_panel)
	{
		super();
		this.main_panel = content_panel;
		content.setVisible(false);
		content.setName("content");
		form = new FormPanel();
		form.setAction("/databank/htmlSave");
		form.setMethod(FormPanel.METHOD_POST);
		VerticalPanel panel = new VerticalPanel();
		panel.add(content);
		panel.add(save_btn);
		form.add(panel);
		
		this.add(form);
		
		save_btn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String s = SaveHTMLAddon.this.main_panel.toString();
				content.setText(s);
				form.submit();
			}
		});
	}
}
