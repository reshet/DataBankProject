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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.ResearchFilesDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.ZaconDTO;
import com.mresearch.databank.shared.ZaconDTO_Light;

@RemoteServiceRelativePath("adminArticleService")
public interface AdminArticleService extends RemoteService {


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
}