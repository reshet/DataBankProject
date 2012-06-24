
package com.mresearch.databank.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.ConsultationDTO;
import com.mresearch.databank.shared.ConsultationDTO_Light;
import com.mresearch.databank.shared.JuryBundleDTO;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.PublicationDTO;
import com.mresearch.databank.shared.PublicationDTO_Light;
import com.mresearch.databank.shared.PublicationsBundleDTO;
import com.mresearch.databank.shared.ResearchFilesDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.SocioResearchFilesDTO;
import com.mresearch.databank.shared.TopicDTO;
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

	void deletePublication(Long id, AsyncCallback<Boolean> callback);

	void getPublication(Long id, AsyncCallback<PublicationDTO> callback);

	void updatePublication(PublicationDTO Publication,
			AsyncCallback<PublicationDTO> callback);

	void getPublicationDTOs(ArrayList<Long> keys,
			AsyncCallback<ArrayList<PublicationDTO_Light>> callback);

	void getPublicationDTOs_Normal(ArrayList<Long> keys,
			AsyncCallback<ArrayList<PublicationDTO>> callback);

	void getPublicationsAll(
			AsyncCallback<ArrayList<PublicationDTO_Light>> callback);

	void getPublications(int limit, int offset,
			AsyncCallback<ArrayList<PublicationDTO_Light>> callback);

	void getTopics(AsyncCallback<ArrayList<TopicDTO>> callback);

	void deleteConsultation(Long id, AsyncCallback<Boolean> callback);

	void getConsultation(Long id, AsyncCallback<ConsultationDTO> callback);

	void updateConsultation(ConsultationDTO Consultation,
			AsyncCallback<ConsultationDTO> callback);

	void getConsultationDTOs(ArrayList<Long> keys,
			AsyncCallback<ArrayList<ConsultationDTO_Light>> callback);

	void getConsultationDTOs_Normal(ArrayList<Long> keys,
			AsyncCallback<ArrayList<ConsultationDTO>> callback);

	void getConsultationsAll(
			AsyncCallback<ArrayList<ConsultationDTO_Light>> callback);

	void getConsultations(int limit, int offset,
			AsyncCallback<ArrayList<ConsultationDTO_Light>> callback);

	void getJuryTopics(AsyncCallback<ArrayList<TopicDTO>> callback);

	void getJuryStartup(AsyncCallback<JuryBundleDTO> callback);

	void getPubStartup(AsyncCallback<PublicationsBundleDTO> callback);

	void addFileToAccessor(long id_research, long id_file, String desc,
			String category, AsyncCallback<Boolean> callback);

	void deleteFileFromAccessor(long id_research, long id_file,
			AsyncCallback<Boolean> callback);

	void updateFileAccessor(long research_id, ResearchFilesDTO dto,
			AsyncCallback<Boolean> callback);

	void getFiles(long research_id, AsyncCallback<ResearchFilesDTO> callback);

	void getFilesInCategory(long research_id, String category,
			AsyncCallback<SocioResearchFilesDTO> callback);
}
