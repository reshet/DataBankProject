package com.mplatforma.amr.service.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import com.mresearch.databank.shared.FilterBaseDTO;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitEntityItemDTO;
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
    MetaUnitMultivaluedEntityDTO getMetaUnitMultivaluedEntityDTO(long id);
    MetaUnitMultivaluedEntityDTO getMetaUnitMultivaluedEntityDTO_FlattenedItems(long id);
    MetaUnitMultivaluedStructureDTO getMetaUnitMultivaluedStructureDTO(long id);
    Boolean addMetaUnit(long parent_id,MetaUnitDTO dto);
    void updateMetaUnitStructure(MetaUnitDTO dto);
    void addEntityItem(Long entity_id,String value,HashMap<String,String> filling);
    void addSubEntityItem(Long parent_id,String value,HashMap<String,String> filling);
    void editEntityItem(Long id,String value,HashMap<String,String> filling);
    void deleteEntityItem(Long id,Long entity_id);
    HashMap<String, String> getEntityItem(Long id);
    ArrayList<String> getEntityItemSubitemsNames(Long id_item);
    ArrayList<Long> getEntityItemSubitemsIDs(Long id_item);
    ArrayList<Long> getEntityItemTaggedEntitiesIDs(Long id_item);
    ArrayList<String> getEntityItemTaggedEntitiesIdentifiers(Long id_item);
    ArrayList<Long> getEntityItemTaggedEntitiesIDs(Long id_item,String identifier);
    MetaUnitEntityItemDTO getEntityItemDTO(Long id);
    ArrayList<MetaUnitEntityItemDTO> getEntityItemSubitemsDTOs(Long id);
    void deleteMetaUnit(Long id,Long unit_parent_id);
    void updateMetaUnitEntityItemLinks(MetaUnitEntityItemDTO old,MetaUnitEntityItemDTO nev);
    void updateMetaUnitEntityItemLinks(MetaUnitEntityItemDTO dto);
    void updateMetaUnitEntityItemLinks(Long item_id,ArrayList<Long> tagged_ids,String identifier);
    void updateVar(VarDTO_Detailed var);
}