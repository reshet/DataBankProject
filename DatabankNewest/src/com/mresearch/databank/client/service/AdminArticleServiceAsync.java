
package com.mresearch.databank.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.ZaconDTO;
import com.mresearch.databank.shared.ZaconDTO_Light;

public interface AdminArticleServiceAsync {

	void deleteArticle(Long id, AsyncCallback<Boolean> callback);

	void updateArticle(ArticleDTO article, AsyncCallback<ArticleDTO> callback);

	void getArticle(Long id, AsyncCallback<ArticleDTO> callback);

	void getArticleDTOs(ArrayList<Long> keys,
			AsyncCallback<ArrayList<ArticleDTO>> callback);

	void getArticlesAll(AsyncCallback<ArrayList<ArticleDTO>> callback);

	void deleteZacon(Long id, AsyncCallback<Boolean> callback);

	void getZacon(Long id, AsyncCallback<ZaconDTO> callback);

	void updateZacon(ZaconDTO Zacon, AsyncCallback<ZaconDTO> callback);

	void getZaconDTOs(ArrayList<Long> keys,
			AsyncCallback<ArrayList<ZaconDTO_Light>> callback);

	void getZaconsAll(AsyncCallback<ArrayList<ZaconDTO_Light>> callback);

	void getZaconDTOs_Normal(ArrayList<Long> keys,
			AsyncCallback<ArrayList<ZaconDTO>> callback);
}
