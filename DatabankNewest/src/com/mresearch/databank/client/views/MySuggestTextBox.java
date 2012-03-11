package com.mresearch.databank.client.views;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.TextBoxBase;

public class MySuggestTextBox extends TextBoxBase {	
	protected MySuggestTextBox(Element elem) 
	{
		super(elem);
	}
	public MySuggestTextBox() 
	{
		this(Document.get().createTextInputElement(), "gwt-TextBox");
	}
	public MySuggestTextBox(InputElement elem,String styleName)
	{
		super(elem);
		if (styleName != null)
		{
			setStyleName(styleName);
		}
	}
	public String getText()
	{
		String totalString=super.getText();
		String newString=totalString;
		if(totalString!=null && !totalString.trim().equals(""))
		{
			int lastComma=totalString.indexOf(",");
			if(lastComma>0){
				newString=totalString.trim().substring(lastComma+1);
			}
		}
		return newString;
	}
	public String getTotalText()
	{
		return super.getText();
	}
//close of getText
	public void setText(String text)
	{
		String totalString=super.getText();
		if(text!=null && text.equals("")){
			super.setText(text);
		}
		else 
		{
			if(totalString!=null)
			{
				int lastComma=totalString.trim().indexOf(",");
				if(lastComma>0)
				{
					totalString=totalString.trim().substring(0, lastComma);
				}else
				{
					totalString="";
				}
				if (!totalString.trim().endsWith(",") && !totalString.trim().equals("")) {
					totalString = totalString + ", ";
				}
				totalString = totalString + text + ", ";
				super.setText(totalString);
			}
		}//close of if loop
	}//Close of Set Text
}