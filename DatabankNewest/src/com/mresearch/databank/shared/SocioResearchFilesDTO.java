package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SocioResearchFilesDTO implements Serializable{
	private long id;
	private ArrayList<String> files_descs;
	private ArrayList<Long> files_ids;
	public SocioResearchFilesDTO(ArrayList<Long> files_ids,ArrayList<String> files_descs)
	{
		this.setFiles_ids(files_ids);
		this.setFiles_descs(files_descs);
	}
	public SocioResearchFilesDTO()
	{
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getID() {
		return id;
	}


	public ArrayList<Long> getFiles_ids() {
		return files_ids;
	}


	public void setFiles_ids(ArrayList<Long> files_ids) {
		this.files_ids = files_ids;
	}


	public ArrayList<String> getFiles_descs() {
		return files_descs;
	}


	public void setFiles_descs(ArrayList<String> files_descs) {
		this.files_descs = files_descs;
	}
}
