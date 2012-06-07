package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class StartupBundleDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	public StartupBundleDTO(){}
	private ArrayList<PublicationDTO_Light> index_last;
	private ArrayList<ZaconDTO_Light> important_laws;
	private ArrayList<SocioResearchDTO_Light> top_researchs;
	public Long getPubs_last_count() {
		return pubs_last_count;
	}
	public void setPubs_last_count(Long pubs_last_count) {
		this.pubs_last_count = pubs_last_count;
	}
	private Long pubs_last_count = new Long(0);
	public StartupBundleDTO(ArrayList<PublicationDTO_Light> index_last,ArrayList<ZaconDTO_Light> important_laws,ArrayList<SocioResearchDTO_Light> top_researchs)
	{
		this.important_laws = important_laws;
		this.top_researchs = top_researchs;
		this.index_last = index_last;
	}
	public ArrayList<PublicationDTO_Light> getIndex_last() {
		return index_last;
	}
	public void setIndex_last(ArrayList<PublicationDTO_Light> index_last) {
		this.index_last = index_last;
	}
	public ArrayList<ZaconDTO_Light> getImportant_laws() {
		return important_laws;
	}
	public void setImportant_laws(ArrayList<ZaconDTO_Light> important_laws) {
		this.important_laws = important_laws;
	}
	public ArrayList<SocioResearchDTO_Light> getTop_researchs() {
		return top_researchs;
	}
	public void setTop_researchs(ArrayList<SocioResearchDTO_Light> top_researchs) {
		this.top_researchs = top_researchs;
	}
	

	
}
