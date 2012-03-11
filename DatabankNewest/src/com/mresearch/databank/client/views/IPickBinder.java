package com.mresearch.databank.client.views;

import java.util.ArrayList;

public interface IPickBinder {
	public void processPickChoice(ArrayList<Long> selected_keys);
	public String getCommandName();
	public String getTitle();
	//public void setPreSelected(ArrayList<String> pre_selected_keys);
}
