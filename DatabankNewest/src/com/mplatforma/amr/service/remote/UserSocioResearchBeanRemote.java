package com.mplatforma.amr.service.remote;

import com.mresearch.databank.shared.*;
import javax.ejb.Remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Remote
public interface UserSocioResearchBeanRemote {
    SocioResearchDTO getResearch(long id);
    VarDTO getVar(long id,UserAccountDTO dto);
    VarDTO_Detailed getVarDetailed(long id,UserAccountDTO dto);
    ArrayList<SocioResearchDTO_Light> getResearchSummaries();
    ArrayList<SocioResearchDTO_Light> getResearchSummaries(List<FilterBaseDTO> filters);
    ArrayList<SocioResearchDTO_Light> getResearchDTOs(ArrayList<Long> ids); 
    ArrayList<VarDTO_Light> getResearchVarsSummaries(long research_id);
    ArrayList<Double> get2DDistribution(long var_id1,long var_id2,UserAccountDTO user);
    ResearchFilesDTO getResearchFiles(long research_id);
    SocioResearchFilesDTO getResearchFilesInCategory(long research_id,String category);
    ArrayList<SSE_DTO> getSSEs(String clas, String kind);
    ArrayList<OrgDTO> getOrgList();
    String doIndexSearch(String json_query,String [] types_to_search);
    ArrayList<VarDTO_Light> getVarDTOs(ArrayList<Long> keys);
    
    MetaUnitMultivaluedEntityDTO getDatabankStructure(String db_name);
      MetaUnitMultivaluedEntityDTO getMetaUnitMultivaluedEntityDTO(long id);
      MetaUnitMultivaluedEntityDTO getMetaUnitMultivaluedEntityDTO_FlattenedItems(long id);
      MetaUnitMultivaluedStructureDTO getMetaUnitMultivaluedStructureDTO(long id);
      
    
    HashMap<String, String> getEntityItem(Long id);
      ArrayList<String> getEntityItemSubitemsNames(Long id_item);
      ArrayList<Long> getEntityItemSubitemsIDs(Long id_item);
      ArrayList<Long> getEntityItemTaggedEntitiesIDs(Long id_item);
       ArrayList<Long> getEntityItemTaggedEntitiesIDs(Long id_item,String identifier);
      ArrayList<String> getEntityItemTaggedEntitiesIdentifiers(Long id_item);
      MetaUnitEntityItemDTO getEntityItemDTO(Long id);
      ArrayList<MetaUnitEntityItemDTO> getEntityItemSubitemsDTOs(Long id);
      
}
