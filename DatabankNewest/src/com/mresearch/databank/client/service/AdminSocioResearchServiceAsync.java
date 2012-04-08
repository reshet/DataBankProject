package com.mresearch.databank.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
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

public interface AdminSocioResearchServiceAsync {

	void updateResearch(SocioResearchDTO research,
			AsyncCallback<SocioResearchDTO> callback);
	void deleteResearch(long id, AsyncCallback<Boolean> callback);
	void parseSPSS(long blobkey, long length, AsyncCallback<Void> callback);
	void addOrgImpl(OrgDTO dto, AsyncCallback<Long> callback);
	void updateResearchGrouped(SocioResearchDTO research,
			AsyncCallback<SocioResearchDTO> callback);
	void generalizeVar(long var_id, ArrayList<Long> gen_var_ids,
			AsyncCallback<VarDTO_Detailed> callback);
	void addSSE(String clas, String kind, String value,
			AsyncCallback<Boolean> callback);
	void updateFileAccessor(long research_id, ResearchFilesDTO dto,
			AsyncCallback<Boolean> callback);
	void addFileToAccessor(long id_research, long id_file, String desc,
			String category, AsyncCallback<Boolean> callback);
	void deleteFileFromAccessor(long id_research, long id_file,
			AsyncCallback<Boolean> callback);
	void getDatabankStructure(String db_name,
			AsyncCallback<MetaUnitMultivaluedEntityDTO> callback);
	void getMetaUnitMultivaluedFullDTO(long id,
			AsyncCallback<MetaUnitMultivaluedDTO> callback);
	void getMetaUnitInteger(long id, AsyncCallback<MetaUnitIntegerDTO> callback);
	void getMetaUnitDate(long id, AsyncCallback<MetaUnitDateDTO> callback);
	void getMetaUnitDouble(long id, AsyncCallback<MetaUnitDoubleDTO> callback);
	void getMetaUnitString(long id, AsyncCallback<MetaUnitStringDTO> callback);
	void updateMetaUnitStructure(MetaUnitDTO dto, AsyncCallback<Void> callback);
	void addMetaUnit(MetaUnitDTO dto, Long parent_unit_id,
			AsyncCallback<Void> callback);
	void addEntityItem(Long entity_id, String value,
			HashMap<String, String> filling, AsyncCallback<Void> callback);
	void deleteMetaUnit(Long id, Long unit_parent_id,
			AsyncCallback<Void> callback);
	void deleteEntityItem(Long id, Long entity_id, AsyncCallback<Void> callback);
	void editEntityItem(Long entity_id, String value,
			HashMap<String, String> filling, AsyncCallback<Void> callback);
	void getEntityItem(Long id, AsyncCallback<HashMap<String, String>> callback);
	void addSubEntityItem(Long paramLong, String paramString,
			HashMap<String, String> paramHashMap, AsyncCallback<Void> callback);
	void getEntityItemDTO(Long paramLong,
			AsyncCallback<MetaUnitEntityItemDTO> callback);
	void getEntityItemSubitemsDTOs(Long paramLong,
			AsyncCallback<ArrayList<MetaUnitEntityItemDTO>> callback);
	void updateMetaUnitEntityItemLinks(
			MetaUnitEntityItemDTO paramMetaUnitEntityItemDTO1,
			MetaUnitEntityItemDTO paramMetaUnitEntityItemDTO2,
			AsyncCallback<Void> callback);
	void updateMetaUnitEntityItemLinks(
			MetaUnitEntityItemDTO paramMetaUnitEntityItemDTO,
			AsyncCallback<Void> callback);
	
	void getMetaUnitMultivaluedEntityDTO(long id,
			AsyncCallback<MetaUnitMultivaluedEntityDTO> callback);
	void getMetaUnitMultivaluedStructureDTO(long id,
			AsyncCallback<MetaUnitMultivaluedStructureDTO> callback);
	void getMetaUnitMultivaluedEntityDTO_FlattenedItems(long id,
			AsyncCallback<MetaUnitMultivaluedEntityDTO> callback);
	void getEntityItemTaggedEntitiesIDs(Long id_item,
			AsyncCallback<ArrayList<Long>> callback);
	void getEntityItemTaggedEntitiesIdentifiers(Long id_item,
			AsyncCallback<ArrayList<String>> callback);
	void getEntityItemTaggedEntitiesIDs(Long id_item, String identifier,
			AsyncCallback<ArrayList<Long>> callback);
	void updateMetaUnitEntityItemLinks(Long item_id,
			ArrayList<Long> tagged_ids, String identifier,
			AsyncCallback<Void> callback);
	
	
}
