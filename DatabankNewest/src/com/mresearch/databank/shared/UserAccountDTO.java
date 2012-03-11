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
package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;





public class UserAccountDTO implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 4066887588027126691L;
private long id;
  private String name;
  private String emailAddress;
  private String accountType;
  
  private Integer weights_use = 0;
  private Integer filters_use = 0;
  private ArrayList<String> filters;
  private ArrayList<Long> filters_categories = new ArrayList<Long>();
  private ArrayList<Integer> filters_usage = new ArrayList<Integer>();
  private long current_research,currant_var;
  
  public UserAccountDTO() {
  
  }

  
  public UserAccountDTO(String email, String name,String accType) {
    this();
    this.setEmailAddress(email);
    this.setName(name);
    this.setAccountType(accType);
  }

  public long getId() {
    return id;
  }
  

  public void setId(long id) {
    this.id = id;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

public String getAccountType() {
	return accountType;
}

public void setAccountType(String accountType) {
	this.accountType = accountType;
}

public Integer getWeights_use() {
	return weights_use;
}

public void setWeights_use(Integer weights_use) {
	this.weights_use = weights_use;
}

public Integer getFilters_use() {
	return filters_use;
}

public void setFilters_use(Integer filters_use) {
	this.filters_use = filters_use;
}

public ArrayList<String> getFilters(Long research_id) {
	 init_d();
	if (filters_categories.contains(research_id))
	{
		ArrayList<String> ans = new ArrayList<String>();
		int i = 0;
		for(Long filtering_research:filters_categories)
		{
			if (filtering_research.equals(research_id)) ans.add(filters.get(i));
			i++;
		}
		return ans;
	}
	return null;
}
public ArrayList<String> getFilters() {
	 init_d();
	return filters;
}

public void setFilters(ArrayList<String> filters) {
	this.filters = filters;
}

public ArrayList<Integer> getFilters_usage(long research_id) {
         init_d();
	ArrayList<Integer> ans = new ArrayList<Integer>();
	
	if (filters_categories.contains(research_id))
	{
		int i = 0;
		for(Long filtering_research:filters_categories)
		{
			if (filtering_research.equals(research_id) && filters_usage.size() > i) ans.add(filters_usage.get(i));
			i++;
		}
	}
	return ans;
}
public ArrayList<Integer> getFilters_usage() {
	 init_d();
	return filters_usage;
}

public ArrayList<Long> getFilters_categories() {
	 init_d();
	return filters_categories;
}

public void setFilters_categories(ArrayList<Long> filters_categories) {
     init_d();
    if(filters_categories == null)return;
	int newsize = filters_categories.size() - this.filters_categories.size();
	if (newsize > 0)
	{
		for(int i = 0; i < newsize;i++) this.filters_usage.add((Integer)0);
	}else
	if (newsize < 0)
	{
		int oldsize = this.filters_usage.size();
		for(int i = 0; i < newsize ;i++) this.filters_usage.remove(oldsize-1-i);
	}
	
	this.filters_categories = filters_categories;
}

private void init_d()
{
       if (this.filters_usage == null)this.filters_usage = new ArrayList<Integer>();
        if (this.filters_categories == null)this.filters_categories = new ArrayList<Long>();
}
public void setFilters_usage(ArrayList<Integer> filters_usage,long research_id) {
         init_d();
        
	int newsize = this.filters_categories.size() - this.filters_usage.size();
	if (newsize > 0)
	{
		for(int i = 0; i < newsize;i++) this.filters_usage.add((Integer)0);
	}else
	if (newsize < 0)
	{
		int oldsize = this.filters_usage.size();
		for(int i = 0; i < newsize ;i++) this.filters_usage.remove(oldsize-1-i);
	}
	
	if (filters_categories.contains(research_id))
	{
		int i = 0,j = 0;
		for(Long filtering_research:filters_categories)
		{
			if (filtering_research.equals(research_id) && j < filters_usage.size()) this.filters_usage.set(i, filters_usage.get(j++));
			i++;
		}
	
//		ArrayList<String> new_filters_categs = new ArrayList<String>();
//		int i = 0;
//		for(String filtering_research:filters_categories)
//		{
//			if (filtering_research.equals(research_id))
//			{
//				filters.remove(i);
//				this.filters_usage.remove(i);
//			}
//			else
//			{
//				new_filters_categs.add(filtering_research);
//			}
//			i++;
//		}
//		filters_categories = new_filters_categs;
//		//for(String filter)
	}

	//ArrayList<String> arr = getFilters_usage(research_id);
	//arr = filters_usage;
}

public void setFilters_usage(ArrayList<Integer> filters_usage) {
	this.filters_usage = filters_usage;
}

public ArrayList<String> getFiltersToProcess(long research_id) {
 init_d();
    if (filters_categories.contains(research_id))
	{
		ArrayList<String> toProcess = new ArrayList<String>();
		int i = 0;
		for(Long filtering_research:filters_categories)
		{
			if (filtering_research!= null && filtering_research.equals(research_id) && filters_usage.size() > i && filters_usage.get(i).equals(new Integer((Integer)1))) 
			{
				toProcess.add(filters.get(i));
			}
			i++;
		}
		return toProcess;
	}
	return null;
}
public ArrayList<Long> getFiltersToProcessIndecies(long research_id) {
         init_d();
	if (filters_categories.contains(research_id))
	{
		ArrayList<Long> toProcess = new ArrayList<Long>();
		int i = 0;
		for(Long filtering_research:filters_categories)
		{
			if (filtering_research!= null && filtering_research.equals(research_id) && filters_usage!= null && filters_usage.get(i)!=null && filters_usage.get(i).equals(new Integer((Integer)1))) 
			{
				toProcess.add(new Long(i));
			}
			i++;
		}
		return toProcess;
	}
	return null;
}



public long getCurrant_var() {
	return currant_var;
}

public void setCurrant_var(long currant_var) {
	this.currant_var = currant_var;
}

public long getCurrent_research() {
	return current_research;
}

public void setCurrent_research(long current_research) {
	this.current_research = current_research;
}
	
//	for(String filter:getFilters(research_id))
//	{
//		if (filters_usage.contains(getFilters(research_id).indexOf(filter)))
//		{
//			toProcess.add(filter);
//		}
//	}
	

}
