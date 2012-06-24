package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


//@SuppressWarnings("serial")
public class SocioResearchDTO implements Serializable,ICatalogizable,IPickableElement{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2708602147113054043L;
	private static SocioResearchDTO type;
	private Long id;
	private String name;
	private Long var_weight_id;
	private String var_weight_name;
	private int selection_size;
	private ArrayList<Long> var_ids = new ArrayList<Long>();
	private Date start_date,end_date;
	private Long file_accessor_id;
	public Long getFile_accessor_id() {
		return file_accessor_id;
	}

	public void setFile_accessor_id(Long file_accessor_id) {
		this.file_accessor_id = file_accessor_id;
	}

	
	private HashMap<String,String> filling;
        private String json_descriptor;

        private String description_text;
        public String getDesctiption() {
		return description_text;
	}


	public void setDesctiption(String desc) {
		this.description_text = desc;
	}
	public SocioResearchDTO()
	{
		filling = new HashMap<String, String>();
	}
	
	
	public SocioResearchDTO(String name)
	{
		this();
		this.setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public ArrayList<Long> getVar_ids() {
		return var_ids;
	}

	public void setVar_ids(ArrayList<Long> var_ids) {
		this.var_ids = var_ids;
	}

	

	public Long getVar_weight_id() {
		return var_weight_id;
	}

	public void setVar_weight_id(long var_weight_id) {
		this.var_weight_id = var_weight_id;
	}

	public int getSelection_size() {
		return selection_size;
	}

	public void setSelection_size(int selection_size) {
		this.selection_size = selection_size;
	}

	

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	

	public String getVar_weight_name() {
		return var_weight_name;
	}

	public void setVar_weight_name(String var_weight_name) {
		this.var_weight_name = var_weight_name;
	}

	public SocioResearchDTO getType() {
		return type;
	}

	@Override
	public String getTextRepresent() {
		return name;
	}

	@Override
	public long getID() {
		return id;
	}

	


    /**
     * @return the filling
     */
    public HashMap<String,String> getFilling() {
        return filling;
    }

    /**
     * @param filling the filling to set
     */
    public void setFilling(HashMap<String,String> filling) {
        this.filling = filling;
    }

    /**
     * @return the json_descriptor
     */
    public String getJson_descriptor() {
        return json_descriptor;
    }

    /**
     * @param json_descriptor the json_descriptor to set
     */
    public void setJson_descriptor(String json_descriptor) {
        this.json_descriptor = json_descriptor;
    }

	
	
}
