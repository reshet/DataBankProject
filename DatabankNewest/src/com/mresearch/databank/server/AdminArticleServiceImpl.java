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

import com.mplatforma.amr.service.remote.AdminLawBeanRemote;
import com.mplatforma.amr.service.remote.AdminSocioResearchBeanRemote;
import com.mresearch.databank.client.service.AdminArticleService;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.shared.ArticleDTO;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.ZaconDTO;
import com.mresearch.databank.shared.ZaconDTO_Light;


/**
 * the FriendsService communicates Friend data via RPC between client and server.
 */
@SuppressWarnings("serial")
public class AdminArticleServiceImpl extends RemoteServiceServlet implements
    AdminArticleService {

	private static AdminLawBeanRemote eao;
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

} // end class
