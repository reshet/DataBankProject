package com.mresearch.databank.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mresearch.databank.shared.FilterBaseDTO;
import com.mresearch.databank.shared.MetaUnitDateDTO;
import com.mresearch.databank.shared.MetaUnitDoubleDTO;
import com.mresearch.databank.shared.MetaUnitEntityItemDTO;
import com.mresearch.databank.shared.MetaUnitIntegerDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedStructureDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.ResearchBundleDTO;
import com.mresearch.databank.shared.ResearchFilesDTO;
import com.mresearch.databank.shared.SSE_DTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO_Light;
import com.mresearch.databank.shared.SocioResearchFilesDTO;
import com.mresearch.databank.shared.UserAccountDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;
import com.mresearch.databank.shared.VarDTO_Light;




public interface UserSocioResearchServiceAsync {
void getResearch(long id, AsyncCallback<SocioResearchDTO> callback);
	void getResearchSummaries(
			AsyncCallback<ArrayList<SocioResearchDTO_Light>> callback);
	void getResearchVarsSummaries(long research_ad,
			AsyncCallback<ArrayList<VarDTO_Light>> callback);
	void getVar(long id, AsyncCallback<VarDTO> callback);
	void get2DDistribution(long var_id1, long var_id2,
			AsyncCallback<ArrayList<Double>> callback);
	void getVarDetailed(long id, AsyncCallback<VarDTO_Detailed> callback);
	void getResearchFiles(long research_id,
			AsyncCallback<ResearchFilesDTO> callback);
	void getSSEs(String clas,String kind, AsyncCallback<ArrayList<SSE_DTO>> callback);
	void getResearchSummaries(ArrayList<FilterBaseDTO> filters,
			AsyncCallback<ArrayList<SocioResearchDTO_Light>> callback);
	void getResearchFilesInCategory(long research_id, String category,
			AsyncCallback<SocioResearchFilesDTO> callback);
	void getResearchDTOs(ArrayList<Long> keys,
			AsyncCallback<ArrayList<SocioResearchDTO_Light>> callback);
	void getOrgList(AsyncCallback<ArrayList<OrgDTO>> callback);
	void doIndexSearch(String json_query, String[] types_to_search,
			AsyncCallback<String> callback);
	void getVarDTOs(ArrayList<Long> keys,
			AsyncCallback<ArrayList<VarDTO_Light>> callback);
	void getResearchBundle(long research_id,
			AsyncCallback<ResearchBundleDTO> callback);
	
	void getDatabankStructure(String db_name,
			AsyncCallback<MetaUnitMultivaluedEntityDTO> callback);
	void getMetaUnitMultivaluedFullDTO(long id,
			AsyncCallback<MetaUnitMultivaluedDTO> callback);
	void getMetaUnitInteger(long id, AsyncCallback<MetaUnitIntegerDTO> callback);
	void getMetaUnitDate(long id, AsyncCallback<MetaUnitDateDTO> callback);
	void getMetaUnitDouble(long id, AsyncCallback<MetaUnitDoubleDTO> callback);
	void getMetaUnitString(long id, AsyncCallback<MetaUnitStringDTO> callback);
	void getEntityItem(Long id, AsyncCallback<HashMap<String, String>> callback);
	void getEntityItemDTO(Long paramLong,
			AsyncCallback<MetaUnitEntityItemDTO> callback);
	void getEntityItemSubitemsDTOs(Long paramLong,
			AsyncCallback<ArrayList<MetaUnitEntityItemDTO>> callback);
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
	
	
}
