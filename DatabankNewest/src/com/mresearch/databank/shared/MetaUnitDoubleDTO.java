package com.mresearch.databank.shared;

import java.io.Serializable;

public class MetaUnitDoubleDTO extends MetaUnitDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2401836473218377772L;

	/**
	 * 
	 */
	 //private Class c_type;
	public MetaUnitDoubleDTO()
	{
		super();
	}
	public MetaUnitDoubleDTO(Long id,String desc,String un_name)
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
