package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class PublicationsBundleDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	public PublicationsBundleDTO(){}
	private ArrayList<PublicationDTO> last_index_dtos;
        private ArrayList<PublicationDTO_Light> last_l_index_dtos;
	private MetaUnitMultivaluedEntityDTO pubMeta;
	private ArrayList<TopicDTO> tops;
	public PublicationsBundleDTO(MetaUnitMultivaluedEntityDTO meta,ArrayList<PublicationDTO_Light> index_last_light,ArrayList<PublicationDTO> index_last,ArrayList<TopicDTO> tops)
	{
		this.pubMeta = meta;
		this.last_index_dtos = index_last;
                this.last_l_index_dtos = index_last_light;
		this.tops = tops;
	}
	public ArrayList<PublicationDTO_Light> getLast_l_index_dtos() {
		return last_l_index_dtos;
	}
	public void setLast_l_index_dtos(
			ArrayList<PublicationDTO_Light> last_l_index_dtos) {
		this.last_l_index_dtos = last_l_index_dtos;
	}
	public ArrayList<PublicationDTO> getLast_index_dtos() {
		return last_index_dtos;
	}
	public void setLast_index_dtos(ArrayList<PublicationDTO> last_index_dtos) {
		this.last_index_dtos = last_index_dtos;
	}
	public MetaUnitMultivaluedEntityDTO getPubMeta() {
		return pubMeta;
	}
	public void setPubMeta(MetaUnitMultivaluedEntityDTO juryMeta) {
		this.pubMeta = juryMeta;
	}
	public ArrayList<TopicDTO> getTops() {
		return tops;
	}
	public void setTops(ArrayList<TopicDTO> tops) {
		this.tops = tops;
	}

	
}
