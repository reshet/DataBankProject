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
	private Long org_order_id;
	private Long org_impl_id;
	private String org_order_name;
	private String org_impl_name;
	private Long var_weight_id;
	private String var_weight_name;
	private int selection_size;
	private String gen_geathering;
	private String method;
	private ArrayList<String> publications,publications_dois,researchers,concepts,publications_urls;
	private ArrayList<Long> var_ids = new ArrayList<Long>();
	private Date start_date,end_date;
	private String sel_randomity;
	private String sel_complexity;
	private Long file_accessor_id;
        private String json_desctiptor;
	private HashMap<String,String> filling;
	public String getJson_desctiptor() {
		return json_desctiptor;
	}


	public void setJson_desctiptor(String json_desctiptor) {
		this.json_desctiptor = json_desctiptor;
	}

	
	public SocioResearchDTO()
	{
		publications = new  ArrayList<String>();
		publications_dois = new  ArrayList<String>();
		researchers = new  ArrayList<String>();
		concepts = new  ArrayList<String>();
		publications_urls = new  ArrayList<String>();
                filling = new HashMap<String, String>();
	}
	
	
	public SocioResearchDTO(String name)
	{
		this();
		this.setName(name);
	}
	public void setBasicInfo(String name,Long org_pr)
	{
		this.setName(name);
		this.org_order_id = org_pr;
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

	public Long getOrg_order_id() {
		return org_order_id;
	}

	public void setOrg_order_id(Long org_order_id) {
		this.org_order_id = org_order_id;
	}

	public Long getOrg_impl_id() {
		return org_impl_id;
	}

	public void setOrg_impl_id(Long org_impl_id) {
		this.org_impl_id = org_impl_id;
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

	public String getGen_geathering() {
		return gen_geathering;
	}

	public void setGen_geathering(String gen_geathering) {
		this.gen_geathering = gen_geathering;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	

	public ArrayList<String> getResearchers() {
		return researchers;
	}

	public void setResearchers(ArrayList<String> researchers) {
		this.researchers = researchers;
	}

	public ArrayList<String> getPublications() {
		return publications;
	}

	public void setPublications(ArrayList<String> publications) {
		this.publications = publications;
	}

	public ArrayList<String> getConcepts() {
		return concepts;
	}

	public void setConcepts(ArrayList<String> concepts) {
		this.concepts = concepts;
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

	public String getOrg_order_name() {
		return org_order_name;
	}

	public void setOrg_order_name(String org_order_name) {
		this.org_order_name = org_order_name;
	}

	public String getOrg_impl_name() {
		return org_impl_name;
	}

	public void setOrg_impl_name(String org_impl_name) {
		this.org_impl_name = org_impl_name;
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

	public ArrayList<String> getPublications_dois() {
		return publications_dois;
	}

	public void setPublications_dois(ArrayList<String> publications_dois) {
		this.publications_dois = publications_dois;
	}

	public String getSel_randomity() {
		return sel_randomity;
	}

	public void setSel_randomity(String sel_randomity) {
		this.sel_randomity = sel_randomity;
	}

	public String getSel_complexity() {
		return sel_complexity;
	}

	public void setSel_complexity(String sel_complexity) {
		this.sel_complexity = sel_complexity;
	}

	public ArrayList<String> getPublications_urls() {
		return publications_urls;
	}

	public void setPublications_urls(ArrayList<String> publications_urls) {
		this.publications_urls = publications_urls;
	}

	public Long getFile_accessor_id() {
		return file_accessor_id;
	}

	public void setFile_accessor_id(Long file_accessor_id) {
		this.file_accessor_id = file_accessor_id;
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

	
	
}
