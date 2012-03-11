package com.mresearch.databank.client.service;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.NewsDTO;
import com.mresearch.databank.shared.NewsSummaryDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;

@RemoteServiceRelativePath("StartPageService")
public interface StartPageService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	ArrayList<NewsSummaryDTO> getNewsSummaries(int latest);
	ArticleDTO getNewsDetailed(String news_id);
	ArticleDTO getArticle(String article_id);
	ArticleDTO getMainPageArticle();
	ArrayList<NewsDTO> getNews(int latest);
}
