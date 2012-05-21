package com.mresearch.databank.client.service;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.ISearchTask;
import com.mresearch.databank.shared.NewsDTO;
import com.mresearch.databank.shared.NewsSummaryDTO;
import com.mresearch.databank.shared.SearchResultDTO;
import com.mresearch.databank.shared.SearchTaskLawDTO;
import com.mresearch.databank.shared.SearchTaskResearchDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;

@RemoteServiceRelativePath("SearchService")
public interface SearchService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	ArrayList<SearchResultDTO> performSearch(String str);
	ArrayList<SearchResultDTO> performAdvancedSearchResearch(SearchTaskResearchDTO task);
	ArrayList<SearchResultDTO> performAdvancedSearchLaw(SearchTaskLawDTO task);
}
