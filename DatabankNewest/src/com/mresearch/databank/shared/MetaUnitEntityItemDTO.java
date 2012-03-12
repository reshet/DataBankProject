package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MetaUnitEntityItemDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -995870345138732422L;
	/**
	 * 
	 */
        
		private Long id;    
		private String v_value;
	    private HashMap<String,String> mapped_values;
	    private ArrayList<MetaUnitEntityItemDTO> subitems;
	    private ArrayList<Long> tagged_entities_ids;
	    private ArrayList<String> tagged_entities_identifiers;
	    public MetaUnitEntityItemDTO(){
      
        }
	    public MetaUnitEntityItemDTO(Long id,String value,HashMap<String, String> map)
        {
	    	this.id = id;
	    	this.v_value = value;
	    	this.mapped_values = map;
        }
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getV_value() {
			return v_value;
		}
		public void setV_value(String v_value) {
			this.v_value = v_value;
		}
		public HashMap<String, String> getMapped_values() {
			return mapped_values;
		}
		public void setMapped_values(HashMap<String, String> mapped_values) {
			this.mapped_values = mapped_values;
		}
		public ArrayList<MetaUnitEntityItemDTO> getSubitems() {
			return subitems;
		}
		public void setSubitems(ArrayList<MetaUnitEntityItemDTO> subitems) {
			this.subitems = subitems;
		}
		public ArrayList<Long> getTagged_entities_ids() {
			return tagged_entities_ids;
		}
		public void setTagged_entities_ids(ArrayList<Long> tagged_entities_ids) {
			this.tagged_entities_ids = tagged_entities_ids;
		}
		public ArrayList<String> getTagged_entities_identifiers() {
			return tagged_entities_identifiers;
		}
		public void setTagged_entities_identifiers(
				ArrayList<String> tagged_entities_identifiers) {
			this.tagged_entities_identifiers = tagged_entities_identifiers;
		}
 




}
