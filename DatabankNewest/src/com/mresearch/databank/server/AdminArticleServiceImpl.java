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
package com.mresearch.databank.server;






import java.io.DataInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.opendatafoundation.data.FileFormatInfo;
import org.opendatafoundation.data.FileFormatInfo.Format;
import org.opendatafoundation.data.spss.mod.SPSSFile;
import org.opendatafoundation.data.spss.mod.SPSSFileException;
import org.opendatafoundation.data.spss.mod.SPSSNumericVariable;
import org.opendatafoundation.data.spss.mod.SPSSVariable;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.mplatforma.amr.service.remote.AdminJuryBeanRemote;
import com.mplatforma.amr.service.remote.AdminLawBeanRemote;
import com.mplatforma.amr.service.remote.AdminPubBeanRemote;
import com.mplatforma.amr.service.remote.AdminSocioResearchBeanRemote;
import com.mresearch.databank.client.service.AdminArticleService;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.ConsultationDTO;
import com.mresearch.databank.shared.ConsultationDTO_Light;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.PublicationDTO;
import com.mresearch.databank.shared.PublicationDTO_Light;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.TopicDTO;
import com.mresearch.databank.shared.ZaconDTO;
import com.mresearch.databank.shared.ZaconDTO_Light;


/**
 * the FriendsService communicates Friend data via RPC between client and server.
 */
@SuppressWarnings("serial")
public class AdminArticleServiceImpl extends RemoteServiceServlet implements
    AdminArticleService {

	private static AdminLawBeanRemote eao;
	private static AdminPubBeanRemote eao2;
	private static AdminJuryBeanRemote eao3;
	static
	{
		Object obj = new String("some");
		try {
		  InitialContext ic = new InitialContext();
		  System.out.println("start lookup");
		  final String jndiName = "java:global/DatabankEnterprise-ejb/AdminLawRemoteBean";
		  obj = ic.lookup(jndiName);
		  System.out.println("lookup returned " + obj);
		  eao = (AdminLawBeanRemote) obj;
		  
		  
		  final String jndiName2 = "java:global/DatabankEnterprise-ejb/AdminPubRemoteBean";
		  obj = ic.lookup(jndiName2);
		  System.out.println("lookup returned " + obj);
		  eao2 = (AdminPubBeanRemote) obj;
		  
		  
		  final String jndiName3 = "java:global/DatabankEnterprise-ejb/AdminJuryRemoteBean";
		  obj = ic.lookup(jndiName3);
		  System.out.println("lookup returned " + obj);
		  eao3 = (AdminJuryBeanRemote) obj;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}

@Override
public Boolean deleteArticle(Long id) {
  return eao.deleteArticle(id);
 }

@Override
public ArticleDTO getArticle(Long id) {
	return eao.getArticle(id);
  }

@Override
public ArticleDTO updateArticle(ArticleDTO aDTO) {
	return eao.updateArticle(aDTO);
}

@Override
public ArrayList<ArticleDTO> getArticleDTOs(ArrayList<Long> keys) {
	return eao.getArticleDTOs(keys);
}

@Override
public ArrayList<ArticleDTO> getArticlesAll() {
	return eao.getArticlesAll();
}


@Override
public Boolean deleteZacon(Long id) {
	return eao.deleteZacon(id);
}

@Override
public ZaconDTO getZacon(Long id) {
	return eao.getZacon(id);
}

@Override
public ZaconDTO updateZacon(ZaconDTO aDTO) {
	return eao.updateZacon(aDTO);
}

@Override
public ArrayList<ZaconDTO_Light> getZaconDTOs(ArrayList<Long> keys) {
	return eao.getZaconDTOs(keys);
}

@Override
public ArrayList<ZaconDTO_Light> getZaconsAll() {
	return eao.getZaconsAll();
}

@Override
public ArrayList<ZaconDTO> getZaconDTOs_Normal(ArrayList<Long> keys) {
	return eao.getZaconDTOs_Normal(keys);
}

@Override
public Boolean deletePublication(Long id) {
	return eao2.deletePublication(id);
}

@Override
public PublicationDTO getPublication(Long id) {
	return eao2.getPublication(id);
}

@Override
public PublicationDTO updatePublication(PublicationDTO aDTO) {
	return eao2.updatePublication(aDTO);
}

@Override
public ArrayList<PublicationDTO_Light> getPublicationDTOs(ArrayList<Long> keys) {
	return eao2.getPublicationDTOs(keys);
}

@Override
public ArrayList<PublicationDTO_Light> getPublicationsAll() {
	return eao2.getPublicationsAll();
}

@Override
public ArrayList<PublicationDTO> getPublicationDTOs_Normal(ArrayList<Long> keys) {
	return eao2.getPublicationDTOs_Normal(keys);
}

@Override
public ArrayList<PublicationDTO_Light> getPublications(int limit, int offset) {
	return eao2.getPublications(limit, offset);
}

@Override
public ArrayList<TopicDTO> getTopics() {
	return eao2.getTopics();
}

/////////////////////

@Override
public Boolean deleteConsultation(Long id) {
	return eao3.deleteConsultation(id);
}

@Override
public ConsultationDTO getConsultation(Long id) {
	return eao3.getConsultation(id);
}

@Override
public ConsultationDTO updateConsultation(ConsultationDTO aDTO) {
	return eao3.updateConsultation(aDTO);
}

@Override
public ArrayList<ConsultationDTO_Light> getConsultationDTOs(ArrayList<Long> keys) {
	return eao3.getConsultationDTOs(keys);
}

@Override
public ArrayList<ConsultationDTO_Light> getConsultationsAll() {
	return eao3.getConsultationsAll();
}

@Override
public ArrayList<ConsultationDTO> getConsultationDTOs_Normal(ArrayList<Long> keys) {
	return eao3.getConsultationDTOs_Normal(keys);
}

@Override
public ArrayList<ConsultationDTO_Light> getConsultations(int limit, int offset) {
	return eao3.getConsultations(limit, offset);
}

@Override
public ArrayList<TopicDTO> getJuryTopics() {
	return eao3.getTopics();
}


} // end class
