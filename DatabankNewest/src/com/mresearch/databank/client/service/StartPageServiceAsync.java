package com.mresearch.databank.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.NewsDTO;
import com.mresearch.databank.shared.NewsSummaryDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;

public interface StartPageServiceAsync {
	void getNewsDetailed(String news_id, AsyncCallback<ArticleDTO> callback);

	void getNewsSummaries(int latest,
			AsyncCallback<ArrayList<NewsSummaryDTO>> callback);
	void getNews(int latest,
			AsyncCallback<ArrayList<NewsDTO>> callback);

	void getArticle(String article_id, AsyncCallback<ArticleDTO> callback);

	void getMainPageArticle(AsyncCallback<ArticleDTO> callback);
}
