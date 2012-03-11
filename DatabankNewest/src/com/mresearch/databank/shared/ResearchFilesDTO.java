package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class ResearchFilesDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2443613870378329241L;
	private Long id;
	private ArrayList<Long> file_ids = new ArrayList<Long>();
	private ArrayList<String> file_categs = new ArrayList<String>();
	private ArrayList<String> file_names = new ArrayList<String>();
	
	public static String CG_arrays = "CG_arrays";
	public ArrayList<String> getFile_names() {
		return file_names;
	}
	public void setFile_names(ArrayList<String> file_names) {
		this.file_names = file_names;
	}

	public static String CG_publications = "CG_publications";
	public static String CG_tables = "CG_tables";
	public static String CG_questionaries = "CG_questionaries";
	public static String CG_questionary_cards = "CG_questionary_cards";
	public static String CG_protocols = "CG_protocols";
	public static String CG_instructions = "CG_instructions";
	public static String CG_analytic_reports = "CG_analytic_reports";
	public static String CG_technical_reports = "CG_technical_reports";
	public static String CG_metadata = "CG_metadata";
	
	public ResearchFilesDTO(){}
	public void addFile(String category,String name,Long id)
	{
		if(!file_ids.contains(id))
		{
			file_categs.add(category);
			file_ids.add(id);
			file_names.add(name);
		}
	}
	public void deleteFile(Long id)
	{
		if(file_ids.contains(id))
		{
			int index = file_ids.indexOf(id);
			file_categs.remove(index);
			file_names.remove(index);
			file_ids.remove(index);
		}
	}
	public String getFileCategory(Long file_id)
	{
		if(file_ids.contains(file_id))
		{
			String category = file_categs.get(file_ids.indexOf(file_id));
			return category;
		}
		return null;
	}
	public String getFileName(Long file_id)
	{
		if(file_ids.contains(file_id))
		{
			String name = file_names.get(file_ids.indexOf(file_id));
			return name;
		}
		return null;
	}
	public ArrayList<String> getFileNames(String category)
	{
		ArrayList<String> names = new ArrayList<String>();
		if(file_categs.contains(category))
		{
			for(int i = 0; i < file_names.size();i++)
			{
				if(file_categs.get(i).equals(category)) names.add(file_names.get(i));
			}
		}
		return names;
	}
	public ArrayList<Long> getFileIds(String category)
	{
		ArrayList<Long> ids = new ArrayList<Long>();
		if(file_categs.contains(category))
		{
			for(int i = 0; i < file_ids.size();i++)
			{
				if(file_categs.get(i).equals(category)) ids.add(file_ids.get(i));
			}
		}
		return ids;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
	public ArrayList<Long> getFile_ids() {
		return file_ids;
	}

	public void setFile_ids(ArrayList<Long> file_ids) {
		this.file_ids = file_ids;
	}

	public ArrayList<String> getFile_categs() {
		return file_categs;
	}

	public void setFile_categs(ArrayList<String> file_categs) {
		this.file_categs = file_categs;
	}
	
}
