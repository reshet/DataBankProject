package com.mresearch.databank.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.UserAccountService;
import com.mresearch.databank.client.service.UserAccountServiceAsync;

public class LoginView extends Composite {

	private static LoginViewUiBinder uiBinder = GWT
			.create(LoginViewUiBinder.class);

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}
	@UiField TextBox login_box;
	@UiField PasswordTextBox pswd_box;
	@UiField HTMLPanel panel;
	private DatabankApp main;
	private PopupPanel parent;
	private UserAccountServiceAsync rpcUserService = GWT.create(UserAccountService.class);
	public LoginView(DatabankApp main, PopupPanel parent) {
		initWidget(uiBinder.createAndBindUi(this));
		this.main = main;
		this.parent = parent;
		login_box.setFocus(true);
		pswd_box.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) doLogin(null);
			}
		});
		
	}
	@UiHandler(value="enterBtn")
	public void doLogin(ClickEvent e)
	{	
		parent.hide();
		new RPCCall<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Eror on logout");
			}

			@Override
			public void onSuccess(Void result) {
				main.login(login_box.getText(), pswd_box.getText());
			}

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				rpcUserService.logout(cb);
			}}.retry(2);
	}
}
