/** 
 * Copyright 2010 Daniel Guermeur and Amy Unruh
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   See http://connectrapp.appspot.com/ for a demo, and links to more information 
 *   about this app and the book that it accompanies.
 */
package com.mresearch.databank.client.service;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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

@RemoteServiceRelativePath("adminArticleService")
public interface AdminArticleService extends RemoteService {

	

	
	public static class Util {
		private static AdminArticleServiceAsync instance;
		public static AdminArticleServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(AdminArticleService.class);
			}
			return instance;
		}
	}	
  Boolean deleteArticle(Long id);

  ArticleDTO getArticle(Long id);

  ArticleDTO updateArticle(ArticleDTO article);


  ArrayList<ArticleDTO> getArticleDTOs(ArrayList<Long> keys);

  ArrayList<ArticleDTO> getArticlesAll();
  
  Boolean deleteZacon(Long id);

  ZaconDTO getZacon(Long id);

  ZaconDTO updateZacon(ZaconDTO Zacon);


  ArrayList<ZaconDTO_Light> getZaconDTOs(ArrayList<Long> keys);
  ArrayList<ZaconDTO> getZaconDTOs_Normal(ArrayList<Long> keys);
  ArrayList<ZaconDTO_Light> getZaconsAll();
  
  
  Boolean deletePublication(Long id);
  PublicationDTO getPublication(Long id);
  PublicationDTO updatePublication(PublicationDTO Publication);
  ArrayList<PublicationDTO_Light> getPublicationDTOs(ArrayList<Long> keys);
  ArrayList<PublicationDTO> getPublicationDTOs_Normal(ArrayList<Long> keys);
  ArrayList<PublicationDTO_Light> getPublicationsAll();
  ArrayList<PublicationDTO_Light> getPublications(int limit,int offset);
  ArrayList<TopicDTO> getTopics();
  
  Boolean deleteConsultation(Long id);
  ConsultationDTO getConsultation(Long id);
  ConsultationDTO updateConsultation(ConsultationDTO Consultation);
  ArrayList<ConsultationDTO_Light> getConsultationDTOs(ArrayList<Long> keys);
  ArrayList<ConsultationDTO> getConsultationDTOs_Normal(ArrayList<Long> keys);
  ArrayList<ConsultationDTO_Light> getConsultationsAll();
  ArrayList<ConsultationDTO_Light> getConsultations(int limit,int offset);
  ArrayList<TopicDTO> getJuryTopics();

  JuryBundleDTO getJuryStartup();
   PublicationsBundleDTO getPubStartup();
   
   
   Boolean addFileToAccessor(long id_research,long id_file,String desc,String category);
   Boolean deleteFileFromAccessor(long id_research,long id_file);
   Boolean updateFileAccessor(long research_id,ResearchFilesDTO dto);
   
   ResearchFilesDTO getFiles(long research_id);
   SocioResearchFilesDTO getFilesInCategory(long research_id,String category);
   
}
