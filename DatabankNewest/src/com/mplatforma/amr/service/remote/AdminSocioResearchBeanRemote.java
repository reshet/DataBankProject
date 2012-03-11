package com.mplatforma.amr.service.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import com.mresearch.databank.shared.FilterBaseDTO;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedStructureDTO;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.ResearchFilesDTO;
import com.mresearch.databank.shared.SSE_DTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.SocioResearchFilesDTO;
import com.mresearch.databank.shared.UserAccountDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;
import com.mresearch.databank.shared.VarDTO_Light;

@Remote
public interface AdminSocioResearchBeanRemote {
	  Boolean deleteResearch(long id);
	  SocioResearchDTO updateResearch(SocioResearchDTO research);
	  SocioResearchDTO updateResearchGrouped(SocioResearchDTO research);

	  VarDTO_Detailed generalizeVar(long var_id, ArrayList<Long> gen_var_ids,UserAccountDTO dto);
	  
	  long parseSPSS(long blobkey, long length);

	  long addOrgImpl(OrgDTO dto);
	  
	  Boolean addFileToAccessor(long id_research,long id_file,String desc,String category);
	  Boolean deleteFileFromAccessor(long id_research,long id_file);
	  Boolean addSSE(String clas,String kind,String value);
	  Boolean updateFileAccessor(long research_id,ResearchFilesDTO dto);
      MetaUnitMultivaluedEntityDTO getDatabankStructure(String db_name);
      MetaUnitMultivaluedDTO getMetaUnitMultivaluedFullDTO(long id);
      Boolean addMetaUnit(long parent_id,MetaUnitDTO dto);
      void updateMetaUnitStructure(MetaUnitDTO dto);
      void addEntityItem(Long entity_id,String value,HashMap<String,String> filling);
      void editEntityItem(Long entity_id,String value,HashMap<String,String> filling);
      void deleteEntityItem(Long id);
      HashMap<String, String> getEntityItem(Long id);
      void deleteMetaUnit(Long id,Long unit_parent_id);
}