package com.mresearch.databank.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.ISearchTask;
import com.mresearch.databank.shared.NewsDTO;
import com.mresearch.databank.shared.NewsSummaryDTO;
import com.mresearch.databank.shared.SearchResultDTO;
import com.mresearch.databank.shared.SearchTaskLawDTO;
import com.mresearch.databank.shared.SearchTaskResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;

public interface SearchServiceAsync {
	void performSearch(String str,
			AsyncCallback<ArrayList<SearchResultDTO>> callback);

	void performAdvancedSearchResearch(SearchTaskResearchDTO task,
			AsyncCallback<ArrayList<SearchResultDTO>> callback);

	void performAdvancedSearchLaw(SearchTaskLawDTO task,
			AsyncCallback<ArrayList<SearchResultDTO>> callback);
}
