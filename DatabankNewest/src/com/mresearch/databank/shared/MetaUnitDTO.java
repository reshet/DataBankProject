package com.mresearch.databank.shared;

import java.io.Serializable;

public class MetaUnitDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4221470333954907316L;
	/**
	 * 
	 */
	private Long id;
	private String desc;
        private String unique_name;
        public String getUnique_name() {
		return unique_name;
	}

	public void setUnique_name(String unique_name) {
		this.unique_name = unique_name;
	}
        //private Class c_type;
	public MetaUnitDTO(){}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
//	public Class getC_type() {
//		return c_type;
//	}
//	public void setC_type(Class c_type) {
//		this.c_type = c_type;
//	}

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
	
    /**
     * @return the isCatalogizable
     */
}
