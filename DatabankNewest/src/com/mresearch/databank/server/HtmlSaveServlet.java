package com.mresearch.databank.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mplatforma.amr.service.remote.UserSocioResearchBeanRemote;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.UserAccountDTO;




public class HtmlSaveServlet extends HttpServlet {

	private static UserSocioResearchBeanRemote eao;
	static
	{
		Object obj = new String("some");
		try {
		  InitialContext ic = new InitialContext();
		  System.out.println("start lookup");
		  final String jndiName = "java:global/DatabankEnterprise-ejb/UserSocioResearchRemoteBean";
		  obj = ic.lookup(jndiName);
		  System.out.println("lookup returned " + obj);
		  eao = (UserSocioResearchBeanRemote) obj;
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws IOException {
		//res.setContentType("application/octet-stream");
		res.setContentType("text/html");
		//res.setCharacterEncoding("UTF-8");
		
		
		String original_file_name = "saved_distr.html";
		res.setHeader( "Content-Disposition", "attachment; filename=\"" + original_file_name +"\"" );
        //res.setHeader("Content-Type", "text/html");
        String st = (String) req.getParameter("content");
        //st = new String(st.getBytes(),"UTF-8");
        
        HttpSession session = req.getSession(true);
        UserAccountDTO userAcc =   (UserAccountDTO) session.getAttribute("user");
       // st = new String(st.getBytes(),"UTF-8");
        
        String dd = getHeaderSection(userAcc)+getResearchSection(userAcc)+getWeightsSection(userAcc)+getFiltersSection(userAcc);
        //userAcc.
        dd = new String(dd.getBytes("UTF-8"),"ISO-8859-1");
        String stt ="<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=UTF-8\"></head><body>"+st+"</body></html>"; 
        //String s = URL.decode(st);
        
      //  res.getWriter().println(stt);
      //res.setCharacterEncoding("UTF-8");
    	//res.setCharacterEncoding("CP1251");
    	String sttt ="<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"></head><body>"+res.getCharacterEncoding()+dd+"</body></html>"; 
    	 res.getWriter().println(res.getCharacterEncoding()+dd);
    	//res.setCharacterEncoding("cp1251");
     	
    	 res.getWriter().println(stt);
    	     
    	 res.getWriter().close();
        //res.setHeader("Location", info.getFilename());   
        //res.
        
        
       // res.setHeader(, arg1)
        
        
        //blobstoreService.serve(blobKey, res);
       
    }
	private String getHeaderSection(UserAccountDTO dto)
	{
		String ans = "<h1>Отчет о распределении переменной в массиве</h1><br/>";
		Date dt = new Date();
		ans+="<h3>Время генерации: "+dt+"</h3><br/><br/>";
		return ans;
	}
	private String getFiltersSection(UserAccountDTO dto)
	{
		String ans = "<h3>Использованные фильтры:</h3><br/>";
		Long research_id = getResearchID(dto);
		if (research_id != null && dto != null)
		{
			ArrayList<String> filters = dto.getFiltersToProcess(research_id);
			if (filters== null)
			{
				ans+=" нет";
			}else
			if (filters.size() == 0)
			{
				ans+=" нет";
			}else
			{
				for(String filt:filters)
				{
					ans+="<p>"+filt+"</p>";
				}
			}
		}else
		{
			ans+=" нет";
		}
		ans+="<br/>";
		return ans;
	}
	private long getResearchID(UserAccountDTO dto)
	{
		//suppose that research id is missing
		if(dto != null)
		{
//			if(dto.getCurrant_var()!= 0)
//			{
//				Var var = null;
//				var = pm.getObjectById(Var.class,dto.getCurrant_var());
//				if (var != null) return var.getResearch_id();
		//	}
		if(dto.getCurrent_research()!= 0)
			{
				return dto.getCurrent_research();
			}
		}
		return 0;
	}
	private String getWeightsSection(UserAccountDTO dto)
	{
		String ans = "<h3>Использованные веса: </h3>";
		Long research_id = getResearchID(dto);
		if (dto != null && dto.getWeights_use()==1 && research_id!= null)
		{
				SocioResearchDTO research;
			    try {
			      research = eao.getResearch(research_id);
			      String var_weight_name = null;
			      if(research != null )var_weight_name= research.getVar_weight_name();
			      if (var_weight_name != null) ans+=var_weight_name;
			      	else 	ans+=" нет";
			    	  
			    } catch (Exception e) {
			      e.printStackTrace();
			    } finally {
			    }
		}else
		{
			ans+=" нет";
		}
		ans+="<br/>";
		return ans;
	}
	private String getResearchSection(UserAccountDTO dto)
	{
		String ans = "<h3>Исследование: </h3>";
		Long res_id = getResearchID(dto);
		if (res_id!= null)
		{
			 SocioResearchDTO research = null;
			    try {
			      research = eao.getResearch(res_id);
			      String name = research.getName();
			      ans+=name;
			    } catch (Exception e) {
			      e.printStackTrace();
			    } finally {
			    }
		}else
		{
			ans+=" неизвестно";
		}
		ans+="<br/>";
		return ans;
	}
}