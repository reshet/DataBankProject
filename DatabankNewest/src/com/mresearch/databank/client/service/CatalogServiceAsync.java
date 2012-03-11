package com.mresearch.databank.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mresearch.databank.shared.CatalogConceptDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;

public interface CatalogServiceAsync {
	void getResearchList(AsyncCallback<ArrayList<SocioResearchDTO>> callback);

	void getResearchVarList(long research_id,
			AsyncCallback<ArrayList<VarDTO>> callback);

	void getRootCatalogConcepts(String c_type,
			AsyncCallback<ArrayList<CatalogConceptDTO>> callback);


	void updateCatalogConcept(CatalogConceptDTO dto,
			AsyncCallback<CatalogConceptDTO> callback);

	void getCatalogSubConcepts(long id_catalog,
			AsyncCallback<ArrayList<CatalogConceptDTO>> callback);

	void getCatalogContentsIDs(long id_catalog,
			AsyncCallback<ArrayList<Long>> callback);

	void setCatalogContentsIDs(long id_catalog, ArrayList<Long> keys,
			AsyncCallback<Boolean> callback);

	void deleteConcept(long id, AsyncCallback<Boolean> callback);
}
