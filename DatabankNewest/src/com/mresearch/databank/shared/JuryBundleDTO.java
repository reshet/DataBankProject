package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class JuryBundleDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	public JuryBundleDTO(){}
	private ArrayList<ConsultationDTO> last_index_dtos;
	private MetaUnitMultivaluedEntityDTO juryMeta;
	private ArrayList<TopicDTO> tops;
	public JuryBundleDTO(MetaUnitMultivaluedEntityDTO meta,ArrayList<ConsultationDTO> index_last,ArrayList<TopicDTO> tops)
	{
		this.juryMeta = meta;
		this.last_index_dtos = index_last;
		this.tops = tops;
	}
	public ArrayList<ConsultationDTO> getLast_index_dtos() {
		return last_index_dtos;
	}
	public void setLast_index_dtos(ArrayList<ConsultationDTO> last_index_dtos) {
		this.last_index_dtos = last_index_dtos;
	}
	public MetaUnitMultivaluedEntityDTO getJuryMeta() {
		return juryMeta;
	}
	public void setJuryMeta(MetaUnitMultivaluedEntityDTO juryMeta) {
		this.juryMeta = juryMeta;
	}
	public ArrayList<TopicDTO> getTops() {
		return tops;
	}
	public void setTops(ArrayList<TopicDTO> tops) {
		this.tops = tops;
	}

	
}
