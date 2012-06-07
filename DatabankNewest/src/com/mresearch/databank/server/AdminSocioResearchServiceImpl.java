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
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.mplatforma.amr.service.remote.AdminSocioResearchBeanRemote;
import com.mplatforma.amr.service.remote.UserSocioResearchBeanRemote;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitDateDTO;
import com.mresearch.databank.shared.MetaUnitDoubleDTO;
import com.mresearch.databank.shared.MetaUnitEntityItemDTO;
import com.mresearch.databank.shared.MetaUnitIntegerDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedStructureDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.ResearchFilesDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.StartupBundleDTO;
import com.mresearch.databank.shared.UserAccountDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;

/**
 * the FriendsService communicates Friend data via RPC between client and server.
 */
@SuppressWarnings("serial")
public class AdminSocioResearchServiceImpl extends RemoteServiceServlet implements
    AdminSocioResearchService {

	
	private static AdminSocioResearchBeanRemote eao;
	static
	{
		Object obj = new String("some");
		try {
		  InitialContext ic = new InitialContext();
		  System.out.println("start lookup");
		  final String jndiName = "java:global/DatabankEnterprise-ejb/AdminSocioResearchRemoteBean";
		  obj = ic.lookup(jndiName);
		  System.out.println("lookup returned " + obj);
		  eao = (AdminSocioResearchBeanRemote) obj;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
      @Override
	  public Boolean deleteResearch(long id)
	  {
	    return eao.deleteResearch(id);
	  }
      @Override
	  public SocioResearchDTO updateResearch(SocioResearchDTO research)
	  {
	    return eao.updateResearch(research);
	  }

      @Override
	  public VarDTO_Detailed generalizeVar(long var_id, ArrayList<Long> gen_var_ids)
	  {
	    UserAccountDTO watching_user = (UserAccountDTO)getThreadLocalRequest().getSession().getAttribute("user");
	    return eao.generalizeVar(var_id, gen_var_ids, watching_user);
	  }

      @Override
	  public SocioResearchDTO updateResearchGrouped(SocioResearchDTO research)
	  {
	    return eao.updateResearchGrouped(research);
	  }

      @Override
	  public void parseSPSS(long blobkey, long length)
	  {
	    eao.parseSPSS(blobkey, length);
	  }

      @Override
	  public long addOrgImpl(OrgDTO dto)
	  {
	    return eao.addOrgImpl(dto);
	  }

      @Override
	  public Boolean addFileToAccessor(long id_research, long id_file, String desc, String category)
	  {
	    return eao.addFileToAccessor(id_research, id_file, desc, category);
	  }

      @Override
	  public Boolean deleteFileFromAccessor(long id_research, long id_file)
	  {
	    return eao.deleteFileFromAccessor(id_research, id_file);
	  }

      @Override
	  public Boolean addSSE(String clas, String kind, String value)
	  {
	    return eao.addSSE(clas, kind, value);
	  }

      @Override
	  public Boolean updateFileAccessor(long research_id, ResearchFilesDTO dto)
	  {
	    return eao.updateFileAccessor(research_id, dto);
	  }

     
      @Override
	  public void updateMetaUnitStructure(MetaUnitDTO dto)
	  {
	    eao.updateMetaUnitStructure(dto);
	  }

      @Override
	  public void addMetaUnit(MetaUnitDTO dto, Long parent_unit_id)
	  {
	    eao.addMetaUnit(parent_unit_id.longValue(), dto);
	  }

      @Override
	  public void addEntityItem(Long entity_id, String value, HashMap<String, String> filling)
	  {
	    eao.addEntityItem(entity_id, value, filling);
	  }

      @Override
	  public void deleteMetaUnit(Long id, Long unit_parent_id)
	  {
	    eao.deleteMetaUnit(id, unit_parent_id);
	  }

      @Override
	  public void deleteEntityItem(Long id, Long entity_id)
	  {
	    eao.deleteEntityItem(id, entity_id);
	  }

      @Override
	  public void editEntityItem(Long entity_id, String value, HashMap<String, String> filling)
	  {
	    eao.editEntityItem(entity_id, value, filling);
	  }

      
      @Override
	  public void addSubEntityItem(Long parent_id, String value, HashMap<String, String> filling)
	  {
	    eao.addSubEntityItem(parent_id, value, filling);
	  }

   
      @Override
	  public void updateMetaUnitEntityItemLinks(MetaUnitEntityItemDTO old, MetaUnitEntityItemDTO nev)
	  {
	    eao.updateMetaUnitEntityItemLinks(old, nev);
	  }

      @Override
	  public void updateMetaUnitEntityItemLinks(MetaUnitEntityItemDTO dto)
	  {
	    eao.updateMetaUnitEntityItemLinks(dto);
	  }
	@Override
	public void updateMetaUnitEntityItemLinks(Long item_id,
			ArrayList<Long> tagged_ids, String identifier) {
		eao.updateMetaUnitEntityItemLinks(item_id, tagged_ids, identifier);
	}
	@Override
	public void updateVar(VarDTO_Detailed var) {
		eao.updateVar(var);
	}
	@Override
	public void setStartupContent(StartupBundleDTO dto) {
		eao.setStartupContent(dto);
	}
	
/*	
  public FriendsServiceImpl() {
    AppMisc.populateDataStoreOnce();
  }
*/
} // end class
