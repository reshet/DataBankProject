package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.shared.IPickableElement;


public class PickElementsTableView extends Composite {

	private static PickElementsTableViewUiBinder uiBinder = GWT
			.create(PickElementsTableViewUiBinder.class);

	interface PickElementsTableViewUiBinder extends
			UiBinder<Widget, PickElementsTableView> {
	}
	private IPickBinder binder;
	@UiField FlexTable elemTable;
	@UiField Button cmdBtn;
	@UiField Label title;
	private ArrayList<IPickableElement> elements;
	private ArrayList<Long> alreadySelectedElems;
	public void processChoice()
	{
		//this.binder = binder;
		binder.processPickChoice(getSelectedKeys());
	}
	public PickElementsTableView(ArrayList<IPickableElement> elems,ArrayList<Long> alreadySelectedElems,IPickBinder customizedBinder) {
		initWidget(uiBinder.createAndBindUi(this));
		this.alreadySelectedElems = alreadySelectedElems;
		this.binder = customizedBinder;
		cmdBtn.setText(binder.getCommandName());
		title.setText(binder.getTitle());
		this.elements = elems;
		elemTable.clear();
		int i = 0;
		
		for(IPickableElement elem:elements)
		{
			PickableElementView v_elem = new PickableElementView(elem.getTextRepresent());
			if (alreadySelectedElems.contains(elem.getID())) v_elem.setChecked(true);else v_elem.setChecked(false);
			elemTable.setWidget(i, 0, v_elem);
			//elemTable.setWidget(i, 0, new CheckBox(elem.getTextRepresent()));
			
			//elemTable.getCellFormatter().addStyleName(i, 0,
		     //       "friendNameInList");
			i++;		 
		}
		//elemTable.setSize("500px", "500px");
	}
	public ArrayList<Long> getSelectedKeys()
	{
		ArrayList<Long> arr = new ArrayList<Long>();
		int i = 0;
		for(IPickableElement elem:elements)
		{
			if(((PickableElementView)elemTable.getWidget(i, 0)).isChecked())
			{
				arr.add(elem.getID());
			}
			i++;
		}
		return arr;
	}

	@UiHandler(value="cmdBtn")
	public void onCmdClick(ClickEvent e)
	{
		processChoice();
	}
}
