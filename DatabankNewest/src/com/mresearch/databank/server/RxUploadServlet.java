package com.mresearch.databank.server;

import gwtupload.server.UploadAction;
import gwtupload.server.UploadServlet;
import gwtupload.server.exceptions.UploadActionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.mplatforma.amr.service.remote.RxStorageBeanRemote;


/**
 * Servlet implementation class RxUploadServlet
 */
public class RxUploadServlet extends UploadAction {

	
	private static RxStorageBeanRemote eao;
	static
	{
		Object obj = new String("some");
		try {
		  InitialContext ic = new InitialContext();
		  System.out.println("start lookup");
		  final String jndiName = "java:global/DatabankEnterprise-ejb/RxStorageRemoteBean";
		  obj = ic.lookup(jndiName);
		  System.out.println("lookup returned " + obj);
		  eao = (RxStorageBeanRemote) obj;
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	  private static final long serialVersionUID = 1L;
	  
	  Hashtable<String, String> receivedContentTypes = new Hashtable<String, String>();
	  /**
	   * Maintain a list with received files and their content types. 
	   */
	  Hashtable<String, File> receivedFiles = new Hashtable<String, File>();

	  Hashtable<String, Long> receivedFileIds = new Hashtable<String, Long>();

	  /**
	   * Override executeAction to save the received files in a custom place
	   * and delete this items from session.  
	   */
	  @Override
	  public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {
	   
		  
		String response = "";
	    int cont = 0;
	    for (FileItem item : sessionFiles) {
	      if (false == item.isFormField()) {
	        cont ++;
	        try {
	        	
	        	
		      
		          
	          /// Create a new file based on the remote file name in the client
	          // String saveName = item.getName().replaceAll("[\\\\/><\\|\\s\"'{}()\\[\\]]+", "_");
	          // File file =new File("/tmp/" + saveName);
	          
	          /// Create a temporary file placed in /tmp (only works in unix)
	          // File file = File.createTempFile("upload-", ".bin", new File("/tmp"));
	          
	          /// Create a temporary file placed in the default system temp folder
	          
		          
		      //File file = File.createTempFile("upload-", ".bin");
	          //item.write(file);
	          
	          /// Save a list with the received files
	          
	          
	        //  receivedFiles.put(item.getFieldName(), file);
	          receivedContentTypes.put(item.getFieldName(), item.getContentType());
	          
	          byte [] arr = new byte[(int) item.getSize()];
	          item.getInputStream().read(arr);
	          long id = eao.storeFile(arr, item.getName(), item.getContentType());
	          
	          receivedFileIds.put(item.getFieldName(),id);
		          
	          /// Send a customized message to the client.
	          response += "<RxStoreId>" + id+"</RxStoreId>";
	          //response += "File saved as " + file.getAbsolutePath();
	          	     
	          
	          
	          

	        } catch (Exception e) {
	          throw new UploadActionException(e);
	        }
	      }
	    }
	    
	    /// Remove files from session because we have a copy of them
	    removeSessionFileItems(request);
	    
	    /// Send your customized message to the client.
	    return response;
	  }
	  
	  /**
	   * Get the content of an uploaded file.
	   */
	  @Override
	  public void getUploadedFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
//	    String fieldName = request.getParameter(UploadServlet.PARAM_SHOW);
//	    File f = receivedFiles.get(fieldName);
//	    if (f != null) {
//	      response.setContentType(receivedContentTypes.get(fieldName));
//	      FileInputStream is = new FileInputStream(f);
//	      copyFromInputStreamToOutputStream(is, response.getOutputStream());
//	    } else {
//	      renderXmlResponse(request, response, ERROR_ITEM_NOT_FOUND);
//	   }
	  }
	  
	  /**
	   * Remove a file when the user sends a delete request.
	   */
	  @Override
	  public void removeItem(HttpServletRequest request, String fieldName)  throws UploadActionException {
//	    File file = receivedFiles.get(fieldName);
//	    receivedFiles.remove(fieldName);
//	    receivedContentTypes.remove(fieldName);
//	    if (file != null) {
//	      file.delete();
//	    }
	  }
	}
