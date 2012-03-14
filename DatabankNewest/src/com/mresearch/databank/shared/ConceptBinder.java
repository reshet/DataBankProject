package com.mresearch.databank.shared;

import java.util.ArrayList;

import com.mresearch.databank.client.views.ConceptContentsItem;
import com.mresearch.databank.client.views.ConceptItem;

public abstract class ConceptBinder<T> {
	public abstract void loadContents(ArrayList<Long> keys);
	public abstract ConceptContentsItem composeContentsItem(T dto);
	private ArrayList<T> dtos_handler;
	private boolean loaded = false;
	private ConceptItem<?>.LoadMonitor loadMonitor;
	public void setLoadMonitor(ConceptItem<?>.LoadMonitor loadMonitor)
	{
		this.loadMonitor = loadMonitor;
	}
	public void setAsyncResultHandler(ArrayList<T> dtos_h)
	{
		this.dtos_handler = dtos_h;
	}
	public ArrayList<T> getDtos_handler() {
		return dtos_handler;
	}
	public boolean isLoaded() {
		return loaded;
	}
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
		if (this.loaded) loadMonitor.loadSucess();
	}
	
}
