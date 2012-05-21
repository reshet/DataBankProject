package com.mresearch.databank.shared;

import java.io.Serializable;

public class MetaUnitDateDTO extends MetaUnitDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
     //private Class c_type;
	public MetaUnitDateDTO(){
		super();
	}
	public MetaUnitDateDTO(Long id,String desc,String un_name)
        {
            super();
            super.setId(id);
            super.setDesc(desc);
            super.setUnique_name(un_name);
        }
		
    /**
     * @return the isCatalogizable
     */
}
