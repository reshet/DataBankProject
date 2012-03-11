package com.mresearch.databank.server;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mplatforma.amr.service.remote.UserSocioResearchBeanRemote;
import com.mresearch.databank.client.service.UserSocioResearchService;
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




public class UserSocioResearchServiceImpl extends RemoteServiceServlet implements UserSocioResearchService {

	
	
	
	private static UserSocioResearchBeanRemote eao;
	static
	{
		Object obj = new String("some");
		try {
		  InitialContext ic = new InitialContext();
		  System.out.println("start lookup");
		  final String jndiName = "java:global/DatabankEnterprise-ejb/UserSocioResearchRemoteBean";
		  obj = ic.lookup(jndiName);
		  System.out.println("lookup returned " + obj);
		  eao = (UserSocioResearchBeanRemote) obj;
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	@Override
	public SocioResearchDTO getResearch(long id) {
		return eao.getResearch(id);
	}

	@Override
	public VarDTO getVar(long id) {
		 UserAccountDTO userAcc =   (UserAccountDTO) this.getThreadLocalRequest().getSession().getAttribute("user");
		return eao.getVar(id,userAcc);
	}

	
	@Override
	public VarDTO_Detailed getVarDetailed(long id) {
		 UserAccountDTO userAcc =   (UserAccountDTO) this.getThreadLocalRequest().getSession().getAttribute("user");
		return eao.getVarDetailed(id,userAcc);
	}

	
	
	
	@Override
	public ArrayList<SocioResearchDTO> getResearchSummaries() {
		return eao.getResearchSummaries();
	}

	@Override
	public ArrayList<SocioResearchDTO> getResearchSummaries(
			ArrayList<FilterBaseDTO> filters) {
		return eao.getResearchSummaries(filters);
	}
	
	

	@Override
	public ArrayList<VarDTO_Light> getResearchVarsSummaries(long research_id) {
		return eao.getResearchVarsSummaries(research_id);
	}

	@Override
	public ArrayList<Double> get2DDistribution(long var_id1, long var_id2) {
		 UserAccountDTO userAcc =   (UserAccountDTO) this.getThreadLocalRequest().getSession().getAttribute("user");
		return eao.get2DDistribution(var_id1, var_id2,userAcc);
	}

	@Override
	public ResearchFilesDTO getResearchFiles(long research_id) {
		return eao.getResearchFiles(research_id);
	}

	@Override
	public SocioResearchFilesDTO getResearchFilesInCategory(long research_id,
			String category) {
		return eao.getResearchFilesInCategory(research_id, category);
	}

	@Override
	public ArrayList<SSE_DTO> getSSEs(String clas, String kind) {
		return eao.getSSEs(clas, kind);
	}

	@Override
	public ArrayList<SocioResearchDTO> getResearchDTOs(ArrayList<Long> keys) {
		return eao.getResearchDTOs(keys);
	}

	@Override
	public ArrayList<OrgDTO> getOrgList() {
		return eao.getOrgList();
	}

	@Override
	public String doIndexSearch(String json_query) {
		return eao.doIndexSearch(json_query);
	}
	

	
}
