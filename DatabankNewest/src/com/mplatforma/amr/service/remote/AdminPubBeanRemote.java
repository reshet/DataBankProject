package com.mplatforma.amr.service.remote;

import com.mresearch.databank.shared.*;
import javax.ejb.Remote;

import java.util.ArrayList;

@Remote
public interface AdminPubBeanRemote {
  Boolean deletePublication(Long id);
  PublicationDTO getPublication(Long id);
  PublicationDTO updatePublication(PublicationDTO Publication);
  ArrayList<PublicationDTO_Light> getPublicationDTOs(ArrayList<Long> keys);
  
  ArrayList<PublicationDTO> getPublicationDTOs_Normal(ArrayList<Long> keys);
  ArrayList<PublicationDTO_Light> getPublicationsAll();
  ArrayList<PublicationDTO_Light> getPublications(int limit,int offset);
  ArrayList<TopicDTO> getTopics();
  
  PublicationsBundleDTO getStartup();
}
