package com.mresearch.databank.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mresearch.databank.shared.FilterBaseDTO;
import com.mresearch.databank.shared.OrgDTO;
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
}
