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
package com.mresearch.databank.server;


import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.catalina.session.PersistentManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mplatforma.amr.service.remote.UserAccountBeanRemote;
import com.mplatforma.amr.service.remote.UserSocioResearchBeanRemote;
import com.mresearch.databank.client.service.StartPageService;
import com.mresearch.databank.client.service.UserAccountService;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.NewsDTO;
import com.mresearch.databank.shared.NewsSummaryDTO;
import com.mresearch.databank.shared.StartupBundleDTO;
import com.mresearch.databank.shared.UserAccountDTO;

@SuppressWarnings("serial")
public class StartPageServiceImpl extends RemoteServiceServlet implements
    StartPageService {

	
	private static UserAccountBeanRemote eao;
	static
	{
		Object obj = new String("some");
		try {
		  InitialContext ic = new InitialContext();
		  System.out.println("start lookup");
		  final String jndiName = "java:global/DatabankEnterprise-ejb/UserAccountRemoteBean";
		  obj = ic.lookup(jndiName);
		  System.out.println("lookup returned " + obj);
		  eao = (UserAccountBeanRemote) obj;
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
		
	
//	private static UserSocioResearchBeanRemote seao;
//	static
//	{
//		Object obj = new String("some");
//		try {
//		  InitialContext ic = new InitialContext();
//		  System.out.println("start lookup");
//		  final String jndiName = "java:global/DatabankEnterprise-ejb/UserSocioResearchRemoteBean";
//		  obj = ic.lookup(jndiName);
//		  System.out.println("lookup returned " + obj);
//		  seao = (UserSocioResearchBeanRemote) obj;
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//	}
	@Override
	public ArrayList<NewsSummaryDTO> getNewsSummaries(int latest) {
		
		return null;
	}
	@Override
	public ArticleDTO getNewsDetailed(String news_id) {
		
		return null;
	}
	@Override
	public ArticleDTO getArticle(String article_id) {
		
		return null;
	}
	@Override
	public ArticleDTO getMainPageArticle() {
		
		return null;
	}
	@Override
	public ArrayList<NewsDTO> getNews(int latest) {
		return null;
	}
	@Override
	public StartupBundleDTO getStartupContent() {
		
		return  eao.getStartupContent();
	}
 

} // end class
