package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class MetaUnitMultivaluedDTO extends MetaUnitDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1903172919395606840L;
	private boolean isCatalogizable;
        private boolean isSplittingEnabled;
        
	//private Class c_type;
	private ArrayList<Long> tagged_entities;
	private ArrayList<MetaUnitDTO> sub_meta_units;
	public MetaUnitMultivaluedDTO(){
            super();
        }
	public MetaUnitMultivaluedDTO(Long id,String desc,String un_name)
        {
            super();
            super.setId(id);
            super.setDesc(desc);
            super.setUnique_name(un_name);
        }
	
    /**
     * @return the isCatalogizable
     */
    public boolean isIsCatalogizable() {
        return isCatalogizable;
    }

    /**
     * @param isCatalogizable the isCatalogizable to set
     */
    public void setIsCatalogizable(boolean isCatalogizable) {
        this.isCatalogizable = isCatalogizable;
    }

    /**
     * @return the tagged_entities
     */
    public Collection<Long> getTagged_entities() {
        return tagged_entities;
    }

    /**
     * @param tagged_entities the tagged_entities to set
     */
    public void setTagged_entities(ArrayList<Long> tagged_entities) {
        this.tagged_entities = tagged_entities;
    }

    /**
     * @return the sub_meta_units
     */
    public ArrayList<MetaUnitDTO> getSub_meta_units() {
        return sub_meta_units;
    }

    /**
     * @param sub_meta_units the sub_meta_units to set
     */
    public void setSub_meta_units(ArrayList<MetaUnitDTO> sub_meta_units) {
        this.sub_meta_units = sub_meta_units;
    }

    /**
     * @return the isSplittingEnabled
     */
    public boolean isIsSplittingEnabled() {
        return isSplittingEnabled;
    }

    /**
     * @param isSplittingEnabled the isSplittingEnabled to set
     */
    public void setIsSplittingEnabled(boolean isSplittingEnabled) {
        this.isSplittingEnabled = isSplittingEnabled;
    }


}
