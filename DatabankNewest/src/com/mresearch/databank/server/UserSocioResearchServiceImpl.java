package com.mresearch.databank.server;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mplatforma.amr.service.remote.UserSocioResearchBeanRemote;
import com.mresearch.databank.client.service.UserSocioResearchService;
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
	public ArrayList<SocioResearchDTO_Light> getResearchSummaries() {
		return eao.getResearchSummaries();
	}

	@Override
	public ArrayList<SocioResearchDTO_Light> getResearchSummaries(
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
	public ArrayList<SocioResearchDTO_Light> getResearchDTOs(ArrayList<Long> keys) {
		return eao.getResearchDTOs(keys);
	}

	@Override
	public ArrayList<OrgDTO> getOrgList() {
		return eao.getOrgList();
	}

	@Override
	public String doIndexSearch(String json_query,String [] types_to_search) {
		return eao.doIndexSearch(json_query,types_to_search);
	}

	@Override
	public ArrayList<VarDTO_Light> getVarDTOs(ArrayList<Long> keys) {
		return eao.getVarDTOs(keys);
	}
	
	 @Override
	  public MetaUnitMultivaluedEntityDTO getDatabankStructure(String db_name)
	  {
	    return eao.getDatabankStructure(db_name);
	  }

     @Override
	  public MetaUnitMultivaluedStructureDTO getMetaUnitMultivaluedStructureDTO(long id)
	  {
	    return eao.getMetaUnitMultivaluedStructureDTO(id);
	  }

     @Override
	  public MetaUnitIntegerDTO getMetaUnitInteger(long id)
	  {
	    return null;
	  }

     @Override
	  public MetaUnitDoubleDTO getMetaUnitDouble(long id)
	  {
	    return null;
	  }

     @Override
	  public MetaUnitDateDTO getMetaUnitDate(long id)
	  {
	    return null;
	  }

     @Override
	  public MetaUnitStringDTO getMetaUnitString(long id)
	  {
	    return null;
	  }

     @Override
	  public HashMap<String, String> getEntityItem(Long id)
	  {
	    return eao.getEntityItem(id);
	  }

     @Override
	  public MetaUnitMultivaluedEntityDTO getMetaUnitMultivaluedEntityDTO(long id)
	  {
	    return eao.getMetaUnitMultivaluedEntityDTO(id);
	  }

     @Override
  	  public MetaUnitEntityItemDTO getEntityItemDTO(Long id)
  	  {
  	    return eao.getEntityItemDTO(id);
  	  }

        @Override
  	  public ArrayList<MetaUnitEntityItemDTO> getEntityItemSubitemsDTOs(Long id)
  	  {
  	    return eao.getEntityItemSubitemsDTOs(id);
  	  }

        @Override
  	  public MetaUnitMultivaluedEntityDTO getMetaUnitMultivaluedEntityDTO_FlattenedItems(long id)
  	  {
  	    return eao.getMetaUnitMultivaluedEntityDTO_FlattenedItems(id);
  	  }

	@Override
	public MetaUnitMultivaluedDTO getMetaUnitMultivaluedFullDTO(long id) {
		return eao.getMetaUnitMultivaluedEntityDTO(id);
	}
	@Override
	public ArrayList<Long> getEntityItemTaggedEntitiesIDs(Long id_item) {
		return eao.getEntityItemTaggedEntitiesIDs(id_item);
	}
	@Override
	public ArrayList<String> getEntityItemTaggedEntitiesIdentifiers(Long id_item) {
		return eao.getEntityItemTaggedEntitiesIdentifiers(id_item);
	}
	@Override
	public ArrayList<Long> getEntityItemTaggedEntitiesIDs(Long id_item,
			String identifier) {
		return eao.getEntityItemTaggedEntitiesIDs(id_item, identifier);
	}

	@Override
	public ResearchBundleDTO getResearchBundle(long research_id) {
		
		SocioResearchDTO dt = eao.getResearch(research_id);
		ResearchFilesDTO fdt = eao.getResearchFiles(research_id);
		MetaUnitMultivaluedEntityDTO mdt = eao.getDatabankStructure("socioresearch");
		ResearchBundleDTO dto = new ResearchBundleDTO(dt,fdt,mdt);
		
		return dto;
	}

}
