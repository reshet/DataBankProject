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
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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
import com.mresearch.databank.shared.SocioResearchFilesDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;

@RemoteServiceRelativePath("adminResearchService")
public interface AdminSocioResearchService extends RemoteService {

	public static class Util {
		private static AdminSocioResearchServiceAsync instance;
		public static AdminSocioResearchServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(AdminSocioResearchService.class);
			}
			return instance;
		}
	}	
  //ArrayList<FriendSummaryDTO> getFriendSummaries();

  Boolean deleteResearch(long id);


  SocioResearchDTO updateResearch(SocioResearchDTO research);
  VarDTO_Detailed generalizeVar(long var_id, ArrayList<Long> gen_var_ids);
  SocioResearchDTO updateResearchGrouped(SocioResearchDTO research);

  void parseSPSS(long blobkey, long length);

  long addOrgImpl(OrgDTO dto);

  
  Boolean addFileToAccessor(long id_research,long id_file,String desc,String category);
  Boolean deleteFileFromAccessor(long id_research,long id_file);
  Boolean addSSE(String clas,String kind,String value);
  Boolean updateFileAccessor(long research_id,ResearchFilesDTO dto);
  MetaUnitMultivaluedEntityDTO getDatabankStructure(String db_name);
  MetaUnitMultivaluedDTO getMetaUnitMultivaluedFullDTO(long id);
  MetaUnitIntegerDTO getMetaUnitInteger(long id);
  MetaUnitDoubleDTO getMetaUnitDouble(long id);
  MetaUnitDateDTO getMetaUnitDate(long id);
  MetaUnitStringDTO getMetaUnitString(long id);
  void updateMetaUnitStructure(MetaUnitDTO dto);
  void addMetaUnit(MetaUnitDTO dto,Long parent_unit_id);
  void addEntityItem(Long entity_id,String value,HashMap<String,String> filling);
  void deleteMetaUnit(Long id,Long unit_parent_id);
  void editEntityItem(Long entity_id,String value,HashMap<String,String> filling);
  HashMap<String, String> getEntityItem(Long id);

  void addSubEntityItem(Long paramLong, String paramString, HashMap<String, String> paramHashMap);
  MetaUnitEntityItemDTO getEntityItemDTO(Long paramLong);
  ArrayList<MetaUnitEntityItemDTO> getEntityItemSubitemsDTOs(Long paramLong);
  void updateMetaUnitEntityItemLinks(MetaUnitEntityItemDTO paramMetaUnitEntityItemDTO1, MetaUnitEntityItemDTO paramMetaUnitEntityItemDTO2);
  void updateMetaUnitEntityItemLinks(MetaUnitEntityItemDTO paramMetaUnitEntityItemDTO);


MetaUnitMultivaluedEntityDTO getMetaUnitMultivaluedEntityDTO(long id);


void deleteEntityItem(Long id, Long entity_id);


MetaUnitMultivaluedStructureDTO getMetaUnitMultivaluedStructureDTO(long id);


MetaUnitMultivaluedEntityDTO getMetaUnitMultivaluedEntityDTO_FlattenedItems(long id);

ArrayList<Long> getEntityItemTaggedEntitiesIDs(Long id_item);
ArrayList<String> getEntityItemTaggedEntitiesIdentifiers(Long id_item);
ArrayList<Long> getEntityItemTaggedEntitiesIDs(Long id_item,String identifier);
void updateMetaUnitEntityItemLinks(Long item_id,ArrayList<Long> tagged_ids,String identifier);

void updateVar(VarDTO_Detailed var);
  //ArrayList<E>
//  SocioResearchDTO addResearch(SocioResearchDTO research);
}
