package com.mresearch.databank.shared;

import java.io.Serializable;

public class MetaUnitFileDTO extends MetaUnitDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6082354918447747417L;
     //private Class c_type;
        private long file_id;
        private String file_name;
	public MetaUnitFileDTO()
	{
		super();
	}
	public MetaUnitFileDTO(Long id,String un_name,String desc)
        {
            super();
            super.setId(id);
            super.setDesc(desc);
            super.setUnique_name(un_name);
//            this.file_name = file_name;
//            this.file_id = file_id;
        }

    /**
     * @return the file_id
     */
    public long getFile_id() {
        return file_id;
    }

    /**
     * @param file_id the file_id to set
     */
    public void setFile_id(long file_id) {
        this.file_id = file_id;
    }

    /**
     * @return the file_name
     */
    public String getFile_name() {
        return file_name;
    }

    /**
     * @param file_name the file_name to set
     */
    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
		
    /**
     * @return the isCatalogizable
     */
}
