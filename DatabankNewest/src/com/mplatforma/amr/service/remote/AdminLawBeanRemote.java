package com.mplatforma.amr.service.remote;

import com.mresearch.databank.shared.*;
import javax.ejb.Remote;

import java.util.ArrayList;
import java.util.HashMap;

@Remote
public interface AdminLawBeanRemote {
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
