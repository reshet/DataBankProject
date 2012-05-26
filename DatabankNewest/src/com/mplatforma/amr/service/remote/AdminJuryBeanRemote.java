package com.mplatforma.amr.service.remote;

import com.mresearch.databank.shared.*;
import javax.ejb.Remote;

import java.util.ArrayList;
import java.util.HashMap;

@Remote
public interface AdminJuryBeanRemote {
  Boolean deleteConsultation(Long id);
  ConsultationDTO getConsultation(Long id);
  ConsultationDTO updateConsultation(ConsultationDTO Consultation);
  ArrayList<ConsultationDTO_Light> getConsultationDTOs(ArrayList<Long> keys);
  
  ArrayList<ConsultationDTO> getConsultationDTOs_Normal(ArrayList<Long> keys);
  ArrayList<ConsultationDTO_Light> getConsultationsAll();
  ArrayList<ConsultationDTO_Light> getConsultations(int limit,int offset);
    ArrayList<TopicDTO> getTopics();
    JuryBundleDTO getStartup();
}
