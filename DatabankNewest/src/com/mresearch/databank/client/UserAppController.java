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

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.js.rhino.ObjToIntMap.Iterator;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.event.ShowStartPageMainEvent;
import com.mresearch.databank.client.presenters.Presenter;
import com.mresearch.databank.client.presenters.StartPagePerspectivePresenter;
//import com.mresearch.databank.client.presenters.UserLawPerspectivePresenter;
//import com.mresearch.databank.client.presenters.UserNewsPerspectivePresenter;
import com.mresearch.databank.client.presenters.UserResearchPerspectivePresenter;
//import com.mresearch.databank.client.presenters.UserSearchPerspectivePresenter;
//import com.mresearch.databank.client.service.AdminArticleService;
//import com.mresearch.databank.client.service.AdminArticleServiceAsync;
import com.mresearch.databank.client.service.SearchService;
import com.mresearch.databank.client.service.SearchServiceAsync;
import com.mresearch.databank.client.service.StartPageServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.client.views.StartPagePerspectiveView;
//import com.mresearch.databank.client.views.UserLawPerspectiveView;
//import com.mresearch.databank.client.views.UserNewsPerspectiveView;
import com.mresearch.databank.client.views.UserResearchPerspectiveView;
//import com.mresearch.databank.client.views.UserSearchPerspectiveView;

public class UserAppController implements ValueChangeHandler<String>, AppController {
  private final SimpleEventBus eventBus;
  private final StartPageServiceAsync startpageService;
  private final SearchServiceAsync searchService = GWT.create(SearchService.class);
  //private final MessagesServiceAsync messagesService = GWT.create(MessagesService.class);
  private String currentFriendId;
  private Widget mainPanel;
  private static UserAppControllerUiBinder uiBinder = GWT
			.create(UserAppControllerUiBinder.class);

	interface UserAppControllerUiBinder extends
			UiBinder<DockLayoutPanel, UserAppController> {
	}
	
  @UiField Anchor mainNav,newsNav,researchNav,lawNav,juryNav,loginNav;
  @UiField DockLayoutPanel centerPanel;
  @UiField TextBox searchBox;
  @UiField Button searchCmd;
  private DockLayoutPanel thisDock;
  private final UserSocioResearchServiceAsync researchService = GWT.create(UserSocioResearchService.class);
  
  //private final AdminArticleServiceAsync articleService = GWT.create(AdminArticleService.class);
  
  public UserAppController(StartPageServiceAsync rpcService, SimpleEventBus eventBus) {
	//initWidget();
	thisDock = uiBinder.createAndBindUi(this);
    this.eventBus = eventBus;
    this.startpageService = rpcService;
    bind();
  }

  private void bind() {
    History.addValueChangeHandler(this);
    mainNav.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			//eventBus.fireEvent(new ShowStartPageMainEvent());
			doViewStartPageMain();
		}
	});
    newsNav.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			//eventBus.fireEvent(new ShowStartPageMainEvent());
			doViewStartPageNews();
		}
	});
    researchNav.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			//eventBus.fireEvent(new ShowStartPageMainEvent());
			doViewUserResearch();
		}
	});
    lawNav.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			doViewUserLaw();
		}
	});
   searchBox.addKeyDownHandler(new KeyDownHandler() {
	@Override
	public void onKeyDown(KeyDownEvent event) {
		// TODO Auto-generated method stub
		int code = event.getNativeKeyCode();
		if (code == 13)
		{
			doViewSearchResults(searchBox.getText());
		}
	}
   });
    searchCmd.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			doViewSearchResults(searchBox.getText());
		}
	});
    loginNav.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			PopupPanel popup = new PopupPanel(true);
			LoginView l_view = new LoginView(DatabankApp.get(), popup);
			popup.add(l_view);
			popup.setPopupPosition(500, 100);
			popup.show();
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

  private void doViewStartPageMain() {
    History.newItem("user-main");
  }
  private void doViewStartPageNews() {
	    History.newItem("user-news");
  }
  private void doViewUserResearch() {
	    History.newItem("user-research");
 }
  private void doViewUserLaw() {
	    History.newItem("user-law");
}  private void doViewSearchResults(String searchstr)
  {
	  if (searchstr != null && !searchstr.equals(""))
		  History.newItem("search-results@query="+searchstr);
  }

  public void go(DockLayoutPanel mainPanel)
  {
	this.mainPanel = mainPanel;
	//mainPanel.setWidth("100%");
	//mainPanel.setHeight("100%");
	
	mainPanel.add(thisDock);
	if ("".equals(History.getToken())) {
      History.newItem("user-main");
    } else {
      History.fireCurrentHistoryState();
    }
  }
  private void clearMainPanel()
  {
//	  int w_count = thisDock.getWidgetCount();
//	  for(int i = 0; i < w_count;i++)
//	  {
//		  if(thisDock.getWidget(i) instanceof DockLayoutPanel)
//		  {
//			  thisDock.remove(i);
//			  break;
//		  }
//	  }
//	  if (w_count>1) thisDock.remove(2);
//	  while(thisDock.iterator().)
//      {
//    	  if(thisDock.iterator().next() instanceof DockLayoutPanel)
//    	  {
//    		  thisDock.iterator().remove();
//    		  break;
//    	  }
//      }
  }
  private void parsePathToken(String token,ArrayList<String> p_names,ArrayList<String> p_values)
  {
	  if (!token.contains("@")) return;
	  
	  String [] ar = token.split("@");
	  String [] params   = ar[1].split("&");
	  for(int i = 0 ; i < params.length;i++)
	  {
		  String [] key_value = params[i].split("=");
		  p_names.add(key_value[0]);
		  p_values.add(URL.decode(key_value[1]));
	  }
	
  }
  public void onValueChange(ValueChangeEvent<String> event) {
    String token = event.getValue();

    if (token != null) {
      Presenter presenter = null;
      //presenter.go();
      if (token.startsWith("user-main")) {
    	//clearMainPanel();
    	presenter = new StartPagePerspectivePresenter(startpageService, eventBus, new StartPagePerspectiveView());
       //thisDock.add(presenter.getPlace());
    	presenter.go(centerPanel,null,null);
        return;
      } else if(token.equals("user-news")){
//    	  presenter = new UserNewsPerspectivePresenter(startpageService, eventBus, new UserNewsPerspectiveView());
//          presenter.go(centerPanel,null,null);
      } else if(token.startsWith("user-research")){
    	  presenter = new UserResearchPerspectivePresenter(researchService, eventBus, new UserResearchPerspectiveView());
    	  
    	  ArrayList<String> param_names,param_values;
    	  param_names = new ArrayList<String>();
    	  param_values = new ArrayList<String>();
    	  parsePathToken(token, param_names, param_values);
    	  presenter.go(centerPanel,param_names,param_values);
      }else if(token.startsWith("user-law")){
    	  
//    	  presenter = new UserLawPerspectivePresenter(articleService, eventBus, new UserLawPerspectiveView());
//    	  ArrayList<String> param_names,param_values;
//    	  param_names = new ArrayList<String>();
//    	  param_values = new ArrayList<String>();
//    	  parsePathToken(token, param_names, param_values);
//    	  presenter.go(centerPanel,param_names,param_values);
    	  
      }
      else if(token.startsWith("search-results")){
//    	  String [] arr = token.split("=");
//    	  String query = arr[1];
    	  
//    	  
//     	  ArrayList<String> param_names,param_values;
//    	  param_names = new ArrayList<String>();
//    	  param_values = new ArrayList<String>();
//    	  parsePathToken(token, param_names, param_values);
//    	  presenter = new UserSearchPerspectivePresenter(searchService,researchService, eventBus, new UserSearchPerspectiveView());
//          presenter.go(centerPanel,param_names,param_values);
          
      }
    	  
//      else if (token.equals("add")) {
//        presenter = new FriendEditPresenter(friendService, eventBus, new FriendEditView());
//        presenter.go(ConnectrApp.get().getMainPanel());
//        return;
//
//      } 
    }

  }
}
