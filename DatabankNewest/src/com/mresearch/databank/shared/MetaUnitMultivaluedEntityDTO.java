package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class MetaUnitMultivaluedEntityDTO extends MetaUnitMultivaluedDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1903172919395606840L;
	private boolean isMultiselected;
    private ArrayList<String> item_names = new ArrayList<String>();
    private ArrayList<Long> item_ids = new ArrayList<Long>();
        
        public MetaUnitMultivaluedEntityDTO(){
            super();
        }
	public MetaUnitMultivaluedEntityDTO(Long id,String un_name,String desc)
        {
            super(id,un_name,desc);
        }
	


    /**
     * @return the tagged_entities
     */

    /**
     * @return the isSplittingEnabled
     */
    /**
     * @return the isMultiselected
     */
    public boolean isIsMultiselected() {
        return isMultiselected;
    }

    /**
     * @param isMultiselected the isMultiselected to set
     */
    public void setIsMultiselected(boolean isMultiselected) {
        this.isMultiselected = isMultiselected;
    }

    /**
     * @return the item_names
     */
    public ArrayList<String> getItem_names() {
        return item_names;
    }

    /**
     * @param item_names the item_names to set
     */
    public void setItem_names(ArrayList<String> item_names) {
        this.item_names = item_names;
    }

    /**
     * @return the item_ids
     */
    public ArrayList<Long> getItem_ids() {
        return item_ids;
    }

    /**
     * @param item_ids the item_ids to set
     */
    public void setItem_ids(ArrayList<Long> item_ids) {
        this.item_ids = item_ids;
    }
}
