package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class ResearchBundleDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private SocioResearchDTO dto;
	private ResearchFilesDTO files_dto;
	private MetaUnitMultivaluedEntityDTO meta;
	public ResearchBundleDTO(){}
	public ResearchBundleDTO(SocioResearchDTO dto,ResearchFilesDTO files_dto,MetaUnitMultivaluedEntityDTO meta)
	{
		this.dto = dto;
		this.files_dto = files_dto;
		this.meta = meta;
	}
	
	
	
	public SocioResearchDTO getDto() {
		return dto;
	}
	public void setDto(SocioResearchDTO dto) {
		this.dto = dto;
	}
	public ResearchFilesDTO getFiles_dto() {
		return files_dto;
	}
	public void setFiles_dto(ResearchFilesDTO files_dto) {
		this.files_dto = files_dto;
	}
	public MetaUnitMultivaluedEntityDTO getMeta() {
		return meta;
	}
	public void setMeta(MetaUnitMultivaluedEntityDTO meta) {
		this.meta = meta;
	}
	
}
