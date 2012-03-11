/** 
 * Copyright 2010 Daniel Guermeur and Amy Unruh
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   See http://connectrapp.appspot.com/ for a demo, and links to more information 
 *   about this app and the book that it accompanies.
 */
package com.mresearch.databank.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.js.rhino.ObjToIntMap.Iterator;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.event.ShowStartPageMainEvent;
import com.mresearch.databank.client.presenters.AdminResearchPerspectivePresenter;
import com.mresearch.databank.client.presenters.Presenter;
//import com.mresearch.databank.client.presenters.StartPagePerspectivePresenter;
//import com.mresearch.databank.client.presenters.UserNewsPerspectivePresenter;
//import com.mresearch.databank.client.presenters.UserResearchPerspectivePresenter;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
//import com.mresearch.databank.client.service.StartPageServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.client.views.AdminResearchPerspectiveView;
//import com.mresearch.databank.client.views.StartPagePerspectiveView;
//import com.mresearch.databank.client.views.UserNewsPerspectiveView;
//import com.mresearch.databank.client.views.UserResearchPerspectiveView;

public class ResearchAdminAppController implements ValueChangeHandler<String>, AppController {
  private final SimpleEventBus eventBus;
 // private final StartPageServiceAsync startpageService;
  //private final MessagesServiceAsync messagesService = GWT.create(MessagesService.class);
  private String currentFriendId;
  private Widget mainPanel;
  private static  ResearchAdminAppControllerUiBinder uiBinder = GWT
			.create(ResearchAdminAppControllerUiBinder.class);

	interface  ResearchAdminAppControllerUiBinder extends
			UiBinder<DockLayoutPanel, ResearchAdminAppController> {
	}
	
  @UiField Anchor bankNav,articlesNav,logoutNav;
  @UiField DockLayoutPanel centerPanel;
  private DockLayoutPanel thisDock;
  private final UserSocioResearchServiceAsync researchUserService = GWT.create(UserSocioResearchService.class);
  private final AdminSocioResearchServiceAsync researchAdminService = GWT.create(AdminSocioResearchService.class);
  public ResearchAdminAppController(SimpleEventBus eventBus) {
	//initWidget();
	thisDock = uiBinder.createAndBindUi(this);
    this.eventBus = eventBus;
   // this.startpageService = rpcService;
    bind();
  }

  private void bind() {
    History.addValueChangeHandler(this);
    bankNav.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			//eventBus.fireEvent(new ShowStartPageMainEvent());
			doViewDatabank();
		}
	});
    articlesNav.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			//eventBus.fireEvent(new ShowStartPageMainEvent());
			doViewArticlesCatalog();
		}
	});
    logoutNav.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			DatabankApp.get().setCurrentUser(null);
			DatabankApp.get().logout();
			DatabankApp.get().login("email", "pass");
		}
	});
   
//    eventBus.addHandler(FriendAddEvent.TYPE, new FriendAddEventHandler() {
//      public void onAddFriend(FriendAddEvent event) {
//        doAddNewFriend();
//      }
//    });
//
//    eventBus.addHandler(ShowFriendPopupEvent.TYPE, new ShowFriendPopupEventHandler() {
//      public void onShowFriendPopup(ShowFriendPopupEvent event) {
//        FriendPopupPresenter friendPopupPresenter = new FriendPopupPresenter(friendService, eventBus, new FriendPopupView(event.getFriend()
//            .getDisplayName(), event.getClickPoint()), event.getFriend());
//        friendPopupPresenter.go();
//      }
//    });

   
    
  }

  private void doViewDatabank() {
    History.newItem("researchadmin-databank");
  }
  private void doViewArticlesCatalog() {
	    History.newItem("researchadmin-articles");
  }

  public void go(DockLayoutPanel mainPanel)
  {
	this.mainPanel = mainPanel;
	//mainPanel.setWidth("100%");
	//mainPanel.setHeight("100%");
	
	mainPanel.add(thisDock);
	if ("".equals(History.getToken())) {
      History.newItem("researchadmin-databank");
    } else {
      History.fireCurrentHistoryState();
    }
  }
 
  public void onValueChange(ValueChangeEvent<String> event) {
    String token = event.getValue();

    if (token != null) {
      Presenter presenter = null;
      //presenter.go();
      if(token.equals("researchadmin-databank")){
    	  presenter = new AdminResearchPerspectivePresenter(researchUserService, researchAdminService, eventBus, new AdminResearchPerspectiveView(eventBus));
          presenter.go(centerPanel,null,null);
      }
    }

  }
}
