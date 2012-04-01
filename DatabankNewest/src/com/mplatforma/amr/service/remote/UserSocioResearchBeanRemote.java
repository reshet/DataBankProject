package com.mplatforma.amr.service.remote;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import com.mresearch.databank.shared.FilterBaseDTO;
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
public interface UserSocioResearchBeanRemote {
    SocioResearchDTO getResearch(long id);
    VarDTO getVar(long id,UserAccountDTO dto);
    VarDTO_Detailed getVarDetailed(long id,UserAccountDTO dto);
    ArrayList<SocioResearchDTO> getResearchSummaries();
    ArrayList<SocioResearchDTO> getResearchSummaries(List<FilterBaseDTO> filters);
    ArrayList<SocioResearchDTO> getResearchDTOs(ArrayList<Long> ids); 
    ArrayList<VarDTO_Light> getResearchVarsSummaries(long research_id);
    ArrayList<Double> get2DDistribution(long var_id1,long var_id2,UserAccountDTO user);
    ResearchFilesDTO getResearchFiles(long research_id);
    SocioResearchFilesDTO getResearchFilesInCategory(long research_id,String category);
    ArrayList<SSE_DTO> getSSEs(String clas, String kind);
    ArrayList<OrgDTO> getOrgList();
    String doIndexSearch(String json_query,String [] types_to_search);
}