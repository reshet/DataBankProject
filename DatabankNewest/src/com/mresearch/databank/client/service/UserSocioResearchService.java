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
package com.mresearch.databank.client.service;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mresearch.databank.shared.FilterBaseDTO;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.ResearchFilesDTO;
import com.mresearch.databank.shared.SSE_DTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO_Light;
import com.mresearch.databank.shared.SocioResearchFilesDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;
import com.mresearch.databank.shared.VarDTO_Light;

@RemoteServiceRelativePath("userResearchService")
public interface UserSocioResearchService extends RemoteService {

  //ArrayList<FriendSummaryDTO> getFriendSummaries();
	public static class Util {
		private static UserSocioResearchServiceAsync instance;
		public static UserSocioResearchServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(UserSocioResearchService.class);
			}
			return instance;
		}
	}	
	
  
  SocioResearchDTO getResearch(long id);
  VarDTO getVar(long id);
  VarDTO_Detailed getVarDetailed(long id);
  ArrayList<SocioResearchDTO_Light> getResearchSummaries();
  ArrayList<SocioResearchDTO_Light> getResearchSummaries(ArrayList<FilterBaseDTO> filters);
  ArrayList<SocioResearchDTO_Light> getResearchDTOs(ArrayList<Long> keys);
  //String getBlobstoreUploadURL();
  ArrayList<VarDTO_Light> getVarDTOs(ArrayList<Long> keys);
  ArrayList<VarDTO_Light> getResearchVarsSummaries(long research_ad);
  ArrayList<Double> get2DDistribution(long var_id1, long var_id2);

  ResearchFilesDTO getResearchFiles(long research_id);
  SocioResearchFilesDTO getResearchFilesInCategory(long research_id,String category);
  
  ArrayList<SSE_DTO> getSSEs(String clas, String kind);
  
  ArrayList<OrgDTO> getOrgList();
  String doIndexSearch(String json_query,String [] types_to_search);

}
