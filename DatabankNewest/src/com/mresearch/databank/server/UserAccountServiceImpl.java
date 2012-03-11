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


import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.catalina.session.PersistentManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mplatforma.amr.service.remote.UserAccountBeanRemote;
import com.mresearch.databank.client.service.UserAccountService;
import com.mresearch.databank.shared.UserAccountDTO;

@SuppressWarnings("serial")
public class UserAccountServiceImpl extends RemoteServiceServlet implements
    UserAccountService {

	
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
  public UserAccountDTO login(String email, String password) {
	UserAccountDTO userAcc =   (UserAccountDTO) this.getThreadLocalRequest().getSession().getAttribute("user");
	if (userAcc != null)
	{
		return userAcc;
	}
	
	UserAccountDTO account;
	account = eao.getUserAccount(email, password);
	if (account == null)
	{
		UserAccountDTO acc = eao.getDefaultUser();
		this.getThreadLocalRequest().getSession().setAttribute("user", acc);
		return acc;
	}
	this.getThreadLocalRequest().getSession().setAttribute("user", account);
	return account;

// return UserAccount.toDTO(UserAccount.getDefaultUser());
//	  return UserAccount.toDTO(UserAccount.getDefaultResearchAdmin());	  
//	  return UserAccount.toDTO(UserAccount.getDefaultLawAdmin());	  
//	  return UserAccount.toDTO(UserAccount.getDefaultJuryConsultant());	  
//	  return UserAccount.toDTO(UserAccount.getDefaultSuperAdmin());	  

  }
  
  
  public void initDefaultUsers()
  {
	  eao.initDefaults();
  }

@Override
public void logout() {
	this.getThreadLocalRequest().getSession().removeAttribute("user");
}

@Override
public UserAccountDTO updateResearchState(UserAccountDTO dto_acc) {
	UserAccountDTO userAcc =   (UserAccountDTO) this.getThreadLocalRequest().getSession().getAttribute("user");
	if (userAcc != null)
	{
		if (userAcc.getId() != 0)
		{
			UserAccountDTO account,dto;
			dto = eao.updateAccountResearchState(dto_acc);	
			this.getThreadLocalRequest().getSession().setAttribute("user", dto);
			return dto;
		}
		else
		{
			this.getThreadLocalRequest().getSession().setAttribute("user", dto_acc);
			return dto_acc;
		}
	}
	return dto_acc;
}

} // end class
