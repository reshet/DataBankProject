package com.mresearch.databank.client.service;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mresearch.databank.shared.CatalogConceptDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;

@RemoteServiceRelativePath("CatalogService")
public interface CatalogService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	
	public static class Util {
		private static CatalogServiceAsync instance;
		public static CatalogServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(CatalogService.class);
			}
			return instance;
		}
	}

	ArrayList<SocioResearchDTO> getResearchList();
	ArrayList<VarDTO> getResearchVarList(long research_id);
	
	ArrayList<CatalogConceptDTO> getRootCatalogConcepts(String c_type);
	ArrayList<CatalogConceptDTO> getCatalogSubConcepts(long id_catalog);
	ArrayList<Long> getCatalogContentsIDs(long id_catalog);
	boolean setCatalogContentsIDs(long id_catalog, ArrayList<Long> keys);
	boolean deleteConcept(long id);
	
	CatalogConceptDTO updateCatalogConcept(CatalogConceptDTO dto);
}
